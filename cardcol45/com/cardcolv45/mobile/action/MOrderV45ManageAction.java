package com.cardcolv45.mobile.action;

import java.math.BigDecimal;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.Timer;
import java.util.TreeMap;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;

import com.alipay.client.Rsa;
import com.alipay.server.AlipayConfig;
import com.aliyun.api.gateway.demo.enums.Constantsms;
import com.cardcol.web.basic.Product45;
import com.cardcol.web.order.ProductOrder45;
import com.cardcol.web.utils.PayUtilsC;
import com.freegym.web.active.Active;
import com.freegym.web.basic.Member;
import com.freegym.web.basic.MemberTicket;
import com.freegym.web.chinapay.action.ChinapayUtils;
import com.freegym.web.config.FactoryCosts;
import com.freegym.web.mobile.BasicJsonAction;
import com.freegym.web.order.ActiveOrder;
import com.freegym.web.order.AlwaysAddr;
import com.freegym.web.order.CourseOrder;
import com.freegym.web.order.FactoryOrder;
import com.freegym.web.order.Goods;
import com.freegym.web.order.GoodsOrder;
import com.freegym.web.order.Order;
import com.freegym.web.order.PlanOrder;
import com.freegym.web.order.Product;
import com.freegym.web.order.ProductOrder;
import com.freegym.web.plan.Course;
import com.freegym.web.plan.PlanRelease;
import com.freegym.web.task.CoursePaymentTask;
import com.freegym.web.utils.SessionConstant;
import com.freegym.web.utils.WebConstant;
import com.freegym.web.wechatPay.utils.GetWxOrderno;
import com.freegym.web.wechatPay.utils.RequestHandler;
import com.freegym.web.wechatPay.utils.Sha1Util;
import com.sanmen.web.core.bean.BaseMember;
import com.sanmen.web.core.common.LogicException;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Namespace("")
@InterceptorRefs({ @InterceptorRef("mobileStack") })
public class MOrderV45ManageAction extends BasicJsonAction implements Constantsms {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * E卡通支付订单
	 */
	public void payMent() {
		try {
			final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			String payType = request.getParameter("payType");
			String orderType = request.getParameter("orderType");
			String ticketId = request.getParameter("ticketId");
			double ticketMoney = 0d;
			if (ticketId != null) {
				final MemberTicket mt = (MemberTicket) service.load(MemberTicket.class, Long.valueOf(ticketId));
				ticketMoney = mt.getTicket().getPrice();
			}
			String trade = new String();
			Order order = new Order();
			if (orderType.equals("2")) {
				order = (ActiveOrder) service.load(ActiveOrder.class, id);
				ActiveOrder a = (ActiveOrder) service.load(ActiveOrder.class, id);
				trade = a.getActive().getName();
				if (ticketId != null)
					order.setTicketId(Long.valueOf(ticketId));
				order.setOrderMoney(a.getActive().getAmerceMoney() - ticketMoney);
			} else if (orderType.equals("3")) {
				order = (Order) service.load(PlanOrder.class, id);
				PlanOrder p = (PlanOrder) service.load(PlanOrder.class, id);
				if (ticketId != null) {
					order.setTicketId(Long.valueOf(ticketId));
				}
				order.setOrderMoney(p.getContractMoney() - ticketMoney);
			} else if (orderType.equals("5")) {
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
				SimpleDateFormat formats = new SimpleDateFormat("yyyy-MM-dd 23:59:59");
				Calendar cd = Calendar.getInstance();
				cd.add(Calendar.MONTH, 0);
				cd.set(Calendar.DAY_OF_MONTH, 1);// 设置为1号,当前日期既为本月第一天
				String first = format.format(cd.getTime());
				cd.set(Calendar.DAY_OF_MONTH, cd.getActualMaximum(Calendar.DAY_OF_MONTH));
				String last = formats.format(cd.getTime());
				List<?> list = service.queryForList(
						"SELECT * FROM tb_courserelease_order a WHERE a.orderDate <= ? AND a.orderDate > ? AND a.member = ? AND a.status != 0",
						last, first, getMobileUser().getId());
				if (list.size() != 0) {
					throw new LogicException("您这个月无法再购买课程！");
				}
				// order.setTicketId(Long.valueOf(ticketId).longValue());
				order = (CourseOrder) service.load(CourseOrder.class, id);
				CourseOrder c = (CourseOrder) service.load(CourseOrder.class, id);
				trade = c.getCourse().getCourseInfo().getName();
				if (ticketId != null) {
					order.setTicketId(Long.valueOf(ticketId));
				}
				order.setOrderMoney(c.getCourse().getMemberPrice() - ticketMoney);
			} else if (orderType.equals("6")) {
				order = (GoodsOrder) service.load(GoodsOrder.class, id);
				GoodsOrder g = (GoodsOrder) service.load(GoodsOrder.class, id);
				trade = g.getGoods().getName();
				if (ticketId != null)
					order.setTicketId(Long.valueOf(ticketId));
				order.setOrderMoney(g.getGoods().getPrice() - ticketMoney);
			} else if (orderType.equals("8")) {
				order = (ProductOrder45) service.load(ProductOrder45.class, id);
				ProductOrder45 p = (ProductOrder45) service.load(ProductOrder45.class, id);
				trade = p.getProduct().getName();
				if (ticketId != null)
					order.setTicketId(Long.valueOf(ticketId));
				order.setOrderMoney(p.getProduct().getPrice() - ticketMoney);
			}
			JSONObject obj = new JSONObject();
			if (order.getOrderMoney() <= 0) {
				order.setOrderMoney(0.00);
				if (orderType.equals("2") || orderType.equals("8")) {
					order.setStatus('1');
				} else if (orderType.equals("6") || orderType.equals("5")) {
					order.setStatus('3');
				}
				service.ordersms(order, orderType);
				order = (Order) service.saveOrUpdate(order);
				if (orderType.equals("8")) {
					service.updateOrderStatuse(order.getNo());
				}
				obj.accumulate("success", true).accumulate("code", "2");
			} else {
				order = (Order) service.saveOrUpdate(order);
				String orderSign;
				// update
				// orderSign = PayUtils.orderSign(request, response, order,
				// payType, trade);
				orderSign = PayUtilsC.orderSign(request, response, order, payType, trade);
				obj.accumulate("success", true)
						.accumulate("order",
								new JSONObject().accumulate("id", order.getId()).accumulate("no", order.getNo())
										.accumulate("payNo", order.getPayNo())
										.accumulate("orderDate", sdf.format(order.getOrderDate())))
						.accumulate("signData", orderSign).accumulate("code", "1");
			}
			response(obj);
		} catch (Exception e) {
			e.printStackTrace();
			response(new JSONObject().accumulate("success", false).accumulate("msg", e));
		}
	}

