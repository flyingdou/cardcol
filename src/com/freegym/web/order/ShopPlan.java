package com.freegym.web.order;

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
import com.freegym.web.plan.PlanRelease;
/**
 * 这是PlanRelease的购物车表
 * @author Administrator
 *
 */
@Entity
@Table(name = "TB_PlanRelease_SHOP")
public class ShopPlan extends BasicShop {

	private static final long serialVersionUID = -7358255008331013507L;

	@ManyToOne(targetEntity = Member.class)
	@JoinColumn(name = "member")
	private Member member;// 收货人

	@ManyToOne(targetEntity = PlanRelease.class)
	@JoinColumn(name = "planRelease")
	private PlanRelease planRelease;// 计划

	@Column(precision = 18, scale = 2)
	private Double unitPrice;// 单价

	private Integer count;// 数量

	@Column(precision = 18, scale = 2)
	private Double orderMoney;// 订单金额

	@Column(precision = 18, scale = 2)
	private Double contractMoney;// 合同金额//暂时无用

	private Integer integral;// 订单积分

	@Temporal(TemporalType.DATE)
	private Date orderStartTime;// 订单开始日期

	@Temporal(TemporalType.DATE)
	private Date orderEndTime;// 订单结束日期

	private Integer reportDay;// 费用结算日
	
	private String planStartTime;//用户填写的日期
	public Integer getReportDay() {
		return reportDay;
	}

	public void setReportDay(Integer reportDay) {
		this.reportDay = reportDay;
	}

	public Date getOrderStartTime() {
		return orderStartTime;
	}

	public void setOrderStartTime(Date orderStartTime) {
		this.orderStartTime = orderStartTime;
	}

	public Date getOrderEndTime() {
		return orderEndTime;
	}

	public void setOrderEndTime(Date orderEndTime) {
		this.orderEndTime = orderEndTime;
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	

	public Double getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public Double getOrderMoney() {
		return orderMoney;
	}

	public void setOrderMoney(Double orderMoney) {
		this.orderMoney = orderMoney;
	}

	public Double getContractMoney() {
		return contractMoney;
	}

	public void setContractMoney(Double contractMoney) {
		this.contractMoney = contractMoney;
	}

	public Integer getIntegral() {
		return integral;
	}

	public void setIntegral(Integer integral) {
		this.integral = integral;
	}

	public static DetachedCriteria getCriteriaQuery(ShopPlan query) {
		DetachedCriteria dc = DetachedCriteria.forClass(ShopPlan.class);
		return dc;
	}

	
	
	public PlanRelease getPlanRelease() {
		return planRelease;
	}

	public void setPlanRelease(PlanRelease planRelease) {
		this.planRelease = planRelease;
	}

	public String getPlanStartTime() {
		return planStartTime;
	}

	public void setPlanStartTime(String planStartTime) {
		this.planStartTime = planStartTime;
	}

	@Override
	public String getTableName() {
		// TODO Auto-generated method stub
		return "TB_PlanRelease_SHOP";
	}
}
