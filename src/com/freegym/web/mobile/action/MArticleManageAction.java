package com.freegym.web.mobile.action;

import java.util.List;
import java.util.Map;

import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;

import com.freegym.web.mobile.BasicJsonAction;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Namespace("")
@InterceptorRefs({ @InterceptorRef("mobileStack") })
public class MArticleManageAction extends BasicJsonAction {

	private static final long serialVersionUID = -1836573287443465809L;

	private String[] code;

	public String[] getCode() {
		return code;
	}

	public void setCode(String[] code) {
		this.code = code;
	}

	protected void list() {
		final List<Map<String, Object>> recomms = service.findRecommendBySectorCode(code);
		final JSONArray jarr = JSONArray.fromObject(recomms);
		final JSONObject obj = new JSONObject();
		obj.accumulate("success", true).accumulate("items", jarr);
		response(obj);
	}
}
