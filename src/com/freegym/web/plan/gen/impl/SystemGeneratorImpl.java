package com.freegym.web.plan.gen.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.freegym.web.basic.Member;
import com.freegym.web.config.Action;
import com.freegym.web.config.PlanEngineAction;
import com.freegym.web.config.PlanEngineRule;
import com.freegym.web.config.Setting;
import com.freegym.web.plan.Course;
import com.freegym.web.plan.Workout;
import com.freegym.web.plan.WorkoutDetail;
import com.freegym.web.plan.gen.AbstractGenerator;
import com.freegym.web.service.IBasicService;
import com.freegym.web.utils.DBConstant;
import com.freegym.web.utils.DateUtils;
import com.sanmen.web.core.common.LogicException;
import com.sanmen.web.core.utils.DateUtil;

public class SystemGeneratorImpl extends AbstractGenerator {

	public SystemGeneratorImpl(IBasicService service, Long coachId, Long memberId, Date startDate) {
		super(service, coachId, memberId, startDate);
	}

	@SuppressWarnings({ "unused", "unchecked" })
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public boolean generator() throws Exception {
		coachId = coachId == null ? SYSTEM_PROJECT_ID : coachId;
		final Member m = (Member) service.load(Member.class, memberId);
		final Setting setting = service.loadSetting(memberId);
		if (setting == null) throw new LogicException("请先完成您的设置！");
		// if (setting.getHeart() == null || "".equals(setting.getHeart()))
		// throw new LogicException("您的静心率尚未设置，请进入“我的设定”设置静心率。！");
		if (setting.getCardioDate() == null || "".equals(setting.getCardioDate())) throw new LogicException("您尚未设置计划每周的哪几天进行有氧训练!");
		if (setting.getCardioDuration() == null || "".equals(setting.getCardioDuration())) throw new LogicException("您尚未设置每次有氧训练用多长时间!");
		if (setting.getFavoriateCardio() == null || "".equals(setting.getFavoriateCardio())) throw new LogicException("您尚未设置所喜爱的有氧训练项目!");
		if (setting.getStrengthDate() == null || "".equals(setting.getStrengthDate())) throw new LogicException("您尚未设置计划每周的哪几天进行力量训练!");
		if (setting.getStrengthDuration() == null || "".equals(setting.getStrengthDuration())) throw new LogicException("您尚未设置每次力量训练用多长时间!");
		if (setting.getTarget() == null || "".equals(setting.getTarget())) throw new LogicException("您的健身目的尚未设置!");
		if (setting.getCurrGymStatus() == null || "".equals(setting.getCurrGymStatus())) throw new LogicException("您的健身状况尚未设置!");
		if (m.getBirthday() == null || "".equals(m.getBirthday())) throw new LogicException("请维护您的真实出生日期！");
		// 如果无教练则取系统默认的课程
		final String planDate = DateUtil.getDateString(startDate);
		System.out.println("--------------------" + startDate + "--------------------");
		// 从下一周开始计算
		Calendar cal = DateUtil.getNextWeekCalendar(planDate);
		// 删除该用户的所有计划数据，产生新的计划数据
		final List<Course> plans = (List<Course>) service.loadCourses(memberId, planDate);
		for (Course c : plans)
			service.delete(c);
		// 力量训练的生成
		int[] daysOfWeek = DateUtils.getDaysOfWeekNumbers(setting.getStrengthDate());// 每周的哪几天
		// cal = DateUtil.getNextWeekCalendar(planDate);

		Course course = null;
		final String purpose = setting.getTarget() + "";// 目的
		getCourseInfo1(purpose);
		final String userLevel = setting.getCurrGymStatus() + "";// 用户类型
		int frequency = daysOfWeek.length; // 每周天数
		final Random r = new Random();
		final List<Course> courses = new ArrayList<Course>();
		try {
			for (int month = 1; month <= 3; month++) { // 三个月，因每个月按周来重复
				for (int dayIndex = 0; dayIndex < frequency; dayIndex++) {
					int dayOfWeek = daysOfWeek[dayIndex];
					cal.set(Calendar.DAY_OF_WEEK, dayOfWeek + 1);
					final Date date = cal.getTime();

					int sort = 0;
					// 得到频率第几天的部位
					final List<PlanEngineRule> pers = (List<PlanEngineRule>) service.findObjectBySql(
							"from PlanEngineRule p where p.userLevel = ? and p.frequency = ? and p.dayIndex = ? order by p.sort", userLevel, frequency,
							dayIndex + 1);
					final List<Workout> wos = new ArrayList<Workout>();
					for (PlanEngineRule per : pers) { // 该天所有部位
						// 得到用户这个部位的规则动作
						PlanEngineAction rule = null;
						final List<PlanEngineAction> ruleActions = (List<PlanEngineAction>) service.findObjectBySql(
								"from PlanEngineAction pc where pc.userLevel = ? and pc.purpose = ? and pc.monthIndex = ? and pc.rulePart = ?", userLevel,
								purpose, month, per.getPart());
						if (ruleActions.size() <= 0) continue;
						rule = ruleActions.get(0);
						final List<Action> catalogs = service.findActionByCoachAndPart('1', 1l, per.getPart());
						List<Action> existActions = new ArrayList<Action>();
						for (int catalogSize = 0; catalogSize < rule.getRuleCatalogs(); catalogSize++, sort++) {
							Action action = catalogs.get(r.nextInt(catalogs.size()));
							while (existActions.contains(action))
								action = catalogs.get(r.nextInt(catalogs.size()));
							existActions.add(action);
							Workout wo = new Workout(null, action, sort);
							wos.add(wo);
							for (int i = 0; i < rule.getRuleSets(); i++) {
								WorkoutDetail wd = new WorkoutDetail();
								wd.setPlanWeight(rule.getRuleWeight()); // 计划重量
								wd.setPlanIntervalSeconds(String.valueOf(Integer.parseInt(rule.getRuleInterval()) * 2));// 间隔次数
								wd.setPlanTimes(rule.getRuleTimes());// 重复次数
								wd.setPlanTips(rule.getRuleWeight());
								wd.setPlanDuration(String.valueOf(Integer.parseInt(rule.getRuleTimes()) * 2));
								wo.addDetail(wd);
							}
						}
					}
					// 当这天完成后，将其复制到接下来的连续三周这一天中
					final Calendar cal1 = Calendar.getInstance();
					cal1.setTime(date);
					for (int i = 0; i <= 3; i++) {
						String dateStr = DateUtil.getDateString(cal1.getTime());
						// course = new Course(memberId, null, ci, dateStr,
						// sTime,
						// eTime, "", null, null);
						course = service.getCourse(memberId, dateStr, sTime, eTime, ci);
						course.setCoach(m.getCoach() == null ? m : m.getCoach());
						course.setPlanSource(PLAN_SOURCE_AUTO);
						course.setColor("#FF6600");
						course.setCycleCount(1);
						course.setCountdown(10);
						course = (Course) service.saveOrUpdate(course);
						for (final Workout wo : wos) {
							// course.addWorkout(wo);
							wo.setCourse(course);
							service.saveOrUpdate(wo);
						}
						// courses.add(course);
						cal1.add(Calendar.WEEK_OF_MONTH, 1);
						System.out.println(i + "----" + new SimpleDateFormat("yyyy-MM-dd").format(cal1.getTime()));
					}
					if (dayIndex == frequency - 1) cal.setTime(cal1.getTime());
				}
			}
			// 有氧训练的生成
			// 得到静心率
			final List<Action> catalogs = service.getFavoriateCardioCatalogs(setting.getFavoriateCardio(), coachId == null || coachId == -1 ? 1l : coachId);
			if (catalogs == null || catalogs.size() == 0) return false;
			daysOfWeek = DateUtils.getDaysOfWeekNumbers(setting.getCardioDate());
			int duration = Integer.valueOf(setting.getCardioDuration());
			int eachDuration = DBConstant.DEFAULT_CARDIO_DURATION;
			int groups = duration / eachDuration; // 组数
			Date d = m.getBirthday();
			Calendar c = Calendar.getInstance();
			c.setTime(d);
			Calendar c1 = Calendar.getInstance();
			Integer age = c1.get(Calendar.YEAR) - c.get(Calendar.YEAR);
			// Character bmiMode = setting.getBmiMode();
			Integer bmiHigh = setting.getBmiHigh() == null ? 85 : setting.getBmiHigh();
			Integer bmiLow = setting.getBmiLow() == null ? 55 : setting.getBmiLow();
			// Integer heart = Integer.valueOf(setting.getHeart());
			Integer minHeart = 0, maxHeart = 0;
			// if (bmiMode == '0') {// 常用估值法
			minHeart = (220 - age) * bmiLow / 100;
			maxHeart = (220 - age) * bmiHigh / 100;
			// } else {
			// minHeart = (220 - age - heart) * bmiLow / 100 + heart;
			// maxHeart = (220 - age - heart) * bmiHigh / 100 + heart;
			// }
			cal = DateUtil.getNextWeekCalendar(planDate);
			for (int month = 1; month <= 3; month++) {
				for (int n = 0; n < 4; n++) {
					for (int dayOfWeek : daysOfWeek) {
						cal.set(Calendar.DAY_OF_WEEK, dayOfWeek + 1);
						Date date = cal.getTime();
						String dateStr = DateUtil.getDateString(date);
						course = service.getCourse(memberId, dateStr, sTime, eTime, ci);
						course.setCoach(m.getCoach() == null ? m : m.getCoach());
						course.setColor("#FF6600");
						course = (Course) service.saveOrUpdate(course);
						Action randAction = catalogs.get(r.nextInt(catalogs.size()));
						Workout wo = new Workout(course, randAction, 999);
						wo = (Workout) service.saveOrUpdate(wo);
						for (int i = 0; i < groups; i++) {
							WorkoutDetail detail = new WorkoutDetail();
							detail.setWorkout(wo);
							detail.setPlanDuration(eachDuration + "");
							detail.setIntensity(minHeart + "-" + maxHeart);
							detail = (WorkoutDetail) service.saveOrUpdate(detail);
						}
					}
					cal.add(Calendar.WEEK_OF_YEAR, 1);
				}
			}
		} catch (Exception e) {
			log.error("error", e);
			e.printStackTrace();
			throw new Exception(e);
		}
		return true;
	}

	public static void main(String[] args) {
		Calendar cal = Calendar.getInstance();
		// cal.setFirstDayOfWeek(1);
		System.out.println(cal.getFirstDayOfWeek() == Calendar.SUNDAY);
		cal.add(Calendar.WEEK_OF_MONTH, 1);
		System.out.println(DateUtil.formatDate(cal.getTime()));

		String weight = "36";
		char c = ' ';
		if (weight.length() > 1) c = weight.charAt(weight.length() - 1);
		else c = weight.charAt(0);
		if (c <= '4') c = '0';
		else c = '5';
		System.out.println(weight.length() > 1 ? weight.substring(0, weight.length() - 1) + String.valueOf(c) : String.valueOf(c));

	}
}
