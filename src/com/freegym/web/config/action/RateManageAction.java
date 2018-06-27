package com.freegym.web.config.action;

import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.freegym.web.BaseBasicAction;
import com.freegym.web.basic.Member;

@Namespace("")
@InterceptorRefs({ @InterceptorRef("foreStack") })
@Results({ @Result(name = "success", location = "/club/rate.jsp"), 
		@Result(name = "next", location = "open.asp", type = "redirect"),
		@Result(name = "prev", location = "mycourse.asp", type = "redirect") })
public class RateManageAction extends BaseBasicAction {

	private static final long serialVersionUID = -3769475961884283745L;

	private Double rate;

	public Double getRate() {
		return rate;
	}

	public void setRate(Double rate) {
		this.rate = rate;
	}

	public String execute() {
		session.setAttribute("spath", 8);
		Member m = toMember();
		rate = m.getRate();
		return SUCCESS;
	}

	private void save() {
		Member member = (Member) service.load(Member.class, toMember().getId());
		member.setRate(rate);
		member = (Member) service.saveOrUpdate(member);
		session.setAttribute(LOGIN_MEMBER, member);
	}

	public String next() {
		save();
		return "next";
	}

	public String prev() {
		save();
		return "prev";
	}
}
