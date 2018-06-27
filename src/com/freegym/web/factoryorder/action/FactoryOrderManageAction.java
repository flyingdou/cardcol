package com.freegym.web.factoryorder.action;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
import com.freegym.web.course.BaseApply;
import com.freegym.web.factoryorder.FactoryApply;
import com.freegym.web.order.FactoryOrder;
import com.sanmen.web.core.system.Parameter;

@Namespace("")
@InterceptorRefs({ @InterceptorRef("customStack"), @InterceptorRef("foreStack") })
@Results({ @Result(name = "success", location = "/course/index.jsp"), @Result(name = "details", location = "/factoryorder/condition.jsp") })
public class FactoryOrderManageAction extends FactoryOrderBasicAction {

	private static final long serialVersionUID = -3769475961884283745L;
	private static final SimpleDateFormat format_ymdhm = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	private static final SimpleDateFormat format_ymd = new SimpleDateFormat("yyyy-MM-dd");
	private static final SimpleDateFormat format_hm = new SimpleDateFormat("HH:mm");

	private Factory factory;

	private FactoryCosts factoryCosts;

	private FactoryOrder factoryOrder;

	private Date startDate, endDate;

	private RepeatWhere where;

	private Integer saveType;

	private String place; // 场地，位置

	private String sportstype;// 运动类型

	private String goType;// 进入的菜单1：评价2: 场地预约

	private String start, end; // 开始时间 ； 结束时间

	private String showdate; // 显示的日期

	private String viewtype; // 显示的类型

	private String timezone;

	private String playtype; // 运动类型

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

	public String getGoType() {
		return goType;
	}

	public void setGoType(String goType) {
		this.goType = goType;
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

	public String execute() {
		// 定义页面加载的类型
		goType = "2";
		// 获取用户信息
		if (member != null && member.getId() != null) {
			member = (Member) service.load(Member.class, member.getId());
		} else {
			member = (Member) service.load(Member.class, toMember().getId());
		}
		final Long memberId = member.getId();
		String role = member.getRole();

		final Long count = service.queryForLong("SELECT COUNT(ap.id) FROM TB_MEMBER_APPRAISE ap where ap.memberTo = ? and ap.isRead = ?", new Object[] {
				memberId, "0" });
		request.setAttribute("countList1", count);
		// 是否阅读分角色处理
		final StringBuffer hql = new StringBuffer("select case when t.status is null then '1' else t.status end status, count(*) cnt from ("
				+ BaseApply.getApplySql() + ") t ");
		if (member.getRole().equals("M")) {
			hql.append(" where t.memberid = ?");
		} else {
			hql.append(" where t.clubid = ?");
		}
		hql.append(" group by case when t.status is null then '1' else t.status end");
		final List<?> countList = service.queryForList(hql.toString(), member.getId());
		request.setAttribute("countList2", getJsonString(countList));

		if (!role.equalsIgnoreCase("E")) {
			goType = "3";
			// 获取所有俱乐部的信息
			// 获取运动类型列表
			@SuppressWarnings("unchecked")
			List<Parameter> listParameter = (List<Parameter>) service
					.findObjectBySql(
							"from Parameter p where p.viewType=1 and p.parent = (select po.id from Parameter po where po.code = ? ) AND p.id IN (SELECT project FROM Factory where id in (select factory from FactoryCosts where id in (select factoryCosts from FactoryOrder))) order by p.id asc",
							"course_type_c");
			if (listParameter != null && listParameter.size() > 0) {
				request.setAttribute("sportsTypes", listParameter);
				Parameter parameter = listParameter.get(0);
				playtype = parameter.getId() + "";
				// 获取场地信息
				final List<?> factorys = service
						.findObjectBySql(
								"from Factory f where f.applied = ? and project = ? and id in (select factory from FactoryCosts where id in (select factoryCosts from FactoryOrder)) order by f.sort",
								new Object[] { '1', playtype });
				request.setAttribute("factorys", factorys);
				request.setAttribute("jsonfactorys", getJsonString(factorys));
				if (factorys.size() > 0) {
					factory = (Factory) factorys.get(0);
					place = factory.getId() + "";
				}
			} else {
				request.setAttribute("sportsTypes", null);
			}
		} else {
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
				final List<?> factorys = service.findObjectBySql("from Factory f where f.club = ? and f.applied = ? order by f.sort", new Object[] { memberId,
						'1' });
				// request.setAttribute("factorys", factorys);
				request.setAttribute("jsonfactorys", getJsonString(factorys));
				if (factorys.size() > 0) {
					factory = (Factory) factorys.get(0);
					place = factory.getId() + "";
				}
			} else {
				request.setAttribute("sportsTypes", null);
			}
		}
		return SUCCESS;
	}

