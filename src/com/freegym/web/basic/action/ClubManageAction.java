package com.freegym.web.basic.action;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.freegym.web.BaseBasicAction;
import com.freegym.web.active.Active;
import com.freegym.web.basic.Friend;
import com.freegym.web.basic.Member;
import com.freegym.web.config.CourseInfo;
import com.freegym.web.course.BaseAppraise;
import com.freegym.web.order.Product;
import com.freegym.web.plan.Course;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Namespace("")
@InterceptorRefs({ @InterceptorRef("customStack") })
@Results({ @Result(name = "success", location = "/club/index.jsp") })
public class ClubManageAction extends BaseBasicAction {

	private static final long serialVersionUID = -3769475961884283745L;
	private Member member;
	private String day1;
	private String day2;
	private String day3;

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

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String execute() {
		if (member != null && member.getId() != null) {
			member = (Member) service.load(Member.class, member.getId());
			if (this.toMember() != null) {
				List friendList = service.findObjectBySql(
						" from Friend f where f.member.id = ? and f.friend.id = ? and f.type = ?",
						new Object[] { member.getId(), this.toMember().getId(), "4" });
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
		StringBuffer memberSql = new StringBuffer(
				Member.getMemberSql(null, null, null, null, null, null, member.getCity()));
		memberSql.append(" and m.id = ?");
		final List<?> memberList = service.queryForList(memberSql.toString(), member.getId());
		Random random = new Random();
		for (final Iterator<?> it = memberList.iterator(); it.hasNext();) {
			Map map = (Map) it.next();
			Long object = (Long) map.get("countEmp");
			if (null == object || object.equals(0l)) {
				int i = random.nextInt(10) + 1;
				map.put("countEmp", i);
				map.put("member_grade", random.nextInt(100 - 80 + 1) + 80);
			}
			member.setCountEmp(new Integer(map.get("countEmp").toString()));
			member.setAvgGrade(new Integer(map.get("member_grade").toString().split("\\.")[0]));
		}
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
		// 健身挑战
		StringBuffer sql = new StringBuffer(Active.getActiveSql2(null, null, null));
		sql.append(" and a.creator = ?");
		sql.append(" limit 2");
		request.setAttribute("actives", service.queryForList(sql.toString(), member.getId()));
		request.setAttribute("coachs", service.findObjectBySql(
				" from Friend f where f.friend.id = ? and f.member.role = ? and f.type = ? and f.topTime is not null order by topTime desc ",
				new Object[] { member.getId(), "S", "1" }));
		// 营业时间
		List<?> workTimeList = service.findObjectBySql("from WorkTime wt where wt.member = ? ", member.getId());
		request.setAttribute("workTime", workTimeList.size() > 0 ? workTimeList.get(0) : null);

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
		request.setAttribute("courseList1", service.findObjectBySql(
				"from Course c  where c.planDate =? and c.member.id =? and c.startTime > ? order by c.startTime ", day1,
				member.getId(), new SimpleDateFormat("HH:mm").format(new Date())));
		request.setAttribute("courseList2", service.findObjectBySql(
				"from Course c  where c.planDate =? and c.member.id =? order by c.startTime ", day2, member.getId()));
		request.setAttribute("courseList3", service.findObjectBySql(
				"from Course c  where c.planDate =? and c.member.id =? order by c.startTime ", day3, member.getId()));
		day1 = day1Show;
		day2 = day2Show;
		day3 = day3Show;

		// 服务项目
		List list = service.queryForList(BaseAppraise.getcourseInfoAppraise() + " where a.member = ?  GROUP BY a.id",
				member.getId());
		for (final Iterator<?> it = list.iterator(); it.hasNext();) {
			Map p = (Map) it.next();
			p.put("avg_grade", p.get("avg_grade") == null ? 0 : Integer.valueOf((String) p.get("avg_grade")));
		}
		request.setAttribute("courses", list);

		JSONArray jsonArr = new JSONArray();
		for (Iterator it = member.getProducts().iterator(); it.hasNext();) {
			Product product = (Product) it.next();
			JSONObject json = new JSONObject();
			json.accumulate("id", product.getId()).accumulate("name", product.getName()).accumulate("image",
					product.getImage1());
			jsonArr.add(json);
		}
		request.setAttribute("products", jsonArr);
		return SUCCESS;
	}
}
