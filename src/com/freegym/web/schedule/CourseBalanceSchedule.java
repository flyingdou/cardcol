package com.freegym.web.schedule;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.freegym.web.basic.Member;
import com.freegym.web.order.CourseOrder;
import com.freegym.web.order.strategy.IBalanceStrategy;
import com.freegym.web.order.strategy.impl.CourseBalanceStrategyImpl;
import com.freegym.web.service.IOrderService;

@Component
public class CourseBalanceSchedule {
	protected Logger log = Logger.getLogger(this.getClass());

	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	@Autowired(required = true)
	@Qualifier("orderService")
	protected IOrderService service;

	@Scheduled(cron = "0 0 2 * * ?")
	public void execute() {
		Member cardcol = (Member) service.load(Member.class, 1l);
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DAY_OF_MONTH, -1);
		System.out.println(sdf.format(c.getTime()));
		String time = sdf.format(c.getTime());
		/*final Date[] ds = DateUtil.getDateTimes(c.getTime());
		String s1=sdf.format(ds[0]).replaceAll("-", "");
		String s2=sdf.format(ds[1]).replaceAll("-", "");*/
		@SuppressWarnings("unchecked")
		final List<CourseOrder> orderList = (List<CourseOrder>) service.findObjectBySql("from CourseOrder o where o.status = '3'and DATE_FORMAT(o.orderDate,'%Y-%m-%d') <= ?",time);
		for (CourseOrder order : orderList) {
			order.setStatus('2');
			c.setTime(order.getOrderStartTime());
			IBalanceStrategy balance = new CourseBalanceStrategyImpl(service, order, cardcol);
			try {
				balance.balance();
			} catch (Exception e) {
				log.info("订单编号为" + order.getNo() + "的订单结算失败，失败原因：" + e.getMessage());
			}
		}

	}
}
