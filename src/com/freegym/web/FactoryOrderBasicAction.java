package com.freegym.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;


import com.freegym.web.service.IFactoryOrderService;
import com.sanmen.web.core.service.IService;

public abstract class FactoryOrderBasicAction extends BasicAction {

	private static final long serialVersionUID = -7725497941492357303L;

	@Autowired(required = true)
	@Qualifier("factoryOrderService")
	protected IFactoryOrderService service;

	@Override
	protected IService getService() {
		return service;
	}

}
