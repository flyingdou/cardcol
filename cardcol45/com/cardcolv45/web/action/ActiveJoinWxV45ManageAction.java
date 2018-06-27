package com.cardcolv45.web.action;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;

import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.freegym.web.basic.Member;
import com.freegym.web.mobile.BasicJsonAction;
import com.freegym.web.order.ActiveOrder;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Namespace("")
@InterceptorRefs({ @InterceptorRef("customStack") })
@Results({ @Result(name = "login", location = "/WX/login.jsp"),
		@Result(name = "success", location = "/wxv45/my_challenge.jsp"), // 我的挑战
		@Result(name = "myActiveDetail", location = "/wxv45/my_challenge_detail.jsp") // 我的挑战详情
})
public class ActiveJoinWxV45ManageAction extends BasicJsonAction {

	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	/**
	 * 
	 */
	private static final long serialVersionUID = -5567628859021580202L;
	private Member member;

	private String activeId;

	public String getActiveId() {
		return activeId;
	}

	public void setActiveId(String activeId) {
		this.activeId = activeId;
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	@Override
	public String execute() {
		// TODO Test Data
		Member memberTest = new Member();
		memberTest.setId(Long.parseLong("9355"));
		request.getSession().setAttribute("member", memberTest);

		member = (Member) session.getAttribute("member");
		if (member == null) {
			return "login";
		} else {
			aorder();
			return SUCCESS;
		}
	}

	public void aorder() {
		member = (Member) session.getAttribute("member");
		final Long memberId = (long) member.getId();

		final DetachedCriteria dc = DetachedCriteria.forClass(ActiveOrder.class);
		dc.add(Restrictions.eq("member.id", memberId));
		dc.add(Restrictions.and(Restrictions.ne("status", '0'), Restrictions.isNotNull("status")));
		pageInfo.setOrder("orderStartTime");
		pageInfo.setOrderFlag("desc");
		pageInfo = service.findPageByCriteria(dc, pageInfo);
		final JSONArray jarr = new JSONArray();

		for (final Iterator<?> it = pageInfo.getItems().iterator(); it.hasNext();) {
			final ActiveOrder ao = (ActiveOrder) it.next();
			final JSONObject obj = new JSONObject();
			int a = ao.getActive().getDays();// 挑战天数
			Date startDate = ao.getOrderStartTime();// 开始时间
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(startDate);
			calendar.add(Calendar.DATE, a);
			Date endDate = calendar.getTime();// 结束时间
			// 活动状态：0为进行中，1为成功，2为失败，3为已结束
			Character result = ao.getResult().equals('0') ? '0' : (ao.getResult() != null ? ao.getResult() : '3');
			obj.accumulate("id", ao.getId()).accumulate("member", getMemberJson(ao.getActive().getCreator()))
					.accumulate("name", getString(ao.getActive().getName()))
					.accumulate("days", getInteger(ao.getActive().getDays()))
					.accumulate("teamNum", getInteger(ao.getActive().getTeamNum()))
					.accumulate("target", getString(ao.getActive().getTarget()))
					.accumulate("award", getString(ao.getActive().getAward()))
					.accumulate("institution", getMemberJson(ao.getActive().getInstitution()))
					.accumulate("amerceMoney", getDouble(ao.getActive().getAmerceMoney()))
					.accumulate("category", getString(ao.getActive().getCategory()))
					.accumulate("status", getString(ao.getStatus()))
					.accumulate("action", getString(ao.getActive().getName()))
					.accumulate("value", getDouble(ao.getActive().getValue()))
					.accumulate("applyCount", applyCount(ao.getActive().getId()))
					.accumulate("image", getString(ao.getActive().getImage()))
					.accumulate("startTime", sdf.format(ao.getOrderStartTime()))
					.accumulate("endTime", sdf.format(endDate)).accumulate("weight", ao.getWeight())
					.accumulate("lastWeight", ao.getLastWeight()).accumulate("result", result)
					.accumulate("activeId", ao.getActive().getId());
			jarr.add(obj);
		}
		final JSONObject ret = new JSONObject();
		ret.accumulate("success", true).accumulate("pageInfo", getJsonForPageInfo()).accumulate("items", jarr);
		request.setAttribute("items", jarr);
	}

	/**
	 * 我参加的指定挑战的详情
	 */
	public String myActiveDetail() {
		// TODO Test Data
		Member memberTest = new Member();
		memberTest.setId(Long.parseLong("9355"));
		request.getSession().setAttribute("member", memberTest);

		member = (Member) session.getAttribute("member");
		final Long memberId = (long) member.getId();

		final DetachedCriteria dc = DetachedCriteria.forClass(ActiveOrder.class);
		dc.add(Restrictions.eq("member.id", memberId));
		dc.add(Restrictions.eq("id", Long.parseLong(activeId)));
		dc.add(Restrictions.and(Restrictions.ne("status", '0'), Restrictions.isNotNull("status")));
		pageInfo.setOrder("orderStartTime");
		pageInfo.setOrderFlag("desc");
		pageInfo = service.findPageByCriteria(dc, pageInfo);
		final JSONArray jarr = new JSONArray();

		for (final Iterator<?> it = pageInfo.getItems().iterator(); it.hasNext();) {
			final ActiveOrder ao = (ActiveOrder) it.next();
			final JSONObject obj = new JSONObject();
			int a = ao.getActive().getDays();// 挑战天数
			Date startDate = ao.getOrderStartTime();// 开始时间
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(startDate);
			calendar.add(Calendar.DATE, a);
			Date endDate = calendar.getTime();// 结束时间
			// 活动状态：0为进行中，1为成功，2为失败，3为已结束
			Character result = ao.getResult().equals('0') ? '0' : (ao.getResult() != null ? ao.getResult() : '3');
			obj.accumulate("id", ao.getId()).accumulate("member", getMemberJson(ao.getActive().getCreator()))
					.accumulate("name", getString(ao.getActive().getName()))
					.accumulate("days", getInteger(ao.getActive().getDays()))
					.accumulate("teamNum", getInteger(ao.getActive().getTeamNum()))
					.accumulate("target", getString(ao.getActive().getTarget()))
					.accumulate("award", getString(ao.getActive().getAward()))
					.accumulate("institution", getMemberJson(ao.getActive().getInstitution()))
					.accumulate("amerceMoney", getDouble(ao.getActive().getAmerceMoney()))
					.accumulate("category", getString(ao.getActive().getCategory()))
					.accumulate("status", getString(ao.getStatus()))
					.accumulate("action", getString(ao.getActive().getName()))
					.accumulate("value", getDouble(ao.getActive().getValue()))
					.accumulate("applyCount", applyCount(ao.getActive().getId()))
					.accumulate("image", getString(ao.getActive().getImage()))
					.accumulate("startTime", sdf.format(ao.getOrderStartTime()))
					.accumulate("endTime", sdf.format(endDate)).accumulate("weight", ao.getWeight())
					.accumulate("lastWeight", ao.getLastWeight()).accumulate("result", result)
					.accumulate("activeId", ao.getActive().getId());
			jarr.add(obj);
		}
		final JSONObject ret = new JSONObject();
		ret.accumulate("success", true).accumulate("pageInfo", getJsonForPageInfo()).accumulate("items", jarr);
		request.setAttribute("items", jarr);

		return "myActiveDetail";
	}

	/**
	 * 计算挑战活参与总数
	 */
	public Long applyCount(Long id) {
		Long count = null;
		if (id != null) {
			count = service.queryForLong(
					"select count(*) from tb_active_order ci where ci.active = ? and ci.status != '0' ", id);
		}
		return count;
	}

	/**
	 * 挑战健身次数
	 * 
	 * @param id
	 * @return
	 */
	public Long signCount(Long id, Date date) {
		SimpleDateFormat ymd = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
		Long count = null;
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, 1);// 今天+1天
		Date endDate = calendar.getTime();
		ActiveOrder ao = (ActiveOrder) service.load(ActiveOrder.class, id);
		if (id != null) {
			count = service.queryForLong(
					"select count(*) from tb_sign_in where  signDate>= ? and signDate < ? and memberSign=?",
					ymd.format(ao.getOrderStartTime()), ymd.format(endDate), getMobileUser().getId());
		}
		return count;
	}

	/**
	 * 挑战成绩提交
	 */
	public void submitWeight() {
		Double weight = new Double(request.getParameter("weight"));// 用户填写的体重
		service.saveActiveResult(weight, id);
		ActiveOrder ao = (ActiveOrder) service.load(ActiveOrder.class, id);
		final JSONObject obj = new JSONObject();
		if ("1".equals(ao.getResult().toString())) {
			obj.accumulate("success", true).accumulate("message", "您未达到本次目标，请继续努力！");
		} else {
			obj.accumulate("success", true).accumulate("message", "恭喜你挑战成功！");
		}
		response(obj);

	}
}
