package com.freegym.web.course.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.freegym.web.CourseBasicAction;
import com.freegym.web.basic.Member;
import com.freegym.web.course.Appraise;
import com.freegym.web.course.BaseAppraise;

@Namespace("")
@InterceptorRefs({ @InterceptorRef("customStack") })
@Results({ @Result(name = "appraise_list", location = "/course/appraise_list.jsp"),
		@Result(name = "course_appraise", location = "/homeWindow/course_appraise.jsp"),
		@Result(name = "appraise_edit", location = "/course/appraise_edit.jsp") })
public class AppraiseManageAction extends CourseBasicAction {

	private static final long serialVersionUID = -3769475961884283745L;

	private Appraise appraise;

	private Appraise query;

	private String delType;// 删除类型：1删除评价，2删除回复

	private String saveType;// 保存类型：1新增评价，2回复评价

	private Integer appType; // 评论类型

	public Member member;

	public Member toMember;

	public Member getToMember() {
		return toMember;
	}

	public void setToMember(Member toMember) {
		this.toMember = toMember;
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public Appraise getAppraise() {
		return appraise;
	}

	public void setAppraise(Appraise appraise) {
		this.appraise = appraise;
	}

	public Appraise getQuery() {
		return query;
	}

	public void setQuery(Appraise query) {
		this.query = query;
	}

	public String getDelType() {
		return delType;
	}

	public void setDelType(String delType) {
		this.delType = delType;
	}

	public String getSaveType() {
		return saveType;
	}

	public void setSaveType(String saveType) {
		this.saveType = saveType;
	}

	public Integer getAppType() {
		return appType;
	}

	public void setAppType(Integer appType) {
		this.appType = appType;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String query() {
		if (query == null)
			query = new Appraise();
		List<Appraise> appraiseList = (List<Appraise>) service.findObjectBySql(" from Appraise ap where ap.memberTo.id = ? and ap.isRead = ?", new Object[] {
				this.toMember().getId(), "0" });
		for (Appraise a : appraiseList)
			a.setIsRead("1");
		service.saveOrUpdate(appraiseList);
		final StringBuffer sb = new StringBuffer("select * from (" + BaseAppraise.getAppraiseManageSql() + ") tm where 1 = 1 ");
		final List<Object> parms = new ArrayList<Object>();
		if (toMember() != null && toMember().getId() != null) {
			sb.append("AND (tm.fromId = ? or tm.toId = ?) ");
			parms.add(toMember().getId());
			parms.add(toMember().getId());
		}
		sb.append("order by appDate desc");
		System.out.println(sb.toString());
		pageInfo.setPageSize(5);
		pageInfo = service.findPageBySql(sb.toString(), pageInfo, parms.toArray());
		for (final Iterator<?> it = pageInfo.getItems().iterator(); it.hasNext();) {
			Map p = (Map) it.next();
			p.put("grade", p.get("grade")==null?0:(int)(double)(p.get("grade")));
		}
		session.setAttribute("spath", 5);
		return "appraise_list";
	}

	// 进入别人的页面查看评价
	public String queryOther() {
		if (query == null)
			query = new Appraise();
		this.setToMember((Member) session.getAttribute("toMember"));
		query.setMemberTo(this.getToMember());
		final StringBuffer sb = new StringBuffer("select * from (" + BaseAppraise.getAppraiseManageSql() + ") tm where 1 = 1 ");
		final List<Object> parms = new ArrayList<Object>();
		sb.append("AND toId = ? ");
		parms.add(getToMember().getId());
		sb.append("order by appDate desc");
		pageInfo.setPageSize(5);
		pageInfo = service.findPageBySql(sb.toString(), pageInfo, parms.toArray());
		// pageInfo =
		// service.findPageByCriteria(Appraise.getCriteriaQuery(query),
		// this.pageInfo, 4);
		return "course_appraise";
	}

	public String edit() {
		return "appraise_edit";
	}

	public String saveReContent() {
		if (appraise != null && appraise.getId() != null) {
			String reContent = appraise.getReContent();
			appraise = (Appraise) service.load(Appraise.class, appraise.getId());
			appraise.setReContent(reContent);
			appraise.setReAppDate(new Date());
			service.saveOrUpdate(appraise);
			response("ok");
		}
		return null;
	}

	/**
	 * 删除评价
	 * 
	 * @return
	 */
	public String deleteAppraise() {
		if (delType != null) {
			if (appraise != null && appraise.getId() != null) {
				if (delType.equals("1")) {
					service.delete(Appraise.class, appraise.getId());
				} else if (delType.equals("2")) {
					appraise = (Appraise) service.load(Appraise.class, appraise.getId());
					appraise.setReAppDate(null);
					appraise.setReContent(null);
					service.saveOrUpdate(appraise);
				}
			}
		}
		return query();
	}
}
