package com.freegym.web.course.action;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.freegym.web.CourseBasicAction;
import com.freegym.web.basic.Member;
import com.freegym.web.config.WorkTime;
import com.freegym.web.course.Apply;
import com.freegym.web.plan.Course;
import com.sanmen.web.core.utils.DateUtil;

import net.sf.json.JSONObject;

@Namespace("")
@InterceptorRefs({ @InterceptorRef("customStack") })
@Results({ @Result(name = "success", location = "/homeWindow/appoint.jsp") })
public class CourseWindowManageAction extends CourseBasicAction {

	private static final long serialVersionUID = -3769475961884283745L;

	private Date startDate, endDate;

	private Course course; // 申请的课程

	private Integer saveType;

	private String clubId; // 用于不进入俱乐部，直接点击团体课表会传来clubId

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

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public Integer getSaveType() {
		return saveType;
	}

	public void setSaveType(Integer saveType) {
		this.saveType = saveType;
	}

	public String getClubId() {
		return clubId;
	}

	public void setClubId(String clubId) {
		this.clubId = clubId;
	}

	/**
	 * 加载页面，并初始化页面数据
	 */
	public String execute() {
		request.setAttribute("goType", "1");
		session.setAttribute("wpath", 2);
		// 不进入俱乐部主页而直接点击团体课表的话会获取不到toMember，届时会传过来clubId
		if (clubId != null && !"".equals(clubId)) {
			Member member = (Member) service.load(Member.class, Long.valueOf(clubId));
			session.setAttribute("toMember", member);
		}
		Member toMember = (Member) session.getAttribute("toMember");
		request.setAttribute("toMember", toMember);

		if (toMember == null) return SUCCESS;
		final String currentRole = toMember() == null ? "" : toMember().getRole();
		// 如果是教练或俱乐部，则取其自己的工作时间
		final String role = toMember.getRole();
		if (toMember() == null) {
			request.setAttribute("role", "{access: {id: " + toMember.getId() + ", nick: '" + toMember.getNick() + "',role: '" + role + "'}}");
		} else {
			request.setAttribute("role", "{current: {id:" + toMember().getId() + ",nick: '" + toMember().getNick() + "',role: '" + currentRole
					+ "'}, access: {id: " + toMember.getId() + ", nick: '" + toMember.getNick() + "',role: '" + role + "'}}");
		}
		final boolean isPE = isPrivateEducation();
		if ("E".equals(role) || "S".equals(role)) {
			request.setAttribute("funcs", "{canAdd: " + isPE + ", canEdit: " + isPE + ", canDrag: false, canView: true}");
			final List<?> list = service.findObjectBySql("from WorkTime wt where wt.member = ?", toMember.getId());
			final StringBuffer sb = new StringBuffer();
			if ("E".equals(role)) {
				for (Iterator<?> it = list.iterator(); it.hasNext();) {
					final WorkTime wt = (WorkTime) it.next();
					sb.append(DateUtil.getTimeRange2(wt.getStartTime(), wt.getEndTime()));
				}
			} else {
				sb.append(
						"00:00,00:30,01:00,01:30,02:00,02:30,03:00,03:30,04:00,04:30,05:00,05:30,06:00,06:30,07:00,07:30,08:00,08:30,09:00,09:30,10:00,10:30,11:00,11:30,12:00,12:30,13:00,13:30,14:00,14:30,15:00,15:30,16:00,16:30,17:00,17:30,18:00,18:30,19:00,19:30,20:00,20:30,21:00,21:30,22:00,22:30,23:00,23:30");
				for (final Iterator<?> it = list.iterator(); it.hasNext();) {
					final WorkTime wt = (WorkTime) it.next();
					final String timeRange = DateUtil.getTimeRange2(wt.getStartTime(), wt.getEndTime());
					int index = sb.indexOf(timeRange);
					if (index >= 0) {
						if (index == 0) sb.delete(index, timeRange.length());
						else sb.delete(index - 1, index + timeRange.length());
					}
				}
			}
			request.setAttribute("workDays", toMember.getWorkDate());
			request.setAttribute("workTimes", sb.toString());
		} else { // 如果被访问者是会员
			request.setAttribute("funcs", "{canAdd: false, canEdit: false, canDrag: false, canView: true}");
			request.setAttribute("workDays", "1,2,3,4,5,6,7");
			request.setAttribute("workTimes",
					"00:00,01:00,02:00,03:00,04:00,05:00,06:00,07:00,08:00,09:00,10:00,11:00,12:00,13:00,14:00,15:00,16:00,17:00,18:00,19:00,20:00,21:00,22:00,23:00");
		}
		return SUCCESS;
	}

