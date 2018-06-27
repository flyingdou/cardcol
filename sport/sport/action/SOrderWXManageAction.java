package sport.action;

import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Timer;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.cardcol.web.balance.impl.CourseBalanceImpl;
import com.cardcol.web.balance.impl.GoodsBalanceImpl;
import com.cardcol.web.basic.Product45;
import com.cardcol.web.order.ProductOrder45;
import com.cardcol.web.utils.PayUtils;
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
import com.freegym.web.utils.SessionConstant;
import com.sanmen.web.core.bean.BaseMember;
import com.sanmen.web.core.common.LogicException;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import sport.util.loginCommons;

@Namespace("")
@InterceptorRefs({ @InterceptorRef("customStack") })
@Results({ @Result(name = "saveProductType", location = "/sport/orderinfo1.jsp"),
		@Result(name = "findOrder", location = "/sport/mine-dd-xx.jsp")

})
public class SOrderWXManageAction extends BasicJsonAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String productName;

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
			 JSONObject jsonObject = new JSONObject();
//			 jsonObject.accumulate("product", 2).accumulate("productType","8")
//			 .accumulate("quantity", 1)
//			 .accumulate("unitPrice", 500)
//			 .accumulate("addressId", 6)
//			 .accumulate("startTime", "2017-08-17")
//			 .accumulate("orderType", 2)
//			 .accumulate("payType", 1);
//			 JSONArray jsonArray = new JSONArray();
//			 jsonArray.add(jsonObject);
//			 String jsons = jsonArray.toString();

			// 测试使用
			Member member = (Member) service.load(Member.class, Long.parseLong("9388"));
			
			//正式使用
