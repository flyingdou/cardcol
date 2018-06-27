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
@Table(name = "TB_MEMBER_EVALUATE_REPLY")
public class EvaluateReply extends CommonId {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4376465919018011131L;
	
	
	/**
	 * 回复时间
	 */
	@Column(name = "time")
    private Date time;
	
	/**
	 * 回复内容
	 */
	@Column(name = "content")
	private String content;
	
	/**
	 * 回复用户（回复者）
	 */
	@ManyToOne(targetEntity = Member.class)
	@JoinColumn(name = "member")
	private Member member;
	
	/**
	 * 被回复的评论
	 */
	@ManyToOne(targetEntity = MemberEvaluate.class)
	@JoinColumn(name = "evaluate")
	private MemberEvaluate evaluate;
	
	/**
	 * 父回复
	 */
	@ManyToOne(targetEntity = EvaluateReply.class)
	@JoinColumn(name = "parent")
	private EvaluateReply parent;
	
	/**
	 * 被回复者
	 */
	@ManyToOne(targetEntity = Member.class)
	@JoinColumn(name = "replyTo")
	private Member replyTo;
	
	

	/**
	 * getter and setter
	 */
	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
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

	
	public EvaluateReply getParent() {
		return parent;
	}

	public void setParent(EvaluateReply parent) {
		this.parent = parent;
	}

	public Member getReplyTo() {
		return replyTo;
	}

	public void setReplyTo(Member replyTo) {
		this.replyTo = replyTo;
	}
	

	@Override
	public String getTableName() {
		return null;
	}

}
