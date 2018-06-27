package com.freegym.web.system.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.BeanUtils;

import com.cardcol.web.order.ProductOrder45;
import com.freegym.web.SystemBasicAction;
import com.freegym.web.basic.Member;
import com.freegym.web.order.Order;
import com.freegym.web.order.ProductOrder;
import com.sanmen.web.core.common.ParamBean;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Namespace("")
@Results({ @Result(name = "success", location = "/jsp/order/order.jsp") })
public class Order1ManageAction extends SystemBasicAction {

	private static final long serialVersionUID = -2297129011587931307L;

	private ProductOrder query;

	private ProductOrder45 order;

	public ProductOrder getQuery() {
		return query;
	}

	public void setQuery(ProductOrder query) {
		this.query = query;
	}

	public ProductOrder45 getOrder() {
		return order;
	}

	public void setOrder(ProductOrder45 order) {
		this.order = order;
	}

	@Override
	protected void executeQuery() {
		final String sql = "select * from (" + Order.getOrdersqlxx() + ") t where 1 = 1";
		final List<Object> parms = new ArrayList<Object>();
		final StringBuffer where = new StringBuffer();
		if (!isAdmin()) {
			where.append(" and toId in (select id from tb_member where city in (select name from tb_area where id in (" + getDataStr() + ")))");
		}
		where.append(ProductOrder.getSQLWhere(query, parms));
		final StringBuffer order = new StringBuffer();
		if (pageInfo.getOrder() == null) {
			pageInfo.setOrder("id");
			pageInfo.setOrderFlag("desc");
		}
		int indexOf = pageInfo.getOrder().indexOf("order1");
		if (indexOf >= 0) order.append(" order by ").append(pageInfo.getOrder().substring(indexOf + 6)).append(" ").append(pageInfo.getOrderFlag());
		else order.append(" order by ").append(pageInfo.getOrder()).append(" ").append(pageInfo.getOrderFlag());
		pageInfo = service.findPageBySql(sql + where.toString() + " order by t.orderDate desc", pageInfo, Order.class, parms.toArray());
	}

	@Override
	protected Long executeSave() {
		if (order != null) {
			if (!StringUtils.isBlank(jsons)) {
				final JSONArray jarr = JSONArray.fromObject(jsons);
				final List<Order> orders = new ArrayList<Order>();
				for (final Iterator<?> it = jarr.iterator(); it.hasNext();) {
					final JSONObject obj = (JSONObject) it.next();
					final ProductOrder45 o45 = new ProductOrder45();
					BeanUtils.copyProperties(order, o45);
					o45.setMember(new Member(obj.getLong("id")));

					o45.setPayNo(service.getKeyNo("", "TB_PRODUCT_ORDER_V45", 28));
					o45.setNo(service.getKeyNo("", "TB_PRODUCT_ORDER_V45", 14));
					o45.setOrderDate(new Date());
					o45.setStatus(ORDER_STATUS_PAID);
					o45.setPayTime(new Date());
					o45.setOrigin('A');
					orders.add(o45);
				}
				List<?> results = service.saveOrUpdate(orders);
				return ((Order) results.get(0)).getId();
			} else {
				order.setPayNo(service.getKeyNo("", "TB_PRODUCT_ORDER_V45", 28));
				order.setNo(service.getKeyNo("", "TB_PRODUCT_ORDER_V45", 14));
				order.setOrderDate(new Date());
				order.setStatus(ORDER_STATUS_PAID);
				order.setPayTime(new Date());
				order.setOrigin('A');
				order = (ProductOrder45) service.saveOrUpdate(order);
			}
			return order.getId();
		}
		return 0l;
	}

	/**
	 * 查看交易明细
	 */
	@Override
	protected List<?> findDetail() {
		return service.findObjectBySql("from OrderDetail od where od.order.id = ?", id);
	}

	@Override
	protected String getExclude() {
		return "pticklings,roduct,coach,coachMembers,friends,clubs,coachs,members,visitors,setting,times,targets,products,integrals,certificates,courses,alwaysAddrs,applys,records,coursess,shops,courseCoachs,courseMembers,allFriends,prods,appraises,appraises1,orderDetails,orderDetails1,messages,messages1,orders,prodOrders,addrs,signIns,signIns1,shops,accounts,tickets";
	}

	public void findStatus() {
		final StringBuffer jsons = new StringBuffer();
		try {
			final List<ParamBean> params = new ArrayList<ParamBean>();
			params.add(new ParamBean(0, "未付款"));
			params.add(new ParamBean(1, "已付款"));
			params.add(new ParamBean(2, "已结束"));
			params.add(new ParamBean(3, "已结算"));
			jsons.append("{success: true, items: " + getJsonString(params) + "}");
		} catch (Exception e) {
			log.error("error", e);
			jsons.append("{success: false, message: '" + e.getMessage() + "'}");
		} finally {
			response(jsons.toString());
		}
	}

}
