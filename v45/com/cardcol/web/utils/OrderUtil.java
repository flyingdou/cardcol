package com.cardcol.web.utils;

import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alipay.client.Rsa;
import com.alipay.server.AlipayConfig;
import com.freegym.web.chinapay.action.ChinapayUtils;
import com.freegym.web.order.Order;
import com.freegym.web.wechatPay.utils.GetWxOrderno;
import com.freegym.web.wechatPay.utils.RequestHandler;
import com.freegym.web.wechatPay.utils.Sha1Util;
import com.sanmen.web.core.common.LogicException;

import net.sf.json.JSONObject;

public class OrderUtil {

	public static String orderSign(HttpServletRequest request, HttpServletResponse response, Order order, String type, String subject) throws Exception {
		if (type.equals("2")) {
			return PayUtils.alipayOrderSign(order, subject);
		} else if (type.equals("1")) {
			return weixinOrderSign(request, response, order);
		} else if (type.equals("3")) { return chinaPaySign(request, response, order, subject); }
		return null;
	}

	protected static String chinaPaySign(HttpServletRequest request, HttpServletResponse resp, Order order, String subject) throws Exception {
		final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		final ChinapayUtils pay = new ChinapayUtils(order, subject);
		final String sign = pay.order();
		final JSONObject orderJson = new JSONObject();
		orderJson.accumulate("id", order.getId()).accumulate("no", order.getPayNo()).accumulate("orderDate", sdf.format(order.getOrderDate())).accumulate("orderSign", sign);
		final JSONObject obj = new JSONObject();
		obj.accumulate("success", true).accumulate("order", orderJson);
		return obj.toString();
	}

	@SuppressWarnings("deprecation")
	protected static String alipayOrderSign(Order order, String subject) throws Exception {
		StringBuffer sb = new StringBuffer();
		final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		final String body = sdf.format(order.getOrderDate()) + "开始的" + sb.toString();
		final StringBuffer makeParms = new StringBuffer();
		makeParms.append("partner=\"").append(AlipayConfig.partner).append("\"&");
		makeParms.append("seller_id=\"").append(AlipayConfig.seller_email).append("\"&");
		makeParms.append("out_trade_no=\"").append(order.getPayNo()).append("\"&");
		makeParms.append("subject=\"").append(subject).append("\"&");
		makeParms.append("body=\"").append(body).append("\"&");
		makeParms.append("total_fee=\"").append(order.getOrderMoney().toString()).append("\"&");
		makeParms.append("notify_url=\"").append(URLEncoder.encode(AlipayConfig.notify_url)).append("\"&");
		makeParms.append("return_url=\"").append(URLEncoder.encode(AlipayConfig.return_url)).append("\"&");
		makeParms.append("service=\"").append("mobile.securitypay.pay").append("\"&");
		makeParms.append("payment_type=\"").append("1").append("\"&");
		makeParms.append("_input_charset=\"").append("utf-8").append("\"&");
		makeParms.append("it_b_pay=\"30m\"&").append("param=\"product\"");
		final String sign = URLEncoder.encode(Rsa.sign(makeParms.toString(), AlipayConfig.private_key));
		makeParms.append("&sign=\"").append(sign).append("\"&");
		makeParms.append("sign_type=\"").append("RSA").append("\"");
		final JSONObject obj = new JSONObject();
		obj.accumulate("sign", sign).accumulate("info", makeParms.toString());
		return obj.toString();
	}

