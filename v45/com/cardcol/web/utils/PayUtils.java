package com.cardcol.web.utils;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.cardcol.web.alipay.AlipayConfig;
import com.freegym.web.order.Order;

public class PayUtils extends OrderUtil {

	protected static String alipayOrderSign(Order order, String subject) throws Exception {
		AlipayTradeAppPayRequest alipay_request = new AlipayTradeAppPayRequest();
		AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
		String product_code = "QUICK_MSECURITY_PAY";
		model.setOutTradeNo(order.getNo());
		model.setSubject(subject);
		model.setTotalAmount(order.getOrderMoney().toString());
		model.setBody(subject);
		model.setTimeoutExpress("2m");
		model.setProductCode(product_code);
		alipay_request.setBizModel(model);
		// 设置异步通知地址
		alipay_request.setNotifyUrl(AlipayConfig.notify_url);
		// 设置同步地址
		alipay_request.setReturnUrl(AlipayConfig.return_url);

		AlipayClient client = new DefaultAlipayClient(AlipayConfig.URL, AlipayConfig.APPID, AlipayConfig.RSA_PRIVATE_KEY, AlipayConfig.FORMAT, AlipayConfig.CHARSET, AlipayConfig.ALIPAY_PUBLIC_KEY, AlipayConfig.SIGNTYPE);
		return client.sdkExecute(alipay_request).getBody();
	}
}
