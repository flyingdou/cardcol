package com.freegym.web.order.action;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Timer;

import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.freegym.web.OrderBasicAction;
import com.freegym.web.active.Active;
import com.freegym.web.basic.Member;
import com.freegym.web.chinapay.config.ChinaPayConfig;
import com.freegym.web.config.FactoryCosts;
import com.freegym.web.config.Setting;
import com.freegym.web.dto.ShopListDto;
import com.freegym.web.order.ActiveOrder;
import com.freegym.web.order.CourseOrder;
import com.freegym.web.order.FactoryOrder;
import com.freegym.web.order.Goods;
import com.freegym.web.order.GoodsOrder;
import com.freegym.web.order.Order;
import com.freegym.web.order.PlanOrder;
import com.freegym.web.order.Product;
import com.freegym.web.order.ProductOrder;
import com.freegym.web.order.Shop;
import com.freegym.web.order.ShopGoods;
import com.freegym.web.order.ShopPlan;
import com.freegym.web.plan.Course;
import com.freegym.web.plan.PlanRelease;
import com.freegym.web.task.CoursePaymentTask;
import com.freegym.web.utils.EasyUtils;

@Namespace("")
@InterceptorRefs({ @InterceptorRef("foreStack") })
@Results({ @Result(name = "order_list", location = "/order/order_list.jsp"),
		@Result(name = "order_select", location = "/order/order_select.jsp"),
		@Result(name = "success", location = "/shop/shopping_payType.jsp"),
		@Result(name = "shopping_order", location = "/order/shopping_order.jsp"),
		@Result(name = "product", type = "redirect", location = "product.asp"),
		@Result(name = "shopim", location = "/order/shopim.jsp"),
		@Result(name = "payment", location = "/payment/payment.jsp"),
		@Result(name = "pay_success", location = "/order/shopping_success.jsp"),
		@Result(name = "pay_fail", location = "/order/shopping_fail.jsp"),
		@Result(name = "order_select", location = "/order/order_select.jsp") })
public class OrderManageAction extends OrderBasicAction {

	private static final long serialVersionUID = -3769475961884283745L;

	private String totalMoney;

	private String subject;

	private List<ShopListDto> shopDtos;

	private String payNo;

	private Order query;

	private Setting setting;

	private Integer type;

	private Integer type2;

	private ProductOrder order;

	private String payType;

	// 场地预约开始时间
	private String factoryStartTime;
	// 场地预约结束时间
	private String factoryEndTime;
	// 计划、自动订单、健身卡开始日期
	private String startDate;

	private CourseOrder courseOrder;

	private String queryType;

	private String queryType2;

	private Member member;

	/**
	 * 团队名称
	 */
	private Long teamId;

	/**
	 * 判断是新建团队，还是加入团队
	 */
	private Character createMode;

	/**
	 * 团队名称
	 */
	private String teamName;

	/**
	 * 新建团队时所有选择的会员
	 */
	private String members;

	private String judge;

	private static final SimpleDateFormat format_hm = new SimpleDateFormat("HH:mm");

	@SuppressWarnings("unchecked")
	public String query() {
		if (query == null)
			query = new ProductOrder();

		final List<Object> params = new ArrayList<Object>();
		final StringBuffer sql = new StringBuffer();
		sql.append("SELECT * FROM (").append(Order.getOrdersqlxx()).append(") t where 1 = 1 ");
		if (query.getStatus() != null) {
			sql.append("and status = ? ");
			params.add(query.getStatus().toString());
		}
		final Long mid = toMember().getId();
		if ("1".equals(queryType)) {
			sql.append("and (toId = ?) ");
			params.add(mid);
		} else {
			sql.append("and (fromId = ?) ");
			params.add(mid);
		}
		if (query.getStartDate() != null) {
			if (query.getEndDate() != null) {
				sql.append("and orderStartTime between ? and ? ");
				params.add(query.getStartDate());
				params.add(query.getEndDate());
			} else {
				sql.append("and orderStartTime >= ? ");
				params.add(query.getStartDate());
			}
		} else {
			if (query.getEndDate() != null) {
				sql.append("and orderStartTime <= ? ");
				params.add(query.getEndDate());
			}
		}
		sql.append("order by orderDate desc");
		pageInfo.setPageSize(10);
		pageInfo = service.findPageBySql(sql.toString(), pageInfo, params.toArray());
		pageInfo.setItems(EasyUtils.decimalFormat(pageInfo.getItems()));
		session.setAttribute("spath", 5);
		if ("E".equals(toMember().getRole())) {
			type = 0;
			type2 = 0;
		} else {
			type = 1;
			type2 = 1;
		}
		return "order_list";
	}

