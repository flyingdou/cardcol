package com.freegym.web.course;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.freegym.web.basic.Member;
import com.sanmen.web.core.bean.CommonId;

@MappedSuperclass
public class BaseAppraise extends CommonId {
	private static final long serialVersionUID = -6310372509022243002L;

	@ManyToOne(targetEntity = Member.class)
	@JoinColumn(name = "member")
	private Member member;// 评价方

	@Column(length = 20)
	private String title;// 标题或摘要

	@Column(length = 200)
	private String content;// 评价内容

	@Temporal(TemporalType.TIMESTAMP)
	private Date appDate;// 评价时间

	private Double grade;// 评分

	@Column(length = 200)
	private String reContent;// 回复评价内容

	@Temporal(TemporalType.TIMESTAMP)
	private Date reAppDate;// 回复评价时间

	@Column(length = 1)
	private String isRead; // 是否阅读0:未阅读，1：已阅读

	@Column(length = 50)
	// 上传图片
	private String image1;

	@Column(length = 50)
	// 上传图片
	private String image2;

	@Column(length = 50)
	// 上传图片
	private String image3;

	@Column(length = 1)
	private String isNApp; // 是否匿名评价0:不匿名评价，1:匿名评价

	@Column(name = "order_id")
	// 订单ID
	private Long orderId;

	@Column(name = "order_type", length = 20)
	// 订单类型 1:健身卡订单 2:活动订单 3:健身计划订单 4:场地订单 5:课程订单 6:智能计划订单
	private String orderType;

	public String getIsNApp() {
		return isNApp;
	}

