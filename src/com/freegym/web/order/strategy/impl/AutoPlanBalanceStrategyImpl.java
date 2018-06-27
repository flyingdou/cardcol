package com.freegym.web.order.strategy.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.freegym.web.basic.Member;
import com.freegym.web.order.GoodsOrder;
import com.freegym.web.order.GoodsOrderDetail;
import com.freegym.web.order.Order;
import com.freegym.web.order.OrderDetail;
import com.freegym.web.order.strategy.AbstractBalanceStrategy;
import com.freegym.web.service.IOrderService;

/**
 * 自动计划结算
 */
public class AutoPlanBalanceStrategyImpl extends AbstractBalanceStrategy {

	public AutoPlanBalanceStrategyImpl(IOrderService service, Order order, Member cardcol) {
		super(service, order, cardcol);
	}

	@Override
	protected List<OrderDetail> calculate() {
		GoodsOrder go = (GoodsOrder) order;
		Date detailDate = new Date();
		Double orderMoney = go.getOrderMoney();
		List<OrderDetail> gods = new ArrayList<OrderDetail>();
		// 收款方，主要指卖方
		GoodsOrderDetail god = new GoodsOrderDetail();
		god.setBalanceTimes(1);
		god.setDetailDate(detailDate);
		god.setDetailMoney(go.getOrderMoney());
		god.setDetailType('5');
		god.setEndDate(detailDate);
		god.setInOutType(INOUT_TYPE_IN);
		god.setTransUser(new Member(go.getGoods().getMember()));
		god.setTransObject(go.getMember());
		god.setOrder(go);
		gods.add(god);
		// 付款方，主要指买方
		GoodsOrderDetail god1 = new GoodsOrderDetail();
		god1.setBalanceTimes(1);
		god1.setDetailDate(detailDate);
		god1.setDetailMoney(orderMoney);
		god1.setDetailType('5');
		god1.setEndDate(detailDate);
		god1.setInOutType(INOUT_TYPE_OUT);
		god1.setTransUser(go.getMember());
		god1.setTransObject(new Member(go.getGoods().getMember()));
		god1.setOrder(go);
		gods.add(god1);
		
		return gods;
	}

	@Override
	protected int getRate() {
		return DETAIL_SELLER2;
	}

}
