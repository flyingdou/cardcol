package com.freegym.web.plan;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import net.sf.json.JSONObject;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.freegym.web.basic.Member;
import com.freegym.web.order.PlanOrder;
import com.freegym.web.utils.DateUtils;
import com.sanmen.web.core.bean.CommonId;

@Entity
@Table(name = "tb_plan_release")
public class PlanRelease extends CommonId implements Cloneable {

	private static final long serialVersionUID = -5585034051724944964L;

	/**
	 * 发布计划会员（这里为教练，只为教练可以发布计划）
	 */
	@ManyToOne(targetEntity = Member.class)
	@JoinColumn(name = "member")
	private Member member;
	
	/**
	 *4.0计划针对会员（并非只有他能看到）
	 */
	@ManyToOne(targetEntity = Member.class)
	@JoinColumn(name = "plan_participant")
	private Member plan_participant;

	/**
	 * 计划名称
	 */
	@Column(name = "plan_name", length = 100)
	private String planName;

	/**
	 * 计划类型A.瘦身减重 B.健美增肌 C.运动康复 D.提高运动表现
	 */
	@Column(name = "plan_type", length = 1)
	private String planType;

	/**
	 * 适用场景。A.办公室 B.健身房 C.家庭 D.户外
	 */
	@Column(name = "apply_scene", length = 20)
	private String scene;

	/**
	 * 适用对象.A.初级 B.中级 C.高级
	 */
	@Column(name = "apply_object", length = 1)
	private String applyObject;

	/**
	 * 所需器材
	 */
	@Column(name = "apparatuses", length = 255)
	private String apparatuses;

	
	/**
	 * 保存范围从
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "start_date")
	private Date startDate;

	/**
	 * 保存范围到
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "end_date")
	private Date endDate;

	/**
	 * 销售价格
	 */
	@Column(name = "unit_price", precision = 18, scale = 2)
	private Double unitPrice;

	/**
	 * 计划简介
	 */
	@Column(name = "briefing", length = 255)
	private String briefing;

	/**
	 * 计划详情
	 */
	@Lob
	@Column(name = "details")
	private String details;
	/**
	 * 计划图片一(168*184)
	 */
	@Column(name = "image1", length = 50)
	private String image1;

	/**
	 * 计划图片二(210*184)
	 */
	@Column(name = "image2", length = 50)
	private String image2;
	/**
	 * 计划图片三(168*369)
	 */
	@Column(name = "image3", length = 50)
	private String image3;

	/**
	 * 状态，1为已审核，0或NULL为未审核
	 */
	@Column(name = "audit", length = 1)
	private String audit;

