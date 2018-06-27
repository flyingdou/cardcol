package com.freegym.web.config.action;

import java.util.List;

import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.freegym.web.BaseBasicAction;
import com.freegym.web.basic.Member;
import com.freegym.web.config.Factory;
import com.sanmen.web.core.common.LogicException;

@Namespace("")
@InterceptorRefs({ @InterceptorRef("foreStack") })
@Results({ @Result(name = "success", location = "/club/factory.jsp"), @Result(name = "next", location = "mycourse.asp", type = "redirect"),
		@Result(name = "content", location = "/club/factory_content.jsp") })
public class FactoryManageAction extends BaseBasicAction {

	private static final long serialVersionUID = -3769475961884283745L;

	private List<Factory> factorys;

	public List<Factory> getFactorys() {
		return factorys;
	}

	public void setFactorys(List<Factory> factorys) {
		this.factorys = factorys;
	}

	@SuppressWarnings("unchecked")
	public String execute() {
		session.setAttribute("spath", 8);
		Member member = toMember();
		factorys = (List<Factory>) service.findObjectBySql("from Factory f where f.club = ?", member.getId());
		request.setAttribute("projects", service.findObjectBySql(
				" from Parameter p where p.parent = (select po.id from Parameter po where po.code = ? ) and p.viewType = '1' order by p.code asc",
				"course_type_c"));
		return SUCCESS;
	}

	@SuppressWarnings("unchecked")
	public String save() {
		Member m = toMember();
		this.saveData();
		factorys = (List<Factory>) service.findObjectBySql("from Factory f where f.club = ?", m.getId());
		request.setAttribute("projects", service.findObjectBySql(
				" from Parameter p where p.parent = (select po.id from Parameter po where po.code = ? ) and p.viewType = '1' order by p.code asc",
				"course_type_c"));
		return "content";
	}

	private void saveData() {
		Member m = toMember();
		try {
			if (factorys != null) {
				for (Factory f : factorys) {
					f.setClub(m.getId());
					if (f.getApplied() == null)
						f.setApplied('0');
				}
				service.saveFactory(factorys);
			}
		} catch (LogicException e) {
			log.error("error", e);
		}
	}

	public void delete() {
		try {
			service.delete(Factory.class, ids);
		} catch (Exception e) {
			log.error("error", e);
		}
	}

	public String next() {
		this.saveData();
		return "next";
	}

}
