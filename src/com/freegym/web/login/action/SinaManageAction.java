package com.freegym.web.login.action;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import weibo4j.Oauth;
import weibo4j.model.WeiboException;
import weibo4j.util.BareBonesBrowserLaunch;

import com.freegym.web.BaseBasicAction;
import com.freegym.web.basic.Member;

@Namespace("")
@InterceptorRefs({ @InterceptorRef("customStack") })
@Results({ @Result(name = "success", location = "/home/register4.jsp"), @Result(name = "login", type = "redirect", location = "login.asp") })
public class SinaManageAction extends BaseBasicAction {

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

	public void login() {
		Oauth oauth = new Oauth();
		String[] args = new String[] { "code", "friendships_groups_read,friendships_groups_write,statuses_to_me_read,follow_app_official_microblog" };
		System.out.println(args.length);
		try {
			BareBonesBrowserLaunch.openURL(oauth.authorize("code", args[0], args[1]));
			System.out.println(oauth.authorize("code", args[0], args[1]));
			System.out.print("Hit enter when it's done.[Enter]:");
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			String code = br.readLine();
			log.info("code: " + code);
			try {
				System.out.println(oauth.getAccessTokenByCode(code));
			} catch (WeiboException e) {
				if (401 == e.getStatusCode()) {
					log.info("Unable to get the access token.");
				} else {
					e.printStackTrace();
				}
			}
		} catch (WeiboException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	public String selected() {
		if (member != null) {
			final String openId = request.getParameter("openId");
			member.setSinaId(openId);
			member = (Member) service.saveOrUpdate(member);
			session.setAttribute(LOGIN_MEMBER, member);
		}
		return "login";
	}

	/**
	 * 登录绑定sina账户
	 * 
	 */
	public void selected1() {
		if (!verifyCode.equals(session.getAttribute(VERIFY_CODE))) {
			response("verifyCodeError");
		} else {
			final Member user = service.login(member.getEmail(), member.getPassword());
			if (user != null) {
				final String openId = request.getParameter("openId");
				user.setSinaId(openId);
				service.saveOrUpdate(user);
				session.setAttribute(LOGIN_MEMBER, user);
				response("ok");
			} else {
				response("noexist");
			}
		}
	}

	public static void main(String[] args) {
		Oauth oauth = new Oauth();
		args = new String[] { "code", "friendships_groups_read,friendships_groups_write,statuses_to_me_read,follow_app_official_microblog" };
		System.out.println(args.length);
		try {
			BareBonesBrowserLaunch.openURL(oauth.authorize("code", args[0], args[1]));
			// System.out.println(oauth.authorize("code", args[0], args[1]));
			// System.out.print("Hit enter when it's done.[Enter]:");
			// BufferedReader br = new BufferedReader(new
			// InputStreamReader(System.in));
			// String code = br.readLine();
			// log.info("code: " + code);
			// try {
			// System.out.println(oauth.getAccessTokenByCode(code));
			// } catch (WeiboException e) {
			// if (401 == e.getStatusCode()) {
			// log.info("Unable to get the access token.");
			// } else {
			// e.printStackTrace();
			// }
			// }
		} catch (WeiboException e1) {
			e1.printStackTrace();
		}
	}
}