	/**
	 * 备注
	 */
	@Column(name = "remark", length = 50)
	private String remark;
	/**
	 * 发布时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "publish_time")
	private Date publishTime;

	@Column(name = "plan_day", length = 8)
	private Integer planDay;

	@Column(length = 1)
	private String isClose;// 关闭状态1：关闭2： 开启

	@Column(length = 1)
	@Temporal(TemporalType.TIMESTAMP)
	private Date topTime;// 置顶时间

	public Set<PlanOrder> getOrders() {
		return orders;
	}

	public void setOrders(Set<PlanOrder> orders) {
		this.orders = orders;
	}

	public PlanRelease() {
	}

	// 我的订单
	@OneToMany(targetEntity = PlanOrder.class, mappedBy = "planRelease", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
	private Set<PlanOrder> orders = new HashSet<PlanOrder>();

	public PlanRelease(Long pid) {
		setId(pid);
	}

	@Override
	public String getTableName() {
		return "计划发布表";
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public Member getPlan_participant() {
		return plan_participant;
	}

	public void setPlan_participant(Member plan_participant) {
		this.plan_participant = plan_participant;
	}

	public String getPlanName() {
		return planName;
	}

	public void setPlanName(String planName) {
		this.planName = planName;
	}

	public String getPlanType() {
		return planType;
	}

	public void setPlanType(String planType) {
		this.planType = planType;
	}

	public String getScene() {
		return scene;
	}

	public void setScene(String scene) {
		this.scene = scene;
	}

	public String getApplyObject() {
		return applyObject;
	}

	public void setApplyObject(String applyObject) {
		this.applyObject = applyObject;
	}

	public String getApparatuses() {
		return apparatuses;
	}

	public void setApparatuses(String apparatuses) {
		this.apparatuses = apparatuses;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Double getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
	}

	public String getBriefing() {
		return briefing;
	}

	public void setBriefing(String briefing) {
		this.briefing = briefing;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public String getImage1() {
		return image1;
	}

	public void setImage1(String image1) {
		this.image1 = image1;
	}

	public String getImage2() {
		return image2;
	}

	public void setImage2(String image2) {
		this.image2 = image2;
	}

	public String getImage3() {
		return image3;
	}

	public void setImage3(String image3) {
		this.image3 = image3;
	}

	public String getAudit() {
		return audit;
	}

	public void setAudit(String audit) {
		this.audit = audit;
	}

	public Date getPublishTime() {
		return publishTime;
	}

	public void setPublishTime(Date publishTime) {
		this.publishTime = publishTime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public static DetachedCriteria getCriteriaQuery(PlanRelease query) {
		final DetachedCriteria dc = DetachedCriteria.forClass(PlanRelease.class);
		// dc.createAlias("member", "m");
		if (query != null) {
			if (query.getPlanName() != null && !"".equals(query.getPlanName())) dc.add(Restrictions.like("planName", "%" + query.getPlanName() + "%"));
			if (query.getMember() != null && query.getMember().getId() != null) dc.add(Restrictions.or(
					Restrictions.like("member.nick", "%" + query.getMember().getName() + "%"),
					Restrictions.like("member.name", "%" + query.getMember().getName() + "%"),
					Restrictions.like("member.mobilephone", "%" + query.getMember().getName() + "%")));
			if (query.getPlan_participant() != null && query.getPlan_participant().getId() != null) dc.add(Restrictions.or(
					Restrictions.like("plan_participant.nick", "%" + query.getPlan_participant().getName() + "%"),
					Restrictions.like("plan_participant.name", "%" + query.getPlan_participant().getName() + "%"),
					Restrictions.like("plan_participant.mobilephone", "%" + query.getPlan_participant().getName() + "%")));
		}
		return dc;
	}

	public Integer getPlanDay() {
		return planDay;
	}

	public void setPlanDay(Integer planDay) {
		this.planDay = planDay;
	}

	public String getIsClose() {
		return isClose;
	}

	public void setIsClose(String isClose) {
		this.isClose = isClose;
	}

	public Date getTopTime() {
		return topTime;
	}

	public void setTopTime(Date topTime) {
		this.topTime = topTime;
	}

	public JSONObject toJson() {
		final JSONObject obj = new JSONObject();
		obj.accumulate("id", getId()).accumulate("memberId", getMember() == null ? getMember() : getMember().getId())
									 .accumulate("plan_participantId", getPlan_participant() == null ? getPlan_participant() : getPlan_participant().getId())		
				.accumulate("planName", getPlanName())
				.accumulate("planType", getPlanType()).accumulate("scene", getScene()).accumulate("applyObject", getApplyObject())
				.accumulate("apparatuses", getApparatuses()).accumulate("startDate", DateUtils.getDateTimeZone(getStartDate(), "yyyy-MM-dd"))
				.accumulate("endDate", DateUtils.getDateTimeZone(getEndDate(), "yyyy-MM-dd")).accumulate("unitPrice", getUnitPrice())
				.accumulate("briefing", getBriefing()).accumulate("details", getDetails()).accumulate("image1", getImage1()).accumulate("remark", getRemark())
				.accumulate("saleout", getOrders().size()).accumulate("publishTime", DateUtils.getDateTimeZone(getPublishTime(), "yyyy-MM-dd HH:mm:ss"))
				.accumulate("planDay", getPlanDay()).accumulate("isClose", getIsClose())
				.accumulate("owner", getPlan_participant()==null?getPlan_participant():getPlan_participant().getName());
		return obj;
	}

	/**
	 * 商务中心-健身计划，包括智能计划
	 * 
	 * @return
	 */
	public static String getPlanSql() {
		StringBuffer sb = new StringBuffer(
				"SELECT '3' planType,p.id planId,p.plan_name planName,p.unit_price price,p.publish_Time publishTime,p.audit audit,p.details details,p.member member,p.isClose isClose,p.topTime topTime,p.image1 image1 FROM tb_plan_release p ");
		sb.append(" UNION ALL (SELECT '6' planType,g.id planId,g.name planName,g.price price,'' publishTime,'1' audit, g.summary details,g.member member,g.isClose isClose,g.topTime topTime,g.image1 image1 FROM tb_goods g)");
		return sb.toString();
	}

	/**
	 * 健身计划列表，包含销量、评分等信息
	 * 
	 * @return
	 */
	public static String getPlanListSql() {
		StringBuffer sb = new StringBuffer(
				"SELECT a.*,CASE WHEN b.saleNum >0 THEN b.saleNum ELSE 0 END saleNum,CASE WHEN g.avgGrade >0 THEN g.avgGrade ELSE 0 END avgGrade ");
		sb.append("FROM tb_plan_release a LEFT JOIN (SELECT planrelease, COUNT(*) saleNum FROM tb_planrelease_order co WHERE co.status = '1' GROUP BY planrelease) b ON a.id = b.planrelease ");
		sb.append("LEFT JOIN(SELECT f.productId, f.orderType, ROUND(SUM(grade) / COUNT(*)) avgGrade FROM TB_MEMBER_APPRAISE c RIGHT JOIN (SELECT e.id productId, d.id orderId, '3' orderType FROM tb_planrelease_order d ");
		sb.append("RIGHT JOIN tb_plan_release e ON d.planrelease = e.id) f ON c.order_id = f.orderId AND c.order_type = f.orderType GROUP BY f.productId, f.orderType) g ON g.productId = a.id ");
		return sb.toString();

	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
}
