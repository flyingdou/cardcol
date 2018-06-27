package com.cardcol.web.balance;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cardcol.web.order.ProductOrderBalance45;
import com.databaseConnection.DataBaseConnection;
import com.freegym.web.common.Constants;
import com.freegym.web.order.ActiveOrder;
import com.freegym.web.service.IOrderService;
import com.sanmen.web.core.bean.CommonId;

/**
 * 其它结算
 */
@Component
public class OtherBalanceSchedule implements Constants {

	@Qualifier("orderService")
	@Autowired(required = true)
	private IOrderService service;



	/**
	 * updated by dou 2018-01-31 
	 */
	@Scheduled(cron = "0 10 3 * * ?") // 秒 分 时 日 月 周 * 年
	@Transactional(propagation = Propagation.REQUIRED)
	public void execute() {
		
		 String sqlx = "select ao.id from tb_active_order ao, tb_member m where ao.member = m.id and ao.status = 1 ";
		 List<Map<String, Object>> maps = DataBaseConnection.getList(sqlx, null);
		 String dou = "";
		 for (int i = 0; i < maps.size(); i++) {
			  dou += maps.get(i).get("id") + ",";
		}
		 
	    List<?> list = new ArrayList<>();
		if (dou != null && dou != ""){
			dou = dou.substring(0, dou.length()-1);
			list = service.findObjectBySql("from ActiveOrder ao where ao.id in(" + dou + ")");
		} else {
			list = new ArrayList<>();
		}
		
		for (final Iterator<?> it = list.iterator(); it.hasNext();) {
			
			ProductOrderBalance45 pob45 = new ProductOrderBalance45();// 结算表
			ActiveOrder ao = (ActiveOrder) it.next();
				
			int a = ao.getActive().getDays();// 挑战天数
			Date startDate = ao.getOrderStartTime() == null ? new Date() : ao.getOrderStartTime(); // 开始时间
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(startDate);
			calendar.add(Calendar.DATE, a);
			Date endDate = calendar.getTime();// 结算时间
			if (endDate.getTime() < new Date().getTime()) {
				if (ao.getActive().getTarget() == 'A' || ao.getActive().getTarget() == 'B') {
					// 判断用户是否已经填写体重挑战结果
					if (ao.getLastWeight() != null) {
						// 用户已经填写挑战结束后体重
						boolean b = "A".equals(ao.getActive().getTarget().toString())
								? ao.getLastWeight() + ao.getActive().getValue() <= ao.getWeight()
								: ao.getWeight() + ao.getActive().getValue() <= ao.getLastWeight();
						if (b) {
							// 挑战成功
							pob45.setTo(ao.getMember());
							ao.setResult('1');
						} else {
							// 挑战失败
							pob45.setTo(ao.getActive().getInstitution());
							ao.setResult('2');
						}
						//订单金额
						Double money = (ao.getOrderMoney());
						
						//服务费
						Double serviceMoney = money*0;
						
						//结算费用
						Double toMoney = BigDecimal.valueOf((money - serviceMoney)).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
						
						ao.setStatus('3');
						pob45.setOrderId(ao.getId());
						pob45.setType(2);
						pob45.setService(serviceMoney);
						pob45.setMoney(toMoney);
						pob45.setFrom(ao.getMember());
						pob45.setBalanceTime(new Date());
						pob45.setOrderNo(ao.getNo());
						pob45.setProdName(ao.getActive().getName());
						pob45.setOrderMoney(money);
						List<CommonId> list1 = new ArrayList<CommonId>();
						ao.setOrderBalanceTime(new Date());
						list1.add(ao);
						list1.add(pob45);
						service.saveOrUpdate(list1);
					} else {
						//到期，用户还未填写体重结果，将体重挑战设为已结束状态，以便前端可以让用户填写结束体重
						ao.setResult('3');
						service.saveOrUpdate(ao);
					}

				} else if (ao.getActive().getTarget() == 'D') {
					List<Map<String, Object>> signList = service.queryForList(
							"SELECT * FROM tb_sign_in si WHERE si.memberSign = ? AND si.signDate >= ? AND si.signDate< ?",
							ao.getMember().getId(), startDate, endDate);
					int signNum = signList.size();// 健身次数
					if (ao.getActive().getValue().intValue() <= (signNum)) {// 挑战成功
						pob45.setTo(ao.getMember());
						ao.setResult('1');
					} else {// 挑战失败
						pob45.setTo(ao.getActive().getInstitution());
						ao.setResult('2');
					}
					
					//订单金额
					Double money = (ao.getOrderMoney());
					
					//服务费
					Double serviceMoney = money*0;
					
					//结算费用
					Double toMoney = BigDecimal.valueOf((money - serviceMoney)).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();

					ao.setStatus('3');
					pob45.setOrderId(ao.getId());
					//挑战订单为2
					pob45.setType(2);
					pob45.setService(serviceMoney);
					pob45.setMoney(toMoney);
					pob45.setFrom(ao.getMember());
					pob45.setBalanceTime(new Date());
					pob45.setOrderNo(ao.getNo());
					pob45.setProdName(ao.getActive().getName());
					pob45.setOrderMoney(money);
					List<CommonId> list1 = new ArrayList<CommonId>();
					ao.setOrderBalanceTime(new Date());
					list1.add(ao);
					list1.add(pob45);
					service.saveOrUpdate(list1);

				}

			}
		}
	}

	
//	@Scheduled(cron = "0 26 20 * * ?") // 秒 分 时 日 月 周 * 年
//	@Transactional(propagation = Propagation.REQUIRED)
//	public void test4Ddou(){
//		 String sqlx = "select ao.id from tb_active_order ao, tb_member m where ao.member = m.id and ao.status = 1 ";
//		 List<Map<String, Object>> maps = DataBaseConnection.getList(sqlx, null);
//		 String dou = "";
//		 for (int i = 0; i < maps.size(); i++) {
//			  dou += maps.get(i).get("id") + ",";
//		}
//		dou = dou.substring(0, dou.length()-1);
//		final List<?> list = service.findObjectBySql("from ActiveOrder ao where ao.id in(" + dou + ")");
//		for (Object object : list) {
//			ActiveOrder ao = (ActiveOrder) object;
//			System.err.println("用户id: " + ao.getMember().getId() + " 用户名： " + ao.getMember().getName());
//		}
//		
//	}

}
