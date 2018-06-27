package com.freegym.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.freegym.web.service.IBasicService;
import com.freegym.web.utils.IpUtils;
import com.freegym.web.utils.SessionConstant;
import com.sanmen.web.core.content.Config;
import com.sanmen.web.core.integrate.ServiceLocator;

public class GlobalFilter implements Filter, SessionConstant {

	final protected Logger log = Logger.getLogger(this.getClass());
	
	public void destroy() {

	}

	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
		setDefaultEnter(req);
		try {
			chain.doFilter(req, resp);
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		} catch (ServletException e) {
			e.printStackTrace();
			throw e;
		}
		setDefaultEnter(req);
	}

	private void setDefaultEnter(ServletRequest req) {
		if (req instanceof HttpServletRequest) {
			HttpServletRequest request = (HttpServletRequest) req;
			HttpSession session = request.getSession();
			IBasicService service = null;
			if (session.getAttribute("currentCity") == null) {
				try {
					String city = IpUtils.getCardcolCity(request);
					service = (IBasicService) ServiceLocator.getInstance().getService("basicService");
					session.setAttribute("currentCity", service.getCurrentCity(city));
				} catch (Exception e) {
					session.setAttribute("currentCity", "武汉市");
				}
			}
			if (session.getAttribute("systemConfig") == null) {
				if (service == null)
					service = (IBasicService) ServiceLocator.getInstance().getService("basicService");
				try {
					session.setAttribute("systemConfig", service.load(Config.class, 1l));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void init(FilterConfig arg0) throws ServletException {

	}

}