	/**
	 * 查找条件内的日历课程
	 */
	public void findCalendar() {
		final Member toMember = (Member) session.getAttribute("toMember");
		if (toMember == null) return;
		final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		final Calendar c = Calendar.getInstance();
		c.setTime(endDate);
		c.add(Calendar.HOUR_OF_DAY, 24);
		endDate = c.getTime();
		final String sDate = sdf.format(startDate), eDate = sdf.format(endDate);
		final Long toId = toMember.getId();
		List<?> list = null;
		final String role = toMember.getRole();
		if (toMember() == null) {
			if (role.equals("S")) { // 教练
				list = service.findObjectBySql("from Course cs where cs.coach.id = ? and cs.planDate between ? and ?", new Object[] { toId, sDate, eDate });
				setPermission(list, 0, 0, 0, 1);
			} else if (role.equals("E")) { // 俱乐部
				list = service.findObjectBySql("from Course cs where cs.member.id = ? and cs.planDate between ? and ?", new Object[] { toId, sDate, eDate });
				setPermission(list, 0, 0, 0, 4);
			} else { // 会员
				list = service.findObjectBySql(
						"from Course cs where cs.planDate between ? and ? and cs.share = '1' and (cs.member.id = ? or cs.id in (select a.course.id from Apply a where a.member.id = ? and a.status = ?)) ",
						new Object[] { sDate, eDate, toId, toId, "2" });
				setPermission(list, 0, 0, 0, 1);
			}
		} else {
			// 如果当前登录的是会员
			final String currentRole = toMember().getRole();
			if (currentRole.equals("M")) {// 会员
				Integer canEdit = 0, canJoin = 0;
				if (role.equals("E")) { // 被访问者为俱乐部
					list = service.findObjectBySql("select count(o.id) from Friend o where o.member.id = ? and o.friend.id = ?",
							new Object[] { toMember().getId(), toMember.getId() });
					final Long hasOrder = (Long) list.get(0);
					if (hasOrder != null && hasOrder > 0) {
						canJoin = 1;
					}
					list = service.findObjectBySql("from Course cs where cs.member.id = ? and cs.planDate between ? and ?",
							new Object[] { toId, sDate, eDate });
					for (final Iterator<?> it = list.iterator(); it.hasNext();) {
						final Course course = (Course) it.next();
						course.setCanEdit(0);
						course.setCanJoin(canJoin);
						course.setCanDel(0);
						course.setUiType(8);
					}
				} else if (role.equals("S")) { // 被访问者为教练
					list = service.findObjectBySql("from Course cs where cs.coach.id = ? and cs.planDate between ? and ?", new Object[] { toId, sDate, eDate });
					// 判断是否可进行修改或加入，如果toMember为教练，则看其有无私教关系，如果toMember为俱乐部，则看其有无合约关系，如果是会员则全部无权限
					final boolean isPe = isPrivateEducation();
					canEdit = isPe ? 1 : 0;
					for (final Iterator<?> it = list.iterator(); it.hasNext();) {
						final Course course = (Course) it.next();
						course.setCanJoin(0);
						course.setCanDel(canEdit);
						course.setCanEdit(canEdit);
						course.setUiType(isPe ? 1 : 7);
						if (course.getMember().getRole().equals("E")) course.setUiType(8);

					}
				} else { // 被访问者为会员
					list = service.findObjectBySql(
							"from Course cs where cs.planDate between ? and ? and cs.share = '1' and (cs.member.id = ? or cs.id in (select a.course.id from Apply a where a.member.id = ? and a.status = ?))",
							new Object[] { sDate, eDate, toId, toId, "2" });
					// 判断是否可进行修改或加入，如果toMember为教练，则看其有无私教关系，如果toMember为俱乐部，则看其有无合约关系，如果是会员则全部无权限
					setPermission(list, 0, 0, 0, 1);
				}
			} else if (currentRole.equals("S")) { // 如果当前登录的是教练
				if (role.equals("E")) { // 被访问者为俱乐部
					list = service.findObjectBySql("from Course cs where cs.member.id = ? and cs.planDate between ? and ?",
							new Object[] { toId, sDate, eDate });
					for (final Iterator<?> it = list.iterator(); it.hasNext();) {
						final Course course = (Course) it.next();
						course.setCanEdit(0);
						course.setCanJoin(0);
						course.setCanDel(0);
						course.setUiType(4);
					}
				} else if (role.equals("S")) {
					list = service.findObjectBySql("from Course cs where cs.coach.id = ? and cs.planDate between ? and ?", new Object[] { toId, sDate, eDate });
					// 判断是否可进行修改或加入，如果toMember为教练，则看其有无私教关系，如果toMember为俱乐部，则看其有无合约关系，如果是会员则全部无权限
					setPermission(list, 0, 0, 0, 1);
				} else {
					list = service.findObjectBySql(
							"from Course cs where (cs.member.id = ? and cs.planDate between ? and ?) or cs.id in (select a.course.id from Apply a where a.member.id = ? and a.course.planDate between ? and ?)",
							new Object[] { toId, sDate, eDate, toId, sDate, eDate });
					// 判断是否可进行修改或加入，如果toMember为教练，则看其有无私教关系，如果toMember为俱乐部，则看其有无合约关系，如果是会员则全部无权限
					for (final Iterator<?> it = list.iterator(); it.hasNext();) {
						final Course course = (Course) it.next();
						course.setCanEdit(0);
						course.setCanJoin(0);
						course.setCanDel(0);
						course.setUiType(5);
						final Member m = (Member) service.load(Member.class, course.getMember().getId());
						course.setMemberName(m.getNick());
						if (m.getRole().equals("E")) course.setUiType(4);
					}
				}
			} else {
				if (role.equals("E")) {
					list = service.findObjectBySql("from Course cs where cs.member.id = ? and cs.planDate between ? and ?",
							new Object[] { toMember.getId(), sDate, eDate });
					for (final Iterator<?> it = list.iterator(); it.hasNext();) {
						final Course course = (Course) it.next();
						course.setCanEdit(0);
						course.setCanJoin(0);
						course.setCanDel(0);
						course.setUiType(4);
					}
				} else if (role.equals("S")) {
					list = service.findObjectBySql("from Course cs where cs.planDate between ? and ? and cs.share = '1' and cs.coach.id = ?", sDate, eDate,
							toId);
					for (final Iterator<?> it = list.iterator(); it.hasNext();) {
						final Course course = (Course) it.next();
						course.setCanEdit(0);
						course.setCanJoin(0);
						course.setCanDel(0);
						course.setUiType(5);
						final Member m = (Member) service.load(Member.class, course.getMember().getId());
						course.setMemberName(m.getNick());
						if (m.getRole().equals("E")) course.setUiType(4);
					}
				} else {
					list = service.findObjectBySql(
							"from Course cs where (cs.member.id = ? or cs.id in (select a.course.id from Apply a where a.member.id = ? and a.status = ?)) and cs.planDate between ? and ? and cs.share = '1'",
							new Object[] { toId, toId, "2", sDate, eDate });
					for (final Iterator<?> it = list.iterator(); it.hasNext();) {
						final Course course = (Course) it.next();
						course.setCanEdit(0);
						course.setCanJoin(0);
						course.setCanDel(0);
						course.setUiType(5);
						final Member m = (Member) service.load(Member.class, course.getMember().getId());
						course.setMemberName(m.getNick());
						if (m.getRole().equals("E")) course.setUiType(4);
					}
				}
			}
		}
		String jsons = "{evts:" + getJsonString(list) + "}";
		response(jsons);
	}

