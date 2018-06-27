package com.freegym.web.plan.action;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.freegym.web.WorkoutBasicAction;
import com.freegym.web.basic.Member;
import com.freegym.web.config.Action;
import com.freegym.web.config.Part;
import com.freegym.web.config.Project;
import com.freegym.web.plan.Course;
import com.freegym.web.plan.Workout;
import com.freegym.web.plan.WorkoutDetail;
import com.sanmen.web.core.utils.DateUtil;

@Namespace("")
@InterceptorRefs({ @InterceptorRef("customStack") })
@Results({ @Result(name = "success", location = "/homeWindow/workout.jsp"), @Result(name = "course", location = "/homeWindow/workout_course.jsp"),
		@Result(name = "detail", location = "/homeWindow/workout_detail.jsp"), @Result(name = "edit", location = "/homeWindow/workout_detail_edit.jsp") })
public class WorkoutWindowManageAction extends WorkoutBasicAction {

	private static final long serialVersionUID = 5439537428568798578L;

	private String planDate;

	private String startDate, endDate;

	private Long member;

	private Course course;

	private Integer[] weeks;

	private List<Workout> workouts;

	private List<WorkoutDetail> details;

	private String isChange = "N";

	public String getIsChange() {
		return isChange;
	}

	public void setIsChange(String isChange) {
		this.isChange = isChange;
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

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public Integer[] getWeeks() {
		return weeks;
	}

	public void setWeeks(Integer[] weeks) {
		this.weeks = weeks;
	}

	public List<Workout> getWorkouts() {
		return workouts;
	}

	public void setWorkouts(List<Workout> workouts) {
		this.workouts = workouts;
	}

	public List<WorkoutDetail> getDetails() {
		return details;
	}

	public void setDetails(List<WorkoutDetail> details) {
		this.details = details;
	}
	//健身计划列表进入到会员健身计划页面呢
	public String goPlan(){
		if(member != null){
			Member m = (Member) service.load(Member.class,member);
			session.setAttribute("toMember", m);
		}
		member = null;
		return execute();
	}
	private Member getToMember() {
		return (Member) session.getAttribute("toMember");
	}

	// 判断是否有访问者与被访问者是否有私教关系
	private void initIsChange() {
		this.setIsChange("N");
		if (toMember() != null && !"".equals(toMember().getId()) && toMember().getCoach() != null && !"".equals(toMember().getCoach().getId())) {
			if (toMember().getCoach().getId().equals(getToMember().getId())) {
				this.setIsChange("Y");
			}
		}
	}

	@SuppressWarnings("unchecked")
	public String execute() {
		initIsChange();
		session.setAttribute("wpath", 3);
		Long coachId = null;
		// Member m = toMember();
		Member m = getToMember();
		if (course != null) {
			if (planDate == null)
				planDate = course.getPlanDate();
			member = course.getMember().getId();
			request.setAttribute("courseinfos", service.findAllCourse(toMember()));
			request.setAttribute("times", DateUtil.getAllTimeForDay());
			List<Course> courses = new ArrayList<Course>();
			courses.add(course);
			// 获取当前日期的所有计划明细数据
			List<Course> oldCourses = (List<Course>) service.findObjectBySql("from Course c where c.member.id = ? and c.planDate = ? order by c.startTime desc",
					new Object[] { member, planDate });
			courses.addAll(oldCourses);
			request.setAttribute("courses", courses);
		} else {
			if (planDate == null)
				planDate = DateUtil.getDateString(new Date());
			member = m.getId();
			loadData();
		}
		List<?> members = null;
		if (m.getRole().equals("S")) {// 教练
			coachId = m.getId();
			members = service.findObjectBySql("from Member m where m.coach.id = ? or m.id = ?", new Object[] { coachId, coachId });
			request.setAttribute("members", members);
			if (member == null && members.size() > 0)
				member = m.getId();// ((Member)members.get(0)).getId();
		} else { // 会员
			member = m.getId();
			coachId = m.getCoach() == null ? 1 : m.getCoach().getId();
		}
		// 获取所有应用项目
		List<Project> projects = new ArrayList<Project>();
		projects = service.loadProjectsByApplied(coachId);
		request.setAttribute("projects", projects);
		// 获取当前项目的所有部位及动作
		Long projectId = projects.get(0) != null ? projects.get(0).getId() : null;
		List<Part> parts = service.findPartsByProjects(projectId, coachId);
		List<Action> actions = service.findActionsByProjectAndPart(projectId,null,m);
		request.setAttribute("parts", parts);
		request.setAttribute("actions", actions);
		getMonthPlanStatus();
		return SUCCESS;
	}

	// 当前用户月计划状态
	private void getMonthPlanStatus() {
		try {
			String status = service.findMonthPlanStatus(member, planDate, "B");
			request.setAttribute("monthStatus", status);
		} catch (ParseException e) {
			e.printStackTrace();
			log.error("error", e);
		}
	}

	@SuppressWarnings("unchecked")
	private void loadData() {
		request.setAttribute("courseinfos", service.findAllCourse(toMember()));
		request.setAttribute("times", DateUtil.getAllTimeForDay());
		// 获取当前日期的所有计划明细数据
		List<Course> courses = (List<Course>) service.findObjectBySql("from Course c where c.member.id = ? and c.planDate = ? order by c.startTime desc",
				new Object[] { member, planDate });
		request.setAttribute("courses", courses);
		if (courses.size() > 0) {
			course = (Course) courses.get(0);
			workouts = new ArrayList<Workout>();
			workouts.addAll(course.getWorks());
		}
	}

	@SuppressWarnings("unchecked")
	public String loadGroup() {
		Workout workout = (Workout) service.load(Workout.class, id);
		details = (List<WorkoutDetail>) service.findObjectBySql("from WorkoutDetail wd where wd.workout.id = ?", id);
		// details = new ArrayList<WorkoutDetail>();
		// details.addAll(workout.getDetails());
		request.setAttribute("workout", workout);
		return "edit";
	}

	public String edit() {
		course = (Course) service.load(Course.class, id);
		workouts = new ArrayList<Workout>();
		workouts.addAll(course.getWorks());
		return "detail";
	}

	public String switchMember() {
		loadData();
		getMonthPlanStatus();
		return "course";
	}

	public void switchYearMonth() {
		try {
			String status = service.findMonthPlanStatus(member, planDate, "B");
			response(status);
		} catch (ParseException e) {
			e.printStackTrace();
			log.error("error", e);
		}
	}

	public String switchDate() {
		initIsChange();
		// 获取所有课程下拉列表
		loadData();
		getMonthPlanStatus();
		return "course";
	}

	public void switchProject() {
		final Long projectId = new Long(request.getParameter("projectId"));
		Long coachId = null;
		if (toMember().getRole().equals("S"))
			coachId = toMember().getId();
		else if (toMember().getRole().equals("M"))
			coachId = toMember().getCoach() == null ? 1l : toMember().getCoach().getId();
		List<Part> parts = service.findPartsByProjects(projectId, coachId);
		StringBuffer sb = new StringBuffer("<?xml version=\"1.0\" encoding=\"UTF-8\"?><root><parts>");
		for (Part p : parts)
			sb.append("<part id=\"").append(p.getId()).append("\" name=\"").append(p.getName()).append("\"/>");
		sb.append("</parts>");
		sb.append("<actions>");
		for (Part p : parts) {
			for (Action a : p.getActions())
				sb.append("<action id=\"").append(a.getId()).append("\" name=\"").append(a.getName()).append("\" partId=\"").append(a.getPart().getId())
						.append("\" partName=\"").append(a.getPart().getName()).append("\" pId=\"").append(a.getPart().getProject().getId())
						.append("\" pName=\"").append(a.getPart().getProject().getName()).append("\" mode=\"").append(a.getPart().getProject().getMode())
						.append("\" image=\"").append(a.getImage()).append("\" video=\"").append(a.getVideo()).append("\" flash=\"").append(a.getFlash())
						.append("\" descr=\"").append(a.getDescr()).append("\"/>");
		}
		sb.append("</actions></root>");
		response(sb.toString());
	}

	public void switchPart() {
		Member m = getToMember();
		final Long projectId = new Long(request.getParameter("projectId"));
		String part = request.getParameter("partId");
		List<Action> actions = null;
		if (part != null) {
			final Long partId = new Long(part);
			actions = service.findActionsByProjectAndPart(projectId,partId,m);
		} else {
			actions = service.findActionsByProjectAndPart(projectId,null,m);
		}
		StringBuffer sb = new StringBuffer("<?xml version=\"1.0\" encoding=\"UTF-8\"?><root><actions>");
		for (Action a : actions)
			sb.append("<action id=\"").append(a.getId()).append("\" name=\"").append(a.getName()).append("\" partId=\"").append(a.getPart().getId())
					.append("\" partName=\"").append(a.getPart().getName()).append("\" pId=\"").append(a.getPart().getProject().getId()).append("\" pName=\"")
					.append(a.getPart().getProject().getName()).append("\" mode=\"").append(a.getPart().getProject().getMode()).append("\" flash=\"")
					.append(a.getFlash()).append("\" image=\"").append(a.getImage()).append("\" video=\"").append(a.getVideo()).append("\" descr=\"")
					.append(a.getDescr()).append("\"/>");
		sb.append("</actions></root>");
		response(sb.toString());
	}

	public String save() {
		if (course != null) {
			if (course.getDoneDate() != null && course.getDoneDate().equals(""))
				course.setDoneDate(null);
			if (course.getCoach() != null && course.getCoach().getId() == null)
				course.setCoach(null);
			if (workouts != null)
				for (Workout wk : workouts)
					course.addWorkout(wk);
			service.saveOrUpdate(course);
		}
		planDate = course.getPlanDate();
		member = course.getMember().getId();
		loadData();
		getMonthPlanStatus();
		return "course";
	}

	public String saveDetail() {
		try {
			service.saveOrUpdate(details);
			return loadGroup();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("error", e);
		}
		return "edit";
	}

	/*
	 * 删除当前课程
	 */
	public void delete() {
		try {
			service.delete(Course.class, id);
			response("OK");
		} catch (Exception e) {
			e.printStackTrace();
			log.error("error", e);
			response(e.getMessage());
		}
	}

	/*
	 * 删除当前动作
	 */
	public void deleteWorkout() {
		try {
			service.delete(Workout.class, id);
			response("OK");
		} catch (Exception e) {
			e.printStackTrace();
			log.error("error", e);
			response(e.getMessage());
		}
	}

	/*
	 * 删除当前组
	 */
	public void deleteGroup() {
		try {
			service.delete(WorkoutDetail.class, id);
			response("OK");
		} catch (Exception e) {
			e.printStackTrace();
			log.error("error", e);
			response(e.getMessage());
		}
	}
}
