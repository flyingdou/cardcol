package com.cardcol.web.member.action;

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

import com.databaseConnection.DataBaseConnection;
import com.freegym.web.basic.Member;
import com.freegym.web.config.Action;
import com.freegym.web.course.Apply;
import com.freegym.web.mobile.BasicJsonAction;
import com.freegym.web.order.Goods;
import com.freegym.web.plan.Course;
import com.freegym.web.plan.Workout;
import com.freegym.web.plan.WorkoutDetail;
import com.sanmen.web.core.bean.BaseMember;
import com.sanmen.web.core.utils.BaiduUtils;
import com.sanmen.web.core.utils.DateUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Namespace("")
@InterceptorRefs({ @InterceptorRef("mobileStack") })
public class MCourse45ManageAction extends BasicJsonAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5539152114664356966L;
	/**
	 * 
	 */
	private Integer type;
	/**
	 * 计划时间
	 */
	protected Date planDate;
	/**
	 * 被访问者角色类型
	 */
	protected String role;

	public Date getPlanDate() {
		return planDate;
	}

	public void setPlanDate(Date planDate) {
		this.planDate = planDate;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	/**
	 * 私人订制详情 传参数 id=1为王严专家
	 */
	public void loadPlan() {
		try {
			final JSONObject obj = new JSONObject();
			final Goods good = (Goods) service.load(Goods.class, id);
			final Member member = (Member) service.load(Member.class, good.getMember());
			obj.accumulate("success", true).accumulate("item",
					good.toJsons().accumulate("memberName", member.getName()));
			response(obj);
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
			final List<?> list = service.findObjectBySql("from Action a where a.part.project.mode = '0' and a.part.member = 1");
			final JSONArray jarr = new JSONArray();
			for (final Iterator<?> it = list.iterator(); it.hasNext();) {
				final Action a = (Action) it.next();
				final JSONObject obj = new JSONObject();
				String imagePath =a.getImage();
				if (imagePath.contains("picture")) {
					imagePath = imagePath.replaceAll("picture/", "");
				}
				obj.accumulate("id", a.getId()).accumulate("name", a.getName()).accumulate("image", imagePath).accumulate("flash", a.getFlash()).accumulate("video",
						a.getVideo());
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
			final BaseMember mu = getMobileUser();
			final Long userId = mu.getId();
			if (mu.getRole().equals("M")) { // 会员
				list = service.findObjectBySql(
						"from Course cs where (cs.member.id = ? or cs.id in (select a.course.id from Apply a where a.member.id = ? and a.status = ?)) and cs.planDate between ? and ? order by cs.planDate, cs.startTime",
						new Object[] { userId, userId, "2", sDate, eDate });
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
					.accumulate("member", getMemberJson(c.getMember())).accumulate("coach", getMemberJson(c.getCoach())).accumulate("planDate", c.getPlanDate())
					.accumulate("startTime", c.getStartTime()).accumulate("endTime", c.getEndTime()).accumulate("courseId", c.getCourseInfo().getId())
					.accumulate("course", c.getCourseInfo().getName()).accumulate("place", c.getPlace() == null ? "" : c.getPlace()).accumulate("count", getInteger(c.getCount()))
					.accumulate("joinNum", getInteger(c.getJoinNum())).accumulate("hasPartake", hasPartake).accumulate("memberPrice", getDouble(c.getMemberPrice()))
					.accumulate("hourPrice", getDouble(c.getHourPrice())).accumulate("memo", c.getMemo() == null ? "" : c.getMemo())
					.accumulate("music", c.getMusic() != null ? c.getMusic().toJson() : null).accumulate("countdown", c.getCountdown() == null ? "" : c.getCountdown())
					.accumulate("cycleCount", c.getCycleCount() == null ? "" : c.getCycleCount());
			jarr.add(obj);
		}
		System.out.println(jarr);
		ret.accumulate("success", true).accumulate("items", jarr);
		response(ret);
	}

	/**
	 * 选中课程的动作列表
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void findPlanByCourse() {
		try {
			final List<?> list = service.findObjectBySql("from Workout wo where wo.course.id = ? order by wo.sort", id);
			// 2017-7-18 优化 sql 提高查询效率 
			long sum = service.queryForLong("SELECT SUM(planDuration) from tb_workout_detail d  LEFT JOIN tb_workout  w ON d.workout = w.id where w.course = ?",id);
			
			final JSONObject ret = new JSONObject();
			final JSONArray jarr = new JSONArray();
			
			// 单个动作时长
			String preSql = " select * from ( "
					      + " select SUM(planDuration) as preTime from tb_workout_detail wd LEFT JOIN tb_workout w ON wd.workout = w.id where w.id = ?  "
					      + " ) temp  ";
			
			for (final Iterator<?> it = list.iterator(); it.hasNext();) {
				final Workout wo = (Workout) it.next();
				
				// 获取单个动作时长
				Object[] preObj = {wo.getId()};
				Map<String, Object> preMap = DataBaseConnection.getOne(preSql, preObj);
				
				final JSONObject obj = new JSONObject(), actObj = new JSONObject();
				String imagePath = wo.getAction().getImage();
				if (imagePath.contains("picture")) {
					imagePath = imagePath.replaceAll("picture/", "");
				}
				actObj.accumulate("id", wo.getAction().getId()).accumulate("name", wo.getAction().getName()).accumulate("flash", getString(wo.getAction().getFlash()))
						.accumulate("video", getString(wo.getAction().getVideo())).accumulate("image", imagePath)
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
						str.append("重量" + planWeightList.get(0).toString().substring(0,planWeightList.get(0).toString().lastIndexOf(".")) + ",");
					} else {
						Collections.sort(planWeightList);
						if (planWeightList.get(0) == planWeightList.get(planWeightList.size() - 1)) {
							str.append("重量" + planWeightList.get(0).toString().substring(0,planWeightList.get(0).toString().lastIndexOf(".")) + "公斤");
						} else {
							// 2017-7-18 hwj 修改成只显示最大值
							str.append("重量" +  planWeightList.get(planWeightList.size() - 1).toString().substring(0,planWeightList.get(planWeightList.size() - 1).toString().lastIndexOf(".")) + "公斤");
						}
					}
				}
				
				String douStr = str.toString();
				Character mode = wo.getAction().getPart().getProject().getMode();
			    String	strs = douStr.split("重量")[1];
			    strs = strs.split("公斤")[0];
				Integer douWeight = Integer.valueOf(strs);
				if (douWeight == 0 ){
					if (mode == '0') {
					 // 有氧运动，显示时间
					 Integer preTime = Double.valueOf(String.valueOf(preMap.get("preTime"))).intValue();
					 String timeStr =  preTime%60 == 0 ? (preTime/60 + "分") : ((preTime/60) + "分" + preTime%60 + "秒");
					 timeStr = "时间: " + timeStr;
					 str = new StringBuilder(timeStr);
					} else {
					 // 非有氧运动，显示组数，力竭
					 String[] douStrs = douStr.split(",");
					 douStr = douStrs[0] + ",力竭";
					 str = new StringBuilder(douStr);
					}
				}
				obj.accumulate("workoutId", wo.getId()).accumulate("mode", wo.getAction().getPart().getProject().getMode()).accumulate("action", actObj).accumulate("detail",
						str.toString());
				jarr.add(obj);
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
	
	
	
	

}
