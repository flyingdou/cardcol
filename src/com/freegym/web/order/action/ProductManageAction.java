package com.freegym.web.order.action;

import java.io.File;
import java.util.ArrayList;
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
import com.freegym.web.config.CourseInfo;
import com.freegym.web.order.Order;
import com.freegym.web.order.Product;
import com.sanmen.web.core.common.LogicException;
import com.sanmen.web.core.utils.StringUtils;

@Namespace("")
@InterceptorRefs({ @InterceptorRef("foreStack") })
@Results({ @Result(name = "success", location = "/order/index.jsp"),
		@Result(name = "product_list", location = "/order/product_list.jsp"),
		@Result(name = "product_edit1", location = "/order/product_edit1.jsp"),
		@Result(name = "product_edit2", location = "/order/product_edit2.jsp"),
		@Result(name = "product_edit3", location = "/order/product_edit3.jsp"),
		@Result(name = "product_edit4", location = "/order/product_edit4.jsp"),
		@Result(name = "product_edit5", location = "/order/product_edit5.jsp"),
		@Result(name = "product_edit6", location = "/order/product_edit6.jsp"),
		@Result(name = "product_clause", location = "/order/product_clause.jsp"),
		@Result(name = "order_list", location = "/order/order_list.jsp") })
public class ProductManageAction extends OrderBasicAction {

	private static final long serialVersionUID = -3769475961884283745L;

	private Product product, query;

	private String isTop;// 1置顶：2取消置顶

	/**
	 * 进入类型
	 */
	private Integer type;
	private File image1, image2, image3;
	private String image1FileName, image2FileName, image3FileName;

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

	public String getIsTop() {
		return isTop;
	}

