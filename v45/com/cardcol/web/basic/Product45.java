package com.cardcol.web.basic;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.criterion.DetachedCriteria;

import com.cardcol.web.order.ProductOrder45;
import com.freegym.web.basic.Member;
import com.sanmen.web.core.bean.CommonId;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Entity
@Table(name = "TB_PRODUCT_V45")
public class Product45 extends CommonId {

	private static final long serialVersionUID = -3725296222304933579L;

	/**
	 * 一卡通名称
	 */
	@Column(name = "PROD_NAME", length = 100, nullable = false)
	private String name;

	/**
	 * 商品图片
	 */
	@Column(name = "PROD_IMAGE", length = 30)
	private String image;

	/**
	 * 商品图片
	 */
	@Column(name = "PROD_DETAIL_IMAGE", length = 30)
	private String image1;

	public String getImage1() {
		return image1;
	}

	public void setImage1(String image1) {
		this.image1 = image1;
	}

	/**
	 * 一卡通类型,A时效卡
	 */
	@Column(name = "PROD_TYPE", length = 1, nullable = false)
	private String type;

	/**
	 * 一 卡通周期
	 */
	@Column(name = "PROD_PERIOD", nullable = false)
	private Integer period;

	/**
	 * 周期单位, A月，B季，C年
	 */
	@Column(name = "PROD_PERIOD_UNIT", nullable = false, length = 1)
	private String periodUnit;

	/**
	 * 单价
	 */
	@Column(name = "PROD_PRICE", nullable = false, precision = 10, scale = 2)
	private Double price;

	/**
	 * 描述
	 */
	@Lob
	@Basic
	@Column(name = "PROD_CONTENT")
	private String content;

	/**
	 * 商品状态，A草稿，B发布中，C关闭
	 */
	@Column(name = "PROD_STATUS", length = 1, nullable = false)
	private String status;

	/**
	 * 商品摘要
	 */
	@Column(name = "PROD_SUMMARY", length = 100)
	private String summary;

	/**
	 * 适用俱乐部
	 */
	@OneToMany(targetEntity = ProductClub45.class, mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	private Set<ProductClub45> clubs = new HashSet<ProductClub45>();

	/**
	 * 商品订单明细
	 */
	@OneToMany(targetEntity = ProductOrder45.class, mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	private Set<ProductOrder45> orders = new HashSet<ProductOrder45>();

	@Transient
	private String applyClubs;

	public Product45() {

	}

	public Product45(long id) {
		setId(id);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getPeriod() {
		return period;
	}

	public void setPeriod(Integer period) {
		this.period = period;
	}

	public String getPeriodUnit() {
		return periodUnit;
	}

	public void setPeriodUnit(String periodUnit) {
		this.periodUnit = periodUnit;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public Set<ProductClub45> getClubs() {
		return clubs;
	}

	public void setClubs(Set<ProductClub45> clubs) {
		this.clubs = clubs;
	}

	public Set<ProductOrder45> getOrders() {
		return orders;
	}

	public void setOrders(Set<ProductOrder45> orders) {
		this.orders = orders;
	}

	public String getApplyClubs() {
		final JSONArray jarr = new JSONArray();
		for (final ProductClub45 pc : getClubs()) {
			final Member club = pc.getClub();
			jarr.add(new JSONObject().accumulate("id", club.getId()).accumulate("email", club.getEmail())
					.accumulate("nick", club.getNick()).accumulate("tell", club.getTell())
					.accumulate("mobilepheno", club.getMobilephone()).accumulate("name", club.getName()));
		}
		return jarr.toString();
	}

	public void setApplyClubs(String applyClubs) {
		this.applyClubs = applyClubs;
	}

	@Override
	public String getTableName() {
		return "一卡通商品信息表";
	}

	public static DetachedCriteria getCriteriaQuery(Product45 query) {
		final DetachedCriteria dc = DetachedCriteria.forClass(Product45.class);
		return dc;
	}

}
