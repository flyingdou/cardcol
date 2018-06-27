package com.freegym.web.wap.action;

import java.util.Iterator;
import java.util.List;

import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.freegym.web.basic.Member;
import com.freegym.web.mobile.action.MDietManageAction;
import com.freegym.web.plan.Diet;

@Namespace("")
@InterceptorRefs({ @InterceptorRef("customStack") })
public class WDietManageAction extends MDietManageAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5691896815900723723L;
	private String _planDate;
	
	public String get_planDate() {
		return _planDate;
	}


	public void set_planDate(String _planDate) {
		this._planDate = _planDate;
	}


	public void listDiet() {
		try{
			Member mu = (Member) service.load(Member.class,id);
			final List<?> list;
			if (mu.getRole().equals("S")) {
				list = service
						.findObjectBySql(
								"from Diet d where (d.member = ? or d.member in (select m.id from Member m where m.coach.id = ?)) and d.planDate = ? order by d.planDate, d.startTime",
								id, id, _planDate);
			} else {
				list = service.findObjectBySql("from Diet d where d.member = ? and d.planDate = ? order by d.planDate, d.startTime", id, _planDate);
			}
			final JSONArray jarr = new JSONArray();
			for (final Iterator<?> it = list.iterator(); it.hasNext();) {
				final Diet d = (Diet) it.next();
				final Member m = (Member) service.load(Member.class, d.getMember());
				final JSONObject obj = new JSONObject();
				obj.accumulate("id", d.getId()).accumulate("member", getMemberJson(m)).accumulate("mealNum", getString(d.getMealNum()))
						.accumulate("planDate", d.getPlanDate()).accumulate("startTime", d.getStartTime()).accumulate("endTime", d.getEndTime())
						.accumulate("details", generatorDetailJson(d.getDetails()));
				jarr.add(obj);
			}
			final JSONObject ret = new JSONObject();
			ret.accumulate("success", true).accumulate("items", jarr);
			response(ret);
		}catch(Exception e){
			log.error("error", e);
			response(e);
		}
	}
}
