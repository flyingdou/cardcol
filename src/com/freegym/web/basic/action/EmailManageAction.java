package com.freegym.web.basic.action;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.freegym.web.BaseBasicAction;
import com.freegym.web.basic.Member;
import com.sanmen.web.core.common.LogicException;
import com.sanmen.web.core.common.MD5;

@Namespace("")
@InterceptorRefs({ @InterceptorRef("customStack") })
@Results({ @Result(name = "success", location = "/basic/email.jsp"), @Result(name = "verify", location = "/basic/emailverify.jsp") })
public class EmailManageAction extends BaseBasicAction {

	private static final long serialVersionUID = 8942409430710869379L;

	private Member member;

	private String email;

	private String auth;

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAuth() {
		return auth;
	}

	public void setAuth(String auth) {
		this.auth = auth;
	}

	public void validEmail() {
		try {
			if (isExist(email))
				throw new LogicException("您输入的邮件地址已经存在，请重新输入！");
			final Member m = toMember();
			final String md5Str = MD5.MD5Encode(email);
			m.setVerifyCode(md5Str);
			service.saveOrUpdate(m);
			Map<String, Object> root = new HashMap<String, Object>();
			root.put("username", m.getNick());
			String urlPath = request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
			final String link = "http://" + urlPath + "/email.asp?id=" + id + "&email=" + email + "&auth=" + md5Str;
			root.put("authUrl", link);
			String str = service.processTemplateIntoString("validate-email.ftl", root);
			service.sendMail(email, "卡库网“邮箱验证”确认邮件", str);
			response("OK");
		} catch (Exception e) {
			response(e.getMessage());
		}
	}

	public void save() {
		try {
			if (isExist(member.getEmail()))
				throw new LogicException("您输入的邮箱已经存在，请重新输入！");
			Member m = toMember();
			m.setEmail(member.getEmail());
			m = (Member) service.saveOrUpdate(m);
			session.setAttribute(LOGIN_MEMBER, m);
			response("ok");
		} catch (Exception e) {
			response(e.getMessage());
		}
	}

	private boolean isExist(final String value) {
		final List<?> list = service.findObjectBySql("from Member m where m.email = ? and m.id <> ?", value, toMember().getId());
		return list.size() > 0;
	}

	public String execute() {
		try {
			if (email != null && !"".equals(email) && auth != null && !"".equals(auth)) {
				final List<?> list = service.findObjectBySql("from Member m where m.id = ? and m.verifyCode = ?", id, auth);
				if (list.size() > 0) {
					final Member m = (Member) list.get(0);
					m.setEmail(email);
					m.setEmailValid(MEMBER_VALIDATE_SUCCESS);
					m.setVerifyCode(MD5.MD5Encode(new Date().toString()));
					service.saveOrUpdate(m);
					session.setAttribute(LOGIN_MEMBER, m);
					request.setAttribute("member", m);
					setMessage("您的邮箱已经成功验证！");
				} else {
					setMessage("您的链接已经失效，如果需要再次确认，请重新验证！");
				}
			}
			member = toMember();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public void modifyEmail() {
		try {
			final Member m = service.findMemberByMail(email);
			if (m == null) {
				Member m1 = toMember();
				final String md5Str = MD5.MD5Encode(email);
				m1.setVerifyCode(md5Str);

				m1 = (Member) service.saveOrUpdate(m1);
				session.setAttribute(LOGIN_MEMBER, m1);
				final Map<String, Object> root = new HashMap<String, Object>();
				final String link = "http://www.cardcol.com/email.asp?id=" + m1.getId() + "&email=" + email + "&auth=" + md5Str;
				root.put("username", m1.getNick());
				root.put("authUrl", link);
				final String str = service.processTemplateIntoString("validate-email.ftl", root);
				service.sendMail(email, "卡库网“邮箱验证”确认邮件", str);
				response("ok");
			} else {
				response("exist");
			}
		} catch (Exception e) {
			response(e.getMessage());
		}
	}
}
