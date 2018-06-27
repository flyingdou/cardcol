package com.freegym.web.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.hibernate.SQLQuery;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.aliyuncs.exceptions.ClientException;
import com.cardcol.web.utils.MovementUtil;
import com.freegym.web.basic.Friend;
import com.freegym.web.basic.Member;
import com.freegym.web.config.FactoryCosts;
import com.freegym.web.course.Apply;
import com.freegym.web.course.Appraise;
import com.freegym.web.course.Message;
import com.freegym.web.factoryorder.FactoryApply;
import com.freegym.web.plan.Course;
import com.freegym.web.service.ICourseService;
import com.freegym.web.task.MessageThread;
import com.sanmen.web.core.common.LogicException;

@Service("courseService")
public class CourseServiceImpl extends ContentServiceImpl implements ICourseService {
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public Message saveOrUpdateMessage(Message msg) {
		final Date sendTime = new Date();
		msg.setSendTime(sendTime);
		msg.setIsRead("0");
		msg.setStatus("1");
		final Member mf = (Member) load(Member.class, msg.getMemberFrom().getId());
		final Member mt = (Member) load(Member.class, msg.getMemberTo().getId());
		final List<?> list = getHibernateTemplate()
				.find("from Message m where ((m.memberFrom.id = ? and m.memberTo.id = ?) or (m.memberTo.id = ? and m.memberFrom.id = ?)) and m.type = ? order by m.sendTime",
						mf.getId(), mt.getId(), mf.getId(), mt.getId(), msg.getType());
		Message m = null;
		for (final Iterator<?> it = list.iterator(); it.hasNext();) {
			m = (Message) it.next();
			break;
		}
		if (m != null) msg.setParent(m.getId());
		if (msg.getType().equals("1")) {
			msg.setContent(mf.getName() + msg.getContent() + "，请审批");
			getHibernateTemplate().merge(new Message(null, mf, sendTime, "您发送了加入" + mt.getName() + "的申请", "3", "0", "1"));
			// getHibernateTemplate().merge(new Message(null, mt, sendTime,
			// msg.getContent(), "3", "0", "1"));
		}
		msg = getHibernateTemplate().merge(msg);
		return msg;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void saveChangeIsCore(Friend friend) {
		Friend oldFriend = friend;
		friend = (Friend) getHibernateTemplate().load(Friend.class, friend.getId());
		if (oldFriend.getIsCore().equals("1")) {
			final List<?> friendList = getHibernateTemplate().find("from Friend f where f.member = ? and f.isCore = ? ",
					new Object[] { friend.getMember(), "1" });
			if (friendList != null && friendList.size() > 0) {
				Friend newFriend = (Friend) friendList.get(0);
				newFriend.setIsCore("0");
				getHibernateTemplate().saveOrUpdate(newFriend);
			}
		}
		friend.setIsCore(oldFriend.getIsCore());
		getHibernateTemplate().saveOrUpdate(friend);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void saveChangeMessageStatus(Message msg, Long[] ids) {
		String status = msg.getStatus();
		String msgContent = "";
		List<Message> msgList = new ArrayList<Message>();
		Date sendTime = new Date();
		if (msg != null && msg.getId() != null) {
			if (status != null && !"".equals(status)) {
				Message mesg = (Message) this.load(Message.class, msg.getId());
				// 增加私教审批
				Member memto = (Member) this.load(Member.class, mesg.getMemberTo().getId());
				if (memto.getRole().equals("S")) {
					if (status.equals("2")) {
						Member memfrom = (Member) this.load(Member.class, mesg.getMemberFrom().getId());
						memfrom.setCoach(memto);
						this.saveOrUpdate(memfrom);
						msgContent = "同意";
					} else if (status.equals("3")) {
						msgContent = "拒绝";
					}

				} else {
					if (status.equals("2")) {
						List<Friend> fList = (List<Friend>) this.findObjectBySql("from Friend f where f.member.id = ? and f.friend.id = ? and type = ?",
								new Object[] { mesg.getMemberFrom().getId(), mesg.getMemberTo().getId(), "1" });
						if (fList.size() == 0) this.saveOrUpdate(new Friend(mesg.getMemberFrom(), mesg.getMemberTo(), sendTime, "1", "0", sendTime));
						msgContent = "同意";
					} else if (status.equals("3")) {
						msgContent = "拒绝";
					}
					String body = mesg.getMemberTo().getName()+ msgContent +"您加入俱乐部!";
					try {
						MovementUtil.movement(mesg.getMemberFrom().getId().toString(),body);
					} catch (ClientException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				msgList.add(new Message(mesg.getMemberTo(), mesg.getMemberFrom(), sendTime, mesg.getMemberTo().getName() + msgContent + "了您的加入申请", "3", "0",
						"1"));
				this.saveOrUpdate(msgList);
				delete(Message.class, msg.getId());
			}
		} else {
			if (ids != null && ids.length > 0) {
				if (status != null && !"".equals(status)) {
					List<Message> messageList = (List<Message>) this.load(Message.class, ids);
					// 增加私教审批
					for (Message m : messageList) {
						Member memto = (Member) this.load(Member.class, m.getMemberTo().getId());
						if (memto.getRole().equals("S")) {
							if (status.equals("2")) {
								Member memfrom = (Member) this.load(Member.class, m.getMemberFrom().getId());
								memfrom.setCoach(memto);
								this.saveOrUpdate(memfrom);
								msgContent = "同意";
							} else if (status.equals("3")) {
								msgContent = "拒绝";
							}
						} else {
							if (status.equals("2")) {
								List<Friend> friendList = new ArrayList();
								Date nowTime = new Date();
								List<Friend> fList = (List<Friend>) this.findObjectBySql(
										"from Friend f where f.member.id = ? and f.friend.id = ? and type = ?", new Object[] { m.getMemberFrom().getId(),
												m.getMemberTo().getId(), "1" });
								if (fList.size() == 0) friendList.add(new Friend(m.getMemberFrom(), m.getMemberTo(), nowTime, "1", "0", nowTime));
								this.saveOrUpdate(friendList);
								msgContent = "同意";
							} else if (status.equals("3")) {
								msgContent = "拒绝";
							}
							String body = memto.getName() + msgContent +"您加入俱乐部！";
							try {
								MovementUtil.movement(m.getMemberFrom().getId().toString(),body);
							} catch (ClientException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}
						}

					for (Message m : messageList) {
						msgList.add(new Message(m.getMemberTo(), m.getMemberFrom(), sendTime, m.getMemberTo().getName() + msgContent + "了您的加入申请", "3", "0", "1"));
					}
					this.saveOrUpdate(msgList);
					delete(Message.class, ids);
				}
			}
		}
	}

	@SuppressWarnings("unused")
	private Member load(Class<Member> class1, Member memberTo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void saveRelieve(String type, Member member, Friend friend) {
		List<Message> msgList = new ArrayList<Message>();
		Date sendTime = new Date();
		if (type != null && !"".equals(type)) {
			if (type.equals("1")) {
				// 往被解除教练手机推送消息
				String uid = member.getCoach().getUserId();
				Long cid = member.getCoach().getChannelId();
				Integer termType = member.getCoach().getTermType();
				new MessageThread(uid, cid, termType, "会员“" + member.getName() + "”解除了您的私教关系。");
				// 修改履约中的课时费订单状态为暂停
				saveOrderStatus(member.getCoach(), member);
				member = (Member) this.load(Member.class, member.getId());
				msgList.add(new Message(null, member, sendTime, "您解除了您的私教【" + member.getCoach().getName() + "】", "3", "0", "1"));
				msgList.add(new Message(null, member.getCoach(), sendTime, member.getName() + "解除了与您的私教关系", "3", "0", "1"));
				member.setCoach(null);
				member.setCoachId(null);
				member.setCoachName(null);
				this.saveOrUpdate(msgList);
				this.saveOrUpdate(member);
			} else {
				if (friend != null && friend.getId() != null) {
					friend = (Friend) this.load(Friend.class, friend.getId());
					String msg = "";
					if (type.equals("2")) {
						msg = "俱乐部";
						saveOrderStatus(friend.getFriend(), member);
					} else if (type.equals("3")) {
						msg = "教练";
					} else if (type.equals("4")) {
						msg = "会员";
					}
					if (friend.getMember().getId().equals(member.getId())) {
						msgList.add(new Message(null, member, sendTime, "您解除了您的" + msg + "【" + friend.getFriend().getName() + "】", "3", "0", "1"));
						msgList.add(new Message(null, friend.getFriend(), sendTime, member.getNick() + "解除了与您的" + msg + "关系", "3", "0", "1"));
					} else {
						msgList.add(new Message(null, member, sendTime, "您解除了您的" + msg + "【" + friend.getMember().getName() + "】", "3", "0", "1"));
						msgList.add(new Message(null, friend.getMember(), sendTime, member.getNick() + "解除了与您的" + msg + "关系", "3", "0", "1"));
						String body = member.getNick() + "解除了与您的" + msg + "关系";
						try {
							MovementUtil.movement(friend.getMember().getId().toString(),body);
						} catch (ClientException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					this.saveOrUpdate(msgList);
					this.delete(friend);
				}
			}
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Course updateCourse(final Course cs, final Integer saveType) {
		if (saveType != null && saveType == 1) {
			final List<?> ccss = getHibernateTemplate().find("from Course cs where cs.groupBy = ?", cs.getGroupBy());
			final List<Course> courses = new ArrayList<Course>();
			courses.add(cs);
			for (Iterator<?> it = ccss.iterator(); it.hasNext();) {
				final Course cs1 = (Course) it.next();
				cs1.setCoach(cs.getCoach());
				cs1.setCourseInfo(cs.getCourseInfo());
				cs1.setColor(cs.getColor());
				cs1.setPlace(cs.getPlace());
				cs1.setCosts(cs.getCosts());
				cs1.setCount(cs.getCount());
				cs1.setHasReminder(cs.getHasReminder());
				cs1.setReminder(cs.getReminder());
				cs1.setEndTime(cs.getEndTime());
				cs1.setStartTime(cs.getStartTime());
				courses.add(cs1);
			}
			saveOrUpdate(courses);
			return cs;
		} else {
			final Course cs1 = getHibernateTemplate().merge(cs);
			return cs1;
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Course saveCourse(Course cs, Integer saveType) {
		try {
			if (cs.getType() == null || cs.getType() == 0) {
				cs = getHibernateTemplate().merge(cs);
			} else {
				List<Course> ccss = handlerPeriod(cs);
				saveOrUpdate(ccss);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new LogicException(e);
		}
		return cs;
	}

	private Integer findMaxGroup(final String table) {
		Integer i = 1;
		final SQLQuery query = getSession().createSQLQuery("select max(groupby) from " + table);
		final List<?> list = query.list();
		if (list.size() > 0) {
			final Object obj = list.get(0);
			if (obj == null) return i;
			i = (Integer) obj;
			i++;
		}
		return i;
	}

	private List<Course> handlerPeriod(Course cs) throws ParseException, IllegalAccessException, InvocationTargetException {
		final Integer maxGroup = findMaxGroup("tb_course");

		cs.setGroupBy(maxGroup);

		final Integer mode = cs.getMode();
		final String value = cs.getValue();

		final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		// String sDate = ;
		final Calendar c = Calendar.getInstance();
		c.setTime(sdf.parse(cs.getRepeatStart()));

		Date eDate = null;
		if (cs.getRepeatEnd() != null && !"".equals(cs.getRepeatEnd())) eDate = sdf.parse(cs.getRepeatEnd());

		final List<Course> ccss = new ArrayList<Course>();

		final Integer repeatNum = cs.getRepeatNum();

		if (mode == 1) {
			final Integer jg = new Integer(value);

			if (eDate == null) {
				for (int i = 0; i < repeatNum; i++) {
					final Course cs1 = new Course();
					BeanUtils.copyProperties(cs1, cs);
					cs1.setPlanDate(sdf.format(c.getTime()));
					ccss.add(cs1);
					c.add(Calendar.DAY_OF_MONTH, jg);
				}
			} else {
				while (c.getTime().before(eDate)) {
					final Course cs1 = new Course();
					BeanUtils.copyProperties(cs1, cs);
					cs1.setPlanDate(sdf.format(c.getTime()));
					ccss.add(cs1);
					c.add(Calendar.DAY_OF_MONTH, jg);
				}
			}
		} else if (mode == 2) {
			final String[] weeks = value.split(",");
			if (eDate == null) {
				for (int i = 0; i < repeatNum; i++) {
					for (String w : weeks) {
						c.set(Calendar.DAY_OF_WEEK, Integer.valueOf(w));
						final Course cs1 = new Course();
						BeanUtils.copyProperties(cs1, cs);
						cs1.setPlanDate(sdf.format(c.getTime()));
						ccss.add(cs1);
					}
					c.add(Calendar.WEEK_OF_YEAR, 1);
					c.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
				}
			} else {
				while (c.getTime().before(eDate)) {
					for (String w : weeks) {
						c.set(Calendar.DAY_OF_WEEK, Integer.valueOf(w));
						if (c.getTime().before(eDate)) {
							final Course cs1 = new Course();
							BeanUtils.copyProperties(cs1, cs);
							cs1.setPlanDate(sdf.format(c.getTime()));
							ccss.add(cs1);
						}
					}
					c.add(Calendar.WEEK_OF_YEAR, 1);
					c.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
				}
			}
		} else if (mode == 3) {
			final Integer jg = new Integer(value);
			if (eDate == null) {
				c.set(Calendar.DAY_OF_MONTH, jg);
				for (int i = 0; i < repeatNum; i++) {
					final Course cs1 = new Course();
					BeanUtils.copyProperties(cs1, cs);
					cs1.setPlanDate(sdf.format(c.getTime()));
					ccss.add(cs1);
					c.add(Calendar.MONTH, 1);
					c.set(Calendar.DAY_OF_MONTH, jg);
				}
			} else {
				c.set(Calendar.DAY_OF_MONTH, jg);
				while (c.getTime().before(eDate)) {
					final Course cs1 = new Course();
					BeanUtils.copyProperties(cs1, cs);
					cs1.setPlanDate(sdf.format(c.getTime()));
					ccss.add(cs1);
					c.add(Calendar.MONTH, 1);
					c.set(Calendar.DAY_OF_MONTH, jg);
				}
			}
		} else if (mode == 4) {
			final Integer jg = new Integer(value);
			final Integer weekOfMonth = cs.getWeekOf();
			if (eDate == null) {
				c.set(Calendar.WEEK_OF_MONTH, jg);
				c.set(Calendar.DAY_OF_WEEK, weekOfMonth);
				for (int i = 0; i < repeatNum; i++) {
					final Course cs1 = new Course();
					BeanUtils.copyProperties(cs1, cs);
					cs1.setPlanDate(sdf.format(c.getTime()));
					ccss.add(cs1);
					c.add(Calendar.MONTH, 1);
					c.set(Calendar.WEEK_OF_MONTH, jg);
					c.set(Calendar.DAY_OF_WEEK, weekOfMonth);
				}
			} else {
				c.set(Calendar.WEEK_OF_MONTH, jg);
				c.set(Calendar.DAY_OF_WEEK, weekOfMonth);
				while (c.getTime().before(eDate)) {
					final Course cs1 = new Course();
					BeanUtils.copyProperties(cs1, cs);
					cs1.setPlanDate(sdf.format(c.getTime()));
					ccss.add(cs1);
					c.add(Calendar.MONTH, 1);
					c.set(Calendar.WEEK_OF_MONTH, jg);
					c.set(Calendar.DAY_OF_WEEK, weekOfMonth);
				}
			}
		}
		return ccss;
	}

	/**
	 * 保存申请的课程，需要将总人数进行记录。
	 * 
	 * @param course
	 *            ，当前被申请的课程
	 * @param member
	 *            ，当前申请会员
	 * @return 返回总的加入人数
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Integer saveRequest(Course course, Member member) {
		int joinNum = course.getJoinNum() == null ? 1 : course.getJoinNum() + 1;
		course.setJoinNum(joinNum);
		getHibernateTemplate().merge(course);
		Apply apply = new Apply();
		apply.setApplyDate(new Date());
		apply.setCourse(course);
		apply.setStatus(STATUS_REQUEST_COURSE_WAIT);
		apply.setIsReadBuy(STATUS_REQUEST_NOREAD);
		apply.setIsReadSell(STATUS_REQUEST_NOREAD);
		apply.setMember(member);
		getHibernateTemplate().merge(apply);
		return joinNum;
	}

	/**
	 * 删除课程，如果saveType (groupBy)大于0，则表示删除其批次生成的所有的课程
	 * 
	 * @param id
	 *            ，当前课程ID号
	 * @param saveType
	 *            ，批次号
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteCourse(final Long id, final Integer saveType) {
		if (saveType != null && saveType > 0) { // 批次删除
			List<?> courses = getHibernateTemplate().find("from Course c where c.groupBy = ?", saveType);
			for (final Iterator<?> it = courses.iterator(); it.hasNext();) {
				Course c = (Course) it.next();
				getHibernateTemplate().delete(c);
			}
		} else {
			delete(Course.class, id);
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void saveApplyStatus(String status, List<Apply> applyList) {
		final Date sendTime = new Date();
		for (final Apply a : applyList) {
			a.setStatus(status);
			a.setApproveDate(new Date());
			a.setIsReadBuy("0");
			a.setIsReadSell("0");
			final Course c = a.getCourse();
			if (status.equals("2")) {
				getHibernateTemplate().merge(
						new Message(c.getMember(), a.getMember(), sendTime, c.getCourseInfo().getMember().getName() + "同意了您的课程【" + c.getCourseInfo().getName()
								+ "】预约申请", "3", "0", "1"));
			} else if (status.equals("3")) {
				c.setJoinNum(c.getJoinNum() == null ? 0 : c.getJoinNum() - 1);
				getHibernateTemplate().merge(c);
				getHibernateTemplate().merge(
						new Message(c.getMember(), a.getMember(), sendTime, c.getCourseInfo().getMember().getName() + "拒绝了您的课程【" + c.getCourseInfo().getName()
								+ "】预约申请", "3", "0", "1"));
			}
			getHibernateTemplate().merge(a);
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void saveSiteApplyStatus(String status, List<FactoryApply> applyList, Member member) {
		final Date sendTime = new Date();
		for (final FactoryApply a : applyList) {
			a.setStatus(status);
			a.setApproveDate(new Date());
			a.setIsReadBuy("0");
			a.setIsReadSell("0");
			final FactoryCosts fc = a.getFactorycosts();
			if (status.equals("2")) {
				getHibernateTemplate().merge(
						new Message(member, a.getMember(), sendTime, member.getName() + "同意了您的场地【" + fc.getFactory().getName() + "】预约申请", "3", "0", "1"));
			} else if (status.equals("3")) {
				getHibernateTemplate().merge(
						new Message(member, a.getMember(), sendTime, member.getName() + "拒绝了您的场地【" + fc.getFactory().getName() + "】预约申请", "3", "0", "1"));
			}
			getHibernateTemplate().merge(a);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteApplys(Long[] ids, Member member) {
		List<Message> msgList = new ArrayList<Message>();
		List<Apply> applyList = (List<Apply>) findObjectByIds(Apply.class, "id", ids);
		List<Course> courseList = new ArrayList<Course>();
		Course c = null;
		Date sendTime = new Date();
		for (Apply a : applyList) {
			c = a.getCourse();
			c.setJoinNum(c.getJoinNum() == null ? 0 : c.getJoinNum() - 1);
			courseList.add(c);
			if (member.getId().equals(a.getMember().getId())) {
				msgList.add(new Message(null, a.getMember(), sendTime, "您撤销了您的课程【" + c.getCourseInfo().getName() + "】预约申请", "3", "0", "1"));
				msgList.add(new Message(null, c.getCourseInfo().getMember(), sendTime, a.getMember().getName() + "撤销了课程【" + c.getCourseInfo().getName()
						+ "】预约申请", "3", "0", "1"));

			} else if (member.getId().equals(c.getCourseInfo().getMember().getId())) {
				msgList.add(new Message(null, c.getCourseInfo().getMember(), sendTime, "您撤销了" + a.getMember().getName() + "的课程【" + c.getCourseInfo().getName()
						+ "】预约申请", "3", "0", "1"));
				msgList.add(new Message(null, a.getMember(), sendTime, c.getCourseInfo().getMember().getName() + "撤销了您的课程【" + c.getCourseInfo().getName()
						+ "】预约申请", "3", "0", "1"));

			}
		}
		saveOrUpdate(courseList);
		this.delete(Apply.class, ids);
		saveOrUpdate(msgList);
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteSiteApplys(Long[] ids, Member member) {
		List<Message> msgList = new ArrayList<Message>();
		List<FactoryApply> applyList = (List<FactoryApply>) findObjectByIds(FactoryApply.class, "id", ids);
		Date sendTime = new Date();
		for (FactoryApply a : applyList) {
			Member tomember = (Member) this.load(Member.class, a.getFactorycosts().getFactory().getClub());
			if (member.getId().equals(a.getMember().getId())) {
				msgList.add(new Message(null, a.getMember(), sendTime, "您撤销了您的场地【" + a.getFactorycosts().getFactory().getName() + "】预约申请", "3", "0", "1"));
				msgList.add(new Message(null, tomember, sendTime, a.getMember().getName() + "撤销了场地【" + a.getFactorycosts().getFactory().getName() + "】预约申请",
						"3", "0", "1"));

			} else if (member.getId().equals(tomember.getId())) {
				msgList.add(new Message(null, tomember, sendTime, "您撤销了" + a.getMember().getName() + "的场地【" + a.getFactorycosts().getFactory().getName()
						+ "】预约申请", "3", "0", "1"));
				msgList.add(new Message(null, a.getMember(), sendTime, tomember.getName() + "撤销了您的场地【" + a.getFactorycosts().getFactory().getName() + "】预约申请",
						"3", "0", "1"));

			}
		}
		this.delete(FactoryApply.class, ids);
		saveOrUpdate(msgList);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Appraise saveOrUpdateAppraise(Appraise appraise) {
		final Member memberFrom = getHibernateTemplate().load(Member.class, appraise.getMemberTo().getId());
		memberFrom.setCountSocure((memberFrom.getCountSocure() == null ? 0d : memberFrom.getCountSocure()) + appraise.getGrade());
		memberFrom.setCountEmp((memberFrom.getCountEmp() == null ? 0 : memberFrom.getCountEmp()) + 1);
		getHibernateTemplate().saveOrUpdate(memberFrom);
		final Member memberTo = getHibernateTemplate().load(Member.class, appraise.getMemberTo().getId());
		memberTo.setIntegralCount(memberTo.getIntegralCount() == null ? 1 : memberTo.getIntegralCount() + 1);
		return getHibernateTemplate().merge(appraise);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteAppraise(Long id) {
		final Appraise ap = getHibernateTemplate().load(Appraise.class, id);
		final Member m = ap.getMemberTo();
		m.setCountSocure((m.getCountSocure() == null ? 0d : m.getCountSocure()) - ap.getGrade());
		m.setCountEmp((m.getCountEmp() == null ? 0 : m.getCountEmp()) - 1);
		getHibernateTemplate().merge(m);
		getHibernateTemplate().delete(ap);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteApply(final Long courseId, final Long memberId) {
		final List<?> list = getHibernateTemplate().find("from Apply a where a.member.id = ? and a.course.id = ?", new Object[] { memberId, courseId });
		final int size = list.size();
		Course c = null;
		if (list.size() > 0) {
			c = ((Apply) list.get(0)).getCourse();
		}
		getHibernateTemplate().deleteAll(list);
		c.setJoinNum(c.getJoinNum() - size);
		getHibernateTemplate().merge(c);
	}

}
