package com.cardcol.web.balance;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import com.freegym.web.basic.Member;
import com.freegym.web.order.CourseOrder;
import com.freegym.web.service.IOrderService;

/**
 * course订单定时结算业务
 * @author Administrator
 *
 */
@Component
public class courseNewBalanceSchedule {

	@Qualifier("orderService")
	@Autowired(required = true)
	private IOrderService service;
	
	private static Double serviceRate = 0.20;

	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	@Scheduled(cron = "0 30 3 * * ? ")
	@Transactional(propagation = Propagation.REQUIRED)
	public void execute(){
		try {
			//查询所有已付款course订单
			String sqlx = "select co.id from TB_CourseRelease_ORDER co, tb_member m where co.member = m.id and co.status = 1";
			List<Map<String, Object>> maps = DataBaseConnection.getList(sqlx, null);
			String dou = "";
			 // 平台账户
			 Member serviceMember = (Member) service.load(Member.class,Long.valueOf(1));
			for (Map<String, Object> map : maps) {
				dou += map.get("id") + ",";
			}
			
			List<?> list = new ArrayList<>();
			if (dou != null && dou != ""){
				dou = dou.substring(0, dou.length()-1);
				list = service.findObjectBySql("from CourseOrder co  where co.id in (" + dou + ")");
			} else {
				list = new ArrayList<>();
			}
			List<ProductOrderBalance45> list1 = new ArrayList<ProductOrderBalance45>(); // 产品订单结算明细
			List<CourseOrder> courseOrders = new ArrayList<CourseOrder>(); // 产品订单
			
			//查询出来的有效course订单
			for (final Iterator<?> it = list.iterator(); it.hasNext();) {
				final CourseOrder co = (CourseOrder) it.next();
				
				@SuppressWarnings("deprecation")
				Date planDate = new Date(co.getCourse().getPlanDate().replace("-", "/"));
				//可以结算的订单
				if ((planDate.getTime()+(24*60*60*1000 -1)) < new Date().getTime()){
					//付款金额
					Double money = co.getOrderMoney();
					
					//服务费
					Double serviceMoney = BigDecimal.valueOf(money*serviceRate).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
					
					//结算金额
					Double moneyTo = BigDecimal.valueOf((money - serviceMoney)).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
					
					ProductOrderBalance45 pob = new ProductOrderBalance45();
					pob.setBalanceTime(new Date());
					pob.setFrom(co.getMember());
					pob.setMoney(moneyTo);
					pob.setOrderId(co.getId());
					pob.setOrderMoney(money);
					pob.setOrderNo(co.getNo());
					pob.setProdName(co.getCourse().getCourseInfo().getName());
					pob.setService(serviceMoney);
					pob.setTo(co.getCourse().getCourseInfo().getMember());
					//课程订单为3
					pob.setType(3);
					list1.add(pob);
					
					co.setStatus('3');
					co.setOrderBalanceTime(new Date());
					courseOrders.add(co);
					
					ProductOrderBalance45 prod45 = new ProductOrderBalance45();
					prod45.setTo(serviceMember);
					prod45.setService(0.0);
					prod45.setMoney(serviceMoney);
					prod45.setBalanceTime(new Date());
					prod45.setFrom(co.getMember());
					prod45.setType(3);
					prod45.setOrderId(co.getId());
					prod45.setOrderNo(co.getNo());
					prod45.setProdName(co.getCourse().getCourseInfo().getName());
					prod45.setOrderMoney(co.getOrderMoney());
					list1.add(prod45);
				}
				
			}
			
			service.saveOrUpdate(list1);
			service.saveOrUpdate(courseOrders);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	
	
	
}
