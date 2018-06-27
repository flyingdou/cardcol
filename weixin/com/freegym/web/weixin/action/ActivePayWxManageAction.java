package com.freegym.web.weixin.action;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.freegym.web.active.Active;
import com.freegym.web.active.Team;
import com.freegym.web.basic.Member;
import com.freegym.web.config.Setting;
import com.freegym.web.mobile.BasicJsonAction;
import com.freegym.web.order.ActiveOrder;
import com.sanmen.web.core.common.LogicException;

import net.sf.json.JSONObject;

@Namespace("")
@InterceptorRefs({ @InterceptorRef("customStack") })
@Results({ @Result(name = "success", location = "/WX/bzj_orders.jsp") })
public class ActivePayWxManageAction extends BasicJsonAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3731027384259793243L;

	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	@SuppressWarnings("unused")
	private static final String[] Request = null;
	private Long teamId;
	private String startDate;
	private double weight;
	private Long jid;
	private ActiveOrder activeOrder;
	private Active active;

	public Active getActive() {
		return active;
	}

	public void setActive(Active active) {
		this.active = active;
	}

	public ActiveOrder getActiveOrder() {
		return activeOrder;
	}

	public void setActiveOrder(ActiveOrder activeOrder) {
		this.activeOrder = activeOrder;
	}

	public Long getJid() {
		return jid;
	}

	public void setJid(Long jid) {
		this.jid = jid;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
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
	 * 保存活动报名,传入参数，JSONS数组[{joinDate: '2013-09-11', weight: 80, judge: ''}]
	 */
	public void savePartake() {
		try {
			final String strKey = request.getParameter("key");
			Member mu = (Member) session.getAttribute("member");
			final Long mid = mu.getId();
			final Setting set = service.loadSetting(mid);
			set.setWeight(weight);
			service.saveOrUpdate(set);
			final Active a = (Active) service.load(Active.class, id);
			if (a == null)
				throw new LogicException("请确认传入的ID值为有效的挑战信息主表的ID值！");
			ActiveOrder ao = new ActiveOrder();
			if (null != strKey && !"".equals(strKey))
				ao.setId(new Long(strKey));
			ao.setActive(a);
			ao.setOrderDate(new Date());
			ao.setOrderStartTime(sdf.parse(startDate));
			/* ao.setJudge(jid.toString()); */
			if (teamId == null)
				ao.setMember(new Member(mid));
			else {
				ao.setMember(new Member(mid));
				ao.setTeam(new Team(teamId));
			}
			ao.setValue(0d);
			ao.setStatus('0');
			ao.setOrigin('B');
			ao.setWeight(weight);
			ao = service.saveActivePartake(ao, teamId, '0', null, null);
			final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			final JSONObject obj = new JSONObject(), orderJson = new JSONObject();
			orderJson.accumulate("id", ao.getId()).accumulate("orderNo", ao.getNo())
					.accumulate("cost", ao.getOrderMoney()).accumulate("orderDate", sdf.format(ao.getOrderDate()))
					.accumulate("payNo", ao.getPayNo());
			obj.accumulate("success", true).accumulate("order", orderJson);
			response(obj);
		} catch (Exception e) {
			log.error("error", e);
			response(e);
		}
	}

	@Override
	public String execute() {
		activeOrder = (ActiveOrder) service.load(ActiveOrder.class, id);
		System.out.println(activeOrder.getActive().getId());
		active = (Active) service.load(Active.class, activeOrder.getActive().getId());
		Long count = null;
		count = service.queryForLong(
				"select count(*) from tb_active_order ci where ci.active = ? and ci.status != '0' ", active.getId());
		request.setAttribute("count", count);
		String[] weekOfDays = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
		Calendar calendar = Calendar.getInstance();
		if (activeOrder.getOrderStartTime() != null) {
			calendar.setTime(activeOrder.getOrderStartTime());
		}
		int w = calendar.get(Calendar.DAY_OF_WEEK) - 1;
		if (w < 0) {
			w = 0;
		}
		request.setAttribute("xq", weekOfDays[w]);
		return SUCCESS;
	}

}
