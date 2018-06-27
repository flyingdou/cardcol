package com.freegym.web.system.action;

import java.util.Date;
import java.util.Iterator;

import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.hibernate.criterion.DetachedCriteria;

import com.freegym.web.SystemBasicAction;
import com.freegym.web.basic.Member;
import com.freegym.web.course.Message;
import com.freegym.web.order.ActiveOrder;
import com.freegym.web.order.Complaint;
import com.freegym.web.order.CourseOrder;
import com.freegym.web.order.FactoryOrder;
import com.freegym.web.order.GoodsOrder;
import com.freegym.web.order.Order;
import com.freegym.web.order.PlanOrder;
import com.freegym.web.order.ProductOrder;

@Namespace("")
@Results({ @Result(name = "success", location = "/manager/order/complaint.jsp") })
public class Complaint1ManageAction extends SystemBasicAction {

	private static final long serialVersionUID = -2297129011587931307L;

	private Complaint complaint, query;

	public Complaint getComplaint() {
		return complaint;
	}

	public void setComplaint(Complaint complaint) {
		this.complaint = complaint;
	}

	public Complaint getQuery() {
		return query;
	}

	public void setQuery(Complaint query) {
		this.query = query;
	}

	@Override
	protected void executeQuery() {
		try {
			DetachedCriteria dc = Complaint.getCriteriaQuery(query);
			pageInfo = service.findPageByCriteria(dc, pageInfo);
			for (final Iterator<?> it = pageInfo.getItems().iterator(); it.hasNext();) {
				final Complaint c = (Complaint) it.next();
				c.setOrder(getOrderByType(c));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private Order getOrderByType(Complaint c) {
		if (c.getType() == PRODUCT_TYPE_CARD) return (ProductOrder) service.load(ProductOrder.class, c.getOrderId());
		else if (c.getType() == PRODUCT_TYPE_ACTIVE) return (ActiveOrder) service.load(ActiveOrder.class, c.getOrderId());
		else if (c.getType() == PRODUCT_TYPE_PLAN) return (PlanOrder) service.load(PlanOrder.class, c.getOrderId());
		else if (c.getType() == PRODUCT_TYPE_AUTO) return (GoodsOrder) service.load(GoodsOrder.class, c.getOrderId());
		else if (c.getType() == PRODUCT_TYPE_COURSE) return (CourseOrder) service.load(CourseOrder.class, c.getOrderId());
		else return (FactoryOrder) service.load(FactoryOrder.class, c.getOrderId());
	}

	@Override
	protected Long executeSave() {
		final Character status = complaint.getStatus();
		final String result = complaint.getProcessResult();
		complaint = (Complaint) service.load(Complaint.class, complaint.getId());
		complaint.setStatus(status);
		complaint.setProcessResult(result);
		service.saveOrUpdate(complaint);
		if (result.equals("2")) {
			final Message msgs = new Message();
			msgs.setContent("您的[" + complaint.getNo() + "]投诉已经处理完成，请确认同意。");
			msgs.setIsRead("0");
			msgs.setMemberFrom(new Member(1l));
			msgs.setMemberTo(complaint.getMemberFrom());
			msgs.setSendTime(new Date());
			msgs.setStatus("0");
			msgs.setType("2");
			final Message msgs1 = new Message();
			msgs1.setContent("您的[" + complaint.getNo() + "]投诉已经处理完成，请确认同意。");
			msgs1.setIsRead("0");
			msgs1.setMemberFrom(new Member(1l));
			msgs1.setMemberTo(complaint.getMemberTo());
			msgs1.setSendTime(new Date());
			msgs1.setStatus("0");
			msgs1.setType("2");
			service.saveOrUpdate(msgs);
			service.saveOrUpdate(msgs1);
			final Order o = getOrderByType(complaint);
			o.setStatus('1');
			service.saveOrUpdate(o);
		}
		return complaint.getId();
	}

	public void loadStatus() {
		response("[{id:'0',name:'未处理'},{id:'1', name: '处理中'}, {id: '2', name: '已处理'}]");
	}

	@Override
	protected String getExclude() {
		return "applyClubs,order45s,balanceFroms,balanceTos,ticklings,judges,courseGrades,order,ao,coach,coachMembers,friends,clubs,coachs,members,visitors,setting,times,products,certificates,integrals,courses,alwaysAddrs,applys,coursess,records,courseCoachs,courseMembers,prods,appraises,appraises1,allFriends,orderDetails,orderDetails1,messages,messages1,orders,addrs,signIns,signIns1,shops,accounts,prodOrders,aos,actives,partakes,teams,tickets";
	}
}
