package com.cardcolv45.web.action;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.aliyun.api.gateway.demo.enums.Constantsms;
import com.freegym.web.active.Active;
import com.freegym.web.basic.Member;
import com.freegym.web.mobile.BasicJsonAction;
import com.freegym.web.order.ActiveOrder;
import com.sanmen.web.core.bean.BaseMember;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Namespace("")
@InterceptorRefs({ @InterceptorRef("mobileStack") })
public class MActiveWxV45ManageAction extends BasicJsonAction implements Constantsms{

	private static final long serialVersionUID = -1974093498400108424L;

	private String[] code;

	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	/**
	 * 关键字, 裁判
	 */
	private String keyword;
	private String recomm_id;

	/**
	 * 活动查询类型，0默认查可参加的，1为已参加的，2为我发起的
	 */
	private Integer type = 0;

	private Long teamId;

	private String startDate;

	/**
	 * 挑战模式： A 个人挑战
	 */
	private Character mode;

	/**
	 * 挑战目标：A体重减少，B体重增加，D运动次数
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

	public String[] getCode() {
		return code;
	}

	public void setCode(String[] code) {
		this.code = code;
	}

	/**
	 * 查询挑战列表
	 */
	@SuppressWarnings("all")
	public void findActiveAndDetail() {
		Member memberTest = new Member();
		memberTest.setId(Long.parseLong("9355"));
		type=2;
		request.getSession().setAttribute("member", memberTest);
		
		final BaseMember mu = getLoginMember();
		final JSONArray jarr = new JSONArray();
		if (type == 0 || type == 1) {
			final DetachedCriteria dc = DetachedCriteria.forClass(Active.class);
			dc.createAlias("creator", "c", CriteriaSpecification.LEFT_JOIN);
			if (keyword != null && !"".equals(keyword)) {
				dc.add(this.or(Restrictions.like("name", keyword), Restrictions.like("c.nick", keyword),
						Restrictions.like("c.email", keyword), Restrictions.like("c.mobilephone", keyword)));
			}
			if (type == 0) {
				dc.add(Restrictions.eq("status", 'B'));
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
				obj.accumulate("id", m.getId()).accumulate("member", getMemberJson(m.getCreator()))
						.accumulate("name", getString(m.getName())).accumulate("mode", getString(m.getMode()))
						.accumulate("days", getInteger(m.getDays())).accumulate("teamNum", getInteger(m.getTeamNum()))
						.accumulate("target", getString(m.getTarget())).accumulate("award", getString(m.getAward()))
						.accumulate("institution", getJsonForMember(m.getInstitution()))
						.accumulate("amerceMoney", getDouble(m.getAmerceMoney()))
						.accumulate("category", getString(m.getCategory()))
						.accumulate("status", getString(m.getStatus())).accumulate("action", getString(m.getAction()))
						.accumulate("value", getDouble(m.getValue())).accumulate("applyCount", applyCount(m.getId()))
						.accumulate("image", getString(m.getImage())).accumulate("memo", getString(m.getMemo()))
						.accumulate("result", null).accumulate("startTime", null).accumulate("endTime", null)
						.accumulate("weight", null).accumulate("activeCount", null).accumulate("lastWeight", null)
						.accumulate("activeId", null);
				jarr.add(obj);
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
				int a = ao.getActive().getDays();// 挑战天数
				Date startDate = ao.getOrderStartTime();// 开始时间
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(startDate);
				calendar.add(Calendar.DATE, a);
				Date endDate = calendar.getTime();// 结束时间
				// 活动状态：0为进行中，1为成功，2为失败，3为已结束
				Character result = ao.getResult().equals('0') ? '0' : (ao.getResult() != null ? ao.getResult() : '3');
				obj.accumulate("id", ao.getId()).accumulate("member", getMemberJson(ao.getActive().getCreator()))
						.accumulate("name", getString(ao.getActive().getName()))
						.accumulate("days", getInteger(ao.getActive().getDays()))
						.accumulate("teamNum", getInteger(ao.getActive().getTeamNum()))
						.accumulate("target", getString(ao.getActive().getTarget()))
						.accumulate("award", getString(ao.getActive().getAward()))
						.accumulate("institution", getMemberJson(ao.getActive().getInstitution()))
						.accumulate("amerceMoney", getDouble(ao.getActive().getAmerceMoney()))
						.accumulate("category", getString(ao.getActive().getCategory()))
						.accumulate("status", getString(ao.getStatus()))
						.accumulate("action", getString(ao.getActive().getName()))
						.accumulate("value", getDouble(ao.getActive().getValue()))
						.accumulate("applyCount", applyCount(ao.getActive().getId()))
						.accumulate("image", getString(ao.getActive().getImage()))
						.accumulate("startTime", sdf.format(ao.getOrderStartTime()))
						.accumulate("endTime", sdf.format(endDate)).accumulate("weight", ao.getWeight())
						.accumulate("lastWeight", ao.getLastWeight())
						.accumulate("activeCount", this.signCount(ao.getId(), endDate)).accumulate("result", result)
						.accumulate("activeId", ao.getActive().getId());
				jarr.add(obj);
			}
		}
		final JSONObject ret = new JSONObject();
		ret.accumulate("success", true).accumulate("pageInfo", getJsonForPageInfo()).accumulate("items", jarr);
		response(ret);
	}