	public void setIsTop(String isTop) {
		this.isTop = isTop;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public File getImage1() {
		return image1;
	}

	public void setImage1(File image1) {
		this.image1 = image1;
	}

	public File getImage2() {
		return image2;
	}

	public void setImage2(File image2) {
		this.image2 = image2;
	}

	public File getImage3() {
		return image3;
	}

	public void setImage3(File image3) {
		this.image3 = image3;
	}

	public String getImage1FileName() {
		return image1FileName;
	}

	public void setImage1FileName(String image1FileName) {
		this.image1FileName = image1FileName;
	}

	public String getImage2FileName() {
		return image2FileName;
	}

	public void setImage2FileName(String image2FileName) {
		this.image2FileName = image2FileName;
	}

	public String getImage3FileName() {
		return image3FileName;
	}

	public void setImage3FileName(String image3FileName) {
		this.image3FileName = image3FileName;
	}

	public String execute() {
		session.setAttribute("spath", 5);
		if (type == null) {
			query("");
		} else {
			switch (type) {
			case 3:
				request.setAttribute("yyxms", service.findActionByMode(1l, '0'));
				request.setAttribute("csjgs", service.findObjectBySql("from Member m where m.role = ?", "I"));
				break;
			}
		}
		return SUCCESS;
	}

	public String query() {
		session.setAttribute("spath", 5);
		final Member m = this.toMember();
		if (query == null) {
			query = new Product();
			if (m.getRole().equals("M")) {
				final List<Object> params = new ArrayList<Object>();
				final StringBuffer sql = new StringBuffer();
				sql.append("SELECT * FROM (").append(Order.getOrderSql()).append(") t where 1 = 1 ");
				final Long mid = toMember().getId();
				sql.append("and fromId = ?");
				params.add(mid);
				sql.append(" order by orderDate desc");
				pageInfo.setPageSize(10);
				pageInfo = service.findPageBySql(sql.toString(), pageInfo, params.toArray());
				return "order_list";
			} else if (m.getRole().equals("E")) {
				query.setType("1");
			} else if (m.getRole().equals("S")) {
				query.setType("3");
			}
		}
		query.setMember(m);
		// 按置顶时间倒序
		pageInfo.setOrder("topTime");
		pageInfo.setOrderFlag("desc");
		pageInfo = service.findPageByCriteria(Product.getCriteriaQuery(query), this.pageInfo);
		return SUCCESS;
	}

	public String query(String bool) {
		session.setAttribute("spath", 5);
		final Member m = this.toMember();
		if (query == null) {
			query = new Product();
			if (m.getRole().equals("M")) {
				final List<Object> params = new ArrayList<Object>();
				final StringBuffer sql = new StringBuffer();
				sql.append("SELECT * FROM (").append(Order.getOrderSql()).append(") t where 1 = 1 ");
				final Long mid = toMember().getId();
				sql.append("and fromId = ?");
				params.add(mid);
				sql.append(" order by orderDate desc");
				pageInfo.setPageSize(10);
				pageInfo = service.findPageBySql(sql.toString(), pageInfo, params.toArray());
				return "order_list";
			} else if (m.getRole().equals("E")) {
				query.setType("1");
			} else if (m.getRole().equals("S")) {
				query.setType("3");
			}
		}
		query.setMember(m);
		// 按置顶时间倒序
		pageInfo.setOrder("topTime");
		pageInfo.setOrderFlag("desc");
		pageInfo = service.findPageByCriteria(Product.getCriteriaQuery(query), this.pageInfo);
		if ("^<v>^".equals(bool)) {
			return "product_list";
		} else {
			return SUCCESS;
		}
	}

	public String save() {
		// 新增时默认加入一些值
		if (product != null) {
			if (product.getId() == null) {
				Date date = new Date();
				product.setIsClose("2");
				product.setCreateTime(date);
				product.setTopTime(date);
			}
			// 上传图片信息
			String fileName1 = image1 != null ? saveFile("picture", image1, image1FileName, null) : null;
			String fileName2 = image2 != null ? saveFile("picture", image2, image2FileName, null) : null;
			String fileName3 = image3 != null ? saveFile("picture", image3, image3FileName, null) : null;
			if (fileName1 != null) {
				product.setImage1(fileName1);
			}
			if (fileName2 != null) {
				product.setImage2(fileName2);
			}
			if (fileName3 != null) {
				product.setImage3(fileName3);
			}
			product.setAssignType("2");
			if (product.getAssignCost() != null && product.getAssignCost() > 0d)
				product.setAssignType("1");
			Member m = this.toMember();
			m.setEndPublishTime(new Date());
			product.setMember((Member) service.saveOrUpdate(m));
			String msg = "";
			try {
				product.setAudit('1');// 每次修改都需要重新进行审核
				service.saveOrUpdate(product);
				msg = "健身卡发布成功!";
			} catch (LogicException e) {
				msg = e.getMessage();
				log.error("error", e);
			}
			response(msg);
		}
		return null;
	}

	// 实物商品/定制收费修改方法
	public String edit() {
		if (product != null && product.getId() != null) {
			product = (Product) service.load(Product.class, product.getId());
			response(getJsonString(product));
		}
		return null;
	}

	// 套餐修改方法
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String editProduct() {
		if (product != null && product.getId() != null) {
			product = (Product) service.load(Product.class, product.getId());
			if (product.getProType().equals("4")) {
				if (product.getCardType().equals("1"))
					product.setNum1(product.getNum());
				else
					product.setNum2(product.getNum());
			}
		} else {
			String proType = product.getProType();
			String type = product.getType();
			product = new Product();
			product.setProType(proType);
			product.setType(type);
			product.setAudit('1');
		}
		request.setAttribute("useRange1", StringUtils.stringToList(product.getUseRange(), ","));
		request.setAttribute("freeProjects1", StringUtils.stringToList(product.getFreeProjects(), ","));
		request.setAttribute("costProjects1", StringUtils.stringToList(product.getCostProjects(), ","));
		List<CourseInfo> projectList = (List<CourseInfo>) service
				.findObjectBySql("from CourseInfo c where c.member.id = ? ", this.toMember().getId());
		CourseInfo p = new CourseInfo();
		p.setId(0l);
		p.setName("其它：");
		projectList.add(p);
		request.setAttribute("projectList", projectList);
		List<Friend> friendList = (List<Friend>) service.findObjectBySql(
				"from Friend f where f.member.id = ? and f.member.role = ? and f.friend.role = ? and f.type = ? ",
				new Object[] { this.toMember().getId(), "E", "E", "1" });
		List<Member> clubList = new ArrayList();
		clubList.add(this.toMember());
		for (Friend f : friendList) {
			clubList.add(f.getFriend());
		}
		request.setAttribute("clubList", clubList);
		session.setAttribute("spath", 5);
		request.setAttribute("types", service.findParametersByCodes("card_type_c"));
		return "product_edit" + product.getProType();
	}

	public String clause() {
		return "product_clause";
	}

	public String delete() {
		String msg = "ok";
		if (product != null && product.getId() != null) {
			/*
			 * 加入购物车或订单时会copy商品 product = (Product) service.load(Product.class,
			 * product.getId()); if (product.getOrders() != null &&
			 * product.getOrders().size() > 0) { msg = "isFefByOrder"; } else {
			 * service.delete(product); return this.query(); }
			 */
			service.delete(product);
			return this.query("^<v>^");
		}
		response(msg);
		return null;

	}

	public String changeClose() {
		if (product != null && product.getId() != null) {
			String isClose = product.getIsClose();
			product = (Product) service.load(Product.class, product.getId());
			product.setIsClose(isClose);
			service.saveOrUpdate(product);
		}
		return query("^<v>^");
	}

	public String changeTopTime() {
		if (product != null && product.getId() != null) {
			product = (Product) service.load(Product.class, product.getId());
			if (isTop != null && isTop.equals("1")) {
				product.setTopTime(new Date());
			} else {
				product.setTopTime(null);
			}
			service.saveOrUpdate(product);
		}
		return query("^<v>^");
	}

	@Override
	protected String getExclude() {
		return "member,shops,orders";
	}

}
