package com.freegym.web.basic.action;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.BeanUtils;

import com.freegym.web.BaseBasicAction;
import com.freegym.web.active.TrainRecord;
import com.freegym.web.basic.Friend;
import com.freegym.web.basic.Member;
import com.freegym.web.config.Setting;
import com.freegym.web.course.Message;
import com.freegym.web.order.ActiveOrder;
import com.freegym.web.utils.WeiboUtils;
import com.sanmen.web.core.common.LogicException;
import com.sanmen.web.core.utils.DateUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Namespace("")
@InterceptorRefs({ @InterceptorRef("customStack") })
@Results({ @Result(name = "success", location = "/member/index.jsp") })
public class MemberManageAction extends BaseBasicAction {

	private static final long serialVersionUID = -3769475961884283745L;

	private Member member;

	private String startDate, endDate, recordType;

	private TrainRecord record;

	private Setting setting;

	public Setting getSetting() {
		return setting;
	}

	public void setSetting(Setting setting) {
		this.setting = setting;
	}

	public TrainRecord getRecord() {
		return record;
	}

	public void setRecord(TrainRecord record) {
		this.record = record;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getRecordType() {
		return recordType;
	}

	public void setRecordType(String recordType) {
		this.recordType = recordType;
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public String execute() {
		final Date nowDate = new Date();
		if (member != null && member.getId() != null) {
			member = (Member) service.load(Member.class, member.getId());
			if (this.toMember() != null) {
				final List<?> friendList = service.findObjectBySql(
						" from Friend f where f.member.id = ? and f.friend.id = ? and f.type = ?",
						new Object[] { member.getId(), this.toMember().getId(), "4" });
				if (friendList == null || friendList.size() == 0)
					service.saveOrUpdate(new Friend(member, toMember(), nowDate, "4", "0", nowDate));
				if (toMember().getSinaId() != null && !"".equals(toMember().getSinaId()))
					request.setAttribute("emotions", WeiboUtils.getEmotions(toMember().getSinaId()));
			}
			session.setAttribute("wpath", 1);
			session.setAttribute("toMember", member);
		} else {
			if (toMember() == null)
				return "login";
			member = (Member) service.load(Member.class, this.toMember().getId());
			session.setAttribute("spath", 1);
			if (toMember().getSinaId() != null && !"".equals(toMember().getSinaId()))
				request.setAttribute("emotions", WeiboUtils.getEmotions(toMember().getSinaId()));
		}
		request.setAttribute("actives", service.findActiveByMember(member.getId()));
		// 目前记录
		record = service.getRecordByDate(member.getId(), nowDate);
		// record = service.getCurrentRecord(member.getId());
		record.setStrength(service.getActualWeight(member.getId(), nowDate));
		// 设定记录
		setting = service.loadSetting(member.getId());
		// 体指指数=体重/身高的平方
		try {
			final Double height = setting.getHeight() / 100d;
			final DecimalFormat df = new DecimalFormat("#00.00");
			final Double bmi = new Double(df.format(setting.getWeight() / (height * height)));
			request.setAttribute("bmi", bmi);
		} catch (Exception e) {
			request.setAttribute("bmi", 0d);
		}
		request.setAttribute("yyxms", service.findActionByMode(1l, '0'));
		Long workoutTimes = service.findWorkoutTimeByMember(member);
		// Long trainRate=service.findTrainRateByMember(member);
		// System.out.println(trainRate+"--"+trainRate/workoutTimes);
		// request.setAttribute("trainRate", trainRate/workoutTimes);
		request.setAttribute("workoutTimes", workoutTimes);
		request.setAttribute("trainTimes", service.findTrainTimesByMember(member));
		return SUCCESS;
	}

	public void getRecordByDate() {
		record = service.getRecordByDate(toMember().getId(), record.getDoneDate());
		record.setStrength(service.getActualWeight(toMember().getId(), record.getDoneDate()));
		String jsonString = getJsonString(record);
		response(jsonString);
	}

	public String queryRecord() {
		final StringBuffer hqlr = new StringBuffer(
				"from TrainRecord cr where cr.partake.id = ? and cr." + recordType + " is not null");
		final List<Object> paramList = new ArrayList<Object>();
		paramList.add(this.toMember().getId());
		if (startDate != null && !"".equals(startDate)) {
			hqlr.append(" and cr.doneDate >= ? ");
			paramList.add(DateUtil.getDateByStr(startDate));
		}
		if (endDate != null && !"".equals(endDate)) {
			hqlr.append("and cr.doneDate <= ? ");
			paramList.add(DateUtil.getDateByStr(endDate));
		}
		hqlr.append(" order by cr.doneDate ");
		final List<?> recordList = service.findObjectBySql(hqlr.toString(), paramList.toArray());
		final StringBuffer json = new StringBuffer("{");
		if (recordList.size() > 0) {
			json.append("\"recordList\":" + getJsonString(recordList));
		}
		json.append("}");
		response(json.toString());
		return null;
	}

	public String saveRecord() {
		JSONObject obj = new JSONObject();
		if (record != null) {
			try {
				final List<TrainRecord> rs = new ArrayList<TrainRecord>();
				if (ids != null) {
					for (int i = 0; i < ids.length; i++) {
						final TrainRecord tr = new TrainRecord();
						BeanUtils.copyProperties(record, tr);
						tr.setActiveOrder(new ActiveOrder(ids[i]));
						tr.setPartake(toMember());
						rs.add(tr);
					}
				} else {
					record.setPartake(toMember());
					rs.add(record);
				}
				final Member m = service.saveOrUpdateRecord(rs, toMember(), ymd.format(record.getDoneDate()));
				Double strength = service.getActualWeight(m.getId(), record.getDoneDate());
				final Integer syncWeibo = Integer.parseInt(request.getParameter("syncWeibo"));
				if (syncWeibo == 1) {
					if (toMember().getSinaId() != null && !"".equals(toMember().getSinaId())) {
						String access_token = toMember().getSinaId();
						WeiboUtils.createWeibo(access_token, record.getMemo());
						obj.accumulate("success", true).accumulate("msg", "ok");
					} else {
						obj.accumulate("success", false).accumulate("msg", "noSinaId");
					}
				} else {
					obj.accumulate("success", true).accumulate("msg", "okRecord");
				}
				obj.accumulate("strength", strength);
				if (m != null)
					session.setAttribute(LOGIN_MEMBER, m);
			} catch (Exception e) {
				log.error("error", e);
				obj.accumulate("success", false).accumulate("msg", e.getMessage());
			}
			response(obj);
		}
		return null;
	}

	/**
	 * 俱乐部在自己的课程上查找其所有的教练，也就是本俱乐部的私教。
	 */
	public void findCoach() {
		final List<?> list = service.findObjectBySql(
				"from Member m where m.id in (select f.member.id from Friend f where f.friend.id = ? and f.member.role = 'S' and f.type = '1')",
				toMember().getId());
		final String str = getJsonString(list);
		response(str);
	}

	/**
	 * 用于教练在自己的课程上查找其所有的会员
	 */
	@SuppressWarnings("static-access")
	public void findMember() {
		try {
			if (toMember() != null) {
				List<?> list = null;
				if (toMember().getRole().equals("S")) {
					list = service.findObjectBySql("from Member m where m.coach.id = ?", toMember().getId());
				} else if (toMember().getRole().equals("E")) {
					String querySql = "select m.* from tb_member m inner join tb_member_friend f on m.id = f.member where f.friend = ? and m.role = 'S' and f.type = '1'";
					list = service.select(querySql, toMember().getId());
				}
				JSONArray arr = this.formatMemberToJson(list);
				response(arr.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 用于会员访问教练的课程表时查询用
	 */
	public void findMemberBy() {
		final Member toMember = (Member) session.getAttribute("toMember");
		final List<?> list = service.findObjectBySql("from Member m where m.coach.id = ?", toMember.getId());
		final String str = getJsonString(list);
		response(str);
	}

	// 解除私教关系
	public String saveRelieve() {
		String msg = "";
		try {
			final Member mm = service.saveRelieve(this.toMember());
			// 会员解除私教关系，私教会收到提醒消息
			Message message = new Message();
			message.setMemberFrom(this.toMember());
			message.setMemberTo(this.toMember().getCoach());
			message.setSendTime(new Date());
			message.setType("3");
			message.setIsRead("0");
			message.setContent(this.toMember().getName() + "已与您解除私教关系！");
			service.saveOrUpdate(message);
			session.setAttribute(LOGIN_MEMBER, mm);
			msg = "ok";
		} catch (LogicException e) {
			msg = e.getMessage();
			log.error("error", e);
		}
		response(msg);
		return null;
	}

	@Override
	protected String getExclude() {
		return "judges,courseGrades,coach,coachMembers,friends,clubs,coachs,members,visitors,setting,times,products,certificates,integrals,courses,alwaysAddrs,applys,coursess,records,courseCoachs,courseMembers,prods,appraises,appraises1,allFriends,orderDetails,orderDetails1,messages,messages1,orders,addrs,signIns,signIns1,shops,accounts,prodOrders,aos,actives,partakes,teams,tickets,ticklings";
	}
	
	/**
	 * 把member转换为json
	 */
	public static JSONObject formatMemberToJson(Member m) {
		JSONObject o = new JSONObject();
		if (m != null) {
			o.accumulate("id", m.getId()).accumulate("name", m.getName()).accumulate("city", m.getCity())
					.accumulate("county", m.getCounty()).accumulate("sex", m.getSex())
					.accumulate("province", m.getProvince()).accumulate("nick", m.getNick())
					.accumulate("image", m.getImage()).accumulate("role", m.getRole())
					.accumulate("lng", m.getLongitude()).accumulate("lat", m.getLatitude())
					.accumulate("mobilephone", m.getMobilephone()).accumulate("mobileValid", m.getMobileValid())
					.accumulate("count", m.getWorkoutTimes());
		}
		return o;
	}

	public static JSONObject formatMemberToJson(Map<String, Object> m) {
		JSONObject o = new JSONObject();
		if (m != null) {
			o.accumulate("id", m.get("id")).accumulate("name", m.get("name")).accumulate("city", m.get("city"))
					.accumulate("county", m.get("county")).accumulate("sex", m.get("sex"))
					.accumulate("province", m.get("province")).accumulate("nick", m.get("nick"))
					.accumulate("image", m.get("image")).accumulate("role", m.get("role"))
					.accumulate("lng", m.get("lng")).accumulate("lat", m.get("lat"))
					.accumulate("mobilephone", m.get("mobilephone")).accumulate("mobileValid", m.get("mobileValid"));
		}
		return o;
	}

	@SuppressWarnings("unchecked")
	public static JSONArray formatMemberToJson(List<?> list) {
		JSONArray o = new JSONArray();
		if (list.get(0) instanceof Member) {
			for (Object m : list) {
				o.add(formatMemberToJson((Member) m));
			}
		} else if (list.get(0) instanceof Map) {
			for (Object m : list) {
				o.add(formatMemberToJson((Map<String, Object>) m));
			}
		}
		return o;
	}

}
