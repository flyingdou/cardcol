package com.freegym.web.system.action;

import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.freegym.web.SystemBasicAction;
import com.freegym.web.basic.Member;

@Namespace("")
@Results({ @Result(name = "success", location = "/manager/system/location.jsp") })
public class LocationManageAction extends SystemBasicAction {

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
		member = (Member) service.load(Member.class, id);
		return SUCCESS;
	}

	@Override
	protected Long executeSave() {
		final Member m = (Member) service.load(Member.class, id);
		m.setLongitude(member.getLongitude());
		m.setLatitude(member.getLatitude());
		service.saveOrUpdate(m);
		return id;
	}

	@Override
	protected String getExclude() {
		return "ticklings,coach,coachMembers,friends,clubs,coachs,members,visitors,setting,times,targets,products,certificates,courses,alwaysAddrs,applys";
	}
}
