package sport.action;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.dom4j.Document;
import org.dom4j.Element;
import org.jdom.input.SAXBuilder;
import org.xml.sax.InputSource;

import com.databaseConnection.DataBaseConnection;
import com.freegym.web.mobile.BasicJsonAction;

import common.util.HttpRequestUtils;
import sport.util.SDK_WX;
import sport.util.loginCommons;

/**
 * 思博特微信公众号相关功能
 * 
 * @author hw
 *
 */
@Namespace("")
@InterceptorRefs({ @InterceptorRef("customStack") })
@Results({ @Result(name = "success", location = "sport/success.jsp"),
		@Result(name = "fail", location = "sport/fail.jsp") })
public class SWechatWXManageAction extends BasicJsonAction {

	private static final long serialVersionUID = 1L;

	public static final String TOKEN = "yfkj_xfcamp_token";
	private String signature;
	private String timestamp;
	private String nonce;
	private String echostr;

	/**
	 * 获取微信sdk信息
	 */
	public void sign() {
		SDK_WX wx = new SDK_WX(request);
		String url = request.getParameter("url");
		String result = wx.sign(url);
		response(result);
	}

	/**
	 * 微信公众号支付签名
	 */
	public void paySign() {
		try {
			SDK_WX wx = new SDK_WX(request);
			String productPrice = request.getParameter("productPrice");
			String productDetail = request.getParameter("productDetail");
			String openid = (String) session.getAttribute("openid");
			String result = "{'msg':'error : opendid is null'}";
			if (openid != null && !"".equals(openid)) {
				result = wx.paySign(openid, productPrice, productDetail);
			}
			response(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 微信公众号支付回调
	 * 
	 * @throws IOException
	 */
	@SuppressWarnings({ "unused", "unchecked" })
	public String weixinNotify() throws IOException {
		String out_trade_no = null;
		String return_code = null;
		try {
			InputStream inStream = request.getInputStream();
			ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
			byte[] buffer = new byte[1024];
			int len = 0;
			while ((len = inStream.read(buffer)) != -1) {
				outSteam.write(buffer, 0, len);
			}
			outSteam.close();
			inStream.close();
			//获得返回的字符串
			String resultStr = new String(outSteam.toByteArray(), "utf-8");
			//把返回的字符串转换成map集合
			Map<String, Object> resultMap = parseXmlToList(resultStr);
			String result_code = (String) resultMap.get("result_code");
			String is_subscribe = (String) resultMap.get("is_subscribe");
			String transaction_id = (String) resultMap.get("transaction_id");
			String sign = (String) resultMap.get("sign");
			String time_end = (String) resultMap.get("time_end");
			String bank_type = (String) resultMap.get("bank_type");
			out_trade_no = (String) resultMap.get("out_trade_no");
			return_code = (String) resultMap.get("return_code");
			request.setAttribute("out_trade_no", out_trade_no);
			// 通知微信.异步确认成功.必写.不然微信会一直通知后台.八次之后就认为交易失败了.
			response.getWriter().write("<xml><return_code><![CDATA[SUCCESS]]></return_code>"
					+ "<return_msg><![CDATA[OK]]></return_msg></xml>");
		} catch (Exception e) {
			response.getWriter().write("<xml><return_code><![CDATA[FAIL]]></return_code>"
					+ "<return_msg><![CDATA[error]]></return_msg></xml>");
		}
		if ("SUCCESS".equals(return_code)) {
			String updateSql = null;
			// 支付成功修改,订单表中的订单状态
			if (loginCommons.orderType == 1) {
				updateSql = "update tb_product_order set status = 1 where no=" + loginCommons.orderNo;
			} else if (loginCommons.orderType == 2) {
				updateSql = "update tb_product_order_45 set status = 1 where no=" + loginCommons.orderNo;
			}
			DataBaseConnection.updateData(updateSql, null);
			return "success";
		} else {
			// 支付失败的业务逻辑
			return "fail";
		}
	}

	/**
	 * 解析回调
	 * 
	 * @param xml
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static Map parseXmlToList(String xml) {
		Map retMap = new HashMap(5);
		try {
			StringReader read = new StringReader(xml);
			// 创建新的输入源SAX 解析器将使用 InputSource 对象来确定如何读取 XML 输入
			InputSource source = new InputSource(read);
			// 创建一个新的SAXBuilder
			SAXBuilder sb = new SAXBuilder();
			// 通过输入源构造一个Document
			Document doc = (Document) sb.build(source);
			// 指向根节点
			Element root = doc.getRootElement();
			//获得根节点里的所有子节点
			List<Element> es = root.attributes();
			if (es != null && es.size() != 0) {
				for (Element element : es) {
					retMap.put(element.getName(), element.getStringValue());
				}
			}
		} catch (Exception e) {
			//抛出异常
			e.printStackTrace();
		}
		//返回map
		return retMap;
	}

	/**
	 * 获取Token
	 */
	public void getToken() {
		try {
			if (StringUtils.isNotEmpty(echostr)) {
				// 返回给微信,表示收到了微信的通知
				response.getWriter().write(echostr);
			}
			// 接收微信发送的xml消息包
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = (Document) db.parse(request.getInputStream());
			Element root = doc.getRootElement();
			Element node = root.element("FromUserName");
			String openId = node.getText();
			HttpRequestUtils.httpGet("http://sport.ecartoon.com.cn/slogin.asp?openId=" + openId);
		} catch (Exception e) {
			System.out.println("获取opendId未成功");
		}
	}

	/**
	 * 微信Token验证
	 * 
	 * @param signature
	 *            微信加密签名
	 * @param timestamp
	 *            时间戳
	 * @param nonce
	 *            随机数
	 * @param echostr
	 *            随机字符串
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws IOException
	 */

	/**
	 * getter && setter
	 * 
	 * @return
	 */
	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String getNonce() {
		return nonce;
	}

	public void setNonce(String nonce) {
		this.nonce = nonce;
	}

	public String getEchostr() {
		return echostr;
	}

	public void setEchostr(String echostr) {
		this.echostr = echostr;
	}

}
