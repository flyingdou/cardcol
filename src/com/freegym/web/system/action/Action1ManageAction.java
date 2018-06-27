package com.freegym.web.system.action;

import java.util.Iterator;
import java.util.List;

import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.freegym.web.SystemBasicAction;
import com.freegym.web.config.Action;
import com.freegym.web.config.Part;
import com.freegym.web.config.Project;
import com.sanmen.web.core.utils.BaiduUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Namespace("")
@InterceptorRefs({ @InterceptorRef("backStack") })
@Results({ @Result(name = "success", location = "/manager/order/action.jsp") })
public class Action1ManageAction extends SystemBasicAction {

	private static final long serialVersionUID = -2297129011587931307L;

	private Action action, query;

	public Action getAction() {
		return action;
	}

	public void setAction(Action action) {
		this.action = action;
	}

	public Action getQuery() {
		return query;
	}

	public void setQuery(Action query) {
		this.query = query;
	}

	@Override
	protected void executeQuery() {
		final DetachedCriteria dc = Action.getCriteriaQuery(query);
		dc.add(Restrictions.eq("system", '1'));
		if (pageInfo.getOrder() == null) {
			pageInfo.setOrder("id");
			pageInfo.setOrderFlag("desc");
		}
		pageInfo = service.findPageByCriteria(dc, pageInfo, 2);
	}

	@Override
	protected Long executeSave() {
		if (action != null) {
			action.setSystem('1');
			action.setMember(1l);
			if (file != null) {
				if (file.getFile() != null) {
					try {
						String filePath = BaiduUtils.upload(BUCKET, FOLDER, getFileName(file.getFileFileName()), file.getFile());
						action.setVideo(filePath);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				if (file.getFiles() != null) {
					String imageName = this.saveFile("picture", file.getFiles()[0], file.getFilesFileName()[0], action.getImage());
					action.setImage("picture/" + imageName);
				}
			}
			action = (Action) service.saveOrUpdate(action);
			return action.getId();
		}
		return null;
	}

	public void loadPart() {
		final List<?> list = service.findProjectBySystem();
		final JSONArray jarr = new JSONArray();
		for (final Iterator<?> it = list.iterator(); it.hasNext();) {
			Project p = (Project) it.next();
			final JSONObject jobj = new JSONObject();
			jobj.accumulate("name", p.getName());
			jarr.add(jobj);
			for (Part part : p.getParts()) {
				if (part.getSystem() != null && part.getSystem() == '1') {
					final JSONObject obj = new JSONObject();
					obj.accumulate("id", part.getId()).accumulate("name", "　　" + part.getName());
					jarr.add(obj);
				}
			}
		}
		response(jarr);
	}

	@Override
	protected Class<?> getEntityClass() {
		return Action.class;
	}

	@Override
	protected String getExclude() {
		return "ticklings,workouts,actions,applies,parts,courseGrades,coach,coachMembers,friends,clubs,coachs,members,visitors,setting,times,products,certificates,integrals,courses,alwaysAddrs,applys,coursess,records,courseCoachs,courseMembers,prods,appraises,appraises1,allFriends,orderDetails,orderDetails1,messages,messages1,orders,addrs,signIns,signIns1,shops,accounts,prodOrders,aos,actives,partakes,teams,grades,tickets";
	}
}