	/**
	 * 获取挑战详情
	 * @param uuid String 用户为标识
     * @param id Long  挑战id 
     * @return success: true/false,成功与否。True成功,false其它例外 
	 *	{"id":227,"member":发起人信息,"name":"新挑战二","mode":"A","days":2,"teamNum":0,"target":"A","award":"五颗棒棒糖","institution":慈善机构信息,"amerceMoney":0.01,"joinMode":"A","category":"A","status":"B","action":"","value":1,"image":"6088772767404032.jpeg","memo":"坚持就是胜利","content":"","customTareget":"","unit":"","evaluationMethod":"","applyCount":0}
	 */
	@SuppressWarnings("unused")
	public void getActive() {
		Active a = (Active) service.load(Active.class, id);
		JSONObject obj1 = new JSONObject();
		JSONArray jarr = new JSONArray();
		obj1.accumulate("id", a.getId()).accumulate("member", getMemberJson(a.getCreator()))
				.accumulate("name", getString(a.getName())).accumulate("mode", getString(a.getMode()))
				.accumulate("days", getInteger(a.getDays())).accumulate("teamNum", getInteger(a.getTeamNum()))
				.accumulate("target", getString(a.getTarget())).accumulate("award", getString(a.getAward()))
				.accumulate("institution", getJsonForMember(a.getInstitution()))
				.accumulate("amerceMoney", getDouble(a.getAmerceMoney()))
				.accumulate("joinMode", getString(a.getJoinMode())).accumulate("category", getString(a.getCategory()))
				.accumulate("status", getString(a.getStatus())).accumulate("action", getString(a.getAction()))
				.accumulate("value", getDouble(a.getValue())).accumulate("image", getString(a.getImage()))
				.accumulate("memo", getString(a.getMemo())).accumulate("content", getString(a.getContent()))
				.accumulate("customTareget", getString(a.getCustomTareget())).accumulate("unit", getString(a.getUnit()))
				.accumulate("evaluationMethod", getString(a.getEvaluationMethod()))
				.accumulate("applyCount", applyCount(id));
		response(obj1);
	}

