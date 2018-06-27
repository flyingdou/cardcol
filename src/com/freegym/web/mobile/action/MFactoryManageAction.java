package com.freegym.web.mobile.action;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;

import com.freegym.web.config.Factory;
import com.freegym.web.config.FactoryCosts;
import com.freegym.web.mobile.BasicJsonAction;
import com.sanmen.web.core.common.LogicException;
import com.sanmen.web.core.system.Parameter;
import com.sanmen.web.core.utils.DateUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Namespace("")
@InterceptorRefs({ @InterceptorRef("mobileStack") })
public class MFactoryManageAction extends BasicJsonAction {

	private static final long serialVersionUID = -4900715126840183203L;

	@SuppressWarnings("unused")
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	private Long toMember;

	private String projectId;

	private Long factoryId;

	private String planDate;

	/**
	 * 加载当前俱乐部的所有有效项目及第一个项目的当前日期所有的场地预约制定情况
	 * 
	 * @author LIUHB
	 * @param toMember
	 *            , planDate
	 */
	@Override
	public void load() {

		try {
			if (planDate == null) planDate = DateUtil.getDateString(new Date());
			// 取得该俱乐部所有有效动动项目
			final List<Parameter> params = service.findProjectForValid(toMember);
			if (params.size() == 0) throw new LogicException("该俱乐部无运动项目！");
			final JSONObject obj = new JSONObject();
			obj.accumulate("success", true);
			final JSONArray jarr = new JSONArray();
			for (final Parameter param : params) {
				jarr.add(param.toJson());
			}
			obj.accumulate("projects", jarr);
			// 取得该俱乐部所有有效运动场地
			if (projectId == null | "".equals(projectId)) {
				projectId = Long.toString(params.get(0).getId());
			}
			final List<Factory> factorys = service.findFactoryByMember(toMember, projectId);
			final JSONArray jarr2 = new JSONArray();
			for (final Factory f : factorys) {
				jarr2.add(f.toJson());
			}
			obj.accumulate("factorys", jarr2);
			// 取得该俱乐部当前日期，第一个运动项目的所有场地制订列表。
			if (factorys.size() > 0) {
				if (factoryId == null) {
					factoryId = factorys.get(0).getId();
				}
				final List<FactoryCosts> costs = service.findFactoryCostsByMember(toMember, factoryId, planDate);
				// final JSONArray jarr1 = new JSONArray();
				final JSONArray jarr3 = new JSONArray();
				for (final FactoryCosts fc : costs) {
					// 获取该场地可预定时间段
					try {
						List<Map<String, Object>> queryForList = service.executeProcedure("proc_split_factory", fc.getId());
						for (Iterator<?> it = queryForList.iterator(); it.hasNext();) {
							final JSONObject obj1 = fc.toJson();
							Map<?, ?> map = (Map<?, ?>) it.next();
							obj1.accumulate("sp_starttime", map.get("sp_starttime")).accumulate("sp_endtime", map.get("sp_endtime"));
							jarr3.add(obj1);
						} ;
					} catch (Exception e) {
						log.error("error", e);
					}

					// jarr1.add(fc.toJson().accumulate("splitTime", jarr3));
				}
				obj.accumulate("factoryCosts", jarr3);
			}
			response(obj);
		} catch (Exception e) {
			log.error("error", e);
			response(e);
		}
	}

	@Override
	protected String getExclude() {
		return "partakes";
	}

	public Long getToMember() {
		return toMember;
	}

	public String getPlanDate() {
		return planDate;
	}

	public void setToMember(Long toMember) {
		this.toMember = toMember;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public Long getFactoryId() {
		return factoryId;
	}

	public void setFactoryId(Long factoryId) {
		this.factoryId = factoryId;
	}

	public void setPlanDate(String planDate) {
		this.planDate = planDate;
	}

}
