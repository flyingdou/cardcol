package com.freegym.web.config.action;

import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.freegym.web.basic.Member;
import com.freegym.web.config.Setting;
import com.sanmen.web.core.common.LogicException;

@Namespace("")
@InterceptorRefs({ @InterceptorRef("foreStack") })
@Results({ @Result(name = "success", location = "/member/target.jsp"), @Result(name = "content", location = "/member/target_content.jsp") })
public class TargetManageAction extends ConfigBasicAction {

	private static final long serialVersionUID = -3769475961884283745L;

	public String execute() {
		session.setAttribute("spath", 8);
		Member member = toMember();
		if (setting == null) {
			setting = service.loadSetting(member.getId());
		}
		return SUCCESS;
	}

	public String save() {
		try {
			if (setting.getMember() == null)
				setting.setMember(toMember().getId());
			setting = (Setting) service.saveOrUpdate(setting);
		} catch (LogicException e) {
			log.error("error", e);
		}
		return "content";
	}
}
