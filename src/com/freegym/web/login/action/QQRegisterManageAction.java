package com.freegym.web.login.action;

import java.util.List;

import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.freegym.web.BaseBasicAction;
import com.freegym.web.basic.Member;

@Namespace("")
@InterceptorRefs({ @InterceptorRef("customStack") })
@Results({ @Result(name = "success", location = "/home/register4.jsp"), @Result(name = "login", location = "login.asp", type = "redirect") })
public class QQRegisterManageAction extends BaseBasicAction {

	private static final long serialVersionUID = 20657051492599936L;

	private String openId;

	private Member member;

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public String execute() {
		if (openId != null && !"".equals(openId)) {
			final List<?> list = service.findObjectBySql("from Member m where m.qqId = ?", openId);
			if (list.size() > 0) {
				session.setAttribute(LOGIN_MEMBER, list.get(0));
				return "login";
			}
		}
		member = new Member();
		member.setQqId(openId);
		request.setAttribute("type", "qq");
		return SUCCESS;
	}
}