	/**
	 * 卡库支付订单
	 */
	@SuppressWarnings({ "deprecation", "static-access", "unused" })
	public void paymentProductType() {
		try {
			String payType = request.getParameter("payType");
			String payNo = request.getParameter("payNo");

			final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

			// 根据payNo查询需要支付订单信息
			final StringBuffer sql = new StringBuffer();
			String orderSql = Order.getOrderSql();
			sql.append("SELECT * FROM (").append(orderSql).append(") t where payNo = ? ");
			final List<Object> params = new ArrayList<Object>();
			params.add(payNo);

			List<Map<String, Object>> queryForList = service.queryForList(sql.toString(), params.toArray());

			StringBuffer sb = new StringBuffer();
			// Date orderStartTime = null;
			Date OrderDate = null;
			BigDecimal total_fee = new BigDecimal(0.0);
			Long id = null;

			for (Iterator<?> it = queryForList.iterator(); it.hasNext();) {
				Map<?, ?> map = (Map<?, ?>) it.next();
				BigDecimal ticketAmount = map.get("ticket_amount") == null ? new BigDecimal(0)
						: new BigDecimal(map.get("ticket_amount").toString());
				BigDecimal money = new BigDecimal(map.get("orderMoney").toString()).subtract(ticketAmount);
				total_fee = total_fee.add(money);
				sb.append(sb.length() > 0 ? "," : "").append(map.get("name")).append("(")
						.append(WebConstant.getType((String) map.get("type"))).append(")");
				id = Long.parseLong((map.get("id")).toString());
				OrderDate = (Date) map.get("OrderDate");
				service.updateOrderPayType(payNo, payType);
			}
			// total_fee=new BigDecimal("0.01");
			total_fee = total_fee.setScale(2);

			final JSONObject obj = new JSONObject(), orderJson = new JSONObject();
			if (payType.equals("1")) {

				final String subject = sb.toString();
				final String body = sdf.format(OrderDate) + "开始的" + sb.toString();
				final StringBuffer makeParms = new StringBuffer();
				makeParms.append("partner=\"").append(AlipayConfig.partner).append("\"&");
				makeParms.append("seller_id=\"").append(AlipayConfig.seller_email).append("\"&");
				makeParms.append("out_trade_no=\"").append(payNo.toString()).append("\"&");
				makeParms.append("subject=\"").append(subject).append("\"&");
				makeParms.append("body=\"").append(body).append("\"&");
				makeParms.append("total_fee=\"").append(total_fee.toString()).append("\"&");
				makeParms.append("notify_url=\"").append(URLEncoder.encode(AlipayConfig.notify_url)).append("\"&");
				makeParms.append("return_url=\"").append(URLEncoder.encode(AlipayConfig.return_url)).append("\"&");
				makeParms.append("service=\"").append("mobile.securitypay.pay").append("\"&");
				makeParms.append("payment_type=\"").append("1").append("\"&");
				makeParms.append("_input_charset=\"").append("utf-8").append("\"&");
				makeParms.append("it_b_pay=\"30m\"&").append("param=\"product\"");
				final String sign = URLEncoder.encode(Rsa.sign(makeParms.toString(), AlipayConfig.private_key));
				makeParms.append("&sign=\"").append(sign).append("\"&");
				makeParms.append("sign_type=\"").append("RSA").append("\"");
				log.error(makeParms.toString());
				boolean doCheck = Rsa.doCheck(makeParms.toString(), URLDecoder.decode(sign),
						AlipayConfig.ali_public_key);
				log.error("签名验证：" + doCheck);
				obj.accumulate("success", true).accumulate("sign", sign).accumulate("info", makeParms.toString());
				response(obj);
			} else if (payType.equals("0")) {

				final ChinapayUtils pay = new ChinapayUtils(this, queryForList);
				final String sign = pay.order();
				orderJson.accumulate("id", id).accumulate("no", payNo.toString())
						.accumulate("orderDate", sdf.format(OrderDate)).accumulate("orderSign", sign);
				obj.accumulate("success", true).accumulate("order", orderJson);
				response(obj);
			} else if (payType.equals("2")) {
				// 新加入微信支付跳转
				// 网页授权后获取传递的参数
				String money = total_fee.toString();
				// 金额转化为分为单位
				float sessionmoney = Float.parseFloat(money);
				String finalmoney = String.format("%.2f", sessionmoney);
				finalmoney = finalmoney.replace(".", "");
				finalmoney = Integer.parseInt(finalmoney) + "";
				// 商户相关资料
				String appid = "wxb6e4cf82d2d3d874";
				String appsecret = "d37f94314058f38024610b0047648ec2";
				String partner = "1325365201";
				String partnerkey = "cArdCol1949com1010jsZGNessfit123";

				// 获取openId后调用统一支付接口https://api.mch.weixin.qq.com/pay/unifiedorder
				// 商户号partner;
				// 随机数
				String nonce_str = Sha1Util.getNonceStr();
				String nonce_str1 = nonce_str;
				String timestamp = Sha1Util.getTimeStamp();
				// 商品描述根据情况修改
				String body = "卡库健身";
				// 商户订单号
				String out_trade_no = payNo;
				String spbill_create_ip = "192.168.1.1";
				// 这里notify_url是 支付完成后微信发给该链接信息，可以判断会员是否支付成功，改变订单状态等。
				String notify_url = "http://test.ecartoon.com.cn/mwpaynotify.asp";
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
				String xml = "<xml>" + "<sign>" + sign + "</sign>" + "<appid>" + appid + "</appid>" + "<body>" + body
						+ "</body>" + "<mch_id>" + partner + "</mch_id>" + "<nonce_str>" + nonce_str + "</nonce_str>"
						+ "<notify_url>" + notify_url + "</notify_url>" + "<out_trade_no>" + out_trade_no
						+ "</out_trade_no>" + "<spbill_create_ip>" + spbill_create_ip + "</spbill_create_ip>"
						+ "<total_fee>" + finalmoney + "</total_fee>" + "<trade_type>" + trade_type + "</trade_type>"
						+ "</xml>";
				String createOrderURL = "https://api.mch.weixin.qq.com/pay/unifiedorder";
				String prepay_id = "";
				try {
					String result = new GetWxOrderno().getPayNo(createOrderURL, xml);
					if (result.equals("")) {
						request.setAttribute("ErrorMsg", "统一支付接口获取预支付订单出错");
						response.sendRedirect("/weChatpay/error.jsp");
					}
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

				obj.accumulate("success", true).accumulate("appid", appid).accumulate("prepay_id", prepay_id)
						.accumulate("partner", partner).accumulate("timeStamp", timestamp)
						.accumulate("nonceStr", nonce_str).accumulate("nonceStr1", nonce_str1)
						.accumulate("package", "Sign=WXPay").accumulate("sign", finalsign);
				// service.updateOrderStatus(out_trade_no);
				response(obj);
			}
		} catch (Exception e) {
			log.error("error", e);
			response(e);
		}
	}

	/**
	 * 新增所有产品下订单接口（增加其它产品提交订单 活动订单、健身计划订单、场地订单、课程订单） jsons = [{product:
	 * ,productType: 1,quantity: 1, unitPrice: 0.01, startTime: '2013-11-11',
	 * endTime: '2013-12-10', reportDay: 30,payType:1}]
	 * 
	 */
	@SuppressWarnings("unused")
	public String saveProductType() {
		try {
			// 测试数据
			// JSONObject objx = new JSONObject();
			// objx.accumulate("address", "")
			// .accumulate("productType", 1)
			// .accumulate("quantity", 1)
			// .accumulate("product", 1275)
			// .accumulate("unitPrice", 0)
			// .accumulate("payType", "1")
			// .accumulate("startTime", "2018-01-10")
			// ;
			// JSONArray jsonArray = new JSONArray();
			// jsonArray.add(objx);
			// jsons = jsonArray.toString();
			final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			Order order = new Order();
			String subject = "";
			final JSONArray objs = JSONArray.fromObject(URLDecoder.decode(jsons, "UTF-8"));
			JSONObject retobj = objs.getJSONObject(0);
			final Double payServiceCostE = getMessage("pay.service.cost.e") == null
					|| "".equals(getMessage("pay.service.cost.e") == null) ? 0d
							: new Double(Long.parseLong(getMessage("pay.service.cost.e")) / 100);
			final Double payServiceCostM = getMessage("pay.service.cost.m") == null
					|| "".equals(getMessage("pay.service.cost.m") == null) ? 0d
							: new Double(Long.parseLong(getMessage("pay.service.cost.e")) / 100);
			final BaseMember mu = getLoginMember();
			final Member m = (Member) service.load(Member.class, mu.getId());
			for (final Iterator<?> it = objs.iterator(); it.hasNext();) {
				final JSONObject obj = (JSONObject) it.next();
				final int quan = 1; // obj.getInt("quantity");
				double price = 0d;
				// final double price = obj.getDouble("unitPrice");
				double ticketMoney = 0d;
				if (obj.containsKey("ticketId")) {
					final MemberTicket mt = (MemberTicket) service.load(MemberTicket.class, obj.getLong("ticketId"));
					ticketMoney = mt.getTicket().getPrice();
				}
				long pid = obj.getLong("product");
				if (obj.getString("productType").equals("1")) {
					if (m.getCoach() != null) {
						final Product pd = (Product) service.load(Product.class, pid);
						if (pd.getMember().getRole().equals("S")) {
							if (!m.getCoach().getId().equals(pd.getMember().getId()))
								throw new LogicException("请先解除您的私教后再购买其它私教的套餐费！");
						}
					}
				}
				if (obj.containsKey("startTime")) {
					order.setOrderStartTime(ymd.parse(obj.getString("startTime")));
				}

				if (obj.getString("productType").equals("1")) {
					order = new ProductOrder();
					final Product product = (Product) service.load(Product.class, pid);
					price = product.getCost();
					order.setOrderStartTime(
							obj.get("startTime") == null ? new Date() : ymd.parse(obj.getString("startTime")));
					((ProductOrder) order).setProduct(product);
					((ProductOrder) order).setReportDay(obj.containsKey("reportDay") ? obj.getInt("reportDay") : null);
					((ProductOrder) order).setBalanceTimes(0);
					((ProductOrder) order).setIsBreach("1");
					((ProductOrder) order).setIsDelay("0");
					if (obj.containsKey("ticketId"))
						order.setTicketId(obj.getLong("ticketId"));
					order.setPayNo(service.getKeyNo("", "CARDCOL_ORDER_NO", 28));
					order.setNo(service.getKeyNo("", "CARDCOL_ORDER_NO", 14));
					subject = product.getName();
					if (!"5".equals(product.getType())) {
						order.setOrderEndTime(new Date(order.getOrderStartTime().getTime()
								+ (product.getWellNum() * 7 * 24 * 60 * 60 * 1000)));
					}
				} else if (obj.getString("productType").equals("2")) {
					order = new ActiveOrder();
					final Active at = (Active) service.load(Active.class, pid);
					price = at.getAmerceMoney();
					((ActiveOrder) order).setActive(at);
					if (!obj.containsKey("weight")) {
						throw new LogicException("请填写当前体重再确定提交！");
					}
					((ActiveOrder) order).setWeight(obj.getDouble("weight"));
					if (obj.containsKey("ticketId"))
						order.setTicketId(obj.getLong("ticketId"));
					order.setPayNo(service.getKeyNo("", "TB_ACTIVE_ORDER", 28));
					order.setNo(service.getKeyNo("", "TB_ACTIVE_ORDER", 14));
					subject = ((Active) service.load(Active.class, pid)).getName();
				} else if (obj.getString("productType").equals("3")) {
					order = new PlanOrder();
					final PlanRelease plan = (PlanRelease) service.load(PlanRelease.class, pid);
					price = plan.getUnitPrice();
					if (price == 0d) {
						((PlanOrder) order).setPlanRelease(plan);
						order.setMember(m);
						order.setOrderMoney(0d);
						order.setOrderDate(new Date());
						order.setOrderStartTime(
								obj.containsKey("startTime") ? ymd.parse(obj.getString("startTime")) : null);
						order.setOrderBalanceTime(
								obj.containsKey("startTime") ? ymd.parse(obj.getString("startTime")) : null);
						order.setOrderEndTime(obj.containsKey("endTime") ? ymd.parse(obj.getString("endTime")) : null);
						service.genPlanData(order, plan);
					}
					((PlanOrder) order).setPlanRelease(plan);
					((PlanOrder) order).setReportDay(obj.containsKey("reportDay") ? obj.getInt("reportDay") : null);
					((PlanOrder) order).setBalanceTimes(0);
					((PlanOrder) order).setIsBreach("1");
					((PlanOrder) order).setIsDelay("0");
					// service.genPlanData(order, plan);
					subject = plan.getPlanName();
					if (obj.containsKey("ticketId")) {
						order.setTicketId(obj.getLong("ticketId"));
					}
					order.setPayNo(service.getKeyNo("", "TB_PLAN_ORDER", 28));
					order.setNo(service.getKeyNo("", "TB_PLAN_ORDER", 14));
				} else if (obj.getString("productType").equals("4")) {
					order = new FactoryOrder();
					final FactoryCosts fc = (FactoryCosts) service.load(FactoryCosts.class, pid);
					price = StringUtils.isBlank(fc.getCosts1()) ? 0d : Double.parseDouble(fc.getCosts1());
					subject = fc.getName();
					((FactoryOrder) order).setFactoryCosts(fc);
					if (obj.containsKey("ticketId"))
						order.setTicketId(obj.getLong("ticketId"));
					order.setPayNo(service.getKeyNo("", "TB_FACTORY_ORDER", 28));
					order.setNo(service.getKeyNo("", "TB_FACTORY_ORDER", 14));
				} else if (obj.getString("productType").equals("5")) {
					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
					SimpleDateFormat formats = new SimpleDateFormat("yyyy-MM-dd 23:59:59");
					Calendar c = Calendar.getInstance();
					c.add(Calendar.MONTH, 0);
					c.set(Calendar.DAY_OF_MONTH, 1);// 设置为1号,当前日期既为本月第一天
					String first = format.format(c.getTime());
					c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
					String last = formats.format(c.getTime());
					List<?> list = service.queryForList(
							"SELECT * FROM tb_courserelease_order a WHERE a.orderDate <= ? AND a.orderDate > ? AND a.member = ? AND a.status != 0",
							last, first, getMobileUser().getId());
					if (list.size() != 0) {
						throw new LogicException("您这个月无法再购买课程！");
					}
					final Course couse = (Course) service.load(Course.class, pid);
					// price = couse.getMemberPrice();
					// 2017-7-10 黄文俊 结算的时候是按照非会员价结算的
					price = couse.getHourPrice();
					order = new CourseOrder();
					((CourseOrder) order).setCourse(new Course(pid));
					((CourseOrder) order).setReportDay(obj.containsKey("reportDay") ? obj.getInt("reportDay") : null);
					((CourseOrder) order).setBalanceTimes(0);
					((CourseOrder) order).setIsBreach("1");
					((CourseOrder) order).setIsDelay("0");
					subject = couse.getCourseInfo().getName();
					if (obj.containsKey("ticketId"))
						order.setTicketId(obj.getLong("ticketId"));
					order.setPayNo(service.getKeyNo("", "TB_COURSE_ORDER", 28));
					order.setNo(service.getKeyNo("", "TB_COURSE_ORDER", 14));
				} else if (obj.getString("productType").equals("6")) {
					order = new GoodsOrder();
					final Goods goods = (Goods) service.load(Goods.class, pid);
					price = goods.getPrice();
					subject = goods.getName();
					((GoodsOrder) order).setGoods(goods);
					if (obj.containsKey("ticketId"))
						order.setTicketId(obj.getLong("ticketId"));
					order.setPayNo(service.getKeyNo("", "TB_GOODS_ORDER", 28));
					order.setNo(service.getKeyNo("", "TB_GOODS_ORDER", 14));
					// 手环 需要有送货地址
				} else if (obj.getString("productType").equals("7")) {

					order = new GoodsOrder();
					// 给订单添加送货地址 by 窦元 2017-08-18

					String addressContent = obj.getString("address");
					if (!"".equals(addressContent)) {
						AlwaysAddr add = new AlwaysAddr();
						add.setAddr(addressContent);
						add.setMember(m);
						add.setMoble(m.getMobilephone());
						add.setEmail(m.getEmail());
						add.setName(m.getName());
						add = (AlwaysAddr) service.saveOrUpdate(add);

						long addId = add.getId();
						AlwaysAddr address = (AlwaysAddr) service.load(AlwaysAddr.class, addId);
						order.setAlwaysAddr(address);
					} else {
						System.err.println("请先选择收货地址！");
					}
					final Goods goods = (Goods) service.load(Goods.class, pid);
					price = goods.getPrice();
					subject = goods.getName();
					((GoodsOrder) order).setGoods(goods);
					if (obj.containsKey("ticketId"))
						order.setTicketId(obj.getLong("ticketId"));
					order.setPayNo(service.getKeyNo("", "TB_GOODS_ORDER", 28));
					order.setNo(service.getKeyNo("", "TB_GOODS_ORDER", 14));
				} else if (obj.getString("productType").equals("8")) {
					order = new ProductOrder45();
					final Product45 prod = (Product45) service.load(Product45.class, pid);
					((ProductOrder45) order).setProduct(prod);
					price = prod.getPrice();
					subject = prod.getName();
					if (obj.containsKey("ticketId"))
						order.setTicketId(obj.getLong("ticketId"));
					// 2017-7-19 添加结算时间的业务逻辑
					order.setPayNo(service.getKeyNo("", "TB_PRODUCT_ORDER_V45", 28));
					order.setNo(service.getKeyNo("", "TB_PRODUCT_ORDER_V45", 14));
					// 计算结束时间 往 obj 中添加结束时间
					int periodUnit = (prod.getPeriodUnit() == "A" ? 30
							: prod.getPeriodUnit() == "B" ? 3 * 30 : prod.getPeriodUnit() == "C" ? 12 * 30 : 1);
					long dateTime = (long) (prod.getPeriod() * periodUnit) - 1;
					Date orderStartTime = ymd.parse(obj.getString("startTime"));

					if (dateTime != 0) {
						Date newDate = addDate(orderStartTime, dateTime);
						order.setOrderEndTime(newDate);
					} else {
						order.setOrderEndTime(orderStartTime);
					}
				}
				order.setTicketAmount(ticketMoney);
				order.setOrderDate(new Date());
				order.setMember(m);
				order.setUnitPrice(price);
				order.setCount(quan);
				order.setContractMoney(price * quan);
				if ((price * quan - ticketMoney) > 0) {
					order.setOrderMoney(price * quan - ticketMoney);
					order.setStatus('0');
				} else {
					order.setOrderMoney(0.00);
					if (obj.getString("productType").equals("2") || obj.getString("productType").equals("8")) {
						order.setStatus('1');
					} else if (obj.getString("productType").equals("6") || obj.getString("productType").equals("5")) {
						order.setStatus('3');
					}
				}
				if (obj.getString("productType").equals("4")) {
					order.setOrderStartTime(
							obj.containsKey("startTime") ? sdf.parse(obj.getString("startTime")) : null);
					order.setOrderBalanceTime(
							obj.containsKey("startTime") ? sdf.parse(obj.getString("startTime")) : null);
					order.setOrderEndTime(obj.containsKey("endTime") ? sdf.parse(obj.getString("endTime")) : null);
				} else {
					order.setOrderStartTime(
							obj.containsKey("startTime") ? ymd.parse(obj.getString("startTime")) : null);
					order.setOrderBalanceTime(
							obj.containsKey("startTime") ? ymd.parse(obj.getString("startTime")) : null);

					if (order.getOrderEndTime() == null) {
						order.setOrderEndTime(obj.containsKey("endTime") ? ymd.parse(obj.getString("endTime")) : null);
					}
				}
				// order.setReportDay(obj.getInt("reportDay"));
				// order.setBalanceTimes(0);
				// order.setIsBreach("1");
				order.setSurplusMoney(price * quan);
				// order.setIsDelay("0");
				order.setShipType(obj.containsKey("shipType") ? obj.getString("shipType") : "1");
				order.setShipTimeType(obj.containsKey("shipTimeType") ? obj.getString("shipTimeType") : "1");
				if (obj.containsKey("payType"))
					order.setPayType(obj.getString("payType"));

				order.setPayServiceCostE(payServiceCostE);
				order.setPayServiceCostM(payServiceCostM);
				order.setOrigin('C');

				if (obj.getString("productType").equals("1")) {
					order = (ProductOrder) service.saveOrUpdate(order);
				} else if (obj.getString("productType").equals("2")) {
					order = (ActiveOrder) service.saveOrUpdate(order);
				} else if (obj.getString("productType").equals("3")) {
					order = (PlanOrder) service.saveOrUpdate(order);
				} else if (obj.getString("productType").equals("4")) {
					// 判断时间段内已有订单
					final List<?> list = service.findObjectBySql(
							"from FactoryOrder a where a.factoryCosts.id = ? and a.orderStartTime between ? and ? ",
							pid, order.getOrderStartTime(), order.getOrderEndTime());
					if (list.size() > 0) {
						throw new Exception("该时间段已有人预定，请重新选择时间!");
					} else {
						order = (FactoryOrder) service.saveOrUpdate(order);
						final Timer timer = new Timer();
						timer.schedule(new CoursePaymentTask(service, order.getId()),
								SessionConstant.PAYMENT_FACTORY_TIME * 60 * 1000);

					}
				} else if (obj.getString("productType").equals("5")) {
					order = (CourseOrder) service.saveOrUpdate(order);
				} else if (obj.getString("productType").equals("6")) {
					order = (GoodsOrder) service.saveOrUpdate(order);
				} else if (obj.getString("productType").equals("7")) {
					order = (GoodsOrder) service.saveOrUpdate(order);
				} else if (obj.getString("productType").equals("8")) {
					order = (ProductOrder45) service.saveOrUpdate(order);
				}
			}
			JSONObject obj = new JSONObject();
			if (order.getOrderMoney() > 0) {
				String orderSign = PayUtilsC.orderSign(request, response, order, order.getPayType(), subject);
				obj.accumulate("success", true)
						.accumulate("order",
								new JSONObject().accumulate("id", order.getId()).accumulate("no", order.getNo())
										.accumulate("payNo", order.getPayNo())
										.accumulate("orderDate", sdf.format(order.getOrderDate())))
						.accumulate("signData", orderSign).accumulate("code", "1");
			} else {
				String no = order.getNo();
				order = service.updateOrderStatus4z(no, "0元购买回调", "");
				if (order.getId() != 0) {
					obj.accumulate("success", true).accumulate("code", "2");
				}

				// if (retobj.getString("productType").equals("6")) {
				// GoodsBalanceImpl bal = new GoodsBalanceImpl();
				// service.saveOrUpdate(bal.execute(order));
				// service.updateOrderStatuse(order.getNo());
				// } else if (retobj.getString("productType").equals("5")) {
				// CourseBalanceImpl bal = new CourseBalanceImpl();
				// service.saveOrUpdate(bal.execute(order));
				// }
				// System.out.println(retobj.getString("productType"));
				// service.ordersms(order, retobj.getString("productType"));
				// obj.accumulate("success", true).accumulate("code", "2");
			}
			if (order.getTicketId() != null) {
				MemberTicket mt = (MemberTicket) service.load(MemberTicket.class, order.getTicketId());
				mt.setStatus(2);
				service.saveOrUpdate(mt);
			}
			response(obj);
		} catch (Exception e) {
			log.error("error", e);
			response(e);
		}
		return null;
	}

	// 时间相加的方法
	public static Date addDate(Date date, long day) {
		long time = date.getTime(); // 得到指定日期的毫秒数
		day = day * 24 * 60 * 60 * 1000; // 要加上的天数转换成毫秒数
		time += day; // 相加得到新的毫秒数
		return new Date(time); // 将毫秒数转换成日期
	}
}
