package com.freegym.web.mobile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.freegym.web.service.IWorkoutService;
import com.sanmen.web.core.service.IService;

public class CourseJsonAction extends MobileBasicAction {

	private static final long serialVersionUID = 7157114233222993271L;

	@Autowired(required = true)
	@Qualifier("workoutService")
	protected IWorkoutService service;

	@Override
	protected IService getService() {
		return service;
	}

}
