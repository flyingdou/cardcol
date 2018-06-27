package com.cardcol.web.balance;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
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

import com.cardcol.web.order.ProductOrder45;
import com.cardcol.web.order.ProductOrderBalance45;
import com.databaseConnection.DataBaseConnection;
import com.freegym.web.basic.Member;
import com.freegym.web.service.IOrderService;

/**
 * 一卡通订单结算
 * 
 * @author Admin
 *
 */
@Component
public class OneCardBalanceSchedule {

	@Qualifier("orderService")
	@Autowired(required = true)
	private IOrderService service;

	// 活动开始时间
	private static final String ACTIVE_START_TIME = "2018-03-02 00:00:00";
	
	// 活动结束时间
	private static final String ACTIVE_END_TIME = "2019-03-01 23:59:59";
	
	// simpleDateFormat
	private static  SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	// 一卡通丙方比例为20%
	private static final double DEFALUT_RATE_20 = 0.20;
	
	// 八通卡id
	private static final int ACTIVE_CARD_1 = 1;
	
	// 八通卡结算金额
	private static final double CARD_BALANCE_MONEY_1 = 88.00;
	
	// 八达卡id
	private static final int ACTIVE_CARD_2 = 2;
	
	// 八达卡结算金额
	private static final double CARD_BALANCE_MONEY_2 = 168.00;
	
	// 八畅卡id
	private static final int ACTIVE_CARD_3 = 3;
	
	// 八畅卡结算金额
	private static final double CARD_BALANCE_MONEY_3 = 248.00;
	

