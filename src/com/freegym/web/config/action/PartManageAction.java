package com.freegym.web.config.action;

import java.util.List;

import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.freegym.web.BaseBasicAction;
import com.freegym.web.basic.Member;
import com.freegym.web.config.Part;
import com.freegym.web.config.Project;
import com.sanmen.web.core.common.LogicException;

@Namespace("")
@InterceptorRefs({ @InterceptorRef("foreStack") })
@Results({ @Result(name = "success", location = "/coach/part.jsp"),
	@Result(name= "content", location = "/coach/part_content.jsp"),
		@Result(name = "prev", location = "project.asp", type = "redirect"),
		@Result(name = "next", location = "action.asp", type = "redirect") })
public class PartManageAction extends BaseBasicAction {

	private static final long serialVersionUID = -3769475961884283745L;

	private List<Part> parts;

	private Long projectId;

	public List<Part> getParts() {
		return parts;
	}

	public void setParts(List<Part> parts) {
		this.parts = parts;
	}

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public void setProjects(List<Part> projects) {
		this.parts = projects;
	}

	public String execute() {
		session.setAttribute("spath", 8);
		Member m = toMember();
		//加载所有应用的项目
		List<Project> list = service.loadProjectsByApplied(m.getId());
		if (projectId == null && list.size() > 0) {
			Project p = list.get(0);
			projectId = p.getId();
		}
		request.setAttribute("projects", list);
		//加载该项目的所有部位
		loadParts();
		return SUCCESS;
	}
	
	@SuppressWarnings("unchecked")
	public String loadParts(){
		parts = (List<Part>) service.findObjectBySql("from Part p where p.project.id = ? and (p.member = ? or p.member = ?)" , new Object[]{projectId, toMember().getId() ,1l});
		return "content";
	}

	@SuppressWarnings("unchecked")
	public String save() {
		try {
			for (Part p : parts) {
				if (p.getMember() == null) p.setMember(toMember().getId());
			}
			parts = (List<Part>) service.saveOrUpdate(parts);
		} catch (Exception e) {
			log.error("保存部位信息失败！", e);
		}
		return "content";
	}

	public void delete() {
		try {
			service.delete(Part.class, ids);
		} catch (Exception e) {
			log.error("error", e);
		}
	}

	public String next() {
		try {
			service.saveOrUpdate(parts);
		} catch (LogicException e) {
			log.error("error", e);
		}
		return "next";
	}
	
	public String prev(){
		try {
			service.saveOrUpdate(parts);
		} catch (LogicException e) {
			log.error("error", e);
		}
		return "prev";
	}

}
