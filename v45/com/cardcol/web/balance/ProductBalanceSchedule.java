package com.cardcol.web.balance;

import java.math.BigDecimal;
import java.text.ParseException;
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
import com.freegym.web.order.ProductOrder;
import com.freegym.web.service.IOrderService;

/**
 * 
 * @author dou 2018-04-19
 * product商品订单结算业务
 * 
 */
@Component
public class ProductBalanceSchedule {

	@Qualifier("orderService")
	@Autowired(required = true)
	private IOrderService service;
	
	
	/**
	 * 平台收费比例
	 */
	private Double SERVICE_RATE = 0.08;
	
	
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	
    /**
     * 目前其中包含俱乐部发布的预付卡，教练发布的私教套餐，直播订单
     */
	@Scheduled(cron = "0 10 3 * * ? ")
	@Transactional(propagation = Propagation.REQUIRED)
	public void doBalance() {
		try {
			
			// 将可以结算的预付卡、私教套餐、直播订单查询出来
			String sqlx = " select po.id from tb_product_order po, tb_member mm where po.status = 1  "
						+ " and po.product in (select p.id from tb_product p, tb_member m  where p.type in (3,5) and m.id = p.member) and po.member = mm.id ";
			balanceProduct(sqlx);
			
			
		} catch (Exception e) {
		  e.printStackTrace();
		}
		
		
	}
	
	
	/**
	 * 俱乐部发布的健身卡结算
	 */
	@Scheduled(cron = "0 15 3 * * ? ")
	@Transactional(propagation = Propagation.REQUIRED)
	public void clubProductBalance() {
		try {
			String sqlx = " select po.id from tb_product_order po, tb_member m where po.status = 1 " 
					    + " and po.product in (select p.id from tb_product p, tb_member mm where p.type = 1 and p.proType = 1 and p.member = mm.id ) and po.member = m.id "; 
			balanceProduct(sqlx);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	
	/**
	 * product订单通用结算逻辑
	 * @throws ParseException 
	 */
	public void balanceProduct (String sqlx) throws ParseException {
    		
    		List<Map<String, Object>> maps = DataBaseConnection.getList(sqlx, null);
    		
    		String dou = "";
    		
    		// 平台账户
			Member serviceMember = (Member) service.load(Member.class,Long.valueOf(1));
    		
    		// 循环处理每条数据
    		for (Map<String, Object> map : maps) {
    			dou += map.get("id") + ",";
    		}
    		
    		List<?> list = new ArrayList<>();
    		if (dou != null && dou != ""){
    			dou = dou.substring(0, dou.length()-1);
    			list = service.findObjectBySql("from ProductOrder po where po.id in(" + dou + ")");
    		} else {
    			list = new ArrayList<>();
    		}
    		

    		// 产品订单结算明细
    		List<ProductOrderBalance45> list1 = new ArrayList<ProductOrderBalance45>();
    		// 产品订单
    		List<ProductOrder> productOrders = new ArrayList<ProductOrder>();
    		
    		// 迭代遍历
    		for (final Iterator<?> it = list.iterator(); it.hasNext();) {
    			ProductOrder po = (ProductOrder) it.next();
    			
    			// 订单时间
    			Date orderDate = sdf.parse(sdf.format(po.getOrderDate()));
    			
    			//进入结算的订单
				if (orderDate.getTime() + ((24*60*1000)-1) < new Date().getTime()) {
					//付款金额
					Double money = po.getOrderMoney();
					
					//服务费
					Double serviceMoney = BigDecimal.valueOf(money*SERVICE_RATE).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
					
					//结算金额
					Double moneyTo = BigDecimal.valueOf((money - serviceMoney)).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
					
					
					// 甲方获得金额
					ProductOrderBalance45 pob = new ProductOrderBalance45();
					pob.setBalanceTime(new Date());
					// product订单为5
					pob.setType(5);
					pob.setFrom(po.getMember());
					pob.setTo(po.getProduct().getMember());
					pob.setMoney(moneyTo);
					pob.setOrderId(po.getId());
					pob.setOrderMoney(money);
					pob.setOrderNo(po.getNo());
					pob.setProdName(po.getProduct().getName());
					pob.setService(serviceMoney);
					list1.add(pob);
					
					po.setStatus('3');
					po.setOrderBalanceTime(new Date());
					productOrders.add(po);
					
					// 平台获得金额
					ProductOrderBalance45 prod45 = new ProductOrderBalance45();
					prod45.setTo(serviceMember);
					prod45.setService(0.0);
					prod45.setMoney(serviceMoney);
					prod45.setBalanceTime(new Date());
					prod45.setFrom(po.getMember());
					// product订单为5
					prod45.setType(5);
					prod45.setOrderId(po.getId());
					prod45.setOrderNo(po.getNo());
					prod45.setProdName(po.getProduct().getName());
					prod45.setOrderMoney(po.getOrderMoney());
					list1.add(prod45);
				}
				
    		}
    		// 持久化数据
    		service.saveOrUpdate(list1);
    		service.saveOrUpdate(productOrders);
        	
        	
	}
	
	
	
	
	
}
