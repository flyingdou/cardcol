package com.freegym.web.mobile.action;

import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;

import com.freegym.web.mobile.CourseJsonAction;

@Namespace("")
@InterceptorRefs({ @InterceptorRef("mobileStack") })
public class MClubManageAction extends CourseJsonAction {

	private static final long serialVersionUID = -4900715126840183203L;

	private String keyword;

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
}
