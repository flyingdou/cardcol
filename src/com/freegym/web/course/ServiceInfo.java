package com.freegym.web.course;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.sanmen.web.core.bean.CommonId;

@Entity
@Table(name = "TB_SERVICE_INFO")
public class ServiceInfo extends CommonId {

	private static final long serialVersionUID = 7563792954778825178L;

	private Long club;

	@Column(length = 50)
	private String name;

	@Column(length = 50)
	private String memo;

	public Long getClub() {
		return club;
	}

	public void setClub(Long club) {
		this.club = club;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	@Override
	public String getTableName() {
		return null;
	}

}
