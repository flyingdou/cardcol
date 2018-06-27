package com.cardcol.web.order.action;

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
import com.cardcol.web.utils.PayUtils;
import com.freegym.web.basic.Member;
import com.freegym.web.basic.MemberTicket;
import com.freegym.web.mobile.BasicJsonAction;
import com.freegym.web.order.ActiveOrder;
import com.freegym.web.order.CourseOrder;
import com.freegym.web.order.GoodsOrder;
import com.freegym.web.order.Order;
import com.freegym.web.order.PickAccount;
import com.freegym.web.order.PickDetail;
import com.sanmen.web.core.common.LogicException;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Namespace("")
@InterceptorRefs({ @InterceptorRef("mobileStack") })
public class MProductOrder45ManageAction extends BasicJsonAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2383558191389610986L;
	private int type;
	private String name;// 账户名称
	private String bankName;// 开户行
	private String account;// 账号



	/**
	 * 提现记录查询
	 */
	public void findPickDetail() {
		// 查询余额
		Double pickMoneyCount = service.findPickMoneyCountByMember((Member) getMobileUser());
		DecimalFormat df = new DecimalFormat("0.00");
		// 查询提现记录
		pageInfo = service.queryPickDetail(pageInfo, getMobileUser().getId().toString());
		JSONArray pickDetail = JSONArray.fromObject(pageInfo.getItems());
		final JSONObject obj = new JSONObject();
		obj.accumulate("success", true).accumulate("pickDetail", pickDetail)
				.accumulate("pageInfo", getJsonForPageInfo()).accumulate("pickMoneyCount", df.format(pickMoneyCount));
		response(obj);
	}

	// 提现是否绑定
	public void queryPickAccount() {
		Map<String, Object> map = service.queryPickAccount(getMobileUser().getId().toString());
		final JSONObject obj = new JSONObject();
		obj.accumulate("success", true).accumulate("pickAccount", map);
		response(obj);

	}

	// 添加账户
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
		Double pickMoney = new Double(request.getParameter("pickMoney"));
		final String mobile = request.getParameter("mobile");
		final String code = request.getParameter("code");
		Map<String, Object> map = service.queryPickAccount(getMobileUser().getId().toString());
		PickAccount pickAccount = (PickAccount) service.load(PickAccount.class, (Long) map.get("id"));
		JSONObject obj = new JSONObject();
		PickDetail pickDetail = new PickDetail();
		if (pickDetail != null) {
			try {
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
			} catch (LogicException e) {
				log.error("error", e);
			}
			response(obj);
		}
		return null;
	}

	/**
	 * 订单查询
	 */
	public void findOrder() {
		String status = request.getParameter("status");
		if ("2".equals(status)){
			status = "3";
		}
		pageInfo = service.queryOrderInStatus(pageInfo, getMobileUser().getId(), status);
		JSONArray allOrder = JSONArray.fromObject(pageInfo.getItems());
		final JSONObject obj = new JSONObject();
		obj.accumulate("success", true).accumulate("allOrder", allOrder).accumulate("pageInfo", getJsonForPageInfo());
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
	@SuppressWarnings("unused")
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
				if(order.getTicketId() != null && order.getTicketId() > 0){
					MemberTicket mt = (MemberTicket) service.load(MemberTicket.class, order.getTicketId());
					ticketMoney = mt.getTicket().getPrice();
				}
				order.setOrderMoney(a.getActive().getAmerceMoney() - ticketMoney);
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
				/*
				 * if (list.size() != 0){throw new
				 * LogicException("您这个月无法再购买课程！");}
				 */
				order = (CourseOrder) service.load(CourseOrder.class, id);
				CourseOrder c = (CourseOrder) service.load(CourseOrder.class, id);
				trade = c.getCourse().getCourseInfo().getName() == null ? "未知商品" : c.getCourse().getCourseInfo().getName();
				if (ticketId != null)
					order.setTicketId(Long.valueOf(ticketId));
				if(order.getTicketId() != null && order.getTicketId() > 0){
					MemberTicket mt = (MemberTicket) service.load(MemberTicket.class, order.getTicketId());
					ticketMoney = mt.getTicket().getPrice();
				}
				order.setOrderMoney(
						(c.getCourse().getHourPrice() == null ? 0.0 : c.getCourse().getHourPrice()) - ticketMoney);
			} else if (orderType.equals("6")) {
				order = (GoodsOrder) service.load(GoodsOrder.class, id);
				GoodsOrder g = (GoodsOrder) service.load(GoodsOrder.class, id);
				trade = g.getGoods().getName();
				if (ticketId != null)
					order.setTicketId(Long.valueOf(ticketId));
				if(order.getTicketId() != null && order.getTicketId() > 0){
					MemberTicket mt = (MemberTicket) service.load(MemberTicket.class, order.getTicketId());
					ticketMoney = mt.getTicket().getPrice();
				}
				order.setOrderMoney(g.getGoods().getPrice() - ticketMoney);
			} else if (orderType.equals("8")) {
				order = (ProductOrder45) service.load(ProductOrder45.class, id);
				ProductOrder45 p = (ProductOrder45) service.load(ProductOrder45.class, id);
				trade = p.getProduct().getName();
				if (ticketId != null)
					order.setTicketId(Long.valueOf(ticketId));
				if(order.getTicketId() != null && order.getTicketId() > 0){
					MemberTicket mt = (MemberTicket) service.load(MemberTicket.class, order.getTicketId());
					ticketMoney = mt.getTicket().getPrice();
				}
				order.setOrderMoney(p.getProduct().getPrice() - ticketMoney);
			}
			JSONObject obj = new JSONObject();
			if (order.getOrderStartTime().getTime() < new Date().getTime()) {
				order.setOrderStartTime(new Date());
			}
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
				orderSign = PayUtils.orderSign(request, response, order, payType, trade);
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
	
	
	
	
	
}