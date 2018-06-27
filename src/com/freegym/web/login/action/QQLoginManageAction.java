package com.freegym.web.login.action;


import java.io.IOException;

import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.freegym.web.BaseBasicAction;
import com.freegym.web.basic.Member;
import com.qq.connect.QQConnectException;
import com.qq.connect.oauth.Oauth;

@Namespace("")
@InterceptorRefs({ @InterceptorRef("customStack") })
@Results({ @Result(name = "success", location = "/home/register4.jsp"), @Result(name = "login", type = "redirect", location = "login.asp"),
		@Result(name = "exist", location = "login", type = "chain") })
public class QQLoginManageAction extends BaseBasicAction {

	private static final long serialVersionUID = 8942409430710869379L;

	private Member member;

	private String verifyCode;

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public String getVerifyCode() {
		return verifyCode;
	}

	public void setVerifyCode(String verifyCode) {
		this.verifyCode = verifyCode;
	}
	
	/**
	 * qq登录重定向
	 */
	public String execute() {
		response.setContentType("text/html;charset=utf-8");
        try {
            response.sendRedirect(new Oauth().getAuthorizeURL(request));
        } catch (QQConnectException e) {
            e.printStackTrace();
        } catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String selected() {
		if (member != null) {
			final String openId = request.getParameter("openId");
			member.setQqId(openId);
			member = (Member) service.saveOrUpdate(member);
			session.setAttribute(LOGIN_MEMBER, member);
		}
		return "login";
	}

	/**
	 * 登录绑定QQ账户
	 * 
	 */
	public void selected1() {
		if (!verifyCode.equals(session.getAttribute(VERIFY_CODE))) {
			response("verifyCodeError");
		} else {
			final Member user = service.login(member.getEmail(), member.getPassword());
			if (user != null) {
				final String openId = request.getParameter("openId");
				user.setQqId(openId);
				service.saveOrUpdate(user);
				session.setAttribute(LOGIN_MEMBER, user);
				response("ok");
			} else {
				response("noexist");
			}
		}
	}
}
