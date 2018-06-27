package com.freegym.web.plan.action;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONObject;

import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.freegym.web.WorkoutBasicAction;
import com.freegym.web.basic.Member;
import com.freegym.web.plan.Diet;
import com.freegym.web.plan.DietDetail;
import com.sanmen.web.core.utils.DateUtil;

@Namespace("")
@InterceptorRefs({ @InterceptorRef("foreStack") })
@Results({ @Result(name = "success", location = "/plan/diet.jsp"), @Result(name = "meal", location = "/plan/diet_meal.jsp"),
		@Result(name = "detail", location = "/plan/diet_detail.jsp") })
public class DietManageAction extends WorkoutBasicAction {

	private static final long serialVersionUID = 5439537428568798578L;

	private Integer year;

	private Integer month;

	private String planDate;

	private String startDate, endDate;

	private Long member, toMember;

	private Integer[] weeks;

	private Diet diet;

	private List<DietDetail> diets;

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public Integer getMonth() {
		return month;
	}

	public void setMonth(Integer month) {
		this.month = month;
	}

	public String getPlanDate() {
		return planDate;
	}

	public void setPlanDate(String planDate) {
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

	public Long getMember() {
		return member;
	}

	public void setMember(Long member) {
		this.member = member;
	}

	public Integer[] getWeeks() {
		return weeks;
	}

	public void setWeeks(Integer[] weeks) {
		this.weeks = weeks;
	}

	public Long getToMember() {
		return toMember;
	}

	public void setToMember(Long toMember) {
		this.toMember = toMember;
	}

	public Diet getDiet() {
		return diet;
	}

	public void setDiet(Diet diet) {
		this.diet = diet;
	}

	public List<DietDetail> getDiets() {
		return diets;
	}

	public void setDiets(List<DietDetail> diets) {
		this.diets = diets;
	}

	public String execute() {
		session.setAttribute("spath", 4);
		Member m = toMember();
		member = m.getId();
		if (planDate == null) planDate = DateUtil.getDateString(new Date());

		// 当前用户月计划状态
		try {
			String status = service.findMonthPlanStatus(member, planDate, "A");
			request.setAttribute("monthStatus", status);
		} catch (ParseException e) {
			log.error("error", e);
		}
		if (toMember().getRole().equals("S")) {
			request.setAttribute("members", service.findObjectBySql("from Member m where m.coach.id = ? or m.id = ?", new Object[] { member, member }));
		}
		loadData(member, planDate);
		return SUCCESS;
	}

	private void loadData(final Long member, final String planDate) {
		// 当前用户当前天的饮食
		final List<Diet> meals = service.findDietByUserAndDate(member, planDate);
		request.setAttribute("meals", meals);

		if (meals.size() > 0) {
			diet = meals.get(0);
			diets = new ArrayList<DietDetail>();
			diets.addAll(diet.getDetails());
		} else {
			diet = new Diet();
			diet.setPlanDate(planDate);
			diet.setMember(member);
		}
		request.setAttribute("times", DateUtil.getAllTimeForDay());

	}

	// 获取当前项目的所有部位及动作

	public void delete() {
		JSONObject obj = new JSONObject();
		try {
			service.delete(Diet.class, id);
			// service.deleteDiet(id, ids);
			String status = service.findMonthPlanStatus(member, planDate, "A");
			obj.put("success", true);
			obj.put("status", status);
		} catch (Exception e) {
			log.error("error", e);
			obj.put("success", false);
			obj.put("message", e.getMessage());
		}
		response(obj.toString());
	}

	public String save() {
		if (diet != null) {
			if (diets != null) {
				int i = 1;
				for (DietDetail dd : diets) {
					if (dd != null) {
						dd.setSort(i++);
						diet.addDetail(dd);
					}
				}
			}
			service.saveOrUpdate(diet);
			service.clean();
			try {
				final String status = service.findMonthPlanStatus(diet.getMember(), diet.getPlanDate(), "A");
				request.setAttribute("monthStatus", status);
			} catch (ParseException e) {
				log.error("error", e);
			}
			loadData(diet.getMember(), diet.getPlanDate());
		}
		return "meal";
	}

	public void switchYearMonth() {
		// 当前用户月计划状态
		try {
			String status = service.findMonthPlanStatus(member, planDate, "A");
			response(status);
		} catch (ParseException e) {
			log.error("error", e);
		}
	}

	public String switchDate() {
		// 当前用户当前天的饮食
		request.setAttribute("times", DateUtil.getAllTimeForDay());
		final List<Diet> meals = service.findDietByUserAndDate(member, planDate);
		request.setAttribute("meals", meals);
		if (meals != null && meals.size() > 0) {
			diet = meals.get(0);
			diets = new ArrayList<DietDetail>();
			diets.addAll(diet.getDetails());
		} else {
			diet = new Diet();
			diet.setMember(member);
			diet.setPlanDate(planDate);
		}
		return "meal";
	}

	public String switchMember() {
		// 当前用户月计划状态
		try {
			String status = service.findMonthPlanStatus(member, planDate, "A");
			request.setAttribute("monthStatus", status);
			request.setAttribute("times", DateUtil.getAllTimeForDay());
			final List<Diet> meals = service.findDietByUserAndDate(member, planDate);
			request.setAttribute("meals", meals);
			if (meals != null && meals.size() > 0) {
				diet = meals.get(0);
				diets = new ArrayList<DietDetail>();
				diets.addAll(diet.getDetails());
			} else {
				diet = new Diet();
				diet.setMember(member);
				diet.setPlanDate(planDate);
			}
		} catch (ParseException e) {
			log.error("error", e);
		}
		return "meal";
	}

	@SuppressWarnings("unchecked")
	public String findDetail() {
		diet = (Diet) service.load(Diet.class, id);
		diets = (List<DietDetail>) service.findObjectBySql("from DietDetail dd where dd.diet.id = ?", id);
		return "detail";
	}

	public void deleteDetail() {
		try {
			service.delete(DietDetail.class, id);
			response("ok");
		} catch (Exception e) {
			log.error("error", e);
			response(e.getMessage());
		}
	}

	public void clean() {

	}

	/**
	 * 复制计划到指定的日期中
	 */
	public void copy() {
		try {
			if (toMember == null) toMember = toMember().getId();
			service.copy(diet, toMember, startDate, endDate, weeks);
			// 当前用户月计划状态
			String status = service.findMonthPlanStatus(member, planDate, "A");
			response("{\"success\":true,\"message\":" + status + "}");
		} catch (Exception e) {
			log.error("error", e);
			response("{\"success\":false,\"message\": \"" + e.getMessage() + "\"}");
		}
	}

}
