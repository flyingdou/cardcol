package com.freegym.web.config;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import net.sf.json.JSONObject;

import org.hibernate.criterion.DetachedCriteria;

import com.sanmen.web.core.bean.CommonId;

@Entity
@Table(name = "TB_PROJECT")
public class Project extends CommonId {

	private static final long serialVersionUID = -1066246753544954163L;

	private Long member;

	@Column(length = 50)
	private String name;

	@Column(length = 10)
	private String shortName;

	private Character mode; // 0:有氧，1：力量，3：柔韧，4：身心

	private Character system;

	private Integer intensity; // 强度
	@Lob
	@Basic
	private String memo;

	@OneToMany(targetEntity = ProjectApplied.class, cascade = CascadeType.REMOVE, fetch = FetchType.LAZY, mappedBy = "project")
	private Set<ProjectApplied> applies = new HashSet<ProjectApplied>();

	@OneToMany(targetEntity = Part.class, cascade = CascadeType.REMOVE, fetch = FetchType.LAZY, mappedBy = "project")
	private Set<Part> parts = new HashSet<Part>();

	@Transient
	private Character applied;

	@Transient
	private Integer sort;

	public Long getMember() {
		return member;
	}

	public void setMember(Long member) {
		this.member = member;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public Character getMode() {
		return mode;
	}

	public void setMode(Character mode) {
		this.mode = mode;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public Set<ProjectApplied> getApplies() {
		return applies;
	}

	public void setApplies(Set<ProjectApplied> applies) {
		this.applies = applies;
	}

	public Set<Part> getParts() {
		return parts;
	}

	public void setParts(Set<Part> parts) {
		this.parts = parts;
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

	public Character getSystem() {
		return system;
	}

	public void setSystem(Character system) {
		this.system = system;
	}

	public Integer getIntensity() {
		return intensity;
	}

	public void setIntensity(Integer intensity) {
		this.intensity = intensity;
	}

	public static DetachedCriteria getCriteriaQuery(Project query) {
		DetachedCriteria dc = DetachedCriteria.forClass(Project.class);
		return dc;
	}

	@Override
	public String getTableName() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public JSONObject toJson() {
		final JSONObject obj = new JSONObject();
		obj.accumulate("id", getId()).accumulate("name", getName()).accumulate("mode", getMode()).accumulate("intensity", getIntensity());
		return obj;
	}

}
