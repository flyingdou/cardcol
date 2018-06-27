package com.freegym.web.mobile.action;

import java.io.File;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

import com.cardcol.web.balance.impl.GoodsBalanceImpl;
import com.cardcol.web.basic.Product45;
import com.cardcol.web.order.ProductOrder45;
import com.cardcol.web.utils.PayUtils;
import com.freegym.web.active.Active;
import com.freegym.web.basic.Member;
import com.freegym.web.basic.MemberTicket;
import com.freegym.web.config.FactoryCosts;
import com.freegym.web.course.Appraise;
import com.freegym.web.mobile.OrderJsonAction;
import com.freegym.web.order.ActiveOrder;
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
@InterceptorRefs({ @InterceptorRef("mobileStack") })
public class MOrderManageAction extends OrderJsonAction {

	private static final long serialVersionUID = -4900715126840183203L;

	private File image1, image2, image3;

	private String image1FileName, image2FileName, image3FileName;

	public File getImage1() {
		return image1;
	}

	public File getImage2() {
		return image2;
	}

	public File getImage3() {
		return image3;
	}

	public String getImage1FileName() {
		return image1FileName;
	}

	public String getImage2FileName() {
		return image2FileName;
	}

	public String getImage3FileName() {
		return image3FileName;
	}

	public void setImage1(File image1) {
		this.image1 = image1;
	}

	public void setImage2(File image2) {
		this.image2 = image2;
	}

	public void setImage3(File image3) {
		this.image3 = image3;
	}

	public void setImage1FileName(String image1FileName) {
		this.image1FileName = image1FileName;
	}

	public void setImage2FileName(String image2FileName) {
		this.image2FileName = image2FileName;
	}

	public void setImage3FileName(String image3FileName) {
		this.image3FileName = image3FileName;
	}

	@Override
	protected void list() {
		final Product p = (Product) service.load(Product.class, id);
		final String freeProjects = getFreeProjects(p.getFreeProjects());
		final String costProjects = getCostProjects(p.getCostProjects());
		final String useRanges = getUseRange(p.getUseRange());
		final JSONObject ret = new JSONObject(), pObj = new JSONObject();
		pObj.accumulate("id", p.getId()).accumulate("name", getString(p.getName()))
				.accumulate("type", getString(p.getType())).accumulate("proType", getString(p.getProType()))
				.accumulate("member", getJsonForMember(p.getMember()))
				.accumulate("startTime", p.getStartTime() == null ? "" : ymd.format(p.getStartTime()))
				.accumulate("endTime", p.getEndTime() == null ? "" : ymd.format(p.getEndTime()))
				.accumulate("num", getInteger(p.getNum())).accumulate("num1", getInteger(p.getNum1()))
				.accumulate("num2", getInteger(p.getNum2())).accumulate("wellNum", getInteger(p.getWellNum()))
				.accumulate("cardType", getString(p.getCardType())).accumulate("isReg", getString(p.getIsReg()))
				.accumulate("assignType", getString(p.getAssignType()).equals("") ? "2" : "1")
				.accumulate("assignCost", getDouble(p.getAssignCost()))
				.accumulate("dutyCost", getDouble(p.getDutyCost())).accumulate("overCost", getDouble(p.getOverCost()))
				.accumulate("cost", getDouble(p.getCost())).accumulate("cost1", getDouble(p.getCost1()))
				.accumulate("cost2", getDouble(p.getCost2())).accumulate("reportDay", getInteger(p.getReportDay()))
				.accumulate("breachCost", getDouble(p.getBreachCost()))
				.accumulate("promiseCost", getDouble(p.getPromiseCost()))
				.accumulate("delayDay", getInteger(p.getDelayDay()))
				.accumulate("promotionType", getString(p.getPromotionType()))
				.accumulate("useType", getString(p.getUseType())).accumulate("useRange", useRanges)
				.accumulate("freeProjects", freeProjects).accumulate("freeProject", getString(p.getFreeProject()))
				.accumulate("costProjects", costProjects).accumulate("costProject", getString(p.getCostProject()))
				.accumulate("talkFunc", getString(p.getTalkFunc())).accumulate("remark", getString(p.getRemark()))
				.accumulate("isClose", getString(p.getIsClose())).accumulate("audit", getString(p.getAudit()));
		ret.accumulate("success", true).accumulate("product", pObj);
		response(ret);
	}

