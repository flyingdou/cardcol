package com.freegym.web.basic.action;


import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.cardcol.web.utils.Base64Util;
import com.freegym.web.BaseBasicAction;
@Namespace("")
@InterceptorRefs({ @InterceptorRef("foreStack") })
@Results({ @Result(name = "success", location = "/basic/code.jsp") })
public class TwocodeManageAction extends BaseBasicAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("static-access")
	@Override
	public String execute() {
		session.setAttribute("spath", 7);
		try {
			String mid =  Base64Util.encode(toMember().getId().toString());  
			// 内网
			/*QRCodeUtil.encode("http://156q1j2075.iask.in:88/cardcolv3/share45/share45.html?app=ecatoon&id=" + mid,"",this.webPath +"picture",true,toMember().getId());*/
			// 外网 
			QRCodeUtil.encode("http://www.ecartoon.com.cn/share45/share45.html?app=ecatoon&id=" + mid,"",this.webPath +"picture",true,toMember().getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		request.setAttribute("member", toMember());
		return SUCCESS;
	}
	
}
