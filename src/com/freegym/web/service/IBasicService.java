package com.freegym.web.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.freegym.web.active.Active;
import com.freegym.web.active.TrainRecord;
import com.freegym.web.basic.Body;
import com.freegym.web.basic.Friend;
import com.freegym.web.basic.Member;
import com.freegym.web.config.Action;
import com.freegym.web.config.CourseInfo;
import com.freegym.web.config.Factory;
import com.freegym.web.config.Project;
import com.freegym.web.config.Setting;
import com.freegym.web.config.WorkTime;
import com.freegym.web.course.Appraise;
import com.freegym.web.course.Message;
import com.freegym.web.course.SignIn;
import com.freegym.web.order.ActiveOrder;
import com.freegym.web.order.PresentHeartRate;
import com.freegym.web.order.Product;
import com.freegym.web.plan.Course;
import com.freegym.web.system.Area1;
import com.freegym.web.utils.SessionConstant;
import com.sanmen.web.core.service.IService;

public interface IBasicService extends IService, SessionConstant {

	public Member login(final String username, final String password);

	/**
	 * 签到检核
	 * 
	 * @param username
	 * @param password
	 * @return
	 */
	public Member sign(final String username, final String password);

	/**
	 * 获取唯一标识号方法
	 * 
	 * @param keyPrefix
	 *            ，标识号前缀
	 * @param tableName
	 *            ，标识号标记
	 * @param keyNoLength
	 *            ，标识号长度
	 */
	@Override
	public String getKeyNo(String keyPrefix, String tableName, int keyNoLength);

	public TrainRecord getCurrentRecord(final Long userId);

	/**
	 * 查询到当前时间总运动时间time，计划的完成率finishrate
	 * 
	 * @param userId
	 * @return
	 */
	public Map<String, Object> getTotalRecord(final Member m);

	public Setting loadSetting(final Long id);

	public Long findCountByWorkout(final Long id);

	public List<Friend> findFriendByType(final Long member, final String type);

	public List<?> findDefaultFavoriteCardio();

	public List<Project> loadProjects(final Long id);

	public List<Project> loadProjects1(final Long id);

	public List<Project> loadProjectsByApplied(final Long id);

	public void saveApplied(final Long member, List<Project> projects);

	public Project saveProject(final Long member, final Project project);

	public Member saveWorkTime(final Member member, final List<WorkTime> worktimes);

	public List<?> findAllCourse(final Member member);

	/**
	 * 加载当前会员，当前日期之后的所有数据
	 * 
	 * @param memberId
	 *            ，会员ID
	 * @param planDate
	 *            ，计划日期
	 * @return
	 */
	public List<Course> loadCourses(Long memberId, String planDate);

	/**
	 * 依据当前教练查找所有动作。
	 * 
	 * @param mode
	 * @param coachId
	 * @param partName
	 * @return
	 */
	public List<Action> findActionByCoachAndPart(final Character mode, final Long coachId, final String partName);

	/**
	 * 获取当前时间点的课程
	 * 
	 * @param memberId
	 * @param dateStr
	 * @param sTime
	 * @param eTime
	 * @param ci
	 * @return
	 */
	public Course getCourse(Long memberId, String dateStr, String sTime, String eTime, CourseInfo ci);

	/**
	 * 获得喜爱的有氧运动
	 * 
	 * @param favoriateCardio
	 * @param l
	 * @return
	 */
	public List<Action> getFavoriateCardioCatalogs(String favoriateCardio, Long coachId);

	/**
	 * 根据用户邮箱地址查找其所有信息，主要用于根据邮箱去查找密码
	 * 
	 * @param email
	 * @return
	 */
	public Member findMemberByMail(final String email);

	/**
	 * 查找所有的热点并开通的城市
	 * 
	 * @return
	 */
	public List<Area1> findHotCity();

	/**
	 * 查找所有开通的城市列表
	 * 
	 * @return
	 */
	public List<Area1> findOpenCity();

	/**
	 * 查找所有开通的城市列表，以拼音排序并分组
	 * 
	 * @return
	 */
	public Map<Character, List<Area1>> findOpenCityForPinYin();

