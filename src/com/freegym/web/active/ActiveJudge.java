package com.freegym.web.active;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.freegym.web.basic.Member;
import com.sanmen.web.core.bean.CommonId;

/**
 * 
 * @author snowe
 *
 */
@Entity
@Table(name = "TB_ACTIVE_JUDGE")
public class ActiveJudge extends CommonId {

	private static final long serialVersionUID = 1238159499867041602L;

	/**
	 * 裁判
	 */
	@ManyToOne(targetEntity = Member.class)
	@JoinColumn(name = "JUDGE")
	private Member judge;

	/**
	 * 活动主表
	 */
	@ManyToOne(targetEntity = Active.class)
	@JoinColumn(name = "ACTIVE")
	private Active active;

	public Member getJudge() {
		return judge;
	}

	public void setJudge(Member judge) {
		this.judge = judge;
	}

	public Active getActive() {
		return active;
	}

	public void setActive(Active active) {
		this.active = active;
	}

	@Override
	public String getTableName() {
		return "活动裁判表";
	}

}
