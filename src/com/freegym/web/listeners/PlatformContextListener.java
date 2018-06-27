package com.freegym.web.listeners;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.lang3.StringEscapeUtils;

import com.freegym.web.service.IBasicService;
import com.sanmen.web.core.common.WebUtils;
import com.sanmen.web.core.integrate.ServiceLocator;
import com.sanmen.web.core.listeners.PlatformUser;

public class PlatformContextListener implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {

	}

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		WebUtils.setRealContextPath(sce.getServletContext().getRealPath("/"));
		final ServletContext sc = sce.getServletContext();
		final IBasicService service = (IBasicService) ServiceLocator.getInstance().getService("basicService");
		sc.setAttribute("provinces", service.getProvincesMap());
		sc.setAttribute("platformuser", new PlatformUser());
	}

	public static void main(String[] args) {
		String text = "\\u6211\\u4eec\\u7684\\u660e\\u5929 \\u4e3a\\u6211\\u4eec\\u7684\\u672a\\u6765\\uff0c\\u4e5f\\u8bb8\\u662f\\u6211\\u592a\\u81ea\\u79c1";
		String result = StringEscapeUtils.unescapeJava(text);
		System.out.println(result);
		text = StringEscapeUtils.escapeJava(result);
		System.out.println(text);

	}

}
