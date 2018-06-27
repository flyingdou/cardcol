package com.freegym.web.weixin.action;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.freegym.web.BaseBasicAction;
import com.freegym.web.basic.Member;
import com.sanmen.web.core.common.LogicException;
import com.sanmen.web.core.common.MD5;
import com.sanmen.web.core.utils.StringUtils;

@Namespace("")
@InterceptorRefs({ @InterceptorRef("customStack") })
/*@Results({ @Result(name = "success", location = "/home/find.jsp"), @Result(name = "mobile_validate", location = "/home/mobile_find.jsp"),
		@Result(name = "email_validate", location = "/home/email_find.jsp"), @Result(name = "reset_password", location = "/home/reset_password.jsp"),
		@Result(name = "find_success", location = "/home/find_success.jsp"), @Result(name = "find_fail", location = "/home/find_fail.jsp") })*/
@Results({ @Result(name = "success", location = "/home/find.jsp")})
public class FindPasswordWXManageAction extends BaseBasicAction {

	private static final long serialVersionUID = 8942409430710869379L;

	private String email;

	private String mobile;

	private String code;

	private String password;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String execute() {
		return SUCCESS;
	}

	public String mobileValidate() {
		return "mobile_validate";
	}

	public String emailValidate() {
		return "email_validate";
	}

	public String resetPassword() {
		return "reset_password";
	}

	public String findSuccess() {
		return "find_success";
	}

	public String findFail() {
		return "find_fail";
	}

	public void send() {
		try {
			final Member m = service.findMemberByMail(email);
			if (m == null) {
				throw new LogicException("该邮箱未注册或者输入不正确，请重新输入！");
			} else {
				if (m.getEmailValid() == null) throw new LogicException("该邮箱尚未进行有效性验证，不得用于找回密码！");
				final Map<String, Object> root = new HashMap<String, Object>();
				Random r = new Random();
				int n = r.nextInt(999999);
				m.setVerifyCode(String.valueOf(n));
				service.saveOrUpdate(m);
				root.put("verifycode", String.valueOf(n));
				root.put("href", "www.cardcol.com");
				final String str = service.processTemplateIntoString("find.ftl", root);
				service.sendMail(email, "重置您在卡库网上的密码", str);
				response("OK");
			}
		} catch (Exception e) {
			response(e.getMessage());
		}
	}

	public void validateEmail() {
		final Member m = service.findMemberByMail(email);
		if (m.getVerifyCode().equals(code)) {
			response("OK");
		} else {
			response("ERROR");
		}
	}

	/*public void getMobileValidCode() {
		final String msg = sendSmsValidate(mobile, "mobile.validate.open");
		response(msg);
	}*/

	public void validMobile() {
		if (isRightful(mobile, code)) response("OK");
		else response("ERROR");
	}

	public void isMibileExist() {
		@SuppressWarnings("rawtypes")
		List list = service.findObjectBySql(" from Member where mobilePhone = ? and mobile_valid = 1", mobile);
		if (list != null && list.size() > 0) {
			response("OK");
		} else {
			response("EXIST");
		}
	}

	public void reset() {
		Member member = new Member();
		if (email != null && !StringUtils.isEmpty(email)) {
			member = (Member) service.findObjectBySql(" from Member where email = ? and email_valid = ?", email, '1').iterator().next();
		} else if (mobile != null && !StringUtils.isEmpty(mobile)) {
			member = (Member) service.findObjectBySql(" from Member where mobilePhone = ? and mobile_valid = ?", mobile, '1').iterator().next();
		}
		if (member.getId() != null) {
			member.setPassword(MD5.MD5Encode(password));
			service.saveOrUpdate(member);
			response("OK");
		} else {
			response("ERROR");
		}
	}
	
	public void updatepassword(){
		Member member = new Member();
		
		if (mobile != null) {
			member = (Member) service.findObjectBySql(" from Member where mobilePhone = ? and mobile_valid = ?", mobile, '1').iterator().next();
		}
		
		if (isRightful(mobile, code)) {
			if (member.getId()!= null) {
				member.setPassword(MD5.MD5Encode(password));
				service.saveOrUpdate(member);
				
				try {
					response.sendRedirect("WX/login.jsp");
				} catch (IOException e) {
					response("ERROR"); 
				};
			} else {
				response("ERROR");
			}
		}else{
			try {
				response.sendRedirect("WX/login.jsp");
			} catch (IOException e) {
				response("ERROR"); 
			}
		}
	}
	
	
}
