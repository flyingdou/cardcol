package com.freegym.web.system.action;

import java.util.HashMap;
import java.util.Map;

import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.hibernate.criterion.DetachedCriteria;

import com.freegym.web.SystemBasicAction;
import com.freegym.web.config.PlanEngineRule;
import com.sanmen.web.core.common.JsonData;

import net.sf.json.JSONObject;

@Namespace("")
@Results({ @Result(name = "success", location = "/manager/system/rule.jsp") })
public class RuleManageAction extends SystemBasicAction {

	private static final long serialVersionUID = -2297129011587931307L;

	private PlanEngineRule rule, query;

	public PlanEngineRule getRule() {
		return rule;
	}

	public void setRule(PlanEngineRule rule) {
		this.rule = rule;
	}

	public PlanEngineRule getQuery() {
		return query;
	}

	public void setQuery(PlanEngineRule query) {
		this.query = query;
	}

	@Override
	protected void executeQuery() {
		final DetachedCriteria dc = PlanEngineRule.getCriteriaQuery(query);
		if (pageInfo.getOrder() == null) {
			pageInfo.setOrder("id");
			pageInfo.setOrderFlag("desc");
		}
		pageInfo = service.findPageByCriteria(dc, pageInfo);
	}

	@Override
	protected Long executeSave() {
		final Map<String, Class<?>> classMap = new HashMap<String, Class<?>>();
		classMap.put("data", PlanEngineRule.class);
		final JsonData jd = (JsonData) JSONObject.toBean(JSONObject.fromObject(jsons), JsonData.class, classMap);
		service.saveOrUpdate(jd.getData());
		return 0l;
	}

	protected Class<?> getEntityClass() {
		return PlanEngineRule.class;
	}

	@Override
	protected String getExclude() {
		return "ticklings,sorts,courseGrades,coach,coachMembers,friends,clubs,coachs,members,visitors,setting,times,products,certificates,integrals,courses,alwaysAddrs,applys,coursess,records,courseCoachs,courseMembers,prods,appraises,appraises1,allFriends,orderDetails,orderDetails1,messages,messages1,orders,addrs,signIns,signIns1,shops,accounts,prodOrders,aos,actives,partakes,teams,tickets";
	}
}
