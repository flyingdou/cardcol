package com.freegym.web.system.action;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.freegym.web.SystemBasicAction;
import com.freegym.web.active.Active;
import com.sanmen.web.core.content.ContentRecommend;

import net.sf.json.JSONObject;

@Namespace("")
@Results({ @Result(name = "success", location = "/manager/order/active.jsp") })
public class Active1ManageAction extends SystemBasicAction {

	private static final long serialVersionUID = -2297129011587931307L;

	private Active query;

	private ContentRecommend sa;

	public Active getQuery() {
		return query;
	}

	public void setQuery(Active query) {
		this.query = query;
	}

	public ContentRecommend getSa() {
		return sa;
	}

	public void setSa(ContentRecommend sa) {
		this.sa = sa;
	}

	@Override
	protected void executeQuery() {
		if (query != null) {
			if (query.getMode() != null) query.setMode(null);
			if (query.getTarget() != null) query.setTarget(null);
		}
		final DetachedCriteria dc = Active.getCriteriaQuery(query);
		loadPermission(dc);
		if (pageInfo.getOrder() == null) {
			pageInfo.setOrder("id");
			pageInfo.setOrderFlag("desc");
		}
		pageInfo = service.findPageByCriteria(dc, pageInfo, 1);
	}

	@Override
	protected void loadAreaPerm(final DetachedCriteria dc, final String str) {
		dc.add(Restrictions.sqlRestriction("this_.creator in (select id from tb_member where city in (select name from tb_area where id in (" + str + ")))"));
	}

	public void onClose() {
		try {
			final List<?> list = getService().load(Active.class, ids);
			for (final Iterator<?> it = list.iterator(); it.hasNext();) {
				final Active obj = (Active) it.next();
				obj.setStatus('A');
			}
			service.saveOrUpdate(list);
			final JSONObject obj = new JSONObject();
			obj.accumulate("success", true).accumulate("message", "OK");
			response(obj);
		} catch (Exception e) {
			log.error("error", e);
			response(e);
		}
	}

	@Override
	public void recommend() {
		try {
			sa.setIcon(saveFile("picture", sa.getIcon()));
			sa.setRecommType(PRODUCT_TYPE_ACTIVE);
			Date nowDate = new Date();
			sa.setRecommDate(nowDate);
			sa.setStickTime(nowDate);
			service.saveOrUpdate(sa);
			response(true, "message: 'ok'");
		} catch (Exception e) {
			response(e);
		}
	}

	@Override
	protected Class<?> getEntityClass() {
		return Active.class;
	}

	@Override
	protected String getExclude() {
		return "applyClubs,order45s,balanceFroms,balanceTos,ticklings,judges,sorts,courseGrades,coach,coachMembers,friends,clubs,coachs,members,visitors,setting,times,products,certificates,integrals,courses,alwaysAddrs,applys,coursess,records,courseCoachs,courseMembers,prods,appraises,appraises1,allFriends,orderDetails,orderDetails1,messages,messages1,orders,addrs,signIns,signIns1,shops,accounts,prodOrders,aos,actives,partakes,teams,tickets";
	}
}
