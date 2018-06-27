package ecartoon.wx.action;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.databaseConnection.DataBaseConnection;
import com.freegym.web.basic.Member;
import com.freegym.web.basic.MemberTicket;
import com.freegym.web.mobile.BasicJsonAction;
import com.freegym.web.system.Ticket;
import com.freegym.web.utils.EasyUtils;
import com.freegym.web.utils.WebConstant;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Namespace("")
@InterceptorRefs({ @InterceptorRef("customStack") })
@Results({ @Result(name = "ticketAll", location = "/ecartoon-weixin/ticket.jsp") })
public class EAccountsWXManageAction extends BasicJsonAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * orderType = 0 时加载当前用户的所有优惠券信息
	 */
	@SuppressWarnings("unchecked")
	public String ticketAll() {
		JSONObject obj = null;
		Member member = (Member) request.getSession().getAttribute("member");
		try {
			String orderType = request.getParameter("orderType");
			if (orderType == null) {
				orderType = "0";
			}
			long id = member.getId();
			if ("0".equals(orderType)) {
				List<MemberTicket> tickets = (List<MemberTicket>) service.findObjectBySql(
						"from MemberTicket mt where  mt.member.id = ? and mt.status = ? order by mt.activeDate  desc ",
						id, STATUS_TICKET_USE);
				JSONArray jarr = new JSONArray();
				for (final MemberTicket mt : tickets) {
					Calendar c = Calendar.getInstance();
					c.setTime(mt.getActiveDate());
					c.add(Calendar.DAY_OF_MONTH, mt.getTicket().getPeriod());
					if (ymd.format(c.getTime()).compareTo(ymd.format(new Date())) >= 0) {
						obj = new JSONObject();
						Calendar calendar = Calendar.getInstance();
						calendar.setTime(mt.getActiveDate());
						calendar.add(Calendar.DATE, mt.getTicket().getPeriod());
						obj.accumulate("id", mt.getId()).accumulate("name", mt.getTicket().getName())
								.accumulate("price", mt.getTicket().getPrice())
								.accumulate("status", mt.getStatus() == STATUS_TICKET_USE ? "可用"
										: mt.getStatus() == STATUS_TICKET_USED ? "已使用" : "已失效")
								.accumulate("endDate", ymd.format(calendar.getTime()))
								.accumulate("becomeDate", ymd.format(mt.getActiveDate()));
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
				obj = new JSONObject().accumulate("success", true).accumulate("tickets", jarr);
				request.setAttribute("data", obj);
			} else {
				if ("8".equals(orderType))
					orderType = "7";
				List<Map<String, Object>> mts = service.queryForList(
						"SELECT a.id tid,a.active_date as activeDate,a.status,b.* FROM tb_member_ticket a LEFT JOIN tb_ticket b ON a.ticket = b.id WHERE DATE_ADD(a.active_date, INTERVAL b.period DAY) >= CURRENT_DATE() and a.status = ? and a.member = ? and b.scope like ?  order by a.active_date  desc ",
						STATUS_TICKET_USE, id, "%" + orderType + "%");
				final JSONArray jarr = new JSONArray();
				for (final Map<String, Object> mt : mts) {
					Calendar calendar = Calendar.getInstance();
					calendar.setTime((Date) mt.get("activeDate"));
					calendar.add(Calendar.DATE, (int) mt.get("period"));
					obj = new JSONObject();
					obj.accumulate("id", mt.get("tid")).accumulate("name", mt.get("name"))
							.accumulate("price", mt.get("price"))
							.accumulate("status", (int) mt.get("status") == STATUS_TICKET_USE ? "可用"
									: (int) mt.get("status") == STATUS_TICKET_USED ? "已使用" : "已失效")
							.accumulate("endDate", ymd.format(calendar.getTime()))
							.accumulate("becomeDate", ymd.format(mt.get("active_date").toString()));
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

				obj = new JSONObject();
				obj = new JSONObject().accumulate("success", true).accumulate("tickets", jarr);
				request.setAttribute("data", obj);
			}
		} catch (Exception e) {
			log.error("error", e);
			e.printStackTrace();
		}
		// response(obj);
		return "ticketAll";
	}

	/**
	 * 取得当前用户当前订单类型所有适用的优惠券
	 */
	public void findTicketByType() {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Member member = (Member) session.getAttribute("member");
			String orderType = request.getParameter("orderType");
			if ("8".equals(orderType)) {
				orderType = "7";
			}
			List<Map<String, Object>> mts = service.queryForList(
					"SELECT a.id tid, a.active_date activeDate, b.*,DATE_ADD(a.active_date, INTERVAL b.period DAY) useDate FROM tb_member_ticket a LEFT JOIN tb_ticket b ON a.ticket = b.id WHERE DATE_ADD(a.active_date, INTERVAL b.period DAY) >= CURRENT_DATE() and a.status = ? and a.member = ? and b.scope like ?",
					STATUS_TICKET_USE, member.getId(), "%" + orderType + "%");
			final JSONArray jarr = new JSONArray();
			for (final Map<String, Object> mt : mts) {
				final JSONObject obj = new JSONObject();
				obj.accumulate("id", mt.get("id")).accumulate("name", mt.get("name"))
						.accumulate("price", mt.get("price")).accumulate("activeDate", sdf.format(mt.get("activeDate")))
						.accumulate("useDate", EasyUtils.dateFormat((Date) mt.get("useDate"), "yyyy-MM-dd"))
						.accumulate("ticketId", mt.get("tid"));
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
	 * 输入激活码激活对应的优惠券。
	 */
	public void activeTicket() {
		try {
			final String activeCode = request.getParameter("activeCode");
			final Ticket tk = service.findTicketByActiveCode(activeCode);
			Member member = (Member) session.getAttribute("member");
			if (tk == null)
				throw new Exception("您的激活码无效，请确认！");
			final List<?> list = service.findMemberTicketByCode(activeCode, member.getId());
			if (list.size() > 0)
				throw new Exception("当前激活码已经被您激活过,请确认!");
			MemberTicket mt = new MemberTicket();
			mt.setMember(member);
			mt.setTicket(tk);
			mt.setActiveDate(new Date());
			mt.setStatus(STATUS_TICKET_USE);
			mt.setActiveCode(activeCode);
			mt = (MemberTicket) service.saveOrUpdate(mt);
			JSONObject obj = new JSONObject();
			obj.accumulate("success", true).accumulate("message", "激活成功");
			response(obj);
		} catch (Exception e) {
			log.error("error", e);
			response(e);
		}
	}

	/**
	 * 修改优惠券使用状态
	 */
	public void updateTicketActive() {
		String ticketId = request.getParameter("ticketId");
		String updateSql = "update tb_member_ticket set status = 2 where member = ? and ticket = ?";
		Member member = (Member) session.getAttribute("member");
		Object[] args = { member.getId(), ticketId };
		DataBaseConnection.updateData(updateSql, args);
		response(new JSONObject().accumulate("success", true));
	}
}
