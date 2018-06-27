package com.freegym.web.home.action;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.freegym.web.BaseBasicAction;
import com.freegym.web.plan.PlanRelease;
import com.freegym.web.utils.DBConstant;

@Namespace("")
@InterceptorRefs({ @InterceptorRef("customStack") })
@Results({ @Result(name = "success", location = "/home/planlist.jsp"), @Result(name = "list", location = "/home/planlist_middle.jsp") })
public class PlanListManageAction extends BaseBasicAction {

	private static final long serialVersionUID = -3769475961884283745L;

	private String keyword; // 关键字搜索
	private String ltarget; // 左上侧状态栏健身计划
	private String ltargetName;// 左上侧状态栏健身计划名称
	private String ptarget; // 健身计划详情页导航
	private String ptargetName;// 健身计划详情页导航
	private String target;// 健身目的'':全部1:瘦身减重2:促进健康3:肌肉健美4:达到最佳运动状态
	private String targetName;// 计划类型名称
	private String scene;// 适用场景
	private String sceneName;// 适用场景名称
	private String applyObject;// 适用对象
	private String applyObjectName;// 适用对象名称
	private String plancircle;// 计划周期
	private String plancircleName;// 计划周期名称

	public String getScene() {
		return scene;
	}

	public void setScene(String scene) {
		this.scene = scene;
	}

	public String getApplyObject() {
		return applyObject;
	}

	public void setApplyObject(String applyObject) {
		this.applyObject = applyObject;
	}

	public String getPlancircle() {
		return plancircle;
	}

	public void setPlancircle(String plancircle) {
		this.plancircle = plancircle;
	}

	public String getLtarget() {
		return ltarget;
	}

	public void setLtarget(String ltarget) {
		this.ltarget = ltarget;
	}

	public String getLtargetName() {
		return ltargetName;
	}

	public void setLtargetName(String ltargetName) {
		this.ltargetName = ltargetName;
	}

	public String getPtarget() {
		return ptarget;
	}

	public void setPtarget(String ptarget) {
		this.ptarget = ptarget;
	}

	public String getPtargetName() {
		return ptargetName;
	}

	public void setPtargetName(String ptargetName) {
		this.ptargetName = ptargetName;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getTargetName() {
		return targetName;
	}

	public void setTargetName(String targetName) {
		this.targetName = targetName;
	}

	public String getApplyObjectName() {
		return applyObjectName;
	}

	public void setApplyObjectName(String applyObjectName) {
		this.applyObjectName = applyObjectName;
	}

	public String getSceneName() {
		return sceneName;
	}

	public void setSceneName(String sceneName) {
		this.sceneName = sceneName;
	}

	public String getPlancircleName() {
		return plancircleName;
	}

	public void setPlancircleName(String plancircleName) {
		this.plancircleName = plancircleName;
	}

	public String execute() {
		String currentCity = (String) session.getAttribute("currentCity");
		// 页面左上侧健身计划、健身计划详情页筛选
		if (target == null || "".equals(target)) {
			if (ltarget != null && !"".equals(ltarget)) {
				target = ltarget;
				targetName = ltargetName;
			}
			if (ptarget != null && !"".equals(ptarget)) {
				target = ptarget;
				targetName = ptargetName;
			}
		}
		session.setAttribute("position", 4);
		// 找出所有幻灯片
		request.setAttribute("slides", service.findRecommendBySectorCode(DBConstant.RECOMM_SECTOR_SLIDES_H, currentCity));
		// 健身计划广告位
		request.setAttribute("planRecommends", service.findRecommendBySectorCode(DBConstant.RECOMM_SECTOR_SLIDES_I, currentCity));
		// 计划类型
		request.setAttribute("planTypeList", service.findObjectBySql(
				" from Parameter p where p.parent = (select po.id from Parameter po where po.code = ?) order by p.id asc", "plan_type_c"));
		// 适用对象
		request.setAttribute("applyObjectList", service.findObjectBySql(
				" from Parameter p where p.parent = (select po.id from Parameter po where po.code = ?) order by p.id asc", "apply_object_c"));
		// 适用场景
		request.setAttribute("applySceneList", service.findObjectBySql(
				" from Parameter p where p.parent = (select po.id from Parameter po where po.code = ?) order by p.id asc", "apply_scene_c"));
		// 计划周期
		request.setAttribute("plancircleList", service.findObjectBySql(
				" from Parameter p where p.parent = (select po.id from Parameter po where po.code = ?) order by p.id asc", "plan_circle_c"));
		pageInfo.setPageSize(9);

		StringBuffer sql = new StringBuffer(PlanRelease.getPlanListSql());
		sql.append("where a.audit = '1' and a.isClose = '2' ");
		// 关键字搜索
		if (keyword != null && !"".equals(keyword)) {
			final String where = "%" + keyword + "%";
			sql.append("and a.plan_name like '").append(where).append("' ");
		}
		// 计划类型
		if (target != null && !"".equals(target)) {
			sql.append("and a.plan_type = '" + target + "' ");
		}
		final List<Object> parms = new ArrayList<Object>();
		pageInfo = service.findPageBySql(sql.toString(), pageInfo, parms.toArray());
		if (pageInfo.getItems().size() > 0) {
			request.setAttribute("appraiseNum", service.loadAppraiseNum("3", pageInfo.getItems()));
		}
		// 自动生成列表
		request.setAttribute("goodsList", service.findObjectBySql(" from Goods"));
		return SUCCESS;
	}

	/**
	 * 健身计划列表条件查询
	 * 
	 * @return
	 */
	public String onQuery() {
		// 计划类型
		request.setAttribute("planTypeList", service.findObjectBySql(
				" from Parameter p where p.parent = (select po.id from Parameter po where po.code = ?) order by p.id asc", "plan_type_c"));
		// 适用对象
		request.setAttribute("applyObjectList", service.findObjectBySql(
				" from Parameter p where p.parent = (select po.id from Parameter po where po.code = ?) order by p.id asc", "apply_object_c"));
		// 适用场景
		request.setAttribute("applySceneList", service.findObjectBySql(
				" from Parameter p where p.parent = (select po.id from Parameter po where po.code = ?) order by p.id asc", "apply_scene_c"));
		// 计划周期
		request.setAttribute("plancircleList", service.findObjectBySql(
				" from Parameter p where p.parent = (select po.id from Parameter po where po.code = ?) order by p.id asc", "plan_circle_c"));
		pageInfo.setPageSize(9);
		StringBuffer sql = new StringBuffer(PlanRelease.getPlanListSql());
		sql.append("where a.audit = '1' and a.isClose = '2' ");
		// 关键字搜索
		if (keyword != null && !"".equals(keyword)) {
			final String where = "%" + keyword + "%";
			sql.append("and a.plan_name like '").append(where).append("' ");
		}
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
		// 排序方式
		if (!"".equals(pageInfo.getOrder()) && !"".equals(pageInfo.getOrderFlag()) && pageInfo.getOrder() != null && pageInfo.getOrderFlag() != null) {
			sql.append(" order by ");
			if ("publishTime".equals(pageInfo.getOrder())) {
				sql.append("a.publish_time ");
			} else if ("saleout".equals(pageInfo.getOrder())) {
				sql.append("saleNum ");
			} else if ("avgCount".equals(pageInfo.getOrder())) {
				sql.append("avgGrade ");
			} else {
				sql.append("a.unit_price ");
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
		if (pageInfo.getItems().size() > 0) {
			request.setAttribute("appraiseNum", service.loadAppraiseNum("3", pageInfo.getItems()));
		}
		return "list";
	}
}
