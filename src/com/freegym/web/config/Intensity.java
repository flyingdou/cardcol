package com.freegym.web.config;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.sanmen.web.core.bean.CommonId;

@Entity
@Table(name = "TB_INTENSITY")
public class Intensity extends CommonId {

	private static final long serialVersionUID = 8917962300464879164L;

	private Integer club;

	@Column(length = 20)
	private String name;

	@Column(length = 200)
	private String descr;

	private Integer intensity;

	@Column(length = 200)
	private String memo;

	public Integer getClub() {
		return club;
	}

	public void setClub(Integer club) {
		this.club = club;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescr() {
		return descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	public Integer getIntensity() {
		return intensity;
	}

	public void setIntensity(Integer intensity) {
		this.intensity = intensity;
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
