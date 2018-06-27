package com.freegym.web.service.impl;

import java.io.UnsupportedEncodingException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.orm.hibernate4.HibernateCallback;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.databaseConnection.DataBaseConnection;
import com.freegym.web.active.Active;
import com.freegym.web.active.Team;
import com.freegym.web.active.TeamMember;
import com.freegym.web.active.TrainRecord;
import com.freegym.web.basic.Body;
import com.freegym.web.basic.Friend;
import com.freegym.web.basic.Member;
import com.freegym.web.chinapay.config.ChinaPayConfig;
import com.freegym.web.config.Action;
import com.freegym.web.config.CourseInfo;
import com.freegym.web.config.Factory;
import com.freegym.web.config.Project;
import com.freegym.web.config.ProjectApplied;
import com.freegym.web.config.Setting;
import com.freegym.web.config.WorkTime;
import com.freegym.web.course.Appraise;
import com.freegym.web.course.BaseAppraise;
import com.freegym.web.course.Message;
import com.freegym.web.course.SignIn;
import com.freegym.web.order.ActiveOrder;
import com.freegym.web.order.PresentHeartRate;
import com.freegym.web.order.Product;
import com.freegym.web.order.ProductOrder;
import com.freegym.web.plan.Course;
import com.freegym.web.service.IBasicService;
import com.freegym.web.system.Area1;
import com.freegym.web.utils.DateUtils;
import com.freegym.web.utils.SessionConstant;
import com.freegym.web.utils.SmsUtils;
import com.sanmen.web.core.common.LogicException;
import com.sanmen.web.core.common.MD5;
import com.sanmen.web.core.common.PageInfo;
import com.sanmen.web.core.service.impl.AbstractServiceImpl;
import com.sanmen.web.core.utils.DateUtil;
import com.sanmen.web.core.utils.DoubleUtil;

import net.sf.json.JSONObject;

@Service("basicService")
public class BasicServiceImpl extends AbstractServiceImpl implements IBasicService, SessionConstant {