	public String delete() {
		if (type != null) {
			if (order != null && order.getId() != null) {
				// String strId = String.valueOf(order.getId());
				// Long keyId = Long.parseLong(strId.substring(2));
				System.out.println();
				if (type == 1) {
					service.delete(ProductOrder.class, order.getId());
				} else if (type == 2) {
					service.delete(ActiveOrder.class, order.getId());
				} else if (type == 3) {
					service.delete(PlanOrder.class, order.getId());
				} else if (type == 4) {
					service.delete(FactoryOrder.class, order.getId());
				} else if (type == 5) {
					service.delete(CourseOrder.class, order.getId());
				} else if (type == 6) {
					service.delete(GoodsOrder.class, order.getId());
				}
			}
		}
		return query();
	}

	public String execute() throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		BigDecimal bd = new BigDecimal(0);
		StringBuffer sb = new StringBuffer();
		Member member = toMember();
		payNo = service.getKeyNo("", "CARDCOL_ORDER_NO", 13);
		payNo = payNo.substring(0, 2) + payNo.substring(4, 6)
				+ ChinaPayConfig.MerId.substring(ChinaPayConfig.MerId.length() - 5) + payNo.substring(6);
		if ((shopDtos != null && shopDtos.size() > 0)) {
			for (ShopListDto dto : shopDtos) {
				if (dto.isChecked()) {
					id = Long.parseLong(dto.getProd_id());
					Long shop_id = Long.valueOf(dto.getShop_id());
					if ("1".equals(dto.getProd_type())) {
						service.saveProductOrder((Product) service.load(Product.class, id),
								sdf.parse(dto.getStart_date()), member, payNo, dto.getProd_price());
						service.deleteProductShop((Shop) service.load(Shop.class, shop_id));
					} else if ("2".equals(dto.getProd_type())) {
						service.saveActiveOrder((Active) service.load(Active.class, id), sdf.parse(dto.getStart_date()),
								member, payNo, dto.getProd_price(), null, null, null, null, null);
					} else if ("3".equals(dto.getProd_type())) {
						service.savePlanOrder((PlanRelease) service.load(PlanRelease.class, id),
								sdf.parse(dto.getStart_date()), member, payNo, dto.getProd_price());
						service.deletePlanShop((ShopPlan) service.load(ShopPlan.class, shop_id));
					} else if ("4".equals(dto.getProd_type())) {
						service.saveFactoryOrder((FactoryCosts) service.load(FactoryCosts.class, id),
								sdf.parse(dto.getStart_date()), member, payNo, dto.getProd_price(),
								format_hm.parse(factoryStartTime), format_hm.parse(factoryEndTime));
					} else if ("5".equals(dto.getProd_type())) {
						service.saveCourseOrder((Course) service.load(Course.class, id), sdf.parse(dto.getStart_date()),
								member, payNo, dto.getProd_price());
					} else if ("6".equals(dto.getProd_type())) {
						service.saveGoodsOrder((Goods) service.load(Goods.class, id), sdf.parse(dto.getStart_date()),
								member, payNo, dto.getProd_price());
						service.deleteGoodsShop((ShopGoods) service.load(ShopGoods.class, shop_id));
					}
					bd = bd.add(new BigDecimal(dto.getProd_price()));
					sb.append(dto.getProd_name() + ",");
				}
			}
		}
		totalMoney = bd.toString();
		subject = sb.toString().substring(0, sb.length() - 1);
		if (member.getMobileValid() != null && member.getMobileValid().equals("1")) {
			request.setAttribute("mobilephone",
					member.getMobilephone().replaceAll(member.getMobilephone().substring(3, 7), "*****"));
		}

