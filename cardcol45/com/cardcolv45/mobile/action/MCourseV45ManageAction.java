package com.cardcolv45.mobile.action;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;

import com.aliyun.api.gateway.demo.enums.Constantsms;
import com.databaseConnection.DataBaseConnection;
import com.freegym.web.basic.Member;
import com.freegym.web.config.Action;
import com.freegym.web.config.CourseInfo;
import com.freegym.web.config.Part;
import com.freegym.web.config.Project;
import com.freegym.web.config.ProjectApplied;
import com.freegym.web.course.Apply;
import com.freegym.web.course.Message;
import com.freegym.web.mobile.BasicJsonAction;
import com.freegym.web.order.Goods;
import com.freegym.web.order.Product;
import com.freegym.web.plan.Course;
import com.freegym.web.plan.PlanRelease;
import com.freegym.web.plan.Workout;
import com.freegym.web.plan.WorkoutDetail;
import com.freegym.web.task.MessageThread;
import com.sanmen.web.core.bean.BaseMember;
import com.sanmen.web.core.common.LogicException;
import com.sanmen.web.core.utils.BaiduUtils;
import com.sanmen.web.core.utils.DateUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Namespace("")
@InterceptorRefs({ @InterceptorRef("mobileStack") })
public class MCourseV45ManageAction extends BasicJsonAction implements Constantsms {

	private static final long serialVersionUID = 1L;

	private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	/**
	 * 被预约的课程
	 */
	public Long courseId;

	public String startDate, endDate;

	public Integer[] weeks;

	public File image1, image2, image3;

	public String image1FileName, image2FileName, image3FileName;

	public Long userId;

	public Long musicId;

	public Integer currentPage;

	/**
	 * 计划时间
	 */
	protected Date planDate;

	/**
	 * 
	 */
	private Integer type;

	/**
	 * 被访问者角色类型
	 */
	protected String role;

	// 放映倒计时
	public Integer time;

	public Course course;

	private Integer saveType;

	private Integer memberId;

	/**
	 * 复制课程
	 */
	@SuppressWarnings("deprecation")
	public void copy() {
		try {
			if ("M".equals(getMobileUser().getRole())) {
				id = getMobileUser().getId();
			}
			JSONObject ret = new JSONObject();
			long startDate = Long.valueOf(0);
			long endDate = Long.valueOf(0);
			if (StringUtils.isNotEmpty(request.getParameter("startDate"))) {
				startDate = Date.parse(request.getParameter("startDate").replace("-", "/"));
			}
			if (StringUtils.isNotEmpty(request.getParameter("endDate"))) {
				endDate = Date.parse(request.getParameter("endDate").replace("-", "/"));
			}
			String week = request.getParameter("weeks");
			if (week.indexOf("[") != -1) {
				week = week.substring(1, week.length());
				week = week.substring(0, week.length() - 1);
			}
			String[] ints = week.split(",");
			Integer[] weeks = new Integer[ints.length];
			for (int i = 0; i < ints.length; i++) {
				weeks[i] = Integer.parseInt(ints[i]);
			}
			// service.copy(new Course(courseId), id, startDate, endDate,
			// weeks);
			// Course course = (Course) DataBaseConnection.load("tb_course",
			// Course.class, courseId);

			// CourseInfo cInfo = new CourseInfo(Long.valueOf(1));
			// Map uMap = DataBaseConnection.getOne("select courseId from
			// tb_course where id=" + courseId, null);
			// if (uMap.containsKey("courseId") && uMap.get("courseId") != null)
			// {
			// cInfo = (CourseInfo) service.load(CourseInfo.class,
			// Long.valueOf(uMap.get("courseId").toString()));
			// }
			// 复制一份Course对象,添加一个id变成一个新的Course对象
			// DataBaseConnection.updateData("insert into tb_course(id)
			// values(null)", null);
			// Object courseId = DataBaseConnection.getOne("select id from
			// tb_course order by id desc limit 1", null)
			// .get("id");
			Course course = (Course) service.load(Course.class, courseId);
			for (long i = startDate; i < endDate; i += (24 * 60 * 60 * 1000)) {
				String doneDate = new SimpleDateFormat("yyyy-MM-dd").format(i);
				for (int j = 0; j < weeks.length; j++) {
					if (dateToWeek(doneDate).equals(String.valueOf(weeks[j]))) {
						Course copyCourse = new Course();
						copyCourse.setMember(new Member(id));
						copyCourse.setPlanDate(doneDate);
						copyCourse.setCourseInfo(course.getCourseInfo());
						// copyCourse.setWorks(works);
						copyCourse.setApplys(course.getApplys());
						copyCourse.setCoach(course.getCoach());
						copyCourse.setMusic(course.getMusic());
						copyCourse.setStartTime(course.getStartTime());
						copyCourse.setEndTime(course.getEndTime());
						copyCourse.setPlace(course.getPlace());
						copyCourse.setDoneDate(course.getDoneDate());
						copyCourse.setMemo(course.getMemo());
						copyCourse.setCycleCount(course.getCycleCount());
						copyCourse.setCountdown(course.getCountdown());
						copyCourse = (Course) service.saveOrUpdate(copyCourse);
						Set<Workout> works = course.getWorks();
						Iterator<Workout> it = works.iterator();
						while (it.hasNext()) {
							Workout wk = it.next();
							Workout newWk = new Workout();
							newWk.setCourse(copyCourse);
							newWk.setAction(wk.getAction());
							newWk.setSort(wk.getSort());
							newWk = (Workout) service.saveOrUpdate(newWk);
							Set<WorkoutDetail> workoutDetails = wk.getDetails();
							Iterator<WorkoutDetail> detailIt = workoutDetails.iterator();
							while (detailIt.hasNext()) {
								WorkoutDetail workoutDetail = detailIt.next();
								WorkoutDetail newWorkoutDetail = new WorkoutDetail();
								newWorkoutDetail.setWorkout(newWk);
								newWorkoutDetail.setActualIntervalSeconds(workoutDetail.getActualIntervalSeconds());
								newWorkoutDetail.setActualTimes(workoutDetail.getActualTimes());
								newWorkoutDetail.setActualWeight(workoutDetail.getActualWeight());
								newWorkoutDetail.setIntensity(workoutDetail.getIntensity());
								newWorkoutDetail.setPlanDistance(workoutDetail.getPlanDistance());
								newWorkoutDetail.setPlanDuration(workoutDetail.getPlanDuration());
								newWorkoutDetail.setPlanGroupSpaceSound(workoutDetail.getPlanGroupSpaceSound());
								newWorkoutDetail.setPlanIntervalSeconds(workoutDetail.getPlanIntervalSeconds());
								newWorkoutDetail.setPlanStartSound(workoutDetail.getPlanStartSound());
								newWorkoutDetail.setPlanTimes(workoutDetail.getPlanTimes());
								newWorkoutDetail.setPlanTips(workoutDetail.getPlanTips());
								newWorkoutDetail.setPlanWeight(workoutDetail.getPlanWeight());
								newWorkoutDetail.setSort(workoutDetail.getSort());
								newWorkoutDetail = (WorkoutDetail) service.saveOrUpdate(newWorkoutDetail);
							}
						}
					}
				}
			}
			String end = new SimpleDateFormat("yyyy-MM-dd").format(endDate);
			for (int i = 0; i < weeks.length; i++) {
				if (dateToWeek(end).equals("" + weeks[i])) {
					Course copyCourse = new Course();
					copyCourse.setMember(new Member(id));
					copyCourse.setPlanDate(end);
					copyCourse.setCourseInfo(course.getCourseInfo());
					// copyCourse.setWorks(works);
					copyCourse.setApplys(course.getApplys());
					copyCourse.setCoach(course.getCoach());
					copyCourse.setMusic(course.getMusic());
					copyCourse.setStartTime(course.getStartTime());
					copyCourse.setEndTime(course.getEndTime());
					copyCourse.setPlace(course.getPlace());
					copyCourse.setDoneDate(course.getDoneDate());
					copyCourse.setMemo(course.getMemo());
					copyCourse.setCycleCount(course.getCycleCount());
					copyCourse.setCountdown(course.getCountdown());
					copyCourse = (Course) service.saveOrUpdate(copyCourse);
					Set<Workout> works = course.getWorks();
					Iterator<Workout> it = works.iterator();
					while (it.hasNext()) {
						Workout wk = it.next();
						Workout newWk = new Workout();
						newWk.setCourse(copyCourse);
						newWk.setAction(wk.getAction());
						newWk.setSort(wk.getSort());
						newWk = (Workout) service.saveOrUpdate(newWk);
						Set<WorkoutDetail> workoutDetails = wk.getDetails();
						Iterator<WorkoutDetail> detailIt = workoutDetails.iterator();
						while (detailIt.hasNext()) {
							WorkoutDetail workoutDetail = detailIt.next();
							WorkoutDetail newWorkoutDetail = new WorkoutDetail();
							newWorkoutDetail.setWorkout(newWk);
							newWorkoutDetail.setActualIntervalSeconds(workoutDetail.getActualIntervalSeconds());
							newWorkoutDetail.setActualTimes(workoutDetail.getActualTimes());
							newWorkoutDetail.setActualWeight(workoutDetail.getActualWeight());
							newWorkoutDetail.setIntensity(workoutDetail.getIntensity());
							newWorkoutDetail.setPlanDistance(workoutDetail.getPlanDistance());
							newWorkoutDetail.setPlanDuration(workoutDetail.getPlanDuration());
							newWorkoutDetail.setPlanGroupSpaceSound(workoutDetail.getPlanGroupSpaceSound());
							newWorkoutDetail.setPlanIntervalSeconds(workoutDetail.getPlanIntervalSeconds());
							newWorkoutDetail.setPlanStartSound(workoutDetail.getPlanStartSound());
							newWorkoutDetail.setPlanTimes(workoutDetail.getPlanTimes());
							newWorkoutDetail.setPlanTips(workoutDetail.getPlanTips());
							newWorkoutDetail.setPlanWeight(workoutDetail.getPlanWeight());
							newWorkoutDetail.setSort(workoutDetail.getSort());
							newWorkoutDetail = (WorkoutDetail) service.saveOrUpdate(newWorkoutDetail);
						}
					}
				}
			}
			ret.accumulate("success", true).accumulate("message", "OK");
			response(ret);
		} catch (Exception e) {
			log.error("error", e);
			e.printStackTrace();
			response(new JSONObject().accumulate("success", false).accumulate("message", e));
		}
	}

