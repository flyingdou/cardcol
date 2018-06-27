package com.freegym.web.order;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "tb_goods_order")
public class GoodsOrder extends Order {

	private static final long serialVersionUID = -1946847679725288926L;

	/**
	 * 活动主表(商品表)
	 */
	@ManyToOne(targetEntity = Goods.class)
	@JoinColumn(name = "goods")
	private Goods goods;
	
	
	/**
	 * 地址表
	 */
	@OneToMany()
	@JoinColumn(name = "alwaysAddr")
	private Set<AlwaysAddr> alwaysAddrs = new HashSet<AlwaysAddr>();
			

	public GoodsOrder() {

	}

	public GoodsOrder(Long id) {
		setId(id);
	}

	public Goods getGoods() {
		return goods;
	}

	public void setGoods(Goods goods) {
		this.goods = goods;
	}

	public Set<AlwaysAddr> getAlwaysAddrs() {
		return alwaysAddrs;
	}

	public void setAlwaysAddrs(Set<AlwaysAddr> alwaysAddrs) {
		this.alwaysAddrs = alwaysAddrs;
	}
	
	
	

}
