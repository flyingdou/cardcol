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
@Table(name = "TB_MEMBER_INTEGRAL")
public class Integral extends CommonId {

	private static final long serialVersionUID = 1336292809407932952L;
	@ManyToOne(targetEntity = Member.class)
	@JoinColumn(name = "member")
	private Member member;

	private Double integral;// 积分

	@Column(length = 1)
	private String inteSource;// 积分来源，1:注册,2:首次登录,3:购买套餐（私教课时）,4:完成一次训练,5:撰写一次评价,6:每登录一次,7:一周未登录（扣分）

	@Temporal(TemporalType.TIMESTAMP)
	private Date inteTime; // 积分生效时间

	public String getInteSource() {
		return inteSource;
	}

	public void setInteSource(String inteSource) {
		this.inteSource = inteSource;
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public Double getIntegral() {
		return integral;
	}

	public void setIntegral(Double integral) {
		this.integral = integral;
	}

	public Date getInteTime() {
		return inteTime;
	}

	public void setInteTime(Date inteTime) {
		this.inteTime = inteTime;
	}

	@Override
	public String getTableName() {
		// TODO Auto-generated method stub
		return null;
	}

}