	private String getFreeProjects(String freeProjects) {
		if (freeProjects == null || "".equals(freeProjects))
			return "无";
		final List<Map<String, Object>> maps = service
				.queryForList("select name from tb_course_info ci where ci.id in (" + freeProjects + ")");
		final StringBuffer sb = new StringBuffer("");
		for (final Map<String, Object> map : maps) {
			sb.append(sb.length() > 0 ? "," : "").append(map.get("name").toString());
		}
		return sb.toString();
	}

	private String getCostProjects(String costProjects) {
		if (costProjects == null || "".equals(costProjects))
			return "无";
		final List<Map<String, Object>> maps = service
				.queryForList("select name from tb_course_info ci where ci.id in (" + costProjects + ")");
		final StringBuffer sb = new StringBuffer("");
		for (final Map<String, Object> map : maps) {
			sb.append(sb.length() > 0 ? "," : "").append(map.get("name").toString());
		}
		return sb.toString();
	}

	private String getUseRange(String useRange) {
		if (useRange == null || "".equals(useRange))
			return "无";
		final List<Map<String, Object>> maps = service
				.queryForList("select name from tb_member ci where ci.id in (" + useRange + ")");
		final StringBuffer sb = new StringBuffer("");
		for (final Map<String, Object> map : maps) {
			sb.append(sb.length() > 0 ? "," : "").append(map.get("name").toString());
		}
		return sb.toString();
	}

	/**
	 * jsons = [{quantity: 1, unitPrice: 0.01, product: , startTime:
	 * '2013-11-11', endTime: '2013-12-10', reportDay: 30, payType: "2"}]
	 */
	@Override
	public String save() {
		try {
			final JSONArray objs = JSONArray.fromObject(URLDecoder.decode(jsons, "UTF-8"));
			final Double payServiceCostE = getMessage("pay.service.cost.e") == null
					|| "".equals(getMessage("pay.service.cost.e") == null) ? 0d
							: new Double(Long.parseLong(getMessage("pay.service.cost.e")) / 100);
			final Double payServiceCostM = getMessage("pay.service.cost.m") == null
					|| "".equals(getMessage("pay.service.cost.m") == null) ? 0d
							: new Double(Long.parseLong(getMessage("pay.service.cost.e")) / 100);
			ProductOrder order = null;
			final BaseMember mu = getMobileUser();
			final Member m = (Member) service.load(Member.class, mu.getId());
			for (final Iterator<?> it = objs.iterator(); it.hasNext();) {
				final JSONObject obj = (JSONObject) it.next();
				final int quan = obj.getInt("quantity");
				final double price = obj.getDouble("unitPrice");
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

				order = new ProductOrder();
				order.setNo(service.getKeyNo("PD", "CARDCOL_ORDER_NO", 14));
				order.setOrderDate(new Date());
				order.setMember(m);
				order.setProduct(new Product(pid));
				order.setUnitPrice(price);
				order.setCount(quan);
				order.setOrderMoney(price * quan);
				order.setOrigin('B');
				// order.setIntegral(shop.getIntegral());
				order.setStatus('0');
				order.setOrderStartTime(ymd.parse(obj.getString("startTime")));
				order.setOrderBalanceTime(ymd.parse(obj.getString("startTime")));
				order.setOrderEndTime(ymd.parse(obj.getString("endTime")));
				order.setReportDay(obj.getInt("reportDay"));
				order.setBalanceTimes(0);
				order.setIsBreach("1");
				order.setSurplusMoney(price * quan);
				order.setIsDelay("0");
				order.setShipType(obj.containsKey("shipType") ? obj.getString("shipType") : "1");
				order.setShipTimeType(obj.containsKey("shipTimeType") ? obj.getString("shipTimeType") : "1");
				if (obj.containsKey("payType"))
					order.setPayType(obj.getString("payType"));
				order.setPayNo(order.getNo());
				order.setPayServiceCostE(payServiceCostE);
				order.setPayServiceCostM(payServiceCostM);
				order = (ProductOrder) service.saveOrUpdate(order);
			}
			final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			response("{success: true, order: {id: " + order.getId() + ", no: '" + order.getNo() + "', orderDate: '"
					+ sdf.format(order.getOrderDate()) + "'}}");
		} catch (Exception e) {
			log.error("error", e);
			response(e);
		}
		return null;
	}

