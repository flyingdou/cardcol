package com.freegym.web.basic.action;

import net.sf.json.JSONObject;

import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.freegym.web.BaseBasicAction;
import com.freegym.web.basic.Member;
import com.sanmen.web.core.utils.StringUtils;

@Namespace("")
@InterceptorRefs({ @InterceptorRef("customStack") })
@Results({ @Result(name = "success", location = "/home/reset.jsp") })
public class ResetManageAction extends BaseBasicAction {

	private static final long serialVersionUID = 8942409430710869379L;

	private String email, verify, password;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getVerify() {
		return verify;
	}

	public void setVerify(String verify) {
		this.verify = verify;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String execute() {
		try {
			final Member m = (Member) service.load(Member.class, id);
			if (m == null) setMessage("请确认您的链接地址为合法的链接地址!");
			else if (m.getEmail().equals(email)) setMessage("请确认您的链接地址的来源是您的原始邮件中的！");
			else if (m.getVerifyCode().equals(verify)) setMessage("您的链接地址已经失效，请重新找回您的密码！");
		} catch (Exception e) {
			log.error("重置密码出错", e);
			setMessage(e.getMessage());
		}
		return SUCCESS;
	}

	public void save() {
		final JSONObject ret = new JSONObject();
		try {
			final Member m = (Member) service.load(Member.class, id);
			if (m == null) setMessage("请确认您的链接地址为合法的链接地址!");
			else if (m.getEmail().equals(email)) setMessage("请确认您的链接地址的来源是您的原始邮件中的！");
			else if (m.getVerifyCode().equals(verify)) setMessage("您的链接地址已经失效，请重新找回您的密码！");
			m.setPassword(password);
			m.setVerifyCode(StringUtils.getUUID());
			service.saveOrUpdate(m);
			ret.accumulate("success", true).accumulate("message", "OK");
			response(ret);
		} catch (Exception e) {
			log.error("重置密码出错", e);
			response(e);
		}
	}

}
