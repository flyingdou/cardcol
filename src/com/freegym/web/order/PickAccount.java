package com.freegym.web.order;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.freegym.web.basic.Member;
import com.sanmen.web.core.bean.CommonId;

@Entity
@Table(name = "TB_PICK_ACCOUNT")
public class PickAccount extends CommonId {

	private static final long serialVersionUID = -7358255008331013507L;

	@ManyToOne(targetEntity = Member.class)
	@JoinColumn(name = "member")
	private Member member;// 会员

	@Column(length = 50)
	private String name;// 账户名称

	@Column(length = 50)
	private String bankName;// 开户行

	@Column(length = 50)
	private String account;// 账号

	@OneToMany(targetEntity = PickDetail.class, mappedBy = "pickAccount", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
	private Set<PickDetail> pickDetails = new HashSet<PickDetail>();

	public Set<PickDetail> getPickDetails() {
		return pickDetails;
	}

	public void setPickDetails(Set<PickDetail> pickDetails) {
		this.pickDetails = pickDetails;
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

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public static DetachedCriteria getCriteriaQuery(PickAccount query) {
		DetachedCriteria dc = DetachedCriteria.forClass(PickAccount.class);
		if (query != null) {
			if (query.getMember() != null) {
				if (query.getMember().getId() != null && !"".equals(query.getMember().getId())) {
					dc.add(Restrictions.eq("member.id", query.getMember().getId()));
				}
			}
		}
		return dc;
	}

	@Override
	public String getTableName() {
		// TODO Auto-generated method stub
		return null;
	}
}
