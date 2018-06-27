package com.freegym.web.wap.action;

import java.text.ParseException;
import java.util.Iterator;
import java.util.List;

import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.freegym.web.basic.Member;
import com.freegym.web.mobile.action.MCourseManageAction;
import com.freegym.web.plan.Course;
import com.sanmen.web.core.bean.BaseMember;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Namespace("")
@InterceptorRefs({ @InterceptorRef("customStack") })
public class WCourseManageAction extends MCourseManageAction {

	private static final long serialVersionUID = -192010263447464619L;

	private Long member;

	private String _planDate;

	private String _type;

	public Long getMember() {
		return member;
	}

	public void setMember(Long member) {
		this.member = member;
	}

	public String get_planDate() {
		return _planDate;
	}

	public void set_planDate(String _planDate) {
		this._planDate = _planDate;
	}

	public String get_type() {
		return _type;
	}

	public void set_type(String _type) {
		this._type = _type;
	}

	@Override
	protected BaseMember getLoginMember() {
		// return (BaseMember) service.load(Member.class, 490l);
		return toMember();
	}

	public void switchYearMonth() {
		try {
			String status = service.findMonthPlanStatus(member, _planDate, _type);
			response(status);
		} catch (ParseException e) {
			e.printStackTrace();
			log.error("error", e);
		}
	}

	/**
	 * 读取教练课表
	 */
	public void findCoachCourse() {
		try {
			final DetachedCriteria dc = DetachedCriteria.forClass(Course.class);

			dc.add(Restrictions.eq("coach.id", id));

			if (_planDate != null && !"".equals(_planDate)) {
				dc.add(Restrictions.eq("planDate", _planDate));
			}

			pageInfo = service.findPageByCriteria(dc, pageInfo);

			final JSONArray jarr = new JSONArray();
			for (final Iterator<?> it = pageInfo.getItems().iterator(); it.hasNext();) {
				final Course course = (Course) it.next();
				jarr.add(course.toJson());
			}
			final JSONObject ret = new JSONObject();
			ret.accumulate("success", true).accumulate("pageInfo", getJsonForPageInfo()).accumulate("items", jarr);
			response(ret);
		} catch (Exception e) {
			log.error("error", e);
			response(e);
		}
	}

	public void listCourse() {
		try {
			List<?> list = null;
			Member m = (Member) service.load(Member.class, id);
			if (m.getRole().equals("M")) { // 会员
				list = service.findObjectBySql(
						"from Course cs where (cs.member.id = ? or cs.id in (select a.course.id from Apply a where a.member.id = ? and a.status = ?)) and cs.planDate = ? order by cs.planDate, cs.startTime",
						new Object[] { id, id, "2", _planDate });

			} else if (m.getRole().equals("S")) { // 教练
				list = service.findObjectBySql("from Course cs where cs.coach.id = ? and cs.planDate = ? order by cs.planDate, cs.startTime",
						new Object[] { id, _planDate });
			}
			final JSONArray jarr = new JSONArray();
			final JSONObject ret = new JSONObject();
			for (final Iterator<?> it = list.iterator(); it.hasNext();) {
				final Course c = (Course) it.next();
				final JSONObject obj = new JSONObject();
				obj.accumulate("id", c.getId()).accumulate("member", getMemberJson(c.getMember())).accumulate("coach", getMemberJson(c.getCoach()))
						.accumulate("planDate", c.getPlanDate()).accumulate("startTime", c.getStartTime()).accumulate("endTime", c.getEndTime())
						.accumulate("courseId", c.getCourseInfo().getId()).accumulate("course", c.getCourseInfo().getName())
						.accumulate("place", getString(c.getPlace())).accumulate("count", getInteger(c.getCount()))
						.accumulate("joinNum", getInteger(c.getJoinNum()));
				jarr.add(obj);
			}
			ret.accumulate("success", true).accumulate("items", jarr);
			response(ret);
		} catch (Exception e) {
			log.error("error", e);
			response(e);
		}
	}

}
