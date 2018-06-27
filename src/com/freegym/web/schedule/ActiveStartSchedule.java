package com.freegym.web.schedule;

import java.util.Date;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.freegym.web.service.IBasicService;
import com.sanmen.web.core.utils.DateUtil;

/**
 * 活动开始提醒
 * @author Admin
 *
 */
@Component
public class ActiveStartSchedule {

	@Qualifier("basicService")
	@Autowired(required = true)
	private IBasicService service;

	@SuppressWarnings("unused")
	@Scheduled(cron = "0 0 0 * * ?")
	public void execute(){
		final Date[] ds = DateUtil.getDateTimes();
		final List<?> list = service.findObjectBySql("from ActiveOrder ao where ao.orderStartTime between ? and ?" , ds[0], ds[1]);
//		for (final Iterator<?> it = list.iterator(); it.hasNext();) {
//			final ActiveOrder ao = (ActiveOrder) it.next();
//			if (ao.getStatus() == '0') {
//			} else if (ao.getStatus() == '4') {
//				ao.setStatus('1');
//			}
//		}
//		service.saveOrUpdate(list);
	}
}
