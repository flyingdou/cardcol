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
import com.freegym.web.order.PlanOrder;
import com.freegym.web.service.IOrderService;

/**
 * 计划订单结算
 * @author dou
 *
 */
@Component
public class planNewBalanceSchedule {
	
	@Qualifier("orderService")
	@Autowired(required = true)
	private IOrderService service;
	
	
	private static Double serviceRate = 0.80;

	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	
	
	/**
	 * 计划订单结算
	 */
	@Scheduled(cron = "0 0 4 * * ? ")
	@Transactional(propagation = Propagation.REQUIRED)
	public void execute () {
		try {
			//将所有有效的plan订单查询出来
			
			String sqlx = "select po.id from TB_PlanRelease_ORDER po, tb_member m where po.member = m.id and po.status = 1 ";
			 List<Map<String, Object>> maps = DataBaseConnection.getList(sqlx, null);
			 String dou = "";
			 // 平台账户
			 Member serviceMember = (Member) service.load(Member.class,Long.valueOf(1));
			 for (int i = 0; i < maps.size(); i++) {
				  dou += maps.get(i).get("id") + ",";
			}
			List<?> list = new ArrayList<>();
			if (dou != null && dou != ""){
				dou = dou.substring(0, dou.length()-1);
				list = service.findObjectBySql("from PlanOrder po where po.id in(" + dou + ")");
			} else {
				list = new ArrayList<>();
			}
			
			// 产品订单结算明细
			List<ProductOrderBalance45> list1 = new ArrayList<ProductOrderBalance45>();
			// 产品订单
			List<PlanOrder> planOrders = new ArrayList<PlanOrder>(); 
			//迭代器遍历
			for (final Iterator<?> it = list.iterator(); it.hasNext();) {
				
				final PlanOrder po = (PlanOrder) it.next();
				
				Date orderDate = sdf.parse(sdf.format(po.getOrderDate()));
			    //进入结算的订单
				if (orderDate.getTime() + ((24*60*1000)-1) < new Date().getTime()) {
					//付款金额
					Double money = po.getOrderMoney();
					
					//服务费
					Double serviceMoney = BigDecimal.valueOf(money*serviceRate).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
					
					//结算金额
					Double moneyTo = BigDecimal.valueOf((money - serviceMoney)).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
					
					ProductOrderBalance45 pob = new ProductOrderBalance45();
					pob.setBalanceTime(new Date());
					//计划订单为4
					pob.setType(4);
					pob.setFrom(po.getMember());
					pob.setTo(po.getPlanRelease().getMember());
					pob.setMoney(moneyTo);
					pob.setOrderId(po.getId());
					pob.setOrderMoney(money);
					pob.setOrderNo(po.getNo());
					pob.setProdName(po.getPlanRelease().getPlanName());
					pob.setService(serviceMoney);
					list1.add(pob);
					
					po.setStatus('3');
					po.setOrderBalanceTime(new Date());
					planOrders.add(po);
					
					ProductOrderBalance45 prod45 = new ProductOrderBalance45();
					prod45.setTo(serviceMember);
					prod45.setService(0.0);
					prod45.setMoney(serviceMoney);
					prod45.setBalanceTime(new Date());
					prod45.setFrom(po.getMember());
					prod45.setType(4);
					prod45.setOrderId(po.getId());
					prod45.setOrderNo(po.getNo());
					prod45.setProdName(po.getPlanRelease().getPlanName());
					prod45.setOrderMoney(po.getOrderMoney());
					list1.add(prod45);
				}
				
			}
			
			service.saveOrUpdate(list1);
			service.saveOrUpdate(planOrders);
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	
	
	

}
