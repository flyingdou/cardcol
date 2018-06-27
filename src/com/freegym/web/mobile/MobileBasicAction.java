package com.freegym.web.mobile;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import net.sf.json.JSONObject;

import com.sanmen.web.core.bean.BaseMember;

public abstract class MobileBasicAction extends AbstractJsonAction {

	private static final long serialVersionUID = -4162005893592945192L;

	private String field;

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	@Override
	protected BaseMember getLoginMember() {
		return getMobileUser();
	}

	public void setAttribute() throws UnsupportedEncodingException {
		String decode = URLDecoder.decode(jsons, "UTF-8");
		session.setAttribute(field, decode);
		JSONObject ret = new JSONObject();
		ret.accumulate("success", true);
		response(ret);
	}

	public void getAttribute() {
		JSONObject ret = new JSONObject();
		String[] param = field.split(",");
		ret.accumulate("success", true);
		for (int i = 0; i < param.length; i++) {
			ret.accumulate(param[i], session.getAttribute(param[i]));
		}
		response(ret);
	}
	
	public static boolean isNumeric(String str){
		for (int i = str.length();--i>=0;){   
			if (!Character.isDigit(str.charAt(i))){
				return false;
			}
		}
		return true;
	 }
}
