package com.freegym.web.active.action;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.freegym.web.OrderBasicAction;
import com.freegym.web.active.Active;
import com.freegym.web.active.TrainRecord;
import com.freegym.web.config.Setting;
import com.freegym.web.order.ActiveOrder;
import com.sanmen.web.core.chart.IChart;
import com.sanmen.web.core.chart.impl.DayLineChart;
import com.sanmen.web.core.utils.Timestamper;

@Namespace("")
@InterceptorRefs({ @InterceptorRef("foreStack") })
@Results({ @Result(name = "success", location = "/active/active_join_list.jsp"), @Result(name = "look", location = "/active/active_view.jsp"),
		@Result(name = "join", location = "/active/active_partake.jsp"), @Result(name = "select", location = "/order/order_select.jsp"),
		@Result(name = "error", location = "/payment/payment.jsp"), @Result(name = "payment", type = "chain", location = "activeorder") })
// @Result(name = "payment", type = "redirect", params = {"encode","true"},
// location =
// "order!submitProd.asp?prodType=2&id=${id}&startDate=${startDate}&teamId=${teamId}&createMode=${createMode}&teamName=${teamName}&member=${members}&judge=${partake.judge}")
// })
public class JoinActiveManageAction extends OrderBasicAction {

	private static final long serialVersionUID = 8942409430710869379L;

	/**
	 * 当前活动
	 */
	private ActiveOrder partake;

	/**
	 * 活动ID，团队ID号
	 */
	private Long activeId, teamId;

	/**
	 * 判断是新建团队，还是加入团队
	 */
	private Character createMode;

	/**
	 * 团队名称
	 */
	private String teamName;

	/**
	 * 新建团队时所有选择的会员
	 */
	private String members;

	private String startDate;

	private String prodType;

	public ActiveOrder getPartake() {
		return partake;
	}

	public void setPartake(ActiveOrder partake) {
		this.partake = partake;
	}

	public Long getActiveId() {
		return activeId;
	}

	public void setActiveId(Long activeId) {
		this.activeId = activeId;
	}

	public Long getTeamId() {
		return teamId;
	}

	public void setTeamId(Long teamId) {
		this.teamId = teamId;
	}

	public Character getCreateMode() {
		return createMode;
	}

	public void setCreateMode(Character createMode) {
		this.createMode = createMode;
	}

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public String getMembers() {
		return members;
	}

