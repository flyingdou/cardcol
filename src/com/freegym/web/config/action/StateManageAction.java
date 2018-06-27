package com.freegym.web.config.action;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

@Namespace("")
@InterceptorRefs({ @InterceptorRef("foreStack") })
@Results({ @Result(name = "success", location = "/member/state.jsp") })
public class StateManageAction extends ConfigBasicAction {

	private static final long serialVersionUID = -3769475961884283745L;

	public String execute() {
		session.setAttribute("spath", 8);
		List<String> list = new ArrayList<String>();
		if (setting.getDiseaseReport() != null) {
			String[] arrs = setting.getDiseaseReport().split(",");
			for (int i = 0; i < arrs.length; i++)
				list.add(arrs[i].trim());
		}
		request.setAttribute("list", list);
		return SUCCESS;
	}

}
