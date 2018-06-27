package com.freegym.web.basic.action;

import java.io.File;
import java.util.List;

import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.freegym.web.BaseBasicAction;
import com.freegym.web.basic.Member;
import com.freegym.web.config.Setting;

@Namespace("")
@InterceptorRefs({ @InterceptorRef("foreStack") })
@Results({ @Result(name = "success", location = "/basic/account.jsp") })
public class AccountManageAction extends BaseBasicAction {

	private static final long serialVersionUID = 8942409430710869379L;

	private Member member;

	private File upload;

	private String uploadContentType, uploadFileName;

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public File getUpload() {
		return upload;
	}

	public void setUpload(File upload) {
		this.upload = upload;
	}

	public String getUploadContentType() {
		return uploadContentType;
	}

	public void setUploadContentType(String uploadContentType) {
		this.uploadContentType = uploadContentType;
	}

	public String getUploadFileName() {
		return uploadFileName;
	}

	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}

	public String execute() {
		session.setAttribute("spath", 7);
		if (member != null) {
			member = (Member) service.load(Member.class, member.getId());
			session.setAttribute(LOGIN_MEMBER, member);
		} else {
			member = toMember();
			if (member == null)
				return "login";
		}
		if (member.getSex() == null || "".equals(member.getSex())) {
			member.setSex("M");
		}
		return SUCCESS;
	}

	public String save() {
		Member m = toMember();
		final String nick = member.getNick();
		final List<?> lists = service.findObjectBySql("from Member m where (m.nick = ? or m.email = ? or m.mobilephone = ?) and m.id <> ?", nick, nick, nick,
				member.getId());
		if (lists.size() > 0) {
			setMessage("无法保存，您输入的昵称已经存在，请重新输入！");
		} else {
			m.setNick(member.getNick());
			m.setName(member.getName());
			m.setSex(member.getSex());
			m.setBirthday(member.getBirthday());
			m.setDescription(member.getDescription());
			m = (Member) service.saveOrUpdate(m);
			member = m;
			session.setAttribute(LOGIN_MEMBER, m);
		}
		return SUCCESS;
	}

	public void upload() {
		if (upload != null) {
			final String saveFile = "picture/" + this.saveFile("picture", upload, uploadFileName, "");
			final String result = "<script type=\"text/javascript\">window.parent.CKEDITOR.tools.callFunction(2, '" + saveFile + "');</script>";
			response(result);
		}
	}

	public void findWeight() {
		try {
			final Member m = toMember();
			final Setting s = service.loadSetting(m.getId());
			response("{\"success\": true, \"weight\": " + getDouble(s.getWeight()) + "}");
		} catch (Exception e) {
			log.error("error", e);
			response("{\"success\": false, \"message\": \"" + e.getMessage() + "\"}");
		}
	}

	public void saveWeight() {
		try {
			final Double weight = new Double(request.getParameter("weight"));
			final Member m = toMember();
			final Setting s = service.loadSetting(m.getId());
			s.setWeight(weight);
			service.saveOrUpdate(s);
			response("{\"success\": true, \"message\": \"OK\"}");
		} catch (Exception e) {
			log.error("error", e);
			response("{\"success\": false, \"message\": \"" + e.getMessage() + "\"}");
		}
	}
}
