package com.freegym.web.basic.action;

import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.freegym.web.BaseBasicAction;
import com.freegym.web.system.Area1;

@Namespace("")
@InterceptorRefs({ @InterceptorRef("customStack") })
@Results({ @Result(name = "success", location = "/home/city.jsp"), @Result(name = "index", type = "redirect", location = "index.asp") })
public class CityManageAction extends BaseBasicAction {

	private static final long serialVersionUID = -3769475961884283745L;

	/**
	 * 取得所有的热点并开通的城市及开通城市列表
	 */
	public String execute() {
		request.setAttribute("hotCitys", service.findHotCity());
		request.setAttribute("openCitys", service.findOpenCityForPinYin());
		request.setAttribute("provinces", service.getProvincesMap());
		return SUCCESS;
	}

	public String changeCity() {
		Area1 area = (Area1) service.load(Area1.class, id);
		session.setAttribute("currentCity", area.getName());
		return "index";
	}
}
