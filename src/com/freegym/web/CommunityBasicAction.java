package com.freegym.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.freegym.web.service.ICommunityService;
import com.sanmen.web.core.service.IService;

public abstract class CommunityBasicAction extends BasicAction {

	private static final long serialVersionUID = -7725497941492357303L;

	@Autowired(required = true)
	@Qualifier("communityService")
	protected ICommunityService service;

	@Override
	protected IService getService() {
		return service;
	}

}
