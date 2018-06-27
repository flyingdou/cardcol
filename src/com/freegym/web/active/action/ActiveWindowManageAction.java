package com.freegym.web.active.action;

import java.util.Date;
import java.util.List;

import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.freegym.web.OrderBasicAction;
import com.freegym.web.active.Active;
import com.freegym.web.basic.Member;
import com.freegym.web.config.Setting;
import com.freegym.web.order.ActiveOrder;

@Namespace("")
@InterceptorRefs({ @InterceptorRef("customStack") })
@Results({ @Result(name = "success", location = "/homeWindow/active.jsp"), @Result(name = "join", location = "/active/active_partake.jsp"),
		@Result(name = "list", location = "/active/active_start_list.jsp") })
public class ActiveWindowManageAction extends OrderBasicAction {

	private static final long serialVersionUID = -3769475961884283745L;

	/**
	 * 数据类型，1列表，2报名，3查看
	 */
	private Character type = '0';

	/**
	 * 活动查询用
	 */
	private Active query;

	/**
	 * 参与者
	 */
	private ActiveOrder partake;

	/**
	 * 被访问会员
	 */
	private Long memberId;

	public Character getType() {
		return type;
	}

	public void setType(Character type) {
		this.type = type;
	}

	public Active getQuery() {
		return query;
	}

	public void setQuery(Active query) {
		this.query = query;
	}

	public ActiveOrder getPartake() {
		return partake;
	}

	public void setPartake(ActiveOrder partake) {
		this.partake = partake;
	}

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	@SuppressWarnings("unchecked")
	public String execute() {
		session.setAttribute("wpath", 4);
		final Active active = (Active) service.load(Active.class, id);
		if (active != null) {
			Member member = null;
			if (active.getCreator() != null) {
				member = active.getCreator();
				session.setAttribute("toMember", member);
			} else {
				member = toMember();
			}
			partake = new ActiveOrder();
			partake.setActive(active);
			partake.setOrderDate(new Date());
			partake.setJudge(active.getJudgeMode() == 'B' ? active.getCreator().getNick() : "");
			if (active.getMode() == 'B') {
				request.setAttribute("teams", service.findTeamByMember(member.getId()));
			}
			try {
				Object o=service.findActivePartakeMembers(active);
				request.setAttribute("partakeMembers", o);
				Object o1=service.queryForList("select * from tb_active a join (select oc.active from( select count(*) as c,id, active from tb_active_order o GROUP BY o.active) oc ORDER BY oc.c desc limit 0,1) ocd on ocd.active=a.id");
				request.setAttribute("HotActive", o1 );//查找最火的挑战
				char types=active.getTarget();
				List<Active> la=(List<Active>) service.findObjectBySql("from Active a where a.target=?", types);//查找同类挑战
				request.setAttribute("SimilarActive", la);
				if (toMember() != null) {
					final Setting set = service.loadSetting(toMember().getId());
					partake.setWeight(set.getWeight());
					request.setAttribute("weight", set.getWeight());
				}
				request.setAttribute("active", active);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			return null;
		}
		return SUCCESS;
	}

	public String query() {
		final Member member = (Member) session.getAttribute("toMember");
		final DetachedCriteria dc = Active.getCriteriaQuery(query);
		if (member != null) {
			dc.add(Restrictions.eq("c.id", member.getId()));
		} else if (toMember() != null) {
			dc.add(Restrictions.eq("c.id", toMember().getId()));
		}
		pageInfo = service.findPageByCriteria(dc, pageInfo, 3);
		return "active_start_list";
	}
	
}
