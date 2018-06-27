package ecartoon.wx.action;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.freegym.web.active.Active;
import com.freegym.web.active.Team;
import com.freegym.web.basic.Member;
import com.freegym.web.config.Setting;
import com.freegym.web.mobile.BasicJsonAction;
import com.freegym.web.order.ActiveOrder;
import com.freegym.web.order.Order;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Namespace("")
@InterceptorRefs({ @InterceptorRef("customStack") })
@Results({ @Result(name = "income", location = "/ecartoon-weixin/mymoneybox.jsp"), })
public class EOrderDetailWXManageAction extends BasicJsonAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3388883381973263518L;

	@SuppressWarnings("unused")
	private static final String ArrayList = null;

	/**
	 * 关键字, 裁判
	 */
	private String keyword, judge;
	private String type;
	private Long teamId;

	private String startDate;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getJudge() {
		return judge;
	}

	public void setJudge(String judge) {
		this.judge = judge;
	}

	public Long getTeamId() {
		return teamId;
	}

	public void setTeamId(Long teamId) {
		this.teamId = teamId;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	/**
	 * 支出
	 */
	@SuppressWarnings("unused")
	public void expenditure() {
		final List<Object> parms = new ArrayList<Object>();
		final JSONArray jarr = new JSONArray();
		Member member = (Member) request.getSession().getAttribute("member");
		long memberId = member.getId();

		StringBuffer sql = new StringBuffer();
		sql.append("SELECT * FROM (").append(Order.getOrderSqls()).append(") t where 1 = 1");
		sql.append(" and (fromId = ?) and STATUS !=0 ");
		parms.add(memberId);
		sql.append("order by payTime desc");
		System.out.println(sql.toString());
		pageInfo = service.findPageBySql(sql.toString(), pageInfo, parms.toArray());
		JSONArray out = JSONArray.fromObject(pageInfo.getItems());
		final JSONObject obj = new JSONObject();
		obj.accumulate("success", true).accumulate("out", out).accumulate("pageInfo", getJsonForPageInfo());
		response(obj);
		request.setAttribute("out", obj);
	}

	/**
	 * 收入
	 */
	@SuppressWarnings("unchecked")
	public String income() {
		Member member = (Member) request.getSession().getAttribute("member");
		long memberId = member.getId();

		// 收入
		pageInfo = service.income(pageInfo, memberId);

		List<Map<String, Object>> incomeList = pageInfo.getItems();
		final JSONArray incomeDetail = new JSONArray();

		if (incomeList.size() > 0) {
			JSONObject objx = null;
			for (Map<String, Object> map : incomeList) {
				objx = new JSONObject();
				objx.accumulate("id", map.get("id")).accumulate("prodName", map.get("prodName"))
						.accumulate("balanceTime", map.get("balanceTime")).accumulate("fromName", map.get("fromName"))
						.accumulate("balanceMoney", map.get("balanceMoney"));
				incomeDetail.add(objx);
			}
		}

		// 支出
		final List<Object> parms = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT * FROM (").append(Order.getOrderSqls()).append(") t where 1 = 1");
		sql.append(" and (fromId = ?) and STATUS !=0 ");
		parms.add(memberId);
		sql.append("order by orderDate desc");
		System.out.println(sql.toString());
		pageInfo = service.findPageBySql(sql.toString(), pageInfo, parms.toArray());

		List<Map<String, Object>> outList = pageInfo.getItems();
		final JSONArray outDetail = new JSONArray();
		if (outList.size() > 0) {
			JSONObject objx = null;
			for (Map<String, Object> map : outList) {
				objx = new JSONObject();
				objx.accumulate("id", map.get("id")).accumulate("payTime", map.get("payTime"))
						.accumulate("toName", map.get("toName")).accumulate("name", map.get("NAME"))
						.accumulate("orderMoney", map.get("orderMoney")).accumulate("status", map.get("STATUS"));
				outDetail.add(objx);
			}
		}

		// 提现
		// 查询余额
		Double pickMoneyCount = service.findPickMoneyCountByMember((Member) member);
		DecimalFormat df = new DecimalFormat("0.00");
		// 查询提现记录
		pageInfo = service.queryPickDetail(pageInfo, memberId + "");
		List<Map<String, Object>> pickList = pageInfo.getItems();
		final JSONArray pickDetail = new JSONArray();
		if (pickList.size() > 0) {
			JSONObject objx = null;
			for (Map<String, Object> map : pickList) {
				if ("支付宝".equals(map.get("bankName"))) {
					map.put("image", "img/zhifubao.png");
				}
				if ("微信".equals(map.get("bankName"))) {
					map.put("image", "img/weixin.png");
				}
				if ("银联".equals(map.get("bankName"))) {
					map.put("image", "img/shouye/taoke/UnionPay@2x.png");
				}
				objx = new JSONObject();
				String etime = (String) map.get("evalTime");
				etime = etime.substring(0, 10);
				objx.accumulate("bankName", map.get("bankName")).accumulate("account", map.get("account"))
						.accumulate("evalTime", etime).accumulate("pickMoney", map.get("pickMoney"))
						.accumulate("nick", member.getName()).accumulate("imgsrc", map.get("image"))
						.accumulate("status", map.get("status"));

				pickDetail.add(objx);
			}
		}
		final JSONObject obj = new JSONObject();
		obj.accumulate("success", true).accumulate("incomeDetail", incomeDetail).accumulate("outDetail", outDetail)
				.accumulate("pickDetail", pickDetail).accumulate("pickMoneyCount", df.format(pickMoneyCount))
				.accumulate("pageInfo", getJsonForPageInfo());
		response(obj);
		request.setAttribute("dataLists", obj);
		return "income";
	}

	/**
	 * 报名参加
	 */
	public void partake() {
		try {
			final String weight = request.getParameter("weight");
			Long mid = getMobileUser().getId();
			final Member m = (Member) service.load(Member.class, mid);
			if (weight != null) {
				final Setting set = service.loadSetting(m.getId());
				set.setWeight(new Double(weight));
				service.saveOrUpdate(set);
			}
			final Active a = (Active) service.load(Active.class, id);
			ActiveOrder ao = new ActiveOrder();
			ao.setNo(service.getKeyNo("", "TB_ACTIVE_ORDER", 14));
			ao.setPayNo(ao.getNo());
			ao.setActive(a);
			ao.setOrderDate(new SimpleDateFormat("yyyy-MM-dd").parse(startDate));
			ao.setJudge(judge.toString());
			ao.setOrderMoney(a.getAmerceMoney());
			if (teamId == null)
				ao.setMember(new Member(mid));
			else
				ao.setTeam(new Team(teamId));
			ao.setValue(0d);
			ao.setStatus('0');
			ao = (ActiveOrder) service.saveOrUpdate(ao);
			final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			final JSONObject obj = new JSONObject(), orderJson = new JSONObject();
			orderJson.accumulate("id", ao.getId()).accumulate("orderNo", ao.getNo())
					.accumulate("cost", ao.getOrderMoney()).accumulate("orderDate", sdf.format(ao.getOrderDate()));
			obj.accumulate("success", true).accumulate("order", orderJson);
			response(obj);
		} catch (Exception e) {
			log.error("error", e);
			response(e);
		}
	}

}
