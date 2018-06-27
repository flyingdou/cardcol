package com.freegym.web.plan.action;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.freegym.web.WorkoutBasicAction;
import com.freegym.web.basic.Member;
import com.freegym.web.config.Setting;
import com.sanmen.web.core.utils.StringUtils;

@Namespace("")
@InterceptorRefs({ @InterceptorRef("foreStack") })
@Results({ @Result(name = "success00", location = "/plan/auto00.jsp"), @Result(name = "success01", location = "/plan/auto01.jsp"),
		@Result(name = "success02", location = "/plan/auto02.jsp"), @Result(name = "success03", location = "/plan/auto03.jsp"),
		@Result(name = "success04", location = "/plan/auto04.jsp"), @Result(name = "success05", location = "/plan/auto05.jsp"),
		@Result(name = "success10", location = "/plan/auto10.jsp"), @Result(name = "success11", location = "/plan/auto11.jsp"),
		@Result(name = "success12", location = "/plan/auto12.jsp"), @Result(name = "success13", location = "/plan/auto13.jsp"),
		@Result(name = "success14", location = "/plan/auto14.jsp"), @Result(name = "detail", location = "/plan/diet_detail.jsp") })
public class AutoManageAction extends WorkoutBasicAction {

	private static final long serialVersionUID = 5439537428568798578L;

	/**
	 * 计划类型，0为卡库，1为王岩
	 */
	private Integer type;

	/**
	 * 步骤
	 */
	private Integer step;

	/**
	 * 个人设置
	 */
	private Setting setting;

	/**
	 * 个人设置页面参数
	 */
	private Map<String, String> params = new HashMap<String, String>();

	private String startDate;

	private Member member;

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getStep() {
		return step;
	}

	public void setStep(Integer step) {
		this.step = step;
	}

	public Setting getSetting() {
		return setting;
	}

	public void setSetting(Setting setting) {
		this.setting = setting;
	}

	public Map<String, String> getParams() {
		return params;
	}

	public void setParams(Map<String, String> params) {
		this.params = params;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public String execute() {
		setting = service.loadSetting(toMember().getId());
		return "success" + type + "0";
	}

	public String guide() {
		final Setting s = service.loadSetting(toMember().getId());
		try {
			for (final String str : params.keySet())
				BeanUtils.copyProperty(s, str, params.get(str));
			setting = (Setting) service.saveOrUpdate(s);
		} catch (IllegalAccessException | InvocationTargetException e) {
			log.error("error", e);
		}
		if (type == 0 && step == 2) {
			request.setAttribute("strengthDates", StringUtils.stringToList(s.getStrengthDate()));
			request.setAttribute("cardioDates", StringUtils.stringToList(s.getCardioDate()));
		}
		if ((type == 0 && step == 3) | (type == 1 && step == 2)) {
			request.setAttribute("cardios", service.findDefaultFavoriteCardio());// service.findParametersByCodes("system_muscle_c"));
			request.setAttribute("cardioVals", StringUtils.stringToList(s.getFavoriateCardio()));
		}
		if (type == 1 && step == 1) {
			request.setAttribute("cardios", service.findDefaultFavoriteCardio());// service.findParametersByCodes("system_muscle_c"));
			request.setAttribute("cardioVals", StringUtils.stringToList(s.getFavoriateCardio()));
		}
		return "success" + type + "" + step;
	}

}
