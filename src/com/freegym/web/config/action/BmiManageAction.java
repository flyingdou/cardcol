package com.freegym.web.config.action;

import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

@Namespace("")
@InterceptorRefs({ @InterceptorRef("foreStack") })
@Results({ @Result(name = "success", location = "/member/bmi.jsp") })
public class BmiManageAction extends ConfigBasicAction {

	private static final long serialVersionUID = -3769475961884283745L;

	public String execute() {
		if (setting.getBmiHigh() == null || setting.getBmiHigh() <= 0) setting.setBmiHigh(85);
		if (setting.getBmiLow() == null || setting.getBmiLow() <= 0) setting.setBmiLow(50);
		session.setAttribute("spath", 8);
		return SUCCESS;
	}

}
