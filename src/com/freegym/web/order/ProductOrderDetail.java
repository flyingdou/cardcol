package com.freegym.web.order;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "TB_PRODUCT_ORDER_DETAIL")
public class ProductOrderDetail extends OrderDetail {

	private static final long serialVersionUID = -7358255008331013507L;

	@ManyToOne(targetEntity = ProductOrder.class)
	@JoinColumn(name = "order_id")
	private ProductOrder order;// 订单

	public ProductOrder getOrder() {
		return order;
	}

	public void setOrder(ProductOrder order) {
		this.order = order;
	}

	@Override
	public String getTableName() {
		return "交易明细表";
	}
}
