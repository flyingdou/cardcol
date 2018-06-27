package com.freegym.web.config.action;

import java.util.List;

import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.freegym.web.BaseBasicAction;
import com.freegym.web.basic.Member;
import com.freegym.web.config.WorkTime;
import com.sanmen.web.core.utils.DateUtil;
import com.sanmen.web.core.utils.StringUtils;

@Namespace("")
@InterceptorRefs({ @InterceptorRef("foreStack") })
@Results({ @Result(name = "success", location = "/coach/style.jsp"), 
	@Result(name = "next", location = "mycourse.asp", type = "redirect") })
public class StyleManageAction extends BaseBasicAction {

	private static final long serialVersionUID = -3769475961884283745L;

	private Member member;

	private List<WorkTime> worktimes;

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public List<WorkTime> getWorktimes() {
		return worktimes;
	}

	public void setWorktimes(List<WorkTime> worktimes) {
		this.worktimes = worktimes;
	}

	@SuppressWarnings("unchecked")
	public String execute() {
		session.setAttribute("spath", 8);
		member = toMember();
//		if (member.getSpeciality() == null || "".equals(member.getSpeciality())) member.setSpeciality("A");
//		if (member.getMode() == null || "".equals(member.getMode())) member.setMode("A");
//		if (member.getStyle() == null || "".equals(member.getStyle())) member.setStyle("A");
		List<String> list = StringUtils.stringToList(member.getSpeciality());
		List<String> list1 = StringUtils.stringToList(member.getMode());
		List<String> list2 = StringUtils.stringToList(member.getStyle());
		request.setAttribute("list", list);
		request.setAttribute("list1", list1);
		request.setAttribute("list2", list2);
		worktimes = (List<WorkTime>) service.findObjectBySql("from WorkTime wt where wt.member = ?" ,member.getId());
		request.setAttribute("times", DateUtil.getAllTimeForDay());
		request.setAttribute("workDates", StringUtils.stringToList(member.getWorkDate()));
		request.setAttribute("targets", service.findParametersByCodes("plan_type_c"));
		return SUCCESS;
	}

	public String next() {
		Member m = toMember();
		try {
			m.setSpeciality(member.getSpeciality());
			m.setMode(member.getMode());
			m.setStyle(member.getStyle());
			m.setWorkDate(member.getWorkDate());
			member = service.saveWorkTime(m, worktimes);
			session.setAttribute(LOGIN_MEMBER, member);
		} catch (Exception e) {
			log.error("保存项目信息失败！", e);
		}
		return "next";
	}

}
