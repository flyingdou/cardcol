package com.freegym.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;
import com.sanmen.web.core.utils.Constant;

public class LoginedInterceptor implements Interceptor {

	private static final long serialVersionUID = 2038692911902117549L;

	private static final String excludeNames = "index,login,logout,verifycode,reg,register,findPassword,validate,home,clublist,coachlist,planlist,content,context,";

	public void destroy() {
	}

	public void init() {
	}

	public String intercept(ActionInvocation invocation) throws Exception {
		ActionContext ic = invocation.getInvocationContext();
		Map<?, ?> map = ic.getSession();
		Object obj = map.get(Constant.LOGIN_MEMBER);
		String name = ic.getName();
		ic.getSession().put("spath", name);
		if (obj == null) {
			if (excludeNames.indexOf(name + ",") == -1){
				HttpServletRequest request = ServletActionContext.getRequest();
				HttpServletResponse response = ServletActionContext.getResponse();
		        if (request.getHeader("x-requested-with") != null && request.getHeader("x-requested-with").equalsIgnoreCase("XMLHttpRequest")) { 
	    			response.sendError(1002);
		        }else{
		        	return "login";
		        }
			}
		}
		return invocation.invoke();
	}
}
