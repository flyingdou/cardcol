package com.cardcolv45.mobile.action;

import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;

import com.freegym.web.basic.Member;
import com.freegym.web.basic.MemberTicket;
import com.freegym.web.mobile.BasicJsonAction;
import com.freegym.web.system.Ticket;
import com.freegym.web.utils.EasyUtils;
import com.freegym.web.utils.WebConstant;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Namespace("")
@InterceptorRefs({ @InterceptorRef("mobileStack") })
public class MAccountsV45ManageAction extends BasicJsonAction {

	private static final long serialVersionUID = 1980197426513589496L;

	/**
	 * orderType = 0 时加载当前用户的所有优惠券信息
	 */
	public void ticketAll() {
		try {
			String orderType = request.getParameter("orderType");
			if ("0".equals(orderType)) {
				@SuppressWarnings("unchecked")
				List<MemberTicket> tickets = (List<MemberTicket>) service.findObjectBySql(
						"from MemberTicket mt where  mt.member.id = ? and mt.status = ? order by mt.activeDate  desc ",
						getMobileUser().getId(), STATUS_TICKET_USE);
				JSONArray jarr = new JSONArray();
				for (final MemberTicket mt : tickets) {
					Calendar c = Calendar.getInstance();
					c.setTime(mt.getActiveDate());
					c.add(Calendar.DAY_OF_MONTH, mt.getTicket().getPeriod());
					if (ymd.format(c.getTime()).compareTo(ymd.format(new Date())) >= 0) {
						final JSONObject obj = new JSONObject();
						Calendar calendar = Calendar.getInstance();
						calendar.setTime(mt.getActiveDate());
						calendar.add(Calendar.DATE, mt.getTicket().getPeriod());
						obj.accumulate("id", mt.getId()).accumulate("name", mt.getTicket().getName())
								.accumulate("price", EasyUtils.decimalFormat(mt.getTicket().getPrice()))
								.accumulate("status", mt.getStatus() == STATUS_TICKET_USE ? "可用"
										: mt.getStatus() == STATUS_TICKET_USED ? "已使用" : "已失效")
								.accumulate("becomeDate", ymd.format(calendar.getTime()));
						if (mt.getTicket().getScope() != null) {
							StringBuffer sb = new StringBuffer();
							String[] ranges = mt.getTicket().getScope().split(",");
							for (int i = 0; i < ranges.length; i++) {
								sb.append(sb.length() <= 0 ? "" : ",").append(WebConstant.getType(ranges[i].trim()));
							}
							obj.accumulate("applyRange", sb.toString());
						}
						jarr.add(obj);
					}
				}
				final JSONObject obj = new JSONObject();
				obj.accumulate("success", true).accumulate("tickets", jarr);
				response(obj);
			} else {
				if ("8".equals(orderType))
					orderType = "7";
				List<Map<String, Object>> mts = service.queryForList(
						"SELECT a.id tid,a.active_date as activeDate,a.status,b.* FROM tb_member_ticket a LEFT JOIN tb_ticket b ON a.ticket = b.id WHERE DATE_ADD(a.active_date, INTERVAL b.period DAY) >= CURRENT_DATE() and a.status = ? and a.member = ? and b.scope like ?  order by a.active_date  desc ",
						STATUS_TICKET_USE, getMobileUser().getId(), "%" + orderType + "%");
				final JSONArray jarr = new JSONArray();
				for (final Map<String, Object> mt : mts) {
					Calendar calendar = Calendar.getInstance();
					calendar.setTime((Date) mt.get("activeDate"));
					calendar.add(Calendar.DATE, (int) mt.get("period"));
					final JSONObject obj = new JSONObject();
					obj.accumulate("id", mt.get("tid")).accumulate("name", mt.get("name"))
							.accumulate("price", mt.get("price"))
							.accumulate("status", (int) mt.get("status") == STATUS_TICKET_USE ? "可用"
									: (int) mt.get("status") == STATUS_TICKET_USED ? "已使用" : "已失效")
							.accumulate("becomeDate", ymd.format(calendar.getTime()));
					if (mt.get("scope") != null) {
						StringBuffer sb = new StringBuffer();
						String[] ranges = mt.get("scope").toString().split(",");
						for (int i = 0; i < ranges.length; i++) {
							sb.append(sb.length() <= 0 ? "" : ",").append(WebConstant.getType(ranges[i].trim()));
						}
						obj.accumulate("applyRange", sb.toString());
					}
					jarr.add(obj);
				}
				JSONObject obj = new JSONObject();
				obj.accumulate("success", true).accumulate("tickets", jarr);
				response(obj);
			}
		} catch (Exception e) {
			log.error("error", e);
			response(e);
		}
	}

