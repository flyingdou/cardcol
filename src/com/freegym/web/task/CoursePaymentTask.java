package com.freegym.web.task;

import java.util.TimerTask;

import com.freegym.web.order.FactoryOrder;
import com.sanmen.web.core.service.IService;

public class CoursePaymentTask extends TimerTask {

	private IService service;

	private Long fId;

	public CoursePaymentTask(IService service, Long fId) {
		this.service = service;
		this.fId = fId;
	}

	@Override
	public void run() {
		String sql = "select status from tb_factory_order where id = " + fId;
		Long status = service.queryForLong(sql);
		if (status != 1) {
			service.delete(FactoryOrder.class, fId);
		}
	}

}
