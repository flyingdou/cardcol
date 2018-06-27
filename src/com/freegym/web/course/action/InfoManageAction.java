package com.freegym.web.course.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.alibaba.fastjson.JSON;
import com.freegym.web.CourseBasicAction;
import com.freegym.web.basic.Friend;
import com.freegym.web.basic.Member;

import net.sf.json.JSONArray;

@Namespace("")
@InterceptorRefs({ @InterceptorRef("foreStack") })
@Results({ @Result(name = "success", location = "/message/index.jsp"),
		@Result(name = "friend", location = "/message/friend.jsp") })
public class InfoManageAction extends CourseBasicAction {

	/**
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	 */
	private static final long serialVersionUID = -2007963300848428684L;
	private String type;// 1:我的私教,2:我的俱乐部,3:我的教练,4:我的会员
	private Friend friend;

	public Friend getFriend() {
		return friend;
	}

	public void setFriend(Friend friend) {
		this.friend = friend;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String execute() {
		session.setAttribute("spath", 6);
		return SUCCESS;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String findFriend() {
		// member = (Member) service.load(Member.class,this.toMember().getId());
		pageInfo.setPageSize(5);
		int start = (pageInfo.getCurrentPage() - 1) * pageInfo.getPageSize();
		int end = pageInfo.getCurrentPage() * pageInfo.getPageSize();
		pageInfo.setStart(start);
		pageInfo.setEnd(end);
		int count = 0;
		List resultList = new ArrayList();
		if (type != null && !"".equals(type)) {
			Member member = this.toMember();
			if (type.equals("1")) {
				request.setAttribute("coach", member.getCoach());
			} else if (type.equals("2")) {
				if (member.getRole().equals("M")) {
					List<?> list = service.findObjectBySql(
							"from Friend f where f.member.id = ? and f.friend.role = ? and f.type = ? order by topTime desc,joinTime desc ",
							new Object[] { member.getId(), "E", "1" });
					count = list.size();
					// request.setAttribute("clubs", service.findObjectBySql(
					// "from Friend f where f.member.id = ? and f.friend.role = ? and f.type = ?
					// order by topTime desc,joinTime desc limit ?,?",
					// new Object[] { member.getId(), "E", "1", pageInfo.getStart(),
					// pageInfo.getEnd() }));
					for (int i = pageInfo.getStart(); i < pageInfo.getEnd(); i++) {
						if (i > list.size() - 1) {
							continue;
						}
						resultList.add(list.get(i));
					}
					request.setAttribute("clubs", resultList);
				} else if (member.getRole().equals("E")) {
					List<?> list = service.findObjectBySql(
							"from Friend f where f.member.id = ? and f.member.role = ? and f.friend.role = ? and f.type = ? order by topTime desc,joinTime desc",
							new Object[] { member.getId(), "E", "E", "1" });
					count = list.size();
					// request.setAttribute("clubs", service.findObjectBySql(
					// "from Friend f where f.member.id = ? and f.member.role = ? and f.friend.role
					// = ? and f.type = ? order by topTime desc,joinTime desc limit ?,?",
					// new Object[] { member.getId(), "E", "E", "1", pageInfo.getStart(),
					// pageInfo.getEnd() }));
					for (int i = pageInfo.getStart(); i < pageInfo.getEnd(); i++) {
						if (i > list.size() - 1) {
							continue;
						}
						resultList.add(list.get(i));
					}
					request.setAttribute("clubs", resultList);
				} else if (member.getRole().equals("S")) {
					List<?> list = service.findObjectBySql(
							"from Friend f where f.member.id = ? and f.friend.role = ?  and f.type = ? order by topTime desc,joinTime desc ",
							new Object[] { member.getId(), "E", "1" });
					count = list.size();
					// request.setAttribute("clubs", service.findObjectBySql(
					// "from Friend f where f.member.id = ? and f.friend.role = ? and f.type = ?
					// order by topTime desc,joinTime desc limit ?,?",
					// new Object[] { member.getId(), "E", "1", pageInfo.getStart(),
					// pageInfo.getEnd() }));
					for (int i = pageInfo.getStart(); i < pageInfo.getEnd(); i++) {
						if (i > list.size() - 1) {
							continue;
						}
						resultList.add(list.get(i));
					}
					request.setAttribute("clubs", resultList);
				}
			} else if (type.equals("3")) {
				if (member.getRole().equals("M")) {
					List<?> list = service.findObjectBySql(
							"from Friend f where f.member.id = ? and f.friend.role = ? and f.type = ? order by topTime desc,joinTime desc ",
							new Object[] { member.getId(), "S", "1" });
					count = list.size();
					// request.setAttribute("coachs", service.findObjectBySql(
					// "from Friend f where f.member.id = ? and f.friend.role = ? and f.type = ?
					// order by topTime desc,joinTime desc limit ?,?",
					// new Object[] { member.getId(), "S", "1", pageInfo.getStart(),
					// pageInfo.getEnd() }));
					for (int i = pageInfo.getStart(); i < pageInfo.getEnd(); i++) {
						if (i > list.size() - 1) {
							continue;
						}
						resultList.add(list.get(i));
					}
					request.setAttribute("coachs", resultList);
				} else if (member.getRole().equals("E")) {
					List<?> list = service.findObjectBySql(
							"from Friend f where f.friend.id = ? and f.member.role = ? and f.type = ? order by topTime desc,joinTime desc ",
							new Object[] { member.getId(), "S", "1" });
					count = list.size();
					// request.setAttribute("coachs", service.findObjectBySql(
					// "from Friend f where f.friend.id = ? and f.member.role = ? and f.type = ?
					// order by topTime desc,joinTime desc limit ?,?",
					// new Object[] { member.getId(), "S", "1", pageInfo.getStart(),
					// pageInfo.getEnd() }));
					for (int i = pageInfo.getStart(); i < pageInfo.getEnd(); i++) {
						if (i > list.size() - 1) {
							continue;
						}
						resultList.add(list.get(i));
					}
					request.setAttribute("coachs", resultList);
				}
			} else if (type.equals("4")) {
				if (member.getRole().equals("E")) {
					List<?> list = service.findObjectBySql(
							"from Friend f where f.friend.id = ? and f.member.role = ? and f.type = ? order by joinTime desc,topTime desc ",
							new Object[] { member.getId(), "M", "1" });
					count = list.size();
					// request.setAttribute("members", service.findObjectBySql(
					// "from Friend f where f.friend.id = ? and f.member.role = ? and f.type = ?
					// order by joinTime desc,topTime desc limit ?,?",
					// new Object[] { member.getId(), "M", "1", pageInfo.getStart(),
					// pageInfo.getEnd() }));
					for (int i = pageInfo.getStart(); i < pageInfo.getEnd(); i++) {
						if (i > list.size() - 1) {
							continue;
						}
						resultList.add(list.get(i));
					}
					request.setAttribute("members", resultList);
				} else if (member.getRole().equals("S")) {
					// request.setAttribute(
					// "members",
					// service.findObjectBySql("from Friend f where f.friend.id = ? and
					// f.member.role = ? and f.type = ? order by joinTime desc,topTime desc ", new
					// Object[] {
					// member.getId(), "M", "1" }));
					// 私教会员
					request.setAttribute("memberList",
							service.findObjectBySql("from Member m where m.coach.id = ?", member.getId()));
				}
			}
		}

		// 添加分页信息
		pageInfo.setTotalCount(count);
		int totalPage = count % pageInfo.getPageSize() == 0 ? count / pageInfo.getPageSize()
				: (count / pageInfo.getPageSize()) + 1;
		pageInfo.setTotalPage(totalPage);
		return "friend";
	}

	public String saveRelieve() {
		service.saveRelieve(type, this.toMember(), friend);
		Member mm = (Member) service.load(Member.class, this.toMember().getId());
		session.setAttribute(LOGIN_MEMBER, mm);
		return findFriend();
	}

	public String saveRelieveAll() {
		String ids = request.getParameter("ids");
		JSONArray array = JSONArray.fromObject(ids);
		for (Object object : array) {
			friend = (Friend) service.load(Friend.class, Long.valueOf(String.valueOf(object)));
			service.saveRelieve(type, this.toMember(), friend);
		}
		Member mm = (Member) service.load(Member.class, this.toMember().getId());
		session.setAttribute(LOGIN_MEMBER, mm);
		return findFriend();
	}

	public String saveChangeIsCore() {
		if (friend != null) {
			service.saveChangeIsCore(friend);
		}
		return findFriend();
	}

	public String changeTopTime() {
		if (friend != null && friend.getId() != null) {
			Date topTime = friend.getTopTime();
			friend = (Friend) service.load(Friend.class, friend.getId());
			if (topTime != null) {
				friend.setTopTime(null);
			} else {
				friend.setTopTime(new Date());
			}
			service.saveOrUpdate(friend);
		}
		return findFriend();
	}

	public String changeRole() {
		type = "3";
		Member member = (Member) service.load(Member.class, id);
		member.setRole("S");
		service.saveOrUpdate(member);
		friend = (Friend) service.load(Friend.class, friend.getId());
		friend.setTopTime(new Date());
		service.saveOrUpdate(friend);
		return findFriend();
	}

	public void getMemberData() {
		try {
			session.setAttribute("spath", 6);
			session.setAttribute("queryType", 4);
			Member member = toMember();
			// type 0 全部 , 1 订单 , 2 优惠券
			int type = 0;
			if (request.getParameter("type") != null) {
				type = Integer.parseInt(request.getParameter("type"));
			}
			String queryByType = type > 0 ? " where t.type = " + type : "";
			// 状态 0 未付款订单 , 1 已付款订单 , 2 全部
			int status = 2;
			if (request.getParameter("status") != null) {
				status = Integer.parseInt(request.getParameter("status"));
			}
			String queryByStatus = "";
			if (status == 1) {
				queryByStatus = " and po.status > 0";
			} else if (status == 0) {
				queryByStatus = " and po.status = 0";
			}
			String querySql = "select t.* from ( select m.id, m.name, m.sex gender, m.nick realName, m.mobilephone, '' origin,"
					+ " t.name productName, t.price price, mt.active_date time, 2 type"
					+ " from tb_ticket_limit tl inner join tb_ticket t on"
					+ " tl.ticket = t.id inner join tb_member_ticket mt on t.id = mt.id"
					+ " inner join tb_member m on mt.member = m.id where tl.creator = ? UNION ALL"
					+ " select m.id, m.name, m.sex gender, m.nick realName, m.mobilephone, po.payEmail origin,"
					+ " p.name productName, po.orderMoney price, po.orderDate time, 1 type"
					+ " from tb_product_order po inner join tb_product p on po.product = p.id"
					+ " inner join tb_member m on po.member = m.id where p.member = ? " + queryByStatus + " UNION ALL"
					+ " select m.id, m.name, m.sex gender, m.nick realName, m.mobilephone, po.payEmail origin,"
					+ " p.name productName, po.orderMoney price, po.orderDate time, 1 type"
					+ " from tb_active_order po inner join tb_active p on po.active = p.id"
					+ " inner join tb_member m on po.member = m.id where p.creator = ? " + queryByStatus + " UNION ALL"
					+ " select m.id, m.name, m.sex gender, m.nick realName, m.mobilephone, po.payEmail origin,"
					+ " ci.name productName, po.orderMoney price, po.orderDate time, 1 type"
					+ " from tb_courserelease_order po inner join tb_course p on po.course = p.id"
					+ " inner join tb_course_info ci on p.courseId = ci.id inner join tb_member m on po.member = m.id"
					+ " where ci.member = ? " + queryByStatus + " UNION ALL"
					+ " select m.id, m.name, m.sex gender, m.nick realName, m.mobilephone, po.payEmail origin,"
					+ " p.name productName, po.orderMoney price, po.orderDate time, 1 type"
					+ " from tb_goods_order po inner join tb_goods p on po.goods = p.id"
					+ " inner join tb_member m on po.member = m.id where p.member = ? " + queryByStatus + ") t "
					+ queryByType + " order by t.time desc";

			List<Map<String, Object>> list = service.queryForList(querySql, member.getId(), member.getId(),
					member.getId(), member.getId(), member.getId());
			List<Map<String, Object>> list2 = new ArrayList<>();

			pageInfo.setPageSize(15);
			int start = (pageInfo.getCurrentPage() - 1) * pageInfo.getPageSize();
			int end = pageInfo.getCurrentPage() * pageInfo.getPageSize();
			for (int i = start; i < end; i++) {
				if (i > list.size() - 1) {
					continue;
				}
				list2.add(list.get(i));
			}
			// 添加分页信息
			int count = list.size();
			pageInfo.setTotalCount(count);
			int totalPage = count % pageInfo.getPageSize() == 0 ? count / pageInfo.getPageSize()
					: (count / pageInfo.getPageSize()) + 1;
			pageInfo.setTotalPage(totalPage);
			request.setAttribute("type", type);
			request.setAttribute("status", status);
			request.setAttribute("memberData", JSON.toJSONStringWithDateFormat(list2, "yyyy-MM-dd"));
			request.getRequestDispatcher("message/user_list.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
