package com.freegym.web.order;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "TB_PlanRelease_ORDER_DETAIL")
public class PlanOrderDetail extends OrderDetail {

	private static final long serialVersionUID = -7358255008331013507L;

	@ManyToOne(targetEntity = PlanOrder.class)
	@JoinColumn(name = "order_id")
	private PlanOrder order;// 订单

	public PlanOrder getOrder() {
		return order;
	}

	public void setOrder(PlanOrder order) {
		this.order = order;
	}

	@Override
	public String getTableName() {
		return "交易明细表";
	}
}
