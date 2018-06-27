package ecartoon.wx.action;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;

import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.freegym.web.active.Active;
import com.freegym.web.basic.Member;
import com.freegym.web.config.Setting;
import com.freegym.web.mobile.BasicJsonAction;
import com.freegym.web.order.ActiveOrder;
import com.freegym.web.order.CourseOrder;
import com.freegym.web.order.Goods;
import com.freegym.web.utils.JSONUtils;
import com.sanmen.web.core.bean.BaseMember;

import ecartoon.wx.util.SaveActive;
import ecartoon.wx.util.loginCommons;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Namespace("")
@InterceptorRefs({ @InterceptorRef("customStack") })
@Results({ @Result(name = "findCourse", location = "/ecartoon-weixin/tk.jsp"),
		@Result(name = "findCourseDetail", location = "/ecartoon-weixin/tk_xq.jsp"),
		@Result(name = "loadPlan", location = "/ecartoon-weixin/zjxt.jsp"),
		@Result(name = "goMd", location = "/ecartoon-weixin/md.jsp"),
		@Result(name = "saveMemberSetting", location = "/ecartoon-weixin/md1.jsp"),
		@Result(name = "findActiveAndDetail", location = "/ecartoon-weixin/jstz.jsp"),
		@Result(name = "getActive1", location = "/ecartoon-weixin/challenge-xq.jsp"),
		@Result(name = "getActive2", location = "/ecartoon-weixin/tzxq_fb.jsp"),
		@Result(name = "goActive", location = "/ecartoon-weixin/tzxq_fb_cjtz.jsp"),
		@Result(name = "activeSave", location = "/ecartoon-weixin/challenge-xq.jsp"),
		@Result(name = "target", location = "/ecartoon-weixin/tzmb.jsp"),
		@Result(name = "reward", location = "/ecartoon-weixin/cgjl.jsp"),
		@Result(name = "punishment", location = "/ecartoon-weixin/sbcf.jsp"),
		@Result(name = "faqitiaozhan", location = "/ecartoon-weixin/faqitiaozhan.jsp"),
		@Result(name = "myActive", location = "/ecartoon-weixin/mine-challenge.jsp") })
public class ECourseWXManageAction extends BasicJsonAction {

	private static final long serialVersionUID = 1L;

	private File image;

	public void test() {
		
	}

