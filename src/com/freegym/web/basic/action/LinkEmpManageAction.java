package com.freegym.web.basic.action;

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
import com.sanmen.web.core.common.MD5;

@Namespace("")
@InterceptorRefs({ @InterceptorRef("foreStack") })
@Results({ @Result(name = "success", location = "/basic/link.jsp") })
public class LinkEmpManageAction extends BaseBasicAction {

	private static final long serialVersionUID = 8942409430710869379L;

	private Member member;

	private String mobile, code, email;

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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String execute() {
		session.setAttribute("spath", 7);
		member = toMember();
		return SUCCESS;
	}

	public void save() {
		try {
//			if (isExist(1, member.getTell()))
//				throw new LogicException("您输入的联系电话已经存在，请重新输入！");
			Member m = (Member) service.load(Member.class, toMember().getId());
			m.setProvince(member.getProvince());
			m.setCity(member.getCity());
			m.setCounty(member.getCounty());
			m.setAddress(member.getAddress());
			m.setTell(member.getTell());
			m.setQq(member.getQq());
			m = (Member) service.saveOrUpdate(m);
			session.setAttribute(LOGIN_MEMBER, m);
			response("ok");
		} catch (Exception e) {
			response(e.getMessage());
		}
	}

	@SuppressWarnings("unused")
	private boolean isExist(final int type, final String value) {
		final List<?> list = service.findObjectBySql("from Member m where " + (type == 0 ? "m.email" : "m.tell") + " = ? and m.id <> ?", value, toMember()
				.getId());
		return list.size() > 0;
	}

	public void modifyEmail() {
		try {
			final Member m = service.findMemberByMail(email);
			if (m == null) {
				Member m1 = toMember();
				m1.setTell(member.getTell());
				m1.setEmail(email);
				m1.setAddress(member.getAddress());
				m1.setProvince(member.getProvince());
				m1.setPostal(member.getPostal());
				m1.setCity(member.getCity());
				m1.setCounty(member.getCounty());
				m1.setMobilephone(member.getMobilephone());
				m1.setQq(member.getQq());
				m1.setWechatID(member.getWechatID());
				m1.setEmailValid(MEMBER_VALIDATE_UNVALID);
				final String md5Str = MD5.MD5Encode(m1.getEmail());
				m1.setVerifyCode(md5Str);
				m1 = (Member) service.saveOrUpdate(m1);
				session.setAttribute(LOGIN_MEMBER, m1);
				final Map<String, Object> root = new HashMap<String, Object>();
				final String link = "http://www.cardcol.com/email.asp?email=" + m1.getEmail() + "&auth=" + md5Str;
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
