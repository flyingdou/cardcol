package com.freegym.web.chinapay.action;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.httpclient.util.DateUtil;
import org.apache.log4j.Logger;

import com.chinaums.pay.api.entities.OrderEntity;
import com.chinaums.pay.api.impl.DefaultSecurityService;
import com.chinaums.pay.api.impl.UMSPayServiceImpl;
import com.freegym.web.mobile.BasicJsonAction;
import com.freegym.web.order.Order;
//import com.freegym.web.order.ActiveOrder;
//import com.freegym.web.order.Order;
//import com.freegym.web.order.ProductOrder;
import com.sanmen.web.core.component.UserMessageResource;
import com.sanmen.web.core.integrate.ServiceLocator;

public class ChinapayUtils {

	private Logger log = Logger.getLogger(this.getClass());

	private UserMessageResource messageResource;

	private Order order;

	private String subject;
	
	
	
	@SuppressWarnings("unused")
	private BasicJsonAction action;


	@SuppressWarnings("unused")
	private List<Map<String, Object>> map;
	

	public ChinapayUtils(Order order, String subject) {
		this.order = order;
		this.subject = subject;
		this.messageResource = (UserMessageResource) ServiceLocator.getInstance().getService("messageResource");
	}

	public ChinapayUtils(BasicJsonAction action, List<Map<String, Object>> map) {
		this.action = action;
		this.map = map;
	}
	
	

	public String order() throws Exception {
		final DecimalFormat df = new DecimalFormat("#0.00");
		final String signKeyMod = messageResource.getMessage("chinapay.sign.private.mod", null, Locale.CHINA);
		final String signKeyExp = messageResource.getMessage("chinapay.sign.private.exp", null, Locale.CHINA);
		final String verifyKeyMod = messageResource.getMessage("chinapay.verify.private.mod", null, Locale.CHINA);
		final String verifyKeyExp = messageResource.getMessage("chinapay.verify.private.exp", null, Locale.CHINA);
		final String creatOrderUrl = messageResource.getMessage("chinapay.order.url", null, Locale.CHINA);
		final String merId = messageResource.getMessage("chinapay.merid", null, Locale.CHINA);
		final String merNoticeUrl = messageResource.getMessage("chinapay.notice.url", null, Locale.CHINA);
		final String merTermId = messageResource.getMessage("chinapay.merterm.id", null, Locale.CHINA);

		BigDecimal total_fee = new BigDecimal(order.getOrderMoney()).setScale(2);
		Date orderDate = order.getOrderDate();
		String payNo = order.getPayNo();

		// 向银商进行下单
		final DefaultSecurityService ss = new DefaultSecurityService(); // 设置签名的商户私钥，验签的银商公钥
		ss.setSignKeyModHex(signKeyMod);// 签名私钥Mod
		ss.setSignKeyExpHex(signKeyExp);// 签名私钥Exp
		ss.setVerifyKeyExpHex(verifyKeyExp);
		ss.setVerifyKeyModHex(verifyKeyMod);
		final UMSPayServiceImpl service = new UMSPayServiceImpl();
		service.setSecurityService(ss);
		service.setOrderServiceURL(creatOrderUrl);
		// 构建订单
		final String curreTime = DateUtil.formatDate(orderDate, "yyyyMMddHHmmss");
		final OrderEntity oe = new OrderEntity();
		oe.setMerId(merId);// 商户号
		oe.setMerTermId(merTermId);// 终端号
		oe.setMerOrderId(payNo.toString());// 订单号，商户根据自己的规则生成，最长32位
		oe.setOrderDate(curreTime.substring(0, 8));// 订单日期
		oe.setOrderTime(curreTime.substring(8));// 订单时间
		oe.setTransAmt(String.valueOf(new Integer(df.format(total_fee).replace(".", ""))));// 订单金额(单位分)

		oe.setOrderDesc(subject);// 订单描述

		// if (order instanceof ProductOrder) {
		// final ProductOrder po = (ProductOrder) order;
		// oe.setOrderDesc(po.getProduct().getName() + "[product]");// 订单描述
		// oe.setReserve("product");
		// } else {
		// final ActiveOrder ao = (ActiveOrder) order;
		// oe.setOrderDesc(ao.getActive().getName() + "挑战惩罚金[active]");
		// oe.setReserve("active");
		// }

		oe.setNotifyUrl(merNoticeUrl);// 通知商户地址，保证外网能够访问
		oe.setTransType("NoticePay");// 固定值
		oe.setEffectiveTime("0");// 订单有效期期限（秒），值小于等于0表示订单长期有效
		// log.error("下单请求报文：" + action.getJson(oe));
		log.error("下单请求报文1：" + oe.convertToJsonString());
		final String signStr = oe.buildSignString();

		oe.setMerSign(ss.sign(signStr));
		// 发送创建订单请求,该方法中已经封装了签名验签的操作，我们不需要关心，只需要设置好公私钥即可
		final OrderEntity respOrder = service.createOrder(oe);
		// log.error("下单响应报文：" + action.getJson(respOrder));
		log.error("下单响应报文1：" + respOrder.convertToJsonString());
		final StringBuffer sb1 = new StringBuffer();
		sb1.append(ss.sign(respOrder.getTransId() + respOrder.getChrCode()) + "|" + respOrder.getChrCode() + "|" + respOrder.getTransId() + "|" + merId);
		return sb1.toString();
	}
}
