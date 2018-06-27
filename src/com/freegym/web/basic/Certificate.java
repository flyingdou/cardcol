package com.freegym.web.basic;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.sanmen.web.core.bean.CommonId;

@Entity
@Table(name = "TB_MEMBER_CERTIFICATE")
public class Certificate extends CommonId {

	private static final long serialVersionUID = -7003227477637796819L;

	private Long member;

	@Column(length = 50)
	private String name;// 证书名称

	@Column(length = 20)
	private String no;// 证书编号

	@Column(length = 50)
	private String authority;// 发证机构

	@Temporal(TemporalType.DATE)
	private Date authTime; // 发证日期

	@Column(length = 50)
	private String fileName;// 文件名

	/**
	 * 是否验证，1为已验证，0或NULL为未验证
	 */
	@Column(length = 1, name = "is_valid")
	private String isValid;

	public Long getMember() {
		return member;
	}

	public void setMember(Long member) {
		this.member = member;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public String getAuthority() {
		return authority;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}

	public Date getAuthTime() {
		return authTime;
	}

	public void setAuthTime(Date authTime) {
		this.authTime = authTime;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getIsValid() {
		return isValid;
	}

	public void setIsValid(String isValid) {
		this.isValid = isValid;
	}

	@Override
	public String getTableName() {
		return null;
	}

}
