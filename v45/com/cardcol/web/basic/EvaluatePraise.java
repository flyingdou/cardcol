package com.cardcol.web.basic;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.freegym.web.basic.Member;
import com.sanmen.web.core.bean.CommonId;

@Entity
@Table(name="TB_MEMBER_EVALUATE_PRAISE")
public class EvaluatePraise extends CommonId {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 点赞时间
	 */
	@Column(name="time")
	private Date time;
	
	/**
	 * 点赞用户
	 */
	@ManyToOne(targetEntity = Member.class)
	@JoinColumn(name = "member")
	private Member member;
	
    /**
     * 被点赞的评论
     */
	@ManyToOne(targetEntity = MemberEvaluate.class)
	@JoinColumn(name = "evaluate")
	private MemberEvaluate evaluate;
	

	public Date getTime() {
		return time;
	}


	public void setTime(Date time) {
		this.time = time;
	}


	public Member getMember() {
		return member;
	}


	public void setMember(Member member) {
		this.member = member;
	}


	public MemberEvaluate getEvaluate() {
		return evaluate;
	}


	public void setEvaluate(MemberEvaluate evaluate) {
		this.evaluate = evaluate;
	}


	@Override
	public String getTableName() {
		
		return null;
	}

}
