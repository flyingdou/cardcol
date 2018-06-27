package com.freegym.web.basic.action;

import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.freegym.web.BaseBasicAction;

@Namespace("")
@InterceptorRefs({ @InterceptorRef("foreStack") })
@Results({ @Result(name = "success", location = "/basic/index.jsp") })
public class AdminManageAction extends BaseBasicAction {

	private static final long serialVersionUID = -3769475961884283745L;

	public String execute() {
		session.setAttribute("spath", 0);
		return SUCCESS;
	}

}
