package com.freegym.web.plan;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import com.freegym.web.config.Action;
import com.sanmen.web.core.bean.CommonId;

@Entity
@Table(name = "TB_WORKOUT")
public class Workout extends CommonId implements Cloneable {

	private static final long serialVersionUID = -1820307765393359293L;

	@ManyToOne(targetEntity = Course.class, cascade = CascadeType.ALL)
	@JoinColumn(name = "course")
	private Course course;

	@ManyToOne(targetEntity = Action.class, cascade = CascadeType.ALL)
	@JoinColumn(name = "action")
	private Action action;

	private Integer sort; // 动作顺序

	@OneToMany(targetEntity = WorkoutDetail.class, mappedBy = "workout", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@OrderBy("sort asc")
	private Set<WorkoutDetail> details = new HashSet<WorkoutDetail>();

	public Workout() {

	}

	public Workout(Course course, Action action, Integer sort) {
		this.course = course;
		this.action = action;
		this.sort = sort;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public Action getAction() {
		return action;
	}

	public void setAction(Action action) {
		this.action = action;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public Set<WorkoutDetail> getDetails() {
		return details;
	}

	public void setDetails(Set<WorkoutDetail> details) {
		this.details = details;
	}

	public void addDetail(WorkoutDetail newDetail) {
		newDetail.setWorkout(this);
		getDetails().add(newDetail);
	}

	@Override
	public String getTableName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
}