	/**
	 * 计算星期
	 * 
	 * @param datetime
	 * @return
	 */
	@SuppressWarnings("unused")
	public String dateToWeek(String datetime) {
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
		String[] weekDays = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
		Calendar cal = Calendar.getInstance(); // 获得一个日历
		Date datet = null;
		try {
			datet = f.parse(datetime);
			cal.setTime(datet);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		int w = cal.get(Calendar.DAY_OF_WEEK) - 1; // 指示一个星期中的某天。
		if (w < 0)
			w = 0;
		return String.valueOf(w);
	}

	/**
	 * 保存课程
	 */
	public String saveCourse() {
		try {
			// 测试数据
			// jsons =
			// "[{'member':9387,'course':299,'place':'湖北','planDate':'2017-10-11',"
			// +
			// "'startTime':'20:00','endTime':'21:00','memo':'无','cycleCount':1,'countdown':10}]";
			final JSONArray objs = JSONArray.fromObject(URLDecoder.decode(jsons, "UTF-8"));
			final Long id = executeSave(objs);
			response(new JSONObject().accumulate("success", true).accumulate("key", id));
		} catch (Exception e) {
			log.error("error", e);
			// "{success: false, code: -1, message: '" + e.getMessage() + "'}"
			response(new JSONObject().accumulate("success", false).accumulate("code", -1).accumulate("message",
					e.getMessage()));
		}
		return null;
	}

	/**
	 * 保存教练给会员制定的课程
	 */
	@Override
	protected Long executeSave(JSONArray objs) {
		log.error("保存制订计划JSON串：" + objs.toString());
		final BaseMember mobileUser = getMobileUser();
		final String role = mobileUser.getRole();
		final Long userId = mobileUser.getId();
		final JSONObject obj = objs.getJSONObject(0);
		Course course = new Course();
		if (obj.containsKey("id") && !"".equals(obj.getString("id"))) {
			course = (Course) service.load(Course.class, obj.getLong("id"));
		}

		CourseInfo cInfo = (CourseInfo) service.load(CourseInfo.class, Long.valueOf(obj.getString("course")));
		course.setCourseInfo(cInfo);
		course.setPlanDate(obj.getString("planDate"));
		course.setStartTime(obj.getString("startTime").trim());
		course.setEndTime(obj.getString("endTime").trim());
		course.setPlace(obj.getString("place"));
		course.setMemo(obj.getString("memo"));
		course.setCycleCount(obj.getInt("cycleCount"));
		course.setCountdown(obj.getInt("countdown"));
		course.setColor("#ffcc00");
		if ("S".equals(role)) {// 教练制订课程，给会员
			course.setPlanSource("1");
			course.setMember(new Member(obj.getLong("member")));
			course.setCoach(new Member(userId));
		} else if ("M".equals(role)) {
			course.setPlanSource("2");
			course.setMember(new Member(userId));
		}
		course = (Course) service.saveOrUpdate(course);
		return course.getId();
	}

	/**
	 * 清除课程
	 */
	public void clean() {
		final BaseMember mu = getMobileUser();
		try {
			final Long keyId = (id == null ? mu.getId() : id);
			final JSONObject obj = new JSONObject();
			final List<?> list = service.findObjectBySql(
					"from Course c where c.member.id = ? and planDate between ? and ?", keyId, startDate, endDate);
			for (final Iterator<?> it = list.iterator(); it.hasNext();) {
				final Course c = (Course) it.next();
				service.delete(c);
			}
			obj.accumulate("success", true).accumulate("message", "OK");
			response(obj);
		} catch (Exception e) {
			log.error("error", e);
			response(e);
		}
	}

	/**
	 * 删除指定的课程
	 */
	@Override
	protected void executeDelete() {
		try {
			// service.delete(Course.class, id);
			Course course = (Course) service.load(Course.class, id);
			course.setPlanDate("1980-01-01");
			service.saveOrUpdate(course);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 私人订制详情 传参数 id=1为王严专家
	 */
	public void loadPlan() {
		try {
			int type = 0;
			if (request.getParameter("type") != null) {
				type = Integer.parseInt(request.getParameter("type"));
			}
			JSONObject obj = new JSONObject();
			if (type == 6) {
				Goods good = (Goods) service.load(Goods.class, id);
				Member member = (Member) service.load(Member.class, good.getMember());
				List<Map<String, Object>> list = DataBaseConnection.getList(
						"select count(t.id) as count from tb_goods_order as t where goods=?", new Object[] { id });
				Object goodsCount = 0;
				if (list.size() > 0) {
					goodsCount = list.get(0).get("count");
				}
				obj.accumulate("success", true).accumulate("item",
						good.toJsons().accumulate("memberName", member.getName()).accumulate("goodsCount", goodsCount));
			}
			if (type == 3) {
				PlanRelease planRelease = (PlanRelease) service.load(PlanRelease.class, id);
				Member member = planRelease.getMember();
				long count = (long) DataBaseConnection
						.getOne("select count(t.id) as count from tb_planrelease_order as t where planrelease="
								+ planRelease.getId(), null)
						.get("count");
				obj.accumulate("success", true).accumulate("item", new JSONObject()
						.accumulate("id", planRelease.getId()).accumulate("memberId", member.getId())
						.accumulate("memberName", member.getName()).accumulate("name", planRelease.getPlanName())
						.accumulate("planType", planRelease.getPlanType()).accumulate("scene", planRelease.getScene())
						.accumulate("applyObject", planRelease.getApplyObject())
						.accumulate("apparatuses", planRelease.getApparatuses())
						.accumulate("price", planRelease.getUnitPrice())
						.accumulate("summary", planRelease.getBriefing()).accumulate("image1", planRelease.getImage1())
						.accumulate("plancircle", planRelease.getPlanDay()).accumulate("goodsCount", count))
						.accumulate("startDate",
								sdf.format(
										planRelease.getStartDate() == null ? new Date() : planRelease.getStartDate()))
						.accumulate("endDate",
								sdf.format(planRelease.getEndDate() == null ? new Date() : planRelease.getEndDate()))
						.accumulate("isClose", planRelease.getIsClose());
				// .accumulate("plan_participantId",
				// planRelease.getPlan_participant().getId())
			}
			if (type == 0) {
				obj.accumulate("success", true).accumulate("message", "请指定'type'的类型");
			}
			response(obj);
		} catch (Exception e) {
			log.error("error", e);
			response(e);
		}
	}

	/**
	 * 选中课程的动作列表
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void findPlanByCourse() {
		try {
			// final List<?> list = service.findObjectBySql("from Workout wo
			// where wo.course.id = ? order by wo.sort", id);
			String querySql = "select * from (" + "select id,sort,action,course from tb_workout"
					+ " where course = ? and sort is not null " + "order by sort asc) t1 " + "UNION ALL"
					+ " select * from (" + "select id,sort,action,course from tb_workout"
					+ " where course = ? and sort is null" + " order by id asc) t2";
			Object[] args = new Object[] { id, id };
			List<Map<String, Object>> woList = DataBaseConnection.getList(querySql, args);
			// 2017-7-18 优化 sql 提高查询效率
			/*
			 * long sum = service.queryForLong(
			 * "SELECT SUM(planDuration) from tb_workout_detail d where d.workout in (SELECT id FROM tb_workout w where w.course = ?)"
			 * ,id);
			 */
			long sum = 0;
			Map<String, Object> uMap = DataBaseConnection
					.getOne("SELECT SUM(planDuration) sum from tb_workout_detail d  LEFT JOIN tb_workout  w ON d.workout = w.id where w.course = "
							+ id, null);
			String sumStr = "";
			if (uMap.containsKey("sum") && uMap.get("sum") != null) {
				sumStr = uMap.get("sum").toString();
				if (sumStr.indexOf(".") > 0) {
					sumStr = sumStr.substring(0, sumStr.indexOf("."));
				}
				sum = Integer.parseInt(sumStr);
			}

			final JSONObject ret = new JSONObject();
			final JSONArray jarr = new JSONArray();

			// 单个动作时长
			String preSql = " select * from ( "
					      + " select SUM(planDuration) as preTime from tb_workout_detail wd LEFT JOIN tb_workout w ON wd.workout = w.id where w.id = ?  "
					      + " ) temp  ";
			
			for (Map<String, Object> map : woList) {
				// Action action = (Action) service.load(Action.class,
				// Long.parseLong(map.get("action").toString()));
				String sqla = "select * from tb_project_action where id = " + map.get("action");
				// Map<String, Object> action = (Map<String, Object>)
				// DataBaseConnection.getList(sqla, obja);
				Map<String, Object> action = DataBaseConnection.getOne(sqla, null);
				
				
				// 获取单个动作时长
				Object[] preObj = {map.get("id")};
				Map<String, Object> preMap = DataBaseConnection.getOne(preSql, preObj);
				
				final JSONObject obj = new JSONObject(), actObj = new JSONObject();
				if (action != null) {
					String imagePath = action.get("image") == null ? "" : action.get("image").toString();
					if (imagePath.contains("picture")) {
						imagePath = imagePath.replaceAll("picture/", "");
					}
					actObj.accumulate("id", action.get("id")).accumulate("name", action.get("name"))
							.accumulate("flash", getString(action.get("flash")))
							.accumulate("video", getString(action.get("video"))).accumulate("image", imagePath)
							.accumulate("descr", action.get("descr")).accumulate("regard", action.get("regard"));
					final List<?> detailList = service.findObjectBySql("from WorkoutDetail wo where wo.workout.id = ?",
							Long.parseLong(map.get("id").toString()));
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
								str.append("每组" + planTimesList.get(0) + "-"
										+ planTimesList.get(planTimesList.size() - 1) + "次,");
							}
						}

						if (planWeightIsnumber) {
							str.append("重量" + planWeightList.get(0).toString().substring(0,
									planWeightList.get(0).toString().lastIndexOf(".")) + ",");
						} else {
							Collections.sort(planWeightList);
							if (planWeightList.get(0) == planWeightList.get(planWeightList.size() - 1)) {
								str.append("重量" + planWeightList.get(0).toString().substring(0,
										planWeightList.get(0).toString().lastIndexOf(".")) + "公斤");
							} else {
								/*
								 * str.append("重量" +
								 * planWeightList.get(0).toString().substring(0,
								 * planWeightList.get(0).toString().lastIndexOf(
								 * ".")) + "-" +
								 * planWeightList.get(planWeightList.size() -
								 * 1).toString().substring(0,planWeightList.get(
								 * planWeightList.size() -
								 * 1).toString().lastIndexOf(".")) + "公斤,");
								 */
								// 2017-7-18 hwj 修改成只显示最大值
								str.append("重量" + planWeightList.get(planWeightList.size() - 1).toString().substring(0,
										planWeightList.get(planWeightList.size() - 1).toString().lastIndexOf("."))
										+ "公斤");
							}
						}
					}

					String sqlm = "select p.mode mode from tb_project_action pa,tb_project_part pp,tb_project p where pa.part = pp.id and pp.project = p.id and pa.id =  ?";
					Object[] objm = { Long.parseLong(action.get("id").toString()) };
					Map<String, Object> modemap = DataBaseConnection.getOne(sqlm, objm);
					int mode = 0;
					if (modemap != null) {
						mode = modemap.get("mode") == null ? 0 : Integer.parseInt(modemap.get("mode").toString());
					}
					
					String douStr = str.toString();
					if (StringUtils.isNotEmpty(douStr)) {
						 String	strs = douStr.split("重量")[1];
						    strs = strs.split("公斤")[0];
							Integer douWeight = Integer.valueOf(strs);
							if (douWeight == 0 ){
								if (mode == 0) {
								 // 有氧运动，显示时间
								 Integer preTime = Double.valueOf(String.valueOf(preMap.get("preTime"))).intValue();
								 String timeStr = preTime%60 == 0 ? (preTime/60 + "分") : ((preTime/60) + "分" + preTime%60 + "秒");
								 timeStr = "时间: " + timeStr;
								 str = new StringBuilder(timeStr);
								} else {
								 // 非有氧运动，显示组数，力竭
								 String[] douStrs = douStr.split(",");
								 douStr = douStrs[0] + ",力竭";
								 str = new StringBuilder(douStr);
								}
							}
					}
				   
					
					obj.accumulate("id", Long.parseLong(map.get("id").toString())).accumulate("sort", map.get("sort"))
							.accumulate("mode", mode).accumulate("action", actObj).accumulate("detail", str.toString());
					jarr.add(obj);
				}
			}
			log.error("动作组次明细数据1" + jarr.toString());
			ret.accumulate("success", true).accumulate("items", jarr).accumulate("sumTime", sum);
			response(ret);
		} catch (Exception e) {
			log.error("error", e);
			response(e);
		}
	}

	/**
	 * 查找所有有氧运动内容
	 */
	public void findAction() {
		try {
			// from Action a where a.part.project.mode = '0' and a.part.member =
			// 1
			List<?> list = service
					.findObjectBySql("from Action a where a.part.project.mode = '0' and a.part.member = 1");
			JSONArray jarr = new JSONArray();
			for (final Iterator<?> it = list.iterator(); it.hasNext();) {
				final Action a = (Action) it.next();
				final JSONObject obj = new JSONObject();
				String imagePath = a.getImage();
				if (imagePath != null) {
					if (imagePath.contains("picture")) {
						imagePath = imagePath.replaceAll("picture/", "");
					}
				}
				obj.accumulate("id", a.getId()).accumulate("name", a.getName()).accumulate("image", imagePath)
						.accumulate("flash", a.getFlash()).accumulate("video", a.getVideo());
				jarr.add(obj);
			}
			final JSONObject ret = new JSONObject();
			ret.accumulate("success", true).accumulate("items", jarr);
			response(ret);
		} catch (Exception e) {
			log.error("error", e);
			response(e);
		}
	}

	/*
	 * 
	 * 我的计划
	 */
	@SuppressWarnings("unused")
	@Override
	public void list() {
		final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if (planDate == null)
			planDate = new Date();
		Date[] ds = null;
		if (type == 1) {
			ds = DateUtil.getMinMaxDateByMonth(planDate);
		} else if (type == 2) {
			ds = DateUtil.getWeekDateByDate(planDate);
		} else {
			ds = DateUtil.getDateTimes(planDate);
		}
		final String sDate = sdf.format(ds[0]);
		final String eDate = sdf.format(ds[1]);
		List<?> list = null;
		boolean hasRel = true;
		if (id == null) { // 这里是自身访问，取得登录用户的课表
			final BaseMember mu = getMobileUser();
			final Long userId = mu.getId();
			if (mu.getRole().equals("M")) { // 会员
				list = service.findObjectBySql(
						"from Course cs where (cs.member.id = ? or cs.id in (select a.course.id from Apply a where a.member.id = ? and a.status = ?)) and cs.planDate between ? and ? order by cs.planDate, cs.startTime",
						new Object[] { userId, userId, "2", sDate, eDate });
			} else if (mu.getRole().equals("S")) { // 教练
				list = service.findObjectBySql(
						"from Course cs where cs.coach.id = ? and cs.member.role <> ? and cs.planDate between ? and ? order by cs.planDate, cs.startTime",
						new Object[] { userId, "E", sDate, eDate });
			}
		} else { // 访问其它用户的课表
			// 判断有无关系
			hasRel = service.hasRelation(getMobileUser().getId(), id);
			if (role.equals("E")) { // 被访问者为俱乐部
				list = service.findObjectBySql(
						"from Course cs where cs.member.id = ? and cs.planDate between ? and ? order by cs.planDate, cs.startTime",
						new Object[] { id, sDate, eDate });
			} else if (role.equals("S")) { // 被访问者为教练
				list = service.findObjectBySql(
						"from Course cs where cs.coach.id = ? and cs.planDate between ? and ? order by cs.planDate, cs.startTime",
						new Object[] { id, sDate, eDate });
			}
		}
		JSONArray jarr = new JSONArray();
		JSONObject ret = new JSONObject();
		if (list != null) {
			for (Iterator<?> it = list.iterator(); it.hasNext();) {
				final Course c = (Course) it.next();
				int hasPartake = 0;
				for (final Apply a : c.getApplys()) {
					if (a.getMember().getId().equals(getMobileUser().getId()) && "2".equals(a.getStatus())) {
						hasPartake = 1;
						break;
					}
				}
				final JSONObject obj = new JSONObject();
				// id 课程ID
				// image 课程所属用户头像
				// planDate 计划时间
				// courseName 课程名称
				// coachName 教练名称
				// startTime 开始时间
				// endTime 结束时间

				obj.accumulate("id", c.getId()).accumulate("image", c.getMember().getImage())
						.accumulate("course", c.getCourseInfo() == null ? null : c.getCourseInfo().getName())
						.accumulate("coachName", c.getCoach() == null ? null : c.getCoach().getName())
						.accumulate("planDate", c.getPlanDate()).accumulate("startTime", c.getStartTime())
						.accumulate("endTime", c.getEndTime())
						.accumulate("image", c.getCourseInfo() == null ? null : c.getCourseInfo().getImage())
						.accumulate("address", c.getMember() == null ? null : c.getMember().getAddress())
						.accumulate("member", c.getMember() == null ? null : getMemberJson(c.getMember()))
						.accumulate("courseId", c.getCourseInfo() == null ? null : c.getCourseInfo().getId())
						.accumulate("place", c.getPlace() == null ? "" : c.getPlace())
						.accumulate("count", c.getCount() == null ? null : getInteger(c.getCount()))
						.accumulate("joinNum", c.getJoinNum() == null ? null : getInteger(c.getJoinNum()))
						.accumulate("hasPartake", hasPartake)
						.accumulate("memberPrice", c.getMemberPrice() == null ? null
								: getDouble(c.getMemberPrice()))
						.accumulate("hourPrice", c.getHourPrice() == null ? null : getDouble(c.getHourPrice()))
						.accumulate("memo", c.getMemo() == null ? "" : c.getMemo())
						.accumulate("music", c.getMusic() != null ? c.getMusic().toJson() : null)
						.accumulate("countdown", c.getCountdown() == null ? "" : c.getCountdown())
						.accumulate("cycleCount", c.getCycleCount() == null ? "" : c.getCycleCount());
				jarr.add(obj);
			}
		}
		Member returnMember = (Member) getMobileUser();
		ret.accumulate("success", true)/* .accumulate("hasRel", hasRel) */
				.accumulate("items", jarr)
				.accumulate("member", new JSONObject().accumulate("id", returnMember.getId())
						.accumulate("name", returnMember.getName()).accumulate("nick", returnMember.getNick())
						.accumulate("role", returnMember.getRole()).accumulate("image", returnMember.getImage()));
		response(ret);
	}

	/**
	 * 发布健身计划接口 参数： uuid : String 用户唯一标识 id: Long 计划主键。 image1: File 计划图片一
	 * jsons: JSON数组， [{ planName: 计划名称， planType： 计划类型 A.瘦身减重 B.健美增肌 C.运动康复
	 * D.提高运动表现， scene： 适用场景 A.健身房 B.办公室 C.家庭 D.户外， applyObject： 适用对象 A.初级 B.中级
	 * C.高级， apparatuses： 所需器材， startDate： 计划开始时间， endDate： 计划结束时间， unitPrice：
	 * 销售价格， briefing： 计划简介， details： 计划详情， image1FileName： 计划图片一名称，
	 * member：保存用户id }];
	 */
	public void releasePlan() {
		try {
			// 创建格式化日期对象
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			// 将json参数编码转换为utf-8
			JSONArray arr = JSONArray.fromObject(URLDecoder.decode(jsons, "UTF-8"));
			log.error("发布计划JSON串：" + arr.toString());
			JSONObject obj = arr.getJSONObject(0);
			// 创建pojo对象并填充对象属性
			PlanRelease plan = null;
			if (null == id) {
				plan = new PlanRelease();
			} else {
				plan = (PlanRelease) service.load(PlanRelease.class, id);
			}
			String fileName1 = image1 != null ? saveFile("picture", image1, image1FileName, null) : null;
			if (fileName1 != null) {
				plan.setImage1(fileName1);
			}
			plan.setPlanName(obj.getString("planName"));
			plan.setPlanType(obj.getString("planType"));
			plan.setScene(obj.getString("scene"));
			plan.setApplyObject(obj.getString("applyObject"));
			plan.setApparatuses(obj.getString("apparatuses"));
			if (obj.containsKey("startDate")) {
				plan.setStartDate(null != obj.get("startDate") ? sdf.parse(obj.getString("startDate")) : null);
			}
			if (obj.containsKey("endDate")) {
				plan.setEndDate(null != obj.get("endDate") ? sdf.parse(obj.getString("endDate")) : null);
			}
			plan.setUnitPrice(obj.getDouble("unitPrice"));
			plan.setBriefing(obj.getString("briefing"));
			plan.setAudit("1");
			plan.setIsClose("2");

			if (obj.containsKey("details")) {
				plan.setDetails(obj.getString("details"));
			}

			final Long userId = getMobileUser().getId();
			// 根据用户id查询用户对象数据
			final Member m = (Member) service.load(Member.class, userId);
			plan.setMember(m);
			if (obj.getString("member") != null && !obj.getString("member").equals("null")
					&& !"".equals(obj.getString("member"))) {
				Long plan_participant_long = Long.parseLong(obj.getString("member"));
				Member plan_participant = (Member) service.load(Member.class, plan_participant_long);
				plan.setPlan_participant(plan_participant);
			}
			plan.setPublishTime(new Date());
			plan.setPlanDay(
					(int) ((plan.getEndDate().getTime() - plan.getStartDate().getTime()) / 1000 / 60 / 60 / 24) + 1);

			// 执行数据库持久化操作
			plan = (PlanRelease) service.saveOrUpdate(plan);
			obj = new JSONObject().accumulate("success", true).accumulate("key", plan.getId());
			response(obj);
		} catch (Exception e) {
			log.error("error", e);
			response(e);
		}
	}

	/**
	 * 新增健身计划列表
	 */

	public void showPlanList() {
		// //测试数据
		// JSONObject jsonObject = new JSONObject();
		// jsonObject.accumulate("planType", "")
		// .accumulate("applyObject", "")
		// .accumulate("scene", "")
		// .accumulate("planTime", "")
		// .accumulate("keyword", "健力美");
		// JSONArray jsonArray = new JSONArray();
		// jsonArray.add(jsonObject);
		// String jsons = jsonArray.toString();

		try {
			pageInfo = getPageInfo();
			String pageSize = request.getParameter("pageSize");
			String currentPage = request.getParameter("currentPage");
			if (pageSize != null) {
				pageInfo.setPageSize(Integer.parseInt(pageSize));
			}
			if (currentPage != null) {
				pageInfo.setCurrentPage(Integer.parseInt(currentPage));
			}

			// 获取筛选条件
			JSONArray arr = JSONArray.fromObject(URLDecoder.decode(jsons, "UTF-8"));
			System.out.println("筛选条件传入的参数：" + arr.toString());
			JSONObject object = (JSONObject) arr.get(0);
			String sql = "select r.id,r.plan_name,r.image1,r.unit_price,r.briefing from tb_plan_release r,tb_member m  where 1=1 and r.audit = '1' and m.id = r.member ";
			String planCount = "select count(*) count from tb_plan_release r ,tb_member m  where 1=1 and  r.audit = '1' and m.id = r.member ";

			// keyword匹配
			if (object.containsKey("keyword")) {
				String keyword = object.getString("keyword");
				if (keyword != null && !"".equals(keyword)) {
					sql += " and (r.plan_name like '%" + keyword + "%' or r.briefing like '%" + keyword
							+ "%'  or m.name like '%" + keyword + "%' ) ";
					planCount += " and (r.plan_name like '%" + keyword + "%' or r.briefing like '%" + keyword
							+ "%'  or m.name like '%" + keyword + "%' ) ";
				}
			}
			// 计划类型
			String planType = object.getString("planType");
			if (planType != null && !"".equals(planType)) {
				sql += " and r.plan_type = '" + planType + "'";
				planCount += " and r.plan_type = '" + planType + "'";
			}

			// 适用对象
			String applyObject = object.getString("applyObject");
			if (applyObject != null && !"".equals(applyObject)) {
				sql += " and r.apply_object = '" + applyObject + "'";
				planCount += " and r.apply_object = '" + applyObject + "'";
			}

			// 适用场景
			String scene = object.getString("scene");
			if (scene != null && !"".equals(scene)) {
				sql += " and r.apply_scene = '" + scene + "'";
				planCount += " and r.apply_scene = '" + scene + "'";
			}
			// 计划时长
			String planTime = object.getString("planTime");
			if (planTime != null && !"".equals(planTime)) {
				// 计划时长小于8
				if ("A".equals(planTime)) {
					sql += " and r.plan_day < 8  ";
					planCount += " r.and plan_day < 8  ";
				}
				// 计划时长在8-31天之间
				if ("B".equals(planTime)) {
					sql += " and r.plan_day between 8 and 31 ";
					planCount += " and r.plan_day between 8 and 31 ";
				}
				// 计划时长在32-92天之间
				if ("C".equals(planTime)) {
					sql += " and r.plan_day between 32 and 92 ";
					planCount += " and r.plan_day between 32 and 92 ";
				}
				// 计划时长大于92天
				if ("D".equals(planTime)) {
					sql += " and r.plan_day > 92  ";
					planCount += " r.and plan_day > 92  ";
				}

			}
			// planCount += "and not isnull(r.plan_participant) ";
			sql += " ORDER BY publish_time desc  limit ?,?";
			// sql语句拼接完成
			long count = (long) DataBaseConnection.getOne(planCount, null).get("count");
			long totalPage = (int) (count % pageInfo.getPageSize() == 0 ? count / pageInfo.getPageSize()
					: (count / pageInfo.getPageSize()) + 1);
			pageInfo.setTotalCount(Integer.valueOf(String.valueOf(count)));
			pageInfo.setTotalPage(Integer.valueOf(String.valueOf(totalPage)));

			Object[] objx = { (pageInfo.getCurrentPage() - 1) * pageInfo.getPageSize(), pageInfo.getPageSize() };

			List<Map<String, Object>> planList = DataBaseConnection.getList(sql, objx);
			JSONObject obj = new JSONObject();
			obj.accumulate("success", true).accumulate("message", "OK")
					.accumulate("planList", JSONArray.fromObject(planList))
					.accumulate("pageInfo", getJsonForPageInfo());
			response(obj);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 改变计划状态
	 */
	public void changeClose() {
		try {
			final JSONObject obj = new JSONObject();
			final PlanRelease plan = (PlanRelease) service.load(PlanRelease.class, id);
			if (plan.getIsClose().equals("2")) {
				plan.setIsClose("1");
			} else {
				plan.setIsClose("2");
			}
			service.saveOrUpdate(plan);
			obj.accumulate("success",
					true);/*
							 * .accumulate("item", plan.toJson().accumulate(
							 * "isClose", plan.getIsClose()));
							 */
			response(obj);
		} catch (Exception e) {
			log.error("error", e);
			response(e);
		}
	}

	/**
	 * 保存计划
	 */
	@SuppressWarnings("unused")
	public void savePlan() {
		try {
			final JSONArray arr = JSONArray.fromObject(URLDecoder.decode(jsons, "UTF-8"));
			log.error("保存计划JSON串：" + arr.toString());
			List<Workout> wos = new ArrayList<Workout>();
			for (final Iterator<?> it = arr.listIterator(); it.hasNext();) {
				final JSONObject obj = (JSONObject) it.next();
				final Workout wo = new Workout();
				Action a = (Action) service.load(Action.class, Long.valueOf(obj.getLong("actionId")));
				wo.setAction(a);
				wo.setCourse((Course) service.load(Course.class, id));
				wos.add(wo);
			}
			List<?> list = service.saveOrUpdate(wos);
			final JSONObject ret = new JSONObject();

			String querySql = "select * from (" + "select id,sort,action,course from tb_workout"
					+ " where course = ? and sort is not null " + "order by sort asc) t1 " + "UNION ALL"
					+ " select * from (" + "select id,sort,action,course from tb_workout"
					+ " where course = ? and sort is null" + " order by id asc) t2";
			Object[] args = new Object[] { id, id };
			List<Map<String, Object>> paramList = DataBaseConnection.getList(querySql, args);
			wos = new ArrayList<Workout>();
			for (Map<String, Object> map : paramList) {
				Workout wo = (Workout) service.load(Workout.class, Long.valueOf(String.valueOf(map.get("id"))));
				wos.add(wo);
			}
			ret.accumulate("success", true).accumulate("items", generatorPlanJson(wos));
			response(ret);
		} catch (Exception e) {
			log.error("error", e);
			response(e);
		}
	}

	/**
	 * 动作排序
	 */
	public void saveSort() {
		try {
			final JSONArray arr = JSONArray.fromObject(URLDecoder.decode(jsons, "UTF-8"));
			log.error("保存计划排序JSON串：" + arr.toString());
			final List<Workout> wos = new ArrayList<Workout>();
			for (final Iterator<?> it = arr.listIterator(); it.hasNext();) {
				final JSONObject obj = (JSONObject) it.next();
				final Workout wo = (Workout) service.load(Workout.class, obj.getLong("id"));
				wo.setSort(obj.getInt("sort"));
				wos.add(wo);
			}
			service.saveOrUpdate(wos);
			final JSONObject ret = new JSONObject();
			ret.accumulate("success", true).accumulate("message", "OK");
			response(ret);
		} catch (Exception e) {
			log.error("error", e);
			response(e);
		}
	}

	/**
	 * 生成计划列表的JSON数据
	 * 
	 * @param list
	 * @return
	 */
	private String generatorPlanJson(List<?> list) {
		final JSONArray jarr = new JSONArray();
		for (final Iterator<?> it = list.iterator(); it.hasNext();) {
			final Workout wo = (Workout) it.next();
			final JSONObject obj = new JSONObject(), actObj = new JSONObject();
			if (wo.getAction() != null) {
				actObj.accumulate("id", wo.getAction().getId()).accumulate("name", wo.getAction().getName())
						.accumulate("flash", getString(wo.getAction().getFlash()))
						.accumulate("video", getString(wo.getAction().getVideo()))
						.accumulate("image", getString(wo.getAction().getImage()))
						.accumulate("descr", wo.getAction().getDescr())
						.accumulate("regard", wo.getAction().getRegard());
				// .accumulate("mode",wo.getAction().getPart().getProject().getMode());
			}
			obj.accumulate("id", wo.getId()).accumulate("sort", wo.getSort()).accumulate("action", actObj);
			jarr.add(obj);
		}
		log.error("动作组次明细数据1" + jarr.toString());
		return jarr.toString();
	}

	/**
	 * 发布私教套餐
	 */
	public void savePrivate() {
		try {
			Member member2 = (Member) getMobileUser();
			String jsons = request.getParameter("jsons") == null ? request.getParameter("Jsons")
					: request.getParameter("jsons");
			JSONArray jsonArray = JSONArray.fromObject(URLDecoder.decode(jsons, "UTF-8"));
			JSONObject jObject = (JSONObject) jsonArray.get(0);
			Product pro = new Product();
			if (jObject.containsKey("id") && StringUtils.isNotEmpty(jObject.getString("id"))) {
				pro = (Product) service.load(Product.class, jObject.getLong("id"));
				pro.setMember(member2);
				String fileName = saveFile("picture", image1, image1FileName, null);
				if(StringUtils.isNotEmpty(fileName)){
					pro.setImage1(fileName);
				}
				if(jObject.containsKey("name") && StringUtils.isNotEmpty(jObject.getString("name"))){
					pro.setName(jObject.getString("name"));
				}
				if(jObject.containsKey("wellNum") && StringUtils.isNotEmpty(jObject.getString("wellNum"))){
					pro.setWellNum(jObject.getInt("wellNum"));
				}
				if(jObject.containsKey("price") && StringUtils.isNotEmpty(jObject.getString("price"))){
					pro.setCost(jObject.getDouble("price"));
				}
				if(jObject.containsKey("remark") && StringUtils.isNotEmpty(jObject.getString("remark"))){
					pro.setRemark(jObject.getString("remark"));
				}
			} else {
				pro.setMember(member2);
				String fileName = saveFile("picture", image1, image1FileName, null);
				pro.setImage1(fileName != null ? fileName : null);
				pro.setName(jObject.getString("name"));
				pro.setWellNum(jObject.getInt("wellNum"));
				pro.setCost(jObject.getDouble("price"));
				pro.setRemark(jObject.getString("remark"));
				pro.setCreateTime(new Date());
			}
			pro.setType("3");
			pro.setIsClose("2");
			pro.setAudit('1');
			pro = (Product) service.saveOrUpdate(pro);
			response(new JSONObject().accumulate("success", true).accumulate("id", pro.getId())
					.accumulate("member", (long) pro.getMember().getId()).accumulate("image1", pro.getImage1())
					.accumulate("name", pro.getName()).accumulate("wellNum", pro.getWellNum())
					.accumulate("price", pro.getCost()).accumulate("remark", pro.getRemark())
					.accumulate("isClose", pro.getIsClose()));
		} catch (Exception e) {
			e.printStackTrace();
			response(e);
		}
	}

	/**
	 * 查看私教套餐详情
	 */
	public void getPrivateInfo() {
		try {
			Product product = (Product) service.load(Product.class, id);
			response(new JSONObject().accumulate("success", true).accumulate("id", product.getId())
					.accumulate("name", product.getName()).accumulate("image", product.getImage1())
					.accumulate("memberId", product.getMember().getId())
					.accumulate("memberName", product.getMember().getName()).accumulate("wellNum", product.getWellNum())
					.accumulate("price", product.getCost()).accumulate("remark", product.getRemark())
					.accumulate("isClose", product.getIsClose()));
		} catch (Exception e) {
			e.printStackTrace();
			response(e);
		}
	}

	/**
	 * 改变私教状态
	 */
	public void PrivateClose() {
		try {
			JSONObject obj = new JSONObject();
			Product pro = (Product) service.load(Product.class, id);
			if (pro.getIsClose().equals("2")) {
				pro.setIsClose("1");
			} else {
				pro.setIsClose("2");
			}
			service.saveOrUpdate(pro);
			obj.accumulate("success", true);
			// .accumulate("item", pro.toJson().accumulate(
			// "isClose", pro.getIsClose()))
			response(obj);
		} catch (Exception e) {
			log.error("error", e);
			response(e);
		}
	}

	/**
	 * 查找选中的动作的组次明细
	 */
	public void findGroupByAction() {
		try {
			final JSONObject ret = new JSONObject(), actObj = new JSONObject();
			final JSONArray jarr = new JSONArray();
			final List<?> list = service
					.findObjectBySql("from WorkoutDetail wo where wo.workout.id = ? order by wo.sort asc ", id);
			final List<?> list1 = service.findObjectBySql("from Workout wk where wk.id = ?", id);
			if (list1.size() > 0) {
				final Workout wk = (Workout) list1.get(0);
				final String video = wk.getAction() != null && wk.getAction().getVideo() != null
						? BaiduUtils.getSignPath(BUCKET, wk.getAction().getVideo()) : null;
				final String music = wk.getCourse() != null && wk.getCourse().getMusic() != null
						&& wk.getCourse().getMusic().getAddr() != null ? wk.getCourse().getMusic().getAddr() : null;
				actObj.accumulate("video", video).accumulate("music", music)
						.accumulate("descr", wk.getAction().getDescr())
						.accumulate("regard", wk.getAction().getRegard());
			}
			if (list.size() > 0) {
				for (final Iterator<?> it = list.iterator(); it.hasNext();) {
					final WorkoutDetail wd = (WorkoutDetail) it.next();
					final JSONObject obj = new JSONObject();
					double d = Double.parseDouble(wd.getPlanDuration());
					int j = (int) d;
					String planWeight = getString(wd.getPlanWeight());
					String planTimes = getString(wd.getPlanTimes());
					if ("0".equals(planWeight)) {
						planTimes = "力竭";
					}
					obj.accumulate("id", wd.getId()).accumulate("planWeight", planWeight)
							.accumulate("planIntervalSeconds", getString(wd.getPlanIntervalSeconds()))
							.accumulate("planTimes", planTimes)
							.accumulate("intensity", getString(wd.getIntensity()))
							.accumulate("planDistance", getString(wd.getPlanDistance())).accumulate("planDuration", j)
							.accumulate("planStartSound",
									getString(wd.getPlanStartSound() == null ? "" : wd.getPlanStartSound()))
							.accumulate("planGroupSpaceSound",
									getString(wd.getPlanGroupSpaceSound() == null ? "" : wd.getPlanGroupSpaceSound()));
					jarr.add(obj);
				}
				actObj.accumulate("groups", jarr);
				ret.accumulate("success", true).accumulate("action", actObj);
				log.error("动作组次明细数据" + ret.toString());
				response(ret);
			} else {
				ret.accumulate("success", true).accumulate("action", actObj);
				response(ret);
			}
		} catch (Exception e) {
			log.error("error", e);
			response(e);
		}
	}

	/**
	 * 展示动作播放
	 */
	public void showAction() {
		try {
			final Action a = (Action) service.load(Action.class, id);
			final JSONObject obj = new JSONObject();
			final String video = a.getVideo() != null ? BaiduUtils.getSignPath(BUCKET, a.getVideo()) : null;
			obj.accumulate("id", a.getId()).accumulate("name", a.getName()).accumulate("image", a.getImage())
					.accumulate("flash", a.getFlash()).accumulate("video", video).accumulate("descr", a.getDescr())
					.accumulate("regard", a.getRegard());
			final JSONObject ret = new JSONObject();
			ret.accumulate("success", true).accumulate("action", obj);
			response(ret);
		} catch (Exception e) {
			log.error("error", e);
			response(e);
		}
	}

	/**
	 * 保存组次
	 */
	public void saveGroup() {
		int type = Integer.parseInt(request.getParameter("type"));
		try {
			final JSONArray arr = JSONArray.fromObject(URLDecoder.decode(jsons, "UTF-8"));
			log.error("保存组次JSON串：" + arr.toString());
			final JSONObject obj = arr.getJSONObject(0);
			final Workout wo = new Workout();
			wo.setId(id);
			WorkoutDetail wd = new WorkoutDetail();
			wd.setWorkout(wo);
			if (obj.containsKey("id") && !"".equals(obj.getString("id")))
				wd = (WorkoutDetail) service.load(WorkoutDetail.class, obj.getLong("id"));
			if (type == 0) {
				if (obj.containsKey("planWeight"))
					wd.setPlanWeight(obj.getString("planWeight"));
				if (obj.containsKey("planIntervalSeconds"))
					wd.setPlanIntervalSeconds(obj.getString("planIntervalSeconds"));
				if (obj.containsKey("planTimes"))
					wd.setPlanTimes(obj.getString("planTimes"));
				if (obj.containsKey("intensity"))
					wd.setIntensity(obj.getString("intensity"));
				if (obj.containsKey("planDistance"))
					wd.setPlanDistance(obj.getString("planDistance"));
				if (obj.containsKey("planDuration"))
					wd.setPlanDuration(obj.getString("planDuration"));
				if (obj.containsKey("planStartSound"))
					wd.setPlanStartSound(obj.getString("planStartSound"));
				if (obj.containsKey("planGroupSpaceSound"))
					wd.setPlanGroupSpaceSound(obj.getString("planGroupSpaceSound"));
			} else {
				if (obj.containsKey("actualWeight"))
					wd.setActualWeight(obj.getString("actualWeight"));
				if (obj.containsKey("actualIntervalSeconds"))
					wd.setActualIntervalSeconds(obj.getString("actualIntervalSeconds"));
				if (obj.containsKey("actualTimes"))
					wd.setActualTimes(obj.getString("actualTimes"));
			}
			wd = (WorkoutDetail) service.saveOrUpdate(wd);
			final JSONObject ret = new JSONObject();
			ret.accumulate("success", true).accumulate("key", wd.getId());
			response(ret);
		} catch (Exception e) {
			log.error("error", e);
			response(new JSONObject().accumulate("success", false).accumulate("msg", e));
		}
	}

	/**
	 * 删除组次
	 */
	public void deleteGroup() {
		try {
			service.delete(WorkoutDetail.class, id);
			final JSONObject ret = new JSONObject();
			ret.accumulate("success", true).accumulate("message", "OK");
			response(ret);
		} catch (Exception e) {
			log.error("error", e);
			response(e);
		}
	}

	/**
	 * 删除计划动作
	 */
	@SuppressWarnings("unused")
	public void deletePlan() {
		try {
			// service.delete(Workout.class, ids);
			StringBuilder wrokoutDetail = new StringBuilder("delete from tb_workout_detail where workout in(");
			StringBuilder wrokout = new StringBuilder("delete from tb_workout where id in(");
			for (Long id : ids) {
				wrokout.append("?,");
				wrokoutDetail.append("?,");
			}
			wrokout.delete(wrokout.length() - 1, wrokout.length());
			wrokout.append(")");
			wrokoutDetail.delete(wrokoutDetail.length() - 1, wrokoutDetail.length());
			wrokoutDetail.append(")");
			DataBaseConnection.updateData(wrokoutDetail.toString(), ids);
			DataBaseConnection.updateData(wrokout.toString(), ids);
			final JSONObject ret = new JSONObject();
			ret.accumulate("success", true).accumulate("msg", "OK");
			response(ret);
		} catch (Exception e) {
			log.error("error", e);
			response(new JSONObject().accumulate("success", false).accumulate("msg", e));
		}
	}

	/**
	 * 预约课程
	 */
	public void appointment() {
		try {
			// 测试数据
			// JSONObject jsonObject = new JSONObject();
			// jsonObject.accumulate("course", 15)
			// .accumulate("planDate", "2017-08-31")
			// .accumulate("startTime", "6:00")
			// .accumulate("endTime", "7:00")
			// .accumulate("place", "光谷广场24楼")
			// .accumulate("member", 274)
			// ;
			// JSONArray jsonArray = new JSONArray();
			// jsonArray.add(jsonObject);
			// String jsons = jsonArray.toString();

			final JSONObject ret = new JSONObject();
			// MobileUser mu = getMobileUser();
			BaseMember mu = getLoginMember();
			if (type == 1) { // 会员在教练课表中进行预约
				final JSONArray arr = JSONArray.fromObject(URLDecoder.decode(jsons, "UTF-8"));
				final JSONObject obj = arr.getJSONObject(0);
				final Long userId = mu.getId();
				Course course = new Course();
				// CourseInfo courseInfo = new
				// CourseInfo(obj.getLong("course"));
				CourseInfo courseInfo = (CourseInfo) service.load(CourseInfo.class, obj.getLong("course"));
				course.setCourseInfo(courseInfo);
				course.setPlanDate(obj.getString("planDate"));
				course.setStartTime(obj.getString("startTime"));
				course.setEndTime(obj.getString("endTime"));
				course.setPlace(obj.getString("place"));
				course.setColor("#ffcc00");
				course.setMember(new Member(userId));
				course.setCoach(new Member(obj.getLong("member")));

				boolean isExist = false;
				final Long requestMember = mu.getId();
				for (Apply apply : course.getApplys()) {
					final String status = apply.getStatus();
					System.out.println(status);
					if (apply.getMember().getId().equals(requestMember) && (status.equals("1") || status.equals("2"))) {
						isExist = true;
						break;
					}
				}
				if (isExist) {
					ret.accumulate("success", false).accumulate("message", "joining");// joining:您已经预约过此课程，不再再次进行预约！
					response(ret);
					return;
				}
				// 判断该会员预约的该课程时间段的是否还有其它课程
				final List<?> list = service.findObjectBySql(
						"from Course c where c.member.id = ? and c.planDate = ? and ((c.startTime = ? and c.endTime = ?) or (? between c.startTime and c.endTime) or (? between c.startTime and c.endTime))",
						requestMember, course.getPlanDate(), course.getStartTime(), course.getEndTime(),
						course.getStartTime(), course.getEndTime());
				if (list.size() > 0) {
					ret.accumulate("success", false).accumulate("message", "exist");// exist:您当前时间有其它的课程，请确认！
					response(ret);
				} else {
					course = service.saveAppointment(course);
					final Member m = (Member) service.load(Member.class, obj.getLong("member"));// 加载教练信息
					new MessageThread(m.getUserId(), m.getChannelId(), m.getTermType(), "会员“" + mu.getName() + "”预约了您"
							+ course.getPlanDate() + "日" + course.getStartTime() + "分“的课程!");
					ret.accumulate("success", true).accumulate("key", course.getId());
					response(ret);
				}
			} else { // 会员预约俱乐部的课程
				if (courseId == null)
					throw new LogicException("未传入课程ID号");
				final Course course = (Course) service.load(Course.class, courseId);
				if (course.getJoinNum() == null)
					course.setJoinNum(0);
				if (course.getJoinNum() == course.getCount()) {
					ret.accumulate("success", false).accumulate("message", "full");// full:当前课程已经满员，不能进行预约，请确认！
					response(ret);
					return;
				}
				boolean isExist = false;
				final Long requestMember = mu.getId();
				for (Apply apply : course.getApplys()) {
					final String status = apply.getStatus();
					System.out.println(status);
					if (apply.getMember().getId().equals(requestMember) && (status.equals("1") || status.equals("2"))) {
						isExist = true;
						break;
					}
				}
				if (isExist) {
					ret.accumulate("success", false).accumulate("message", "joining");// joining:您已经预约过此课程，不再再次进行预约！
					response(ret);
					return;
				}
				// 判断该会员预约的该课程时间段的是否还有其它课程
				final List<?> list = service.findObjectBySql(
						"from Course c where c.member.id = ? and c.planDate = ? and ((c.startTime = ? and c.endTime = ?) or (? between c.startTime and c.endTime) or (? between c.startTime and c.endTime))",
						requestMember, course.getPlanDate(), course.getStartTime(), course.getEndTime(),
						course.getStartTime(), course.getEndTime());
				if (list.size() > 0) {
					ret.accumulate("success", false).accumulate("message", "exist");// exist:您当前时间有其它的课程，请确认！
					response(ret);
				} else {
					final Integer joinNum = service.saveRequest(course, new Member(requestMember));
					ret.accumulate("success", true).accumulate("message", "OK").accumulate("value", joinNum);
					response(ret);
				}
			}
		} catch (Exception e) {
			log.error("error", e);
			response(e);
		}
	}

	/**
	 * 查找当前用户的所有课程
	 */
	public void loadCourseInfo() {
		try {
			List<?> list = null;
			if (id == null) {
				final Long userId = getMobileUser().getId();
				final Member m = (Member) service.load(Member.class, userId);
				if (m.getRole().equals("M")) {// 会员没有私教，则取系统默认的课程
					if (m.getCoach() == null)
						list = service.findObjectBySql("from CourseInfo ci where ci.member.id = ?", 1l);
					else
						list = service.findObjectBySql("from CourseInfo ci where ci.member.id = ?",
								m.getCoach().getId());
				} else {
					list = service.findObjectBySql("from CourseInfo ci where ci.member.id = ?", userId);
					if (list.size() <= 0)
						list = service.findObjectBySql("from CourseInfo ci where ci.member.id = ?", 1l);
				}
			} else {
				list = service.findObjectBySql("from CourseInfo ci where ci.member.id = ?", id);
				if (list.size() <= 0)
					list = service.findObjectBySql("from CourseInfo ci where ci.member.id = ?", 1l);
			}
			final JSONArray jarr = new JSONArray();
			for (final Iterator<?> it = list.iterator(); it.hasNext();) {
				final CourseInfo ci = (CourseInfo) it.next();
				final JSONObject obj = new JSONObject();
				obj.accumulate("id", ci.getId()).accumulate("name", ci.getName());
				jarr.add(obj);
			}
			final JSONObject obj = new JSONObject();
			obj.accumulate("success", true).accumulate("items", jarr);
			response(obj);
		} catch (Exception e) {
			log.error("error", e);
			response(e);
		}
	}

	/**
	 * 撤消预约
	 */
	public void undoAppointment() {
		try {
			final JSONObject obj = new JSONObject();
			final Message msg = new Message();
			Object[] objx = { id };
			final Course c = (Course) service.load(Course.class, id);
			msg.setContent("会员【" + getMobileUser().getName() + "】取消了您的" + c.getPlanDate() + "日" + c.getStartTime()
					+ "的名为【" + c.getCourseInfo().getName() + "】的课程的预约！");
			msg.setMemberFrom(new Member(getMobileUser().getId()));
			msg.setIsRead("0");
			msg.setSendTime(new Date());
			msg.setStatus("2");
			msg.setType("3");
			if (type == 1) {
				// 撤消私教课预约
				String deleteCourse = "delete from tb_course where id = ?";
				DataBaseConnection.updateData(deleteCourse, objx);
				// service.delete(Course.class, id);
				Member coach = c.getCoach();
				msg.setMemberTo(coach);
				new MessageThread(coach.getUserId(), coach.getChannelId(), coach.getTermType(), msg.getContent());
			} else {
				// 撤消团体课预约
				final List<?> list = service.findObjectBySql("from Apply a where a.member.id = ? and a.course.id = ?",
						getMobileUser().getId(), id);
				if (list.size() > 0) {
					final Apply app = (Apply) list.get(0);
					c.setJoinNum(c.getJoinNum() - 1);
					service.saveOrUpdate(c);
					service.delete(app);
				}
				msg.setMemberTo(c.getMember());
			}
			service.saveOrUpdate(msg);
			obj.accumulate("success", true).accumulate("message", "OK");
			response(obj);
		} catch (Exception e) {
			response(e);
		}
	}

	/**
	 * 加载所有项目及动作
	 */
	public void loadAction() {
		try {
			final Long cId = id == null ? getMobileUser().getId() : id;
			final Member mb = (Member) service.load(Member.class, cId);
			List<?> list = service.findObjectBySql("from ProjectApplied p where p.member = ? and p.applied = ?", cId,
					'1');
			if (list.size() <= 0)
				list = service.findObjectBySql("from ProjectApplied p where p.member = ? and p.applied = ?", 1l, '1');
			final JSONArray jarr = new JSONArray();
			for (final Iterator<?> it = list.iterator(); it.hasNext();) {
				final Project p = ((ProjectApplied) it.next()).getProject();
				final JSONObject jobj = new JSONObject();
				jobj.accumulate("id", p.getId()).accumulate("name", p.getName()).accumulate("shortName",
						p.getShortName());
				final List<?> parts = service.findObjectBySql(
						"from Part p where (p.member = ? or p.member = ?) and p.project.id = ?", cId, 1l, p.getId());
				final JSONArray jarr1 = new JSONArray();
				for (final Iterator<?> it1 = parts.iterator(); it1.hasNext();) {
					final Part part = (Part) it1.next();
					final JSONObject jobj1 = new JSONObject();
					jobj1.accumulate("id", part.getId()).accumulate("name", part.getName()).accumulate("actions",
							getActionJson(service.findActionsByProjectAndPart(p.getId(), part.getId(), mb)));
					jarr1.add(jobj1);
				}
				jobj.accumulate("parts", jarr1);
				jarr.add(jobj);
			}
			final JSONObject ret = new JSONObject();
			ret.accumulate("success", true).accumulate("items", jarr);
			response(ret);
		} catch (Exception e) {
			log.error("error", e);
			response(e);
		}
	}

	private String getActionJson(List<Action> acts) {
		final JSONArray jarr = new JSONArray();
		for (final Action a : acts) {
			final JSONObject obj = new JSONObject();
			obj.accumulate("id", a.getId()).accumulate("name", a.getName())
					.accumulate("image", getString(a.getImage()).split("/")[1])
					.accumulate("flash", getString(a.getFlash())).accumulate("video", getString(a.getVideo()));
			jarr.add(obj);
		}
		log.error("动作组次明细数据1" + jarr.toString());
		return jarr.toString();
	}

	public void test() {
		response(new JSONObject().accumulate("msg", "uuu"));
	}

	/**
	 * getter and setter
	 */
	public Long getCourseId() {
		return courseId;
	}

	public void setCourseId(Long courseId) {
		this.courseId = courseId;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public Integer[] getWeeks() {
		return weeks;
	}

	public void setWeeks(Integer[] weeks) {
		this.weeks = weeks;
	}

	public File getImage1() {
		return image1;
	}

	public void setImage1(File image1) {
		this.image1 = image1;
	}

	public File getImage2() {
		return image2;
	}

	public void setImage2(File image2) {
		this.image2 = image2;
	}

	public File getImage3() {
		return image3;
	}

	public void setImage3(File image3) {
		this.image3 = image3;
	}

	public String getImage1FileName() {
		return image1FileName;
	}

	public void setImage1FileName(String image1FileName) {
		this.image1FileName = image1FileName;
	}

	public String getImage2FileName() {
		return image2FileName;
	}

	public void setImage2FileName(String image2FileName) {
		this.image2FileName = image2FileName;
	}

	public String getImage3FileName() {
		return image3FileName;
	}

	public void setImage3FileName(String image3FileName) {
		this.image3FileName = image3FileName;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getMusicId() {
		return musicId;
	}

	public void setMusicId(Long musicId) {
		this.musicId = musicId;
	}

	@Override
	public Integer getCurrentPage() {
		return currentPage;
	}

	@Override
	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}

	public Integer getTime() {
		return time;
	}

	public void setTime(Integer time) {
		this.time = time;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public Integer getSaveType() {
		return saveType;
	}

	public void setSaveType(Integer saveType) {
		this.saveType = saveType;
	}

	public Integer getMemberId() {
		return memberId;
	}

	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}

	public Date getPlanDate() {
		return planDate;
	}

	public void setPlanDate(Date planDate) {
		this.planDate = planDate;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

}
