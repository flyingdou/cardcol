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
@Results({ @Result(name = "success", location = "/basic/password.jsp") })
public class PasswordManageAction extends BaseBasicAction {

	private static final long serialVersionUID = 8942409430710869379L;

	private Member member;

	private String password;

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String execute() {
		session.setAttribute("spath", 7);
		member = toMember();
		return SUCCESS;
	}

	public void save() {
		Member m = toMember();
		m.setPassword(member.getPassword());
		m = (Member) service.saveOrUpdate(m);
		session.setAttribute(LOGIN_MEMBER, m);
	}

	public void check() {
		String msg = "";
		member = toMember();
		if (member.getPassword().equals(password)) {
			msg = "ok";
		} else {
			msg = "error";
		}
		response(msg);
	}

}
