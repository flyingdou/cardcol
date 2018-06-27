package com.freegym.web.active.action;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.freegym.web.OrderBasicAction;
import com.freegym.web.chinapay.config.ChinaPayConfig;
import com.freegym.web.order.ActiveOrder;
import com.sanmen.web.core.common.LogicException;

@Namespace("")
@InterceptorRefs({ @InterceptorRef("foreStack") })
@Results({ @Result(name = "success", location = "/payment/payment.jsp"), @Result(name = "alipay", type = "chain", location = "alipayto"),
		@Result(name = "chinaPay", type = "chain", location = "chinapayto") })
public class ActivePayManageAction extends OrderBasicAction {

	private static final long serialVersionUID = 8942409430710869379L;

	/**
	 * 支付类型，1支付宝，2银联
	 */
	private Character payType;

	/**
	 * 
	 */
	private ActiveOrder partake;

	// 支付宝参数Start
	private String out_trade_no;// 订单号
	private String subject;// 订单名称
	private String total_fee;// 订单金额
	private String body;// 订单描述
	private String param; // 参数
	// 支付宝参数End

	// 银行支付参数Start
	private String OrdId; // 商户提交给ChinaPay的交易订单号，订单号的第五至第九位必须是商户号的最后五位，即“12345”；16位长度，必填
	private String TransAmt; // 订单交易金额，12位长度，左补0，必填,单位为分，000000001234// 表示 12.34
								// 元
	private String TransDate; // 订单交易日期，8位长度，必填，格式yyyyMMdd
	private String Priv1; // 商户私有域，长度不要超过60个字节,商户通过此字段向Chinapay发送的信息，Chinapay依原样填充返回给商户

	// 银行支付参数End

	public ActiveOrder getPartake() {
		return partake;
	}

	public Character getPayType() {
		return payType;
	}

	public void setPayType(Character payType) {
		this.payType = payType;
	}

	public void setPartake(ActiveOrder partake) {
		this.partake = partake;
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

	public String getParam() {
		return param;
	}

	public void setParam(String param) {
		this.param = param;
	}

	public String execute() {
		try {
			session.setAttribute("spath", 5);
			partake = (ActiveOrder) service.load(ActiveOrder.class, id);
			request.setAttribute("no", partake.getPayNo());
			request.setAttribute("money", partake.getOrderMoney());
			request.setAttribute("name", partake.getActive().getName());
			if (partake.getStatus() != '0')
				throw new LogicException("当前订单已经付款，不得再次进行付款！");
		} catch (Exception e) {
			log.error("error", e);
			setMessage(e.getMessage());
		}
		return SUCCESS;
	}

	/**
	 * 付款
	 */
	public String payment() {
		final ActiveOrder o = (ActiveOrder) service.load(ActiveOrder.class, id);
		final DecimalFormat df = new DecimalFormat("#0.00");
		if (payType == '1') { // 支付宝
			o.setPayType("1");
			service.saveOrUpdate(o);
			setOut_trade_no(o.getNo());
			setSubject("参与挑战惩罚金");
			setBody("卡库网：参与挑战惩罚金");
			setTotal_fee(df.format(o.getOrderMoney()));
			setParam("active");
			return "alipay";
		} else {
			final String payNo = service.getKeyNo("99", "TB_ACTIVE_ORDER", 13);
			o.setPayNo(payNo.substring(0, 2) + payNo.substring(4, 6) + ChinaPayConfig.MerId.substring(ChinaPayConfig.MerId.length() - 5) + payNo.substring(6));
			o.setPayType("2");
			service.saveOrUpdate(o);
			this.setOrdId(o.getPayNo());
			final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			String transAmt = df.format(o.getOrderMoney()).replace(".", "");
			while (transAmt.length() < 12) {
				transAmt = "0" + transAmt;
			}
			this.setTransAmt(transAmt);
			this.setTransDate(sdf.format(o.getOrderDate()));
			this.setPriv1("active");
			return "chinaPay";
		}
	}
}
