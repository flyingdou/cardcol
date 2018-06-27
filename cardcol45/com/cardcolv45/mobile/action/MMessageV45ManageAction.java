package com.cardcolv45.mobile.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.freegym.web.active.TrainRecord;
import com.freegym.web.basic.Member;
import com.freegym.web.config.Setting;
import com.freegym.web.course.Message;
import com.freegym.web.mobile.BasicJsonAction;
import com.sanmen.web.core.bean.BaseMember;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.OpenimCustmsgPushRequest;
import com.taobao.api.request.OpenimCustmsgPushRequest.CustMsg;
import com.taobao.api.response.OpenimCustmsgPushResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Namespace("")
@InterceptorRefs({ @InterceptorRef("mobileStack") })
public class MMessageV45ManageAction extends BasicJsonAction {

	private static final long serialVersionUID = -4900715126840183203L;
	
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	/**
	 * 类型：2为消息,3为提醒，1为审批;
	 */
	private Integer type;

	/**
	 * 审核状态：０拒绝，１同意
	 */
	private Character status;

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Character getStatus() {
		return status;
	}

	public void setStatus(Character status) {
		this.status = status;
	}

	@Override
	protected void list() {
		final DetachedCriteria dc = DetachedCriteria.forClass(Message.class);
		dc.createAlias("memberFrom", "mf");
		dc.createAlias("memberTo", "mt");
		final BaseMember mu = getMobileUser();

		if (type == 1) {
			dc.add(Restrictions.eq("type", "1"));
			dc.add(Restrictions.eq("mt.id", mu.getId()));
		} else if (type == 2) {
			dc.add(Restrictions.eq("type", "2"));
			dc.add(Restrictions.eq("mt.id", mu.getId()));
		} else if (type == 4) {
			dc.add(Restrictions.eq("type", "4"));
			dc.add(Restrictions.eq("mt.id", mu.getId()));
		} else {
			dc.add(Restrictions.eq("type", String.valueOf(type)));
			dc.add(Restrictions.or(Restrictions.eq("mf.id", mu.getId()), Restrictions.eq("mt.id", mu.getId())));
		}

		dc.add(Restrictions.isNull("parent"));
		pageInfo.setOrder("sendTime");
		pageInfo.setOrderFlag("desc");
		pageInfo = service.findPageByCriteria(dc, pageInfo, 2);
		final JSONArray jarr = new JSONArray();
		for (final Iterator<?> it = pageInfo.getItems().iterator(); it.hasNext();) {
			final Message msg = (Message) it.next();
			final JSONObject obj = new JSONObject();
			     obj.accumulate("id", msg.getId())
			     	.accumulate("parent", getLong(msg.getParent()))
			     	.accumulate("type", getString(msg.getType()))
					.accumulate("isRead", getString(msg.getIsRead()))
					.accumulate("memberFrom", getMemberJson(msg.getMemberFrom()))
					.accumulate("memberTo", getMemberJson(msg.getMemberTo()))
					.accumulate("content", sdf.format(msg.getSendTime()) + " " + getString(msg.getContent()))
					.accumulate("status", getString(msg.getStatus()))
					;
			jarr.add(obj);
		}
		final JSONObject ret = new JSONObject();
		ret.accumulate("success", true).accumulate("pageInfo", getJsonForPageInfo()).accumulate("items", jarr);
		response(ret);
	}

	/**
	 * 加载提醒列表
	 */
	public void remindList() {
		final DetachedCriteria dc = DetachedCriteria.forClass(Message.class);
		dc.createAlias("memberFrom", "mf");
		dc.createAlias("memberTo", "mt");
		final BaseMember mu = getMobileUser();
		dc.add(Restrictions.eq("mt.id", mu.getId()));
		dc.add(Restrictions.eq("type", "3"));
		dc.add(Restrictions.or(Restrictions.eq("isRead", "0"), Restrictions.isNull("isRead")));
		pageInfo = service.findPageByCriteria(dc, pageInfo, 4);
		final JSONArray jarr = new JSONArray();
		final List<Message> msgs = new ArrayList<Message>();
		for (final Iterator<?> it = pageInfo.getItems().iterator(); it.hasNext();) {
			final Message msg = (Message) it.next();
			msg.setIsRead("1");
			msgs.add(msg);
			final JSONObject obj = new JSONObject();
			obj.accumulate("id", msg.getId()).accumulate("parent", getLong(msg.getParent())).accumulate("type", getString(msg.getType()))
					.accumulate("memberFrom", getMemberJson(msg.getMemberFrom())).accumulate("memberTo", getMemberJson(msg.getMemberTo()))
					.accumulate("content", getString(msg.getContent())).accumulate("status", getString(msg.getStatus()));
			jarr.add(obj);
		}
		if (msgs.size() > 0) service.saveOrUpdate(msgs);
		final JSONObject ret = new JSONObject();
		ret.accumulate("success", true).accumulate("pageInfo", getJsonForPageInfo()).accumulate("items", jarr);
		response(ret);

	}

