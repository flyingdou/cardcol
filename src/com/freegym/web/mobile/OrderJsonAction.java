package com.freegym.web.mobile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.freegym.web.service.IOrderService;
import com.sanmen.web.core.service.IService;

public class OrderJsonAction extends MobileBasicAction {

	private static final long serialVersionUID = 7157114233222993271L;

	@Autowired(required = true)
	@Qualifier("orderService")
	protected IOrderService service;

	@Override
	protected IService getService() {
		return service;
	}

}
