package com.freegym.web.order.strategy.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.freegym.web.basic.Member;
import com.freegym.web.order.ActiveOrder;
import com.freegym.web.order.ActiveOrderDetail;
import com.freegym.web.order.Order;
import com.freegym.web.order.OrderDetail;
import com.freegym.web.order.strategy.AbstractBalanceStrategy;
import com.freegym.web.service.IOrderService;

public class ActiveBalanceStrategyImpl extends AbstractBalanceStrategy{

	public ActiveBalanceStrategyImpl(IOrderService service, Order order, Member cardcol) {
		super(service, order, cardcol);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected int getRate() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	protected List<OrderDetail> calculate() {
		// TODO Auto-generated method stub
		ActiveOrder go = (ActiveOrder) order;
		Date detailDate = new Date();
		List<OrderDetail> gods = new ArrayList<OrderDetail>();
		ActiveOrderDetail god1 = new ActiveOrderDetail();
		god1.setBalanceTimes(1);
		god1.setDetailDate(detailDate);
		god1.setDetailMoney(go.getOrderMoney());
		god1.setDetailType('5');
		god1.setEndDate(detailDate);
		god1.setInOutType(INOUT_TYPE_IN);
		god1.setTransObject(go.getMember());
		god1.setTransUser(go.getActive().getInstitution());
		god1.setOrder(go);
		
//		ActiveOrderDetail god2 = new ActiveOrderDetail();
//		god2.setBalanceTimes(1);
//		god2.setDetailDate(detailDate);
//		god2.setDetailMoney(go.getOrderMoney());
//		god2.setDetailType('5');
//		god2.setEndDate(detailDate);
//		god2.setInOutType(INOUT_TYPE_OUT);
//		god2.setTransObject(go.getMember());
//		god2.setTransUser(go.getActive().getInstitution());
//		god2.setOrder(go);
//		gods.add(god2);
		return gods;
		
	}
   
}
