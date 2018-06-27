package com.freegym.web.system.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.freegym.web.SystemBasicAction;
import com.freegym.web.basic.Member;
import com.freegym.web.course.Message;
import com.freegym.web.plan.PlanRelease;
import com.sanmen.web.core.content.ContentRecommend;

@Namespace("")
@Results({ @Result(name = "success", location = "/manager/order/plan.jsp") })
public class Plan1ManageAction extends SystemBasicAction {

	private static final long serialVersionUID = -2297129011587931307L;

	private PlanRelease plan, query;

	private ContentRecommend sa;

	public PlanRelease getPlan() {
		return plan;
	}

	public void setPlan(PlanRelease plan) {
		this.plan = plan;
	}

	public PlanRelease getQuery() {
		return query;
	}

	public void setQuery(PlanRelease query) {
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
		final DetachedCriteria dc = PlanRelease.getCriteriaQuery(query);
		loadPermission(dc);
		if (pageInfo.getOrder() == null) {
			pageInfo.setOrder("id");
			pageInfo.setOrderFlag("desc");
		}
		pageInfo = service.findPageByCriteria(dc, pageInfo);
	}

	@Override
	protected void loadAreaPerm(final DetachedCriteria dc, final String str) {
		dc.add(Restrictions.sqlRestriction("this_.member in (select id from tb_member where city in (select name from tb_area where id in (" + str + ")))"));
	}

	@Override
	protected Long executeSave() {
		if (plan != null) {
			plan = (PlanRelease) service.saveOrUpdate(plan);
			return plan.getId();
		}
		return null;
	}

	public String showDetail() {
		plan = (PlanRelease) service.load(PlanRelease.class, id);
		// request.setAttribute("costProjects1",
		// StringUtils.stringToList(product.getCostProjects(), ","));
		return "detail";
	}

	@SuppressWarnings("unused")
	@Override
	public void audit() {
		try {
			final List<?> list = getService().load(PlanRelease.class, ids);
			for (final Iterator<?> it = list.iterator(); it.hasNext();) {
				final PlanRelease obj = (PlanRelease) it.next();
				obj.setAudit(String.valueOf(getAudit()));
			}
			getService().saveOrUpdate(list);
			if (getAudit() == '1') {
				final List<Message> messages = new ArrayList<Message>();
				for (final Iterator<?> it = list.iterator(); it.hasNext();) {
					final PlanRelease p = (PlanRelease) it.next();
					final String msg = getMessage("mobile.validate.product.audit", new Object[] { p.getPlanName() });
					// 2013/6/24 delete liuhb, 取消短信发送功能，增加系统消息发送功能
					// sendSms(p.getMember().getMobilephone(), msg);
					final Message msgs = new Message();
					msgs.setContent("您发布的[" + p.getPlanName() + "]已经通过审核并正式发布。");
					msgs.setIsRead("0");
					msgs.setMemberFrom(new Member(1l));
					msgs.setMemberTo(p.getMember());
					msgs.setSendTime(new Date());
					msgs.setStatus("0");
					msgs.setType("3");
					messages.add(msgs);
				}
				service.saveOrUpdate(messages);
			}
			response("{success: true, desc: 'OK'}");
		} catch (Exception e) {
			log.error("error", e);
			response("{success: false, desc: '" + e.getMessage() + "'}");
		}
	}

	@Override
	public void recommend() {
		try {
			sa.setIcon(saveFile("picture", sa.getIcon()));
			sa.setRecommType(PRODUCT_TYPE_PLAN);
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
	protected String getExclude() {
		return "applyClubs,order45s,balanceFroms,balanceTos,ticklings,judges,courseGrades,coach,coachMembers,friends,clubs,coachs,members,visitors,setting,times,products,certificates,integrals,courses,alwaysAddrs,applys,coursess,records,courseCoachs,courseMembers,prods,appraises,appraises1,allFriends,orderDetails,orderDetails1,messages,messages1,orders,addrs,signIns,signIns1,shops,accounts,prodOrders,aos,actives,partakes,teams,grades,tickets";
	}
}
