package com.freegym.web.order;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.freegym.web.course.BaseAppraise;

@Entity
@Table(name = "tb_product_grade")
public class ProductGrade extends BaseAppraise{
	private static final long serialVersionUID = 5067764877142594672L;
	
	/**
	 * 健身卡主表
	 */
	@ManyToOne(targetEntity = Product.class)
	@JoinColumn(name = "product")
	private Product product;

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}
	
	@Override
	public String getTableName(){
		return "健身卡评论表";
	}
}
