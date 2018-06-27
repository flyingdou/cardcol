package com.freegym.web.order.action;

import net.sf.json.JSONObject;

import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.freegym.web.OrderBasicAction;
import com.freegym.web.order.AlwaysAddr;

@Namespace("")
@InterceptorRefs({ @InterceptorRef("foreStack") })
@Results({ @Result(name = "success", location = "/order/always_addr.jsp") })
public class AlwaysAddrManageAction extends OrderBasicAction {

	private static final long serialVersionUID = -3769475961884283745L;

	private AlwaysAddr alwaysAddr;

	public AlwaysAddr getAlwaysAddr() {
		return alwaysAddr;
	}

	public void setAlwaysAddr(AlwaysAddr alwaysAddr) {
		this.alwaysAddr = alwaysAddr;
	}

	public String save() {
		try {
			final JSONObject ret = new JSONObject();
			alwaysAddr.setMember(this.toMember());
			alwaysAddr = (AlwaysAddr) service.saveOrUpdate(alwaysAddr);
			ret.accumulate("success", true).accumulate("key", alwaysAddr.getId());
			response(ret);
		} catch (Exception e) {
			log.error("error", e);
			response(e);
		}
		return null;
	}

	public String delete() {
		try {
			final JSONObject ret = new JSONObject();
			final Long count = service.queryForLong("select count(*) from tb_product_order where alwaysAddr = ?", alwaysAddr.getId());
			if (count > 0) {
				ret.accumulate("success", false).accumulate("message", "reference");
				response(ret);
			} else {
				service.delete(AlwaysAddr.class, alwaysAddr.getId());
				ret.accumulate("success", true);
				response(ret);
			}
		} catch (Exception e) {
			log.error("error", e);
			response(e);
		}
		return null;
	}

	@Override
	protected String getExclude() {
		return "member";
	}
}
