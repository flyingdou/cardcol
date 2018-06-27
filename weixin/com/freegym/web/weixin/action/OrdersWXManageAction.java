package com.freegym.web.weixin.action;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
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
import com.freegym.web.order.CourseOrder;
import com.freegym.web.order.FactoryOrder;
import com.freegym.web.order.Goods;
import com.freegym.web.order.Order;
import com.freegym.web.order.Product;
import com.freegym.web.plan.Course;
import com.freegym.web.plan.PlanRelease;
import com.freegym.web.task.CoursePaymentTask;

@Namespace("")
@InterceptorRefs({ @InterceptorRef("customStack") })
@Results({ @Result(name = "writeorder", location = "/WX/write_orders.jsp"),
		@Result(name = "success", location = "/WX/sp_oeders.jsp") })
public class OrdersWXManageAction extends OrderBasicAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5285813674437046852L;
	private static final SimpleDateFormat format_hm = new SimpleDateFormat("HH:mm");
	private String totalMoney;

	private String subject;
	// 计划、自动订单、健身卡开始日期
	private String startDate;
	private String queryType;
	private Member member;
	private Setting setting;
	// 场地预约开始时间
	private String factoryStartTime;
	// 场地预约结束时间
	private String factoryEndTime;

	private String payNo;
	private List<ShopListDto> shopDtos;
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
	/**
	 * 团队名称
	 */
	private Long teamId;

	public String getTotalMoney() {
		return totalMoney;
	}

	public void setTotalMoney(String totalMoney) {
		this.totalMoney = totalMoney;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getPayNo() {
		return payNo;
	}

	public void setPayNo(String payNo) {
		this.payNo = payNo;
	}

	public List<ShopListDto> getShopDtos() {
		return shopDtos;
	}

	public void setShopDtos(List<ShopListDto> shopDtos) {
		this.shopDtos = shopDtos;
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
		this.judge = judge;
	}

	public Long getTeamId() {
		return teamId;
	}

	public void setTeamId(Long teamId) {
		this.teamId = teamId;
	}

	public Setting getSetting() {
		return setting;
	}

	public void setSetting(Setting setting) {
		this.setting = setting;
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

	/*
	 * 立即下载
	 */
	public void genPlan() {
		member = (Member) session.getAttribute("member");
		Order order = new Order();
		try {
			order.setOrderStartTime(ymd.parse(request.getParameter("startDate")));
			order.setMember(member);
			final PlanRelease pr = (PlanRelease) service.load(PlanRelease.class, id);
			if (pr.getUnitPrice() == 0d) {
				service.genPlanData(order, pr);
				response();
			} else {
				throw new Exception("数据异常，请确认数据是否已经刷新?");
			}
		} catch (Exception e) {
			response(e);
		}
	}

	/*
	 * 立即购买
	 */
	public String submitProd() {
		String prodType = request.getParameter("prodType");
		String factoryMoney = request.getParameter("factoryMoney");
		this.setFactoryStartTime(factoryStartTime);
		this.setFactoryEndTime(factoryEndTime);
		Member m = (Member) session.getAttribute("member");
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

		if (m.getMobileValid() != null && m.getMobileValid().equals('1') && m.getMobilephone() != null
				&& m.getMobilephone().length() > 7) {
			request.setAttribute("mobilephone",
					m.getMobilephone().replaceAll(m.getMobilephone().substring(3, 7), "*****"));
		}
		return "writeorder";
	}

	/*
	 * 提交订单
	 */
	public String payprod() throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		BigDecimal bd = new BigDecimal(0);
		StringBuffer sb = new StringBuffer();
		Member member = (Member) session.getAttribute("member");
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
						@SuppressWarnings("unused")
						final CourseOrder courseOrder = service.saveCourseOrder((Course) service.load(Course.class, id),
								sdf.parse(dto.getStart_date()), member, payNo, dto.getProd_price());
					}

				} else if ("6".equals(dto.getProd_type())) {
					service.saveGoodsOrder((Goods) service.load(Goods.class, id), sdf.parse(dto.getStart_date()),
							member, payNo, dto.getProd_price());
				}
				bd = bd.add(new BigDecimal(dto.getProd_price()));
				sb.append(dto.getProd_name() + ",");
				startDate = dto.getStart_date();
			}
		}
		totalMoney = bd.toString();
		subject = sb.toString().substring(0, sb.length() - 1);
		if (member.getMobileValid() != null && member.getMobileValid().equals('1') && member.getMobilephone() != null) {
			request.setAttribute("mobilephone",
					member.getMobilephone().replaceAll(member.getMobilephone().substring(3, 7), "*****"));
		}
		return "success";
	}

}