	/**
	 * 根据当前用户名及邮件地址，取得当前用户信息，如果不存在，则返回NULL对象
	 * 
	 * @param username
	 * @param email
	 * @return
	 */
	public Member findMemberByMail(final String username, final String email);

	public List<?> findObjectByIds(final Class<?> cls, final String fieldName, final Object[] ids);

	/**
	 * 如果当前传入的城市未开通，则取系统默认的城市，否则取当前城市
	 * 
	 * @param city
	 * @return
	 */
	public String getCurrentCity(final String city);

	public void clean();

	/**
	 * 解除会员私教关系，并将与私教的订单修改为暂停
	 * 
	 * @param member
	 * @return
	 */
	public Member saveRelieve(final Member member);

	/**
	 * 查询签到List
	 * 
	 * @param memberFrom
	 *            签到方
	 * @param memberTo
	 *            签到确定方
	 * @param startDate
	 *            签到开始时间
	 * @param endDate
	 *            签到结束时间
	 * @return 签到List
	 */
	public List<SignIn> findSignListByDate(final Member memberFrom, final Member memberTo, final Date startDate, final Date endDate);

	/**
	 * 查询签到List
	 * 
	 * @param orderId
	 *            订单ID
	 * @return 签到List
	 */
	public List<SignIn> findSignListByOrderId(final Long orderId);

	/**
	 * 查询卡酷网
	 * 
	 * @return 卡酷网member
	 */
	public Member findCardColManager();

	/**
	 * 保存训练记录
	 * 
	 * @param record
	 *            训练记录对象
	 * @param member
	 *            填写训练人员
	 * @param doneDate
	 * @return member
	 */
	public Member saveOrUpdateRecord(final List<TrainRecord> record, final Member member, final String doneDate) throws Exception;

	/**
	 * 训练周期提醒：用户如果一个月签到次数少于1次，系统发提醒邮件。
	 */
	public void sendMailByWorkoutTimes();

	/**
	 * 查找当前用户的所有参与的活动
	 * 
	 * @param id
	 * @return
	 */
	public List<Active> findActiveByMember(Long id);

	/**
	 * 查找正在参与活动的所有会员
	 */
	public List<Map<String, Object>> findActiving();

	/**
	 * 保存挑战报名，同时生成挑战订单
	 * 
	 * @param partake
	 *            ，参与表
	 * @param teamId
	 *            ，团队ID
	 * @param name
	 *            ，团队名称
	 * @param createMode
	 *            ，创建模式
	 * @param members
	 *            ,团队成员
	 */
	public ActiveOrder saveActivePartake(ActiveOrder partake, Long teamId, Character createMode, String name, String members);

	/**
	 * 汇总当前用户，指定日期的实际力量负荷
	 * 
	 * @param memberId
	 * @param doneDate
	 * @return
	 */
	public Double getActualWeight(Long memberId, Date doneDate);

	/**
	 * 获取当前会员指定时间内的实际数据
	 * 
	 * @param memberId
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public Double getActualWeight(Long memberId, Date startDate, Date endDate);

	/**
	 * 依据当前会员当前活动,完成日期查询截至完成日期范围内的实际运动次数
	 * 
	 * @param memberId
	 * @param activeId
	 * @param doneDate
	 * @return
	 */
	public Integer getActualTime(Long memberId, Long activeId, Date doneDate);

	/**
	 * 更新记录状态，同时更新会员最新数据
	 * 
	 * @param id
	 * @param audit
	 */
	public void updateRecordData(Long id, Character audit);

	/**
	 * 取得当前会员的动作数据,依据项目类型
	 * 
	 * @param memberId
	 * @param mode
	 * @return
	 */
	public List<?> findActionByMode(long memberId, char mode);

	/**
	 * 判断两个会员之间是否有关系
	 */
	public boolean hasRelation(final Long fromId, final Long toId);

	/**
	 * 判断两个会员之间是否有关系
	 */
	public boolean hasRelation(Long id, Long id2, String role);

	/**
	 * 会员 教练 俱乐部 签到校验
	 */
	public boolean registration(Long id, Long id2);

	/**
	 * 发送系统提醒
	 * 
	 * @param lists
	 * @return
	 */
	public Message sendNotity(Object object, String content);

	public String sendMessage(final String mobile, final String keyName, final Object... values);

