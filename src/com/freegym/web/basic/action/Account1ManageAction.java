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
@Results({ @Result(name = "success", location = "/basic/account.jsp"),
		@Result(name = "successimage", location = "/basic/account_image.jsp"),
		@Result(name = "successlink", location = "/basic/account_link.jsp"),
		@Result(name = "successpass", location = "/basic/account_pass.jsp"),
		@Result(name = "successintegral", location = "/basic/account_integral.jsp") })
public class Account1ManageAction extends BaseBasicAction {

	private static final long serialVersionUID = -3769475961884283745L;

	private Member member;

	private String code;

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String execute() {
		session.setAttribute("spath", 5);
		if (member != null) {
			save();
		}
		member = (Member) service.load(Member.class, toMember().getId());
		if (code == null || "".equals(code)) {
			// request.setAttribute("provinces", service.getProvincesMap());
			return SUCCESS;
		}
		return "success" + code;
	}

	public String save() {
		try {
			if (file != null && file.getFile() != null)
				member.setImage(this.saveFile("picture"));
			member.setCoach(toMember().getCoach());
			member = (Member) service.saveOrUpdate(member);
			session.setAttribute(LOGIN_MEMBER, member);
		} catch (Exception e) {
			log.error("error", e);
		}
		return SUCCESS;
	}

}
