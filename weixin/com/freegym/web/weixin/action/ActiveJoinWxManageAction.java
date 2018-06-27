package com.freegym.web.weixin.action;

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

import net.sf.json.JSONObject;

@Namespace("")
@InterceptorRefs({ @InterceptorRef("customStack") })
@Results({ @Result(name = "login", location = "/WX/login.jsp"),
		@Result(name = "success", location = "/WX/my_challenge.jsp") })
public class ActiveJoinWxManageAction extends BasicJsonAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5567628859021580202L;
	private Member member;

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	@Override
	public String execute() {
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
