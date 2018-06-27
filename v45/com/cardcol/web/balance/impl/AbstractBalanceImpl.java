package com.cardcol.web.balance.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.cardcol.web.balance.IBalance;
import com.cardcol.web.order.ProductOrderBalance45;
import com.databaseConnection.DataBaseConnection;
import com.freegym.web.basic.Member;
import com.freegym.web.common.Constants;
import com.freegym.web.order.Order;
import com.sanmen.web.core.bean.CommonId;
import com.sanmen.web.core.component.UserMessageResource;

public abstract class AbstractBalanceImpl implements IBalance, Constants {
	@Autowired
	protected UserMessageResource messageResource;

	protected abstract Double getServiceCost();

	protected List<CommonId> handleBalance(Member member, Order order, int type, String prodName, Member platform) {
		String updateSql = "";
		Object[] args = new Object[1];
		
		// 修改订单状态为已结算
		order.setStatus(ORDER_STATUS_BALANCE);

		// 给俱乐部的收入
		ProductOrderBalance45 pob45 = new ProductOrderBalance45();// 结算表
		pob45.setTo(member);
		pob45.setOrderId(order.getId());
		pob45.setType(type);
		if (order.getOrderMoney() > 0) {
			// Double serviceCost = getServiceCost();
			if (type == 2) {
				pob45.setService(order.getOrderMoney() * 0 / 100);
				pob45.setMoney(order.getOrderMoney() - order.getOrderMoney() * 0 / 100);
			} else if (type == 3) {
				pob45.setService(order.getOrderMoney() * 80 / 100);
				pob45.setMoney(order.getOrderMoney() - order.getOrderMoney() * 80 / 100);
			} else if (type == 4) {
				pob45.setService(order.getOrderMoney() * 80 / 100);
				pob45.setMoney(order.getOrderMoney() - order.getOrderMoney() * 80 / 100);
			}
		} else {
			pob45.setService(order.getOrderMoney());
			pob45.setMoney(order.getOrderMoney());
		}
		pob45.setFrom(order.getMember());
		pob45.setBalanceTime(new Date());
		pob45.setOrderNo(order.getNo());
		pob45.setProdName(prodName);
		pob45.setOrderMoney(order.getOrderMoney());
		updateSql = "insert into TB_ORDER_BALANCE_V45(BALANCE_FROM,BALANCE_TO,BALANCE_TIME,BALANCE_MONEY,BALANCE_SERVICE,BALANCE_TYPE,ORDER_ID,ORDER_NO,ORDER_MONEY,PROD_NAME) values(?,?,?,?,?,?,?,?,?,?)";
		args = new Object[] { pob45.getFrom().getId(), pob45.getTo().getId(), pob45.getBalanceTime(), pob45.getMoney(),
				pob45.getService(), pob45.getType(), pob45.getOrderId(), pob45.getOrderNo(), pob45.getOrderMoney(),
				pob45.getProdName() };
		DataBaseConnection.updateData(updateSql, args);

		// 给平台账户的服务费
		ProductOrderBalance45 pob1 = new ProductOrderBalance45();
		pob1.setService(0.0);
		pob1.setMoney(order.getOrderMoney() * 80 / 100);
		pob1.setFrom(order.getMember());
		pob1.setBalanceTime(new Date());
		pob1.setOrderId(order.getId());
		pob1.setOrderNo(order.getNo());
		pob1.setProdName(prodName);
		pob1.setOrderMoney(order.getOrderMoney());
		pob1.setTo(platform);
		pob1.setType(type);
		pob1.setProdName(prodName);
		args = new Object[] { pob1.getFrom().getId(), pob1.getTo().getId(), pob1.getBalanceTime(), pob1.getMoney(),
				pob1.getService(), pob1.getType(), pob1.getOrderId(), pob1.getOrderNo(), pob1.getOrderMoney(),
				pob1.getProdName() };
		DataBaseConnection.updateData(updateSql, args);

		// 返回值
		List<CommonId> list = new ArrayList<CommonId>();
		list.add(order);
		list.add(pob45);
		list.add(pob1);
		return list;
	}

}
