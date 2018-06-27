package com.freegym.web.order;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import net.sf.json.JSONObject;

import org.hibernate.annotations.OrderBy;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.freegym.web.basic.Member;
import com.sanmen.web.core.bean.CommonId;

@Entity
@Table(name = "TB_PRODUCT")
public class Product extends CommonId {

	private static final long serialVersionUID = -5009193341834034733L;

	@Column(length = 50)
	private String no;// 商品编号

	@Column(length = 200)
	private String name;// 商品名称/健身套餐/实物名称

	@Column(length = 1)
	private String type;// 商品类型 1.健身套餐 , 2.实物商品 , 3.定制收费 , 4.高级会员套餐 , 5.直播付费套餐

	@Column(length = 1)
	private String proType;// 套餐类型 1：圈存(时效) 2：圈存(计次) 3：圈存(储值) 4：对赌(次数) 5：对赌(频率) 6：预付卡

	@ManyToOne(targetEntity = Member.class)
	@JoinColumn(name = "member")
	private Member member;// 合同创建者

	@Temporal(TemporalType.DATE)
	private Date startTime;// 开始时间

	@Temporal(TemporalType.DATE)
	private Date endTime;// 结束时间

	private Integer num, num1, num2;// 训练次数

	private Integer wellNum;// 训练有效期限周/月

	@Column(length = 1)
	private String cardType;// 预付费套餐卡类型1：月卡2：次卡

	@Column(length = 1)
	private String isReg;// 资格是否记名1：记名2：不记名

	@Column(length = 1)
	private String assignType;// 是否需要转让费用1：需要2：不需要

	@Column(precision = 18, scale = 2)
	private Double assignCost;// 转让费用

	@Column(precision = 18, scale = 2)
	private Double dutyCost;// 缺勤费用

	@Column(precision = 18, scale = 2)
	private Double overCost;// 超过费用

	@Column(precision = 18, scale = 2)
	private Double cost, cost1, cost2;// 合同费用/商品费用

	private Integer reportDay;// 费用结算日

	@Column(precision = 18, scale = 2)
	private Double breachCost;// 违约费用

	@Column(precision = 18, scale = 2)
	private Double promiseCost;// 保证金

	private Integer delayDay;// 按月（计次）付费剩余/预付费延时服务剩余

	@Column(length = 1)
	private String promotionType;// 是否其他优惠促销活动同时使用1：可以2：不能

	@Column(length = 1)
	private String useType;// 使用范围类型1:限在本店2：其他店

	@Column(length = 200)
	private String useRange;// 其它使用范围

	@Column(length = 200)
	private String freeProjects;// 免费服务项目

	@Column(length = 100)
	private String freeProject;// 其它免费服务项目

	@Column(length = 200)
	private String costProjects;// 需收费项目

	@Column(length = 100)
	private String costProject;// 其它需收费项目

	@Column(length = 200)
	private String talkFunc;// 公告告知方式1:交易平台短消息2:电子邮件3:短信4：传真

	@Column(length = 200)
	private String remark;// 其它约定/描述

	@Column(length = 1)
	private String isClose;// 关闭状态1：关闭  2： 开启
	
	private Integer appraiseNum;//评价总人数
	
	@Column(length = 1)
	@Temporal(TemporalType.TIMESTAMP)
	private Date topTime;// 置顶时间

	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime;// 创建时间

	/**
	 * 审核状态
	 */
	private Character audit;// 审核状态0：待审核1： 审核通过

