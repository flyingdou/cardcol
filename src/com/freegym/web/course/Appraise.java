package com.freegym.web.course;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.freegym.web.basic.Member;

@Entity
@Table(name = "TB_MEMBER_APPRAISE")
public class Appraise extends BaseAppraise {

	private static final long serialVersionUID = 7563792954778825178L;

	@ManyToOne(targetEntity = Member.class)
	@JoinColumn(name = "memberTo")
	private Member memberTo;// 被评价方

	public Member getMemberTo() {
		return memberTo;
	}

	public void setMemberTo(Member memberTo) {
		this.memberTo = memberTo;
	}

	public static DetachedCriteria getCriteriaQuery(Appraise query) {
		final DetachedCriteria dc = DetachedCriteria.forClass(Appraise.class);
		if (query != null) {
			if (query.getMember() != null && query.getMember().getId() != null) {
				if (query.getMemberTo() != null && query.getMemberTo().getId() != null) {
					dc.add(Restrictions.or(Restrictions.eq("member.id", query.getMember().getId()),
							Restrictions.eq("memberTo.id", query.getMemberTo().getId())));
				} else {
					dc.add(Restrictions.eq("member.id", query.getMemberTo().getId()));
				}
			} else {
				if (query.getMemberTo() != null && query.getMemberTo().getId() != null) {
					dc.add(Restrictions.eq("memberTo.id", query.getMemberTo().getId()));
				}
			}
		}
		return dc;
	}

	@Override
	public String getTableName() {
		return null;
	}

}
