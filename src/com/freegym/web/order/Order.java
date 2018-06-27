package com.freegym.web.order;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.freegym.web.basic.Member;
import com.sanmen.web.core.bean.CommonId;

@MappedSuperclass
public class Order extends CommonId {

	private static final long serialVersionUID = -7358255008331013507L;

	@Column(length = 14)
	private String no;// 订单编号

	@Temporal(TemporalType.TIMESTAMP)
	private Date orderDate;// 订单时间

	@ManyToOne(targetEntity = Member.class)
	@JoinColumn(name = "member")
	private Member member;// 收货人

	@Column(precision = 18, scale = 2)
	private Double unitPrice;// 单价

	private Integer count;// 数量

	@Column(precision = 18, scale = 2)
	private Double orderMoney;// 订单金额

	@Column(precision = 18, scale = 2)
	private Double contractMoney;// 合同金额

	private Integer integral;// 订单积分

	private Character status; // 0未确认，1履约中，2已完成，3已结算

	private String isappraise; // 1已评价

	@Temporal(TemporalType.TIMESTAMP)
	private Date orderStartTime;// 订单开始日期

	@Temporal(TemporalType.TIMESTAMP)
	private Date orderEndTime;// 订单结束日期

	@Temporal(TemporalType.DATE)
	private Date orderBalanceTime;// 订单结算日期

	/**
	 * 剩余金额，主要为其结算后的费用
	 */
	private Double surplusMoney;

	@ManyToOne(targetEntity = AlwaysAddr.class)
	@JoinColumn(name = "alwaysAddr")
	private AlwaysAddr alwaysAddr;// 收货人地址真实

	/**
	 * 订单来源A网站，B手机
	 */
	
	/**
	 * 订单来源 C：卡库app、D：卡库网站、E：E卡通app、F：E卡通网站
	 */
	private Character origin;

	@Column(length = 1)
	private String shipType;// 送货方式1：不需要配送，2：快递送货上门

	@Column(length = 1)
	private String shipTimeType;// 送货时间1：工作日，双休日，节假日均可
								// ，2：只双休日，节假日送货（工作日不送货）3:只工作日送货（双休日，节假日不送货）
	@Column(length = 1)
	private String payType;// 支付方式1：支付宝2:银行支付

	@Column(length = 50)
	private String payEmail;// 购买者支付宝账号

	@Column(length = 28)
	private String payNo;// 支付订单号

	/**
	 * 支付宝徽信交易号
	 */
	@Column(name = "TRADE_NO", length = 50)
	private String tradeNo;

	@Column(precision = 18, scale = 2)
	private Double payServiceCostE;// 交易服务费比率

	@Column(precision = 18, scale = 2)
	private Double payServiceCostM;// 交易手续费比率

	/**
	 * 优惠券金额
	 */

	@Column(name = "ticket_amount", precision = 10, scale = 2)
	private Double ticketAmount;

	private Long ticketId;

