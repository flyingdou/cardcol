package com.freegym.web.order;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import net.sf.json.JSONObject;

import com.sanmen.web.core.bean.CommonId;

/**
 * 内部商品订单，主要指卡库自动生成计划、王严自动生成计划
 * 
 * @author LIUHB
 * 
 */
@Entity
@Table(name = "tb_goods")
public class Goods extends CommonId {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9129728853858643626L;

	/**
	 * 会员ID 默认：1（卡库网）
	 */
	private Long member;

	/**
	 * 名称
	 */
	@Column(name = "name", length = 100)
	private String name;

	/**
	 * 单价
	 */
	@Column(name = "price", precision = 18, scale = 2)
	private Double price;

	/**
	 * 简介
	 */
	@Column(name = "summary", length = 255)
	private String summary;

	/**
	 * 描述
	 */
	@Lob
	@Basic
	@Column(name = "descr")
	private String descr;

	/**
	 * 实现类
	 */
	@Column(name = "impl_class", length = 50)
	private String implClass;

	/**
	 * 1，卡库；2，王严
	 */
	@Column(name = "type", length = 50)
	private String type;

	/**
	 * 图片1
	 */
	@Column(name = "image1", length = 30)
	private String image1;

	/**
	 * 图片2
	 */
	@Column(name = "image2", length = 30)
	private String image2;

	/**
	 * 图片3
	 */
	@Column(name = "image3", length = 30)
	private String image3;

	@Column(length = 1)
	private String isClose;// 关闭状态1：关闭2： 开启

	@Column(length = 1)
	@Temporal(TemporalType.TIMESTAMP)
	private Date topTime;// 置顶时间

	/**
	 * 计划类型A.瘦身减重 B.健美增肌 C.运动康复 D.提高运动表现
	 */
	@Column(name = "plan_type", length = 20)
	private String planType;

	/**
	 * 适用场景。A.健身房 B.办公室 C.家庭 D.户外
	 */
	@Column(name = "apply_scene", length = 20)
	private String scene;

	/**
	 * 适用对象.A.初级 B.中级 C.高级
	 */
	@Column(name = "apply_object", length = 20)
	private String applyObject;

	/**
	 * 所需器材
	 */
	@Column(name = "apparatuses", length = 255)
	private String apparatuses;

	/**
	 * 计划周期
	 */
	@Column(name = "plan_circle", length = 20)
	private String plancircle;

	/**
	 * 发布时间
	 */
	@Column(name = "publish_time")
	private String publishTime;

	public Goods() {
	}

	public Goods(long pid) {
		setId(pid);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getDescr() {
		return descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	public String getImplClass() {
		return implClass;
	}

	public void setImplClass(String implClass) {
		this.implClass = implClass;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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

	@Override
	public String getTableName() {
		return "内部商品订单";
	}

	public Long getMember() {
		return member;
	}

	public void setMember(Long member) {
		this.member = member;
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

	public String getPlancircle() {
		return plancircle;
	}

	public void setPlancircle(String plancircle) {
		this.plancircle = plancircle;
	}

	public String getPublishTime() {
		return publishTime;
	}

	public void setPublishTime(String publishTime) {
		this.publishTime = publishTime;
	}

	public JSONObject toJson() {
		final JSONObject obj = new JSONObject();
		obj.accumulate("id", getId()).accumulate("member", getMember()).accumulate("name", getName()).accumulate("price", getPrice())
				.accumulate("summary", getSummary()).accumulate("descr", getDescr()).accumulate("implClass", getImplClass()).accumulate("type", getType())
				.accumulate("image1", getImage1()).accumulate("image2", getImage2()).accumulate("image3", getImage3())
				.accumulate("planType", getPlanType().replaceAll("A", "瘦身减重").replaceAll("B", "健美增肌").replaceAll("C", "运动康复").replaceAll("D", "提高运动表现"))
				.accumulate("scene", getScene().replaceAll("A", "健身房").replaceAll("B", "办公室").replaceAll("C", "家庭").replaceAll("D", "户外").replaceAll("E", "其它"))
				.accumulate("applyObject", getApplyObject().replaceAll("A", "初级").replaceAll("B", "中级").replaceAll("C", "高级"))
				.accumulate("apparatuses", getApparatuses()).accumulate("plancircle", getPlancircle()).accumulate("publishTime", getPublishTime());
		return obj;
	}
	public JSONObject toJsons() {
		final JSONObject obj = new JSONObject();
		obj.accumulate("id", getId()).accumulate("member", getMember()).accumulate("name", getName()).accumulate("price", getPrice())
				.accumulate("summary", getSummary()).accumulate("implClass", getImplClass()).accumulate("type", getType())
				.accumulate("image1", getImage1()).accumulate("planType", getPlanType().replaceAll("A", "瘦身减重").replaceAll("B", "健美增肌").replaceAll("C", "运动康复").replaceAll("D", "提高运动表现"))
				.accumulate("scene", getScene().replaceAll("A", "健身房").replaceAll("B", "办公室").replaceAll("C", "家庭").replaceAll("D", "户外").replaceAll("E", "其它"))
				.accumulate("applyObject", getApplyObject().replaceAll("A", "初级").replaceAll("B", "中级").replaceAll("C", "高级"))
				.accumulate("apparatuses", getApparatuses()).accumulate("plancircle", getPlancircle()).accumulate("publishTime", getPublishTime());
		return obj;
	}
}
