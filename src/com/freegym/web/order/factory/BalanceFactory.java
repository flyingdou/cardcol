package com.freegym.web.order.factory;

import com.freegym.web.basic.Member;
import com.freegym.web.order.ProductOrder;
import com.freegym.web.order.strategy.IBalanceStrategy;
import com.freegym.web.order.strategy.impl.AdvanceBalanceStrategyImpl;
import com.freegym.web.order.strategy.impl.CourseMonthPeriodBalanceStrategyImpl;
import com.freegym.web.order.strategy.impl.CourseMonthTimeBalanceStrategyImpl;
import com.freegym.web.order.strategy.impl.GambleBalanceStrategyImpl;
import com.freegym.web.order.strategy.impl.MonthPeriodBalanceStrategyImpl;
import com.freegym.web.order.strategy.impl.MonthTimeBalanceStrategyImpl;
import com.freegym.web.service.IOrderService;

public class BalanceFactory {
	public static IBalanceStrategy createBalance(String type, IOrderService service, ProductOrder order, Member cardcol) {
		if ("1".equals(type)) {
			return new MonthPeriodBalanceStrategyImpl(service, order, cardcol);
		} else if ("2".equals(type)) {
			return new MonthTimeBalanceStrategyImpl(service, order, cardcol);
		} else if ("3".equals(type)) {
			return new CourseMonthPeriodBalanceStrategyImpl(service, order, cardcol);
		} else if ("4".equals(type)) {
			return new GambleBalanceStrategyImpl(service, order, cardcol);
		} else if ("5".equals(type)) {
			return new CourseMonthTimeBalanceStrategyImpl(service, order, cardcol);
		} else if ("6".equals(type)) { return new AdvanceBalanceStrategyImpl(service, order, cardcol); }
		return null;
	}
}
