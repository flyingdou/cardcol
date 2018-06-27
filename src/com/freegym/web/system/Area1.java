package com.freegym.web.system;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.criterion.DetachedCriteria;

import com.sanmen.web.core.bean.CommonId;

@Entity
@Table(name = "tb_area")
public class Area1 extends CommonId {

	private static final long serialVersionUID = 4256982972347754688L;

	private String name;

	private Long parent;

	private Character open;

	private Character letter;

	private Character hotCity;

	private Character type; // 0省会，1市，2县

	@OneToMany(targetEntity = Area1.class, mappedBy = "parent", cascade = CascadeType.REMOVE)
	@OrderBy("id asc")
	private Set<Area1> childs = new HashSet<Area1>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getParent() {
		return parent;
	}

	public void setParent(Long parent) {
		this.parent = parent;
	}

	public Character getOpen() {
		return open;
	}

	public void setOpen(Character open) {
		this.open = open;
	}

	public Character getLetter() {
		return letter;
	}

	public void setLetter(Character letter) {
		this.letter = letter;
	}

	public Character getHotCity() {
		return hotCity;
	}

	public void setHotCity(Character hotCity) {
		this.hotCity = hotCity;
	}

	public Character getType() {
		return type;
	}

	public void setType(Character type) {
		this.type = type;
	}

	public Set<Area1> getChilds() {
		return childs;
	}

	public void setChilds(Set<Area1> childs) {
		this.childs = childs;
	}

	public static DetachedCriteria getCriteriaQuery(Area1 query) {
		DetachedCriteria dc = DetachedCriteria.forClass(Area1.class);
		return dc;
	}

	@Override
	public String getTableName() {
		// TODO Auto-generated method stub
		return null;
	}
}
