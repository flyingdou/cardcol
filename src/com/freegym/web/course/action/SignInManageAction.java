package com.freegym.web.course.action;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.cardcol.web.order.ProductOrder45;
import com.freegym.web.OrderBasicAction;
import com.freegym.web.basic.Friend;
import com.freegym.web.basic.Member;
import com.freegym.web.course.SignIn;
import com.freegym.web.order.CourseOrder;
import com.freegym.web.order.Product;
import com.freegym.web.order.ProductOrder;
import com.freegym.web.utils.EasyUtils;

import net.sf.json.JSONArray;

@Namespace("")
@InterceptorRefs({ @InterceptorRef("foreStack") })
@Results({ @Result(name = "signin_list", location = "/course/signin_list.jsp"),
		@Result(name = "signin_edit", location = "/course/signin_edit.jsp"),
		@Result(name = "query_sign", location = "/course/query_sign.jsp") })
public class SignInManageAction extends OrderBasicAction {

	private static final long serialVersionUID = -3769475961884283745L;

	private SignIn signIn;

	private SignIn query;

	private String username;

	private String password;

	private ProductOrder queryOrder;

	private Member queryMember;

	private Integer type;

	public ProductOrder getQueryOrder() {
		return queryOrder;
	}

	public void setQueryOrder(ProductOrder queryOrder) {
		this.queryOrder = queryOrder;
	}

	public Member getQueryMember() {
		return queryMember;
	}