	/**
	 * 通过运动类型场地信息
	 */
	public void queryForType() {
		JSONObject obj = new JSONObject();
		// 获取用户信息
		if (member != null && member.getId() != null) {
			member = (Member) service.load(Member.class, member.getId());
		} else {
			member = (Member) service.load(Member.class, toMember().getId());
		}
		final Long memberId = member.getId();
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
	 * 通过运动类型所有俱乐部的场地信息
	 */
	public void queryForTypeAll() {
		JSONObject obj = new JSONObject();
		// 获取场地信息
		// 获取场地信息
		final List<?> factorys = service
				.findObjectBySql(
						"from Factory f where f.applied = ? and project = ? and id in (select factory from FactoryCosts where id in (select factoryCosts from FactoryOrder)) order by f.sort",
						new Object[] { '1', playtype });
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

	// 获取指定场地的所有已经预定的信息
	public void queryall() {
		try {
			// 获取用户信息
			if (member != null && member.getId() != null) {
				member = (Member) service.load(Member.class, member.getId());
			} else {
				member = (Member) service.load(Member.class, toMember().getId());
			}
			final Long memberId = member.getId();

			final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date date = new Date();
			String sDate = "";
			// String eDate="";
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
				// obj.put("end", c);
				// eDate = sdf.format(c.getTime());
			} else if ("week".equalsIgnoreCase(viewtype)) {
				// 如果是星期天，设置前一天
				if (c.get(Calendar.DAY_OF_WEEK) == 1) {
					c.add(Calendar.DATE, -1);
				}
				c.set(Calendar.DAY_OF_WEEK, 2);
				obj.put("start", c);
				sDate = sdf.format(c.getTime());
				// c.add(Calendar.DATE, 6);
				// obj.put("end", c.getTime());
				// eDate = sdf.format(c.getTime());
			} else if ("month".equalsIgnoreCase(viewtype)) {
				c.set(Calendar.DAY_OF_MONTH, 1);
				obj.put("start", c);
				sDate = sdf.format(c.getTime());
				// c.set(Calendar.DAY_OF_MONTH,
				// c.getActualMaximum(Calendar.DAY_OF_MONTH));
				// obj.put("end", c);
				// eDate = sdf.format(c.getTime());
			}

			JSONArray jsonArray = new JSONArray();
			// 获取预定数据
			List<?> list = service.findObjectBySql("from FactoryOrder fo where fo.member.id=? and fo.starttime>? and fo.factoryCosts.factory.id=? ",
					new Object[] { memberId, sDate, Long.parseLong(place) });
			for (final Iterator<?> it = list.iterator(); it.hasNext();) {
				FactoryOrder fo = (FactoryOrder) it.next();
				FactoryCosts fc = fo.getFactoryCosts();
				StringBuffer sb = new StringBuffer();
				sb.append("[\"").append(fo.getId()).append("\",\"已预订");
				sb.append("\",\"").append(sdf.format(fo.getOrderStartTime()).replace("-", "/")).append("\",\"").append(sdf.format(fo.getOrderEndTime()).replace("-", "/"));
				sb.append("\",\"0\",\"0\",\"0\",\"14\",\"1\",\"");
				// 获取俱乐部的名称
				List<?> memberlist = service.findObjectBySql("from Member where id=?", fc.getFactory().getClub());
				if (memberlist.size() > 0) {
					Member member = (Member) memberlist.get(0);
					sb.append(member.getName() + " " + fc.getFactory().getName());
				} else {
					sb.append("");
				}
				sb.append("\",\"\",\"\",\"\",\"").append(fo.getOrderMoney()).append("\",\"");
				// 获取运动项目类型名称
				List<?> parameterlist = service.findObjectBySql("from Parameter where id=?", Long.parseLong(fc.getFactory().getProject()));
				if (parameterlist.size() > 0) {
					Parameter parameter = (Parameter) parameterlist.get(0);
					sb.append(parameter.getName());
				} else {
					sb.append("");
				}
				sb.append("\"]");
				jsonArray.add(sb.toString());
			}
			// 获取预约信息
			List<?> list_siteapply = service.findObjectBySql(
					"from FactoryApply f where f.member.id=? and f.placeDate>=? and f.factorycosts.factory.id=? and f.status!=3", new Object[] { memberId,
							sDate, Long.parseLong(place) });
			if (list_siteapply.size() > 0) {
				Iterator<?> it = list_siteapply.iterator();
				while (it.hasNext()) {
					FactoryApply sa = (FactoryApply) it.next();
					StringBuffer sb = new StringBuffer();
					if (sa.getStatus().equals("2")) {
						sb.append("[\"").append(sa.getId()).append("\",\"已预订");
					} else {
						sb.append("[\"").append(sa.getId()).append("\",\"已申请");
					}
					sb.append("\",\"").append((sa.getPlaceDate() + " " + sa.getStartTime()).replace("-", "/")).append("\",\"")
							.append((sa.getPlaceDate() + " " + sa.getEndTime()).replace("-", "/"));
					sb.append("\",\"0\",\"0\",\"0\",\"14\",\"1\",\"");
					// 获取俱乐部的名称
					List<?> memberlist = service.findObjectBySql("from Member where id=?", sa.getFactorycosts().getFactory().getClub());
					if (memberlist.size() > 0) {
						Member member = (Member) memberlist.get(0);
						sb.append(member.getName() + " " + sa.getFactorycosts().getFactory().getName());
					} else {
						sb.append("");
					}
					sb.append("\",\"\",\"\",\"\",\"").append("0").append("\",\"");
					// 获取运动项目类型名称
					List<?> parameterlist = service
							.findObjectBySql("from Parameter where id=?", Long.parseLong(sa.getFactorycosts().getFactory().getProject()));
					if (parameterlist.size() > 0) {
						Parameter parameter = (Parameter) parameterlist.get(0);
						sb.append(parameter.getName());
					} else {
						sb.append("");
					}
					sb.append("\"]");
					jsonArray.add(sb.toString());
				}
			}
			obj.put("events", jsonArray);
			response(obj.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取指定区间的预约信息
	 */
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
				sb.append("[\"").append(fc.getId()).append("\",\"");
				if (fc.getFactoryorders().size() > 0) {
					sb.append("已预订").append("\",\"").append(fc.getPlanDate().replace("-", "/")).append(" ").append(fc.getStarttime()).append("\",\"")
							.append(fc.getPlanDate().replace("-", "/")).append(" ").append(fc.getEndtime());
					sb.append("\",\"0\",\"0\",\"0\",\"14\",\"1\",\"\",\"\",\"");
				} else {
					sb.append("可预订").append("\",\"").append(fc.getPlanDate().replace("-", "/")).append(" ").append(fc.getStarttime()).append("\",\"")
							.append(fc.getPlanDate().replace("-", "/")).append(" ").append(fc.getEndtime());
					sb.append("\",\"0\",\"0\",\"0\",\"9\",\"1\",\"\",\"\",\"");
				}
				sb.append(fc.getCosts1()).append("\",\"").append(fc.getCosts2()).append("\"]");
				jsonArray.add(sb.toString());
			}

			obj.put("events", jsonArray);
			response(obj.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 查询预约条件的详细信息
	 * 
	 * @return
	 * @throws ParseException
	 */
	public String details() throws ParseException {
		if (factoryCosts.getId() != 0) {
			// 从数据库中获取
			List<?> list = service.findObjectBySql("from FactoryCosts fc where fc.id = ?", factoryCosts.getId());
			if (list.size() > 0) {
				factoryCosts = (FactoryCosts) list.get(0);
				if (factoryCosts.getFactoryorders().size() > 0) {
					factoryCosts.setCanEdit("Y");
				} else {
					factoryCosts.setCanEdit("");
				}
			}
		} else {
			String starttime = this.getStart();
			if (!starttime.isEmpty()) {
				String[] arrStartTime = format_ymdhm.format(format_ymdhm.parse(starttime)).split(" ");
				factoryCosts.setPlanDate(arrStartTime[0]);
				factoryCosts.setStarttime(arrStartTime[1]);
			}
			String endtime = this.getEnd();
			if (!endtime.isEmpty()) {
				factoryCosts.setEndtime(endtime.split(" ")[1]);
			}
			factoryCosts.setCanEdit("");
		}
		return "details";
	}

	/**
	 * 保存预约条件
	 */
	public void saveAndUpdateCondition() {
		if (factoryCosts.getId() == 0) {
			// 判断当前时间区段场地是否在用
			final List<?> olds = service
					.findObjectBySql(
							"from FactoryCosts f where f.factory.id = ? and f.planDate = ? and ((f.starttime = ? and f.endtime = ?) or (f.starttime < ? and f.endtime > ?) or (f.starttime < ? and f.endtime > ?) or (f.starttime > ? and f.endtime < ?)or (f.starttime < ? and f.endtime > ?))",
							new Object[] { factoryCosts.getFactory().getId(), factoryCosts.getPlanDate(), factoryCosts.getStarttime(), factoryCosts.getEndtime(),
											factoryCosts.getStarttime(), factoryCosts.getStarttime(), factoryCosts.getEndtime(), factoryCosts.getEndtime() , factoryCosts.getStarttime(), factoryCosts.getEndtime(), factoryCosts.getStarttime(), factoryCosts.getEndtime()});
			if (olds.size() > 0) {
				response("{success: false, desc: '当前日期的数据已经存在！'}");
				return;
			}
			// 增加
			factoryCosts.setId(null);
			factoryCosts.setConditionGp(factoryCosts.getFactory().getId() + "" + new Date().getTime());
			if (factoryCosts.getHasMode() != null && !factoryCosts.getHasMode().isEmpty()) {
				final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				List<FactoryCosts> _list = new ArrayList<FactoryCosts>();

				Date startDate = new Date();
				try {
					startDate = format_ymdhm.parse(factoryCosts.getRepeatStart() + " " + factoryCosts.getStarttime());
				} catch (ParseException e) {
					// do nothing
				}
				// 如果开始日期小于当前日期，会使用当前日期
				if (startDate.before(new Date())) {
					startDate = new Date();
				}

				Calendar c = Calendar.getInstance();
				c.setTimeInMillis(startDate.getTime());

				if (factoryCosts.getRepeatWhere().equalsIgnoreCase("1")) {
					// 次数
					int repeatNum = Integer.parseInt(factoryCosts.getRepeatNum());
					if (factoryCosts.getMode().equalsIgnoreCase("1")) {
						// 日
						int dayof = Integer.parseInt(factoryCosts.getDayof());
						for (int i = 0; i < repeatNum; i++) {
							if (new Date().before(c.getTime())) {
								// clone new factoryCosts
								FactoryCosts newFactoryCosts = (FactoryCosts) factoryCosts.clone();
								newFactoryCosts.setPlanDate(sdf.format(c.getTime()));
								_list.add(newFactoryCosts);
							}
							c.add(Calendar.DATE, dayof);
						}
					} else if (factoryCosts.getMode().equalsIgnoreCase("2")) {
						// 周
						String weekOf = factoryCosts.getWeekOf();
						weekOf = weekOf.substring(0, weekOf.length() - 1);
						String[] arrWeekOf = weekOf.split(",");
						int wookofsize = arrWeekOf.length;
						for (int i = 0; i < repeatNum; i++) {
							for (int j = 0; j < wookofsize; j++) {
								c.set(Calendar.DAY_OF_WEEK, Integer.parseInt(arrWeekOf[j]));
								if (new Date().before(c.getTime())) {
									// clone new factoryCosts
									FactoryCosts newFactoryCosts = (FactoryCosts) factoryCosts.clone();
									newFactoryCosts.setPlanDate(sdf.format(c.getTime()));
									_list.add(newFactoryCosts);
								}
							}
							c.add(Calendar.DATE, 7);
						}
					} else {
						// 月
						if (factoryCosts.getType().equalsIgnoreCase("0")) {
							// 每月第几天
							int day = Integer.parseInt(factoryCosts.getValue());
							for (int i = 0; i < repeatNum; i++) {
								c.set(Calendar.DAY_OF_MONTH, day);
								if (new Date().before(c.getTime())) {
									FactoryCosts newFactoryCosts = (FactoryCosts) factoryCosts.clone();
									newFactoryCosts.setPlanDate(sdf.format(c.getTime()));
									_list.add(newFactoryCosts);
								}
								// 下个月
								c.add(Calendar.MONTH, 1);
							}
						} else {
							// 每月的第几个星期的星期几
							int cycleweek = Integer.parseInt(factoryCosts.getCycle1());
							int cycleday = Integer.parseInt(factoryCosts.getCycle2());
							for (int i = 0; i < repeatNum; i++) {
								c.set(Calendar.DAY_OF_WEEK_IN_MONTH, cycleweek);
								c.set(Calendar.DAY_OF_WEEK, cycleday);
								if (new Date().before(c.getTime())) {
									FactoryCosts newFactoryCosts = (FactoryCosts) factoryCosts.clone();
									newFactoryCosts.setPlanDate(sdf.format(c.getTime()));
									_list.add(newFactoryCosts);
								}
								// 下个月
								c.add(Calendar.MONTH, 1);
							}

						}
					}
				} else {
					// 时间区间
					Date repeatEnd = new Date();
					try {
						repeatEnd = sdf.parse(factoryCosts.getRepeatEnd());
					} catch (ParseException e) {
						// do nothing
					}
					if (new Date().before(repeatEnd)) {
						if (factoryCosts.getMode().equalsIgnoreCase("1")) {
							// 日
							int dayof = Integer.parseInt(factoryCosts.getDayof());
							while (true) {
								if (new Date().before(c.getTime())) {
									// clone new factoryCosts
									FactoryCosts newFactoryCosts = (FactoryCosts) factoryCosts.clone();
									newFactoryCosts.setPlanDate(sdf.format(c.getTime()));
									_list.add(newFactoryCosts);
								}
								c.add(Calendar.DATE, dayof);
								if (repeatEnd.before(c.getTime())) {
									break;
								}
							}
						} else if (factoryCosts.getMode().equalsIgnoreCase("2")) {
							// 周
							String weekOf = factoryCosts.getWeekOf();
							weekOf = weekOf.substring(0, weekOf.length() - 1);
							String[] arrWeekOf = weekOf.split(",");
							int wookofsize = arrWeekOf.length;
							while (true) {
								for (int j = 0; j < wookofsize; j++) {
									c.set(Calendar.DAY_OF_WEEK, Integer.parseInt(arrWeekOf[j]));
									if (new Date().before(c.getTime())) {
										// clone new factoryCosts
										FactoryCosts newFactoryCosts = (FactoryCosts) factoryCosts.clone();
										newFactoryCosts.setPlanDate(sdf.format(c.getTime()));
										_list.add(newFactoryCosts);
									}
								}
								c.add(Calendar.DATE, 7);
								if (repeatEnd.before(c.getTime())) {
									break;
								}
							}
						} else {
							// 月
							if (factoryCosts.getType().equalsIgnoreCase("0")) {
								// 每月第几天
								int day = Integer.parseInt(factoryCosts.getValue());
								while (true) {
									c.set(Calendar.DAY_OF_MONTH, day);
									if (new Date().before(c.getTime())) {
										FactoryCosts newFactoryCosts = (FactoryCosts) factoryCosts.clone();
										newFactoryCosts.setPlanDate(sdf.format(c.getTime()));
										_list.add(newFactoryCosts);
									}
									// 下个月
									c.add(Calendar.MONTH, 1);
									if (repeatEnd.before(c.getTime())) {
										break;
									}
								}
							} else {
								// 每月的第几个星期的星期几
								int cycleweek = Integer.parseInt(factoryCosts.getCycle1());
								int cycleday = Integer.parseInt(factoryCosts.getCycle2());
								while (true) {
									c.set(Calendar.DAY_OF_WEEK_IN_MONTH, cycleweek);
									c.set(Calendar.DAY_OF_WEEK, cycleday);
									if (new Date().before(c.getTime())) {
										FactoryCosts newFactoryCosts = (FactoryCosts) factoryCosts.clone();
										newFactoryCosts.setPlanDate(sdf.format(c.getTime()));
										_list.add(newFactoryCosts);
									}
									// 下个月
									c.add(Calendar.MONTH, 1);
									if (repeatEnd.before(c.getTime())) {
										break;
									}
								}

							}
						}
					}
				}
				// save
				int listsize = _list.size();
				for (int i = 0; i < listsize; i++) {
					service.saveOrUpdate(_list.get(i));
				}
			} else {
				service.saveOrUpdate(factoryCosts);
			}

		} else {
			service.saveOrUpdate(factoryCosts);
		}
		response("{success: 'ok'}");
	}
	/**
	 * 更新，主要指移动预约内容
	 */
	public void update(){
		try{
			factoryCosts = (FactoryCosts) service.load(FactoryCosts.class, id);
			factoryCosts.setPlanDate(format_ymd.format(startDate));
			factoryCosts.setStarttime(format_hm.format(startDate));
			factoryCosts.setEndtime(format_hm.format(endDate));
			service.saveOrUpdate(factoryCosts);
			response(true, "ok");
		}catch(Exception e) {
			response(e);
		}
	}

	/**
	 * 删除预约条件
	 */
	public void deleteCondition() {
		if (factoryCosts.getId() != null && factoryCosts.getId() != 0) {
			service.delete(FactoryCosts.class, factoryCosts.getId());
			response("{success: 'ok'}");
		}
	}

}
