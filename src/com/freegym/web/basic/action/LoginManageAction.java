package com.freegym.web.basic.action;

import java.util.Enumeration;

import javax.servlet.http.Cookie;

import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import weibo4j.Oauth;
import weibo4j.model.WeiboException;

import com.freegym.web.BaseBasicAction;
import com.freegym.web.basic.Member;
import com.sanmen.web.core.common.MD5;

@Namespace("")
@InterceptorRefs({ @InterceptorRef("foreStack") })
@Results({ @Result(name = "successm", location = "member.asp", type = "redirect"), @Result(name = "failure", location = "/home/loginRegist.jsp"),
		@Result(name = "logout", location = "index.asp", type = "redirect"), @Result(name = "successs", location = "coach.asp", type = "redirect"),
		@Result(name = "successe", location = "club.asp", type = "redirect"), @Result(name = "successa", location = "admin.asp", type = "redirect"),
		@Result(name = "unreg", location = "/home/loginRegist.jsp") })
public class LoginManageAction extends BaseBasicAction {

	private static final long serialVersionUID = -3769475961884283745L;

	private String username;
	private String mobilephone;
	private String password;
	private Member member;

	

	public String execute() {
		if (session.getAttribute("position") != null)
			session.removeAttribute("position");
		try {
			Oauth oauth = new Oauth();
			String[] args = new String[] { "code", "friendships_groups_read,friendships_groups_write,statuses_to_me_read,follow_app_official_microblog" };
			request.setAttribute("sinaurl", oauth.authorize("code", args[0], args[1]));
		} catch (WeiboException e) {
			log.error("error", e);
		}
		member = toMember();
		if (member == null) {
			return "failure";
		}
		String role = member.getRole().toLowerCase();
		role = role.equalsIgnoreCase("i") ? "e" : role;
		return "success" + role;
	}

	public String logout() {
		Cookie[] cookies = request.getCookies();
		for (Cookie cookie : cookies) {
			cookie.setMaxAge(0); 
            cookie.setPath("/"); 
            response.addCookie(cookie);
        }
		for (final Enumeration<String> names = session.getAttributeNames(); names.hasMoreElements();){
			final String name = names.nextElement();
			session.removeAttribute(name);
		}
		return "logout";
	}

	public void check() {
		String msg = "";
		try {
			final Member user = service.login(mobilephone, password);
			if (user == null) {
				msg = "errorUserPassword";
			} else {
				msg = "success";
				session.setAttribute(LOGIN_MEMBER, user);
			}
		} catch (Exception e) {
			msg = e.toString();
			log.error("error", e);
		} finally {
			response(msg);
		}
	}

	public static void main(String[] args) {
		System.out.println(MD5.MD5Encode("admin123456"));
	}
	
	
	
	/**
	 * setter && getter
	 */
	public String getMobilephone() {
		return mobilephone;
	}

	public void setMobilephone(String mobilephone) {
		this.mobilephone = mobilephone;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}
	
	
}
