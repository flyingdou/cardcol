package com.freegym.web.config;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.sanmen.web.core.bean.CommonId;

@Entity
@Table(name = "TB_MEMBER_WORKTIME")
public class WorkTime extends CommonId {

	private static final long serialVersionUID = -8095757111854166375L;

	private Long member;

	@Column(length = 5)
	private String startTime;

	@Column(length = 5)
	private String endTime;

	public Long getMember() {
		return member;
	}

	public void setMember(Long member) {
		this.member = member;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	@Override
	public String getTableName() {
		return null;
	}
}
