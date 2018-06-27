package com.freegym.web;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import net.sf.json.JSONObject;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.freegym.web.common.Constants;
import com.freegym.web.service.IBackService;
import com.freegym.web.utils.SessionConstant;
import com.freegym.web.utils.SmsUtils;
import com.sanmen.web.core.SystemJsonAction;
import com.sanmen.web.core.system.Role;

public class SystemBasicAction extends SystemJsonAction implements SessionConstant, Constants {

	private static final long serialVersionUID = 2684115129509217063L;

	@Autowired
	@Qualifier("backService")
	protected IBackService service;

	@Override
	protected IBackService getService() {
		return service;
	}

	protected boolean isAdmin() {
		final Role role = (Role) toRole();
		return role.getCode().equalsIgnoreCase("administrator");
	}

	protected void handlerPermission(final DetachedCriteria dc) {
		if (!isAdmin()) dc.add(Restrictions.eq("id", toUser().getId()));
	}

	@SuppressWarnings("unused")
	protected List<Integer> getAccessMembers() {
		final List<?> list = service.findObjectBySql("from UserMember um where um.user.id = ?", toUser().getId());
		final List<Integer> ids = new ArrayList<Integer>();
		for (final Iterator<?> it = list.iterator(); it.hasNext();) {

		}
		return null;
	}

	/**
	 * 发送短信验证码
	 * 
	 * @param content
	 * @return
	 */
	protected final String sendSms(final String mobile, final String content) {
		final String msg = SmsUtils.sendSms(mobile, content);
		return msg;
	}

	/**
	 * 置顶。主要将文章进行置顶
	 */
	public void stick() {
		try {
			final List<?> list = getService().load(getEntityClass(), ids);
			for (final Iterator<?> it = list.iterator(); it.hasNext();) {
				final Object obj = it.next();
				final Date d = '1' == getStick() ? new Date() : null;
				final Method method = obj.getClass().getMethod("setStickTime", Date.class);
				method.invoke(obj, d);
			}
			getService().saveOrUpdate(list);
			final JSONObject obj = new JSONObject();
			obj.accumulate("success", true).accumulate("message", "OK");
			response(obj);
		} catch (Exception e) {
			log.error("error", e);
			response(e);
		}
	}
}
