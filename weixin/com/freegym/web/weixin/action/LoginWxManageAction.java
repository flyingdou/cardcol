package com.freegym.web.weixin.action;

import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.freegym.web.BaseBasicAction;
import com.freegym.web.basic.Member;

@Namespace("")
@InterceptorRefs({ @InterceptorRef("customStack") })
@Results({@Result(name="success",location="/WX/login.jsp" )})
public class LoginWxManageAction extends BaseBasicAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1681342915116338322L;

	

	public String getMobilephone() {
		return mobilephone;
	}

	public void setMobilephone(String mobilephone) {
		this.mobilephone = mobilephone;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String mobilephone, password;
	
	public String execute(){
		return SUCCESS;
	}
	public void dialog() {
		String msg = "";
		try {
			Member member = service.login(mobilephone, password);
			if (member == null) {
				msg = "errorUserPassword";
			} else {
				msg = "success";
				session.setAttribute("member", member);
			}
		} catch (Exception e) {
			msg = e.toString();
			log.error("error", e);
		} finally {
			response(msg);
		}
	}
}