	/**
	 * 付款时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "pay_time")
	private Date payTime;

	@Transient
	private Date startDate;// 查询用开始时间

	@Transient
	private Date endDate;// 查询用开始时间

	@Transient
	private Long fromId, toId;

	@Transient
	private String fromName, toName, orderType, name, proType;

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public Double getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public Double getOrderMoney() {
		return orderMoney;
	}

	public void setOrderMoney(Double orderMoney) {
		this.orderMoney = orderMoney;
	}

	public Double getContractMoney() {
		return contractMoney;
	}

	public void setContractMoney(Double contractMoney) {
		this.contractMoney = contractMoney;
	}

	public Integer getIntegral() {
		return integral;
	}

	public void setIntegral(Integer integral) {
		this.integral = integral;
	}

	public Character getStatus() {
		return status;
	}

	public void setStatus(Character status) {
		this.status = status;
	}

	public String getIsappraise() {
		return isappraise;
	}

	public void setIsappraise(String isappraise) {
		this.isappraise = isappraise;
	}

	public Date getOrderStartTime() {
		return orderStartTime;
	}

	public void setOrderStartTime(Date orderStartTime) {
		this.orderStartTime = orderStartTime;
	}

	public Date getOrderEndTime() {
		return orderEndTime;
	}

	public void setOrderEndTime(Date orderEndTime) {
		this.orderEndTime = orderEndTime;
	}

	public Date getOrderBalanceTime() {
		return orderBalanceTime;
	}

	public void setOrderBalanceTime(Date orderBalanceTime) {
		this.orderBalanceTime = orderBalanceTime;
	}

	public Double getSurplusMoney() {
		return surplusMoney;
	}

	public void setSurplusMoney(Double surplusMoney) {
		this.surplusMoney = surplusMoney;
	}

	public AlwaysAddr getAlwaysAddr() {
		return alwaysAddr;
	}

	public void setAlwaysAddr(AlwaysAddr alwaysAddr) {
		this.alwaysAddr = alwaysAddr;
	}

	public Character getOrigin() {
		return origin;
	}

	public void setOrigin(Character origin) {
		this.origin = origin;
	}

	public String getShipType() {
		return shipType;
	}

	public void setShipType(String shipType) {
		this.shipType = shipType;
	}

	public String getShipTimeType() {
		return shipTimeType;
	}

	public void setShipTimeType(String shipTimeType) {
		this.shipTimeType = shipTimeType;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String getTradeNo() {
		return tradeNo;
	}

	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}

	public String getPayEmail() {
		return payEmail;
	}

	public void setPayEmail(String payEmail) {
		this.payEmail = payEmail;
	}

	public String getPayNo() {
		return payNo;
	}

	public void setPayNo(String payNo) {
		this.payNo = payNo;
	}

	public Double getPayServiceCostE() {
		return payServiceCostE;
	}

	public void setPayServiceCostE(Double payServiceCostE) {
		this.payServiceCostE = payServiceCostE;
	}

	public Double getPayServiceCostM() {
		return payServiceCostM;
	}

	public void setPayServiceCostM(Double payServiceCostM) {
		this.payServiceCostM = payServiceCostM;
	}

	public Double getTicketAmount() {
		return ticketAmount;
	}

	public void setTicketAmount(Double ticketAmount) {
		this.ticketAmount = ticketAmount;
	}

	public Long getTicketId() {
		return ticketId;
	}

	public void setTicketId(Long ticketId) {
		this.ticketId = ticketId;
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

	public Long getFromId() {
		return fromId;
	}

	public void setFromId(Long fromId) {
		this.fromId = fromId;
	}

	public Long getToId() {
		return toId;
	}

	public void setToId(Long toId) {
		this.toId = toId;
	}

	public String getFromName() {
		return fromName;
	}

	public void setFromName(String fromName) {
		this.fromName = fromName;
	}

	public String getToName() {
		return toName;
	}

	public void setToName(String toName) {
		this.toName = toName;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getProType() {
		return proType;
	}

	public void setProType(String proType) {
		this.proType = proType;
	}

	public Date getPayTime() {
		return payTime;
	}

	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}

	public static String getOrderSql() {
		final StringBuffer sql = new StringBuffer();
		sql.append(
				"SELECT '活动订单' orderType, a.id, ifnull(a.ticket_amount, 0) ticket_amount, a.member AS fromId, c.name AS fromName, '' as receive, '2' as type, b.id as productId, b.name, '' as proType, b.creator AS toId, d.name as toName, a.no, a.payNo, a.orderDate, a.orderStartTime, a.orderEndTime, IFNULL(a.orderMoney, 0)  orderMoney, a.count, a.status, a.integral,a.isappraise,b.active_image as image FROM tb_active_order a LEFT JOIN tb_active b ON a.active = b.id LEFT JOIN tb_member c ON a.member = c.id left join tb_member d on b.creator = d.id where b.mode = 'A' ");
		sql.append(" UNION ALL ");
		sql.append(
				"SELECT '活动订单' orderType, a.id, ifnull(a.ticket_amount, 0) ticket_amount, a.member AS fromId, concat(d.name, '(', c.name , ')') AS fromName, '' as receive, '2' as type, b.id as productId, b.name, '' as proType, b.creator AS toId, e.name as toName, a.no, a.payNo, a.orderDate, a.orderStartTime, a.orderEndTime, IFNULL(a.orderMoney, 0)  orderMoney, a.count, a.status, a.integral, a.isappraise,b.active_image as image FROM tb_active_order a LEFT JOIN tb_active b ON a.active = b.id LEFT JOIN tb_active_team c ON a.team = c.id left join tb_member d ON a.member = d.id left join tb_member e on b.creator = e.id where b.mode = 'B'");
		sql.append(" UNION ALL ");
		sql.append(
				"SELECT '计划订单' orderType, a.id, ifnull(a.ticket_amount, 0) ticket_amount, a.member AS fromId, c.name as fromName, ifnull(e.name, '') as receive, '3' as type, b.id as productId, b.plan_name as name, '' as proType, b.member AS toId, d.name as toName, a.no, a.payNo, a.orderDate, a.orderStartTime, a.orderEndTime, IFNULL(a.orderMoney, 0)  orderMoney, a.count, a.status, a.integral,a.isappraise,b.image1 as image FROM TB_PlanRelease_ORDER a LEFT JOIN tb_plan_release b ON a.planRelease = b.id left join tb_member c on a.member = c.id left join tb_member d on b.member = d.id left join tb_always_addr e on a.alwaysAddr = e.id");
		sql.append(" UNION ALL ");
		sql.append(
				"SELECT '课程订单' orderType, a.id, ifnull(a.ticket_amount, 0) ticket_amount, a.member AS fromId, c.name as fromName, ifnull(e.name, '') as receive, '5' as type, b.id as productId, h.name, '' as proType, b.member AS toId, d.name as toName, a.no, a.payNo, a.orderDate, a.orderStartTime, a.orderEndTime, IFNULL(a.orderMoney, 0)  orderMoney, a.count, a.status, a.integral,a.isappraise,h.image as image FROM TB_CourseRelease_ORDER a LEFT JOIN TB_COURSE b ON a.course = b.id LEFT JOIN TB_COURSE_INFO h ON h.id = b.courseId LEFT JOIN tb_member c ON a.member = c.id LEFT JOIN tb_member d ON b.member = d.id LEFT JOIN tb_always_addr e ON a.alwaysAddr = e.id");
		sql.append(" UNION ALL ");
		sql.append(
				"SELECT '自动订单' orderType, a.id, ifnull(a.ticket_amount, 0) ticket_amount, a.member AS fromId,c.name AS fromName,IFNULL(e.name, '') AS receive, '6' as type, b.id AS productId,b.name,'' AS proType,b.member AS toId,d.name AS toName,a.no,a.payNo,a.orderDate,a.orderStartTime,a.orderEndTime,IFNULL(a.orderMoney, 0)  orderMoney,a.count,a.status,a.integral,a.isappraise,b.image1 as image FROM tb_goods_order a LEFT JOIN tb_goods b ON a.goods = b.id LEFT JOIN tb_member c ON a.member = c.id LEFT JOIN tb_member d ON b.member = d.id LEFT JOIN tb_always_addr e ON a.alwaysAddr = e.id ");
		sql.append(" UNION ALL ");
		sql.append(
				"SELECT '一卡通订单' orderType, a.id, IFNULL(a.ticket_amount, 0) ticket_amount, a.member fromId, c.name formName, '' AS receive, '8' AS TYPE,  b.id productId, b.prod_name, '' AS  proType, 1 AS toId, '卡库平台' AS toName, a.no, a.payNo, a.orderDate, a.orderStartTime, a.orderEndTime, IFNULL(a.orderMoney, 0) orderMoney, a.count, a.status, a.integral, a.isappraise,  b.prod_image image FROM tb_product_order_v45 a LEFT JOIN tb_product_v45 b ON a.order_product = b.id LEFT JOIN tb_member c ON a.member = c.id ");

		return sql.toString();
	}
	
	public static String getOrdersqlx() {
		final StringBuffer sql = new StringBuffer();
		sql.append(
				"SELECT '活动订单' orderType, a.id, ifnull(a.ticket_amount, 0) ticket_amount, a.member AS fromId, c.name AS fromName, '' as receive, '2' as type, b.id as productId, b.name, '' as proType, b.creator AS toId, d.name as toName, a.no, a.payNo, a.orderDate, a.orderStartTime, a.orderEndTime, IFNULL(a.orderMoney, 0)  orderMoney, a.count, a.status, a.integral,a.isappraise,b.active_image as image FROM tb_active_order a LEFT JOIN tb_active b ON a.active = b.id LEFT JOIN tb_member c ON a.member = c.id left join tb_member d on b.creator = d.id where b.mode = 'A' ");
		sql.append(" UNION ALL ");
		sql.append(
				"SELECT '活动订单' orderType, a.id, ifnull(a.ticket_amount, 0) ticket_amount, a.member AS fromId, concat(d.name, '(', c.name , ')') AS fromName, '' as receive, '2' as type, b.id as productId, b.name, '' as proType, b.creator AS toId, e.name as toName, a.no, a.payNo, a.orderDate, a.orderStartTime, a.orderEndTime, IFNULL(a.orderMoney, 0)  orderMoney, a.count, a.status, a.integral, a.isappraise,b.active_image as image FROM tb_active_order a LEFT JOIN tb_active b ON a.active = b.id LEFT JOIN tb_active_team c ON a.team = c.id left join tb_member d ON a.member = d.id left join tb_member e on b.creator = e.id where b.mode = 'B'");
		sql.append(" UNION ALL ");
		sql.append(
				"SELECT '计划订单' orderType, a.id, ifnull(a.ticket_amount, 0) ticket_amount, a.member AS fromId, c.name as fromName, ifnull(e.name, '') as receive, '3' as type, b.id as productId, b.plan_name as name, '' as proType, b.member AS toId, d.name as toName, a.no, a.payNo, a.orderDate, a.orderStartTime, a.orderEndTime, IFNULL(a.orderMoney, 0)  orderMoney, a.count, a.status, a.integral,a.isappraise,b.image1 as image FROM TB_PlanRelease_ORDER a LEFT JOIN tb_plan_release b ON a.planRelease = b.id left join tb_member c on a.member = c.id left join tb_member d on b.member = d.id left join tb_always_addr e on a.alwaysAddr = e.id");
		sql.append(" UNION ALL ");
		sql.append(
				"SELECT '课程订单' orderType, a.id, ifnull(a.ticket_amount, 0) ticket_amount, a.member AS fromId, c.name as fromName, ifnull(e.name, '') as receive, '5' as type, b.id as productId, h.name, '' as proType, b.member AS toId, d.name as toName, a.no, a.payNo, a.orderDate, a.orderStartTime, a.orderEndTime, IFNULL(a.orderMoney, 0)  orderMoney, a.count, a.status, a.integral,a.isappraise,h.image as image FROM TB_CourseRelease_ORDER a LEFT JOIN TB_COURSE b ON a.course = b.id LEFT JOIN TB_COURSE_INFO h ON h.id = b.courseId LEFT JOIN tb_member c ON a.member = c.id LEFT JOIN tb_member d ON b.member = d.id LEFT JOIN tb_always_addr e ON a.alwaysAddr = e.id");
		sql.append(" UNION ALL ");
		sql.append(
				"SELECT '自动订单' orderType, a.id, ifnull(a.ticket_amount, 0) ticket_amount, a.member AS fromId,c.name AS fromName,IFNULL(e.name, '') AS receive, '6' as type, b.id AS productId,b.name,'' AS proType,b.member AS toId,d.name AS toName,a.no,a.payNo,a.orderDate,a.orderStartTime,a.orderEndTime,IFNULL(a.orderMoney, 0)  orderMoney,a.count,a.status,a.integral,a.isappraise,b.image1 as image FROM tb_goods_order a LEFT JOIN tb_goods b ON a.goods = b.id LEFT JOIN tb_member c ON a.member = c.id LEFT JOIN tb_member d ON b.member = d.id LEFT JOIN tb_always_addr e ON a.alwaysAddr = e.id ");
		sql.append(" UNION ALL ");
		sql.append(
				"SELECT '一卡通订单' orderType, a.id, IFNULL(a.ticket_amount, 0) ticket_amount, a.member fromId, c.name formName, '' AS receive, '8' AS TYPE,  b.id productId, b.prod_name, '' AS  proType, 1 AS toId, '卡库平台' AS toName, a.no, a.payNo, a.orderDate, a.orderStartTime, a.orderEndTime, IFNULL(a.orderMoney, 0) orderMoney, a.count, a.status, a.integral, a.isappraise,  b.prod_image image FROM tb_product_order_v45 a LEFT JOIN tb_product_v45 b ON a.order_product = b.id LEFT JOIN tb_member c ON a.member = c.id ");
        sql.append(" UNION ALL ");
		sql.append(
				 "SELECT '私教套餐订单' orderType, a.id, IFNULL(a.ticket_amount, 0) ticket_amount, a.member fromId, c.name formName, '' AS receive, '1' AS TYPE,  b.id productId, b.name, '' AS  proType, b.member AS toId, x.name AS toName, a.no, a.payNo, a.orderDate, a.orderStartTime, a.orderEndTime, IFNULL(a.orderMoney, 0) orderMoney, a.count, a.status, a.integral, a.isappraise,  b.image1 image  FROM tb_product_order a LEFT JOIN tb_product b ON a.product = b.id LEFT JOIN tb_member c ON a.member = c.id left join tb_member x on x.id = b.member");
		return sql.toString();
	}
	
	public static String getOrdersqlxx() {
		final StringBuffer sql = new StringBuffer();
		sql.append(
				"SELECT '活动订单' orderType, a.id, ifnull(a.ticket_amount, 0) ticket_amount, a.member AS fromId, c.name AS fromName, '' as receive, '2' as type, b.id as productId, b.name, '' as proType, b.creator AS toId, d.name as toName, a.no, a.payNo, a.orderDate, a.orderStartTime, a.orderEndTime, IFNULL(a.orderMoney, 0)  orderMoney, a.count, a.status, a.integral,a.isappraise,b.active_image as image FROM tb_active_order a LEFT JOIN tb_active b ON a.active = b.id LEFT JOIN tb_member c ON a.member = c.id left join tb_member d on b.creator = d.id where b.mode = 'A' ");
		sql.append(" UNION ALL ");
		sql.append(
				"SELECT '活动订单' orderType, a.id, ifnull(a.ticket_amount, 0) ticket_amount, a.member AS fromId, concat(d.name, '(', c.name , ')') AS fromName, '' as receive, '2' as type, b.id as productId, b.name, '' as proType, b.creator AS toId, e.name as toName, a.no, a.payNo, a.orderDate, a.orderStartTime, a.orderEndTime, IFNULL(a.orderMoney, 0)  orderMoney, a.count, a.status, a.integral, a.isappraise,b.active_image as image FROM tb_active_order a LEFT JOIN tb_active b ON a.active = b.id LEFT JOIN tb_active_team c ON a.team = c.id left join tb_member d ON a.member = d.id left join tb_member e on b.creator = e.id where b.mode = 'B'");
		sql.append(" UNION ALL ");
		sql.append(
				"SELECT '计划订单' orderType, a.id, ifnull(a.ticket_amount, 0) ticket_amount, a.member AS fromId, c.name as fromName, ifnull(e.name, '') as receive, '3' as type, b.id as productId, b.plan_name as name, '' as proType, b.member AS toId, d.name as toName, a.no, a.payNo, a.orderDate, a.orderStartTime, a.orderEndTime, IFNULL(a.orderMoney, 0)  orderMoney, a.count, a.status, a.integral,a.isappraise,b.image1 as image FROM TB_PlanRelease_ORDER a LEFT JOIN tb_plan_release b ON a.planRelease = b.id left join tb_member c on a.member = c.id left join tb_member d on b.member = d.id left join tb_always_addr e on a.alwaysAddr = e.id");
		sql.append(" UNION ALL ");
		sql.append(
				"SELECT '课程订单' orderType, a.id, ifnull(a.ticket_amount, 0) ticket_amount, a.member AS fromId, c.name as fromName, ifnull(e.name, '') as receive, '5' as type, b.id as productId, h.name, '' as proType, b.member AS toId, d.name as toName, a.no, a.payNo, a.orderDate, a.orderStartTime, a.orderEndTime, IFNULL(a.orderMoney, 0)  orderMoney, a.count, a.status, a.integral,a.isappraise,h.image as image FROM TB_CourseRelease_ORDER a LEFT JOIN TB_COURSE b ON a.course = b.id LEFT JOIN TB_COURSE_INFO h ON h.id = b.courseId LEFT JOIN tb_member c ON a.member = c.id LEFT JOIN tb_member d ON b.member = d.id LEFT JOIN tb_always_addr e ON a.alwaysAddr = e.id");
		sql.append(" UNION ALL ");
		sql.append(
				"SELECT '自动订单' orderType, a.id, ifnull(a.ticket_amount, 0) ticket_amount, a.member AS fromId,c.name AS fromName,IFNULL(e.name, '') AS receive, '6' as type, b.id AS productId,b.name,'' AS proType,b.member AS toId,d.name AS toName,a.no,a.payNo,a.orderDate,a.orderStartTime,a.orderEndTime,IFNULL(a.orderMoney, 0)  orderMoney,a.count,a.status,a.integral,a.isappraise,b.image1 as image FROM tb_goods_order a LEFT JOIN tb_goods b ON a.goods = b.id LEFT JOIN tb_member c ON a.member = c.id LEFT JOIN tb_member d ON b.member = d.id LEFT JOIN tb_always_addr e ON a.alwaysAddr = e.id ");
		sql.append(" UNION ALL ");
		sql.append(
				"SELECT '一卡通订单' orderType, a.id, IFNULL(a.ticket_amount, 0) ticket_amount, a.member fromId, c.name formName, '' AS receive, '8' AS TYPE,  b.id productId, b.prod_name, '' AS  proType, 1 AS toId, '卡库平台' AS toName, a.no, a.payNo, a.orderDate, a.orderStartTime, a.orderEndTime, IFNULL(a.orderMoney, 0) orderMoney, a.count, a.status, a.integral, a.isappraise,  b.prod_image image FROM tb_product_order_v45 a LEFT JOIN tb_product_v45 b ON a.order_product = b.id LEFT JOIN tb_member c ON a.member = c.id ");
        sql.append(" UNION ALL ");
		sql.append(
				 "SELECT '商品订单' orderType, a.id, IFNULL(a.ticket_amount, 0) ticket_amount, a.member fromId, c.name formName, '' AS receive, '1' AS TYPE,  b.id productId, b.name, '' AS  proType, b.member AS toId, x.name AS toName, a.no, a.payNo, a.orderDate, a.orderStartTime, a.orderEndTime, IFNULL(a.orderMoney, 0) orderMoney, a.count, a.status, a.integral, a.isappraise,  b.image1 image  FROM tb_product_order a LEFT JOIN tb_product b ON a.product = b.id LEFT JOIN tb_member c ON a.member = c.id left join tb_member x on x.id = b.member");
		return sql.toString();
	}

	/*
	 * public static String getOrderSqls() { final StringBuffer sql = new
	 * StringBuffer(); sql. append(
	 * "SELECT '活动订单' orderTypeName, a.id, ifnull(a.ticket_amount, 0) ticket_amount, a.member AS fromId, c.name AS fromName, '' as receive, '2' as type, (SELECT NAME FROM tb_parameter WHERE parent = 21 AND CODE = '2') AS orderType, b.id as productId, b.name, '' as proType, b.creator AS toId, d.name as toName, a.no, a.payNo, a.orderDate, a.orderStartTime, a.orderEndTime, IFNULL(a.orderMoney, 0)  orderMoney, a.count, a.status, a.integral,a.isappraise,b.active_image as image FROM tb_active_order a LEFT JOIN tb_active b ON a.active = b.id LEFT JOIN tb_member c ON a.member = c.id left join tb_member d on b.creator = d.id where b.mode = 'A' "
	 * ); sql.append(" UNION ALL "); sql. append(
	 * "SELECT '商品订单' orderTypeName, a.id, ifnull(a.ticket_amount, 0) ticket_amount, a.member AS fromId, c.name as fromName, ifnull(e.name, '') as receive, '1' as type, (SELECT NAME FROM tb_parameter WHERE parent = 21 AND CODE = '1') as orderType, b.id as productId, b.name, b.proType, b.member AS toId, d.name as toName, a.no, a.payNo, a.orderDate, a.orderStartTime, a.orderEndTime, IFNULL(a.orderMoney, 0)  orderMoney, a.count, a.status, a.integral,a.isappraise,b.image1 as image FROM tb_product_order a LEFT JOIN tb_product b ON a.product = b.id left join tb_member c on a.member = c.id left join tb_member d on b.member = d.id left join tb_always_addr e on a.alwaysAddr = e.id"
	 * ); sql.append(" UNION ALL "); sql. append(
	 * "SELECT '计划订单' orderTypeName, a.id, ifnull(a.ticket_amount, 0) ticket_amount, a.member AS fromId, c.name as fromName, ifnull(e.name, '') as receive, '3' as type, (SELECT NAME FROM tb_parameter WHERE parent = 21 AND CODE = '3') as orderType, b.id as productId, b.plan_name as name, '' as proType, b.member AS toId, d.name as toName, a.no, a.payNo, a.orderDate, a.orderStartTime, a.orderEndTime, IFNULL(a.orderMoney, 0)  orderMoney, a.count, a.status, a.integral,a.isappraise,b.image1 as image FROM TB_PlanRelease_ORDER a LEFT JOIN tb_plan_release b ON a.planRelease = b.id left join tb_member c on a.member = c.id left join tb_member d on b.member = d.id left join tb_always_addr e on a.alwaysAddr = e.id"
	 * ); sql.append(" UNION ALL "); sql. append(
	 * "SELECT '课程订单' orderTypeName, a.id, ifnull(a.ticket_amount, 0) ticket_amount, a.member AS fromId, c.name as fromName, ifnull(e.name, '') as receive, '5' as type, (SELECT NAME FROM tb_parameter WHERE parent = 21 AND CODE = '5') as orderType, b.id as productId, h.name, '' as proType, b.member AS toId, d.name as toName, a.no, a.payNo, a.orderDate, a.orderStartTime, a.orderEndTime, IFNULL(a.orderMoney, 0)  orderMoney, a.count, a.status, a.integral,a.isappraise,h.image as image FROM TB_CourseRelease_ORDER a LEFT JOIN TB_COURSE b ON a.course = b.id LEFT JOIN TB_COURSE_INFO h ON h.id = b.courseId LEFT JOIN tb_member c ON a.member = c.id LEFT JOIN tb_member d ON b.member = d.id LEFT JOIN tb_always_addr e ON a.alwaysAddr = e.id"
	 * ); sql.append(" UNION ALL "); sql. append(
	 * "SELECT '自动订单' orderTypeName, a.id, ifnull(a.ticket_amount, 0) ticket_amount, a.member AS fromId,c.name AS fromName,IFNULL(e.name, '') AS receive, '6' as type, (SELECT NAME FROM tb_parameter WHERE parent = 21 AND CODE = '6') AS orderType,b.id AS productId,b.name,'' AS proType,b.member AS toId,d.name AS toName,a.no,a.payNo,a.orderDate,a.orderStartTime,a.orderEndTime,IFNULL(a.orderMoney, 0)  orderMoney,a.count,a.status,a.integral,a.isappraise,b.image1 as image FROM tb_goods_order a LEFT JOIN tb_goods b ON a.goods = b.id LEFT JOIN tb_member c ON a.member = c.id LEFT JOIN tb_member d ON b.member = d.id LEFT JOIN tb_always_addr e ON a.alwaysAddr = e.id "
	 * ); sql.append(" UNION ALL "); sql. append(
	 * "SELECT '一卡通订单' orderTypeName,a.id,ifnull(a.ticket_amount, 0) ticket_amount,a.member AS fromId,c. NAME AS fromName,'' AS receive,'7' AS type,(SELECT NAME FROM tb_parameter WHERE parent = 21 AND CODE = '7') AS orderType,b.id AS productId,b.PROD_NAME as NAME,b.PROD_TYPE as proType,'' as toId,''as toName,a.NO,a.payNo,a.orderDate,a.orderStartTime,a.orderEndTime,IFNULL(a.orderMoney, 0) orderMoney,a.count,a.STATUS,a.integral,a.isappraise,'' as image FROM tb_product_order_v45 a LEFT JOIN tb_product_v45 b ON a.ORDER_PRODUCT = b.id LEFT JOIN tb_member c ON a.member = c.id LEFT JOIN tb_always_addr e ON a.alwaysAddr =e.id  "
	 * ); return sql.toString(); }
	 */
	public static String getOrderSqls() {
		String sql = 
				  " SELECT a.id, a.orderDate, DATE_FORMAT(a.pay_time,'%Y-%c-%d') AS payTime,d. NAME AS toName,b. NAME,a.orderMoney,a. STATUS, a.member AS fromId FROM tb_active_order a LEFT JOIN tb_active b ON a.active = b.id  LEFT JOIN tb_member d ON b.creator = d.id WHERE b. MODE = 'A' "
				+ " UNION ALL "
				+ " SELECT a.id, a.orderDate, DATE_FORMAT(a.pay_time,'%Y-%c-%d') AS payTime, d. NAME AS toName,h. NAME, a.orderMoney, a. STATUS, a.member AS fromId FROM TB_CourseRelease_ORDER a LEFT JOIN TB_COURSE b ON a.course = b.id LEFT JOIN TB_COURSE_INFO h ON h.id = b.courseId LEFT JOIN tb_member d ON b.member = d.id "
				+ " UNION ALL "
				+ " SELECT a.id, a.orderDate, DATE_FORMAT(a.pay_time,'%Y-%c-%d') AS payTime, d. NAME AS toName,b. NAME, a.orderMoney, a. STATUS, a.member AS fromId FROM tb_goods_order a LEFT JOIN tb_goods b ON a.goods = b.id LEFT JOIN tb_member d ON b.member = d.id "
				+ " UNION ALL "
				+ " SELECT a.id, a.orderDate, DATE_FORMAT(a.pay_time,'%Y-%c-%d') AS payTime, '一卡通' AS toName, b.PROD_NAME as NAME,a.orderMoney, a. STATUS, a.member AS fromId FROM tb_product_order_v45 a LEFT JOIN tb_product_v45 b ON a.ORDER_PRODUCT = b.id LEFT JOIN tb_always_addr e ON a.alwaysAddr = e.id ";
		return sql;
	}

