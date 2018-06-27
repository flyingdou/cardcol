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
@Results({ @Result(name = "success", location = "/basic/card.jsp") })
public class CardManageAction extends BaseBasicAction {

	private static final long serialVersionUID = 8942409430710869379L;

	private Member member;

	private String mobile, code, email;

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String execute() {
		session.setAttribute("spath", 7);
		member = toMember();
		return SUCCESS;
	}

	public String save() {
		try {
			member = toMember();
			if (file != null)
				member.setCardImage(saveFile("picture", member.getCardImage()));
			member = (Member) service.saveOrUpdate(member);
			session.setAttribute(LOGIN_MEMBER, member);
			return SUCCESS;
		} catch (Exception e) {
			log.error("error", e);
			return SUCCESS;
		}
	}

}