	/**
	 * 客户端待审核的数据为运动记录
	 */
	public void loadRecords() {
		final Member m = (Member) service.load(Member.class, getMobileUser().getId());
		final DetachedCriteria dc = DetachedCriteria.forClass(TrainRecord.class);
		dc.createAlias("activeOrder", "ao");
		dc.add(Restrictions.eq("confrim", '0'));
		dc.add(Restrictions.in("ao.judge", new Object[] { m.getId().toString(), m.getName(), m.getNick(), m.getMobilephone(), m.getEmail() }));
		pageInfo.setOrder("doneDate");
		pageInfo.setOrderFlag("desc");
		pageInfo = service.findPageByCriteria(dc, pageInfo, 1);
		final JSONArray jarr = new JSONArray();
		for (final Iterator<?> it = pageInfo.getItems().iterator(); it.hasNext();) {
			final TrainRecord tr = (TrainRecord) it.next();
			tr.setStrength(service.getActualWeight(tr.getPartake().getId(), tr.getDoneDate()));
			final JSONObject obj = new JSONObject();

			int i = 0;
			Calendar cal = Calendar.getInstance();
			if (!cal.before(tr.getPartake().getBirthday())) {
				int year1 = cal.get(Calendar.YEAR);
				int month1 = cal.get(Calendar.MONTH) + 1;
				int day1 = cal.get(Calendar.DAY_OF_MONTH);
				cal.setTime(tr.getPartake().getBirthday());
				int year2 = cal.get(Calendar.YEAR);
				int month2 = cal.get(Calendar.MONTH) + 1;
				int day2 = cal.get(Calendar.DAY_OF_MONTH);
				i = year1 - year2;
				if (month1 <= month2) {
					if (month1 == month2) {
						if (day1 < day2) {
							i--;
						}
					} else {
						i--;
					}
				}
			}
			final Setting set = service.loadSetting(tr.getPartake().getId());
			obj.accumulate("id", tr.getId()).accumulate("parent", 0).accumulate("type", "1").accumulate("isRead", "0").accumulate("doneDate", ymd.format(tr.getDoneDate()))
					.accumulate("memberFrom",
							getMemberJson(tr.getPartake()).accumulate("bmiHigh", set.getBmiHigh()).accumulate("bmiLow", set.getBmiLow()).accumulate("heart", set.getHeart())
									.accumulate("age", i))
					.accumulate("memberTo", getMemberJson(tr.getActiveOrder().getActive().getCreator())).accumulate("content", ymd.format(tr.getDoneDate()) + "待审核挑战成绩");
			if (tr.getTimes() != null) obj.accumulate("times", getDouble(tr.getTimes()));
			if (tr.getAction() != null && !"".equals(tr.getAction())) obj.accumulate("action", tr.getAction());
			if (tr.getActionQuan() != null) obj.accumulate("actionQuan", tr.getActionQuan());
			if (tr.getFat() != null) obj.accumulate("fat", tr.getFat());
			if (tr.getHip() != null) obj.accumulate("hip", tr.getHip());
			if (tr.getMemo() != null && !"".equals(tr.getMemo())) obj.accumulate("memo", tr.getMemo());
			obj.accumulate("strength", getDouble(tr.getStrength()));
			// if(tr.getHeartRate() != null) obj.accumulate("heart",
			// tr.getHeartRate());
			if (tr.getHeight() != null) obj.accumulate("height", tr.getHeight());
			if (tr.getWaist() != null) obj.accumulate("waist", tr.getWaist());
			if (tr.getWeight() != null) obj.accumulate("weight", tr.getWeight());
			if (tr.getHeartRate() != null) obj.accumulate("heartRate", tr.getHeartRate());
			if (tr.getUnit() != null) obj.accumulate("unit", tr.getUnit());

			jarr.add(obj);
		}
		final JSONObject ret = new JSONObject();
		ret.accumulate("success", true).accumulate("pageInfo", getJsonForPageInfo()).accumulate("items", jarr);
		response(ret);
	}

	/**
	 * 待审核的数据为运动记录
	 */
	public void auditRecord() {
		try {
			service.updateRecordData(id, status);
			final JSONObject ret = new JSONObject();
			ret.accumulate("success", true).accumulate("message", "OK");
			response(ret);
		} catch (Exception e) {
			log.error("error", e);
			response(e);
		}
	}

