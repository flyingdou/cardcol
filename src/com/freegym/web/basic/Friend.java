package com.freegym.web.basic;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.sanmen.web.core.bean.CommonId;

@Entity
@Table(name = "TB_MEMBER_FRIEND")
public class Friend extends CommonId {

	private static final long serialVersionUID = -7003227477637796819L;
	@ManyToOne(targetEntity = Member.class)
	@JoinColumn(name = "member")
	private Member member;

	@ManyToOne(targetEntity = Member.class)
	@JoinColumn(name = "friend")
	private Member friend;

	@Temporal(TemporalType.TIMESTAMP)
	private Date joinTime; // 加入时间/访问时间

	// 1：我的俱乐部，4：访客
	// 错误：由审核表审核同过加入时，同时产生两方的信息，如：申请好友时，同时加入申请方，和被申请方的两条数据
	// 会员加入俱乐部时，type=1,member = 会员，friend = 俱乐部
	// 教练加入俱乐部时，type=1,member = 教练，friend = 俱乐部
	// 俱乐部加入俱乐部时，type=1,member = 加入俱乐部，friend = 被加入俱乐部
	// A访问B，type=4，friend=A,member=B
	//
	// 所以type=2,3不存在
	@Column(length = 1)
	private String type;

	private String isCore;// 是否为核心俱乐部0:非核心，1核心

	@Temporal(TemporalType.TIMESTAMP)
	private Date topTime;// 置顶时间

	public String getIsCore() {
		return isCore;
	}

	public void setIsCore(String isCore) {
		this.isCore = isCore;
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public Member getFriend() {
		return friend;
	}

	public void setFriend(Member friend) {
		this.friend = friend;
	}

	public Date getJoinTime() {
		return joinTime;
	}

	public void setJoinTime(Date joinTime) {
		this.joinTime = joinTime;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Date getTopTime() {
		return topTime;
	}

	public void setTopTime(Date topTime) {
		this.topTime = topTime;
	}

	public Friend() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Friend(Member member, Member friend, Date joinTime, String type, String isCore, Date topTime) {
		super();
		this.member = member;
		this.friend = friend;
		this.joinTime = joinTime;
		this.type = type;
		this.isCore = isCore;
		this.topTime = topTime;
	}

	@Override
	public String getTableName() {
		// TODO Auto-generated method stub
		return null;
	}

}
