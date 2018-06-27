package test.com.freegym.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;

import com.freegym.web.service.IWorkoutService;

@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class ServiceImplTest {

	@Autowired
	@Qualifier("workoutService")
	private IWorkoutService service;
	
}
