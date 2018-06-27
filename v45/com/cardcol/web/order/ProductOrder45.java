package com.cardcol.web.order;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.cardcol.web.basic.Product45;
import com.freegym.web.order.Order;

@Entity
@Table(name = "TB_PRODUCT_ORDER_V45")
public class ProductOrder45 extends Order {

	private static final long serialVersionUID = -3948299870861042020L;

	/**
	 * 一卡通商品
	 */
	@ManyToOne(targetEntity = Product45.class)
	@JoinColumn(name = "ORDER_PRODUCT")
	private Product45 product;

	/**
	 * 订单状态。0未付款，1已付款，2已结算
	 */

	public Product45 getProduct() {
		return product;
	}

	public void setProduct(Product45 product) {
		this.product = product;
	}

	@Override
	public String getTableName() {
		return "一卡通商品订单表";
	}

}
