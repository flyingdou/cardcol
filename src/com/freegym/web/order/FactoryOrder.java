package com.freegym.web.order;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.freegym.web.config.FactoryCosts;

/**
 * 会员，教练预定场地订单表
 * 
 * @author Administrator
 * 
 */
@Entity
@Table(name = "TB_FACTORY_Order")
public class FactoryOrder extends Order {

	private static final long serialVersionUID = -2662894643623010379L;
	/**
	 * 订单与场地价格产品一一对应
	 */
	@ManyToOne(targetEntity = FactoryCosts.class)
	@JoinColumn(name = "factoryCosts")
	private FactoryCosts factoryCosts;

	@Override
	public String getTableName() {
		return "会员，教练预定场地订单表";
	}

	public FactoryCosts getFactoryCosts() {
		return factoryCosts;
	}

	public void setFactoryCosts(FactoryCosts factoryCosts) {
		this.factoryCosts = factoryCosts;
	}

}
