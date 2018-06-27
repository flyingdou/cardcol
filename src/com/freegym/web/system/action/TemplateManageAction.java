package com.freegym.web.system.action;

import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.hibernate.criterion.DetachedCriteria;

import com.freegym.web.SystemBasicAction;
import com.freegym.web.system.Template;

@Namespace("")
@Results({ @Result(name = "success", location = "/manager/system/template.jsp") })
public class TemplateManageAction extends SystemBasicAction {

	private static final long serialVersionUID = -2297129011587931307L;

	private Template template, query;

	public Template getTemplate() {
		return template;
	}

	public void setTemplate(Template template) {
		this.template = template;
	}

	public Template getQuery() {
		return query;
	}

	public void setQuery(Template query) {
		this.query = query;
	}

	@Override
	protected void executeQuery() {
		DetachedCriteria dc = Template.getCriteriaQuery(query);
		pageInfo = service.findPageByCriteria(dc, pageInfo);
	}

	@Override
	protected Long executeSave() {
		if (template != null) {
			template = (Template) service.saveOrUpdate(template);
			return template.getId();
		}
		return null;
	}
}
