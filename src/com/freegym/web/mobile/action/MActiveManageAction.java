package com.freegym.web.mobile.action;

import java.io.File;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;
import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.freegym.web.active.Active;
import com.freegym.web.active.ActiveJudge;
import com.freegym.web.active.Team;
import com.freegym.web.active.TeamMember;
import com.freegym.web.active.TrainRecord;
import com.freegym.web.basic.Member;
import com.freegym.web.config.Action;
import com.freegym.web.config.Setting;
import com.freegym.web.course.Message;
import com.freegym.web.mobile.BasicJsonAction;
import com.freegym.web.order.ActiveOrder;
import com.sanmen.web.core.bean.BaseMember;
import com.sanmen.web.core.common.LogicException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.OpenimCustmsgPushRequest;
import com.taobao.api.request.OpenimCustmsgPushRequest.CustMsg;
import com.taobao.api.response.OpenimCustmsgPushResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Namespace("")
@InterceptorRefs({ @InterceptorRef("mobileStack") })
public class MActiveManageAction extends BasicJsonAction {

	private static final long serialVersionUID = -4900715126840183203L;

	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	/**
	 * 关键字, 裁判
	 */
	private String keyword, judge;
	private String recomm_id;

	/**
	 * 活动查询类型，0默认查可参加的，1为已参加的，2为我发起的
	 */
	private Integer type = 0;

	private Long teamId;

	private String startDate;

	/**
	 * 挑战模式： A 个人挑战 B 团体挑战
	 */
	private Character mode;

	/**
	 * 挑战目标：A 体重 B 运动量 C 次数/时间/频率
	 */
	private Character target;

	/**
	 * 挑战周期：A 小于10天 B 11-30天 C 31-90天 D 大于90天
	 */
	private String circle;

	/**
	 * 发起活动时增加缩略图上传
	 * 
	 */
	private File image1;

	private String image1FileName;

	public File getImage1() {
		return image1;
	}

	public String getImage1FileName() {
		return image1FileName;
	}

	public void setImage1(File image1) {
		this.image1 = image1;
	}

	public void setImage1FileName(String image1FileName) {
		this.image1FileName = image1FileName;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		if (keyword != null && !keyword.equals(""))
			keyword = urlDecode(keyword);
		this.keyword = keyword == null ? "" : "%" + keyword + "%";
	}

	public String getJudge() {
		return judge;
	}

