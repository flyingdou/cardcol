package com.freegym.web.order;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.sanmen.web.core.bean.CommonId;

@Entity
@Table(name = "TB_SYSTEM_FORMS")
public class SystemForms extends CommonId {
	private static final long serialVersionUID = -7358255008331013507L;

	@Column(length = 50)
	private String FORM_CODE;// 表名

	@Column(length = 2)
	private String FORM_PREFIX;// 前缀

	private Integer FORM_YEAR;// 年

	private Integer FORM_MONTH;// 月

	private Integer FORM_ORDER;// 当前值

	public String getFORM_CODE() {
		return FORM_CODE;
	}

	public void setFORM_CODE(String fORM_CODE) {
		FORM_CODE = fORM_CODE;
	}

	public String getFORM_PREFIX() {
		return FORM_PREFIX;
	}

	public void setFORM_PREFIX(String fORM_PREFIX) {
		FORM_PREFIX = fORM_PREFIX;
	}

	public Integer getFORM_YEAR() {
		return FORM_YEAR;
	}

	public void setFORM_YEAR(Integer fORM_YEAR) {
		FORM_YEAR = fORM_YEAR;
	}

	public Integer getFORM_MONTH() {
		return FORM_MONTH;
	}

	public void setFORM_MONTH(Integer fORM_MONTH) {
		FORM_MONTH = fORM_MONTH;
	}

	public Integer getFORM_ORDER() {
		return FORM_ORDER;
	}

	public void setFORM_ORDER(Integer fORM_ORDER) {
		FORM_ORDER = fORM_ORDER;
	}

	@Override
	public String getTableName() {
		// TODO Auto-generated method stub
		return null;
	}

}
