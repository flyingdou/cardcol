package com.freegym.web.mobile.action;

import java.text.DecimalFormat;
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
import com.freegym.web.config.Setting;
import com.freegym.web.mobile.BasicJsonAction;
import com.freegym.web.system.Ticket;
import com.freegym.web.utils.WebConstant;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Namespace("")
@InterceptorRefs({ @InterceptorRef("mobileStack") })
public class MAccountManageAction extends BasicJsonAction {

	private static final long serialVersionUID = -4900715126840183203L;

	@Override
	protected void list() {
			final Setting set = service.loadSetting(getMobileUser().getId());
			final Member m = (Member) service.load(Member.class, getMobileUser().getId());
			final JSONObject obj = new JSONObject(), mObj = new JSONObject();
			DecimalFormat df =new DecimalFormat("#.00");
			// 20160720
			final List<Map<String, Object>> list=service.queryForList("select p.time time,p.traincount traincount,p.traincount/m.workoutTimes *100 finishrate from tb_member m left join (select sum(t.times) time, count(*) traincount,t.partake from tb_plan_record t where t.done_date<=SYSDATE() GROUP BY t.partake) p on m.id=p.partake where m.id= ?", new Object[]{getMobileUser().getId()});
			mObj.accumulate("id", m.getId()).accumulate("birthday", m.getBirthday() == null ? "" : ymd.format(m.getBirthday()))
					.accumulate("nick", getString(m.getNick())).accumulate("name", getString(m.getName())).accumulate("image", getString(m.getImage()))
					.accumulate("height", getInteger(set.getHeight())).accumulate("heart", getInteger(set.getHeart()))
					.accumulate("bmiLow", getInteger(set.getBmiLow())).accumulate("bmiHigh", getInteger(set.getBmiHigh()))
					.accumulate("heartRate", getInteger(set.getBmiLow()) + "~" + getInteger(set.getBmiHigh())).accumulate("sex", getString(m.getSex()))
					.accumulate("province", m.getProvince()).accumulate("city", m.getCity()).accumulate("county", m.getCounty())
					.accumulate("mobilephone", getString(m.getMobilephone())).accumulate("email", getString(m.getEmail()))
					.accumulate("mobileValid", m.getMobileValid())
					// 20160720
					.accumulate("count", list.get(0).get("traincount")==null?0:list.get(0).get("traincount").toString())
					.accumulate("finishrate",list.get(0).get("finishrate")==null?0:list.get(0).get("finishrate").toString())
					.accumulate("time", list.get(0).get("time")==null?0:String.valueOf(df.format(Double.parseDouble(list.get(0).get("time").toString())/60)));
			obj.accumulate("success", true).accumulate("member", mObj);
			response(obj);
			}

	@Override
	protected Long executeSave(JSONArray objs) {
		final JSONObject obj = objs.getJSONObject(0);
		Member m = (Member) service.load(Member.class, getMobileUser().getId());
		final Setting set = m.getSetting() == null ? new Setting() : m.getSetting();
		m.setName(obj.getString("name"));
		m.setNick(obj.getString("nick"));
		set.setHeight(obj.getInt("height"));
		set.setHeart(obj.getInt("heart"));
		if (obj.containsKey("bmiLow")) set.setBmiLow(obj.getInt("bmiLow"));
		if (obj.containsKey("bmiHigh")) set.setBmiHigh(obj.getInt("bmiHigh"));
		m.setProvince(obj.getString("province"));
		m.setCity(obj.getString("city"));
		m.setCounty(obj.getString("county"));
		m.setEmail(obj.getString("email"));
		m.setMobilephone(obj.getString("mobilephone"));
		m.setSex(obj.getString("sex"));
		m = (Member) service.saveOrUpdate(m);
		service.saveOrUpdate(set);
		return m.getId();
	}

	/**
	 * 加载当前用户的所有优惠券信息
	 */
	@SuppressWarnings("unchecked")
	public void listTicket() {
		try {
			final List<MemberTicket> tickets = (List<MemberTicket>) service.findObjectBySql("from MemberTicket mt where  mt.member.id = ? and mt.status = ?",
					getMobileUser().getId(), STATUS_TICKET_USE);
			JSONArray jarr = new JSONArray();
			for (final MemberTicket mt : tickets) {
				Calendar c = Calendar.getInstance();
				c.setTime(mt.getActiveDate());
				c.add(Calendar.DAY_OF_MONTH, mt.getTicket().getPeriod());
				if (ymd.format(c.getTime()).compareTo(ymd.format(new Date())) >= 0) {
					final JSONObject obj = new JSONObject();
					obj.accumulate("id", mt.getId()).accumulate("name", mt.getTicket().getName()).accumulate("price", mt.getTicket().getPrice())
							.accumulate("status", mt.getStatus() == STATUS_TICKET_USE ? "可用" : mt.getStatus() == STATUS_TICKET_USED ? "已使用" : "已失效")
							.accumulate("activeDate", ymd.format(mt.getActiveDate())).accumulate("period", mt.getTicket().getPeriod());
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
			final Member m = (Member) service.load(Member.class, getMobileUser().getId());
			final String activeCode = request.getParameter("activeCode");
			final Ticket tk = service.findTicketByActiveCode(activeCode);
			if (tk == null) throw new Exception("您的激活码无效，请确认！");
			final List<?> list = service.findMemberTicketByCode(activeCode, m.getId());
			if (list.size() > 0) throw new Exception("当前激活码已经被您激活过,请确认!");
			MemberTicket mt = new MemberTicket();
			mt.setMember(m);
			mt.setTicket(tk);
			mt.setActiveDate(new Date());
			mt.setStatus(STATUS_TICKET_USE);
			mt.setActiveCode(activeCode);
			mt = (MemberTicket) service.saveOrUpdate(mt);
			response();
		} catch (Exception e) {
			log.error("error", e);
			response(e);
		}
	}

	public void findTicketByShare() {
		try {
			final Member m = (Member) service.load(Member.class, getMobileUser().getId());
			final List<?> list = service.findObjectBySql("from Ticket t where t.kind = ? and t.effective = ?", "B", "1");
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

	/**
	 * 取得当前用户当前订单类型所有适用的优惠券
	 */
	public void findTicketByType() {
		try {
			final String orderType = request.getParameter("orderType");
			List<Map<String, Object>> mts = service
					.queryForList(
							"SELECT a.id tid, b.* FROM tb_member_ticket a LEFT JOIN tb_ticket b ON a.ticket = b.id WHERE DATE_ADD(a.active_date, INTERVAL b.period DAY) >= CURRENT_DATE() and a.status = ? and a.member = ? and b.scope like ?",
							STATUS_TICKET_USE, getMobileUser().getId(), "%" + orderType + "%");
			final JSONArray jarr = new JSONArray();
			for (final Map<String, Object> mt : mts) {
				final JSONObject obj = new JSONObject();
				obj.accumulate("id", mt.get("id")).accumulate("name", mt.get("name")).accumulate("price", mt.get("price"))
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
	 * 手机端用户头像上传
	 */
	public void saveImage() {
		try {
			final Member m = (Member) service.load(Member.class, getMobileUser().getId());
			final JSONObject ret = new JSONObject();
			if (file.getFile() != null) {
				m.setImage(saveFile("picture", m.getImage()));
				service.saveOrUpdate(m);
				ret.accumulate("success", true).accumulate("message", "OK");
			} else {
				ret.accumulate("success", false).accumulate("code", -1).accumulate("message", "上传文件请以“file.file”属性名称上传！");
			}
			response(ret);
		} catch (Exception e) {
			log.error("error", e);
			response(e);
		}
	}
}
