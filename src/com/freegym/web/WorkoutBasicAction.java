package com.freegym.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.freegym.web.service.IWorkoutService;
import com.sanmen.web.core.service.IService;

public abstract class WorkoutBasicAction extends BasicAction {

	private static final long serialVersionUID = -7725497941492357303L;

	@Autowired(required = true)
	@Qualifier("workoutService")
	protected IWorkoutService service;

	@Override
	protected IService getService() {
		return service;
	}

}
