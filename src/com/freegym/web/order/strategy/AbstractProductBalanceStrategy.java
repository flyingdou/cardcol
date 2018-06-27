package com.freegym.web.order.strategy;

import com.freegym.web.basic.Member;
import com.freegym.web.order.Order;
import com.freegym.web.service.IOrderService;

public abstract class AbstractProductBalanceStrategy extends AbstractBalanceStrategy {

	public AbstractProductBalanceStrategy(IOrderService service, Order order, Member cardcol) {
		super(service, order, cardcol);
	}

	protected int getRate() {
		return DETAIL_SELLER1;
	}

}
