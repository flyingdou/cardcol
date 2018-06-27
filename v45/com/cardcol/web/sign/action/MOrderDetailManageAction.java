package com.cardcol.web.sign.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;

import com.freegym.web.active.Active;
import com.freegym.web.active.Team;
import com.freegym.web.basic.Member;
import com.freegym.web.config.Setting;
import com.freegym.web.mobile.BasicJsonAction;
import com.freegym.web.order.ActiveOrder;
import com.freegym.web.order.Order;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Namespace("")
@InterceptorRefs({ @InterceptorRef("mobileStack") })
public class MOrderDetailManageAction extends BasicJsonAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3388883381973263518L;
	
	@SuppressWarnings("unused")
	private static final String ArrayList = null;
	/**
	 * 关键字, 裁判
	 */
	private String keyword, judge;
	private String type;
	private Long teamId;

	private String startDate;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getJudge() {
		return judge;
	}

	public void setJudge(String judge) {
		this.judge = judge;
	}

	public Long getTeamId() {
		return teamId;
	}

	public void setTeamId(Long teamId) {
		this.teamId = teamId;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	/**
	 * 支出
	 */
	@SuppressWarnings("unused")
	public void expenditure() {
		final List<Object> parms = new ArrayList<Object>();
		final JSONArray jarr = new JSONArray();
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT * FROM (").append(Order.getOrderSqls()).append(") t where 1 = 1");
		sql.append(" and (fromId = ?) and STATUS !=0 ");
		parms.add(getMobileUser().getId());
		sql.append("order by payTime desc");
		System.out.println(sql.toString());
		pageInfo = service.findPageBySql(sql.toString(), pageInfo, parms.toArray());
		JSONArray income = JSONArray.fromObject(pageInfo.getItems());
		final JSONObject obj = new JSONObject();
		obj.accumulate("success", true).accumulate("income", income).accumulate("pageInfo", getJsonForPageInfo());
		response(obj);
	}

	/**
	 * 收入
	 */
	public void income() {
		pageInfo = service.income(pageInfo, getMobileUser().getId());
		JSONArray income = JSONArray.fromObject(pageInfo.getItems());
		final JSONObject obj = new JSONObject();
		obj.accumulate("success", true).accumulate("income", income).accumulate("pageInfo", getJsonForPageInfo());
		response(obj);
	}

	/**
	 * 报名参加
	 */
	public void partake() {
		try {
			final String weight = request.getParameter("weight");
			Long mid = getMobileUser().getId();
			final Member m = (Member) service.load(Member.class, mid);
			if (weight != null) {
				final Setting set = service.loadSetting(m.getId());
				set.setWeight(new Double(weight));
				service.saveOrUpdate(set);
			}
			final Active a = (Active) service.load(Active.class, id);
			ActiveOrder ao = new ActiveOrder();
			ao.setNo(service.getKeyNo("", "TB_ACTIVE_ORDER", 14));
			ao.setPayNo(ao.getNo());
			ao.setActive(a);
			ao.setOrderDate(new SimpleDateFormat("yyyy-MM-dd").parse(startDate));
			ao.setJudge(judge.toString());
			ao.setOrderMoney(a.getAmerceMoney());
			if (teamId == null) ao.setMember(new Member(mid));
			else ao.setTeam(new Team(teamId));
			ao.setValue(0d);
			ao.setStatus('0');
			ao = (ActiveOrder) service.saveOrUpdate(ao);
			final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			final JSONObject obj = new JSONObject(), orderJson = new JSONObject();
			orderJson.accumulate("id", ao.getId()).accumulate("orderNo", ao.getNo()).accumulate("cost", ao.getOrderMoney()).accumulate("orderDate", sdf.format(ao.getOrderDate()));
			obj.accumulate("success", true).accumulate("order", orderJson);
			response(obj);
		} catch (Exception e) {
			log.error("error", e);
			response(e);
		}
	}

}
