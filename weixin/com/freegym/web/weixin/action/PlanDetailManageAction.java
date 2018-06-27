package com.freegym.web.weixin.action;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.freegym.web.BaseBasicAction;
import com.freegym.web.basic.Member;
import com.freegym.web.plan.PlanRelease;
import com.sanmen.web.core.utils.DateUtil;

@Namespace("")
@InterceptorRefs({ @InterceptorRef("customStack") })
@Results({ @Result(name = "success", location = "/WX/planXq.jsp") })
public class PlanDetailManageAction extends BaseBasicAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5166448834690566133L;

	private PlanRelease planrelease;// 计划详情页
	private Member member;
	private int period;// 计划周期

	public int getPeriod() {
		return period;
	}

	public void setPeriod(int period) {
		this.period = period;
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public PlanRelease getPlanrelease() {
		return planrelease;
	}

	public void setPlanrelease(PlanRelease planrelease) {
		this.planrelease = planrelease;
	}

	/*
	 * 计划详情
	 */
	@SuppressWarnings("unchecked")
	public String execute() {
		// 根据id查询计划详情
		planrelease = (PlanRelease) service.load(PlanRelease.class, id);
		// 计划周期
		period = DateUtil.dateDiff(planrelease.getEndDate(),
				planrelease.getStartDate()) + 1;
		// 适用场景
		planrelease.setScene(planrelease.getScene().replaceAll("A", "健身房")
				.replaceAll("B", "办公室").replaceAll("C", "家庭")
				.replaceAll("D", "户外").replaceAll("E", "其它"));
		// 计划类型
		planrelease.setPlanType(planrelease.getPlanType()
				.replaceAll("A", "瘦身减重").replaceAll("B", "健美增肌")
				.replaceAll("C", "运动康复").replaceAll("D", "提高运动表现"));
		// 适用对象
		planrelease.setApplyObject(planrelease.getApplyObject()
				.replaceAll("A", "初级(从未健身)").replaceAll("B", "中级（6个月健身经历）")
				.replaceAll("C", "高级"));
		// 作者综合评分以及服务人次
		StringBuffer memberSql = new StringBuffer("select * from ("
				+ Member.getMemberSql(null, null, null, null, null, null,
						planrelease.getMember().getCity()) + ") t where t.id=?");
		@SuppressWarnings("rawtypes")
		List memberAppraises = service.queryForList(memberSql.toString(),
				planrelease.getMember().getId());
		for (final Iterator<?> it = memberAppraises.iterator(); it.hasNext();) {
			@SuppressWarnings("rawtypes")
			Map p = (Map) it.next();
			p.put("member_grade", p.get("member_grade") == null ? 0
					: (int) (double) (p.get("member_grade")));
		}
		request.setAttribute("memberAppraise", memberAppraises);
		return SUCCESS;
	}
}
