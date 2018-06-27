package com.freegym.web.order;

import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import com.freegym.web.active.Active;
import com.freegym.web.active.Team;
import com.freegym.web.active.TrainRecord;

import net.sf.json.JSONObject;

@Entity
@Table(name = "tb_active_order")
public class ActiveOrder extends Order {

	private static final long serialVersionUID = -8253280953155876557L;

	/**
	 * 活动主表(商品表)
	 */
	@ManyToOne(targetEntity = Active.class)
	@JoinColumn(name = "active")
	private Active active;

	/**
	 * 团队对象
	 */
	@ManyToOne(targetEntity = Team.class)
	@JoinColumn(name = "team")
	private Team team;

	/**
	 * 裁判
	 */
	@Column(name = "judge", length = 50)
	private String judge;

	/**
	 * 当前体重
	 */
	@Column(name = "weight", precision = 10, scale = 2)
	private Double weight;

	/**
	 * 结束时体重
	 */
	@Column(name = "LAST_WEIGHT", precision = 10, scale = 2)
	private Double lastWeight;

	/**
	 * 最新值,依据目标其值不同
	 */
	private Double value;

	/**
	 * 最终结果,0为活动中,1为胜,2为负,3挑战结束
	 */
	private Character result = '0';

	/**
	 * 活动记录明细
	 */
	@OneToMany(targetEntity = TrainRecord.class, mappedBy = "activeOrder", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
	@OrderBy("doneDate desc")
	private Set<TrainRecord> records = new HashSet<TrainRecord>();

	public ActiveOrder() {

	}

	public ActiveOrder(Long id) {
		setId(id);
	}

	@Override
	public void setOrderStartTime(Date orderStartTime) {
		super.setOrderStartTime(orderStartTime);
		if (orderStartTime != null && active != null && active.getDays() != null) {
			final Calendar c = Calendar.getInstance();
			c.setTime(orderStartTime);
			c.add(Calendar.DAY_OF_MONTH, active.getDays());
			setOrderEndTime(c.getTime());
		}
	}

	public Active getActive() {
		return active;
	}

	public void setActive(Active active) {
		this.active = active;
	}

	public Team getTeam() {
		return team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}

	public String getJudge() {
		return judge;
	}

	public void setJudge(String judge) {
		this.judge = judge;
	}

	public Double getWeight() {
		return weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}

	public Double getLastWeight() {
		return lastWeight;
	}

	public void setLastWeight(Double lastWeight) {
		this.lastWeight = lastWeight;
	}

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}

	public Character getResult() {
		return result;
	}

	public void setResult(Character result) {
		this.result = result;
	}

	public Set<TrainRecord> getRecords() {
		return records;
	}

	public void setRecords(Set<TrainRecord> records) {
		this.records = records;
	}

	@Override
	public String getTableName() {
		return "活动订单表";
	}

	public JSONObject toJson() {
		final JSONObject obj = new JSONObject();
		obj.accumulate("active", getActive().toJson());
		return obj;
	}

}
