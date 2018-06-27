package com.cardcol.web.order.action;

import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.freegym.web.OrderBasicAction;

@Namespace("")
@InterceptorRefs({ @InterceptorRef("foreStack") })
@Results({ @Result(name = "success", location = "/jsp/order/attendance.jsp") })
public class AttendanceManageAction extends OrderBasicAction {

	private static final long serialVersionUID = -6811995475498907750L;

	private String category;

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	@Override
	public String execute() {
		// 加入查询代码
		return SUCCESS;
	}
}
