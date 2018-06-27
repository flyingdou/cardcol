package com.cardcolv45.mobile.action;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;

import com.cardcol.web.order.ProductOrder45;
import com.cardcol.web.utils.PayUtilsC;
import com.freegym.web.basic.Member;
import com.freegym.web.basic.MemberTicket;
import com.freegym.web.mobile.BasicJsonAction;
import com.freegym.web.order.ActiveOrder;
import com.freegym.web.order.CourseOrder;
import com.freegym.web.order.GoodsOrder;
import com.freegym.web.order.Order;
import com.freegym.web.order.PickAccount;
import com.freegym.web.order.PickDetail;
import com.freegym.web.order.PlanOrder;
import com.freegym.web.order.ProductOrder;
import com.sanmen.web.core.common.LogicException;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Namespace("")
@InterceptorRefs({ @InterceptorRef("mobileStack") })
public class MProductOrderV45ManageAction extends BasicJsonAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2383558191389610986L;
	private int type;
	private String name;// 账户名称
	private String bankName;// 开户行
	private String account;// 账号

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	/**
	 * 提现记录查询
	 */
	@SuppressWarnings("unchecked")
	public void findPickDetail() {

		// 设置pageSize,currentPage
		String page = request.getParameter("pageSize");
		String current = request.getParameter("currentPage");
		int pageSize = 20;
		int currentPage = 1;
		if (page != null) {
			pageSize = Integer.parseInt(page);
		}
		if (current != null) {
			currentPage = Integer.parseInt(current);
		}
		pageInfo.setPageSize(pageSize);
		pageInfo.setCurrentPage(currentPage);

		// 查询余额
		Double pickMoneyCount = service.findPickMoneyCountByMember((Member) getMobileUser());
		DecimalFormat df = new DecimalFormat("0.00");
		// 查询提现记录
		pageInfo = service.queryPickDetail(pageInfo, getMobileUser().getId().toString());
		List<Map<String, Object>> pickList = pageInfo.getItems();
		final JSONArray jarr = new JSONArray();
		if (pickList.size() > 0) {
			JSONObject objx = null;
			for (Map<String, Object> map : pickList) {
				objx = new JSONObject();
				objx.accumulate("bankName", map.get("bankName")).accumulate("account", map.get("account"))
						.accumulate("evalTime", map.get("evalTime")).accumulate("pickMoney", map.get("pickMoney"))
						.accumulate("status", map.get("status"));
				jarr.add(objx);
			}
		}

		final JSONObject obj = new JSONObject();
		obj.accumulate("success", true).accumulate("pickDetail", jarr).accumulate("pageInfo", getJsonForPageInfo())
				.accumulate("pickMoneyCount", df.format(pickMoneyCount));
		response(obj);
	}

	// 提现是否绑定
	public void queryPickAccount() {
		Map<String, Object> map = service.queryPickAccount(getMobileUser().getId().toString());
		final JSONObject obj = new JSONObject();
		obj.accumulate("success", true).accumulate("pickAccount", map);
		response(obj);

	}

	// 添加提现账户
	public void savePickAccount() {
		PickAccount pickAccount = new PickAccount();
		final String mobile = request.getParameter("mobile");
		final String code = request.getParameter("code");
		final JSONObject obj = new JSONObject();
		if (type == 1) {
			if (isRightful(mobile, code)) {
				pickAccount.setBankName("支付宝");
				pickAccount.setMember(new Member(getMobileUser().getId()));
				pickAccount.setAccount(account);
				pickAccount = (PickAccount) service.saveOrUpdate(pickAccount);
				obj.accumulate("id", pickAccount.getId()).accumulate("bankName", pickAccount.getBankName())
						.accumulate("member", pickAccount.getMember().getId())
						.accumulate("account", pickAccount.getAccount()).accumulate("name", pickAccount.getName());
			} else {
				obj.accumulate("success", false).accumulate("message", "验证输入不正确，请重新输入");
			}
		} else {
			if (isRightful(mobile, code)) {
				pickAccount.setBankName(bankName);
				pickAccount.setMember(new Member(getMobileUser().getId()));
				pickAccount.setAccount(account);
				pickAccount.setName(name);
				pickAccount = (PickAccount) service.saveOrUpdate(pickAccount);
				obj.accumulate("id", pickAccount.getId()).accumulate("bankName", pickAccount.getBankName())
						.accumulate("member", pickAccount.getMember().getId())
						.accumulate("account", pickAccount.getAccount()).accumulate("name", pickAccount.getName());
			} else {
				obj.accumulate("success", false).accumulate("message", "验证输入不正确，请重新输入");
			}
		}
		final JSONObject ret = new JSONObject();
		ret.accumulate("success", true).accumulate("pickAccount", obj);
		response(ret);
	}

	/**
	 * 申请提现
	 */
	@SuppressWarnings("unused")
	public String savePickDetail() {
		try {
			Double pickMoney = new Double(request.getParameter("pickMoney"));
			final String mobile = request.getParameter("mobile");
			final String code = request.getParameter("code");
			Map<String, Object> map = service.queryPickAccount(getMobileUser().getId().toString());
			PickAccount pickAccount = (PickAccount) service.load(PickAccount.class, (Long) map.get("id"));
			JSONObject obj = new JSONObject();
			PickDetail pickDetail = new PickDetail();
			if (pickDetail != null) {
				if (getMobileUser().getMobilephone() != null && !"".equals(getMobileUser().getMobilephone())) {
					if (isRightful(getMobileUser().getMobilephone(), code)) {
						pickDetail.setNo(service.getKeyNo("", "TB_PICK_DETAIL", 14));
						pickDetail.setType("1");
						pickDetail.setFlowType("1");
						pickDetail.setMember((Member) getMobileUser());
						pickDetail.setPickDate(new Date());
						pickDetail.setStatus("1");
						pickDetail.setPickMoney(pickMoney);
						pickDetail.setPickAccount(pickAccount);
						service.saveOrUpdate(pickDetail);
						obj.accumulate("success", true).accumulate("msg", "ok");
					} else {
						obj.accumulate("success", false).accumulate("msg", "您输入的验证码不正确，请重新输入");
					}
				} else {
					obj.accumulate("success", false).accumulate("msg", "您未注册手机号");
				}
				response(obj);
			}
		} catch (LogicException e) {
			response(new JSONObject().accumulate("success", true).accumulate("msg", e));
		}
		return null;
	}

	/**
	 * 订单查询
	 */
	@SuppressWarnings("unchecked")
	public void findOrder() {
		String status = request.getParameter("status");
		int pageSize = 0;
		if (request.getParameter("pageSize") != null) {
			pageSize = Integer.parseInt(request.getParameter("pageSize").toString());
		}
		int currentPage = 0;
		if (request.getParameter("currentPage") != null) {
			Integer.parseInt(request.getParameter("currentPage").toString());
		}
		pageSize = pageSize == 0 ? 20 : pageSize;
		currentPage = currentPage == 0 ? 1 : currentPage;
		pageInfo.setPageSize(pageSize);
		pageInfo.setCurrentPage(currentPage);

		pageInfo = service.queryOrder(pageInfo, getMobileUser().getId(), status, "dou", "hua");
		// JSONArray allOrder = JSONArray.fromObject(pageInfo.getItems());

		List<Map<String, Object>> orderList = pageInfo.getItems();
		JSONObject objx = null;
		JSONArray jsonArray = new JSONArray();
		if (orderList.size() > 0) {
			for (Map<String, Object> map : orderList) {
				objx = new JSONObject();
				objx.accumulate("id", map.get("id")).accumulate("name", map.get("name")).accumulate("no", map.get("no"))
						.accumulate("type", map.get("type")).accumulate("orderStartTime", map.get("orderStartTime"))
						.accumulate("orderMoney", map.get("orderMoney")).accumulate("image", map.get("image"))
						.accumulate("productId", map.get("productId"));
				jsonArray.add(objx);
			}
		}
		final JSONObject obj = new JSONObject();
		obj.accumulate("success", true).accumulate("allOrder", jsonArray).accumulate("pageInfo", getJsonForPageInfo());
		response(obj);
	}

	public void delectOrder() {
		String type = request.getParameter("type");
		if ("2".equals(type)) {
			service.delete(ActiveOrder.class, id);
		} else if ("5".equals(type)) {
			service.delete(CourseOrder.class, id);
		} else if ("6".equals(type)) {
			service.delete(GoodsOrder.class, id);
		} else if ("8".equals(type)) {
			service.delete(ProductOrder45.class, id);
		}
		final JSONObject obj = new JSONObject();
		obj.accumulate("success", true).accumulate("code", "ok");
		response(obj);
	}

	/**
	 * 转让会员名单(教练名单)
	 */
	/*
	 * public void findMember() { String str = request.getParameter("str");
	 * String id = request.getParameter("id"); pageInfo =
	 * service.queryMember(pageInfo, id, str); JSONArray memberList =
	 * JSONArray.fromObject(pageInfo.getItems()); final JSONObject obj = new
	 * JSONObject(); obj.accumulate("success", true).accumulate("member",
	 * memberList).accumulate("pageInfo", getJsonForPageInfo()); response(obj);
	 * }
	 */

	/**
	 * 退卡金额
	 */
	/*
	 * public void returnCardPrice() { String id = request.getParameter("id");//
	 * 订单id Double returnCardPrice = service.returnCardPrice(id); }
	 */
	/**
	 * 我的订单中一卡通订单详情
	 */
	public void oneCardOrderDetail() {
		String id = request.getParameter("id");// 订单id
		Map<String, Object> map = service.queryOneCardOrderDetail(id);
		final JSONObject obj = new JSONObject();
		obj.accumulate("success", true).accumulate("oneCardOrderDetail", map);
		response(obj);
	}

	/**
	 * 更新未付款订单订单
	 */
	public void bepaidOrderDetail() {
		String no = request.getParameter("no");// 订单no
		Long ticketId = new Long(request.getParameter("ticketId"));// 优惠券ID
		Double actualMoney = new Double(request.getParameter("actualMoney"));// 实付金额
		Double ticketMoney = new Double(request.getParameter("ticketMoney"));// 优惠券金额
		List<?> list = service
				.findObjectBySql("from "
						+ (no.startsWith("22") ? "ProductOrder45"
								: no.startsWith("99") ? "ActiveOrder"
										: no.startsWith("44") ? "GoodsOrder"
												: no.startsWith("55") ? "CourseOrder" : "CourseOrder")
						+ " where no = ?", no);

		Order order = (Order) list.get(0);
		order.setOrderMoney(actualMoney);
		order.setTicketId(ticketId);
		order.setTicketAmount(ticketMoney);
		service.saveOrUpdate(order);
	}

	// 未付款订单付款接口
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
			if (orderType.equals("1")) {

				order = (ProductOrder) service.load(ProductOrder.class, id);
				ProductOrder p = (ProductOrder) service.load(ProductOrder.class, id);
				trade = p.getProduct().getName();
				if (ticketId != null) {
					order.setTicketId(Long.valueOf(ticketId));
				}
				order.setOrderMoney(p.getUnitPrice() - ticketMoney);

			} else if (orderType.equals("2")) {
				order = (ActiveOrder) service.load(ActiveOrder.class, id);
				ActiveOrder a = (ActiveOrder) service.load(ActiveOrder.class, id);
				trade = a.getActive().getName();
				if (ticketId != null)
					order.setTicketId(Long.valueOf(ticketId));
				order.setOrderMoney(a.getActive().getAmerceMoney() - ticketMoney);
			} else if (orderType.equals("3")) {
				order = (PlanOrder) service.load(PlanOrder.class, id);
				PlanOrder p = (PlanOrder) service.load(PlanOrder.class, id);
				trade = p.getPlanRelease().getPlanName();
				if (ticketId != null) {
					order.setTicketId(Long.valueOf(ticketId));
				}
				order.setOrderMoney(p.getUnitPrice() - ticketMoney);

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
				order.setTicketId(Long.valueOf(ticketId).longValue());
				order = (CourseOrder) service.load(CourseOrder.class, id);
				CourseOrder c = (CourseOrder) service.load(CourseOrder.class, id);
				trade = c.getCourse().getCourseInfo().getName();
				if (ticketId != null)
					order.setTicketId(Long.valueOf(ticketId));
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
			response(e);
			e.printStackTrace();
		}
	}
}