	@SuppressWarnings("unused")
	@Scheduled(cron = "0 50 2 * * ? ")
	public void execute() {
		try {
		// 查询所有已经付款的一卡通订单
		String sqlx = "select po.id from tb_product_order_v45 po, tb_member m where po.member = m.id and po.status = 1 ";
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
			list = service.findObjectBySql("from ProductOrder45 po where po.id in(" + dou + ")");
		} else {
			list = new ArrayList<>();
		}
		
		List<ProductOrderBalance45> list1 = new ArrayList<ProductOrderBalance45>(); // 产品订单结算明细
		List<ProductOrder45> ProductOrder45s = new ArrayList<ProductOrder45>(); // 产品订单
		
		for (final Iterator<?> it = list.iterator(); it.hasNext();) {
			final ProductOrder45 po = (ProductOrder45) it.next();
			// 定义总天数
			Integer time = 0;
			Date balanceEndDate;// 订单结算日
			Calendar calendar = Calendar.getInstance();
			Date balanceStartDate = po.getOrderStartTime();// 订单开始计算日
			if (balanceStartDate == null) {
				continue;
			}
			
			// 根据订单开始时间 计算订单结束时间
			calendar.setTime(balanceStartDate);
			
			if (po.getProduct() != null) {
				Integer period = po.getProduct().getPeriod();
				String periodUnit = po.getProduct().getPeriodUnit();
				time = 	periodUnit.equals("D") ?
							period
						:
							periodUnit.equals("A") ? 
										period * 30
									:
										periodUnit.equals("B") ?
													period * 3 * 30 
												:
													period * 12 * 30;
			}
			
			calendar.add(Calendar.DATE, time);
			balanceEndDate = calendar.getTime();
			// 如果结算结束日期小于或等于今天
			Date now = new Date();
			if (balanceEndDate.compareTo(now) <= 0) {
				
				// 当前订单的付款金额
				double Money = po.getOrderMoney();
				
				// 活动期间，结算变动
				if (po.getOrderDate().after(sdf.parse(ACTIVE_START_TIME)) && po.getOrderDate().before(sdf.parse(ACTIVE_END_TIME))) {
					if (po.getProduct().getId() == ACTIVE_CARD_1) {
						Money = po.getOrderMoney() > CARD_BALANCE_MONEY_1 ? CARD_BALANCE_MONEY_1 : po.getOrderMoney();
					}
					if (po.getProduct().getId() == ACTIVE_CARD_2) {
						Money = po.getOrderMoney() > CARD_BALANCE_MONEY_2 ? CARD_BALANCE_MONEY_2 : po.getOrderMoney();
					}
					if (po.getProduct().getId() == ACTIVE_CARD_3) {
						Money = po.getOrderMoney() > CARD_BALANCE_MONEY_3 ? CARD_BALANCE_MONEY_3 : po.getOrderMoney();
					}
				}
				// 服务费
				double service2 = Money * DEFALUT_RATE_20;
				
				
				// 查询该用户签到表 signInList 获取 被签到方 订单 id 签到次数  结算总金额（结算金额 + 服务费）结算次数
				final List<Map<String, Object>> signInList = service.queryForList(
						"select a.memberAudit, a.orderId, a.cnt, b.balanceMoney, b.balanceNum from (select si.memberAudit, si.orderId, count(*) cnt from tb_sign_in si where si.orderId = ? and signDate> ? and si.signDate <= ? group by si.memberAudit, si.orderId ) a left join (select ORDER_ID, sum(pb.BALANCE_MONEY + pb.BALANCE_SERVICE) balanceMoney, count(*) balanceNum from TB_ORDER_BALANCE_V45 pb group by ORDER_ID) b on a.orderid = b.ORDER_ID",
						po.getId(), balanceStartDate, balanceEndDate);
				if (signInList.size() != 0) {
					// 用户当月签到了 则结算订单 计算出每家可以分多少钱
					BigDecimal balanceMoney = signInList.get(0).get("BALANCEMONEY") == null
							? new BigDecimal(0) : BigDecimal.valueOf(Double.parseDouble(signInList.get(0).get("BALANCEMONEY").toString()));
					Long signCnt = 0l;// 签到总次数
					
					for (final Map<String, Object> map : signInList) {
						Long num = Long.parseLong(map.get("CNT").toString());
						signCnt += num;
					}

					for (Map<String, Object> map :  signInList ) {
						Long num = Long.parseLong(map.get("CNT").toString());
						// 结算表 TB_ORDER_BALANCE_V45
						ProductOrderBalance45 pob45 = new ProductOrderBalance45();
						//收款方
						pob45.setTo(new Member((Long) map.get("MEMBERAUDIT")));
						//平台服务费
						pob45.setService(BigDecimal.valueOf(service2).divide(BigDecimal.valueOf(signInList.size()), RoundingMode.HALF_UP).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
						//俱乐部的收入
						 BigDecimal dd = BigDecimal.valueOf((Money - service2));
						 BigDecimal ss = BigDecimal.valueOf(num).divide(BigDecimal.valueOf(signCnt), 2, RoundingMode.HALF_UP);
						
						 
						pob45.setMoney(dd.multiply(ss).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
						//结算时间
						pob45.setBalanceTime(new Date());
						//付款方
						pob45.setFrom(po.getMember());
						pob45.setType(1);
						pob45.setOrderId(po.getId());
						pob45.setOrderNo(po.getNo());
						pob45.setProdName(po.getProduct().getName());
						pob45.setOrderMoney(po.getOrderMoney());
						list1.add(pob45);
					}
					
					// 给平台添加一条服务费收入记录
					ProductOrderBalance45 prod45 = new ProductOrderBalance45();
					prod45.setTo(serviceMember);
					prod45.setService(0.0);
					
					// 活动期间，结算变动，服务费变动
					if (po.getOrderDate().after(sdf.parse(ACTIVE_START_TIME)) && po.getOrderDate().before(sdf.parse(ACTIVE_END_TIME))) {
						if (po.getProduct().getId() == ACTIVE_CARD_1) {
							if (po.getOrderMoney() > CARD_BALANCE_MONEY_1) {
								service2 = service2 + (po.getOrderMoney() - CARD_BALANCE_MONEY_1);
							}
						}
						if (po.getProduct().getId() == ACTIVE_CARD_2) {
							if (po.getOrderMoney() > CARD_BALANCE_MONEY_2) {
								service2 = service2 + (po.getOrderMoney() - CARD_BALANCE_MONEY_2);
							}
						}
						if (po.getProduct().getId() == ACTIVE_CARD_3) {
							if (po.getOrderMoney() > CARD_BALANCE_MONEY_3) {
								service2 = service2 + (po.getOrderMoney() - CARD_BALANCE_MONEY_3);
							}
						}
					}
					
					prod45.setMoney(service2);
					prod45.setBalanceTime(new Date());
					prod45.setFrom(po.getMember());
					prod45.setType(1);
					prod45.setOrderId(po.getId());
					prod45.setOrderNo(po.getNo());
					prod45.setProdName(po.getProduct().getName());
					prod45.setOrderMoney(po.getOrderMoney());
					list1.add(prod45);
					
				} else {
                    //若该卡在有效期内用户未签到过，则费用全部归属平台
					// 给平台添加一条服务费收入记录
					ProductOrderBalance45 prod45 = new ProductOrderBalance45();
					prod45.setTo(serviceMember);
					prod45.setService(0.0);
					prod45.setMoney(po.getOrderMoney());
					prod45.setBalanceTime(new Date());
					prod45.setFrom(po.getMember());
					prod45.setType(1);
					prod45.setOrderId(po.getId());
					prod45.setOrderNo(po.getNo());
					prod45.setProdName(po.getProduct().getName());
					prod45.setOrderMoney(po.getOrderMoney());
					list1.add(prod45);
				}
				
				
				
				if (signInList.size() != 0) {
					// 更新订单状态。若已经结算完成
					Long balanceNum = (Long) signInList.get(0).get("BALANCENUM") == null ? new Long(0)
							: Long.parseLong(signInList.get(0).get("BALANCENUM").toString());
					// 如果该订单没结算过 则保存该订单
						po.setStatus('3');
						po.setOrderBalanceTime(new Date());
						ProductOrder45s.add(po);
				} else {
					// 如果该用户没有签到过
					po.setStatus('3');
					po.setOrderBalanceTime(new Date());
					ProductOrder45s.add(po);
				}
			}
		}
		service.saveOrUpdate(list1);
		service.saveOrUpdate(ProductOrder45s);
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	
	
	
	/*
	 * 2017-7-8 修改原来的逻辑 将 月结算 变成了日结算
	 * 
	 * 第二次修改 
	 */
//	@Scheduled(cron = "0 0 2 * * ? ")
//	public void execute2() {
//		// 查询所有已经付款的一卡通订单
//		final List<?> list = service.findObjectBySql("from ProductOrder45 po where po.status = ?", '1');
//		List<ProductOrderBalance45> list1 = new ArrayList<ProductOrderBalance45>(); // 产品订单结算明细
//		List<ProductOrder45> ProductOrder45s = new ArrayList<ProductOrder45>(); // 产品订单
//		
//		for (final Iterator<?> it = list.iterator(); it.hasNext();) {
//			final ProductOrder45 po = (ProductOrder45) it.next();
//			// 定义总天数
//			Integer time = 0;
//			Date balanceEndDate;// 订单结算日
//			Calendar calendar = Calendar.getInstance();
//			Date balanceStartDate = po.getOrderStartTime();// 订单开始计算日
//			
//			// 根据订单开始时间 计算订单结束时间
//			calendar.setTime(balanceStartDate);
//			
//			if (po.getProduct() != null) {
//				Integer period = po.getProduct().getPeriod();
//				String periodUnit = po.getProduct().getPeriodUnit();
//				time = 	periodUnit.equals("D") ?
//							period
//						:
//							periodUnit.equals("A") ? 
//										period * 30
//									:
//										periodUnit.equals("B") ?
//													period * 3 * 30 
//												:
//													period * 12 * 30;
//			}
//			
//			calendar.add(Calendar.DATE, time);
//			calendar.add(Calendar.DATE, 1);
//			balanceEndDate = calendar.getTime();
//			DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
//			// 如果今天是结算结束日期
//			if (formatter.format(balanceEndDate).equals(formatter.format(new Date()))) {
////			if (balanceEndDate.compareTo(new Date()) <= 0) {
//				// 一次出勤所用的钱
//				Double onceMoney = po.getOrderMoney() / time;
//				// 服务费
//				double service2 = onceMoney * DEFALUT_RATE;
//				// 查询该用户签到表 signInList 获取 被签到方 订单 id 签到次数  结算总金额（结算金额 + 服务费）结算次数
//				final List<Map<String, Object>> signInList = service.queryForList(
//						"select a.memberAudit, a.orderId, a.cnt, b.balanceMoney, b.balanceNum from (select si.memberAudit, si.orderId, count(*) cnt from tb_sign_in si where si.orderId = ? and signDate> ? and si.signDate <= ? group by si.memberAudit, si.orderId ) a left join (select ORDER_ID, sum(pb.BALANCE_MONEY + pb.BALANCE_SERVICE) balanceMoney, count(*) balanceNum from TB_ORDER_BALANCE_V45 pb group by ORDER_ID) b on a.orderid = b.ORDER_ID",
//						po.getId(), balanceStartDate, balanceEndDate);
//				if (signInList.size() != 0) {
//					// 用户当月签到了 则结算订单 计算出每家可以分多少钱
//					BigDecimal balanceMoney = signInList.get(0).get("BALANCEMONEY") == null
//							? new BigDecimal(0) : BigDecimal.valueOf(Double.parseDouble(signInList.get(0).get("BALANCEMONEY").toString())) ;
//					// 如果该订单已经结算过了 ？
//					if (balanceMoney.doubleValue() + onceMoney > po.getOrderMoney())
//						onceMoney = po.getOrderMoney() - balanceMoney.doubleValue();
//					Long signCnt = 0l;// 签到总次数
//					
//					for (final Map<String, Object> map : signInList) {
//						Long num = Long.parseLong(map.get("CNT").toString());
//						signCnt += num;
//					}
//
//					for (Map<String, Object> map :  signInList ) {
//						Long num = Long.parseLong(map.get("CNT").toString());
//						ProductOrderBalance45 pob45 = new ProductOrderBalance45();// 结算表 TB_ORDER_BALANCE_V45
//						pob45.setTo(new Member((Long) map.get("MEMBERAUDIT")));
//						pob45.setService(service2);
//						pob45.setMoney((onceMoney - service2) * (num / signCnt));
//						pob45.setBalanceTime(new Date());
//						pob45.setFrom(po.getMember());
//						pob45.setType(1);
//						pob45.setOrderId(po.getId());
//						pob45.setOrderNo(po.getNo());
//						pob45.setProdName(po.getProduct().getName());
//						pob45.setOrderMoney(po.getOrderMoney());
//						list1.add(pob45);
//					}
//				} else {
//					// 用户当月没签到 则查询出该卡所有的关联俱乐部 然后所有俱乐部平分收益
//					final List<?> clubList = service.findObjectBySql("from ProductClub45 pc where pc.product = ?",
//							po.getProduct());
//					for (final Iterator<?> it1 = clubList.iterator(); it1.hasNext();) {
//						final ProductClub45 pc = (ProductClub45) it1.next();
//						ProductOrderBalance45 pob45 = new ProductOrderBalance45();// 结算表
//						pob45.setTo(pc.getClub());
//						pob45.setService(service2 / clubList.size());
//						pob45.setMoney((onceMoney - service2) / clubList.size());
//						pob45.setBalanceTime(new Date());
//						pob45.setFrom(po.getMember());
//						pob45.setType(1);
//						pob45.setOrderId(po.getId());
//						pob45.setOrderNo(po.getNo());
//						pob45.setProdName(po.getProduct().getName());
//						pob45.setOrderMoney(po.getOrderMoney());
//						list1.add(pob45);
//					}
//				}
//				
//				if (signInList.size() != 0) {
//					// 更新订单状态。若已经结算完成
//					Long balanceNum = (Long) signInList.get(0).get("BALANCENUM") == null ? new Long(0)
//							: Long.parseLong(signInList.get(0).get("BALANCENUM").toString());
//					// 如果该订单没结算过 则保存该订单
//					if (time == balanceNum + 1) {
//						po.setStatus('3');
//						po.setOrderBalanceTime(new Date());
//						ProductOrder45s.add(po);
//					}
//				} else {
//					// 如果该用户没有签到过
//					po.setStatus('3');
//					po.setOrderBalanceTime(new Date());
//					ProductOrder45s.add(po);
//				}
//			}
//		}
//		
//		service.saveOrUpdate(list1);
//		service.saveOrUpdate(ProductOrder45s);
//	}
	

	/*
	 * 原来的结算逻辑
	 */
//	@Scheduled(cron = "0 0 0 * * ?")
//	public void execute() {
//		final List<?> list = service.findObjectBySql("from ProductOrder45 po where po.status = ?", '1');
//		//Double cost = Double.parseDouble(messageResource.getMessage("order.balance.onecard.cost", null, Locale.CHINA));
//		List list1 = new ArrayList();
//		for (final Iterator<?> it = list.iterator(); it.hasNext();) {
//			final ProductOrder45 po = (ProductOrder45) it.next();
//			Date balanceDate = po.getOrderBalanceTime() == null ? po.getOrderStartTime() : po.getOrderBalanceTime();
//			Calendar calendar = Calendar.getInstance();
//			calendar.setTime(balanceDate);
//			calendar.add(Calendar.MONTH, 1);
//			calendar.add(Calendar.DATE, 1);
//			Date balanceDateAddOneMonth = calendar.getTime();
//			DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
//			if (formatter.format(balanceDateAddOneMonth).equals(formatter.format(new Date()))) {
//				// 每次应付金额
//				Integer time = 0;
//				if (po.getProduct() != null) {
//					Integer period = po.getProduct().getPeriod();
//					String periodUnit = po.getProduct().getPeriodUnit();
//					time = periodUnit.equals("A") ? period : periodUnit.equals("B") ? period * 3 : period * 12;
//				}
//				Double onceMoney = po.getOrderMoney() / time;
//				double service2 = onceMoney * 15 / 100;
//				// 查询该用户签到表list
//				final List<Map<String, Object>> signInList = service.queryForList(
//						"select a.memberAudit, a.orderId, a.cnt, b.balanceMoney, b.balanceNum from (select si.memberAudit, si.orderId, count(*) cnt from tb_sign_in si where si.orderId = ? and signDate> ? and si.signDate <= ? group by si.memberAudit, si.orderId ) a left join (select pb.BALANCE_ORDER, sum(pb.BALANCE_MONEY + pb.BALANCE_SERVICE) balanceMoney, count(*) balanceNum from TB_PRODUCT_ORDER_BALANCE_V45 pb group by pb.BALANCE_ORDER) b on a.orderid = b.BALANCE_ORDER",
//						po.getId(), balanceDate, balanceDateAddOneMonth);
//				if (signInList.size() != 0) {
//					BigDecimal balanceMoney = (BigDecimal) signInList.get(0).get("BALANCEMONEY") == null
//							? new BigDecimal(0) : (BigDecimal) signInList.get(0).get("BALANCEMONEY");
//					if (balanceMoney.doubleValue() + onceMoney > po.getOrderMoney())
//						onceMoney = po.getOrderMoney() - balanceMoney.doubleValue();
//					//
//					Long signCnt = 0l;
//					for (final Map<String, Object> map : signInList) {
//						Long num = Long.parseLong(map.get("CNT").toString());
//						signCnt += num;
//					}
//
//					for (Map<String, Object> map : signInList) {
//						Long num = Long.parseLong(map.get("CNT").toString());
//						ProductOrderBalance45 pob45 = new ProductOrderBalance45();// 结算表
//						pob45.setTo(new Member((Long) map.get("MEMBERAUDIT")));
//						pob45.setService(service2);
//						pob45.setMoney((onceMoney - service2) * (num / signCnt));
//						pob45.setBalanceTime(new Date());
//						pob45.setFrom(po.getMember());
//						pob45.setType(1);
//						pob45.setOrderId(po.getId());
//						pob45.setOrderNo(po.getNo());
//						pob45.setProdName(po.getProduct().getName());
//						pob45.setOrderMoney(po.getOrderMoney());
//						list1.add(pob45);
//					}
//				} else {// 用户当月没签到
//					final List<?> clubList = service.findObjectBySql("from ProductClub45 pc where pc.product = ?",
//							po.getProduct());
//					for (final Iterator<?> it1 = list.iterator(); it1.hasNext();) {
//						final ProductClub45 pc = (ProductClub45) it1.next();
//						ProductOrderBalance45 pob45 = new ProductOrderBalance45();// 结算表
//						pob45.setTo(pc.getClub());
//						pob45.setService(onceMoney * 15 / 100);
//						pob45.setMoney((onceMoney - service2) / clubList.size());
//						pob45.setBalanceTime(new Date());
//						pob45.setFrom(po.getMember());
//						pob45.setType(1);
//						pob45.setOrderId(po.getId());
//						pob45.setOrderNo(po.getNo());
//						pob45.setProdName(po.getProduct().getName());
//						pob45.setOrderMoney(po.getOrderMoney());
//						list1.add(pob45);
//					}
//				}
//				// 更新订单状态。若已经结算完成
//				Long balanceNum = (Long) signInList.get(0).get("BALANCENUM") == null ? new Long(0)
//						: Long.parseLong(signInList.get(0).get("BALANCENUM").toString());
//				if (time == balanceNum + 1) {
//					po.setStatus('3');
//					po.setOrderBalanceTime(new Date());
//					list1.add(po);
//				}
//			}
//		}
//		service.saveOrUpdate(list1);
//	}

}