	public void setQueryMember(Member queryMember) {
		this.queryMember = queryMember;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public SignIn getSignIn() {
		return signIn;
	}

	public void setSignIn(SignIn signIn) {
		this.signIn = signIn;
	}

	public SignIn getQuery() {
		return query;
	}

	public void setQuery(SignIn query) {
		this.query = query;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	@SuppressWarnings("unchecked")
	public String query() {
		session.setAttribute("spath", 2);
		if (query == null) {
			query = new SignIn();
		}
		query.setMemberSign(this.toMember());
		query.setMemberAudit(this.toMember());
		pageInfo.setOrder("signDate");
		pageInfo.setOrderFlag("desc");
		pageInfo = service.findPageByCriteria(SignIn.getCriteriaQuery(query), this.pageInfo, 2);
		List<Object> items = pageInfo.getItems();
		List<Map<String, Object>> itemss = new ArrayList<>();
		for (Object object : items) {
			SignIn signIn = (SignIn) object;
			Map<String, Object> map = new HashMap<>();
			map.put("id", signIn.getId());
			map.put("memberSignId", signIn.getMemberSign().getId());
			map.put("memberSignName", signIn.getMemberSign().getName());
			map.put("memberAudit", signIn.getMemberAudit().getId());
			map.put("memberAuditName", signIn.getMemberAudit().getName());
			map.put("signType", signIn.getSignType());
			map.put("signDate", signIn.getSignDate());
			map.put("orderNo", signIn.getOrderNo());
			map.put("orderId", signIn.getOrderId());
			if ("5".equals(signIn.getSignType())) {
				CourseOrder courseOrder = (CourseOrder) service.load(CourseOrder.class, signIn.getOrderId());
				map.put("productName", courseOrder.getCourse().getCourseInfo().getName());
			} else if ("0".equals(signIn.getSignType())) {
				map.put("productName", "线下会员订单");
			} else {
				ProductOrder45 productOrder45 = (ProductOrder45) service.load(ProductOrder45.class,
						signIn.getOrderId());
				if (productOrder45 != null && productOrder45.getProduct() != null
						&& StringUtils.isNotEmpty(productOrder45.getProduct().getName())) {
					map.put("productName", productOrder45.getProduct().getName());
				} else {
					map.put("productName", "E卡通");
				}
			}
			itemss.add(map);
		}
		pageInfo.setItems(itemss);
		if ("M".equals(toMember().getRole())) {
			type = 0;
		} else {
			type = 1;
		}
		return "signin_list";
	}

	@SuppressWarnings("unchecked")
	public void listSignInAndClubName() {
		try {
			String orderNo = request.getParameter("orderNo");
			String memberSign = request.getParameter("memberSign");
			String querySql = "select s.signDate,m.name from tb_sign_in s inner join"
					+ " tb_member m on s.memberAudit = m.id where s.memberSign = ? and s.orderNo = ? order by s.signDate desc";
			List<Map<String, Object>> signList = service.findPageBySql(querySql, pageInfo, memberSign, orderNo)
					.getItems();
			EasyUtils.dateANDdecimalFormat(signList, "yyyy-MM-dd");
			response(JSONArray.fromObject(signList));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String edit() {
		return "signin_edit";
	}

	// 判断两者之间是否有关系，有关系返回关系字符串，无关系返回被访问关系对象toMember的角色
	@SuppressWarnings({ "unchecked", "unused" })
	private String getRelation(Member fromMember, Member toMember) {
		if (fromMember.getRole().equals("M")) {
			final List<Member> memList = (List<Member>) service.findObjectBySql(
					"from Member m where m.id=? and m.coach.id = ?",
					new Object[] { fromMember.getId(), toMember.getId() });
			if (memList.size() > 0)
				return "M:PS";
		}
		final List<Friend> memberList = (List<Friend>) service.findObjectBySql(
				"from Friend f where f.member.id = ? and f.friend.id = ? and f.type = ?",
				new Object[] { fromMember.getId(), toMember.getId(), "1" });
		if (memberList.size() > 0)
			return fromMember.getRole() + ":" + toMember.getRole();
		return toMember.getRole();
	}

	public String checkUserAndLoadOrder() {
		Member user = service.sign(username, password);
		if (user == null) {
			response("errorUserPassword");
			return null;
		}
		String msg = "";
		final List<ProductOrder> signOrderListF = service.findSignOrder(user.getId(), toMember().getId().toString(), 0);
		if (signOrderListF == null || signOrderListF.size() == 0) {
			msg = "noOrder";
		} else {
			msg = this.getJsonString(signOrderListF);
		}
		response(msg);
		return null;
	}

	public String save() {
		String msg = "";
		if (signIn != null) {
			try {
				service.saveOrUpdateSignIn(signIn);
				msg = "ok";
			} catch (Exception e) {
				msg = e.getMessage();
				log.error("error", e);
			}
			response(msg);
		}
		return null;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private String findMember() {
		List<Friend> friendList = (List<Friend>) service.findObjectBySql(
				"from Friend f where (f.member.id = ? or f.friend.id = ? ) and f.type = ?  ",
				new Object[] { this.toMember().getId(), this.toMember().getId(), "1" });
		List<Member> memberList = new ArrayList();
		StringBuffer sb = new StringBuffer("");
		Member m = this.toMember();
		for (Friend f : friendList) {
			if (f.getMember().getId().equals(m.getId())) {
				sb.append(f.getFriend().getId() + "," + f.getFriend().getName() + ":");
				memberList.add(f.getFriend());
			} else {
				sb.append(f.getMember().getId() + "," + f.getMember().getName() + ":");
				memberList.add(f.getMember());
			}
		}
		if (m.getRole().equals("M") && m.getCoach() != null) {
			sb.append(m.getCoach().getId() + "," + m.getCoach().getName() + ":");
			memberList.add(m.getCoach());
		}
		if (m.getRole().equals("S")) {
			List<Member> memList = (List<Member>) service.findObjectBySql("from Member m where m.coach.id = ?",
					this.toMember().getId());
			for (Member mem : memList) {
				sb.append(mem.getId() + "," + mem.getName() + ":");
			}
			memberList.addAll(memList);
		}
		// response(sb.length()>0?sb.toString().substring(0,sb.length()-1):"");
		request.setAttribute("memberList", memberList);
		return null;
	}

	@SuppressWarnings("rawtypes")
	public String querySign() {
		findMember();
		Member member = this.toMember();
		List<ProductOrder> orderList = service.findCurrentOrder(queryOrder, queryMember, member);
		List<String[]> signCountList = new ArrayList<String[]>();
		String[] signCountArr = null;
		for (ProductOrder order : orderList) {
			Product product = order.getProduct();
			Date orderStartTime = order.getOrderStartTime();
			// Date orderEndTime = order.getOrderEndTime();
			List<SignIn> signList = service.findSignListByOrderId(order.getId());
			Integer stepCount = 0, sumYCount = 0, sumSignCount = 0;
			String stepType = "M";
			if (signList.size() > 0) {
				Calendar calStart = Calendar.getInstance();
				calStart.setTime(orderStartTime);
				// Calendar calEnd = Calendar.getInstance();
				// calEnd.setTime(orderEndTime);
				SignIn si = null;
				Integer signCount = 0;
				String jName = order.getMember().getNick();
				String yNmae = product.getMember().getNick();
				String pName = order.getNo();// product.getName();
				Integer yCount = 0;
				if (product.getType().equals("1")) {
					if (product.getProType().equals("1")) {
						stepType = "W";
						stepCount = product.getWellNum();
						yCount = product.getNum();
					} else if (product.getProType().equals("2")) {
						stepType = "M";
						stepCount = product.getWellNum();
					} else if (product.getProType().equals("3")) {
						stepType = "M";
						stepCount = product.getWellNum();
					} else if (product.getProType().equals("4")) {
						stepType = "M";
						if (product.getCardType().equals("1")) {
							stepCount = product.getNum();
						} else {
							stepCount = product.getWellNum();
						}
					}
				} else if (product.getType().equals("2")) {

				} else if (product.getType().equals("3")) {
					if (product.getProType().equals("7")) {
						stepType = "M";
						stepCount = product.getWellNum();
					} else if (product.getProType().equals("8")) {
						stepType = "M";
						stepCount = product.getNum();
					}
				} else if (product.getType().equals("4")) {

				}
				for (int i = 0; i < stepCount; i++) {
					signCount = 0;
					if (stepType.equals("W")) {
						calStart.add(Calendar.WEEK_OF_YEAR, 1);
					} else if (stepType.equals("M")) {
						calStart.add(Calendar.MONTH, 1);
					}
					// calEnd =calStart.getInstance();
					for (Iterator it = signList.iterator(); it.hasNext();) {
						si = (SignIn) it.next();
						if (si.getSignDate().before(calStart.getTime())) {
							signCount++;
							it.remove();
						} else {
							break;
						}
					}
					signCountArr = new String[7];
					signCountArr[0] = jName;
					signCountArr[1] = yNmae;
					signCountArr[2] = pName;
					signCountArr[3] = "第" + (i + 1) + (stepType.equals("W") ? "周" : "月");
					signCountArr[4] = yCount == null || yCount == 0 ? "" : yCount.toString();
					signCountArr[5] = signCount.toString();
					signCountArr[6] = String.valueOf((yCount == null ? 0 : yCount) - signCount);
					signCountList.add(signCountArr);
					sumYCount += (yCount == null ? 0 : yCount);
					sumSignCount += signCount;
				}
				// 合计
				signCountArr = new String[7];
				signCountArr[0] = "合计";
				signCountArr[1] = "";
				signCountArr[2] = pName;
				signCountArr[3] = stepCount + (stepType.equals("W") ? "周" : "月");
				signCountArr[4] = sumYCount.toString();
				signCountArr[5] = signCount.toString();
				signCountArr[6] = String.valueOf(sumYCount - sumSignCount);
				signCountList.add(signCountArr);
			}
		}
		request.setAttribute("signCountList", signCountList);
		return "query_sign";
	}

	@Override
	protected String getExclude() {
		return "member,product,orderDetails,complaints,signIns,ticklings";
	}
}
