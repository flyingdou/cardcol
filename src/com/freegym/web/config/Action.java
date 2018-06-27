package com.freegym.web.config;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.freegym.web.plan.Workout;
import com.sanmen.web.core.bean.CommonId;

@Entity
@Table(name = "TB_PROJECT_ACTION")
public class Action extends CommonId {

	private static final long serialVersionUID = 1686612623564639129L;

	/**
	 * 动作名称
	 */
	@Column(length = 50)
	private String name;

	/**
	 * 部位
	 */
	@ManyToOne(targetEntity = Part.class)
	@JoinColumn(name = "part")
	private Part part;

	/**
	 * 卧推系数
	 */
	@Column(length = 30)
	private String coefficient;

	/**
	 * 肌肉属性
	 */
	@Column(length = 200)
	private String muscle;

	/**
	 * 描述
	 */
	@Column(length = 250)
	private String descr;

	/**
	 * 注意事项
	 */
	@Column(length = 255)
	private String regard;

	/**
	 * 演示图片
	 */
	@Column(length = 200)
	private String image;

	/**
	 * 演示动画，网页用。一般为FLASH
	 */
	@Column(length = 200)
	private String flash;

	/**
	 * 演示视频，手机用。一般为MP4
	 */
	@Column(length = 200)
	private String video;

	/**
	 * 必须输入重量
	 */
	private Character needWeight;

	/**
	 * 必须输入距离
	 */
	private Character needDistance;

	/**
	 * 必须输入时间
	 */
	private Character needTime;

	/**
	 * 是否系统内定
	 */
	private Character system;

	/**
	 * 动作所属教练
	 */
	private Long member;

	@OneToMany(targetEntity = Workout.class, mappedBy = "action", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	private Set<Workout> workouts = new HashSet<Workout>();

	public Action() {

	}

	public Action(long id) {
		setId(id);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Part getPart() {
		return part;
	}

	public void setPart(Part part) {
		this.part = part;
	}

	public String getCoefficient() {
		return coefficient;
	}

	public void setCoefficient(String coefficient) {
		this.coefficient = coefficient;
	}

	public String getMuscle() {
		return muscle;
	}

	public void setMuscle(String muscle) {
		this.muscle = muscle;
	}

	public String getDescr() {
		return descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	public String getRegard() {
		return regard;
	}

	public void setRegard(String regard) {
		this.regard = regard;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getFlash() {
		return flash;
	}

	public void setFlash(String flash) {
		this.flash = flash;
	}

	public String getVideo() {
		return video;
	}

	public void setVideo(String video) {
		this.video = video;
	}

	public Character getNeedWeight() {
		return needWeight;
	}

	public void setNeedWeight(Character needWeight) {
		this.needWeight = needWeight;
	}

	public Character getNeedDistance() {
		return needDistance;
	}

	public void setNeedDistance(Character needDistance) {
		this.needDistance = needDistance;
	}

	public Character getNeedTime() {
		return needTime;
	}

	public void setNeedTime(Character needTime) {
		this.needTime = needTime;
	}

	public Character getSystem() {
		return system;
	}

	public void setSystem(Character system) {
		this.system = system;
	}

	public Long getMember() {
		return member;
	}

	public void setMember(Long member) {
		this.member = member;
	}

	public Set<Workout> getWorkouts() {
		return workouts;
	}

	public void setWorkouts(Set<Workout> workouts) {
		this.workouts = workouts;
	}

	@Override
	public String getTableName() {
		return null;
	}

	public static DetachedCriteria getCriteriaQuery(Action query) {
		final DetachedCriteria dc = DetachedCriteria.forClass(Action.class);
		dc.createAlias("part", "pt");
		dc.createAlias("pt.project", "p");
		dc.add(Restrictions.eq("p.system", '1'));
		if (query != null) {
			if (query.getName() != null && !"".equals(query.getName())) dc.add(Restrictions.like("name", "%" + query.getName() + "%"));
			if (query.getPart() != null) {
				if (query.getPart().getName() != null && "".equals(query.getPart().getName())) {
					dc.add(Restrictions.like("pt.name", "%" + query.getPart().getName() + "%"));
				}
			}
		}
		return dc;
	}

}
