package com.freegym.web.basic;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.criterion.DetachedCriteria;

import com.freegym.web.basic.Member;
import com.freegym.web.system.Ticket;
import com.sanmen.web.core.bean.CommonId;

/**
 * 会员关联优惠券表
 * 
 * @author Admin
 * 
 */
@Entity
@Table(name = "tb_member_ticket")
public class MemberTicket extends CommonId {

	private static final long serialVersionUID = -5825160828591955376L;

	/**
	 * 前台会员用户
	 */
	@ManyToOne(targetEntity = Member.class)
	@JoinColumn(name = "member")
	private Member member;

	/**
	 * 优惠券
	 */
	@ManyToOne(targetEntity = Ticket.class)
	@JoinColumn(name = "ticket")
	private Ticket ticket;

	/**
	 * 激活日期
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "active_date")
	private Date activeDate;

	/**
	 * 状态，1可用，2已用，3失效
	 */
	@Column(name = "status")
	private Integer status;

	/**
	 * 激活码
	 */
	@Column(name = "active_code", length = 10)
	private String activeCode;

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public Ticket getTicket() {
		return ticket;
	}

	public void setTicket(Ticket ticket) {
		this.ticket = ticket;
	}

	public Date getActiveDate() {
		return activeDate;
	}

	public void setActiveDate(Date activeDate) {
		this.activeDate = activeDate;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getActiveCode() {
		return activeCode;
	}

	public void setActiveCode(String activeCode) {
		this.activeCode = activeCode;
	}

	public void setMemberId(Long id) {
		if (member == null) member = new Member();
		member.setId(id);
	}

	public static DetachedCriteria getCriteriaQuery(MemberTicket query) {
		final DetachedCriteria dc = DetachedCriteria.forClass(MemberTicket.class);

		return dc;
	}

	@Override
	public String getTableName() {
		return null;
	}
}
