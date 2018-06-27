package ecartoon.wx.action;

import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Timer;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.aliyun.api.gateway.demo.enums.Constantsms;
import com.cardcol.web.balance.impl.CourseBalanceImpl;
import com.cardcol.web.balance.impl.GoodsBalanceImpl;
import com.cardcol.web.basic.Product45;
import com.cardcol.web.order.ProductOrder45;
import com.databaseConnection.DataBaseConnection;
import com.freegym.web.active.Active;
import com.freegym.web.basic.Member;
import com.freegym.web.basic.MemberTicket;
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
import com.freegym.web.utils.EasyUtils;
import com.freegym.web.utils.SessionConstant;
import com.sanmen.web.core.bean.BaseMember;
import com.sanmen.web.core.common.LogicException;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Namespace("")
@InterceptorRefs({ @InterceptorRef("customStack") })
@Results({ @Result(name = "saveProductType", location = "/ecartoon-weixin/submit_order.jsp"),
		@Result(name = "payResult", location = "/ecartoon-weixin/pay/zhifu_result_s.jsp") })
public class EOrderWXManageAction extends BasicJsonAction implements Constantsms {

	private static final long serialVersionUID = 1L;

	/**
	 * 保存商品名称
	 */
	private String productName;

	/**
	 * 提交订单
	 */
	@SuppressWarnings("unused")
	public String payMent() {
		try {
			final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			/* String payType = "1"; */
			String orderType = request.getParameter("orderType");
			String ticketId = request.getParameter("ticketId");
			Member member = (Member) session.getAttribute("member");
			int days = 0;
			double ticketMoney = 0d;
			/*
			 * if (ticketId != null) { final MemberTicket mt = (MemberTicket)
			 * service.load(MemberTicket.class, Long.valueOf(ticketId));
			 * ticketMoney = mt.getTicket().getPrice(); }
			 */
			String trade = new String();
			Order order = new Order();
			if (orderType.equals("1")) {
				order = (ProductOrder) service.load(Product.class, id);
				ProductOrder p = (ProductOrder) service.load(ProductOrder.class, id);
				/*
				 * trade = p.getProduct().getName(); if (ticketId != null)
				 * order.setTicketId(Long.valueOf(ticketId));
				 * order.setOrderMoney(p.getProduct().getCost() - ticketMoney);
				 */

				EasyUtils.orderType = "TB_PRODUCT_ORDER";
				EasyUtils.orderNo = order.getNo();
			} else if (orderType.equals("2")) {
				order = (ActiveOrder) service.load(ActiveOrder.class, id);
				ActiveOrder a = (ActiveOrder) service.load(ActiveOrder.class, id);
				trade = a.getActive().getName();
				/*
				 * if (ticketId != null)
				 * order.setTicketId(Long.valueOf(ticketId));
				 * order.setOrderMoney(a.getActive().getAmerceMoney() -
				 * ticketMoney);
				 */

				if (a.getActive().getDays() != null) {
					days = a.getActive().getDays() - 1;
				}

				EasyUtils.orderType = "TB_ACTIVE_ORDER";
				EasyUtils.orderNo = order.getNo();
			} else if (orderType.equals("5")) {
				order = (CourseOrder) service.load(CourseOrder.class, id);
				CourseOrder c = (CourseOrder) service.load(CourseOrder.class, id);
				trade = c.getCourse().getCourseInfo().getName();
				/*
				 * if (ticketId != null)
				 * order.setTicketId(Long.valueOf(ticketId));
				 */
				// order.setOrderMoney(c.getCourse().getMemberPrice() == null ?
				// 0 : c.getCourse().getMemberPrice() - ticketMoney);

				EasyUtils.orderType = "TB_CourseRelease_ORDER";
				EasyUtils.orderNo = order.getNo();
			} else if (orderType.equals("6")) {
				order = (GoodsOrder) service.load(GoodsOrder.class, id);
				GoodsOrder g = (GoodsOrder) service.load(GoodsOrder.class, id);
				trade = g.getGoods().getName();
				/*
				 * if (ticketId != null)
				 * order.setTicketId(Long.valueOf(ticketId));
				 * order.setOrderMoney(g.getGoods().getPrice() - ticketMoney);
				 */

				EasyUtils.orderType = "tb_goods_order";
				EasyUtils.orderNo = order.getNo();
			} else if (orderType.equals("8")) {
				order = (ProductOrder45) service.load(ProductOrder45.class, id);
				ProductOrder45 p = (ProductOrder45) service.load(ProductOrder45.class, id);
				trade = p.getProduct().getName();
				/*
				 * if (ticketId != null)
				 * order.setTicketId(Long.valueOf(ticketId));
				 * order.setOrderMoney(p.getProduct().getPrice() - ticketMoney);
				 */

				EasyUtils.orderType = "TB_PRODUCT_ORDER_V45";
				EasyUtils.orderNo = order.getNo();
			}
			JSONObject obj = new JSONObject();
			if (order.getOrderStartTime().getTime() < new Date().getTime()) {
				order.setOrderStartTime(new Date());
			}

			/*
			 * if (order.getOrderMoney() <= 0) { order.setOrderMoney(0.00); if
			 * (orderType.equals("2") || orderType.equals("8")) {
			 * order.setStatus('1'); } else if (orderType.equals("6") ||
			 * orderType.equals("5")) { order.setStatus('3'); }
			 * service.ordersms(order, orderType); order = (Order)
			 * service.saveOrUpdate(order); if (orderType.equals("8")) {
			 * service.updateOrderStatuse(order.getNo()); }
			 * obj.accumulate("success", true).accumulate("code", "2");
			 * 
			 * loginCommons.orderType = "TB_CourseRelease_ORDER";
			 * loginCommons.orderNo = order.getNo(); } else { order = (Order)
			 * service.saveOrUpdate(order); String orderSign; orderSign =
			 * PayUtils.orderSign(request, response, order, payType, trade);
			 * obj.accumulate("success", true) .accumulate("order", new
			 * JSONObject().accumulate("id", order.getId()).accumulate("no",
			 * order.getNo()) .accumulate("payNo", order.getPayNo())
			 * .accumulate("orderDate", sdf.format(order.getOrderDate())))
			 * .accumulate("signData", orderSign).accumulate("code", "1"); }
			 */
			JSONObject res = new JSONObject();
			res.accumulate("prod_name", trade).accumulate("contractMoney", order.getContractMoney())
					.accumulate("orderMoney", order.getOrderMoney()).accumulate("orderType", orderType);
			session.setAttribute("payMain", res);
			response.sendRedirect("ecartoon-weixin/submit_order.jsp");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 新增所有产品下订单接口（增加其它产品提交订单 活动订单、健身计划订单、场地订单、课程订单） jsons = [{product:
	 * ,productType: 1,quantity: 1, unitPrice: 0.01, startTime: '2013-11-11',
	 * endTime: '2013-12-10', reportDay: 30,payType:1}]
	 * 
	 */
	@SuppressWarnings({ "deprecation", "unused" })
	public void saveProductType() {
		try {

			// 判断当前用户是否绑定手机号
			String mobilePhone = ((Member) session.getAttribute("member")).getMobilephone();
			if (StringUtils.isEmpty(mobilePhone)) {
				session.setAttribute("jsons", jsons);
				request.getRequestDispatcher("ecartoon-weixin/saveMobile.jsp").forward(request, response);
				return;
			}

			final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			Order order = new Order();
			String subject = "";
			int days = 0;
			Date startTime = new Date();
			if (session.getAttribute("jsons") != null) {
				jsons = (String) session.getAttribute("jsons");
				session.setAttribute("jsons", null);
			}

			final JSONArray objs = JSONArray.fromObject(URLDecoder.decode(jsons, "UTF-8"));
			JSONObject retobj = objs.getJSONObject(0);
			final Double payServiceCostE = getMessage("pay.service.cost.e") == null
					|| "".equals(getMessage("pay.service.cost.e") == null) ? 0d
							: new Double(Long.parseLong(getMessage("pay.service.cost.e")) / 100);
			final Double payServiceCostM = getMessage("pay.service.cost.m") == null
					|| "".equals(getMessage("pay.service.cost.m") == null) ? 0d
							: new Double(Long.parseLong(getMessage("pay.service.cost.e")) / 100);
			final BaseMember mu = (BaseMember) request.getSession().getAttribute("member");
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
				if (obj.containsKey("startTime") && StringUtils.isNotEmpty(obj.getString("startTime"))) {
					startTime = ymd.parse(obj.getString("startTime"));
					order.setOrderStartTime(startTime);
				}
				if (obj.getString("productType").equals("1")) {
					order = new ProductOrder();
					final Product product = (Product) service.load(Product.class, pid);
					productName = product.getName();
					price = product.getCost();
					((ProductOrder) order).setProduct(product);
					((ProductOrder) order).setReportDay(obj.containsKey("reportDay") ? obj.getInt("reportDay") : null);
					((ProductOrder) order).setBalanceTimes(0);
					((ProductOrder) order).setIsBreach("1");
					((ProductOrder) order).setIsDelay("0");
					if (obj.containsKey("ticketId"))
						order.setTicketId(obj.getLong("ticketId"));
					order.setPayNo(service.getKeyNo("", "CARDCOL_ORDER_NO", 28));
					order.setNo(service.getKeyNo("", "CARDCOL_ORDER_NO", 14));

					EasyUtils.orderType = "TB_PRODUCT_ORDER";
					EasyUtils.orderNo = order.getNo();
				} else if (obj.getString("productType").equals("2")) {
					order = new ActiveOrder();
					final Active at = (Active) service.load(Active.class, pid);
					productName = at.getName();
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
					order.setOrderStartTime(startTime);
					if (at.getDays() != null) {
						days = at.getDays() - 1;
					}

					EasyUtils.orderType = "tb_active_order";
					EasyUtils.orderNo = order.getNo();
				} else if (obj.getString("productType").equals("3")) {
					order = new PlanOrder();
					final PlanRelease plan = (PlanRelease) service.load(PlanRelease.class, pid);
					productName = plan.getPlanName();
					price = plan.getUnitPrice();
					if (price == 0d) {
						((PlanOrder) order).setPlanRelease(plan);
						order.setMember(m);
						order.setOrderDate(new Date());
						order.setOrderStartTime(startTime);
						order.setOrderBalanceTime(startTime);
						order.setOrderEndTime(
								obj.containsKey("endTime") ? ymd.parse(obj.getString("endTime")) : new Date());
						service.genPlanData(order, plan);
						continue;
					}
					((PlanOrder) order).setPlanRelease(plan);
					((PlanOrder) order).setReportDay(obj.containsKey("reportDay") ? obj.getInt("reportDay") : null);
					((PlanOrder) order).setBalanceTimes(0);
					((PlanOrder) order).setIsBreach("1");
					((PlanOrder) order).setIsDelay("0");
					// service.genPlanData(order, plan);
					subject = plan.getPlanName();
					if (obj.containsKey("ticketId"))
						order.setTicketId(obj.getLong("ticketId"));
					order.setPayNo(service.getKeyNo("", "TB_PLAN_ORDER", 28));
					order.setNo(service.getKeyNo("", "TB_PLAN_ORDER", 14));

					EasyUtils.orderType = "TB_PlanRelease_ORDER";
					EasyUtils.orderNo = order.getNo();
				} else if (obj.getString("productType").equals("4")) {
					order = new FactoryOrder();
					final FactoryCosts fc = (FactoryCosts) service.load(FactoryCosts.class, pid);
					productName = fc.getName();
					price = StringUtils.isBlank(fc.getCosts1()) ? 0d : Double.parseDouble(fc.getCosts1());
					subject = fc.getName();
					((FactoryOrder) order).setFactoryCosts(fc);
					if (obj.containsKey("ticketId"))
						order.setTicketId(obj.getLong("ticketId"));
					order.setPayNo(service.getKeyNo("", "TB_FACTORY_ORDER", 28));
					order.setNo(service.getKeyNo("", "TB_FACTORY_ORDER", 14));

					EasyUtils.orderType = "TB_FACTORY_Order";
					EasyUtils.orderNo = order.getNo();
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
							last, first, obj.getLong("product"));
					/*
					 * if (list.size() != 0) { throw new
					 * LogicException("您这个月无法再购买课程！"); }
					 */
					final Course couse = (Course) service.load(Course.class, pid);
					productName = couse.getCourseInfo() == null ? "未知商品" : couse.getCourseInfo().getName();
					// price = couse.getMemberPrice();
					// 2017-7-10 黄文俊 结算的时候是按照非会员价结算的
					price = couse.getHourPrice() == null ? 0.00 : couse.getHourPrice();
					order = new CourseOrder();
					startTime = new Date(couse.getPlanDate().replace("-", "/"));
					((CourseOrder) order).setCourse(new Course(pid));
					((CourseOrder) order).setReportDay(obj.containsKey("reportDay") ? obj.getInt("reportDay") : null);
					((CourseOrder) order).setBalanceTimes(0);
					((CourseOrder) order).setIsBreach("1");
					((CourseOrder) order).setIsDelay("0");
					((CourseOrder) order).setOrderStartTime(startTime);
					subject = couse.getCourseInfo().getName();
					if (obj.containsKey("ticketId"))
						order.setTicketId(obj.getLong("ticketId"));
					order.setPayNo(service.getKeyNo("", "TB_COURSE_ORDER", 28));
					order.setNo(service.getKeyNo("", "TB_COURSE_ORDER", 14));

					EasyUtils.orderType = "TB_CourseRelease_ORDER";
					EasyUtils.orderNo = order.getNo();
				} else if (obj.getString("productType").equals("6")) {
					order = new GoodsOrder();
					final Goods goods = (Goods) service.load(Goods.class, pid);
					productName = goods.getName();
					price = goods.getPrice();
					subject = goods.getName();
					((GoodsOrder) order).setGoods(goods);
					if (obj.containsKey("ticketId"))
						order.setTicketId(obj.getLong("ticketId"));
					order.setPayNo(service.getKeyNo("", "TB_GOODS_ORDER", 28));
					order.setNo(service.getKeyNo("", "TB_GOODS_ORDER", 14));
					// 手环 需要有送货地址

					EasyUtils.orderType = "tb_goods_order";
					EasyUtils.orderNo = order.getNo();
				} else if (obj.getString("productType").equals("7")) {
					order = new GoodsOrder();
					// 给订单添加送货地址 by dou 2017-08-18
					int alwaysAddr = (int) obj.get("addressId");
					if (alwaysAddr > 0) {
						long addId = Long.valueOf(alwaysAddr);
						AlwaysAddr address = (AlwaysAddr) service.load(AlwaysAddr.class, addId);
						order.setAlwaysAddr(address);
					} else {
						System.err.println("请先选择收货地址！");
					}
					final Goods goods = (Goods) service.load(Goods.class, pid);
					productName = goods.getName();
					price = goods.getPrice();
					subject = goods.getName();
					((GoodsOrder) order).setGoods(goods);
					if (obj.containsKey("ticketId"))
						order.setTicketId(obj.getLong("ticketId"));
					order.setPayNo(service.getKeyNo("", "TB_GOODS_ORDER", 28));
					order.setNo(service.getKeyNo("", "TB_GOODS_ORDER", 14));

					EasyUtils.orderType = "tb_goods_order";
					EasyUtils.orderNo = order.getNo();
				} else if (obj.getString("productType").equals("8")) {
					order = new ProductOrder45();
					final Product45 prod = (Product45) service.load(Product45.class, pid);
					productName = prod.getName();
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

					if (dateTime != 0) {
						Date newDate = EasyUtils.addDate(startTime, dateTime);
						order.setOrderEndTime(newDate);
					} else {
						order.setOrderEndTime(startTime);
					}

					EasyUtils.orderType = "TB_PRODUCT_ORDER_V45";
					EasyUtils.orderNo = order.getNo();
				}
				order.setTicketAmount(ticketMoney);
				order.setOrderDate(new Date());
				order.setMember(m);
				order.setUnitPrice(price);
				order.setCount(quan);
				order.setContractMoney(price * quan);
				order.setOrderMoney(price * quan);
				/*
				 * if ((price * quan - ticketMoney) > 0) {
				 * order.setOrderMoney(price * quan - ticketMoney);
				 * order.setStatus('0'); } else { order.setOrderMoney(0.00); if
				 * (obj.getString("productType").equals("2") ||
				 * obj.getString("productType").equals("8")) {
				 * order.setStatus('1'); } else if
				 * (obj.getString("productType").equals("6") ||
				 * obj.getString("productType").equals("5")) {
				 * order.setStatus('3'); } }
				 */
				if (obj.getString("productType").equals("4")) {
					order.setOrderStartTime(startTime);
					order.setOrderBalanceTime(startTime);
					order.setOrderEndTime(
							obj.containsKey("endTime") ? sdf.parse(obj.getString("endTime")) : new Date());
				} else {
					order.setOrderStartTime(startTime);
					order.setOrderBalanceTime(startTime);

					if (order.getOrderEndTime() == null) {
						order.setOrderEndTime(
								obj.containsKey("endTime") ? ymd.parse(obj.getString("endTime")) : new Date());
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

				if (obj.getString("productType").equals("2")) {
					order.setOrderEndTime(EasyUtils.addDate(order.getOrderStartTime(), days));
				}
				order.setPayServiceCostE(payServiceCostE);
				order.setPayServiceCostM(payServiceCostM);
				order.setStatus('0');
				// E卡通订单
				order.setOrigin('E');

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
			if (order.getOrderMoney() >= 0) {
				/*
				 * String Ptype = order.getPayType(); if (Ptype == null ||
				 * "".equals(Ptype)) { Ptype = "1"; } String orderSign =
				 * PayUtils.orderSign(request, response, order, Ptype, subject);
				 * Product45 prod = (Product45) service.load(Product45.class,
				 * retobj.getLong("product"));
				 */
				Member member = (Member) request.getSession().getAttribute("member");
				obj.accumulate("success", true).accumulate("orderId", order.getId())
						.accumulate("orderNo", order.getNo()).accumulate("payNo", order.getPayNo())
						.accumulate("orderDate", sdf.format(order.getOrderDate()))
						.accumulate("contractMoney", order.getContractMoney())
						.accumulate("orderMoney", order.getOrderMoney()).accumulate("prod_name", productName)
						.accumulate("mobile", member.getMobilephone()).accumulate("signData", "")
						.accumulate("code", "1").accumulate("orderType", retobj.getString("productType"));
			} else {
				if (retobj.getString("productType").equals("6")) {
					GoodsBalanceImpl bal = new GoodsBalanceImpl();
					service.saveOrUpdate(bal.execute(order));
					service.updateOrderStatuse(order.getNo());
				} else if (retobj.getString("productType").equals("5")) {
					CourseBalanceImpl bal = new CourseBalanceImpl();
					service.saveOrUpdate(bal.execute(order));
				}
				if (order.getTicketId() != null) {
					MemberTicket mt = (MemberTicket) service.load(MemberTicket.class, order.getTicketId());
					mt.setStatus(2);
					service.saveOrUpdate(mt);
				}
				System.out.println(retobj.getString("productType"));
				service.ordersms(order, retobj.getString("productType"));
				obj.accumulate("success", true).accumulate("code", "2");
			}
			session.setAttribute("payMain", obj);
			response.sendRedirect("ecartoon-weixin/submit_order.jsp");
			// response(obj);
		} catch (Exception e) {
			log.error("error", e);
			// response(e);
		}
	}

	
	
	
	/**
	 * 修改goodsOrder订单结算，计划
	 */
	@SuppressWarnings("unused")
	public void updateDou () {
		try {
			GoodsBalanceImpl bal = new GoodsBalanceImpl();
			Order order = (Order) service.load(GoodsOrder.class,Long.valueOf("410"));
			service.updateDou(order);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	
	
	
	/**
	 * 修改订单状态
	 */
	public void updateOrderStatus() {
		String updateSql = null;
		// 支付成功修改,订单表中的订单状态

		String payTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(System.currentTimeMillis());
		updateSql = "update " + EasyUtils.orderType + " set status = 1, pay_time = '" + payTime + "' where no="
				+ EasyUtils.orderNo;
		DataBaseConnection.updateData(updateSql, null);

		// 如果是分享订单,需要返现,在结算数据表中添加一条数据
		if (EasyUtils.shareOrder != null) {

			// 生成返现红包
			updateSql = "insert into tb_order_balance_v45(id,BALANCE_TIME,ORDER_MONEY,BALANCE_MONEY,BALANCE_SERVICE,ORDER_NO,PROD_NAME,BALANCE_TYPE,BALANCE_FROM,BALANCE_TO,ORDER_ID)"
					+ " values(null,?,?,?,?,?,?,?,?,?,?)";

			String orderId = DataBaseConnection
					.getOne("select id from " + EasyUtils.orderType + " where no =" + EasyUtils.orderNo, null).get("id")
					.toString();

			Object[] args = { new Date(), EasyUtils.shareOrder.get("backMoney"), EasyUtils.shareOrder.get("backMoney"),
					0, "55" + EasyUtils.getRandomName(4), "红包", 1, 1, EasyUtils.shareOrder.get("toMember"), orderId };
			DataBaseConnection.updateData(updateSql, args);

			// 添加返现提醒记录
			updateSql = "insert into tb_back_money(id,from_member,to_member,back_money,time,status) values(null,?,?,?,?,?)";
			args = new Object[] { EasyUtils.shareOrder.get("fromMember"), EasyUtils.shareOrder.get("toMember"),
					EasyUtils.shareOrder.get("backMoney"), new Date(), 0 };
			DataBaseConnection.updateData(updateSql, args);

			// 重置常量
			EasyUtils.shareOrder = null;
		}

		// 如果需要response返回
		if ("1".equals(request.getParameter("ajax"))) {
			response(new JSONObject().accumulate("success", true));
		}
	}

	/**
	 * 创建E卡通活动订单
	 */
	public void createActiveProductOrder45() {
		JSONObject result = new JSONObject();
		try {
			Member member = (Member) session.getAttribute("member");
			if (request.getParameter("adwy_test") != null) {
				member = (Member) service.load(Member.class, Long.valueOf("12769"));
			}
			Product45 prod = (Product45) service.load(Product45.class, Long.valueOf(request.getParameter("productId")));
			// 生成订单
			ProductOrder45 order = new ProductOrder45();
			Date startTime = EasyUtils.formatStringToDate(EasyUtils.dateFormat(new Date(), "yyyy-MM-dd"));
			order.setProduct(prod);
			order.setOrderDate(new Date());
			order.setOrderBalanceTime(startTime);
			order.setOrderStartTime(startTime);
			// 计算结束时间 往 obj 中添加结束时间
			int periodUnit = (prod.getPeriodUnit() == "A" ? 30
					: prod.getPeriodUnit() == "B" ? 3 * 30 : prod.getPeriodUnit() == "C" ? 12 * 30 : 1);
			long dateTime = (long) (prod.getPeriod() * periodUnit) - 1;
			if (dateTime != 0) {
				Date newDate = EasyUtils.addDate(startTime, dateTime);
				order.setOrderEndTime(newDate);
			} else {
				order.setOrderEndTime(startTime);
			}
			// 设置订单号,商品金额,实付金额,来源,状态,所属用户
			order.setNo("22" + EasyUtils.getRandomName(4));
			order.setContractMoney(prod.getPrice());
			order.setOrderMoney(prod.getPrice());
			order.setOrigin('E');
			order.setStatus('0');
			order.setMember(member);
			order = (ProductOrder45) service.saveOrUpdate(order);

			// 返回订单信息
			result.accumulate("orderId", order.getId()).accumulate("productName", prod.getName())
					.accumulate("orderMoney", order.getOrderMoney())
					.accumulate("currentDate", EasyUtils.dateFormat(new Date(), "yyyy-MM-dd"));
		} catch (Exception e) {
			result.accumulate("success", false).accumulate("message", e);
			e.printStackTrace();
		}
		response(result);
	}

	/**
	 * 改变E卡通活动订单的开卡日期
	 */
	public void changeActiveProductOrder45() {
		Long orderId = Long.valueOf(request.getParameter("orderId"));
		Date startTime = EasyUtils.formatStringToDate(request.getParameter("startTime"));
		ProductOrder45 productOrder45 = (ProductOrder45) service.load(ProductOrder45.class, orderId);
		productOrder45.setOrderStartTime(startTime);
		productOrder45 = (ProductOrder45) service.saveOrUpdate(productOrder45);
		response(new JSONObject().accumulate("success", true));
	}

	/**
	 * 查询E卡通活动订单的订单状态
	 */
	public void queryActiveProductOrder45() {
		Long orderId = Long.valueOf(request.getParameter("orderId"));
		ProductOrder45 productOrder45 = (ProductOrder45) service.load(ProductOrder45.class, orderId);
		String status = productOrder45 == null ? "0" : productOrder45.getStatus().toString();
		response(new JSONObject().accumulate("status", status));
	}
}