//			Member member = (Member) request.getSession().getAttribute("member");
			request.getSession().setAttribute("member", member);

			final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			Order order = new Order();
			String subject = "";
			final JSONArray objs = JSONArray.fromObject(URLDecoder.decode(jsons, "UTF-8"));
			JSONObject retobj = objs.getJSONObject(0);
			// 存储订单编号和订单类型
			loginCommons.orderNo = retobj.getString("product");
			loginCommons.orderType = Integer.parseInt(retobj.get("orderType").toString());

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
				if (obj.containsKey("startTime")) {
					order.setOrderStartTime(ymd.parse(obj.getString("startTime")));
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
				} else if (obj.getString("productType").equals("3")) {
					order = new PlanOrder();
					final PlanRelease plan = (PlanRelease) service.load(PlanRelease.class, pid);
					productName = plan.getPlanName();
					price = plan.getUnitPrice();
					if (price == 0d) {
						((PlanOrder) order).setPlanRelease(plan);
						order.setMember(m);
						order.setOrderDate(new Date());
						order.setOrderStartTime(
								obj.containsKey("startTime") ? ymd.parse(obj.getString("startTime")) : null);
						order.setOrderBalanceTime(
								obj.containsKey("startTime") ? ymd.parse(obj.getString("startTime")) : null);
						order.setOrderEndTime(obj.containsKey("endTime") ? ymd.parse(obj.getString("endTime")) : null);
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
					if (list.size() != 0) {
						throw new LogicException("您这个月无法再购买课程！");
					}
					final Course couse = (Course) service.load(Course.class, pid);
					productName = couse.getCoachName();
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
					productName = goods.getName();
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
				order.setOrigin('B');
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
				String Ptype = order.getPayType();
				if (Ptype == null || "".equals(Ptype)) {
					Ptype = "1";
				}
				String orderSign = PayUtils.orderSign(request, response, order, Ptype, subject);
				Product45 prod = (Product45) service.load(Product45.class, retobj.getLong("product"));
				// 临时修改
				member = (Member) request.getSession().getAttribute("member");
				member = (Member) service.load(Member.class, member.getId());
				obj.accumulate("success", true).accumulate("orderId", order.getId())
						.accumulate("memberId", member.getId()).accumulate("memberName", member.getName())
						.accumulate("orderNo", order.getNo()).accumulate("payNo", order.getPayNo())
						.accumulate("orderDate", sdf.format(order.getOrderDate()))
						.accumulate("contractMoney", order.getContractMoney())
						.accumulate("orderMoney", order.getOrderMoney()).accumulate("prod_name", productName)
						.accumulate("mobile", member.getMobilephone()).accumulate("signData", orderSign)
						.accumulate("code", "1");
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
				// System.out.println(retobj.getString("productType"));
				service.ordersms(order, retobj.getString("productType"));
				obj.accumulate("success", true).accumulate("code", "2");
			}
			request.setAttribute("payMain", obj);
			return "saveProductType";
		} catch (Exception e) {
			log.error("error", e);
			return null;
		}
	}

	// 时间相加的方法
	public static Date addDate(Date date, long day) {
		long time = date.getTime(); // 得到指定日期的毫秒数
		day = day * 24 * 60 * 60 * 1000; // 要加上的天数转换成毫秒数
		time += day; // 相加得到新的毫秒数
		return new Date(time); // 将毫秒数转换成日期
	}

	/**
	 * 订单查询
	 */
	@SuppressWarnings("unchecked")
	public String findOrder() {
		String sql = "select count(*) count from tb_plan_record where partake = ?";
		// 获取session中的member
		// 测试数据
//		Member member = (Member) service.load(Member.class, Long.parseLong("9565"));
		 Member member = (Member) request.getSession().getAttribute("member");

		if (member == null) {
			loginCommons.thirdType = "W";
			new loginCommons().setMember(request, service);
			member = (Member) request.getSession().getAttribute("member");
		} else {
			member = (Member) service.load(Member.class, member.getId());
			request.getSession().setAttribute("member", member);
		}
		Object[] obj1 = { member.getId() };

		long memberId = member.getId();
		// status = 0 未付款订单
		String status = "0";
		pageInfo = service.queryOrder(pageInfo, memberId, status);
		List<Map<String, Object>> orderList0 = pageInfo.getItems();

		JSONArray jsonArray0 = new JSONArray();
		JSONObject objx0 = null;
		if (orderList0.size() > 0) {
			for (Map<String, Object> map : orderList0) {
				objx0 = new JSONObject();
				objx0.accumulate("id", map.get("id")).accumulate("name", map.get("name"))
						.accumulate("no", map.get("no")).accumulate("orderStartTime", map.get("orderStartTime"))
						.accumulate("orderMoney", map.get("orderMoney")).accumulate("image", map.get("image"));
				jsonArray0.add(objx0);
			}
		}

		// status = 1 有效订单
		status = "1";
		pageInfo = service.queryOrder(pageInfo, memberId, status);
		List<Map<String, Object>> orderList1 = pageInfo.getItems();

		JSONArray jsonArray1 = new JSONArray();
		JSONObject objx1 = null;
		if (orderList1.size() > 0) {
			for (Map<String, Object> map : orderList1) {
				objx1 = new JSONObject();
				objx1.accumulate("id", map.get("id")).accumulate("name", map.get("name"))
						.accumulate("no", map.get("no")).accumulate("orderStartTime", map.get("orderStartTime"))
						.accumulate("orderMoney", map.get("orderMoney")).accumulate("image", map.get("image"));
				jsonArray1.add(objx1);
			}
		}

		// status = 2 已完成订单
		status = "2";
		pageInfo = service.queryOrder(pageInfo, memberId, status);
		List<Map<String, Object>> orderList2 = pageInfo.getItems();

		JSONArray jsonArray2 = new JSONArray();
		JSONObject objx2 = null;
		if (orderList2.size() > 0) {
			for (Map<String, Object> map : orderList2) {
				objx2 = new JSONObject();
				objx2.accumulate("id", map.get("id")).accumulate("name", map.get("name"))
						.accumulate("no", map.get("no")).accumulate("orderStartTime", map.get("orderStartTime"))
						.accumulate("orderMoney", map.get("orderMoney")).accumulate("image", map.get("image"));
				jsonArray2.add(objx2);
			}
		}

		// 用户个人信息

		final JSONObject obj = new JSONObject();
		obj.accumulate("success", true).accumulate("a", jsonArray0).accumulate("b", jsonArray1)
				.accumulate("c", jsonArray2).accumulate("id", member.getId()).accumulate("name", member.getName())
				.accumulate("image", member.getImage()).accumulate("city", member.getCity())
				.accumulate("county", member.getCounty())
				.accumulate("trainRecordCount", DataBaseConnection.getList(sql, obj1).get(0))
				.accumulate("pageInfo", getJsonForPageInfo());
		response(obj);
		request.setAttribute("orderLists", obj);
		return "findOrder";
	}
}
