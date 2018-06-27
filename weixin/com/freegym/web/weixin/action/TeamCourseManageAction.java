package com.freegym.web.weixin.action;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.freegym.web.BaseBasicAction;
import com.freegym.web.basic.Member;

@Namespace("")
@InterceptorRefs({ @InterceptorRef("customStack") })
@Results({ @Result(name = "login", location = "/WX/login.jsp"),
		@Result(name = "success", location = "/eg/myClass.jsp") })
public class TeamCourseManageAction extends BaseBasicAction {

	private static final long serialVersionUID = 1L;
	/**
	   * 
	   */
	private Member member;

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public String execute() {
		member = (Member) session.getAttribute("member");
		if (member == null) {
			return "login";
		} else {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date date = new Date();
			List<Map<String, Object>> mycourse = service.queryForList(
					"select * from tb_course c where c.member=? and c.planDate=?",
					new Object[] { 454, sdf.format(date) });
			request.setAttribute("courses", mycourse);
			@SuppressWarnings("rawtypes")
			List li = service.queryForList(
					"select * from tb_member tm where tm.id=?  union all  select * from tb_member m where m.id in(select mf.friend from tb_member_friend mf where mf.member=?)",
					new Object[] { 454, 454 });
			request.setAttribute("friends", li);
			return SUCCESS;
		}
	}
}
