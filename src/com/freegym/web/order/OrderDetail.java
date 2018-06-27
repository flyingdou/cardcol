package com.freegym.web.order;

import java.util.Date;
import java.util.List;

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
public class OrderDetail extends CommonId {

	private static final long serialVersionUID = -7358255008331013507L;

	/**
	 * 交易者
	 */
	@ManyToOne(targetEntity = Member.class)
	@JoinColumn(name = "trans_user")
	private Member transUser;

	/**
	 * 被交易对象
	 */
	@ManyToOne(targetEntity = Member.class)
	@JoinColumn(name = "trans_object")
	private Member transObject;

	@Temporal(TemporalType.TIMESTAMP)
	private Date detailDate;// 交易时间

	// 可考虑拿掉，因为有付款方和收款方，更加这两个来确定
	private Character inOutType;// 收入支出类型1：收入2：支出

	private Character detailType;// 交易类型1：预付款2：保证金3：违约金4：缺勤费用5：训练费用6：交易服务费7:交易手续费8：超勤费用9:退款0:提现，A活动处罚金

	private Double detailMoney;// 交易金额

	/**
	 * 交易服务费
	 */
	@Column(name = "service_money", precision = 15, scale = 6)
	private Double serviceMoney;

	@Column(length = 1)
	private Integer balanceTimes;// 结算次数

	@Column(length = 200)
	private String remark;// 备注

	@Transient
	private Date startDate;// 查询用开始时间

	@Transient
	private Date endDate;// 查询用开始时间

	@Transient
	private String orderType;// 订单类型

	// 查询用
	@Transient
	private Order orderQuery;

	public Integer getBalanceTimes() {
		return balanceTimes;
	}

	public void setBalanceTimes(Integer balanceTimes) {
		this.balanceTimes = balanceTimes;
	}

	public Character getInOutType() {
		return inOutType;
	}

	public void setInOutType(Character inOutType) {
		this.inOutType = inOutType;
	}

