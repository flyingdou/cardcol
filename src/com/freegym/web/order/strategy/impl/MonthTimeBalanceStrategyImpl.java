package com.freegym.web.order.strategy.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;

import com.freegym.web.basic.Member;
import com.freegym.web.course.SignIn;
import com.freegym.web.order.Order;
import com.freegym.web.order.OrderDetail;
import com.freegym.web.order.ProductOrder;
import com.freegym.web.order.ProductOrderDetail;
import com.freegym.web.order.Product;
import com.freegym.web.order.strategy.AbstractProductBalanceStrategy;
import com.freegym.web.service.IOrderService;

/**
 * 按月（计次）付费（2）
 */
public class MonthTimeBalanceStrategyImpl extends AbstractProductBalanceStrategy {

	public MonthTimeBalanceStrategyImpl(IOrderService service, Order order, Member cardcol) {
		super(service, order, cardcol);
	}

	@Override
	protected List<OrderDetail> calculate() {
		ProductOrder po = (ProductOrder) order;
		int balanceTimes = po.getBalanceTimes() == null ? 1 : po.getBalanceTimes() + 1;
		List<SignIn> signList = service.findSignListByOrderId(po.getId());
		int signCount = signList.size();
		// 健身次数已经达到上线
		if (signCount >= po.getProduct().getNum()) {
			final List<OrderDetail> orderDetailList = new ArrayList<OrderDetail>();
			Double surplusMoney = po.getSurplusMoney();
			ProductOrderDetail detail = null;
			Date detailDate = new Date();
			Product product = po.getProduct();
			// 训练费用
			detail = new ProductOrderDetail();
			detail.setOrder(po);
			detail.setTransUser(product.getMember());
			detail.setTransObject(po.getMember());
			detail.setDetailDate(detailDate);
			po.setStatus('2');
			detail.setDetailMoney(surplusMoney);
			detail.setDetailType('5');
			detail.setBalanceTimes(balanceTimes);
			orderDetailList.add(detail);
			surplusMoney = surplusMoney - detail.getDetailMoney();
			po.setBalanceTimes(balanceTimes);
			order.setSurplusMoney(surplusMoney);
			return orderDetailList;
		}
		Calendar calNow = Calendar.getInstance();
		calNow.set(Calendar.HOUR_OF_DAY, 0);
		calNow.set(Calendar.MINUTE, 0);
		calNow.set(Calendar.SECOND, 0);
		Calendar calOrder = Calendar.getInstance();
		calOrder.setTime(order.getOrderStartTime());
		calOrder.add(Calendar.MONTH, balanceTimes);
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
				detail2.setTransUser(po.getMember());
				detail2.setTransObject(product.getMember());
				detail2.setInOutType(INOUT_TYPE_OUT);
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
