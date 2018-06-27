package com.freegym.web.system.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.hibernate.criterion.DetachedCriteria;

import com.freegym.web.SystemBasicAction;
import com.freegym.web.system.UserMember;
import com.sanmen.web.core.common.JsonData;
import com.sanmen.web.core.common.MD5;
import com.sanmen.web.core.system.User;

import net.sf.json.JSONObject;

@Namespace("")
@Results({ @Result(name = "success", location = "/manager/system/user.jsp") })
public class User1ManageAction extends SystemBasicAction {

	private static final long serialVersionUID = -2297129011587931307L;

	private User user, query;

	private String password1;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public User getQuery() {
		return query;
	}

	public void setQuery(User query) {
		this.query = query;
	}

	public String getPassword1() {
		return password1;
	}

	public void setPassword1(String password1) {
		this.password1 = password1;
	}

	@Override
	protected void executeQuery() {
		final DetachedCriteria dc = User.getCriteriaQuery(query);
		handlerPermission(dc);
		if (pageInfo.getOrder() == null) {
			pageInfo.setOrder("id");
			pageInfo.setOrderFlag("desc");
		}
		pageInfo = service.findPageByCriteria(dc, pageInfo, 1);
	}

	@Override
	protected Long executeSave() {
		if (user != null) {
			if (password1 != null && !"".equals(password1)) user.setPassword(MD5.MD5Encode(user.getCode() + password1));
			user = (User) service.saveOrUpdate(user);
			if (jsons != null && !"".equals(jsons)) {
				final Map<String, Class<?>> classMap = new HashMap<String, Class<?>>();
				classMap.put("data", UserMember.class);
				final JsonData jd = (JsonData) JSONObject.toBean(JSONObject.fromObject(jsons), JsonData.class, classMap);
				final List<UserMember> ums = new ArrayList<UserMember>();
				for (final Iterator<?> it = jd.getData().iterator(); it.hasNext();) {
					final UserMember um = (UserMember) it.next();
					um.setUser(user);
					ums.add(um);
				}
				service.saveOrUpdate(ums);
			}
			return user.getId();
		}
		return null;
	}

	@Override
	protected List<?> findDetail() {
		return service.findObjectBySql("from UserMember um where um.user.id = ?", id);
	}

	@Override
	protected Class<?> getEntityClass() {
		return User.class;
	}

	@Override
	protected String getExclude() {
		return "ticklings,judges,role,coach,coachMembers,friends,clubs,coachs,members,visitors,setting,times,targets,products,certificates,courses,alwaysAddrs,applys,coursess,records,prods,appraises,appraises1,allFriends,orderDetails,orderDetails1,messages,messages1,orders,addrs,signIns,signIns1,shops,accounts,prodOrders,courseCoachs,courseMembers,tickets";
	}
}
