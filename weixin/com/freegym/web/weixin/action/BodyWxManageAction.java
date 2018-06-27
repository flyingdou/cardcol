package com.freegym.web.weixin.action;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.BeanUtils;

import com.freegym.web.BaseBasicAction;
import com.freegym.web.active.TrainRecord;
import com.freegym.web.basic.Body;
import com.freegym.web.basic.Member;
import com.freegym.web.config.Setting;
import com.freegym.web.order.ActiveOrder;
import com.freegym.web.utils.WeiboUtils;

import net.sf.json.JSONObject;

@Namespace("")
@InterceptorRefs({ @InterceptorRef("customStack") })
@Results({ @Result(name = "success", location = "/WX/login.jsp"),
		@Result(name = "bodys", location = "/WX/trainRizhi.jsp"),
		@Result(name = "trainDate", location = "/WX/trainRizhiDate.jsp"),
		@Result(name = "sport", location = "/WX/writeSportData.jsp"),
		@Result(name = "trainRizhis", location = "/WX/trainRizhi1.jsp"),
		@Result(name = "writeSport", location = "/WX/writeSportData1.jsp") })
public class BodyWxManageAction extends BaseBasicAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8614804039704207800L;

	private Member member;
	private Body body;// 体态分析
	private Setting setting;// 会员身体数据
	private TrainRecord trainRecord;// 活动订单
	private int age;
	private String date;
	private String conclusion;
	private String doneDate;

	public String getDoneDate() {
		return doneDate;
	}

	public void setDoneDate(String doneDate) {
		this.doneDate = doneDate;
	}

	public String getConclusion() {
		return conclusion;
	}

	public void setConclusion(String conclusion) {
		this.conclusion = conclusion;
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getNowDate() {
		return date;
	}

	public void setNowDate(String nowDate) {
		this.date = nowDate;
	}

	public Body getBody() {
		return body;
	}

	public void setBody(Body body) {
		this.body = body;
	}

	public Setting getSetting() {
		return setting;
	}

	public void setSetting(Setting setting) {
		this.setting = setting;
	}

	public TrainRecord getTrainRecord() {
		return trainRecord;
	}

	public void setTrainRecord(TrainRecord trainRecord) {
		this.trainRecord = trainRecord;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	/*
	 * 训练日志首页
	 */
	@Override
	public String execute() {
		member = (Member) session.getAttribute("member");
		if (member != null) {
			Long mid = member.getId();
			setting = service.loadSetting(mid);
			final Date nowDate = new Date();
			trainRecord = service.getRecordByDate(mid, nowDate);
			if (member.getRole().equals("S")) {
				request.setAttribute("members", service
						.findObjectBySql("from Member m where m.coach.id = ? or m.id = ?", new Object[] { mid, mid }));
			}
			return "bodys";
		} else {
			return SUCCESS;
		}
	}

	public String trainDate() {
		member = (Member) session.getAttribute("member");
		Long mid = member.getId();
		final Date nowDate = new Date();
		trainRecord = service.getRecordByDate(mid, nowDate);
		return "trainDate";
	}

	public String switchYearMonth() {
		member = (Member) session.getAttribute("member");
		body = service.loadBodyData(body, member.getId());
		conclusion = body.getConclusion();
		return "bodys";
	}

	/*
	 * 选择日期保存查看训练日志
	 */
	public String switchDate() {
		@SuppressWarnings("unused")
		final Date nowDate = new Date();
		member = (Member) session.getAttribute("member");
		Long mid = member.getId();
		setting = service.loadSetting(mid);
		request.setAttribute("doneDate", trainRecord.getDoneDate());
		trainRecord = service.getRecordByDate(mid, trainRecord.getDoneDate());
		if (member.getRole().equals("S")) {
			request.setAttribute("members", service.findObjectBySql("from Member m where m.coach.id = ? or m.id = ?",
					new Object[] { mid, mid }));
		}
		return "bodys";

	}

	public String writeSportData() {
		trainRecord = (TrainRecord) service.load(TrainRecord.class, id);
		member = (Member) service.load(Member.class, trainRecord.getPartake().getId());
		setting = service.loadSetting(trainRecord.getPartake().getId());
		return "sport";
	}

	public String writeData() {
		request.setAttribute("doneDate", doneDate);
		member = (Member) session.getAttribute("member");
		setting = service.loadSetting(member.getId());
		return "sport";
	}

	public String trainRizhis() {
		write();
		return "trainRizhis";
	}

	public String trainRizhi() {
		write();
		member = (Member) session.getAttribute("member");
		setting = service.loadSetting(member.getId());
		return "writeSport";
	}

	public String writeSport() {
		member = (Member) session.getAttribute("member");
		write();
		request.setAttribute("weight", trainRecord.getWeight());
		request.setAttribute("waist", trainRecord.getWaist());
		request.setAttribute("hip", trainRecord.getHip());
		request.setAttribute("height", member.getSetting().getHeight());
		return "trainRizhis";
	}

	public String saveRecord() {
		JSONObject obj = new JSONObject();
		member = (Member) session.getAttribute("member");
		if (trainRecord != null) {
			try {
				final List<TrainRecord> rs = new ArrayList<TrainRecord>();
				if (ids != null) {
					for (int i = 0; i < ids.length; i++) {
						final TrainRecord tr = new TrainRecord();
						BeanUtils.copyProperties(trainRecord, tr);
						tr.setActiveOrder(new ActiveOrder(ids[i]));
						tr.setPartake(member);
						rs.add(tr);
					}
				} else {
					trainRecord.setPartake(member);
					rs.add(trainRecord);
				}
				final Member m = service.saveOrUpdateRecord(rs, member, ymd.format(trainRecord.getDoneDate()));
				Double strength = service.getActualWeight(m.getId(), trainRecord.getDoneDate());
				final Integer syncWeibo = Integer.parseInt(request.getParameter("syncWeibo"));
				if (syncWeibo == 1) {
					if (toMember().getSinaId() != null && !"".equals(toMember().getSinaId())) {
						String access_token = toMember().getSinaId();
						WeiboUtils.createWeibo(access_token, trainRecord.getMemo());
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

	public int getMemberAge(Date birthday) {
		Calendar cal = Calendar.getInstance();
		if (cal.before(birthday)) {
			return 0;
		}
		int yearNow = cal.get(Calendar.YEAR);
		int monthNow = cal.get(Calendar.MONTH) + 1;
		int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);
		cal.setTime(birthday);
		int yearBirth = cal.get(Calendar.YEAR);
		int monthBirth = cal.get(Calendar.MONTH) + 1;
		int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);
		int age = yearNow - yearBirth;
		if (monthNow <= monthBirth) {
			if (monthNow == monthBirth) {
				// monthNow==monthBirth
				if (dayOfMonthNow < dayOfMonthBirth) {
					age--;
				}
			} else {
				// monthNow>monthBirth
				age--;
			}
		}
		return age;
	}

	public void write() {
		request.setAttribute("trainRecord", trainRecord);
		request.setAttribute("doneDate", trainRecord.getDoneDate());
		System.out.println("---------------------------------");
		System.out.println(trainRecord.getDoneDate());
		System.out.println(trainRecord.getAction());
		System.out.println(trainRecord.getActionQuan());
		System.out.println(trainRecord.getTimes());
		System.out.println(trainRecord.getHeartRate());
		System.out.println("---------------------------------");
		request.setAttribute("action", trainRecord.getAction());
		request.setAttribute("actionquan", trainRecord.getActionQuan());
		request.setAttribute("times", trainRecord.getTimes());
		request.setAttribute("heartrate", trainRecord.getHeartRate());
		request.setAttribute("unit", trainRecord.getUnit());
	}

}
