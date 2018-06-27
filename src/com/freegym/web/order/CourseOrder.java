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
import com.freegym.web.plan.Course;
import com.freegym.web.utils.DateUtils;

@Entity
@Table(name = "TB_CourseRelease_ORDER")
public class CourseOrder extends Order {

	private static final long serialVersionUID = -7358255008331013507L;

	@ManyToOne(targetEntity = Course.class)
	@JoinColumn(name = "course")
	private Course course;// 订单商品
	
	// 开始时间
	private String starttime;
	// 结束时间
	private String endtime;

	private Integer reportDay;// 费用结算日

	private Integer balanceTimes;// 已进入结算次数

	@Column(length = 1)
	private String isBreach;// 是否缴纳违约金:0未缴纳，1已缴纳

	@Column(length = 1)
	private String isDelay;// 是否延期合同:0未延期，1已延期

	private String CourseStartTime;//用户填写的日期

	// 订单交易记录
	@OneToMany(targetEntity = CourseOrderDetail.class, mappedBy = "order", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
	private Set<CourseOrderDetail> orderDetails = new HashSet<CourseOrderDetail>();

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

	public Set<CourseOrderDetail> getOrderDetails() {
		return orderDetails;
	}

	public void setOrderDetails(Set<CourseOrderDetail> orderDetails) {
		this.orderDetails = orderDetails;
	}

	public Set<SignIn> getSignIns() {
		return signIns;
	}

	public void setSignIns(Set<SignIn> signIns) {
		this.signIns = signIns;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public String getStarttime() {
		return starttime;
	}

	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}

	public String getEndtime() {
		return endtime;
	}

	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}

	public static DetachedCriteria getCriteriaQuery(CourseOrder query) {
		final DetachedCriteria dc = DetachedCriteria.forClass(CourseOrder.class);
		dc.createAlias("Course", "p");
		if (query != null) {
			if (query.getMember() != null) {
				if (query.getMember().getRole() != null && !"".equals(query.getMember().getRole())) {
					if (query.getMember().getRole().equals("M")) {
						dc.add(Restrictions.eq("member.id", query.getMember().getId()));
					} else if (query.getMember().getRole().equals("E")) {
						dc.add(Restrictions.or(Restrictions.eq("p.member.id", query.getMember().getId()),
								Restrictions.eq("member.id", query.getMember().getId())));
//						if (query.getCourse() != null && query.getCourse().getType() != null && !"".equals(query.getCourse().getType())) {
//							dc.add(Restrictions.eq("p.type", query.getCourse().getType()));
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

	public static String getSQLWhere(CourseOrder query, List<Object> parms) {
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

	

	public String getCourseStartTime() {
		return CourseStartTime;
	}

	public void setCourseStartTime(String CourseStartTime) {
		this.CourseStartTime = CourseStartTime;
	}

	@Override
	public String getTableName() {
		// TODO Auto-generated method stub
		return null;
	}

}
