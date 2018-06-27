package com.freegym.web.basic;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.sanmen.web.core.bean.CommonId;

@Entity
@Table(name = "tb_sector_firstpage")
public class FirstPage extends CommonId {

	private static final long serialVersionUID = 1336292809407932952L;

	/**
	 * 推荐人
	 */
	@ManyToOne(targetEntity = Member.class)
	@JoinColumn(name = "recommPerson")
	private Member recommPerson;

	/**
	 * 推荐时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "recommDate")
	private Date recommDate;

	/**
	 * 名称（描述）
	 */
	@Lob
	@Column(name = "name", length = 255)
	private String name;

	/**
	 * 图片
	 */
	@Column(name = "icon", length = 50)
	private String icon;
	
	/**
	 * 图片title
	 */
	@Column(name = "title", length = 50)
	private String title;
	
	/**
	 * 链接
	 */
	@Column(name = "link", length = 100)
	private String link;

	/**
	 * tb_sector表的id
	 */
	@Column(name = "sector", length = 10)
	private Long sector;

	/**
	 * productID
	 */
	@Column(name = "productId", length = 10)
	private Long productId;

	public Member getRecommPerson() {
		return recommPerson;
	}

	public void setRecommPerson(Member recommPerson) {
		this.recommPerson = recommPerson;
	}

	public Date getRecommDate() {
		return recommDate;
	}

	public void setRecommDate(Date recommDate) {
		this.recommDate = recommDate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	
	public Long getSector() {
		return sector;
	}

	public void setSector(Long sector) {
		this.sector = sector;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	@Override
	public String getTableName() {
		// TODO Auto-generated method stub
		return null;
	}

}

	