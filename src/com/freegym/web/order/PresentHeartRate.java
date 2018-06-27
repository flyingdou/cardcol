package com.freegym.web.order;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.criterion.DetachedCriteria;

import com.freegym.web.basic.Member;
import com.sanmen.web.core.bean.CommonId;

@Entity
@Table(name = "TB_PRESENT_HEARTRATE")
public class PresentHeartRate extends CommonId {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(length = 1000)
	private String heartRates;

	@Temporal(TemporalType.DATE)
	private Date train_date;
	
	@ManyToOne(targetEntity = Member.class)
	@JoinColumn(name = "member")
	private Member member;// 合同创建者

	@Temporal(TemporalType.TIMESTAMP)
	private Date start_date;
	@Temporal(TemporalType.TIMESTAMP)
	private Date end_date;
	
	private Integer time_diff;
	
	public String getHeartRates() {
		return heartRates;
	}


	public void setHeartRates(String heartRates) {
		this.heartRates = heartRates;
	}


	public Date getTrain_date() {
		return train_date;
	}


	public void setTrain_date(Date train_date) {
		this.train_date = train_date;
	}


	public Member getMember() {
		return member;
	}


	public void setMember(Member member) {
		this.member = member;
	}


	public Date getStart_date() {
		return start_date;
	}


	public void setStart_date(Date start_date) {
		this.start_date = start_date;
	}


	public Date getEnd_date() {
		return end_date;
	}


	public void setEnd_date(Date end_date) {
		this.end_date = end_date;
	}


	public Integer getTime_diff() {
		return time_diff;
	}


	public void setTime_diff(Integer time_diff) {
		this.time_diff = time_diff;
	}
	
	public static DetachedCriteria getCriteriaQuery(PresentHeartRate query) {
		DetachedCriteria dc = DetachedCriteria.forClass(PresentHeartRate.class);
		return dc;
	}

	@Override
	public String getTableName() {
		// TODO Auto-generated method stub
		return "手环实时心率表";
	}

}
