package com.freegym.web.config;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.sanmen.web.core.bean.CommonId;

@Entity
@Table(name = "tb_plan_engine_action")
public class PlanEngineAction extends CommonId {

	/**
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	 */

	private static final long serialVersionUID = -8424918175083240201L;

	/**
	 * 健身目的
	 */
	private String purpose;

	/**
	 * 健康状态
	 */
	private String userLevel;

	/**
	 * 月索引
	 */
	private Integer monthIndex;

	/**
	 * 重量
	 */
	private String ruleWeight;

	/**
	 * 组数
	 */
	private Integer ruleSets;

	/**
	 * 次数
	 */
	private String ruleTimes;

	/**
	 * 间隔
	 */
	private String ruleInterval;

	private Integer ruleCatalogs;

	/**
	 * 部位
	 */
	private String rulePart;

	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	public String getUserLevel() {
		return userLevel;
	}

	public void setUserLevel(String userLevel) {
		this.userLevel = userLevel;
	}

	public Integer getMonthIndex() {
		return monthIndex;
	}

	public void setMonthIndex(Integer monthIndex) {
		this.monthIndex = monthIndex;
	}

	public String getRuleWeight() {
		return ruleWeight;
	}

	public void setRuleWeight(String ruleWeight) {
		this.ruleWeight = ruleWeight;
	}

	public Integer getRuleSets() {
		return ruleSets;
	}

	public void setRuleSets(Integer ruleSets) {
		this.ruleSets = ruleSets;
	}

	public String getRuleTimes() {
		return ruleTimes;
	}

	public void setRuleTimes(String ruleTimes) {
		this.ruleTimes = ruleTimes;
	}

	public String getRuleInterval() {
		return ruleInterval;
	}

	public void setRuleInterval(String ruleInterval) {
		this.ruleInterval = ruleInterval;
	}

	public Integer getRuleCatalogs() {
		return ruleCatalogs;
	}

	public void setRuleCatalogs(Integer ruleCatalogs) {
		this.ruleCatalogs = ruleCatalogs;
	}

	public String getRulePart() {
		return rulePart;
	}

	public void setRulePart(String rulePart) {
		this.rulePart = rulePart;
	}

	@Override
	public String getTableName() {
		return null;
	}

	public static DetachedCriteria getCriteriaQuery(PlanEngineAction query) {
		final DetachedCriteria dc = DetachedCriteria.forClass(PlanEngineAction.class);
		if (query != null) {
			if (query.getUserLevel() != null && !"".equals(query.getUserLevel())) dc.add(Restrictions.eq("userLevel", query.getUserLevel()));
			if (query.getPurpose() != null && !"".equals(query.getPurpose())) dc.add(Restrictions.eq("purpose", query.getPurpose()));
			if (query.getMonthIndex() != null && query.getMonthIndex() > 0) dc.add(Restrictions.eq("monthIndex", query.getMonthIndex()));
		}
		return dc;
	}

}