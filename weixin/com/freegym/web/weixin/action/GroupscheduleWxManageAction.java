package com.freegym.web.weixin.action;

import java.util.Date;

import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.freegym.web.BaseBasicAction;

@Namespace("")
@InterceptorRefs({ @InterceptorRef("customStack") })
@Results({ /* @Result(name = "success", location = "WX/login.jsp"), */
		@Result(name = "Schedule", location = "/eg/myCourse.jsp"),
		@Result(name = "booking", location = "/eg/bookingClass.jsp"),
		@Result(name = "planDetail", location = "/WX/trainingplan.jsp"),
		@Result(name = "myClass", location = "/eg/myClass.jsp") })
public class GroupscheduleWxManageAction extends BaseBasicAction {

	private static final long serialVersionUID = 6900527520662127731L;
	private Long member;
	private Date planDate;
	private Long id;
	private String state;// 预约状态：“可预约” “人已满”

	public Date getPlanDate() {
		return planDate;
	}

	public void setPlanDate(Date planDate) {
		this.planDate = planDate;
	}

	public Long getMember() {
		return member;
	}

	public void setMember(Long member) {
		this.member = member;
	}

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@Override
	public String execute() {
		// Test TODO
		/*Member member = (Member) session.getAttribute("member");
		if (member == null) {
			return SUCCESS;
		} else {*/
			return Schedule();
		/*}*/
	}

	/**
	 * 团体课表
	 */
	public String Schedule() {
		String sql = "SELECT c.startTime,c.endTime,i.name,m.name mname,(c.count-c.joinNum) AS num FROM tb_course_info i INNER JOIN tb_course c ON i.id=c.courseId INNER JOIN tb_member m ON c.member=m.id WHERE m.role='E' AND c.planDate=? AND m.id=?";
		pageInfo = service.findPageBySql(sql, pageInfo, new Object[] { "2013-11-22", 456 });
		System.out.println(pageInfo.getItems());
		return "Schedule";
	}

	/**
	 * 课程预约
	 * @param String planDate:课程日期
	 * @param String member:俱乐部编号
	 * @param String id:课程编号
	 * 
	 * @return 课程的详细信息
	 */
	public String booking() {
		StringBuffer sql = new StringBuffer();
		sql.append(
				"SELECT i.name,c.coach,c.startTime,c.endTime,c.place,(c.count-c.joinNum) num,c.costs FROM tb_course_info i INNER JOIN tb_course c ON i.id=c.courseId INNER JOIN tb_member m ON c.member=m.id WHERE m.role='E' AND  c.planDate=? AND m.id=? AND c.id=?");
		pageInfo = service.findPageBySql(sql.toString(), pageInfo, new Object[] { "2013-11-22", 456, 8 });
		
		return "booking";
	}

	/**
	 * 我的课程
	 */
	public String myclass() {
		String sql = "SELECT i.name,c.startTime,c.endTime,c.memo FROM tb_course_info i INNER JOIN tb_course c ON i.id=c.courseId INNER JOIN tb_member m ON c.member=m.id WHERE m.role='E' AND  c.planDate=? AND m.id=? AND c.id=?";
		pageInfo = service.findPageBySql(sql, pageInfo, new Object[] { "2013-11-22", 456, 8 });
		
		return "myClass";
	}
	
	/***
	 * 查询训练计划的详情
	 * 
	 * @param Long courseId : 课程编号
	 * 
	 * @return actionName 动作名称, actionImage 动作图片, groupTimes 动作组次, actionCount 动作次数,planWeight 计划重量
	 */
	public String planDetail() {
		String sql = "SELECT DISTINCT name as actionName,image actionImage, planTimes groupTimes, intensity actionCount,  planWeight FROM tb_project_action a, tb_workout_detail d , tb_workout w WHERE  course = ? AND w.id = d.workout AND w.action = a.id";
		pageInfo = service.findPageBySql(sql, pageInfo, new Object[] { 1111 });
		
		return "planDetail";
	}
}
