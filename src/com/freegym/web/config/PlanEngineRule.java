package com.freegym.web.config;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.sanmen.web.core.bean.CommonId;

@Entity
@Table(name = "tb_plan_engine_rule")
public class PlanEngineRule extends CommonId {

	private static final long serialVersionUID = 1723150698009099864L;

	/**
	 * 健身目的
	 */
	private String userLevel;

	/**
	 * 
	 */
	private Integer duration;

	/**
	 * 频率
	 */
	private Integer frequency;

	/**
	 * 天数
	 */
	private Integer dayIndex;

	/**
	 * 部位
	 */
	@Column(length = 30)
	private String part;

	/**
	 * 顺序
	 */
	private int sort;

	public PlanEngineRule() {
	}

	public PlanEngineRule(String userLevel, Integer duration, Integer frequency, Integer dayIndex, String part) {
		this.userLevel = userLevel;
		this.duration = duration;
		this.frequency = frequency;
		this.dayIndex = dayIndex;
		this.part = part;
	}

	public String getUserLevel() {
		return this.userLevel;
	}

	public void setUserLevel(String userLevel) {
		this.userLevel = userLevel;
	}

	public Integer getDuration() {
		return this.duration;
	}

	public void setDuration(Integer duration) {
		this.duration = duration;
	}

	public Integer getFrequency() {
		return this.frequency;
	}

	public void setFrequency(Integer frequency) {
		this.frequency = frequency;
	}

	public Integer getDayIndex() {
		return this.dayIndex;
	}

	public void setDayIndex(Integer dayIndex) {
		this.dayIndex = dayIndex;
	}

	public String getPart() {
		return this.part;
	}

	public void setPart(String part) {
		this.part = part;
	}

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

	@Override
	public String getTableName() {
		return "规则库-部位";
	}

	public static DetachedCriteria getCriteriaQuery(PlanEngineRule query) {
		final DetachedCriteria dc = DetachedCriteria.forClass(PlanEngineRule.class);
		if (query != null) {
			if (query.getDayIndex() != null) dc.add(Restrictions.eq("dayIndex", query.getDayIndex()));
			if (query.getFrequency() != null) dc.add(Restrictions.eq("frequency", query.getFrequency()));
			if (query.getUserLevel() != null && !"".equals(query.getUserLevel())) dc.add(Restrictions.eq("userLevel", query.getUserLevel()));
		}
		return dc;
	}

}
