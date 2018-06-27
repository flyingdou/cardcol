package com.freegym.web.basic;

import javax.persistence.Transient;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.cardcol.web.basic.Product45;
import com.freegym.web.active.Active;
import com.freegym.web.plan.PlanRelease;
import com.sanmen.web.core.content.ContentRecommend;

public class SystemRecommend extends ContentRecommend {

	private static final long serialVersionUID = 359538601907749600L;
	/**
	 * 推荐类型 1:健身卡 2:活动 3:健身计划 4:场地 5:课程 6:智能计划，7：会员推荐，8文章推荐，9广告位,0为一卡通
	 */

	@Transient
	private Product45 product;

	@Transient
	private PlanRelease plan;

	@Transient
	private Active active;

	@Transient
	private Member member;

	public Product45 getProduct() {
		return product;
	}

	public void setProduct(Product45 product) {
		this.product = product;
		if (product != null) {
			setOldTitle(product.getName());
			if (null == getLink() || "".equals(getLink())) setLink("clublist!shoGo.asp?productId=" + product.getId());
		}
	}

	public PlanRelease getPlan() {
		return plan;
	}

	public void setPlan(PlanRelease plan) {
		this.plan = plan;
		if (plan != null) {
			setOldTitle(plan.getPlanName());
			if (null == getLink() || "".equals(getLink())) setLink("plan.asp?pid=" + plan.getId());
		}
	}

	public Active getActive() {
		return active;
	}

	public void setActive(Active active) {
		this.active = active;
		if (active != null) {
			setOldTitle(active.getName());
			if (null == getLink() || "".equals(getLink())) setLink("activewindow.asp?id=" + active.getId());
		}
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
		if (member != null) {
			setOldTitle(member.getName());
			if (null == getLink() || "".equals(getLink()))
				setLink(member.getRole().equals("E") ? "club.asp?member.id=" + member.getId() : "coach.asp?member.id=" + member.getId());
		}
	}

	@Override
	public String getTableName() {
		return "系统推荐表";
	}

	public static DetachedCriteria getCriteriaQuery(SystemRecommend query) {
		final DetachedCriteria dc = DetachedCriteria.forClass(ContentRecommend.class);
		if (query != null) {
			if (query.getId() != null) dc.add(Restrictions.eq("sector", query.getId()));
			if (query.getTitle() != null && !"".equals(query.getTitle())) dc.add(Restrictions.like("title", "%" + query.getTitle() + "%"));
		}
		return dc;
	}

}