	/**
	 * 保存场地，如果名称与原名称不一致，则需要进行课程表的更新
	 * 
	 * @param factorys
	 */
	public void saveFactory(List<Factory> factorys);

	/**
	 * 获得当前日期的记录数据
	 * 
	 * @param id
	 * @param nowDate
	 * @return
	 */
	public TrainRecord getRecordByDate(Long id, Date nowDate);

	/**
	 * 获取会员月体态分析数据
	 * 
	 * @param id
	 * @param analyDate
	 * @return
	 */
	public String findMonthStatus(Long id, Date analyDate);

	/**
	 * 加载当前会员当前日期的体态数据
	 * 
	 * @param body
	 * @return
	 */
	public Body loadBodyData(Body body, Long member);

	/**
	 * 注册，如果是会员或教练，则加入BMI默认值
	 * 
	 * @param member
	 * @return
	 */
	public Member register(Member member);

	/**
	 * 查找当前商品所有的评价。主要是购买此商品的订单的评价。
	 * 
	 * @param id
	 * @param productTypeCard
	 * @return
	 */
	public List<Appraise> findAppraiseByProduct(Long id, Character productTypeCard);

	/**
	 * 查看教练和俱乐部发布的健身卡
	 * 
	 * @param memberList
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public Map<String, List<Product>> loadProductsByMember(List memberList);

	/**
	 * 查询商品的评价总数
	 * 
	 * @param memberList
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public Map<String, Integer> loadAppraiseNum(String type, List list);

	public Object findRecommendBySectorCode(String code, String currentCity);

	public Object findRecommendBySectorCode(String code, String currentCity, String speciality);

	public Object findRecommendBySectorCode(String[] code);

	/**
	 * 取得系统课程
	 * 
	 * @return
	 */
	public List<CourseInfo> findSystemCourse();

	/**
	 * 判断用户是否能参加过某个挑战
	 * 
	 * @param partake
	 * @param teamId
	 * @param createMode
	 * @param name
	 * @param members
	 */
	public ActiveOrder validateJoined(ActiveOrder partake, final Long teamId, final Character createMode, final String name, final String members);

	/**
	 * 
	 * @param recommSectorSlidesG
	 * @param city
	 * @param id
	 * @return
	 */
	public Object findRecommendClubsByProject(String recommSectorSlidesG, String city, Long id);

	/**
	 * 获得当前会员的健身次数
	 * 
	 * @param member
	 * @return
	 */
	public Long findWorkoutTimeByMember(Member member);

	/**
	 * 获得当前会员的健身总时间
	 * 
	 * @param member
	 * @return
	 */
	public Long findTrainTimesByMember(Member member);

	/**
	 * 获得当前会员的课程完成数
	 * 
	 * @param member
	 * @return
	 */
	public Long findTrainRateByMember(Member member);

	@SuppressWarnings("rawtypes")
	public Map<String, List<CourseInfo>> loadCourseInfosByMember(List items);

	/**
	 * 依据COURSE ID获取所有COURSEINFO 对象
	 * 
	 * @param freeProjects
	 * @return
	 */
	public List<CourseInfo> findCourseInfoByIds(String freeProjects);

	public List<Member> findMemberByIds(String useRange);

	/**
	 * 
	 * @param code
	 * @param role
	 * @return
	 */
	public List<Map<String, Object>> findRecommendMember(String code, String role);

	/**
	 * 验证用户是否存在
	 * 
	 * @param userPhone
	 * @return
	 */
	public boolean checkUserExist(String userPhone);

	/**
	 * 修改用户密码
	 * 
	 * @param member
	 */
	public void updateUserPwd(Member member);

	/**
	 * 第三方登录验证
	 * 
	 * @param thirdType
	 * @param thirdId
	 */
	public Member thirdLoginCheck(String thirdType, String thirdId);

	public Member thirdRegister(Member member);
	
	public Member thirdRegister(Member member,String wx);

	public PresentHeartRate loadPresentHeartRate(long member, Date train_date);
	
	public Map<String, Object> loadPresentHeartRate(long member,int currentPage,int pageSize);
	
	public Map<String, Object> loadPresentHeartRate(long member,int currentPage,int pageSize,String sport);
	
	public Member findMemberByName(String name);

	public Object findRecommendPlanByType(String[] planType);
}
