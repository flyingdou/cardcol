package com.freegym.web.home.action;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.freegym.web.BaseBasicAction;
import com.freegym.web.basic.Member;
import com.freegym.web.utils.DBConstant;
import com.sanmen.web.core.system.Area;
import com.sanmen.web.core.system.Parameter;

@Namespace("")
@InterceptorRefs({ @InterceptorRef("customStack") })
@Results({ @Result(name = "coach_step", location = "/home/coach_step.jsp"), @Result(name = "success", location = "/home/coachlist.jsp"),
		@Result(name = "list", location = "/home/coachlist_middle.jsp") })
public class CoachListManageAction extends BaseBasicAction {

	private static final long serialVersionUID = -3769475961884283745L;
	/**
	 * 查询类型
	 */
	private Character queryType;
	private String typeId;// 服务项目
	private String typeName;// 服务项目
	private List<Parameter> typeList;
	private String area;// 地区
	private List<Area> areaList;
	private String grade;// 评分'':全部2:20分以下3:20-40分4:40-60分5:60-80分6:80-100分
	private String speciality; // 专长，A瘦身减重，B促进健康，C肌肉健美，D达到最佳运动状态
	private String specialityName;
	private String mode; // 私教形式，A网络线上指导，B传统线下服务
	private String modeName;
	private String style; // 教练类型，A私人教练，B团体教练
	private String styleName; // “私人教练”、“团体教练”。
	private String sex; // 性别,'M':'男','F':'女
	private String sexName; // 性别,'M':'男','F':'女
	private String orderType;// 排序方式,''：默认2：发布时间3：销量4：评分
	private String step;
	private String keyword; // 关键字搜索
	private String ltstyle; // 左上侧列表传来的私教形式
	private String ltstyleName;
	private String course;
	private String courseName;
	private String county;

	public Character getQueryType() {
		return queryType;
	}

	public void setQueryType(Character queryType) {
		this.queryType = queryType;
	}

	public String getStep() {
		return step;
	}

	public void setStep(String step) {
		this.step = step;
	}

	public String getSpeciality() {
		return speciality;
	}

