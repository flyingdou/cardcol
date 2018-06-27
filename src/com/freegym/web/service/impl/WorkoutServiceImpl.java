package com.freegym.web.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.freegym.web.basic.Member;
import com.freegym.web.config.Action;
import com.freegym.web.config.Factory;
import com.freegym.web.config.FactoryCosts;
import com.freegym.web.config.Part;
import com.freegym.web.plan.Course;
import com.freegym.web.plan.Diet;
import com.freegym.web.plan.DietDetail;
import com.freegym.web.plan.Workout;
import com.freegym.web.plan.WorkoutDetail;
import com.freegym.web.service.IWorkoutService;
import com.freegym.web.system.Ticket;
import com.sanmen.web.core.common.LogicException;
import com.sanmen.web.core.system.Parameter;
import com.sanmen.web.core.utils.DateUtil;

import net.sf.json.JSONObject;

@Service("workoutService")
public class WorkoutServiceImpl extends OrderServiceImpl implements IWorkoutService {

	// (2015/1/15 wh0708006)将isDiet类型改成String
	@Override
	public String findMonthPlanStatus(Long member, String planDate, String isDiet) throws ParseException {
		// 0为未计划，1为未完成，2为已完成
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		int days = DateUtil.getMaxDayForMonth(planDate);
		Date[] dates = DateUtil.getMinMaxDateByMonth(planDate);
		String sDate = sdf.format(dates[0]), eDate = sdf.format(dates[1]);
		Map<Integer, Integer> status = new HashMap<Integer, Integer>();
		JSONObject obj = new JSONObject();
		for (int i = 0; i < days; i++) {
			obj.put("day" + (i + 1), 0);
			status.put(i + 1, 0);
		}
		log.info(obj.toString());
		// (2015/1/15 wh0708006)增加查教练下课表
		if (isDiet.equals("A")) {
			loadDietCalendar(obj, sDate, eDate, member);
		} else if (isDiet.equals("B")) {
			loadWorkoutCalendar(obj, sDate, eDate, member);
		} else if (isDiet.equals("C")) {

			loadCoachWorkoutCalendar(obj, sDate, eDate, member);
		}
		return obj.toString();
	}

	private void loadDietCalendar(JSONObject status, String sDate, String eDate, Long member) throws ParseException {
		final List<?> list = getHibernateTemplate().find(
				"from Diet d where d.member = ? and d.planDate between ? and ? order by d.planDate",
				new Object[] { member, sDate, eDate });
		final Calendar c = Calendar.getInstance();
		final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Diet d = null;
		int day = 0;
		for (Iterator<?> it = list.iterator(); it.hasNext();) {
			d = (Diet) it.next();
			c.setTime(sdf.parse(d.getPlanDate()));
			day = c.get(Calendar.DAY_OF_MONTH);
			status.put("day" + day, 1);
		}
	}

	private void loadWorkoutCalendar(JSONObject status, String sDate, String eDate, Long member) throws ParseException {
		List<?> list = getHibernateTemplate().find(
				"from Course c where c.member.id = ? and c.planDate between ? and ? order by c.planDate",
				new Object[] { member, sDate, eDate });
		Calendar c = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Course wk = null;
		int day = 0;
		for (Iterator<?> it = list.iterator(); it.hasNext();) {
			wk = (Course) it.next();
			c.setTime(sdf.parse(wk.getPlanDate()));
			day = c.get(Calendar.DAY_OF_MONTH);
			status.put("day" + day, wk.getDoneDate() == null ? 1 : 2);
		}
	}

