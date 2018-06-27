package com.cardcolv45.web.action;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.freegym.web.BaseBasicAction;
import com.freegym.web.active.Active;
import com.freegym.web.basic.Member;
import com.freegym.web.order.ActiveOrder;
import com.opensymphony.xwork2.ActionContext;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Namespace("")
@InterceptorRefs({ @InterceptorRef("customStack") })
@Results({ @Result(name = "success", location = "/WX/login.jsp"),
		@Result(name = "login", location = "/WX/login.jsp"),
		@Result(name = "activeall", location = "/wxv45/challenge_list.jsp"),// 挑战列表页
		@Result(name = "execut", location = "/wxv45/challenge_detail.jsp"),// 挑战详情
		@Result(name = "execut1", location = "/WX/challengeXq1.jsp"),
		@Result(name = "mychallengeXq", location = "/WX/mychallengeXq.jsp"),
		@Result(name = "orderinfo", location = "/eg/orderinfo.jsp"),
		@Result(name = "se", location = "/WX/choiceCp.jsp"), @Result(name = "query", location = "/WX/my_challenge.jsp"),
		@Result(name = "joinchallenge", location = "/wxv45/join_challenge.jsp") })  // 参加挑战页面
public class ActiveListWxV45ManageAction extends BaseBasicAction {
	private static final long serialVersionUID = -3769475961884283745L;
	@SuppressWarnings("unchecked")
	Map<String, Object> request1 = (Map<String, Object>) ActionContext.getContext().get("request");
	/**
	 * 活动查询类型，0默认查可参加的，1为已参加的，2为我发起的
	 */
	private Integer type = 0;
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	private Member member;
	private String mode;
	private String modeName;
	/**
	 * 挑战目标：A体重减少，B体重增加，D运动次数
	 */
	private Character target;
	private String targetName;
	private String circle;
	private String circleName;
	private Active active;
	private ActiveOrder activeOrder;
	private String keyword;
	private Double amerceMoney;
	private ActiveOrder partake;// 参与者
	private Date date;
	private Integer i;
	private Character external = '0';
	private Long id;
	private Long mid;

	public ActiveOrder getAcactiveOrder() {
		return activeOrder;
	}

	public void setAcactiveOrder(ActiveOrder acactiveOrder) {
		this.activeOrder = acactiveOrder;
	}

	public Long getMid() {
		return mid;
	}

