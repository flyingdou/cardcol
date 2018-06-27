package com.freegym.web.config;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.sanmen.web.core.bean.CommonId;

import net.sf.json.JSONObject;

@Entity
@Table(name = "TB_MEMBER_FACTORY")
public class Factory extends CommonId {

	private static final long serialVersionUID = -2662894643623010379L;

	/**
	 * 俱乐部
	 */
	private Long club;

	/**
	 * 场地名称
	 */
	@Column(name = "name", length = 50)
	private String name;

	/**
	 * 运动项目id
	 */
	@Column(name = "project", length = 2)
	private String project;

	/**
	 * 备注
	 */
	@Column(length = 255)
	private String memo;

	/**
	 * 顺序
	 */
	private Integer sort;

	/**
	 * 是否生效，0未生效，1已生效
	 */
	private Character applied;

	/**
	 * 原名称
	 */
	@Transient
	private String oldName;

	/**
	 * 平均分
	 */
	@Column(name = "avgCount", length = 10)
	private Integer avgCount;

	/**
	 * 评价总人数
	 */
	@Column(name = "appraisenum", length = 10)
	private Integer appraiseNum;

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

	public String getProject() {
		return project;
	}

	public void setProject(String project) {
		this.project = project;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public Character getApplied() {
		return applied;
	}

	public void setApplied(Character applied) {
		this.applied = applied;
	}

	public String getOldName() {
		return oldName;
	}

	public void setOldName(String oldName) {
		this.oldName = oldName;
	}

	public Integer getAvgCount() {
		return avgCount;
	}

	public void setAvgCount(Integer avgCount) {
		this.avgCount = avgCount;
	}

	public Integer getAppraiseNum() {
		return appraiseNum;
	}

	public void setAppraiseNum(Integer appraiseNum) {
		this.appraiseNum = appraiseNum;
	}

	@Override
	public String getTableName() {
		return "场地信息表";
	}

	public JSONObject toJson() {
		final JSONObject obj = new JSONObject();
		obj.accumulate("id", getId()).accumulate("club", club).accumulate("name", name).accumulate("project", project).accumulate("memo", memo)
				.accumulate("sort", sort).accumulate("applied", applied);
		return obj;
	}
}
