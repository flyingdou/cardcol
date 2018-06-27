package com.freegym.web.system.action;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.freegym.web.system.Area1;
import com.sanmen.web.core.SystemJsonAction;
import com.sanmen.web.core.common.Node;
import com.sanmen.web.core.common.ParamBean;

@Namespace(value = "")
@Results({ @Result(name = "success", location = "/manager/system/area.jsp") })
public class Area1ManageAction extends SystemJsonAction {

	private static final long serialVersionUID = 9107610847716595429L;

	private Area1 area, query;

	private Long parent, node;

	public Area1 getArea() {
		return area;
	}

	public void setArea(Area1 area) {
		this.area = area;
	}

	public Area1 getQuery() {
		return query;
	}

	public void setQuery(Area1 query) {
		this.query = query;
	}

	public Long getParent() {
		return parent;
	}

	public void setParent(Long parent) {
		this.parent = parent;
	}

	public Long getNode() {
		return node;
	}

	public void setNode(Long node) {
		this.node = node;
		if (node != null) this.id = node;
	}

	@Override
	public String execute() {
		return SUCCESS;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.dgqc.web.oa.BasicJsonAction#load()
	 */
	public List<Node> findNode() {
		final List<Node> nodes = new ArrayList<Node>();
		List<?> list = null;
		if (id == null || id <= 0) list = service.findObjectBySql("from Area1 a where a.parent is null or a.parent = ''");
		else list = service.findObjectBySql("from Area1 a where a.parent = ?", id);
		for (Iterator<?> it = list.iterator(); it.hasNext();) {
			final Area1 area = (Area1) it.next();
			final Node node = new Node(area.getId(), String.valueOf(area.getId()), area.getName(), area.getChilds().size() <= 0);
			nodes.add(node);
		}
		return nodes;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sanmentyre.sell.AbstractJsonAction#executeQuery()
	 */
	@Override
	protected void executeQuery() {
		pageInfo = service.findPageByCriteria(Area1.getCriteriaQuery(query), pageInfo);
	}
	
	@Override
	public String edit(){
		try {
			area = (Area1) service.load(Area1.class, id);
			response("{items: " + getJsonString(area) + "}");
		} catch (Exception e) {
			log.error("error", e);
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sanmentyre.sell.AbstractJsonAction#getEntityClass()
	 */
	@Override
	protected Class<?> getEntityClass() {
		return Area1.class;
	}

	/**
	 * 类型下拉框
	 */
	public void findType() {
		String jsons = null;
		try {
			List<ParamBean> params = new ArrayList<ParamBean>();
			params.add(new ParamBean(0, "省"));
			params.add(new ParamBean(1, "市"));
			params.add(new ParamBean(2, "县区"));
			jsons = getJsonString(params);
		} catch (Exception e) {
			log.error("error", e);
		} finally {
			response(jsons);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sanmentyre.sell.AbstractJsonAction#saveData()
	 */
	@Override
	public String save() {
		if (area.getParent() != null && area.getParent() <= 0) area.setParent(null);
		area = (Area1) service.saveOrUpdate(area);
		response("{success: true, items:{id: " + area.getId() + ",text: '" + area.getName() + "',leaf: true,no: '" + area.getId() + "'}}");
		return null;
	}

	public void move() {
		area = (Area1) service.load(Area1.class, id);
		area.setParent(parent);
		service.saveOrUpdate(area);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sanmentyre.sell.AbstractJsonAction#getExclude()
	 */
	@Override
	protected String getExclude() {
		return "";
	}
}
