package com.freegym.web.system.action;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.freegym.web.SystemBasicAction;
import com.freegym.web.basic.Member;
import com.freegym.web.basic.SystemRecommend;
import com.sanmen.web.core.content.ContentRecommend;

@Namespace("")
@InterceptorRefs({ @InterceptorRef("backStack") })
@Results({ @Result(name = "success", location = "/manager/basic/member_recommend.jsp") })
public class MembeRecommendManageAction extends SystemBasicAction {

	private static final long serialVersionUID = -2297129011587931307L;

	private ContentRecommend recomm, query;

	public ContentRecommend getRecomm() {
		return recomm;
	}

	public void setRecomm(ContentRecommend recomm) {
		this.recomm = recomm;
	}

	public ContentRecommend getQuery() {
		return query;
	}

	public void setQuery(ContentRecommend query) {
		this.query = query;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	protected void executeQuery() {
		final DetachedCriteria dc = SystemRecommend.getCriteriaQuery(query);
		dc.add(Restrictions.eq("recommType", PRODUCT_TYPE_MEMBER));
		if (pageInfo.getOrder() == null) {
			pageInfo.setOrder("id");
			pageInfo.setOrderFlag("desc");
		}
		pageInfo = service.findPageByCriteria(dc, pageInfo);
		final List srs = new ArrayList();
		for (int i = 0; i < pageInfo.getItems().size(); i++) {
			final ContentRecommend cr = (ContentRecommend) pageInfo.getItems().get(i);
			SystemRecommend sr = new SystemRecommend();
			try {
				BeanUtils.copyProperties(sr, cr);
				sr.setMember((Member) service.load(Member.class, cr.getRecommId()));
				srs.add(sr);
			} catch (IllegalAccessException | InvocationTargetException e) {
				log.error("error", e);
			}
		}
		pageInfo.getItems().clear();
		pageInfo.getItems().addAll(srs);
	}

	@Override
	protected Long executeSave() {
		if (recomm != null) {
			recomm.setIcon(saveFile("picture", recomm.getIcon()));
			recomm.setRecommDate(new Date());
			recomm = (ContentRecommend) service.saveOrUpdate(recomm);
			return recomm.getId();
		}
		return null;
	}

	@Override
	protected Class<?> getEntityClass() {
		return ContentRecommend.class;
	}

	@Override
	protected String getExclude() {
		return "ticklings,workouts,actions,applies,parts,courseGrades,coach,coachMembers,friends,clubs,coachs,members,visitors,setting,times,products,certificates,integrals,courses,alwaysAddrs,applys,coursess,records,courseCoachs,courseMembers,prods,appraises,appraises1,allFriends,orderDetails,orderDetails1,messages,messages1,orders,addrs,signIns,signIns1,shops,accounts,prodOrders,aos,actives,partakes,teams,grades,tickets";
	}
}
