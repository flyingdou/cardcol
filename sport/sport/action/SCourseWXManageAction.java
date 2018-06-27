package sport.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.databaseConnection.DataBaseConnection;
import com.freegym.web.basic.Member;
import com.freegym.web.mobile.BasicJsonAction;
import com.freegym.web.order.Product;
import com.freegym.web.plan.Workout;
import com.freegym.web.plan.WorkoutDetail;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Namespace("")
@InterceptorRefs({ @InterceptorRef("customStack") })
@Results({ @Result(name = "privateMealPackage", location = "/sport/private_meal_package.jsp"),
		@Result(name = "privateMealPackageDetail", location = "/sport/package_detail.jsp"),
		@Result(name = "products", location = "/sport/product_list.jsp"),
		@Result(name = "productDetail", location = "/sport/product_detail.jsp"),
		@Result(name = "goMake", location = "/sport/product_make.jsp"),
		@Result(name = "cancelMake", location = "/sport/product_list.jsp"),
		@Result(name = "myPlan", location = "/sport/my_plan.jsp"),
		@Result(name = "myPlanDetail", location = "/sport/group.jsp") })
public class SCourseWXManageAction extends BasicJsonAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 查询私教套餐列表
	 */
	public String findProduct() {
		id = Long.parseLong("290");// 教练id
		try {
			String status = "1";
			final DetachedCriteria dc = DetachedCriteria.forClass(Product.class);
			final Member m = (Member) service.load(Member.class, id);
			dc.add(Restrictions.eq("member.id", id));
			if (m.getRole().equals("S")) {
				dc.add(Restrictions.eq("audit", '1'));
			} else {
				dc.add(Restrictions.eq("audit", '1'));
				dc.add(Restrictions.eq("isClose", "2"));
			}
			pageInfo.setOrder("topTime");
			pageInfo.setOrderFlag("desc");
			pageInfo = service.findPageByCriteria(dc, pageInfo);
			
			if(status.equals(request.getParameter("ajax"))){
				response(JSONArray.fromObject(pageInfo.getItems()));
			}
			request.setAttribute("data", pageInfo);
		} catch (Exception e) {
			log.error("error", e);
		}
		return "privateMealPackage";
	}

	/**
	 * 查看私教套餐详情
	 */
	public String privateMealPackageDetail() {
		String pakcageId = request.getParameter("id");
		String sql = "SELECT *,(SELECT COUNT(id) FROM tb_product_order WHERE product = ?) AS count FROM tb_product p WHERE id = ?";
		pageInfo = service.findPageBySql(sql, pageInfo, new Object[] { pakcageId, pakcageId });
		request.setAttribute("pageInfo", JSONObject.fromObject(pageInfo));
		request.setAttribute("date", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		return "privateMealPackageDetail";
	}

	/**
	 * 需要预约的课程列表
	 * 
	 * @return
	 */
	@SuppressWarnings({ "deprecation", "unchecked", "null", "rawtypes" })
	public String products() {
		request.getSession().setAttribute("member", service.load(Member.class, Long.valueOf("9388")));
		Date planDate = new Date();
		if (request.getParameter("planDate") != null) {
			planDate = new Date(request.getParameter("planDate").replace("-", "/"));
		}
		String date = new SimpleDateFormat("yyyy-MM-dd").format(planDate);
		List<Map<String,Object>> list = null;
		JSONArray jarr = new JSONArray();
		JSONObject ret = new JSONObject();
		Member mu = (Member) session.getAttribute("member");
		// 查询会员是否有已经购买的套餐(在有效期内)
		List<Map<String,Object>> listPro = getProducts(mu);
		if (listPro == null || listPro.size() == 0) {
			ret.accumulate("isHave", false);
		} else {
			ret.accumulate("isHave", true);
		}
		if (listPro != null || listPro.size() > 0) {
			if ("M".equals(mu.getRole())) {
				// 获得当前登录用户的私教id
				String coachId = DataBaseConnection.getOne("select m.id from tb_member m inner join tb_member mm "
						+ "on m.id=mm.coach where mm.id=" + mu.getId(), null).get("id").toString();
				// 通过当前的私教id查询教练的所有预约
				String sql = "select co.id,p.name,p.image1 image,co.startTime,co.endTime"
						+ " from tb_member m inner join tb_member mm on m.coach = mm.id inner join"
						+ " tb_product p on mm.id = p.member inner join tb_product_order po"
						+ " on po.member = m.id and  po.product = p.id inner join tb_course co"
						+ " on co.coach = mm.id where po.orderStartTime > ? and po.orderEndTime < ?"
						+ " and planDate = ? and mm.id = ?";
				list = DataBaseConnection.getList(sql, new Object[] { date, date, date, coachId });
				// 根据id查询教练
				Member coach = (Member) service.load(Member.class, Long.valueOf(coachId));
				if (list != null && list.size() > 0) {
					JSONObject obj = null;
					for (Map map : list) {
						obj = new JSONObject();
						// 此处为教练对象
						obj.accumulate("id", map.get("id")).accumulate("name", map.get("name"))
								.accumulate("member", getMemberJson(coach)).accumulate("image", map.get("image"))
								.accumulate("startTime", map.get("startTime"))
								.accumulate("endTime", map.get("endTime"));
						jarr.add(obj);
					}
				}
				ret.accumulate("success", true).accumulate("items", jarr).accumulate("member", getMemberJson(mu));
			} else if ("S".equals(mu.getRole())) {
				// 如果当前登录的会员是教练,则查出教练自己的预约情况
				String sql = "select co.id,p.name,p.image1 image,c.startTime,c.endTime,mm.id member "
						+ "from tb_member m inner join "
						+ "tb_course c on m.id = c.coach inner join tb_member mm on mm.id = c.member inner join "
						+ "tb_product p on p.member = m.id where m.id=? and c.planDate=?";
				list = DataBaseConnection.getList(sql, new Object[] { mu.getId(), date });
				if (list != null && list.size() > 0) {
					JSONObject obj = null;
					for (Map map : list) {
						obj = new JSONObject();
						// 此处为会员对象
						Member member = (Member) service.load(Member.class, Long.valueOf((String) map.get("member")));
						obj.accumulate("id", map.get("id")).accumulate("name", map.get("name"))
								.accumulate("member", getMemberJson(member)).accumulate("image", map.get("image"))
								.accumulate("startTime", map.get("startTime"))
								.accumulate("endTime", map.get("endTime"));
						jarr.add(obj);
					}
				}
				ret.accumulate("success", true).accumulate("items", jarr);
			}
			ret.accumulate("isHave", true);
		} else {
			// 返回一个标识,给前端判断用户是否购买套餐
			ret.accumulate("isHave", false);
		}
		if (request.getParameter("ajax") != null) {
			response(ret);
			return null;
		} else {
			request.setAttribute("pageInfo", ret);
			return "products";
		}
	}

	/**
	 * 去预约
	 * 
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "unused", "rawtypes" })
	public String goMake() {
		Member member = (Member) session.getAttribute("member");
		// 测试使用
		if (member == null) {
			member = new Member(Long.valueOf(508));
		}
		List<Map> products = getProducts(member);
		JSONArray res = new JSONArray();
		SimpleDateFormat simp = new SimpleDateFormat("HH:mm:ss");
		for (Map map : products) {
			res.add(new JSONObject().accumulate("id", map.get("id")).accumulate("name", map.get("name"))
					.accumulate("startTime", null).accumulate("endTime", null));
		}
		request.setAttribute("products", res);
		return "goMake";
	}

	// 获取会员购买的套餐
	@SuppressWarnings("rawtypes")
	public List getProducts(Member member) {
		String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		// 获取教练id
		String queryCoachId = "select m.id from tb_member m inner join tb_member mm on m.id=mm.coach where mm.id=?";
		Object coachId = DataBaseConnection.getOne(queryCoachId, new Object[] { member.getId() }).get("id");
		// 查询教练发布的健身套餐
		String productSql = "select p.id,p.name,p.startTime,p.endTime from tb_product p inner join"
				+ " tb_member m on p.member = m.id inner join tb_product_order po on po.product=p.id"
				+ " inner join tb_member mm on po.member=mm.id where p.type=1 and m.id = " + coachId
				+ " and po.orderEndTime > '" + date + "'";
		return DataBaseConnection.getList(productSql, null);
	}

	/**
	 * 预约
	 */
	public void make() {
		SimpleDateFormat simp = new SimpleDateFormat("yyyy-MM-dd");
		JSONObject json = JSONObject.fromObject(request.getParameter("json"));
		String planDate = simp.format(new Date());
		Member member = (Member) session.getAttribute("member");
		long coachId = (long) DataBaseConnection.getOne("select m.id from tb_member m inner join tb_member mm "
				+ "on m.id=mm.coach where mm.id=" + member.getId(), null).get("id");
		Member coach = (Member) service.load(Member.class, coachId);
		String querySql = "select id from tb_course c inner join tb_member m on c.coach=m.id where c.planDate=? "
				+ "and c.startTime>=? and c.endTime<=?";
		Object[] queryObjs = { planDate, json.get("startTime"), json.get("endTime") };
		Object isNull = DataBaseConnection.getOne(querySql, queryObjs).get("id");
		if (isNull != null) {
			response(new JSONObject().accumulate("success", false).accumulate("msg", "抱歉,该时间段已有预约"));
			return;
		}
		try {
			// 添加课程详情表信息
			String courseInfoSql = "insert into tb_course_info(name,member) " + "values('" + json.get("name") + "',"
					+ member.getId() + ")";
			DataBaseConnection.updateData(courseInfoSql, null);
			querySql = "select id from tb_course_info order by id desc limit 1";
			Object id = DataBaseConnection.getOne(querySql, null).get("id");
			// 添加课程表信息
			String courseSql = "insert into tb_course(coach,member,startTime,endTime,planDate,place,courseId) values(?,?,?,?,?,?,?)";
			Object[] obj = { coach.getId(), member.getId(), json.get("startTime"), json.get("endTime"), planDate,
					json.get("address"), id };
			DataBaseConnection.updateData(courseSql, obj);
			response(new JSONObject().accumulate("success", true));
		} catch (Exception e) {
			response(new JSONObject().accumulate("success", false).accumulate("msg", "网络异常,请稍后再试"));
		}
	}

	/**
	 * 详情
	 */
	@SuppressWarnings({ "unused", "rawtypes" })
	public String productDetail() {
		JSONObject res = new JSONObject();
		int id = 0;
		if (request.getParameter("id") != null) {
			id = Integer.parseInt(request.getParameter("id"));
		}
		String querySql = "select c.id,p.name,c.startTime,c.endTime,c.planDate,c.place from tb_course c "
				+ "inner join tb_member m on c.coach=m.id inner join tb_product p on p.member=m.id where m.id=" + id;
		Map map = DataBaseConnection.getOne(querySql, null);
		SimpleDateFormat simp = new SimpleDateFormat("HH:mm:ss");
		if (map != null) {
			res.accumulate("id", map.get("id")).accumulate("name", map.get("name"))
					.accumulate("startTime", map.get("startTime")).accumulate("endTime", map.get("endTime"))
					.accumulate("planDate", map.get("planDate")).accumulate("place", map.get("place"));
		}
		request.setAttribute("res", res);
		return "productDetail";
	}

	/**
	 * 取消预约
	 */
	public String cancelMake() {
		int id = 0;
		if (request.getParameter("id") != null) {
			id = Integer.parseInt("id");
		}
		String delSql = "delete from tb_course_info where id=(select courseId from tb_course where id=" + id + ")";
		DataBaseConnection.updateData(delSql, null);
		delSql = "delete from tb_course where id=" + id;
		DataBaseConnection.updateData(delSql, null);
		return "cancelMake";
	}

	/**
	 * 我的计划
	 * 
	 * @return
	 */
	@SuppressWarnings({ "deprecation", "rawtypes" })
	public String myPlan() {
		JSONObject res = new JSONObject();
		SimpleDateFormat simp = new SimpleDateFormat("yyyy-MM-dd");
		// 取出当前登录的用户
		Member member = (Member) session.getAttribute("member");
		// 测试使用
		if (member == null) {
			member = new Member(Long.valueOf(1393));
		} // 在开发阶段中给一个测试id
		member = (Member) service.load(Member.class, member.getId());
		String planDate = simp.format(new Date());
		if (request.getParameter("planDate") != null) {
			planDate = simp.format(new Date(request.getParameter("planDate").replace("-", "/")));
		}
		String querySql = null;
		if ("M".equals(member.getRole())) {
			// 如果当前登录用户为普通用户,查出该用户的预约情况
			querySql = "select c.id,ci.name,c.startTime,c.endTime,c.place,c.memo from tb_course c "
					+ "inner join tb_member m on c.member=m.id " + "inner join tb_course_info ci on ci.id=c.courseId "
					+ "where c.planDate='" + planDate + "' and m.id=" + member.getId();
			Map map = DataBaseConnection.getOne(querySql, null);
			// 返回结果 to json
			if (map != null) {
				res = JSONObject.fromObject(map);
			}
		} else if ("S".equals(member.getRole())) {
			// 如果当前登录的用户为教练,查出该教练预约的第一条信息(微信公众号的业务限制,尽量让用户使用APP)
			querySql = "select c.id,ci.name,c.startTime,c.endTime,c.place,c.memo from tb_course c "
					+ "inner join tb_member m on c.coach=m.id inner join tb_course_info ci on ci.id=c.courseId "
					+ "where c.planDate=? and m.id=?";
			Map map = DataBaseConnection.getOne(querySql, new Object[] { simp.format(new Date()), member.getId() });
			// 返回结果 to json
			if (map != null) {
				res = JSONObject.fromObject(map);
			}
		}
		request.setAttribute("res", res);
		if (request.getParameter("ajax") != null) {
			response(res);
			return null;
		} else {
			return "myPlan";
		}
	}

	/**
	 * 查找选中的课表的所有计划
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String findPlanByCourse() {
		try {
			final List<?> list = service.findObjectBySql("from Workout wo where wo.course.id = ? order by wo.sort", id);
			final JSONObject ret = new JSONObject();
			final JSONArray jarr = new JSONArray();
			for (final Iterator<?> it = list.iterator(); it.hasNext();) {
				final Workout wo = (Workout) it.next();
				final JSONObject obj = new JSONObject(), actObj = new JSONObject();
				actObj.accumulate("id", wo.getAction().getId()).accumulate("name", wo.getAction().getName())
						.accumulate("flash", getString(wo.getAction().getFlash()))
						.accumulate("video", getString(wo.getAction().getVideo()))
						.accumulate("image", getString(wo.getAction().getImage()))
						.accumulate("descr", wo.getAction().getDescr())
						.accumulate("regard", wo.getAction().getRegard());

				final List<?> detailList = service.findObjectBySql("from WorkoutDetail wo where wo.workout.id = ?",
						wo.getId());
				StringBuilder str = new StringBuilder();
				if (detailList.size() > 0) {
					List planTimesList = new ArrayList<>();
					List planWeightList = new ArrayList<>();
					boolean planTimesIsnumber = false;
					boolean planWeightIsnumber = false;
					for (final Iterator<?> it2 = detailList.iterator(); it2.hasNext();) {
						WorkoutDetail detail = (WorkoutDetail) it2.next();
						if (!isNumeric(detail.getPlanTimes() == null ? "0" : detail.getPlanTimes())) {
							planTimesIsnumber = true;
							planTimesList.add(detail.getPlanTimes());
						} else {
							planTimesList.add(toInt(detail.getPlanTimes()));
						}

						if (!isNumeric(detail.getPlanWeight() == null ? "0" : detail.getPlanWeight())) {
							planWeightIsnumber = true;
							planWeightList.add(detail.getPlanWeight());
						} else {
							planWeightList.add(toDouble(detail.getPlanWeight()));
						}
					}
					str.append(detailList.size() + "组,");
					if (planTimesIsnumber) {
						str.append("每组" + planTimesList.get(0) + ",");
					} else {
						Collections.sort(planTimesList);
						if (planTimesList.get(0) == planTimesList.get(planTimesList.size() - 1)) {
							str.append("每组" + planTimesList.get(0) + "次,");
						} else {
							str.append("每组" + planTimesList.get(0) + "-" + planTimesList.get(planTimesList.size() - 1)
									+ "次,");
						}
					}
					if (planWeightIsnumber) {
						str.append("重量" + planWeightList.get(0) + ",");
					} else {
						Collections.sort(planWeightList);
						if (planWeightList.get(0) == planWeightList.get(planWeightList.size() - 1)) {
							str.append("重量" + planWeightList.get(0) + "公斤,");
						} else {
							str.append("重量" + planWeightList.get(0) + "-"
									+ planWeightList.get(planWeightList.size() - 1) + "公斤,");
						}
					}
				}
				obj.accumulate("id", wo.getId()).accumulate("mode", wo.getAction().getPart().getProject().getMode())
						.accumulate("action", actObj).accumulate("detail", str.toString());
				jarr.add(obj);
			}
			log.error("动作组次明细数据1" + jarr.toString());
			ret.accumulate("success", true).accumulate("items", jarr);
			request.setAttribute("pageInfo", ret);
			return "myPlanDetail";
		} catch (Exception e) {
			log.error("error", e);
			response(e);
			return "myPlanDetail";
		}
	}
}
