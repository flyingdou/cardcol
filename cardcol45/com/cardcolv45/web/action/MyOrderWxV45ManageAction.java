package com.cardcolv45.web.action;

import java.text.SimpleDateFormat;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.cardcol.web.order.ProductOrder45;
import com.cardcol.web.utils.PayUtils;
import com.freegym.web.basic.Member;
import com.freegym.web.mobile.BasicJsonAction;
import com.freegym.web.order.ActiveOrder;
import com.freegym.web.order.CourseOrder;
import com.freegym.web.order.GoodsOrder;
import com.freegym.web.order.Order;
import com.sanmen.web.core.common.PageInfo;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Namespace("")
@InterceptorRefs({ @InterceptorRef("customStack") })
@Results({ @Result(name = "success", location = "/WX/login.jsp"),
		@Result(name = "myorder", location = "/wxv45/myorder.jsp") })
public class MyOrderWxV45ManageAction extends BasicJsonAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6869939103917681744L;

	@Override
	public String execute() {
		// TODO Test Data
		Member memberTest = new Member();
		memberTest.setId(Long.parseLong("9355"));
		request.getSession().setAttribute("member", memberTest);

		Member member = (Member) session.getAttribute("member");
		if (member == null) {
			return SUCCESS;
		} else {
			return findOrder();
		}
	}

	/**
	 * 订单查询 一次查出用户所有状态的订单
	 */
	public String findOrder() {
		// TODO Test Data

		Member member = (Member) session.getAttribute("member");
		PageInfo order0 = service.queryOrder(pageInfo, member.getId(), "0"); // 未确认
		PageInfo order1 = service.queryOrder(pageInfo, member.getId(), "1"); // 履约中
		PageInfo order2 = service.queryOrder(pageInfo, member.getId(), "2"); // 已完成

		JSONArray jsonArray0 = getJsonOrder(order0);
		JSONArray jsonArray1 = getJsonOrder(order1);
		JSONArray jsonArray2 = getJsonOrder(order2);

		request.setAttribute("pageInfo0", jsonArray0);
		request.setAttribute("pageInfo1", jsonArray1);
		request.setAttribute("pageInfo2", jsonArray2);

		return "myorder";
	}

	@SuppressWarnings("unchecked")
	private JSONArray getJsonOrder(PageInfo pageInfo) {
		JSONArray jsonArray = new JSONArray();

		for (Object order : pageInfo.getItems()) {
			LinkedHashMap<String, String> map = (LinkedHashMap<String, String>) order;
			JSONObject orderJson = new JSONObject();
			orderJson.accumulate("orderId", map.get("id"));
			orderJson.accumulate("orderNo", map.get("no"));
			orderJson.accumulate("orderName", map.get("name"));
			orderJson.accumulate("orderStartTime", map.get("orderStartTime"));
			orderJson.accumulate("orderPrice", map.get("orderMoney"));
			jsonArray.add(orderJson);
		}
		return jsonArray;
	}

	/**
	 * 未付款订单详情
	 */
	public String OrderDetail() {
		return uuid;
	}

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

	// 未付款订单付款接口
	public void payMent() {
		try {
			final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			String payType = request.getParameter("payType");
			String orderType = request.getParameter("orderType");
			String subject = request.getParameter("subject");
			Order order = null;
			if (orderType.equals("2")) {
				order = (Order) service.load(ActiveOrder.class, id);
			} else if (orderType.equals("5")) {
				order = (Order) service.load(CourseOrder.class, id);
			} else if (orderType.equals("6")) {
				order = (Order) service.load(GoodsOrder.class, id);
			} else if (orderType.equals("8")) {
				order = (Order) service.load(ProductOrder45.class, id);
			}
			String orderSign;

			orderSign = PayUtils.orderSign(request, response, order, payType, subject);

			JSONObject obj = new JSONObject().accumulate("success", true)
					.accumulate("order",
							new JSONObject().accumulate("id", order.getId()).accumulate("no", order.getNo())
									.accumulate("payNo", order.getPayNo())
									.accumulate("orderDate", sdf.format(order.getOrderDate())))
									.accumulate("signData", orderSign);

			response(obj);
		} catch (Exception e) {
			response(e);
		}
	}

	/**
	 * 更新未付款订单订单
	 */
	public void bepaidOrderDetail() {
		String no = request.getParameter("no");// 订单no
		Double actualMoney = new Double(request.getParameter("actualMoney"));// 实付金额
		List<?> list = service.findObjectBySql("from "
				+ (no.startsWith("22") ? "ProductOrder45"
						: no.startsWith("99") ? "ActiveOrder" : no.startsWith("44") ? "GoodsOrder" : "CourseOrder")
				+ " where no = ?", no);
		Order order = (Order) list.get(0);
		order.setOrderMoney(actualMoney);
		service.saveOrUpdate(order);
	}

}
