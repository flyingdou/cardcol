package com.freegym.web.schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.freegym.web.service.IBasicService;

/**
 * 训练周期提醒：用户如果一个月签到次数少于1次，系统发提醒邮件。
 * @author Admin
 *
 */
@Component
public class SignRemindSchedule {

	@Qualifier("basicService")
	@Autowired(required = true)
	private IBasicService service;

	@Scheduled(cron = "0 0 2 * * ?")
	public void execute(){
		service.sendMailByWorkoutTimes();
	}
}
