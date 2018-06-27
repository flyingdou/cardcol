package com.freegym.web.config.action;

import java.util.ArrayList;
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

@Namespace("")
@InterceptorRefs({ @InterceptorRef("foreStack") })
@Results({ @Result(name = "success", location = "/coach/worktime.jsp"), @Result(name = "content", location = "/coach/worktime_content.jsp"),
		@Result(name = "edit", location = "/coach/action_edit.jsp"), @Result(name = "prev", location = "style.asp", type = "redirect"),
		@Result(name = "next", location = "project.asp", type = "redirect") })
public class WorktimeManageAction extends BaseBasicAction {

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
		session.setAttribute("spath", 6);
		member = toMember();
		worktimes = (List<WorkTime>) service.findObjectBySql("from WorkTime wt where wt.member = ?" ,member.getId());
		request.setAttribute("times", DateUtil.getAllTimeForDay());
		List<String> arrs = new ArrayList<String>();
		if (member.getWorkDate() != null) {
			String[] dates = member.getWorkDate().split(",");
			for (int i = 0; i < dates.length; i++) {
				arrs.add(dates[i].trim());
			}
		}
		request.setAttribute("list", arrs);
		return SUCCESS;
	}

	@SuppressWarnings("unchecked")
	public String save() {
		this.ajaxSave();
		worktimes = (List<WorkTime>) service.findObjectBySql("from WorkTime wt where wt.member = ?" ,member.getId());
		request.setAttribute("times", DateUtil.getAllTimeForDay());
		List<String> arrs = new ArrayList<String>();
		if (member.getWorkDate() != null) {
			String[] dates = member.getWorkDate().split(",");
			for (int i = 0; i < dates.length; i++) {
				arrs.add(dates[i].trim());
			}
		}
		request.setAttribute("list", arrs);
		return "content";
	}
	
	private void ajaxSave(){
		try{
			member = service.saveWorkTime(member, worktimes);
			session.setAttribute(LOGIN_MEMBER, member);
		}catch(Exception e) {
			log.error("error", e);
		}
	}

	public void delete() {
		try {
			service.delete(WorkTime.class, ids);
		} catch (Exception e) {
			log.error("error", e);
		}
	}

	public String next() {
		this.ajaxSave();
		return "next";
	}

	public String prev() {
		this.ajaxSave();
		return "prev";
	}

}
