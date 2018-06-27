package com.freegym.web.mobile.action;

import java.util.Date;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;

import com.freegym.web.basic.Member;
import com.freegym.web.mobile.BasicJsonAction;
import com.freegym.web.system.Tickling;

@Namespace("")
@InterceptorRefs({ @InterceptorRef("customStack") })
public class MTicklingManageAction extends BasicJsonAction {

	private static final long serialVersionUID = -4900715126840183203L;

	public String execute() {
		try{
			
		}catch(Exception e) {
			
		}
		return null;
	}

	@Override
	protected Long executeSave(JSONArray objs){
		final JSONObject obj = objs.getJSONObject(0);
		Tickling t = new Tickling();
		t.setContent(obj.getString("content"));
		t.setLink(obj.getString("link"));
		t.setMember(new Member(getMobileUser().getId()));
		t.setCreateTime(new Date());
		t.setStatus('0');	
		t = (Tickling) service.saveOrUpdate(t);
		return t.getId();
	}
}
