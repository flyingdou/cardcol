package com.freegym.web.basic;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.sanmen.web.core.bean.CommonId;

@Entity
@Table(name = "TB_PRICE_CUTDOWN")
public class priceCutDown extends CommonId {

	/**
	 * 序列化
	 */
	private static final long serialVersionUID = -1934332511919429177L;
	
	/**
	 * 商品
	 */
	@Column
	private Long product;
	
	/**
	 * 砍价者
	 */
	@Column
	private Long Member ;
	
	/**
	 * parent
	 */
	@ManyToOne(targetEntity = priceCutDown.class)
	@JoinColumn(name = "parent")
	private priceCutDown parent;
	
	/**
	 * 商品价格
	 */
	@Column
	private Double money;
	
	/**
	 * 砍价金额
	 */
	@Column
	private Double cutMoney;
	
	/**
	 * 砍价时间
	 */
	@Column
	private Date time;
	
	/**
	 * 砍价状态(0砍价中，1砍价结束{用户已购买，即时结束该砍价 ；  已砍至最低价}，对parent中的数据修改)
	 */
	@Column
	private String status;
	
	/**
	 * 商品底价
	 */
	@Column
	private Double lowPrice;
	
	/**
	 * 砍价周期(不给，默认3天)
	 */
	@Column
	private Integer period;
	
	
	/**
	 * 砍价活动海报
	 */
	@Column
	private String poster;
	
	/**
	 * 活动发起者
	 */
	@Column
	private Long creator;
	
	/**
	 * 砍价所属活动
	 */
	@Column
	private Long priceActive;
	
	/**
	 * 砍价活动说明
	 */
	@Column
	private String remark;
	
	/**
	 * 砍价活动名称
	 */
	@Column
	private String activeName;
	
	/**
	 * 当前砍价次数
	 */
	@Column
	private Integer currentTimes;
	
	/**
	 * 砍价总次数
	 */
	@Column
	private Integer totalTimes;
	
	/**
	 * 砍价比例
	 */
	@Column
	private String cutRates;
	
	

	@Override
	public String getTableName() {
		return null;
	}


	/**
	 * getter && setter
	 * @return
	 */
	public Long getProduct() {
		return product;
	}

	public void setProduct(Long product) {
		this.product = product;
	}

	public Long getMember() {
		return Member;
	}

	public void setMember(Long member) {
		Member = member;
	}

	public priceCutDown getParent() {
		return parent;
	}

	public void setParent(priceCutDown parent) {
		this.parent = parent;
	}

	public Double getMoney() {
		return money;
	}

	public void setMoney(Double money) {
		this.money = money;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public Double getLowPrice() {
		return lowPrice;
	}

	public void setLowPrice(Double lowPrice) {
		this.lowPrice = lowPrice;
	}

	public Double getCutMoney() {
		return cutMoney;
	}

	public void setCutMoney(Double cutMoney) {
		this.cutMoney = cutMoney;
	}

	public Integer getPeriod() {
		return period;
	}

	public void setPeriod(Integer period) {
		this.period = period;
	}

	public String getPoster() {
		return poster;
	}

	public void setPoster(String poster) {
		this.poster = poster;
	}

	public Long getCreator() {
		return creator;
	}

	public void setCreator(Long creator) {
		this.creator = creator;
	}

	public Long getPriceActive() {
		return priceActive;
	}

	public void setPriceActive(Long priceActive) {
		this.priceActive = priceActive;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getActiveName() {
		return activeName;
	}

	public void setActiveName(String activeName) {
		this.activeName = activeName;
	}

	public Integer getCurrentTimes() {
		return currentTimes;
	}

	public void setCurrentTimes(Integer currentTimes) {
		this.currentTimes = currentTimes;
	}

	public Integer getTotalTimes() {
		return totalTimes;
	}

	public void setTotalTimes(Integer totalTimes) {
		this.totalTimes = totalTimes;
	}

	public String getCutRates() {
		return cutRates;
	}

	public void setCutRates(String cutRates) {
		this.cutRates = cutRates;
	}
	
	
	
	
	
	
	
	

}