	/**
	 * 新增所有产品下订单接口（增加其它产品提交订单 活动订单、健身计划订单、场地订单、课程订单） jsons = [{product:
	 * ,productType: 1,quantity: 1, unitPrice: 0.01, startTime: '2013-11-11',
	 * endTime: '2013-12-10', reportDay: 30,payType:1}]
	 * 
	 */
	@SuppressWarnings("unused")
	public String saveProductType() {
		// jsons =
		// "[{\"product\":226,\"productType\":\"4\",\"quantity\":1,\"weight\":50,\"unitPrice\":0.01,\"startTime\":\'2017-05-24\',\"payType\":1}]";
		try {
			final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			Order order = new Order();
			String subject = "";
			int days = 0;
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
				long ticketId = 0l;
				if (obj.containsKey("ticketId")) {
					final MemberTicket mt = (MemberTicket) service.load(MemberTicket.class, obj.getLong("ticketId"));
					ticketId = mt.getId();
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
					((ProductOrder) order).setProduct(product);
					((ProductOrder) order).setReportDay(obj.containsKey("reportDay") ? obj.getInt("reportDay") : null);
					((ProductOrder) order).setBalanceTimes(0);
					((ProductOrder) order).setIsBreach("1");
					((ProductOrder) order).setIsDelay("0");
					if (obj.containsKey("ticketId"))
						order.setTicketId(obj.getLong("ticketId"));
					order.setPayNo(service.getKeyNo("", "CARDCOL_ORDER_NO", 28));
					order.setNo(service.getKeyNo("", "CARDCOL_ORDER_NO", 14));
					order.setOrderEndTime(new Date(
							order.getOrderStartTime().getTime() + (product.getWellNum() * 7 * 24 * 60 * 60 * 1000)));
				} else if (obj.getString("productType").equals("2")) {
					order = new ActiveOrder();
					final Active at = (Active) service.load(Active.class, pid);
					price = at.getAmerceMoney();
					if (obj.containsKey("startTime")) {
						order.setOrderStartTime(ymd.parse(obj.getString("startTime")));
					}
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
					if (at.getDays() != null) {
						days = at.getDays() - 1;
					}
				} else if (obj.getString("productType").equals("3")) {
					order = new PlanOrder();
					final PlanRelease plan = (PlanRelease) service.load(PlanRelease.class, pid);
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
					/*
					 * if (list.size() != 0) { throw new
					 * LogicException("您这个月无法再购买课程！"); }
					 */
					final Course couse = (Course) service.load(Course.class, pid);
					// price = couse.getMemberPrice();
					// 2017-7-10 黄文俊 结算的时候是按照非会员价结算的
					price = couse.getHourPrice() == null ? 0 : couse.getHourPrice();
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
					// 手环
				} else if (obj.getString("productType").equals("7")) {
					order = new GoodsOrder();
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
					price = prod.getPrice() == null ? 0 : prod.getPrice();
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
					order.setOrderMoney(Double.valueOf(EasyUtils.decimalFormat(price * quan - ticketMoney)));
					order.setStatus('0');
					order.setTicketId(ticketId);
					String updateSql = "update tb_member_ticket set status = 2 where id = " + ticketId;
					service.executeUpdate(updateSql);
				} else {
					order.setOrderMoney(0.00);
					if (obj.getString("productType").equals("2") || obj.getString("productType").equals("8")) {
						order.setStatus('1');
					} else if (obj.getString("productType").equals("5")) {
						order.setStatus('1');
					} else if (obj.getString("productType").equals("6")) {
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
				order.setOrigin('E');

				if (obj.getString("productType").equals("1")) {
					order = (ProductOrder) service.saveOrUpdate(order);
				} else if (obj.getString("productType").equals("2")) {
					order.setOrderEndTime(addDate(order.getOrderStartTime(), days));
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
				String orderSign = PayUtils.orderSign(request, response, order, order.getPayType(), subject);
				obj.accumulate("success", true)
						.accumulate("order",
								new JSONObject().accumulate("id", order.getId()).accumulate("no", order.getNo())
										.accumulate("payNo", order.getPayNo())
										.accumulate("orderDate", sdf.format(order.getOrderDate())))
						.accumulate("signData", orderSign).accumulate("code", "1");
			} else {
				if (retobj.getString("productType").equals("6")) {
					GoodsBalanceImpl bal = (GoodsBalanceImpl) new GoodsBalanceImpl();
					service.saveOrUpdate(bal.execute(order));
					service.updateOrderStatuse(order.getNo());
				} /*
					 * else if (retobj.getString("productType").equals("5")) {
					 * CourseBalanceImpl bal = (CourseBalanceImpl) new
					 * CourseBalanceImpl();
					 * service.saveOrUpdate(bal.execute(order)); }
					 */
				if (order.getTicketId() != null) {
					MemberTicket mt = (MemberTicket) service.load(MemberTicket.class, order.getTicketId());
					mt.setStatus(2);
					service.saveOrUpdate(mt);
				}
				System.out.println(retobj.getString("productType"));
				service.ordersms(order, retobj.getString("productType"));
				obj.accumulate("success", true).accumulate("code", "2");
			}
			response(obj);
		} catch (Exception e) {
			log.error("error", e);
			response(e);
		}
		return null;
	}

	@Override
	protected String getExclude() {
		return "";
	}

	/**
	 * 订单管理
	 */
	public void orderList() {
		try {
			final BaseMember mu = getMobileUser();
			final Member m = (Member) service.load(Member.class, mu.getId());

			final JSONObject obj = new JSONObject();

			final StringBuffer sql = new StringBuffer();
			String orderSql = Order.getOrderSql();
			sql.append("SELECT * FROM (").append(orderSql)
					.append(") t where ( status = ? or (status = ? and (isappraise <> ? or  isappraise is null) ))  ");
			final List<Object> params = new ArrayList<Object>();
			params.add("0");
			params.add("1");
			params.add("1");
			sql.append("and fromId = ? ");
			params.add(m.getId());
			sql.append("order by orderDate desc");
			pageInfo = service.findPageBySql(sql.toString(), pageInfo, params.toArray());

			final JSONArray items = new JSONArray();
			for (Iterator<?> it = pageInfo.getItems().iterator(); it.hasNext();) {
				Map<?, ?> map = (Map<?, ?>) it.next();
				JSONObject jobj = new JSONObject();

				jobj.accumulate("id", map.get("id")).accumulate("no", map.get("no")).accumulate("name", map.get("name"))
						.accumulate("status", map.get("status")).accumulate("isappraise", map.get("isappraise"))
						.accumulate("productId", map.get("productId")).accumulate("orderType", map.get("type"))
						.accumulate("toId", map.get("toId")).accumulate("toName", map.get("toName"))
						.accumulate("image", map.get("image")).accumulate("orderMoney", map.get("orderMoney"))
						.accumulate("appraise", getGradeJson(getLong(map.get("productId")), getString(map.get("type"))))
						.accumulate("payNo", map.get("payNo"));

				items.add(jobj);
			}
			obj.accumulate("success", true).accumulate("currentPage", pageInfo.getCurrentPage())
					.accumulate("totalPage", pageInfo.getTotalPage()).accumulate("items", items);
			response(obj);

		} catch (Exception e) {
			log.error("error", e);
			response(e);
		}

	}

	/**
	 * 重新写支付接口， 增加其它商品支付
	 */
	// public void paymentProductType() {
	// try {
	// String payType = request.getParameter("payType");
	// String payNo = request.getParameter("payNo");
	//
	// final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	//
	// // 根据payNo查询需要支付订单信息
	// final StringBuffer sql = new StringBuffer();
	// String orderSql = Order.getOrderSql();
	// sql.append("SELECT * FROM (").append(orderSql).append(") t where payNo =
	// ? ");
	// final List<Object> params = new ArrayList<Object>();
	// params.add(payNo);
	//
	// List<Map<String, Object>> queryForList =
	// service.queryForList(sql.toString(), params.toArray());
	//
	// StringBuffer sb = new StringBuffer();
	// // Date orderStartTime = null;
	// Date OrderDate = null;
	// BigDecimal total_fee = new BigDecimal(0.0);
	// Long id = null;
	//
	// for (Iterator<?> it = queryForList.iterator(); it.hasNext();) {
	// Map<?, ?> map = (Map<?, ?>) it.next();
	// BigDecimal ticketAmount = map.get("ticket_amount") == null ? new
	// BigDecimal(0) : new BigDecimal(map.get("ticket_amount").toString());
	// BigDecimal money = new
	// BigDecimal(map.get("orderMoney").toString()).subtract(ticketAmount);
	// total_fee = total_fee.add(money);
	// sb.append(sb.length() > 0 ? "," :
	// "").append(map.get("name")).append("(").append(WebConstant.getType((String)
	// map.get("type"))).append(")");
	// id = Long.parseLong(((String) map.get("id")).substring(2));
	// OrderDate = (Date) map.get("OrderDate");
	// service.updateOrderPayType(payNo, payType);
	// }
	// // total_fee=new BigDecimal("0.01");
	// total_fee = total_fee.setScale(2);
	//
	// final JSONObject obj = new JSONObject(), orderJson = new JSONObject();
	// if (payType.equals("1")) {
	//
	// final String subject = sb.toString();
	// final String body = sdf.format(OrderDate) + "开始的" + sb.toString();
	// final StringBuffer makeParms = new StringBuffer();
	// makeParms.append("partner=\"").append(AlipayConfig.partner).append("\"&");
	// makeParms.append("seller_id=\"").append(AlipayConfig.seller_email).append("\"&");
	// makeParms.append("out_trade_no=\"").append(payNo.toString()).append("\"&");
	// makeParms.append("subject=\"").append(subject).append("\"&");
	// makeParms.append("body=\"").append(body).append("\"&");
	// makeParms.append("total_fee=\"").append(total_fee.toString()).append("\"&");
	// makeParms.append("notify_url=\"").append(URLEncoder.encode(AlipayConfig.notify_url)).append("\"&");
	// makeParms.append("return_url=\"").append(URLEncoder.encode(AlipayConfig.return_url)).append("\"&");
	// makeParms.append("service=\"").append("mobile.securitypay.pay").append("\"&");
	// makeParms.append("payment_type=\"").append("1").append("\"&");
	// makeParms.append("_input_charset=\"").append("utf-8").append("\"&");
	// makeParms.append("it_b_pay=\"30m\"&").append("param=\"product\"");
	// final String sign = URLEncoder.encode(Rsa.sign(makeParms.toString(),
	// AlipayConfig.private_key));
	// makeParms.append("&sign=\"").append(sign).append("\"&");
	// makeParms.append("sign_type=\"").append("RSA").append("\"");
	// log.error(makeParms.toString());
	// boolean doCheck = Rsa.doCheck(makeParms.toString(),
	// URLDecoder.decode(sign), AlipayConfig.ali_public_key);
	// log.error("签名验证：" + doCheck);
	// obj.accumulate("success", true).accumulate("sign",
	// sign).accumulate("info", makeParms.toString());
	// response(obj);
	// } else if (payType.equals("0")) {
	// final ChinapayUtils pay = new ChinapayUtils(this, queryForList);
	// final String sign = pay.order();
	// orderJson.accumulate("id", id).accumulate("no",
	// payNo.toString()).accumulate("orderDate",
	// sdf.format(OrderDate)).accumulate("orderSign", sign);
	// obj.accumulate("success", true).accumulate("order", orderJson);
	// response(obj);
	// } else if (payType.equals("2")) {
	// // 新加入微信支付跳转
	// // 网页授权后获取传递的参数
	// String money = total_fee.toString();
	// // 金额转化为分为单位
	// float sessionmoney = Float.parseFloat(money);
	// String finalmoney = String.format("%.2f", sessionmoney);
	// finalmoney = finalmoney.replace(".", "");
	// finalmoney = Integer.parseInt(finalmoney) + "";
	// // 商户相关资料
	// String appid = "wxb6e4cf82d2d3d874";
	// String appsecret = "d37f94314058f38024610b0047648ec2";
	// String partner = "1325365201";
	// String partnerkey = "cArdCol1949com1010jsZGNessfit123";
	//
	// // 获取openId后调用统一支付接口https://api.mch.weixin.qq.com/pay/unifiedorder
	// // 商户号partner;
	// // 随机数
	// String nonce_str = Sha1Util.getNonceStr();
	// String nonce_str1 = nonce_str;
	// String timestamp = Sha1Util.getTimeStamp();
	// // 商品描述根据情况修改
	// String body = "卡库健身";
	// // 商户订单号
	// String out_trade_no = payNo;
	// String spbill_create_ip = "192.168.1.1";
	// // 这里notify_url是 支付完成后微信发给该链接信息，可以判断会员是否支付成功，改变订单状态等。
	// String notify_url = "http://www.cardcol.com/cardcolv3/mwpaynotify.asp";
	// String trade_type = "APP";
	//
	// SortedMap<String, String> packageParams = new TreeMap<String, String>();
	// packageParams.put("appid", appid);
	// packageParams.put("mch_id", partner);
	// packageParams.put("nonce_str", nonce_str);
	// packageParams.put("body", body);
	// packageParams.put("key", partnerkey);
	// packageParams.put("out_trade_no", out_trade_no);
	// packageParams.put("notify_url", notify_url);
	// packageParams.put("spbill_create_ip", spbill_create_ip);
	// packageParams.put("trade_type", trade_type);
	// // packageParams.put("signType", "MD5");
	// // 金额为
	// packageParams.put("total_fee", finalmoney);
	// RequestHandler reqHandler = new RequestHandler(request, response);
	// reqHandler.init(appid, appsecret, partnerkey);
	// String sign = reqHandler.createSign(packageParams);
	// String xml = "<xml>" + "<sign>" + sign + "</sign>" + "<appid>" + appid +
	// "</appid>" + "<body>" + body + "</body>" + "<mch_id>" + partner +
	// "</mch_id>"
	// + "<nonce_str>" + nonce_str + "</nonce_str>" + "<notify_url>" +
	// notify_url + "</notify_url>" + "<out_trade_no>" + out_trade_no +
	// "</out_trade_no>"
	// + "<spbill_create_ip>" + spbill_create_ip + "</spbill_create_ip>" +
	// "<total_fee>" + finalmoney + "</total_fee>" + "<trade_type>" + trade_type
	// + "</trade_type>" + "</xml>";
	// String createOrderURL = "https://api.mch.weixin.qq.com/pay/unifiedorder";
	// String prepay_id = "";
	// try {
	// String result = new GetWxOrderno().getPayNo(createOrderURL, xml);
	// if (result.equals("")) {
	// request.setAttribute("ErrorMsg", "统一支付接口获取预支付订单出错");
	// response.sendRedirect("/weChatpay/error.jsp");
	// }
	// prepay_id = result.split(",")[0];
	// nonce_str = result.split(",")[1];
	// } catch (Exception e1) {
	// e1.printStackTrace();
	// }
	// SortedMap<String, String> finalMap = new TreeMap<String, String>();
	// finalMap.put("appid", appid);
	// finalMap.put("partnerid", partner);
	// finalMap.put("prepayid", prepay_id);
	// finalMap.put("noncestr", nonce_str);
	// finalMap.put("timestamp", timestamp);
	// finalMap.put("package", "Sign=WXPay");
	// String finalsignTest = reqHandler.createSign(finalMap);
	// // finalMap.put("signType", "MD5");
	// String finalsign = reqHandler.createSign(finalMap);
	// System.out.println("finalsign3:" + finalsign);
	//
	// obj.accumulate("success", true).accumulate("appid",
	// appid).accumulate("prepay_id", prepay_id).accumulate("partner",
	// partner).accumulate("timeStamp", timestamp)
	// .accumulate("nonceStr", nonce_str).accumulate("nonceStr1",
	// nonce_str1).accumulate("package", "Sign=WXPay").accumulate("sign",
	// finalsign);
	// // service.updateOrderStatus(out_trade_no);
	// response(obj);
	// }
	// } catch (Exception e) {
	// log.error("error", e);
	// response(e);
	// }
	// }

	/**
	 * 订单评价
	 */
	public void orderAppraise() {
		try {

			Appraise ar = new Appraise();
			Order order = null;
			final JSONObject objs = JSONObject.fromObject(URLDecoder.decode(jsons, "UTF-8"));
			final BaseMember mu = getMobileUser();
			Long l = Long.parseLong(objs.get("id").toString().substring(2));
			ar.setOrderId(l);
			ar.setOrderType(objs.getString("orderType"));
			ar.setTitle(objs.getString("title"));
			ar.setContent(objs.getString("content"));
			ar.setGrade(objs.getDouble("grade"));
			ar.setMember((Member) service.load(Member.class, mu.getId()));
			ar.setMemberTo((Member) service.load(Member.class, objs.getLong("toId")));
			ar.setAppDate(new Date());
			ar.setIsRead("0");
			ar.setIsNApp("0");

			String fileName1 = image1 != null ? saveFile("picture", image1, image1FileName, null) : null;
			String fileName2 = image2 != null ? saveFile("picture", image2, image2FileName, null) : null;
			String fileName3 = image3 != null ? saveFile("picture", image3, image3FileName, null) : null;
			if (fileName1 != null) {
				ar.setImage1(fileName1);
			}
			if (fileName2 != null) {
				ar.setImage2(fileName2);
			}
			if (fileName3 != null) {
				ar.setImage3(fileName3);
			}

			service.saveOrUpdateAppraise(ar);
			// productType 1:健身卡订单 2:活动订单 3:健身计划订单 4:场地订单 5:课程订单 6:智能计划订单
			if (objs.getString("orderType").equals("1")) {
				order = (ProductOrder) service.load(ProductOrder.class, l);
				order.setIsappraise("1");
				order = (ProductOrder) service.saveOrUpdate(order);
			} else if (objs.getString("orderType").equals("2")) {
				order = (ActiveOrder) service.load(ActiveOrder.class, l);
				order.setIsappraise("1");
				order = (ActiveOrder) service.saveOrUpdate(order);
			} else if (objs.getString("orderType").equals("3")) {
				order = (PlanOrder) service.load(PlanOrder.class, l);
				order.setIsappraise("1");
				order = (PlanOrder) service.saveOrUpdate(order);
			} else if (objs.getString("orderType").equals("4")) {
				order = (FactoryOrder) service.load(FactoryOrder.class, l);
				order.setIsappraise("1");
				order = (FactoryOrder) service.saveOrUpdate(order);
			} else if (objs.getString("orderType").equals("5")) {
				order = (CourseOrder) service.load(CourseOrder.class, l);
				order.setIsappraise("1");
				order = (CourseOrder) service.saveOrUpdate(order);
			} else if (objs.getString("orderType").equals("6")) {
				order = (GoodsOrder) service.load(GoodsOrder.class, l);
				order.setIsappraise("1");
				order = (GoodsOrder) service.saveOrUpdate(order);
			}
			final JSONObject obj = new JSONObject();
			obj.accumulate("success", true);
			response(obj);

		} catch (Exception e) {
			log.error("error", e);
			response(e);
		}

	}

	/**
	 * 订单地址
	 */
	public void getOrderAddress() {
		try {

			Appraise ar = new Appraise();
			Order order = null;
			final JSONObject objs = JSONObject.fromObject(URLDecoder.decode(jsons, "UTF-8"));
			final BaseMember mu = getMobileUser();
			ar.setOrderId(objs.getLong("id"));
			ar.setOrderType(objs.getString("orderType"));
			ar.setTitle(objs.getString("title"));
			ar.setContent(objs.getString("content"));
			ar.setGrade(objs.getDouble("grade"));
			ar.setMember((Member) service.load(Member.class, mu.getId()));
			ar.setMemberTo((Member) service.load(Member.class, objs.getLong("toId")));
			ar.setAppDate(new Date());
			ar.setIsRead("0");
			ar.setIsNApp("0");

			String fileName1 = image1 != null ? saveFile("picture", image1, image1FileName, null) : null;
			String fileName2 = image2 != null ? saveFile("picture", image2, image2FileName, null) : null;
			String fileName3 = image3 != null ? saveFile("picture", image3, image3FileName, null) : null;
			if (fileName1 != null) {
				ar.setImage1(fileName1);
			}
			if (fileName2 != null) {
				ar.setImage2(fileName2);
			}
			if (fileName3 != null) {
				ar.setImage3(fileName3);
			}

			service.saveOrUpdateAppraise(ar);
			// productType 1:健身卡订单 2:活动订单 3:健身计划订单 4:场地订单 5:课程订单 6:智能计划订单
			if (objs.getString("orderType").equals("1")) {
				order = (ProductOrder) service.load(ProductOrder.class, objs.getLong("id"));
				order.setIsappraise("1");
				order = (ProductOrder) service.saveOrUpdate(order);
			} else if (objs.getString("orderType").equals("2")) {
				order = (ActiveOrder) service.load(ActiveOrder.class, objs.getLong("id"));
				order.setIsappraise("1");
				order = (ActiveOrder) service.saveOrUpdate(order);
			} else if (objs.getString("orderType").equals("3")) {
				order = (PlanOrder) service.load(PlanOrder.class, objs.getLong("id"));
				order.setIsappraise("1");
				order = (PlanOrder) service.saveOrUpdate(order);
			} else if (objs.getString("orderType").equals("4")) {
				order = (FactoryOrder) service.load(FactoryOrder.class, objs.getLong("id"));
				order.setIsappraise("1");
				order = (FactoryOrder) service.saveOrUpdate(order);
			} else if (objs.getString("orderType").equals("5")) {
				order = (CourseOrder) service.load(CourseOrder.class, objs.getLong("id"));
				order.setIsappraise("1");
				order = (CourseOrder) service.saveOrUpdate(order);
			} else if (objs.getString("orderType").equals("6")) {
				order = (GoodsOrder) service.load(GoodsOrder.class, objs.getLong("id"));
				order.setIsappraise("1");
				order = (GoodsOrder) service.saveOrUpdate(order);
			}
			final JSONObject obj = new JSONObject();
			obj.accumulate("success", true);
			response(obj);

		} catch (Exception e) {
			log.error("error", e);
			response(e);
		}

	}

	// 时间相加的方法
	public static Date addDate(Date date, long day) {
		long time = date.getTime(); // 得到指定日期的毫秒数
		day = day * 24 * 60 * 60 * 1000; // 要加上的天数转换成毫秒数
		time += day; // 相加得到新的毫秒数
		return new Date(time); // 将毫秒数转换成日期
	}

}
