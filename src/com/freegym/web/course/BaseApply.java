package com.freegym.web.course;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.freegym.web.basic.Member;
import com.freegym.web.utils.DateUtils;
import com.sanmen.web.core.bean.CommonId;

@MappedSuperclass
public class BaseApply extends CommonId {

	private static final long serialVersionUID = 3061987305674407034L;

	@ManyToOne(targetEntity = Member.class)
	@JoinColumn(name = "member")
	private Member member;// 申请人

	@Column(length = 1)
	private String status;// 申请状态1:待审2：成功3：拒绝

	@Temporal(TemporalType.TIMESTAMP)
	private Date approveDate;// 审批时间

	@Temporal(TemporalType.TIMESTAMP)
	private Date applyDate;// 预约时间

	@Column(length = 200)
	private String remark;// 备注

	@Column(length = 1)
	private String isReadBuy; // 审批人是否阅读0:未阅读，1：已阅读

	@Column(length = 1)
	private String isReadSell; // 申请人是否阅读0:未阅读，1：已阅读

	@Transient
	private Date startDate;// 查询用开始时间

	@Transient
	private Date endDate;// 查询用开始时间

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getApproveDate() {
		return approveDate;
	}

	public void setApproveDate(Date approveDate) {
		this.approveDate = approveDate;
	}

	public Date getApplyDate() {
		return applyDate;
	}

	public void setApplyDate(Date applyDate) {
		this.applyDate = applyDate;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getIsReadBuy() {
		return isReadBuy;
	}

	public void setIsReadBuy(String isReadBuy) {
		this.isReadBuy = isReadBuy;
	}

	public String getIsReadSell() {
		return isReadSell;
	}

	public void setIsReadSell(String isReadSell) {
		this.isReadSell = isReadSell;
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

	@Override
	public String getTableName() {
		return null;
	}

	public static String getApplySql() {
		final StringBuffer sql = new StringBuffer();
		sql.append("SELECT a.id AS applyid,'1' AS applyType, a.applyDate,a.approveDate,a.isReadBuy,a.isReadSell,a.status,a.course AS projectid,a.member AS memberid,b.place, b.planDate, b.startTime ,b.endTime, c.name AS projectname, d.name AS membername,c.member AS clubid, e.nick coachname FROM TB_COURSE_APPLY a  LEFT JOIN tb_course b ON a.course = b.id LEFT JOIN TB_COURSE_INFO c ON b.courseId = c.id LEFT JOIN tb_member d ON a.member = d.id left join tb_member e on b.coach = e.id");
		sql.append(" UNION ALL ");
		sql.append("SELECT a.id AS applyid,'2' AS applyType, a.applyDate,a.approveDate,a.isReadBuy,a.isReadSell,a.status,a.factorycosts AS projectid,a.member AS memberid,c.name AS place,a.place_date AS planDate , a.start_time AS startTime, a.end_time AS endTime, d.name AS projectname, e.name AS membername,c.club AS clubid, '' coachname FROM TB_FACTORY_APPLY a LEFT JOIN TB_MEMBER_FACTORY_COSTS b ON a.factorycosts = b.id LEFT JOIN TB_MEMBER_FACTORY c ON b.factory = c.id LEFT JOIN tb_parameter d ON c.project = d.id LEFT JOIN tb_member e ON a.member = e.id");
		return sql.toString();
	}
	
	public static StringBuffer getWhere(Apply query, List<Object> parms) {
		final StringBuffer sb = new StringBuffer();
		if (query != null) {
			if(query.getStatus()!=null){
				sb.append(" and status = ?");
				parms.add(query.getStatus());
			}
			if (query.getMember() != null) {
				if (query.getMember().getId() != null && !"".equals(query.getMember().getId())) {
					if (query.getMember().getRole().equals("M")) {
						sb.append(" and memberid = ?");
						parms.add(query.getMember().getId());
					} else if (query.getMember().getRole().equals("E")) {
						sb.append(" and clubid = ?");
						parms.add(query.getMember().getId());
					} else if (query.getMember().getRole().equals("S")) {
						if(query.getStatus()!=null){
							if(query.getStatus().equals("1")){
								sb.append(" and clubid = ?");
								parms.add(query.getMember().getId());
							}else{
								sb.append(" and memberid = ?");
								parms.add(query.getMember().getId());
							}
						}
					}
				}
			}
			if (query.getStartDate() != null) {
				if (query.getEndDate() != null) {
					sb.append(" and (applyDate between ? and ?)");
					parms.add(DateUtils.getStartDate(query.getStartDate()));
					parms.add(DateUtils.getEndDate(query.getEndDate()));
				} else {
					sb.append(" and applyDate >= ?");
					parms.add(DateUtils.getStartDate(query.getStartDate()));
				}
			} else {
				if (query.getEndDate() != null) {
					sb.append(" and applyDate <= ?");
					parms.add(DateUtils.getEndDate(query.getEndDate()));
				}
			}
		}
		return sb;

	}
}
