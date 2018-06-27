package com.freegym.web.course.action;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.freegym.web.CourseBasicAction;
import com.freegym.web.basic.Friend;
import com.freegym.web.basic.Member;
import com.freegym.web.course.Message;
import com.taobao.api.ApiException;

@Namespace("")
@InterceptorRefs({ @InterceptorRef("foreStack") })
@Results({ @Result(name = "success", location = "/message/index.jsp"),
		@Result(name = "message_list1", location = "/message/message_list1.jsp"),
		@Result(name = "message_list2", location = "/message/message_list2.jsp"),
		@Result(name = "message_list3", location = "/message/message_list3.jsp"),
		@Result(name = "message_list4", location = "/message/message_list2.jsp") })
public class MessageManageAction extends CourseBasicAction {

	private static final long serialVersionUID = -3769475961884283745L;

	private Message msg;

	private Message query;

	private String queryType;// 查询信息类型1:申请，2：收件箱，3：提醒，4：发件箱

	public String getQueryType() {
		return queryType;
	}

	public void setQueryType(String queryType) {
		this.queryType = queryType;
	}

	public Message getMsg() {
		return msg;
	}

	public void setMsg(Message msg) {
		this.msg = msg;
	}

	public Message getQuery() {
		return query;
	}

	public void setQuery(Message query) {
		this.query = query;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String execute() {
		session.setAttribute("spath", 6);
		final List countList1 = service.findObjectBySql(
				"SELECT COUNT(msg.id),msg.type FROM Message msg where msg.memberTo.id = ? and msg.isRead = ? and type != '1' GROUP BY msg.type",
				new Object[] { this.toMember().getId(), "0" });
		final List countList = service.findObjectBySql(
				"SELECT COUNT(msg.id),msg.type FROM Message msg where msg.memberTo.id = ? and msg.type = '1' and msg.status = '1' ",
				new Object[] { this.toMember().getId() });
		countList.addAll(countList1);
		request.setAttribute("countList", getJsonString(countList));
		if (request.getParameter("flag") != null) {
			request.setAttribute("flag", request.getParameter("flag"));
		}
		query();
		return SUCCESS;
	}

	@SuppressWarnings("unchecked")
	public String query() {
		if (queryType == null || "".equals(queryType)) {
			if (this.toMember().getRole().equals("M")) {
				this.setQueryType("2");
			} else {
				this.setQueryType("1");
			}
		} else if (!queryType.equals("4")) {
			final List<Message> msgList = (List<Message>) service.findObjectBySql(
					" from Message msg where msg.memberTo.id = ? and msg.isRead = ? and msg.type = ?",
					new Object[] { this.toMember().getId(), "0", this.getQueryType() });
			for (Message m : msgList)
				m.setIsRead("1");
			service.saveOrUpdate(msgList);
		}
		pageInfo.setPageSize(5);
		pageInfo.setOrder("sendTime");
		pageInfo.setOrderFlag("desc");
		pageInfo = service.findPageByCriteria(Message.getCriteriaQuery(query, this.toMember(), queryType),
				this.pageInfo);

		if (request.getParameter("status") != null) {
			return "message_list" + queryType;
		} else {
			return SUCCESS;
		}
	}

	@SuppressWarnings("unchecked")
	public String query2() {
		if (queryType == null || "".equals(queryType)) {
			if (this.toMember().getRole().equals("M")) {
				this.setQueryType("2");
			} else {
				this.setQueryType("1");
			}
		} else if (!queryType.equals("4")) {
			final List<Message> msgList = (List<Message>) service.findObjectBySql(
					" from Message msg where msg.memberTo.id = ? and msg.isRead = ? and msg.type = ?",
					new Object[] { this.toMember().getId(), "0", this.getQueryType() });
			for (Message m : msgList)
				m.setIsRead("1");
			service.saveOrUpdate(msgList);
		}
		pageInfo.setPageSize(5);
		pageInfo.setOrder("sendTime");
		pageInfo.setOrderFlag("desc");
		pageInfo = service.findPageByCriteria(Message.getCriteriaQuery(query, this.toMember(), queryType),
				this.pageInfo);

		return "message_list" + queryType;
	}

	public String changeStatus() throws ApiException {
		msg.setMemberFrom(toMember());
		// 选中checkbox的时候ids才会有值
		if (ids == null) {
			Long[] id = { msg.getId() };
			// 没选中则将处理当前行消息
			service.saveChangeMessageStatus(msg, id);
		} else {
			service.saveChangeMessageStatus(msg, ids);
		}
		return query2();
	}

	public String delete() {
		if (msg != null && msg.getId() != null) {
			service.delete(Message.class, msg.getId());
		} else {
			if (ids != null && ids.length > 0) {
				service.delete(Message.class, ids);
			}
		}
		return query2();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String findMember() {
		List<Friend> friendList = (List<Friend>) service.findObjectBySql(
				"from Friend f where (f.member.id = ? or f.friend.id = ? ) and f.type = ?  ",
				new Object[] { this.toMember().getId(), this.toMember().getId(), "1" });
		List<Member> memberList = new ArrayList();
		StringBuffer sb = new StringBuffer("");
		Member m = this.toMember();
		for (Friend f : friendList) {
			if (f.getMember().getId().equals(m.getId())) {
				sb.append(f.getFriend().getId() + "," + f.getFriend().getNick() + ":");
				memberList.add(f.getFriend());
			} else {
				sb.append(f.getMember().getId() + "," + f.getMember().getNick() + ":");
				memberList.add(f.getMember());
			}
		}
		if (m.getRole().equals("M") && m.getCoach() != null) {
			sb.append(m.getCoach().getId() + "," + m.getCoach().getNick() + ":");
			memberList.add(m.getCoach());
		}
		if (m.getRole().equals("S")) {
			List<Member> memList = (List<Member>) service.findObjectBySql("from Member m where m.coach.id = ?",
					this.toMember().getId());
			for (Member mem : memList) {
				sb.append(mem.getId() + "," + mem.getNick() + ":");
			}
			memberList.addAll(memList);
		}
		response(sb.length() > 0 ? sb.toString().substring(0, sb.length() - 1) : "");
		// request.setAttribute("memberList", memberList);
		return null;
	}

	public String save() {
		String msgg = "";
		try {
			if (msg.getType().equals("1"))
				msgg = checkJoin();
			if (msgg != null && "".equals(msgg)) {
				msg.setMemberFrom(this.toMember());
				service.saveOrUpdateMessage(msg);
				msgg = "ok";
			}
		} catch (Exception e) {
			msgg = e.getMessage();
			log.error("error", e);
		}
		response(msgg);
		return null;
	}

	@SuppressWarnings("rawtypes")
	private String checkJoin() {
		String msgg = "";
		Member memberTo = (Member) service.load(Member.class, msg.getMemberTo().getId());
		if (msg != null) {
			// 若是加入教练
			if (memberTo.getRole().equals("S")) {
				// 判断该会员是否已加入过其他教练
				List coachList = service.findObjectBySql(
						"from Member m where m.id = ? and m.coach.id != null and  m.coach.id != ?",
						new Object[] { this.toMember().getId(), msg.getMemberTo().getId() });
				if (coachList != null && coachList.size() > 0) {
					msgg = "joined";
				} else {
					// 判断会员是否加入过此教练
					coachList = service.findObjectBySql("from Member m where m.id = ? and m.coach.id = ? ",
							new Object[] { this.toMember().getId(), msg.getMemberTo().getId() });
					if (coachList != null && coachList.size() > 0) {
						msgg = "isFriend";
					}
				}
				if (msgg != null && "".equals(msgg)) {
					// 若是加入教练，判断是否发出过申请且未得到审批
					List msgList = service.findObjectBySql(
							" from Message m where m.memberFrom.id = ? and m.memberTo.id = ? and m.type = ? ",
							new Object[] { this.toMember().getId(), msg.getMemberTo().getId(), "1" });
					if (msgList != null && msgList.size() > 0) {
						msgg = "isMsg";
					}
				}
			} else if (memberTo.getRole().equals("E")) {
				// 若是加入俱乐部，判断是否发出过申请且未得到审批
				List msgList = service.findObjectBySql(
						" from Message m where m.memberFrom.id = ? and m.memberTo.id = ? and m.type = ? ",
						new Object[] { this.toMember().getId(), msg.getMemberTo().getId(), "1" });
				if (msgList != null && msgList.size() > 0) {
					msgg = "isMsg";
				}
				if (msgg != null && "".equals(msgg)) {
					// 判断是否加入过此俱乐部
					List friendList = service.findObjectBySql(
							"from Friend f where f.member.id = ? and f.friend.id = ? and f.friend.role = ? and f.type = ? ",
							new Object[] { this.toMember().getId(), msg.getMemberTo().getId(), "E", "1" });
					if (friendList != null && friendList.size() > 0) {
						msgg = "isFriend";
					}
				}
			}
		}
		return msgg;
	}
}
