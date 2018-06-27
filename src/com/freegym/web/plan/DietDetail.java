package com.freegym.web.plan;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.sanmen.web.core.bean.CommonId;

@Entity
@Table(name = "TB_DIET_DETAIL")
public class DietDetail extends CommonId {

	/**
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	 */

	private static final long serialVersionUID = 923334593743047273L;

	@ManyToOne(targetEntity = Diet.class)
	@JoinColumn(name = "diet")
	private Diet diet;

	/**
	 * 食物名称
	 */
	@Column(length = 20)
	private String mealName;

	/**
	 * 食物重量
	 */
	@Column(length = 20)
	private String mealWeight;

	/**
	 * 食物热量
	 */
	@Column(length = 20)
	private String mealKcal;

	/**
	 * 顺序
	 */
	private Integer sort;

	public DietDetail() {

	}

	public DietDetail(Diet copyToDiet, String mealName, String mealWeight, String mealKcal) {
		this.diet = copyToDiet;
		this.mealName = mealName;
		this.mealWeight = mealWeight;
		this.mealKcal = mealKcal;
	}

	public Diet getDiet() {
		return diet;
	}

	public void setDiet(Diet diet) {
		this.diet = diet;
	}

	public String getMealName() {
		return mealName;
	}

	public void setMealName(String mealName) {
		this.mealName = mealName;
	}

	public String getMealWeight() {
		return mealWeight;
	}

	public void setMealWeight(String mealWeight) {
		this.mealWeight = mealWeight;
	}

	public String getMealKcal() {
		return mealKcal;
	}

	public void setMealKcal(String mealKcal) {
		this.mealKcal = mealKcal;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	@Override
	public String getTableName() {
		// TODO Auto-generated method stub
		return null;
	}

}
