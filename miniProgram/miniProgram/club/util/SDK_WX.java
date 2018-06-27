package miniProgram.club.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.freegym.web.order.Order;
import com.freegym.web.utils.EasyUtils;

import net.sf.json.JSONObject;
import common.util.HttpRequestUtils;
import miniProgram.club.constant.Constants;

/**
 * 微信公众号 SDK
 * 
 * @author hw
 */
public class SDK_WX {
	/**
	 * access_token
	 */
	private String ACCESS_TOKEN;
	
	/**
	 * jsapi_ticket
	 */
	private String JSAPI_TICKET;
	
	/**
	 * 客户端ip
	 */
	private String ip;

	public SDK_WX() {
	}

	/**
	 * H5支付 , 获取ip
	 */
	public SDK_WX(HttpServletRequest request, String H5) {
		ip = EasyUtils.getIpAddr(request);
	}

	/**
	 * 载入access_token,jsapi_ticket初始值
	 * 
	 * @param request
	 */
	public SDK_WX(HttpServletRequest request) {
		if (request != null) {
			ServletContext application = request.getServletContext();
			long token_time = 0;
			if (application.getAttribute("token_time_ecartoon") != null) {
				token_time = Long.parseLong(application.getAttribute("token_time_ecartoon").toString());
			}
			if (application.getAttribute("ACCESS_TOKEN_ecartoon") != null) {
				this.ACCESS_TOKEN = (String) application.getAttribute("ACCESS_TOKEN_ecartoon");
			}
			if (application.getAttribute("JSAPI_TICKET_ecartoon") != null) {
				this.JSAPI_TICKET = (String) application.getAttribute("JSAPI_TICKET_ecartoon");
			}
			// 如果application中没有jsapi_ticket,就重新获取access_token,jsapi_ticket并存入application中
			if (this.JSAPI_TICKET == null) {
				String GET_ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="
						+ Constants.APPID + "&secret=" + Constants.APP_SECRET;
				JSONObject json = HttpRequestUtils.httpGet(GET_ACCESS_TOKEN_URL);
				String access_token = json.getString("access_token");
				this.ACCESS_TOKEN = access_token;
				String GET_JSAPI_TICKET_Url = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token="
						+ access_token + "&type=jsapi";
				json = HttpRequestUtils.httpGet(GET_JSAPI_TICKET_Url);
				this.JSAPI_TICKET = json.getString("ticket");
				token_time = System.currentTimeMillis() + (3600 * 1000);
				application.setAttribute("ACCESS_TOKEN_ecartoon", this.ACCESS_TOKEN);
				application.setAttribute("JSAPI_TICKET_ecartoon", this.JSAPI_TICKET);
				application.setAttribute("token_time_ecartoon", token_time);
			}
			// 如果access_token过期了就重新获取
			if (token_time < System.currentTimeMillis()) {
				String GET_ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="
						+ Constants.APPID + "&secret=" + Constants.APP_SECRET;
				JSONObject json = HttpRequestUtils.httpGet(GET_ACCESS_TOKEN_URL);
				String access_token = json.getString("access_token");
				this.ACCESS_TOKEN = access_token;
				String GET_JSAPI_TICKET_Url = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token="
						+ access_token + "&type=jsapi";
				json = HttpRequestUtils.httpGet(GET_JSAPI_TICKET_Url);
				this.JSAPI_TICKET = json.getString("ticket");
				token_time = System.currentTimeMillis() + (3600 * 1000);
				application.setAttribute("ACCESS_TOKEN_ecartoon", this.ACCESS_TOKEN);
				application.setAttribute("JSAPI_TICKET_ecartoon", this.JSAPI_TICKET);
				application.setAttribute("token_time_ecartoon", token_time);
			}
		}
		ip = EasyUtils.getIpAddr(request);
	}

