package com.cardcol.web.balance.impl;

import java.util.List;
import java.util.Locale;

import org.springframework.stereotype.Component;

import com.freegym.web.basic.Member;
import com.freegym.web.order.ActiveOrder;
import com.freegym.web.order.Order;
import com.sanmen.web.core.bean.CommonId;
import com.sanmen.web.core.common.LogicException;

@Component("activeBalanceImpl")
public class ActiveBalanceImpl extends AbstractBalanceImpl {

	@Override
	public List<CommonId> execute(Order order) throws LogicException {
		ActiveOrder ao = (ActiveOrder) order;
		int type = 2;
		// 挑战是否成功
		//boolean b = ao.getActive().getTarget().equals("A") ? ao.getLastWeight() + ao.getActive().getValue()<= ao.getWeight() : ao.getWeight() + ao.getActive().getValue() <= ao.getLastWeight();
		boolean b = "A".equals(ao.getActive().getTarget().toString()) ? ao.getLastWeight() + ao.getActive().getValue()<= ao.getWeight() : ao.getWeight() + ao.getActive().getValue() <= ao.getLastWeight();
		if("A".equals(ao.getActive().getTarget().toString())|| "B".equals(ao.getActive().getTarget().toString())){
			if (b == true) {
				ao.setResult('1');
				return handleBalance(ao.getMember(), order, type, ao.getActive()
						.getName(),new Member(Long.valueOf(1)));
			} else {
				ao.setResult('2');
				return handleBalance(ao.getActive().getInstitution(), ao, type, ao
						.getActive().getName(),new Member(Long.valueOf(1)));
			}
		}else if("D".equals(ao.getActive().getTarget().toString())){
			if ("1".equals(ao.getResult().toString())) {
				return handleBalance(ao.getMember(), order, type, ao.getActive()
						.getName(),new Member(Long.valueOf(1)));
			} else {
				return handleBalance(ao.getActive().getInstitution(), ao, type, ao
						.getActive().getName(),new Member(Long.valueOf(1)));
			}
		}
		return null;
	}

	@Override
	protected Double getServiceCost() {
		return Double.parseDouble(messageResource.getMessage(
				"order.balance.goods.cost", null, Locale.CHINA));
	}

}
