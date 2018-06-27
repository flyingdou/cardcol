package com.freegym.web.order.strategy;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.freegym.web.basic.Member;
import com.freegym.web.common.Constants;
import com.freegym.web.order.ActiveOrder;
import com.freegym.web.order.ActiveOrderDetail;
import com.freegym.web.order.CourseOrder;
import com.freegym.web.order.CourseOrderDetail;
import com.freegym.web.order.FactoryOrder;
import com.freegym.web.order.FactoryOrderDetail;
import com.freegym.web.order.GoodsOrder;
import com.freegym.web.order.GoodsOrderDetail;
import com.freegym.web.order.Order;
import com.freegym.web.order.OrderDetail;
import com.freegym.web.order.PlanOrder;
import com.freegym.web.order.PlanOrderDetail;
import com.freegym.web.order.ProductOrder;
import com.freegym.web.order.ProductOrderDetail;
import com.freegym.web.service.IOrderService;

public abstract class AbstractBalanceStrategy implements IBalanceStrategy, Constants {

	protected Order order;

	protected IOrderService service;

	protected Member cardcol;

	protected SimpleDateFormat ymd = new SimpleDateFormat("yyyy-MM-dd");

	public AbstractBalanceStrategy(IOrderService service, Order order, Member cardcol) {
		this.order = order;
		this.service = service;
		this.cardcol = cardcol;
		if (this.order.getSurplusMoney() == null) this.order.setSurplusMoney(order.getOrderMoney());
	}

	public void balance() {
		List<OrderDetail> details = calculate();
		List<OrderDetail> orderDetailList = new ArrayList<OrderDetail>();
		for (final OrderDetail detail : details) {
			if (detail.getInOutType() == INOUT_TYPE_IN) {
				// 当前收入的交易费
				detail.setServiceMoney(detail. getDetailMoney() * getRate() / 100);
				detail.setDetailMoney(detail.getDetailMoney() - detail.getServiceMoney());
				// 加入卡库平台的交易费收入
				OrderDetail od = getTransDetail(detail);
				orderDetailList.add(od);
			}
		}
		orderDetailList.addAll(details);

		Date newOrderBalanceTime = new Date();                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                        
		sendMail();
		order.setOrderBalanceTime(newOrderBalanceTime);
		service.saveBalance(order, orderDetailList);
	}  
	
	public void abalance() {
		List<OrderDetail> details = calculate();
		List<OrderDetail> orderDetailList = new ArrayList<OrderDetail>();
		for (final OrderDetail detail : details) {
			if (detail.getInOutType() == INOUT_TYPE_IN) {
				// 当前收入的交易费
				detail.setServiceMoney(detail. getDetailMoney() * getRate() / 100);
				detail.setDetailMoney(detail.getDetailMoney() - detail.getServiceMoney());
				detail.setTransUser(order.getMember());
				detail.setTransObject(cardcol);

			}
		}
		orderDetailList.addAll(details);
		
		Date newOrderBalanceTime = new Date();                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                        
		sendMail();
		order.setOrderBalanceTime(newOrderBalanceTime);
		service.saveBalance(order, orderDetailList);
	}  
     
	public void bbalance(){
		List<OrderDetail> details = calculate();
		List<OrderDetail> orderDetailList = new ArrayList<OrderDetail>();
		for (final OrderDetail detail : details) {
			if (detail.getInOutType() == INOUT_TYPE_IN) {
				// 卡库当前收入的交易费
				detail.setServiceMoney(detail. getDetailMoney() * 10 / 100);
				detail.setDetailMoney(detail.getDetailMoney() - detail.getServiceMoney());
				// 加入教练的交易费收入
				OrderDetail od = getCoachTransDetail(detail);
				orderDetailList.add(od);
			}
		}
		orderDetailList.addAll(details);

		Date newOrderBalanceTime = new Date();                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                        
		sendMail();
		order.setOrderBalanceTime(newOrderBalanceTime);
		service.saveBalance(order, orderDetailList);
	}
	
	protected abstract int getRate();

