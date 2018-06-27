package com.freegym.web.order;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "TB_CourseRelease_ORDER_DETAIL")
public class CourseOrderDetail extends OrderDetail {

	private static final long serialVersionUID = -7358255008331013507L;

	@ManyToOne(targetEntity = CourseOrder.class)
	@JoinColumn(name = "order_id")
	private CourseOrder order;// 订单

	public CourseOrder getOrder() {
		return order;
	}

	public void setOrder(CourseOrder order) {
		this.order = order;
	}

	@Override
	public String getTableName() {
		return "交易明细表";
	}
}
