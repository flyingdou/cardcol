package com.freegym.web.schedule;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.freegym.web.basic.Member;
import com.freegym.web.order.ProductOrder;
import com.freegym.web.order.Product;
import com.freegym.web.order.factory.BalanceFactory;
import com.freegym.web.order.strategy.IBalanceStrategy;
import com.freegym.web.service.IOrderService;

/**
 * 健身卡结算
 * 
 * @author Admin
 * 
 */
@Component
public class OrderBalanceSchedule {

	protected Logger log = Logger.getLogger(this.getClass());

	@Autowired(required = true)
	@Qualifier("orderService")
	protected IOrderService service;

	@SuppressWarnings("unchecked")
	@Scheduled(cron = "0 0 2 * * ?")
	public void execute() {
		Member cardcol = service.findCardColManager();
		// 交易类型1：预付款2：保证金3：违约金4：缺勤费用5：训练费用6：交易服务费7:交易手续费8：超勤费用9:退款10:提现
		final List<ProductOrder> orderList = (List<ProductOrder>) service.findObjectBySql(" from ProductOrder o where o.status = ? ", '1');
		for (ProductOrder order : orderList) {
			order.setStatus('2');
			if (order.getSurplusMoney() == null) order.setSurplusMoney(order.getOrderMoney());
			Product product = order.getProduct();
			try {
				IBalanceStrategy balance = BalanceFactory.createBalance(product.getProType(), service, order, cardcol);
				balance.balance();
			} catch (Exception e) {
				log.error("订单编号为" + order.getNo() + "的订单结算失败，失败原因：" + e.getMessage(), e);
			}
		}

	}
}
