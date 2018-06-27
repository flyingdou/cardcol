package com.freegym.web.order.action;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.freegym.web.OrderBasicAction;

@Namespace("")
@InterceptorRefs({ @InterceptorRef("foreStack") })
@Results({ @Result(name = "alipay", type = "chain", location = "alipayto"), @Result(name = "chinaPay", type = "chain", location = "chinapayto") })
public class OrderPayManageAction extends OrderBasicAction {

	private static final long serialVersionUID = -3769475961884283745L;
	private String payNo, totalMoney, payType;// 支付方式1：支付宝2:银行支付
	// 支付宝参数Start
	private String out_trade_no;// 订单号
	private String subject;// 订单名称
	private String total_fee;// 订单金额
	private String body;// 订单描述
	private String param; // 订单类型
	// 支付宝参数End
	// 银行支付参数Start
	private String OrdId; // 商户提交给ChinaPay的交易订单号，订单号的第五至第九位必须是商户号的最后五位，即“12345”；16位长度，必填
	private String TransAmt; // 订单交易金额，12位长度，左补0，必填,单位为分，000000001234// 表示
								// 12.34元
	private String TransDate; // 订单交易日期，8位长度，必填，格式yyyyMMdd
	private String Priv1; // 商户私有域，长度不要超过60个字节,商户通过此字段向Chinapay发送的信息，Chinapay依原样填充返回给商户

	// 银行支付参数End

	/**
	 * 选择支付方式
	 * 
	 * @return
	 */
	public String toPay() {
		final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		final Date orderDate = new Date();
		totalMoney = new DecimalFormat("0.00").format(new BigDecimal(totalMoney));
		if (payType != null) {
			service.updateOrderPayType(payNo, payType);
			if ("1".equals(payType)) {
				setSubject(subject);
				setBody("卡库网商品：" + subject);
				setTotal_fee(totalMoney);// 0.01
				setParam("product");
				this.setOut_trade_no(payNo);// 8820141200219
				return "alipay";
				// 银行
			} else if ("2".equals(payType)) {
				// 从页面传过来的payNo为28位，银联只需要前16位
				payNo = payNo.substring(0, 16);
				this.setOrdId(payNo);// 8814003211200217
				String transAmt = totalMoney.replace(".", "");
				while (transAmt.length() < 12)
					transAmt = "0" + transAmt;
				this.setTransAmt(transAmt);// 000000000001
				this.setTransDate(sdf.format(orderDate));
				this.setPriv1("product");
				return "chinaPay";
			}
		}
		return null;
	}

	public String getPayNo() {
		return payNo;
	}

	public void setPayNo(String payNo) {
		this.payNo = payNo;
	}

	public String getTotalMoney() {
		return totalMoney;
	}

	public void setTotalMoney(String totalMoney) {
		this.totalMoney = totalMoney;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

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

	public String getTotal_fee() {
		return total_fee;
	}

	public void setTotal_fee(String total_fee) {
		this.total_fee = total_fee;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getParam() {
		return param;
	}

	public void setParam(String param) {
		this.param = param;
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

	public String getTransDate() {
		return TransDate;
	}

	public void setTransDate(String transDate) {
		TransDate = transDate;
	}

	public String getPriv1() {
		return Priv1;
	}

	public void setPriv1(String priv1) {
		Priv1 = priv1;
	}

}
