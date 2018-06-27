package com.freegym.web.course.action;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.freegym.web.CourseBasicAction;
import com.freegym.web.basic.Member;
import com.freegym.web.course.Apply;
import com.freegym.web.course.BaseApply;
import com.freegym.web.factoryorder.FactoryApply;

@Namespace("")
@InterceptorRefs({ @InterceptorRef("foreStack") })
@Results({ @Result(name = "apply_list", location = "/course/apply_list.jsp") })
public class ApplyManageAction extends CourseBasicAction {

	private static final long serialVersionUID = -3769475961884283745L;

	private Apply apply;

	private Apply query;
	
	private String[] typeandids;

	
	
	public Apply getApply() {
		return apply;
	}

	public void setApply(Apply apply) {
		this.apply = apply;
	}
	
	public Apply getQuery() {
		return query;
	}

	public void setQuery(Apply query) {
		this.query = query;
	}

	public String[] getTypeandids() {
		return typeandids;
	}

	public void setTypeandids(String[] typeandids) {
		this.typeandids = typeandids;
	}
	

	

	public String query() {
		if (query == null) query = new Apply();
		query.setMember(this.toMember());
		Member member = (Member) service.load(Member.class, this.toMember().getId());
		
		final String applySql = Apply.getApplySql();
		final List<Object> parms = new ArrayList<Object>();
		final StringBuffer where = Apply.getWhere(query, parms);
		pageInfo = service.findPageBySql("select *  from (" + applySql + ") tmp where 1 = 1 " + where.toString() + " order by applyDate desc", pageInfo, parms.toArray());
		
		final StringBuffer hql = new StringBuffer(
				"select case when t.status is null then '1' else t.status end status, count(*) cnt from ("
						+ BaseApply.getApplySql() + ") t ");
		if (member.getRole().equals("M")) {
			hql.append(" where t.memberid = ?");
		} else {
			hql.append(" where t.clubid = ?");
		}
		hql.append(" group by case when t.status is null then '1' else t.status end");
		final List<?> countList = service.queryForList(hql.toString(), member.getId());
		request.setAttribute("countList2", getJsonString(countList));
		session.setAttribute("spath", 2);
		return "apply_list";
	}

	@SuppressWarnings("unchecked")
	public String changeStatus() {
		if(typeandids!=null && typeandids.length>0){
			List<Long> courseids = new ArrayList<Long>();
			List<Long> siteids = new ArrayList<Long>();
			for(int i=0;i<typeandids.length;i++){
				String[] arrids = typeandids[i].split(",");
				if(arrids[0].equals("1")){
					courseids.add(Long.parseLong(arrids[1]));
				}else if(arrids[0].equals("2")){
					siteids.add(Long.parseLong(arrids[1]));
				}else{
					// do nothing
				}
			}
			String status = apply.getStatus();
			// 更新课程
			if(courseids.size()>0){
				Long[] arrLong = new Long[courseids.size()];
				List<Apply> applyList = (List<Apply>) service.load(Apply.class, courseids.toArray(arrLong));
				service.saveApplyStatus(status, applyList);
			}
			// 更新场地
			if(siteids.size()>0){
				Long[] arrLong = new Long[siteids.size()];
				List<FactoryApply> factoryApplyList = (List<FactoryApply>) service.load(FactoryApply.class, siteids.toArray(arrLong));
				service.saveSiteApplyStatus(status, factoryApplyList, toMember());
			}
		}
		return query();
	}

	public String delete() {
		if(typeandids!=null && typeandids.length>0){
			List<Long> courseids = new ArrayList<Long>();
			List<Long> siteids = new ArrayList<Long>();
			for(int i=0;i<typeandids.length;i++){
				String[] arrids = typeandids[i].split(",");
				if(arrids[0].equals("1")){
					courseids.add(Long.parseLong(arrids[1]));
				}else if(arrids[0].equals("2")){
					siteids.add(Long.parseLong(arrids[1]));
				}else{
					// do nothing
				}
			}
			// 更新课程
			if(courseids.size()>0){
				Long[] arrLong = new Long[courseids.size()];
				service.deleteApplys(courseids.toArray(arrLong), toMember());
			}
			// 更新场地
			if(siteids.size()>0){
				Long[] arrLong = new Long[siteids.size()];
				service.deleteSiteApplys(siteids.toArray(arrLong), toMember());
			}
		}
		return query();
	}
}
