package com.freegym.web.plan;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import net.sf.json.JSONObject;

import com.freegym.web.basic.Member;
import com.sanmen.web.core.bean.CommonId;

@Entity
@Table(name = "TB_MUSIC")
public class Music extends CommonId {
	private static final long serialVersionUID = -3907467273449357483L;

	@ManyToOne(targetEntity = Member.class)
	@JoinColumn(name = "member")
	private Member member;

	/**
	 * 音乐名称
	 */
	@Column(length = 50)
	private String name;

	/**
	 * 音乐地址
	 */
	@Column(length = 50)
	private String addr;
	
	/**
	 * 音乐描述
	 */
	@Column(length = 100)
	private String desciption;

	public Music() {

	}

	public Music(Member member, String name, String addr, String desciption) {
		this.member = member;
		this.name = name;
		this.addr = addr;
		this.desciption = desciption;
	}

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

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public String getDesciption() {
		return desciption;
	}

	public void setDesciption(String desciption) {
		this.desciption = desciption;
	}

	@Override
	public String getTableName() {
		return "音乐信息表";
	}

	public Object toJson() {
		final JSONObject obj = new JSONObject();
		obj.accumulate("id", getId()).accumulate("name", getName()).accumulate("addr", getAddr()).accumulate("desciption", getDesciption())
				.accumulate("memberId", getMember().getId());
		return obj;
	}
}
