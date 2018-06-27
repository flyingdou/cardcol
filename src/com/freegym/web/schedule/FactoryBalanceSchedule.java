package com.freegym.web.schedule;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.freegym.web.basic.Member;
import com.freegym.web.order.FactoryOrder;
import com.freegym.web.order.strategy.IBalanceStrategy;
import com.freegym.web.order.strategy.impl.FactoryBalanceStrategyImpl;
import com.freegym.web.service.IOrderService;
import com.sanmen.web.core.utils.DateUtil;

@Component
public class FactoryBalanceSchedule {
	protected Logger log = Logger.getLogger(this.getClass());

	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	@Autowired(required = true)
	@Qualifier("orderService")
	protected IOrderService service;

	@SuppressWarnings({ "unused", "unchecked" })
	@Scheduled(cron = "0 0 2 * * ?")
	public void execute() {
		Member cardcol = service.findCardColManager();
		final String currDate = sdf.format(Calendar.getInstance().getTime());
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DAY_OF_MONTH, -1);
		final Date[] ds = DateUtil.getDateTimes(c.getTime());
		String s1=sdf.format(ds[0]).replaceAll("-", "");
		String s2=sdf.format(ds[1]).replaceAll("-", "");
		final List<FactoryOrder> orderList = (List<FactoryOrder>) service.findObjectBySql(" from FactoryOrder o where o.status = '1' and DATE_FORMAT(o.orderDate,'%Y%m%d')  between ? and ?", s1,s2);
		for (FactoryOrder order : orderList) {
			order.setStatus('2');
			c.setTime(order.getOrderStartTime());
			IBalanceStrategy balance = new FactoryBalanceStrategyImpl(service, order, cardcol);
			try {
				balance.balance();
			} catch (Exception e) {
				log.info("订单编号为" + order.getNo() + "的订单结算失败，失败原因：" + e.getMessage());
			}
		}

	}
}
