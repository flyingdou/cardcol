package com.freegym.web.plan;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.freegym.web.basic.Member;
import com.freegym.web.config.CourseInfo;
import com.freegym.web.course.Apply;
import com.freegym.web.order.CourseOrder;
import com.sanmen.web.core.bean.CommonId;

import net.sf.json.JSONObject;

@Entity
@Table(name = "TB_COURSE")
public class Course extends CommonId implements Cloneable {

	private static final long serialVersionUID = 2796965949078262759L;

	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	/**
	 * 制定课程会员,如果是团体课，则coach为指定的教练，如果是会员制订课程，则member为会员，coach为对应的私教，planSource为2。
	 * 如果member为教练
	 */
	@ManyToOne(targetEntity = Member.class)
	@JoinColumn(name = "member")
	private Member member;

	/**
	 * 上课人，主要指私教会员
	 */
	@ManyToOne(targetEntity = Member.class)
	@JoinColumn(name = "coach", nullable = true)
	private Member coach;

	/**
	 * 课程信息
	 */
	@ManyToOne(targetEntity = CourseInfo.class,cascade=CascadeType.ALL)
	@JoinColumn(name = "courseId", nullable = false)
	private CourseInfo courseInfo;

	/**
	 * 课程日期
	 */
	@Column(name = "planDate", length = 10, nullable = false)
	private String planDate;

	/**
	 * 开始时间
	 */
	@Column(length = 5, nullable = false)
	private String startTime;

	/**
	 * 结束时间
	 */
	@Column(length = 5, nullable = false)
	private String endTime;

	/**
	 * 完成日期
	 */
	@Column(name = "doneDate", length = 10)
	private String doneDate;

	/**
	 * 备注
	 */
	@Column(length = 255)
	private String memo;

	/**
	 * 课程场地
	 */
	@Column(length = 30, nullable = false)
	private String place;

	/**
	 * 課程展现顏色
	 */
	@Column(length = 30)
	private String color;

	/**
	 * 人數上限
	 */
	private Integer count;

	/**
	 * 加入人數
	 */
	private Integer joinNum;

	/**
	 * 费用
	 */
	@Column(length = 30)
	private String costs;

	/**
	 * 会员价
	 */
	@Column(name = "member_price", precision = 18, scale = 2)
	private Double memberPrice;

	/**
	 * 非会员价
	 */
	@Column(name = "hour_price", precision = 18, scale = 2)
	private Double hourPrice;

	//
	private Integer cycleCount;
	private Integer countdown;

	/**
	 * 周期类型
	 */
	private Integer mode;

	/**
	 * 周期模式，0單次，1為周期
	 */
	private Integer type;

	/**
	 * 周期模式值
	 */
	@Column(length = 30)
	private String value;

	/**
	 * 每周
	 */
	private Integer weekOf;

	/**
	 * 重複條件
	 */
	@Column(length = 30)
	private String repeatWhere;

	/**
	 * 重複開始時間
	 */
	@Column(length = 10)
	private String repeatStart;

	/**
	 * 重複次數
	 */
	private Integer repeatNum;

	/**
	 * 重複結束時間
	 */
	@Column(length = 10)
	private String repeatEnd;

	/**
	 * 是否提醒
	 */
	private Integer hasReminder;

	/**
	 * 提醒时间（分钟）
	 */
	private Integer reminder;

	/**
	 * 組次
	 */
	private Integer groupBy;
	/**
	 * 计划来源 1:私人健身教练或俱乐部;2: 会员DIY;3:系统自动产生 私人健身教练：有私人教练会员的计划均为这一种
	 * 会员DIY：没有私人教练会员的计划 系统自动产生：卡库智能计划功能产生
	 */
	private String planSource;

	/**
	 * 对于本次课程的总评分
	 */
	@Column(name = "count_grade", precision = 18, scale = 1)
	private Double countGrade;

	/**
	 * 对于本次课程评分人数
	 */
	private Integer appraiseCount;

	/**
	 * 计划音乐ID
	 */
	@ManyToOne(targetEntity = Music.class)
	@JoinColumn(name = "music_id")
	private Music music;

	/**
	 * 创建时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_time", nullable = true)
	private Date createTime;

	@Transient
	private Double avgGrade;

	@Transient
	private Date startDate, endDate;

	/**
	 * 能否申请加入
	 */
	@Transient
	private Integer canJoin;

	/**
	 * 能否进行编辑
	 */
	@Transient
	private Integer canEdit;

	/**
	 * 能否进行删除
	 */
	@Transient
	private Integer canDel;

	@Transient
	private Integer uiType;

