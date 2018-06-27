package com.freegym.web.basic.action;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;

import com.freegym.web.BaseBasicAction;

@Namespace("")
@InterceptorRefs({ @InterceptorRef("customStack") })
public class FooterManageAction extends BaseBasicAction {

	private static final long serialVersionUID = -3769475961884283745L;


	@Action(value="law",results={@Result(name= "success",location = "/footer/law.jsp")})
	public String execute() {
		session.setAttribute("spath", 5);
		return "success";
	}

	@Action(value="copy",results={@Result(name= "success",location = "/footer/copy.jsp")})
	public String copy() {
		return SUCCESS;
	}

	@Action(value="server",results={@Result(name= "success",location = "/footer/service.jsp")})
	public String server() {
		return SUCCESS;
	}

	@Action(value="declare",results={@Result(name= "success",location = "/footer/declare.jsp")})
	public String declare() {
		return SUCCESS;
	}

	@Action(value="integrity",results={@Result(name= "success",location = "/footer/integrity.jsp")})
	public String integrity() {
		return SUCCESS;
	}

}