	/**
	 * 挑战健身次数
	 * 
	 * @param id
	 * @return
	 */
	public Long signCount(Long id, Date date) {
		SimpleDateFormat ymd = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
		Long count = null;
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, 1);// 今天+1天
		Date endDate = calendar.getTime();
		ActiveOrder ao = (ActiveOrder) service.load(ActiveOrder.class, id);
		if (id != null) {
			count = service.queryForLong(
					"select count(*) from tb_sign_in where  signDate>= ? and signDate < ? and memberSign=?",
					ymd.format(ao.getOrderStartTime()), ymd.format(endDate), getMobileUser().getId());
		}
		return count;
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
	 * 查找慈善机构列表
	 */
	public void findInstitution() {
		try {
			final List<?> list_login = service.findObjectBySql("from Member m where m.id = ?", getMobileUser().getId());
			final List<?> list = service.findObjectBySql("from Member m where m.role = ?", "I");
			final JSONArray jarr = new JSONArray();
			for (final Iterator<?> it = list.iterator(); it.hasNext();) {
				final Member in = (Member) it.next();
				final JSONObject obj = new JSONObject();
				obj.accumulate("id", in.getId()).accumulate("name", in.getName()).accumulate("city", in.getCity())
						.accumulate("sex", in.getSex()).accumulate("nick", in.getNick())
						.accumulate("image", in.getImage()).accumulate("role", in.getRole())
						.accumulate("lng", in.getLongitude()).accumulate("lat", in.getLatitude())
						.accumulate("mobilephone", in.getMobilephone()).accumulate("mobileValid", in.getMobileValid())
						.accumulate("description", in.getDescription());
				jarr.add(obj);
			}
			for (final Iterator<?> it = list_login.iterator(); it.hasNext();) {
				final Member in = (Member) it.next();
				final JSONObject obj = new JSONObject();
				obj.accumulate("id", in.getId()).accumulate("name", in.getName()).accumulate("city", in.getCity())
						.accumulate("sex", in.getSex()).accumulate("nick", in.getNick())
						.accumulate("image", in.getImage()).accumulate("role", in.getRole())
						.accumulate("lng", in.getLongitude()).accumulate("lat", in.getLatitude())
						.accumulate("mobilephone", in.getMobilephone()).accumulate("mobileValid", in.getMobileValid())
						.accumulate("description", in.getDescription());
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
	 * 发起挑战
	 */
	@SuppressWarnings("unused")
	public Long activeSave() {
		// jsons =
		// "[{\"name\":\"挑战名称\",\"target\":\"A\",\"days\":2, \"memo\":\"注意事项\",\"award\":\"奖励\",\"amerceMoney\":0.01,\"institutionId\":9355,\"amerceMoney\":0.01,\"value\":0.01}]";
		JSONArray arr;
		try {
			arr = JSONArray.fromObject(URLDecoder.decode(jsons, "UTF-8"));
			final JSONObject obj = arr.getJSONObject(0);
			Active a = new Active();
			Member m = new Member();
			a.setStatus('B');
			a.setCreator(new Member(getMobileUser().getId()));
			a.setName(obj.getString("name"));
			a.setDays(obj.getInt("days"));
			a.setTarget(obj.getString("target").charAt(0));
			a.setMemo(obj.getString("memo"));
			a.setMode("A".charAt(0));
			a.setJoinMode("A".charAt(0));
			a.setAward(obj.getString("award"));
			a.setAmerceMoney(obj.getDouble("amerceMoney"));
			a.setInstitution(new Member(obj.getLong("institutionId")));
			a.setCategory(a.getTarget());
			a.setValue(obj.getDouble("value"));
			a.setCreateTime(new Date());
			String fileName1 = image1 != null ? saveFile("picture", image1, image1FileName, null) : null;
			if (fileName1 != null) {
				a.setImage(fileName1);
			}
			a = (Active) service.saveOrUpdate(a);
			JSONObject objs = new JSONObject();
			response(obj.accumulate("success", true).accumulate("id", a.getId()));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 挑战成绩提交
	 */
	public void submitWeight() {
		Double weight = new Double(request.getParameter("weight"));// 用户填写的体重
		service.saveActiveResult(weight, id);
		ActiveOrder ao = (ActiveOrder) service.load(ActiveOrder.class, id);
		final JSONObject obj = new JSONObject();
		obj.accumulate("success", true).accumulate("code", ao.getResult());
		response(obj);
	}

	/**
	 * 首页推荐
	 */
	public void lists() {
		final List<Map<String, Object>> recomms = service.findRecommendBySectorCode(code);
		final JSONArray jarr = JSONArray.fromObject(recomms);
		final JSONObject obj = new JSONObject();
		obj.accumulate("success", true).accumulate("items", jarr);
		response(obj);
	}
}