	public void setMid(Long mid) {
		this.mid = mid;
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public Character getExternal() {
		return external;
	}

	public void setExternal(Character external) {
		this.external = external;
	}

	public Integer getI() {
		return i;
	}

	public void setI(Integer i) {
		this.i = i;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Double getAmerceMoney() {
		return amerceMoney;
	}

	public void setAmerceMoney(Double amerceMoney) {
		this.amerceMoney = amerceMoney;
	}

	public ActiveOrder getPartake() {
		return partake;
	}

	public void setPartake(ActiveOrder partake) {
		this.partake = partake;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public Active getActive() {
		return active;
	}

	public void setActive(Active active) {
		this.active = active;
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

	public Character getTarget() {
		return target;
	}

	public void setTarget(Character target) {
		this.target = target;
	}

	public ActiveOrder getActiveOrder() {
		return activeOrder;
	}

	public void setActiveOrder(ActiveOrder activeOrder) {
		this.activeOrder = activeOrder;
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

	@Override
	public String execute() {
		@SuppressWarnings("unused")
		Member member = (Member) session.getAttribute("member");
		// 测试 所以注释了
		// TODO 以后还原
		/*if (member == null) {
			return SUCCESS;
		} else {*/
			return activeAll();
		/*}*/
	}

	@SuppressWarnings("deprecation")
	public String activeAll() {
		// TODO 后期可能要改成查询指定教练的所有挑战
		Member mu = (Member) session.getAttribute("member");
		final JSONArray jarr = new JSONArray();
			final DetachedCriteria dc = DetachedCriteria.forClass(Active.class);
			dc.createAlias("creator", "c", Criteria.LEFT_JOIN);
			if (keyword != null && !"".equals(keyword)) {
				dc.add(this.or(Restrictions.like("name", keyword),
						Restrictions.like("c.nick", keyword),
						Restrictions.like("c.email", keyword),
						Restrictions.like("c.mobilephone", keyword)));
			}
			if (type == 0) {
				dc.add(Restrictions.eq("status", 'B'));
				if (null != target && !"".equals(target)) {
					if (target == 'A') {
						dc.add(Restrictions.or(
								Restrictions.eq("category", 'A'),
								Restrictions.eq("category", 'B')));
					} else if (target == 'D') {
						dc.add(Restrictions.eq("category", 'D'));
					}
				}
			} else {
				dc.add(Restrictions.eq("c.id",mu.getId()));
				if (null != target && !"".equals(target))
					dc.add(Restrictions.eq("target", target));
			}
			pageInfo.setOrder("createTime");
			pageInfo.setOrderFlag("desc");
			pageInfo = service.findPageByCriteria(dc, pageInfo);
			if (pageInfo.getItems() != null && pageInfo.getItems().size() > 0) {
				for (final Iterator<?> it = pageInfo.getItems().iterator(); it.hasNext();) {
					final Object[] objs = (Object[]) it.next();
					final Active a = (Active) objs[1];
					final JSONObject obj = new JSONObject();
					obj.accumulate("id", a.getId()).accumulate("name", a.getName())
							.accumulate("applyCount", applyCount(a.getId())).accumulate("days",a.getDays())
							.accumulate("category", a.getCategory()).accumulate("value",a.getValue()).accumulate("image", a.getImage());
					jarr.add(obj);
				}
			}
			final List<Map<String, Object>> recomms = service
					.findRecommendBySectorCode("E2");
			final JSONObject ret = new JSONObject();
			ret.accumulate("items", jarr).accumulate("code",recomms);
			request.setAttribute("map", ret);
			return "activeall";
	}

	/*
	 * 查询挑战
	 */
	public String targetSearch() {
		StringBuffer sb = new StringBuffer();
		sb.append("select * from tb_active a where a.name like '%" + keyword + "%' and  a.status = 'B'");
		sb.append("and a.mode='A'");
		sb.append("order by a.create_time desc ");
		final List<Object> parm = new ArrayList<Object>();
		pageInfo = service.findPageBySql(sb.toString(), pageInfo, parm.toArray());
		return "activeall";
	}

	public String detail() {
		particulars();
		return "execut";
	}

	public String detail1() {
		activeOrder = (ActiveOrder) service.load(ActiveOrder.class, id);
		System.out.println(id);
		Long count = null;
		count = service.queryForLong(
				"SELECT count(*) from tb_active a join tb_active_order o on a.id=o.active where o.id=?", id);
		request.setAttribute("count", count);
		request.setAttribute("activeOrder", activeOrder);
		Long num = null;
		num = service.queryForLong("SELECT count(*) from tb_sign_in where memberSign=?",
				activeOrder.getMember().getId());
		request.setAttribute("num", num);
		if (activeOrder.getActive() != null) {
			if (activeOrder.getActive().getCreator() != null) {
				member = activeOrder.getActive().getCreator();
			} else {
				member = toMember();
			}
		}
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date orderStartTime;
		try {
			String startTime = activeOrder.getOrderStartTime().toString();
			orderStartTime = format.parse(startTime);
			Calendar ca = Calendar.getInstance();
			ca.setTime(orderStartTime);
			ca.add(Calendar.DATE, activeOrder.getActive().getDays());
			String orderEndTime = format.format(ca.getTime());
			String StartTime = format.format(orderStartTime);
			request.setAttribute("orderStartTime", StartTime);
			request.setAttribute("orderEndTime", orderEndTime);

		} catch (ParseException e) {
			e.printStackTrace();
		}
		return "execut1";
	}

	public String detail2() {
		particulars();
		return "mychallengeXq";
	}

	// 挑战详情
	public void particulars() {
		active = (Active) service.load(Active.class, id);
		request.setAttribute("count", applyCount(id));
		if (active != null) {
			if (active.getCreator() != null) {
				member = active.getCreator();
			} else {
				member = toMember();
			}
		}
	}

	/**
	 * 参加挑战
	 */
	public String joinChallenge() {
		particulars();
		return "joinchallenge";
	}

	/*
	 * 选择裁判
	 */
	public String referee() {
		active = (Active) service.load(Active.class, id);
		String judgeid = active.getJudgeID();
		System.out.println(judgeid);
		String[] re = judgeid.split(",");
		System.out.println(re);
		final List<Member> lm = new ArrayList<Member>();
		for (String string : re) {
			Long lid = Long.parseLong(string);
			Member member = (Member) service.load(Member.class, lid);
			lm.add(member);
		}
		if (active.getId() != null) {
			request.setAttribute("mb", lm);
		}
		return "se";
	}

	public String over() {
		member = (Member) service.load(Member.class, mid);
		request.setAttribute("mb", member);
		active = (Active) service.load(Active.class, id);
		return "joinchallenge";
	}

	/*
	 * 我发起的挑战
	 */
	public String query() {
		session.setAttribute("spath", 7);
		final DetachedCriteria dc = DetachedCriteria.forClass(Active.class);
		dc.createAlias("creator", "m");
		if (external != null && external == '1') {
			final Member m = (Member) session.getAttribute("toMember");
			dc.add(Restrictions.eq("m.id", m.getId()));
		} else {
			Member member = (Member) session.getAttribute("member");
			dc.add(Restrictions.eq("m.id", member.getId()));
		}
		pageInfo.setOrder("createTime");
		pageInfo.setOrderFlag("desc");
		pageInfo = service.findPageByCriteria(dc, pageInfo, 1);
		return "query";
	}

	/**
	 * 计算挑战活参与总数
	 */
	public Long applyCount(Long id) {
		Long count = null;
		if (id != null) {
			count = service.queryForLong(
					"select count(*) from tb_active_order ci where ci.active = ? and ci.status != '0' ", id);
		}
		return count;
	}
	
	/**
	 * 支付保证金订单详情
	 * 
	 * @param String cid: 健身卡编号
	 * @param String date: 开卡日期
	 * 
	 * @return name 产品名, cost 产品价格
	 */
	public String orderInfo() {
		// Test Data
		// TODO 以后去掉
		Member memberTest = new Member();
		memberTest.setMobilephone("110");
		session.setAttribute("member", memberTest);
		
		// 获取登录用户的账号 查看是否登录
		Member member = (Member) session.getAttribute("member");
		
		if (member == null) {
			return SUCCESS;
		} else {
			// 如果用户登录了 查询订单的详细信息
			String sql = "SELECT name as orderName, amerce_money as orderCost FROM tb_active WHERE id = ?";
			pageInfo = service.findPageBySql(sql, pageInfo, new Object[] { 324 });
			
			return "orderinfo";
		} 
	}
}