	/**
	 * 计划表，针对于课程制定的健身计划
	 */
	@OneToMany(targetEntity = Workout.class, fetch = FetchType.LAZY, mappedBy = "course", cascade = {
			CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE })
	@OrderBy("sort asc")
	private Set<Workout> works = new HashSet<Workout>();

	/**
	 * 预约表
	 */
	@OneToMany(targetEntity = Apply.class, fetch = FetchType.LAZY, mappedBy = "course", cascade = CascadeType.REMOVE)
	private Set<Apply> applys = new HashSet<Apply>();

	/**
	 * 课程订单表
	 */
	@OneToMany(targetEntity = CourseOrder.class, fetch = FetchType.LAZY, mappedBy = "course", cascade = CascadeType.REMOVE)
	private Set<CourseOrder> orders = new HashSet<CourseOrder>();

	/**
	 * 教练ID，主要用于新用户无私教时使用
	 */
	@Transient
	private Long coachId;

	/**
	 * 教练名称，主要用于新用户无私教时使用
	 */
	@Transient
	private String coachName;

	@Transient
	private String memberName;

	public Course() {

	}

	public Course(Long id) {
		setId(id);
	}

	public Course(Long toMember, String copyToPlanDate) {
		this.member = new Member(toMember);
		this.planDate = copyToPlanDate;
	}

