package com.freegym.web.mobile.action;

import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;
import com.freegym.web.mobile.BasicJsonAction;

@Namespace("")
@InterceptorRefs({ @InterceptorRef("mobileStack") })
public class MCoachManageAction extends BasicJsonAction {

	private static final long serialVersionUID = -4900715126840183203L;


}
