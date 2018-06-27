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
import com.freegym.web.order.strategy.AbstractProductBalanceStrategy;
import com.freegym.web.service.IOrderService;
import com.sanmen.web.core.utils.StringUtils;

/**
 * 对赌(频率)(5)
 */
public class CourseMonthTimeBalanceStrategyImpl extends AbstractProductBalanceStrategy {

	public CourseMonthTimeBalanceStrategyImpl(IOrderService service, Order order, Member cardcol) {
		super(service, order, cardcol);
	}

	@Override
	protected List<OrderDetail> calculate() {
		ProductOrder po = (ProductOrder) order;
		int balanceTimes = po.getBalanceTimes() == null ? 1 : po.getBalanceTimes() + 1;
		List<SignIn> signList = service.findSignListByOrderId(order.getId());
		Calendar calNow = Calendar.getInstance();
		Calendar calOrder = Calendar.getInstance();
		calOrder.setTime(order.getOrderStartTime());
		if (po.getOrderEndTime() == null) {
			calOrder.add(Calendar.DAY_OF_MONTH, po.getProduct().getWellNum() * 7);
			po.setOrderEndTime(calOrder.getTime());
		} else {
			calOrder.setTime(order.getOrderEndTime());
		}
		// 合同期满进行结算
		if (ymd.format(calNow.getTime()).compareTo(ymd.format(calOrder.getTime())) == 0) {
			Calendar calEnd = Calendar.getInstance();
			Calendar calStart = Calendar.getInstance();
			calStart.setTime(order.getOrderStartTime());
			int wellNum = po.getProduct().getWellNum();
			int[] arr = new int[wellNum];
			for (int i = 0; i < wellNum; i++) {
				int count = 0;
				calEnd.setTime(calStart.getTime());
				calEnd.add(Calendar.DAY_OF_MONTH, 7);
				for (SignIn signIn : signList) {
					if (ymd.format(signIn.getSignDate()).compareTo(ymd.format(calStart.getTime())) >= 0
							&& ymd.format(signIn.getSignDate()).compareTo(ymd.format(calEnd.getTime())) < 0) {
						count++;
					}
				}
				calStart.setTime(calEnd.getTime());
				arr[i] = count;
			}
			final List<OrderDetail> orderDetailList = new ArrayList<OrderDetail>();
			ProductOrderDetail detail1 = new ProductOrderDetail();
			// 训练费用
			detail1.setOrder(po);
			detail1.setTransObject(po.getMember());
			detail1.setTransUser(po.getProduct().getMember());
			detail1.setDetailDate(new Date());
			detail1.setDetailMoney(po.getProduct().getCost());
			detail1.setDetailType('5');
			detail1.setInOutType(INOUT_TYPE_IN);
			detail1.setBalanceTimes(balanceTimes);
			orderDetailList.add(detail1);
			ProductOrderDetail detail2 = new ProductOrderDetail();
			try {
				BeanUtils.copyProperties(detail2, detail1);
				detail2.setInOutType(INOUT_TYPE_OUT);
				detail1.setTransObject(po.getMember());
				detail1.setTransUser(po.getProduct().getMember());
				orderDetailList.add(detail2);
			} catch (IllegalAccessException | InvocationTargetException e) {
				e.printStackTrace();
			}
			// 判断是否遵守承诺次数
			if (!StringUtils.isEmpty(arr.toString().replaceAll(",", "").replaceAll(wellNum + "", ""))) {
				int manyCount = 0;
				int lessCount = 0;
				for (int i = 0; i < wellNum; i++) {
					if (arr[i] > po.getProduct().getNum()) {
						manyCount += arr[i] - po.getProduct().getNum();
					} else {
						lessCount += po.getProduct().getNum() - arr[i];
					}
				}
				if (manyCount > 0) {
					ProductOrderDetail detail3 = new ProductOrderDetail();
					ProductOrderDetail detail4 = new ProductOrderDetail();
					try {
						BeanUtils.copyProperties(detail3, detail1);
						detail3.setDetailMoney(manyCount * po.getProduct().getOverCost());
						detail3.setDetailType('8');
						orderDetailList.add(detail3);
						BeanUtils.copyProperties(detail4, detail3);
						detail4.setInOutType('2');
						orderDetailList.add(detail4);
					} catch (IllegalAccessException | InvocationTargetException e) {
						e.printStackTrace();
					}
				}
				double cost = lessCount * po.getProduct().getDutyCost();
				cost = cost > po.getProduct().getPromiseCost() ? po.getProduct().getPromiseCost() : cost;
				if (lessCount > 0) {
					ProductOrderDetail detail5 = new ProductOrderDetail();
					ProductOrderDetail detail6 = new ProductOrderDetail();
					try {
						BeanUtils.copyProperties(detail5, detail1);
						detail5.setDetailMoney(cost);
						detail5.setDetailType('4');
						orderDetailList.add(detail5);
						BeanUtils.copyProperties(detail6, detail5);
						detail6.setInOutType('2');
						orderDetailList.add(detail6);
					} catch (IllegalAccessException | InvocationTargetException e) {
						e.printStackTrace();
					}
				}

			}
			order.setStatus('2');
			return orderDetailList;
		}
		return new ArrayList<OrderDetail>();
	}
}
