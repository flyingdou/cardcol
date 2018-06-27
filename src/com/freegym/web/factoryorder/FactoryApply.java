package com.freegym.web.factoryorder;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.freegym.web.config.FactoryCosts;
import com.freegym.web.course.BaseApply;

/**
 * 
 * @author Administrator
 * 
 */
@Entity
@Table(name = "TB_FACTORY_APPLY")
public class FactoryApply extends BaseApply {

	private static final long serialVersionUID = 2367352178825768776L;

	@ManyToOne(targetEntity = FactoryCosts.class)
	@JoinColumn(name = "factorycosts")
	private FactoryCosts factorycosts;// 申请场地

	@Column(name = "place_date", length = 10)
	private String placeDate;

	@Column(name = "start_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date startTime; // 预约场地开始时间

	@Column(name = "end_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date endTime; // 预约场地结束时间

	public FactoryCosts getFactorycosts() {
		return factorycosts;
	}

	public void setFactorycosts(FactoryCosts factorycosts) {
		this.factorycosts = factorycosts;
	}

	public String getPlaceDate() {
		return placeDate;
	}

	public void setPlaceDate(String placeDate) {
		this.placeDate = placeDate;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	@Override
	public String getTableName() {
		return "";
	}

}
