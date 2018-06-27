package com.freegym.web.basic.action;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.freegym.web.BaseBasicAction;
import com.freegym.web.active.Active;
import com.freegym.web.active.TrainRecord;
import com.freegym.web.basic.Friend;
import com.freegym.web.basic.Member;
import com.freegym.web.config.CourseInfo;
import com.freegym.web.course.BaseAppraise;
import com.freegym.web.plan.Course;
import com.freegym.web.plan.PlanRelease;

@Namespace("")
@InterceptorRefs({ @InterceptorRef("customStack") })
@Results({ @Result(name = "success", location = "/coach/index.jsp") })
public class CoachManageAction extends BaseBasicAction {

	private static final long serialVersionUID = -3769475961884283745L;
	private Member member;
	private String day1;
	private String day2;
	private String day3;
	private TrainRecord record;

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public String getDay1() {
		return day1;
	}

	public void setDay1(String day1) {
		this.day1 = day1;
	}

	public String getDay2() {
		return day2;
	}

	public void setDay2(String day2) {
		this.day2 = day2;
	}

	public String getDay3() {
		return day3;
	}

	public void setDay3(String day3) {
		this.day3 = day3;
	}

	public TrainRecord getRecord() {
		return record;
	}

	public void setRecord(TrainRecord record) {
		this.record = record;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String execute() {
		if (member != null && member.getId() != null) {
			member = (Member) service.load(Member.class, member.getId());
			if (this.toMember() != null) {
				final List<?> friendList = service.findObjectBySql(" from Friend f where f.member.id = ? and f.friend.id = ? and f.type = ?", new Object[] {
						member.getId(), this.toMember().getId(), "4" });
				if (friendList == null || friendList.size() == 0) {
					Date nowDate = new Date();
					service.saveOrUpdate(new Friend(member, toMember(), nowDate, "4", "0", nowDate));
				}
			}
			session.setAttribute("wpath", 1);
			session.setAttribute("toMember", member);
		} else {
			if (toMember() == null)
				return "login";
			member = (Member) service.load(Member.class, this.toMember().getId());
			session.setAttribute("spath", 1);
		}
		// 查询评价人数以及评分
		StringBuffer memberSql = new StringBuffer(Member.getMemberSql(null, null, null, null, null, null, member.getCity()));
		memberSql.append(" and m.id = ?");
		final List<?> memberList = service.queryForList(memberSql.toString(), member.getId());
		for (final Iterator<?> it = memberList.iterator(); it.hasNext();) {
			Map map = (Map) it.next();
			member.setCountEmp(new Integer(map.get("countEmp").toString()));
			member.setAvgGrade(new Integer(map.get("member_grade").toString().split("\\.")[0]));
		}
		// 目前记录
		record = service.getRecordByDate(member.getId(), new Date());
		for (final CourseInfo ci : member.getCourses()) {
			Double grade = 0d;
			Integer empNum = 0;
			for (final Course c : ci.getCourses()) {
				grade += c.getCountGrade() == null ? 0d : c.getCountGrade();
				empNum += c.getAppraiseCount() == null ? 0 : c.getAppraiseCount();
			}
			ci.setGrade(grade);
			ci.setGradeNum(empNum);
		}
		request.setAttribute("yyxms", service.findActionByMode(1l, '0'));
		StringBuffer sql = new StringBuffer(Active.getActiveSql(null, null, null));
		// 健身挑战
		sql.append(" and a.creator = ?");
		sql.append(" limit 2");
		request.setAttribute("actives", service.queryForList(sql.toString(), member.getId()));
		request.setAttribute("buyActives", service.findActiveByMember(member.getId()));
		// 淘课吧
		SimpleDateFormat sdf1 = new SimpleDateFormat("YYYY-MM-dd");
		SimpleDateFormat sdf2 = new SimpleDateFormat("MM.dd");
		day1 = sdf1.format(new Date());
		String day1Show = sdf2.format(new Date());
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_YEAR, 1);
		Date date = calendar.getTime();
		day2 = sdf1.format(date);
		String day2Show = sdf2.format(date);
		calendar.add(Calendar.DAY_OF_YEAR, 1);
		date = calendar.getTime();
		day3 = sdf1.format(date);
		String day3Show = sdf2.format(date);
		request.setAttribute("courseList1",
				service.findObjectBySql("from Course c  where c.planDate =? and c.member.id =? order by c.costs desc ", day1, member.getId()));
		request.setAttribute("courseList2",
				service.findObjectBySql("from Course c  where c.planDate =? and c.member.id =? order by c.costs desc ", day2, member.getId()));
		request.setAttribute("courseList3",
				service.findObjectBySql("from Course c  where c.planDate =? and c.member.id =? order by c.costs desc ", day3, member.getId()));
		day1 = day1Show;
		day2 = day2Show;
		day3 = day3Show;

		// 服务项目
		List list = service.queryForList(BaseAppraise.getcourseInfoAppraise() + " where a.member = ?", member.getId());
		for (final Iterator<?> it = list.iterator(); it.hasNext();) {
			Map p = (Map) it.next();
			p.put("avg_grade", p.get("avg_grade")==null?0:Integer.valueOf((String) p.get("avg_grade")));
		}
		request.setAttribute("courses", list);
		request.setAttribute("certs", service.findObjectBySql("from Certificate c where c.member = ?", member.getId()));
		String planSql = "select * from (" + PlanRelease.getPlanSql() + ") t where t.member = ? and t.audit=? and t.isClose=? order by t.publishTime desc limit 3";
		request.setAttribute("plans", service.queryForList(planSql, member.getId(),1,2));
		return SUCCESS;
	}

}
