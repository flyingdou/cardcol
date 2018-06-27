package com.freegym.web.system.action;

import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.hibernate.criterion.DetachedCriteria;

import com.freegym.web.SystemBasicAction;
import com.freegym.web.system.Tickling;

@Namespace("")
@Results({ @Result(name = "success", location = "/manager/system/tickling.jsp") })
public class TicklingManageAction extends SystemBasicAction {

	private static final long serialVersionUID = -2297129011587931307L;

	private Tickling tick, query;

	public Tickling getTick() {
		return tick;
	}

	public void setTick(Tickling tick) {
		this.tick = tick;
	}

	public Tickling getQuery() {
		return query;
	}

	public void setQuery(Tickling query) {
		this.query = query;
	}

	@Override
	protected void executeQuery() {
		final DetachedCriteria dc = Tickling.getCriteriaQuery(query);
		if (pageInfo.getOrder() == null) {
			pageInfo.setOrder("createTime");
			pageInfo.setOrderFlag("desc");
		}
		pageInfo = service.findPageByCriteria(dc, pageInfo);
	}

	@Override
	protected Long executeSave() {
		final Tickling t = (Tickling) service.load(Tickling.class, tick.getId());
		t.setStatus(tick.getStatus());
		t.setResult(tick.getResult());
		service.saveOrUpdate(t);
		return t.getId();
	}

	@Override
	protected String getExclude() {
		return "judges,courseGrades,coach,coachMembers,friends,clubs,coachs,members,visitors,setting,times,products,certificates,integrals,courses,alwaysAddrs,applys,coursess,records,courseCoachs,courseMembers,prods,appraises,appraises1,allFriends,orderDetails,orderDetails1,messages,messages1,orders,addrs,signIns,signIns1,shops,accounts,prodOrders,aos,actives,partakes,teams,tickets,ticklings";
	}
}
