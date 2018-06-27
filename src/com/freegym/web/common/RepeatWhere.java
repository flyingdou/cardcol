package com.freegym.web.common;

import java.util.Date;

public class RepeatWhere {
	private Integer mode;
	private String value;
	private Integer weekOf;
	private Date start;
	private Date end;
	private Integer num;

	public Integer getMode() {
		return mode;
	}

	public void setMode(Integer mode) {
		this.mode = mode;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Integer getWeekOf() {
		return weekOf;
	}

	public void setWeekOf(Integer weekOf) {
		this.weekOf = weekOf;
	}

	public Date getStart() {
		return start;
	}

	public void setStart(Date start) {
		this.start = start;
	}

	public Date getEnd() {
		return end;
	}

	public void setEnd(Date end) {
		this.end = end;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

}
