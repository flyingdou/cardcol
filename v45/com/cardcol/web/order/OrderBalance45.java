package com.cardcol.web.order;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.freegym.web.basic.Member;
import com.sanmen.web.core.bean.CommonId;

@MappedSuperclass
public abstract class OrderBalance45 extends CommonId {

	private static final long serialVersionUID = -4753813685418890418L;

	/**
	 * 从会员
	 */
	@ManyToOne(targetEntity = Member.class)
	@JoinColumn(name = "BALANCE_FROM")
	private Member from;

	/**
	 * 到会员
	 */
	@ManyToOne(targetEntity = Member.class)
	@JoinColumn(name = "BALANCE_TO")
	private Member to;

	/**
	 * 结算时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "BALANCE_TIME")
	private Date balanceTime;

	/**
	 * 结算金额
	 */
	@Column(name = "BALANCE_MONEY", precision = 10, scale = 2)
	private Double money;

	/**
	 * 服务费
	 */
	@Column(name = "BALANCE_SERVICE", precision = 10, scale = 2)
	private Double service;

	public Member getFrom() {
		return from;
	}

	public void setFrom(Member from) {
		this.from = from;
	}

	public Member getTo() {
		return to;
	}

	public void setTo(Member to) {
		this.to = to;
	}

	public Date getBalanceTime() {
		return balanceTime;
	}

	public void setBalanceTime(Date balanceTime) {
		this.balanceTime = balanceTime;
	}

	public Double getMoney() {
		return money;
	}

	public void setMoney(Double money) {
		this.money = money;
	}

	public Double getService() {
		return service;
	}

	public void setService(Double service) {
		this.service = service;
	}
}
