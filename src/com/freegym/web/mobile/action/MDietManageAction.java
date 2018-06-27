package com.freegym.web.mobile.action;

import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;

import com.freegym.web.basic.Member;
import com.freegym.web.mobile.CourseJsonAction;
import com.freegym.web.plan.Diet;
import com.freegym.web.plan.DietDetail;
import com.sanmen.web.core.bean.BaseMember;
import com.sanmen.web.core.utils.DateUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Namespace("")
@InterceptorRefs({ @InterceptorRef("mobileStack") })
public class MDietManageAction extends CourseJsonAction {

	private static final long serialVersionUID = -4900715126840183203L;

	/**
	 * 课表列表用类型，1月，2周，3日,课表撤消用类型1为私教课，2为团体课,组次类型0为计划，1为实际
	 */
	private int type;

	/**
	 * 计划时间
	 */
	private Date planDate;

	private String startDate, endDate;
	/**
	 * 被复制会员
	 */
	private Long toMember;

	/**
	 * 星期数组
	 */
	private Integer[] weeks;

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public Date getPlanDate() {
		return planDate;
	}

	public void setPlanDate(Date planDate) {
		this.planDate = planDate;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public Long getToMember() {
		return toMember;
	}

	public void setToMember(Long toMember) {
		this.toMember = toMember;
	}

	public Integer[] getWeeks() {
		return weeks;
	}

	public void setWeeks(Integer[] weeks) {
		this.weeks = weeks;
	}

	@Override
	protected void list() {
		final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if (planDate == null) planDate = new Date();
		Date[] ds = null;
		if (type == 1) {
			ds = DateUtil.getMinMaxDateByMonth(planDate);
		} else if (type == 2) {
			ds = DateUtil.getWeekDateByDate(planDate);
		} else {
			ds = DateUtil.getDateTimes(planDate);
		}
		final String sDate = sdf.format(ds[0]);
		final String eDate = sdf.format(ds[1]);
		final BaseMember mu = getMobileUser();
		final Long keyId = mu.getId();
		final List<?> list;
		if (mu.getRole().equals("S")) {
			list = service.findObjectBySql(
					"from Diet d where (d.member = ? or d.member in (select m.id from Member m where m.coach.id = ?)) and d.planDate between ? and ? order by d.planDate, d.startTime",
					keyId, keyId, sDate, eDate);
		} else {
			list = service.findObjectBySql("from Diet d where d.member = ? and d.planDate between ? and ? order by d.planDate, d.startTime", keyId, sDate, eDate);
		}
		final JSONArray jarr = new JSONArray();
		for (final Iterator<?> it = list.iterator(); it.hasNext();) {
			final Diet d = (Diet) it.next();
			final Member m = (Member) service.load(Member.class, d.getMember());
			final JSONObject obj = new JSONObject();
			obj.accumulate("id", d.getId()).accumulate("member", getMemberJson(m)).accumulate("mealNum", getString(d.getMealNum())).accumulate("planDate", d.getPlanDate())
					.accumulate("startTime", d.getStartTime()).accumulate("endTime", d.getEndTime()).accumulate("details", generatorDetailJson(d.getDetails()));
			jarr.add(obj);
		}
		final JSONObject ret = new JSONObject();
		ret.accumulate("success", true).accumulate("items", jarr);
		response(ret);
	}

	/**
	 * 保存计划明细（即食物）
	 */
	public void saveDetail() {
		try {
			final JSONArray arr = JSONArray.fromObject(URLDecoder.decode(jsons, "UTF-8"));
			final List<DietDetail> dds = new ArrayList<DietDetail>();
			for (final Iterator<?> it = arr.listIterator(); it.hasNext();) {
				final JSONObject obj = (JSONObject) it.next();
				final DietDetail dd = new DietDetail();
				dd.setDiet(new Diet(obj.getLong("pid")));
				if (obj.containsKey("id")) dd.setId(obj.getLong("id"));
				dd.setMealName(obj.getString("mealName"));
				dd.setMealWeight(String.valueOf(obj.getDouble("mealWeight")));
				dd.setMealKcal(obj.getString("mealKcal"));
				dds.add(dd);
			}
			final List<?> list = service.saveOrUpdate(dds);
			final JSONObject ret = new JSONObject();
			ret.accumulate("success", true).accumulate("items", generatorDetailJson(list));
			response(ret);
		} catch (Exception e) {
			log.error("error", e);
			response(e);
		}
	}

	/**
	 * 删除明细
	 */
	@Override
	protected void deletes() {
		service.delete(DietDetail.class, ids);
	}

	/**
	 * 生成计划列表的JSON数据
	 * 
	 * @param list
	 * @return
	 */
	protected String generatorDetailJson(final Collection<?> list) {
		final JSONArray jarr = new JSONArray();
		for (final Iterator<?> it = list.iterator(); it.hasNext();) {
			final DietDetail dd = (DietDetail) it.next();
			final JSONObject obj = new JSONObject();
			obj.accumulate("id", dd.getId()).accumulate("mealName", getString(dd.getMealName())).accumulate("mealWeight", getString(dd.getMealWeight())).accumulate("mealKcal",
					getString(dd.getMealKcal()));
			jarr.add(obj);
		}
		return jarr.toString();
	}

	/**
	 * 保存饮食计划主表
	 */
	@Override
	protected Long executeSave(JSONArray objs) {
		final BaseMember mu = getMobileUser();
		final JSONObject obj = objs.getJSONObject(0);
		Diet d = new Diet();
		if (obj.containsKey("id")) d.setId(obj.getLong("id"));
		if (obj.containsKey("member") && obj.getLong("member") > 0) d.setMember(obj.getLong("member"));
		else d.setMember(mu.getId());
		d.setPlanDate(obj.getString("planDate"));
		d.setStartTime(obj.getString("startTime"));
		d.setEndTime(obj.getString("endTime"));
		d.setMealNum(obj.getString("mealNum"));
		d = (Diet) service.saveOrUpdate(d);
		return d.getId();
	}

	/**
	 * 删除指定的饮食计划
	 */
	@Override
	protected void executeDelete() {
		try {
			service.delete(Diet.class, ids);
			final JSONObject ret = new JSONObject();
			ret.accumulate("success", true).accumulate("message", "OK");
			response(ret);
		} catch (Exception e) {
			log.error("error", e);
			response(e);
		}
	}

	public void copy() {
		try {
			if (toMember == null) toMember = getMobileUser().getId();
			service.copy(new Diet(id), toMember, startDate, endDate, weeks);
			final JSONObject ret = new JSONObject();
			ret.accumulate("success", true).accumulate("message", "OK");
			response(ret);
		} catch (Exception e) {
			log.error("error", e);
			response(e);
		}
	}

	public void clean() {
		try {
			Long key = id == null ? getMobileUser().getId() : id;
			final List<?> list = service.findObjectBySql("from Diet d where d.member = ? and d.planDate between ? and ?", key, startDate, endDate);
			for (final Iterator<?> it = list.iterator(); it.hasNext();) {
				final Diet d = (Diet) it.next();
				service.delete(d);
			}
			final JSONObject ret = new JSONObject();
			ret.accumulate("success", true).accumulate("message", "OK");
			response(ret);
		} catch (Exception e) {
			log.error("error", e);
			response(e);
		}
	}

}
