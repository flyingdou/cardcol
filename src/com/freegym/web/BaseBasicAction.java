package com.freegym.web;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.freegym.web.service.IBasicService;
import com.sanmen.web.core.service.IService;

@Results({@Result(name="lefttop", location="/share/leftTop.jsp")})
public class BaseBasicAction extends BasicAction {

	private static final long serialVersionUID = 7078421117868710206L;

	@Autowired(required = true)
	@Qualifier("basicService")
	protected IBasicService service;
	
	@Override
	protected IService getService() {
		return service;
	}

	public static void main(String[] args) {
		
		final String str = "%5B%7BdoneDate%3A+%272014-04-17%27%2Caction%3A+%27%E7%99%BB%E6%A5%BC%E6%A2%AF%27%2C+weight%3A+%2760%27%2C+times%3A+%2745%27%2C+activeId%3A+%27145%27%7D%5D";
		System.out.println(str);
		try {
			System.out.println(URLEncoder.encode(str, "UTF-8"));
			System.out.println(URLDecoder.decode(str, "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	
	public String initLeftTopList(){
		//健身计划
		request.setAttribute("lt_planTypeList", service.findObjectBySql(
			" from Parameter p where p.parent = (select po.id from Parameter po where po.code = ? ) order by p.id asc", "plan_type_c"));
		//健身卡
		request.setAttribute("lt_cardtypeList", service.findObjectBySql(
			" from Parameter p where p.parent = (select po.id from Parameter po where po.code = ? ) order by p.id asc", "card_type_c"));
		//场馆预订
		request.setAttribute("lt_coursetypeList", service.findObjectBySql(
				" from Parameter p where p.parent = (select po.id from Parameter po where po.code = ? ) and p.viewType = '1' order by p.code asc", "course_type_c"));
		//健身教练
		request.setAttribute("lt_coachtypeList", service.findObjectBySql(
				   " from Parameter p where p.parent = (select po.id from Parameter po where po.code = ? ) order by p.id asc", "coach_type_c1"));
		return "lefttop";
	}
	
}