	protected PageInfo pageInfo = PageInfo.getInstance();

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public PageInfo findPageByCriteria(final DetachedCriteria detachedCriteria, final PageInfo pageInfo) {
		getHibernateTemplate().setCacheQueries(true);
		return (PageInfo) getHibernateTemplate().executeWithNativeSession(new HibernateCallback<Object>() {
			@Override
			public Object doInHibernate(Session session) throws HibernateException {
				final Criteria criteria = detachedCriteria.getExecutableCriteria(session);
				try {
					final Object uniqueResult = criteria.setProjection(Projections.rowCount()).uniqueResult();
					int totalCount = 0;
					if (uniqueResult instanceof Long)
						totalCount = ((Long) uniqueResult).intValue();
					else
						totalCount = ((Integer) uniqueResult).intValue();
					criteria.setProjection(null);
					if (pageInfo.getOrder() != null && !pageInfo.getOrder().equals("")) {
						final String[] orders = pageInfo.getOrder().split(",");
						final String[] flags = pageInfo.getOrderFlag().split(",");
						for (int i = 0; i < orders.length; i++) {
							if (flags[i].equalsIgnoreCase("desc"))
								criteria.addOrder(Order.desc(orders[i]));
							else
								criteria.addOrder(Order.asc(orders[i]));
						}
					}
					final List<Object> items = criteria.setFirstResult(pageInfo.getStart())
							.setMaxResults(pageInfo.getPageSize()).list();
					final PageInfo pi = PageInfo.getInstance();
					pi.setCurrentPage(pageInfo.getCurrentPage());
					pi.setPageSize(pageInfo.getPageSize());
					pi.setOrder(pageInfo.getOrder());
					pi.setOrderFlag(pageInfo.getOrderFlag());
					pi.setTotalCount(totalCount);
					pi.setItems(items);
					return pi;
				} catch (Exception e) {
					log.error("error", e);
				}
				return null;
			}
		});
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public Member login(final String mobile, final String password) {
		final List<?> list = getHibernateTemplate().find(
				"from Member u where (u.mobilephone = ? and u.mobileValid = '1' and u.password = ?) or (u.nick = ? and u.password = ? )",
				new Object[] { mobile, password, mobile, password });
		if (list.size() > 0) {
			final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Member member = (Member) list.get(0);
			member.setLoginTime(new Date());
			member.setToKen(MD5.MD5Encode(mobile + password + sdf.format(member.getLoginTime())));
			return getHibernateTemplate().merge(member);
		}
		return null;
	}

	/**
	 * 2014-11-23增加签到检核
	 */
	@Override
	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public Member sign(final String username, final String password) {
		final List<?> list = getHibernateTemplate().find(
				"from Member u where (u.email = ? or u.nick = ? or (u.mobilephone = ? and u.mobileValid = '1')) and u.signPass = ?",
				new Object[] { username, username, username, password });
		if (list.size() > 0)
			return (Member) list.get(0);
		return null;
	}

	@Override
	public TrainRecord getRecordByDate(Long userId, Date nowDate) {
		final Date[] dates = DateUtil.getDateTimes(nowDate);
		final List<?> list = getHibernateTemplate().find(
				"from TrainRecord cr where cr.partake.id = ? and cr.doneDate between ? and ? order by cr.doneDate desc",
				userId, dates[0], dates[1]);
		if (list.size() > 0) {
			final TrainRecord tr = (TrainRecord) list.get(0);
			tr.setStrength(getActualWeight(tr.getPartake().getId(), tr.getDoneDate()));
			return tr;
		} else {
			TrainRecord tr = new TrainRecord();
			tr.setPartake(new Member(userId));
			tr.setDoneDate(nowDate);
			return tr;
		}
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public TrainRecord getCurrentRecord(final Long userId) {
		final List<?> list = getHibernateTemplate()
				.find("from TrainRecord cr where cr.partake.id = ? order by cr.doneDate desc", userId);
		if (list.size() > 0) {
			final TrainRecord tr = (TrainRecord) list.get(0);
			tr.setStrength(getActualWeight(tr.getPartake().getId(), tr.getDoneDate()));
			return tr;
		}
		return null;
	}

	@Override
	public Map<String, Object> getTotalRecord(Member m) {
		// TODO Auto-generated method stub
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("finishrate", 0.0);
		map.put("time", 0.0);
		map.put("traincount", 0);
		Double timeT = 0.0;
		Double rate = 0.0;
		final List<?> list = getHibernateTemplate().find(
				"from TrainRecord cr where cr.partake.id = ? and cr.doneDate <= SYSDATE() order by cr.doneDate desc",
				m.getId());
		if (list.size() > 0) {
			for (final Iterator<?> it = list.iterator(); it.hasNext();) {
				final TrainRecord tr = (TrainRecord) it.next();
				if (tr.getTimes() != null) {
					timeT = timeT + tr.getTimes();
				}
			}
			rate = (double) list.size() / m.getWorkoutTimes();
			map.put("finishrate", rate * 100);
			map.put("time", timeT);
			map.put("traincount", list.size());
		}
		return map;
	}

	@Override
	public Setting loadSetting(final Long id) {
		final List<?> list = getHibernateTemplate().find("from Setting s where s.member = ?", id);
		if (list.size() > 0) {
			final Setting set = (Setting) list.get(0);
			if (set.getHeight() == null)
				set.setHeight(0);
			if (set.getWeight() == null)
				set.setWeight(0d);
			return set;
		} else {
			Setting setting = new Setting();
			setting.setMember(id);
			setting.setHeight(0);
			setting.setWeight(0d);
			return setting;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Friend> findFriendByType(final Long member, final String type) {
		return (List<Friend>) getHibernateTemplate().find("from Friend f where f.member = ? and f.type = ?",
				new Object[] { member, type });
	}

	@Override
	public Long findCountByWorkout(final Long id) {
		final List<?> list = getHibernateTemplate().find("select count(*) from TrainRecord wr where wr.partake.id = ?",
				id);
		if (list.size() > 0)
			return (Long) list.get(0);
		return 0l;
	}

	@Override
	public List<?> findDefaultFavoriteCardio() {
		return getHibernateTemplate().find(
				"from Action a where a.member = ? and a.part in (select id from Part p where p.project in (select id from Project p1 where p1.mode = '0'))",
				new Object[] { 1l });
	}

	@Override
	public List<Project> loadProjects(final Long id) {
		final List<?> list = getHibernateTemplate().find("from ProjectApplied p where p.member = ? order by p.sort",
				id);
		final List<Project> projects = new ArrayList<Project>();
		for (final Iterator<?> it = list.iterator(); it.hasNext();) {
			final ProjectApplied pa = (ProjectApplied) it.next();
			final Project p = pa.getProject();
			p.setApplied(pa.getApplied());
			p.setSort(pa.getSort());
			projects.add(p);
		}
		return projects;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Project> loadProjects1(final Long id) {
		final List<Project> list = (List<Project>) getHibernateTemplate()
				.find("from Project p where p.member = ? or p.member = ?", id, 1l);
		final List<ProjectApplied> pas = (List<ProjectApplied>) getHibernateTemplate()
				.find("from ProjectApplied pa where pa.member = ?", id);
		for (final Iterator<?> it = list.iterator(); it.hasNext();) {
			Project p = (Project) it.next();
			for (final ProjectApplied pa : pas) {
				if (pa.getProject().getId() == p.getId())
					p.setApplied(pa.getApplied());
			}
		}
		return list;
	}

	@Override
	public List<Project> loadProjectsByApplied(final Long id) {
		List<?> list = getHibernateTemplate().find(
				"from ProjectApplied p where p.member = ? and p.applied = ? order by p.sort", new Object[] { id, '1' });
		if (list.size() <= 0)
			list = getHibernateTemplate().find(
					"from ProjectApplied p where p.member = ? and p.applied = ? order by p.sort",
					new Object[] { 1l, '1' });
		final List<Project> projects = new ArrayList<Project>();
		for (final Iterator<?> it = list.iterator(); it.hasNext();) {
			final ProjectApplied pa = (ProjectApplied) it.next();
			projects.add(pa.getProject());
		}
		return projects;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void saveApplied(final Long member, final List<Project> projects) {
		for (final Project p : projects) {
			if (p == null)
				continue;
			if (p.getApplied() == null)
				p.setApplied('0');
			final List<?> list = getHibernateTemplate().find(
					"from ProjectApplied pa where pa.member = ? and pa.project.id = ?",
					new Object[] { member, p.getId() });
			ProjectApplied pa = null;
			if (list.size() > 0) {
				pa = (ProjectApplied) list.get(0);
			} else {
				pa = new ProjectApplied();
				pa.setProject(p);
				pa.setMember(member);
			}
			pa.setSort(p.getSort());
			pa.setApplied(p.getApplied());
			getHibernateTemplate().merge(pa);
			getHibernateTemplate().merge(p);
		}
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public Project saveProject(final Long member, Project project) {
		boolean isNew = project.getId() == null;
		if (isNew) {
			project.setMember(member);
			project.setSystem('0');
		}
		final Integer sort = project.getSort();
		project = getHibernateTemplate().merge(project);
		if (isNew) {
			final ProjectApplied pa = new ProjectApplied();
			pa.setProject(project);
			pa.setSort(sort);
			pa.setApplied(project.getApplied());
			pa.setMember(member);
			getHibernateTemplate().merge(pa);
		}
		return project;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public Member saveWorkTime(Member member, final List<WorkTime> worktimes) {
		member = (Member) this.saveOrUpdate(member);
		if (worktimes != null) {
			for (WorkTime wt : worktimes)
				wt.setMember(member.getId());
			this.saveOrUpdate(worktimes);
		}
		return member;
	}

	@Override
	public List<Action> findActionByCoachAndPart(final Character mode, final Long coachId, final String partName) {
		final List<?> list = getHibernateTemplate().find(
				"from Action a, Part p, Project pr where a.part.id = p.id and p.project.id = pr.id and a.member = ? and p.name = ? and pr.mode = ?",
				coachId, partName, mode);
		final List<Action> actions = new ArrayList<Action>();
		for (final Iterator<?> it = list.iterator(); it.hasNext();) {
			final Object[] objs = (Object[]) it.next();
			actions.add((Action) objs[0]);
		}
		return actions;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Course> loadCourses(Long memberId, String planDate) {
		return (List<Course>) getHibernateTemplate().find("from Course c where c.member.id =? and c.planDate > ?",
				memberId, planDate);
	}

	/**
	 * 取得当前用户选择的有氧训练对应的所有有氧动作集合
	 * 
	 * @param catalogNames
	 * @param coachId
	 * @return
	 */
	@Override
	public List<Action> getFavoriateCardioCatalogs(final String catalogNames, final Long coachId) {
		final String[] names = catalogNames.split("\\,");
		final StringBuffer sb = new StringBuffer();
		for (String name : names) {
			sb.append("'").append(name.trim()).append("',");
		}
		if (sb.length() > 0) {
			sb.deleteCharAt(sb.length() - 1);
		}
		final List<?> list = getHibernateTemplate()
				.find("from Action a, Part part, Project proj, ProjectApplied pa where a.part.id = part.id and part.project.id = proj.id and proj.id = pa.project.id and proj.mode = '0' and pa.member = ? and a.name in ("
						+ sb.toString() + ")", coachId);
		final List<Action> actions = new ArrayList<Action>();
		for (final Iterator<?> it = list.iterator(); it.hasNext();) {
			final Object[] objs = (Object[]) it.next();
			actions.add((Action) objs[0]);
		}
		return actions;
	}

	@Override
	public Course getCourse(final Long member, final String dateStr, final String sTime, final String eTime,
			final CourseInfo ci) {
		final List<?> list = getHibernateTemplate().find(
				"from Course c where c.member.id = ? and c.planDate = ? and c.startTime = ? and c.endTime = ?",
				new Object[] { member, dateStr, sTime, eTime });
		if (list.size() > 0)
			return (Course) list.get(0);
		return new Course(member, null, ci, dateStr, sTime, eTime, "", null, null, null, null);
	}

	/**
	 * 获取该会员所属的教练的课程，如果该会员未申请私教，则取得该用户的健身目标的对应的系统默认课程
	 */
	@Override
	public List<?> findAllCourse(Member member) {
		Long coachId = null;
		if (member == null) {
			coachId = SYSTEM_PROJECT_ID;
		} else {
			if (member.getRole().equals("S")) { // 教练
				coachId = member.getId();
			} else {
				if (member.getCoach() != null)
					coachId = member.getCoach().getId();
				else
					coachId = 1l;
			}
		}
		return getHibernateTemplate().find("from CourseInfo ci where ci.member.id = ?", coachId);
	}

	/**
	 * 根据用户邮箱地址查找其所有的信息
	 */
	@Override
	public Member findMemberByMail(final String email) {
		final List<?> list = getHibernateTemplate()
				.find("from Member m where m.email = ? or m.nick = ? or m.mobilephone = ? ", email, email, email);
		if (list.size() > 0)
			return (Member) list.get(0);
		return null;
	}

	/**
	 * 根据用户邮箱地址查找其所有的信息
	 */
	@Override
	public Member findMemberByMail(final String username, final String email) {
		final List<?> list = getHibernateTemplate().find("from Member m where m.email = ? and m.nick = ?",
				new Object[] { email, username });
		if (list.size() > 0)
			return (Member) list.get(0);
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Area1> findHotCity() {
		final List<?> list = getHibernateTemplate().find("from Area1 a where a.hotCity = ? and a.open = ?",
				new Object[] { '1', '1' });
		return (List<Area1>) list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Area1> findOpenCity() {
		final List<?> list = getHibernateTemplate()
				.find("from Area1 a where a.open = ? and a.type = ? order by a.letter", new Object[] { '1', '1' });
		return (List<Area1>) list;
	}

	@Override
	public Map<Character, List<Area1>> findOpenCityForPinYin() {
		final Map<Character, List<Area1>> maps = new LinkedHashMap<Character, List<Area1>>();
		final List<Area1> areas = findOpenCity();
		for (Area1 a : areas) {
			final Character letter = a.getLetter();
			if (maps.get(letter) == null)
				maps.put(letter, new ArrayList<Area1>());
			maps.get(letter).add(a);
		}
		return maps;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<?> findObjectByIds(Class<?> cls, String fieldName, Object[] ids) {
		if (ids != null) {
			StringBuffer hql = new StringBuffer(" from " + cls.getName() + " o ");
			for (int i = 0; i < ids.length; i++) {
				if (i == 0) {
					hql.append(" where o." + fieldName + " = ? ");
				} else {
					hql.append(" or o." + fieldName + " = ? ");
				}
			}
			return this.getHibernateTemplate().find(hql.toString(), ids);
		}
		return null;
	}

	@Override
	public String getCurrentCity(final String city) {
		List<?> list = getHibernateTemplate().find("from Area1 a where a.name like ? and a.open = '1'",
				"%" + city + "%");
		if (list.size() > 0) {
			return city;
		}
		return SYSTEM_DEFAULT_CITY;
	}

	@Override
	public void clean() {
		getSession().clear();
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public Member saveRelieve(Member member) {
		Member m = (Member) this.load(Member.class, member.getId());
		this.saveOrderStatus(member.getCoach(), m);
		m.setCoach(null);
		m.setCoachId(null);
		m.setCoachName(null);
		getHibernateTemplate().saveOrUpdate(m);
		return m;
	}

	// 修改履约中的课时费订单状态为暂停
	@SuppressWarnings("unchecked")
	protected void saveOrderStatus(Member memberProduct, Member memberOrder) {
		List<ProductOrder> orderList = (List<ProductOrder>) getHibernateTemplate().find(
				"from ProductOrder o where o.product.member.id = ? and o.member.id = ? and o.status = ? ",
				new Object[] { memberProduct.getId(), memberOrder.getId(), '1' });
		if (orderList.size() > 0) {
			for (ProductOrder order : orderList) {
				order.setStatus('3');
				getHibernateTemplate().merge(order);
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public List<SignIn> findSignListByDate(Member memberFrom, Member memberTo, Date startDate, Date endDate) {
		Date[] dateArr = DateUtils.getStartAndEndDate(startDate, endDate);
		return (List<SignIn>) getHibernateTemplate().find(
				" from SignIn si where si.memberSign.id = ? and si.memberAudit.id = ? and si.signDate between ? and ? order by si.signDate ",
				new Object[] { memberFrom.getId(), memberTo.getId(), dateArr[0], dateArr[1] });
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public List<SignIn> findSignListByOrderId(final Long orderId) {
		return (List<SignIn>) getHibernateTemplate().find(" from SignIn si where orderId = ? order by si.signDate ",
				orderId);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public Member findCardColManager() {
		return getHibernateTemplate().load(Member.class, 1l);
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public Member saveOrUpdateRecord(final List<TrainRecord> records, Member member, final String doneDate)
			throws Exception {
		final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		final List<?> list = getHibernateTemplate().find("from Course c where c.member.id = ? and c.planDate = ?",
				member.getId(), doneDate);
		for (final Iterator<?> it = list.iterator(); it.hasNext();) {
			final Course c = (Course) it.next();
			c.setDoneDate(doneDate);
			getHibernateTemplate().merge(c);
		}
		for (final TrainRecord tr : records) {
			if (tr.getActiveOrder() != null) {
				List<?> recordList = getHibernateTemplate().find(
						"from TrainRecord cr where cr.partake.id = ? and cr.doneDate = ? and cr.activeOrder.id = ?",
						new Object[] { member.getId(), sdf.parse(doneDate), tr.getActiveOrder().getId() });
				if (recordList.size() > 0) {
					TrainRecord recordNew = (TrainRecord) recordList.get(0);
					recordNew.setScore(tr.getScore());
					recordNew.setHeartRate(tr.getHeartRate());
					recordNew.setAction(tr.getAction());
					recordNew.setActionQuan(tr.getActionQuan());
					recordNew.setWeight(tr.getWeight());
					recordNew.setWaist(tr.getWaist());
					recordNew.setTimes(tr.getTimes());
					recordNew.setHip(tr.getHip());
					recordNew.setFat(tr.getFat());
					recordNew.setMemo(tr.getMemo());
					recordNew.setHeight(tr.getHeight());
					recordNew.setUnit(tr.getUnit());
					recordNew = getHibernateTemplate().merge(recordNew);
					if (tr.getWeight() != null) {
						final Setting set = loadSetting(member.getId());
						set.setWeight(tr.getWeight());
						getHibernateTemplate().merge(set);
					}
					tr.setId(recordNew.getId());
				} else {
					// 如果当天已经有签到则只计算签到次数，不计算保存次数。如果当天没有签到，则计算保存训练体会的次数
					final Date[] dateArr = DateUtils.getStartAndEndDate(new Date());
					final List<SignIn> signList = (List<SignIn>) getHibernateTemplate().find(
							" from SignIn si where si.memberSign.id = ? and si.signDate between ? and ? ",
							new Object[] { member.getId(), dateArr[0], dateArr[1] });
					if (signList != null && recordList.size() == 0) {
						member = getHibernateTemplate().load(Member.class, member.getId());
						member.setWorkoutTimes(member.getWorkoutTimes() == null ? 1 : member.getWorkoutTimes() + 1);
						getHibernateTemplate().saveOrUpdate(member);
					}
					getHibernateTemplate().saveOrUpdate(tr);
					if (tr.getWeight() != null) {
						final Setting set = loadSetting(member.getId());
						set.setWeight(tr.getWeight());
						getHibernateTemplate().merge(set);
					}
				}
			} else {
				List<?> recordList = getHibernateTemplate().find(
						"from TrainRecord cr where cr.partake.id = ? and cr.doneDate = ?",
						new Object[] { member.getId(), sdf.parse(doneDate) });
				if (recordList.size() > 0) {
					TrainRecord recordNew = (TrainRecord) recordList.get(0);
					recordNew.setScore(tr.getScore());
					recordNew.setHeartRate(tr.getHeartRate());
					recordNew.setAction(tr.getAction());
					recordNew.setActionQuan(tr.getActionQuan());
					recordNew.setWeight(tr.getWeight());
					recordNew.setWaist(tr.getWaist());
					recordNew.setTimes(tr.getTimes());
					recordNew.setHip(tr.getHip());
					recordNew.setFat(tr.getFat());
					recordNew.setMemo(tr.getMemo());
					recordNew.setHeight(tr.getHeight());
					recordNew.setUnit(tr.getUnit());
					recordNew = getHibernateTemplate().merge(recordNew);
					tr.setId(recordNew.getId());
					if (tr.getWeight() != null) {
						final Setting set = loadSetting(member.getId());
						set.setWeight(tr.getWeight());
						getHibernateTemplate().merge(set);
					}
				} else {
					// 如果当天已经有签到则只计算签到次数，不计算保存次数。如果当天没有签到，则计算保存训练体会的次数
					final Date[] dateArr = DateUtils.getStartAndEndDate(new Date());
					final List<SignIn> signList = (List<SignIn>) getHibernateTemplate().find(
							" from SignIn si where si.memberSign.id = ? and si.signDate between ? and ? ",
							new Object[] { member.getId(), dateArr[0], dateArr[1] });
					if (signList != null && recordList.size() == 0) {
						member = getHibernateTemplate().load(Member.class, member.getId());
						member.setWorkoutTimes(member.getWorkoutTimes() == null ? 1 : member.getWorkoutTimes() + 1);
						getHibernateTemplate().saveOrUpdate(member);
					}
					getHibernateTemplate().saveOrUpdate(tr);
					if (tr.getWeight() != null) {
						final Setting set = loadSetting(member.getId());
						set.setWeight(tr.getWeight());
						getHibernateTemplate().merge(set);
					}
				}
			}
		}
		return member;
	}

	// 训练周期提醒：用户如果一个月签到次数少于1次，系统发提醒邮件。
	@SuppressWarnings("rawtypes")
	@Override
	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public void sendMailByWorkoutTimes() {
		List<Member> memberList = getHibernateTemplate().loadAll(Member.class);
		Date nowDate = new Date();
		Calendar calDate = Calendar.getInstance();
		calDate.setTime(nowDate);
		calDate.add(Calendar.DAY_OF_YEAR, -30);
		Date[] dateArr = DateUtils.getStartAndEndDate(calDate.getTime(), nowDate);
		for (Member m : memberList) {
			if (m.getRole().equals("M")) {
				final List countList = getHibernateTemplate().find(
						" select count(si.id) from SignIn si where si.memberSign.id = ? and si.signDate between ? and ? ",
						new Object[] { m.getId(), dateArr[0], dateArr[1] });
				if (!(Long.parseLong(countList.get(0).toString()) > 0)) {
					Map<String, Object> root = new HashMap<String, Object>();
					Member cardColManager = findCardColManager();
					root.put("serviceTell", cardColManager.getTell());
					root.put("serviceQQ", cardColManager.getQq());
					root.put("serviceHandset", cardColManager.getMobilephone());
					root.put("serviceEmail", cardColManager.getEmail());
					root.put("memberName", m.getNick());
					String str = processTemplateIntoString("training-remind.ftl", root);
					sendMail(m.getEmail(), "卡库网“训练周期”提醒邮件", str);
				}
			}
		}
	}

	@Override
	public List<Active> findActiveByMember(Long id) {
		final Date d = new Date();
		return jdbc.query(
				"SELECT distinct a.id, b.name FROM TB_ACTIVE_ORDER A LEFT JOIN TB_ACTIVE B ON A.active = b.id WHERE a.orderEndTime >= ? AND a.member = ? AND (a.status <> '0' and a.status is not null) ORDER BY a.id",
				new Object[] { d, id }, new ResultSetExtractor<List<Active>>() {
					@Override
					public List<Active> extractData(ResultSet rs) throws SQLException, DataAccessException {
						final List<Active> actives = new ArrayList<Active>();
						while (rs.next()) {
							final Active a = new Active();
							a.setId(rs.getLong("id"));
							a.setName(rs.getString("name"));
							actives.add(a);
						}
						return actives;
					}
				});
	}

	@Override
	public List<Map<String, Object>> findActiving() {
		jdbc.setMaxRows(10);
		return jdbc.query(
				"SELECT a.id, b.nick, b.name memberName, b.id memberId, b.role memberRole, b.image, c.id activeId, c.name activeName,c.action, c.`target`, c.`category`, c.`days`, c.`value` FROM tb_active_order a LEFT JOIN tb_member b ON a.member = b.id LEFT JOIN tb_active c ON a.`active` = c.id order by orderDate desc",
				new ResultSetExtractor<List<Map<String, Object>>>() {
					@Override
					public List<Map<String, Object>> extractData(ResultSet rs)
							throws SQLException, DataAccessException {
						final List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
						final ResultSetMetaData md = rs.getMetaData();
						int colCnt = md.getColumnCount();
						while (rs.next()) {
							final Map<String, Object> map = new HashMap<String, Object>();
							for (int i = 1; i <= colCnt; i++) {
								map.put(md.getColumnLabel(i), rs.getObject(i));
							}
							list.add(map);
						}
						return list;
					}

				});
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public ActiveOrder saveActivePartake(ActiveOrder partake, final Long teamId, final Character createMode,
			final String name, final String members) {
		if (partake.getActive().getJudgeMode() == 'A') {
			final String judge = partake.getJudge();
			final List<?> list = getHibernateTemplate().find("from Member m where m.id=?", Long.parseLong(judge));
			if (list.size() <= 0)
				throw new LogicException("您输入的裁判不存在本系统中，请重新输入！");
		}
		partake.setNo(getKeyNo("HD", "TB_ACTIVE_ORDER", 14));
		String payNo = getKeyNo("", "CARDCOL_ORDER_NO", 13);
		payNo = payNo.substring(0, 2) + payNo.substring(4, 6)
				+ ChinaPayConfig.MerId.substring(ChinaPayConfig.MerId.length() - 5) + payNo.substring(6);
		payNo = payNo + payNo.substring(0, 12);
		partake.setPayNo(payNo);
		partake.setOrderMoney(partake.getActive().getAmerceMoney());
		partake.setContractMoney(partake.getOrderMoney());
		final Date nowDate = new Date();
		final Long memberId = partake.getMember().getId();
		if (partake.getActive().getMode() == 'A') {
			if (partake.getId() == null) {
				final List<?> list = getHibernateTemplate().find(
						"from ActiveOrder app where app.active.id = ? and app.member.id = ? and app.orderEndTime > ?",
						partake.getActive().getId(), memberId, partake.getOrderStartTime());
				if (list.size() > 0) {
					for (final Iterator<?> it = list.iterator(); it.hasNext();) {
						final ActiveOrder ato = (ActiveOrder) it.next();
						if (ato.getStatus() != '0') {
							throw new LogicException("该时间段的挑战您已经参与过，请到【已参加健身挑战】列表中查看！");
						} else {
							throw new LogicException("该时间段的挑战您已经参与过，请到【订单管理】列表中查看！");
						}

					}
				}

			}
			partake = getHibernateTemplate().merge(partake);
		} else {
			if (teamId != null) {
				final List<?> list = getHibernateTemplate().find(
						"from ActiveOrder ao where ao.team.id = ? and ao.active.id = ?", teamId,
						partake.getActive().getId());
				if (list.size() >= partake.getActive().getTeamNum())
					throw new LogicException("该团队已经针对此活动已经满员，不得加入到该团队！");
			}
			if (createMode == '0') { // 加入团队
				if (teamId == null)
					throw new LogicException("请选择需要加入的团队！");
				final List<?> aos = getHibernateTemplate().find(
						"from ActiveOrder ao where ao.active.id = ? and ao.team.id = ? order by ao.id",
						partake.getActive().getId(), teamId);
				// if (aos.size() <= 0) throw new
				// LogicException("该挑战已经不存在，请确认！");

				for (final Iterator<?> it = aos.iterator(); it.hasNext();) {
					final ActiveOrder ao = (ActiveOrder) it.next();
					if (ao.getMember().getId().equals(memberId))
						throw new LogicException("您已经参加过该挑战了，不得重复参加！");
				}
				if (aos.size() > 0) {
					final ActiveOrder ao = (ActiveOrder) aos.get(0);
					partake.setOrderStartTime(ao.getOrderStartTime());
					partake.setOrderEndTime(ao.getOrderEndTime());
					partake.setOrderBalanceTime(ao.getOrderBalanceTime());
					partake.setContractMoney(ao.getContractMoney());
					partake.setJudge(ao.getJudge());
					partake.setUnitPrice(ao.getUnitPrice());
					partake.setTeam(new Team(teamId));
				}
				partake = getHibernateTemplate().merge(partake);
				// 发送提醒
				final Message msg = new Message(partake.getMember(), partake.getActive().getCreator(), nowDate,
						"会员【" + partake.getMember().getNick() + "】参加了您发起的【" + partake.getActive().getName() + "】挑战活动！",
						"3", "0", "2");
				getHibernateTemplate().save(msg);
				// 当前用户加入到团队列表中
				final TeamMember tm = new TeamMember();
				tm.setMember(partake.getMember());
				tm.setTeam(new Team(teamId));
				getHibernateTemplate().merge(tm);
			} else { // 新建团队
				Team t = new Team();
				if (name != null && !"".equals(name)) {
					final List<?> teams = getHibernateTemplate().find("from Team t where t.name = ?", name);
					if (teams.size() > 0)
						throw new LogicException("此团队名称已经存在，请重新输入！");
					t.setName(name);
				}
				if (members != null && !"".equals(members)) {
					final String[] arrs = members.split(",");
					for (final String arr : arrs) {
						final String mName = arr.trim();
						final List<?> list1 = getHibernateTemplate().find(
								"from Member m where m.nick = ? or m.mobilephone = ? or m.email = ?", mName, mName,
								mName);
						if (list1.size() > 0) {
							final Member m1 = (Member) list1.get(0);
							t.addMember(new TeamMember(m1));
							final Message msg = new Message(partake.getMember(), m1, nowDate,
									"会员【" + partake.getMember().getNick()
											+ "】邀请您参与【<a href='activewindow.asp?type=2&memberId=" + memberId + "&id="
											+ partake.getActive().getId() + "'>" + partake.getActive().getName()
											+ "</a>】挑战活动！",
									"3", "0", "2");
							getHibernateTemplate().merge(msg);
						} else {
							throw new LogicException("会员" + arr + "不存在本系统中，请确认！");
						}
					}
				}
				t.addMember(new TeamMember(partake.getMember()));
				t = getHibernateTemplate().merge(t);
				partake.setTeam(t);
				partake = getHibernateTemplate().merge(partake);
			}
		}
		return partake;
	}

	@Override
	public ActiveOrder validateJoined(ActiveOrder partake, Long teamId, Character createMode, String name,
			String members) {
		final Long memberId = partake.getMember().getId();
		if (partake.getId() == null) {
			final List<?> list = getHibernateTemplate().find(
					"from ActiveOrder app where app.active.id = ? and app.member.id = ? and app.orderEndTime > ?",
					partake.getActive().getId(), memberId, partake.getOrderStartTime());
			if (list.size() > 0) {
				for (final Iterator<?> it = list.iterator(); it.hasNext();) {
					final ActiveOrder ato = (ActiveOrder) it.next();
					if (ato.getStatus() != '0') {
						throw new LogicException("该时间段的挑战您已经参与过，请到【商务中心】的【健身挑战】中查看！");
					} else {
						throw new LogicException("该时间段的挑战您已经参与过，请到【商务中心】的【订单管理】中查看！");
					}
				}
			}
		}
		if (partake.getActive().getJudgeMode() == 'A') {
			final String judge = partake.getJudge();
			final List<?> list = getHibernateTemplate().find(
					"from Member m where m.name = ? or m.nick = ? or m.email = ? or m.mobilephone = ?", judge, judge,
					judge, judge);
			if (list.size() <= 0)
				throw new LogicException("您输入的裁判不存在本系统中，请重新输入！");
		}
		if (partake.getActive().getMode() == 'B') {
			if (teamId != null) {
				final List<?> list = getHibernateTemplate().find(
						"from ActiveOrder ao where ao.team.id = ? and ao.active.id = ?", teamId,
						partake.getActive().getId());
				if (list.size() >= partake.getActive().getTeamNum())
					throw new LogicException("该团队已经针对此活动已经满员，不得加入到该团队！");
			}
			if (createMode == '0') { // 加入团队
				if (teamId == null)
					throw new LogicException("请选择需要加入的团队！");
				final List<?> aos = getHibernateTemplate().find(
						"from ActiveOrder ao where ao.active.id = ? and ao.team.id = ? order by ao.id",
						partake.getActive().getId(), teamId);
				// if (aos.size() <= 0) throw new
				// LogicException("该挑战已经不存在，请确认！");

				for (final Iterator<?> it = aos.iterator(); it.hasNext();) {
					final ActiveOrder ao = (ActiveOrder) it.next();
					if (ao.getMember().getId().equals(memberId))
						throw new LogicException("您已经参加过该挑战了，不得重复参加！");
				}
			} else { // 新建团队
				Team t = new Team();
				if (name != null && !"".equals(name)) {
					final List<?> teams = getHibernateTemplate().find("from Team t where t.name = ?", name);
					if (teams.size() > 0)
						throw new LogicException("此团队名称已经存在，请重新输入！");
					t.setName(name);
				}
			}
		}
		return partake;
	}

	@Override
	public Double getActualWeight(Long memberId, Date doneDate) {
		final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		final String sDate = sdf.format(doneDate);
		final String sql = "select sum(ifnull(a.actualWeight, 0)) from tb_workout_detail a left join tb_workout b on a.workout = b.id left join tb_course c on b.course = c.id left join tb_project_action d on b.action = d.id left join tb_project_part e on d.part = e.id left join tb_project f on e.project = f.id where f.mode = '1' and c.member = ? and c.planDate = ?";
		final Double weight = jdbc.queryForObject(sql, new Object[] { memberId, sDate }, Double.class);
		return weight == null ? 0d : weight;
	}

	@Override
	public Double getActualWeight(Long memberId, Date startDate, Date endDate) {
		final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		final String sDate = sdf.format(startDate);
		final String eDate = sdf.format(endDate);
		final String sql = "select sum(ifnull(a.actualWeight, 0)) from tb_workout_detail a left join tb_workout b on a.workout = b.id left join tb_course c on b.course = c.id left join tb_project_action d on b.action = d.id left join tb_project_part e on d.part = e.id left join tb_project f on e.project = f.id where f.mode = '1' and c.member = ? and c.planDate between ? and ?";
		final Double weight = jdbc.queryForObject(sql, new Object[] { memberId, sDate, eDate }, Double.class);
		return weight;
	}

	@SuppressWarnings("deprecation")
	@Override
	public Integer getActualTime(Long memberId, Long activeId, Date doneDate) {
		final int time = jdbc.queryForInt(
				"select count(*) from tb_plan_record a left join tb_active_order b on a.active_order = b.id where confrim = '1' and b.active = ? and partake = ? and done_date <= ?",
				activeId, memberId, doneDate);
		return time;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void updateRecordData(Long id, Character audit) {
		final TrainRecord tr = getHibernateTemplate().get(TrainRecord.class, id);
		tr.setConfrim(audit);
		getHibernateTemplate().merge(tr);
		final ActiveOrder ao = (ActiveOrder) load(ActiveOrder.class, tr.getActiveOrder().getId());
		if (ao.getValue() == null)
			ao.setValue(0d);
		if (tr.getActiveOrder().getActive().getTarget() == 'A') {
			List<?> list = getHibernateTemplate().find(
					"from TrainRecord tr where tr.partake.id = ? and tr.activeOrder.id=? and tr.confrim = ? order by tr.doneDate desc",
					tr.getPartake().getId(), tr.getActiveOrder().getId(), '1');
			if (list.size() > 0) {
				TrainRecord tr1 = (TrainRecord) list.get(0);
				Setting setting = loadSetting(tr.getPartake().getId());
				Double val = DoubleUtil.getDouble(setting.getWeight()) - DoubleUtil.getDouble(tr1.getWeight());
				ao.setValue(val);
			}
		} else if (tr.getActiveOrder().getActive().getTarget() == 'B') {
			if (tr.getActiveOrder().getActive().getCategory() == 'H') {
				ao.setValue(getActualWeight(tr.getPartake().getId(), ao.getOrderStartTime(), ao.getOrderEndTime()));
			} else {
				ao.setValue(ao.getValue() + (tr.getActionQuan() == null ? 0d : tr.getActionQuan()));
			}
		} else if (tr.getActiveOrder().getActive().getTarget() == 'C') {
			if (tr.getActiveOrder().getActive().getCategory() == 'E')
				ao.setValue(ao.getValue() + (tr.getTimes() == null ? 0d : tr.getTimes()));
			else
				ao.setValue(ao.getValue() + 1d);
		}
		getHibernateTemplate().merge(ao);
	}

	@Override
	public List<?> findActionByMode(long memberId, char mode) {
		final List<?> actions = getHibernateTemplate()
				.find("from Action a where a.part.project.mode = ? and a.part.project.member = ?", mode, memberId);
		return actions;
	}

	@Override
	public boolean hasRelation(Long fromId, Long toId) {
		final List<?> list = getHibernateTemplate().find(
				"from Friend f where ((f.member.id = ? and f.friend.id = ?) or (f.member.id = ? and f.friend.id = ?)) and type = ?",
				fromId, toId, toId, fromId, "1");
		return list.size() > 0;
	}

	@Override
	public boolean hasRelation(Long fromId, Long toId, String role) {
		if (role.equals("S")) {
			final Long queryForLong = queryForLong("select count(*) from tb_member where id = ? and coach = ?", fromId,
					toId);
			return queryForLong > 0;
		} else {
			final List<?> list = getHibernateTemplate().find(
					"from Friend f where ((f.member.id = ? and f.friend.id = ?) or (f.member.id = ? and f.friend.id = ?)) and type = ?",
					fromId, toId, toId, fromId, "1");
			return list.size() > 0;
		}
	}

	@Override
	public boolean registration(Long fromId, Long toId) {

		final List<?> list = getHibernateTemplate().find(
				"from ProductOrder f where f.member.id = ? and f.product.member.id = ? and f.status = ?", fromId, toId,
				'1');
		return list.size() > 0;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Message sendNotity(Object object, String content) {
		if (object != null) {
			if (object instanceof Collection) {
				Collection<?> list = (Collection<?>) object;
				for (final Iterator<?> it = list.iterator(); it.hasNext();) {
					Object obj = it.next();
					final Message msg = new Message();
					msg.setMemberFrom(new Member(1l));
					msg.setContent(content);
					msg.setSendTime(new Date());
					msg.setIsRead(STATUS_REQUEST_NOREAD);
					msg.setType("3");
					if (obj instanceof Member) {
						Member m = (Member) obj;
						msg.setMemberTo(m);
					} else if (obj instanceof TeamMember) {
						TeamMember tm = (TeamMember) obj;
						msg.setMemberTo(tm.getMember());
					}
					getHibernateTemplate().merge(msg);
				}
			}
		}
		return null;
	}

	@Override
	public String sendMessage(final String mobile, final String keyName, final Object... values) {
		final String content = messageResource.getMessage(keyName, values, Locale.CHINA);
		final String msg = SmsUtils.sendSms(mobile, content);
		return msg;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void saveFactory(List<Factory> factorys) {
		for (final Factory f : factorys) {
			getHibernateTemplate().merge(f);
			if (f.getId() != null && !f.getOldName().equals(f.getName())) {
				jdbc.update("update tb_course set place = ? where place = ? and member = ?", f.getName(),
						f.getOldName(), f.getClub());
			}
		}
	}

	@Override
	public String findMonthStatus(Long id, Date analyDate) {
		final int days = DateUtil.getMaxDayForMonth(analyDate);
		final Date[] dates = DateUtil.getMinMaxDateByMonth(analyDate);
		final List<?> list = getHibernateTemplate().find(
				"from Body b where b.member = ? and b.analyDate between ? and ? order by b.analyDate", id, dates[0],
				dates[1]);
		final JSONObject obj = new JSONObject();
		for (int i = 0; i < days; i++) {
			obj.put("day" + (i + 1), 0);
		}
		final Calendar cal = Calendar.getInstance();
		int day = 0;
		for (Iterator<?> it = list.iterator(); it.hasNext();) {
			final Body body = (Body) it.next();
			cal.setTime(body.getAnalyDate());
			day = cal.get(Calendar.DAY_OF_MONTH);
			obj.put("day" + day, 1);
		}
		return obj.toString();
	}

	@Override
	public Body loadBodyData(Body body, Long member) {
		if (body == null)
			body = new Body();
		if (body.getAnalyDate() == null)
			body.setAnalyDate(new Date());
		if (body.getMember() == null)
			body.setMember(member);
		final List<?> list = getHibernateTemplate().find(
				"from Body b where b.member = ? and b.analyDate = ? order by b.id desc", body.getMember(),
				body.getAnalyDate());
		if (list.size() > 0)
			return (Body) list.get(0);
		return body;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Member register(Member member) {
		member.setRate(0d);
		member.setCountSocure(0d);
		member.setIntegralCount(0);
		member.setAvgGrade(0);
		member.setCountEmp(0);
		member.setOrderCount(0);
		member.setRegDate(new Date());
		member.setGrade("1");
		member.setLoginTime(new Date());
		member.setToKen(MD5.MD5Encode(member.getMobilephone() + member.getPassword()
				+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(member.getLoginTime())));
		member = (Member) saveOrUpdate(member);
		if (!"E".equals(member.getRole())) {
			Setting set = new Setting();
			set.setMember(member.getId());
			set = (Setting) saveOrUpdate(set);
			member.setSetting(set);
		}
		return member;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Appraise> findAppraiseByProduct(Long id, Character type) {
		switch (type) {
		case '1':
			return (List<Appraise>) getHibernateTemplate().find(
					"from Appraise a where a.orderId in (select o.id from ProductOrder o where o.product.id = ?) and a.orderType=?",
					id, String.valueOf(type));
		}
		return null;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Map<String, List<Product>> loadProductsByMember(List memberList) {
		StringBuffer memberStr = new StringBuffer();
		for (final Iterator<?> it = memberList.iterator(); it.hasNext();) {
			Map map = (Map) it.next();
			memberStr.append(map.get("id")).append(",");
		}
		memberStr.deleteCharAt(memberStr.length() - 1);
		List productList = getHibernateTemplate().find(
				"from Product p where p.member.id in (" + memberStr.toString() + ") and audit='1' and isClose='2'");
		Map<String, List<Product>> map = new HashMap<>();
		for (final Iterator<?> it = productList.iterator(); it.hasNext();) {
			Product p = (Product) it.next();
			if (map.get(p.getMember().getId().toString()) == null)
				map.put(p.getMember().getId().toString(), new ArrayList<Product>());
			map.get(p.getMember().getId().toString()).add(p);
		}
		return map;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Map<String, Integer> loadAppraiseNum(String type, List list) {
		StringBuffer prodStr = new StringBuffer();
		for (final Iterator<?> it = list.iterator(); it.hasNext();) {
			Map map = (Map) it.next();
			prodStr.append(map.get("id")).append(",");
		}
		prodStr.deleteCharAt(prodStr.length() - 1);
		List appraiseNumList = jdbc.queryForList("select * from (" + BaseAppraise.getAppraiseInfo()
				+ ") t where t.ordertype = " + type + " and t.productId in (" + prodStr + ")");
		Map<String, Integer> map = new HashMap<>();
		for (final Iterator<?> it = appraiseNumList.iterator(); it.hasNext();) {
			Map p = (Map) it.next();
			if (map.get(p.get("productId").toString()) == null)
				map.put(p.get("productId").toString(), 0);
			map.put(p.get("productId").toString(), map.get(p.get("productId").toString()).intValue() + 1);
		}
		return map;
	}

	@Override
	public List<Map<String, Object>> findRecommendBySectorCode(String code, String currentCity) {
		StringBuffer sql = new StringBuffer(
				"SELECT sr.id, sr.color, sr.recomm_date, sr.recomm_id, sr.recomm_type, sr.sector, sr.stick_time, sr.summary, sr.title, case when sr.icon is null then p.image else sr.icon end icon, case when sr.link is null or sr.link = '' then p.href else sr.link end link, p.* FROM TB_SYSTEM_RECOMMEND sr LEFT JOIN ( ");
		sql.append(
				"SELECT '1' rType, a.id rId, a.name rName, a.image1 image,'' rLink, m.id memberId, m.name memberName,'' memberRole,'' rSummary,'' category,'' value,'' days,''target,'' award,'' amerce_Money,'' institutionName, concat('clublist!shoGo.asp?productId=', a.id) href FROM tb_product a LEFT JOIN tb_member m ON a.member = m.id where m.city = ?");
		sql.append(" UNION ALL ");
		sql.append(
				"SELECT '2' rType, b.id rId, b.name rName, b.active_image image,'' rLink,m.id memberId, m.name memberName,'' memberRole,'' rSummary,b.category category,b.value value,b.days days,b.target target,b.award award,b.amerce_Money amerce_Money,e.institutionName institutionName, concat('activewindow.asp?id=', b.id) href FROM tb_active b LEFT JOIN tb_member m ON b.creator = m.id LEFT JOIN (SELECT id institutionId,name institutionName FROM tb_member) e ON e.institutionId = b.institution");
		sql.append(" UNION ALL ");
		sql.append(
				"SELECT '3' rType, c.id rId, c.plan_name rName, c.image1 image,'' rLink,m.id memberId, m.name memberName,'' memberRole,'' rSummary,'' category,'' value,'' days,''target,'' award,'' amerce_Money,'' institutionName, concat('plan.asp?pid=', c.id) href FROM tb_plan_release c LEFT JOIN tb_member m ON c.member = m.id");
		sql.append(" UNION ALL ");
		sql.append(
				"SELECT '7' rType, d.id rId, d.name rName, d.image image,'' rLink,d.id memberId, d.name memberName,d.role memberRole,'' rSummary,'' category,'' value,'' days,''target,'' award,'' amerce_Money,'' institutionName, case when d.role = 'E' then concat('club.asp?member.id=', id) else concat('coach.asp?member.id=', id) end href FROM tb_member d where d.city = ?");
		sql.append(" UNION ALL ");
		sql.append(
				"SELECT '8' rType, e.id rId, e.title rName, e.icon image,e.link rLink,'' memberId, '' memberName,'' memberRole,e.summary rSummary,'' category,'' value,'' days,''target,'' award,'' amerce_Money,'' institutionName, concat('detail.asp?channel=', sector, '&id=', id) href FROM tb_article e");
		sql.append(" UNION ALL ");
		sql.append(
				"SELECT '9' rType, f.id rId, f.name rName, f.icon image,f.link rLink,'' memberId, '' memberName,'' memberRole,'' rSummary,'' category,'' value,'' days,''target,'' award,'' amerce_Money,'' institutionName, link href FROM tb_banner f ");
		sql.append(") p ON sr.recomm_id = rId AND sr.recomm_type = rType ");
		sql.append("left join tb_sector b on sr.sector = b.id ");
		sql.append("left join tb_parameter c on p.rType = c.code and c.parent = 21 ");
		sql.append("where b.code = ? AND p.rId IS NOT NULL order by sr.sort");
		return jdbc.queryForList(sql.toString(), new Object[] { currentCity, currentCity, code });
	}

	@Override
	public List<Map<String, Object>> findRecommendBySectorCode(String code, String currentCity, String speciality) {
		StringBuffer sql = new StringBuffer(
				"SELECT sr.star_coach as speciality,sr.id, sr.color, sr.recomm_date, sr.recomm_id, sr.recomm_type, sr.sector, sr.stick_time, sr.summary, sr.title, case when sr.icon is null then p.image else sr.icon end icon, case when sr.link is null or sr.link = '' then p.href else sr.link end link, p.* FROM TB_SYSTEM_RECOMMEND sr LEFT JOIN ( ");
		sql.append(
				"SELECT '1' rType, a.id rId, a.name rName, a.image1 image,'' rLink, m.id memberId, m.name memberName,'' memberRole,'' rSummary,'' category,'' value,'' days,'' award,'' amerce_Money,'' institutionName, concat('clublist!shoGo.asp?productId=', a.id) href FROM tb_product a LEFT JOIN tb_member m ON a.member = m.id where m.city = ?");
		sql.append(" UNION ALL ");
		sql.append(
				"SELECT '2' rType, b.id rId, b.name rName, b.active_image image,'' rLink,m.id memberId, m.name memberName,'' memberRole,'' rSummary,b.category category,b.value value,b.days days,b.award award,b.amerce_Money amerce_Money,e.institutionName institutionName, concat('activewindow.asp?id=', b.id) href FROM tb_active b LEFT JOIN tb_member m ON b.creator = m.id LEFT JOIN (SELECT id institutionId,name institutionName FROM tb_member) e ON e.institutionId = b.institution");
		sql.append(" UNION ALL ");
		sql.append(
				"SELECT '3' rType, c.id rId, c.plan_name rName, c.image1 image,'' rLink,m.id memberId, m.name memberName,'' memberRole,'' rSummary,'' category,'' value,'' days,'' award,'' amerce_Money,'' institutionName, concat('plan.asp?pid=', c.id) href FROM tb_plan_release c LEFT JOIN tb_member m ON c.member = m.id");
		sql.append(" UNION ALL ");
		sql.append(
				"SELECT '7' rType, d.id rId, d.name rName, d.image image,'' rLink,d.id memberId, d.name memberName,d.role memberRole,'' rSummary,'' category,'' value,'' days,'' award,'' amerce_Money,'' institutionName, case when d.role = 'E' then concat('club.asp?member.id=', id) else concat('coach.asp?member.id=', id) end href FROM tb_member d where d.city = ?");
		sql.append(" UNION ALL ");
		sql.append(
				"SELECT '8' rType, e.id rId, e.title rName, e.icon image,e.link rLink,'' memberId, '' memberName,'' memberRole,e.summary rSummary,'' category,'' value,'' days,'' award,'' amerce_Money,'' institutionName, concat('detail.asp?channel=', sector, '&id=', id) href FROM tb_article e");
		sql.append(" UNION ALL ");
		sql.append(
				"SELECT '9' rType, f.id rId, f.name rName, f.icon image,f.link rLink,'' memberId, '' memberName,'' memberRole,'' rSummary,'' category,'' value,'' days,'' award,'' amerce_Money,'' institutionName, link href FROM tb_banner f ");
		sql.append(") p ON sr.recomm_id = rId AND sr.recomm_type = rType ");
		sql.append("left join tb_sector b on sr.sector = b.id ");
		sql.append("left join tb_parameter c on p.rType = c.code and c.parent = 21 ");
		sql.append("where b.code = ? AND p.rId IS NOT NULL ");
		List<String> spe = new ArrayList<String>();
		spe.add(currentCity);
		spe.add(currentCity);
		spe.add(code);
		if (speciality != null && !"".equals(speciality)) {
			String[] speArr = speciality.split(",");
			for (int i = 0; i < speArr.length; i++) {
				if (i == 0) {
					sql.append(" and ( sr.speciality like ? ");
				} else {
					sql.append(" or sr.speciality like ? ");
				}
				spe.add("%" + speArr[i] + "%");
			}
			sql.append(" ) ");
		}
		sql.append(" order by sr.sort");
		return jdbc.queryForList(sql.toString(), spe.toArray());
	}

	@Override
	public List<Map<String, Object>> findRecommendMember(String code, String role) {
		StringBuffer sql = new StringBuffer(
				"SELECT sr.id, sr.color, sr.recomm_date, sr.recomm_id, sr.recomm_type, sr.sector, sr.stick_time, sr.summary, sr.title, case when sr.icon is null then p.image else sr.icon end icon, case when sr.link is null or sr.link = '' then p.href else sr.link end link, p.* FROM TB_SYSTEM_RECOMMEND sr LEFT JOIN ( ");
		sql.append(
				"SELECT '7' rType, d.id rId, d.name rName, d.role, d.image image,'' rLink,d.id memberId, d.name memberName,d.role memberRole,'' rSummary,'' category,'' value,'' days,'' award,'' amerce_Money,'' institutionName, case when d.role = 'E' then concat('club.asp?member.id=', id) else concat('coach.asp?member.id=', id) end href FROM tb_member d");
		sql.append(") p ON sr.recomm_id = p.rId AND sr.recomm_type = p.rType ");
		sql.append("left join tb_sector b on sr.sector = b.id ");
		sql.append("where b.code = ? AND p.rId IS NOT NULL and p.role = ? order by sr.sort");
		return jdbc.queryForList(sql.toString(), new Object[] { code, role });
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CourseInfo> findSystemCourse() {
		return (List<CourseInfo>) getHibernateTemplate().find("from CourseInfo ci where ci.member.id = ?",
				SYSTEM_PROJECT_ID);
	}

	@Override
	public Object findRecommendClubsByProject(String recommSectorSlidesG, String city, Long id) {
		final String sql = "SELECT a.id, a.color, a.recomm_date, a.recomm_id, a.recomm_type, a.sector, a.stick_time, a.summary, a.title, case when a.icon is null then b.image else a.icon end icon, case when a.link is null or a.link = '' then case when b.role = 'E' then concat('club.asp?member.id=', b.id) else concat('coach.asp?member.id=', b.id) end else a.link end link from tb_system_recommend a left join tb_member b on a.recomm_id = b.id and a.recomm_type = ? where b.city = ? and a.sector in (select id from tb_sector where code = ?) and (b.id in (select club from tb_member_factory where project =? and applied = ?) or b.id in (select member from tb_course_info where type = ?))";
		return queryForList(sql, 7, city, recommSectorSlidesG, id, 1, id);
	}

	@Override
	public Long findWorkoutTimeByMember(Member member) {
		final String sql = "SELECT COUNT(*) FROM tb_plan_record where partake = ?";
		return queryForLong(sql, member.getId());
	}

	@Override
	public Long findTrainTimesByMember(Member member) {
		final String sql = "SELECT SUM(times) FROM tb_plan_record where partake = ?";
		return queryForLong(sql, member.getId());
	}

	@Override
	public Long findTrainRateByMember(Member member) {
		final String sql = "SELECT COUNT(*) FROM tb_plan_record where partake = ? and confrim=1";
		return queryForLong(sql, member.getId());
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Map<String, List<CourseInfo>> loadCourseInfosByMember(List items) {
		StringBuffer memberStr = new StringBuffer();
		for (final Iterator<?> it = items.iterator(); it.hasNext();) {
			Map map = (Map) it.next();
			memberStr.append(map.get("id")).append(",");
		}
		memberStr.deleteCharAt(memberStr.length() - 1);
		List courseList = getHibernateTemplate()
				.find("from CourseInfo p where p.member.id in (" + memberStr.toString() + ")");
		Map<String, List<CourseInfo>> map = new HashMap<String, List<CourseInfo>>();
		for (final Iterator<?> it = courseList.iterator(); it.hasNext();) {
			CourseInfo c = (CourseInfo) it.next();
			if (map.get(c.getMember().getId().toString()) == null)
				map.put(c.getMember().getId().toString(), new ArrayList<CourseInfo>());
			map.get(c.getMember().getId().toString()).add(c);
		}
		return map;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CourseInfo> findCourseInfoByIds(String freeProjects) {
		return (List<CourseInfo>) getHibernateTemplate().find("from CourseInfo c where c.id in (" + freeProjects + ")");
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Member> findMemberByIds(String ids) {
		return (List<Member>) getHibernateTemplate().find("from Member m where m.id in (" + ids + ")");
	}

	@Override
	public boolean checkUserExist(String userPhone) {

		List<?> list = getHibernateTemplate().find("from Member m where m.mobilephone = ?", userPhone);

		return list.size() > 0 ? true : false;
	}

	@Override
	public void updateUserPwd(Member member) {
		getHibernateTemplate().update(member);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public Member thirdLoginCheck(String thirdType, String thirdId) {
		String hql = "from Member m where ";
		if ("Q".equals(thirdType)) {
			hql += "m.qqId = ?";
		} else if ("S".equals(thirdType)) {
			hql += "m.sinaId = ?";
		} else if ("W".equals(thirdType)) {
			hql += "m.wechatID = ?";
		}
		List<?> list = getHibernateTemplate().find(hql, thirdId);
		if (list.size() > 0)
			return (Member) list.get(0);
		return null;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Member thirdRegister(Member member) {
		final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		member.setRate(0d);
		member.setCountSocure(0d);
		member.setIntegralCount(0);
		member.setAvgGrade(0);
		member.setCountEmp(0);
		member.setOrderCount(0);
		member.setRegDate(new Date());
		member.setGrade("1");
		member.setProvince("北京市");
		member.setCity("北京市");
		member.setCounty("东城区");
		member.setRegisterType(member.getRegisterType());
		member.setThirdType(member.getThirdType());
		member.setRole("M");
		member.setLoginTime(new Date());
		member.setToKen(MD5.MD5Encode(member.getNick() + sdf.format(member.getLoginTime())));

		member = (Member) saveOrUpdate(member);
		if (!"E".equals(member.getRole())) {
			Setting set = new Setting();
			set.setMember(member.getId());
			set = (Setting) saveOrUpdate(set);
			member.setSetting(set);
		}
		return member;
	}
	
	
	/**
	 * 微信登录
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Member thirdRegister(Member member,String wx) {
		final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			try {
				member.setRate(0d);
				member.setCountSocure(0d);
				member.setIntegralCount(0);
				member.setAvgGrade(0);
				member.setCountEmp(0);
				member.setOrderCount(0);
				member.setRegDate(new Date());
				member.setGrade("1");
				String pr = "北京市";
				String ci = "北京市";
				String co = "东城区";
				//String encode =  getEncode.getEncoding(pr);
				pr = new String(pr.getBytes("GB2312"), "utf-8");
				ci = new String(ci.getBytes("GB2312"), "utf-8");
				co = new String(co.getBytes("GB2312"), "utf-8");
				member.setProvince(pr);
				member.setCity(ci);
				member.setCounty(co);
				member.setRegisterType(member.getRegisterType());
				member.setThirdType(member.getThirdType());
				member.setRole("M");
				member.setLoginTime(new Date());
				member.setToKen(MD5.MD5Encode(member.getNick() + sdf.format(member.getLoginTime())));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}

		String sqlx = " insert into tb_member(name,nick,thirdType,wechatID,longitude,latitude,reg_date,grade, "
				                          + " province,city,county,registerType,role,login_time,login_token,"
				                          + " rate,count_socure,integralCount,count_emp,orderCount) "
				                          + " values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ";
		Object[] objx = {member.getName(),member.getNick(),member.getThirdType(),member.getWechatID(),member.getLongitude(),member.getLatitude(),member.getRegDate(),member.getGrade(),
				         member.getProvince(),member.getCity(),member.getCounty(),member.getRegisterType(),member.getRole(),member.getLoginTime(),member.getToKen(),
				         member.getRate(),member.getCountSocure(),member.getIntegralCount(),member.getCountEmp(),member.getOrderCount()
		                };
		int i = DataBaseConnection.updateData(sqlx, objx);
		if(i > 0){//添加数据成功
			String sqld = "select * from tb_member where wechatID=?";
			Object[] objd = {member.getWechatID()};
			List<Map<String, Object>> list =  DataBaseConnection.getList(sqld, objd);
			if(list.size() > 0 ){
				Map<String, Object> map = list.get(0);
				member.setId(Long.parseLong(map.get("id").toString()));
			}
		}
		//member = (Member) saveOrUpdate(member);
		if (!"E".equals(member.getRole())) {
			Setting set = new Setting();
			set.setMember(member.getId());
			//set = (Setting) saveOrUpdate(set);
			String sqls = "insert into tb_member_setting (member) values (?)";
			Object[] objs = {member.getId()};
			int s = DataBaseConnection.updateData(sqls, objs);
			if(s > 0){//添加数据成功
				String sqlss = "select * from tb_member_setting where member=?";
				Object[] objss = {member.getId()};
				List<Map<String, Object>> listss= DataBaseConnection.getList(sqlss, objss);
				if(listss.size() > 0){
					Map<String, Object> mapss = listss.get(0);
					set.setId(Long.parseLong(mapss.get("id").toString()));
					set.setMember(member.getId());
					member.setSetting(set);
					
 				}
			}
			member.setSetting(set);
		}
		return member;
	}
	
	
	

	/**
	 * 根据日期查询某一用户打卡记录
	 */
	@Override
	public PresentHeartRate loadPresentHeartRate(long member, Date train_date) {
		// TODO Auto-generated method stub
		final Date[] dates = DateUtil.getDateTimes(train_date);
		final List<?> list = getHibernateTemplate().find(
				"from PresentHeartRate cr where cr.member.id = ? and cr.train_date between ? and ? order by cr.train_date desc",
				member, dates[0], dates[1]);
		if (list.size() > 0) {
			final PresentHeartRate phr = (PresentHeartRate) list.get(0);
			return phr;
		}
		return new PresentHeartRate();
	}

	/**
	 * 查询某一用户的所有打卡记录（分页查询）
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public Map<String, Object> loadPresentHeartRate(long member, int currentPage, int pageSize) {
		Map<String, Object> map1 = new HashMap<String, Object>();
		// 查询出总数据条数
		String countSql = "select count(*) count from TB_PRESENT_HEARTRATE  where member = ?";
		Object[] obj1 = { member };
		Map map = (Map) DataBaseConnection.getList(countSql, obj1).get(0);

		int count = Integer.valueOf(String.valueOf(map.get("count")));

		// 计算pageCount
		pageInfo.setTotalCount(count);
		int totalPage = count % pageInfo.getPageSize() == 0 ? count / pageInfo.getPageSize() : (count / (pageInfo.getPageSize())) + 1;
		pageInfo.setTotalPage(totalPage);

		// 设置分页参数
		pageInfo.setCurrentPage(currentPage);
		pageInfo.setPageSize(pageSize);

		// 查询数据（分页）

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		int off = (pageInfo.getCurrentPage() - 1) * pageInfo.getPageSize();

		
		String sqlx = "select member,train_date,heartRates,"
				+ "(select count(id) count from tb_present_heartrate where member = ? and    "
				+ "train_date <= (select train_date from tb_present_heartrate x where x.member = ? ORDER BY train_date desc limit ?,1 ) "
				+ " ) count from tb_present_heartrate cr where cr.member = ? and    "
				+ "train_Date = (select train_date from tb_present_heartrate x where x.member = ? ORDER BY train_date desc limit ?,1 )   "
				+ " ORDER BY train_date desc limit 1";
		if (pageInfo.getCurrentPage() <= pageInfo.getTotalPage()) {
			if (count > pageInfo.getPageSize()) {
				if (pageInfo.getCurrentPage() == pageInfo.getTotalPage()) {
					for (int i = off; i < pageInfo.getTotalCount(); i++) {
						Object[] objx = { member, member, i, member, member, i};
						Map<String, Object> mapx = DataBaseConnection.getOne(sqlx, objx);
						list.add(mapx);
					}
				} else {
					for (int i = off; i < (pageInfo.getCurrentPage()*pageInfo.getPageSize()); i++) {
						Object[] objx = { member, member, i, member, member, i };
						Map<String, Object> mapx = DataBaseConnection.getOne(sqlx, objx);
						list.add(mapx);
					}
				}
			} else {
				for (int i = off; i < count; i++) {
					Object[] objx = { member, member, i, member, member, i };
					Map<String, Object> mapx = DataBaseConnection.getOne(sqlx, objx);
					list.add(mapx);
				}
			}
		}

		map1.put("phrList", list);
		map1.put("pageInfo", pageInfo);
		return map1;

	}
	
	

	
	/**
	 * 查询某一用户的所有打卡记录（分页查询）
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public Map<String, Object> loadPresentHeartRate(long member, int currentPage, int pageSize,String sport) {
		Map<String, Object> map1 = new HashMap<String, Object>();
		// 查询出总数据条数
		String countSql = "select count(*) count from TB_PRESENT_HEARTRATE  where member = ?";
		Object[] obj1 = { member };
		Map map = (Map) DataBaseConnection.getList(countSql, obj1).get(0);

		int count = Integer.valueOf(String.valueOf(map.get("count")));

		// 计算pageCount
		pageInfo.setTotalCount(count);
		int totalPage = count % pageInfo.getPageSize() == 0 ? count / pageInfo.getPageSize()
				: (count / (pageInfo.getPageSize())) + 1;
		pageInfo.setTotalPage(totalPage);

		// 设置分页参数
		pageInfo.setCurrentPage(currentPage);
		pageInfo.setPageSize(pageSize);

		// 查询数据（分页）

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		int off = (pageInfo.getCurrentPage() - 1) * pageInfo.getPageSize();
		
		String sqlx = "select cr.member,train_date,heartRates,  "
				+ "(220-DATE_FORMAT(FROM_DAYS(TO_DAYS(NOW())-TO_DAYS(m.birthday)), '%Y')+0-s.heart)*s.bmiHigh/100+s.heart as high ,  "
				+ "(220-DATE_FORMAT(FROM_DAYS(TO_DAYS(NOW())-TO_DAYS(m.birthday)), '%Y')+0-s.heart)*s.bmiLow/100+s.heart as low,  "
				+ "(select count(id) count from tb_present_heartrate where member = ? and     "
				+ "train_date<= (select train_date from tb_present_heartrate x where x.member = ? ORDER BY train_date desc limit ?,1 ) )   "
				+ "count   from tb_present_heartrate cr,tb_member m,tb_member_setting s  where  cr.member = m.id  and m.id = s.member and m.id = ? and    "
				+ "train_Date = (select train_date from tb_present_heartrate x where x.member = ? ORDER BY train_date desc limit ?,1 )    "
				+ "ORDER BY train_date desc limit 1";
		if (pageInfo.getCurrentPage() <= pageInfo.getTotalPage()) {
			if (count > pageInfo.getPageSize()) {
				if (pageInfo.getCurrentPage() == pageInfo.getTotalPage()) {
					for (int i = off; i < pageInfo.getTotalCount(); i++) {
						Object[] objx = { member, member, i, member, member, i};
						Map<String, Object> mapx = DataBaseConnection.getOne(sqlx, objx);
						list.add(mapx);
					}
				} else {
					for (int i = off; i < (pageInfo.getCurrentPage()*pageInfo.getPageSize()); i++) {
						Object[] objx = { member, member, i, member, member, i };
						Map<String, Object> mapx = DataBaseConnection.getOne(sqlx, objx);
						list.add(mapx);
					}
				}
			} else {
				for (int i = off; i < count; i++) {
					Object[] objx = { member, member, i, member, member, i };
					Map<String, Object> mapx = DataBaseConnection.getOne(sqlx, objx);
					list.add(mapx);
				}
			}
		}

		map1.put("phrList", list);
		map1.put("pageInfo", pageInfo);
		return map1;

	}
	
	
	
	
	@Override
	public Member findMemberByName(String name) {
		final List<?> list = getHibernateTemplate().find(
				"from Member m where m.name = ? or m.nick = ? or m.email = ? or m.mobilephone = ?", name, name, name, name);
		return (Member) (list.size() > 0 ? list.get(0) : null);
	}

	@Override
	public Object findRecommendPlanByType(String[] planType) {
		final StringBuffer sb = new StringBuffer();
		final Object[] args = new Object[planType.length];
		for (int i = 0; i < planType.length; i++) {
			args[i] = planType[i];
			sb.append(sb.length() <= 0 ? "" : ",").append("?");
		}
		String sql = "select ts.*,tp.unit_price from TB_SYSTEM_RECOMMEND ts join TB_PLAN_RELEASE tp on ts.recomm_id=tp.id where ts.recomm_type=3 and tp.plan_type in ("
				+ sb.toString() + ")";
		return jdbc.queryForList(sql.toString(), args);
	}

	@Override
	public Object findRecommendBySectorCode(String[] code) {
		return null;
	}

	public PageInfo queryClubById(PageInfo pageInfo, String id) {
		return null;
	}

}
