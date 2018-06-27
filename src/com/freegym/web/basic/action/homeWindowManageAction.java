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
@Results({ @Result(name = "successm", location = "member.asp", type = "redirect"),
	@Result(name = "successs", location = "coach.asp", type = "redirect"),
	@Result(name = "successe", location = "club.asp", type = "redirect")})
public class homeWindowManageAction extends BaseBasicAction {

	private static final long serialVersionUID = -3769475961884283745L;
	private Member member;

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public String execute() {
		member = (Member) service.load(Member.class,member.getId());
		session.setAttribute("toMember", member);
		return "success" + member.getRole().toLowerCase();
	}
}