	private void loadCoachWorkoutCalendar(JSONObject status, String sDate, String eDate, Long member)
			throws ParseException {
		List<?> list = getHibernateTemplate().find(
				"from Course c where c.coach.id = ? and c.planDate between ? and ? order by c.planDate",
				new Object[] { member, sDate, eDate });
		Calendar c = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Course wk = null;
		int day = 0;
		for (Iterator<?> it = list.iterator(); it.hasNext();) {
			wk = (Course) it.next();
			c.setTime(sdf.parse(wk.getPlanDate()));
			day = c.get(Calendar.DAY_OF_MONTH);
			status.put("day" + day, wk.getDoneDate() == null ? 1 : 2);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Part> findPartsByProjects(final Long projectId, final Long coachId) {
		if (projectId != null)
			return (List<Part>) getHibernateTemplate().find(
					"from Part p where p.project.id = ? and (p.member = ? or p.member = ?) order by p.id", projectId,
					coachId, 1l);
		return new ArrayList<Part>();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Action> findActionsByProjectAndPart(final Long projectId, final Long partId, Member member) {
		if (partId != null)
			return (List<Action>) getHibernateTemplate().find(
					"from Action a where a.part.id in (select p.id from Part p where p.project.id = ?) and a.part.id = ? and (a.member = ? or a.member = ? or a.member = ?) order by a.part.id, a.id",
					projectId, partId, 1l, member.getId(), member.getCoachId());
		if (projectId != null)
			return (List<Action>) getHibernateTemplate().find(
					"from Action a where a.part.id in (select p.id from Part p where p.project.id = ?) and (a.member = ? or a.member = ? or a.member = ?) order by a.part.id, a.id",
					projectId, 1l, member.getId(), member.getCoachId());
		return new ArrayList<Action>();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Diet> findDietByUserAndDate(final Long member, final String planDate) {
		return (List<Diet>) getHibernateTemplate()
				.find("from Diet d where d.member = ? and d.planDate = ? order by d.startTime", member, planDate);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void deleteDiet(final Long id, final Long[] ids) {
		// delete(DietDetail.class, ids);
		Diet diet = getHibernateTemplate().load(Diet.class, id);
		for (final Iterator<DietDetail> it = diet.getDetails().iterator(); it.hasNext();) {
			final DietDetail dd = it.next();
			for (int i = 0; i < ids.length; i++) {
				if (ids[i].equals(dd.getId())) {
					getHibernateTemplate().delete(dd);
					it.remove();
					break;
				}
			}
		}
		if (diet.getDetails().size() <= 0)
			getHibernateTemplate().delete(diet);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void copy(Course course, Long toMember, String sDate, String eDate, Integer[] weeks) throws ParseException {
		// 得到当前计划信息
		Course p = getHibernateTemplate().get(Course.class, course.getId());
		if (p == null)
			throw new LogicException("请先选择一个有效的计划日期再进行复制！");

		Date startDate = DateUtil.parseDate(sDate);
		Date endDate = DateUtil.parseDate(eDate);
		Calendar cal = Calendar.getInstance();
		cal.setTime(startDate);
		try {
			// 先删除当前用户其它的计划
			// List<Course> courses = (List<Course>)
			// getHibernateTemplate().find("from Course c where c.member.id = ?
			// and c.planDate between ? and ?",
			// new Object[] { toMember, sDate, eDate });
			// for (Course p1 : courses)
			// getHibernateTemplate().delete(p1);

			while (!cal.getTime().after(endDate)) {
				for (int day : weeks) {
					cal.set(Calendar.DAY_OF_WEEK, day);
					if (!cal.getTime().after(endDate) && !cal.getTime().before(startDate)) {
						String copyToPlanDate = DateUtil.getDateString(cal.getTime());
						Course copyToCourse = new Course(toMember, p.getCoach(), p.getCourseInfo(), copyToPlanDate,
								p.getStartTime(), p.getEndTime(), p.getPlace(), p.getMemo(), p.getMusic(),
								p.getCycleCount(), p.getCountdown());
						copyToCourse = getHibernateTemplate().merge(copyToCourse);
						for (Workout wo : p.getWorks()) {
							Workout newWork = new Workout(copyToCourse, wo.getAction(), wo.getSort());
							newWork = getHibernateTemplate().merge(newWork);
							for (WorkoutDetail wd : wo.getDetails()) {
								WorkoutDetail newDetail = new WorkoutDetail(newWork, wd.getActualIntervalSeconds(),
										wd.getActualTimes(), wd.getActualWeight(), wd.getIntensity(),
										wd.getPlanIntervalSeconds(), wd.getPlanTimes(), wd.getPlanTips(),
										wd.getPlanWeight(), wd.getPlanDistance(), wd.getPlanDuration(),
										wd.getPlanStartSound(), wd.getPlanGroupSpaceSound());
								getHibernateTemplate().merge(newDetail);
							}
						}
					}
				}
				cal.add(Calendar.WEEK_OF_YEAR, 1);
				cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new LogicException(e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void copy(final Diet diet, final Long toMember, final String sDate, final String eDate,
			final Integer[] weeks) throws ParseException {
		// 得到当前计划信息
		final Diet t = getHibernateTemplate().load(Diet.class, diet.getId());
		final List<?> list = getHibernateTemplate().find("from Diet d where d.planDate = ? and d.member = ?",
				t.getPlanDate(), t.getMember());
		if (list.size() <= 0)
			throw new LogicException("请先选择一个有效的计划日期再进行复制！");

		final Date startDate = DateUtil.parseDate(sDate);
		final Date endDate = DateUtil.parseDate(eDate);
		final Calendar cal = Calendar.getInstance();
		cal.setTime(startDate);
		try {
			// 先删除当前用户其它的计划
			final List<Diet> diets = (List<Diet>) getHibernateTemplate().find(
					"from Diet d where d.member = ? and d.planDate between ? and ?",
					new Object[] { toMember, sDate, eDate });
			for (Diet d1 : diets)
				getHibernateTemplate().delete(d1);

			while (!cal.getTime().after(endDate)) {
				for (int day : weeks) {
					cal.set(Calendar.DAY_OF_WEEK, day);
					if (!cal.getTime().after(endDate) && !cal.getTime().before(startDate)) {
						for (final Iterator<?> it = list.iterator(); it.hasNext();) {
							final Diet d = (Diet) it.next();
							String copyToPlanDate = DateUtil.getDateString(cal.getTime());
							Diet copyToDiet = new Diet(toMember, copyToPlanDate);
							copyToDiet.setEndTime(d.getEndTime());
							copyToDiet.setStartTime(d.getStartTime());
							copyToDiet.setMealNum(d.getMealNum());
							copyToDiet = getHibernateTemplate().merge(copyToDiet);
							for (DietDetail dd : d.getDetails()) {
								final DietDetail newDetail = new DietDetail(copyToDiet, dd.getMealName(),
										dd.getMealWeight(), dd.getMealKcal());
								getHibernateTemplate().merge(newDetail);
							}
						}
					}
				}
				cal.add(Calendar.WEEK_OF_YEAR, 1);
				cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new LogicException(e);
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Course saveWorkout(final Course course, final List<Workout> workouts) {
		if (course.getMusic() != null && course.getMusic().getId() == null)
			course.setMusic(null);
		course.setCreateTime(new Date());
		final Course c = getHibernateTemplate().merge(course);
		final List<Workout> works = new ArrayList<Workout>();
		if (workouts != null) {
			int i = 0;
			for (Workout wk : workouts) {
				if (wk == null)
					continue;
				wk.setCourse(c);
				// wk.setSort(i);
				wk = getHibernateTemplate().merge(wk);
				works.add(i, wk);
				i++;
			}
			c.getWorks().addAll(works);
		}
		return c;
	}

	@Override
	public Double findWeightByMember(final Long id) {
		final List<?> wds = getHibernateTemplate().find("from WorkoutDetail wd where wd.workout.course.member.id = ?",
				id);
		Double weights = 0d;
		for (final Iterator<?> it = wds.iterator(); it.hasNext();) {
			final WorkoutDetail wd = (WorkoutDetail) it.next();
			weights = weights + (wd.getActualWeight() == null || "".equals(wd.getActualWeight()) ? 0d
					: new Double(wd.getActualWeight()));
		}
		return weights;
	}

	@Override
	public Double findWeightByMember(Long id, Date doneDate) {
		final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		final List<?> wds = getHibernateTemplate().find(
				"from WorkoutDetail wd where wd.workout.course.member.id = ? and wd.workout.course.planDate = ?", id,
				sdf.format(doneDate));
		Double weights = 0d;
		for (final Iterator<?> it = wds.iterator(); it.hasNext();) {
			final WorkoutDetail wd = (WorkoutDetail) it.next();
			weights = weights + (wd.getActualWeight() == null || "".equals(wd.getActualWeight()) ? 0d
					: new Double(wd.getActualWeight()));
		}
		return weights;
	}

	/**
	 * 查找当前活动所有未满员的团队
	 */
	@Override
	public List<Map<String, Object>> findTeamByActive(Long id) {
		final StringBuffer sql = new StringBuffer(
				"SELECT a.*, b.team_num FROM (SELECT a.active, MAX(a.orderStartTime) AS orderStartTime, a.team, COUNT(*) AS partake_num, b.name AS team_name FROM tb_active_order a LEFT JOIN tb_active_team b ON a.team = b.id WHERE a.team IS NOT NULL GROUP BY a.active, a.team, b.name) a LEFT JOIN tb_active b ON a.active = b.id WHERE a.partake_num < b.team_num AND a.active = ?");
		return jdbc.queryForList(sql.toString(), id);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Course saveAppointment(Course course) {
		final List<?> list = getHibernateTemplate().find(
				"from Course c where c.coach.id = ? and c.planDate = ? and c.startTime= ? and c.endTime = ?",
				course.getCoach().getId(), course.getPlanDate(), course.getStartTime(), course.getEndTime());
		if (list.size() > 0)
			throw new LogicException("该教练的当前时间已经存在其它课程，请不要再次申请！");
		course = getHibernateTemplate().merge(course);
		return course;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Parameter> findProjectForValid(Long toMember) {
		return (List<Parameter>) getHibernateTemplate().find(
				"from Parameter p where p.parent in (select id from Parameter p1 where p1.code = ?) and p.id in (select project from Factory f where f.club = ? and f.applied = ?) order by p.code",
				"course_type_c", toMember, '1');
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<FactoryCosts> findFactoryCostsByMember(Long toMember, Long factoryId, String planDate) {
		return (List<FactoryCosts>) getHibernateTemplate().find(
				"from FactoryCosts fc where fc.factory.club = ? and fc.factory.id = ? and fc.planDate = ? order by  fc.starttime ",
				toMember, factoryId, planDate);
	}

	@Override
	public Integer findMessageCount(Long toMember) {
		final List<?> list = getHibernateTemplate().find(
				"from Message c where c.memberTo.id = ? and c.isRead = ? and c.parent is null and c.type != ? ",
				new Object[] { toMember, "0", "3" });
		return list.size();

	}

	@Override
	public List<?> findBannerBySectorId(Long sectorId) {
		if (sectorId != null)
			return getHibernateTemplate().find("from Banner b where b.section.id = ?", sectorId);
		else
			return getHibernateTemplate().find("from Banner b");
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Factory> findFactoryByMember(Long member, String projectId) {
		return (List<Factory>) getHibernateTemplate().find(
				"from Factory f where f.club = ? and f.applied = ? and f.project = ? order by f.sort ", member, '1',
				projectId);
	}

	@Override
	public Ticket findTicketByActiveCode(String activeCode) {
		final List<?> list = getHibernateTemplate().find("from Ticket t where t.activeCode = ? and t.effective = ?",
				activeCode, "1");
		return (Ticket) (list.size() > 0 ? list.get(0) : null);
	}

	@Override
	public List<?> findMemberTicketByCode(String activeCode, Long id) {
		return getHibernateTemplate().find("from MemberTicket mt where mt.member.id = ? and mt.activeCode = ?", id,
				activeCode);
	}

	@Override
	public List<Map<String, Object>> findRecommendBySectorCode(String[] code) {
		final StringBuffer sb = new StringBuffer();
		final Object[] args = new Object[code.length];
		for (int i = 0; i < code.length; i++) {
			args[i] = code[i];
			sb.append(sb.length() <= 0 ? "" : ",").append("?");
		}
		StringBuffer sql = new StringBuffer(
				"SELECT sr.id, sr.color,date_format(sr.recomm_date, '%Y-%m-%d')as recomm_date , sr.recomm_id, sr.recomm_type, sr.sector, sr.stick_time, sr.summary, sr.title, case when sr.icon is null then p.image else sr.icon end icon, case when sr.recomm_type != '8' then p.href else sr.link end link, p.* FROM TB_SYSTEM_RECOMMEND sr LEFT JOIN ( ");
		sql.append(
				"SELECT '8' rType, e.id rId, e.title rName, e.icon image,'' memberId, '' memberName,e.summary rSummary,'' category,'' value,'' days,'' award,'' amerce_Money,'' institutionName, sector sectorId, concat('detail.asp?channel=', sector, '&id=', id) href FROM tb_article e");
		sql.append(" UNION ALL ");
		sql.append(
				"SELECT '9' rType, f.id rId, f.name rName, f.icon image,'' memberId, '' memberName,'' rSummary,'' category,'' value,'' days,'' award,'' amerce_Money,'' institutionName, 0 sectorId, link href FROM tb_banner f ");
		sql.append(") p ON sr.recomm_id = rId AND sr.recomm_type = rType ");
		sql.append("left join tb_sector b on sr.sector = b.id ");
		sql.append("left join tb_parameter c on p.rType = c.code and c.parent = 21 ");
		sql.append("where b.code in (" + sb.toString() + ") order by sr.recomm_date desc");
		return jdbc.queryForList(sql.toString(), args);
	}
}
