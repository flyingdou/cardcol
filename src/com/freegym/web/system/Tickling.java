package com.freegym.web.system;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.criterion.DetachedCriteria;

import com.freegym.web.basic.Member;
import com.sanmen.web.core.bean.CommonId;

@Entity
@Table(name = "tb_tickling")
public class Tickling extends CommonId {

	private static final long serialVersionUID = 1503142888667788290L;

	/**
	 * 当前会员
	 */
	@ManyToOne(targetEntity = Member.class)
	@JoinColumn(name = "member")
	private Member member;

	/**
	 * 反馈内容
	 */
	@Column(name = "content", length = 255)
	private String content;

	/**
	 * 联系方式
	 */
	@Column(name = "link", length = 200)
	private String link;

	/**
	 * 创建时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_time")
	private Date createTime;

	/**
	 * 状态,0未处理,1已处理
	 */
	private Character status;

	/**
	 * 结果
	 */
	@Column(name = "result", length = 255)
	private String result;

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Character getStatus() {
		return status;
	}

	public void setStatus(Character status) {
		this.status = status;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	@Override
	public String getTableName() {
		return "反馈信息表";
	}

	public static DetachedCriteria getCriteriaQuery(Tickling query) {
		final DetachedCriteria dc = DetachedCriteria.forClass(Tickling.class);
		return dc;
	}

}
