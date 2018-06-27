package com.freegym.web.system.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.freegym.web.SystemBasicAction;
import com.freegym.web.basic.Friend;
import com.freegym.web.basic.Member;
import com.freegym.web.config.Project;
import com.freegym.web.course.Message;
import com.freegym.web.order.Product;
import com.freegym.web.task.MessageThread;
import com.freegym.web.utils.EasyUtils;
import com.sanmen.web.core.content.ContentRecommend;
import com.sanmen.web.core.utils.StringUtils;

@Namespace("")
@Results({ @Result(name = "success", location = "/manager/order/product.jsp"),
		@Result(name = "e1", location = "/manager/order/e1.jsp"),
		@Result(name = "e2", location = "/manager/order/e2.jsp"),
		@Result(name = "e3", location = "/manager/order/e3.jsp"),
		@Result(name = "e4", location = "/manager/order/e4.jsp"),
		@Result(name = "e7", location = "/manager/order/s1.jsp"),
		@Result(name = "e5", location = "/manager/order/e5.jsp"),
		@Result(name = "e6", location = "/manager/order/e6.jsp"),
		@Result(name = "e8", location = "/manager/order/s2.jsp") })
public class Product1ManageAction extends SystemBasicAction {

	private static final long serialVersionUID = -2297129011587931307L;

	private Product product, query;

	private ContentRecommend sa;

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

	public ContentRecommend getSa() {
		return sa;
	}

	public void setSa(ContentRecommend sa) {
		this.sa = sa;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void executeQuery() {
		final DetachedCriteria dc = Product.getCriteriaQuery(query);
		loadPermission(dc);
		if (pageInfo.getOrder() == null) {
			pageInfo.setOrder("id");
			pageInfo.setOrderFlag("desc");
		}
		pageInfo = service.findPageByCriteria(dc, pageInfo);
		List<Product> items = pageInfo.getItems();
		List<Map<String, Object>> itemss = new ArrayList<Map<String, Object>>();
		Map<String, Object> item = null;
		Date createTime = null;
		for (Product product : items) {
			item = new HashMap<String, Object>();
			item.put("id", product.getId());
			item.put("no", product.getNo());
			item.put("name", product.getName());
			item.put("type", product.getType());
			item.put("proType", product.getProType());
			item.put("audit", product.getAudit());
			item.put("memberId", product.getMember().getId());
			item.put("role", product.getMember().getRole());
			createTime = product.getCreateTime() == null ? new Date() : product.getCreateTime();
			item.put("createTime", EasyUtils.dateFormat(createTime, "yyyy-MM-dd"));
			item.put("num", product.getNum());
			item.put("wellNum", product.getWellNum());
			item.put("memberNick", product.getMember().getName());
			itemss.add(item);
		}
		pageInfo.setItems(itemss);
	}

	@Override
	protected void loadAreaPerm(final DetachedCriteria dc, final String str) {
		dc.add(Restrictions.sqlRestriction(
				"this_.member in (select id from tb_member where city in (select name from tb_area where id in (" + str
						+ ")))"));
	}

	@Override
	protected Long executeSave() {
		if (product != null) {
			product = (Product) service.saveOrUpdate(product);
			return product.getId();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public String showDetail() {
		product = (Product) service.load(Product.class, id);
		if ("4".equals(product.getProType())) {
			if ("1".equals(product.getCardType())) {
				product.setNum1(product.getNum());
			} else {
				product.setNum2(product.getNum());
			}
		}
		request.setAttribute("useRange1", StringUtils.stringToList(product.getUseRange(), ","));
		request.setAttribute("freeProjects1", StringUtils.stringToList(product.getFreeProjects(), ","));
		request.setAttribute("costProjects1", StringUtils.stringToList(product.getCostProjects(), ","));
		final Long memberId = product.getMember().getId();
		final List<Project> projectList = (List<Project>) service
				.findObjectBySql("from CourseInfo c where c.member.id = ? ", memberId);
		final Project p = new Project();
		p.setId(0l);
		p.setName("其它：");
		projectList.add(p);
		request.setAttribute("projectList", projectList);
		final List<Friend> friendList = (List<Friend>) service.findObjectBySql(
				"from Friend f where f.member.id = ? and f.member.role = ? and f.friend.role = ? and f.type = ? ",
				new Object[] { memberId, "E", "E", "1" });
		final List<Member> clubList = new ArrayList<Member>();
		for (final Friend f : friendList) {
			clubList.add(f.getFriend());
		}
		request.setAttribute("clubList", clubList);

		// final String role = product.getMember().getRole();
		final String forward = "e" + (product.getProType() == null ? "1" : product.getProType());
		return forward;
	}

	protected void afterAudit(List<?> list) {
		final List<Message> messages = new ArrayList<Message>();
		for (final Iterator<?> it = list.iterator(); it.hasNext();) {
			final Product p = (Product) it.next();
			// 健身卡审核通过后 推送消息给发布者
			String uid = p.getMember().getUserId();
			Long cid = p.getMember().getChannelId();
			Integer termType = p.getMember().getTermType();
			new MessageThread(uid, cid, termType, "您发布的[" + p.getName() + "]已经通过审核并正式发布。");

			final Message msgs = new Message();
			msgs.setContent("您发布的[" + p.getName() + "]已经通过审核并正式发布。");
			msgs.setIsRead("0");
			msgs.setMemberFrom(new Member(1l));
			msgs.setMemberTo(p.getMember());
			msgs.setSendTime(new Date());
			msgs.setStatus("0");
			msgs.setType("3");
			messages.add(msgs);
		}
		service.saveOrUpdate(messages);
		response("{success: true, desc: 'OK'}");
	}

	public void recommend() {
		try {
			sa.setIcon(saveFile("picture", sa.getIcon()));
			sa.setRecommType(PRODUCT_TYPE_CARD);
			Date nowDate = new Date();
			sa.setRecommDate(nowDate);
			sa.setStickTime(nowDate);
			service.saveOrUpdate(sa);
			response(true, "message: 'ok'");
		} catch (Exception e) {
			response(e);
		}
	}

	@Override
	protected Class<?> getEntityClass() {
		return Product.class;
	}

	@Override
	protected String getExclude() {
		return "ticklings,judges, courseGrades,coach,coachMembers,friends,clubs,coachs,members,visitors,setting,times,products,certificates,integrals,courses,alwaysAddrs,applys,coursess,records,courseCoachs,courseMembers,prods,appraises,appraises1,allFriends,orderDetails,orderDetails1,messages,messages1,orders,addrs,signIns,signIns1,shops,accounts,prodOrders,aos,actives,partakes,teams,tickets";
	}
}
