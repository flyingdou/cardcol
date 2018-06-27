package com.freegym.web.config.action;

import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.freegym.web.BaseBasicAction;
import com.freegym.web.basic.Member;

@Namespace("")
@InterceptorRefs({ @InterceptorRef("foreStack") })
@Results({ @Result(name = "successe", location = "factory.asp", type = "redirect"),
		@Result(name = "successm", location = "target.asp", type = "redirect", params = {"autoGen", "${autoGen}"}),
		@Result(name = "successs", location = "style.asp", type = "redirect") })
public class SettingManageAction extends BaseBasicAction {

	private static final long serialVersionUID = -2236664686824111186L;

	private Integer autoGen;
	
	public Integer getAutoGen() {
		return autoGen;
	}

	public void setAutoGen(Integer autoGen) {
		this.autoGen = autoGen;
	}

	@Override
	public String execute() {
		session.setAttribute("spath", 8);
		Member m = toMember();
		final String role = m.getRole().equalsIgnoreCase("i") ? "e" : m.getRole().toLowerCase();
		return "success" + role;
	}

}