	/**
	 * 加载单个信息具体内容
	 */
	public void loadMessage() {
		try {
			final Message msg = (Message) service.load(Message.class, id);
			final JSONArray jarr = new JSONArray();
			for (final Message m : msg.getMessages()) {
				final JSONObject obj = new JSONObject();
				obj.accumulate("id", m.getId()).accumulate("memberFrom", getMemberJson(m.getMemberFrom())).accumulate("memberTo", getMemberJson(m.getMemberTo()))
						.accumulate("content", getString(m.getContent()));
				jarr.add(obj);
			}
			final JSONObject ret = new JSONObject(), mObj = new JSONObject();
			mObj.accumulate("id", msg.getId()).accumulate("memberFrom", getMemberJson(msg.getMemberFrom())).accumulate("memberTo", getMemberJson(msg.getMemberTo()))
					.accumulate("content", getString(msg.getContent()));
			jarr.add(mObj);
			ret.accumulate("success", true).accumulate("items", jarr);
			response(ret);
		} catch (Exception e) {
			log.error("error", e);
			response(e);
		}
	}

	/**
	 * 保存消息或审批等，参数json=[{memberTo: 11, type: 1,2,3(1审批，2消息，3提醒), parent: 10,
	 * content: 内容}]
	 */
	@Override
	protected Long executeSave(JSONArray objs) {
		final BaseMember mu = getMobileUser();
		final JSONObject obj = objs.getJSONObject(0);
		obj.accumulate("memberFrom", getMobileUser().getId());
		Message msg = new Message();
		msg.setContent(obj.getString("content"));
		msg.setIsRead("0");
		msg.setMemberFrom(new Member(mu.getId()));
		msg.setParent(null);
		msg.setMemberTo(new Member(obj.getLong("memberTo")));
		msg.setSendTime(new Date());
		msg.setStatus("2");
		msg.setType(obj.getString("type"));
		msg = service.saveOrUpdateMessage(msg);
		return msg.getId();
	}

	/**
	 * 审批数据
	 */
	@SuppressWarnings({ "rawtypes", "unchecked", "unused" })
	@Override
	public void audit() {
		try {
			//测试数据
//			JSONObject jsonObject = new JSONObject();
//			jsonObject.accumulate("id", 2821)
//			.accumulate("type", 2)
//			.accumulate("parent", 10)
//			//.accumulate("content", "dou同意了您的聘请！2017-08-21")
//			//.accumulate("memberTo", 9388)
//			;
//			JSONArray jsonArray = new JSONArray();
//			jsonArray.add(jsonObject);
//			String jsons = jsonArray.toString();
			
			
			final JSONArray objs = JSONArray.fromObject(jsons);
			final JSONObject obj = objs.getJSONObject(0);
			Message msg = new Message();
			msg.setId(obj.getLong("id"));
			msg.setStatus(obj.getInt("type") == 0 ? "3" : "2");
			//msg.setParent(obj.getLong("parent"));
			//msg.setContent(obj.getString("content"));
			service.saveChangeMessageStatus(msg, null);
			String url = "http://gw.api.taobao.com/router/rest";
			TaobaoClient client = new DefaultTaobaoClient(url, "23330566", "0c6b77f937d49f3a14ca98a6316d110e");
			OpenimCustmsgPushRequest req = new OpenimCustmsgPushRequest();
			CustMsg obj1 = new CustMsg();
			ArrayList list = new ArrayList();
			Member m = (Member) service.load(Member.class, Long.parseLong(obj.getString("memberTo")));
			obj1.setFromUser("some_one");
			obj1.setToAppkey("0");
			list.add(m.getId());
			obj1.setToUsers(list);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			obj1.setSummary(sdf.format(new Date()));
			obj1.setData("applyresult");
			obj1.setAps("{\"alert\":\"申请结果\"}");
			obj1.setApnsParam("applyresult");
			obj1.setInvisible(1L);
			obj1.setFromNick(getMobileUser().getName());
			req.setCustmsg(obj1);
			OpenimCustmsgPushResponse rsp = client.execute(req);
			final JSONObject ret = new JSONObject();
			ret.accumulate("success", true).accumulate("message", "OK");
			response(ret);
		} catch (Exception e) {
			log.error("error", e);
			response(e);
		}
	}

	/**
	 * 更新查看状态
	 */
	public void updateStatus() {
		try {
			final Message msg = (Message) service.load(Message.class, id);
			msg.setIsRead("1");
			service.saveOrUpdate(msg);
			final JSONObject ret = new JSONObject();
			ret.accumulate("success", true).accumulate("message", "OK");
			response(ret);
		} catch (Exception e) {
			log.error("error", e);
			response(e);
		}
	}

	@Override
	protected Class<?> getEntityClass() {
		return Message.class;
	}

	@Override
	protected String getExclude() {
		return "coach,coachMembers,friends,clubs,coachs,members,visitors,setting,times,targets,products,certificates,courses,alwaysAddrs,applys,coursess,records,prods,appraises,appraises1,allFriends,orderDetails,orderDetails1,messages,messages1,orders,addrs,signIns,signIns1,shops,accounts,prodOrders,courseCoachs,courseMembers,tickets";
	}
}