		return "success";

	}

	public void genPlan() {
		Order order = new Order();
		try {
			order.setOrderStartTime(ymd.parse(request.getParameter("startDate")));
			order.setMember(toMember());
			final PlanRelease pr = (PlanRelease) service.load(PlanRelease.class, id);
			if (pr.getUnitPrice() == 0d) {
				service.genPlanData(order, pr);
				response();
			} else {
				throw new Exception("数据异常，请确认数据是否已经刷新？");
			}
		} catch (Exception e) {
			response(e);
		}

	}

	public String submitProd() {
		String prodType = request.getParameter("prodType");
		String factoryMoney = request.getParameter("factoryMoney");
		this.setFactoryStartTime(factoryStartTime);
		this.setFactoryEndTime(factoryEndTime);
		Member m = toMember();
		// 自动订单的付款
		if ("6".equals(prodType)) {
			String type = request.getParameter("type");
			// 在我的健身计划里，setting为null
			if (setting != null) {
				Setting s = service.loadSetting(m.getId());
				if (s == null) {
					s = new Setting();
				}
				if ("1".equals(type)) {
					s.setCurrGymStatus(setting.getCurrGymStatus());
					s.setCardioDate(setting.getCardioDate());
					s.setCardioDuration(setting.getCardioDuration());
					s.setStrengthDate(setting.getStrengthDate());
					s.setStrengthDuration(setting.getStrengthDuration());
				} else if ("2".equals(type)) {
					String sex = member.getSex();
					if (m.getSex() == null || !m.getSex().equals(sex)) {
						m.setSex(sex);
						service.saveOrUpdate(m);
					}
					s.setHeight(setting.getHeight());
					s.setWeight(setting.getWeight());
					s.setMaxwm(setting.getMaxwm());
					s.setWaistline(setting.getWaistline());
				}
				s.setFavoriateCardio(setting.getFavoriateCardio());
				s.setMember(m.getId());
				s.setTarget(setting.getTarget());
				service.saveOrUpdate(s);
			}
			String sql = "SELECT * FROM (" + Order.getProdSql(prodType, m.getId(), factoryMoney, startDate)
					+ ") t where t.pro_type =?";
			request.setAttribute("list", service.queryForList(sql, type));
		} else {
			String sql = "SELECT * FROM (" + Order.getProdSql(prodType, m.getId(), factoryMoney, startDate)
					+ ") t where t.pro_id =?";
			request.setAttribute("list", service.queryForList(sql, id));
		}

		if (m.getMobileValid() != null && m.getMobileValid().equals("1") && m.getMobilephone() != null
				&& m.getMobilephone().length() > 7) {
			request.setAttribute("mobilephone",
					m.getMobilephone().replaceAll(m.getMobilephone().substring(3, 7), "*****"));
		}
		return "shopim";
	}

	@SuppressWarnings("unused")
	public String payProd() throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		BigDecimal bd = new BigDecimal(0);
		StringBuffer sb = new StringBuffer();
		Member member = toMember();
		payNo = service.getKeyNo("", "CARDCOL_ORDER_NO", 13);
		payNo = payNo.substring(0, 2) + payNo.substring(4, 6)
				+ ChinaPayConfig.MerId.substring(ChinaPayConfig.MerId.length() - 5) + payNo.substring(6);
		// 交易号从原有16位改为28位，前8位数字不变，后面填充至28位:如：之前交易号是：2014102200001111，升级后变成：2014102200001111222233334444
		// 在这里直接在后面填充至28位
		payNo = payNo + payNo.substring(0, 12);
		if ((shopDtos != null && shopDtos.size() > 0)) {
			for (ShopListDto dto : shopDtos) {
				id = Long.parseLong(dto.getProd_id());
				if ("1".equals(dto.getProd_type())) {
					service.saveProductOrder((Product) service.load(Product.class, id), sdf.parse(dto.getStart_date()),
							member, payNo, dto.getProd_price());
				} else if ("2".equals(dto.getProd_type())) {
					service.saveActiveOrder((Active) service.load(Active.class, id), sdf.parse(dto.getStart_date()),
							member, payNo, dto.getProd_price(), teamId, createMode, teamName, members, judge);
				} else if ("3".equals(dto.getProd_type())) {
					service.savePlanOrder((PlanRelease) service.load(PlanRelease.class, id),
							sdf.parse(dto.getStart_date()), member, payNo, dto.getProd_price());
				} else if ("4".equals(dto.getProd_type()) | "5".equals(dto.getProd_type())) {
					if ("4".equals(dto.getProd_type())) {
						final FactoryOrder factoryOrder = service.saveFactoryOrder(
								(FactoryCosts) service.load(FactoryCosts.class, id), sdf.parse(dto.getStart_date()),
								member, payNo, dto.getProd_price(), format_hm.parse(factoryStartTime),
								format_hm.parse(factoryEndTime));
						final Timer timer = new Timer();
						timer.schedule(new CoursePaymentTask(service, factoryOrder.getId()),
								PAYMENT_FACTORY_TIME * 60 * 1000);
					} else {
						final CourseOrder courseOrder = service.saveCourseOrder((Course) service.load(Course.class, id),
								sdf.parse(dto.getStart_date()), member, payNo, dto.getProd_price());
					}

				} else if ("6".equals(dto.getProd_type())) {
					service.saveGoodsOrder((Goods) service.load(Goods.class, id), sdf.parse(dto.getStart_date()),
							member, payNo, dto.getProd_price());
				}
				bd = bd.add(new BigDecimal(dto.getProd_price()));
				sb.append(dto.getProd_name() + ",");
			}
		}
		totalMoney = bd.toString();
		subject = sb.toString().substring(0, sb.length() - 1);
		if (member.getMobileValid() != null && member.getMobileValid().equals("1") && member.getMobilephone() != null) {
			request.setAttribute("mobilephone",
					member.getMobilephone().replaceAll(member.getMobilephone().substring(3, 7), "*****"));
		}

		return "success";
	}

	public String payOrder() {
		if ("1".equals(queryType)) {
			ProductOrder o = (ProductOrder) service.load(ProductOrder.class, order.getId());
			totalMoney = o.getOrderMoney().toString();
			subject = o.getProduct().getName();
			payNo = o.getPayNo();
		} else if ("2".equals(queryType)) {
			ActiveOrder o = (ActiveOrder) service.load(ActiveOrder.class, order.getId());
			totalMoney = o.getOrderMoney().toString();
			subject = o.getActive().getName();
			payNo = o.getPayNo();
		} else if ("3".equals(queryType)) {
			PlanOrder o = (PlanOrder) service.load(PlanOrder.class, order.getId());
			totalMoney = o.getOrderMoney().toString();
			subject = o.getPlanRelease().getPlanName();
			payNo = o.getPayNo();
		} else if ("4".equals(queryType)) {
			FactoryOrder o = (FactoryOrder) service.load(FactoryOrder.class, order.getId());
			totalMoney = o.getOrderMoney().toString();
			subject = o.getFactoryCosts().getName();
			payNo = o.getPayNo();
		} else if ("5".equals(queryType)) {
			CourseOrder o = (CourseOrder) service.load(CourseOrder.class, order.getId());
			totalMoney = o.getOrderMoney().toString();
			subject = o.getCourse().getCourseInfo().getName();
			payNo = o.getPayNo();
		} else if ("6".equals(queryType)) {
			GoodsOrder o = (GoodsOrder) service.load(GoodsOrder.class, order.getId());
			totalMoney = o.getOrderMoney().toString();
			subject = o.getGoods().getName();
			payNo = o.getPayNo();
		}
		Member member = toMember();
		if (member.getMobileValid() != null && member.getMobileValid().equals("1") && member.getMobilephone() != null) {
			request.setAttribute("mobilephone",
					member.getMobilephone().replaceAll(member.getMobilephone().substring(3, 7), "*****"));
		}

		return "success";
	}

	public String showPayment() {
		try {
			session.setAttribute("spath", 5);
			if (type == 1) {
				ProductOrder productOrder = (ProductOrder) service.load(ProductOrder.class, id);
				request.setAttribute("no", productOrder.getPayNo());
				request.setAttribute("money", productOrder.getOrderMoney());
				request.setAttribute("name", productOrder.getProduct().getName());
			} else if (type == 3) {
				PlanOrder planOrder = (PlanOrder) service.load(PlanOrder.class, id);
				request.setAttribute("no", planOrder.getPayNo());
				request.setAttribute("money", planOrder.getOrderMoney());
				request.setAttribute("name", planOrder.getPlanRelease().getPlanName());
			} else if (type == 4) {
				FactoryOrder factoryOrder = (FactoryOrder) service.load(FactoryOrder.class, id);
				request.setAttribute("no", factoryOrder.getPayNo());
				request.setAttribute("money", factoryOrder.getOrderMoney());
				request.setAttribute("name", factoryOrder.getFactoryCosts().getFactory().getName());
			} else if (type == 5) {
				CourseOrder courseOrder = (CourseOrder) service.load(CourseOrder.class, id);
				request.setAttribute("no", courseOrder.getPayNo());
				request.setAttribute("money", courseOrder.getOrderMoney());
				request.setAttribute("name", courseOrder.getCourse().getCourseInfo().getName());
			} else if (type == 6) {
				GoodsOrder goodsOrder = (GoodsOrder) service.load(GoodsOrder.class, id);
				request.setAttribute("no", goodsOrder.getPayNo());
				request.setAttribute("money", goodsOrder.getOrderMoney());
				request.setAttribute("name", goodsOrder.getName());
			}
		} catch (Exception e) {
			log.error("error", e);
			setMessage(e.getMessage());
		}
		return "payment";
	}

	public String paySuccess() {
		String sql = "SELECT * FROM (" + Order.getOrderSql() + ") t where t.payNo ='" + payNo + "'";
		List<Map<String, Object>> list = service.queryForList(sql);
		String status = (String) list.iterator().next().get("status");
		if ("1".equals(status)) {
			return "pay_success";
		} else {
			return "pay_fail";
		}
	}

	/**
	 * 我的投诉中查询订单
	 * 
	 * @return
	 */
	public String showOrdersForComplaint() {
		StringBuffer sql = new StringBuffer("select * from (" + Order.getOrderSql() + ") t where 1=1 ");
		pageInfo.setPageSize(10);
		final List<Object> params = new ArrayList<Object>();
		if (query != null) {
			if (query.getStartDate() != null) {
				if (query.getEndDate() != null) {
					sql.append("and t.orderDate between ? and ? ");
					params.add(query.getStartDate());
					params.add(new Date(query.getEndDate().getTime() + 24 * 60 * 60 * 1000));
				} else {
					sql.append("and t.orderDate >= ? ");
					params.add(query.getStartDate());
				}
			} else {
				if (query.getEndDate() != null) {
					sql.append("and t.orderDate <= ? ");
					params.add(new Date(query.getEndDate().getTime() + 24 * 60 * 60 * 1000));
				}
			}
			if (query.getOrderType() != null && !"".equals(query.getOrderType())) {
				sql.append("and t.type = ? ");
				params.add(query.getOrderType());
			}
		}
		sql.append("and t.fromId = ? ");
		params.add(toMember().getId());
		pageInfo = service.findPageBySql(sql.toString() + " order by orderDate desc", pageInfo, params.toArray());
		return "order_select";
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getTotalMoney() {
		return totalMoney;
	}

	public void setTotalMoney(String totalMoney) {
		this.totalMoney = totalMoney;
	}

	public List<ShopListDto> getShopDtos() {
		return shopDtos;
	}

	public void setShopDtos(List<ShopListDto> shopDtos) {
		this.shopDtos = shopDtos;
	}

	public String getPayNo() {
		return payNo;
	}

	public void setPayNo(String payNo) {
		this.payNo = payNo;
	}

	public Setting getSetting() {
		return setting;
	}

	public void setSetting(Setting setting) {
		this.setting = setting;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public ProductOrder getOrder() {
		return order;
	}

	public void setOrder(ProductOrder order) {
		this.order = order;
	}

	public Order getQuery() {
		return query;
	}

	public void setQuery(Order query) {
		this.query = query;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String getFactoryStartTime() {
		return factoryStartTime;
	}

	public void setFactoryStartTime(String factoryStartTime) {
		this.factoryStartTime = factoryStartTime;
	}

	public String getFactoryEndTime() {
		return factoryEndTime;
	}

	public void setFactoryEndTime(String factoryEndTime) {
		this.factoryEndTime = factoryEndTime;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public CourseOrder getCourseOrder() {
		return courseOrder;
	}

	public void setCourseOrder(CourseOrder courseOrder) {
		this.courseOrder = courseOrder;
	}

	public String getQueryType() {
		return queryType;
	}

	public void setQueryType(String queryType) {
		this.queryType = queryType;
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public Long getTeamId() {
		return teamId;
	}

	public void setTeamId(Long teamId) {
		this.teamId = teamId;
	}

	public Character getCreateMode() {
		return createMode;
	}

	public void setCreateMode(Character createMode) {
		this.createMode = createMode;
	}

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public String getMembers() {
		return members;
	}

	public void setMembers(String members) {
		this.members = members;
	}

	public String getJudge() {
		return judge;
	}

	public void setJudge(String judge) {
		// if (judge != null && !"".equals(judge)) try {
		// judge = new String(judge.getBytes("ISO-8859-1"), "UTF-8");
		// } catch (UnsupportedEncodingException e) {
		// e.printStackTrace();
		// }
		this.judge = judge;
	}

	public Integer getType2() {
		return type2;
	}

	public void setType2(Integer type2) {
		this.type2 = type2;
	}

	public String getQueryType2() {
		return queryType2;
	}

	public void setQueryType2(String queryType2) {
		this.queryType2 = queryType2;
	}

}
