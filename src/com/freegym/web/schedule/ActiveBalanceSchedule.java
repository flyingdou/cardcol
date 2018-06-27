package com.freegym.web.schedule;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.cardcol.web.balance.impl.ActiveBalanceImpl;
import com.freegym.web.active.Active;
import com.freegym.web.active.TrainRecord;
import com.freegym.web.basic.Member;
import com.freegym.web.order.ActiveOrder;
import com.freegym.web.order.strategy.AbstractBalanceStrategy;
import com.freegym.web.order.strategy.impl.ActiveBalanceStrategyImpl;
import com.freegym.web.service.IOrderService;
import com.sanmen.web.core.utils.DateUtil;

/**
 * 活动结束结算提醒
 * 
 * @author Admin
 * 
 */
@Component
public class ActiveBalanceSchedule extends ActiveBalanceImpl{
	protected Logger log = Logger.getLogger(this.getClass());
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh-mm-ss");
	@Autowired(required = true)
	@Qualifier("orderService")
	private IOrderService service;

	@SuppressWarnings({ "unchecked", "static-access" })
	@Scheduled(cron = "0 0 2 * * ?") // 秒 分 时 日 月 周 * 年
	//@Scheduled(cron = "0 * 10 * * ?")
	public void execute() {
		Member cardcol = (Member) service.load(Member.class, 1l);
		final Calendar c = Calendar.getInstance();
		c.add(Calendar.DAY_OF_MONTH, -1);
		final Date[] ds = DateUtil.getDateTimes(c.getTime());
		String s1 = sdf.format(ds[0]).replaceAll("-", "");
		String s2 = sdf.format(ds[1]).replaceAll("-", "");
		final List<?> list = service.findObjectBySql("from ActiveOrder ao where ao.status ='1' and DATE_FORMAT(ao.orderEndTime,'%Y%m%d') between ? and ?", s1, s2);
		for (final Iterator<?> it = list.iterator(); it.hasNext();) {
			final ActiveOrder order = (ActiveOrder) it.next();
			final Active active = order.getActive();
			/*int a = active.getDays();// 挑战天数
			Date startDate = order.getOrderStartTime();// 开始时间
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(startDate);
			calendar.add(Calendar.DATE, a);
			Date endDate = calendar.getTime();//结束时间 
			if(endDate.getTime() <= ds[0].getTime()){
				if(active.getTarget() == 'A'||active.getTarget() == 'B'){
					order.setStatus('2');
					order.setResult('3');
					service.saveOrUpdate(order);
				}else if(active.getTarget() == 'D'){
				Long count = service.queryForLong("SELECT count(*) FROM tb_sign_in WHERE  signDate>=? AND signDate<=? AND memberSign=?",sdf.format(startDate),sdf.format(endDate),order.getMember().getId());
				int type = 2;
				if(count >= active.getValue()){
					order.setResult('1');
				}else{
					order.setResult('2');
				}
				//ActiveOrder orders = (ActiveOrder) service.saveOrUpdate(order);
				service.saveOrUpdate(execute(order));
				}
			}*/
		  
			
			final Long aid = order.getId();
			final Long mid = order.getMember().getId();
			final Double oldValue = active.getValue();
			final Double oldWeight = order.getWeight();
			int workouttime = 0;
			int workouttimes = 0;
			int maxact = 0;
			int minact = 0;
			int middle = 0;
			int actionquan = 0;
			 String balanceDate=null;
			 c.setTime((order.getOrderEndTime()));
			 c.add(c.DATE, 1);
			 balanceDate = sdf.format(c.getTime());
			 if
			 (!balanceDate.equals(sdf.format(Calendar.getInstance().getTime())))
			 { // 挑战结束日期的隔天凌晨2点结算
			 continue;
			 }
			order.setStatus('2');
			order.setResult('2');
			final List<TrainRecord> tlist = (List<TrainRecord>) service
					.findObjectBySql(" from TrainRecord p where p.confrim=1 and p.activeOrder.id=? and p.partake.id=? order by p.doneDate ", new Object[] { aid, mid });
			double newValue = oldWeight;
			if (tlist.size() > 0) {
				newValue = tlist.get(0).getWeight();
				for (int i = 0; i < tlist.size(); i++) {
					workouttimes += tlist.get(i).getTime();
					workouttime += tlist.get(i).getTimes();
					actionquan += tlist.get(i).getActionQuan();
					middle = tlist.get(0).getActionQuan();
					if (tlist.get(i).getActionQuan() > middle) {
						middle = tlist.get(i).getActionQuan();
						maxact = middle;
					} else {
						middle = tlist.get(i).getActionQuan();
						minact = middle;
					}

				}
			}
			if (active.getTarget() == 'A') { // 体重增加
				if (newValue - oldWeight >= active.getValue()) order.setResult('1');
			} else if (active.getTarget() == 'B') {// 体重减少
				if (oldWeight - newValue >= active.getValue()) order.setResult('1');
			} else if (active.getTarget() == 'C') { // 运动时间
				Double hour = (workouttime / 60) + (workouttime % 60 > 0 ? 1d : 0d);
				if (hour >= oldValue) order.setResult('1');
			} else if (active.getCategory() == 'D') {// 运动次数
				if (workouttimes >= newValue) order.setResult('1');
			} else // 其他目标
			{
				if (active.getEvaluationMethod() == "A") {
					if (maxact >= active.getValue()) order.setResult('1');
				} else if (active.getEvaluationMethod() == "B") {
					if (minact <= active.getValue()) order.setResult('1');
				} else if (active.getEvaluationMethod() == "C") {
					if (actionquan >= active.getValue()) order.setResult('1');
				} else if (active.getEvaluationMethod() == "D") {
					if (actionquan <= active.getValue()) order.setResult('1');
				} else if (active.getEvaluationMethod() == "E") {
					if (newValue >= active.getValue()) order.setResult('1');
				} else if (newValue >= active.getValue()) order.setResult('1');
			}

			AbstractBalanceStrategy balance = new ActiveBalanceStrategyImpl(service, order, cardcol);
			try {
				if (order.getResult() == '2') balance.balance();
				if (order.getResult() == '1') balance.abalance();
			} catch (Exception e) {
				// TODO: handle exception
				log.error("error", e);
				log.info("订单编号为" + order.getNo() + "的订单结算失败，失败原因：" + e.getMessage());
			}
		}
	}

	

	

	
}
