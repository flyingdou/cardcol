package com.freegym.web.basic.action;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONObject;

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
import com.freegym.web.utils.BodyUtils;
import com.freegym.web.utils.WeiboUtils;

@Namespace("")
@InterceptorRefs({ @InterceptorRef("foreStack") })
@Results({ @Result(name = "success", location = "/basic/body.jsp"), @Result(name = "detail", location = "/basic/body_detail.jsp") })
public class BodyManageAction extends BaseBasicAction {

	private static final long serialVersionUID = 8942409430710869379L;

	private Body body;

	private File image;

	private String imageFileName;

	private String imageContentType;

	private Integer type;

	private String conclusion;

	private Setting setting;

	private TrainRecord trainRecord;

	private int age;
	
	public Body getBody() {
		return body;
	}

	public void setBody(Body body) {
		this.body = body;
	}

	public File getImage() {
		return image;
	}

	public void setImage(File image) {
		this.image = image;
	}

	public String getImageFileName() {
		return imageFileName;
	}

	public void setImageFileName(String imageFileName) {
		this.imageFileName = imageFileName;
	}

	public String getImageContentType() {
		return imageContentType;
	}

	public void setImageContentType(String imageContentType) {
		this.imageContentType = imageContentType;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getConclusion() {
		return conclusion;
	}

	public void setConclusion(String conclusion) {
		this.conclusion = conclusion;
	}

	public Setting getSetting() {
		return setting;
	}

	public void setSetting(Setting setting) {
		this.setting = setting;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public TrainRecord getTrainRecord() {
		return trainRecord;
	}

	public void setTrainRecord(TrainRecord trainRecord) {
		this.trainRecord = trainRecord;
	}

	@SuppressWarnings("unused")
	public String execute() {
		final Date nowDate = new Date();
		session.setAttribute("spath", 9);
		Long mid = toMember().getId();
		body = service.loadBodyData(body, mid);
		conclusion = body.getConclusion();
		setting = service.loadSetting(mid);
		loadBodyStatus();
		age = getMemberAge(toMember().getBirthday());
		trainRecord = service.getCurrentRecord(mid);
		
		//record = service.getRecordByDate(mid, nowDate);
		if (toMember().getRole().equals("S")) {
			request.setAttribute("members", service.findObjectBySql("from Member m where m.coach.id = ? or m.id = ?", new Object[] { mid, mid }));
		}
		return SUCCESS;
	}
	public void getRecordByDate() {
		trainRecord = service.getRecordByDate(toMember().getId(), trainRecord.getDoneDate());
		trainRecord.setStrength(service.getActualWeight(toMember().getId(), trainRecord.getDoneDate()));
		String jsonString = getJsonString(trainRecord);
		response(jsonString);
	}

	public String saveRecord() {
		JSONObject obj = new JSONObject();
		if (trainRecord != null) {
			try {
				final List<TrainRecord> rs = new ArrayList<TrainRecord>();
				if (ids != null) {
					for (int i = 0; i < ids.length; i++) {
						final TrainRecord tr = new TrainRecord();
						BeanUtils.copyProperties(trainRecord, tr);
						tr.setActiveOrder(new ActiveOrder(ids[i]));
						tr.setPartake(toMember());
						rs.add(tr);
					}
				} else {
					trainRecord.setPartake(toMember());
					rs.add(trainRecord);
				}
				final Member m = service.saveOrUpdateRecord(rs, toMember(), ymd.format(trainRecord.getDoneDate()));
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
				if (m != null) session.setAttribute(LOGIN_MEMBER, m);
			} catch (Exception e) {
				log.error("error", e);
				obj.accumulate("success", false).accumulate("msg", e.getMessage());
			}
			response(obj);
		}
		return null;
	}
	
	private void loadBodyStatus() {
		if (body == null) {
			body = new Body();
			body.setMember(toMember().getId());
			body.setAnalyDate(new Date());
		}
		request.setAttribute("status", service.findMonthStatus(body.getMember(), body.getAnalyDate()));
	}

	public String switchYearMonth() {
		body = service.loadBodyData(body, toMember().getId());
		conclusion = body.getConclusion();
		loadBodyStatus();
		return "detail";
	}

	@SuppressWarnings("unused")
	public String switchDate() {
		final Date nowDate = new Date();
		body = service.loadBodyData(body, toMember().getId());
		conclusion = body.getConclusion();
		loadBodyStatus();
		Long mid = toMember().getId();
		setting = service.loadSetting(body.getMember());
		age = getMemberAge(((Member)(service.findObjectBySql
				("from Member m where m.id = ?", new Object[] {body.getMember()}).get(0))).getBirthday());
		//trainRecord = service.getCurrentRecord(body.getMember());
		trainRecord = service.getRecordByDate(mid, trainRecord.getDoneDate());
		if (toMember().getRole().equals("S")) {
			request.setAttribute("members", service.findObjectBySql("from Member m where m.coach.id = ? or m.id = ?", new Object[] { mid, mid }));
		}
		return "detail";
	}

	public String getMemberData() {
		final JSONObject obj = new JSONObject();
		try {
			trainRecord = service.getRecordByDate(toMember().getId(), trainRecord.getDoneDate());
			trainRecord.setStrength(service.getActualWeight(toMember().getId(), trainRecord.getDoneDate()));
			//String jsonString = getJsonString(record);
			setting = service.loadSetting(id);
			Member m = (Member) service.load(Member.class, id);
			age = getMemberAge(m.getBirthday());
			trainRecord = service.getCurrentRecord(id);
			if (setting != null)
				obj.accumulate("height", setting.getHeight()).accumulate("weight", setting.getWeight()).accumulate("age", age)
						.accumulate("waistline", setting.getWaistline());
			if (trainRecord != null) {
				obj.accumulate("hip", trainRecord.getHip()).accumulate("action", trainRecord.getAction());
				
			}
			obj.accumulate("success", true);
		} catch (Exception e) {
			obj.accumulate("success", false).accumulate("message", e.getMessage());
		}
		response(obj);
		return null;
	}

	public String upload() {
		body = service.loadBodyData(body, toMember().getId());
		String oldFile = type == 1 ? body.getImageFront() : type == 2 ? body.getImageSide() : body.getImageBack();
		String fileName = image != null ? saveFile("picture", image, imageFileName, oldFile) : null;
		switch (type) {
		case 1:
			body.setImageFront(fileName);
			break;
		case 2:
			body.setImageSide(fileName);
			break;
		case 3:
			body.setImageBack(fileName);
			break;
		}
		body = (Body) service.saveOrUpdate(body);
		conclusion = body.getConclusion();
		Long mid = toMember().getId();
		if (toMember().getRole().equals("S")) {
			request.setAttribute("members", service.findObjectBySql("from Member m where m.coach.id = ? or m.id = ?", new Object[] { mid, mid }));
		}
		loadBodyStatus();
		return SUCCESS;
	}

	public String onSave() {
		body.setConclusion(BodyUtils.handlerBody(body));
		body = (Body) service.saveOrUpdate(body);
		conclusion = body.getConclusion();
		Long mid = toMember().getId();
		if (toMember().getRole().equals("S")) {
			request.setAttribute("members", service.findObjectBySql("from Member m where m.coach.id = ? or m.id = ?", new Object[] { mid, mid }));
		}
		loadBodyStatus();
		return "detail";
	}

	public String onSaveConclusion() {
		body = service.loadBodyData(body, toMember().getId());
		body.setConclusion(conclusion);
		body = (Body) service.saveOrUpdate(body);
		loadBodyStatus();
		return "detail";
	}

	public String onSaveSetting() {
		try {
			final Setting s = service.loadSetting(toMember().getId());
			s.setHeart(setting.getHeart());
			s.setHeight(setting.getHeight());
			s.setWeight(setting.getWeight());
			s.setWaistline(setting.getWaistline());
			setting = (Setting) service.saveOrUpdate(s);
			response();
		} catch (Exception e) {
			response(e);
		}
		return null;
	}

	public int getMemberAge(Date birthday) {
		Calendar cal = Calendar.getInstance();
		if (cal.before(birthday)) { return 0; }
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

}
