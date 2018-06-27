package com.freegym.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.freegym.web.service.IContentService;
import com.sanmen.web.core.service.IService;

public class ContentJsonAction extends BasicJsonAction {

	/** 
	* @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么) 
	*/ 
	private static final long serialVersionUID = -5330130866829357011L;

	@Autowired(required = true)
	@Qualifier("contentService")
	protected IContentService service;
	
	@Override
	protected IService getService() {
		return service;
	}

}
