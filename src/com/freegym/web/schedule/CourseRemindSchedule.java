package com.freegym.web.schedule;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.cardcol.web.utils.MovementUtil;
import com.freegym.web.basic.Member;
import com.freegym.web.course.Message;
import com.freegym.web.order.CourseOrder;
import com.freegym.web.plan.Course;
import com.freegym.web.service.ICourseService;

/**
 * 课程开始推送，提前一小时
 * 
 * @author Admin
 * 
 */
@Component
public class CourseRemindSchedule {
	final Logger log = Logger.getLogger(CourseRemindSchedule.class);

	@Qualifier("courseService")
	@Autowired(required = true)
	private ICourseService service;

	@SuppressWarnings("unused")
	@Scheduled(cron = "0 0 */1 * * ?")
	public void execute() throws Exception {
		final SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		final SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
		final Calendar c = Calendar.getInstance();
		final String nTime=sdf.format(c.getTime());
		c.add(Calendar.HOUR_OF_DAY, 1);
		final String sDate = sdf2.format(c.getTime());
		final String sTime = sdf.format(c.getTime());
		final String nowTime = sDate + " " + sTime;
		final List<?> list = service.findObjectBySql("from Course where planDate = ? and startTime between ? and ?",sDate,nTime, sTime);
		if (list.size() > 0) {
			final List<Message> messages = new ArrayList<Message>();
			for (final Iterator<?> it = list.iterator(); it.hasNext();) {
				final Course course = (Course) it.next();
				final List<?> co = service.findObjectBySql("from CourseOrder where course.id = ?" ,course.getId());
				for (final Iterator<?> its = co.iterator(); it.hasNext();) {
				final CourseOrder courseorder = (CourseOrder) its.next();
				final Member member = courseorder.getMember();
				String body="您于[" + courseorder.getCourse().getStartTime() + "]有[" + courseorder.getCourse().getCourseInfo().getName() + "]的预约，请准时参加。";
				MovementUtil.movement(member.getId().toString(), body);
				}
//				new Thread(new Runnable() {
//					@Override
//					public void run() {
//						BaiduPushUtils.push(member.getTermType(), member.getChannelId(), member.getUserId(), "卡库通知",
//								"您于[" + nowTime + "]有[" + course.getCourseInfo().getName() + "]的预约，请准时参加。");
//					}
//				}).start();
			}
			service.saveOrUpdate(messages);
		}
	}
}
