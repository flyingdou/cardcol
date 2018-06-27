package com.freegym.web.basic.action;

import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.freegym.web.BaseBasicAction;
import com.freegym.web.basic.Member;

@Namespace("")
@InterceptorRefs({ @InterceptorRef("foreStack") })
@Results({ @Result(name = "success", location = "/basic/location.jsp") })
public class LocatManageAction extends BaseBasicAction {

	private static final long serialVersionUID = -2297129011587931307L;

	private Member member;

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	@Override
	public String execute() {
		member = (Member) service.load(Member.class, toMember().getId());
		return SUCCESS;
	}

	public String save() {
		final Member m = (Member) service.load(Member.class, id);
		m.setLongitude(member.getLongitude());
		m.setLatitude(member.getLatitude());
		m.setRadius(member.getRadius());
		member = (Member) service.saveOrUpdate(m);
		session.setAttribute(LOGIN_MEMBER, m);
		return SUCCESS;
	}
}
