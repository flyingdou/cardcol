package com.freegym.web.system;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.hibernate.criterion.DetachedCriteria;

import com.sanmen.web.core.bean.CommonId;


@Entity
@Table(name = "TB_CONTRACT_TEMPLATE")
public class Template extends CommonId {

	private static final long serialVersionUID = 6344260216330713277L;

	@Column(length = 20)
	private String name;

	@Lob
	@Basic
	private String template;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

	public static DetachedCriteria getCriteriaQuery(Template query) {
		DetachedCriteria dc = DetachedCriteria.forClass(Template.class);
		return dc;
	}

	@Override
	public String getTableName() {
		// TODO Auto-generated method stub
		return null;
	}

}
