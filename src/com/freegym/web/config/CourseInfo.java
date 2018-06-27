package com.freegym.web.config;

import java.text.DecimalFormat;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.struts2.json.annotations.JSON;
import org.hibernate.criterion.DetachedCriteria;

import com.freegym.web.basic.Member;
import com.freegym.web.plan.Course;
import com.sanmen.web.core.bean.CommonId;
import com.sanmen.web.core.system.Parameter;

@Entity
@Table(name = "TB_COURSE_INFO")
public class CourseInfo extends CommonId {

	private static final long serialVersionUID = -2479327525438373251L;

	/**
	 * 所属会员
	 */
	@ManyToOne(targetEntity = Member.class)
	@JoinColumn(name = "member", nullable = true)
	private Member member;

	/**
	 * 课程名称
	 */
	@Column(length = 50, nullable = false)
	private String name;

	/**
	 * 课程类型
	 */
	@ManyToOne(targetEntity = Parameter.class)
	@JoinColumn(name = "type", nullable = false)
	private Parameter type;

	/**
	 * 课程运动强度
	 */
	private Integer intensity;

	/**
	 * 课程图标
	 */
	@Column(length = 30)
	private String image;

	/**
	 * 总评分
	 */
	@Transient
	private Double grade;

	/**
	 * 评分人数
	 */
	@Transient
	private Integer gradeNum;

	/**
	 * 平均分数
	 */
	@Transient
	private Double avgGrade;

	/**
	 * 备注
	 */
	@Lob
	@Basic
	private String memo;

	/**
	 * 顺序
	 */
	private Integer sort;

	@OneToMany(targetEntity = Course.class, cascade = CascadeType.REMOVE, fetch = FetchType.LAZY, mappedBy = "courseInfo")
	private Set<Course> courses = new HashSet<Course>();

	public CourseInfo() {

	}

	public CourseInfo(Long id) {
		this.setId(id);
	}

	@JSON(serialize = false)
	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Parameter getType() {
		return type;
	}

	public void setType(Parameter type) {
		this.type = type;
	}

	public Integer getIntensity() {
		return intensity;
	}

	public void setIntensity(Integer intensity) {
		this.intensity = intensity;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Double getGrade() {
		return grade;
	}

	public void setGrade(Double grade) {
		this.grade = grade;
	}

	public Integer getGradeNum() {
		return gradeNum;
	}

	public void setGradeNum(Integer gradeNum) {
		this.gradeNum = gradeNum;
	}

	public Double getAvgGrade() {
		try {
			final DecimalFormat df = new DecimalFormat("#00.00");
			avgGrade = new Double(df.format(grade / gradeNum));
		} catch (Exception e) {

		}
		return avgGrade;
	}

	public void setAvgGrade(Double avgGrade) {
		this.avgGrade = avgGrade;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public Set<Course> getCourses() {
		return courses;
	}

	public void setCourses(Set<Course> courses) {
		this.courses = courses;
	}

	public static DetachedCriteria getCriteriaQuery(CourseInfo query) {
		DetachedCriteria dc = DetachedCriteria.forClass(CourseInfo.class);
		return dc;
	}

	@Override
	public String getTableName() {
		return "课程（服务项目）基本信息表";
	}

	@Override
	public String toString() {
		return "CourseInfo [member=" + member + ", name=" + name + ", type=" + type + ", intensity=" + intensity
				+ ", image=" + image + ", grade=" + grade + ", gradeNum=" + gradeNum + ", avgGrade=" + avgGrade
				+ ", memo=" + memo + ", sort=" + sort + ", courses=" + courses + "]";
	}

}
