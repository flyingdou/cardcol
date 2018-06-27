package com.freegym.web.system.action;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.hibernate.criterion.DetachedCriteria;

import com.freegym.web.SystemBasicAction;
import com.freegym.web.config.PlanEngineAction;
import com.sanmen.web.core.common.JsonData;

@Namespace("")
@Results({ @Result(name = "success", location = "/manager/system/group.jsp") })
public class GroupManageAction extends SystemBasicAction {

	private static final long serialVersionUID = -2297129011587931307L;

	private PlanEngineAction query;

	public PlanEngineAction getQuery() {
		return query;
	}

	public void setQuery(PlanEngineAction query) {
		this.query = query;
	}

	@Override
	protected void executeQuery() {
		final DetachedCriteria dc = PlanEngineAction.getCriteriaQuery(query);
		if (pageInfo.getOrder() == null) {
			pageInfo.setOrder("id");
			pageInfo.setOrderFlag("desc");
		}
		pageInfo = service.findPageByCriteria(dc, pageInfo);
	}

	@Override
	protected Long executeSave() {
		final Map<String, Class<?>> classMap = new HashMap<String, Class<?>>();
		classMap.put("data", PlanEngineAction.class);
		final JsonData jd = (JsonData) JSONObject.toBean(JSONObject.fromObject(jsons), JsonData.class, classMap);
		service.saveOrUpdate(jd.getData());
		return 0l;
	}

	protected Class<?> getEntityClass() {
		return PlanEngineAction.class;
	}
}
