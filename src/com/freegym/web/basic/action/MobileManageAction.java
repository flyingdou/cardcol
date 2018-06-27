package com.freegym.web.basic.action;

import java.util.List;

import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.freegym.web.BaseBasicAction;
import com.freegym.web.basic.Member;
import com.sanmen.web.core.common.LogicException;

@Namespace("")
@InterceptorRefs({ @InterceptorRef("foreStack") })
@Results({ @Result(name = "success", location = "/basic/mobile.jsp"),
		@Result(name = "mobile1", location = "/basic/mobile1.jsp"),
		@Result(name = "mobile2", location = "/basic/mobile2.jsp"),
		@Result(name = "mobile3", location = "/basic/mobile3.jsp") })
public class MobileManageAction extends BaseBasicAction {

	private static final long serialVersionUID = 8942409430710869379L;

	private Member member;

	private String mobile, code;

	private String oldMobile;

	private String newMobile;

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

	public String getOldMobile() {
		return oldMobile;
	}

	public void setOldMobile(String oldMobile) {
		this.oldMobile = oldMobile;
	}

	public String getNewMobile() {
		return newMobile;
	}

	public void setNewMobile(String newMobile) {
		this.newMobile = newMobile;
	}

	public String execute() {
		session.setAttribute("spath", 7);
		member = toMember();
		return SUCCESS;
	}

	private boolean isExist(final int type, final String value) {
		final List<?> list = service.findObjectBySql(
				"from Member m where m.mobilephone = ? and m.mobileValid = '1' and m.id <> ?", value, id);
		return list.size() > 0;
	}

	public void getMobileValidCode() {
		if (isExist(1, mobile)) {
			response("您输入的手机号码已经验证过，请重新输入！");
		} else {
			String msg = sendSmsValidate(mobile, "mobile.validate.open", "");
			final Member m = toMember();
			m.setMobilephone(mobile);
			service.saveOrUpdate(m);
			msg = "".equals(msg) ? "OK" : msg;
			response(msg);
		}
	}

	public void save() {
		try {
			if (isExist(1, member.getMobilephone()))
				throw new LogicException("您输入的手机号码已经存在，请重新输入！");
			Member m = toMember();
			m.setMobilephone(member.getMobilephone());
			m.setMobileValid(MEMBER_VALIDATE_UNVALID);
			m = (Member) service.saveOrUpdate(m);
			session.setAttribute(LOGIN_MEMBER, m);
			response("ok");
		} catch (Exception e) {
			response(e.getMessage());
		}
	}

	public void validMobile() {
		if (isRightful(mobile, code))
			response("OK");
		else
			response("ERROR");
	}

	public void saveMobileValid() {
		if (isRightful(mobile, code)) {
			Member m = toMember();
			m.setMobilephone(mobile);
			m.setMobileValid(MEMBER_VALIDATE_SUCCESS);
			service.saveOrUpdate(m);
			response("OK");
		} else {
			response("ERROR");
		}
	}

	public String mobile1() {
		return "mobile1";
	}

	public String mobile2() {
		return "mobile2";
	}

	public String mobile3() {
		return "mobile3";
	}
}
