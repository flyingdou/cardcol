package com.freegym.web.order;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.freegym.web.basic.Member;
import com.freegym.web.utils.DateUtils;
import com.sanmen.web.core.bean.CommonId;

@Entity
@Table(name = "TB_PICK_DETAIL")
public class PickDetail extends CommonId {

	private static final long serialVersionUID = -7358255008331013507L;

	@Column(length = 14)
	private String no;// 流水号

	@ManyToOne(targetEntity = Member.class)
	@JoinColumn(name = "member")
	private Member member;// 提现人

	private Double pickMoney;// 提现金额

	@Temporal(TemporalType.TIMESTAMP)
	private Date pickDate;// 提现时间

	@ManyToOne(targetEntity = PickAccount.class)
	@JoinColumn(name = "pickAccount")
	private PickAccount pickAccount;// 提现账户

	@Column(length = 1)
	private String type;// 类型1：提现

	@Column(length = 1)
	private String flowType;// 资金流向1：支出2:收入

	@Column(length = 1)
	private String status;// 状态1:请求处理中,2:成功,3:失败

	@Column(length = 200)
	private String remark;// 备注

	@Transient
	private Date startDate;// 查询用开始时间

	@Transient
	private Date endDate;// 查询用开始时间

	@Transient
	private String identificationCode;// 验证码
	
	@Transient
	private Float balance; // 当前用户的可用余额 

	public String getIdentificationCode() {
		return identificationCode;
	}

	public void setIdentificationCode(String identificationCode) {
		this.identificationCode = identificationCode;
	}

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getFlowType() {
		return flowType;
	}

	public void setFlowType(String flowType) {
		this.flowType = flowType;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public PickAccount getPickAccount() {
		return pickAccount;
	}

	public void setPickAccount(PickAccount pickAccount) {
		this.pickAccount = pickAccount;
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public Double getPickMoney() {
		return pickMoney;
	}

	

	public void setPickMoney(Double pickMoney) {
		this.pickMoney = pickMoney;
	}

	public Date getPickDate() {
		return pickDate;
	}

	public void setPickDate(Date pickDate) {
		this.pickDate = pickDate;
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

	public Float getBalance() {
		return balance;
	}

	public void setBalance(Float balance) {
		this.balance = balance;
	}
			
			
			
	@SuppressWarnings("deprecation")
	public static DetachedCriteria getCriteriaQuery(PickDetail query) {
		final DetachedCriteria dc = DetachedCriteria.forClass(PickDetail.class);
		try {
		dc.createAlias("member", "m", Criteria.LEFT_JOIN);
		if (query != null) {
			if (query.getMember() != null) {
				if (query.getMember().getId() != null && !"".equals(query.getMember().getId())) {
					dc.add(Restrictions.eq("m.id", query.getMember().getId()));
				}
			}
			if (query.getStartDate() != null) {
				if (query.getEndDate() != null) {
					dc.add(Restrictions.between("pickDate", DateUtils.getStartDate(query.getStartDate()),
							DateUtils.getEndDate(query.getEndDate())));
				} else {
					dc.add(Restrictions.ge("pickDate", DateUtils.getStartDate(query.getStartDate())));
				}
			} else {
				if (query.getEndDate() != null) {
					dc.add(Restrictions.le("pickDate", DateUtils.getEndDate(query.getEndDate())));
				}
			}
		}
		
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dc;
	}

	@Override
	public String getTableName() {
		return null;
	}
}
