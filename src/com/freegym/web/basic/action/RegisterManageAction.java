package com.freegym.web.basic.action;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.freegym.web.BaseBasicAction;
import com.freegym.web.basic.Member;
import com.freegym.web.utils.EasyUtils;
import com.sanmen.web.core.common.LogicException;

import net.sf.json.JSONObject;

@Namespace("")
@InterceptorRefs({ @InterceptorRef("foreStack") })
@Results({ @Result(name = "success", location = "/home/register1.jsp"),
		@Result(name = "success1", location = "login.asp", type = "redirect") })
public class RegisterManageAction extends BaseBasicAction {

	private static final long serialVersionUID = -3769475961884283745L;

	private Member member;

	private String mobilephone;
	private String code;
	private String flag;
	private String type;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getMobilephone() {
		return mobilephone;
	}

	public void setMobilephone(String mobilephone) {
		this.mobilephone = mobilephone;
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public String execute() {
		if (session.getAttribute("position") != null)
			session.removeAttribute("position");
		return SUCCESS;
	}

	public void checkMail() {
		final Member m = service.findMemberByMail(member.getEmail());
		if (m != null) {
			response("EXIST");
		} else {
			response("OK");
		}
	}

	public void checkName() {
		try {
			final String nick = new String(member.getNick().getBytes("iso-8859-1"), "UTF-8");
			final List<?> lists = service.findObjectBySql(
					"from Member m where m.nick = ? or m.email = ? or m.mobilephone = ?", nick, nick, nick);
			if (lists.size() <= 0) {
				response("OK");
			} else {
				response("EXIST");
			}
		} catch (UnsupportedEncodingException e) {
			log.error("error", e);
		}
	}

	/*
	 * public String save() { try { if (member != null) { String nick =
	 * member.getNick(); final List<?> lists = service.findObjectBySql(
	 * "from Member m where m.nick = ? or m.email = ? or m.mobilephone = ?",
	 * nick, nick, nick); if (lists.size() > 0) setMessage("exist"); Member m =
	 * service.findMemberByMail(member.getEmail()); if (m != null) {
	 * setMessage("exist"); } else { member.setName(nick); member =
	 * service.register(member); session.setAttribute(LOGIN_MEMBER, member);
	 * setMessage("OK"); } } } catch (Exception e) { log.error("error", e); }
	 * return "success1"; }
	 */

	/*
	 * public void mobile(){ try { final List<?> lists =
	 * service.findObjectBySql("from Member m where  m.mobilephone = ?",
	 * mobilephone); if (lists.size() == 0){ setMessage("exist"); final
	 * JSONObject ret = new JSONObject(); ret.accumulate("success",
	 * true).accumulate("message", "OK"); response(ret); } }catch (Exception e)
	 * { log.error("error", e); response(e); }
	 * 
	 * }
	 */

	/**
	 * 获取手机验证码
	 */
	public void MobileCode() {
		try {
			if (flag != null && "0".equals(flag)) {
				final Long count = service.queryForLong(
						"select count(*) from tb_member where mobilephone = ? and mobile_valid = '1'", mobilephone);
				if (count > 0) {
					sendSmsValidate(mobilephone, type);
				} else {
					throw new LogicException("当前手机号码未绑定至用户！");
				}
			} else if (flag != null && "1".equals(flag)) {
				sendSmsValidate(mobilephone, type);
			}
			final JSONObject ret = new JSONObject();
			ret.accumulate("success", true).accumulate("message", "OK");
			response(ret);
		} catch (Exception e) {
			log.error("error", e);
			response(e);
		}
	}

	public String savemember() {
		try {
			System.out.println(member);
			if (isRightful(member.getMobilephone(), code)) {
				final List<?> lists = service.findObjectBySql("from Member m where m.mobilephone = ? ",
						member.getMobilephone());
				if (lists.size() > 0)
					setMessage("exist");
				else {
					member.setName(member.getMobilephone());
					member.setMobileValid("1");
					member = service.register(member);
					session.setAttribute(LOGIN_MEMBER, member);
					setMessage("OK");
				}
			}
		} catch (Exception e) {
			log.error("error", e);
		}
		return "success1";
	}

	public String save() {
		try {
			if (member != null) {
				String nick = member.getNick();
				final List<?> lists = service.findObjectBySql(
						"from Member m where m.nick = ? or m.email = ? or m.mobilephone = ?", nick, nick, nick);
				if (lists.size() > 0)
					setMessage("exist");
				Member m = null;
				if (member.getNick() != null && StringUtils.isNotEmpty(member.getNick())) {
					m = service.findMemberByMail(member.getNick());
				}
				if (member.getEmail() != null && StringUtils.isNotEmpty(member.getEmail())) {
					m = service.findMemberByMail(member.getEmail());
				}
				if (member.getMobilephone() != null && StringUtils.isNotEmpty(member.getMobilephone())) {
					m = service.findMemberByMail(member.getMobilephone());
				}
				if (m != null) {
					setMessage("exist");
				} else {
					member.setName(nick);
					member.setPassword(EasyUtils.MD5(StringUtils.trim(member.getPassword().split(",")[1])));
					member = service.register(member);
					session.setAttribute(LOGIN_MEMBER, member);
					setMessage("OK");
				}
			}
		} catch (Exception e) {
			log.error("error", e);
		}
		return "success1";
	}
}
