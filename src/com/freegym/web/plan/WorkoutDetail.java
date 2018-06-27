package com.freegym.web.plan;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.sanmen.web.core.bean.CommonId;

@Entity
@Table(name = "TB_WORKOUT_DETAIL")
public class WorkoutDetail extends CommonId implements Cloneable {

	private static final long serialVersionUID = 7645586034048786690L;

	@ManyToOne(targetEntity = Workout.class, fetch = FetchType.EAGER)
	@JoinColumn(name = "workout")
	private Workout workout;

	// 20160721
	private String planStartSound;
	private String planGroupSpaceSound;
	
	
	public String getPlanStartSound() {
		return planStartSound;
	}

	public void setPlanStartSound(String planStartSound) {
		this.planStartSound = planStartSound;
	}

	public String getPlanGroupSpaceSound() {
		return planGroupSpaceSound;
	}

	public void setPlanGroupSpaceSound(String planGroupSpaceSound) {
		this.planGroupSpaceSound = planGroupSpaceSound;
	}

	/**
	 * 次数
	 */
	private String intensity;

	/**
	 * 计划重量
	 */
	@Column(length = 50)
	private String planWeight;

	/**
	 * 时间
	 */
	@Column(length = 50)
	private String actualWeight;

	/**
	 * 力量：间隔(计划间隔秒)
	 */
	@Column(length = 50)
	private String planIntervalSeconds;

	/**
	 * 力量：实际间隔
	 */
	@Column(length = 50)
	private String actualIntervalSeconds;

	/**
	 * 计划次数
	 */
	@Column(length = 50)
	private String planTimes;

	/**
	 * 间隔
	 */
	@Column(length = 50)
	private String actualTimes;

	/**
	 * 提示信息
	 */
	@Column(length = 50)
	private String planTips;

	/**
	 * 动作顺序
	 */
	private Integer sort;

	/**
	 * 计划距离
	 */
	@Column(name = "planDistance", length = 50)
	private String planDistance;

	/**
	 * 计划持续时间
	 */
	@Column(name = "planDuration", length = 50)
	private String planDuration;

	public WorkoutDetail() {

	}

	public WorkoutDetail(Workout newWork, String actualIntervalSeconds, String actualTimes, String actualWeight, String intensity, String planIntervalSeconds,
			String planTimes, String planTips, String planWeight, String planDistance, String planDuration) {
		this.workout = newWork;
		this.actualIntervalSeconds = actualIntervalSeconds;
		this.actualTimes = actualTimes;
		this.actualWeight = actualWeight;
		this.intensity = intensity;
		this.planIntervalSeconds = planIntervalSeconds;
		this.planTimes = planTimes;
		this.planTips = planTips;
		this.planWeight = planWeight;
		this.planDistance = planDistance;
		this.planDuration = planDuration;
	}
	public WorkoutDetail(Workout newWork, String actualIntervalSeconds, String actualTimes, String actualWeight, String intensity, String planIntervalSeconds,
			String planTimes, String planTips, String planWeight, String planDistance, String planDuration, String planStartSound, String planGroupSpaceSound) {
		this.workout = newWork;
		this.actualIntervalSeconds = actualIntervalSeconds;
		this.actualTimes = actualTimes;
		this.actualWeight = actualWeight;
		this.intensity = intensity;
		this.planIntervalSeconds = planIntervalSeconds;
		this.planTimes = planTimes;
		this.planTips = planTips;
		this.planWeight = planWeight;
		this.planDistance = planDistance;
		this.planDuration = planDuration;
		this.planStartSound = planStartSound;
		this.planGroupSpaceSound = planGroupSpaceSound;
	}

	
	
	public Workout getWorkout() {
		return workout;
	}

	public void setWorkout(Workout workout) {
		this.workout = workout;
	}

	public String getIntensity() {
		return intensity;
	}

	public void setIntensity(String intensity) {
		this.intensity = intensity;
	}

	public String getPlanWeight() {
		return planWeight;
	}

	public void setPlanWeight(String planWeight) {
		this.planWeight = planWeight;
	}

	public String getActualWeight() {
		return actualWeight;
	}

	public void setActualWeight(String actualWeight) {
		this.actualWeight = actualWeight;
	}

	public String getPlanIntervalSeconds() {
		return planIntervalSeconds;
	}

	public void setPlanIntervalSeconds(String planIntervalSeconds) {
		this.planIntervalSeconds = planIntervalSeconds;
	}

	public String getActualIntervalSeconds() {
		return actualIntervalSeconds;
	}

	public void setActualIntervalSeconds(String actualIntervalSeconds) {
		this.actualIntervalSeconds = actualIntervalSeconds;
	}

	public String getPlanTimes() {
		return planTimes;
	}

	public void setPlanTimes(String planTimes) {
		this.planTimes = planTimes;
	}

	public String getActualTimes() {
		return actualTimes;
	}

	public void setActualTimes(String actualTimes) {
		this.actualTimes = actualTimes;
	}

	public String getPlanTips() {
		return planTips;
	}

	public void setPlanTips(String planTips) {
		this.planTips = planTips;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getPlanDistance() {
		return planDistance;
	}

	public void setPlanDistance(String planDistance) {
		this.planDistance = planDistance;
	}

	public String getPlanDuration() {
		return planDuration;
	}

	public void setPlanDuration(String planDuration) {
		this.planDuration = planDuration;
	}

	@Override
	public String getTableName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
}