	/**
	 * 淘课列表
	 */
	public String findCourse() {
		try {
			String[] status = { "1", "2" };
			Double longitude = null, latitude = null;
			JSONObject obj = new JSONObject();
			JSONArray course1 = new JSONArray();
			JSONArray course2 = new JSONArray();
			JSONArray course3 = new JSONArray();
			SimpleDateFormat simp = new SimpleDateFormat("yyyy-MM-dd");
			// 获取session中的member
			Member member = (Member) request.getSession().getAttribute("member");
			if (member == null) {
				request.setAttribute("status", 0);
				request.getRequestDispatcher("ecartoon-weixin/requestOpenId2.jsp").forward(request, response);
			} else {
				member = (Member) service.load(Member.class, member.getId());
				request.getSession().setAttribute("member", member);
			}

			String date1 = simp.format(new Date());
			String date2 = simp.format(new Date().getTime() + (24 * 60 * 60 * 1000));
			String date3 = simp.format(new Date().getTime() + (48 * 60 * 60 * 1000));

			if (request.getParameter("longitude") != null && request.getParameter("latitude") != null) {
				longitude = Double.parseDouble(request.getParameter("longitude"));
				latitude = Double.parseDouble(request.getParameter("latitude"));
			} else {
				longitude = member.getLongitude();
				latitude = member.getLatitude();
			}
			// 获取经纬度
			if (longitude != null && latitude != null) {
				if (status[0].equals(request.getParameter("ajax"))) {
					pageInfo = service.queryCourse(pageInfo, request.getParameter("date"), longitude, latitude);
					JSONArray course = JSONArray.fromObject(pageInfo.getItems());
					JSONUtils.bubbleSort(course, true, "distance");
					obj.accumulate("success", true).accumulate("items", course).accumulate("pageInfo",
							getJsonForPageInfo());
					response(obj);
					return null;
				}
				pageInfo = service.queryCourseByTime(pageInfo, date1, longitude, latitude);
				course1 = JSONArray.fromObject(pageInfo.getItems());
				pageInfo = service.queryCourse(pageInfo, date2, longitude, latitude);
				course2 = JSONArray.fromObject(pageInfo.getItems());
				pageInfo = service.queryCourse(pageInfo, date3, longitude, latitude);
				course3 = JSONArray.fromObject(pageInfo.getItems());
				JSONUtils.bubbleSort(course1, true, "distance");
				JSONUtils.bubbleSort(course2, true, "distance");
				JSONUtils.bubbleSort(course3, true, "distance");
				obj.accumulate("success", true).accumulate("longitude", longitude).accumulate("latitude", latitude);
			} else {
				obj.accumulate("success", false);
			}
			obj.accumulate("course1", course1).accumulate("course2", course2).accumulate("course3", course3)
					.accumulate("date1", date1).accumulate("date2", date2).accumulate("date3", date3);
			if (status[1].equals(request.getParameter("ajax"))) {
				response(obj);
				return null;
			}
			request.setAttribute("courseData", obj);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "findCourse";
	}

	/**
	 * 课程详情
	 */
	public String findCourseDetail() {
		try {

			String id = null;
			String orderId = null;
			Member member = (Member) session.getAttribute("member");
			if (member == null) {
				request.setAttribute("status", 0);
				request.getRequestDispatcher("ecartoon-weixin/requestOpenId.jsp").forward(request, response);
			} else {
				member = (Member) service.load(Member.class, member.getId());
				request.getSession().setAttribute("member", member);
			}
			if (request.getParameter("id") == null) {
				orderId = request.getParameter("orderId");
				CourseOrder courseOrder = (CourseOrder) service.load(CourseOrder.class, Long.valueOf(orderId));
				id = String.valueOf(courseOrder.getCourse().getId());
			} else {
				id = request.getParameter("id");
			}

			Map<String, Object> map = service.queryCourseDetail(id);
			JSONObject obj = new JSONObject();
			JSONArray jarr = new JSONArray();
			jarr.add(new JSONObject().accumulate("product", id).accumulate("productType", 5).accumulate("quantity", 1)
					.accumulate("unitPrice", map.get("hour_price") == null ? 0 : map.get("hour_price"))
					.accumulate("startTime", new SimpleDateFormat("yyyy-MM-dd").format(new Date())));
			obj.accumulate("success", true).accumulate("courseDetail", map).accumulate("json", jarr);
			request.setAttribute("courseDetail", obj);
			request.setAttribute("orderId", orderId == null ? 0 : orderId);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "findCourseDetail";
	}

	/**
	 * 检查当前用户本月是否可以再买课程
	 */
	public void checkBuyCourse() {
		Member member = (Member) session.getAttribute("member");
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
		SimpleDateFormat formats = new SimpleDateFormat("yyyy-MM-dd 23:59:59");
		Calendar cd = Calendar.getInstance();
		cd.add(Calendar.MONTH, 0);
		cd.set(Calendar.DAY_OF_MONTH, 1);// 设置为1号,当前日期既为本月第一天
		String first = format.format(cd.getTime());
		cd.set(Calendar.DAY_OF_MONTH, cd.getActualMaximum(Calendar.DAY_OF_MONTH));
		String last = formats.format(cd.getTime());
		List<?> list = service.queryForList(
				"SELECT * FROM tb_courserelease_order a WHERE a.orderDate <= ? AND a.orderDate > ? AND a.member = ? AND a.status != 0",
				last, first, member.getId());
		if (list.size() == 0) {
			response(new JSONObject().accumulate("success", true));
		} else {
			response(new JSONObject().accumulate("success", false));
		}
	}

	/**
	 * 私人订制详情 传参数 id=1为王严专家
	 */
	public String loadPlan() {
		try {
			Member member = (Member) request.getSession().getAttribute("member");
			if (member == null) {
				request.setAttribute("status", 0);
				request.getRequestDispatcher("ecartoon-weixin/requestOpenId2.jsp").forward(request, response);
			} else {
				member = (Member) service.load(Member.class, member.getId());
				request.getSession().setAttribute("member", member);
			}
			JSONObject obj = new JSONObject();
			Goods good = (Goods) service.load(Goods.class, Long.valueOf(1));
			// Member member = (Member) service.load(Member.class,
			// good.getMember());

			Goods goods = new Goods();
			goods.setPrice(150.0);
			Member m = (Member) request.getSession().getAttribute("member");
			goods.setMember(m.getId());
			goods.setName(good.getName());
			goods.setDescr(good.getDescr());
			goods.setSummary(good.getSummary());
			goods = (Goods) service.saveOrUpdate(goods);

			obj.accumulate("success", true)
					.accumulate("item", good.toJsons().accumulate("memberName", member.getName()))
					.accumulate("goodId", "1");
			request.setAttribute("data", obj);

			// if (StringUtils.isEmpty(member.getMobilephone())) {
			// // 用户未验证手机号，跳转到验证手机号的页面
			// request.getRequestDispatcher("ecartoon-weixin/saveMobile.jsp").forward(request,
			// response);
			// }
		} catch (Exception e) {
			log.error("error", e);
		}
		return "loadPlan";
	}

	/**
	 * .....
	 * 
	 * @return
	 */
	public String goMd() {
		request.setAttribute("goodId", request.getParameter("id"));
		return "goMd";
	}

	/**
	 * 保存会员设置
	 */
	public void saveMemberSetting() {
		try {
			long goodId = Long.valueOf(request.getParameter("id"));
			Member member = (Member) session.getAttribute("member");
			JSONObject obj = JSONObject.fromObject(URLDecoder.decode(jsons, "UTF-8"));
			Setting setting = new Setting();
			setting = service.loadSetting(member.getId());
			if (null == setting.getId()) {
				setting.setId(id);
			}
			setting.setMember(member.getId());
			String target = obj.getString("target");
			if ("A".equals(target)) {
				target = "1";
			} else if ("B".equals(target)) {
				target = "2";
			} else if ("C".equals(target)) {
				target = "3";
			} else if ("D".equals(target)) {
				target = "4";
			}
			setting.setStrengthDate(obj.getString("strengthDate"));
			setting.setHeight(obj.getInt("height"));
			setting.setWeight(obj.getDouble("weight"));
			setting.setWaistline(obj.getDouble("waistline"));
			setting.setMaxwm(obj.getDouble("maxwm"));
			setting.setFavoriateCardio(obj.getString("favoriateCardio"));

			if (obj.containsKey("st")) {
				member.setSex(obj.getString("st"));
				service.saveOrUpdate(member);
			}
			setting = (Setting) service.saveOrUpdate(setting);

			JSONArray jarr = new JSONArray();
			jarr.add(new JSONObject().accumulate("product", goodId).accumulate("productType", 6)
					.accumulate("quantity", 1).accumulate("unitPrice", 150)
					.accumulate("startTime", new SimpleDateFormat("yyyy-MM-dd").format(new Date())));

			response(new JSONObject().accumulate("jsons", jarr));
		} catch (Exception e) {
			log.error("error", e);
		}
	}

	/**
	 * 保存挑战数据
	 */
	public String activeSave() {
		try {
			JSONObject obj = (JSONObject) session.getAttribute("Active");
			String memo = "";
			try {
				memo = request.getParameter("memo");
			} catch (Exception e) {
				memo = "注意不要受伤哦";
			}
			obj.accumulate("memo", memo);
			Active a = new Active();
			Member m = (Member) session.getAttribute("member");
			m = (Member) service.load(Member.class, m.getId());
			a.setStatus('B');
			a.setCreator(m);
			a.setName(obj.getString("name"));
			a.setDays(obj.getInt("days"));
			a.setTarget(obj.getString("target").charAt(0));
			a.setMemo(obj.getString("memo"));
			a.setMode("A".charAt(0));
			a.setJoinMode("A".charAt(0));
			a.setAward(obj.getString("award"));
			a.setAmerceMoney(obj.getDouble("amerceMoney"));
			a.setInstitution(new Member(obj.getLong("institutionId")));
			a.setCategory(a.getTarget());
			a.setValue(0.01);
			a.setCreateTime(new Date());
			String fileName1 = image != null ? saveFile("picture", image, image.getName(), null) : null;
			if (fileName1 != null) {
				a.setImage(fileName1);
			}
			a = (Active) service.saveOrUpdate(a);
			request.setAttribute("id", a.getId());
		} catch (Exception e) {
			e.printStackTrace();
		}

		findActiveAndDetail();
		return "findActiveAndDetail";
	}

	/**
	 * 发起挑战跳转
	 * 
	 * @return
	 */
	public String faqitiaozhan() {
		int id = Integer.parseInt(request.getParameter("id") == null ? "0" : request.getParameter("id"));
		if (session.getAttribute("Active") != null) {
			SaveActive.active = (JSONObject) session.getAttribute("Active");
		}
		if (id == 1) {
			if (SaveActive.active.get("name") == null) {
				SaveActive.active.accumulate("name", getChinaStr(request.getParameter("name")));
			}
			int type = Integer.parseInt(request.getParameter("type"));
			if (type == 1) {
				return "target";
			} else if (type == 2) {
				return "reward";
			} else if (type == 3) {
				return "punishment";
			}
		}
		if (id == 2) {
			if (SaveActive.active.get("days") == null) {
				SaveActive.active.accumulate("days", request.getParameter("days")).accumulate("target", j());
			}
		}
		if (id == 3) {
			SaveActive.active.accumulate("award", request.getParameter("award"));
		}
		if (id == 4) {
			if (SaveActive.active.get("institutionId") == null) {
				SaveActive.active.accumulate("amerceMoney", request.getParameter("money")).accumulate("institutionId",
						9355);
			}
		}
		session.setAttribute("Active", SaveActive.active);
		return "faqitiaozhan";
	}

	public String j() {
		int count = (int) Math.round(Math.random() * 3);
		return new String[] { "A", "B", "C", "D" }[count];
	}

	/**
	 * 查询挑战列表
	 */
	@SuppressWarnings("deprecation")
	public String findActiveAndDetail() {
		try {
			Member member = (Member) request.getSession().getAttribute("member");
			if (member == null) {
				request.setAttribute("status", 0);
				request.getRequestDispatcher("ecartoon-weixin/requestOpenId2.jsp").forward(request, response);
			} else {
				member = (Member) service.load(Member.class, member.getId());
				request.getSession().setAttribute("member", member);
			}
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			// 挑战类型 0.所有挑战,1.我发起的挑战,2.我参加的挑战
			Integer type = 0;
			if (request.getParameter("type") != null) {
				type = Integer.parseInt(request.getParameter("type"));
			}
			// 挑战目标:A体重减少,B体重增加,D运动次数
			Character target = 'A';
			if (request.getParameter("target") != null) {
				target = request.getParameter("target").charAt(0);
			}
			// 搜索关键字
			String keyword = request.getParameter("keyword") == null ? null
					: getChinaStr(request.getParameter("keyword"));
			// 挑战模式:A 个人挑战
			String circle = request.getParameter("circle");
			// 挑战周期 :A:小于10天 ,B:11-30天,C:31-90天 D:大于90天
			Character mode = request.getParameter("mode") == null ? null : request.getParameter("mode").charAt(0);

			BaseMember mu = getLoginMember();
			JSONArray jarr = new JSONArray();
			if (type == 0 || type == 1) {
				final DetachedCriteria dc = DetachedCriteria.forClass(Active.class);
				dc.createAlias("creator", "c", Criteria.LEFT_JOIN);
				if (keyword != null && !"".equals(keyword)) {
					dc.add(this.or(Restrictions.like("name", keyword), Restrictions.like("c.nick", keyword),
							Restrictions.like("c.email", keyword), Restrictions.like("c.mobilephone", keyword)));
				}
				if (type == 0) {
					dc.add(Restrictions.eq("status", 'B'));
					if (null != target && !"".equals(target)) {
						if (target == 'A') {
							dc.add(Restrictions.or(Restrictions.eq("category", 'A'), Restrictions.eq("category", 'B')));
						} else if (target == 'C') {
							dc.add(Restrictions.eq("category", 'E'));
						} else if (target == 'D') {
							dc.add(Restrictions.eq("category", 'D'));
						} else {
							dc.add(Restrictions.in("category", new Object[] { 'C', 'F', 'G', 'H' }));
						}
					}
				} else {
					dc.add(Restrictions.eq("c.id", mu.getId()));
					if (null != target && !"".equals(target))
						dc.add(Restrictions.eq("target", target));
				}
				if (null != mode && !"".equals(mode)) {
					dc.add(Restrictions.eq("mode", mode));
				}
				if (circle != null && !"".equals(circle)) {
					if ("A".equals(circle)) {
						dc.add(Restrictions.le("days", 10));
					} else if ("B".equals(circle)) {
						dc.add(Restrictions.ge("days", 11));
						dc.add(Restrictions.le("days", 30));
					} else if ("C".equals(circle)) {
						dc.add(Restrictions.ge("days", 31));
						dc.add(Restrictions.le("days", 90));
					} else if ("D".equals(circle)) {
						dc.add(Restrictions.ge("days", 91));
					}
				}
				pageInfo.setOrder("createTime");
				pageInfo.setOrderFlag("desc");
				pageInfo = service.findPageByCriteria(dc, pageInfo);
				for (final Iterator<?> it = pageInfo.getItems().iterator(); it.hasNext();) {
					final Object[] objs = (Object[]) it.next();
					final Active m = (Active) objs[1];
					final JSONObject obj = new JSONObject();
					obj.accumulate("id", m.getId()).accumulate("member", getMemberJson(m.getCreator()))
							.accumulate("name", getString(m.getName())).accumulate("mode", getString(m.getMode()))
							.accumulate("days", getInteger(m.getDays()))
							.accumulate("teamNum", getInteger(m.getTeamNum()))
							.accumulate("target", getString(m.getTarget())).accumulate("award", getString(m.getAward()))
							.accumulate("institution", getJsonForMember(m.getInstitution()))
							.accumulate("amerceMoney", getDouble(m.getAmerceMoney()))
							.accumulate("category", getString(m.getCategory()))
							.accumulate("status", getString(m.getStatus()))
							.accumulate("action", getString(m.getAction())).accumulate("value", getDouble(m.getValue()))
							.accumulate("applyCount", applyCount(m.getId()))
							.accumulate("image", getString(m.getImage())).accumulate("memo", getString(m.getMemo()))
							.accumulate("result", null).accumulate("startTime", null).accumulate("endTime", null)
							.accumulate("weight", null).accumulate("activeCount", null).accumulate("lastWeight", null)
							.accumulate("activeId", null);
					jarr.add(obj);
				}
			} else {
				final DetachedCriteria dc = DetachedCriteria.forClass(ActiveOrder.class);
				dc.add(Restrictions.eq("member.id", mu.getId()));
				dc.add(Restrictions.and(Restrictions.ne("status", '1'), Restrictions.isNotNull("status")));
				pageInfo.setOrder("orderDate");
				pageInfo.setOrderFlag("desc");
				pageInfo = service.findPageByCriteria(dc, pageInfo);
				for (final Iterator<?> it = pageInfo.getItems().iterator(); it.hasNext();) {
					final ActiveOrder ao = (ActiveOrder) it.next();
					final JSONObject obj = new JSONObject();
					int a = ao.getActive().getDays();// 挑战天数
					Date startDate = ao.getOrderStartTime();// 开始时间
					if (startDate == null) {
						startDate = new Date(System.currentTimeMillis());
					}
					Calendar calendar = Calendar.getInstance();
					calendar.setTime(startDate);
					calendar.add(Calendar.DATE, a);
					Date endDate = calendar.getTime();// 结束时间
					// 活动状态：0为进行中，1为成功，2为失败，3为已结束
					String targetStr = getString(ao.getActive().getTarget());
					// 健身目标: A体重减少，B体重增加，C运动时间，D运动次数，E自定义目标
					if ("A".equalsIgnoreCase(targetStr)) {
						targetStr = "体重减少";
					} else if ("B".equalsIgnoreCase(targetStr)) {
						targetStr = "体重增加";
					} else if ("C".equalsIgnoreCase(targetStr)) {
						targetStr = "运动时间";
					} else if ("D".equalsIgnoreCase(targetStr)) {
						targetStr = "运动次数";
					} else if ("E".equalsIgnoreCase(targetStr)) {
						targetStr = "自定义目标";
					}
					Character result = ao.getResult().equals('0') ? '0'
							: (ao.getResult() != null ? ao.getResult() : '3');
					obj.accumulate("id", ao.getId()).accumulate("member", getMemberJson(ao.getActive().getCreator()))
							.accumulate("name", getString(ao.getActive().getName()))
							.accumulate("days", getInteger(ao.getActive().getDays()))
							.accumulate("teamNum", getInteger(ao.getActive().getTeamNum()))
							.accumulate("target", targetStr).accumulate("award", getString(ao.getActive().getAward()))
							.accumulate("institution", getMemberJson(ao.getActive().getInstitution()))
							.accumulate("amerceMoney", getDouble(ao.getActive().getAmerceMoney()))
							.accumulate("category", getString(ao.getActive().getCategory()))
							.accumulate("status", getString(ao.getStatus()))
							.accumulate("action", getString(ao.getActive().getName()))
							.accumulate("value", getDouble(ao.getActive().getValue()))
							.accumulate("applyCount", applyCount(ao.getActive().getId()))
							.accumulate("image", getString(ao.getActive().getImage()))
							.accumulate("startTime", sdf.format(ao.getOrderStartTime()))
							.accumulate("endTime", sdf.format(endDate)).accumulate("weight", ao.getWeight())
							.accumulate("lastWeight", ao.getLastWeight())
							.accumulate("activeCount", this.signCount(ao.getId(), endDate)).accumulate("result", result)
							.accumulate("activeId", ao.getActive().getId())
							.accumulate("memo", ao.getActive().getMemo());
					jarr.add(obj);
				}
			}
			final JSONObject ret = new JSONObject();
			ret.accumulate("success", true).accumulate("pageInfo", getJsonForPageInfo()).accumulate("items", jarr);

			if ("1".equals(request.getParameter("ajax"))) {
				response(ret);
				return null;
			}

			request.setAttribute("data", ret);

			// if (StringUtils.isEmpty(member.getMobilephone())) {
			// // 用户未验证手机号，跳转到验证手机号的页面
			// request.getRequestDispatcher("ecartoon-weixin/saveMobile.jsp").forward(request,
			// response);
			// }
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "findActiveAndDetail";
	}

	/**
	 * 计算挑战活动参与总数
	 */
	public Long applyCount(Long id) {
		Long count = null;
		if (id != null) {
			count = service.queryForLong(
					"select count(*) from tb_active_order ci where ci.active = ? and ci.status != '0' ", id);
		}
		return count;
	}

	/**
	 * 挑战健身次数
	 * 
	 * @param id
	 * @return
	 */
	public Long signCount(Long id, Date date) {
		SimpleDateFormat ymd = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
		Long count = null;
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, 1);// 今天+1天
		Date endDate = calendar.getTime();
		ActiveOrder ao = (ActiveOrder) service.load(ActiveOrder.class, id);
		if (id != null) {
			Member member = (Member) session.getAttribute("member");
			count = service.queryForLong(
					"select count(*) from tb_sign_in where  signDate>= ? and signDate < ? and memberSign=?",
					ymd.format(ao.getOrderStartTime() == null ? new Date() : ao.getOrderStartTime()),
					ymd.format(endDate), member.getId());
		}
		return count;
	}

	/**
	 * 去参加挑战
	 * 
	 * @return
	 */
	public String goActive() {
		request.setAttribute("id", request.getParameter("id"));
		id = Long.valueOf(request.getParameter("id"));
		getActive();
		return "goActive";
	}

	/**
	 * 我的挑战
	 */
	@SuppressWarnings("deprecation")
	public String myActive() {
		try {
			Member member = (Member) request.getSession().getAttribute("member");
			if (member == null) {
				member = new loginCommons().setMember(request, service);
				request.getSession().setAttribute("member", member);
			} else {
				member = (Member) service.load(Member.class, member.getId());
				request.getSession().setAttribute("member", member);
			}
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			// 挑战类型
			Integer type = 0;
			if (request.getParameter("type") != null) {
				type = Integer.parseInt(request.getParameter("type"));
			}
			// 挑战目标:A体重减少,B体重增加,D运动次数
			Character target = 'A';
			if (request.getParameter("target") != null) {
				target = request.getParameter("target").charAt(0);
			}
			// 搜索关键字
			String keyword = request.getParameter("keyword") == null ? null
					: getChinaStr(request.getParameter("keyword"));
			// 挑战模式:A 个人挑战
			String circle = request.getParameter("circle");
			// 挑战周期 :A:小于10天 ,B:11-30天,C:31-90天 D:大于90天
			Character mode = request.getParameter("mode") == null ? null : request.getParameter("mode").charAt(0);

			Member mu = (Member) session.getAttribute("member");
			JSONArray jarr = new JSONArray();
			if (type == 0 || type == 1) {
				final DetachedCriteria dc = DetachedCriteria.forClass(Active.class);
				dc.createAlias("creator", "c", Criteria.LEFT_JOIN);
				if (keyword != null && !"".equals(keyword)) {
					dc.add(this.or(Restrictions.like("name", keyword), Restrictions.like("c.nick", keyword),
							Restrictions.like("c.email", keyword), Restrictions.like("c.mobilephone", keyword)));
				}
				if (type == 0) {
					dc.add(Restrictions.eq("status", 'B'));
					if (null != target && !"".equals(target)) {
						if (target == 'A') {
							dc.add(Restrictions.or(Restrictions.eq("category", 'A'), Restrictions.eq("category", 'B')));
						} else if (target == 'C') {
							dc.add(Restrictions.eq("category", 'E'));
						} else if (target == 'D') {
							dc.add(Restrictions.eq("category", 'D'));
						} else {
							dc.add(Restrictions.in("category", new Object[] { 'C', 'F', 'G', 'H' }));
						}
					}
				} else {
					dc.add(Restrictions.eq("c.id", mu.getId()));
					/*
					 * if (null != target && !"".equals(target)) dc.add(Restrictions.eq("target",
					 * target));
					 */
				}
				if (null != mode && !"".equals(mode)) {
					dc.add(Restrictions.eq("mode", mode));
				}
				if (circle != null && !"".equals(circle)) {
					if ("A".equals(circle)) {
						dc.add(Restrictions.le("days", 10));
					} else if ("B".equals(circle)) {
						dc.add(Restrictions.ge("days", 11));
						dc.add(Restrictions.le("days", 30));
					} else if ("C".equals(circle)) {
						dc.add(Restrictions.ge("days", 31));
						dc.add(Restrictions.le("days", 90));
					} else if ("D".equals(circle)) {
						dc.add(Restrictions.ge("days", 91));
					}
				}
				pageInfo.setOrder("createTime");
				pageInfo.setOrderFlag("desc");
				pageInfo = service.findPageByCriteria(dc, pageInfo);
				for (final Iterator<?> it = pageInfo.getItems().iterator(); it.hasNext();) {
					final Object[] objs = (Object[]) it.next();
					final Active m = (Active) objs[1];
					final JSONObject obj = new JSONObject();
					obj.accumulate("id", m.getId()).accumulate("member", getMemberJson(m.getCreator()))
							.accumulate("name", getString(m.getName())).accumulate("mode", getString(m.getMode()))
							.accumulate("days", getInteger(m.getDays()))
							.accumulate("teamNum", getInteger(m.getTeamNum()))
							.accumulate("target", getString(m.getTarget())).accumulate("award", getString(m.getAward()))
							.accumulate("institution", getJsonForMember(m.getInstitution()))
							.accumulate("amerceMoney", getDouble(m.getAmerceMoney()))
							.accumulate("category", getString(m.getCategory()))
							.accumulate("status", getString(m.getStatus()))
							.accumulate("action", getString(m.getAction())).accumulate("value", getDouble(m.getValue()))
							.accumulate("applyCount", applyCount(m.getId()))
							.accumulate("image", getString(m.getImage())).accumulate("memo", getString(m.getMemo()))
							.accumulate("result", null).accumulate("startTime", null).accumulate("endTime", null)
							.accumulate("weight", null).accumulate("activeCount", null).accumulate("lastWeight", null)
							.accumulate("activeId", null);
					jarr.add(obj);
				}
			} else {
				final DetachedCriteria dc = DetachedCriteria.forClass(ActiveOrder.class);
				dc.add(Restrictions.eq("member.id", mu.getId()));
				dc.add(Restrictions.and(Restrictions.ne("status", '0'), Restrictions.isNotNull("status")));
				pageInfo.setOrder("orderDate");
				pageInfo.setOrderFlag("desc");
				pageInfo = service.findPageByCriteria(dc, pageInfo);
				for (final Iterator<?> it = pageInfo.getItems().iterator(); it.hasNext();) {
					final ActiveOrder ao = (ActiveOrder) it.next();
					final JSONObject obj = new JSONObject();
					int a = ao.getActive().getDays();// 挑战天数
					Date startDate = ao.getOrderStartTime();// 开始时间
					if (startDate == null) {
						startDate = new Date(System.currentTimeMillis());
					}
					Calendar calendar = Calendar.getInstance();
					calendar.setTime(startDate);
					calendar.add(Calendar.DATE, a);
					Date endDate = calendar.getTime();// 结束时间
					// 活动状态：0为进行中，1为成功，2为失败，3为已结束
					// String targetStr = getString(ao.getActive().getTarget());
					// // 健身目标: A体重减少，B体重增加，C运动时间，D运动次数，E自定义目标
					// if ("A".equalsIgnoreCase(targetStr)) {
					// targetStr = "体重减少";
					// } else if ("B".equalsIgnoreCase(targetStr)) {
					// targetStr = "体重增加";
					// } else if ("C".equalsIgnoreCase(targetStr)) {
					// targetStr = "运动时间";
					// } else if ("D".equalsIgnoreCase(targetStr)) {
					// targetStr = "运动次数";
					// } else if ("E".equalsIgnoreCase(targetStr)) {
					// targetStr = "自定义目标";
					// }
					Character result = ao.getResult() != null ? ao.getResult() : '3';
					obj.accumulate("id", ao.getId()).accumulate("member", getMemberJson(ao.getActive().getCreator()))
							.accumulate("name", getString(ao.getActive().getName()))
							.accumulate("days", getInteger(ao.getActive().getDays()))
							.accumulate("teamNum", getInteger(ao.getActive().getTeamNum()))
							.accumulate("target", ao.getActive().getTarget())
							.accumulate("award", getString(ao.getActive().getAward()))
							.accumulate("institution", getMemberJson(ao.getActive().getInstitution()))
							.accumulate("amerceMoney", getDouble(ao.getActive().getAmerceMoney()))
							.accumulate("category", getString(ao.getActive().getCategory()))
							.accumulate("status", getString(ao.getStatus()))
							.accumulate("action", getString(ao.getActive().getName()))
							.accumulate("value", getDouble(ao.getActive().getValue()))
							.accumulate("applyCount", applyCount(ao.getActive().getId()))
							.accumulate("image", getString(ao.getActive().getImage()))
							.accumulate("startTime", startDate).accumulate("endTime", sdf.format(endDate))
							.accumulate("weight", ao.getWeight()).accumulate("lastWeight", ao.getLastWeight())
							.accumulate("activeCount", this.signCount(ao.getId(), endDate)).accumulate("result", result)
							.accumulate("activeId", ao.getActive().getId())
							.accumulate("memo", ao.getActive().getMemo());
					jarr.add(obj);
				}
			}
			final JSONObject ret = new JSONObject();
			ret.accumulate("success", true).accumulate("pageInfo", getJsonForPageInfo()).accumulate("items", jarr);

			if ("1".equals(request.getParameter("ajax"))) {
				response(ret);
				return null;
			}

			request.setAttribute("data", ret);

			// if (StringUtils.isEmpty(member.getMobilephone())) {
			// // 用户未验证手机号，跳转到验证手机号的页面
			// request.getRequestDispatcher("ecartoon-weixin/saveMobile.jsp").forward(request,
			// response);
			// }
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		return "myActive";
	}

	/**
	 * 把挑战对象转换为json格式
	 * 
	 * @param list
	 * @param id
	 * @return
	 */
	public String getActiveJson(List<Map<String, Object>> list, long id) {
		JSONArray jarr = new JSONArray();
		if (list.size() > 0) {
			for (Map<String, Object> m : list) {
				jarr.add(new JSONObject().accumulate("id", m.get("id")).accumulate("name", m.get("name"))
						.accumulate("applyCount", applyCount(id)).accumulate("memo", m.get("memo"))
						.accumulate("success", Math.round(Math.random() * 3)).accumulate("image", m.get("image")));
			}
		}
		return JSONArray.fromObject(jarr).toString();
	}

	/**
	 * 挑战详情
	 */
	public void getActive() {

		try {
			// 确保用户是登录的状态
			Member member = (Member) session.getAttribute("member");
			if (member == null) {
				request.setAttribute("status", 0);
				request.getRequestDispatcher("ecartoon-weixin/requestOpenId.jsp").forward(request, response);
			} else {
				member = (Member) service.load(Member.class, member.getId());
				request.getSession().setAttribute("member", member);
			}

			String type = request.getParameter("type");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			if ("1".equals(type) || "4".equals(type)) {
				String orderId = null;
				if (id == null) {
					orderId = request.getParameter("orderId");
					id = Long.valueOf(orderId);
				}
				ActiveOrder ao = (ActiveOrder) service.load(ActiveOrder.class, id);
				Active a = ao.getActive();
				JSONObject obj1 = new JSONObject();

				// 活动状态：0为进行中，1为成功，2为失败，3为已结束
				String targetStr = String.valueOf(a.getTarget());
				// 健身目标: A体重减少，B体重增加，C运动时间，D运动次数，E自定义目标
				Date orderStartTime = ao.getOrderStartTime() == null ? new Date() : ao.getOrderStartTime();
				Date orderEndTime = ao.getOrderEndTime() == null ? new Date() : ao.getOrderEndTime();
				String createTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
						.format(a.getCreateTime() == null ? new Date() : a.getCreateTime());
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(orderStartTime);
				calendar.add(Calendar.DAY_OF_MONTH, a.getDays() - 1);
				Date endDate = calendar.getTime(); // 结束时间
				String endTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime());
				obj1.accumulate("id", a.getId()).accumulate("orderId", ao.getId())
						.accumulate("member", a.getCreator().toJson()).accumulate("name", getString(a.getName()))
						.accumulate("mode", getString(a.getMode())).accumulate("days", getInteger(a.getDays()))
						.accumulate("teamNum", getInteger(a.getTeamNum())).accumulate("target", targetStr)
						.accumulate("award", getString(a.getAward()))
						.accumulate("institution", getJsonForMember(a.getInstitution()))
						.accumulate("amerceMoney", getDouble(a.getAmerceMoney()))
						.accumulate("joinMode", getString(a.getJoinMode()))
						.accumulate("category", getString(a.getCategory()))
						.accumulate("status", getString(a.getStatus())).accumulate("action", getString(a.getAction()))
						.accumulate("value", getDouble(a.getValue())).accumulate("image", getString(a.getImage()))
						.accumulate("memo", getString(a.getMemo())).accumulate("content", getString(a.getContent()))
						.accumulate("customTareget", getString(a.getCustomTareget()))
						.accumulate("unit", getString(a.getUnit()))
						.accumulate("evaluationMethod", getString(a.getEvaluationMethod()))
						.accumulate("applyCount", applyCount(a.getId())).accumulate("startTime", createTime)
						.accumulate("endTime", endTime).accumulate("orderStartTime", sdf.format(orderStartTime))
						.accumulate("orderEndTime", sdf.format(endDate)).accumulate("weight", ao.getWeight())
						.accumulate("activeCount", this.signCount(ao.getId(), orderEndTime))
						.accumulate("result", ao.getResult()).accumulate("lastWeight", ao.getLastWeight());
				request.setAttribute("data", obj1);
				request.setAttribute("orderId", orderId == null ? 0 : orderId);
			} else {
				Active a = (Active) service.load(Active.class, id);
				JSONObject obj1 = new JSONObject();
				// 活动状态：0为进行中，1为成功，2为失败，3为已结束
				String targetStr = String.valueOf(a.getTarget());
				// 健身目标: A体重减少，B体重增加，C运动时间，D运动次数，E自定义目标
				String createTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
						.format(a.getCreateTime() == null ? new Date() : a.getCreateTime());
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(a.getCreateTime());
				calendar.add(Calendar.DAY_OF_MONTH, a.getDays());
				String endTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime());
				obj1.accumulate("id", a.getId()).accumulate("member", a.getCreator().toJson())
						.accumulate("name", getString(a.getName())).accumulate("mode", getString(a.getMode()))
						.accumulate("days", getInteger(a.getDays())).accumulate("teamNum", getInteger(a.getTeamNum()))
						.accumulate("target", targetStr).accumulate("award", getString(a.getAward()))
						.accumulate("institution", getJsonForMember(a.getInstitution()))
						.accumulate("amerceMoney", getDouble(a.getAmerceMoney()))
						.accumulate("joinMode", getString(a.getJoinMode()))
						.accumulate("category", getString(a.getCategory()))
						.accumulate("status", getString(a.getStatus())).accumulate("action", getString(a.getAction()))
						.accumulate("value", getDouble(a.getValue())).accumulate("image", getString(a.getImage()))
						.accumulate("memo", getString(a.getMemo())).accumulate("content", getString(a.getContent()))
						.accumulate("customTareget", getString(a.getCustomTareget()))
						.accumulate("unit", getString(a.getUnit()))
						.accumulate("evaluationMethod", getString(a.getEvaluationMethod()))
						.accumulate("applyCount", applyCount(id)).accumulate("startTime", createTime)
						.accumulate("endTime", endTime).accumulate("weight", number())
						.accumulate("result", request.getParameter("result"));

				request.setAttribute("orderId", "0");
				request.setAttribute("data", obj1);
			}

			if ("1".equals(type)) {
				request.getRequestDispatcher("ecartoon-weixin/challenge-xq.jsp").forward(request, response);
			} else if ("2".equals(type)) {
				request.getRequestDispatcher("ecartoon-weixin/tzxq_fb.jsp").forward(request, response);
			} else if ("3".equals(type)) {
				request.getRequestDispatcher("ecartoon-weixin/tzxq_fb.jsp").forward(request, response);
			} else if ("4".equals(type)) {
				request.getRequestDispatcher("ecartoon-weixin/tzxq_fb.jsp").forward(request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 挑战成绩提交
	 */
	public void submitWeight() {
		Double weight = new Double(request.getParameter("weight"));// 用户填写的体重
		service.saveActiveResult(weight, id);
		ActiveOrder ao = (ActiveOrder) service.load(ActiveOrder.class, id);
		JSONObject obj = new JSONObject();
		ao.setLastWeight(weight);
		ao = (ActiveOrder) service.saveOrUpdate(ao);
		obj.accumulate("success", true).accumulate("code", ao.getResult());
		response(obj);
	}

	/**
	 * 
	 * @return
	 */
	private int number() {
		List<Integer> list = new ArrayList<Integer>();
		for (int i = 0; i < 20; i++) {
			list.add(60 + i);
		}
		try {
			return list.get(Integer.parseInt(String.valueOf(Math.round(((Math.random() * 20))))) > 19 ? 19
					: Integer.parseInt(String.valueOf(Math.round(((Math.random() * 20) + 1)))));
		} catch (Exception e) {
			return list.get(0);
		}
	}

	/**
	 * getter and setter
	 * 
	 * @return
	 */
	public File getImage() {
		return image;
	}

	public void setImage(File image) {
		this.image = image;
	}
}
