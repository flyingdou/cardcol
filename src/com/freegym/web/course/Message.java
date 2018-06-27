package com.freegym.web.course;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.freegym.web.basic.Member;
import com.sanmen.web.core.bean.CommonId;

@Entity
@Table(name = "TB_MESSAGE")
public class Message extends CommonId {

	private static final long serialVersionUID = -7003227477637796819L;
	@ManyToOne(targetEntity = Member.class)
	@JoinColumn(name = "memberFrom")
	private Member memberFrom;// 发件人/申请人

	@ManyToOne(targetEntity = Member.class)
	@JoinColumn(name = "memberTo")
	private Member memberTo;// 收件人/审批人

	@Temporal(TemporalType.TIMESTAMP)
	private Date sendTime; // 发送时间

	@Column(length = 200)
	private String content; // 信息内容

	/**
	 * 父消息，原则上是同一个人一个会话
	 */
	private Long parent;

	@Column(length = 1)
	private String type; // 信息类型1:申请，2：消息，3：提醒

	@Column(length = 1)
	private String isRead; // 是否阅读0:未阅读，1：已阅读

	@Column(length = 1)
	private String status; // 申请状态1:待审2：成功3：拒绝

	/**
	 * 回复的消息
	 */
	@OneToMany(targetEntity = Message.class, cascade = CascadeType.REMOVE, fetch = FetchType.LAZY, mappedBy = "parent")
	@OrderBy("sendTime desc")
	private Set<Message> messages = new HashSet<Message>();

	public Message() {
		super();
	}

	public Message(Member memberFrom, Member memberTo, Date sendTime, String content, String type, String isRead, String status) {
		super();
		this.memberFrom = memberFrom;
		this.memberTo = memberTo;
		this.sendTime = sendTime;
		this.content = content;
		this.type = type;
		this.isRead = isRead;
		this.status = status;
	}

	public String getContent() {
		return content;
	}

	public Long getParent() {
		return parent;
	}

	public void setParent(Long parent) {
		this.parent = parent;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Member getMemberFrom() {
		return memberFrom;
	}

	public void setMemberFrom(Member memberFrom) {
		this.memberFrom = memberFrom;
	}

	public Member getMemberTo() {
		return memberTo;
	}

	public void setMemberTo(Member memberTo) {
		this.memberTo = memberTo;
	}

	public Date getSendTime() {
		return sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getIsRead() {
		return isRead;
	}

	public void setIsRead(String isRead) {
		this.isRead = isRead;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Set<Message> getMessages() {
		return messages;
	}

	public void setMessages(Set<Message> messages) {
		this.messages = messages;
	}

	public static DetachedCriteria getCriteriaQuery(Message query, Member member, String queryType) {
		DetachedCriteria dc = DetachedCriteria.forClass(Message.class);
		if (queryType != null && !"".equals(queryType)) {
			if (queryType.equals("1")) {
				dc.add(Restrictions.eq("type", "1"));
				dc.add(Restrictions.eq("memberTo.id", member.getId()));
			} else if (queryType.equals("2")) {
				dc.add(Restrictions.eq("type", "2"));
				dc.add(Restrictions.eq("memberTo.id", member.getId()));
			} else if (queryType.equals("3")) {
				dc.add(Restrictions.eq("type", "3"));
				dc.add(Restrictions.eq("memberTo.id", member.getId()));
			} else if (queryType.equals("4")) {
				dc.add(Restrictions.eq("type", "2"));
				dc.add(Restrictions.eq("memberFrom.id", member.getId()));
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
