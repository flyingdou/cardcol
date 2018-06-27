package com.freegym.web.chinapay.action;

import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.freegym.web.OrderBasicAction;
import com.freegym.web.chinapay.config.ChinaPayConfig;
import com.freegym.web.order.Order;

@Namespace("")
@InterceptorRefs({ @InterceptorRef("customStack") })
@Results({ @Result(name = "shopping_order", location = "/order/shopping_order.jsp"),
		@Result(name = "index", type = "chain", location = "index"),
		@Result(name = "pay_success", location = "/order/shopping_success.jsp") })
public class ChinaPayPgReturnManageAction extends OrderBasicAction {

	private static final long serialVersionUID = -3769475961884283745L;
	private String merid; // ChinaPay统一分配给商户的商户号，15位长度，必填
	private String orderno; // 商户提交给ChinaPay的交易订单号，订单号的第五至第九位必须是商户号的最后五位，即“12345”；16位长度，必填
	private String transdate; // 订单交易日期，8位长度，必填，格式yyyyMMdd
	private String amount; // 订单交易金额，12位长度，左补0，必填,单位为分，000000001234 表示 12.34 元
	private String currencycode; // 订单交易币种，3位长度，固定为人民币156，必填
	private String transtype; // 交易类型，4位长度，必填，0001表示消费交易，0002表示退货交易
	private String status; // 表示交易转态，只有"1001"的时候才为交易成功，其他均为失败，因此在验证签名数据为ChinaPay发出的以后，还需要判定交易状态代码为"1001"
	private String checkvalue; // 256字节长的ASCII码,为此次交易提交关键数据的数字签名，必填
	private String GateId; // 支付网关号，可选，参看银联网关类型，如填写GateId（支付网关号），则消费者将直接进入支付页面，否则进入网关选择页面
	private String Priv1; // 商户私有域，长度不要超过60个字节,商户通过此字段向Chinapay发送的信息，Chinapay依原样填充返回给商户

	private Double payMoney;// 应付金额
	private Double paidMoney;// 你已付款
	private Double needPayMoney;// 还需付款
	private String shipType;// 送货方式1：不需要配送，2：快递送货上门
	private String shipTimeType;// 送货时间1：工作日，双休日，节假日均可
								// ，2：只双休日，节假日送货（工作日不送货）3:只工作日送货（双休日，节假日不送货）
	private String payType;// 支付方式1：支付宝2:银行支付
	private String totalMoney;

	public String getMerid() {
		return merid;
	}

	public void setMerid(String merid) {
		this.merid = merid;
	}

	public String getOrderno() {
		return orderno;
	}

	public void setOrderno(String orderno) {
		this.orderno = orderno;
	}

	public String getTransdate() {
		return transdate;
	}

	public void setTransdate(String transdate) {
		this.transdate = transdate;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getCurrencycode() {
		return currencycode;
	}

	public void setCurrencycode(String currencycode) {
		this.currencycode = currencycode;
	}

	public String getTranstype() {
		return transtype;
	}

	public void setTranstype(String transtype) {
		this.transtype = transtype;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCheckvalue() {
		return checkvalue;
	}

	public void setCheckvalue(String checkvalue) {
		this.checkvalue = checkvalue;
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

	public String getTotalMoney() {
		return totalMoney;
	}

	public void setTotalMoney(String totalMoney) {
		this.totalMoney = totalMoney;
	}

	@SuppressWarnings("unused")
	@Override
	public String execute() {
		try {
			chinapay.PrivateKey key = new chinapay.PrivateKey();
			chinapay.SecureLink t;
			boolean flag;
			boolean flag1;
			String msg = "";
			String PgId = ChinaPayConfig.PgId;
			String tomcatRoot = request.getSession().getServletContext().getRealPath("/");
			flag = key.buildKey(PgId, 0, tomcatRoot + ChinaPayConfig.PgPubkPath);
			if (flag == false) {
				msg = "build key error!";
				return null;
			}
			t = new chinapay.SecureLink(key);
			flag1 = t.verifyTransResponse(merid, orderno, amount, currencycode, transdate, transtype, status,
					checkvalue);
			if (flag1 == false) {
				System.out.println("交易验证失败!");
				msg = "交易验证失败!";
			} else {
				if (status.equals("1001")) {
					// 1001表示支付成功，之后为商户系统对成功订单的逻辑处理
					// 注意：如果您在提交时同时填写了页面返回地址和后台返回地址，且地址相同，请先做一次数据库查询判断订单状态，以防止重复处理该笔订单
					/* …... 数据库更新等相关处理过程 */
					final Order order = service.updateOrderStatus(orderno, "");
					if (order != null) {
						this.setPayMoney(order.getOrderMoney());
						this.setPaidMoney(order.getOrderMoney());
						this.setNeedPayMoney(Double.valueOf("0.00"));
						this.setShipType(order.getShipType());
						this.setShipTimeType(order.getShipTimeType());
						this.setPayType(order.getPayType());
						setTotalMoney(order.getOrderMoney() + "");
					}
				}
			}
			return "pay_success";
			// return queryOrder();
		} catch (Exception e) {
			return null;
		}
	}

	// 3.成功提交订单
	public String queryOrder() {
		return "shopping_order";
	}
}
