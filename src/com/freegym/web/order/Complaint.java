package com.freegym.web.order;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.freegym.web.basic.Member;
import com.sanmen.web.core.bean.CommonId;

@Entity
@Table(name = "tb_complaint")
public class Complaint extends CommonId {

	private static final long serialVersionUID = -7358255008331013507L;

	@Column(length = 14)
	private String no;// 投诉编号

	@Temporal(TemporalType.TIMESTAMP)
	private Date compDate;// 投诉时间

	/**
	 * 投诉订单类型1，商品订单，2，活动订单，3，计划订单，4，课程订单，5，场地订单，6，自动订单
	 */
	private Character type;

	/**
	 * 对象ID号，如果type为1则ID对应的为订单表，否则为活动表
	 */

	private Long orderId;// 连接商品订单表主键

	@ManyToOne(targetEntity = Member.class)
	@JoinColumn(name = "memberFrom")
	private Member memberFrom;// 投诉方

	@Column(length = 13)
	private String telFrom;

	@Column(length = 50)
	private String emailFrom;

	@ManyToOne(targetEntity = Member.class)
	@JoinColumn(name = "memberTo")
	private Member memberTo;// 被投诉方

	@Column(length = 13)
	private String telTo;

	@Column(length = 200)
	private String content;// 投诉内容

	/**
	 * 处理状态，0为未处理，1为处理中，2为已处理，下拉选择
	 */
	private Character status;

	/**
	 * 处理结果内容，录入
	 */
	@Lob
	@Basic
	private String processResult;

	@Column(length = 100)
	private String affix;// 投诉证据

	@Column(length = 1)
	private String fromStatus;// 投诉方是否同意处理结果0：不同意1：同意

	@Column(length = 1)
	private String toStatus;// 被投诉方是否同意处理结果0：不同意1：同意

	@Column(length = 1)
	private String delStatus;// 是否删除0：未删除1：已删除

	@Transient
	private Order order;

	@Transient
	private String orderNo, prodName;

	@Transient
	private Date orderDate;

	public String getDelStatus() {
		return delStatus;
	}

	public void setDelStatus(String delStatus) {
		this.delStatus = delStatus;
	}

	public String getFromStatus() {
		return fromStatus;
	}

	public void setFromStatus(String fromStatus) {
		this.fromStatus = fromStatus;
	}

	public String getToStatus() {
		return toStatus;
	}

	public void setToStatus(String toStatus) {
		this.toStatus = toStatus;
	}

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public Date getCompDate() {
		return compDate;
	}

	public void setCompDate(Date compDate) {
		this.compDate = compDate;
	}

	public Character getType() {
		return type;
	}

	public void setType(Character type) {
		this.type = type;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Member getMemberFrom() {
		return memberFrom;
	}

	public void setMemberFrom(Member memberFrom) {
		this.memberFrom = memberFrom;
	}

	public String getTelFrom() {
		return telFrom;
	}

	public void setTelFrom(String telFrom) {
		this.telFrom = telFrom;
	}

	public String getEmailFrom() {
		return emailFrom;
	}

	public void setEmailFrom(String emailFrom) {
		this.emailFrom = emailFrom;
	}

	public Member getMemberTo() {
		return memberTo;
	}

	public void setMemberTo(Member memberTo) {
		this.memberTo = memberTo;
	}

	public String getTelTo() {
		return telTo;
	}

	public void setTelTo(String telTo) {
		this.telTo = telTo;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Character getStatus() {
		return status;
	}

	public void setStatus(Character status) {
		this.status = status;
	}

	public String getProcessResult() {
		return processResult;
	}

	public void setProcessResult(String processResult) {
		this.processResult = processResult;
	}

	public String getAffix() {
		return affix;
	}

	public void setAffix(String affix) {
		this.affix = affix;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
		if (order != null) {
			this.setOrderNo(order.getNo());
			this.setOrderDate(order.getOrderDate());
			this.setOrderId(order.getId());
		}
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getProdName() {
		return prodName;
	}

	public void setProdName(String prodName) {
		this.prodName = prodName;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public static DetachedCriteria getCriteriaQuery(Complaint query) {
		DetachedCriteria dc = DetachedCriteria.forClass(Complaint.class);
		if (query != null) {
			if (query.getType() != null) {
				dc.add(Restrictions.eq("type", query.getType()));
			}
			if (query.getMemberFrom() != null) {
				if (query.getMemberFrom().getId() != null && !"".equals(query.getMemberFrom().getId())) {
					dc.add(Restrictions.eq("memberFrom.id", query.getMemberFrom().getId()));
				}
			}
			if (query.getMemberTo() != null) {
				if (query.getMemberTo().getId() != null && !"".equals(query.getMemberTo().getId())) {
					dc.add(Restrictions.eq("memberTo.id", query.getMemberTo().getId()));
				}
			}
			if (query.getDelStatus() != null && !"".equals(query.getDelStatus())) {
				dc.add(Restrictions.eq("delStatus", query.getDelStatus()));
			}
		}
		return dc;
	}

	public static String getComplaintSql() {
		final StringBuffer sql = new StringBuffer();
		sql.append("select c.id complaintId,c.no complaintNo,c.compDate,c.memberFrom,c.memberTo,c.type,c.status,c.content,c.affix,c.processResult,t.orderTypeName,t.payNo orderNo,m1.name memberFromName,m2.name memberToName from tb_complaint c left join ("
				+ Order.getOrderSql() + ") t on c.orderId = t.id and c.type = t.type ");
		sql.append("left join tb_member m1 on c.memberFrom = m1.id left join tb_member m2 on c.memberTo = m2.id ");
		return sql.toString();
	}

	@Override
	public String getTableName() {
		// TODO Auto-generated method stub
		return null;
	}
}
