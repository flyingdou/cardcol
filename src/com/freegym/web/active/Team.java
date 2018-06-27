package com.freegym.web.active;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import com.sanmen.web.core.bean.CommonId;

@Entity
@Table(name = "tb_active_team")
public class Team extends CommonId {

	private static final long serialVersionUID = -7761981418979991025L;

	/**
	 * 团队名称
	 */
	private String name;

	/**
	 * 团队成员明细
	 */
	@OneToMany(targetEntity = TeamMember.class, mappedBy = "team", cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@OrderBy("id desc")
	private Set<TeamMember> members = new HashSet<TeamMember>();

	public Team() {

	}

	public Team(Long id) {
		setId(id);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<TeamMember> getMembers() {
		return members;
	}

	public void setMembers(Set<TeamMember> members) {
		this.members = members;
	}

	@Override
	public String getTableName() {
		return "团队信息表";
	}

	public void addMember(TeamMember tm) {
		tm.setTeam(this);
		members.add(tm);
	}

}