	public void setMembers(String members) {
		this.members = members;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getProdType() {
		return prodType;
	}

	public void setProdType(String prodType) {
		this.prodType = prodType;
	}

	public String execute() {
		session.setAttribute("spath", 5);
		onQuery();
		return SUCCESS;
	}

	private void onQuery() {
		final Long memberId = toMember().getId();
		final DetachedCriteria dc = DetachedCriteria.forClass(ActiveOrder.class);
		pageInfo.setPageSize(15);
		dc.add(Restrictions.eq("member.id", memberId));
		dc.add(Restrictions.and(Restrictions.ne("status", '0'), Restrictions.isNotNull("status")));
		pageInfo.setOrder("orderStartTime");
		pageInfo.setOrderFlag("desc");
		pageInfo = service.findPageByCriteria(dc, pageInfo);
	}

	public String querySelect() {
		onQuery();
		// type = '2';
		return "select";
	}

	/**
	 * 加载当前活动的所有团队，主要针对于团队挑战
	 */
	public void loadOrder() {
		final List<Map<String, Object>> list = service
				.queryForList(
						"select id, name from tb_active_team where id in (select a.team from tb_active_order a left join tb_active b on a.active = b.id left join (SELECT active, team, COUNT(*) as cnt FROM tb_active_order WHERE team IS NOT NULL GROUP BY active, team) c on b.id = c.active and a.team = c.team where b.id = ? and c.cnt < b.team_num)",
						activeId);
		final String jsons = getJsonString(list);
		final JSONObject obj = new JSONObject();
		obj.accumulate("success", true).accumulate("items", jsons);
		response(obj);
	}

	public void loadOrderInfo() {
		final List<?> list = service.findObjectBySql("from ActiveOrder ao where ao.active.id = ? and ao.team.id = ?", activeId, teamId);
		final JSONObject obj = new JSONObject();
		if (list.size() > 0) {
			final ActiveOrder ao = (ActiveOrder) list.get(0);
			obj.accumulate("success", true).accumulate("orderStartTime", ymd.format(ao.getOrderStartTime())).accumulate("judge", ao.getJudge());
		} else {
			obj.accumulate("success", false).accumulate("message", "该团队无此挑战的订单！");
		}
		response(obj);
	}

	public String join() {
		partake = new ActiveOrder();
		if (toMember() != null) {
			partake.setMember(toMember());
			final Setting set = service.loadSetting(toMember().getId());
			partake.setWeight(set.getWeight());
		}
		final Active active = (Active) service.load(Active.class, id);
		partake.setActive(active);
		partake.setOrderStartTime(new Date());
		partake.setJudge(active.getJudgeMode() == 'B' ? active.getCreator().getNick() : "");
		if (active.getMode() == 'B') request.setAttribute("teams", service.findTeamByMember(toMember().getId()));
		request.setAttribute("active", active);
		return "join";
	}

	public String look() {
		partake = (ActiveOrder) service.load(ActiveOrder.class, id);
		final List<?> rs = service.findObjectBySql(
				"from TrainRecord tr where tr.activeOrder.id = ? and tr.partake.id = ? and tr.confrim = '1' order by tr.doneDate", id, toMember().getId());
		final Map<String, Double> value = new HashMap<String, Double>();
		for (final Iterator<?> it = rs.iterator(); it.hasNext();) {
			final TrainRecord tr = (TrainRecord) it.next();
			value.put(ymd.format(tr.getDoneDate()), getValue(tr));
		}
		final String name = "temp/" + Timestamper.next() + ".png";
		final File f = new File(webPath + "/" + name);
		final Map<String, Map<String, Double>> map = new HashMap<String, Map<String, Double>>();
		map.put("运动时间", value);
		final IChart<Map<String, Map<String, Double>>> chart = new DayLineChart(f, "运动记录曲线图", "时间", "值", map, 686, 380);
		chart.create();
		request.setAttribute("fileName", name);
		request.setAttribute("rs", rs);
		return "look";
	}

	private Double getValue(TrainRecord tr) {
		final ActiveOrder ao = tr.getActiveOrder();
		Active active = ao.getActive();
		final Character target = active.getTarget();
		if (target == 'A') {
			return tr.getWeight();
		} else if (target == 'B') {
			if (active.getCategory() == 'G') {
				return new Double(tr.getActionQuan() == null ? 0d : tr.getActionQuan());
			} else if (active.getCategory() == 'H') {
				tr.setStrength(service.getActualWeight(toMember().getId(), tr.getDoneDate()));
				return tr.getStrength();
			}
		} else if (target == 'C') {
			if (active.getCategory() == 'D' || active.getCategory() == 'F') {
				tr.setTime(service.getActualTime(toMember().getId(), id, tr.getDoneDate()));
				return new Double(tr.getTime());
			} else if (active.getCategory() == 'E') { return tr.getTimes(); }
		}
		return null;
	}

	public String save() {
		try {
			partake.setMember(toMember());
			service.validateJoined(partake, partake.getActive().getId(), createMode, teamName, members);
			teamId = id;
			activeId = partake.getActive().getId();
			prodType = "2";
			startDate = new SimpleDateFormat("yyyy-MM-dd").format(partake.getOrderStartTime());
			return "payment";
		} catch (Exception e) {
			log.error("error", e);
			setMessage(e.getMessage());
			return "error";
		}
	}

	public String onCancel() {
		try {
			service.delete(ActiveOrder.class, id);
		} catch (Exception e) {
			log.error("error", e);
		}
		return execute();
	}

}
