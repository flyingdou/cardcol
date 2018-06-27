package com.freegym.web.plan;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import com.sanmen.web.core.bean.CommonId;

@Entity
@Table(name = "TB_DIET")
public class Diet extends CommonId {

	/**
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	 */
	private static final long serialVersionUID = 923334593743047273L;

	private Long member;

	/**
	 * 计划日期
	 */
	@Column(length = 10)
	private String planDate;

	/**
	 * 开始时间
	 */
	@Column(name = "start_time", length = 5)
	private String startTime;

	/**
	 * 结束时间
	 */
	@Column(name = "end_time", length = 5)
	private String endTime;

	/**
	 * 餐数,系统默认早餐，中餐，晚餐，宵夜下拉选择
	 */
	@Column(length = 20)
	private String mealNum;

	@OneToMany(targetEntity = DietDetail.class, cascade = { CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE }, fetch = FetchType.LAZY, mappedBy = "diet")
	@OrderBy("sort asc")
	private Set<DietDetail> details = new HashSet<DietDetail>();

	public Diet() {

	}

	public Diet(Long toMember, String copyToPlanDate) {
		this.member = toMember;
		this.planDate = copyToPlanDate;
	}

	public Diet(long id) {
		super.setId(id);
	}

	public Long getMember() {
		return member;
	}

	public void setMember(Long member) {
		this.member = member;
	}

	public String getPlanDate() {
		return planDate;
	}

	public void setPlanDate(String planDate) {
		this.planDate = planDate;
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

	public String getMealNum() {
		return mealNum;
	}

	public void setMealNum(String mealNum) {
		this.mealNum = mealNum;
	}

	public Set<DietDetail> getDetails() {
		return details;
	}

	public void setDetails(Set<DietDetail> details) {
		this.details = details;
	}

	public void addDetail(DietDetail dd) {
		dd.setDiet(this);
		details.add(dd);
	}

	@Override
	public String getTableName() {
		// TODO Auto-generated method stub
		return null;
	}

}