	private OrderDetail getTransDetail(OrderDetail detail) {
		OrderDetail od = null;
		if (detail instanceof ProductOrderDetail) {
			od = new ProductOrderDetail();
			((ProductOrderDetail) od).setOrder((ProductOrder) order);
		}
		else if(detail instanceof ActiveOrderDetail){
			od = new ActiveOrderDetail();
			((ActiveOrderDetail) od).setOrder((ActiveOrder) order);
		}
		else if(detail instanceof GoodsOrderDetail){
			od = new GoodsOrderDetail();
			((GoodsOrderDetail) od).setOrder((GoodsOrder) order);
		}
		else if(detail instanceof CourseOrderDetail){
			od = new CourseOrderDetail();
			((CourseOrderDetail) od).setOrder((CourseOrder) order);
		}
		else if(detail instanceof FactoryOrderDetail){
			od = new FactoryOrderDetail();
			((FactoryOrderDetail) od).setOrder((FactoryOrder) order);
		}
		else if(detail instanceof PlanOrderDetail){
			od = new PlanOrderDetail();
			((PlanOrderDetail) od).setOrder((PlanOrder) order);
		}
		od.setTransUser(cardcol);
		od.setTransObject(detail.getTransUser());
		od.setInOutType(INOUT_TYPE_IN);
		od.setDetailDate(detail.getDetailDate());
		// 交易服务费
		od.setDetailType('6');
		od.setDetailMoney(detail.getServiceMoney());
		od.setBalanceTimes(detail.getBalanceTimes());
		return od;
	}
	private OrderDetail getCoachTransDetail(OrderDetail detail) {
		OrderDetail od = null;
		if (detail instanceof ProductOrderDetail) {
			od = new ProductOrderDetail();
			((ProductOrderDetail) od).setOrder((ProductOrder) order);
		}
		od.setTransUser(detail.getTransObject().getCoach());
		od.setTransObject(cardcol);
		od.setInOutType(INOUT_TYPE_IN);
		od.setDetailDate(detail.getDetailDate());
		// 交易服务费
		od.setDetailType('6');
		od.setDetailMoney(detail.getServiceMoney());
		od.setBalanceTimes(detail.getBalanceTimes());
		return od;
	}

	protected abstract List<OrderDetail> calculate();

	protected void sendMail() {
		// 给买方发送邮件
		Member member = getMemberInfo();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("serviceTell", cardcol.getTell());
		root.put("serviceQQ", cardcol.getQq());
		root.put("serviceHandset", cardcol.getMobilephone());
		root.put("serviceEmail", cardcol.getEmail());
		root.put("memberName", order.getMember().getNick());
		if (order instanceof ProductOrder) {
			ProductOrder po = (ProductOrder) order;
			root.put("startTime", po.getBalanceTimes());
		} else {
			root.put("startTime", 1);
		}
		
		root.put("endTime", sdf.format(new Date()));
		root.put("orderMember", member.getName());
		root.put("orderNo", order.getNo());
		root.put("money", order.getOrderMoney());
		String str = service.processTemplateIntoString("balance-remind-buy.ftl", root);
		service.sendMail(order.getMember().getEmail(), "卡库网“结算”提醒邮件", str);
		// 给卖方发送邮件
		root.put("memberName", member.getName());
		if (order instanceof ProductOrder) {
			ProductOrder po = (ProductOrder) order;
			root.put("startTime", po.getBalanceTimes());
		} else {
			root.put("startTime", 1);
		}
		Date d=new Date();
		root.put("endTime", d.toString());
		root.put("orderMember", order.getMember().getNick());
		root.put("orderNo", order.getNo());
		str = service.processTemplateIntoString("balance-remind-sell.ftl", root);
		service.sendMail(member.getEmail(), "卡库网“结算”提醒邮件", str);

	}

	private Member getMemberInfo() {
		if (order instanceof ProductOrder) return ((ProductOrder) order).getProduct().getMember();
		else if (order instanceof ActiveOrder) return ((ActiveOrder) order).getActive().getCreator();
		else if (order instanceof PlanOrder) return ((PlanOrder) order).getPlanRelease().getMember();
		else if (order instanceof CourseOrder) return ((CourseOrder) order).getCourse().getMember();
		else if (order instanceof GoodsOrder) return (Member) service.load(Member.class, ((GoodsOrder) order).getGoods().getMember());
		else if (order instanceof FactoryOrder) return (Member) service.load(Member.class, ((FactoryOrder) order).getFactoryCosts().getFactory().getClub());
		return null;
	}

	protected void changeOrderStatus(Order order) {
		order.setStatus('2');
		service.saveOrUpdate(order);

	}
}
