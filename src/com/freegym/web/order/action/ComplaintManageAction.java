package com.freegym.web.order.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.freegym.web.OrderBasicAction;
import com.freegym.web.basic.Member;
import com.freegym.web.order.ActiveOrder;
import com.freegym.web.order.Complaint;
import com.freegym.web.order.CourseOrder;
import com.freegym.web.order.CreateNo;
import com.freegym.web.order.FactoryOrder;
import com.freegym.web.order.PlanOrder;
import com.freegym.web.order.ProductOrder;
import com.sanmen.web.core.common.LogicException;

@Namespace("")
@InterceptorRefs({ @InterceptorRef("foreStack") })
@Results({ @Result(name = "complaint_list", location = "/order/complaint_list.jsp"), @Result(name = "complaint_show", location = "/order/complaint_show.jsp"),
		@Result(name = "complaint_index", location = "/order/index.jsp"), @Result(name = "complaint_edit", location = "/order/complaint_edit.jsp") })
public class ComplaintManageAction extends OrderBasicAction {

	private static final long serialVersionUID = -3769475961884283745L;

	private Complaint complaint, query;

	/**
	 * 投诉类型1:我收到的投诉2：我发起的投诉
	 */
	private String type;

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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String goComplaint() {
		if (type.equals("1")) {
			edit();
		} else if (type.equals("2")) {
			query = new Complaint();
			query.setDelStatus("0");
			query();
		}
		return "complaint_index";
	}

	public String query() {
		final List<Object> params = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer("select * from (" + Complaint.getComplaintSql() + ") p where 1=1 ");
		if (type != null && !"".equals(type)) {
			if (type.equals("1")) {
				sql.append(" and p.memberTo = ?");
				params.add(this.toMember().getId());
			} else if (type.equals("2")) {
				sql.append(" and p.memberFrom = ?");
				params.add(this.toMember().getId());
			}
		}
		if (query != null && query.getType() != null) {
			sql.append(" and p.type = ?");
			params.add(query.getType().toString());
		}
		pageInfo.setPageSize(10);
		pageInfo = service.findPageBySql(sql.toString() + " order by p.compDate desc", pageInfo, params.toArray());
		session.setAttribute("spath", 4);
		return "complaint_list";
	}

	public String edit() {
		Member m = this.toMember();
		if (complaint == null)
			complaint = new Complaint();
		if (complaint.getType() != null && complaint.getOrderId() != null) {
			if (complaint.getType() == PRODUCT_TYPE_CARD) {
				ProductOrder order = (ProductOrder) service.load(ProductOrder.class, complaint.getOrderId());
				complaint.setOrder(order);
				if (order.getMember().getId().equals(m.getId())) {
					complaint.setMemberTo(order.getProduct().getMember());
					complaint.setTelFrom(m.getMobilephone());
					// complaint.setTelTo(order.getProduct().getMember().getMobilephone());
					complaint.setEmailFrom(m.getEmail());
				} else {
					complaint.setMemberTo(order.getMember());
					complaint.setTelFrom(m.getMobilephone());
					// complaint.setTelTo(order.getMember().getMobilephone());
					complaint.setEmailFrom(m.getEmail());
				}
			} else if (complaint.getType() == PRODUCT_TYPE_PLAN) {
				PlanOrder order = (PlanOrder) service.load(PlanOrder.class, complaint.getOrderId());
				complaint.setOrder(order);
				if (order.getMember().getId().equals(m.getId())) {
					complaint.setMemberTo(order.getPlanRelease().getMember());
					complaint.setTelFrom(m.getMobilephone());
					complaint.setEmailFrom(m.getEmail());
				} else {
					complaint.setMemberTo(order.getMember());
					complaint.setTelFrom(m.getMobilephone());
					complaint.setEmailFrom(m.getEmail());
				}
			} else if (complaint.getType() == PRODUCT_TYPE_FACTORY) {
				FactoryOrder factOrder = (FactoryOrder) service.load(FactoryOrder.class, complaint.getOrderId());
				complaint.setOrder(factOrder);
				if (factOrder.getMember().getId().equals(m.getId())) {
					complaint.setMemberTo(complaint.getOrder().getMember());
					complaint.setTelFrom(m.getMobilephone());
					complaint.setEmailFrom(m.getEmail());
				} else {
					complaint.setMemberTo(factOrder.getMember());
					complaint.setTelFrom(m.getMobilephone());
					complaint.setEmailFrom(m.getEmail());
				}
			} else if (complaint.getType() == PRODUCT_TYPE_COURSE) {
				CourseOrder courseOrder = (CourseOrder) service.load(CourseOrder.class, complaint.getOrderId());
				complaint.setOrder(courseOrder);
				if (courseOrder.getMember().getId().equals(m.getId())) {
					complaint.setMemberTo(courseOrder.getCourse().getMember());
					complaint.setTelFrom(m.getMobilephone());
					complaint.setEmailFrom(m.getEmail());
				} else {
					complaint.setMemberTo(courseOrder.getMember());
					complaint.setTelFrom(m.getMobilephone());
					complaint.setEmailFrom(m.getEmail());
				}
			} else {
				complaint.setOrder((ActiveOrder) service.load(ActiveOrder.class, complaint.getOrderId()));
				complaint.setMemberFrom(toMember());
				complaint.setTelFrom(getString(toMember().getTell(), toMember().getMobilephone()));
				complaint.setEmailFrom(toMember().getEmail());
				final Member creator = ((ActiveOrder) complaint.getOrder()).getActive().getCreator();
				complaint.setMemberTo(creator);
				complaint.setTelTo(getString(creator.getTell(), creator.getMobilephone()));
			}
		}
		session.setAttribute("spath", 4);
		return "complaint_edit";
	}

	public String save() {
		if (complaint != null) {
			complaint.setNo(CreateNo.createNo());
			complaint.setMemberFrom(this.toMember());
			complaint.setCompDate(new Date());
			if (file != null && file.getFile() != null) {
				complaint.setAffix(this.saveFile("picture"));
			}
			String msg = "";
			try {
				service.saveOrUpdateComplaint(complaint);
				msg = "ok";
			} catch (LogicException e) {
				msg = e.getMessage();
				log.error("error", e);
			}
			response(msg);
		}
		return null;
	}

	public String changeStatus() {
		if (complaint != null && complaint.getId() != null) {
			String status = complaint.getStatus().toString();
			complaint = (Complaint) service.load(Complaint.class, complaint.getId());
			if (type.equals("1")) {
				complaint.setToStatus(status);
			} else if (type.equals("2")) {
				complaint.setFromStatus(status);
			}
			String msg = "";
			try {
				service.saveOrUpdate(complaint);
				msg = "ok";
			} catch (LogicException e) {
				e.printStackTrace();
				msg = e.getMessage();
				log.error("error", e);
			}
			response(msg);
		}
		return null;
	}

	public String changeDelStatus() {
		if (complaint != null && complaint.getId() != null) {
			complaint = (Complaint) service.load(Complaint.class, complaint.getId());
			complaint.setStatus('2');
			complaint.setDelStatus("1");
			service.saveOrUpdate(complaint);
		}
		return query();
	}

	public String showComplaint() {
		if (complaint != null && complaint.getId() != null) {
			StringBuffer sql = new StringBuffer("select p.* from (" + Complaint.getComplaintSql() + ") p where p.complaintId = ? ");
			request.setAttribute("complaintList", service.queryForList(sql.toString(), complaint.getId()));
			// service.loadOrderObject(complaint);
		}
		return "complaint_show";
	}
}
