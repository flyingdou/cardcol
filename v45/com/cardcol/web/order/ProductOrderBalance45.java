package com.cardcol.web.order;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.freegym.web.basic.Member;
import com.sanmen.web.core.bean.CommonId;

@Entity
@Table(name = "TB_ORDER_BALANCE_V45")
public class ProductOrderBalance45 extends CommonId {

	private static final long serialVersionUID = 9104427882446328918L;

	@Override
	public String getTableName() {
		return "商品结算明细";
	}

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

	/**
	 * 类型:1为一卡通，2为挑战，3为课程，4为专家计划，5为product订单(包括私教套餐、直播订单)
	 */
	@Column(name = "BALANCE_TYPE", length = 1)
	private int type;

	/**
	 * 订单id
	 */
	@Column(name = "ORDER_ID")
	private Long orderId;

	/**
	 * 订单号
	 */
	@Column(name = "ORDER_NO", length = 14)
	private String orderNo;

	/**
	 * 订单金额
	 */
	@Column(name = "ORDER_MONEY", precision = 10, scale = 2)
	private Double orderMoney;

	/**
	 * 商品名称
	 */
	@Column(name = "PROD_NAME", length = 100)
	private String prodName;

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

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getProdName() {
		return prodName;
	}

	public void setProdName(String prodName) {
		this.prodName = prodName;
	}

	public Double getOrderMoney() {
		return orderMoney;
	}

	public void setOrderMoney(Double orderMoney) {
		this.orderMoney = orderMoney;
	}

}
