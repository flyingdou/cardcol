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
import com.freegym.web.order.GoodsOrder;
import com.freegym.web.order.strategy.AbstractBalanceStrategy;
import com.freegym.web.order.strategy.impl.AutoPlanBalanceStrategyImpl;
import com.freegym.web.service.IOrderService;
import com.sanmen.web.core.utils.DateUtil;

/**
 * 智能计划结算
 * 
 * @author Admin
 * 
 */
@Component
public class AutoBalanceSchedule {

	protected Logger log = Logger.getLogger(this.getClass());

	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	@Autowired(required = true)
	@Qualifier("orderService")
	protected IOrderService service;

	@SuppressWarnings("unchecked")
	//@Scheduled(cron = "0 0 2 * * ?")
	@Scheduled(cron = "0 * 11 * * ?")
	public void execute() {
		Member cardcol = service.findCardColManager();
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DAY_OF_MONTH, -1);
		final Date[] ds = DateUtil.getDateTimes(c.getTime());
		String s1=sdf.format(ds[0]).replaceAll("-", "");
		String s2=sdf.format(ds[1]).replaceAll("-", "");
		final List<GoodsOrder> orderList = (List<GoodsOrder>) service.findObjectBySql(" from GoodsOrder o where o.status = '1' and DATE_FORMAT(o.orderDate,'%Y%m%d') between ? and ?", s1,s2);
		Long gid=0L;
		for (GoodsOrder order : orderList) {
			gid=order.getGoods().getId();
			order.setStatus('2');
			c.setTime(order.getOrderStartTime());
			AbstractBalanceStrategy balance = new AutoPlanBalanceStrategyImpl(service, order, cardcol);
			try {
				if(gid==1||gid==2)
				balance.balance();
				else 
				balance.bbalance();
			} catch (Exception e) {
				log.info("订单编号为" + order.getNo() + "的订单结算失败，失败原因：" + e.getMessage());
			}
		}

	}
}