	public Course(Long toMember, Member coach, CourseInfo courseInfo, String copyToPlanDate, String startTime,
			String endTime, String place, String memo, Music music, Integer cycleCount, Integer countdown) {
		this.member = new Member(toMember);
		this.planDate = copyToPlanDate;
		this.coach = coach;
		this.courseInfo = courseInfo;
		this.startTime = startTime;
		this.endTime = endTime;
		this.place = place;
		this.memo = memo;
		this.music = music;
		this.cycleCount = cycleCount;
		this.countdown = countdown;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public String getDoneDate() {
		return doneDate;
	}

	public void setDoneDate(String doneDate) {
		this.doneDate = doneDate;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public CourseInfo getCourseInfo() {
		return courseInfo;
	}

	public Member getCoach() {
		return coach;
	}

	public void setCoach(Member coach) {
		this.coach = coach;
	}

	public void setCourseInfo(CourseInfo courseInfo) {
		this.courseInfo = courseInfo;
	}

	public String getPlanDate() {
		return planDate;
	}

	public void setPlanDate(String planDate) {
		this.planDate = planDate;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public Integer getJoinNum() {
		return joinNum;
	}

	public void setJoinNum(Integer joinNum) {
		this.joinNum = joinNum;
	}

	public Integer getMode() {
		return mode;
	}

	public void setMode(Integer mode) {
		this.mode = mode;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Integer getWeekOf() {
		return weekOf;
	}

	public void setWeekOf(Integer weekOf) {
		this.weekOf = weekOf;
	}

	public String getRepeatWhere() {
		return repeatWhere;
	}

	public void setRepeatWhere(String repeatWhere) {
		this.repeatWhere = repeatWhere;
	}

	public String getRepeatStart() {
		return repeatStart;
	}

	public void setRepeatStart(String repeatStart) {
		this.repeatStart = repeatStart;
	}

	public Integer getRepeatNum() {
		return repeatNum;
	}

	public void setRepeatNum(Integer repeatNum) {
		this.repeatNum = repeatNum;
	}

	public String getRepeatEnd() {
		return repeatEnd;
	}

	public void setRepeatEnd(String repeatEnd) {
		this.repeatEnd = repeatEnd;
	}

	public Integer getHasReminder() {
		return hasReminder;
	}

	public void setHasReminder(Integer hasReminder) {
		this.hasReminder = hasReminder;
	}

	public Integer getReminder() {
		return reminder;
	}

	public void setReminder(Integer reminder) {
		this.reminder = reminder;
	}

	public Integer getGroupBy() {
		return groupBy;
	}

	public void setGroupBy(Integer groupBy) {
		this.groupBy = groupBy;
	}

	public String getPlanSource() {
		return planSource;
	}

	public void setPlanSource(String planSource) {
		this.planSource = planSource;
	}

	public Date getStartDate() {
		if (startTime != null)
			try {
				startDate = sdf.parse(planDate + " " + startTime + ":00");
			} catch (ParseException e) {
				e.printStackTrace();
			}
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		if (endTime != null && !"".equals(endTime))
			try {
				endDate = sdf.parse(planDate + " " + endTime + ":00");
			} catch (ParseException e) {
				e.printStackTrace();
			}
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getCosts() {
		return costs;
	}

	public void setCosts(String costs) {
		this.costs = costs;
	}

	public Double getMemberPrice() {
		return memberPrice;
	}

	public void setMemberPrice(Double memberPrice) {
		this.memberPrice = memberPrice;
	}

	public Double getHourPrice() {
		return hourPrice;
	}

	public void setHourPrice(Double hourPrice) {
		this.hourPrice = hourPrice;
	}

	public Double getCountGrade() {
		return countGrade;
	}

	public void setCountGrade(Double countGrade) {
		this.countGrade = countGrade;
	}

	public Integer getAppraiseCount() {
		return appraiseCount;
	}

	public void setAppraiseCount(Integer appraiseCount) {
		this.appraiseCount = appraiseCount;
	}

	public Double getAvgGrade() {
		try {
			final DecimalFormat df = new DecimalFormat("#00.00");
			avgGrade = new Double(df.format(countGrade / appraiseCount));
		} catch (Exception e) {

		}
		return avgGrade;
	}

	public void setAvgGrade(Double avgGrade) {
		this.avgGrade = avgGrade;
	}

	public Music getMusic() {
		return music;
	}

	public void setMusic(Music music) {
		this.music = music;
	}

	public Integer getCanJoin() {
		return canJoin;
	}

	public void setCanJoin(Integer canJoin) {
		this.canJoin = canJoin;
	}

	public Integer getCanEdit() {
		return canEdit;
	}

	public void setCanEdit(Integer canEdit) {
		this.canEdit = canEdit;
	}

	public Integer getCanDel() {
		return canDel;
	}

	public void setCanDel(Integer canDel) {
		this.canDel = canDel;
	}

	public Integer getUiType() {
		return uiType;
	}

	public void setUiType(Integer uiType) {
		this.uiType = uiType;
	}

	public Set<Workout> getWorks() {
		return works;
	}

	public void setWorks(Set<Workout> works) {
		this.works = works;
	}

	public Set<Apply> getApplys() {
		return applys;
	}

	public void setApplys(Set<Apply> applys) {
		this.applys = applys;
	}

	public Set<CourseOrder> getOrders() {
		return orders;
	}

	public void setOrders(Set<CourseOrder> orders) {
		this.orders = orders;
	}

	public Long getCoachId() {
		if (coach != null) {
			coachId = coach.getId();
		}
		return coachId;
	}

	public void setCoachId(Long coachId) {
		this.coachId = coachId;
		if (coachId != null) {
			setCoach(new Member(coachId));
		}
	}

	public String getCoachName() {
		if (coach != null) {
			coachName = coach.getNick();
		}
		return coachName;
	}

	public void setCoachName(String coachName) {
		this.coachName = coachName;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public void addWorkout(Workout wk) {
		wk.setCourse(this);
		getWorks().add(wk);
	}

	public Integer getCycleCount() {
		return cycleCount;
	}

	public void setCycleCount(Integer cycleCount) {
		this.cycleCount = cycleCount;
	}

	public Integer getCountdown() {
		return countdown;
	}

	public void setCountdown(Integer countdown) {
		this.countdown = countdown;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	@Override
	public String getTableName() {
		return null;
	}

	public JSONObject toJson() {
		final JSONObject obj = new JSONObject();
		obj.accumulate("id", getId()).accumulate("clubName", getCourseInfo().getMember().getName())
				.accumulate("courseName", getCourseInfo().getName()).accumulate("planDate", planDate)
				.accumulate("startTime", startTime).accumulate("endTime", endTime)
				.accumulate("memberPrice", memberPrice).accumulate("hourPrice", hourPrice)
				.accumulate("image", getCourseInfo().getImage()).accumulate("address", member.getAddress())
				.accumulate("empNum", count).accumulate("joinNum", joinNum).accumulate("memo", memo)
				.accumulate("cycleCount", cycleCount).accumulate("countdown", countdown);

		return obj;
	}

	public JSONObject toJson1() {
		final JSONObject obj = new JSONObject();
		final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		obj.accumulate("id", getId()).accumulate("appraiseCount", getAppraiseCount())
				.accumulate("avgGrade", getAvgGrade()).accumulate("canDel", getCanDel())
				.accumulate("canEdit", getCanEdit()).accumulate("canJoin", getCanJoin())
				.accumulate("coachId", getCoachId()).accumulate("coachName", getCoachName())
				.accumulate("color", getColor()).accumulate("costs", getCosts()).accumulate("count", getCount())
				.accumulate("countdown", getCountdown()).accumulate("countGrade", getCountGrade())
				.accumulate("createTime", getCreateTime()).accumulate("cycleCount", getCycleCount())
				.accumulate("doneDate", getDoneDate())
				.accumulate("endDate", getEndDate() == null ? null : sdf.format(getEndDate()))
				.accumulate("endTime", getEndTime()).accumulate("groupBy", getGroupBy())
				.accumulate("hasReminder", getHasReminder()).accumulate("hourPrice", getHourPrice())
				.accumulate("joinNum", getJoinNum()).accumulate("memberName", getMemberName())
				.accumulate("memberPrice", getMemberPrice()).accumulate("memo", getMemo()).accumulate("mode", getMode())
				.accumulate("place", getPlace()).accumulate("planDate", getPlanDate())
				.accumulate("planSource", getPlanSource()).accumulate("reminder", getReminder())
				.accumulate("repeatEnd", getRepeatEnd()).accumulate("repeatNum", getRepeatNum())
				.accumulate("repeatStart", getRepeatStart()).accumulate("repeatWhere", getRepeatWhere())
				.accumulate("startDate", getStartDate() == null ? null : sdf.format(getStartDate()))
				.accumulate("startTime", getStartTime()).accumulate("type", getType()).accumulate("uiType", getUiType())
				.accumulate("value", getValue()).accumulate("weekOf", getWeekOf());
		final JSONObject memberObj = new JSONObject();
		memberObj.accumulate("id", member.getId()).accumulate("name", member.getName());
		final JSONObject coachObj = new JSONObject();
		coachObj.accumulate("id", coach.getId()).accumulate("name", coach.getName());
		final JSONObject courseInfoObj = new JSONObject();
		courseInfoObj.accumulate("id", courseInfo.getId()).accumulate("name", courseInfo.getName());
		obj.accumulate("member", memberObj).accumulate("coach", coachObj).accumulate("courseInfo", courseInfoObj);
		return obj;
	}

	@SuppressWarnings("deprecation")
	public static DetachedCriteria getCriteriaQuery(Course query, String county, Long typeId, String isAll) {
		final DetachedCriteria dc = DetachedCriteria.forClass(Course.class);
		dc.createAlias("courseInfo", "c", Criteria.LEFT_JOIN);
		dc.createAlias("member", "m", Criteria.LEFT_JOIN);
		dc.createAlias("c.type", "p", Criteria.LEFT_JOIN);
		if (query != null) {
			if (query.getCoachName() != null && !"".equals(query.getCoachName())) {
				dc.add(Restrictions.eq("m.city", query.getCoachName()));
			}
			if (null != isAll && isAll.equals("true")) {
				Date date = new Date();
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				String currDate = df.format(date);
				String afterDate = df.format(new Date(date.getTime() + 2 * 24 * 60 * 60 * 1000));
				String sysTime = String.format("%tH:%tM", date, date);
				dc.add(Restrictions
						.sqlRestriction("CONCAT(this_.planDate, this_.startTime) >= '" + currDate + sysTime + "'"));
				dc.add(Restrictions.le("planDate", afterDate));
			} else {
				if (query.getPlanDate() != null && !"".equals(query.getPlanDate())) {
					dc.add(Restrictions.eq("planDate", query.getPlanDate()));
				}
			}
		}
		// 地区
		if (county != null && !"".equals(county)) {
			dc.add(Restrictions.eq("m.county", county));
		}
		// 类型
		if (typeId != null && !"".equals(typeId)) {
			dc.add(Restrictions.eq("p.id", typeId));
		}

		dc.add(Restrictions.eq("m.role", "E"));

		return dc;
	}

	@Override
	public String toString() {
		return "Course [member=" + member + ", coach=" + coach + ", courseInfo=" + courseInfo + ", planDate=" + planDate
				+ ", startTime=" + startTime + ", endTime=" + endTime + ", doneDate=" + doneDate + ", memo=" + memo
				+ ", place=" + place + ", color=" + color + ", count=" + count + ", joinNum=" + joinNum + ", costs="
				+ costs + ", memberPrice=" + memberPrice + ", hourPrice=" + hourPrice + ", cycleCount=" + cycleCount
				+ ", countdown=" + countdown + ", mode=" + mode + ", type=" + type + ", value=" + value + ", weekOf="
				+ weekOf + ", repeatWhere=" + repeatWhere + ", repeatStart=" + repeatStart + ", repeatNum=" + repeatNum
				+ ", repeatEnd=" + repeatEnd + ", hasReminder=" + hasReminder + ", reminder=" + reminder + ", groupBy="
				+ groupBy + ", planSource=" + planSource + ", countGrade=" + countGrade + ", appraiseCount="
				+ appraiseCount + ", music=" + music + ", createTime=" + createTime + ", avgGrade=" + avgGrade
				+ ", startDate=" + startDate + ", endDate=" + endDate + ", canJoin=" + canJoin + ", canEdit=" + canEdit
				+ ", canDel=" + canDel + ", uiType=" + uiType + ", works=" + works + ", applys=" + applys + ", orders="
				+ orders + ", coachId=" + coachId + ", coachName=" + coachName + ", memberName=" + memberName + "]";
	}
}
