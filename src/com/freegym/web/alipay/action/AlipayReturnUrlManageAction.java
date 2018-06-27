package com.freegym.web.alipay.action;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.freegym.web.OrderBasicAction;
import com.freegym.web.alipay.util.AlipayNotify;
import com.freegym.web.order.Order;

@Namespace("")
@InterceptorRefs({ @InterceptorRef("customStack") })
@Results({ @Result(name = "shopping_order", location = "/order/shopping_order.jsp"),
		@Result(name = "index", type = "chain", location = "index"),
		@Result(name = "pay_success", location = "/order/shopping_success.jsp") })
public class AlipayReturnUrlManageAction extends OrderBasicAction {

	private static final long serialVersionUID = -3769475961884283745L;
	private String out_trade_no;
	private String subject;
	private String body;
	private String total_fee;

	private Double payMoney;// 应付金额
	private Double paidMoney;// 你已付款
	private Double needPayMoney;// 还需付款
	private String shipType;// 送货方式1：不需要配送，2：快递送货上门

	private String shipTimeType;// 送货时间1：工作日，双休日，节假日均可
								// ，2：只双休日，节假日送货（工作日不送货）3:只工作日送货（双休日，节假日不送货）

	private String payType;// 支付方式1：支付宝2:银行支付

	private String totalMoney;// 应付款和已付款，字段名与其他页面支付统一

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

	public Double getPayMoney() {
		return payMoney;
	}

	public void setPayMoney(Double payMoney) {
		this.payMoney = payMoney;
	}

	public Double getPaidMoney() {
		return paidMoney;
	}

	public void setPaidMoney(Double paidMoney) {
		this.paidMoney = paidMoney;
	}

	public Double getNeedPayMoney() {
		return needPayMoney;
	}

	public void setNeedPayMoney(Double needPayMoney) {
		this.needPayMoney = needPayMoney;
	}

	public String getShipType() {
		return shipType;
	}

	public void setShipType(String shipType) {
		this.shipType = shipType;
	}

	public String getShipTimeType() {
		return shipTimeType;
	}

	public void setShipTimeType(String shipTimeType) {
		this.shipTimeType = shipTimeType;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String getTotalMoney() {
		return totalMoney;
	}

	public void setTotalMoney(String totalMoney) {
		this.totalMoney = totalMoney;
	}

	@Override
	public String execute() throws UnsupportedEncodingException {
		// 获取支付宝GET过来反馈信息

		final Map<String, String> params = new HashMap<String, String>();
		final Map<String, String[]> requestParams = request.getParameterMap();
		for (final Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
			final String name = iter.next();
			final String[] values = requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
			}
			// 乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
			valueStr = new String(valueStr.getBytes("ISO-8859-1"), "UTF-8");
			params.put(name, valueStr);
		}
		// 获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//
		String order_no = request.getParameter("out_trade_no"); // 获取订单号
		String total_fee = request.getParameter("total_fee"); // 获取总金额
		String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"), "UTF-8");

		// 获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)//

		// 计算得出通知验证结果
		boolean verify_result = AlipayNotify.verify(params);
		if (verify_result) {// 验证成功
			try {
				final Order o = service.updateOrderStatus(order_no, trade_no);
				this.setPayMoney(Double.valueOf(total_fee));
				this.setPaidMoney(Double.valueOf(total_fee));
				this.setNeedPayMoney(Double.valueOf("0.00"));
				this.setShipType(o.getShipType());
				this.setShipTimeType(o.getShipTimeType());
				this.setPayType(o.getPayType());
				totalMoney = total_fee;
			} catch (Exception e) {

			}
		} else {
			// 该页面可做页面美工编辑
			// response("验证失败");
		}
		return "pay_success";
	}

	// 3.成功提交订单
	public String queryOrder() {
		return "shopping_order";
	}
}
