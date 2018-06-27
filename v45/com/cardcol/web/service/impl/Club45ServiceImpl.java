package com.cardcol.web.service.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.orm.hibernate4.HibernateOptimisticLockingFailureException;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cardcol.web.balance.IBalance;
import com.cardcol.web.service.IClub45Service;
import com.databaseConnection.DataBaseConnection;
import com.freegym.web.basic.Member;
import com.freegym.web.common.Constants;
import com.freegym.web.order.ActiveOrder;
import com.freegym.web.service.impl.WorkoutServiceImpl;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.sanmen.web.core.bean.BaseId;
import com.sanmen.web.core.common.LogicException;
import com.sanmen.web.core.common.MD5;
import com.sanmen.web.core.common.PageInfo;
import com.sanmen.web.core.common.WebUtils;
import com.sanmen.web.core.log.Log;
import com.sanmen.web.core.utils.LnglatUtil;
import com.sanmen.web.core.utils.LogUtils;

@Service("club45Service")
public class Club45ServiceImpl extends WorkoutServiceImpl implements IClub45Service, Constants {

	@Autowired
	@Qualifier("activeBalanceImpl")
	IBalance activeBalanceImpl;

	/*
	 * 修改成日卡前的代码
	 * 
	 * @Override public PageInfo queryOneCardList(String city, PageInfo
	 * pageInfo) { String sql =
	 * "SELECT a.id,a.prod_name,a.prod_price,a.prod_image,a.prod_content,a.prod_summary,a.prodPeriodMonth,b.clubNum,IFNULL(c.saleNum,0) AS saleNum FROM ("
	 * +
	 * "SELECT id,prod_name,prod_price,prod_image,prod_content,prod_summary,(CASE prod_period_unit WHEN 'A' THEN prod_period WHEN 'B' THEN prod_period*4 ELSE prod_period*12 END) AS prodPeriodMonth "
	 * +
	 * "FROM tb_product_v45 WHERE id in (SELECT pc.product from tb_product_club_v45 pc, tb_member m WHERE pc.club = m.id and m.city = ?) AND prod_status = 'B' "
	 * + ") a LEFT JOIN (" +
	 * "SELECT pc.product,COUNT(1) AS clubNum from tb_product_club_v45 pc,tb_member m WHERE pc.club = m.id GROUP BY pc.product) b "
	 * + "ON a.id = b.product LEFT JOIN (" +
	 * "SELECT order_product,COUNT(order_product) AS saleNum from tb_product_order_v45 GROUP BY order_product) c ON a.id = c.order_product"
	 * ; pageInfo = this.findPageBySql(sql, pageInfo, city); return pageInfo; }
	 */

	@Override
	public PageInfo queryOneCardList(String city, PageInfo pageInfo) {
		String sql = "SELECT a.id,a.prod_name,a.prod_price,a.prod_image,a.prod_detail_image,a.prod_content,a.prod_summary,a.prodPeriodMonth,b.clubNum,IFNULL(c.saleNum,0) AS saleNum FROM ("
				+ "SELECT id,prod_name,prod_price,prod_image,prod_detail_image,prod_content,prod_summary,(CASE prod_period_unit WHEN 'D' THEN prod_period WHEN 'A' THEN prod_period * 30 WHEN 'B' THEN prod_period * 3 * 30 ELSE prod_period * 12 * 30 END) AS prodPeriodMonth "
				+ "FROM tb_product_v45 WHERE id in (SELECT pc.product from tb_product_club_v45 pc, tb_member m WHERE pc.club = m.id and m.city = ?) AND prod_status = 'B' "
				+ ") a LEFT JOIN ("
				+ "SELECT pc.product,COUNT(1) AS clubNum from tb_product_club_v45 pc,tb_member m WHERE pc.club = m.id GROUP BY pc.product) b "
				+ "ON a.id = b.product LEFT JOIN ("
				+ "SELECT order_product,COUNT(order_product) AS saleNum from tb_product_order_v45 where status != '0' and status != '' GROUP BY order_product) c ON a.id = c.order_product";
		pageInfo = this.findPageBySql(sql, pageInfo, city);
		return pageInfo;
	}

	/*      */ @Transactional(propagation = Propagation.REQUIRED)
	/*      */ public Object saveOrUpdate(Object obj) throws LogicException
	/*      */ {
		/* 376 */ boolean hasLog = WebUtils.isHasLog();
		/*      */ try {
			/* 378 */ addLog(obj);
			/* 379 */ if ((obj instanceof BaseId)) {
				/* 380 */ BaseId bid = (BaseId) obj;
				/* 381 */ if ((bid.getId() != null) && (hasLog)) {
					/* 382 */ Log log = LogUtils.makeLog(this, obj);
					/* 383 */ getHibernateTemplate().merge(log);
					/*      */ }
				/*      */ }
			/* 386 */ return getHibernateTemplate().merge(obj);
			/*      */ } catch (HibernateOptimisticLockingFailureException e) {
			/* 388 */ throw new LogicException("您当前的数据已经被其它用户更改或删除，请重新刷新再试！");
			/*      */ } catch (Exception e) {
			/* 390 */ throw new LogicException(e);
			/*      */ }
		/*      */ }

	/*
	 * @Override public PageInfo queryByPriceType(String city, String priceType,
	 * PageInfo pageInfo) {
	 * 
	 * String appendSql = priceType == "A" ? "AND prod_price<500" : priceType ==
	 * "B" ? "AND prod_price<999 AND prod_price>=500" : priceType == "C" ?
	 * "AND prod_price<2999 AND prod_price>=1000" : "AND prod_price>=3000";
	 * String sql =
	 * "SELECT a.id,a.prod_name,a.prod_price,a.prod_image,a.prod_content,a.prodPeriodMonth,b.clubNum,IFNULL(c.saleNum,0) AS saleNum FROM ("
	 * +
	 * "SELECT id,prod_name,prod_price,prod_image,prod_content,(CASE prod_period_unit WHEN 'A' THEN prod_period WHEN 'B' THEN prod_period*4 ELSE prod_period*12 END) AS prodPeriodMonth "
	 * +
	 * "FROM tb_product_v45 WHERE id in (SELECT pc.product from tb_product_club_v45 pc, tb_member m WHERE pc.club = m.id and m.city = ?) AND prod_status = 'B' "
	 * + appendSql + ") a LEFT JOIN (" +
	 * "SELECT pc.product,COUNT(1) AS clubNum from tb_product_club_v45 pc,tb_member m WHERE pc.club = m.id and m.city = ? GROUP BY pc.product) b "
	 * + "ON a.id = b.product LEFT JOIN (" +
	 * "SELECT order_product,COUNT(order_product) AS saleNum from tb_product_order_v45 GROUP BY order_product) c ON a.id = c.order_product"
	 * ; pageInfo = this.findPageBySql(sql, pageInfo, city, city); return
	 * pageInfo; }
	 */

	/**
	 * 查询门店分布及门店明细信息
	 */
	@Override
	public PageInfo queryProduct45Member(String city, PageInfo pageInfo, Double longitude, Double latitude, String id) {
		String sql = "";
		if ("".equals(id) || id == null || "null".equals(id) || "0".equals(id)) {
			sql = "SELECT m.id, m.name, m.image, m.longitude, m.latitude, ROUND(6378.138 * 2 * ASIN(SQRT(POW(SIN((? * PI() / 180 - m.latitude * PI() / 180) / 2),2) + COS(? * PI() / 180) * COS(m.latitude * PI() / 180) * POW(SIN((? * PI() / 180 - m.longitude * PI() / 180) / 2),2))) * 1000) AS distance , m.address, m.tell mobilephone, "
					+ " IFNULL(v.totalScore, 0) AS totalScore, IFNULL(v.deviceScore, 0) AS deviceScore, IFNULL(v.evenScore, 0) AS evenScore, IFNULL(v.serviceScore, 0) AS serviceScore, COUNT(z.memberAudit) AS evaluateNum  FROM tb_member m LEFT JOIN v_member_score_detail v ON m.id = v.id LEFT JOIN ( SELECT si.memberAudit FROM"
					+ " tb_sign_in si, tb_member_evaluate me WHERE si.id = me.signIn) z ON m.id = z.memberAudit WHERE m.city = ? AND M.role = 'E' AND m.longitude != '' AND m.latitude != '' AND m.grade = 1 GROUP BY m.id ORDER BY distance";
			pageInfo = findPageBySql(sql, pageInfo, new Object[] { latitude, latitude, longitude, city });
		} else {
			sql = "select m.id,m.name,m.image,m.longitude,m.latitude,ROUND(6378.138 * 2 * ASIN(SQRT(POW(SIN((? * PI() / 180 - m.latitude * PI() / 180) / 2),2) + COS(? * PI() / 180) * COS(m.latitude * PI() / 180) * POW(SIN((? * PI() / 180 - m.longitude * PI() / 180) / 2),2))) * 1000) AS distance ,m.address,m.tell mobilephone,IFNULL(v.totalScore,0) as totalScore,IFNULL(v.deviceScore,0) as deviceScore,IFNULL(v.evenScore,0) as evenScore,IFNULL(v.serviceScore,0) as serviceScore,COUNT(z.memberAudit) AS evaluateNum "
					+ "FROM tb_member m LEFT JOIN v_member_score_detail v ON m.id = v.id "
					+ "LEFT JOIN (SELECT si.memberAudit FROM tb_sign_in si,tb_member_evaluate me WHERE si.id = me.signIn) z ON m.id = z.memberAudit "
					+ "WHERE m.id in (select p.CLUB FROM tb_product_club_v45 p WHERE p.PRODUCT = ?) and m.grade = 1 AND m.longitude != '' AND m.latitude != ''  GROUP BY m.id ORDER BY distance";
			pageInfo = findPageBySql(sql, pageInfo, new Object[] { latitude, latitude, longitude, id });
		}

		return pageInfo;
	}

