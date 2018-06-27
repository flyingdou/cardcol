package com.cardcol.web.basic;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.freegym.web.basic.Member;
import com.sanmen.web.core.bean.CommonId;

@Entity
@Table(name = "TB_PRODUCT_CLUB_V45")
public class ProductClub45 extends CommonId {

	private static final long serialVersionUID = -7285672210382024659L;

	/**
	 * 一卡通商品
	 */
	@ManyToOne(targetEntity = Product45.class)
	@JoinColumn(name = "PRODUCT")
	private Product45 product;

	/**
	 * 俱乐部
	 */
	@ManyToOne(targetEntity = Member.class)
	@JoinColumn(name = "CLUB")
	private Member club;

	public ProductClub45() {

	}

	public ProductClub45(long id) {
		club = new Member(id);
	}

	public Product45 getProduct() {
		return product;
	}

	public void setProduct(Product45 product) {
		this.product = product;
	}

	public Member getClub() {
		return club;
	}

	public void setClub(Member club) {
		this.club = club;
	}

	@Override
	public String getTableName() {
		return "商品适用俱乐部明细";
	}

}
