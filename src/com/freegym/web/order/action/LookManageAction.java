package com.freegym.web.order.action;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.freegym.web.OrderBasicAction;
import com.freegym.web.basic.Friend;
import com.freegym.web.basic.Member;
import com.freegym.web.config.Project;
import com.freegym.web.order.Product;
import com.freegym.web.order.Shop;

@Namespace("")
@InterceptorRefs({ @InterceptorRef("customStack") })
@Results({ @Result(name = "success", location = "/homeWindow/product.jsp") })
public class LookManageAction extends OrderBasicAction {

	private static final long serialVersionUID = -3769475961884283745L;

	private Long pId;

	private Shop shop;

	private Product product;

	private String goType;// goType == 1,订单里面进入查看

	public Long getPId() {
		return pId;
	}

	public void setPId(Long pId) {
		this.pId = pId;
	}

	public Shop getShop() {
		return shop;
	}

	public void setShop(Shop shop) {
		this.shop = shop;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public String getGoType() {
		return goType;
	}

	public void setGoType(String goType) {
		this.goType = goType;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String execute() {
		shop = new Shop();
		shop.setMember(toMember());
		shop.setOrderStartTime(new Date());
		if (pId != null) {
			product = (Product) service.load(Product.class, pId);
			if (product.getProType().equals("5")) {
				final Calendar c = Calendar.getInstance();
				c.add(Calendar.MONTH, product.getWellNum());
				shop.setOrderEndTime(c.getTime());
			}
			List<Project> projectList = (List<Project>) service.findObjectBySql("from CourseInfo c where c.member.id = ? ", product.getMember().getId());
			Project p = new Project();
			p.setId(null);
			p.setName("其它：");
			projectList.add(p);
			request.setAttribute("projectList", projectList);
			List<Friend> friendList = (List<Friend>) service.findObjectBySql(
					"from Friend f where f.member.id = ? and f.member.role = ? and f.friend.role = ? and f.type = ? ",
					new Object[] { product.getMember().getId(), "E", "E", "1" });
			List<Member> clubList = new ArrayList();
			for (Friend f : friendList) {
				clubList.add(f.getFriend());
			}
			request.setAttribute("clubList", clubList);
			session.setAttribute("toMember", product.getMember());
			if (goType != null && goType.equals("1")) {
				return "shop_edit" + product.getProType();
			}
		}
		session.setAttribute("wpath", 4);
		return SUCCESS;
	}
}