	/**
	 * 微信公众号js接口调用签名
	 * 
	 * @param url
	 * @return
	 */
	public String sign(String url) {
		JSONObject result = new JSONObject();
		// 这里的jsapi_ticket是获取的jsapi_ticket。
		String jsapi_ticket = JSAPI_TICKET;
		String nonce_str = create_nonce_str();
		String timestamp = create_timestamp();
		String code = null;
		String signature = null;
		// 注意这里参数名必须全部小写，且必须有序
		code = "jsapi_ticket=" + jsapi_ticket + "&noncestr=" + nonce_str + "&timestamp=" + timestamp + "&url=" + url;
		// System.out.println(code);
		try {
			MessageDigest crypt = MessageDigest.getInstance("SHA-1");
			crypt.reset();
			crypt.update(code.getBytes("UTF-8"));
			signature = byteToHex(crypt.digest());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		result.accumulate("appid", Constants.APPID);
		result.accumulate("timestamp", timestamp);
		result.accumulate("nonceStr", nonce_str);
		result.accumulate("signature", signature);
		return result.toString();
	}

	/**
	 * 微信公众号支付签名
	 * 
	 * @param openid
	 *            String 微信公众号用户id
	 * @param productPrice
	 *            String 商品价格
	 * @param productDetail
	 *            String 商品描述
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String paySign(String openid, Order order) throws Exception {
		// 获取发送给微信的参数
		// 随机字符串
		String nonce_str = create_nonce_str();
		// 微信签名
		String sign = "";
		// 商品描述
		String body = "ecartoon";
		// 商品订单号
//		String out_trade_no = order.getPayNo();
		String out_trade_no = order.getNo();
		// 商品价格(单位:分)
		String total_fee = String.valueOf((new Double(order.getOrderMoney() * 100)).intValue());
		// 交易类型
		String trade_type = "JSAPI";
		// 微信回调通知地址
		String notify_url = "http://www.ecartoon.com.cn/mwpaynotifyc.asp";
		// 终端设备类型
		String deviceInfo = "WEB";
		
		//微信支付分配的商户号
		String MCH_ID = "1492695742";

		//key为商户平台设置的密钥key
		String KEY = "5FED11C18CDA9E50D24EBA10677E9E10";

		// 参数
		TreeMap paramMap = new TreeMap();
		paramMap.put("appid", Constants.APPID);// 公众账号ID
		paramMap.put("device_info", deviceInfo);// 支付终端型号
		paramMap.put("mch_id", MCH_ID);// 商户号
		paramMap.put("nonce_str", nonce_str);// 随机字符串
		paramMap.put("body", body);// 商品描述
		paramMap.put("out_trade_no", out_trade_no);// 商户订单号
		// 交易金额默认为人民币交易，接口中参数支付金额单位为【分】，参数值不能带小数。
		paramMap.put("total_fee", total_fee);// 标价金额
		paramMap.put("spbill_create_ip", ip);// 终端IP
		paramMap.put("notify_url", notify_url);// 通知地址
		paramMap.put("trade_type", trade_type);// 交易类型
		paramMap.put("openid", openid);// 用户标识

		// 调用加密方法生成签名
		// 注：MD5签名方式
		sign = createSign(paramMap, KEY);
		// 生成xml发送消息
		StringBuilder xml = new StringBuilder("<xml>");
		xml.append("<appid>").append(Constants.APPID).append("</appid>");
		xml.append("<device_info>").append(deviceInfo).append("</device_info>");
		xml.append("<mch_id>").append(MCH_ID).append("</mch_id>");
		xml.append("<nonce_str>").append(nonce_str).append("</nonce_str>");
		xml.append("<sign>").append(sign).append("</sign>");
		xml.append("<body><![CDATA[").append(body).append("]]></body>");
		xml.append("<out_trade_no>").append(out_trade_no).append("</out_trade_no>");
		xml.append("<total_fee>").append(total_fee).append("</total_fee>");
		xml.append("<spbill_create_ip>").append(ip).append("</spbill_create_ip>");
		xml.append("<notify_url>").append(notify_url).append("</notify_url>");
		xml.append("<trade_type>").append(trade_type).append("</trade_type>");
		xml.append("<openid>").append(openid).append("</openid>");
		xml.append("</xml>");
		// 调用微信统一支付接口
		String createOrderURL = "https://api.mch.weixin.qq.com/pay/unifiedorder";
		// GetWxOrderno getno = new GetWxOrderno();
		String prepay_id = getPayNo(createOrderURL, xml.toString());
		String timeStamp = create_timestamp();
		String packageValue = "prepay_id=" + prepay_id;
		TreeMap paySignMap = new TreeMap();
		paySignMap.put("appId", Constants.APPID);
		paySignMap.put("timeStamp", timeStamp);
		paySignMap.put("nonceStr", nonce_str);
		paySignMap.put("package", packageValue);
		paySignMap.put("signType", "MD5");
		// 參數返回給前台
		JSONObject res = new JSONObject();
		res.accumulate("appId", Constants.APPID);
		res.accumulate("timeStamp", timeStamp);
		res.accumulate("nonceStr", nonce_str);
		res.accumulate("packageValue", packageValue);
		res.accumulate("signType", "MD5");
		res.accumulate("paySign", createSign(paySignMap, KEY));
		res.accumulate("success", true);
		return res.toString();
	}

	/**
	 * 生成签名
	 * 
	 * @param parameters
	 * @param key
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public static String createSign(SortedMap<String, Object> parameters, String key) throws Exception {
		StringBuffer sb = new StringBuffer();
		Set es = parameters.entrySet();
		Iterator it = es.iterator();
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
			String k = (String) entry.getKey();
			Object v = entry.getValue();
			if (null != v && !"".equals(v) && !"sign".equals(k) && !"key".equals(k)) {
				sb.append(k + "=" + v + "&");
			}
		}
		// 注：key为商户平台设置的密钥key
		sb.append("key=" + key);
		System.out.println("签名参数：" + sb.toString());
		String xx = sb.toString();
		String sign = MD5(xx).toUpperCase();
		System.out.println("签名: " + sign);
		return sign;
	}

	/**
	 * 生成签名
	 * 
	 * @param parameters
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static String createSign(SortedMap<String, Object> parameters) {
		StringBuffer sb = new StringBuffer();
		Set es = parameters.entrySet();
		Iterator it = es.iterator();
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
			String k = (String) entry.getKey();
			Object v = entry.getValue();
			if (null != v && !"".equals(v) && !"sign".equals(k) && !"key".equals(k)) {
				sb.append(k + "=" + v + "&");
			}
		}
		// 注：key为商户平台设置的密钥key
		sb.delete(sb.length() - 1, sb.length());
		System.out.println("签名：" + sb.toString());
		String sign = MD5(sb.toString()).toUpperCase();
		return sign;
	}

	/**
	 * MD5
	 * 
	 * @param buffer
	 * @return
	 */
	public final static String MD5(String buffer) {
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
		try {
			MessageDigest mdTemp = MessageDigest.getInstance("MD5");
			mdTemp.update(buffer.getBytes("UTF-8"));
			byte[] md = mdTemp.digest();
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(str);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * http请求
	 * 
	 * @param url
	 * @param param
	 * @return
	 */
	@SuppressWarnings("deprecation")
	private static String httpReques(String url, String param) {
		// 创建httpclient工具对象
		HttpClient client = new HttpClient();
		// 创建post请求方法
		PostMethod myPost = new PostMethod(url);
		try {
			// 设置post请求参数
			// 设置请求头部类型
			myPost.setRequestHeader("Content-Type", "text/xml");
			myPost.setRequestHeader("charset", "utf-8");
			// 设置请求体，即xml文本内容，一种是直接获取xml内容字符串，一种是读取xml文件以流的形式
			myPost.setRequestBody(param);
			int statusCode = client.executeMethod(myPost);
			// url = URLDecoder.decode(url, "UTF-8");
			// 请求发送成功，并得到响应
			if (statusCode == 200) {
				String str = "";
				try {
					// 读取服务器返回过来的json字符串数据
					// str = EntityUtils.toString(result.getEntity());
					InputStream inputStream = myPost.getResponseBodyAsStream();
					BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, "utf-8"));
					StringBuffer stringBuffer = new StringBuffer();
					while ((str = br.readLine()) != null) {
						stringBuffer.append(str);
					}
					return stringBuffer.toString();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取支付id
	 * 
	 * @param url
	 * @param param
	 * @return
	 */
	private static String getPayNo(String url, String param) {
		try {
			String xml = httpReques(url, param);
			Document doc = DocumentHelper.parseText(xml);
			// 指向根节点
			Element root = doc.getRootElement();
			// 获得perpay_id节点
			Element element = root.element("prepay_id");
			return element.getText();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/*
	 * 加密
	 * 
	 * @param hash
	 * 
	 * @return
	 */
	private static String byteToHex(final byte[] hash) {
		Formatter formatter = new Formatter();
		for (byte b : hash) {
			formatter.format("%02x", b);
		}
		String result = formatter.toString();
		formatter.close();
		return result;
	}

	/**
	 * 获取唯一字符串
	 * 
	 * @return
	 */
	private static String create_nonce_str() {
		return (int) Math.floor(Math.random() * 1000000) + "";
	}

	/**
	 * 获取时间戳
	 * 
	 * @return
	 */
	private static String create_timestamp() {
		return Long.toString(System.currentTimeMillis() / 1000);
	}
}