	public static String getProdSql(String type, Long memberId, String factoryMoney, String startDate) {
		StringBuffer sb = new StringBuffer();
		if ("1".equals(type)) {
			sb.append(
					"SELECT p.id pro_id,P.name pro_NAME,m.id member_id, m.role member_role,M.nick member_nick,'1' TYPE, (CASE WHEN (protype=4 or protype=5) THEN p.promiseCost ELSE p.cost END) PRICE ,'");
			sb.append(startDate);
			sb.append("' startDate FROM tb_PRODUCT P LEFT JOIN tb_member m ON P.member = m.id");
		} else if ("2".equals(type)) {
			sb.append(
					"SELECT a.id pro_id,a.name pro_NAME,m.id member_id, m.role member_role,M.nick member_nick,'2' TYPE, a.amerce_Money PRICE ,'");
			sb.append(startDate);
			sb.append("' startDate FROM tb_active a LEFT JOIN tb_member m ON a.creator = m.id");
		} else if ("3".equals(type)) {
			sb.append(
					"SELECT p.id pro_id,P.plan_name pro_NAME,m.id member_id, m.role member_role,M.nick member_nick,'3' TYPE, P.unit_PRICE PRICE ,'");
			sb.append(startDate);
			sb.append("' startDate FROM tb_PlAN_RELEASE P LEFT JOIN tb_member m ON p.member = m.id");
		} else if ("4".equals(type)) {
			sb.append(
					"SELECT fc.id pro_id,tf.name pro_NAME,m.id member_id, m.role member_role,M.nick member_nick,'4' TYPE,");
			sb.append(factoryMoney);
			sb.append(
					" price ,fc.planDate startDate FROM TB_MEMBER_FACTORY_COSTS fc LEFT JOIN TB_MEMBER_FACTORY tf ON fc.factory = tf.id LEFT JOIN tb_member m ON tf.club = m.id LEFT JOIN tb_member_friend f ON tf.club = f.member ");
			sb.append(" AND f.member = ");
			sb.append(memberId);
			sb.append(" AND f.type = '1'");
		} else if ("5".equals(type)) {
			sb.append(
					"SELECT c.id pro_id,ci.name pro_NAME,m.id member_id, m.role member_role,M.nick member_nick,'5' TYPE, CASE WHEN f.member IS NULL THEN c.hour_Price ELSE c.member_Price END price ,c.planDate startDate FROM tb_course c LEFT JOIN tb_member m ON c.member = m.id LEFT JOIN tb_member_friend f ON c.member = f.friend ");
			sb.append(" AND f.member = ");
			sb.append(memberId);
			sb.append(" AND f.type = '1' LEFT JOIN tb_course_info ci ON c.courseid=ci.id ");
		} else if ("6".equals(type)) {
			sb.append(
					"SELECT g.id pro_id,g.type pro_type,g.name pro_NAME,m.id member_id, m.role member_role,M.nick member_nick,'6' TYPE, g.PRICE PRICE ,'");
			sb.append(startDate);
			sb.append("' startDate FROM tb_goods g LEFT JOIN tb_member m ON g.member = m.id");
		}
		return sb.toString();

	}

	@Override
	public String getTableName() {
		return null;
	}
}
