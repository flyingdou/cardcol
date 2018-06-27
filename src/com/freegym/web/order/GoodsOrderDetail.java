package com.freegym.web.order;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "TB_GOODS_ORDER_DETAIL")
public class GoodsOrderDetail extends OrderDetail {

	private static final long serialVersionUID = -3156848430559782264L;

	@ManyToOne(targetEntity = GoodsOrder.class)
	@JoinColumn(name = "order_id")
	private GoodsOrder order;// 订单

	public GoodsOrder getOrder() {
		return order;
	}

	public void setOrder(GoodsOrder order) {
		this.order = order;
	}

	@Override
	public String getTableName() {
		return "交易明细表";
	}
}
