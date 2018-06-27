package com.cardcol.web.balance.impl;

import java.util.List;
import java.util.Locale;

import org.springframework.stereotype.Component;

import com.freegym.web.basic.Member;
import com.freegym.web.order.CourseOrder;
import com.freegym.web.order.Order;
import com.sanmen.web.core.bean.CommonId;
import com.sanmen.web.core.common.LogicException;

@Component("courseBalanceImpl")
public class CourseBalanceImpl extends AbstractBalanceImpl {

	@Override
	public List<CommonId> execute(Order order) throws LogicException {
		CourseOrder co = (CourseOrder) order;
		int type = 3;
		return handleBalance(co.getCourse().getMember(), order, type, co.getCourse().getCourseInfo().getName(),new Member(Long.valueOf(1)));

	}

	@Override
	protected Double getServiceCost() {
		return Double.parseDouble(messageResource.getMessage("order.balance.course.cost", null, Locale.CHINA));
	}

}
