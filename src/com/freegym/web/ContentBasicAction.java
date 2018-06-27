package com.freegym.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.freegym.web.service.IContentService;
import com.sanmen.web.core.service.IService;

public abstract class ContentBasicAction extends BasicAction {

	private static final long serialVersionUID = -7725497941492357303L;

	@Autowired(required = true)
	@Qualifier("contentService")
	protected IContentService service;

	@Override
	protected IService getService() {
		return service;
	}

}