	public void setIsNApp(String isNApp) {
		this.isNApp = isNApp;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
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

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getAppDate() {
		return appDate;
	}

	public void setAppDate(Date appDate) {
		this.appDate = appDate;
	}

	public Double getGrade() {
		return grade;
	}

	public void setGrade(Double grade) {
		this.grade = grade;
	}

	public String getReContent() {
		return reContent;
	}

	public void setReContent(String reContent) {
		this.reContent = reContent;
	}

	public Date getReAppDate() {
		return reAppDate;
	}

	public void setReAppDate(Date reAppDate) {
		this.reAppDate = reAppDate;
	}

	public String getIsRead() {
		return isRead;
	}

	public void setIsRead(String isRead) {
		this.isRead = isRead;
	}

	@Override
	public String getTableName() {
		return "";
	}

	/**
	 * 评价管理
	 * 
	 * @return
	 */
	public static String getAppraiseManageSql() {
		final StringBuffer sb = new StringBuffer();
		sb.append("SELECT '1' isAppraise,ma.id,ma.grade,ma.title,ma.content,ma.recontent,ma.image1,ma.appDate,m.id fromId,m.role fromRole,m.name fromNick,g.* ,m1.id toId,m1.role toRole,m1.name toNick ");
		sb.append(" FROM tb_member_appraise ma LEFT JOIN(");
		sb.append(" SELECT a.id productId,a.name productName,ao.id orderId,'1' orderType,ao.orderDate,a.member sellerId FROM tb_product_order ao LEFT JOIN tb_product a ON ao.product = a.id");
		sb.append(" UNION ALL SELECT b.id productId,b.name productName,bo.id orderId,'2' orderType,bo.orderDate,b.creator sellerId FROM tb_active_order bo LEFT JOIN tb_active b ON bo.active = b.id");
		sb.append(" UNION ALL SELECT c.id productId,c.plan_name productName,co.id orderId,'3' orderType,co.orderDate,c.member sellerId FROM tb_planrelease_order co LEFT JOIN tb_plan_release c  ON co.planrelease = c.id");
		sb.append(" UNION ALL SELECT d1.id productId,d2.name productName,cdo.id orderId,'4' orderType,cdo.orderDate,d2.club sellerId FROM tb_factory_order cdo LEFT JOIN tb_member_factory_costs d1 ON cdo.factoryCosts = d1.id LEFT JOIN tb_member_factory d2 ON d1.factory = d2.id");
		sb.append(" UNION ALL SELECT e1.id productId,e2.name productName,eo.id orderId,'5' orderType,eo.orderDate,e1.member sellerId FROM tb_courserelease_order eo LEFT JOIN tb_course e1 ON eo.course = e1.id LEFT JOIN tb_course_info e2 ON e1.courseId = e2.id");
		sb.append(" UNION ALL SELECT f.id productId,f.name productName,fo.id orderId,'6' orderType,fo.orderDate,f.member sellerId FROM tb_goods_order fo LEFT JOIN tb_goods f ON fo.goods=f.id");
		sb.append(" ) g ON g.orderType = ma.order_type AND g.orderId = ma.order_id LEFT JOIN tb_member m ON m.id = ma.member LEFT JOIN tb_member m1 ON m1.id = g.sellerId");
		return sb.toString();
	}

	/**
	 * 我的评价
	 * 
	 * @return
	 */
	public static String getMyAppraiseSql() {
		String orderSql = "SELECT a.id productId,a.name productName,a.image1 image,ao.id orderId,'1' orderType,ao.orderDate,a.member sellerId,ao.member memberId,ao.status status FROM tb_product_order ao LEFT JOIN tb_product a ON ao.product = a.id "
				+ "UNION ALL SELECT b.id productId,b.name productName,b.active_image image,bo.id orderId,'2' orderType,bo.orderDate,b.creator sellerId,bo.member memberId,bo.status status FROM tb_active_order bo LEFT JOIN tb_active b ON bo.active = b.id UNION ALL SELECT c.id productId,c.plan_name productName,c.image1 image,co.id orderId,'3' orderType,co.orderDate,c.member sellerId,co.member memberId,co.status status FROM tb_planrelease_order co LEFT JOIN tb_plan_release c  ON co.planrelease = c.id UNION ALL SELECT d1.id productId,d2.name productName,d3.param_image image,cdo.id orderId,'4' orderType,cdo.orderDate,d2.club sellerId,cdo.member memberId,cdo.status status FROM tb_factory_order cdo LEFT JOIN tb_member_factory_costs d1 ON cdo.factoryCosts = d1.id LEFT JOIN tb_member_factory d2 ON d1.factory = d2.id left join tb_parameter d3 on d2.project = d3.id UNION ALL SELECT e1.id productId,e2.name productName,e2.image,eo.id orderId,'5' orderType,eo.orderDate,e1.member sellerId,eo.member memberId,eo.status status FROM tb_courserelease_order eo LEFT JOIN tb_course e1 ON eo.course = e1.id LEFT JOIN tb_course_info e2 ON e1.courseId = e2.id UNION ALL SELECT f.id productId,f.name productName,f.image1 image,fo.id orderId,'6' orderType,fo.orderDate,f.member sellerId,fo.member memberId,fo.status status FROM tb_goods_order fo LEFT JOIN tb_goods f ON fo.goods=f.id ";
		final StringBuffer sb = new StringBuffer();
		sb.append("SELECT '1' isAppraise, ma.id, ma.grade, ma.title,ma.content, ma.recontent,  ma.image1,ma.image2,ma.image3, ma.appDate,  m.id fromId, m.role fromRole,m.name fromNick, g.*, m1.name sellerName, p.cnt");
		sb.append(" FROM tb_member_appraise ma LEFT JOIN ( ").append(orderSql);
		sb.append(" ) g ON g.orderType = ma.order_type AND g.orderId = ma.order_id LEFT JOIN tb_member m ON m.id = ma.member LEFT JOIN tb_member m1 ON m1.id = g.sellerId LEFT JOIN (");
		sb.append("	SELECT b.productId, b.orderType, COUNT(*) cnt FROM TB_MEMBER_APPRAISE a LEFT JOIN (").append(orderSql);
		sb.append(" ) b ON a.order_id = b.orderId AND a.order_type = b.orderType GROUP BY b.productId, b.orderType) p ON g.productId = p.productId AND g.orderType = p.orderType ");
		sb.append(" UNION ALL (");
		sb.append(" SELECT  '0' isAppraise,'' id,'' grade,'' title,'' content,'' recontent,'' image1,'' image2,'' image3,'' appDate, m.id fromId, m.role fromRole, m.name fromNick, g.*, m1.name sellerName, p.cnt");
		sb.append(" FROM (").append(orderSql);
		sb.append(" ) g LEFT JOIN tb_member m  ON m.id = g.memberId LEFT JOIN tb_member m1 on g.sellerId = m1.id LEFT JOIN tb_member_appraise a  ON g.orderId = a.order_id  AND g.orderType = a.order_type LEFT JOIN (");
		sb.append(" SELECT b.productId, b.orderType, COUNT(*) cnt FROM TB_MEMBER_APPRAISE a LEFT JOIN (").append(orderSql);
		sb.append(" ) b ON a.order_id = b.orderId AND a.order_type = b.orderType GROUP BY b.productId, b.orderType) p ON g.productId = p.productId AND g.orderType = p.orderType WHERE a.id IS NULL)");
		return sb.toString();
	}

	/**
	 * 查询服务项目的评价
	 * 
	 * @return
	 */
	public static String getcourseInfoAppraise() {
		final StringBuffer sb = new StringBuffer();
		sb.append("SELECT a.id, a.member,a.name, a.image,a.memo,case when b.appraiseNum is null then '0' else b.appraiseNum end appraiseNum, case when b.avg_grade is null then '0' else b.avg_grade end avg_grade FROM tb_course_info a LEFT JOIN (");
		sb.append("SELECT o.courseId, m.course, COUNT(*) appraiseNum,round(SUM(n.grade) / COUNT(*)) avg_grade FROM tb_course o RIGHT JOIN TB_CourseRelease_ORDER m ON o.id = m.course LEFT JOIN tb_member_appraise n ON m.id = n.order_id AND n.order_type = '5' GROUP BY o.courseId, m.course) b ON a.id = b.courseId");
		return sb.toString();
	}

	/**
	 * 查询商品的评价明细
	 * 
	 * @return
	 */
	public static String getAppraiseInfo() {
		final StringBuffer sb = new StringBuffer();
		sb.append("SELECT ma.grade grade, ma.title,ma.content, ma.recontent, ma.image1,ma.image2,ma.image3, ma.appDate, g.*,  p.cnt,p.goodNum,p.generalNum,p.badNum,p.picNum,m.id fromId,m.name fromName,m.image fromImage,m.role fromRole,p.avgGrade avgGrade,CONCAT(ROUND((p.goodNum/p.cnt)*100,2),'%') goodProp,CONCAT(ROUND((p.generalNum/p.cnt)*100,2),'%') generalProp,CONCAT(ROUND((p.badNum/p.cnt)*100,2),'%') badProp,c.saleNum ");
		sb.append(" FROM tb_member_appraise ma LEFT JOIN ( SELECT a.id productId,a.name productName,a.image1 image,ao.id orderId,'1' orderType,ao.orderDate,a.member sellerId,ao.member memberId FROM tb_product_order ao LEFT JOIN tb_product a ON ao.product = a.id UNION ALL SELECT b.id productId,b.name productName,b.active_image image,bo.id orderId,'2' orderType,bo.orderDate,b.creator sellerId,bo.member memberId FROM tb_active_order bo LEFT JOIN tb_active b ON bo.active = b.id UNION ALL SELECT c.id productId,c.plan_name productName,c.image1 image,co.id orderId,'3' orderType,co.orderDate,c.member sellerId,co.member memberId FROM tb_planrelease_order co LEFT JOIN tb_plan_release c  ON co.planrelease = c.id UNION ALL SELECT d1.id productId,d2.name productName,'' image,cdo.id orderId,'4' orderType,cdo.orderDate,d2.club sellerId,cdo.member memberId FROM tb_factory_order cdo LEFT JOIN tb_member_factory_costs d1 ON cdo.factoryCosts = d1.id LEFT JOIN tb_member_factory d2 ON d1.factory = d2.id UNION ALL SELECT e1.id productId,e2.name productName,e2.image,eo.id orderId,'5' orderType,eo.orderDate,e1.member sellerId,eo.member memberId FROM tb_courserelease_order eo LEFT JOIN tb_course e1 ON eo.course = e1.id LEFT JOIN tb_course_info e2 ON e1.courseId = e2.id UNION ALL SELECT f.id productId,f.name productName,f.image1 image,fo.id orderId,'6' orderType,fo.orderDate,f.member sellerId,fo.member memberId FROM tb_goods_order fo LEFT JOIN tb_goods f ON fo.goods=f.id");
		sb.append(" ) g ON g.orderType = ma.order_type  AND g.orderId = ma.order_id ");
		sb.append(" LEFT JOIN (SELECT b.productId, b.orderType, ROUND(SUM(grade)/COUNT(*)) avgGrade, COUNT(*) cnt, SUM(CASE WHEN a.grade>=80 THEN 1 ELSE 0 END) goodNum, SUM(CASE WHEN a.grade>=60 AND a.grade<80 THEN 1 ELSE 0 END) generalNum, SUM(CASE WHEN a.grade<60 THEN 1 ELSE 0 END) badNum,SUM(CASE WHEN a.image1 is not NULL THEN 1 ELSE 0 END) picNum FROM TB_MEMBER_APPRAISE a LEFT JOIN (SELECT a.id productId,a.name productName,a.image1 image,ao.id orderId,'1' orderType,ao.orderDate,a.member sellerId,ao.member memberId FROM tb_product_order ao LEFT JOIN tb_product a ON ao.product = a.id UNION ALL SELECT b.id productId,b.name productName,b.active_image image,bo.id orderId,'2' orderType,bo.orderDate,b.creator sellerId,bo.member memberId FROM tb_active_order bo LEFT JOIN tb_active b ON bo.active = b.id UNION ALL SELECT c.id productId,c.plan_name productName,c.image1 image,co.id orderId,'3' orderType,co.orderDate,c.member sellerId,co.member memberId FROM tb_planrelease_order co LEFT JOIN tb_plan_release c  ON co.planrelease = c.id UNION ALL SELECT d1.id productId,d2.name productName,'' image,cdo.id orderId,'4' orderType,cdo.orderDate,d2.club sellerId,cdo.member memberId FROM tb_factory_order cdo LEFT JOIN tb_member_factory_costs d1 ON cdo.factoryCosts = d1.id LEFT JOIN tb_member_factory d2 ON d1.factory = d2.id UNION ALL SELECT e1.id productId,e2.name productName,e2.image,eo.id orderId,'5' orderType,eo.orderDate,e1.member sellerId,eo.member memberId FROM tb_courserelease_order eo LEFT JOIN tb_course e1 ON eo.course = e1.id LEFT JOIN tb_course_info e2 ON e1.courseId = e2.id UNION ALL SELECT f.id productId,f.name productName,f.image1 image,fo.id orderId,'6' orderType,fo.orderDate,f.member sellerId,fo.member memberId FROM tb_goods_order fo LEFT JOIN tb_goods f ON fo.goods=f.id");
		sb.append(" ) b ON a.order_id = b.orderId AND a.order_type = b.orderType GROUP BY b.productId, b.orderType) p ON g.productId = p.productId AND g.orderType = p.orderType  LEFT JOIN tb_member m ON ma.member = m.id");
		sb.append(" LEFT JOIN (SELECT a1.id, a2.saleNum,'6' proType FROM tb_goods a1 LEFT JOIN (SELECT goods, COUNT(*) saleNum FROM tb_goods_order ao WHERE ao.status = '1' GROUP BY goods) a2 ON a1.id = a2.goods ");
		sb.append(" UNION ALL SELECT b1.id, b2.saleNum, '1' proType FROM tb_product b1 LEFT JOIN (SELECT product, COUNT(*) saleNum FROM tb_product_order bo WHERE bo.status = '1' GROUP BY product) b2  ON b1.id = b2.product");
		sb.append(" UNION ALL SELECT c1.id, c2.saleNum, '3' proType FROM tb_plan_release c1 LEFT JOIN (SELECT planrelease, COUNT(*) saleNum FROM tb_planrelease_order co WHERE co.status = '1' GROUP BY planrelease) c2  ON c1.id = c2.planrelease ");
		sb.append(" UNION ALL SELECT d1.id, d2.saleNum, '5' proType FROM tb_course d1 LEFT JOIN (SELECT course, COUNT(*) saleNum FROM TB_CourseRelease_ORDER do WHERE do.status = '1' GROUP BY course) d2  ON d1.id = d2.course ");
		sb.append(" UNION ALL SELECT e1.id, e2.saleNum, '2' proType FROM tb_active e1 LEFT JOIN (SELECT active, COUNT(*) saleNum FROM TB_active_ORDER eo WHERE eo.status = '1' GROUP BY active) e2  ON e1.id = e2.active ");
		sb.append(" UNION ALL SELECT f2.id, f3.saleNum, '4' proType FROM TB_MEMBER_FACTORY f1 left join TB_MEMBER_FACTORY_COSTS f2 on f1.id = f2.factory RIGHT JOIN (SELECT factoryCosts, COUNT(*) saleNum FROM TB_FACTORY_Order fo WHERE fo.status = '1' GROUP BY factoryCosts) f3  ON f2.id = f3.factoryCosts) c ");
		sb.append(" ON g.productId = c.id AND ma.order_type = c.proType ");
		return sb.toString();
	}
}
