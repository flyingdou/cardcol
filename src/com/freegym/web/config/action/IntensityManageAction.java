package com.freegym.web.config.action;

import java.util.List;

import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.freegym.web.BaseBasicAction;
import com.freegym.web.basic.Member;
import com.freegym.web.config.Project;
import com.sanmen.web.core.common.LogicException;

@Namespace("")
@InterceptorRefs({ @InterceptorRef("foreStack") })
@Results({ @Result(name = "success", location = "/club/intensity.jsp"), @Result(name = "next", location = "rate.asp", type = "redirect"),
		@Result(name = "prev", location = "factory.asp", type = "redirect"),
		@Result(name = "content", location = "/club/intensity_content.jsp") })
public class IntensityManageAction extends BaseBasicAction {

	private static final long serialVersionUID = -3769475961884283745L;

	private Project project;

	private List<Project> intensitys;

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public List<Project> getIntensitys() {
		return intensitys;
	}

	public void setIntensitys(List<Project> intensitys) {
		this.intensitys = intensitys;
	}

	@SuppressWarnings("unchecked")
	public String execute() {
		Member member = toMember();
		intensitys = (List<Project>) service.findObjectBySql("from Project f where f.member = ?", member.getId());
		return SUCCESS;
	}

	@SuppressWarnings("unchecked")
	public String save() {
		Member m = toMember();
		try {
			project.setMember(m.getId());
			service.saveOrUpdate(project);
		} catch (LogicException e) {
			log.error("error", e);
		}
		intensitys = (List<Project>) service.findObjectBySql("from Project f where f.member = ?", m.getId());
		return "content";
	}

	public void delete() {
		try {
			service.delete(Project.class, ids);
		} catch (Exception e) {
			log.error("error", e);
		}
	}

	private void saveData() {
		try {
			service.saveOrUpdate(intensitys);
		} catch (Exception e) {
			log.error("error", e);
		}
	}

	public String next() {
		this.saveData();
		return "next";
	}

	public String prev() {
		this.saveData();
		return "prev";
	}
}