	// 我的购物车
	@OneToMany(targetEntity = Shop.class, mappedBy = "product", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
	private Set<Shop> shops = new HashSet<Shop>();

	// 我的订单
	@OneToMany(targetEntity = ProductOrder.class, mappedBy = "product", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
	private Set<ProductOrder> orders = new HashSet<ProductOrder>();
	
	/**
	 * 平均分
	 */
	@Column(name = "avgCount", length = 10)
	private Integer avgCount;
	
	/**
	 * 套餐图片
	 */
	@Column(name = "image1", length = 50)
	private String image1;
	
	/**
	 * 套餐图片
	 */
	@Column(name = "image2", length = 50)
	private String image2;
	
	/**
	 * 套餐图片
	 */
	@Column(name = "image3", length = 50)
	private String image3;
	
	/**
	 * 评分表
	 */
	@OneToMany(targetEntity = ProductGrade.class, fetch = FetchType.LAZY, mappedBy = "product", cascade = CascadeType.REMOVE)
	@OrderBy(clause = "appDate desc")
	private Set<ProductGrade> grades = new HashSet<ProductGrade>();
	
	public Product() {

	}

	public Product(long id) {
		setId(id);
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

	public Integer getAvgCount() {
		return avgCount;
	}

	public void setAvgCount(Integer avgCount) {
		this.avgCount = avgCount;
	}

	public Set<ProductGrade> getGrades() {
		return grades;
	}

	public void setGrades(Set<ProductGrade> grades) {
		this.grades = grades;
	}

	public String getAssignType() {
		return assignType;
	}

	public void setAssignType(String assignType) {
		this.assignType = assignType;
	}

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getProType() {
		return proType;
	}

	public void setProType(String proType) {
		this.proType = proType;
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public Integer getNum1() {
		return num1;
	}

	public void setNum1(Integer num1) {
		this.num1 = num1;
	}

	public Integer getNum2() {
		return num2;
	}

	public void setNum2(Integer num2) {
		this.num2 = num2;
	}

	public Integer getWellNum() {
		return wellNum;
	}

	public void setWellNum(Integer wellNum) {
		this.wellNum = wellNum;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public String getIsReg() {
		return isReg;
	}

	public void setIsReg(String isReg) {
		this.isReg = isReg;
	}

	public Double getAssignCost() {
		return assignCost;
	}

	public void setAssignCost(Double assignCost) {
		this.assignCost = assignCost;
	}

	public Double getDutyCost() {
		return dutyCost;
	}

	public void setDutyCost(Double dutyCost) {
		this.dutyCost = dutyCost;
	}

	public Double getOverCost() {
		return overCost;
	}

	public void setOverCost(Double overCost) {
		this.overCost = overCost;
	}

	public Double getCost() {
		return cost;
	}

	public void setCost(Double cost) {
		this.cost = cost;
	}

	public Double getCost1() {
		return cost1;
	}

	public void setCost1(Double cost1) {
		this.cost1 = cost1;
	}

	public Double getCost2() {
		return cost2;
	}

	public void setCost2(Double cost2) {
		this.cost2 = cost2;
	}

	public Integer getReportDay() {
		return reportDay;
	}

	public void setReportDay(Integer reportDay) {
		this.reportDay = reportDay;
	}

	public Double getBreachCost() {
		return breachCost;
	}

	public void setBreachCost(Double breachCost) {
		this.breachCost = breachCost;
	}

	public Double getPromiseCost() {
		return promiseCost;
	}

	public void setPromiseCost(Double promiseCost) {
		this.promiseCost = promiseCost;
	}

	public Integer getDelayDay() {
		return delayDay;
	}

	public void setDelayDay(Integer delayDay) {
		this.delayDay = delayDay;
	}

	public String getPromotionType() {
		return promotionType;
	}

	public void setPromotionType(String promotionType) {
		this.promotionType = promotionType;
	}

	public String getUseType() {
		return useType;
	}

	public void setUseType(String useType) {
		this.useType = useType;
	}

	public String getUseRange() {
		return useRange;
	}

	public void setUseRange(String useRange) {
		this.useRange = useRange;
	}

	public String getFreeProjects() {
		return freeProjects;
	}

	public void setFreeProjects(String freeProjects) {
		this.freeProjects = freeProjects;
	}

	public String getFreeProject() {
		return freeProject;
	}

	public void setFreeProject(String freeProject) {
		this.freeProject = freeProject;
	}

	public String getCostProjects() {
		return costProjects;
	}

	public void setCostProjects(String costProjects) {
		this.costProjects = costProjects;
	}

	public String getCostProject() {
		return costProject;
	}

	public void setCostProject(String costProject) {
		this.costProject = costProject;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

	public String getTalkFunc() {
		return talkFunc;
	}

	public void setTalkFunc(String talkFunc) {
		this.talkFunc = talkFunc;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Character getAudit() {
		return audit;
	}

	public void setAudit(Character audit) {
		this.audit = audit;
	}

	public Set<Shop> getShops() {
		return shops;
	}

	public void setShops(Set<Shop> shops) {
		this.shops = shops;
	}

	public Integer getAppraiseNum() {
		return appraiseNum;
	}

	public void setAppraiseNum(Integer appraiseNum) {
		this.appraiseNum = appraiseNum;
	}

	public Set<ProductOrder> getOrders() {
		return orders;
	}

	public void setOrders(Set<ProductOrder> orders) {
		this.orders = orders;
	}

	public static DetachedCriteria getCriteriaQuery(Product query) {
		final DetachedCriteria dc = DetachedCriteria.forClass(Product.class);
		if (query != null) {
			if (query.getNo() != null && !"".equals(query.getNo()))
				dc.add(Restrictions.like("no", "%" + query.getNo() + "%"));
			if (query.getName() != null && !"".equals(query.getName()))
				dc.add(Restrictions.like("name", "%" + query.getName() + "%"));
			if (query.getType() != null && !"".equals(query.getType())) {
				dc.add(Restrictions.eq("type", query.getType()));
			}
			if (query.getAudit() != null && !"".equals(query.getAudit())) {
				dc.add(Restrictions.eq("audit", query.getAudit()));
			}
			if (query.getIsClose() != null && !"".equals(query.getIsClose())) {
				dc.add(Restrictions.eq("isClose", query.getIsClose()));
			}
			if (query.getMember() != null) {
				if (query.getMember().getId() != null) {
					dc.add(Restrictions.eq("member.id", query.getMember()
							.getId()));
				}
			}
		}
		return dc;
	}

	@Override
	public String getTableName() {
		return "商品信息表";
	}
	
	public JSONObject toJson() {
		final JSONObject obj = new JSONObject();
		obj.accumulate("id", getId()).accumulate("name", getName()).accumulate("clubname", getMember().getName())
		.accumulate("proType", getProType()).accumulate("num", getNum()).accumulate("wellnum", getWellNum()).accumulate("dutyCost", getDutyCost())
		.accumulate("image1", getImage1()).accumulate("image2", getImage2()).accumulate("overCost", getOverCost()).accumulate("breachCost", getBreachCost())
		.accumulate("image3", getImage3()).accumulate("remark", getRemark()).accumulate("promiseCost", getPromiseCost()).accumulate("promotionType", getPromotionType())
		.accumulate("cost", getCost()).accumulate("cost1", getCost1()).accumulate("cost2", getCost2()).accumulate("buys", getOrders().size())
		.accumulate("freeProjects", getFreeProjects()).accumulate("freeProject", getFreeProject()).accumulate("costProjects", getCostProjects())
		.accumulate("costProject", getCostProject()).accumulate("talkFunc", getTalkFunc()).accumulate("isReg", getIsReg())
		.accumulate("assignCost", getAssignCost()).accumulate("useType", getUseType()).accumulate("useRange", getUseRange()).accumulate("delayDay", getDelayDay())
		.accumulate("cardType", getCardType()).accumulate("num1", getNum1()).accumulate("num2", getNum2()).accumulate("isClose", getIsClose());
		return obj;
	}

}
