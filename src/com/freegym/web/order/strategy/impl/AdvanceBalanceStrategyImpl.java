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
 * 预付费（6）
 */
public class AdvanceBalanceStrategyImpl extends AbstractProductBalanceStrategy {

	public AdvanceBalanceStrategyImpl(IOrderService service, Order order, Member cardcol) {
		super(service, order, cardcol);
	}

	@SuppressWarnings("static-access")
	@Override
	protected List<OrderDetail> calculate() {
		ProductOrder po = (ProductOrder) order;
		int balanceTimes = po.getBalanceTimes() == null ? 1 : po.getBalanceTimes() + 1;
		Calendar calNow = Calendar.getInstance();
		Calendar calOrder = Calendar.getInstance();
		calOrder.setTime(order.getOrderStartTime());
		calOrder.add(calOrder.DATE,1);
		if (po.getOrderEndTime() == null) {
			calOrder.add(Calendar.MONTH, po.getProduct().getWellNum() == null ? po.getProduct().getNum() : po.getProduct().getWellNum());
			po.setOrderEndTime(calOrder.getTime());
		} else {
			calOrder.setTime(order.getOrderEndTime());
		}
		// 当前时间等于订单结算时间
		if (ymd.format(calNow.getTime()).compareTo(ymd.format(calOrder.getTime())) == 0) {
			final List<OrderDetail> orderDetailList = new ArrayList<OrderDetail>();
			ProductOrderDetail detail1 = new ProductOrderDetail();
			Date detailDate = new Date();
			Product product = po.getProduct();
			// 训练费用
			detail1.setOrder(po);
			detail1.setTransUser(product.getMember());
			detail1.setTransObject(po.getMember());
			detail1.setDetailDate(detailDate);
			detail1.setDetailMoney(po.getOrderMoney());
			detail1.setDetailType('5');
			detail1.setInOutType(INOUT_TYPE_IN);
			detail1.setBalanceTimes(balanceTimes);
			orderDetailList.add(detail1);

			ProductOrderDetail detail2 = new ProductOrderDetail();
			try {
				BeanUtils.copyProperties(detail2, detail1);
				detail2.setInOutType(INOUT_TYPE_OUT);
				detail2.setTransUser(po.getMember());
				detail2.setTransObject(product.getMember());
				orderDetailList.add(detail2);
			} catch (IllegalAccessException | InvocationTargetException e) {
				e.printStackTrace();
			}
			po.setBalanceTimes(balanceTimes);
			po.setSurplusMoney(po.getSurplusMoney() - po.getOrderMoney());
			po.setStatus('2');
			return orderDetailList;
		}
		return new ArrayList<OrderDetail>();
	}
}
