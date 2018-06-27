package com.freegym.web.wap.action;

import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.Cookie;

import net.sf.json.JSONObject;

import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;

import com.freegym.web.active.TrainRecord;
import com.freegym.web.basic.Member;
import com.freegym.web.config.Setting;
import com.freegym.web.mobile.action.MloginManageAction;

@Namespace("")
@InterceptorRefs({ @InterceptorRef("customStack") })
public class WLoginManageAction extends MloginManageAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4514951214395420370L;

	@Override
	public String execute() {
		final Member member = service.login(username, password);
		final JSONObject ret = new JSONObject();
		if (member == null) {
			ret.accumulate("success", false).accumulate("message", "您输入的账户及密码不正确，请重新输入！");
			response(ret);
			return null;
		} else {
			ret.accumulate("success", true).accumulate("member", getMemberJson(member));
			session.setAttribute(LOGIN_MEMBER, member);
			final Setting set = service.loadSetting(member.getId());
			List<?> records = service.findObjectBySql("from TrainRecord r where r.partake.id =" + member.getId() + " order by r.doneDate desc");
			TrainRecord lastRecord = new TrainRecord();
			if (null != records && 0 != records.size()) {
				lastRecord = (TrainRecord) records.get(0);
				ret.accumulate("weight", null == lastRecord.getWeight() ? set.getWeight() : lastRecord.getWeight())
						.accumulate("height", null == lastRecord.getHeight() ? set.getHeight() : lastRecord.getHeight())
						.accumulate("waist", null == lastRecord.getWaist() ? set.getWaistline() : lastRecord.getWaist()).accumulate("hip", lastRecord.getHip());
			} else {
				ret.accumulate("weight", set.getWeight()).accumulate("height", set.getHeight()).accumulate("waist", set.getWaistline()).accumulate("hip", null);
			}
			session.setAttribute("member", getMemberJson(member));
			
			JSONObject obj = new JSONObject();
			session.setAttribute("cityData", obj.accumulate("city", member.getCity()));
			response(ret);
		}
		return null;
	}
	
	/**
	 * 登出
	 * @return
	 */
	public void logout() {
		JSONObject ret = new JSONObject();
		Cookie[] cookies = request.getCookies();
		for (Cookie cookie : cookies) {
			cookie.setMaxAge(0); 
            cookie.setPath("/"); 
            response.addCookie(cookie);
        }
		for (final Enumeration<String> names = session.getAttributeNames(); names.hasMoreElements();){
			final String name = names.nextElement();
			session.removeAttribute(name);
		}
		ret.accumulate("success", true).accumulate("message", "OK");
		response(ret);
	}

}
