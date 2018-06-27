package com.freegym.web.order.strategy.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

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
 * 对赌(次数)（4）
 */
public class GambleBalanceStrategyImpl extends AbstractProductBalanceStrategy {

	public GambleBalanceStrategyImpl(IOrderService service, Order order, Member cardcol) {
		super(service, order, cardcol);
	}

	@Override
	protected List<OrderDetail> calculate() {
		ProductOrder po = (ProductOrder) order;
		int balanceTimes = po.getBalanceTimes() == null ? 1 : po.getBalanceTimes() + 1;
		Calendar calNow = Calendar.getInstance();
		calNow.set(Calendar.HOUR_OF_DAY, 0);
		calNow.set(Calendar.MINUTE, 0);
		calNow.set(Calendar.SECOND, 0);
		Calendar calOrder = Calendar.getInstance();
		calOrder.setTime(order.getOrderEndTime());
		// 当前时间等于订单结算（结束）时间
		if (calNow.compareTo(calOrder) == 0) {
			final List<OrderDetail> orderDetailList = new ArrayList<OrderDetail>();
			Double surplusMoney = po.getSurplusMoney();
			ProductOrderDetail detail = null;
			Date detailDate = new Date();
			Product product = po.getProduct();
			// List signList =
			// service.findSignListByDate(order.getMember(),product.getMember(),order.getOrderStartTime(),order.getOrderEndTime());
			List<SignIn> signList = service.findSignListByOrderId(order.getId());
			int overTimes = 0, dutyTimes = 0;
			for (int i = 1; i <= po.getProduct().getNum(); i++) {
				Calendar orderEndTime = Calendar.getInstance();
				orderEndTime.setTime(po.getOrderStartTime());
				orderEndTime.add(Calendar.WEEK_OF_YEAR, i);
				int signCount = 0;
				for (Iterator<?> it = signList.iterator(); it.hasNext();) {
					SignIn si = (SignIn) it.next();
					if (si.getSignDate().before(orderEndTime.getTime())) {
						signCount++;
						it.remove();
					} else {
						break;
					}
				}
				if (signCount - product.getNum() > 0) {
					overTimes = signCount - product.getNum();
				} else if (signCount - product.getNum() < 0) {
					dutyTimes = signCount - product.getNum();
				}
			}
			// 存在超过或少于健身次数
			if (overTimes > 0 || dutyTimes > 0) {
				if (overTimes > 0) {
					// 超过健身次数
					detail = new ProductOrderDetail();
					detail.setOrder(po);
					detail.setTransUser(product.getMember());
					detail.setTransObject(po.getMember());
					detail.setDetailDate(detailDate);
					detail.setDetailMoney(product.getOverCost() * overTimes);
					detail.setDetailType('8');
					detail.setBalanceTimes(balanceTimes);
					orderDetailList.add(detail);
				}
				if (dutyTimes > 0) {
					// 缴纳违约金
					detail = new ProductOrderDetail();
					detail.setOrder(po);
					detail.setTransUser(product.getMember());
					detail.setTransObject(order.getMember());
					detail.setDetailDate(detailDate);
					detail.setDetailMoney(product.getBreachCost());
					detail.setDetailType('3');
					detail.setBalanceTimes(balanceTimes);
					orderDetailList.add(detail);
					po.setIsBreach("1");
					surplusMoney = surplusMoney - detail.getDetailMoney();
					// 少于健身次数
					detail = new ProductOrderDetail();
					detail.setOrder(po);
					detail.setTransUser(order.getMember());
					detail.setTransObject(product.getMember());
					detail.setDetailDate(detailDate);
					Double dutyMoney = product.getDutyCost() * dutyTimes;
					if (dutyMoney.compareTo(surplusMoney) == 1) {
						detail.setDetailMoney(surplusMoney);
					} else {
						detail.setDetailMoney(dutyMoney);
					}
					detail.setDetailType('4');
					detail.setBalanceTimes(balanceTimes);
					orderDetailList.add(detail);
					surplusMoney = surplusMoney - detail.getDetailMoney();
				}
			} else {
				// 按照承诺正常结束合同
				detail = new ProductOrderDetail();
				detail.setOrder(po);
				detail.setTransUser(product.getMember());
				detail.setTransObject(order.getMember());
				detail.setDetailDate(detailDate);
				detail.setDetailMoney(product.getCost());
				detail.setDetailType('5');
				detail.setBalanceTimes(balanceTimes);
				orderDetailList.add(detail);
				surplusMoney = surplusMoney - detail.getDetailMoney();
			}
			if (surplusMoney.compareTo(0d) == 1) {
				// 退款
				Member cardColManager = service.findCardColManager();
				detail = new ProductOrderDetail();
				detail.setOrder(po);
				detail.setTransUser(cardColManager);
				detail.setTransObject(order.getMember());
				detail.setDetailDate(detailDate);
				detail.setDetailMoney(surplusMoney);
				detail.setDetailType('9');
				detail.setBalanceTimes(balanceTimes);
				orderDetailList.add(detail);
				surplusMoney = surplusMoney - detail.getDetailMoney();
			}
			order.setStatus('2');
			po.setBalanceTimes(balanceTimes);
			order.setSurplusMoney(surplusMoney);
			return orderDetailList;
		}
		return new ArrayList<OrderDetail>();
	}
}
