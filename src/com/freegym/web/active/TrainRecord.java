package com.freegym.web.active;

import java.text.DecimalFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.freegym.web.basic.Member;
import com.freegym.web.order.ActiveOrder;
import com.sanmen.web.core.bean.CommonId;

@Entity
@Table(name = "tb_plan_record")
public class TrainRecord extends CommonId {

	private static final long serialVersionUID = -3907467273449357483L;

	/**
	 * 活动订单主表
	 */
	@ManyToOne(targetEntity = ActiveOrder.class)
	@JoinColumn(name = "active_order")
	private ActiveOrder activeOrder;

	/**
	 * 参与者(个人)
	 */
	@ManyToOne(targetEntity = Member.class)
	@JoinColumn(name = "partake")
	private Member partake;

	/**
	 * 完成时间
	 */
	@Column(name = "done_date")
	@Temporal(TemporalType.DATE)
	private Date doneDate;
	/**
	 * 运动计量单位
	 */
	private String unit;
	/**
	 * 评分
	 */
	private Integer score;
	/**
	 * 运动项目
	 */
	private String action;

	/**
	 * 运动时间
	 */
	private Double times;

	/**
	 * 运动量
	 */
	private Integer actionQuan;

	/**
	 * 体重
	 */
	private Double weight;

	/**
	 * 腰围
	 */
	private Double waist;

	/**
	 * 臀围
	 */
	private Double hip;

	/**
	 * 体脂率
	 */
	private Double fat;

	/**
	 * 身高
	 */
	private Double height;

	/**
	 * 运动后心率
	 */
	private Double heartRate;

	/**
	 * 体会
	 */
	@Column(length = 200)
	private String memo;

	/**
	 * 确认状态0为待确认.1为通过,2为未通过
	 */
	private Character confrim = '0';

	/**
	 * 运动负荷(力量KG)
	 */
	@Transient
	private Double strength;

	/**
	 * 运动次数
	 */
	@Transient
	private Integer time;

	/**
	 * 体质指数
	 */
	@Transient
	private Double bmi;
	
	
	/**
	 * 腰臀比
	 */
	@Transient
	private Double waistHip;

	public Double getHeight() {
		return height;
	}

	public Double getHeartRate() {
		return heartRate;
	}

	public void setHeight(Double height) {
		this.height = height;
	}

	public void setHeartRate(Double heartRate) {
		this.heartRate = heartRate;
	}

	public ActiveOrder getActiveOrder() {
		return activeOrder;
	}

	public void setActiveOrder(ActiveOrder activeOrder) {
		this.activeOrder = activeOrder;
	}

	public Member getPartake() {
		return partake;
	}

	public void setPartake(Member partake) {
		this.partake = partake;
	}

	public Date getDoneDate() {
		return doneDate;
	}

	public void setDoneDate(Date doneDate) {
		this.doneDate = doneDate;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public Double getTimes() {
		return times;
	}

	public void setTimes(Double times) {
		this.times = times;
	}

	public Integer getActionQuan() {
		return actionQuan;
	}

	public void setActionQuan(Integer actionQuan) {
		this.actionQuan = actionQuan;
	}

	public Double getWeight() {
		return weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}

	public Double getWaist() {
		return waist;
	}

	public void setWaist(Double waist) {
		this.waist = waist;
	}

	public Double getHip() {
		return hip;
	}

	public void setHip(Double hip) {
		this.hip = hip;
	}

	public Double getFat() {
		return fat;
	}

	public void setFat(Double fat) {
		this.fat = fat;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public Character getConfrim() {
		return confrim;
	}

	public void setConfrim(Character confrim) {
		this.confrim = confrim;
	}

	public Double getStrength() {
		return strength;
	}

	public void setStrength(Double strength) {
		this.strength = strength;
	}

	public Integer getTime() {
		return time;
	}

	public void setTime(Integer time) {
		this.time = time;
	}

	public Double getBmi() {
		if (height != null && weight != null && height > 0 && weight > 0) {
			DecimalFormat df = new DecimalFormat("#00.00");
			bmi = new Double(df.format(weight / (height*height/10000)));
		}
		return bmi;
	}

	public void setBmi(Double bmi) {
		this.bmi = bmi;
	}

	public Double getWaistHip() {
		if (waist != null && hip != null && waist > 0 && hip > 0) {
			DecimalFormat df = new DecimalFormat("#00.00");
			waistHip = new Double(df.format(waist / hip));
		}
		return waistHip;
	}

	public void setWaistHip(Double waistHip) {
		this.waistHip = waistHip;
	}

	@Override
	public String getTableName() {
		return "活动参与表";
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

}