	public Double getDetailMoney() {
		return detailMoney;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public void setDetailMoney(Double detailMoney) {
		this.detailMoney = detailMoney;
	}

	public Double getServiceMoney() {
		return serviceMoney;
	}

	public void setServiceMoney(Double serviceMoney) {
		this.serviceMoney = serviceMoney;
	}

	public Member getTransUser() {
		return transUser;
	}

	public void setTransUser(Member transUser) {
		this.transUser = transUser;
	}

	public Member getTransObject() {
		return transObject;
	}

	public void setTransObject(Member transObject) {
		this.transObject = transObject;
	}

	public Date getDetailDate() {
		return detailDate;
	}

	public void setDetailDate(Date detailDate) {
		this.detailDate = detailDate;
	}

	public Character getDetailType() {
		return detailType;
	}

	public void setDetailType(Character detailType) {
		this.detailType = detailType;
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

	public Order getOrderQuery() {
		return orderQuery;
	}

	public void setOrderQuery(Order orderQuery) {
		this.orderQuery = orderQuery;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public static String getTradeSql() {
		final StringBuffer sql = new StringBuffer();
		/*
		 * 更新SQL 1.更新 TYPE字段从 参数表 取类型“order_type_c"对应name 2.增加订单类型
		 * 健身计划订单、场地订单、课程订单、智能计划订单
		 */
		sql.append(
				"SELECT a.id, a.order_id AS orderId, e.member sellerId,(SELECT NAME FROM tb_parameter WHERE parent = 21 AND CODE = '1') AS typeName, b.no AS orderNo, b.orderMoney, b.surplusMoney, b.payNo,b.status, c.id AS trans_user_id, c.nick AS trans_user_name, d.id AS trans_object_id, d.nick AS trans_object_name, a.inOutType, a.detailType, a.detailMoney, a.detailDate, a.balanceTimes FROM tb_product_order_detail a LEFT JOIN tb_product_order b ON a.order_id = b.id LEFT JOIN tb_member c ON a.trans_user = c.id LEFT JOIN tb_member d ON a.trans_object = d.id LEFT JOIN tb_product e ON b.product = e.id");
		sql.append(" UNION ALL ");
		sql.append(
				"SELECT a.id, a.order_id AS orderId, e.creator sellerId,(SELECT NAME FROM tb_parameter WHERE parent = 21 AND CODE = '2') AS typeName, b.no AS orderNo, b.orderMoney, b.surplusMoney, b.payNo,b.status, c.id AS trans_user_id, c.nick AS trans_user_name, d.id AS trans_object_id, d.nick AS trans_object_name, a.inOutType, a.detailType, a.detailMoney, a.detailDate, a.balanceTimes FROM tb_active_order_detail a LEFT JOIN tb_active_order b ON a.order_id = b.id LEFT JOIN tb_member c ON a.trans_user = c.id LEFT JOIN tb_member d ON a.trans_object = d.id LEFT JOIN tb_active e ON b.active = e.id");
		sql.append(" UNION ALL ");
		sql.append(
				"SELECT a.id, a.order_id AS orderId, e.member sellerId,(SELECT NAME FROM tb_parameter WHERE parent = 21 AND CODE = '3') AS typeName, b.no AS orderNo, b.orderMoney, b.surplusMoney, b.payNo,b.status, c.id AS trans_user_id, c.nick AS trans_user_name, d.id AS trans_object_id, d.nick AS trans_object_name, a.inOutType, a.detailType, a.detailMoney, a.detailDate, a.balanceTimes FROM tb_planrelease_order_detail a LEFT JOIN tb_planrelease_order b ON a.order_id = b.id LEFT JOIN tb_member c ON a.trans_user = c.id LEFT JOIN tb_member d ON a.trans_object = d.id LEFT JOIN tb_plan_release e ON b.planRelease = e.id");
		sql.append(" UNION ALL ");
		sql.append(
				"SELECT a.id, a.order_id AS orderId, f.club sellerId,(SELECT NAME FROM tb_parameter WHERE parent = 21 AND CODE = '4') AS typeName, b.no AS orderNo, b.orderMoney, b.surplusMoney, b.payNo,b.status, c.id AS trans_user_id, c.nick AS trans_user_name, d.id AS trans_object_id, d.nick AS trans_object_name, a.inOutType, a.detailType, a.detailMoney, a.detailDate, a.balanceTimes FROM tb_factory_order_detail a LEFT JOIN tb_factory_order b ON a.order_id = b.id LEFT JOIN tb_member c ON a.trans_user = c.id LEFT JOIN tb_member d ON a.trans_object = d.id LEFT JOIN tb_member_factory_costs e ON b.factoryCosts = e.id LEFT JOIN tb_member_factory f ON e.factory = f.id");
		sql.append(" UNION ALL ");
		sql.append(
				"SELECT a.id, a.order_id AS orderId, e.member sellerId,(SELECT NAME FROM tb_parameter WHERE parent = 21 AND CODE = '5') AS typeName, b.no AS orderNo, b.orderMoney, b.surplusMoney, b.payNo,b.status, c.id AS trans_user_id, c.nick AS trans_user_name, d.id AS trans_object_id, d.nick AS trans_object_name, a.inOutType, a.detailType, a.detailMoney, a.detailDate, a.balanceTimes FROM tb_courserelease_order_detail a LEFT JOIN tb_courserelease_order b ON a.order_id = b.id LEFT JOIN tb_member c ON a.trans_user = c.id LEFT JOIN tb_member d ON a.trans_object = d.id LEFT JOIN tb_course_info e ON b.course = e.id ");
		sql.append(" UNION ALL ");
		sql.append(
				"SELECT a.id, a.order_id AS orderId, e.member sellerId,(SELECT NAME FROM tb_parameter WHERE parent = 21 AND CODE = '6') AS typeName, b.no AS orderNo, b.orderMoney, b.surplusMoney, b.payNo,b.status, c.id AS trans_user_id, c.nick AS trans_user_name, d.id AS trans_object_id, d.nick AS trans_object_name, a.inOutType, a.detailType, a.detailMoney, a.detailDate, a.balanceTimes FROM tb_goods_order_detail a LEFT JOIN tb_goods_order b ON a.order_id = b.id LEFT JOIN tb_member c ON a.trans_user = c.id LEFT JOIN tb_member d ON a.trans_object = d.id LEFT JOIN tb_goods e ON b.goods = e.id");

		return sql.toString();
	}

	public static String getTradeSqls() {
		final StringBuffer sql = new StringBuffer();
		/*
		 * 2017-02-03新增一卡通订单 更新SQL 1.更新 TYPE字段从 参数表 取类型“order_type_c"对应name
		 * 2.增加订单类型 健身计划订单、场地订单、课程订单、智能计划订单,一卡通订单
		 */
		sql.append(
				"SELECT a.id, a.order_id AS orderId, e.member AS sellerId,e.name AS prodName,(SELECT NAME FROM tb_parameter WHERE parent = 21 AND CODE = '1') AS typeName, b.no AS orderNo, b.orderMoney, b.surplusMoney, b.payNo,b.status, c.id AS trans_user_id, c.nick AS trans_user_name, d.id AS trans_object_id, d.nick AS trans_object_name, a.detailMoney, a.service_money,a.detailDate, a.balanceTimes FROM tb_product_order_detail a LEFT JOIN tb_product_order b ON a.order_id = b.id LEFT JOIN tb_member c ON a.trans_user = c.id LEFT JOIN tb_member d ON a.trans_object = d.id LEFT JOIN tb_product e ON b.product = e.id ");
		sql.append(" UNION ALL ");
		sql.append(
				"SELECT a.id, a.order_id AS orderId, e.creator sellerId,e.name AS prodName,(SELECT NAME FROM tb_parameter WHERE parent = 21 AND CODE = '2') AS typeName, b.no AS orderNo, b.orderMoney, b.surplusMoney, b.payNo,b.status, c.id AS trans_user_id, c.nick AS trans_user_name, d.id AS trans_object_id, d.nick AS trans_object_name, a.detailMoney, a.service_money,a.detailDate, a.balanceTimes FROM tb_active_order_detail a LEFT JOIN tb_active_order b ON a.order_id = b.id LEFT JOIN tb_member c ON a.trans_user = c.id LEFT JOIN tb_member d ON a.trans_object = d.id LEFT JOIN tb_active e ON b.active = e.id ");
		sql.append(" UNION ALL ");
		sql.append(
				"SELECT a.id, a.order_id AS orderId, e.member sellerId,e.plan_name AS prodName,(SELECT NAME FROM tb_parameter WHERE parent = 21 AND CODE = '3') AS typeName, b.no AS orderNo, b.orderMoney, b.surplusMoney, b.payNo,b.status, c.id AS trans_user_id, c.nick AS trans_user_name, d.id AS trans_object_id, d.nick AS trans_object_name, a.detailMoney,a.service_money, a.detailDate, a.balanceTimes FROM tb_planrelease_order_detail a LEFT JOIN tb_planrelease_order b ON a.order_id = b.id LEFT JOIN tb_member c ON a.trans_user = c.id LEFT JOIN tb_member d ON a.trans_object = d.id LEFT JOIN tb_plan_release e ON b.planRelease = e.id ");
		sql.append(" UNION ALL ");
		sql.append(
				"SELECT a.id, a.order_id AS orderId, f.club sellerId,f.name AS prodName,(SELECT NAME FROM tb_parameter WHERE parent = 21 AND CODE = '4') AS typeName, b.no AS orderNo, b.orderMoney, b.surplusMoney, b.payNo,b.status, c.id AS trans_user_id, c.nick AS trans_user_name, d.id AS trans_object_id, d.nick AS trans_object_name, a.detailMoney, a.service_money,a.detailDate, a.balanceTimes FROM tb_factory_order_detail a LEFT JOIN tb_factory_order b ON a.order_id = b.id LEFT JOIN tb_member c ON a.trans_user = c.id LEFT JOIN tb_member d ON a.trans_object = d.id LEFT JOIN tb_member_factory_costs e ON b.factoryCosts = e.id LEFT JOIN tb_member_factory f ON e.factory = f.id  ");
		sql.append(" UNION ALL ");
		sql.append(
				"SELECT a.id, a.order_id AS orderId, e.member sellerId,e.name AS prodName,(SELECT NAME FROM tb_parameter WHERE parent = 21 AND CODE = '5') AS typeName, b.no AS orderNo, b.orderMoney, b.surplusMoney, b.payNo,b.status, c.id AS trans_user_id, c.nick AS trans_user_name, d.id AS trans_object_id, d.nick AS trans_object_name, a.detailMoney, a.service_money,a.detailDate, a.balanceTimes FROM tb_courserelease_order_detail a LEFT JOIN tb_courserelease_order b ON a.order_id = b.id LEFT JOIN tb_member c ON a.trans_user = c.id LEFT JOIN tb_member d ON a.trans_object = d.id LEFT JOIN tb_course_info e ON b.course = e.id  ");
		sql.append(" UNION ALL ");
		sql.append(
				"SELECT a.id, a.order_id AS orderId, e.member sellerId,e.name AS prodName,(SELECT NAME FROM tb_parameter WHERE parent = 21 AND CODE = '6') AS typeName, b.no AS orderNo, b.orderMoney, b.surplusMoney, b.payNo,b.status, c.id AS trans_user_id, c.nick AS trans_user_name, d.id AS trans_object_id, d.nick AS trans_object_name, a.detailMoney, a.service_money,a.detailDate, a.balanceTimes FROM tb_goods_order_detail a LEFT JOIN tb_goods_order b ON a.order_id = b.id LEFT JOIN tb_member c ON a.trans_user = c.id LEFT JOIN tb_member d ON a.trans_object = d.id LEFT JOIN tb_goods e ON b.goods = e.id ");
		sql.append(" UNION ALL ");
		sql.append(
				"SELECT a.id, a.order_id AS orderId, '' as sellerId,e.PROD_NAME AS prodName, (SELECT NAME FROM tb_parameter WHERE parent = 21 AND CODE = '7' ) AS typeName, b. NO AS orderNo, b.orderMoney, b.surplusMoney, b.payNo, b.STATUS, c.id AS trans_user_id, c.nick AS trans_user_name, d.id AS trans_object_id, d.nick AS trans_object_name, a.BALANCE_MONEY as detailMoney, a.BALANCE_SERVICE as service_money,a.BALANCE_TIME as detailDate, '1' AS balanceTimes FROM tb_order_balance_v45 a LEFT JOIN tb_product_order_v45 b ON a.ORDER_ID = b.id LEFT JOIN tb_member c ON a.BALANCE_FROM = c.id LEFT JOIN tb_member d ON a.BALANCE_TO = d.id LEFT JOIN tb_product_v45 e ON b.ORDER_PRODUCT = e.id ");
		return sql.toString();
	}

	public static StringBuffer getWhere(ProductOrderDetail query, String type, List<Object> parms) {
		final StringBuffer sb = new StringBuffer();
		if (query != null) {
			if (type != null && !"".equals(type) && "2".equals(type)) {
				if (query.getOrder().getNo() != null && !"".equals(query.getOrder().getNo())) {
					sb.append(" and orderNo like ?");
					parms.add("%" + query.getOrder().getNo().trim() + "%");
				}
			}
			if (query.getOrderType() != null && !query.getOrderType().equals("")) {
				sb.append(" and balanceType = ?");
				parms.add(query.getOrderType());
			}
			if (query.getStartDate() != null) {
				if (query.getEndDate() != null) {
					sb.append(" and (balanceTime between ? and ?)");
					parms.add(query.getStartDate());
					parms.add(query.getEndDate());
				} else {
					sb.append(" and balanceTime >= ?");
					parms.add(query.getStartDate());
				}
			} else {
				if (query.getEndDate() != null) {
					sb.append(" and balanceTime <= ?");
					parms.add(query.getEndDate());
				}
			}
		}

		System.out.println(sb.toString());
		return sb;

	}

	@Override
	public String getTableName() {
		return "交易明细表";
	}
}
