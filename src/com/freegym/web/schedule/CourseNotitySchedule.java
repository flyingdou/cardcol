package com.freegym.web.schedule;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.freegym.web.basic.Member;
import com.freegym.web.course.Message;
import com.freegym.web.plan.Course;
import com.freegym.web.service.ICourseService;
import com.freegym.web.task.MessageThread;

/**
 * 课程开始提醒
 * 
 * @author Admin
 * 
 */
@Component
public class CourseNotitySchedule {

	@Qualifier("courseService")
	@Autowired(required = true)
	private ICourseService service;

	@Scheduled(cron = "0 0 2 * * ?")
	public void execute() throws Exception {
		final SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		final SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
		final Calendar c = Calendar.getInstance();
		final String sDate = sdf2.format(c.getTime());
		final String sTime = sdf.format(c.getTime());
		final String nowTime = sDate + " " + sTime;
		c.add(Calendar.HOUR_OF_DAY, 24);
		final String eDate = sdf2.format(c.getTime());

		final List<?> list = service.findObjectBySql("from Course c where c.planDate between ? and ? and c.startTime > ?", sDate, eDate, sTime);
		if (list.size() > 0) {
			final SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			final Calendar c1 = Calendar.getInstance();
			final List<Message> messages = new ArrayList<Message>();
			for (final Iterator<?> it = list.iterator(); it.hasNext();) {
				final Course course = (Course) it.next();
				final Integer reminder = course.getReminder();
				if (reminder != null) {
					final String planTime = course.getPlanDate() + " " + course.getStartTime();
					final Date d = sdf1.parse(planTime);
					c1.setTime(d);
					c1.add(Calendar.HOUR_OF_DAY, 0 - reminder);
					if (planTime.compareTo(nowTime) <= 0) {
						
						//课程提醒推送
						String uid = course.getMember().getUserId();
						Long cid = course.getMember().getChannelId();
						Integer termType = course.getMember().getTermType();
						new MessageThread(uid, cid, termType, "您于" + planTime + "有[" + course.getCourseInfo().getName() + "]的预约，请准时参加。");
						
						final Message msg = new Message();
						msg.setContent("您于" + planTime + "有[" + course.getCourseInfo().getName() + "]的预约，请准时参加。");
						msg.setIsRead("0");
						msg.setMemberFrom(new Member(1l));
						msg.setMemberTo(course.getMember());
						msg.setSendTime(new Date());
						msg.setStatus("2");
						msg.setType("2");
						messages.add(msg);
					}
				}
			}
			service.saveOrUpdate(messages);
		}
	}
}
