package com.freegym.web.config.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
@Results({ @Result(name = "success", location = "/club/open.jsp"),
		@Result(name = "next", location = "done1.asp", type = "redirect"),
		@Result(name = "prev", location = "rate.asp", type = "redirect") })
public class OpenManageAction extends BaseBasicAction {

	private static final long serialVersionUID = -3769475961884283745L;

	private String workDate;

	private List<WorkTime> worktimes;

	public String getWorkDate() {
		return workDate;
	}

	public void setWorkDate(String workDate) {
		this.workDate = workDate;
	}

	public List<WorkTime> getWorktimes() {
		return worktimes;
	}

	public void setWorktimes(List<WorkTime> worktimes) {
		this.worktimes = worktimes;
	}

	public String execute() {
		session.setAttribute("spath", 8);
		Member m = (Member) service.load(Member.class, toMember().getId());
		workDate = m.getWorkDate();
		List<String> list = new ArrayList<String>();
		if (workDate != null) {
			String[] arrs = workDate.split(",");
			for (int i = 0; i < arrs.length; i++)
				list.add(arrs[i].trim());
		}
		request.setAttribute("list", list);
		Map<String, String> allTimeForDay = DateUtil.getAllTimeForDay();
		request.setAttribute("times", allTimeForDay);
		worktimes = new ArrayList<WorkTime>();
		worktimes.addAll(m.getTimes());

		String querySql = "select IFNULL(t.msn, 0) signTime from ("
				+ "select msn,count(id) from tb_member where id = ?) t";
		long signTime = service.queryForLong(querySql, m.getId());
		request.setAttribute("signTime", signTime);
		return SUCCESS;
	}

	private void save() {
		Member m = (Member) service.load(Member.class, toMember().getId());
		m.setWorkDate(workDate);
		service.saveOrUpdate(m);
		for (WorkTime wt : worktimes)
			wt.setMember(toMember().getId());
		service.saveOrUpdate(worktimes);

		String signTime = request.getParameter("signTime");
		String updateSql = "update tb_member set msn = " + signTime + " where id = " + m.getId();
		service.executeUpdate(updateSql);
	}

	public String next() {
		save();
		return "next";
	}

	public String prev() {
		save();
		return "prev";
	}
}
