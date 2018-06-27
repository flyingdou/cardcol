package com.freegym.web.order;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.freegym.web.course.SignIn;
import com.freegym.web.plan.PlanRelease;
import com.freegym.web.utils.DateUtils;

@Entity
@Table(name = "TB_PlanRelease_ORDER")
public class PlanOrder extends Order {

	private static final long serialVersionUID = -7358255008331013507L;

	@ManyToOne(targetEntity = PlanRelease.class)
	@JoinColumn(name = "planRelease")
	private PlanRelease planRelease;// 订单商品

	private Integer reportDay;// 费用结算日

	@Column(length = 1)
	private Integer balanceTimes;// 已进入结算次数

	@Column(length = 1)
	private String isBreach;// 是否缴纳违约金:0未缴纳，1已缴纳

	@Column(length = 1)
	private String isDelay;// 是否延期合同:0未延期，1已延期

	private String planStartTime;//用户填写的日期

	// 订单交易记录
	@OneToMany(targetEntity = PlanOrderDetail.class, mappedBy = "order", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
	private Set<PlanOrderDetail> orderDetails = new HashSet<PlanOrderDetail>();

	// 签到记录
	@OneToMany(targetEntity = SignIn.class, mappedBy = "orderId", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
	private Set<SignIn> signIns = new HashSet<SignIn>();

	public Integer getReportDay() {
		return reportDay;
	}

	public void setReportDay(Integer reportDay) {
		this.reportDay = reportDay;
	}

	public Integer getBalanceTimes() {
		return balanceTimes;
	}

	public void setBalanceTimes(Integer balanceTimes) {
		this.balanceTimes = balanceTimes;
	}

	public String getIsBreach() {
		return isBreach;
	}

	public void setIsBreach(String isBreach) {
		this.isBreach = isBreach;
	}

	public String getIsDelay() {
		return isDelay;
	}

	public void setIsDelay(String isDelay) {
		this.isDelay = isDelay;
	}

	public Set<PlanOrderDetail> getOrderDetails() {
		return orderDetails;
	}

	public void setOrderDetails(Set<PlanOrderDetail> orderDetails) {
		this.orderDetails = orderDetails;
	}

	public Set<SignIn> getSignIns() {
		return signIns;
	}

	public void setSignIns(Set<SignIn> signIns) {
		this.signIns = signIns;
	}

	public static DetachedCriteria getCriteriaQuery(PlanOrder query) {
		final DetachedCriteria dc = DetachedCriteria.forClass(PlanOrder.class);
		dc.createAlias("Plan", "p");
		if (query != null) {
			if (query.getMember() != null) {
				if (query.getMember().getRole() != null && !"".equals(query.getMember().getRole())) {
					if (query.getMember().getRole().equals("M")) {
						dc.add(Restrictions.eq("member.id", query.getMember().getId()));
					} else if (query.getMember().getRole().equals("E")) {
						dc.add(Restrictions.or(Restrictions.eq("p.member.id", query.getMember().getId()),
								Restrictions.eq("member.id", query.getMember().getId())));
//						if (query.getPlan() != null && query.getPlan().getType() != null && !"".equals(query.getPlan().getType())) {
//							dc.add(Restrictions.eq("p.type", query.getPlan().getType()));
//						}
					} else if (query.getMember().getRole().equals("S")) {
						dc.add(Restrictions.or(Restrictions.eq("p.member.id", query.getMember().getId()),
								Restrictions.eq("member.id", query.getMember().getId())));
					}
				}
			}
			if (query.getStatus() != null && !"".equals(query.getStatus())) {
				dc.add(Restrictions.eq("status", query.getStatus()));
			}
			if (query.getStartDate() != null) {
				if (query.getEndDate() != null) {
					dc.add(Restrictions.between("orderDate", DateUtils.getStartDate(query.getStartDate()), DateUtils.getEndDate(query.getEndDate())));
				} else {
					dc.add(Restrictions.ge("orderDate", DateUtils.getStartDate(query.getStartDate())));
				}
			} else {
				if (query.getEndDate() != null) {
					dc.add(Restrictions.le("orderDate", DateUtils.getEndDate(query.getEndDate())));
				}
			}
			if (query.getNo() != null && !"".equals(query.getNo())) {
				dc.add(Restrictions.like("no", query.getNo()));
			}
		}
		return dc;
	}

	public static String getSQLWhere(PlanOrder query, List<Object> parms) {
		final StringBuffer sb = new StringBuffer("");
		if (query != null) {
			if (query.getNo() != null && !"".equals(query.getNo())) {
				sb.append(" and no like ?");
				parms.add("%" + query.getNo() + "%");
			}
			if (query.getStartDate() != null) {
				sb.append(" and orderDate = ?");
				parms.add(query.getStartDate());
			}
			if (query.getStatus() != null) {
				sb.append(" and status = ?");
				parms.add(query.getStatus());
			}
		}
		return sb.toString();
	}

	public PlanRelease getPlanRelease() {
		return planRelease;
	}

	public void setPlanRelease(PlanRelease planRelease) {
		this.planRelease = planRelease;
	}

	public String getPlanStartTime() {
		return planStartTime;
	}

	public void setPlanStartTime(String planStartTime) {
		this.planStartTime = planStartTime;
	}

	@Override
	public String getTableName() {
		// TODO Auto-generated method stub
		return null;
	}

}
