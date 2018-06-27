package com.freegym.web.order.strategy.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;

import com.freegym.web.basic.Member;
import com.freegym.web.order.Order;
import com.freegym.web.order.OrderDetail;
import com.freegym.web.order.ProductOrder;
import com.freegym.web.order.ProductOrderDetail;
import com.freegym.web.order.Product;
import com.freegym.web.order.strategy.AbstractProductBalanceStrategy;
import com.freegym.web.service.IOrderService;

/**
 * 按月（时效）付费（1）
 */
public class MonthPeriodBalanceStrategyImpl extends AbstractProductBalanceStrategy {

	public MonthPeriodBalanceStrategyImpl(IOrderService service, Order order, Member cardcol) {
		super(service, order, cardcol);
	}

	@Override
	protected List<OrderDetail> calculate() {
		ProductOrder po = (ProductOrder) order;
		Calendar c = Calendar.getInstance();
		c.setTime(order.getOrderStartTime());

		Calendar calNow = Calendar.getInstance();
		Calendar calOrder = Calendar.getInstance();
		calOrder.setTime(po.getOrderStartTime());
		int balanceTimes = po.getBalanceTimes() == null ? 1 : po.getBalanceTimes() + 1;
		calOrder.add(Calendar.MONTH, balanceTimes);
		if (po.getOrderEndTime() == null) {
			c.add(Calendar.MONTH, po.getProduct().getWellNum());
			po.setOrderEndTime(c.getTime());
		}
		// 当前时间等于订单结算时间
		if (ymd.format(calNow.getTime()).compareTo(ymd.format(calOrder.getTime())) == 0) {
			final List<OrderDetail> orderDetailList = new ArrayList<OrderDetail>();
			Double surplusMoney = po.getSurplusMoney();
			ProductOrderDetail detail1 = new ProductOrderDetail();
			Date detailDate = new Date();
			Product product = po.getProduct();
			// 训练费用
			detail1.setOrder(po);
			detail1.setTransUser(product.getMember());
			detail1.setTransObject(po.getMember());
			detail1.setDetailDate(detailDate);
			// 当前时间等于订单结束时间
			if (calNow.getTime().compareTo(po.getOrderEndTime()) == 0) {
				order.setStatus('2');
				detail1.setDetailMoney(surplusMoney);
			} else {
				detail1.setDetailMoney(product.getCost() / product.getWellNum());
			}
			detail1.setDetailType('5');
			detail1.setInOutType(INOUT_TYPE_IN);
			detail1.setBalanceTimes(balanceTimes);
			orderDetailList.add(detail1);

			ProductOrderDetail detail2 = new ProductOrderDetail();
			try {
				BeanUtils.copyProperties(detail2, detail1);
				detail2.setInOutType(INOUT_TYPE_OUT);
				detail1.setTransUser(po.getMember());
				detail1.setTransObject(product.getMember());
				orderDetailList.add(detail2);
			} catch (IllegalAccessException | InvocationTargetException e) {
				e.printStackTrace();
			}

			surplusMoney = surplusMoney - detail1.getDetailMoney();
			po.setBalanceTimes(balanceTimes);
			po.setSurplusMoney(surplusMoney);
			return orderDetailList;
		}
		return new ArrayList<OrderDetail>();
	}
}
