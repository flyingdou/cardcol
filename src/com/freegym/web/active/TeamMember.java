package com.freegym.web.active;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.freegym.web.basic.Member;
import com.sanmen.web.core.bean.CommonId;

@Entity
@Table(name = "tb_active_team_member")
public class TeamMember extends CommonId {

	private static final long serialVersionUID = -7761981418979991025L;

	/**
	 * 团队主表
	 */
	@ManyToOne(targetEntity = Team.class)
	@JoinColumn(name = "team")
	private Team team;

	/**
	 * 成员信息
	 */
	@ManyToOne(targetEntity = Member.class)
	@JoinColumn(name = "member")
	private Member member;

	/**
	 * 状态，0为邀请中，1为正式成员
	 */
	private Character status;
	
	public TeamMember(){
		
	}
	
	public TeamMember(Member member){
		this.member = member;
	}

	public Team getTeam() {
		return team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public Character getStatus() {
		return status;
	}

	public void setStatus(Character status) {
		this.status = status;
	}

	@Override
	public String getTableName() {
		return "团队成员信息表";
	}

}
