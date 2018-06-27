package com.freegym.web.basic.action;

import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.freegym.web.BaseBasicAction;
import com.freegym.web.basic.Member;

@Namespace("")
@InterceptorRefs({ @InterceptorRef("foreStack") })
@Results({ @Result(name = "success", location = "/basic/descr.jsp") })
public class DescriptionManageAction extends BaseBasicAction {

	private static final long serialVersionUID = 8942409430710869379L;

	private Member member;

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public String execute() {
		session.setAttribute("spath", 7);
		member = toMember();
		return SUCCESS;
	}

	public void save() {
		Member m = toMember();
		m.setDescription(member.getDescription());
		m = (Member) service.saveOrUpdate(m);
		session.setAttribute(LOGIN_MEMBER, m);
		StringBuffer sb = new StringBuffer("<script type='text/javascript'>");
		sb.append("alert('当前数据已经成功保存！');");
		sb.append("</script>");
		response(sb.toString());
	}
}
