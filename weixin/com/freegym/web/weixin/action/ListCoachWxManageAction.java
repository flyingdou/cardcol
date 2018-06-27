package com.freegym.web.weixin.action;

import java.util.ArrayList;
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
import com.freegym.web.course.BaseAppraise;
import com.freegym.web.order.Product;
import com.freegym.web.plan.PlanRelease;

@Namespace("")
@InterceptorRefs({ @InterceptorRef("customStack") })
@Results({ @Result(name = "success", location = "/WX/qingCoach.jsp"),
		@Result(name = "detail", location = "/WX/teachXq.jsp"),
		@Result(name = "summary", location = "/WX/coachsummary.jsp") })
public class ListCoachWxManageAction extends BaseBasicAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3215412849335107878L;
	private String speciality;
	private Member member;
	private Product product;

	/*
	 * private String course; private String county; private String mode;
	 * private String style; private String sex;
	 */

	public String getSpeciality() {
		return speciality;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public void setSpeciality(String speciality) {
		this.speciality = speciality;
	}

	/*
	 * 教练列表
	 */
	public String execute() {
		StringBuffer sql = new StringBuffer();
		sql.append("select * from tb_Member m where m.speciality = '"
				+ speciality + "' ");
		sql.append("and m.role = 'S' ");
		sql.append("and m.grade = 1 ");
		sql.append("order by grade desc");
		final List<Object> parms = new ArrayList<Object>();
		pageInfo = service.findPageBySql(sql.toString(), pageInfo,
				parms.toArray());
		return SUCCESS;
	}

	/*
	 * 教练详情页
	 */
	@SuppressWarnings("unchecked")
	public String detail() {
			member = (Member) service.load(Member.class, id);
			/*product = (Product) service.load(Product.class,product.getId());
			product = (Product) service.load(Product.class, productId);
			if (product != null) {
				// 健身卡简介，根据健身卡类型而定
				List<Map<String, Object>> parameterList = service.queryForList(
						"SELECT * FROM tb_parameter p WHERE p.parent = (SELECT id FROM tb_parameter WHERE CODE = 'card_type_c') and p.code = ?",
						product.getProType());
				if (parameterList != null && parameterList.size() > 0) {
					request.setAttribute("memo", parameterList.get(0).get("memo"));
				}*/
			
		// 查询评价人数以及评分
		StringBuffer memberSql = new StringBuffer(Member.getMemberSql(null,
				null, null, null, null, null, member.getCity()));
		memberSql.append(" and m.id = ?");
		final List<?> memberList = service.queryForList(memberSql.toString(),
				member.getId());
		for (final Iterator<?> it = memberList.iterator(); it.hasNext();) {
			@SuppressWarnings("rawtypes")
			Map map = (Map) it.next();
			member.setCountEmp(new Integer(map.get("countEmp").toString()));
			member.setAvgGrade(new Integer(map.get("member_grade").toString()
					.split("\\.")[0]));
		}
		// 服务项目
		@SuppressWarnings("rawtypes")
		List list = service.queryForList(BaseAppraise.getcourseInfoAppraise()
				+ " where a.member = ?", member.getId());
		for (final Iterator<?> it = list.iterator(); it.hasNext();) {
			@SuppressWarnings("rawtypes")
			Map p = (Map) it.next();
			p.put("avg_grade",
					p.get("avg_grade") == null ? 0 : Integer.valueOf((String) p
							.get("avg_grade")));
		}
			request.setAttribute("courses", list);
		 //健身计划
			String planSql = "select * from (" + PlanRelease.getPlanSql() + ") t where t.member = ? and t.audit=? and t.isClose=? order by t.publishTime desc limit 3";
			request.setAttribute("plans", service.queryForList(planSql, member.getId(),1,2));
		 return "detail";
	}

	/*
	 * 教练简介
	 */
	public String summary(){
		member = (Member) service.load(Member.class, id);
		request.setAttribute("certs", service.findObjectBySql("from Certificate c where c.member = ?", member.getId()));
		return "summary";
	}
}
