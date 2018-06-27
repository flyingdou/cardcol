package com.freegym.web.order;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "TB_FACTORY_ORDER_DETAIL")
public class FactoryOrderDetail extends OrderDetail {

	private static final long serialVersionUID = -7358255008331013507L;

	@ManyToOne(targetEntity = FactoryOrder.class)
	@JoinColumn(name = "order_id")
	private FactoryOrder order;// 订单

	@Override
	public String getTableName() {
		return "交易明细表";
	}

	public FactoryOrder getOrder() {
		return order;
	}

	public void setOrder(FactoryOrder order) {
		this.order = order;
	}
}