	@SuppressWarnings({ "static-access", "unused" })
	private static String weixinOrderSign(HttpServletRequest request, HttpServletResponse response, Order order) {
		// 新加入微信支付跳转
		// 网页授权后获取传递的参数
		String money = order.getOrderMoney().toString();
		// 金额转化为分为单位
		float sessionmoney = Float.parseFloat(money);
		String finalmoney = String.format("%.2f", sessionmoney);
		finalmoney = finalmoney.replace(".", "");
		finalmoney = Integer.parseInt(finalmoney) + "";
		// 商户相关资料
		String appid = "wx6815a580a08e95a4";
		String appsecret = "3db088f24464853d422720112358d7b3";
		String partner = "1481442902";
		String partnerkey = "ArdCol1949com1010jsZGNessfit7u8i";// 商户密钥
		
		// 获取openId后调用统一支付接口https://api.mch.weixin.qq.com/pay/unifiedorder
		// 商户号partner;
		// 随机数
		String nonce_str = Sha1Util.getNonceStr();
		String nonce_str1 = nonce_str;
		String timestamp = Sha1Util.getTimeStamp();
		// 商品描述根据情况修改
		String body = "健身E卡通";
		// 商户订单号
		String out_trade_no = order.getNo();
		String spbill_create_ip = "192.168.1.1";
		// 这里notify_url是 支付完成后微信发给该链接信息，可以判断会员是否支付成功，改变订单状态等。
		String notify_url = "http://www.ecartoon.com.cn/mwpaynotify.asp";
		// 2017-7-13  黄文俊  去掉 v3 作测试
		// String notify_url = "http://www.ecartoon.com.cn/mwpaynotify.asp";
		// 内网微信支付
		// String notify_url = "http://156q1j2075.iask.in:88/cardcolv3/mwpaynotify.asp";
		String trade_type = "APP";

		SortedMap<String, String> packageParams = new TreeMap<String, String>();
		packageParams.put("appid", appid);
		packageParams.put("mch_id", partner);
		packageParams.put("nonce_str", nonce_str);
		packageParams.put("body", body);
		packageParams.put("key", partnerkey);
		packageParams.put("out_trade_no", out_trade_no);
		packageParams.put("notify_url", notify_url);
		packageParams.put("spbill_create_ip", spbill_create_ip);
		packageParams.put("trade_type", trade_type);
		// packageParams.put("signType", "MD5");
		// 金额为
		packageParams.put("total_fee", finalmoney);
		RequestHandler reqHandler = new RequestHandler(request, response);
		reqHandler.init(appid, appsecret, partnerkey);
		String sign = reqHandler.createSign(packageParams);
		String xml = "<xml>" + "<sign>" + sign + "</sign>" + "<appid>" + appid + "</appid>" + "<body>" + body + "</body>" + "<mch_id>" + partner + "</mch_id>" + "<nonce_str>"
				+ nonce_str + "</nonce_str>" + "<notify_url>" + notify_url + "</notify_url>" + "<out_trade_no>" + out_trade_no + "</out_trade_no>" + "<spbill_create_ip>"
				+ spbill_create_ip + "</spbill_create_ip>" + "<total_fee>" + finalmoney + "</total_fee>" + "<trade_type>" + trade_type + "</trade_type>" + "</xml>";
		String createOrderURL = "https://api.mch.weixin.qq.com/pay/unifiedorder";
		String prepay_id = "";
		try {
			String result = new GetWxOrderno().getPayNo(createOrderURL, xml);
			if (result.equals("")) { throw new LogicException("统一支付接口获取预支付订单出错"); }
			prepay_id = result.split(",")[0];
			nonce_str = result.split(",")[1];
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		SortedMap<String, String> finalMap = new TreeMap<String, String>();
		finalMap.put("appid", appid);
		finalMap.put("partnerid", partner);
		finalMap.put("prepayid", prepay_id);
		finalMap.put("noncestr", nonce_str);
		finalMap.put("timestamp", timestamp);
		finalMap.put("package", "Sign=WXPay");
		String finalsignTest = reqHandler.createSign(finalMap);
		// finalMap.put("signType", "MD5");
		String finalsign = reqHandler.createSign(finalMap);
		System.out.println("finalsign3:" + finalsign);
		final JSONObject obj = new JSONObject();
		obj.accumulate("appid", appid).accumulate("prepay_id", prepay_id).accumulate("partner", partner).accumulate("timeStamp", timestamp).accumulate("nonceStr", nonce_str)
				.accumulate("nonceStr1", nonce_str1).accumulate("package", "Sign=WXPay").accumulate("sign", finalsign);
		// service.updateOrderStatus(out_trade_no);
		return obj.toString();
	}

}
