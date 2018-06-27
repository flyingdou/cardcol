package com.freegym.web.weixin.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.freegym.web.WorkoutBasicAction;
import com.freegym.web.basic.Member;
import com.freegym.web.plan.Course;
import com.freegym.web.plan.Workout;
import com.freegym.web.plan.WorkoutDetail;
import com.sanmen.web.core.utils.BaiduUtils;
import com.sanmen.web.core.utils.DateUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Namespace("")
@InterceptorRefs({ @InterceptorRef("customStack") })
@Results({@Result(name="success",location="/WX/myPlan.jsp"),
	@Result(name="login",location="/WX/login.jsp"),
	@Result(name="trainPlan",location="/WX/trainPlan.jsp")})
public class WorkoutWxManageAction extends WorkoutBasicAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = -584497216536402167L;
	
	private Long member;
	private List<Workout> workouts;
	private List<WorkoutDetail> details;
	private Course course;
	private Date planDate;
	private Long wid;
	
	public Long getWid() {
		return wid;
	}
	public void setWid(Long wid) {
		this.wid = wid;
	}
	public List<WorkoutDetail> getDetails() {
		return details;
	}
	public void setDetails(List<WorkoutDetail> details) {
		this.details = details;
	}
	public Long getMember() {
		return member;
	}
	public void setMember(Long member) {
		this.member = member;
	}
	public List<Workout> getWorkouts() {
		return workouts;
	}
	public void setWorkouts(List<Workout> workouts) {
		this.workouts = workouts;
	}
	public Course getCourse() {
		return course;
	}
	public void setCourse(Course course) {
		this.course = course;
	}
	public Date getPlanDate() {
		return planDate;
	}
	public void setPlanDate(Date planDate) {
		this.planDate = planDate;
	}
	
	
	
	/*
	 * 我的计划首页
	 */
	public String execute(){
		Member m = (Member) session.getAttribute("member");
		if(m==null){
			return "login";
		}
		member = m.getId();
		if (planDate == null) planDate = new Date();
		if(course==null) {
			course=new Course();
			course.setPlanDate(DateUtil.getDateString(planDate));
		}
		@SuppressWarnings("unchecked")
		List<Course> courses =(List<Course>) service.findObjectBySql("from Course c where c.member.id = ? and c.planDate = ? order by c.id asc",new Object[] { member,course.getPlanDate()});
		request.setAttribute("courses", courses);
		loadMonthStatus();
		return SUCCESS;
	}
	public void loadMonthStatus(){
		if(course==null){
			course=new Course();
			course.setPlanDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
		}
		@SuppressWarnings("unused")
		Member m = (Member) session.getAttribute("member");
		try {
			planDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(course.getPlanDate()+" 00:00:00");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public String switchYearMonth() {
		Member m = (Member) session.getAttribute("member");
		member = m.getId();
		@SuppressWarnings("unchecked")
		List<Course> courses =(List<Course>) service.findObjectBySql("from Course c where c.member.id = ? and c.planDate = ? order by c.id asc",new Object[] { member,course.getPlanDate()});
		request.setAttribute("courses", courses);
		loadMonthStatus();
		return SUCCESS;
	}
	
	public String switchDate() {
		@SuppressWarnings("unused")
		final Date nowDate = new Date();
		Member m = (Member) session.getAttribute("member");
		@SuppressWarnings("rawtypes")
		List list = service.findAllCourse(m);
		if (list == null || list.size() == 0) {
			list = service.findAllCourse((Member) service.load(Member.class, 1L));
		}
		request.setAttribute("courseinfos", list);
		request.setAttribute("times", DateUtil.getAllTimeForDay());
		@SuppressWarnings("unchecked")
		List<Course> courses = (List<Course>) service.findObjectBySql("from Course c where c.member.id = ? and c.planDate = ? order by c.id asc",
				new Object[] { m.getId(), course.getPlanDate() });
		System.out.println(planDate+"-="+course.getPlanDate());
		request.setAttribute("course", course);
		request.setAttribute("courses", courses);
		workouts = new ArrayList<Workout>();
		if (course != null) {
			for (final Course c : courses) {
				if (c.getId().equals(course.getId())) {
					workouts.addAll(c.getWorks());
					break;
				}
			}
		} else {
			if (courses.size() > 0) {
				course = (Course) courses.get(0);
				workouts = new ArrayList<Workout>();
				workouts.addAll(course.getWorks());
				if (course.getMusic() != null) request.setAttribute("audioSignPath", BaiduUtils.getSignPath(BUCKET, course.getMusic().getAddr()));
			}
		}
		if(workouts!=null&&workouts.size()>0){
			
			Workout work=workouts.get(0);
			wid=work.getId();
			request.setAttribute("wid",wid);
		}
		loadMonthStatus();
		return SUCCESS;
	}
/*	public String showAction(){
		final Action a=(Action) service.load(Action.class,id);
		return "action";
	}*/
	/*public String loadGroup() {
		Workout workout = (Workout) service.load(Workout.class, id);
		details = (List<WorkoutDetail>) service.findObjectBySql("from WorkoutDetail wd where wd.workout.id = ?", id);
		request.setAttribute("workout", workout);
		System.out.println("视频地址"+workout.getAction().getVideo());
		request.setAttribute("videoSignPath", BaiduUtils.getSignPath(BUCKET, workout.getAction().getVideo()));
		return "action";
	}*/
	
	@SuppressWarnings({ "unused", "rawtypes", "unchecked" })
	public String findPlanByCourse(){
		List<?> lists = new ArrayList<>();
		final List<?> list = service.findObjectBySql("from Workout wo where wo.course.id = ? order by wo.sort", id);
		final JSONObject ret = new JSONObject();
		final JSONArray jarr = new JSONArray();
		for (final Iterator<?> it = list.iterator(); it.hasNext();) {
			final Workout wo = (Workout) it.next();
			final JSONObject obj = new JSONObject(), actObj = new JSONObject();
			actObj.accumulate("id", wo.getAction().getId()).accumulate("name", wo.getAction().getName())
					.accumulate("flash", getString(wo.getAction().getFlash())).accumulate("video", getString(wo.getAction().getVideo()))
					.accumulate("image", getString(wo.getAction().getImage())).accumulate("descr", wo.getAction().getDescr())
					.accumulate("regard", wo.getAction().getRegard());
			final List<?> detailList = service.findObjectBySql("from WorkoutDetail wo where wo.workout.id = ?", wo.getId());
		StringBuffer str = new StringBuffer();
		if(detailList.size()>0){
			List planTimesList = new ArrayList<>();
			List planWeightList = new ArrayList<>();
			boolean planTimesIsnumber = false;
			boolean planWeightIsnumber = false;
			for (final Iterator<?> it2 = detailList.iterator(); it2.hasNext();) {
				WorkoutDetail detail = (WorkoutDetail) it2.next();
				planTimesList.add(detail.getPlanTimes());
				planWeightList.add(detail.getPlanWeight());
			}
			str.append(detailList.size() + "组,");
			if (planTimesIsnumber) {
				str.append("每组" + planTimesList.get(0) + ",");
			} else {
				Collections.sort(planTimesList);
				if (planTimesList.get(0) == planTimesList.get(planTimesList.size() - 1)) {
					str.append("每组" + planTimesList.get(0) + "次,");
				} else {
					str.append("每组" + planTimesList.get(0) + "-" + planTimesList.get(planTimesList.size() - 1) + "次,");
				}
			}
			if (planWeightIsnumber) {
				str.append("重量" + planWeightList.get(0));
			} else {
				Collections.sort(planWeightList);
				if (planWeightList.get(0) == planWeightList.get(planWeightList.size() - 1)) {
					str.append("重量" + planWeightList.get(0) + "公斤");
				} else {
					str.append("重量" + planWeightList.get(0) + "-" + planWeightList.get(planWeightList.size() - 1) + "公斤");
				}
			}
			System.out.println(str);
			List l = new ArrayList<>();
			l.add(str);
			request.setAttribute("list",list );
			request.setAttribute("l",l );
			request.setAttribute("str",str);
 		}
		}
		return "trainPlan";
}
}