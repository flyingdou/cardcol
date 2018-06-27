package com.freegym.web.alipay.action;

import java.util.HashMap;
import java.util.Map;

import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.freegym.web.OrderBasicAction;
import com.freegym.web.alipay.services.AlipayService;

@Namespace("")
@InterceptorRefs({ @InterceptorRef("customStack") })
@Results({ @Result(name = "order_list", location = "/order/order_list.jsp"), @Result(name = "success10", location = "/order/order10.jsp") })
public class AlipayToManageAction extends OrderBasicAction {

	private static final long serialVersionUID = -3769475961884283745L;
	private String out_trade_no;
	private String subject;
	private String body;
	private String total_fee;
	private String param;

	public String getOut_trade_no() {
		return out_trade_no;
	}

	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getTotal_fee() {
		return total_fee;
	}

	public void setTotal_fee(String total_fee) {
		this.total_fee = total_fee;
	}

	public String getParam() {
		return param;
	}

	public void setParam(String param) {
		this.param = param;
	}

	public String execute() {
		// //////////////////////////////////请求参数//////////////////////////////////////

		// 必填参数//

		// 请与贵网站订单系统中的唯一订单号匹配
		/*
		 * String out_trade_no = UtilDate.getOrderNum();
		 * 
		 * //订单名称，显示在支付宝收银台里的“商品名称”里，显示在支付宝的交易管理的“商品名称”的列表里。 String subject =
		 * new
		 * String(request.getParameter("subject").getBytes("ISO-8859-1"),"utf-8"
		 * ); //订单描述、订单详细、订单备注，显示在支付宝收银台里的“商品描述”里 String body = new
		 * String(request
		 * .getParameter("alibody").getBytes("ISO-8859-1"),"utf-8");
		 * //订单总金额，显示在支付宝收银台里的“应付总额”里 String total_fee =
		 * request.getParameter("total_fee");
		 */

		// 扩展功能参数——默认支付方式//

		// 默认支付方式，取值见“即时到帐接口”技术文档中的请求参数列表
		String paymethod = "";
		// 默认网银代号，代号列表见“即时到帐接口”技术文档“附录”→“银行列表”
		String defaultbank = "";

		// 扩展功能参数——防钓鱼//

		// 防钓鱼时间戳
		String anti_phishing_key = "";
		// 获取客户端的IP地址，建议：编写获取客户端IP地址的程序
		String exter_invoke_ip = "";
		// 注意：
		// 1.请慎重选择是否开启防钓鱼功能
		// 2.exter_invoke_ip、anti_phishing_key一旦被设置过，那么它们就会成为必填参数
		// 3.开启防钓鱼功能后，服务器、本机电脑必须支持远程XML解析，请配置好该环境。
		// 4.建议使用POST方式请求数据
		// 示例：
		// anti_phishing_key = AlipayService.query_timestamp(); //获取防钓鱼时间戳函数
		// exter_invoke_ip = "202.1.1.1";

		// 扩展功能参数——其他///

		// 自定义参数，可存放任何内容（除=、&等特殊字符外），不会显示在页面上
		// String extra_common_param = param;// "";
		// 默认买家支付宝账号
		String buyer_email = "";
		// 商品展示地址，要用http:// 格式的完整路径，不允许加?id=123这类自定义参数
		String show_url = "http://www.cardcol.com/order!queryShopping.asp";

		// 扩展功能参数——分润(若要使用，请按照注释要求的格式赋值)//

		// 提成类型，该值为固定值：10，不需要修改
		String royalty_type = "";
		// 提成信息集
		String royalty_parameters = "";
		// 注意：
		// 与需要结合商户网站自身情况动态获取每笔交易的各分润收款账号、各分润金额、各分润说明。最多只能设置10条
		// 各分润金额的总和须小于等于total_fee
		// 提成信息集格式为：收款方Email_1^金额1^备注1|收款方Email_2^金额2^备注2
		// 示例：
		// royalty_type = "10"
		// royalty_parameters = "111@126.com^0.01^分润备注一|222@126.com^0.01^分润备注二"

		// ////////////////////////////////////////////////////////////////////////////////

		// 把请求参数打包成数组
		Map<String, String> sParaTemp = new HashMap<String, String>();
		sParaTemp.put("payment_type", "1");
		sParaTemp.put("out_trade_no", out_trade_no);
		sParaTemp.put("subject", subject);
		sParaTemp.put("body", body);
		sParaTemp.put("total_fee", total_fee);
		sParaTemp.put("show_url", show_url);
		sParaTemp.put("paymethod", paymethod);
		sParaTemp.put("defaultbank", defaultbank);
		sParaTemp.put("anti_phishing_key", anti_phishing_key);
		sParaTemp.put("exter_invoke_ip", exter_invoke_ip);
		sParaTemp.put("extra_common_param", param);
		sParaTemp.put("buyer_email", buyer_email);
		sParaTemp.put("royalty_type", royalty_type);
		sParaTemp.put("royalty_parameters", royalty_parameters);

		// 构造函数，生成请求URL
		String sHtmlText = AlipayService.create_direct_pay_by_user(sParaTemp);
		response(sHtmlText);
		return null;
	}

	public static void main(String[] args) {
		String str = "9WUV3CK80QPTF8L8Q1NSXD2X2ZQRU1RZTK365X1KJJXZ7TZEYG4WZWTZ";
		System.out.println(str.length());
	}
}
