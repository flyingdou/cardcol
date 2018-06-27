package com.freegym.web.chinapay.action;

import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.freegym.web.OrderBasicAction;
import com.freegym.web.chinapay.config.ChinaPayConfig;

@Namespace("")
@InterceptorRefs({ @InterceptorRef("customStack") })
@Results({ @Result(name = "order_list", location = "/order/order_list.jsp"), @Result(name = "success10", location = "/order/order10.jsp") })
public class ChinaPayToManageAction extends OrderBasicAction {

	private static final long serialVersionUID = -3769475961884283745L;
	private String payUrl = ChinaPayConfig.payUrl; // 测试地址，测试的时候用这个地址，应用到网站时用下面那个地址
	private String MerId = ChinaPayConfig.MerId; // ChinaPay统一分配给商户的商户号，15位长度，必填
	private String OrdId; // 商户提交给ChinaPay的交易订单号，订单号的第五至第九位必须是商户号的最后五位，即“12345”；16位长度，必填
	private String TransAmt; // 订单交易金额，12位长度，左补0，必填,单位为分，000000001234// 表示 12.34
								// 元
	private String CuryId = ChinaPayConfig.CuryId; // 订单交易币种，3位长度，固定为人民币156，必填
	private String TransDate; // 订单交易日期，8位长度，必填，格式yyyyMMdd
	private String TransType = ChinaPayConfig.TransTypePay; // 交易类型，4位长度，必填，0001表示消费交易，0002表示退货交易
	private String Version = ChinaPayConfig.Version; // 支付接入版本号，808080开头的商户用此版本，必填,另一版本为"20070129"
	private String BgRetUrl = ChinaPayConfig.BgRetUrl; // 后台交易接收URL，为后台接受应答地址，用于商户记录交易信息和处理，对于使用者是不可见的，长度不要超过80个字节，必填
	private String PageRetUrl = ChinaPayConfig.PageRetUrl; // 页面交易接收URL，为页面接受应答地址，用于引导使用者返回支付后的商户网站页面，长度不要超过80个字节，必填
	private String GateId = ChinaPayConfig.GateId; // 支付网关号，可选，参看银联网关类型，如填写GateId（支付网关号），则消费者将直接进入支付页面，否则进入网关选择页面
	private String Priv1; // 商户私有域，长度不要超过60个字节,商户通过此字段向Chinapay发送的信息，Chinapay依原样填充返回给商户
	private String ChkValue; // 256字节长的ASCII码,为此次交易提交关键数据的数字签名，必填

	public String getPayUrl() {
		return payUrl;
	}

	public void setPayUrl(String payUrl) {
		this.payUrl = payUrl;
	}

	public String getMerId() {
		return MerId;
	}

	public void setMerId(String merId) {
		MerId = merId;
	}

	public String getOrdId() {
		return OrdId;
	}

	public void setOrdId(String ordId) {
		OrdId = ordId;
	}

	public String getTransAmt() {
		return TransAmt;
	}

	public void setTransAmt(String transAmt) {
		TransAmt = transAmt;
	}

	public String getCuryId() {
		return CuryId;
	}

	public void setCuryId(String curyId) {
		CuryId = curyId;
	}

	public String getTransDate() {
		return TransDate;
	}

	public void setTransDate(String transDate) {
		TransDate = transDate;
	}

	public String getTransType() {
		return TransType;
	}

	public void setTransType(String transType) {
		TransType = transType;
	}

	public String getVersion() {
		return Version;
	}

	public void setVersion(String version) {
		Version = version;
	}

	public String getBgRetUrl() {
		return BgRetUrl;
	}

	public void setBgRetUrl(String bgRetUrl) {
		BgRetUrl = bgRetUrl;
	}

	public String getPageRetUrl() {
		return PageRetUrl;
	}

	public void setPageRetUrl(String pageRetUrl) {
		PageRetUrl = pageRetUrl;
	}

	public String getGateId() {
		return GateId;
	}

	public void setGateId(String gateId) {
		GateId = gateId;
	}

	public String getPriv1() {
		return Priv1;
	}

	public void setPriv1(String priv1) {
		Priv1 = priv1;
	}

	public String getChkValue() {
		return ChkValue;
	}

	public void setChkValue(String chkValue) {
		ChkValue = chkValue;
	}

	public String execute() {
		chinapay.PrivateKey key = new chinapay.PrivateKey();
		boolean flag;
		String tomcatRoot = request.getSession().getServletContext().getRealPath("/");
		flag = key.buildKey(MerId, 0, tomcatRoot + ChinaPayConfig.MerPrkPath);
		if (flag == false) {
			System.out.println("build key error!");
			return null;
		}
		chinapay.SecureLink t = new chinapay.SecureLink(key);
		ChkValue = t.signOrder(MerId, OrdId, TransAmt, CuryId, TransDate, TransType);
		StringBuffer sbHtml = new StringBuffer();
		sbHtml.append("<form name='chinapayForm' method='post' action='" + payUrl + "'>"); // 支付地址
		sbHtml.append("<input type='hidden' name='MerId' value='" + MerId + "' />"); // 商户号
		sbHtml.append("<input type='hidden' name='OrdId' value='" + OrdId + "' />"); // 订单号
		sbHtml.append("<input type='hidden' name='TransAmt' value='" + TransAmt + "' />"); // 支付金额
		sbHtml.append("<input type='hidden' name='CuryId' value='" + CuryId + "' />"); // 交易币种
		sbHtml.append("<input type='hidden' name='TransDate' value='" + TransDate + "' />"); // 交易日期
		sbHtml.append("<input type='hidden' name='TransType' value='" + TransType + "' />"); // 交易类型
		sbHtml.append("<input type='hidden' name='Version' value='" + Version + "' />"); // 支付接入版本号
		sbHtml.append("<input type='hidden' name='BgRetUrl' value='" + BgRetUrl + "' />"); // 后台接受应答地址
		sbHtml.append("<input type='hidden' name='PageRetUrl' value='" + PageRetUrl + "' />"); // 为页面接受应答地址
		sbHtml.append("<input type='hidden' name='GateId' value='" + GateId + "' />"); // 支付网关号
		sbHtml.append("<input type='hidden' name='Priv1' value='" + Priv1 + "' />"); // 商户私有域，这里将订单自增编号放进去了
		sbHtml.append("<input type='hidden' name='ChkValue' value='" + ChkValue + "' />"); // 此次交易所提交的关键数据的数字签名
		sbHtml.append("<script>");
		sbHtml.append("document.chinapayForm.submit();");
		sbHtml.append("</script></form>");
		// 构造函数，生成请求URL
		String sHtmlText = sbHtml.toString();
		response(sHtmlText);
		return null;
	}
}