	/**
	 * 设置操作权限
	 * 
	 * @param list
	 * @param intPer
	 */
	private void setPermission(final List<?> list, final Integer canEdit, final Integer canJoin, final Integer canDel, final Integer uiType) {
		for (final Iterator<?> it = list.iterator(); it.hasNext();) {
			final Course course = (Course) it.next();
			course.setCanEdit(canEdit);
			course.setCanJoin(canJoin);
			course.setCanDel(canDel);
			course.setUiType(uiType);
		}
	}

	/**
	 * 某会员申请团体课程。 传入参数：course: Course,当前课程
	 */
	public void onJoin() {
		final JSONObject jobj = new JSONObject();
		try {
			course = (Course) service.load(Course.class, course.getId());
			if (course.getJoinNum() == course.getCount()) {
				response(jobj.accumulate("success", false).accumulate("message", "full").toString());
				return;
			}
			if (isExist(course)) {
				response(jobj.accumulate("success", false).accumulate("message", "joining").toString());
				return;
			}
			Member clubMember = (Member) session.getAttribute("toMember");
			if (clubMember == null) {
				clubMember = course.getMember();
			}
			if (clubMember == null) {
				response(jobj.accumulate("success", false).accumulate("message", "未知课程！").toString());
				return;
			}
			Member toMember = toMember();

			// 判断该会员预约的该课程时间段的是否还有其它课程
			final List<?> list = service.findObjectBySql(
					"from Course c where c.member.id = ? and c.planDate = ? and ((c.startTime = ? and c.endTime = ?) or (? between c.startTime and c.endTime) or (? between c.startTime and c.endTime))",
					toMember.getId(), course.getPlanDate(), course.getStartTime(), course.getEndTime(), course.getStartTime(), course.getEndTime());
			if (list.size() > 0) {
				response(jobj.accumulate("success", false).accumulate("message", "exist").toString());
				return;
			}
			// 是否会员
			boolean ismember = false;
			List<?> _list = service.findObjectBySql("select count(o.id) from Friend o where o.member.id = ? and o.friend.id = ?",
					new Object[] { toMember.getId(), clubMember.getId() });
			final Long hasOrder = (Long) _list.get(0);
			if (hasOrder != null && hasOrder > 0) {
				ismember = true;
			}
			Double price = ismember ? course.getMemberPrice() : course.getHourPrice();
			if (price > 0) {
				response(jobj.accumulate("success", true).accumulate("message", "pay").toString());
			} else {
				final Integer joinNum = service.saveRequest(course, toMember);
				response(jobj.accumulate("success", true).accumulate("message", "ok").accumulate("value", joinNum).toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("error", e);
			response(jobj.accumulate("success", false).accumulate("message", e.getMessage()).toString());
		}
	}

	/**
	 * 判断是否已经申请过，如果以前申请过，被拒绝则允许继续申请，如果申请中或申请成功，则不允许进行申请
	 * 
	 * @param course
	 * @return
	 */
	private boolean isExist(Course course) {
		boolean isExist = false;
		final Long requestMember = toMember().getId();
		for (Apply apply : course.getApplys()) {
			final String status = apply.getStatus();
			if (apply.getMember().getId().equals(requestMember) && (status.equals("1") || status.equals("2"))) {
				isExist = true;
				break;
			}
		}
		return isExist;
	}

	/**
	 * 判断是否私教关系
	 * 
	 * @return
	 */
	private boolean isPrivateEducation() {
		boolean canAdd = false;
		final Member toMember = (Member) session.getAttribute("toMember");
		if (toMember() != null) {
			final List<?> members = service.findObjectBySql("from Member m where (m.id = ? and m.coach.id = ?) or (m.id = ? and m.coach.id = ?)",
					toMember().getId(), toMember.getId(), toMember.getId(), toMember().getId());
			if (members.size() > 0) canAdd = true;
		}
		return canAdd;
	}

	/**
	 * 保存该课程，主要为会员在教练的课程上进行制订课程，并发送待审消息。
	 * 只有两种情况会出现保存功能，会员与私教，即会员访问教练及教练访问会员，并且是私教关系
	 */
	public void save() {
		final Member toMember = (Member) session.getAttribute("toMember");
		try {
			// 如果会员访问的是私教
			course.setColor("#333300");
			if (toMember.getRole().equals("S")) {
				course.setMember(toMember());
				course.setCoach(toMember);
				course.setPlanSource(PLAN_SOURCE_SELF);
			} else {// 如果私教访问的会员
				course.setMember(toMember);
				course.setCoach(toMember());
				course.setPlanSource(PLAN_SOURCE_COACH);
			}
			// 判断当前时间段的课程是否已经在用
			if (course.getId() == null) {
				final List<?> olds = service.findObjectBySql("from Course c where c.member.id = ? and c.planDate = ? and c.startTime = ? and c.endTime = ?",
						new Object[] { course.getMember().getId(), course.getPlanDate(), course.getStartTime(), course.getEndTime() });
				if (olds.size() > 0) {
					response("{success: false, desc: '当前日期的数据已经存在！'}");
					return;
				}
			}
			if (course.getId() != null && course.getId() > 0) {
				course = (Course) service.updateCourse(course, saveType);
			} else {
				course = (Course) service.saveCourse(course, saveType);
			}
			response("{success: true, id: " + course.getId() + ", color:'" + course.getColor() + "', coach:'" + course.getCoach().getName() + "'}");
		} catch (Exception e) {
			log.error("error", e);
			response("{success: false, desc: '" + e.getMessage() + "'}");
		}
	}

	/**
	 * 删除该课程，主要为会员在教练的课程进行删除操作，这里是否需要确定可以删除
	 */
	public void delete() {
		try {
			// 如果是会员进行删除，则需要进行判断：如果该课程为团体课，则删除为取消预约，否则是删除
			final Member m = toMember();
			if (m.getRole().equals("M")) {
				final List<?> list = service.findObjectBySql("from Member m where m.id in (select c.member from Course c where c.id = ?)", id);
				if (list.size() > 0) {
					final Member m1 = (Member) list.get(0);
					if (m1.getRole().equals("E")) {
						service.deleteApply(id, m.getId());
					} else {
						service.deleteCourse(id, saveType);
					}
				}
			} else {
				service.deleteCourse(id, saveType);
			}
			response("OK");
		} catch (Exception e) {
			response(e.getMessage());
		}
	}

	@Override
	protected String getExclude() {
		return "ticklings,courseGrades,works,grades,description,coachMembers,friends,clubs,coachs,members,visitors,setting,times,products,certificates,integrals,courses,alwaysAddrs,applys,coursess,records,courseCoachs,courseMembers,prods,appraises,appraises1,allFriends,orderDetails,orderDetails1,messages,messages1,orders,addrs,signIns,signIns1,shops,accounts,prodOrders,aos,actives,partakes,teams,tickets";
	}
}
