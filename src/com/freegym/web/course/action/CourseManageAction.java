package com.freegym.web.course.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.databaseConnection.DataBaseConnection;
import com.freegym.web.CourseBasicAction;
import com.freegym.web.basic.Member;
import com.freegym.web.common.RepeatWhere;
import com.freegym.web.config.CourseInfo;
import com.freegym.web.config.WorkTime;
import com.freegym.web.course.Apply;
import com.freegym.web.course.BaseApply;
import com.freegym.web.plan.Course;
import com.sanmen.web.core.utils.DateUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Namespace("")
@InterceptorRefs({ @InterceptorRef("foreStack") })
@Results({ @Result(name = "success", location = "/course/index.jsp") })
public class CourseManageAction extends CourseBasicAction {

	private static final long serialVersionUID = -3769475961884283745L;

	private Course course;

	private Date startDate, endDate;

	private RepeatWhere where;

	private Integer saveType;

	private String goType;// 进入的菜单1：评价

	public String getGoType() {
		return goType;
	}

	public void setGoType(String goType) {
		this.goType = goType;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course cs) {
		this.course = cs;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public RepeatWhere getWhere() {
		return where;
	}

	public void setWhere(RepeatWhere where) {
		this.where = where;
	}

	public Integer getSaveType() {
		return saveType;
	}

	public void setSaveType(Integer saveType) {
		this.saveType = saveType;
	}

	private Member member;

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public String execute() {
		session.setAttribute("spath", 2);
		if (member != null && member.getId() != null) {
			member = (Member) service.load(Member.class, member.getId());
		} else {
			member = (Member) service.load(Member.class, toMember().getId());
		}
		final String role = member.getRole();
		final Long memberId = member.getId();
		Long count = service.queryForLong(
				"SELECT COUNT(ap.id) FROM TB_MEMBER_APPRAISE ap where ap.memberTo = ? and ap.isRead = ?",
				new Object[] { memberId, "0" });
		request.setAttribute("countList1", count);
		// 是否阅读分角色处理
		final StringBuffer hql = new StringBuffer(
				"select case when t.status is null then '1' else t.status end status, count(*) cnt from ("
						+ BaseApply.getApplySql() + ") t ");
		if (member.getRole().equals("M")) {
			hql.append(" where t.memberid = ?");
		} else {
			hql.append(" where t.clubid = ?");
		}
		hql.append(" group by case when t.status is null then '1' else t.status end");
		final List<?> countList = service.queryForList(hql.toString(), member.getId());
		request.setAttribute("countList2", getJsonString(countList));
		if (goType != null && !"".equals(goType)) {
			if (goType.equals("1")) {
			}
		} else {
			// 如果是教练或俱乐部，则取其自己的工作时间
			request.setAttribute("role",
					"{current: {id:" + member.getId() + ",role: '" + role + "', nick: '" + member.getNick() + "'}}");
			request.setAttribute("funcs", "{canAdd: true, canEdit: true, canDrag: true, canView: true}");
			if ("E".equals(role) || "S".equals(role)) {
				request.setAttribute("workDays", member.getWorkDate());
				final StringBuffer sb = new StringBuffer();
				if (role.equals("E")) {
					final List<?> list = service
							.findObjectBySql("from WorkTime wt where wt.member = ? order by wt.startTime", memberId);
					for (final Iterator<?> it = list.iterator(); it.hasNext();) {
						final WorkTime wt = (WorkTime) it.next();
						sb.append(DateUtil.getTimeRange2(wt.getStartTime(), wt.getEndTime()));
					}
				} else {
					sb.append(
							"00:00,00:30,01:00,01:30,02:00,02:30,03:00,03:30,04:00,04:30,05:00,05:30,06:00,06:30,07:00,07:30,08:00,08:30,09:00,09:30,10:00,10:30,11:00,11:30,12:00,12:30,13:00,13:30,14:00,14:30,15:00,15:30,16:00,16:30,17:00,17:30,18:00,18:30,19:00,19:30,20:00,20:30,21:00,21:30,22:00,22:30,23:00,23:30");
					final List<?> list = service
							.findObjectBySql("from WorkTime wt where wt.member = ? order by wt.startTime", memberId);
					for (final Iterator<?> it = list.iterator(); it.hasNext();) {
						final WorkTime wt = (WorkTime) it.next();
						removeTime(sb, wt.getStartTime(), wt.getEndTime());
					}
				}
				request.setAttribute("workTimes", sb.toString());
			} else { // 如果是会员，则取该会员的私教的工作时间
				request.setAttribute("funcs", "{canAdd: false, canEdit: false, canDrag: false, canView: true}");
				request.setAttribute("workDays", "1,2,3,4,5,6,7");
				request.setAttribute("workTimes",
						"00:00,01:00,02:00,03:00,04:00,05:00,06:00,07:00,08:00,09:00,10:00,11:00,12:00,13:00,14:00,15:00,16:00,17:00,18:00,19:00,20:00,21:00,22:00,23:00");
			}
		}
		return SUCCESS;
	}

	/**
	 * 教练非工作时间段的数据移除
	 * 
	 * @param times
	 * @param startTime
	 * @param endTime
	 */
	private void removeTime(StringBuffer times, String startTime, String endTime) {
		String timeRange = DateUtil.getTimeRange2(startTime, endTime);
		int index = times.indexOf(timeRange);
		if (index >= 0) {
			if (index == 0)
				times.delete(index, timeRange.length());
			else
				times.delete(index - 1, index + timeRange.length());
		}
	}

	/**
	 * 加载当前用户所有的课程，包括会员或团体课的课程。特殊处理要求
	 */
	public void findCalendar() {
		final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		final Calendar c = Calendar.getInstance();
		c.setTime(endDate);
		c.add(Calendar.HOUR_OF_DAY, 24);
		endDate = c.getTime();
		final String sDate = sdf.format(startDate), eDate = sdf.format(endDate);
		List<?> list = null;
		final Long mId = toMember().getId();
		final String role = toMember().getRole();
		final JSONArray jarr = new JSONArray();
		if (role.equals("E")) { // 俱乐部
			list = service.findObjectBySql("from Course cs where cs.member.id = ? and cs.planDate between ? and ?",
					new Object[] { mId, sDate, eDate });
			setPermission(list, 1, 0, 1, 1, 3);
			for (final Iterator<?> it = list.iterator(); it.hasNext();) {
				final Course course = (Course) it.next();
				jarr.add(course.toJson1());
			}
		} else if (role.equals("S")) { // 教练，比较特别。需要考虑是否是私教的课程及团体课的课程，对于团体课不能修改及删除
			list = service.findObjectBySql(
					"from Course cs, Member m where cs.member.id = m.id and cs.coach.id = ? and cs.planDate between ? and ?",
					new Object[] { mId, sDate, eDate });
			final List<Course> courses = new ArrayList<Course>();
			setCoachPermission(list, courses);
			list = courses;
			// -----------v vvvvvvv liuhb 2017-01-23 add  -----------------------//
			for (final Iterator<?> it = list.iterator(); it.hasNext();) {
				final Course course = (Course) it.next();
				jarr.add(course.toJson1());
			}
		} else { // 会员，只允许查看及删除。删除指对于预约的课程进行删除
			list = service.findObjectBySql(
					"from Course cs where (cs.member.id = ? or cs.id in (select a.course.id from Apply a where a.member.id = ? and a.status = ?)) and cs.planDate between ? and ?",
					new Object[] { mId, mId, "2", sDate, eDate });
			for (final Iterator<?> it = list.iterator(); it.hasNext();) {
				final Course course = (Course) it.next();
				course.setCanEdit(0);
				course.setCanJoin(0);
				course.setCanDel(0);
				course.setUiType(course.getMember().getRole().equals("E") ? 6 : 7);
				if (course.getMember().getId().equals(mId))
					course.setUiType(1);
				// -----------v vvvvvvv liuhb 2017-01-23 add  -----------------------//
				jarr.add(course.toJson1());
			}
		}
		response("{evts:" + jarr + "}");
//		String jsons = "{evts:" + getJsonString(list) + "}";
//		response(jarr);
	}

	private void setCoachPermission(final List<?> list, final List<Course> courses) {
		for (final Iterator<?> it = list.iterator(); it.hasNext();) {
			final Object[] objs = (Object[]) it.next();
			final Course cs = (Course) objs[0];
			final Member m = (Member) objs[1];
			if (m.getRole().equals("E")) {
				cs.setCanEdit(0);
				cs.setCanJoin(0);
				cs.setCanDel(0);
				cs.setUiType(4);
			} else {
				cs.setCanEdit(1);
				cs.setCanJoin(0);
				cs.setCanDel(1);
				cs.setUiType(2);
			}
			courses.add(cs);
		}
	}

	/**
	 * 
	 * @param list
	 * @param canEdit
	 * @param canJoin
	 * @param canDel
	 * @param canView
	 * @param uiType
	 *            ，界面类型， 1为会员制订课程界面（主要指在私教页面进行制订课程）， 2为教练给会员制订课程录入界面，
	 *            3为俱乐部制订课程界面， 4为查看俱乐部课程界面 5为查看教练的课程界面
	 */
	private void setPermission(final List<?> list, final Integer canEdit, final Integer canJoin, final Integer canDel,
			final Integer canView, final Integer uiType) {
		for (final Iterator<?> it = list.iterator(); it.hasNext();) {
			final Course c = (Course) it.next();
			c.setCanEdit(canEdit);
			c.setCanJoin(canJoin);
			c.setCanDel(canDel);
			c.setUiType(uiType);
		}
	}

	public void save() {
		try {
			CourseInfo courseInfo = (CourseInfo) service.load(CourseInfo.class, course.getCourseInfo().getId());
			course.setCourseInfo(courseInfo);
			final Member member = toMember();
			final String role = member.getRole();
			if (role.equals("E")) {
				course.setMember(member);
				course.setPlanSource(PLAN_SOURCE_SELF);
			} else if (role.equals("S")) {
				course.setCoach(member);
				course.setPlanSource(PLAN_SOURCE_COACH);
			} else {
				course.setMember(member);
				course.setCoach(member.getCoach());
				course.setPlanSource(PLAN_SOURCE_SELF);
			}
			final Long id2 = course.getId();
			
			
			if (id2 != null && id2 > 0) {
				course = (Course) service.updateCourse(course, saveType);
			} else {
				// 2017-7-22 hwj 修复购买 0 元课程闪退的 bug
				if (course.getHourPrice() == null) {
					course.setHourPrice(0.0);
				}
				
				course = (Course) service.saveCourse(course, saveType);
			}
			response("{success: true, id: " + id2 + ", color:'#fefefe', coach:'" + course.getCoach().getName() + "'}");
		} catch (Exception e) {
			e.printStackTrace();
			response("{success: false, desc: '" + e.getMessage() + "'}");
		}
	}

	public void delete() {
		try {
			// 如果是会员进行删除，则需要进行判断：如果该课程为团体课，则删除为取消预约，否则是删除
			final Member m = toMember();
			if (m.getRole().equals("M")) {
				final List<?> list = service.findObjectBySql(
						"from Member m where m.id in (select c.member from Course c where c.id = ?)", id);
				if (list.size() > 0) {
					final Member m1 = (Member) list.get(0);
					if (m1.getRole().equals("E")) {
						service.deleteApply(id, m.getId());
					} else {
//						service.deleteCourse(id, saveType);
						if(saveType != null && saveType > 0){
							String sqly = "update tb_course set planDate = '1980-01-01' where groupBy = ?";
							Object[] objy = {saveType}; 
							DataBaseConnection.updateData(sqly, objy);
						}else{
							String sqlx = "update  tb_course set planDate = '1980-01-01' where id = ?";
							Object[] objx = {id};
							DataBaseConnection.updateData(sqlx, objx);
						}
						
					}
				}
			} else {
//				service.deleteCourse(id, saveType);
				if(saveType != null && saveType > 0){
					String sqly = "update tb_course set planDate = '1980-01-01' where groupBy = ?";
					Object[] objy = {saveType};
					DataBaseConnection.updateData(sqly, objy);
				}else{
					String sqlx = "update  tb_course set planDate = '1980-01-01' where id = ?";
					Object[] objx = {id};
					DataBaseConnection.updateData(sqlx, objx);
				}
			}
			service.clean();
			response("OK");
		} catch (Exception e) {
			e.printStackTrace();
			log.error("error", e);
			response(e.getMessage());
		}
	}

	/**
	 * 课程预约取消
	 */
	public void undo() {
		final JSONObject ret = new JSONObject();
		try {
			// 俱乐部的课程预约取消
			if (saveType == 1) {
				final List<?> list = service.findObjectBySql("from Apply a where a.course.id = ? and a.member.id = ?",
						id, toMember().getId());
				for (final Iterator<?> it = list.iterator(); it.hasNext();) {
					final Apply a = (Apply) it.next();
					service.delete(a);
				}
			} else { // 私教课的预约取消
				service.delete(Course.class, id);
			}
			ret.accumulate("success", true);
			response(ret);
		} catch (Exception e) {
			log.error("课程预约取消失败！", e);
			response(e);
		}
	}

	public static void main(String[] args) {
		final StringBuffer sb = new StringBuffer(
				"00:00,00:30,01:00,01:30,02:00,02:30,03:00,03:30,04:00,04:30,05:00,05:30,06:00,06:30,07:00,07:30,08:00,08:30,09:00,09:30,10:00,10:30,11:00,11:30,12:00,12:30,13:00,13:30,14:00,14:30,15:00,15:30,16:00,16:30,17:00,17:30,18:00,18:30,19:00,19:30,20:00,20:30,21:00,21:30,22:00,22:30,23:00,23:30");
		final String str = DateUtil.getTimeRange2("01:00", "07:00");
		int index = sb.indexOf(str);
		System.out.println(sb.delete(index - 1, index + str.length()));
	}

	@Override
	protected String getExclude() {
		return "ticklings,courseGrades,works,grades,description,coachMembers,friends,clubs,coachs,members,visitors,setting,times,products,certificates,integrals,courses,alwaysAddrs,applys,coursess,records,courseCoachs,courseMembers,prods,appraises,appraises1,allFriends,orderDetails,orderDetails1,messages,messages1,orders,addrs,signIns,signIns1,shops,accounts,prodOrders,aos,actives,partakes,teams,tickets";
	}
}
