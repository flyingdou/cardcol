package com.freegym.web.config.action;

import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.freegym.web.config.Setting;

@Namespace("")
@InterceptorRefs({ @InterceptorRef("foreStack") })
@Results({ @Result(name = "success", location = "/member/done.jsp") })
public class DoneManageAction extends ConfigBasicAction {

	private static final long serialVersionUID = -3769475961884283745L;

	public String execute() {
		session.setAttribute("spath", 8);
		if (setting.getMember() == null) setting.setMember(toMember().getId());
		setting = (Setting) service.saveOrUpdate(setting);
		return SUCCESS;
	}

}
