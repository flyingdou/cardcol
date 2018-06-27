package com.freegym.web.basic;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.sanmen.web.core.bean.CommonId;

@Entity
@Table(name = "TB_MEMBER_CHARGE")
public class Charge extends CommonId {

	private static final long serialVersionUID = 157468353871796358L;

	private Integer coach;

	@Column(length = 30)
	private String type;

	private Double cost;

	@Column(length = 255)
	private String memo;

	public Integer getCoach() {
		return coach;
	}

	public void setCoach(Integer coach) {
		this.coach = coach;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Double getCost() {
		return cost;
	}

	public void setCost(Double cost) {
		this.cost = cost;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	@Override
	public String getTableName() {
		// TODO Auto-generated method stub
		return null;
	}

}
