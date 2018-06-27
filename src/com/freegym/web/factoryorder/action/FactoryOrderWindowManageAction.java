package com.freegym.web.factoryorder.action;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.freegym.web.FactoryOrderBasicAction;
import com.freegym.web.basic.Member;
import com.freegym.web.common.RepeatWhere;
import com.freegym.web.config.Factory;
import com.freegym.web.config.FactoryCosts;
import com.freegym.web.factoryorder.FactoryApply;
import com.freegym.web.order.FactoryOrder;
import com.sanmen.web.core.system.Parameter;

@Namespace("")
@InterceptorRefs({ @InterceptorRef("customStack") })
@Results({ @Result(name = "success", location = "/homeWindow/appoint.jsp"), @Result(name = "details", location = "/factoryorder/conditionwindow.jsp") })
public class FactoryOrderWindowManageAction extends FactoryOrderBasicAction {

	private static final long serialVersionUID = -3769475961884283745L;
	private static final SimpleDateFormat format_ymdhm = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	private static final SimpleDateFormat format_hm = new SimpleDateFormat("HH:mm");

	private Factory factory;

	private FactoryCosts factoryCosts;

	private FactoryOrder factoryOrder;

	private Date startDate, endDate;

	private RepeatWhere where;

	private Integer saveType;

	private String place; // 场地，位置

	private String sportstype;// 运动类型

	private String start, end; // 开始时间 ； 结束时间

	private String showdate; // 显示的日期

	private String viewtype; // 显示的类型

	private String timezone;

	private String playtype; // 运动类型

	private String costs; // 每小时的费用

	private String color; // 颜色

	private String clubId; // 用于不进入俱乐部，直接点击场地预约会传来clubId

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getCosts() {
		return costs;
	}

	public void setCosts(String costs) {
		this.costs = costs;
	}

	public String getPlaytype() {
		return playtype;
	}

	public void setPlaytype(String playtype) {
		this.playtype = playtype;
	}

	public String getShowdate() {
		return showdate;
	}

	public void setShowdate(String showdate) {
		this.showdate = showdate;
	}

	public String getViewtype() {
		return viewtype;
	}

	public void setViewtype(String viewtype) {
		this.viewtype = viewtype;
	}

	public String getTimezone() {
		return timezone;
	}

	public void setTimezone(String timezone) {
		this.timezone = timezone;
	}

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getEnd() {
		return end;
	}

	public void setEnd(String end) {
		this.end = end;
	}

	public FactoryOrder getFactoryOrder() {
		return factoryOrder;
	}

	public void setFactoryOrder(FactoryOrder factoryOrder) {
		this.factoryOrder = factoryOrder;
	}

	public String getSportstype() {
		return sportstype;
	}

	public void setSportstype(String sportstype) {
		this.sportstype = sportstype;
	}

	public FactoryCosts getFactoryCosts() {
		return factoryCosts;
	}

