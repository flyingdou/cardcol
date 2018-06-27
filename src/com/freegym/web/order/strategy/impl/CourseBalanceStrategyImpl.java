package com.freegym.web.order.strategy.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.freegym.web.basic.Member;
import com.freegym.web.order.CourseOrder;
import com.freegym.web.order.CourseOrderDetail;
import com.freegym.web.order.Order;
import com.freegym.web.order.OrderDetail;
import com.freegym.web.order.strategy.AbstractBalanceStrategy;
import com.freegym.web.service.IOrderService;

/**
 * 自动计划结算
 */
public class CourseBalanceStrategyImpl extends AbstractBalanceStrategy {

	public CourseBalanceStrategyImpl(IOrderService service, Order order, Member cardcol) {
		super(service, order, cardcol);
	}

	@Override
	protected int getRate() {
		return DETAIL_SELLER2;
	}

	@Override
	protected List<OrderDetail> calculate() {
		CourseOrder go = (CourseOrder) order;
		Date detailDate = new Date();
		List<OrderDetail> gods = new ArrayList<OrderDetail>();
		// 收款方，主要指卖方
		CourseOrderDetail god = new CourseOrderDetail();
		god.setBalanceTimes(1);
		god.setDetailDate(detailDate);
		god.setDetailMoney(go.getOrderMoney());
		god.setDetailType('5');
		god.setEndDate(detailDate);
		god.setInOutType(INOUT_TYPE_IN);
		god.setTransUser(go.getCourse().getMember());
		god.setTransObject(go.getMember());
		god.setOrder(go);
		gods.add(god);
		// 付款方，主要指买方
		CourseOrderDetail god1 = new CourseOrderDetail();
		god1.setBalanceTimes(1);
		god1.setDetailDate(detailDate);
		god1.setDetailMoney(go.getOrderMoney());
		god1.setDetailType('5');
		god1.setEndDate(detailDate);
		god1.setInOutType(INOUT_TYPE_OUT);
		god1.setTransUser(go.getMember());
		god1.setTransObject(go.getCourse().getMember());
		god1.setOrder(go);
		gods.add(god1);

		return gods;
	}

}