	/**
	 * 查询门店分布及门店明细信息
	 */
	@Override
	public PageInfo queryStore4EWX(String city, PageInfo pageInfo, Double longitude, Double latitude, String id) {
		try {
			String sql = "";
			List<Map<String, Object>> storeList = new ArrayList<Map<String, Object>>();
			int totalCount = 0;
			String sqlCount = "";
			Map<String, Object> countMap = new HashMap<String, Object>();
			//判断用户上传的经纬度是否为空
			if (longitude == 0 || latitude == 0 ){
				if ("".equals(id) || id == null || "null".equals(id) || "0".equals(id)) {
					sql = "SELECT m.id, m.name, m.image, m.longitude, m.latitude, ROUND(6378.138 * 2 * ASIN(SQRT(POW(SIN((m.latitude * PI() / 180 - m.latitude * PI() / 180) / 2),2) + COS(m.latitude * PI() / 180) * COS(m.latitude * PI() / 180) * POW(SIN((m.longitude * PI() / 180 - m.longitude * PI() / 180) / 2),2))) * 1000) AS distance , m.address, m.tell, "
							+ "  (CASE " + " WHEN v.totalScore IN (0-5) THEN v.totalScore "
							+ " WHEN v.totalScore IN (6,20) THEN 1 " + " WHEN v.totalScore IN (21,40) THEN 2 "
							+ " WHEN v.totalScore IN (41,60) THEN 3 " + " WHEN v.totalScore IN (61,80) THEN 4 "
							+ " WHEN v.totalScore IN (81,100) THEN 5 " + " ELSE 0 END ) as totalScore, " + " (CASE "
							+ " WHEN v.deviceScore IN (0-5) THEN v.deviceScore " + " WHEN v.deviceScore IN (6,20) THEN 1 "
							+ " WHEN v.deviceScore IN (21,40) THEN 2 " + " WHEN v.deviceScore IN (41,60) THEN 3 "
							+ " WHEN v.deviceScore IN (61,80) THEN 4 " + " WHEN v.deviceScore IN (81,100) THEN 5 "
							+ " ELSE 0 END ) as deviceScore, " + " (CASE " + " WHEN v.evenScore IN (0-5) THEN v.evenScore "
							+ " WHEN v.evenScore IN (6,20) THEN 1 " + " WHEN v.evenScore IN (21,40) THEN 2 "
							+ " WHEN v.evenScore IN (41,60) THEN 3 " + " WHEN v.evenScore IN (61,80) THEN 4 "
							+ " WHEN v.evenScore IN (81,100) THEN 5 " + " ELSE 0 END ) as evenScore, " + " (CASE "
							+ " WHEN v.serviceScore IN (0-5) THEN v.serviceScore "
							+ " WHEN v.serviceScore IN (6,20) THEN 1 " + " WHEN v.serviceScore IN (21,40) THEN 2 "
							+ " WHEN v.serviceScore IN (41,60) THEN 3 " + " WHEN v.serviceScore IN (61,80) THEN 4 "
							+ " WHEN v.serviceScore IN (81,100) THEN 5 " + " ELSE 0 END ) as serviceScore, "
							+ " COUNT(z.memberAudit) AS evaluateNum  FROM tb_member m LEFT JOIN v_member_score_detail v ON m.id = v.id LEFT JOIN ( SELECT si.memberAudit FROM"
							+ " tb_sign_in si, tb_member_evaluate me WHERE si.id = me.signIn) z ON m.id = z.memberAudit WHERE m.city = ? AND M.role = 'E' AND m.longitude != '' AND m.latitude != '' AND m.grade = 1 GROUP BY m.id ORDER BY distance";
					
					sqlCount = "select count(*) count from (" + sql + ") temp";
					Object[] obj = { city };
					storeList = DataBaseConnection.getList(sql, obj);
					countMap = DataBaseConnection.getOne(sqlCount, obj);
					totalCount = countMap.size() < 0 ? 0 : Integer.valueOf(countMap.get("count").toString());
					
				} else {
					sql = "select m.id,m.name,m.image,m.longitude,m.latitude,ROUND(6378.138 * 2 * ASIN(SQRT(POW(SIN((m.latitude * PI() / 180 - m.latitude * PI() / 180) / 2),2) + COS(m.latitude * PI() / 180) * COS(m.latitude * PI() / 180) * POW(SIN((m.longitude * PI() / 180 - m.longitude * PI() / 180) / 2),2))) * 1000) AS distance ,m.address,m.tell, "
							+ " (CASE " + " WHEN v.totalScore IN (0-5) THEN v.totalScore "
							+ " WHEN v.totalScore IN (6,20) THEN 1 " + " WHEN v.totalScore IN (21,40) THEN 2 "
							+ " WHEN v.totalScore IN (41,60) THEN 3 " + " WHEN v.totalScore IN (61,80) THEN 4 "
							+ " WHEN v.totalScore IN (81,100) THEN 5 " + " ELSE 0 END ) as totalScore, " + " (CASE "
							+ " WHEN v.deviceScore IN (0-5) THEN v.deviceScore " + " WHEN v.deviceScore IN (6,20) THEN 1 "
							+ " WHEN v.deviceScore IN (21,40) THEN 2 " + " WHEN v.deviceScore IN (41,60) THEN 3 "
							+ " WHEN v.deviceScore IN (61,80) THEN 4 " + " WHEN v.deviceScore IN (81,100) THEN 5 "
							+ " ELSE 0 END ) as deviceScore, " + " (CASE " + " WHEN v.evenScore IN (0-5) THEN v.evenScore "
							+ " WHEN v.evenScore IN (6,20) THEN 1 " + " WHEN v.evenScore IN (21,40) THEN 2 "
							+ " WHEN v.evenScore IN (41,60) THEN 3 " + " WHEN v.evenScore IN (61,80) THEN 4 "
							+ " WHEN v.evenScore IN (81,100) THEN 5 " + " ELSE 0 END ) as evenScore, " + " (CASE "
							+ " WHEN v.serviceScore IN (0-5) THEN v.serviceScore "
							+ " WHEN v.serviceScore IN (6,20) THEN 1 " + " WHEN v.serviceScore IN (21,40) THEN 2 "
							+ " WHEN v.serviceScore IN (41,60) THEN 3 " + " WHEN v.serviceScore IN (61,80) THEN 4 "
							+ " WHEN v.serviceScore IN (81,100) THEN 5 " + " ELSE 0 END ) as serviceScore, "
							+ "COUNT(z.memberAudit) AS evaluateNum "
							+ "FROM tb_member m LEFT JOIN v_member_score_detail v ON m.id = v.id "
							+ "LEFT JOIN (SELECT si.memberAudit FROM tb_sign_in si,tb_member_evaluate me WHERE si.id = me.signIn) z ON m.id = z.memberAudit "
							+ "WHERE m.id in (select p.CLUB FROM tb_product_club_v45 p WHERE p.PRODUCT = ?) and m.grade = 1 AND m.longitude != '' AND m.latitude != ''  GROUP BY m.id ORDER BY distance";
					
					sqlCount = "select count(*) count from (" + sql + ") temp";
					Object[] obj = { id };
					storeList = DataBaseConnection.getList(sql, obj);
					countMap = DataBaseConnection.getOne(sqlCount, obj);
					totalCount = countMap.size() < 0 ? 0 : Integer.valueOf(countMap.get("count").toString());
				}

			//用户上传的经纬度不为空
			} else {
				if ("".equals(id) || id == null || "null".equals(id) || "0".equals(id)) {
					sql = "SELECT m.id, m.name, m.image, m.longitude, m.latitude, ROUND(6378.138 * 2 * ASIN(SQRT(POW(SIN((? * PI() / 180 - m.latitude * PI() / 180) / 2),2) + COS(? * PI() / 180) * COS(m.latitude * PI() / 180) * POW(SIN((? * PI() / 180 - m.longitude * PI() / 180) / 2),2))) * 1000) AS distance , m.address, m.tell, "
							+ "  (CASE " + " WHEN v.totalScore IN (0-5) THEN v.totalScore "
							+ " WHEN v.totalScore IN (6,20) THEN 1 " + " WHEN v.totalScore IN (21,40) THEN 2 "
							+ " WHEN v.totalScore IN (41,60) THEN 3 " + " WHEN v.totalScore IN (61,80) THEN 4 "
							+ " WHEN v.totalScore IN (81,100) THEN 5 " + " ELSE 0 END ) as totalScore, " + " (CASE "
							+ " WHEN v.deviceScore IN (0-5) THEN v.deviceScore " + " WHEN v.deviceScore IN (6,20) THEN 1 "
							+ " WHEN v.deviceScore IN (21,40) THEN 2 " + " WHEN v.deviceScore IN (41,60) THEN 3 "
							+ " WHEN v.deviceScore IN (61,80) THEN 4 " + " WHEN v.deviceScore IN (81,100) THEN 5 "
							+ " ELSE 0 END ) as deviceScore, " + " (CASE " + " WHEN v.evenScore IN (0-5) THEN v.evenScore "
							+ " WHEN v.evenScore IN (6,20) THEN 1 " + " WHEN v.evenScore IN (21,40) THEN 2 "
							+ " WHEN v.evenScore IN (41,60) THEN 3 " + " WHEN v.evenScore IN (61,80) THEN 4 "
							+ " WHEN v.evenScore IN (81,100) THEN 5 " + " ELSE 0 END ) as evenScore, " + " (CASE "
							+ " WHEN v.serviceScore IN (0-5) THEN v.serviceScore "
							+ " WHEN v.serviceScore IN (6,20) THEN 1 " + " WHEN v.serviceScore IN (21,40) THEN 2 "
							+ " WHEN v.serviceScore IN (41,60) THEN 3 " + " WHEN v.serviceScore IN (61,80) THEN 4 "
							+ " WHEN v.serviceScore IN (81,100) THEN 5 " + " ELSE 0 END ) as serviceScore, "
							+ " COUNT(z.memberAudit) AS evaluateNum  FROM tb_member m LEFT JOIN v_member_score_detail v ON m.id = v.id LEFT JOIN ( SELECT si.memberAudit FROM"
							+ " tb_sign_in si, tb_member_evaluate me WHERE si.id = me.signIn) z ON m.id = z.memberAudit WHERE m.city = ? AND M.role = 'E' AND m.longitude != '' AND m.latitude != '' AND m.grade = 1 GROUP BY m.id ORDER BY distance";
					
					sqlCount = "select count(*) count from (" + sql + ") temp";
					Object[] obj = { latitude, latitude, longitude, city };
					storeList = DataBaseConnection.getList(sql, obj);
					countMap = DataBaseConnection.getOne(sqlCount, obj);
					totalCount = countMap.size() < 0 ? 0 : Integer.valueOf(countMap.get("count").toString());
					
				} else {
					sql = "select m.id,m.name,m.image,m.longitude,m.latitude,ROUND(6378.138 * 2 * ASIN(SQRT(POW(SIN((? * PI() / 180 - m.latitude * PI() / 180) / 2),2) + COS(? * PI() / 180) * COS(m.latitude * PI() / 180) * POW(SIN((? * PI() / 180 - m.longitude * PI() / 180) / 2),2))) * 1000) AS distance ,m.address,m.tell, "
							+ " (CASE " + " WHEN v.totalScore IN (0-5) THEN v.totalScore "
							+ " WHEN v.totalScore IN (6,20) THEN 1 " + " WHEN v.totalScore IN (21,40) THEN 2 "
							+ " WHEN v.totalScore IN (41,60) THEN 3 " + " WHEN v.totalScore IN (61,80) THEN 4 "
							+ " WHEN v.totalScore IN (81,100) THEN 5 " + " ELSE 0 END ) as totalScore, " + " (CASE "
							+ " WHEN v.deviceScore IN (0-5) THEN v.deviceScore " + " WHEN v.deviceScore IN (6,20) THEN 1 "
							+ " WHEN v.deviceScore IN (21,40) THEN 2 " + " WHEN v.deviceScore IN (41,60) THEN 3 "
							+ " WHEN v.deviceScore IN (61,80) THEN 4 " + " WHEN v.deviceScore IN (81,100) THEN 5 "
							+ " ELSE 0 END ) as deviceScore, " + " (CASE " + " WHEN v.evenScore IN (0-5) THEN v.evenScore "
							+ " WHEN v.evenScore IN (6,20) THEN 1 " + " WHEN v.evenScore IN (21,40) THEN 2 "
							+ " WHEN v.evenScore IN (41,60) THEN 3 " + " WHEN v.evenScore IN (61,80) THEN 4 "
							+ " WHEN v.evenScore IN (81,100) THEN 5 " + " ELSE 0 END ) as evenScore, " + " (CASE "
							+ " WHEN v.serviceScore IN (0-5) THEN v.serviceScore "
							+ " WHEN v.serviceScore IN (6,20) THEN 1 " + " WHEN v.serviceScore IN (21,40) THEN 2 "
							+ " WHEN v.serviceScore IN (41,60) THEN 3 " + " WHEN v.serviceScore IN (61,80) THEN 4 "
							+ " WHEN v.serviceScore IN (81,100) THEN 5 " + " ELSE 0 END ) as serviceScore, "
							+ "COUNT(z.memberAudit) AS evaluateNum "
							+ "FROM tb_member m LEFT JOIN v_member_score_detail v ON m.id = v.id "
							+ "LEFT JOIN (SELECT si.memberAudit FROM tb_sign_in si,tb_member_evaluate me WHERE si.id = me.signIn) z ON m.id = z.memberAudit "
							+ "WHERE m.id in (select p.CLUB FROM tb_product_club_v45 p WHERE p.PRODUCT = ?) and m.grade = 1 AND m.longitude != '' AND m.latitude != ''  GROUP BY m.id ORDER BY distance";
					
					sqlCount = "select count(*) count from (" + sql + ") temp";
					Object[] obj = { latitude, latitude, longitude, id };
					storeList = DataBaseConnection.getList(sql, obj);
					countMap = DataBaseConnection.getOne(sqlCount, obj);
					totalCount = countMap.size() < 0 ? 0 : Integer.valueOf(countMap.get("count").toString());
				}
				
			}
			for (Map<String, Object> map : storeList) {
				map.put("mobilephone", map.get("tell"));
			}
			pageInfo.setCurrentPage(1);
			pageInfo.setTotalCount(totalCount);
			pageInfo.setItems(storeList);
			pageInfo.setPageSize(totalCount);
			pageInfo.setTotalPage(1);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return pageInfo;
	}

	/**
	 * 查询门店分布及门店明细信息
	 */
	@Override
	public PageInfo queryStore(String city, PageInfo pageInfo, Double longitude, Double latitude, String id) {
		try {
			String sql = "";
			List<Map<String, Object>> storeList = new ArrayList<Map<String, Object>>();
			int totalCount = 0;
			String sqlCount = "";
			Map<String, Object> countMap = new HashMap<String, Object>();
			if ("".equals(id) || id == null || "null".equals(id) || "0".equals(id)) {
				sql = "SELECT m.id, m.name, m.image, m.longitude, m.latitude, ROUND(6378.138 * 2 * ASIN(SQRT(POW(SIN((? * PI() / 180 - m.latitude * PI() / 180) / 2),2) + COS(? * PI() / 180) * COS(m.latitude * PI() / 180) * POW(SIN((? * PI() / 180 - m.longitude * PI() / 180) / 2),2))) * 1000) AS distance , m.address, m.tell, "
						+ " IFNULL(v.totalScore, 0) AS totalScore, IFNULL(v.deviceScore, 0) AS deviceScore, IFNULL(v.evenScore, 0) AS evenScore, IFNULL(v.serviceScore, 0) AS serviceScore,  "
						+ " COUNT(sd.id) AS evaluateNum "
						+ " FROM tb_member m LEFT JOIN v_member_score_detail v ON m.id = v.id "
						+ " LEFT JOIN tb_sign_in sd ON sd.memberAudit = m.id "
						+ " WHERE m.city = ? AND M.role = 'E' AND m.longitude != '' AND m.latitude != '' AND m.grade = 1 GROUP BY m.id ORDER BY distance";

				sqlCount = "select count(*) count from (" + sql + ") temp";
				Object[] obj = { latitude, latitude, longitude, city };
				storeList = DataBaseConnection.getList(sql, obj);
				countMap = DataBaseConnection.getOne(sqlCount, obj);
				totalCount = countMap.size() < 0 ? 0 : Integer.valueOf(countMap.get("count").toString());

			} else {
				sql = "select m.id,m.name,m.image,m.longitude,m.latitude,ROUND(6378.138 * 2 * ASIN(SQRT(POW(SIN((? * PI() / 180 - m.latitude * PI() / 180) / 2),2) + COS(? * PI() / 180) * COS(m.latitude * PI() / 180) * POW(SIN((? * PI() / 180 - m.longitude * PI() / 180) / 2),2))) * 1000) AS distance ,m.address,m.tell,IFNULL(v.totalScore,0) as totalScore,IFNULL(v.deviceScore,0) as deviceScore,IFNULL(v.evenScore,0) as evenScore,IFNULL(v.serviceScore,0) as serviceScore, "
						+ " COUNT(sd.id) AS evaluateNum "
						+ " FROM tb_member m LEFT JOIN v_member_score_detail v ON m.id = v.id "
						+ " LEFT JOIN tb_sign_in sd ON sd.memberAudit = m.id "
						+ " WHERE m.id in (select p.CLUB FROM tb_product_club_v45 p WHERE p.PRODUCT = ?) and m.grade = 1 AND m.longitude != '' AND m.latitude != ''  GROUP BY m.id ORDER BY distance";

				sqlCount = "select count(*) count from (" + sql + ") temp";
				Object[] obj = { latitude, latitude, longitude, id };
				storeList = DataBaseConnection.getList(sql, obj);
				countMap = DataBaseConnection.getOne(sqlCount, obj);
				totalCount = countMap.size() < 0 ? 0 : Integer.valueOf(countMap.get("count").toString());
			}
			for (Map<String, Object> map : storeList) {
				map.put("mobilephone", map.get("tell"));
			}
			pageInfo.setCurrentPage(1);
			pageInfo.setTotalCount(totalCount);
			pageInfo.setItems(storeList);
			pageInfo.setPageSize(totalCount);
			pageInfo.setTotalPage(1);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return pageInfo;
	}

	/**
	 * 查询单个门店明细信息
	 */
	@Override
	public PageInfo queryClubById(PageInfo pageInfo, String id) {
		String sql = "SELECT m.id, m.name, m.image, m.longitude, m.latitude, m.address, m.tell mobilephone," + " (CASE "
				+ " WHEN v.totalScore IN (0-5) THEN v.totalScore " + " WHEN v.totalScore IN (6,20) THEN 1 "
				+ " WHEN v.totalScore IN (21,40) THEN 2 " + " WHEN v.totalScore IN (41,60) THEN 3 "
				+ " WHEN v.totalScore IN (61,80) THEN 4 " + " WHEN v.totalScore IN (81,100) THEN 5 "
				+ " ELSE 0 END ) as totalScore,   " + " (CASE  " + " WHEN v.deviceScore IN (0-5) THEN v.deviceScore "
				+ " WHEN v.deviceScore IN (6,20) THEN 1  " + " WHEN v.deviceScore IN (21,40) THEN 2 "
				+ " WHEN v.deviceScore IN (41,60) THEN 3 " + " WHEN v.deviceScore IN (61,80) THEN 4 "
				+ " WHEN v.deviceScore IN (81,100) THEN 5 " + " ELSE 0 END ) as deviceScore, " + " (CASE "
				+ " WHEN v.evenScore IN (0-5) THEN v.evenScore " + " WHEN v.evenScore IN (6,20) THEN 1 "
				+ " WHEN v.evenScore IN (21,40) THEN 2 " + " WHEN v.evenScore IN (41,60) THEN 3 "
				+ " WHEN v.evenScore IN (61,80) THEN 4 " + " WHEN v.evenScore IN (81,100) THEN 5 "
				+ " ELSE 0 END ) as evenScore, " + " (CASE " + " WHEN v.serviceScore IN (0-5) THEN v.serviceScore "
				+ " WHEN v.serviceScore IN (6,20) THEN 1 " + " WHEN v.serviceScore IN (21,40) THEN 2 "
				+ " WHEN v.serviceScore IN (41,60) THEN 3 " + " WHEN v.serviceScore IN (61,80) THEN 4 "
				+ " WHEN v.serviceScore IN (81,100) THEN 5 " + " ELSE 0 END ) as serviceScore, "
				+ " COUNT(z.memberAudit) AS evaluateNum  FROM tb_member m LEFT JOIN v_member_score_detail v   ON m.id = v.id  LEFT JOIN "
				+ " ( SELECT si.memberAudit FROM  tb_sign_in si, tb_member_evaluate me WHERE si.id = me.signIn) z  ON m.id = z.memberAudit "
				+ " WHERE m.id = ?  AND NOT ISNULL(m.longitude)  AND NOT ISNULL(m.latitude) AND m.grade = 1 GROUP BY m.id";
		pageInfo = findPageBySql(sql, pageInfo, new Object[] { id });
		return pageInfo;
	}

	@SuppressWarnings("unused")
	@Override
	public PageInfo queryProduct45Member(PageInfo pageInfo, Double longitude, Double latitude, Object[] args) {
		String sql = "";
		if (true) {
			sql = "SELECT m.id, m.name, m.image, m.longitude, m.latitude, ROUND(6378.138 * 2 * ASIN(SQRT(POW(SIN((? * PI() / 180 - m.latitude * PI() / 180) / 2),2) + COS(? * PI() / 180) * COS(m.latitude * PI() / 180) * POW(SIN((? * PI() / 180 - m.longitude * PI() / 180) / 2),2))) * 1000) AS distance , m.address, m.mobilephone, "
					+ " IFNULL(v.totalScore, 0) AS totalScore, IFNULL(v.deviceScore, 0) AS deviceScore, IFNULL(v.evenScore, 0) AS evenScore, IFNULL(v.serviceScore, 0) AS serviceScore, COUNT(z.memberAudit) AS evaluateNum  FROM tb_member m LEFT JOIN v_member_score_detail v ON m.id = v.id LEFT JOIN ( SELECT si.memberAudit FROM"
					+ " tb_sign_in si, tb_member_evaluate me WHERE si.id = me.signIn) z ON m.id = z.memberAudit WHERE m.city = ? AND M.role = 'E' AND m.longitude != '' AND m.latitude != '' AND m.grade = 1 GROUP BY m.id ORDER BY distance";
			pageInfo = findPageBySql(sql, pageInfo, new Object[] { latitude, latitude, longitude });
		} else {
			sql = "select m.id,m.name,m.image,m.longitude,m.latitude,ROUND(6378.138 * 2 * ASIN(SQRT(POW(SIN((? * PI() / 180 - m.latitude * PI() / 180) / 2),2) + COS(? * PI() / 180) * COS(m.latitude * PI() / 180) * POW(SIN((? * PI() / 180 - m.longitude * PI() / 180) / 2),2))) * 1000) AS distance ,m.address,m.mobilephone,IFNULL(v.totalScore,0) as totalScore,IFNULL(v.deviceScore,0) as deviceScore,IFNULL(v.evenScore,0) as evenScore,IFNULL(v.serviceScore,0) as serviceScore,COUNT(z.memberAudit) AS evaluateNum "
					+ "FROM tb_member m LEFT JOIN v_member_score_detail v ON m.id = v.id "
					+ "LEFT JOIN (SELECT si.memberAudit FROM tb_sign_in si,tb_member_evaluate me WHERE si.id = me.signIn) z ON m.id = z.memberAudit "
					+ "WHERE m.id in (select p.CLUB FROM tb_product_club_v45 p WHERE p.PRODUCT = ?) and m.grade = 1 AND m.longitude != '' AND m.latitude != ''  GROUP BY m.id ORDER BY distance";
			pageInfo = findPageBySql(sql, pageInfo, new Object[] { latitude, latitude, longitude });
		}

		return pageInfo;
	}

	@Override
	public PageInfo listPageBySql(String sql, PageInfo pageInfo, Object[] args) throws SQLException {

		StringBuffer sbCount = new StringBuffer("select count(m.*) from (" + sql + ") as m");
		StringBuffer sbQuery = new StringBuffer(sql + " limit " + pageInfo.getStart() + " , " + pageInfo.getPageSize());
		Connection connection = (Connection) jdbc.getDataSource().getConnection();
		PreparedStatement statement = (PreparedStatement) connection.prepareStatement(sbCount.toString());
		ResultSet rSet = statement.executeQuery();
		Integer count = 0;
		while (rSet.next()) {
			count = (Integer) rSet.getObject(0);
		}

		statement = (PreparedStatement) connection.prepareStatement(sbQuery.toString());
		rSet = statement.executeQuery();

		PageInfo pi = PageInfo.getInstance();
		pi.setCurrentPage(pageInfo.getCurrentPage());
		pi.setPageSize(pageInfo.getPageSize());
		pi.setOrder(pageInfo.getOrder());
		pi.setOrderFlag(pageInfo.getOrderFlag());
		pi.setTotalCount(count.intValue());
		pi.setItems(jdbc.queryForList(sbQuery.toString(), args));
		return pageInfo;
	};

	@Override
	public PageInfo queryPickDetail(PageInfo pageInfo, String id) {
		String sql = "SELECT d.id, a.bankName, a.account, a.`name`,d.flowType,date_format(d.pickDate, '%Y-%m-%d %h:%i') as evalTime,d.pickMoney,d.status  from tb_pick_detail d LEFT JOIN tb_pick_account a ON d.pickAccount =a.id where d.member = ?";
		pageInfo = this.findPageBySql(sql, pageInfo, id);
		return pageInfo;
	}

	@Override
	public PageInfo queryOrder(PageInfo pageInfo, long id, String status) {
		String sql = "SELECT * FROM ("
				+ "SELECT '活动订单' orderTypeName, a.id, ifnull(a.ticket_amount, 0) ticket_amount, a.member AS fromId, c.name AS fromName, '' as receive, '2' as type, (SELECT NAME FROM tb_parameter WHERE parent = 21 AND CODE = '2') AS orderType, b.id as productId, b.name, '' as proType, b.creator AS toId, d.name as toName, a.no, a.payNo, date_format(a.orderDate, '%Y-%m-%d') as orderDate,a.orderDate as  odate  ,date_format(a.orderStartTime, '%Y-%m-%d') as orderStartTime,date_format(a.orderEndTime, '%Y-%m-%d') as orderEndTime,( CASE a. STATUS WHEN 0 THEN b.amerce_money ELSE IFNULL(a.orderMoney, 0) END ) orderMoney, a.count,  "
				+ "(CASE a.STATUS WHEN 2 THEN 1 ELSE a.STATUS END) STATUS, a.integral,a.isappraise,b.active_image as image,b.amerce_money AS price,a.origin FROM tb_active_order a LEFT JOIN tb_active b ON a.active = b.id LEFT JOIN tb_member c ON a.member = c.id left join tb_member d on b.creator = d.id where b.mode = 'A' "
				+ " UNION ALL "
				+ "SELECT '活动订单' orderTypeName, a.id, ifnull(a.ticket_amount, 0) ticket_amount, a.member AS fromId, concat(d.name, '(', c.name , ')') AS fromName, '' as receive, '2' as type, (SELECT NAME FROM tb_parameter WHERE parent = 21 AND CODE = '2') AS orderType, b.id as productId, b.name, '' as proType, b.creator AS toId, e.name as toName, a.no, a.payNo, date_format(a.orderDate, '%Y-%m-%d') as orderDate,a.orderDate as  odate , date_format(a.orderStartTime, '%Y-%m-%d') as orderStartTime,date_format(a.orderEndTime, '%Y-%m-%d') as orderEndTime, ( CASE a. STATUS WHEN 0 THEN b.amerce_money ELSE IFNULL(a.orderMoney, 0) END ) orderMoney, a.count, "
				+ "(CASE a.STATUS WHEN 2 THEN 1 ELSE a.STATUS END) STATUS, a.integral, a.isappraise,b.active_image as image, b.amerce_money AS price,a.origin FROM tb_active_order a LEFT JOIN tb_active b ON a.active = b.id LEFT JOIN tb_active_team c ON a.team = c.id left join tb_member d ON a.member = d.id left join tb_member e on b.creator = e.id where b.mode = 'B'"
				+ " UNION ALL "
				+ "SELECT '课程订单' orderTypeName, a.id, ifnull(a.ticket_amount, 0) ticket_amount, a.member AS fromId, c.name as fromName, ifnull(e.name, '') as receive, '5' as type, (SELECT NAME FROM tb_parameter WHERE parent = 21 AND CODE = '5') as orderType, b.id as productId, h.name, '' as proType, b.member AS toId, d.name as toName, a.no, a.payNo, date_format(a.orderDate, '%Y-%m-%d') as orderDate,a.orderDate as  odate , date_format(a.orderStartTime, '%Y-%m-%d') as orderStartTime,date_format(a.orderEndTime, '%Y-%m-%d') as orderEndTime,( CASE a. STATUS WHEN 0 THEN b.hour_price ELSE IFNULL(a.orderMoney, 0) END ) orderMoney, a.count, "
				+ "(CASE a.STATUS WHEN 2 THEN 1 ELSE a.STATUS END) STATUS,a.integral,a.isappraise,h.image as image,b.hour_price as price,a.origin FROM TB_CourseRelease_ORDER a LEFT JOIN TB_COURSE b ON a.course = b.id LEFT JOIN TB_COURSE_INFO h ON h.id = b.courseId LEFT JOIN tb_member c ON a.member = c.id LEFT JOIN tb_member d ON b.member = d.id LEFT JOIN tb_always_addr e ON a.alwaysAddr = e.id"
				+ " UNION ALL "
				+ "SELECT '自动订单' orderTypeName, a.id, ifnull(a.ticket_amount, 0) ticket_amount, a.member AS fromId,c.name AS fromName,IFNULL(e.name, '') AS receive, '6' as type, (SELECT NAME FROM tb_parameter WHERE parent = 21 AND CODE = '6') as orderType,b.id AS productId,b.name,'' AS proType,b.member AS toId,d.name AS toName,a.no,a.payNo,date_format(a.orderDate, '%Y-%m-%d') as orderDate,a.orderDate as  odate ,date_format(a.orderStartTime, '%Y-%m-%d') as orderStartTime,date_format(a.orderStartTime, '%Y-%m-%d') as orderStartTime,( CASE a. STATUS WHEN 0 THEN b.price ELSE IFNULL(a.orderMoney, 0) END ) orderMoney,a.count, "
				+ "(CASE a.STATUS WHEN 2 THEN 1 ELSE a.STATUS END) STATUS ,a.integral,a.isappraise,b.image1 as image,b.price,a.origin FROM tb_goods_order a LEFT JOIN tb_goods b ON a.goods = b.id LEFT JOIN tb_member c ON a.member = c.id LEFT JOIN tb_member d ON b.member = d.id LEFT JOIN tb_always_addr e ON a.alwaysAddr = e.id "
				+ " UNION ALL "
				+ "SELECT 'E卡通订单' orderTypeName, a.id, IFNULL(a.ticket_amount, 0) ticket_amount, a.member AS fromId, c.name as fromName, '' as receive, '8' as type, (SELECT NAME FROM tb_parameter WHERE parent = 21 AND CODE = '8') as orderType, b.id as productId, b.prod_name, '' as proType, '' AS toId, '' as toName, a.no, a.payNo, date_format(a.orderDate, '%Y-%m-%d') as orderDate,a.orderDate as  odate ,date_format(a.orderStartTime, '%Y-%m-%d') as orderStartTime,date_format(a.orderEndTime, '%Y-%m-%d') as orderEndTime, a.unitPrice as orderMoney, a.count,"
				+ "(CASE a.STATUS WHEN 2 THEN 1 ELSE a.STATUS END) STATUS , a.integral,a.isappraise,b.prod_image as image,b.PROD_PRICE AS price,a.origin FROM tb_product_order_v45 a LEFT JOIN tb_product_v45 b ON a.order_product = b.id LEFT JOIN tb_member c ON a.member = c.id "
				+ ") z WHERE z.fromid = ? and z.status = ? and z.origin = 'E' order by z.odate desc";
		pageInfo = this.findPageBySql(sql, pageInfo, id, status);
		return pageInfo;
	}

	@Override
	public PageInfo queryOrderInStatus(PageInfo pageInfo, long id, String status) {
		String sql = "SELECT * FROM ( "
				+ " SELECT '活动订单' orderTypeName, a.id, ifnull(a.ticket_amount, 0) ticket_amount, a.member AS fromId, c.name AS fromName, '' as receive, '2' as type, (SELECT NAME FROM tb_parameter WHERE parent = 21 AND CODE = '2') AS orderType, b.id as productId, b.name, '' as proType, b.creator AS toId, d.name as toName, a.no, a.payNo, date_format(a.orderDate, '%Y-%m-%d') as orderDate,a.orderDate as  odate  ,date_format(a.orderStartTime, '%Y-%m-%d') as orderStartTime,date_format(a.orderEndTime, '%Y-%m-%d') as orderEndTime,( CASE a.STATUS WHEN 0 THEN b.amerce_money ELSE IFNULL(a.orderMoney, 0) END ) orderMoney, a.count, "
				+ "(CASE a.STATUS WHEN 2 THEN 1 ELSE a.STATUS END) STATUS, a.integral,a.isappraise,b.active_image as image,b.amerce_money AS price,a.origin FROM tb_active_order a LEFT JOIN tb_active b ON a.active = b.id LEFT JOIN tb_member c ON a.member = c.id left join tb_member d on b.creator = d.id  where b.mode = 'A' "
				+ " UNION ALL "
				+ " SELECT '活动订单' orderTypeName, a.id, ifnull(a.ticket_amount, 0) ticket_amount, a.member AS fromId, concat(d.name, '(', c.name , ')') AS fromName, '' as receive, '2' as type, (SELECT NAME FROM tb_parameter WHERE parent = 21 AND CODE = '2') AS orderType, b.id as productId, b.name, '' as proType, b.creator AS toId, e.name as toName, a.no, a.payNo, date_format(a.orderDate, '%Y-%m-%d') as orderDate,a.orderDate as  odate , date_format(a.orderStartTime, '%Y-%m-%d') as orderStartTime,date_format(a.orderEndTime, '%Y-%m-%d') as orderEndTime, ( CASE a.STATUS WHEN 0 THEN b.amerce_money ELSE IFNULL(a.orderMoney, 0) END ) orderMoney, a.count,  "
				+ "(CASE a.STATUS WHEN 2 THEN 1 ELSE a.STATUS END) STATUS, a.integral, a.isappraise,b.active_image as image, b.amerce_money AS price,a.origin FROM tb_active_order a LEFT JOIN tb_active b ON a.active = b.id LEFT JOIN tb_active_team c ON a.team = c.id left join tb_member d ON a.member = d.id left join tb_member e on b.creator = e.id  where b.mode = 'B' "
				+ " UNION ALL "
				+ " SELECT '课程订单' orderTypeName, a.id, ifnull(a.ticket_amount, 0) ticket_amount, a.member AS fromId, c.name as fromName, ifnull(e.name, '') as receive, '5' as type, (SELECT NAME FROM tb_parameter WHERE parent = 21 AND CODE = '5') as orderType, b.id as productId, h.name, '' as proType, b.member AS toId, d.name as toName, a.no, a.payNo, date_format(a.orderDate, '%Y-%m-%d') as orderDate,a.orderDate as  odate , date_format(a.orderStartTime, '%Y-%m-%d') as orderStartTime,date_format(a.orderEndTime, '%Y-%m-%d') as orderEndTime,( CASE a.STATUS WHEN 0 THEN b.hour_price ELSE IFNULL(a.orderMoney, 0) END ) orderMoney, a.count,  "
				+ "(CASE a.STATUS WHEN 2 THEN 1 ELSE a.STATUS END) STATUS,a.integral,a.isappraise,h.image as image,b.hour_price as price,a.origin FROM TB_CourseRelease_ORDER a LEFT JOIN TB_COURSE b ON a.course = b.id LEFT JOIN TB_COURSE_INFO h ON h.id = b.courseId LEFT JOIN tb_member c ON a.member = c.id LEFT JOIN tb_member d ON b.member = d.id LEFT JOIN tb_always_addr e ON a.alwaysAddr = e.id "
				+ " UNION ALL "
				+ " SELECT '自动订单' orderTypeName, a.id, ifnull(a.ticket_amount, 0) ticket_amount, a.member AS fromId,c.name AS fromName,IFNULL(e.name, '') AS receive, '6' as type, (SELECT NAME FROM tb_parameter WHERE parent = 21 AND CODE = '6') as orderType,b.id AS productId,b.name,'' AS proType,b.member AS toId,d.name AS toName,a.no,a.payNo,date_format(a.orderDate, '%Y-%m-%d') as orderDate,a.orderDate as  odate ,date_format(a.orderStartTime, '%Y-%m-%d') as orderStartTime,date_format(a.orderStartTime, '%Y-%m-%d') as orderStartTime,( CASE a.STATUS WHEN 0 THEN a.orderMoney ELSE IFNULL(a.orderMoney, 0) END ) orderMoney,a.count, "
				+ "(CASE a.STATUS WHEN 2 THEN 1 ELSE a.STATUS END) STATUS,a.integral,a.isappraise,b.image1 as image,b.price,a.origin FROM tb_goods_order a LEFT JOIN tb_goods b ON a.goods = b.id LEFT JOIN tb_member c ON a.member = c.id LEFT JOIN tb_member d ON b.member = d.id LEFT JOIN tb_always_addr e ON a.alwaysAddr = e.id "
				+ " UNION ALL "
				+ " SELECT 'E卡通订单' orderTypeName, a.id, IFNULL(a.ticket_amount, 0) ticket_amount, a.member AS fromId, c.name as fromName, '' as receive, '8' as type, (SELECT NAME FROM tb_parameter WHERE parent = 21 AND CODE = '8') as orderType, b.id as productId, b.prod_name, '' as proType, '' AS toId, '' as toName, a.no, a.payNo, date_format(a.orderDate, '%Y-%m-%d') as orderDate,a.orderDate as  odate ,date_format(a.orderStartTime, '%Y-%m-%d') as orderStartTime,date_format(a.orderEndTime, '%Y-%m-%d') as orderEndTime, a.orderMoney as orderMoney, a.count,  "
				+ "(CASE a.STATUS WHEN 2 THEN 1 ELSE a.STATUS END) STATUS, a.integral,a.isappraise,b.prod_image as image,b.PROD_PRICE AS price,a.origin FROM tb_product_order_v45 a LEFT JOIN tb_product_v45 b ON a.order_product = b.id LEFT JOIN tb_member c ON a.member = c.id "
				+ "union ALL " 
				+ "  SELECT '商品订单' orderTypeName, a.id, IFNULL(a.ticket_amount, 0) ticket_amount, a.member AS fromId, c.name as fromName, '' as receive, '5' as type, (SELECT NAME FROM tb_parameter WHERE parent = 21 AND CODE = '9') as orderType, b.id as productId, b.name as prod_name, '' as proType, '' AS toId, '' as toName,   a.no, a.payNo, date_format(a.orderDate, '%Y-%m-%d') as orderDate,a.orderDate as  odate ,date_format(a.orderStartTime, '%Y-%m-%d') as orderStartTime,  date_format(a.orderEndTime, '%Y-%m-%d') as orderEndTime,a.orderMoney as orderMoney, a.count,( CASE a.STATUS WHEN 2 THEN 1 ELSE a.STATUS END ) STATUS, a.integral,a.isappraise,b.image1 as image,b.cost AS price, a.origin FROM tb_product_order a LEFT JOIN tb_product b ON a.product = b.id    LEFT JOIN tb_member c ON a.member = c.id "
				+ ") z WHERE z.fromid = ? and z.status in(" + status + ") order by z.odate desc  ";
		pageInfo = this.findPageBySql(sql, pageInfo, id);
		return pageInfo;
	}

	@Override
	public PageInfo queryOrderByType(PageInfo pageInfo, long id, String status, String type) {
		String sql = "SELECT * FROM ("
				+ "SELECT '活动订单' orderTypeName, a.id, ifnull(a.ticket_amount, 0) ticket_amount, a.member AS fromId, c.name AS fromName, '' as receive, '2' as type, (SELECT NAME FROM tb_parameter WHERE parent = 21 AND CODE = '2') AS orderType, b.id as productId, b.name, '' as proType, b.creator AS toId, d.name as toName, a.no, a.payNo, date_format(a.orderDate, '%Y-%m-%d') as orderDate,a.orderDate as  odate  ,date_format(a.orderStartTime, '%Y-%m-%d') as orderStartTime,date_format(a.orderEndTime, '%Y-%m-%d') as orderEndTime,( CASE a. STATUS WHEN 0 THEN b.amerce_money ELSE IFNULL(a.orderMoney, 0) END ) orderMoney, a.count, (CASE a. STATUS WHEN 3 THEN 2 ELSE a. STATUS END) STATUS, a.integral,a.isappraise,b.active_image as image,b.amerce_money AS price,a.origin FROM tb_active_order a LEFT JOIN tb_active b ON a.active = b.id LEFT JOIN tb_member c ON a.member = c.id left join tb_member d on b.creator = d.id where b.mode = 'A' "
				+ " UNION ALL "
				+ "SELECT '活动订单' orderTypeName, a.id, ifnull(a.ticket_amount, 0) ticket_amount, a.member AS fromId, concat(d.name, '(', c.name , ')') AS fromName, '' as receive, '2' as type, (SELECT NAME FROM tb_parameter WHERE parent = 21 AND CODE = '2') AS orderType, b.id as productId, b.name, '' as proType, b.creator AS toId, e.name as toName, a.no, a.payNo, date_format(a.orderDate, '%Y-%m-%d') as orderDate,a.orderDate as  odate , date_format(a.orderStartTime, '%Y-%m-%d') as orderStartTime,date_format(a.orderEndTime, '%Y-%m-%d') as orderEndTime, ( CASE a. STATUS WHEN 0 THEN b.amerce_money ELSE IFNULL(a.orderMoney, 0) END ) orderMoney, a.count,(CASE a. STATUS WHEN 3 THEN 2 ELSE a. STATUS END) STATUS, a.integral, a.isappraise,b.active_image as image, b.amerce_money AS price,a.origin FROM tb_active_order a LEFT JOIN tb_active b ON a.active = b.id LEFT JOIN tb_active_team c ON a.team = c.id left join tb_member d ON a.member = d.id left join tb_member e on b.creator = e.id where b.mode = 'B'"
				+ " UNION ALL "
				+ "SELECT '课程订单' orderTypeName, a.id, ifnull(a.ticket_amount, 0) ticket_amount, a.member AS fromId, c.name as fromName, ifnull(e.name, '') as receive, '5' as type, (SELECT NAME FROM tb_parameter WHERE parent = 21 AND CODE = '5') as orderType, b.id as productId, h.name, '' as proType, b.member AS toId, d.name as toName, a.no, a.payNo, date_format(a.orderDate, '%Y-%m-%d') as orderDate,a.orderDate as  odate , date_format(a.orderStartTime, '%Y-%m-%d') as orderStartTime,date_format(a.orderEndTime, '%Y-%m-%d') as orderEndTime,( CASE a. STATUS WHEN 0 THEN b.hour_price ELSE IFNULL(a.orderMoney, 0) END ) orderMoney, a.count,(CASE a. STATUS WHEN 3 THEN 1 ELSE a. STATUS END) STATUS,a.integral,a.isappraise,h.image as image,b.hour_price as price,a.origin FROM TB_CourseRelease_ORDER a LEFT JOIN TB_COURSE b ON a.course = b.id LEFT JOIN TB_COURSE_INFO h ON h.id = b.courseId LEFT JOIN tb_member c ON a.member = c.id LEFT JOIN tb_member d ON b.member = d.id LEFT JOIN tb_always_addr e ON a.alwaysAddr = e.id"
				+ " UNION ALL "
				+ "SELECT '自动订单' orderTypeName, a.id, ifnull(a.ticket_amount, 0) ticket_amount, a.member AS fromId,c.name AS fromName,IFNULL(e.name, '') AS receive, '6' as type, (SELECT NAME FROM tb_parameter WHERE parent = 21 AND CODE = '6') as orderType,b.id AS productId,b.name,'' AS proType,b.member AS toId,d.name AS toName,a.no,a.payNo,date_format(a.orderDate, '%Y-%m-%d') as orderDate,a.orderDate as  odate ,date_format(a.orderStartTime, '%Y-%m-%d') as orderStartTime,date_format(a.orderStartTime, '%Y-%m-%d') as orderStartTime,( CASE a. STATUS WHEN 0 THEN b.price ELSE IFNULL(a.orderMoney, 0) END ) orderMoney,a.count,(CASE a. STATUS WHEN 3 THEN 1 ELSE a. STATUS END) STATUS ,a.integral,a.isappraise,b.image1 as image,b.price,a.origin FROM tb_goods_order a LEFT JOIN tb_goods b ON a.goods = b.id LEFT JOIN tb_member c ON a.member = c.id LEFT JOIN tb_member d ON b.member = d.id LEFT JOIN tb_always_addr e ON a.alwaysAddr = e.id "
				+ " UNION ALL "
				+ "SELECT 'e卡通订单' orderTypeName, a.id, IFNULL(a.ticket_amount, 0) ticket_amount, a.member AS fromId, c.name as fromName, '' as receive, '8' as type, (SELECT NAME FROM tb_parameter WHERE parent = 21 AND CODE = '8') as orderType, b.id as productId, b.prod_name, '' as proType, '' AS toId, '' as toName, a.no, a.payNo, date_format(a.orderDate, '%Y-%m-%d') as orderDate,a.orderDate as  odate ,date_format(a.orderStartTime, '%Y-%m-%d') as orderStartTime,date_format(a.orderEndTime, '%Y-%m-%d') as orderEndTime,( CASE a. STATUS WHEN 0 THEN b.PROD_PRICE ELSE IFNULL(a.orderMoney, 0) END ) orderMoney, a.count, a.status, a.integral,a.isappraise,b.prod_image as image,b.PROD_PRICE AS price,a.origin FROM tb_product_order_v45 a LEFT JOIN tb_product_v45 b ON a.order_product = b.id LEFT JOIN tb_member c ON a.member = c.id "
				+ ") z WHERE z.fromid = ? and z.status = ? and z.origin = ? order by z.odate desc";
		pageInfo = this.findPageBySql(sql, pageInfo, id, status, type);
		return pageInfo;
	}

	@Override
	public PageInfo queryOrder(PageInfo pageInfo, long id, String status, String dou) {
		String sql = "SELECT * FROM ( "
				+ " SELECT '活动订单' orderTypeName, a.id, ifnull(a.ticket_amount, 0) ticket_amount, a.member AS fromId, c.name AS fromName, '' as receive, '2' as type, (SELECT NAME FROM tb_parameter WHERE parent = 21 AND CODE = '2') AS orderType, b.id as productId, b.name, '' as proType, b.creator AS toId, d.name as toName, a.no, a.payNo, date_format(a.orderDate, '%Y-%m-%d') as orderDate,a.orderDate as  odate  ,date_format(a.orderStartTime, '%Y-%m-%d') as orderStartTime,date_format(a.orderEndTime, '%Y-%m-%d') as orderEndTime,( CASE a. STATUS WHEN 0 THEN b.amerce_money ELSE IFNULL(a.orderMoney, 0) END ) orderMoney, a.count, (CASE a. STATUS WHEN 3 THEN 2 ELSE a. STATUS END) STATUS, a.integral,a.isappraise,b.active_image as image,b.amerce_money AS price FROM tb_active_order a LEFT JOIN tb_active b ON a.active = b.id LEFT JOIN tb_member c ON a.member = c.id left join tb_member d on b.creator = d.id where b.mode = 'A'  "
				+ " 	UNION ALL  "
				+ " SELECT '活动订单' orderTypeName, a.id, ifnull(a.ticket_amount, 0) ticket_amount, a.member AS fromId, concat(d.name, '(', c.name , ')') AS fromName, '' as receive, '2' as type, (SELECT NAME FROM tb_parameter WHERE parent = 21 AND CODE = '2') AS orderType, b.id as productId, b.name, '' as proType, b.creator AS toId, e.name as toName, a.no, a.payNo, date_format(a.orderDate, '%Y-%m-%d') as orderDate,a.orderDate as  odate , date_format(a.orderStartTime, '%Y-%m-%d') as orderStartTime,date_format(a.orderEndTime, '%Y-%m-%d') as orderEndTime, ( CASE a. STATUS WHEN 0 THEN b.amerce_money ELSE IFNULL(a.orderMoney, 0) END ) orderMoney, a.count,(CASE a. STATUS WHEN 3 THEN 2 ELSE a. STATUS END) STATUS, a.integral, a.isappraise,b.active_image as image, b.amerce_money AS price FROM tb_active_order a LEFT JOIN tb_active b ON a.active = b.id LEFT JOIN tb_active_team c ON a.team = c.id left join tb_member d ON a.member = d.id left join tb_member e on b.creator = e.id where b.mode = 'B' "
				+ " 	UNION ALL "
				+ " SELECT '课程订单' orderTypeName, a.id, ifnull(a.ticket_amount, 0) ticket_amount, a.member AS fromId, c.name as fromName, ifnull(e.name, '') as receive, '5' as type, (SELECT NAME FROM tb_parameter WHERE parent = 21 AND CODE = '5') as orderType, b.id as productId, h.name, '' as proType, b.member AS toId, d.name as toName, a.no, a.payNo, date_format(a.orderDate, '%Y-%m-%d') as orderDate,a.orderDate as  odate , date_format(a.orderStartTime, '%Y-%m-%d') as orderStartTime,date_format(a.orderEndTime, '%Y-%m-%d') as orderEndTime,( CASE a. STATUS WHEN 0 THEN b.hour_price ELSE IFNULL(a.orderMoney, 0) END ) orderMoney, a.count,(CASE a. STATUS WHEN 3 THEN 1 ELSE a. STATUS END) STATUS,a.integral,a.isappraise,h.image as image,b.hour_price as price FROM TB_CourseRelease_ORDER a LEFT JOIN TB_COURSE b ON a.course = b.id LEFT JOIN TB_COURSE_INFO h ON h.id = b.courseId LEFT JOIN tb_member c ON a.member = c.id LEFT JOIN tb_member d ON b.member = d.id LEFT JOIN tb_always_addr e ON a.alwaysAddr = e.id "
				+ " 	UNION ALL 	"
				+ " SELECT '自动订单' orderTypeName, a.id, ifnull(a.ticket_amount, 0) ticket_amount, a.member AS fromId,c.name AS fromName,IFNULL(e.name, '') AS receive, '6' as type,  (SELECT NAME FROM tb_parameter WHERE parent = 21 AND CODE = '6') as orderType,b.id AS productId,b.name,'' AS proType,b.member AS toId,d.name AS toName,a.no,a.payNo,date_format(a.orderDate, '%Y-%m-%d') as orderDate,a.orderDate as  odate ,date_format(a.orderStartTime, '%Y-%m-%d') as orderStartTime,date_format(a.orderStartTime, '%Y-%m-%d') as orderStartTime,( CASE a. STATUS WHEN 0 THEN b.price ELSE IFNULL(a.orderMoney, 0) END ) orderMoney,a.count,(CASE a. STATUS WHEN 3 THEN 1 ELSE a. STATUS END) STATUS ,a.integral,a.isappraise,b.image1 as image,b.price FROM tb_goods_order a LEFT JOIN tb_goods b ON a.goods = b.id LEFT JOIN tb_member c ON a.member = c.id LEFT JOIN tb_member d ON b.member = d.id LEFT JOIN tb_always_addr e ON a.alwaysAddr = e.id  "
				+ " 	UNION ALL "
				+ " SELECT '一卡通订单' orderTypeName, a.id, IFNULL(a.ticket_amount, 0) ticket_amount, a.member AS fromId, c.name as fromName, '' as receive, '8' as type, (SELECT NAME FROM tb_parameter WHERE parent = 21 AND CODE = '8') as orderType, b.id as productId, b.prod_name, '' as proType, '' AS toId, '' as toName,   a.no, a.payNo, date_format(a.orderDate, '%Y-%m-%d') as orderDate,a.orderDate as  odate ,date_format(a.orderStartTime, '%Y-%m-%d') as orderStartTime,  date_format(a.orderEndTime, '%Y-%m-%d') as orderEndTime,( CASE a.STATUS WHEN 0 THEN b.PROD_PRICE ELSE IFNULL(a.orderMoney, 0) END ) orderMoney, a.count, a.status, a.integral,a.isappraise,b.prod_image as image,b.PROD_PRICE AS price FROM tb_product_order_v45 a LEFT JOIN tb_product_v45 b ON a.order_product = b.id   LEFT JOIN tb_member c ON a.member = c.id  "
				+ " 	UNION ALL "
				+ " SELECT '健身计划订单' orderTypeName, a.id, IFNULL(a.ticket_amount, 0) ticket_amount, a.member AS fromId, c.name as fromName, '' as receive, '3' as type, (SELECT NAME FROM tb_parameter WHERE parent = 21 AND CODE = '3') as orderType, b.id as productId, b.plan_name, '' as proType, '' AS toId, '' as toName,    a.no, a.payNo, date_format(a.orderDate, '%Y-%m-%d') as orderDate,a.orderDate as  odate ,date_format(a.orderStartTime, '%Y-%m-%d') as orderStartTime,  date_format(a.orderEndTime, '%Y-%m-%d') as orderEndTime,( CASE a.STATUS WHEN 0 THEN b.unit_price ELSE IFNULL(a.orderMoney, 0) END ) orderMoney, a.count, a.status, a.integral,a.isappraise,b.image1 as image,b.unit_price AS price FROM tb_planrelease_order a LEFT JOIN tb_plan_release b ON a.planRelease = b.id   LEFT JOIN tb_member c ON a.member = c.id  "
				+ " 	UNION ALL "
				+ " SELECT '商品订单' orderTypeName, a.id, IFNULL(a.ticket_amount, 0) ticket_amount, a.member AS fromId, c.name as fromName, '' as receive, '5' as type,    (SELECT NAME FROM tb_parameter WHERE parent = 21 AND CODE = '9') as orderType, b.id as productId, b.name, '' as proType, '' AS toId, '' as toName,   a.no, a.payNo, date_format(a.orderDate, '%Y-%m-%d') as orderDate,a.orderDate as  odate ,date_format(a.orderStartTime, '%Y-%m-%d') as orderStartTime,    date_format(a.orderEndTime, '%Y-%m-%d') as orderEndTime,( CASE a.STATUS WHEN 0 THEN b.cost ELSE IFNULL(a.orderMoney, 0) END ) orderMoney,   a.count, a.status, a.integral,a.isappraise,b.image1 as image,b.cost AS price FROM tb_product_order a LEFT JOIN tb_product b ON a.product = b.id    LEFT JOIN tb_member c ON a.member = c.id "
				+ "	) z WHERE z.fromid = ? and z.status = ? order by z.orderStartTime desc ";
		pageInfo = this.findPageBySql(sql, pageInfo, id, status);
		return pageInfo;
	}

	@Override
	public PageInfo queryOrder(PageInfo pageInfo, long id, String status, String dou, String hua) {
		String sql = "SELECT * FROM ( "
				+ " SELECT '活动订单' orderTypeName, a.id, ifnull(a.ticket_amount, 0) ticket_amount, a.member AS fromId, c.name AS fromName, '' as receive, '2' as type, (SELECT NAME FROM tb_parameter WHERE parent = 21 AND CODE = '2') AS orderType, b.id as productId, b.name, '' as proType, b.creator AS toId, d.name as toName, a.no, a.payNo, date_format(a.orderDate, '%Y-%m-%d') as orderDate,a.orderDate as  odate  ,date_format(a.orderStartTime, '%Y-%m-%d') as orderStartTime,date_format(a.orderEndTime, '%Y-%m-%d') as orderEndTime,( CASE a. STATUS WHEN 0 THEN b.amerce_money ELSE IFNULL(a.orderMoney, 0) END ) orderMoney, a.count, (CASE a. STATUS WHEN 3 THEN 2 ELSE a. STATUS END) STATUS, a.integral,a.isappraise,b.active_image as image,b.amerce_money AS price,a.origin FROM tb_active_order a LEFT JOIN tb_active b ON a.active = b.id LEFT JOIN tb_member c ON a.member = c.id left join tb_member d on b.creator = d.id where b.mode = 'A'  "
				+ " 	UNION ALL  "
				+ " SELECT '活动订单' orderTypeName, a.id, ifnull(a.ticket_amount, 0) ticket_amount, a.member AS fromId, concat(d.name, '(', c.name , ')') AS fromName, '' as receive, '2' as type, (SELECT NAME FROM tb_parameter WHERE parent = 21 AND CODE = '2') AS orderType, b.id as productId, b.name, '' as proType, b.creator AS toId, e.name as toName, a.no, a.payNo, date_format(a.orderDate, '%Y-%m-%d') as orderDate,a.orderDate as  odate , date_format(a.orderStartTime, '%Y-%m-%d') as orderStartTime,date_format(a.orderEndTime, '%Y-%m-%d') as orderEndTime, ( CASE a. STATUS WHEN 0 THEN b.amerce_money ELSE IFNULL(a.orderMoney, 0) END ) orderMoney, a.count,(CASE a. STATUS WHEN 3 THEN 2 ELSE a. STATUS END) STATUS, a.integral, a.isappraise,b.active_image as image, b.amerce_money AS price,a.origin FROM tb_active_order a LEFT JOIN tb_active b ON a.active = b.id LEFT JOIN tb_active_team c ON a.team = c.id left join tb_member d ON a.member = d.id left join tb_member e on b.creator = e.id where b.mode = 'B' "
				+ " 	UNION ALL "
				+ " SELECT '课程订单' orderTypeName, a.id, ifnull(a.ticket_amount, 0) ticket_amount, a.member AS fromId, c.name as fromName, ifnull(e.name, '') as receive, '5' as type, (SELECT NAME FROM tb_parameter WHERE parent = 21 AND CODE = '5') as orderType, b.id as productId, h.name, '' as proType, b.member AS toId, d.name as toName, a.no, a.payNo, date_format(a.orderDate, '%Y-%m-%d') as orderDate,a.orderDate as  odate , date_format(a.orderStartTime, '%Y-%m-%d') as orderStartTime,date_format(a.orderEndTime, '%Y-%m-%d') as orderEndTime,( CASE a. STATUS WHEN 0 THEN b.hour_price ELSE IFNULL(a.orderMoney, 0) END ) orderMoney, a.count,(CASE a. STATUS WHEN 3 THEN 1 ELSE a. STATUS END) STATUS,a.integral,a.isappraise,h.image as image,b.hour_price as price,a.origin FROM TB_CourseRelease_ORDER a LEFT JOIN TB_COURSE b ON a.course = b.id LEFT JOIN TB_COURSE_INFO h ON h.id = b.courseId LEFT JOIN tb_member c ON a.member = c.id LEFT JOIN tb_member d ON b.member = d.id LEFT JOIN tb_always_addr e ON a.alwaysAddr = e.id "
				+ " 	UNION ALL 	"
				+ " SELECT '自动订单' orderTypeName, a.id, ifnull(a.ticket_amount, 0) ticket_amount, a.member AS fromId,c.name AS fromName,IFNULL(e.name, '') AS receive, '6' as type,  (SELECT NAME FROM tb_parameter WHERE parent = 21 AND CODE = '6') as orderType,b.id AS productId,b.name,'' AS proType,b.member AS toId,d.name AS toName,a.no,a.payNo,date_format(a.orderDate, '%Y-%m-%d') as orderDate,a.orderDate as  odate ,date_format(a.orderStartTime, '%Y-%m-%d') as orderStartTime,date_format(a.orderStartTime, '%Y-%m-%d') as orderStartTime,( CASE a. STATUS WHEN 0 THEN b.price ELSE IFNULL(a.orderMoney, 0) END ) orderMoney,a.count,(CASE a. STATUS WHEN 3 THEN 1 ELSE a. STATUS END) STATUS ,a.integral,a.isappraise,b.image1 as image,b.price,a.origin FROM tb_goods_order a LEFT JOIN tb_goods b ON a.goods = b.id LEFT JOIN tb_member c ON a.member = c.id LEFT JOIN tb_member d ON b.member = d.id LEFT JOIN tb_always_addr e ON a.alwaysAddr = e.id  "
				+ " 	UNION ALL "
				// + " SELECT '一卡通订单' orderTypeName, a.id,
				// IFNULL(a.ticket_amount, 0) ticket_amount, a.member AS fromId,
				// c.name as fromName, '' as receive, '8' as type, (SELECT NAME
				// FROM tb_parameter WHERE parent = 21 AND CODE = '8') as
				// orderType, b.id as productId, b.prod_name, '' as proType, ''
				// AS toId, '' as toName, a.no, a.payNo,
				// date_format(a.orderDate, '%Y-%m-%d') as orderDate,a.orderDate
				// as odate ,date_format(a.orderStartTime, '%Y-%m-%d') as
				// orderStartTime, date_format(a.orderEndTime, '%Y-%m-%d') as
				// orderEndTime,( CASE a.STATUS WHEN 0 THEN b.PROD_PRICE ELSE
				// IFNULL(a.orderMoney, 0) END ) orderMoney, a.count, a.status,
				// a.integral,a.isappraise,b.prod_image as image,b.PROD_PRICE AS
				// price FROM tb_product_order_v45 a LEFT JOIN tb_product_v45 b
				// ON a.order_product = b.id LEFT JOIN tb_member c ON a.member =
				// c.id "
				// + " UNION ALL "
				+ " SELECT '健身计划订单' orderTypeName, a.id, IFNULL(a.ticket_amount, 0) ticket_amount, a.member AS fromId, c.name as fromName, '' as receive, '3' as type, (SELECT NAME FROM tb_parameter WHERE parent = 21 AND CODE = '3') as orderType, b.id as productId, b.plan_name, '' as proType, '' AS toId, '' as toName,    a.no, a.payNo, date_format(a.orderDate, '%Y-%m-%d') as orderDate,a.orderDate as  odate ,date_format(a.orderStartTime, '%Y-%m-%d') as orderStartTime,  date_format(a.orderEndTime, '%Y-%m-%d') as orderEndTime,( CASE a.STATUS WHEN 0 THEN b.unit_price ELSE IFNULL(a.orderMoney, 0) END ) orderMoney, a.count, a.status, a.integral,a.isappraise,b.image1 as image,b.unit_price AS price,a.origin FROM tb_planrelease_order a LEFT JOIN tb_plan_release b ON a.planRelease = b.id   LEFT JOIN tb_member c ON a.member = c.id  "
				+ " 	UNION ALL "
				+ " SELECT '商品订单' orderTypeName, a.id, IFNULL(a.ticket_amount, 0) ticket_amount, a.member AS fromId, c.name as fromName, '' as receive, '1' as type,    (SELECT NAME FROM tb_parameter WHERE parent = 21 AND CODE = '9') as orderType, b.id as productId, b.name, '' as proType, '' AS toId, '' as toName,   a.no, a.payNo, date_format(a.orderDate, '%Y-%m-%d') as orderDate,a.orderDate as  odate ,date_format(a.orderStartTime, '%Y-%m-%d') as orderStartTime,    date_format(a.orderEndTime, '%Y-%m-%d') as orderEndTime,( CASE a.STATUS WHEN 0 THEN b.cost ELSE IFNULL(a.orderMoney, 0) END ) orderMoney,   a.count, a.status, a.integral,a.isappraise,b.image1 as image,b.cost AS price,a.origin FROM tb_product_order a LEFT JOIN tb_product b ON a.product = b.id    LEFT JOIN tb_member c ON a.member = c.id where b.type != 5"
				+ " 	UNION ALL "
				+ "SELECT '直播订单' orderTypeName, a.id, IFNULL(a.ticket_amount, 0) ticket_amount, a.member AS fromId, c.name as fromName, '' as receive, '10' as type,    (SELECT NAME FROM tb_parameter WHERE parent = 21 AND CODE = '9') as orderType, b.id as productId, b.name, '' as proType, '' AS toId, '' as toName,   a.no, a.payNo, date_format(a.orderDate, '%Y-%m-%d') as orderDate,a.orderDate as  odate ,date_format(a.orderStartTime, '%Y-%m-%d') as orderStartTime,    date_format(a.orderEndTime, '%Y-%m-%d') as orderEndTime,( CASE a.STATUS WHEN 0 THEN b.cost ELSE IFNULL(a.orderMoney, 0) END ) orderMoney,   a.count, a.status, a.integral,a.isappraise,b.image1 as image,b.cost AS price,a.origin FROM tb_product_order a LEFT JOIN tb_product b ON a.product = b.id    LEFT JOIN tb_member c ON a.member = c.id where b.type = 5"
				+ "	) z WHERE z.fromid = ? and z.status = ? and z.origin = 'C' order by z.orderStartTime desc ";
		pageInfo = this.findPageBySql(sql, pageInfo, id, status);
		return pageInfo;
	}

	/*
	 * @Override public PageInfo queryMember(PageInfo pageInfo, String id,
	 * String str) { String sql =
	 * "SELECT a.id,a.name,IFNULL(b.signNum,0) AS signNum FROM tb_member a LEFT JOIN (SELECT memberSign,COUNT(memberSign) AS signNum FROM tb_sign_in GROUP BY memberSign) b ON a.id = b.memberSign WHERE a.id <> ? AND a.name LIKE '%?%'"
	 * ; pageInfo = this.findPageBySql(sql, pageInfo, id, str); return pageInfo;
	 * }
	 */

	// @Override
	/*
	 * public Double returnCardPrice(String id) { Double backMoney = 0.00; final
	 * List<?> list = this.findObjectBySql(
	 * "from ProductOrder45 po where po.id = ?", id); ProductOrder45 po =
	 * (ProductOrder45) list.get(0); // 每次应付金额 Integer time = 0; if
	 * (po.getProduct() != null) { Integer period = po.getProduct().getPeriod();
	 * String periodUnit = po.getProduct().getPeriodUnit(); time =
	 * periodUnit.equals("A") ? period : periodUnit.equals("B") ? period * 3 :
	 * period * 12; } Double onceMoney = po.getOrderMoney() / time; Map<String,
	 * Object> map = this.queryForMap(
	 * "SELECT SUN(pob.balance_money) AS balanceMoney FROM tb_product_order_balance_v45 pob WHERE pob.balance_order = ?"
	 * , id); backMoney = po.getOrderMoney() - ((Double) map.get("BALANCEMONEY")
	 * + onceMoney); return backMoney; }
	 */

	@SuppressWarnings("unchecked")
	@Override
	public PageInfo queryCourse(PageInfo pageInfo, String date, Double longitude, Double latitude) {
		String sql = "SELECT a.id, a.hour_price as member_price,a.startTime,a.endTime,b.name AS courseName,b.image,c.name AS clubName,c.latitude,c.longitude  "
				+ "FROM tb_course a LEFT JOIN tb_course_info b ON a.courseId = b.id LEFT JOIN tb_member c ON a.member = c.id "
				+ "WHERE a.planDate = ? AND c.role='E' GROUP BY a.id";
		pageInfo = findPageBySql(sql, pageInfo, date);
		List<Map<String, Object>> list = pageInfo.getItems();
		for (Map<String, Object> map : list) {
			Double Clongitude = Double.valueOf(map.get("LONGITUDE").toString());
			Double Clatitude = Double.valueOf(map.get("LATITUDE").toString());
			// 两点间距离 m
			// Double d = DistanceUtil.getDistanceByLatAndLng(latitude,
			// longitude, Clatitude, Clongitude);
			Double d = null;
			if (latitude == null && longitude == null) {
				d = new Double(-1);
			} else {
				d = LnglatUtil.GetDistance(longitude, latitude, Clongitude, Clatitude);
			}
			if (longitude == 0 || latitude == 0) {
				map.put("distance", 0);
			} else {
				map.put("distance", d);
			}
			/*
			 * map.remove("LONGITUDE"); map.remove("LATITUDE");
			 */
		}
		return pageInfo;
	}

	@SuppressWarnings("unchecked")
	@Override
	public PageInfo queryCourseByTime(PageInfo pageInfo, String date, Double longitude, Double latitude) {
		String sql = "SELECT a.id, a.hour_price as member_price,a.startTime,a.endTime,b.name AS courseName,b.image,c.name AS clubName,c.latitude,c.longitude  "
				+ "FROM tb_course a LEFT JOIN tb_course_info b ON a.courseId = b.id LEFT JOIN tb_member c ON a.member = c.id "
				+ "WHERE a.planDate = ? AND c.role='E' AND a.startTime >= ? GROUP BY a.id";
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		pageInfo = findPageBySql(sql, pageInfo, date, sdf.format(new Date()));
		List<Map<String, Object>> list = pageInfo.getItems();
		for (Map<String, Object> map : list) {
			Double Clongitude = Double.valueOf(map.get("LONGITUDE").toString());
			Double Clatitude = Double.valueOf(map.get("LATITUDE").toString());
			// 两点间距离 m
			// Double d = DistanceUtil.getDistanceByLatAndLng(latitude,
			// longitude, Clatitude, Clongitude);
			Double d = null;
			if (latitude == null && longitude == null) {
				d = new Double(-1);
			} else {
				d = LnglatUtil.GetDistance(longitude, latitude, Clongitude, Clatitude);
			}
			if (longitude == 0 || latitude == 0) {
				map.put("distance", 0);
			} else {
				map.put("distance", d);
			}
			/*
			 * map.remove("LONGITUDE"); map.remove("LATITUDE");
			 */
		}
		return pageInfo;
	}

	// 查询字段添加coach，by dou(2017-08-21)
	@Override
	public Map<String, Object> queryMemberDetail(String id) {
		String sql = "SELECT a.name,a.image,a.mobilephone,a.coach,a.province,a.city,a.county,a.longitude,a.latitude, count(b.memberSign) AS signNum FROM tb_member a,tb_sign_in b WHERE a.id = b.memberSign AND a.id = ?";
		Map<String, Object> map = this.queryForMap(sql, id);
		return map;
	}

	/**
	 * E卡通俱乐部的评论
	 */
	@Override
	public PageInfo queryProduct45MemberEvaluate(PageInfo pageInfo, String id, String type) {
		StringBuffer sql = new StringBuffer();
		sql.append(
				"SELECT e.id,m.id AS mid,m.`name`,m.image,e.totality_score,e.eval_content,date_format(e.EVAL_TIME, '%Y-%m-%d') AS evalTime, "
						+ "e.image1,e.image2,e.image3,s.memberAudit,m.signNum FROM tb_member_evaluate e LEFT JOIN tb_sign_in s ON e.signIn = s.id  "
						+ "LEFT JOIN ( SELECT a.id,a. NAME,a.image,IFNULL(b.signNum, 0) AS signNum,b.memberAudit FROM tb_member a "
						+ "LEFT JOIN (SELECT memberSign,COUNT(memberSign) AS signNum,memberAudit FROM tb_sign_in GROUP BY memberSign) b ON a.id = b.memberSign) m "
						+ " ON s.memberSign = m.id LEFT JOIN tb_member mm ON s.memberAudit = mm.id WHERE mm.role = 'E' and s.memberAudit = ? ");
		if ("1".equals(type)) {
			sql.append(" AND e.totality_score >= 80");
		} else if ("2".equals(type)) {
			sql.append(" AND e.totality_score >=60 AND e.totality_score < 80");
		} else {
			sql.append(" AND e.totality_score < 60");
		}
		sql.append(" order by evalTime desc");
		pageInfo = this.findPageBySql(sql.toString(), pageInfo, id);
		return pageInfo;
	}

	/**
	 * E卡通俱乐部的评论
	 */
	@Override
	public PageInfo queryEvaluateByDou(PageInfo pageInfo, String id, String type) {
		StringBuffer sql = new StringBuffer();
		sql.append(
				"SELECT e.id,m.id AS mid,m.`name`,m.image,e.totality_score,e.eval_content,date_format(e.EVAL_TIME, '%Y-%m-%d') AS evalTime, "
						+ "e.image1,e.image2,e.image3,s.memberAudit,m.signNum FROM tb_member_evaluate e LEFT JOIN tb_sign_in s ON e.signIn = s.id  "
						+ "LEFT JOIN ( SELECT a.id,a. NAME,a.image,IFNULL(b.signNum, 0) AS signNum,b.memberAudit FROM tb_member a "
						+ "LEFT JOIN (SELECT memberSign,COUNT(memberSign) AS signNum,memberAudit FROM tb_sign_in GROUP BY memberSign) b ON a.id = b.memberSign) m "
						+ " ON s.memberSign = m.id LEFT JOIN tb_member mm ON s.memberAudit = mm.id WHERE mm.role = 'E' and s.memberAudit = ? ");
		if ("1".equals(type)) {
			sql.append(" AND e.totality_score >= 80");
		}

		if ("2".equals(type)) {
			sql.append(" AND e.totality_score >=60 AND e.totality_score < 80");
		}

		if ("3".equals(type)) {
			sql.append(" AND e.totality_score < 60");
		}
		sql.append(" order by evalTime desc");
		pageInfo = this.findPageBySql(sql.toString(), pageInfo, id);
		return pageInfo;
	}

	/**
	 * 俱乐部的所有评论
	 */
	@Override
	public PageInfo queryStoreEvaluate(PageInfo pageInfo, String id, String type, String queryType) {
		String sql = "";
		sql = "SELECT e.id,m.id as mid,mm.id as cid, m.`name` mName,mm.name as cName, m.image mImage , mm.image as cImage, "
				+ " (CASE " + " WHEN e.totality_score IN (0-5) THEN e.totality_score "
				+ " WHEN e.totality_score IN (6,20) THEN 1 " + " WHEN e.totality_score IN (21,40) THEN 2 "
				+ " WHEN e.totality_score IN (41,60) THEN 3 " + " WHEN e.totality_score IN (61,80) THEN 4 "
				+ " WHEN e.totality_score IN (81,100) THEN 5 " + " ELSE 0 END ) as totality_score, " + " (CASE "
				+ " WHEN e.device_score IN (0-5) THEN e.device_score " + " WHEN e.device_score IN (6,20) THEN 1 "
				+ " WHEN e.device_score IN (21,40) THEN 2 " + " WHEN e.device_score IN (41,60) THEN 3 "
				+ " WHEN e.device_score IN (61,80) THEN 4 " + " WHEN e.device_score IN (81,100) THEN 5 "
				+ " ELSE 0 END ) as device_score, " + " (CASE " + " WHEN e.even_score IN (0-5) THEN e.even_score "
				+ " WHEN e.even_score IN (6,20) THEN 1  " + " WHEN e.even_score IN (21,40) THEN 2 "
				+ " WHEN e.even_score IN (41,60) THEN 3 " + " WHEN e.even_score IN (61,80) THEN 4 "
				+ " WHEN e.even_score IN (81,100) THEN 5 " + " ELSE 0 END ) as even_score, " + " (CASE  "
				+ " WHEN e.service_score IN (0-5) THEN e.service_score " + " WHEN e.service_score IN (6,20) THEN 1  "
				+ " WHEN e.service_score IN (21,40) THEN 2 " + " WHEN e.service_score IN (41,60) THEN 3 "
				+ " WHEN e.service_score IN (61,80) THEN 4 " + " WHEN e.service_score IN (81,100) THEN 5 "
				+ " ELSE 0 END ) as service_score, "
				+ " e.eval_content, date_format(e.EVAL_TIME, '%Y-%m-%d') AS evalTime, e.image1, e.image2, e.image3, s.memberAudit, "
				+ " m.signNum FROM tb_member_evaluate e LEFT JOIN tb_sign_in s ON e.signIn = s.id LEFT JOIN (SELECT a.id, a. NAME, a.image, "
				+ " IFNULL(b.signNum, 0) AS signNum, b.memberAudit FROM tb_member a LEFT JOIN ( SELECT memberSign, COUNT(memberSign) AS signNum, "
				+ " memberAudit FROM tb_sign_in GROUP BY memberSign ) b ON a.id = b.memberSign) m ON s.memberSign = m.id LEFT JOIN tb_member mm on s.memberAudit=mm.id WHERE mm.role = 'E'";

		if ("1".equals(type)) {
			sql += " AND e.totality_score >= 80";
		}
		if ("2".equals(type)) {
			sql += " AND e.totality_score >=60 AND e.totality_score < 80";
		}
		if ("3".equals(type)) {
			sql += " AND e.totality_score < 60";
		}

		if ("0".equals(queryType)) {
			sql += " order by e.totality_score desc";
		} else {
			sql += " AND s.memberAudit = " + id;
			sql += " order by eval_time desc";
		}
		pageInfo = this.findPageBySql(sql, pageInfo);
		return pageInfo;
	}

	/**
	 * 教练的所有评论
	 */
	@Override
	public PageInfo queryProduct45MemberEvaluate(PageInfo pageInfo, String id, String type, String queryType) {
		StringBuffer sql = new StringBuffer();
		sql.append(
				"SELECT e.id,m.id as mid,mm.id as cid, m.`name` mName,mm.name as cName, m.image mImage , mm.image as cImage, e.totality_score, e.even_score, e.device_score, e.service_score, "
						+ "e.eval_content, date_format(e.EVAL_TIME, '%Y-%m-%d') AS evalTime, e.image1, e.image2, e.image3, s.memberAudit, "
						+ "m.signNum FROM tb_member_evaluate e LEFT JOIN tb_sign_in s ON e.signIn = s.id LEFT JOIN (SELECT a.id, a. NAME, a.image, "
						+ "IFNULL(b.signNum, 0) AS signNum, b.memberAudit FROM tb_member a LEFT JOIN ( SELECT memberSign, COUNT(memberSign) AS signNum, "
						+ "memberAudit FROM tb_sign_in GROUP BY memberSign ) b ON a.id = b.memberSign) m ON s.memberSign = m.id LEFT JOIN tb_member mm on s.memberAudit=mm.id WHERE mm.role = 'S'");

		if ("0".equals(queryType)) {
			sql.append(" order by eval_time desc");
		} else {
			sql.append(" AND s.memberAudit = " + id);
			sql.append(" order by eval_time desc ");
		}
		pageInfo = this.findPageBySql(sql.toString(), pageInfo);
		return pageInfo;
	}

	/**
	 * 会员的所有评论
	 */
	public PageInfo queryProduct45MemberEvaluate(PageInfo pageInfo, String id, String type, String queryType,
			String member) {
		StringBuffer sql = new StringBuffer();
		sql.append(
				"SELECT e.id,m.id as mid,mm.id as cid, m.`name` mName,mm.name as cName, m.image mImage , mm.image as cImage, e.totality_score, e.even_score, e.device_score, e.service_score, "
						+ "e.eval_content, date_format(e.EVAL_TIME, '%Y-%m-%d') AS evalTime, e.image1, e.image2, e.image3, s.memberAudit, "
						+ "m.signNum FROM tb_member_evaluate e LEFT JOIN tb_sign_in s ON e.signIn = s.id LEFT JOIN (SELECT a.id, a. NAME, a.image, "
						+ "IFNULL(b.signNum, 0) AS signNum, b.memberAudit FROM tb_member a LEFT JOIN ( SELECT memberSign, COUNT(memberSign) AS signNum, "
						+ "memberAudit FROM tb_sign_in GROUP BY memberSign ) b ON a.id = b.memberSign) m ON s.memberSign = m.id LEFT JOIN tb_member mm on s.memberAudit=mm.id WHERE mm.role = 'S'");


		if ("0".equals(queryType)) {
			sql.append(" order by eval_time desc");
		} else {
			sql.append(" AND s.memberSign = " + id);
			sql.append(" order by eval_time desc");
		}
		pageInfo = this.findPageBySql(sql.toString(), pageInfo);
		return pageInfo;
	}

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public PageInfo findPageBySql(String sql, PageInfo pageInfo) {
		StringBuffer sbCount = new StringBuffer("select count(*) from (" + sql + ") temp");
		StringBuffer sbQuery = new StringBuffer(
				"select * from (" + sql + ") temp limit " + pageInfo.getStart() + " , " + pageInfo.getPageSize());
		Integer count = (Integer) this.jdbc.queryForObject(sbCount.toString(), Integer.class);
		PageInfo pi = PageInfo.getInstance();
		pi.setCurrentPage(pageInfo.getCurrentPage());
		pi.setPageSize(pageInfo.getPageSize());
		pi.setOrder(pageInfo.getOrder());
		pi.setOrderFlag(pageInfo.getOrderFlag());
		pi.setTotalCount(count.intValue());
		pi.setItems(this.jdbc.queryForList(sbQuery.toString()));
		return pi;
	}

	@Override
	public List<Map<String, Object>> queryProduct45MemberCourse(String id) {
		String sql = "SELECT id,image,name FROM tb_course_info WHERE member = ?";
		List<Map<String, Object>> list = this.queryForList(sql, id);
		return list;
	}

	/*
	 * @Override public PageInfo queryActiveByType(PageInfo pageInfo, String
	 * type) { String appendSql = type.equals("A") ?
	 * "(a.target = 'A' or a.target = 'B')" : "a.target = 'C'"; String sql =
	 * "SELECT a.id,a.name,a.creator,a.days,a.target,a.value,COUNT(b.active) AS signNum,a.award,c.name AS institution,a.amerce_money,a.memo FROM tb_active a "
	 * +
	 * "LEFT JOIN tb_active_order b ON a.id = b.active and b.status = '1' LEFT JOIN tb_member c ON a.institution = c.id "
	 * + "WHERE " + appendSql + " and a.status = 'B' GROUP BY a.id"; pageInfo =
	 * this.findPageBySql(sql, pageInfo); List<Map<String, Object>> list =
	 * pageInfo.getItems(); for (Map<String, Object> map : list) { if
	 * (map.get("TARGET").toString().equals("A")) { map.put("TARGET",
	 * map.get("DAYS").toString() + "天增加" + map.get("VALUE") + "KG"); } else if
	 * (map.get("TARGET").toString().equals("B")) { map.put("TARGET",
	 * map.get("DAYS").toString() + "天减少" + map.get("VALUE") + "KG"); } else {
	 * map.put("TARGET", map.get("DAYS").toString() + "天健身" + map.get("VALUE") +
	 * "次"); } map.remove("DAYS"); map.remove("VALUE"); } return pageInfo; }
	 */

	@Override
	public Map<String, Object> queryCourseDetail(String id) {
		String sql = "SELECT a.id,b.name,b.image,a.hour_price,a.planDate,a.startTime,a.endTime,a.memo,b.name AS courseName,c.name AS clubName,c.address,c.longitude as last_lng, c.latitude as last_lat,c.tell as mobilephone,(a.count-COUNT(d.course)) AS surplusNum  "
				+ "FROM tb_course a LEFT JOIN tb_course_info b ON a.courseId = b.id LEFT JOIN tb_member c ON a.member = c.id LEFT JOIN TB_CourseRelease_ORDER d ON a.id = d.course and d.status = '1' "
				+ "WHERE a.id = ? GROUP BY a.id";
		Map<String, Object> map = this.queryForMap(sql, id);
		return map;
	}

	@Override
	public Map<String, Object> queryCourseDetail2(String id) {
		String sql = "SELECT a.id,b.name,b.image,a.hour_price,a.planDate,a.startTime,a.endTime,b.memo,b.name AS courseName,c.name AS clubName,c.address,c.longitude as last_lng, c.latitude as last_lat,c.tell as mobilephone,(a.count-COUNT(d.course)) AS surplusNum  "
				+ "FROM tb_course a LEFT JOIN tb_course_info b ON a.courseId = b.id LEFT JOIN tb_member c ON a.member = c.id LEFT JOIN TB_CourseRelease_ORDER d ON a.id = d.course and d.status = '1' "
				+ "WHERE a.id = ? GROUP BY a.id";
		Map<String, Object> map = this.queryForMap(sql, id);
		return map;
	}

	@Override
	public PageInfo income(PageInfo pageInfo, long id) {		
		String sql = "";
		sql = "SELECT b.id,b.PROD_NAME as prodName,date_format(b.BALANCE_TIME,'%Y-%m-%d') as balanceTime  ,m.`name` as  fromName, round(b.BALANCE_MONEY, 2) as balanceMoney  FROM tb_order_balance_v45 b LEFT JOIN tb_member  m ON m.id = b.BALANCE_FROM where b.BALANCE_TO =? ORDER BY BALANCE_TIME DESC";
		pageInfo = this.findPageBySql(sql, pageInfo, id);
		return pageInfo;
	}

	@Override
	public Map<String, Object> queryActiveDetail(String aid, String id) {
		String sql = "";
		sql = "SELECT a.name,a.creator,a.create_time,a.days,COUNT(b.id) AS signNum,a.target,a.value "
				+ "FROM tb_active a LEFT JOIN tb_active_order b ON a.id = b.active WHERE a.id = ?";
		Map<String, Object> map = this.queryForMap(sql, aid);
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date openDate;
		try {
			openDate = formatter.parse(map.get("CREATE_TIME").toString());
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(openDate);
			calendar.add(Calendar.DATE, Integer.parseInt(map.get("DAYS").toString()));
			Date endDate = calendar.getTime();
			map.put("END_DATE", endDate);
			if (map.get("TARGET").toString().equals("A")) {
				map.put("TARGET", map.get("DAYS").toString() + "天增加" + map.get("VALUE") + "KG");
			} else if (map.get("TARGET").toString().equals("B")) {
				map.put("TARGET", map.get("DAYS").toString() + "天减少" + map.get("VALUE") + "KG");
			} else {
				map.put("TARGET", map.get("DAYS").toString() + "天健身" + map.get("VALUE") + "次");
			}
			map.remove("VALUE");
			map.remove("DAYS");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		sql = "SELECT COUNT(a.id) AS playNum FROM tb_sign_in a where a.memberSign = '9355' AND a.signDate >= ? AND a.signDate <= ?";
		Map<String, Object> map1 = this.queryForMap(sql, id, map.get("CREATE_TIME"), map.get("END_DATE"));
		map.put("playNum", map1.get("playNum"));
		return map;
	}

	// 2017-7-19 修改原来查询逻辑和SQL
	@SuppressWarnings("unused")
	@Override
	public Map<String, Object> queryOneCardOrderDetail(String id) {
		String sql = "SELECT b.id, a.member,b.PROD_NAME,b.PROD_PERIOD_UNIT,a.orderMoney AS PROD_PRICE,a.no,date_format(a.orderStartTime, '%Y-%m-%d')  as orderStartTime, a.orderStartTime as startTime,date_format(a.orderEndTime,'%Y-%m-%d') AS END_DATE,COUNT(d.id) AS count, b.PROD_CONTENT FROM tb_product_order_v45 a "
				+ "LEFT JOIN tb_product_v45 b ON a.ORDER_PRODUCT = b.id "
				+ " LEFT JOIN tb_product_club_v45  d ON d.PRODUCT = a.ORDER_PRODUCT WHERE a.id = ?";
		Map<String, Object> map = this.queryForMap(sql, id);
		String openDate = map.get("orderStartTime").toString();
		// 查询签到次数
		Long signNum = queryForLong(
				"SELECT count(*) from tb_sign_in where signDate >=? and signDate <= ?  AND memberSign=? and  orderId= ? ",
				map.get("startTime"), map.get("END_DATE"), map.get("member"), id);
		map.put("signNum", signNum);

		/*
		 * String sql =
		 * "SELECT b.id, a.member,b.PROD_NAME,b.PROD_PERIOD_UNIT,a.orderMoney AS PROD_PRICE,a.no,date_format(a.orderStartTime, '%Y-%m-%d')  as orderStartTime, a.orderStartTime as startTime,COUNT(d.id) AS count, b.PROD_CONTENT FROM tb_product_order_v45 a "
		 * + "LEFT JOIN tb_product_v45 b ON a.ORDER_PRODUCT = b.id " +
		 * " LEFT JOIN tb_product_club_v45  d ON d.PRODUCT = a.ORDER_PRODUCT WHERE a.id = ?"
		 * ; Map<String, Object> map = this.queryForMap(sql, id); String
		 * openDate = map.get("orderStartTime").toString(); if
		 * (map.get("PROD_PERIOD_UNIT").equals("A")) { DateFormat formatter =
		 * new SimpleDateFormat("yyyy-MM-dd"); DateFormat formatters = new
		 * SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); Date date; try { date =
		 * formatter.parse(openDate); Calendar calendar =
		 * Calendar.getInstance(); calendar.setTime(date);
		 * calendar.add(Calendar.MONTH, 1); Date endDate = calendar.getTime();
		 * map.put("END_DATE", formatter.format(endDate));
		 * map.remove("PROD_PERIOD_UNIT");
		 * calendar.setTime(formatter.parse(formatter.format(endDate).toString()
		 * )); calendar.add(Calendar.DAY_OF_MONTH, 1); Date endDates =
		 * calendar.getTime();
		 * 
		 * // 查询签到次数 Long signNum = queryForLong(
		 * "SELECT count(*) from tb_sign_in where signDate >=? and signDate <= ?  AND memberSign=? and  orderId= ? "
		 * ,map.get("startTime"),
		 * formatters.format(endDates),map.get("member"),id); map.put("signNum",
		 * signNum); } catch (ParseException e) { e.printStackTrace(); } }
		 */

		return map;
	}

	@Override
	public PageInfo queryMySignIn(PageInfo pageInfo, String id) {
		String sql = "SELECT a.id,date_format(a.signDate, '%Y-%m-%d') as signDate,m.image,m.name,b.TOTALITY_SCORE,b.SERVICE_SCORE,b.DEVICE_SCORE,b.EVEN_SCORE,a.SIGN_TYPE   "
				+ "FROM tb_sign_in a LEFT JOIN tb_member m ON a.memberAudit = m.id "
				+ "LEFT JOIN tb_member_evaluate b ON a.id = b.signIn "
				+ "WHERE a.memberSign = ? GROUP BY a.id ORDER BY a.signDate DESC";
		pageInfo = this.findPageBySql(sql, pageInfo, id);
		return pageInfo;
	}

	@Override
	public Map<String, Object> queryMemberEvaluateDetail(String id) {
		String sql = "SELECT m.image as cimage,m.name AS cname,m.address,v.totalScore,v.deviceScore,v.evenScore,v.serviceScore,b.name,b.image,count(s.id) AS signNum,c.EVAL_CONTENT,date_format(c.EVAL_TIME, '%Y-%m-%d %H:%i ') as EVAL_TIME,c.id,c.image1,c.image2,c.image3 "
				+ "FROM tb_sign_in a LEFT JOIN tb_member m ON a.memberAudit = m.id "
				+ "LEFT JOIN v_member_score_detail v ON a.memberAudit = v.id "
				+ "LEFT JOIN tb_member b ON a.memberSign = b.id LEFT JOIN tb_member_evaluate c ON a.id = c.signIn "
				+ "LEFT JOIN tb_sign_in s ON b.id  = s.memberSign WHERE a.id = ? ";
		Map<String, Object> map = this.queryForMap(sql, id);
		return map;
	}

	@Override
	public Map<String, Object> querySignInfo(long id, String type) {
		String sql = "";
		if ("8".equals(type) || "0".equals(type)) {
			sql = "SELECT s.id AS id, o.id AS oid, date_format(s.signDate, '%Y-%m-%d %H:%i') as signDate, m.name as cname, p.PROD_NAME as pname, m.address,m.image "
					+ "FROM tb_sign_in s LEFT JOIN tb_product_order_v45 o ON s.orderId = o.id "
					+ "LEFT JOIN tb_member m ON s.memberAudit = m.id "
					+ "LEFT JOIN tb_product_v45 p ON p.id=o.ORDER_PRODUCT "
					+ "LEFT JOIN v_member_score_detail v ON m.id = v.id WHERE S.id= ?";
		} else if ("5".equals(type)) {
			sql = "SELECT s.id AS id, o.id AS oid, date_format(s.signDate, '%Y-%m-%d %H:%i') as signDate, m.name as cname, i.name as pname, m.address,m.image "
					+ "FROM tb_sign_in s "
					+ "LEFT JOIN tb_courserelease_order o ON s.orderId = o.id LEFT JOIN tb_course c ON c.id = o.course "
					+ "LEFT JOIN tb_course_info i on c.courseId = i.id LEFT JOIN tb_member m ON m.id=i.member "
					+ "LEFT JOIN v_member_score_detail v ON m.id = v.id WHERE s.id= ?";
		}
		Map<String, Object> map = this.queryForMap(sql, id);
		return map;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void saveActiveResult(Double weight, long id) {
		ActiveOrder ao = (ActiveOrder) this.load(ActiveOrder.class, id);
		ao.setLastWeight(weight);
		saveOrUpdate(activeBalanceImpl.execute(ao));
	}

	@Override
	public Map<String, Object> queryClubEvaluateNum(String id) {
		String sql = "SELECT SUM(CASE WHEN c.TOTALITY_SCORE>=80 THEN 1 ELSE 0 END) AS Aevaluate,"
				+ "SUM(CASE WHEN c.TOTALITY_SCORE >=60 AND c.TOTALITY_SCORE < 80 THEN 1 ELSE 0 END ) AS Bevaluate,"
				+ "SUM(CASE WHEN c.TOTALITY_SCORE<60 THEN 1 ELSE 0 END) AS Cevaluate FROM tb_member a "
				+ "LEFT JOIN tb_sign_in b ON a.id = b.memberAudit "
				+ "LEFT JOIN tb_member_evaluate c ON b.id = c.signIn WHERE a.id = ?";
		Map<String, Object> map = this.queryForMap(sql, id);
		return map;
	}

	public Map<String, Object> queryClubEvaluateNum(String id, String s) {
		String sql = "select count(*) allEvaluate FROM tb_member_evaluate c";
		Map<String, Object> map = this.queryForMap(sql);
		return map;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public Member login(final String mobile, final String password, Double lng, Double lat) {
		final List<?> list = getHibernateTemplate().find(
				"from Member u where (u.mobilephone = ? or u.email = ?) and u.password = ? ",
				new Object[] { mobile, mobile, password });
		if (list.size() > 0) {
			final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Member member = (Member) list.get(0);
			member.setLoginTime(new Date());
			member.setToKen(MD5.MD5Encode(mobile + password + sdf.format(member.getLoginTime())));
			if (lng != null && lat != null) {
				member.setLongitude(lng);
				member.setLatitude(lat);
			}
			return getHibernateTemplate().merge(member);
		}
		return null;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public Member login(final String mobile, final String password) {

		String sql1 = "select * from tb_member where (nick = ? and password = ?) or (mobilephone = ? and mobile_valid ='1' and password = ?) ";
		Object[] obj1 = { mobile, password, mobile, password };
		Map<String, Object> map1 = DataBaseConnection.getOne(sql1, obj1);
		long id = Long.parseLong(map1.get("id").toString());

		if (id > 0) {
			final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date loginTime = new Date();
			String loginToken = MD5.MD5Encode(mobile + password + sdf.format(loginTime));
			// Member member = (Member) list.get(0);

			String sql2 = "update tb_member set login_time=?,login_token=? where id=?";
			Object[] obj2 = { loginTime, loginToken, id };
			int x = DataBaseConnection.updateData(sql2, obj2);

			if (x > 0) {
				String sql3 = "select * from tb_member where id=?";
				Object[] obj3 = { id };
				Map<String, Object> map3 = DataBaseConnection.getOne(sql3, obj3);
				Member member = new Member();

				// member = (Member) DataBaseConnection.getOne(sql3, obj3,
				// Member.class);
				String role = map3.get("role").toString();
				member.setId(id);
				member.setRole(role);

				return member;
			}

		}
		return null;
	}

	@SuppressWarnings("unused")
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public Member thirdLoginCheck(String thirdType, String thirdId, Double lng, Double lat) {
		String hql = "from Member m where ";
		if ("Q".equals(thirdType)) {
			hql += "m.qqId = ?";
		} else if ("S".equals(thirdType)) {
			hql += "m.sinaId = ?";
		} else if ("W".equals(thirdType)) {
			hql += "m.wechatID = ?";
		}
		HibernateTemplate template = getHibernateTemplate();
		List<?> list = getHibernateTemplate().find(hql, thirdId);
		if (list.size() > 0) {
			Member member = (Member) list.get(0);
			final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			member.setLoginTime(new Date());
			member.setToKen(MD5.MD5Encode((member.getMobilephone() == null ? "" : member.getMobilephone())
					+ (member.getPassword() == null ? "" : member.getPassword()) + sdf.format(member.getLoginTime())));
			if (lng != null && lat != null) {
				member.setLongitude(lng);
				member.setLatitude(lat);
			}
			return getHibernateTemplate().merge(member);
		}
		return null;
	}

	/**
	 * 微信公众号登录检查
	 */
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public Member thirdLoginCheck(String thirdType, String thirdId, Double lng, Double lat, String wx) {
		String sql = "select * from tb_member m where ";
		if ("Q".equals(thirdType)) {
			sql += "m.qqId = ?";
		} else if ("S".equals(thirdType)) {
			sql += "m.sinaId = ?";
		} else if ("W".equals(thirdType)) {
			sql += "m.wechatID = ?";
		}

		List<Map<String, Object>> list = DataBaseConnection.getList(sql, new Object[] { thirdId });
		if (list.size() > 0) {
			Map<String, Object> map = list.get(0);
			final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Member member = new Member();
			member.setId(Long.parseLong(map.get("id").toString()));
			member.setThirdType(map.get("thirdType").toString());
			if ("Q".equals(thirdType)) {
				member.setQqId(map.get("qqId").toString());
			} else if ("S".equals(thirdType)) {
				member.setSinaId(map.get("sina_id").toString());
			} else if ("W".equals(thirdType)) {
				member.setWechatID(map.get("wechatID").toString());
			}
			member.setLoginTime(new Date());
			member.setToKen(MD5.MD5Encode((map.get("mobilephone") == null ? "" : map.get("mobilephone").toString())
					+ (map.get("password") == null ? "" : map.get("password").toString())
					+ sdf.format(member.getLoginTime())));
			if (lng != null && lat != null) {
				member.setLongitude(lng);
				member.setLatitude(lat);
			}
			String sqlx = "";
			Object[] objx = new Object[1];
			if (lng != null && lat != null) {
				sqlx = "update tb_member set login_time=?,login_token=? where id=?";
				objx = new Object[] { member.getLoginTime(), member.getToKen(), member.getId() };
			} else {
				sqlx = "update tb_member set login_time=?,login_token=?,latitude=?,longitude=? where id=?";
				objx = new Object[] { member.getLoginTime(), member.getToKen(), member.getLatitude(),
						member.getLongitude(), member.getId() };
			}

			int x = DataBaseConnection.updateData(sqlx, objx);
			if (x > 0) {
				return member;
			}

		}
		return null;
	}

	@Override
	public Map<String, Object> queryPickAccount(String id) {
		String sql = "SELECT * FROM tb_pick_account where member=?";
		Map<String, Object> map = this.queryForMap(sql, id);
		return map;
	}

	@Override
	public Map<String, Object> signShare(String id) {
		String sql = "SELECT m.id,m.image,m.`name` as name,m.province,m.county,m.city, date_format(s.signDate, '%Y-%m-%d') as signDate,mc.longitude, mc.latitude,mc.`name` as cname FROM tb_sign_in s LEFT JOIN tb_member m ON m.id= s.memberSign LEFT JOIN tb_member mc ON mc.id=s.memberAudit where s.id=?";
		Map<String, Object> map = this.queryForMap(sql, id);
		return map;
	}

}