	public void setFactoryCosts(FactoryCosts factoryCosts) {
		this.factoryCosts = factoryCosts;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public RepeatWhere getWhere() {
		return where;
	}

	public void setWhere(RepeatWhere where) {
		this.where = where;
	}

	public Factory getFactory() {
		return factory;
	}

	public void setFactory(Factory factory) {
		this.factory = factory;
	}

	public Integer getSaveType() {
		return saveType;
	}

	public void setSaveType(Integer saveType) {
		this.saveType = saveType;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	private Member member;

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public String getClubId() {
		return clubId;
	}

	public void setClubId(String clubId) {
		this.clubId = clubId;
	}

	public String execute() {
		// 定义页面加载的类型
		request.setAttribute("goType", "2");
		// 不进入俱乐部主页而直接点击场地预约的话会获取不到toMember，届时会传过来clubId
		if (clubId != null && !"".equals(clubId)) {
			Member member = (Member) service.load(Member.class, Long.valueOf(clubId));
			session.setAttribute("toMember", member);
		}
		final Member toMember = (Member) session.getAttribute("toMember");
		if (toMember == null) {
			return SUCCESS;
		}
		// 俱乐部列表场地预约链接，页面传来clubId，如此可直接跳转到该俱乐部下场地预定的页面，否则就会进入会员的场地预定页面
		final Long memberId = clubId == null ? toMember.getId() : Long.valueOf(clubId);
		// 获取运动类型列表
		@SuppressWarnings("unchecked")
		List<Parameter> listParameter = (List<Parameter>) service
				.findObjectBySql(
						"from Parameter p where p.parent = (select po.id from Parameter po where po.code = ? ) AND p.id IN (SELECT t.project FROM Factory t WHERE t.club=? and t.applied = '1') order by p.id asc",
						new Object[] { "course_type_c", memberId });

		if (listParameter != null && listParameter.size() > 0) {
			request.setAttribute("sportsTypes", listParameter);
			Parameter parameter = listParameter.get(0);
			playtype = parameter.getId() + "";
			// 获取场地信息
			final List<?> factorys = service.findObjectBySql("from Factory f where f.club = ? and f.applied = ? order by f.sort",
					new Object[] { memberId, '1' });
			// request.setAttribute("factorys", factorys);
			request.setAttribute("jsonfactorys", getJsonString(factorys));
			if (factorys.size() > 0) {
				factory = (Factory) factorys.get(0);
				place = factory.getId() + "";
			}
		} else {
			request.setAttribute("sportsTypes", null);
		}
		return SUCCESS;
	}

	/**
	 * 通过运动类型场地信息
	 */
	public void queryForType() {
		final Member toMember = (Member) session.getAttribute("toMember");
		JSONObject obj = new JSONObject();
		final Long memberId = toMember.getId();
		// 获取场地信息
		final List<?> factorys = service.findObjectBySql("from Factory f where f.club = ? and f.applied = ? and project = ? order by f.sort", new Object[] {
				memberId, '1', playtype });
		request.setAttribute("factorys", factorys);
		obj.put("jsonfactorys", getJsonString(factorys));
		if (factorys.size() > 0) {
			factory = (Factory) factorys.get(0);
			obj.put("place", factory.getId());
		} else {
			obj.put("place", "");
		}
		response(obj.toString());
	}

	/**
	 * 获取指定区间的预约信息
	 */
	@SuppressWarnings("unchecked")
	public void query() {
		try {
			final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date date = new Date();
			String sDate = "", eDate = "";
			try {
				date = sdf.parse(showdate);
			} catch (ParseException e) {
				// do nothing
			}

			JSONObject obj = new JSONObject();
			Calendar c = Calendar.getInstance();
			c.setTimeInMillis(date.getTime());

			if ("day".equalsIgnoreCase(viewtype)) {
				obj.put("start", c);
				sDate = sdf.format(c.getTime());
				obj.put("end", c);
				eDate = sdf.format(c.getTime());
			} else if ("week".equalsIgnoreCase(viewtype)) {
				// 如果是星期天，设置前一天
				if (c.get(Calendar.DAY_OF_WEEK) == 1) {
					c.add(Calendar.DATE, -1);
				}
				c.set(Calendar.DAY_OF_WEEK, 2);
				obj.put("start", c);
				sDate = sdf.format(c.getTime());
				c.add(Calendar.DATE, 6);
				obj.put("end", c.getTime());
				eDate = sdf.format(c.getTime());
			} else if ("month".equalsIgnoreCase(viewtype)) {
				c.set(Calendar.DAY_OF_MONTH, 1);
				obj.put("start", c);
				sDate = sdf.format(c.getTime());
				c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
				obj.put("end", c);
				eDate = sdf.format(c.getTime());
			}

			JSONArray jsonArray = new JSONArray();
			// 获取预约数据
			List<?> list = service.findObjectBySql("from FactoryCosts fc where fc.factory.id = ? and fc.planDate between ? and ?",
					new Object[] { Long.parseLong(place), sDate, eDate });
			for (final Iterator<?> it = list.iterator(); it.hasNext();) {
				final FactoryCosts fc = (FactoryCosts) it.next();
				StringBuffer sb = new StringBuffer();
				// 获取购买数据
				List<FactoryOrder> sfo = (List<FactoryOrder>) service.findObjectBySql("from FactoryOrder f where f.factoryCosts.id=?", fc.getId());
				List<?> list_siteapply = service.findObjectBySql("from FactoryApply f where f.factorycosts.id=? and f.status!=3", fc.getId());
				List<String> datelist = getConditionDate(list_siteapply, sfo);
				if (datelist.size() > 0) {
					String plandate = fc.getPlanDate();
					String starttime = fc.getStarttime();
					String endtime = fc.getEndtime();
					Date c_start = format_ymdhm.parse(plandate + " " + starttime);
					Date c_end = format_ymdhm.parse(plandate + " " + endtime);
					for (int i = 0; i < datelist.size(); i++) {
						StringBuffer sb1 = new StringBuffer();
						String[] datedetails = datelist.get(i).split(",");
						Date r_start = format_ymdhm.parse(datedetails[0]);
						Date r_end = format_ymdhm.parse(datedetails[1]);
						if (c_start.compareTo(r_start) < 0) {
							// 可预订
							sb1.append("[\"").append(fc.getId()).append("\",\"");
							sb1.append("可预订").append("\",\"").append(format_ymdhm.format(c_start).replace("-", "/")).append("\",\"")
									.append(format_ymdhm.format(r_start).replace("-", "/"));
							sb1.append("\",\"0\",\"0\",\"0\",\"9\",\"1\",\"\",\"\",\"");
							sb1.append(fc.getCosts1()).append("\",\"").append(fc.getCosts2()).append("\",\"\"]");
							jsonArray.add(sb1.toString());
							// 已预订
							sb1 = new StringBuffer();
							sb1.append("[\"").append(fc.getId()).append("\",\"");
							sb1.append("已预订").append("\",\"").append(format_ymdhm.format(r_start).replace("-", "/")).append("\",\"")
									.append(format_ymdhm.format(r_end).replace("-", "/"));
							sb1.append("\",\"0\",\"0\",\"0\",\"14\",\"1\",\"\",\"\",\"");
							sb1.append(fc.getCosts1()).append("\",\"").append(fc.getCosts2()).append("\",\"\"]");
							jsonArray.add(sb1.toString());
							c_start = r_end;
						} else {
							// 已预订
							sb1.append("[\"").append(fc.getId()).append("\",\"");
							sb1.append("已预订").append("\",\"").append(format_ymdhm.format(r_start).replace("-", "/")).append("\",\"")
									.append(format_ymdhm.format(r_end).replace("-", "/"));
							sb1.append("\",\"0\",\"0\",\"0\",\"14\",\"1\",\"\",\"\",\"");
							sb1.append(fc.getCosts1()).append("\",\"").append(fc.getCosts2()).append("\",\"\"]");
							jsonArray.add(sb1.toString());
							c_start = r_end;
						}
					}
					if (c_start.compareTo(c_end) < 0) {
						// 可预订
						sb.append("[\"").append(fc.getId()).append("\",\"");
						sb.append("可预订").append("\",\"").append(format_ymdhm.format(c_start).replace("-", "/")).append("\",\"")
								.append(format_ymdhm.format(c_end).replace("-", "/"));
						sb.append("\",\"0\",\"0\",\"0\",\"9\",\"1\",\"\",\"\",\"");
						sb.append(fc.getCosts1()).append("\",\"").append(fc.getCosts2()).append("\",\"\"]");
						jsonArray.add(sb.toString());
					}
				} else {
					sb.append("[\"").append(fc.getId()).append("\",\"");
					sb.append("可预订").append("\",\"").append(fc.getPlanDate().replace("-", "/")).append(" ").append(fc.getStarttime()).append("\",\"")
							.append(fc.getPlanDate().replace("-", "/")).append(" ").append(fc.getEndtime());
					sb.append("\",\"0\",\"0\",\"0\",\"9\",\"1\",\"\",\"\",\"");
					sb.append(fc.getCosts1()).append("\",\"").append(fc.getCosts2()).append("\",\"\"]");
					jsonArray.add(sb.toString());
				}
			}
			obj.put("events", jsonArray);
			response(obj.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private List<String> getConditionDate(List<?> list_siteapply, List<FactoryOrder> sfo) {
		List<String> _list = new ArrayList<String>();
		if (list_siteapply != null && list_siteapply.size() > 0) {
			for (int i = 0; i < list_siteapply.size(); i++) {
				FactoryApply siteApply = (FactoryApply) list_siteapply.get(i);
				_list.add(siteApply.getPlaceDate() + " " + siteApply.getStartTime() + "," + siteApply.getPlaceDate() + " " + siteApply.getEndTime());
			}
		}
		if (sfo.size() > 0) {
			Iterator<FactoryOrder> iterator = sfo.iterator();
			while (iterator.hasNext()) {
				FactoryOrder factoryorder = iterator.next();
				// if (factoryorder.getStatus() != null &&
				// !(factoryorder.getStatus().toString().equals("0"))) {
				_list.add(factoryorder.getFactoryCosts().getPlanDate() + " " + format_hm.format(factoryorder.getOrderStartTime()) + "," + factoryorder.getFactoryCosts().getPlanDate()
						+ " " + format_hm.format(factoryorder.getOrderEndTime()));
				// }
			}
		}
		if (_list.size() > 0) {
			Collections.sort(_list);
		}
		return _list;
	}

	/**
	 * 查询预约条件的详细信息
	 * 
	 * @return
	 */
	public String details() {
		// 是否会员
		boolean isFriend = false;
		// 获取俱乐部信息
		final Member toMember = (Member) session.getAttribute("toMember");
		final Long clubId = toMember.getId();
		// 获取会员信息
		if (member != null && member.getId() != null) {
			member = (Member) service.load(Member.class, member.getId());
		} else {
			member = (Member) service.load(Member.class, toMember().getId());
		}
		final Long memberId = member.getId();
		List<?> lst_friend = service.findObjectBySql("from Friend f where f.member.id = ? and f.friend.id = ? and f.type = ? ", new Object[] { 
				memberId,clubId, "1" });
		if (lst_friend.size() > 0) {
			isFriend = true;
		}

		if (factoryCosts.getId() != 0) {
			// 从数据库中获取
			List<?> list = service.findObjectBySql("from FactoryCosts fc where fc.id = ?", factoryCosts.getId());
			if (list.size() > 0) {
				factoryCosts = (FactoryCosts) list.get(0);
				if (factoryCosts.getFactoryorders().size() > 0) {
					factoryCosts.setCanEdit("N");
				} else {
					factoryCosts.setCanEdit("Y");

				}
				if (isFriend) {
					this.costs = factoryCosts.getCosts2();
				} else {
					this.costs = factoryCosts.getCosts1();
				}
			}
		}

		return "details";
	}

	/**
	 * 保存预约
	 */
	public void saveAndUpdateCondition() {
		if (factoryCosts.getId() != 0) {
			// 获取用户信息
			if (member != null && member.getId() != null) {
				member = (Member) service.load(Member.class, member.getId());
			} else {
				member = (Member) service.load(Member.class, toMember().getId());
			}
			// 获取俱乐部信息
			final Member toMember = (Member) session.getAttribute("toMember");
			// 是否会员
			List<?> _list = service.findObjectBySql("select count(o.id) from Friend o where o.member.id = ? and o.friend.id = ?",
					new Object[] { toMember.getId(), member.getId() });
			final Long hasOrder = (Long) _list.get(0);
			if (hasOrder != null && hasOrder > 0) {
				try {
					// 预约流程
					Date c_start = factoryOrder.getOrderStartTime();
					Date c_end = factoryOrder.getOrderEndTime();
					List<?> applylist = service.findObjectBySql("from FactoryApply f where f.factorycosts.id = ? and f.status!=3 ", factoryCosts.getId());
					boolean isExist = false;
					for (int i = 0; i < applylist.size(); i++) {
						FactoryApply apply = (FactoryApply) applylist.get(i);
						Date applystartdate = format_ymdhm.parse(apply.getApplyDate() + " " + apply.getStartTime());
						Date applyenddate = format_ymdhm.parse(apply.getApplyDate() + " " + apply.getEndTime());
						if (applystartdate.compareTo(c_start) >= 0 && applystartdate.compareTo(c_end) < 0) {
							isExist = true;
							break;
						} else if (applyenddate.compareTo(c_start) > 0 && applyenddate.compareTo(c_end) <= 0) {
							isExist = true;
							break;
						} else if (c_start.compareTo(applystartdate) >= 0 && c_start.compareTo(applyenddate) < 0) {
							isExist = true;
							break;
						} else if (c_end.compareTo(applystartdate) >= 0 && c_end.compareTo(applyenddate) <= 0) {
							isExist = true;
							break;
						} else {
							isExist = false;
						}
					}

					if (isExist) {
						// 不能预约
						response("{success: false, message: 'exist'}");
					} else {
						// 可以预约
						FactoryApply apply = new FactoryApply();
						apply.setApplyDate(new Date());
						apply.setPlaceDate(factoryCosts.getPlanDate());
						apply.setStartTime(factoryOrder.getOrderStartTime());
						apply.setEndTime(factoryOrder.getOrderEndTime());
						apply.setFactorycosts(factoryCosts);
						apply.setStatus(STATUS_REQUEST_COURSE_WAIT);
						apply.setIsReadBuy(STATUS_REQUEST_NOREAD);
						apply.setIsReadSell(STATUS_REQUEST_NOREAD);
						apply.setMember(member);
						service.saveOrUpdate(apply);
						response("{success: true, message: 'apply'}");
					}
				} catch (Exception e) {
					e.printStackTrace();
					response("{success: false, message: '" + e.getMessage() + "'}");
				}
			} else {
				response("{success: false, message: '您还不是该俱乐部会员!'}");
			}
		}
	}

	public void validateTimeCross() {

	}

}
