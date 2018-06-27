package com.freegym.web.wap.action;

import java.util.List;

import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;

import com.freegym.web.mobile.action.MFactoryManageAction;
import com.sanmen.web.core.bean.BaseMember;

import net.sf.json.JSONObject;

@Namespace("")
@InterceptorRefs({ @InterceptorRef("customStack") })
public class WFactoryManageAction extends MFactoryManageAction {

	private static final long serialVersionUID = -3731508241757525048L;

	private Long id;

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	@Override
	protected BaseMember getLoginMember() {
		// return (BaseMember) getService().load(Member.class, 490l);
		return toMember();
	}

	/**
	 * 会员校验
	 */
	public void checkMember() {
		try {

			BaseMember mu = getLoginMember();
			JSONObject obj = new JSONObject();
			final List<?> list = service.findObjectBySql("from Friend fr where fr.friend.id = ? and fr.member.id = ? and fr.type = ? ", new Object[] { id, mu.getId(), "1" });
			if (list.size() > 0) {
				obj.accumulate("success", true).accumulate("isMember", "Y");
			} else {
				obj.accumulate("success", true).accumulate("isMember", "N");
			}
			response(obj);
		} catch (Exception e) {

			log.error("error", e);
			response(e);
		}

	}

}
