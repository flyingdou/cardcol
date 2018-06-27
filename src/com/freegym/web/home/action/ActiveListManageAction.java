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

import com.freegym.web.BaseBasicAction;
import com.freegym.web.active.Active;
import com.freegym.web.utils.DBConstant;

@Namespace("")
@InterceptorRefs({ @InterceptorRef("customStack") })
@Results({ @Result(name = "success", location = "/home/challenge.jsp"), @Result(name = "list", location = "/home/activelist_middle.jsp") })
public class ActiveListManageAction extends BaseBasicAction {

	private static final long serialVersionUID = -3769475961884283745L;

	private String mode;
	private String modeName;
	private String target;
	private String targetName;
	private String circle;
	private String circleName;
	private String keyword;

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public String getModeName() {
		return modeName;
	}

	public void setModeName(String modeName) {
		this.modeName = modeName;
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

	public String getCircle() {
		return circle;
	}

	public void setCircle(String circle) {
		this.circle = circle;
	}

	public String getCircleName() {
		return circleName;
	}

	public void setCircleName(String circleName) {
		this.circleName = circleName;
	}

	public String execute() {
		final String currentCity = (String) session.getAttribute("currentCity");
		session.setAttribute("position", 1);
		// 找出所有幻灯片
		request.setAttribute("slides", service.findRecommendBySectorCode(DBConstant.RECOMM_SECTOR_SLIDES_K, currentCity));
		// 查找已经参加活动的用户
		final List<Map<String, Object>> activing = service.findActiving();
        request.setAttribute("challenging", activing);
		// 文章广告位
		request.setAttribute("articles", service.findRecommendBySectorCode(DBConstant.RECOMM_SECTOR_SLIDES_M, currentCity));
		// 慈善机构广告位
		List<Map<String, Object>> findRecommendBySectorCode = service.findRecommendMember(DBConstant.RECOMM_SECTOR_SLIDES_L, "I");
		request.setAttribute("institutions", findRecommendBySectorCode);
		final Double cntMoney = service.queryForDouble("select sum(orderMoney) as money from tb_active_order where status <> '0'", 2);
		request.setAttribute("money", cntMoney);
		final Double modeA = service.queryForDouble(Active.getActiveModeSql("modeA"), 2);
		request.setAttribute("modeA", modeA);
		final Double modeB = service.queryForDouble(Active.getActiveModeSql("modeB"), 2);
		request.setAttribute("modeB", modeB);
		final Double modeC = service.queryForDouble(Active.getActiveModeSql("modeC"), 2);
		request.setAttribute("modeC", modeC);
		final Double modeD = service.queryForDouble(Active.getActiveModeSql("modeD"), 2);
		request.setAttribute("modeD", modeD);
		request.setAttribute("activeModeList", service.findParametersByCodes("active_mode_c"));
		request.setAttribute("activeTargetList", service.findParametersByCodes("active_target_c"));
		request.setAttribute("activeCircleList", service.findParametersByCodes("active_circle_c"));
		onQuery();
		return SUCCESS;
	}

	// 找出所有能参与的挑战
	// 条件1，找出所有对外发布的挑战并且时间在范围内的。
	// 条件2，找出所有不是对外发布的挑战，会员所属自己的俱乐部的挑战
	// 条件3，未关闭的挑战
	// 条件4，如果登录，则包含自已的。
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String onQuery() {
		pageInfo.setPageSize(5);
		StringBuffer sql = new StringBuffer(Active.getActiveSql(mode, target, circle));
		sql.append(" and a.status = 'B'");
		if (!"".equals(pageInfo.getOrder()) && !"".equals(pageInfo.getOrderFlag()) && pageInfo.getOrder() != null && pageInfo.getOrderFlag() != null) {
			sql.append(" order by ");
			if ("createTime".equals(pageInfo.getOrder())) {
				sql.append("a.create_Time ");
			} else if ("partake_num".equals(pageInfo.getOrder())) {
				sql.append("b.partake_num ");
			} else {
				sql.append("a.create_Time ");
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
		if (pageInfo.getItems() != null && pageInfo.getItems().size() > 0) {
			for (final Iterator<?> it = pageInfo.getItems().iterator(); it.hasNext();) {
				Map p = (Map) it.next();
				p.put("avg_grade", p.get("avg_grade") == null ? 0 : new Integer(p.get("avg_grade").toString().split("\\.")[0]));
				if (p.get("category") != null) {
					if (p.get("category").toString().equals("D")) {
						p.put("value", p.get("value") == null ? 0 : new Integer(p.get("value").toString().split("\\.")[0]));
					}
				}
			}
		}
		request.setAttribute("activeModeList", service.findParametersByCodes("active_mode_c"));
		request.setAttribute("activeTargetList", service.findParametersByCodes("active_target_c"));
		request.setAttribute("activeCircleList", service.findParametersByCodes("active_circle_c"));
		return "list";
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String queryByTarget(){
		StringBuffer sb=new StringBuffer();
		sb.append("from Active a where 1=1");
		sb.append("and a.status = 'B'");
		if(target=="A"){
			sb.append("and a.category='A'or a.category='B'");
		}
		else if(target=="C"){
			sb.append("and a.category='E'");
		}
		else if(target=="D"){
			sb.append("and a.category='D'");
		}
		else
			sb.append("and a.category in ('C','F','G','H')");
		sb.append("order by a.createTime desc");
		pageInfo=service.findPageBySql(sb.toString(), pageInfo);
		if (pageInfo.getItems() != null && pageInfo.getItems().size() > 0) {
			for (final Iterator<?> it = pageInfo.getItems().iterator(); it.hasNext();) {
				Map p = (Map) it.next();
				p.put("avg_grade", p.get("avg_grade") == null ? 0 : new Integer(p.get("avg_grade").toString().split("\\.")[0]));
				if (p.get("category") != null) {
					if (p.get("category").toString().equals("D")) {
						p.put("value", p.get("value") == null ? 0 : new Integer(p.get("value").toString().split("\\.")[0]));
					}
				}
			}
		}
		return null;
	}
}
