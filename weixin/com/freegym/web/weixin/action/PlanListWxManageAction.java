package com.freegym.web.weixin.action;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.freegym.web.BaseBasicAction;
import com.freegym.web.basic.Member;
import com.freegym.web.plan.PlanRelease;
import com.freegym.web.utils.DBConstant;

@Namespace("")
@InterceptorRefs({ @InterceptorRef("customStack") })
@Results({ @Result(name = "login", location = "/WX/login.jsp"),
	@Result(name = "success", location = "/WX/planAll.jsp"),
		@Result(name = "list", location = "/WX/planChoice.jsp") })
public class PlanListWxManageAction extends BaseBasicAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7916678459270765913L;

	private String keyword; // 关键字搜索
	private String target;// 健身目的'':全部1:瘦身减重2:促进健康3:肌肉健美4:达到最佳运动状态
	private String targetName;// 计划类型名称
	private String scene;// 适用场景
	private String sceneName;// 适用场景名称
	private String applyObject;// 适用对象
	private String applyObjectName;// 适用对象名称
	private String plancircle;// 计划周期
	private String plancircleName;// 计划周期名称

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public String getTargetName() {
		return targetName;
	}

	public void setTargetName(String targetName) {
		this.targetName = targetName;
	}

	public String getScene() {
		return scene;
	}

	public void setScene(String scene) {
		this.scene = scene;
	}

	public String getSceneName() {
		return sceneName;
	}

	public void setSceneName(String sceneName) {
		this.sceneName = sceneName;
	}

	public String getApplyObject() {
		return applyObject;
	}

	public void setApplyObject(String applyObject) {
		this.applyObject = applyObject;
	}

	public String getApplyObjectName() {
		return applyObjectName;
	}

	public void setApplyObjectName(String applyObjectName) {
		this.applyObjectName = applyObjectName;
	}

	public String getPlancircle() {
		return plancircle;
	}

	public void setPlancircle(String plancircle) {
		this.plancircle = plancircle;
	}

	public String getPlancircleName() {
		return plancircleName;
	}

	public void setPlancircleName(String plancircleName) {
		this.plancircleName = plancircleName;
	}

	/*
	 * 健身计划首页列表 (non-Javadoc)
	 * 
	 * @see com.opensymphony.xwork2.ActionSupport#execute()
	 */
	public String execute() {
		Member member =(Member) session.getAttribute("member");
		if(member == null){
			return "login";
		}else{
		String currentCity = (String) session.getAttribute("currentCity");
		// 找出所有幻灯片
		request.setAttribute("slides", service.findRecommendBySectorCode(
				DBConstant.RECOMM_SECTOR_SLIDES_H, currentCity));
		 @SuppressWarnings("unused")
		Object A = service.findRecommendBySectorCode(
				DBConstant.RECOMM_SECTOR_SLIDES_H, currentCity);
		// 所有健身计划
		StringBuffer sql = new StringBuffer(PlanRelease.getPlanListSql());
		sql.append("where a.audit = '1' and a.isClose = '2' ");
		sql.append("order by publish_time  desc ");
		// 关键字搜索
		if (keyword != null && !"".equals(keyword)) {
			final String where = "%" + keyword + "%";
			sql.append("and a.plan_name like '").append(where).append("' ");
		}
		List<Object> pr = new ArrayList<Object>();
		pageInfo = service
				.findPageBySql(sql.toString(), pageInfo, pr.toArray());
		return SUCCESS;
		}
	}

	/*
	 * 健身计划条件列表筛选
	 */
	public String onTerm() {
		// 计划类型
		request.setAttribute(
				"planTypeList",
				service.findObjectBySql(
						" from Parameter p where p.parent = (select po.id from Parameter po where po.code = ?) order by p.id asc",
						"plan_type_c"));
		// 适用对象
		request.setAttribute(
				"applyObjectList",
				service.findObjectBySql(
						" from Parameter p where p.parent = (select po.id from Parameter po where po.code = ?) order by p.id asc",
						"apply_object_c"));
		// 适用场景
		request.setAttribute(
				"applySceneList",
				service.findObjectBySql(
						" from Parameter p where p.parent = (select po.id from Parameter po where po.code = ?) order by p.id asc",
						"apply_scene_c"));
		// 计划周期
		request.setAttribute(
				"plancircleList",
				service.findObjectBySql(
						" from Parameter p where p.parent = (select po.id from Parameter po where po.code = ?) order by p.id asc",
						"plan_circle_c"));
		// 健身列表
		StringBuffer sql = new StringBuffer(PlanRelease.getPlanListSql());
		sql.append("where a.audit = '1' and a.isClose = '2' ");
		
		// 计划类型
		if (target != null && !"".equals(target)) {
			sql.append("and a.plan_type = '" + target + "' ");
		}
		// 适用场景
		if (scene != null && !"".equals(scene)) {
			sql.append("and a.apply_scene like '%" + scene + "%' ");
		}
		// 适用对象
		if (applyObject != null && !"".equals(applyObject)) {
			sql.append("and a.apply_object = '" + applyObject + "' ");
		}
		// 计划周期
		if (plancircle != null && !"".equals(plancircle)) {
			if (plancircle.equals("A")) {
				sql.append("and datediff(end_date,start_date) <8 ");
			} else if (plancircle.equals("B")) {
				sql.append("and datediff(end_date,start_date) >8 and datediff(end_date,start_date) <31 ");
			} else if (plancircle.equals("C")) {
				sql.append("and datediff(end_date,start_date) >32 and datediff(end_date,start_date) <92 ");
			} else if (plancircle.equals("D")) {
				sql.append("and datediff(end_date,start_date) >92 ");
			}
		}
		
		sql.append("order by publish_time  desc ");
		List<Object> pr = new ArrayList<Object>();
		pageInfo = service
				.findPageBySql(sql.toString(), pageInfo, pr.toArray());
		return "list";
	}
}
