package sport.util;

import java.io.IOException;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;
import java.util.UUID;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.httpclient.HttpClient;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.jdom.input.SAXBuilder;
import org.xml.sax.InputSource;

import common.util.HttpRequestUtils;
import net.sf.json.JSONObject;

/**
 * 微信公众号 SDK
 * 
 * @author hw
 */
@SuppressWarnings("deprecation")
public class SDK_WX {
	/**
	 * 公众账号ID
	 */
	private final String APPID = "wx9f313f1f49972b40";
	/**
	 * 开发者密码
	 */
	private final String APPSERCRET = "bfcbe09c405bf6711c3a468ced027cb2";
	/**
	 * 微信支付分配的商户号
	 */
	private final String MCH_ID = "1489441122";

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
	
	/**
	 * 载入access_token,jsapi_ticket初始值
	 * 
	 * @param request
	 */
	public SDK_WX(HttpServletRequest request) {
		if (request != null) {
			ServletContext application = request.getServletContext();
			long token_time = 0;
			if (application.getAttribute("token_time_sport") != null) {
				token_time = Long.parseLong(application.getAttribute("token_time_sport").toString());
			}
			if (application.getAttribute("ACCESS_TOKEN_sport") != null) {
				this.ACCESS_TOKEN = (String) application.getAttribute("ACCESS_TOKEN_sport");
			}
			if (application.getAttribute("JSAPI_TICKET_sport") != null) {
				this.JSAPI_TICKET = (String) application.getAttribute("JSAPI_TICKET_sport");
			}
			// 如果application中没有jsapi_ticket,就重新获取access_token,jsapi_ticket并存入application中
			if (this.JSAPI_TICKET == null) {
				String GET_ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="
						+ APPID + "&secret=" + APPSERCRET;
				JSONObject json = HttpRequestUtils.httpGet(GET_ACCESS_TOKEN_URL);
				String access_token = json.getString("access_token");
				this.ACCESS_TOKEN = access_token;
				String GET_JSAPI_TICKET_Url = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token="
						+ access_token + "&type=jsapi";
				json = HttpRequestUtils.httpGet(GET_JSAPI_TICKET_Url);
				this.JSAPI_TICKET = json.getString("ticket");
				token_time = System.currentTimeMillis() + (3600 * 1000);
				application.setAttribute("ACCESS_TOKEN_sport", this.ACCESS_TOKEN);
				application.setAttribute("JSAPI_TICKET_sport", this.JSAPI_TICKET);
				application.setAttribute("token_time_sport", token_time);
			}
			
			// 如果access_token过期了就重新获取
			if (token_time < System.currentTimeMillis()) {
				String GET_ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="
						+ APPID + "&secret=" + APPSERCRET;
				JSONObject json = HttpRequestUtils.httpGet(GET_ACCESS_TOKEN_URL);
				String access_token = json.getString("access_token");
				this.ACCESS_TOKEN = access_token;
				String GET_JSAPI_TICKET_Url = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token="
						+ access_token + "&type=jsapi";
				json = HttpRequestUtils.httpGet(GET_JSAPI_TICKET_Url);
				this.JSAPI_TICKET = json.getString("ticket");
				token_time = System.currentTimeMillis() + (3600 * 1000);
				application.setAttribute("ACCESS_TOKEN_sport", this.ACCESS_TOKEN);
				application.setAttribute("JSAPI_TICKET_sport", this.JSAPI_TICKET);
				application.setAttribute("token_time_sport", token_time);
			}
			ip = request.getRemoteAddr();
		}
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
		result.accumulate("appid", APPID);
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
	public String paySign(String openid, String productPrice, String productDetail) throws Exception {
		// 获取发送给微信的参数
		// 随机字符串
		String nonce_str = create_nonce_str();
		// 微信签名
		String sign = "";
		// 商品描述
		String body = productDetail == null ? "default" : productDetail;
		// 商品订单号
		String out_trade_no = getProductNo();
		// 商品价格(单位:分)
		String total_fee = productPrice;
		// 交易类型
		String trade_type = "JSAPI";
		// key为商户平台设置的密钥key
		String key = "fd5sdSdsSDLsd56sdfSKDUWks21ssEYR";
		// 微信回调通知地址
		String notify_url = "http://www.ecartoon.com.cn/swechatwx!weixinNotify.asp";
		// 拼接参数
		// "appid=wxd930ea5d5a258f4f&body=test&device_info=1000&mch_id=10000100&nonce_str=ibuaiVcKdpRxkhJA";
		StringBuilder stringA = new StringBuilder();
		stringA.append("appid=").append(APPID);
		stringA.append("&body=").append(body);
		stringA.append("&mch_id=").append(MCH_ID);
		stringA.append("&nonce_str=").append(nonce_str);
		stringA.append("&out_trade_no=").append(out_trade_no);
		// 调用加密方法生成签名
		// 注：key为商户平台设置的密钥key
		String stringSignTemp = stringA.toString() + "&key=" + key;
		// 注：MD5签名方式
		sign = MD5(stringSignTemp).toUpperCase();
		// 生成xml发送消息
		StringBuilder xml = new StringBuilder("<xml>");
		xml.append("<appid>").append(APPID).append("</xml>");
		xml.append("<mch_id>").append(MCH_ID).append("</mch_id>");
		xml.append("<sign>").append(sign).append("</sign>");
		xml.append("<body><![CDATA[").append(body).append("]]></body>");
		xml.append("<out_trade_no>").append(out_trade_no).append("</out_trade_no>");
		xml.append("<total_fee>").append(total_fee).append("</total_fee>");
		xml.append("<spbill_create_ip>").append(ip).append("</spbill_create_ip>");
		xml.append("<notify_url>").append(notify_url).append("</notify_url>");
		xml.append("<trade_type>").append(trade_type).append("</trade_type>");
		xml.append("<openid>").append(openid).append("</openid>");
		// 调用微信统一支付接口
		String createOrderURL = "https://api.mch.weixin.qq.com/pay/unifiedorder";
		String prepay_id = getPayNo(createOrderURL, xml.toString());
		JSONObject res = new JSONObject();
		res.accumulate("appId", APPID);
		res.accumulate("timeStamp", create_timestamp());
		res.accumulate("nonceStr ", nonce_str);
		res.accumulate("packageValue", "prepay_id=" + prepay_id);
		res.accumulate("signType", "MD5");
		res.accumulate("paySign", sign);
		res.accumulate("success", true);
		return res.toString();
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
	@SuppressWarnings({ "unused", "resource" })
	private static String httpReques(String url, String param) {
		// post请求返回结果
		HttpClient http = new HttpClient();
		DefaultHttpClient httpClient = new DefaultHttpClient();
		JSONObject jsonResult = null;
		HttpPost method = new HttpPost(url);
		try {
			HttpResponse result = httpClient.execute(method);
			url = URLDecoder.decode(url, "UTF-8");
			/** 请求发送成功，并得到响应 **/
			if (result.getStatusLine().getStatusCode() == 200) {
				String str = "";
				try {
					/** 读取服务器返回过来的json字符串数据 **/
					str = EntityUtils.toString(result.getEntity());
					/** 把json字符串转换成json对象 **/
					return str;
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
			StringReader read = new StringReader(xml);
			// 创建新的输入源SAX 解析器将使用 InputSource 对象来确定如何读取 XML 输入
			InputSource source = new InputSource(read);
			// 创建一个新的SAXBuilder
			SAXBuilder sb = new SAXBuilder();
			// 通过输入源构造一个Document
			Document doc = (Document) sb.build(source);
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

	/**
	 * 加密
	 * 
	 * @param hash
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
	 * 生成商品订单号
	 */
	private static String getProductNo() {
		String timestamp = Long.toHexString(Long.valueOf(create_timestamp()));
		return timestamp + UUID.randomUUID().toString().substring(0, 15).replace("-", "");
	}

	/**
	 * 获取唯一字符串
	 * 
	 * @return
	 */
	private static String create_nonce_str() {
		return UUID.randomUUID().toString();
	}

	/**
	 * 获取时间戳
	 * 
	 * @return
	 */
	private static String create_timestamp() {
		return Long.toString(System.currentTimeMillis() / 1000);
	}

	/**
	 * getter
	 */
	public String getAPPID() {
		return this.APPID;
	}

	public String getAPPSERCRET() {
		return this.APPSERCRET;
	}

	public String getACCESS_TOKEN() {
		return ACCESS_TOKEN;
	}

	public String getJSAPI_TICKET() {
		return JSAPI_TICKET;
	}
}
