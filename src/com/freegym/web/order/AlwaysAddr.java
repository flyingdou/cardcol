package com.freegym.web.order;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.freegym.web.basic.Member;
import com.sanmen.web.core.bean.CommonId;

@Entity
@Table(name = "TB_ALWAYS_ADDR")
public class AlwaysAddr extends CommonId {

	private static final long serialVersionUID = -7358255008331013507L;

	@ManyToOne(targetEntity = Member.class)
	@JoinColumn(name = "member")
	private Member member;// 会员

	@Column(length = 50)
	private String name; // 收货人姓名

	@Column(length = 30)
	private String province; // 省

	@Column(length = 50)
	private String city; // 市

	@Column(length = 50)
	private String county; // 县

	@Column(length = 50)
	private String addr; // 地址

	@Column(length = 11)
	private String moble; // 手机

	@Column(length = 12)
	private String tell; // 固定电话

	@Column(length = 50)
	private String email; // 电子邮件

	@Column(length = 6)
	private String zipCode; // 邮政编码

	@Column(length = 1)
	private String isAlways;// 是否常用地址0：非常用，1：常用地址

	

	public String getIsAlways() {
		return isAlways;
	}

	public void setIsAlways(String isAlways) {
		this.isAlways = isAlways;
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

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCounty() {
		return county;
	}

	public void setCounty(String county) {
		this.county = county;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public String getMoble() {
		return moble;
	}

	public void setMoble(String moble) {
		this.moble = moble;
	}

	public String getTell() {
		return tell;
	}

	public void setTell(String tell) {
		this.tell = tell;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	@Override
	public String getTableName() {
		// TODO Auto-generated method stub
		return null;
	}

}
