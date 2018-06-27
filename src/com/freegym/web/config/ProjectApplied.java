package com.freegym.web.config;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.sanmen.web.core.bean.CommonId;

@Entity
@Table(name = "TB_PROJECT_APPLIED")
public class ProjectApplied extends CommonId {

	private static final long serialVersionUID = 3902026960530735407L;

	@Column(nullable = false)
	private Long member;

	@ManyToOne(targetEntity = Project.class)
	@JoinColumn(name = "project", nullable = false)
	private Project project;

	private Character applied;

	private Integer sort;

	public Long getMember() {
		return member;
	}

	public void setMember(Long member) {
		this.member = member;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public Character getApplied() {
		return applied;
	}

	public void setApplied(Character applied) {
		this.applied = applied;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	@Override
	public String getTableName() {
		// TODO Auto-generated method stub
		return null;
	}

}
