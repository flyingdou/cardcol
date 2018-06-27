package com.freegym.web.course;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.freegym.web.basic.Member;
import com.freegym.web.utils.DateUtils;
import com.sanmen.web.core.bean.CommonId;

@Entity
@Table(name = "TB_SIGN_IN")
public class SignIn extends CommonId {

	private static final long serialVersionUID = 7563792954778825178L;

	@ManyToOne(targetEntity = Member.class)
	@JoinColumn(name = "memberSign")
	private Member memberSign;// 签到方

	@ManyToOne(targetEntity = Member.class)
	@JoinColumn(name = "memberAudit")
	private Member memberAudit;// 被签到方

	private Long orderId;// 订单Id

	@Column(length = 14)
	private String orderNo;// 订单编号

	@Column(length = 200)
	private String orderNick;// 订单方

	@Temporal(TemporalType.TIMESTAMP)
	private Date signDate;// 签到日期
	
	@Column(name = "SIGN_LNG",length = 12,precision=6)
	private Double signLng;//签到位置经度
	
	@Column(name="SIGN_LAT",length = 12,precision=6)
	private Double signLat;//签到位置纬度
	
	@Column(name="SIGN_TYPE", length=1)
	private String signType;//签到订单类型
	
	/**
	 * 消费金额
	 */
	@Column(name = "f_money", precision = 10, scale = 2)
	private Double money;

	@Transient
	private Date startDate;// 查询用开始时间
	
	@Transient
	private Date endDate;// 查询用开始时间

	@Transient
	private String name;// 查询用会员名称

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getOrderNick() {
		return orderNick;
	}

	public void setOrderNick(String orderNick) {
		this.orderNick = orderNick;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Member getMemberSign() {
		return memberSign;
	}

	public void setMemberSign(Member memberSign) {
		this.memberSign = memberSign;
	}

	public Member getMemberAudit() {
		return memberAudit;
	}

	public void setMemberAudit(Member memberAudit) {
		this.memberAudit = memberAudit;
	}

	public Date getSignDate() {
		return signDate;
	}

	public void setSignDate(Date signDate) {
		this.signDate = signDate;
	}

	public Double getMoney() {
		return money;
	}

	public void setMoney(Double money) {
		this.money = money;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	

	public Double getSignLng() {
		return signLng;
	}

	public void setSignLng(Double signLng) {
		this.signLng = signLng;
	}

	public Double getSignLat() {
		return signLat;
	}

	public void setSignLat(Double signLat) {
		this.signLat = signLat;
	}

	

	public String getSignType() {
		return signType;
	}

	public void setSignType(String signType) {
		this.signType = signType;
	}

	public static DetachedCriteria getCriteriaQuery(SignIn query) {
		DetachedCriteria dc = DetachedCriteria.forClass(SignIn.class);
		dc.createAlias("memberSign", "ms");
		dc.createAlias("memberAudit", "ma");
		if (query != null) {
			if (query.getMemberSign() != null && query.getMemberAudit() != null) {
				if (query.getMemberSign().getId() != null && !"".equals(query.getMemberSign().getId()) && query.getMemberAudit().getId() != null
						&& !"".equals(query.getMemberAudit().getId())) {
					// dc.add(Restrictions.or(Restrictions.eq("ms.id",
					// query.getMemberSign().getId()), Restrictions.eq(
					// "ma.id", query.getMemberAudit().getId())));
					dc.add(Restrictions.or(
							Restrictions.or(Restrictions.eq("ms.id", query.getMemberSign().getId()), Restrictions.eq("ma.id", query.getMemberAudit().getId())),
							Restrictions.eq("orderNick", query.getMemberSign().getNick())));
					// dc.add(Restrictions.eq("orderNick",
					// query.getMemberSign().getNick()));
				}
			}
			if (query.getName() != null && !"".equals(query.getName())) {
				dc.add(Restrictions.or(Restrictions.like("ms.nick", "%" + query.getName() + "%"), Restrictions.like("ma.nick", "%" + query.getName() + "%")));
			}
			if (query.getStartDate() != null) {
				if (query.getEndDate() != null) {
					dc.add(Restrictions.between("signDate", DateUtils.getStartDate(query.getStartDate()), DateUtils.getEndDate(query.getEndDate())));
				} else {
					dc.add(Restrictions.ge("signDate", DateUtils.getStartDate(query.getStartDate())));
				}
			} else {
				if (query.getEndDate() != null) {
					dc.add(Restrictions.le("signDate", DateUtils.getEndDate(query.getEndDate())));
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
	public SignIn (Long id){
		setId(id);
	}
	public SignIn (){
	}

}
