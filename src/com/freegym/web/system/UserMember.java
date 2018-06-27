package com.freegym.web.system;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.criterion.DetachedCriteria;

import com.freegym.web.basic.Member;
import com.sanmen.web.core.bean.CommonId;
import com.sanmen.web.core.system.User;

/**
 * 用户关联会员表，主要用于数据权限的处理
 * 
 * @author Admin
 * 
 */
@Entity
@Table(name = "tb_user_member")
public class UserMember extends CommonId {

	private static final long serialVersionUID = -5825160828591955376L;

	/**
	 * 后台登录用户
	 */
	@ManyToOne(targetEntity = User.class)
	@JoinColumn(name = "user")
	private User user;

	/**
	 * 前台会员用户
	 */
	@ManyToOne(targetEntity = Member.class)
	@JoinColumn(name = "member")
	private Member member;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public void setMemberId(Long id) {
		if (member == null)
			member = new Member();
		member.setId(id);
	}

	public static DetachedCriteria getCriteriaQuery(UserMember query) {
		final DetachedCriteria dc = DetachedCriteria.forClass(UserMember.class);

		return dc;
	}

	@Override
	public String getTableName() {
		return null;
	}
}
