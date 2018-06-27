package com.freegym.web.config.action;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.freegym.web.BaseBasicAction;
import com.freegym.web.basic.Member;
import com.freegym.web.config.Project;
import com.freegym.web.config.ProjectApplied;
import com.sanmen.web.core.common.LogicException;

@Namespace("")
@InterceptorRefs({ @InterceptorRef("foreStack") })
@Results({ @Result(name = "success", location = "/coach/project.jsp"), @Result(name = "content", location = "/coach/project_content.jsp"),
		@Result(name = "prev", location = "mycourse.asp", type = "redirect"), @Result(name = "next", location = "part.asp", type = "redirect") })
public class ProjectManageAction extends BaseBasicAction {

	private static final long serialVersionUID = -3769475961884283745L;

	private Project project;

	private List<Project> projects;

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public List<Project> getProjects() {
		return projects;
	}

	public void setProjects(List<Project> projects) {
		this.projects = projects;
	}

	public String execute() {
		session.setAttribute("spath", 8);
		final Member m = toMember();
		projects = service.loadProjects1(m.getId());
		if (projects.size() <= 0) {
			final List<?> list = service.findObjectBySql("from Project p where p.member = ?", 1l);
			Project p = null;
			ProjectApplied pa = null;
			final List<ProjectApplied> pas = new ArrayList<ProjectApplied>();
			int i = 0;
			for (final Iterator<?> it = list.iterator(); it.hasNext(); i++) {
				p = (Project) it.next();
				pa = new ProjectApplied();
				pa.setProject(p);
				pa.setApplied('1');
				pa.setMember(m.getId());
				pa.setSort(i);
				pas.add(pa);
			}
			service.saveOrUpdate(pas);
			projects = service.loadProjects(m.getId());
		}
		return SUCCESS;
	}
	
	public String loadContent() {
		final Member m = toMember();
		projects = service.loadProjects(m.getId());
		return "content";
	}

	public void save() {
		final Member m = toMember();
		final StringBuffer sb = new StringBuffer();
		try {
			project = service.saveProject(m.getId(), project);
			sb.append("{'success': true, 'message': '当前数据已经成功保存！'}");
		} catch (Exception e) {
			log.error("保存项目信息失败！", e);
			sb.append("{'success': false, 'message': '" + e.getMessage() + "'}");
		}
		final StringBuffer sb1 = new StringBuffer("<script type='text/javascript'>");
		sb1.append("parent.loadData(" + sb.toString() + ");");
		sb1.append("</script>");
		response(sb1.toString());
	}

	public void delete() {
		try {
			service.delete(Project.class, ids);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("error", e);
		}
	}

	public String prev() {
		Member m = toMember();
		try {
			service.saveApplied(m.getId(), projects);
		} catch (LogicException e) {
			log.error("error", e);
		}
		return "prev";
	}

	public String next() {
		Member m = toMember();
		try {
			service.saveApplied(m.getId(), projects);
		} catch (LogicException e) {
			log.error("error", e);
		}
		return "next";
	}

}
