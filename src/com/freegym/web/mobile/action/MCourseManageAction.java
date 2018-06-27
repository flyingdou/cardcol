package com.freegym.web.mobile.action;

import java.io.File;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.http.ProtocolType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.aliyuncs.push.model.v20160801.PushRequest;
import com.aliyuncs.push.model.v20160801.PushResponse;
import com.freegym.web.basic.Member;
import com.freegym.web.config.Action;
import com.freegym.web.config.CourseInfo;
import com.freegym.web.config.Part;
import com.freegym.web.config.Project;
import com.freegym.web.config.ProjectApplied;
import com.freegym.web.course.Apply;
import com.freegym.web.course.Message;
import com.freegym.web.mobile.CourseJsonAction;
import com.freegym.web.order.Goods;
import com.freegym.web.order.Product;
import com.freegym.web.plan.Course;
import com.freegym.web.plan.Music;
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
@InterceptorRefs({ @InterceptorRef("customStack") })
public class MCourseManageAction extends CourseJsonAction {

	private static final long serialVersionUID = -4900715126840183203L;

	/**
	 * 课表列表用类型，1月，2周，3日, 课表撤消用类型1为私教课，2为团体课, 组次类型0为计划，1为实际 计划订单类型：3为健身计划订单
	 * 6为智能计划订单
	 */
	protected int type;

	/**
	 * 被访问者角色类型
	 */
	protected String role;

	/**
	 * 计划时间
	 */
	protected Date planDate;

	/**
	 * 当前城市
	 */
	protected String city;

	/**
	 * 被预约的课程
	 */
	protected Long courseId;

	protected String startDate, endDate;

	protected Integer[] weeks;

	protected File image1, image2, image3;

	protected String image1FileName, image2FileName, image3FileName;

	protected Long userId;

	protected Long musicId;

	protected int currentPage;

	/**
	 * 关键字 计划名称
	 */
	protected String keyword;

	protected String planType, applyObject, scene;

	/**
	 * 筛选条件
	 */
	protected String county; // 地区
	protected Long typeId; // 服务项目
	protected String isAll;// 是否筛选3天

	/**
	 * 计划查询时间范围 ： A 小于8天 B 8-31天 C 32-92天 D 大于92天
	 */
	private String planSearchDay;

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public Date getPlanDate() {
		return planDate;
	}

	public void setPlanDate(Date planDate) {
		this.planDate = planDate;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		if (city != null && !"".equals(city)) city = urlDecode(city);
		this.city = city;
	}

	public String getCounty() {
		return county;
	}

	public void setCounty(String county) {
		if (county != null && !"".equals(county)) county = urlDecode(county);
		this.county = county;
	}

	public Long getTypeId() {
		return typeId;
	}