	public void setJudge(String judge) {
		if (judge != null && !"".equals(judge))
			judge = urlDecode(judge);
		this.judge = judge;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Long getTeamId() {
		return teamId;
	}

	public void setTeamId(Long teamId) {
		this.teamId = teamId;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public Character getMode() {
		return mode;
	}

	public Character getTarget() {
		return target;
	}

	public void setMode(Character mode) {
		this.mode = mode;
	}

	public void setTarget(Character target) {
		this.target = target;
	}

	public String getCircle() {
		return circle;
	}

	public void setCircle(String circle) {
		this.circle = circle;
	}

	public String getRecomm_id() {
		return recomm_id;
	}

	public void setRecomm_id(String recomm_id) {
		this.recomm_id = recomm_id;
	}

	@SuppressWarnings("deprecation")
	@Override
	protected void list() {
		// final MobileUser mobileUser = getMobileUser();
		final BaseMember mu = getLoginMember();
		final JSONArray jarr = new JSONArray();
		if (type == 0 || type == 2) {
			final DetachedCriteria dc = DetachedCriteria.forClass(Active.class);
			dc.createAlias("creator", "c", Criteria.LEFT_JOIN);
			if (keyword != null && !"".equals(keyword)) {
				dc.add(this.or(Restrictions.like("name", keyword), Restrictions.like("c.nick", keyword),
						Restrictions.like("c.email", keyword), Restrictions.like("c.mobilephone", keyword)));
			}
			if (type == 0) {
				/*
				 * final Long memberId = toMember() == null ? 0l :
				 * toMember().getId(); final Criterion where = Restrictions
				 * .sqlRestriction(
				 * "(this_.join_mode = 'B' and this_.creator in (select friend from tb_member_friend where member = "
				 * + memberId + "))");
				 * dc.add(Restrictions.or(Restrictions.eq("joinMode", 'A'),
				 * where));
				 */ dc.add(Restrictions.eq("status", 'B'));
				if (null != target && !"".equals(target)) {
					if (target == 'A') {
						dc.add(Restrictions.or(Restrictions.eq("category", 'A'), Restrictions.eq("category", 'B')));
					} else if (target == 'C') {
						dc.add(Restrictions.eq("category", 'E'));
					} else if (target == 'D') {
						dc.add(Restrictions.eq("category", 'D'));
					} else {
						dc.add(Restrictions.in("category", new Object[] { 'C', 'F', 'G', 'H' }));
					}
				}
			} else {
				dc.add(Restrictions.eq("c.id", mu.getId()));
				if (null != target && !"".equals(target))
					dc.add(Restrictions.eq("target", target));
			}
			if (null != mode && !"".equals(mode)) {
				dc.add(Restrictions.eq("mode", mode));
			}
			if (circle != null && !"".equals(circle)) {
				if ("A".equals(circle)) {
					dc.add(Restrictions.le("days", 10));
				} else if ("B".equals(circle)) {
					dc.add(Restrictions.ge("days", 11));
					dc.add(Restrictions.le("days", 30));
				} else if ("C".equals(circle)) {
					dc.add(Restrictions.ge("days", 31));
					dc.add(Restrictions.le("days", 90));
				} else if ("D".equals(circle)) {
					dc.add(Restrictions.ge("days", 91));
				}
			}
			pageInfo.setOrder("createTime");
			pageInfo.setOrderFlag("desc");
			pageInfo = service.findPageByCriteria(dc, pageInfo);
			for (final Iterator<?> it = pageInfo.getItems().iterator(); it.hasNext();) {
				final Object[] objs = (Object[]) it.next();
				final Active m = (Active) objs[1];
				final JSONObject obj = new JSONObject();
				// && m.getJoinMode().compareTo('B')!=0 &&
				// m.getStatus().compareTo('B')!=0
				if (m.getMode().compareTo('B') != 0) {
					obj.accumulate("id", m.getId()).accumulate("member", getMemberJson(m.getCreator()))
							.accumulate("name", getString(m.getName())).accumulate("mode", getString(m.getMode()))
							.accumulate("days", getInteger(m.getDays()))
							.accumulate("teamNum", getInteger(m.getTeamNum()))
							.accumulate("target", getString(m.getTarget())).accumulate("award", getString(m.getAward()))
							.accumulate("institution", getJsonForMember(m.getInstitution()))
							.accumulate("amerceMoney", getDouble(m.getAmerceMoney()))
							.accumulate("judgeMode", getString(m.getJudgeMode()))
							.accumulate("joinMode", getString(m.getJoinMode()))
							.accumulate("category", getString(m.getCategory()))
							.accumulate("status", getString(m.getStatus()))
							.accumulate("action", getString(m.getAction())).accumulate("value", getDouble(m.getValue()))
							.accumulate("applyCount", applyCount(m.getId()))
							.accumulate("image", getString(m.getImage()))
							// 20160629
							.accumulate("memo", getString(m.getMemo())).accumulate("judgeID", getString(m.getJudgeID()))
							.accumulate("content", getString(m.getContent()))
							.accumulate("customTareget", getString(m.getCustomTareget()))
							.accumulate("unit", getString(m.getUnit()))
							.accumulate("evaluationMethod", getString(m.getEvaluationMethod()));
					if (m.getJudges().size() > 0) {
						final JSONArray jarr1 = new JSONArray();
						for (final ActiveJudge aj : m.getJudges()) {
							final JSONObject obj1 = new JSONObject();
							obj1.accumulate("id", aj.getId()).accumulate("judgeId", aj.getJudge().getId())
									.accumulate("judgeName", aj.getJudge().getName());
							jarr1.add(obj1);
						}
						obj.accumulate("judges", jarr1);
					}
					jarr.add(obj);
				}
			}
		} else {
			final DetachedCriteria dc = DetachedCriteria.forClass(ActiveOrder.class);
			dc.add(Restrictions.eq("member.id", mu.getId()));
			dc.add(Restrictions.and(Restrictions.ne("status", '0'), Restrictions.isNotNull("status")));
			pageInfo.setOrder("orderDate");
			pageInfo.setOrderFlag("desc");
			pageInfo = service.findPageByCriteria(dc, pageInfo);
			for (final Iterator<?> it = pageInfo.getItems().iterator(); it.hasNext();) {
				final ActiveOrder ao = (ActiveOrder) it.next();
				final JSONObject obj = new JSONObject();
				obj.accumulate("id", ao.getActive().getId())
						.accumulate("member", getMemberJson(ao.getActive().getCreator()))
						.accumulate("name", getString(ao.getActive().getName()))
						.accumulate("mode", getString(ao.getActive().getMode()))
						.accumulate("days", getInteger(ao.getActive().getDays()))
						.accumulate("teamNum", getInteger(ao.getActive().getTeamNum()))
						.accumulate("target", getString(ao.getActive().getTarget()))
						.accumulate("award", getString(ao.getActive().getAward()))
						.accumulate("institution", getMemberJson(ao.getActive().getInstitution()))
						.accumulate("amerceMoney", getDouble(ao.getActive().getAmerceMoney()))
						.accumulate("judgeMode", getString(ao.getActive().getJudgeMode()))
						.accumulate("joinMode", getString(ao.getActive().getJoinMode()))
						.accumulate("category", getString(ao.getActive().getCategory()))
						.accumulate("status", getString(ao.getStatus()))
						.accumulate("action", getString(ao.getActive().getAction()))
						.accumulate("value", getDouble(ao.getActive().getValue()))
						.accumulate("applyCount", applyCount(ao.getActive().getId()))
						.accumulate("image", getString(ao.getActive().getImage()))
						.accumulate("payNo", getString(ao.getPayNo()));
				jarr.add(obj);
			}
		}
		final JSONObject ret = new JSONObject();
		ret.accumulate("success", true).accumulate("pageInfo", getJsonForPageInfo()).accumulate("items", jarr);
		response(ret);
	}

	/**
	 * 查找慈善机构列表
	 */
	public void findInstitution() {
		try {
			final List<?> list_login = service.findObjectBySql("from Member m where m.id = ?", getMobileUser().getId());
			final List<?> list = service.findObjectBySql("from Member m where m.role = ?", "I");
			final JSONArray jarr = new JSONArray();
			for (final Iterator<?> it = list.iterator(); it.hasNext();) {
				final Member in = (Member) it.next();
				final JSONObject obj = getMemberJson(in);
				jarr.add(obj);
			}
			for (final Iterator<?> it = list_login.iterator(); it.hasNext();) {
				final Member in = (Member) it.next();
				final JSONObject obj = getMemberJson(in);
				jarr.add(obj);
			}
			final JSONObject obj = new JSONObject();
			obj.accumulate("success", true).accumulate("items", jarr);
			response(obj);
		} catch (Exception e) {
			log.error("error", e);
			response(e);
		}
	}

	/**
	 * 查找团队
	 */
	public void findTeam() {
		try {
			final List<Map<String, Object>> list = service.findTeamByActive(id);
			final JSONObject ret = new JSONObject();
			final JSONArray jarr = new JSONArray();
			for (Map<String, Object> map : list) {
				final JSONObject obj = new JSONObject();
				final Object st = map.get("orderStartTime");
				obj.accumulate("id", map.get("team")).accumulate("name", map.get("team_name"))
						.accumulate("orderStartTime", st == null ? "" : ymd.format(st));
				obj.accumulate("partakeNum", map.get("partake_num")).accumulate("teamNum", map.get("team_num"));
				jarr.add(obj);
			}
			ret.accumulate("success", true).accumulate("items", jarr);
			response(ret);
		} catch (Exception e) {
			log.error("error", e);
			response(e);
		}
	}

	/**
	 * 保存团队
	 */
	public void saveTeam() {
		try {
			final JSONObject ret = new JSONObject();
			final JSONArray arrs = JSONArray.fromObject(URLDecoder.decode(jsons, "UTF-8"));
			final JSONObject obj = arrs.getJSONObject(0);
			Team team = new Team();
			team.setName(obj.getString("name"));
			final List<?> list = service.findObjectBySql("from Team t where t.name = ?", team.getName());
			if (list.size() > 0) {
				final Team t = (Team) list.get(0);
				ret.accumulate("success", true).accumulate("key", t.getId());
				response(ret);
				return;
			}
			final JSONArray arr = obj.getJSONArray("members");
			for (final Iterator<?> it = arr.listIterator(); it.hasNext();) {
				final JSONObject obj1 = (JSONObject) it.next();
				final String name = obj1.getString("name");
				final Member m = service.findMemberByMail(name);
				if (m == null)
					throw new LogicException("会员" + name + "不存在，请重新输入！");
				final TeamMember tm = new TeamMember();
				tm.setMember(m);
				tm.setStatus('0');
				team.addMember(tm);
			}
			team = (Team) service.saveOrUpdate(team);
			ret.accumulate("success", true).accumulate("key", team.getId());
			response(ret);
		} catch (Exception e) {
			log.error("error", e);
			response(e);
		}
	}

	@Override
	protected Long executeSave(JSONArray objs) {
		final JSONObject obj = objs.getJSONObject(0);
		List<ActiveJudge> judges = new ArrayList<ActiveJudge>();
		Active a = new Active();
		Member m = new Member();
		a.setStatus('B');
		a.setCreator(new Member(getMobileUser().getId()));
		a.setName(obj.getString("name"));
		a.setDays(obj.getInt("days"));
		// 20160629
		if ("G".equals(obj.getString("category"))) {

			a.setContent(obj.getString("content"));
			a.setCustomTareget(obj.getString("customTareget"));
			a.setUnit(obj.getString("unit"));
			a.setEvaluationMethod(obj.getString("evaluationMethod"));
		}
		a.setMemo(obj.getString("memo"));

		// 20160629
		a.setMode("A".charAt(0));
		a.setJoinMode("A".charAt(0));

		if ("A".equals(obj.getString("judgeMode"))) {
			a.setJudgeID(obj.getString("judgeID"));
			String[] ll = obj.getString("judgeID").split(",");
			for (int i = 0; i < ll.length; i++) {
				ActiveJudge aj = new ActiveJudge();
				Long l = Long.parseLong(ll[i]);
				m = (Member) service.load(Member.class, l);
				aj.setJudge(m);
				aj = (ActiveJudge) service.saveOrUpdate(aj);
				judges.add(aj);
			}
			if (judges != null) {
				for (final ActiveJudge judge : judges) {
					a.addJudge(judge);
				}
			}
		} else {
			a.setJudgeID(a.getCreator().getId().toString());
		}
		a.setJudgeMode(obj.getString("judgeMode").charAt(0));

		a.setAward(obj.getString("award"));
		a.setAmerceMoney(obj.getDouble("amerceMoney"));
		a.setInstitution(new Member(obj.getLong("institutionId")));
		a.setCategory(obj.getString("category").charAt(0));
		a.setValue(obj.getDouble("value"));

		/*
		 * a.setTarget(obj.getString("target").charAt(0)); if
		 * (obj.containsKey("teamNum")) a.setTeamNum(obj.getInt("teamNum")); if
		 * (obj.containsKey("action")) a.setAction(obj.getString("action"));
		 */

		a.setCreateTime(new Date());
		String fileName1 = image1 != null ? saveFile("picture", image1, image1FileName, null) : null;
		if (fileName1 != null) {
			a.setImage(fileName1);
		}

		a = (Active) service.saveOrUpdate(a);
		return a.getId();
	}

	/**
	 * 报名参加
	 */
	public void partake() {
		try {
			final String weight = request.getParameter("weight");
			final Long mid = getLoginMember().getId();
			final Member m = (Member) service.load(Member.class, mid);
			if (weight != null) {
				final Setting set = service.loadSetting(m.getId());
				set.setWeight(new Double(weight));
				service.saveOrUpdate(set);
			}
			final Active a = (Active) service.load(Active.class, id);
			ActiveOrder ao = new ActiveOrder();
			ao.setNo(service.getKeyNo("", "TB_ACTIVE_ORDER", 14));
			ao.setPayNo(ao.getNo());
			ao.setActive(a);
			ao.setOrderDate(new SimpleDateFormat("yyyy-MM-dd").parse(startDate));
			ao.setJudge(judge.toString());
			ao.setOrderMoney(a.getAmerceMoney());
			if (teamId == null)
				ao.setMember(new Member(mid));
			else
				ao.setTeam(new Team(teamId));
			ao.setValue(0d);
			ao.setStatus('0');
			ao = (ActiveOrder) service.saveOrUpdate(ao);
			final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			final JSONObject obj = new JSONObject(), orderJson = new JSONObject();
			orderJson.accumulate("id", ao.getId()).accumulate("orderNo", ao.getNo())
					.accumulate("cost", ao.getOrderMoney()).accumulate("orderDate", sdf.format(ao.getOrderDate()));
			obj.accumulate("success", true).accumulate("order", orderJson);
			response(obj);
		} catch (Exception e) {
			log.error("error", e);
			response(e);
		}

	}

	/**
	 * 查找排名
	 */
	public void findRanking() {
		try {
			final List<?> list = service.findObjectBySql(
					"from ActiveOrder ao where ao.active.id = ? and ao.status <> '0' order by ao.value desc", id);
			final JSONArray jarr = new JSONArray();
			for (final Iterator<?> it = list.iterator(); it.hasNext();) {
				final ActiveOrder ao = (ActiveOrder) it.next();
				final JSONObject obj = getMemberJson(ao.getMember());
				obj.accumulate("value", getDouble(ao.getValue()))
						.accumulate("target", getString(ao.getActive().getTarget()))
						.accumulate("action", getString(ao.getActive().getAction()))
						.accumulate("category", getString(ao.getActive().getCategory()))
						.accumulate("startDate", ymd.format(ao.getOrderStartTime()));
				jarr.add(obj);
			}
			final JSONObject ret = new JSONObject();
			ret.accumulate("success", true).accumulate("items", jarr);
			response(ret);
		} catch (Exception e) {
			log.error("error", e);
			response(e);
		}
	}

	/**
	 * 保存活动报名,传入参数，JSONS数组[{joinDate: '2013-09-11', weight: 80, judge: ''}]
	 */
	public void savePartake() {
		try {
			final String weight = request.getParameter("weight");
			final String strKey = request.getParameter("key");
			final BaseMember mu = getLoginMember();
			final Long mid = mu.getId();
			final Setting set = service.loadSetting(mid);
			set.setWeight(new Double(weight));
			service.saveOrUpdate(set);
			final Active a = (Active) service.load(Active.class, id);
			if (a == null)
				throw new LogicException("请确认传入的ID值为有效的挑战信息主表的ID值！");
			ActiveOrder ao = new ActiveOrder();
			if (null != strKey && !"".equals(strKey))
				ao.setId(new Long(strKey));
			ao.setActive(a);
			ao.setOrderDate(new Date());
			ao.setOrderStartTime(sdf.parse(startDate));
			ao.setJudge(judge.toString());
			if (teamId == null)
				ao.setMember(new Member(mid));
			else {
				ao.setMember(new Member(mid));
				ao.setTeam(new Team(teamId));
			}
			ao.setValue(0d);
			ao.setStatus('0');
			ao.setOrigin('B');
			ao.setWeight(new Double(weight));
			ao = service.saveActivePartake(ao, teamId, '0', null, null);
			final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			final JSONObject obj = new JSONObject(), orderJson = new JSONObject();
			orderJson.accumulate("id", ao.getId()).accumulate("orderNo", ao.getNo())
					.accumulate("cost", ao.getOrderMoney()).accumulate("orderDate", sdf.format(ao.getOrderStartTime()))
					.accumulate("payNo", ao.getPayNo());
			obj.accumulate("success", true).accumulate("order", orderJson);
			response(obj);
		} catch (Exception e) {
			log.error("error", e);
			response(e);
		}
	}

	/**
	 * 支付
	 */
	// public void payment() {
	// try {
	// final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	// final String payType = request.getParameter("payType");
	// final ActiveOrder ao = (ActiveOrder) service.load(ActiveOrder.class, id);
	// ao.setPayType(payType);
	// final JSONObject obj = new JSONObject(), orderJson = new JSONObject();
	// if (payType.equals("1")) {
	// service.saveOrUpdate(ao);
	// final String subject = ao.getActive().getName() + "保证金[active]";
	// final String body = ymd.format(ao.getOrderStartTime()) + "的" +
	// ao.getActive().getName() + "挑战活动的保证金[active]";
	// final StringBuffer makeParms = new StringBuffer();
	// makeParms.append("partner=\"").append(AlipayConfig.partner).append("\"&");
	// makeParms.append("seller_id=\"").append(AlipayConfig.seller_email).append("\"&");
	// makeParms.append("out_trade_no=\"").append(ao.getPayNo()).append("\"&");
	// makeParms.append("subject=\"").append(subject).append("\"&");
	// makeParms.append("body=\"").append(body).append("\"&");
	// makeParms.append("total_fee=\"").append(ao.getOrderMoney()).append("\"&");
	// makeParms.append("notify_url=\"").append(URLEncoder.encode(AlipayConfig.notify_url)).append("\"&");
	// makeParms.append("return_url=\"").append(URLEncoder.encode(AlipayConfig.return_url)).append("\"&");
	// makeParms.append("service=\"").append("mobile.securitypay.pay").append("\"&");
	// makeParms.append("payment_type=\"").append("1").append("\"&");
	// makeParms.append("_input_charset=\"").append("utf-8").append("\"&");
	// makeParms.append("it_b_pay=\"30m\"");
	// String sign = URLEncoder.encode(Rsa.sign(makeParms.toString(),
	// AlipayConfig.private_key));
	// makeParms.append("&sign=\"").append(sign).append("\"&");
	// makeParms.append("sign_type=\"").append("RSA").append("\"");
	// obj.accumulate("success", true).accumulate("sign",
	// sign).accumulate("info", makeParms.toString());
	// } else {
	// // String merId = getMessage("chinapay.merid");
	// // ao.setPayNo(ao.getNo().substring(0, 6) +
	// // merId.substring(merId.length() - 5) +
	// // ao.getNo().substring(11));
	// service.saveOrUpdate(ao);
	// final ChinapayUtils pay = new ChinapayUtils(this, ao);
	// final String signs = pay.order();
	// orderJson.accumulate("id", ao.getId()).accumulate("no",
	// ao.getNo()).accumulate("orderDate", sdf.format(ao.getOrderDate()))
	// .accumulate("orderSign", signs);
	// obj.accumulate("success", true).accumulate("order", orderJson);
	// }
	// response(obj);
	// } catch (Exception e) {
	// log.error("error", e);
	// response(e);
	// }
	// }

	/**
	 * 查找所有有氧运动内容
	 */
	public void findAction() {
		try {
			final List<?> list = service
					.findObjectBySql("from Action a where a.part.project.mode = '0' and a.part.member = 1");
			final JSONArray jarr = new JSONArray();
			for (final Iterator<?> it = list.iterator(); it.hasNext();) {
				final Action a = (Action) it.next();
				final JSONObject obj = new JSONObject();
				obj.accumulate("id", a.getId()).accumulate("name", a.getName()).accumulate("image", a.getImage())
						.accumulate("flash", a.getFlash()).accumulate("video", a.getVideo());
				jarr.add(obj);
			}
			final JSONObject ret = new JSONObject();
			ret.accumulate("success", true).accumulate("items", jarr);
			response(ret);
		} catch (Exception e) {
			log.error("error", e);
			response(e);
		}
	}

	/**
	 * 保存活动记录，jsons=[{activeId: 12, weight: 80, waist: 30, hip: 50, fat: 30}]
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void saveRecord() {
		final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			final JSONArray arr = JSONArray.fromObject(URLDecoder.decode(jsons, "UTF-8"));
			final JSONObject obj = arr.getJSONObject(0);
			Long memberid = Long.parseLong(obj.get("memberId").toString());
			String recordtime = obj.getString("doneDate").replaceAll("-", "");
			List<?> blist = service.queryForList(
					"select * from tb_active_order ao where ao.member=? and ? >= DATE_FORMAT(ao.orderStartTime,'%Y%m%d') and DATE_FORMAT(ao.orderEndTime,'%Y%m%d')>? and ao.status=1",
					new Object[] { memberid, recordtime, recordtime });
			if (blist.size() > 0) {
				StringBuffer sb = new StringBuffer();
				for (Iterator<?> it = blist.iterator(); it.hasNext();) {
					Map<String, Object> map = (Map<String, Object>) it.next();
					sb = sb.append(map.get("id").toString());
					sb.append(",");
				}
				String s = sb.toString().substring(0, sb.length() - 1);
				obj.accumulate("activeId", s);
			}
			final List<TrainRecord> rs = new ArrayList<TrainRecord>();
			final String doneDate = obj.getString("doneDate");
			long judgeId;
			if (obj.containsKey("memberId") && obj.getString("memberId") != null) {
				judgeId = obj.getLong("memberId");
			} else {
				judgeId = id != null ? id : getMobileUser().getId();
			}
			final Long key = judgeId;
			final Setting set = service.loadSetting(key);
			if (obj.containsKey("activeId")) {
				final String[] strIds = obj.getString("activeId").split(",");
				for (int i = 0; i < strIds.length; i++) {
					final TrainRecord record = new TrainRecord();
					record.setHeight(Double.valueOf(set.getHeight()));
					record.setActiveOrder(new ActiveOrder(new Long(strIds[i].trim())));
					record.setDoneDate(sdf.parse(doneDate));
					if (obj.containsKey("action"))
						record.setAction(obj.getString("action"));
					if (obj.containsKey("actionQuan") && !StringUtils.isEmpty(obj.getString("actionQuan")))
						record.setActionQuan(obj.getInt("actionQuan"));
					if (obj.containsKey("fat") && !StringUtils.isEmpty(obj.getString("fat")))
						record.setFat(obj.getDouble("fat"));
					if (obj.containsKey("hip") && !StringUtils.isEmpty(obj.getString("hip")))
						record.setHip(obj.getDouble("hip"));
					record.setPartake(new Member(key));
					if (obj.containsKey("waist") && !StringUtils.isEmpty(obj.getString("waist")))
						record.setWaist(obj.getDouble("waist"));
					if (obj.containsKey("times") && !StringUtils.isEmpty(obj.getString("times")))
						record.setTimes(obj.getDouble("times"));
					if (obj.containsKey("weight") && !StringUtils.isEmpty(obj.getString("weight")))
						record.setWeight(obj.getDouble("weight"));
					if (obj.containsKey("memo"))
						record.setMemo(obj.getString("memo"));
					// 增加身体数据字段 身高 运动后心率
					/*
					 * if (obj.containsKey("height"))
					 * record.setHeight(obj.getDouble("height"));
					 */
					if (obj.containsKey("heartRate") && !StringUtils.isEmpty(obj.getString("heartRate")))
						record.setHeartRate(obj.getDouble("heartRate"));
					if (obj.containsKey("SportTime") && !StringUtils.isEmpty(obj.getString("SportTime")))
						record.setTimes(obj.getDouble("SportTime"));
					if (obj.containsKey("SportUnit"))
						record.setUnit(obj.getString("SportUnit"));
					if (obj.containsKey("SportScore") && !StringUtils.isEmpty(obj.getString("SportScore")))
						record.setScore(obj.getInt("SportScore"));
					rs.add(record);
				}
			} else {
				final TrainRecord record = new TrainRecord();
				record.setHeight(Double.valueOf(set.getHeight()));
				record.setDoneDate(sdf.parse(doneDate));
				if (obj.containsKey("action"))
					record.setAction(obj.getString("action"));
				if (obj.containsKey("actionQuan") && !StringUtils.isEmpty(obj.getString("actionQuan")))
					record.setActionQuan(obj.getInt("actionQuan"));
				if (obj.containsKey("fat") && !StringUtils.isEmpty(obj.getString("fat")))
					record.setFat(obj.getDouble("fat"));
				if (obj.containsKey("hip") && !StringUtils.isEmpty(obj.getString("hip")))
					record.setHip(obj.getDouble("hip"));
				record.setPartake(new Member(key));
				if (obj.containsKey("waist") && !StringUtils.isEmpty(obj.getString("waist")))
					record.setWaist(obj.getDouble("waist"));
				if (obj.containsKey("times") && !StringUtils.isEmpty(obj.getString("times")))
					record.setTimes(obj.getDouble("times"));
				if (obj.containsKey("weight") && !StringUtils.isEmpty(obj.getString("weight")))
					record.setWeight(obj.getDouble("weight"));
				if (obj.containsKey("memo"))
					record.setMemo(obj.getString("memo"));
				// 增加身体数据字段 身高 运动后心率
				/*
				 * if (obj.containsKey("height"))
				 * record.setHeight(obj.getDouble("height"));
				 */
				if (obj.containsKey("heartRate") && !StringUtils.isEmpty(obj.getString("heartRate")))
					record.setHeartRate(obj.getDouble("heartRate"));
				if (obj.containsKey("SportTime") && !StringUtils.isEmpty(obj.getString("SportTime")))
					record.setTimes(obj.getDouble("SportTime"));
				if (obj.containsKey("SportUnit") && !StringUtils.isEmpty(obj.getString("SportUnit")))
					record.setUnit(obj.getString("SportUnit"));
				if (obj.containsKey("SportScore") && !StringUtils.isEmpty(obj.getString("SportScore")))
					record.setScore(obj.getInt("SportScore"));
				rs.add(record);
			}
			service.saveOrUpdateRecord(rs, new Member(key), doneDate);