	/**
	 * 输入激活码激活对应的优惠券。
	 */
	public void activeTicket() {
		try {
			final String activeCode = request.getParameter("activeCode");
			final Ticket tk = service.findTicketByActiveCode(activeCode);
			if (tk == null)
				throw new Exception("您的激活码无效，请确认！");
			final List<?> list = service.findMemberTicketByCode(activeCode, getMobileUser().getId());
			if (list.size() > 0)
				throw new Exception("当前激活码已经被您激活过,请确认!");
			MemberTicket mt = new MemberTicket();
			mt.setMember(new Member(getMobileUser().getId()));
			mt.setTicket(tk);
			mt.setActiveDate(new Date());
			mt.setStatus(STATUS_TICKET_USE);
			mt.setActiveCode(activeCode);
			mt = (MemberTicket) service.saveOrUpdate(mt);
			JSONObject obj = new JSONObject();
			obj.accumulate("success", true).accumulate("message", "OK");
			response(obj);
		} catch (Exception e) {
			log.error("error", e);
			response(e);
		}
	}

	/**
	 * 取得当前用户当前订单类型所有适用的优惠券
	 */
	public void findTicketByType() {
		try {
			final String orderType = request.getParameter("orderType");
			List<Map<String, Object>> mts = service.queryForList(
					"SELECT a.id tid, b.* FROM tb_member_ticket a LEFT JOIN tb_ticket b ON a.ticket = b.id WHERE DATE_ADD(a.active_date, INTERVAL b.period DAY) >= CURRENT_DATE() and a.status = ? and a.member = ? and b.scope like ?",
					STATUS_TICKET_USE, getMobileUser().getId(), "%" + orderType + "%");
			final JSONArray jarr = new JSONArray();
			for (final Map<String, Object> mt : mts) {
				final JSONObject obj = new JSONObject();
				MemberTicket mmt = (MemberTicket) service.load(MemberTicket.class,
						Long.valueOf(String.valueOf(mt.get("tid"))));
				if (mmt.getTicket().getScope() != null) {
					StringBuffer sb = new StringBuffer();
					String[] ranges = mmt.getTicket().getScope().split(",");
					for (int i = 0; i < ranges.length; i++) {
						sb.append(sb.length() <= 0 ? "" : ",").append(WebConstant.getType(ranges[i].trim()));
					}
					obj.accumulate("applyRange", sb.toString());
				}
				obj.accumulate("id", mt.get("id")).accumulate("name", mt.get("name"))
						.accumulate("price", mt.get("price")).accumulate("ticketId", mt.get("tid"));
				jarr.add(obj);
			}
			JSONObject obj = new JSONObject();
			obj.accumulate("success", true).accumulate("tickets", jarr);
			response(obj);
		} catch (Exception e) {
			log.error("error", e);
			response(e);
		}
	}

	/**
	 * 分享获取优惠券
	 */
	public void findTicketByShare() {
		try {
			final Member m = (Member) service.load(Member.class, getMobileUser().getId());
			final List<?> list = service.findObjectBySql("from Ticket t where t.kind = ? and t.effective = ?", "B",
					"1");
			for (final Iterator<?> it = list.iterator(); it.hasNext();) {
				final Ticket tk = (Ticket) it.next();
				MemberTicket mt = new MemberTicket();
				mt.setMember(m);
				mt.setTicket(tk);
				mt.setActiveDate(new Date());
				mt.setStatus(STATUS_TICKET_USE);
				mt = (MemberTicket) service.saveOrUpdate(mt);
				break;
			}
			response();
		} catch (Exception e) {
			log.error("error", e);
			response(e);
		}
	}

}