	public void setTypeId(Long typeId) {
		this.typeId = typeId;
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

	public Long getCourseId() {
		return courseId;
	}

	public void setCourseId(Long courseId) {
		this.courseId = courseId;
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

	public String getKeyword() {
		return keyword;
	}

	public String getPlanType() {
		return planType;
	}

	public void setPlanType(String planType) {
		this.planType = planType;
	}

	public String getApplyObject() {
		return applyObject;
	}

	public void setApplyObject(String applyObject) {
		this.applyObject = applyObject;
	}

	public String getScene() {
		return scene;
	}

	public void setScene(String scene) {
		this.scene = scene;
	}

	public String getPlanSearchDay() {
		return planSearchDay;
	}

	public void setPlanSearchDay(String planSearchDay) {
		this.planSearchDay = planSearchDay;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public void setKeyword(String keyword) {
		if (keyword != null && !keyword.equals("")) keyword = urlDecode(keyword);
		this.keyword = keyword;
	}

	public String getIsAll() {
		return isAll;
	}

	public void setIsAll(String isAll) {
		this.isAll = isAll;
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

	@Override
	protected void list() {
		final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if (planDate == null) planDate = new Date();
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
				list = service.findObjectBySql("from Course cs where cs.coach.id = ? and cs.member.role <> ? and cs.planDate between ? and ? order by cs.planDate, cs.startTime",
						new Object[] { userId, "E", sDate, eDate });
			}
		} else { // 访问其它用户的课表
			// 判断有无关系
			hasRel = service.hasRelation(getMobileUser().getId(), id);
			if (role.equals("E")) { // 被访问者为俱乐部
				list = service.findObjectBySql("from Course cs where cs.member.id = ? and cs.planDate between ? and ? order by cs.planDate, cs.startTime",
						new Object[] { id, sDate, eDate });
			} else if (role.equals("S")) { // 被访问者为教练
				list = service.findObjectBySql("from Course cs where cs.coach.id = ? and cs.planDate between ? and ? order by cs.planDate, cs.startTime",
						new Object[] { id, sDate, eDate });
			}
		}
		final JSONArray jarr = new JSONArray();
		final JSONObject ret = new JSONObject();
		for (final Iterator<?> it = list.iterator(); it.hasNext();) {
			final Course c = (Course) it.next();
			int hasPartake = 0;
			for (final Apply a : c.getApplys()) {
				if (a.getMember().getId().equals(getMobileUser().getId()) && "2".equals(a.getStatus())) {
					hasPartake = 1;
					break;
				}
			}
			final JSONObject obj = new JSONObject();
			obj.accumulate("id", c.getId()).accumulate("image", c.getCourseInfo().getImage())
					// .accumulate("address", c.getMember().getAddress())
					.accumulate("member", getMemberJson(c.getMember())).accumulate("coach", getMemberJson(c.getCoach())).accumulate("planDate", c.getPlanDate())
					.accumulate("startTime", c.getStartTime()).accumulate("endTime", c.getEndTime()).accumulate("courseId", c.getCourseInfo().getId())
					.accumulate("course", c.getCourseInfo().getName()).accumulate("place", c.getPlace() == null ? "" : c.getPlace()).accumulate("count", getInteger(c.getCount()))
					.accumulate("joinNum", getInteger(c.getJoinNum())).accumulate("hasPartake", hasPartake).accumulate("memberPrice", getDouble(c.getMemberPrice()))
					.accumulate("hourPrice", getDouble(c.getHourPrice())).accumulate("memo", c.getMemo() == null ? "" : c.getMemo())
					.accumulate("music", c.getMusic() != null ? c.getMusic().toJson() : null).accumulate("countdown", c.getCountdown() == null ? "" : c.getCountdown())
					.accumulate("cycleCount", c.getCycleCount() == null ? "" : c.getCycleCount());
			jarr.add(obj);
		}
		ret.accumulate("success", true).accumulate("hasRel", hasRel).accumulate("items", jarr);
		response(ret);
	}

	/**
	 * 查找选中的课表的所有计划
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void findPlanByCourse() {
		try {
			final List<?> list = service.findObjectBySql("from Workout wo where wo.course.id = ? order by wo.sort", id);
			final JSONObject ret = new JSONObject();
			final JSONArray jarr = new JSONArray();
			for (final Iterator<?> it = list.iterator(); it.hasNext();) {
				final Workout wo = (Workout) it.next();
				final JSONObject obj = new JSONObject(), actObj = new JSONObject();
				actObj.accumulate("id", wo.getAction().getId()).accumulate("name", wo.getAction().getName()).accumulate("flash", getString(wo.getAction().getFlash()))
						.accumulate("video", getString(wo.getAction().getVideo())).accumulate("image", getString(wo.getAction().getImage()))
						.accumulate("descr", wo.getAction().getDescr()).accumulate("regard", wo.getAction().getRegard());

				final List<?> detailList = service.findObjectBySql("from WorkoutDetail wo where wo.workout.id = ?", wo.getId());
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
							str.append("每组" + planTimesList.get(0) + "-" + planTimesList.get(planTimesList.size() - 1) + "次,");
						}
					}

					if (planWeightIsnumber) {
						str.append("重量" + planWeightList.get(0) + ",");
					} else {
						Collections.sort(planWeightList);
						if (planWeightList.get(0) == planWeightList.get(planWeightList.size() - 1)) {
							str.append("重量" + planWeightList.get(0) + "公斤,");
						} else {
							str.append("重量" + planWeightList.get(0) + "-" + planWeightList.get(planWeightList.size() - 1) + "公斤,");
						}
					}

					// Collections.sort(planTimesList);
					// Collections.sort(planWeightList);
					// str.append(detailList.size() + "组,");
					// if (planTimesList.get(0) ==
					// planTimesList.get(planTimesList.size() - 1)) {
					// str.append("每组" + planTimesList.get(0) + "次,");
					// } else {
					// str.append("每组" + planTimesList.get(0) + "-" +
					// planTimesList.get(planTimesList.size() - 1) + "次,");
					// }
					// if (planWeightList.get(0) ==
					// planWeightList.get(planWeightList.size() - 1)) {
					// str.append("重量" + planWeightList.get(0) + "公斤,");
					// } else {
					// str.append("重量" + planWeightList.get(0) + "-" +
					// planWeightList.get(planWeightList.size() - 1) + "公斤,");
					// }
				}

				obj.accumulate("id", wo.getId()).accumulate("mode", wo.getAction().getPart().getProject().getMode()).accumulate("action", actObj).accumulate("detail",
						str.toString());
				jarr.add(obj);
			}
			log.error("动作组次明细数据1" + jarr.toString());
			ret.accumulate("success", true).accumulate("items", jarr);
			response(ret);
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

			final List<?> list = service.findObjectBySql("from WorkoutDetail wo where wo.workout.id = ? order by wo.sort asc ", id);
			final List<?> list1 = service.findObjectBySql("from Workout wk where wk.id = ?", id);
			if (list1.size() > 0) {
				final Workout wk = (Workout) list1.get(0);
				final String video = wk.getAction() != null && wk.getAction().getVideo() != null ? BaiduUtils.getSignPath(BUCKET, wk.getAction().getVideo()) : null;
				final String music = wk.getCourse() != null && wk.getCourse().getMusic() != null && wk.getCourse().getMusic().getAddr() != null
						? wk.getCourse().getMusic().getAddr() : null;
				actObj.accumulate("video", video).accumulate("music", music);
			}

			if (list.size() > 0) {

				for (final Iterator<?> it = list.iterator(); it.hasNext();) {
					final WorkoutDetail wd = (WorkoutDetail) it.next();
					final JSONObject obj = new JSONObject();
					double d = Double.parseDouble(wd.getPlanDuration());
					int j = (int) d;
					obj.accumulate("id", wd.getId()).accumulate("planWeight", getString(wd.getPlanWeight())).accumulate("actualWeight", getString(wd.getActualWeight()))
							.accumulate("planIntervalSeconds", getString(wd.getPlanIntervalSeconds())).accumulate("actualIntervalSeconds", getString(wd.getActualIntervalSeconds()))
							.accumulate("planTimes", getString(wd.getPlanTimes())).accumulate("actualTimes", getString(wd.getActualTimes()))
							.accumulate("intensity", getString(wd.getIntensity())).accumulate("planDistance", getString(wd.getPlanDistance())).accumulate("planDuration", j)
							.accumulate("planStartSound", getString(wd.getPlanStartSound() == null ? "" : wd.getPlanStartSound()))
							.accumulate("planGroupSpaceSound", getString(wd.getPlanGroupSpaceSound() == null ? "" : wd.getPlanGroupSpaceSound()));
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
	 * 保存计划
	 */
	public void savePlan() {
		try {
			final JSONArray arr = JSONArray.fromObject(URLDecoder.decode(jsons, "UTF-8"));
			log.error("保存计划JSON串：" + arr.toString());
			final List<Workout> wos = new ArrayList<Workout>();
			for (final Iterator<?> it = arr.listIterator(); it.hasNext();) {
				final JSONObject obj = (JSONObject) it.next();
				final Workout wo = new Workout();
				wo.setAction(new Action(obj.getLong("actionId")));
				wo.setCourse(new Course(id));
				wos.add(wo);
			}
			final List<?> list = service.saveOrUpdate(wos);
			final JSONObject ret = new JSONObject();
			ret.accumulate("success", true).accumulate("items", generatorPlanJson(list));
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
	 * 删除计划动作
	 */
	public void deletePlan() {
		try {
			service.delete(Workout.class, ids);
			final JSONObject ret = new JSONObject();
			ret.accumulate("success", true).accumulate("message", "OK");
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
		try {
			final JSONArray arr = JSONArray.fromObject(URLDecoder.decode(jsons, "UTF-8"));
			log.error("保存组次JSON串：" + arr.toString());
			final JSONObject obj = arr.getJSONObject(0);
			final Workout wo = new Workout();
			wo.setId(id);
			WorkoutDetail wd = new WorkoutDetail();
			wd.setWorkout(wo);
			if (obj.containsKey("id") && !"".equals(obj.getString("id"))) wd = (WorkoutDetail) service.load(WorkoutDetail.class, obj.getLong("id"));
			if (type == 0) {
				if (obj.containsKey("planWeight")) wd.setPlanWeight(obj.getString("planWeight"));
				if (obj.containsKey("planIntervalSeconds")) wd.setPlanIntervalSeconds(obj.getString("planIntervalSeconds"));
				if (obj.containsKey("planTimes")) wd.setPlanTimes(obj.getString("planTimes"));
				if (obj.containsKey("intensity")) wd.setIntensity(obj.getString("intensity"));
				if (obj.containsKey("planDistance")) wd.setPlanDistance(obj.getString("planDistance"));
				if (obj.containsKey("planDuration")) wd.setPlanDuration(obj.getString("planDuration"));
				if (obj.containsKey("planStartSound")) wd.setPlanStartSound(obj.getString("planStartSound"));
				if (obj.containsKey("planGroupSpaceSound")) wd.setPlanGroupSpaceSound(obj.getString("planGroupSpaceSound"));
			} else {
				if (obj.containsKey("actualWeight")) wd.setActualWeight(obj.getString("actualWeight"));
				if (obj.containsKey("actualIntervalSeconds")) wd.setActualIntervalSeconds(obj.getString("actualIntervalSeconds"));
				if (obj.containsKey("actualTimes")) wd.setActualTimes(obj.getString("actualTimes"));
			}
			wd = (WorkoutDetail) service.saveOrUpdate(wd);
			final JSONObject ret = new JSONObject();
			ret.accumulate("success", true).accumulate("key", wd.getId());
			response(ret);
		} catch (Exception e) {
			log.error("error", e);
			response(e);
		}
	}

	/**
	 * 发布计划
	 */
	public void releasePlan() {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			final JSONArray arr = JSONArray.fromObject(URLDecoder.decode(jsons, "UTF-8"));
			log.error("发布计划JSON串：" + arr.toString());
			final JSONObject obj = arr.getJSONObject(0);
			PlanRelease plan = new PlanRelease(id);
			String fileName1 = image1 != null ? saveFile("picture", image1, image1FileName, null) : null;
			String fileName2 = image2 != null ? saveFile("picture", image2, image2FileName, null) : null;
			String fileName3 = image3 != null ? saveFile("picture", image3, image3FileName, null) : null;
			if (fileName1 != null) {
				plan.setImage1(fileName1);
			}
			if (fileName2 != null) {
				plan.setImage2(fileName2);
			}
			if (fileName3 != null) {
				plan.setImage3(fileName3);
			}
			plan.setPlanName(obj.getString("planName"));
			plan.setPlanType(obj.getString("planType"));
			plan.setScene(obj.getString("scene"));
			plan.setApplyObject(obj.getString("applyObject"));
			plan.setApparatuses(obj.getString("apparatuses"));
			plan.setStartDate(null != obj.get("startDate") ? sdf.parse(obj.getString("startDate")) : null);
			plan.setEndDate(null != obj.get("endDate") ? sdf.parse(obj.getString("endDate")) : null);
			plan.setUnitPrice(obj.getDouble("unitPrice"));
			plan.setBriefing(obj.getString("briefing"));
			plan.setAudit("1");
			plan.setIsClose("2");

			if (obj.containsKey("details")) {
				plan.setDetails(obj.getString("details"));
			}

			final Long userId = getMobileUser().getId();
			final Member m = (Member) service.load(Member.class, userId);
			plan.setMember(m);
			if (obj.getString("member") != null) {
				final Long plan_participant_long = Long.parseLong(obj.getString("member"));
				final Member plan_participant = (Member) service.load(Member.class, plan_participant_long);
				plan.setPlan_participant(plan_participant);
			}
			plan.setPublishTime(new Date());
			plan.setPlanDay((int) ((plan.getEndDate().getTime() - plan.getStartDate().getTime()) / 1000 / 60 / 60 / 24) + 1);
			plan = (PlanRelease) service.saveOrUpdate(plan);
			final JSONObject ret = new JSONObject();
			ret.accumulate("success", true).accumulate("key", plan.getId());
			response(ret);
		} catch (Exception e) {
			log.error("error", e);
			response(e);
		}
	}

	/**
	 * 展示计划音乐
	 */
	public void listMusic() {
		try {
			List<?> list = null;
			if (userId == null) userId = getMobileUser().getId();
			list = service.findObjectBySql("from Music m where m.member.id = ?", userId);
			final JSONArray jarr = new JSONArray();
			for (final Iterator<?> it = list.iterator(); it.hasNext();) {
				final Music music = (Music) it.next();
				jarr.add(music.toJson());
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
	 * 修改计划音乐
	 */
	public void updateCourseMusic() {
		try {
			final Course c = (Course) service.load(Course.class, courseId);
			Music music = new Music();
			music.setId(musicId);
			c.setMusic(music);
			service.saveOrUpdate(c);
			final JSONObject ret = new JSONObject();
			ret.accumulate("success", true).accumulate("message", "OK");
			response(ret);
		} catch (Exception e) {
			log.error("error", e);
			response(e);
		}
	}

	/**
	 * 删除计划音乐
	 */
	public void deleteCourseMusic() {
		try {
			final Course c = (Course) service.load(Course.class, courseId);
			c.setMusic(null);
			service.saveOrUpdate(c);
			final JSONObject ret = new JSONObject();
			ret.accumulate("success", true).accumulate("message", "OK");
			response(ret);
		} catch (Exception e) {
			log.error("error", e);
			response(e);
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
	 * 生成计划列表的JSON数据
	 * 
	 * @param list
	 * @return
	 */
	private String generatorPlanJson(final List<?> list) {
		final JSONArray jarr = new JSONArray();
		for (final Iterator<?> it = list.iterator(); it.hasNext();) {
			final Workout wo = (Workout) it.next();
			final JSONObject obj = new JSONObject(), actObj = new JSONObject();
			actObj.accumulate("id", wo.getAction().getId()).accumulate("name", wo.getAction().getName()).accumulate("flash", getString(wo.getAction().getFlash()))
					.accumulate("video", getString(wo.getAction().getVideo())).accumulate("image", getString(wo.getAction().getImage()))
					.accumulate("descr", wo.getAction().getDescr()).accumulate("regard", wo.getAction().getRegard());
			obj.accumulate("id", wo.getId()).accumulate("mode", wo.getAction().getPart().getProject().getMode()).accumulate("action", actObj);
			jarr.add(obj);
		}
		log.error("动作组次明细数据1" + jarr.toString());
		return jarr.toString();
	}

	/**
	 * 加载所有项目及动作
	 */
	public void loadAction() {
		try {
			final Long cId = id == null ? getMobileUser().getId() : id;
			final Member mb = (Member) service.load(Member.class, cId);
			List<?> list = service.findObjectBySql("from ProjectApplied p where p.member = ? and p.applied = ?", cId, '1');
			if (list.size() <= 0) list = service.findObjectBySql("from ProjectApplied p where p.member = ? and p.applied = ?", 1l, '1');
			final JSONArray jarr = new JSONArray();
			for (final Iterator<?> it = list.iterator(); it.hasNext();) {
				final Project p = ((ProjectApplied) it.next()).getProject();
				final JSONObject jobj = new JSONObject();
				jobj.accumulate("id", p.getId()).accumulate("name", p.getName()).accumulate("shortName", p.getShortName());
				final List<?> parts = service.findObjectBySql("from Part p where (p.member = ? or p.member = ?) and p.project.id = ?", cId, 1l, p.getId());
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
			obj.accumulate("id", a.getId()).accumulate("name", a.getName()).accumulate("image", getString(a.getImage())).accumulate("flash", getString(a.getFlash()))
					.accumulate("video", getString(a.getVideo()));
			jarr.add(obj);
		}
		log.error("动作组次明细数据1" + jarr.toString());
		return jarr.toString();
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
					if (m.getCoach() == null) list = service.findObjectBySql("from CourseInfo ci where ci.member.id = ?", 1l);
					else list = service.findObjectBySql("from CourseInfo ci where ci.member.id = ?", m.getCoach().getId());
				} else {
					list = service.findObjectBySql("from CourseInfo ci where ci.member.id = ?", userId);
					if (list.size() <= 0) list = service.findObjectBySql("from CourseInfo ci where ci.member.id = ?", 1l);
				}
			} else {
				list = service.findObjectBySql("from CourseInfo ci where ci.member.id = ?", id);
				if (list.size() <= 0) list = service.findObjectBySql("from CourseInfo ci where ci.member.id = ?", 1l);
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
		if (obj.containsKey("id")) course.setId(obj.getLong("id"));
		course.setCourseInfo(new CourseInfo(obj.getLong("course")));
		course.setPlanDate(obj.getString("planDate"));
		course.setStartTime(obj.getString("startTime"));
		course.setEndTime(obj.getString("endTime"));
		course.setPlace(obj.getString("place"));
		course.setMemo(obj.getString("memo"));
		course.setCycleCount(obj.getInt("cycleCount"));
		course.setCountdown(obj.getInt("countdown"));
		course.setColor("#ffcc00");
		if (role.equals("S")) {// 教练制订课程，给会员
			course.setPlanSource("1");
			course.setMember(new Member(obj.getLong("member")));
			course.setCoach(new Member(userId));
		} else if (role.equals("M")) {
			course.setPlanSource("2");
			course.setMember(new Member(userId));
		}
		course = (Course) service.saveOrUpdate(course);
		return course.getId();
	}

	/**
	 * 删除指定的课程
	 */
	@Override
	protected void executeDelete() {
		try {
			service.delete(Course.class, id);
			final JSONObject ret = new JSONObject();
			ret.accumulate("success", true).accumulate("message", "OK");
			response(ret);
		} catch (Exception e) {
			log.error("error", e);
			response(e);
		}
	}

	/**
	 * 复制课程
	 */
	public void copy() {
		try {
			if (id == null) id = getMobileUser().getId();
			final JSONObject ret = new JSONObject();
			service.copy(new Course(courseId), id, startDate, endDate, weeks);
			ret.accumulate("success", true).accumulate("message", "OK");
			response(ret);
		} catch (Exception e) {
			log.error("error", e);
			response(e);
		}
	}

	/**
	 * 清除课程
	 */
	public void clean() {
		final BaseMember mu = getMobileUser();
		try {
			final Long keyId = (id == null ? mu.getId() : id);
			final JSONObject obj = new JSONObject();
			final List<?> list = service.findObjectBySql("from Course c where c.member.id = ? and planDate between ? and ?", keyId, startDate, endDate);
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

	// /**
	// * 课程评分
	// */
	// public void grade() {
	// try {
	// final JSONObject ret = new JSONObject();
	// JSONArray arr = JSONArray.fromObject(URLDecoder.decode(jsons, "UTF-8"));
	// final JSONObject obj = arr.getJSONObject(0);
	// final Long id = obj.getLong("id");
	// final String memo = obj.getString("memo");
	// final String score = String.valueOf(obj.getDouble("score"));
	// final Long cId = getMobileUser().getId();
	// // 判断是否评分过
	// final List<?> list =
	// service.findObjectBySql("from CourseGrade cg where cg.member.id = ? and
	// cg.course.id = ?",
	// cId, id);
	// if (list.size() > 0) {
	// ret.accumulate("success", false).accumulate("message",
	// "您已经给当前课表评过分，不得再次进行评分！");
	// } else {
	// final CourseGrade cg = service.saveCourseGrade(cId, id, score, memo);
	// ret.accumulate("success", true).accumulate("key", cg.getId());
	// }
	// response(ret);
	// } catch (Exception e) {
	// log.error("error", e);
	// response(e);
	// }
	//
	// response("{success: true, message: 'OK'}");
	// }

	/**
	 * 预约课程
	 */
	public void appointment() {
		try {
			final JSONObject ret = new JSONObject();
			// MobileUser mu = getMobileUser();
			BaseMember mu = getLoginMember();
			if (type == 1) { // 会员在教练课表中进行预约
				final JSONArray arr = JSONArray.fromObject(URLDecoder.decode(jsons, "UTF-8"));
				final JSONObject obj = arr.getJSONObject(0);
				final Long userId = mu.getId();
				Course course = new Course();
				course.setCourseInfo(new CourseInfo(obj.getLong("course")));
				course.setPlanDate(obj.getString("planDate"));
				course.setStartTime(obj.getString("startTime"));
				course.setEndTime(obj.getString("endTime"));
				course.setPlace(obj.getString("place"));
				course.setColor("#ffcc00");
				course.setMember(new Member(userId));
				course.setCoach(new Member(obj.getLong("member")));
				// 2014/12/26 增加备注字段
				course.setMemo(obj.getString("memo"));
				course = service.saveAppointment(course);
				final Member m = (Member) service.load(Member.class, obj.getLong("member"));
				new MessageThread(m.getUserId(), m.getChannelId(), m.getTermType(), "会员“" + mu.getName() + "”预约了您" + course.getPlanDate() + "日" + course.getStartTime() + "分“的课程!");
				ret.accumulate("success", true).accumulate("key", course.getId());
				response(ret);
			} else { // 会员预约俱乐部的课程
				if (courseId == null) throw new LogicException("未传入课程ID号");
				final Course course = (Course) service.load(Course.class, courseId);
				if (course.getJoinNum() == null) course.setJoinNum(0);
				if (course.getJoinNum() == course.getCount()) {
					ret.accumulate("success", false).accumulate("message", "当前课程已经满员，不能进行预约，请确认！");
					response(ret);
					return;
				}
				boolean isExist = false;
				final Long requestMember = mu.getId();
				for (Apply apply : course.getApplys()) {
					final String status = apply.getStatus();
					if (apply.getMember().getId().equals(requestMember) && (status.equals("1") || status.equals("2"))) {
						isExist = true;
						break;
					}
				}
				if (isExist) {
					ret.accumulate("success", false).accumulate("message", "您已经预约过此课程，不再再次进行预约！");
					response(ret);
					return;
				}
				// 判断该会员预约的该课程时间段的是否还有其它课程
				final List<?> list = service.findObjectBySql(
						"from Course c where c.member.id = ? and c.planDate = ? and ((c.startTime = ? and c.endTime = ?) or (? between c.startTime and c.endTime) or (? between c.startTime and c.endTime))",
						requestMember, course.getPlanDate(), course.getStartTime(), course.getEndTime(), course.getStartTime(), course.getEndTime());
				if (list.size() > 0) {
					ret.accumulate("success", false).accumulate("message", "您当前时间有其它的课程，请确认！");
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
	 * 撤消预约
	 */
	public void undoAppointment() {
		try {
			final JSONObject obj = new JSONObject();
			final Message msg = new Message();
			final Course c = (Course) service.load(Course.class, id);
			msg.setContent("会员【" + getMobileUser().getName() + "】取消了您的" + c.getPlanDate() + "日" + c.getStartTime() + "的名为【" + c.getCourseInfo().getName() + "】的课程的预约！");
			msg.setMemberFrom(new Member(getMobileUser().getId()));
			msg.setIsRead("0");
			msg.setSendTime(new Date());

			msg.setStatus("2");
			msg.setType("3");
			if (type == 1) {
				// 撤消私教课预约
				service.delete(Course.class, id);
				Member coach = c.getCoach();
				msg.setMemberTo(coach);
				PushRequest pushRequest = new PushRequest();
				IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", "LTAIEBpPW8aDSjKX", "x9wTapvynUDLiyHg3zdWuVjGs3JFxj");
				Long appkey = (long) 23580685;
				DefaultAcsClient client = new DefaultAcsClient(profile);
				pushRequest.setProtocol(ProtocolType.HTTPS);
				pushRequest.setMethod(MethodType.POST);
				pushRequest.setActionName("Push");
				pushRequest.setRegionId("cn-hangzhou");
				pushRequest.setVersion("2016-08-01");
				pushRequest.setAppKey(appkey);
				pushRequest.setTarget("ACCOUNT"); // 推送目标: DEVICE:按设备推送 ALIAS :
													// 按别名推送 ACCOUNT:按帐号推送
													// TAG:按标签推送; ALL: 广播推送
				pushRequest.setTargetValue(coach.getId().toString()); // 根据Target来设定，如Target=DEVICE,
																		// 则对应的值为
																		// 设备id1,设备id2.
																		// 多个值使用逗号分隔.(帐号与设备有一次最多100个的限制)
				pushRequest.setPushType("NOTICE"); // 消息类型 MESSAGE NOTICE
				pushRequest.setDeviceType("ALL"); // 设备类型 ANDROID iOS ALL.
				pushRequest.setTitle("卡库取消预约课程通知"); // 消息的标题
				pushRequest.setBody(msg.getContent()); // 消息的内容
				pushRequest.setiOSApnsEnv("PRODUCT");// DEV:开发模式; PRODUCT:生产模式
				pushRequest.setAndroidOpenType("APPLICATION"); // 点击通知后动作
																// "APPLICATION"
																// : 打开应用
																// "ACTIVITY" :
																// 打开AndroidActivity
																// "URL" : 打开URL
																// "NONE" : 无跳转
				pushRequest.setStoreOffline(true); // 离线消息是否保存,若保存,
													// 在推送时候，用户即使不在线，下一次上线则会收到
				PushResponse pushResponse = client.getAcsResponse(pushRequest);
				System.out.printf("RequestId: %s, MessageID: %s\n", pushResponse.getRequestId(), pushResponse.getMessageId());
			} else {
				// 撤消团体课预约
				final List<?> list = service.findObjectBySql("from Apply a where a.member.id = ? and a.course.id = ?", getMobileUser().getId(), id);
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
	 * 展示智能计划
	 */
	public void listGoods() {
		try {
			List<?> list = null;
			list = service.findObjectBySql("from Goods");
			final JSONArray jarr = new JSONArray();
			for (final Iterator<?> it = list.iterator(); it.hasNext();) {
				final Goods good = (Goods) it.next();
				jarr.add(good.toJson().accumulate("appraise", getGradeJson(good.getId(), "6")));
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
	 * 查询计划详情
	 */
	public void loadPlan() {
		try {
			final JSONObject obj = new JSONObject();
			if (3 == type) {
				final PlanRelease plan = (PlanRelease) service.load(PlanRelease.class, id);
				final Member member = (Member) service.load(Member.class, plan.getMember().getId());
				obj.accumulate("success", true).accumulate("item",
						plan.toJson().accumulate("memberName", member.getName()).accumulate("appraise", getGradeJson(plan.getId(), type + "")));
			} else if (6 == type) {
				final Goods good = (Goods) service.load(Goods.class, id);
				final Member member = (Member) service.load(Member.class, good.getMember());
				obj.accumulate("success", true).accumulate("item",
						good.toJson().accumulate("memberName", member.getName()).accumulate("appraise", getGradeJson(good.getId(), type + "")));
			}
			response(obj);
		} catch (Exception e) {
			log.error("error", e);
			response(e);
		}
	}

	/**
	 * 分页展示发布的计划
	 */
	@SuppressWarnings("unused")
	public void listPlans() {
		try {
			// 获取手机登陆者
			final Member member = (Member) service.load(Member.class, getMobileUser().getId());
			final DetachedCriteria dc = DetachedCriteria.forClass(PlanRelease.class);
			if (keyword != null && !"".equals(keyword)) {
				dc.add(this.or(Restrictions.like("planName", "%" + keyword + "%")));
			}

			if (planType != null && !"".equals(planType)) {
				dc.add(Restrictions.eq("planType", planType));
			}
			if (applyObject != null && !"".equals(applyObject)) {
				dc.add(Restrictions.eq("applyObject", applyObject));
			}
			if (scene != null && !"".equals(scene)) {
				if (-1 != scene.indexOf(",")) {
					String[] sceneStr = scene.split(",");
					for (int i = 0; i < sceneStr.length; i++) {
						if (null != sceneStr[i].trim() && !"".equals(sceneStr[i].trim())) {
							dc.add(Restrictions.like("scene", "%" + sceneStr[i].trim() + "%"));
						}
					}
				} else {
					dc.add(Restrictions.like("scene", "%" + scene + "%"));
				}
			}
			if (planSearchDay != null && !"".equals(planSearchDay)) {
				if ("A".equals(planSearchDay)) {
					dc.add(Restrictions.le("planDay", 7));
				} else if ("B".equals(planSearchDay)) {
					dc.add(Restrictions.ge("planDay", 8));
					dc.add(Restrictions.le("planDay", 31));
				} else if ("C".equals(planSearchDay)) {
					dc.add(Restrictions.ge("planDay", 32));
					dc.add(Restrictions.le("planDay", 92));
				} else if ("D".equals(planSearchDay)) {
					dc.add(Restrictions.ge("planDay", 93));
				}
			}

			// 这个是教练id
			if (!"S".equals(member.getRole())) {
				// 是否开启 关闭状态1：关闭2： 开启
				dc.add(Restrictions.eq("isClose", "2"));
				// 状态，1为已审核，0或NULL为未审核
				dc.add(Restrictions.eq("audit", "1"));
				if (null != userId) {
					dc.add(Restrictions.eq("member.id", userId));
				}
			} else {
				if (null != userId) {
					dc.add(Restrictions.eq("member.id", userId));
					if (userId != member.getId()) {
						// 是否开启 关闭状态1：关闭2： 开启
						dc.add(Restrictions.eq("isClose", "2"));
						// 状态，1为已审核，0或NULL为未审核
						dc.add(Restrictions.eq("audit", "1"));
					}
				} else {
					dc.add(Restrictions.or(Restrictions.and(Restrictions.ne("member.id", member.getId()), Restrictions.eq("isClose", "2"), Restrictions.eq("audit", "1")),
							Restrictions.and(Restrictions.eq("member.id", member.getId()))));
				}
			}

			pageInfo.setOrder("publishTime");
			pageInfo.setOrderFlag("desc");
			pageInfo.setPageSize(15);
			pageInfo.setCurrentPage(currentPage);
			pageInfo = service.findPageByCriteria(dc, pageInfo);
			final JSONArray jarr = new JSONArray();
			for (final Iterator<?> it = pageInfo.getItems().iterator(); it.hasNext();) {
				final PlanRelease plan = (PlanRelease) it.next();
				jarr.add(plan.toJson().accumulate("appraise", getGradeJson(plan.getId(), "3")));
			}
			final JSONObject obj = new JSONObject();
			obj.accumulate("success", true).accumulate("currentPage", pageInfo.getCurrentPage()).accumulate("totalPage", pageInfo.getTotalPage()).accumulate("items", jarr);
			response(obj);
			long end = System.currentTimeMillis();
		} catch (Exception e) {
			log.error("error", e);
			response(e);
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
			obj.accumulate("success", true).accumulate("item", plan.toJson().accumulate("isClose", plan.getIsClose()));
			response(obj);
		} catch (Exception e) {
			log.error("error", e);
			response(e);
		}
	}

	/**
	 * 改变私教状态
	 */
	public void PrivateClose() {
		try {

			final JSONObject obj = new JSONObject();
			final Product pro = (Product) service.load(Product.class, id);
			if (pro.getIsClose().equals("2")) {
				pro.setIsClose("1");
			}

			else {
				pro.setIsClose("2");
			}
			service.saveOrUpdate(pro);
			obj.accumulate("success", true).accumulate("item", pro.toJson().accumulate("isClose", pro.getIsClose()));
			response(obj);
		} catch (Exception e) {
			log.error("error", e);
			response(e);
		}
	}

	/**
	 * 展现淘课吧数据
	 */
	public void showCourse() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {

			Date date = new Date();
			String sysDate = sdf.format(date);
			String sysTime = String.format("%tH:%tM", date, date);

			final BaseMember mu = getLoginMember();

			final Member m = (Member) service.load(Member.class, mu.getId());
			Course query = new Course();
			query.setPlanDate(planDate == null ? String.format("%tY-%tm-%td", date, date, date) : sdf.format(planDate));
			query.setCoachName(city == null ? m.getCity() : city);

			final DetachedCriteria dc = Course.getCriteriaQuery(query, county, typeId, isAll);

			if (!"true".equals(isAll)) {
				if (query.getPlanDate().equals(sysDate)) {
					dc.add(Restrictions.ge("startTime", sysTime));
				} else {
					sysTime = "00:00";
					dc.add(Restrictions.ge("startTime", sysTime));
				}
			}
			if (keyword != null && !"".equals(keyword)) {
				dc.add(this.or(Restrictions.like("c.name", "%" + keyword + "%"), Restrictions.like("m.clubname", "%" + keyword)));
			}

			pageInfo.setOrder("planDate,startTime");
			pageInfo.setOrderFlag("asc,asc");
			pageInfo = service.findPageByCriteria(dc, pageInfo, 3);

			final JSONArray jarr = new JSONArray();
			for (final Iterator<?> it = pageInfo.getItems().iterator(); it.hasNext();) {
				final Course course = (Course) it.next();
				jarr.add(course.toJson().accumulate("coach", getMemberJson(course.getCoach())).accumulate("lng", getDouble(course.getCourseInfo().getMember().getLongitude()))
						.accumulate("lat", getDouble(course.getCourseInfo().getMember().getLatitude())));
			}
			final JSONObject ret = new JSONObject();
			ret.accumulate("success", true).accumulate("pageInfo", getJsonForPageInfo()).accumulate("items", jarr);
			response(ret);

		} catch (Exception e) {
			log.error("error", e);
			response(e);
		}

	}

	/**
	 * 展示课程详细信息
	 */
	public void showCourseDetail() {
		try {
			final Course c = (Course) service.load(Course.class, courseId);
			final JSONObject obj = new JSONObject();
			obj.accumulate("id", c.getId()).accumulate("courseName", c.getCourseInfo().getName()).accumulate("coach", c.getCoach().getName())
					.accumulate("planDate", c.getPlanDate()).accumulate("startTime", c.getStartTime()).accumulate("endTime", c.getEndTime()).accumulate("count", c.getCount())
					.accumulate("joinNum", c.getJoinNum()).accumulate("memberPrice", c.getMemberPrice()).accumulate("hourPrice", c.getHourPrice())
					.accumulate("courseMember", c.getMember().getId()).accumulate("address", getString(c.getMember().getAddress()))
					.accumulate("longitude", getDouble(c.getMember().getLongitude())).accumulate("latitude", getDouble(c.getMember().getLatitude()))
					.accumulate("memo", getString(c.getMemo()));
			final JSONObject ret = new JSONObject();
			ret.accumulate("success", true).accumulate("course", obj);
			response(ret);

		} catch (Exception e) {

			log.error("error", e);
			response(e);
		}
	}

	public void showAction() {
		try {
			final Action a = (Action) service.load(Action.class, id);
			final JSONObject obj = new JSONObject();
			final String video = a.getVideo() != null ? BaiduUtils.getSignPath(BUCKET, a.getVideo()) : null;
			obj.accumulate("id", a.getId()).accumulate("name", a.getName()).accumulate("image", a.getImage()).accumulate("flash", a.getFlash()).accumulate("video", video)
					.accumulate("descr", a.getDescr()).accumulate("regard", a.getRegard());
			final JSONObject ret = new JSONObject();
			ret.accumulate("success", true).accumulate("action", obj);
			response(ret);
		} catch (Exception e) {
			log.error("error", e);
			response(e);
		}
	}
}