	public void setSpeciality(String speciality) {
		this.speciality = speciality;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getTypeId() {
		return typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public List<Parameter> getTypeList() {
		return typeList;
	}

	public void setTypeList(List<Parameter> typeList) {
		this.typeList = typeList;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public List<Area> getAreaList() {
		return areaList;
	}

	public void setAreaList(List<Area> areaList) {
		this.areaList = areaList;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getCourse() {
		return course;
	}

	public void setCourse(String course) {
		this.course = course;
	}

	public String getCounty() {
		return county;
	}

	public void setCounty(String county) {
		this.county = county;
	}

	public String getSpecialityName() {
		return specialityName;
	}

	public void setSpecialityName(String specialityName) {
		this.specialityName = specialityName;
	}

	public String getModeName() {
		return modeName;
	}

	public void setModeName(String modeName) {
		this.modeName = modeName;
	}

	public String getStyleName() {
		return styleName;
	}

	public void setStyleName(String styleName) {
		this.styleName = styleName;
	}

	public String getSexName() {
		return sexName;
	}

	public void setSexName(String sexName) {
		this.sexName = sexName;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public String getLtstyle() {
		return ltstyle;
	}

	public void setLtstyle(String ltstyle) {
		this.ltstyle = ltstyle;
	}

	public String getLtstyleName() {
		return ltstyleName;
	}

	public void setLtstyleName(String ltstyleName) {
		this.ltstyleName = ltstyleName;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String execute() {
		session.setAttribute("position", 3);
		String currentCity = (String) session.getAttribute("currentCity");
		request.setAttribute("areaList",
				service.findObjectBySql("from Area a where a.parent in (select a1.id from Area a1 where a1.name = ? and a1.parent != null)", currentCity));
		request.setAttribute("courseList", service.findParametersByCodes("course_type_c"));
		request.setAttribute("specialityList", service.findParametersByCodes("plan_type_c"));
		request.setAttribute("serviceTypeList", service.findParametersByCodes("coach_servicetype_c"));
		request.setAttribute("styleList", service.findParametersByCodes("coach_type_c1"));
		pageInfo.setPageSize(8);
		// 左上侧健身教练
		if (style == null || "".equals(style)) {
			if (ltstyle != null && !"".equals(ltstyle)) {
				style = ltstyle;
				styleName = ltstyleName;
			}
		}
		StringBuffer sql1=new StringBuffer(Member.getMemberSql(null, null, null, null, null, null, null));
		sql1.append("and m.role = 'S' ");
		sql1.append("order by member_grade");
		Object o=service.queryForList(sql1.toString());
		request.setAttribute("HotCoach", o);
		request.setAttribute("coachRecommends", service.findRecommendBySectorCode(DBConstant.RECOMM_SECTOR_SLIDES_C, currentCity));
		StringBuffer sql = new StringBuffer(Member.getMemberSql(county, speciality, course, mode, style, sex, currentCity));
		sql.append(" and m.role = 'S' ");
		// 关键字搜索
		if (keyword != null && !"".equals(keyword)) {
			final String where = "%" + keyword + "%";
			sql.append(" and (m.name like '").append(where).append("' or m.nick like '").append(where).append("' or m.email like '").append(where)
					.append("' or m.mobilephone like '").append(where).append("')");
		}
		final List<Object> parms = new ArrayList<Object>();
		pageInfo = service.findPageBySql(sql.toString(), pageInfo, parms.toArray());
		for (final Iterator<?> it = pageInfo.getItems().iterator(); it.hasNext();) {
			Map p = (Map) it.next();
			p.put("member_grade", p.get("member_grade")==null?0:(int)(double)(p.get("member_grade")));
		}
		if (pageInfo.getItems().size() > 0) {
			request.setAttribute("products", service.loadProductsByMember(pageInfo.getItems()));
		}
		return SUCCESS;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String onQuery() {
		pageInfo.setPageSize(8);
		String currentCity = (String) session.getAttribute("currentCity");
		StringBuffer sql = new StringBuffer(Member.getMemberSql(county, speciality, course, mode, style, sex, currentCity));
		sql.append(" and m.role = 'S' ");
		if (!"".equals(pageInfo.getOrder()) && !"".equals(pageInfo.getOrderFlag()) && pageInfo.getOrder() != null && pageInfo.getOrderFlag() != null) {
			sql.append(" order by ");
			if ("birthday".equals(pageInfo.getOrder())) {
				sql.append("birthday ");
			} else if ("salesNum".equals(pageInfo.getOrder())) {
				sql.append("salesNum ");
			} else if ("member_grade".equals(pageInfo.getOrder())) {
				sql.append("member_grade ");
			} else {
				sql.append("birthday ");
			}
			if ("asc".equals(pageInfo.getOrderFlag())) {
				sql.append("asc");
			} else if ("desc".equals(pageInfo.getOrderFlag())) {
				sql.append("desc");
			} else {
				sql.append("desc ");
			}
		}
		final List<Object> parms = new ArrayList<Object>();
		pageInfo = service.findPageBySql(sql.toString(), pageInfo, parms.toArray());
		for (final Iterator<?> it = pageInfo.getItems().iterator(); it.hasNext();) {
			Map p = (Map) it.next();
			p.put("member_grade", p.get("member_grade")==null?0:(int)(double)(p.get("member_grade")));
		}
		if (pageInfo.getItems().size() > 0) {
			request.setAttribute("products", service.loadProductsByMember(pageInfo.getItems()));
		}
		request.setAttribute("areaList",
				service.findObjectBySql("from Area a where a.parent in (select a1.id from Area a1 where a1.name = ? and a1.parent != null)", currentCity));
		request.setAttribute("courseList", service.findParametersByCodes("course_type_c"));
		request.setAttribute("specialityList", service.findParametersByCodes("plan_type_c"));
		request.setAttribute("serviceTypeList", service.findParametersByCodes("coach_servicetype_c"));
		request.setAttribute("styleList", service.findParametersByCodes("coach_type_c1"));
		return "list";
	}

	public String queryCoach() {
		pageInfo.setPageSize(8);
		DetachedCriteria dc = DetachedCriteria.forClass(Member.class);
		dc.add(Restrictions.eq("role", "S"));
		if (typeId != null && !"".equals(typeId)) {
			dc.add(Restrictions.sqlRestriction("this_.id in (select member from tb_course_info where type = " + typeId + ")"));
		}
		if (speciality != null && !"".equals(speciality)) {
			dc.add(Restrictions.eq("speciality", speciality));
		}
		if (mode != null && !"".equals(mode)) {
			dc.add(Restrictions.eq("mode", mode));
		}
		if (style != null && !"".equals(style)) {
			dc.add(Restrictions.eq("style", style));
		}
		if (sex != null && !"".equals(sex)) {
			dc.add(Restrictions.eq("sex", sex));
		}
		pageInfo.setOrder("stickTime");
		pageInfo.setOrderFlag("desc");
		pageInfo = service.findPageByCriteria(dc, pageInfo);
		if (pageInfo.getItems().size() > 0) {
			request.setAttribute("products", service.loadProductsByMember(pageInfo.getItems()));
		}
		return "coach_step";
	}

}