			// obj11.setFromUser(id != null ? id+"" :
			// getMobileUser().getId().toString());
			if (obj.containsKey("activeId")) {
				final String[] strIds = obj.getString("activeId").split(",");
				for (String id : strIds) {
					StringBuffer sb = new StringBuffer("from ActiveOrder A where 1=1 and ");
					ArrayList list_fir = new ArrayList();
					sb.append(" A.id = ? ");
					list_fir.add(Long.parseLong(id));
					List<?> list = service.findObjectBySql(sb.toString(), list_fir.toArray());
					ArrayList userList = new ArrayList();
					for (Object ob : list) {
						ActiveOrder a = (ActiveOrder) ob;
						Long jid = Long.parseLong(a.getJudge().toString());
						Message msg = new Message();
						msg.setContent(getMobileUser().getName() + doneDate + "的训练日志");
						msg.setIsRead("0");
						msg.setMemberFrom(new Member(getMobileUser().getId()));
						msg.setMemberTo(new Member(jid));
						msg.setSendTime(new Date());
						msg.setStatus("1");
						msg.setType("4");
						service.saveOrUpdate(msg);
						if (jid != null) {
							userList.add(jid);
							break;
						}
					}
					if (!userList.isEmpty()) {
						String url = "http://gw.api.taobao.com/router/rest";
						TaobaoClient client = new DefaultTaobaoClient(url, "23330566",
								"0c6b77f937d49f3a14ca98a6316d110e");
						OpenimCustmsgPushRequest req = new OpenimCustmsgPushRequest();
						CustMsg obj11 = new CustMsg();
						obj11.setFromUser("some_one");
						obj11.setToAppkey("0");
						obj11.setToUsers(userList);
						obj11.setSummary("用户" + doneDate + "的运动成绩");
						obj11.setData("challenge");
						obj11.setAps("{\"alert\":\"裁判挑战成绩\"}");
						obj11.setApnsParam("challenge");
						obj11.setInvisible(1L);
						obj11.setFromNick(getMobileUser().getName());
						req.setCustmsg(obj11);
						OpenimCustmsgPushResponse rsp = client.execute(req);
						System.out.println(rsp.getBody());
					}
				}
			}
			final JSONObject obj1 = new JSONObject();
			obj1.accumulate("success", true).accumulate("message", "OK").accumulate("id", rs.get(0).getId());
			response(obj1);
		} catch (Exception e) {
			log.error("error", e);
			response(e);
		}
	}

	/**
	 * 开通或关闭状态
	 */
	public void updateStatus() {
		try {
			final Active a = (Active) service.load(Active.class, id);
			a.setStatus(type == 0 ? 'A' : 'B');
			service.saveOrUpdate(a);
			final JSONObject obj1 = new JSONObject();
			obj1.accumulate("success", true).accumulate("message", "OK");
			response(obj1);
		} catch (Exception e) {
			log.error("error", e);
			response(e);
		}
	}

	/**
	 * 查找当前会员正在进行中的活动
	 */
	public void findActiveByMember() {
		try {
			if (id == null)
				id = getMobileUser().getId();
			final List<Active> list = service.findActiveByMember(id);
			final JSONArray jarr = new JSONArray();
			for (final Iterator<Active> it = list.iterator(); it.hasNext();) {
				final Active a = it.next();
				final JSONObject obj = new JSONObject();
				obj.accumulate("id", a.getId()).accumulate("name", a.getName());
				jarr.add(obj);
			}
			final JSONObject ret = new JSONObject();
			ret.accumulate("success", true).accumulate("items", jarr);
			response(ret);
		} catch (Exception e) {
			log.error("error", e);
			response(e);
		}
	}

	/**
	 * 图表
	 */
	public void chart() {
		try {
			final List<?> list = service.findObjectBySql(
					"from TrainRecord r where r.confrim = '1' and r.partake.id = ? and r.activeOrder.id = ? order by r.doneDate",
					getMobileUser().getId(), id);
			final JSONArray jarr = new JSONArray();
			for (final Iterator<?> it = list.iterator(); it.hasNext();) {
				final TrainRecord tr = (TrainRecord) it.next();
				tr.setStrength(service.getActualWeight(tr.getPartake().getId(), tr.getDoneDate()));
				tr.setTime(service.getActualTime(tr.getPartake().getId(), id, tr.getDoneDate()));
				final JSONObject obj = new JSONObject();
				obj.accumulate("id", tr.getId()).accumulate("doneDate", sdf.format(tr.getDoneDate()))
						.accumulate("weight", getDouble(tr.getWeight())).accumulate("times", getDouble(tr.getTimes()))
						.accumulate("fat", getDouble(tr.getFat())).accumulate("waist", getDouble(tr.getWaist()))
						.accumulate("hip", getDouble(tr.getHip()))
						.accumulate("actionQuan", getInteger(tr.getActionQuan()))
						.accumulate("strength", getDouble(tr.getStrength()))
						.accumulate("time", getInteger(tr.getTime()));

				jarr.add(obj);
			}
			final JSONObject ret = new JSONObject();
			ret.accumulate("success", true).accumulate("items", jarr);
			response(ret);
		} catch (Exception e) {
			log.error("error", e);
			response(e);
		}
	}

	@Override
	protected String getExclude() {
		return "partakes";
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
	 * 展示运动记录
	 */
	public void showRecord() {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

			Date date = new Date();

			String sysDate = String.format("%tY-%tm-%td", date, date, date);

			Date doneDate = sdf.parse(request.getParameter("doneDate"));

			if (doneDate == null) {
				doneDate = sdf.parse(sysDate);
			}
			long memberId = getMobileUser().getId();
			if (request.getParameter("memberId") != null) {
				memberId = Long.parseLong(request.getParameter("memberId").toString());
			}
			final List<?> list = service.findObjectBySql(
					"from TrainRecord r where r.partake.id = ? and r.doneDate = ? ",
					new Object[] { memberId, doneDate });

			final JSONArray jarr = new JSONArray();

			for (final Iterator<?> it = list.iterator(); it.hasNext();) {
				final TrainRecord tr = (TrainRecord) it.next();
				final JSONObject obj = new JSONObject();

				obj.accumulate("id", tr.getId()).accumulate("doneDate", sdf.format(tr.getDoneDate()))
						.accumulate("weight", getDouble(tr.getWeight())).accumulate("times", getDouble(tr.getTimes()))
						.accumulate("fat", getDouble(tr.getFat())).accumulate("waist", getDouble(tr.getWaist()))
						.accumulate("hip", getDouble(tr.getHip()))
						.accumulate("actionQuan", getInteger(tr.getActionQuan()))
						.accumulate("strength", getDouble(tr.getStrength()))
						.accumulate("heartRate", getDouble(tr.getHeartRate()))
						.accumulate("height", getDouble(tr.getHeight()))
						.accumulate("activeOrder", tr.getActiveOrder() == null ? "" : tr.getActiveOrder().toJson())
						.accumulate("BMI", tr.getBmi()).accumulate("WHR", tr.getWaistHip())
						.accumulate("SportTime", getDouble(tr.getTimes())).accumulate("SportUnit", tr.getUnit())
						.accumulate("action", tr.getAction());

				jarr.add(obj);
			}

			Setting setting = new Setting();
			setting = service.loadSetting(memberId);

			final JSONObject ret = new JSONObject();
			ret.accumulate("success", true).accumulate("items", jarr).accumulate("bmiLow", setting.getBmiLow())
					.accumulate("bmiHigh", setting.getBmiHigh());
			response(ret);

		} catch (Exception e) {
			log.error("error", e);
			response(e);
		}
	}

	public void getActive() {
		Long l = Long.parseLong(recomm_id.toString());
		Active a = (Active) service.load(Active.class, l);
		JSONObject obj1 = new JSONObject();
		JSONArray jarr = new JSONArray();
		obj1.accumulate("id", a.getId()).accumulate("member", getMemberJson(a.getCreator()))
				.accumulate("name", getString(a.getName())).accumulate("mode", getString(a.getMode()))
				.accumulate("days", getInteger(a.getDays())).accumulate("teamNum", getInteger(a.getTeamNum()))
				.accumulate("target", getString(a.getTarget())).accumulate("award", getString(a.getAward()))
				.accumulate("institution", getJsonForMember(a.getInstitution()))
				.accumulate("amerceMoney", getDouble(a.getAmerceMoney()))
				.accumulate("judgeMode", getString(a.getJudgeMode())).accumulate("joinMode", getString(a.getJoinMode()))
				.accumulate("category", getString(a.getCategory())).accumulate("status", getString(a.getStatus()))
				.accumulate("action", getString(a.getAction())).accumulate("value", getDouble(a.getValue()))
				.accumulate("image", getString(a.getImage())).accumulate("memo", getString(a.getMemo()))
				.accumulate("judgeID", getString(a.getJudgeID())).accumulate("content", getString(a.getContent()))
				.accumulate("customTareget", getString(a.getCustomTareget())).accumulate("unit", getString(a.getUnit()))
				.accumulate("evaluationMethod", getString(a.getEvaluationMethod()))
				.accumulate("applyCount", applyCount(l));
		if (a.getJudges().size() > 0) {
			final JSONArray jarr1 = new JSONArray();
			for (final ActiveJudge aj : a.getJudges()) {
				final JSONObject obj2 = new JSONObject();
				obj2.accumulate("ajid", aj.getId()).accumulate("judgeId", aj.getJudge().getId())
						.accumulate("judgeName", aj.getJudge().getName())
						.accumulate("judgeImage", aj.getJudge().getImage()).accumulate("role", aj.getJudge().getRole());
				jarr1.add(obj2);
			}
			obj1.accumulate("judges", jarr1);
		}
		jarr.add(obj1);
		JSONObject obj = new JSONObject();
		obj.accumulate("success", true).accumulate("items", jarr);
		response(obj);
	}
}
