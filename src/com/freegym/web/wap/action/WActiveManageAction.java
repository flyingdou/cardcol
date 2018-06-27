package com.freegym.web.wap.action;

import java.util.List;

import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;

import com.freegym.web.active.Active;
import com.freegym.web.mobile.action.MActiveManageAction;
import com.sanmen.web.core.bean.BaseMember;

import net.sf.json.JSONObject;

@Namespace("")
@InterceptorRefs({ @InterceptorRef("customStack") })
public class WActiveManageAction extends MActiveManageAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7682893496137606995L;

	@Override
	protected BaseMember getLoginMember() {
		//return (BaseMember) getService().load(Member.class, 490l);
		return toMember();
	}

	/**
	 * 查询活动详情
	 */
	public void loadActive() {
		Active a = (Active) service.load(Active.class, id);
		final JSONObject obj = new JSONObject();
		obj.accumulate("id", a.getId()).accumulate("member", getMemberJson(a.getCreator())).accumulate("name", getString(a.getName()))
				.accumulate("mode", getString(a.getMode())).accumulate("days", getInteger(a.getDays())).accumulate("teamNum", getInteger(a.getTeamNum()))
				.accumulate("target", getString(a.getTarget())).accumulate("award", getString(a.getAward()))
				.accumulate("institution", getJsonForMember(a.getInstitution())).accumulate("amerceMoney", getDouble(a.getAmerceMoney()))
				.accumulate("judgeMode", getString(a.getJudgeMode())).accumulate("joinMode", getString(a.getJoinMode()))
				.accumulate("category", getString(a.getCategory())).accumulate("status", getString(a.getStatus()))
				.accumulate("action", getString(a.getAction())).accumulate("image", getString(a.getImage())).accumulate("value", getDouble(a.getValue()))
				.accumulate("applyCount", applyCount(a.getId()));
		final JSONObject ret = new JSONObject();
		ret.accumulate("success", true).accumulate("item", obj);
		response(ret);
	}

	@Override
	public void partake() {
		String judge = urlDecode(request.getParameter("judge"));
		final List<?> lists = service.findObjectBySql("from Member m where m.nick = ? or m.email = ? or m.mobilephone = ?", judge, judge, judge);
		if (lists.size() > 0) {
			super.partake();
		} else {
			final JSONObject ret = new JSONObject();
			ret.accumulate("result", "notExist");
			response(ret);
		}
	}
}
