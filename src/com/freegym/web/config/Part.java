package com.freegym.web.config;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.sanmen.web.core.bean.CommonId;

@Entity
@Table(name = "TB_PROJECT_PART")
public class Part extends CommonId {

	private static final long serialVersionUID = -726229232265801449L;

	@Column(nullable = false)
	private Long member;

	@ManyToOne(targetEntity = Project.class, fetch = FetchType.EAGER)
	@JoinColumn(name = "project")
	private Project project;

	@Column(length = 50)
	private String name;

	@Column(length = 30)
	private String mgroup;

	@Column(length = 30)
	private String marea;

	private Character system;

	@OneToMany(targetEntity = Action.class, mappedBy = "part", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	private Set<Action> actions = new HashSet<Action>();

	public Part() {

	}

	public Part(Long id) {
		this.setId(id);
	}

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMgroup() {
		return mgroup;
	}

	public void setMgroup(String mgroup) {
		this.mgroup = mgroup;
	}

	public String getMarea() {
		return marea;
	}

	public void setMarea(String marea) {
		this.marea = marea;
	}

	public Character getSystem() {
		return system;
	}

	public void setSystem(Character system) {
		this.system = system;
	}

	public Set<Action> getActions() {
		return actions;
	}

	public void setActions(Set<Action> actions) {
		this.actions = actions;
	}

	@Override
	public String getTableName() {
		// TODO Auto-generated method stub
		return null;
	}

}
