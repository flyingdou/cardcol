package com.freegym.web.mobile.action;

import java.util.Iterator;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.freegym.web.basic.Member;
import com.freegym.web.mobile.BasicJsonAction;

@Namespace("")
@InterceptorRefs({ @InterceptorRef("mobileStack") })
public class MTeamManageAction extends BasicJsonAction {

	private static final long serialVersionUID = -4900715126840183203L;

	private String keyword;

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		if (keyword != null && !"".equals(keyword)) keyword = urlDecode(keyword);
		this.keyword = keyword;
	}

	public void findClubs() {
		try {
			final DetachedCriteria dc = DetachedCriteria.forClass(Member.class);
			dc.add(Restrictions.eq("role", "E"));
			if (keyword != null && !"".equals(keyword)) {
				final String where = "%" + keyword + "%";
				this.or(Restrictions.like("name", where), Restrictions.like("nick", where), Restrictions.like("email", where),
						Restrictions.like("mobilephone", where));
			}
			pageInfo = service.findPageByCriteria(dc, pageInfo);
			final JSONArray jarr = new JSONArray();
			for (final Iterator<?> it = pageInfo.getItems().iterator(); it.hasNext();) {
				final Member m = (Member) it.next();
				final JSONObject obj = getMemberJson(m);
				obj.accumulate("tell", getString(m.getTell())).accumulate("address", getString(m.getAddress())).accumulate("lng", getDouble(m.getLongitude()))
						.accumulate("lat", getDouble(m.getLatitude()));
				jarr.add(obj);
			}
			final JSONObject ret = new JSONObject();
			ret.accumulate("success", true).accumulate("pageInfo", getJsonForPageInfo()).accumulate("items", jarr);
			response(ret);
		} catch (Exception e) {
			log.error("error", e);
			response(e);
		}
	}
}
