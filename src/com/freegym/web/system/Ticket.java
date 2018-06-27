package com.freegym.web.system;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.criterion.DetachedCriteria;

import com.sanmen.web.core.bean.CommonId;

@Entity
@Table(name = "tb_ticket")
public class Ticket extends CommonId {

	private static final long serialVersionUID = 310398563804942295L;

	/**
	 * 优惠券名称
	 */
	@Column(name = "name", nullable = false, length = 50, unique = true)
	private String name;

	/**
	 * 优惠券金额
	 */
	@Column(name = "price", precision = 10, scale = 2)
	private Double price;

	/**
	 * 有效期
	 */
	@Column(name = "period", nullable = false)
	private Integer period;

	/**
	 * 种类
	 */
	@Column(name = "kind", length = 30)
	private String kind;

	/**
	 * 使用范围
	 */
	@Column(name = "scope", length = 50)
	private String scope;

	/**
	 * 激活码
	 */
	@Column(name = "active_code", length = 30)
	private String activeCode;

	/**
	 * 是否有效
	 */
	@Column(name = "effective", length = 1)
	private String effective;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getKind() {
		return kind;
	}

	public void setKind(String kind) {
		this.kind = kind;
	}

	public String getEffective() {
		return effective;
	}

	public void setEffective(String effective) {
		this.effective = effective;
	}

	public Integer getPeriod() {
		return period;
	}

	public void setPeriod(Integer period) {
		this.period = period;
	}

	public String getActiveCode() {
		return activeCode;
	}

	public void setActiveCode(String activeCode) {
		this.activeCode = activeCode;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	@Override
	public String getTableName() {
		return "优惠券信息表";
	}

	public static DetachedCriteria getCriteriaQuery(Ticket query) {
		final DetachedCriteria dc = DetachedCriteria.forClass(Ticket.class);
		return dc;
	}

}
