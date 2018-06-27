package com.freegym.web.basic;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

/**
 * @author hw
 * @version 创建时间：2018年3月26日 下午3:36:16
 * @ClassName 跟踪,来源
 * @Description 统计用户来源
 */
@Entity
@Table(name = "TB_TRACE_SOURCE")
public class TraceSource {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long id;

	// 用户微信openId
	@JoinColumn(name = "openid")
	public String openId;

	// 用户扫码时间
	@Column(length = 50, name = "scan_date")
	public Date scanDate;

	// 用户来源
	@Column(length = 50, name = "source")
	public String source;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public Date getScanDate() {
		return scanDate;
	}

	public void setScanDate(Date scanDate) {
		this.scanDate = scanDate;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

}
