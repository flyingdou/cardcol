package com.freegym.web.active;

import java.util.ArrayList;
import java.util.List;

public class Challeng {

	private Long id;

	private String name;

	private String image;
	
	private String judgeID;

	private List<Active> actives = new ArrayList<Active>();

	public Challeng(Long id, String nick, String image, Active active, String judgeID) {
		this.id = id;
		this.name = nick;
		this.image = image;
		this.judgeID = judgeID;
		this.actives.add(active);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public List<Active> getActives() {
		return actives;
	}

	public void setActives(List<Active> actives) {
		this.actives = actives;
	}

	public boolean equals(Long id) {
		return this.id.equals(id);
	}

	public String getJudgeID() {
		return judgeID;
	}

	public void setJudgeID(String judgeID) {
		this.judgeID = judgeID;
	}
	
}
