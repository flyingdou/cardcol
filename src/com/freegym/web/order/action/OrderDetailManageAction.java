package com.freegym.web.order.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.freegym.web.OrderBasicAction;
import com.freegym.web.basic.Member;
import com.freegym.web.order.OrderDetail;
import com.freegym.web.order.ProductOrderDetail;
import com.freegym.web.order.Statistic;
import com.freegym.web.utils.EasyUtils;
import com.sanmen.web.core.utils.StringUtils;

@Namespace("")
@InterceptorRefs({ @InterceptorRef("foreStack") })
@Results({ @Result(name = "order_detail_list", location = "/order/order_detail_list.jsp") })
public class OrderDetailManageAction extends OrderBasicAction {

	private static final long serialVersionUID = -3769475961884283745L;

	private String type;// 1:全部交易记录2:按订单查看交易记录

	private ProductOrderDetail orderDetail, orderQuery;

	private Map<String, Object> moneys = new HashMap<String, Object>();

	@SuppressWarnings({ "unused", "unchecked" })
	public String query() {
		session.setAttribute("spath", 5);
		/* 统计信息开始 */
		// 您的总合同额
		final Statistic stat = new Statistic();
		final Member m = this.toMember();
		final String tradeSql = "SELECT a.id,a.BALANCE_TO,a.BALANCE_FROM,a.ORDER_ID,b.name AS fromName,a.PROD_NAME AS prodName,(CASE a.BALANCE_TYPE WHEN '1' THEN 'E卡通订单' WHEN '2' THEN '挑战订单' WHEN '3' THEN '课程订单' WHEN '4' THEN '专家计划订单' END) AS orderName,a.ORDER_NO AS orderNo,a.ORDER_MONEY AS orderMoney,a.BALANCE_MONEY AS balanceMoney,a.BALANCE_SERVICE AS serviceMoney,a.BALANCE_TIME AS balanceDate "
				+ "FROM tb_order_balance_v45 a " + "LEFT JOIN tb_member b ON a.BALANCE_FROM = b.id ";
		final String tradeSql1 = " UNION ALL SELECT a.id,a.BALANCE_TO,a.BALANCE_FROM,a.ORDER_ID,b.name,a.PROD_NAME AS prodName,"
				+ "(CASE a.BALANCE_TYPE WHEN '1' THEN 'E卡通订单' WHEN '2' THEN '挑战订单' WHEN '3' THEN '课程订单' WHEN '4' THEN '专家计划订单' END) AS orderName,  "
				+ "a.ORDER_NO AS orderNo,a.ORDER_MONEY AS orderMoney, a.BALANCE_MONEY AS balanceMoney,a.BALANCE_SERVICE AS serviceMoney,a.BALANCE_TIME AS balanceDate "
				+ "FROM tb_order_balance_v45 a " + "LEFT JOIN tb_member b ON a.BALANCE_FROM = b.id "
				+ "WHERE a.BALANCE_FROM = ? ";
		StringBuffer hql = new StringBuffer(
				" select sum(o.orderMoney) from (" + tradeSql + ") o where o.BALANCE_TO = ?");
		/*
		 * if (m.getRole().equals("M")) { hql.append(" and o.fromId = ? "); }
		 * else { hql.append(" and o.toId = ? "); }3
		 */
		Double stat1Double = service.queryForDouble(hql.toString(), new Object[] { m.getId() }) == null ? 0.0
				: service.queryForDouble("select IFNULL(sum(pickMoney),0) from tb_pick_detail where member = ?",
						new Object[] { m.getId() });

		Double stat2Double = service.queryForDouble(
				"select sum(od.balanceMoney) from (" + tradeSql + ") od where od.BALANCE_TO = ?",
				new Object[] { m.getId() }) == null
						? 0.0
						: service.queryForDouble(
								"select sum(od.balanceMoney) from (" + tradeSql + ") od where od.BALANCE_TO = ?",
								new Object[] { m.getId() });

		Double stat3Double = service.queryForDouble(
				"select sum(od.orderMoney) from (" + tradeSql + ") od where od.BALANCE_FROM = ?",
				new Object[] { m.getId() }) == null
						? 0.0
						: service.queryForDouble(
								"select sum(od.orderMoney) from (" + tradeSql + ") od where od.BALANCE_FROM = ?",
								new Object[] { m.getId() });

		// 服务费
		// Double stat3Double = service.queryForDouble(
		// "select sum(od.serviceMoney) from (" + tradeSql + ") od where
		// od.BALANCE_TO = ?",
		// new Object[] { m.getId() }) == null
		// ? 0.0
		// : service.queryForDouble(
		// "select sum(od.serviceMoney) from (" + tradeSql + ") od where
		// od.BALANCE_TO = ?",
		// new Object[] { m.getId() });

		// 传给前端的用户可提现余额, 支出, 收入
		// 用户可提现余额 = 收入 - 提现记录金额总和
		double withdrawals = service.findPickMoneyCountByMember(toMember());
		moneys.put("withdrawals", EasyUtils.decimalFormat(withdrawals < 0 ? 0 : withdrawals));
		moneys.put("income", EasyUtils.decimalFormat(stat2Double));
		moneys.put("expenditure", EasyUtils.decimalFormat(stat3Double));

		/* 统计信息结束 */
		if (orderQuery == null)
			orderQuery = new ProductOrderDetail();
		orderQuery.setTransUser(m);
		List<Object> parms = new ArrayList<Object>();
		parms.add(m.getId());
		parms.add(m.getId());
		parms.add(m.getId());
		parms.add(m.getId());
		final StringBuffer where = OrderDetail.getWhere(orderQuery, type, parms);
		System.out.println(where.toString());
		String string = "select * from (" + tradeSql + " where a.BALANCE_TO = ?" + tradeSql1 + ") tmp where 1=1 "
				+ where.toString() + " order by balanceDate desc";

		String querySql = "select tmp.id,tmp.orderNo,tmp.fromName,tmp.balanceTime,tmp.balanceType,tmp.prodName,"
				+ "IFNULL(tmp.expenditure,0) expenditure ,IFNULL(tmp.income,0) income from (select t.*,(select BALANCE_MONEY from "
				+ "tb_order_balance_v45 where BALANCE_TO = ? AND id = t.id ) income,(select ORDER_MONEY expenditure from "
				+ "tb_order_balance_v45 where BALANCE_FROM = ? AND id = t.id ) expenditure from "
				+ "(select b.id,b.BALANCE_TIME balanceTime,b.ORDER_NO orderNo,b.BALANCE_TYPE balanceType,b.PROD_NAME prodName,m.name fromName,"
				+ "b.BALANCE_MONEY balanceMoney from tb_order_balance_v45 b inner join tb_member m on b.BALANCE_FROM = m.id "
				+ "where b.balance_from = ? or b.balance_to = ?) t) tmp  where 1=1 ";
		if (!StringUtils.isEmpty(where.toString())) {
			 querySql += where.toString();
		}
		querySql += " order by tmp.balanceType desc  ";
		pageInfo = service.findPageBySql(querySql, pageInfo, parms.toArray());
		pageInfo.setItems(EasyUtils.decimalFormat(pageInfo.getItems()));
		return "order_detail_list";
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public ProductOrderDetail getOrderDetail() {
		return orderDetail;
	}

	public void setOrderDetail(ProductOrderDetail orderDetail) {
		this.orderDetail = orderDetail;
	}

	public ProductOrderDetail getOrderQuery() {
		return orderQuery;
	}

	public void setOrderQuery(ProductOrderDetail orderQuery) {
		this.orderQuery = orderQuery;
	}

	public Map<String, Object> getMoneys() {
		return moneys;
	}

	public void setMoneys(Map<String, Object> moneys) {
		this.moneys = moneys;
	}
}
