package com.freegym.web.plan.gen;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.freegym.web.config.CourseInfo;
import com.freegym.web.service.IBasicService;
import com.freegym.web.utils.SessionConstant;

public abstract class AbstractGenerator implements IPlanGenerator, SessionConstant {

	protected Logger log = Logger.getLogger(getClass());

	protected static String sTime = "09:00", eTime = "11:00";

	protected IBasicService service;

	protected Long coachId;

	protected Long memberId;

	protected Date startDate;

	protected CourseInfo ci;

	private List<CourseInfo> courseInfos;

	private static Map<String, String> maps = new HashMap<String, String>();
	private static Map<String, String> maps1 = new HashMap<String, String>();

	static {
		maps.put("1", "瘦身减重");
		maps.put("2", "健美增肌");
		maps.put("3", "提高运动表现");
	}
	static {
		maps1.put("1", "瘦身减重");
		maps1.put("2", "健美增肌");
		maps1.put("3", "增加力量,运动康复,促进健康");
		maps1.put("4", "提高运动表现");
	}

	public AbstractGenerator(IBasicService service, Long coachId, Long memberId, Date startDate) {
		this.service = service;
		this.coachId = coachId;
		this.memberId = memberId;
		this.startDate = startDate;
		this.courseInfos = service.findSystemCourse();
	}

	protected void getCourseInfo(String target) {
		String name = maps.get(target);
		for (final CourseInfo course : courseInfos) {
			if (course.getName().equals(name)) {
				ci = course;
				break;
			}
		}
	}

	protected String getIntervalSeconds(String target) {
		if ("1".equals(target)) return "30";
		else if ("2".equals(target)) return "60";
		else if ("3".equals(target)) return "120";
		return "0";
	}

	protected void getCourseInfo1(String target) {
		String name = maps1.get(target);
		for (final CourseInfo course : courseInfos) {
			if (name.indexOf(course.getName()) >= 0) {
				ci = course;
				break;
			}
		}
	}
}
