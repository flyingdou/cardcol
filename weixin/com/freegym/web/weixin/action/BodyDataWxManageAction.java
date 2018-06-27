package com.freegym.web.weixin.action;

import java.util.ArrayList;
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
import com.sanmen.web.core.utils.StringUtils;

import net.sf.json.JSONObject;

@Namespace("")
@InterceptorRefs({ @InterceptorRef("customStack") })
@Results({ @Result(name = "success", location = "/WX/login.jsp"), @Result(name = "bodys", location = "/WX/body.jsp") })
public class BodyDataWxManageAction extends BaseBasicAction {
	/**
	 * 会员身体数据
	 */
	private static final long serialVersionUID = 6264505341320543659L;

	private Member member;
	private Body body;// 体态分析
	private Setting setting;// 会员身体数据
	private TrainRecord trainRecord;// 活动订单

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
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
	@Override
	public String execute() {
		// Test Data
		Member memberTest = new Member();
		memberTest.setId(Long.parseLong("335"));
		memberTest.setRole("M");
		
	    session.setAttribute("member", memberTest);
		Member member = (Member) session.getAttribute("member");
		if (member == null) {
			return SUCCESS;
		} else {
			return bodys();
		}
	}
	public String bodys() {
		member = (Member) session.getAttribute("member");
		if (member != null) {
			Long mid = member.getId();
			setting = service.loadSetting(mid);
			final Date nowDate = new Date();
			trainRecord = service.getRecordByDate(mid, nowDate);
			if (trainRecord.getId() == null) {
				String sql = "SELECT * from tb_plan_record cr where cr.partake = ? order by id desc LIMIT 0,1";
				pageInfo = service.findPageBySql(sql, pageInfo, mid);
			}
			if (member.getRole().equals("S")) {
				request.setAttribute("members", service
						.findObjectBySql("from Member m where m.coach.id = ? or m.id = ?", new Object[] { mid, mid }));
			}
			return "bodys";
		} else {
			return SUCCESS;
		}
	}

	/*
	 * 选择日期保存查看训练日志
	 */
	public String switchDate() {
		member = (Member) session.getAttribute("member");
		Long mid = member.getId();
		setting = service.loadSetting(mid);
		request.setAttribute("doneDate", trainRecord.getDoneDate());
		trainRecord = service.getRecordByDate(mid, trainRecord.getDoneDate());
		if (trainRecord.getId() == null) {
			String sql = "SELECT * from tb_plan_record cr where cr.partake = ? order by id desc LIMIT 0,1";
			pageInfo = service.findPageBySql(sql, pageInfo, mid);
			System.out.println(pageInfo.getItems());
		}
		if (member.getRole().equals("S")) {
			request.setAttribute("members", service.findObjectBySql("from Member m where m.coach.id = ? or m.id = ?",
					new Object[] { mid, mid }));
		}
		return "bodys";

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
				
				if (!StringUtils.isEmpty(request.getParameter("syncWeibo"))) {
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
}