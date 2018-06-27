package com.freegym.web.order.action;

import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.freegym.web.OrderBasicAction;
import com.freegym.web.active.Active;
import com.freegym.web.basic.Member;
import com.freegym.web.order.Product;

@Namespace("")
@InterceptorRefs({ @InterceptorRef("customStack") })
@Results({ @Result(name = "success", location = "/homeWindow/product.jsp"), @Result(name = "product_list", location = "/homeWindow/product_list.jsp"),
		@Result(name = "active_partake", location = "/active/active_partake.jsp"), @Result(name = "active_list", location = "/active/active_start_list.jsp") })
public class ProductWindowManageAction extends OrderBasicAction {

	private static final long serialVersionUID = -3769475961884283745L;

	/**
	 * active进入类型
	 */
	private String type;

	private Active active;

	private Product product;

	private Product query;

	private Member member;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Active getActive() {
		return active;
	}

	public void setActive(Active active) {
		this.active = active;
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Product getQuery() {
		return query;
	}

	public void setQuery(Product query) {
		this.query = query;
	}

	public String execute() {
		if (member != null && member.getId() != null) {
			member = (Member) service.load(Member.class, member.getId());
			session.setAttribute("toMember", member);
		}
		session.setAttribute("wpath", 4);
		query();
		return SUCCESS;
	}

	public String query() {
		Member m = (Member) session.getAttribute("toMember");
		if (query == null) {
			query = new Product();
			query.setIsClose("2");
			if (m.getRole().equals("M")) {

			} else if (m.getRole().equals("E")) {
				query.setType("1");
			} else if (m.getRole().equals("S")) {
				query.setType("3");
			}
		}
		query.setIsClose("2");
		query.setAudit('1');
		query.setMember(m);
		// 按置顶时间倒序
		pageInfo.setOrder("topTime");
		pageInfo.setOrderFlag("desc");
		pageInfo = service.findPageByCriteria(Product.getCriteriaQuery(query), this.pageInfo);
		return "product_list";
	}

	public String active() {
		if (active != null && active.getId() != null)
			active = (Active) service.load(Active.class, active.getId());
		return type;
	}
}
