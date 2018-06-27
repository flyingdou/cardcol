package com.cardcol.web.balance.impl;

import java.util.List;
import java.util.Locale;

import org.springframework.stereotype.Component;

import com.freegym.web.basic.Member;
import com.freegym.web.order.GoodsOrder;
import com.freegym.web.order.Order;
import com.sanmen.web.core.bean.CommonId;
import com.sanmen.web.core.common.LogicException;

@Component("goodsBalanceImpl")
public class GoodsBalanceImpl extends AbstractBalanceImpl {

	@Override
	public List<CommonId> execute(Order order) throws LogicException {
		GoodsOrder go = (GoodsOrder) order;
		int type = 4;
		return handleBalance(new Member(go.getGoods().getMember()), order, type, go.getGoods().getName(), new Member(Long.valueOf(1)));

	}
	
	@Override
	protected Double getServiceCost() {
		return Double.parseDouble(messageResource.getMessage("order.balance.goods.cost", null, Locale.CHINA));
	}
	
}
