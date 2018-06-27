package com.freegym.web.home.action;

import java.util.List;

import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.freegym.web.BaseBasicAction;
import com.freegym.web.basic.Member;

@Namespace("")
@InterceptorRefs({ @InterceptorRef("customStack") })
@Results({ @Result(name = "success", location = "/home/map.jsp"), @Result(name = "middle", location = "/home/map_middle.jsp") })
public class MapManageAction extends BaseBasicAction {

	private static final long serialVersionUID = 8942409430710869379L;
	private String index;// 俱乐部0，教练1

	public String execute() {
		session.setAttribute("position", 5);
		final String city = (String) session.getAttribute("currentCity");
		StringBuffer sql = new StringBuffer(Member.getMemberSql(null, null, null, null, null, null, city));
		sql.append(" and m.longitude is not null and m.longitude > 0 and m.latitude is not null and m.latitude > 0 and m.grade = '1'");
		if ("1".equals(index)) {
			sql.append(" and m.role = 'S'");
		} else {
			sql.append(" and m.role = 'E'");
		}
		final List<?> list = service.queryForList(sql.toString());
		request.setAttribute("list", list);
		if (index != null) {
			return "middle";
		}
		return SUCCESS;
	}

	public String getIndex() {
		return index;
	}

	public void setIndex(String index) {
		this.index = index;
	}

}
