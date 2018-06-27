package com.freegym.web.login.action;

import java.io.IOException;
import java.util.Enumeration;
import java.util.List;

import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import weibo4j.Oauth;
import weibo4j.model.WeiboException;

import com.freegym.web.BaseBasicAction;
import com.freegym.web.basic.Member;

@Namespace("")
@InterceptorRefs({ @InterceptorRef("customStack") })
@Results({ @Result(name = "success", location = "/home/register4.jsp"), @Result(name = "login", type = "redirect", location = "login.asp") })
public class SinaCallbackManageAction extends BaseBasicAction {

	private static final long serialVersionUID = 3594508026563617293L;

	private String code;

	private Member member;

	private String openId;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	@Override
	public String execute() throws IOException, WeiboException {
		for (Enumeration<String> names = request.getParameterNames(); names.hasMoreElements();) {
			String name = names.nextElement();
			System.out.println(name + " = " + request.getParameter(name));
		}
		String errorCode = request.getParameter("error_code");
		if (errorCode != null && "21330".equals(errorCode))
			return "login";
		final Oauth oauth = new Oauth();
		openId = oauth.getAccessTokenByCode(code).getAccessToken();
		final List<?> list = service.findObjectBySql("from Member m where m.sinaId = ?", openId);
		if (list.size() > 0) {
			session.setAttribute(LOGIN_MEMBER, list.get(0));
			return "login";
		}
		request.setAttribute("type", "sina");
		return SUCCESS;
	}
}
