package com.freegym.web.config;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

import net.sf.json.JSONObject;

import com.freegym.web.factoryorder.FactoryApply;
import com.freegym.web.order.FactoryOrder;
import com.sanmen.web.core.bean.CommonId;

/**
 * 场地定价信息表
 * 
 * @author Administrator
 * 
 */
@Entity
@Table(name = "TB_MEMBER_FACTORY_COSTS")
public class FactoryCosts extends CommonId implements Cloneable {

	private static final long serialVersionUID = -2662894643623010379L;

	/**
	 * 场地
	 */
	@ManyToOne(targetEntity = Factory.class)
	@JoinColumn(name = "factory")
	private Factory factory;

	@OneToMany(targetEntity = FactoryOrder.class, mappedBy = "factoryCosts", fetch = FetchType.LAZY)
	@OrderBy(value = "orderStartTime ASC")
	private Set<FactoryOrder> factoryorders = new HashSet<FactoryOrder>();

	@OneToMany(targetEntity = FactoryApply.class, mappedBy = "factorycosts", fetch = FetchType.LAZY)
	private Set<FactoryApply> factoryApply = new HashSet<FactoryApply>();

	/**
	 * 场地名称
	 */
	@Column(name = "name", length = 50)
	private String name;

	/**
	 * 场地日期
	 */
	private String planDate;
	/**
	 * 开始时间
	 */
	@Column(length = 5, nullable = false)
	private String starttime;
	/**
	 * 结束时间
	 */
	@Column(length = 5, nullable = false)
	private String endtime;
	/**
	 * 普通价格
	 */
	private String costs1;
	/**
	 * 会员价格
	 */
	private String costs2;

	/**
	 * 是否提醒 1为提醒
	 */
	private String hasReminder;

	/**
	 * 提醒时间（小时）
	 */
	private String reminder;

	/**
	 * 展现顏色
	 */
	@Column(length = 30)
	private String color;

	/**
	 * 备注
	 */
	@Column(length = 255)
	private String memo;

	/**
	 * 是否周期模式 1为周期模式
	 */
	private String hasMode;

	/**
	 * 周期类型 1为日 2为周 3为月
	 */
	private String mode;

	/**
	 * 每日
	 */
	private String dayof;

	/**
	 * 每周
	 */
	private String weekOf;

	/**
	 * 每月，周期模式，0單次，1為周期
	 */
	private String type;

	/**
	 * 單次模式值
	 */
	@Column(length = 30)
	private String value;

	/**
	 * 周期模式值1
	 */
	private String cycle1;

	/**
	 * 周期模式值1
	 */
	private String cycle2;

	/**
	 * 重複條件 1。重复次数 2，重复时间
	 */
	@Column(length = 30)
	private String repeatWhere;

	/**
	 * 重複開始時間
	 */
	@Column(length = 10)
	private String repeatStart;

	/**
	 * 重複次數
	 */
	private String repeatNum;

	/**
	 * 重複結束時間
	 */
	@Column(length = 10)
	private String repeatEnd;

	/**
	 * 能否进行编辑
	 */
	@Transient
	private String canEdit;

	/**
	 * 能否进行删除
	 */
	@Transient
	private String canDel;

	// 是否同一组
	private String conditionGp;

	public String getMemo() {
		return memo;
	}

	public FactoryCosts() {
	}

	public FactoryCosts(Long pid) {
		setId(pid);
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPlanDate() {
		return planDate;
	}

	public void setPlanDate(String planDate) {
		this.planDate = planDate;
	}

	public String getHasReminder() {
		return hasReminder;
	}

	public void setHasReminder(String hasReminder) {
		this.hasReminder = hasReminder;
	}

	public String getReminder() {
		return reminder;
	}

	public void setReminder(String reminder) {
		this.reminder = reminder;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getWeekOf() {
		return weekOf;
	}

	public void setWeekOf(String weekOf) {
		this.weekOf = weekOf;
	}

	public String getRepeatWhere() {
		return repeatWhere;
	}

	public void setRepeatWhere(String repeatWhere) {
		this.repeatWhere = repeatWhere;
	}

	public String getRepeatStart() {
		return repeatStart;
	}

	public void setRepeatStart(String repeatStart) {
		this.repeatStart = repeatStart;
	}

	public String getRepeatNum() {
		return repeatNum;
	}

	public void setRepeatNum(String repeatNum) {
		this.repeatNum = repeatNum;
	}

	public String getRepeatEnd() {
		return repeatEnd;
	}

	public void setRepeatEnd(String repeatEnd) {
		this.repeatEnd = repeatEnd;
	}

	public String getCanEdit() {
		return canEdit;
	}

	public void setCanEdit(String canEdit) {
		this.canEdit = canEdit;
	}

	public String getCanDel() {
		return canDel;
	}

	public void setCanDel(String canDel) {
		this.canDel = canDel;
	}

	@Override
	public String getTableName() {
		return "场地定价信息表";
	}

	public Factory getFactory() {
		return factory;
	}

	public void setFactory(Factory factory) {
		this.factory = factory;
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

	public String getCosts1() {
		return costs1;
	}

	public void setCosts1(String costs1) {
		this.costs1 = costs1;
	}

	public String getCosts2() {
		return costs2;
	}

	public void setCosts2(String costs2) {
		this.costs2 = costs2;
	}

	public Set<FactoryOrder> getFactoryorders() {
		return factoryorders;
	}

	public void setFactoryorders(Set<FactoryOrder> factoryorders) {
		this.factoryorders = factoryorders;
	}

	public String getHasMode() {
		return hasMode;
	}

	public void setHasMode(String hasMode) {
		this.hasMode = hasMode;
	}

	public String getDayof() {
		return dayof;
	}

	public void setDayof(String dayof) {
		this.dayof = dayof;
	}

	public String getCycle1() {
		return cycle1;
	}

	public void setCycle1(String cycle1) {
		this.cycle1 = cycle1;
	}

	public String getCycle2() {
		return cycle2;
	}

	public void setCycle2(String cycle2) {
		this.cycle2 = cycle2;
	}

	public String getConditionGp() {
		return conditionGp;
	}

	public void setConditionGp(String conditionGp) {
		this.conditionGp = conditionGp;
	}

	public Set<FactoryApply> getApplyApply() {
		return factoryApply;
	}

	public void setApplyApply(Set<FactoryApply> applyApply) {
		this.factoryApply = applyApply;
	}

	public Object clone() {
		FactoryCosts o = null;
		try {
			o = (FactoryCosts) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return o;
	}

	public JSONObject toJson() {
		final JSONObject obj = new JSONObject();
		obj.accumulate("id", getId()).accumulate("factory", getFactory().toJson()).accumulate("name", name).accumulate("planDate", planDate)
				.accumulate("starttime", starttime).accumulate("endtime", endtime).accumulate("cost1", costs1).accumulate("cost2", costs2)
				.accumulate("hasReminder", hasReminder).accumulate("reminder", reminder).accumulate("color", color).accumulate("memo", memo)
				.accumulate("hasMode", hasMode).accumulate("mode", mode).accumulate("dayOf", dayof).accumulate("weekOf", weekOf).accumulate("type", type)
				.accumulate("value", value).accumulate("cycle1", cycle1).accumulate("cycle2", cycle2).accumulate("repeatWhere", repeatWhere)
				.accumulate("repeatStart", repeatStart).accumulate("repeatNum", repeatNum).accumulate("repeatEnd", repeatEnd);

		return obj;
	}
}
