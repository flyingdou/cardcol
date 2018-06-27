package com.freegym.web.order;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "TB_ACTIVE_ORDER_DETAIL")
public class ActiveOrderDetail extends OrderDetail {

	private static final long serialVersionUID = -7358255008331013507L;

	@ManyToOne(targetEntity = ActiveOrder.class)
	@JoinColumn(name = "order_id")
	private ActiveOrder order;// 订单

	public ActiveOrder getOrder() {
		return order;
	}

	public void setOrder(ActiveOrder order) {
		this.order = order;
	}

	@Override
	public String getTableName() {
		return "交易明细表";
	}
}
