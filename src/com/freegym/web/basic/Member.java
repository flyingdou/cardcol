package com.freegym.web.basic;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.apache.struts2.json.annotations.JSON;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.OrderBy;
import org.hibernate.annotations.Where;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.cardcol.web.basic.ProductClub45;
import com.cardcol.web.order.ProductOrder45;
import com.cardcol.web.order.ProductOrderBalance45;
import com.freegym.web.active.Active;
import com.freegym.web.active.ActiveJudge;
import com.freegym.web.active.TeamMember;
import com.freegym.web.active.TrainRecord;
import com.freegym.web.config.CourseInfo;
import com.freegym.web.config.Setting;
import com.freegym.web.config.WorkTime;
import com.freegym.web.course.Apply;
import com.freegym.web.course.Appraise;
import com.freegym.web.course.Message;
import com.freegym.web.course.SignIn;
import com.freegym.web.order.ActiveOrder;
import com.freegym.web.order.AlwaysAddr;
import com.freegym.web.order.PickAccount;
import com.freegym.web.order.Product;
import com.freegym.web.order.ProductOrder;
import com.freegym.web.order.ProductOrderDetail;
import com.freegym.web.order.Shop;
import com.freegym.web.plan.Course;
import com.freegym.web.system.Tickling;
import com.sanmen.web.core.bean.BaseMember;

import net.sf.json.JSONObject;

@Entity
@Table(name = "TB_MEMBER")
public class Member extends BaseMember {

	private static final long serialVersionUID = 1336292809407932952L;

	/**
	 * 用户电子邮件
	 */
	@Column(length = 100)
	private String email;

	/**
	 * 用户昵称
	 */
	@Column(length = 50)
	private String nick;

	/**
	 * 用户名称
	 */
	@Column(length = 50)
	private String name;

	/**
	 * 用户性别,M为男，F为女
	 */
	@Column(length = 1)
	private String sex;

	/**
	 * 用户密码
	 */
	@Column(length = 64)
	private String password;

	/**
	 * 会员生日
	 */
	@Temporal(TemporalType.DATE)
	private Date birthday;

	/**
	 * 身份证图片
	 */
	@Column(name = "card_image", length = 50)
	private String cardImage;

	/**
	 * 用户移动电话
	 */
	@Column(length = 11)
	private String mobilephone;

	/**
	 * 注册类型p是手机号码，u是用户名，t是第三方登陆
	 */
	@Column(length = 1)
	private String registerType;

	/**
	 * 第三方登陆方式
	 */

	@Column(length = 1)
	private String thirdType;

	/**
	 * 用户固定电话
	 */
	@Column(length = 100)
	private String tell;

	/**
	 * 用户QQ号码
	 */
	@Column(length = 30)
	private String qq;

	/**
	 * 用户MSN号码
	 */
	@Column(length = 50)
	private String wechatID;

	/**
	 * 所在省份
	 */
	@Column(length = 30)
	private String province;

	/**
	 * 会员所在城市
	 */
	@Column(length = 50)
	private String city;

	/**
	 * 会员所在区县
	 */
	@Column(length = 50)
	private String county;

	/**
	 * 通讯地址邮编
	 */
	@Column(length = 6)
	private String postal;

	/**
	 * 会员通讯地址
	 */
	@Column(length = 200)
	private String address;

	/**
	 * 会员类型，A:系统管理员，M:会员，E:俱乐部，S：教练, I：慈善机构
	 */
	@Column(length = 1)
	private String role;

	/**
	 * 会员头像
	 */
	@Column(length = 30)
	private String image;

	/**
	 * 会员照片（签到使用）
	 */
	@Column(length = 30)
	private String headPortrait;

	/**
	 * 会员私教
	 */
	@ManyToOne(targetEntity = Member.class)
	@JoinColumn(name = "coach")
	private Member coach;

	/**
	 * 会员专长，A瘦身减重，B促进健康，C肌肉健美，D达到最佳运动状态
	 */
	@Column(length = 1)
	private String speciality;

	/**
	 * 服务方式，A网络线上指导，B传统线下服务
	 */
	@Column(length = 1)
	private String mode;

	/**
	 * 教练类型，A私人教练，B团体教练
	 */
	private String style;

	/**
	 * 分成比率，指教练与俱乐部的分成比率
	 */
	@Column(precision = 18, scale = 2)
	private Double rate;

	/**
	 * 教练、俱乐部每周工作日期天数，以逗号进行分隔
	 */
	@Column(length = 20)
	private String workDate;

	/**
	 * 会员等级，0普通，1高级
	 */
	@Column(length = 1)
	private String grade;

	/**
	 * 验证码，主要用于注册找回密码用
	 */
	@Column(length = 64)
	private String verifyCode;

	/**
	 * 教练或俱乐部的总评分数
	 */
	@Column(name = "count_socure", precision = 18, scale = 2)
	private Double countSocure;

	/**
	 * 评论总人数(查询用)
	 */
	@Transient
	private Integer countEmp;

	/**
	 * 推荐时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	private Date recommTime;

	/**
	 * 个人简介
	 */
	@Lob
	@Basic
	private String description;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "stick_time")
	private Date stickTime; // 置顶时间

	@Transient
	private Integer avgGrade;// 平均评分(查询用)

	private Integer orderCount;// 销量，已经付款的订单总数

	@Temporal(TemporalType.TIMESTAMP)
	private Date endPublishTime;// 最后一次发布套餐或者课时费时间

	private Integer integralCount;// 总积分

	private Integer workoutTimes;// 训练总次数

	private Integer trainTimes;// 训练总时间

	private Integer trainRate;// 课程完成率

	/**
	 * 经度
	 */
	private Double longitude;

	/**
	 * 纬度
	 */
	private Double latitude;

	/**
	 * 服务半径（米）
	 */
	@Column(name = "radius", precision = 15, scale = 2)
	private Double radius;

	/**
	 * QQ开放平台ID号
	 */
	@Column(name = "qqid", length = 50)
	private String qqId;

	/**
	 * 新浪开放平台ID号
	 */
	@Column(name = "sina_id", length = 50)
	private String sinaId;

	/**
	 * 百度通道ID号，用于短信推送
	 */
	private Long channelId;

	/**
	 * 百度用户ID号，用于短信推送
	 */
	private String userId;

	/**
	 * 终端类型,3为ANROID,4为IOS
	 */
	private Integer termType;

	@Transient
	private Long coachId;

	@Transient
	private String coachName;

	/**
	 * 是否验证，1为已经验证通过，2为验证未通过，0为未进行验证。
	 */
	@Column(name = "has_valid", length = 1)
	private String hasValid;

	/**
	 * 手机是否验证，1为已经通过验证，0为未进行验证或未通过
	 */
	@Column(name = "mobile_valid", length = 1)
	private String mobileValid;

	/**
	 * 邮箱是否验证过，1为已经验证通过，0为未进行验证或未通过
	 */
	@Column(name = "email_valid", length = 1)
	private String emailValid;

	/**
	 * 身份证是否验证,1为已经验证通过，0为未进行验证或未通过
	 */
	@Column(name = "card_valid", length = 1)
	private String cardValid;

	/**
	 * 注册日期
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "reg_date")
	private Date regDate;

	/**
	 * 上次登录时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "LOGIN_TIME")
	private Date loginTime;

	/**
	 * 登录令牌
	 */
	@Column(name = "LOGIN_TOKEN", length = 32)
	private String toKen;

	/**
	 * 教练的所有会员
	 */
	@OneToMany(targetEntity = Member.class, mappedBy = "coach", cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
	private Set<Member> coachMembers = new HashSet<Member>();

	/**
	 * 我的好友
	 */
	@OneToMany(targetEntity = Friend.class, mappedBy = "member", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
	@Where(clause = "type = '0'")
	private Set<Friend> friends = new HashSet<Friend>();

	/**
	 * 我的俱乐部
	 */
	@OneToMany(targetEntity = Friend.class, mappedBy = "member", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
	@Where(clause = "type = '1'")
	private Set<Friend> clubs = new HashSet<Friend>();

	/**
	 * 我的教练
	 */
	@OneToMany(targetEntity = Friend.class, mappedBy = "member", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
	@Where(clause = "type = '2'")
	private Set<Friend> coachs = new HashSet<Friend>();

	/**
	 * 我的会员
	 */
	@OneToMany(targetEntity = Friend.class, mappedBy = "member", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
	@Where(clause = "type = '3'")
	private Set<Friend> members = new HashSet<Friend>();

	/**
	 * 访客
	 */
	@OneToMany(targetEntity = Friend.class, mappedBy = "member", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
	@Where(clause = "type = '4'")
	@OrderBy(clause = "joinTime desc")
	private Set<Friend> visitors = new HashSet<Friend>();

	/**
	 * 会员设置
	 */
	@OneToOne(targetEntity = Setting.class, cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
	@JoinColumn(name = "member", unique = true)
	@Fetch(FetchMode.JOIN)
	private Setting setting;

	/**
	 * 会员优惠券
	 */
	@OneToMany(targetEntity = MemberTicket.class, mappedBy = "ticket", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
	// @OrderBy(clause = "")
	private Set<MemberTicket> tickets = new HashSet<MemberTicket>();

	/**
	 * 工作时间
	 */
	@OneToMany(targetEntity = WorkTime.class, mappedBy = "member", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
	@OrderBy(clause = "startTime")
	private Set<WorkTime> times = new HashSet<WorkTime>();

	/**
	 * 套餐或者课时费
	 */
	@OneToMany(targetEntity = Product.class, mappedBy = "member", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
	@Where(clause = "(type = '1' or type = '3') and isClose = '2' and audit = '1'")
	@OrderBy(clause = "topTime desc")
	private Set<Product> products = new HashSet<Product>();

	/**
	 * 专业证书
	 */
	@OneToMany(targetEntity = Certificate.class, mappedBy = "member", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
	@OrderBy(clause = "authTime desc")
	private Set<Certificate> certificates = new HashSet<Certificate>();

	/**
	 * 积分记录
	 */
	@OneToMany(targetEntity = Integral.class, mappedBy = "member", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
	@OrderBy(clause = "inteTime desc")
	private Set<Integral> integrals = new HashSet<Integral>();

	/**
	 * 课程
	 */
	@OneToMany(targetEntity = CourseInfo.class, mappedBy = "member", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
	@OrderBy(clause = "sort asc")
	private Set<CourseInfo> courses = new HashSet<CourseInfo>();

	/**
	 * 常用地址
	 */
	@OneToMany(targetEntity = AlwaysAddr.class, mappedBy = "member", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
	private Set<AlwaysAddr> alwaysAddrs = new HashSet<AlwaysAddr>();

	/**
	 * 课程申请
	 */
	@OneToMany(targetEntity = Apply.class, mappedBy = "member", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
	private Set<Apply> applys = new HashSet<Apply>();

	/**
	 * 训练记录
	 */
	@OneToMany(targetEntity = TrainRecord.class, mappedBy = "partake", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
	@OrderBy(clause = "doneDate desc")
	private Set<TrainRecord> records = new HashSet<TrainRecord>();

	/**
	 * 共享课程计划表
	 */
	@OneToMany(targetEntity = Course.class, mappedBy = "member", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
	@OrderBy(clause = "planDate desc, endTime desc")
	@Where(clause = "share = '1'")
	private Set<Course> coursess = new HashSet<Course>();

	/**
	 * 课程表（教练），主要用于删除
	 */
	@OneToMany(targetEntity = Course.class, mappedBy = "coach", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
	private Set<Course> courseCoachs = new HashSet<Course>();

	/**
	 * 课程表（会员），主要用于删除
	 */
	@OneToMany(targetEntity = Course.class, mappedBy = "member", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
	private Set<Course> courseMembers = new HashSet<Course>();

	/**
	 * 访客表，主要用于删除
	 */
	@OneToMany(targetEntity = Friend.class, mappedBy = "friend", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
	private Set<Friend> allFriends = new HashSet<Friend>();

	/**
	 * 虚拟产品
	 */
	@OneToMany(targetEntity = Product.class, mappedBy = "member", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
	private Set<Product> prods = new HashSet<Product>();

	/**
	 * 课程评价(评价方)
	 */
	@OneToMany(targetEntity = Appraise.class, mappedBy = "member", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
	private Set<Appraise> appraises = new HashSet<Appraise>();

	/**
	 * 课程评价(被评价方)
	 */
	@OneToMany(targetEntity = Appraise.class, mappedBy = "memberTo", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
	private Set<Appraise> appraises1 = new HashSet<Appraise>();

	/**
	 * 订单明细关联，主要用于删除
	 */
	@OneToMany(targetEntity = ProductOrderDetail.class, mappedBy = "transUser", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
	private Set<ProductOrderDetail> orderDetails = new HashSet<ProductOrderDetail>();

	/**
	 * 订单明细关联，主要用于删除
	 */
	@OneToMany(targetEntity = ProductOrderDetail.class, mappedBy = "transObject", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
	private Set<ProductOrderDetail> orderDetails1 = new HashSet<ProductOrderDetail>();

	/**
	 * 消息表，主要用于删除
	 */
	@OneToMany(targetEntity = Message.class, mappedBy = "memberFrom", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
	private Set<Message> messages = new HashSet<Message>();

	/**
	 * 消息表，主要用于删除
	 */
	@OneToMany(targetEntity = Message.class, mappedBy = "memberTo", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
	private Set<Message> messages1 = new HashSet<Message>();

	/**
	 * 订单表（收货人），主要用于删除
	 */
	@OneToMany(targetEntity = ProductOrder.class, mappedBy = "member", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
	private Set<ProductOrder> orders = new HashSet<ProductOrder>();

	/**
	 * 收货地址表，主要用于删除
	 */
	@OneToMany(targetEntity = AlwaysAddr.class, mappedBy = "member", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
	private Set<AlwaysAddr> addrs = new HashSet<AlwaysAddr>();

	/**
	 * 签名表，主要用于删除
	 */
	@OneToMany(targetEntity = SignIn.class, mappedBy = "memberSign", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
	private Set<SignIn> signIns = new HashSet<SignIn>();

	/**
	 * 签名表，主要用于删除
	 */
	@OneToMany(targetEntity = SignIn.class, mappedBy = "memberAudit", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
	private Set<SignIn> signIns1 = new HashSet<SignIn>();

	/**
	 * 购物车表，主要用于删除
	 */
	@OneToMany(targetEntity = Shop.class, mappedBy = "member", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
	private Set<Shop> shops = new HashSet<Shop>();

	/**
	 * 账户信息表，主要用于删除
	 */
	@OneToMany(targetEntity = PickAccount.class, mappedBy = "member", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
	private Set<PickAccount> accounts = new HashSet<PickAccount>();

	/**
	 * 活动裁判信息表，主要用于删除
	 */
	@OneToMany(targetEntity = ActiveJudge.class, mappedBy = "judge", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
	private Set<ActiveJudge> judges = new HashSet<ActiveJudge>();

	/**
	 * 会员活动列表
	 */
	@OneToMany(targetEntity = Active.class, mappedBy = "creator", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
	private Set<Active> actives = new HashSet<Active>();

	/**
	 * 团队成员列表
	 */
	@OneToMany(targetEntity = TeamMember.class, mappedBy = "member", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
	private Set<TeamMember> teams = new HashSet<TeamMember>();

	/**
	 * 活动订单表
	 */
	@OneToMany(targetEntity = ActiveOrder.class, mappedBy = "member", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
	private Set<ActiveOrder> aos = new HashSet<ActiveOrder>();

	/**
	 * 反馈信息表
	 */
	@OneToMany(targetEntity = Tickling.class, mappedBy = "member", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
	private Set<Tickling> ticklings = new HashSet<Tickling>();

	@OneToMany(targetEntity = ProductClub45.class, mappedBy = "club", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	private Set<ProductClub45> applyClubs = new HashSet<ProductClub45>();

	@OneToMany(targetEntity = ProductOrder45.class, mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	private Set<ProductOrder45> order45s = new HashSet<ProductOrder45>();

	@OneToMany(targetEntity = ProductOrderBalance45.class, mappedBy = "from", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	private Set<ProductOrderBalance45> balanceFroms = new HashSet<ProductOrderBalance45>();

	@OneToMany(targetEntity = ProductOrderBalance45.class, mappedBy = "to", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	private Set<ProductOrderBalance45> balanceTos = new HashSet<ProductOrderBalance45>();

	@Transient
	private Integer agencyId;

	@Transient
	private String agencyName;

	public Set<Course> getCoursess() {
		return coursess;
	}

	public void setCoursess(Set<Course> coursess) {
		this.coursess = coursess;
	}

	public Set<Course> getCourseCoachs() {
		return courseCoachs;
	}

	public void setCourseCoachs(Set<Course> courseCoachs) {
		this.courseCoachs = courseCoachs;
	}

	public Set<ProductOrderDetail> getOrderDetails() {
		return orderDetails;
	}

	public void setOrderDetails(Set<ProductOrderDetail> orderDetails) {
		this.orderDetails = orderDetails;
	}

	public Set<ProductOrderDetail> getOrderDetails1() {
		return orderDetails1;
	}

	public void setOrderDetails1(Set<ProductOrderDetail> orderDetails1) {
		this.orderDetails1 = orderDetails1;
	}

	public Set<Message> getMessages() {
		return messages;
	}

	public void setMessages(Set<Message> messages) {
		this.messages = messages;
	}

	public Set<Message> getMessages1() {
		return messages1;
	}

	public void setMessages1(Set<Message> messages1) {
		this.messages1 = messages1;
	}

	public Set<ProductOrder> getOrders() {
		return orders;
	}

	public void setOrders(Set<ProductOrder> orders) {
		this.orders = orders;
	}

	public Set<AlwaysAddr> getAddrs() {
		return addrs;
	}

	public void setAddrs(Set<AlwaysAddr> addrs) {
		this.addrs = addrs;
	}

	public Set<SignIn> getSignIns() {
		return signIns;
	}

	public void setSignIns(Set<SignIn> signIns) {
		this.signIns = signIns;
	}

	public Set<SignIn> getSignIns1() {
		return signIns1;
	}

	public void setSignIns1(Set<SignIn> signIns1) {
		this.signIns1 = signIns1;
	}

	public Set<Shop> getShops() {
		return shops;
	}

	public void setShops(Set<Shop> shops) {
		this.shops = shops;
	}

	public Set<PickAccount> getAccounts() {
		return accounts;
	}

	public void setAccounts(Set<PickAccount> accounts) {
		this.accounts = accounts;
	}

	public Set<ActiveJudge> getJudges() {
		return judges;
	}

	public void setJudges(Set<ActiveJudge> judges) {
		this.judges = judges;
	}

	public Integer getTrainTimes() {
		return trainTimes;
	}

	public void setTrainTimes(Integer trainTimes) {
		this.trainTimes = trainTimes;
	}

	public Integer getTrainRate() {
		return trainRate;
	}

	public void setTrainRate(Integer trainRate) {
		this.trainRate = trainRate;
	}

	public Integer getWorkoutTimes() {
		return workoutTimes;
	}

	public void setWorkoutTimes(Integer workoutTimes) {
		this.workoutTimes = workoutTimes;
	}

	public Set<TrainRecord> getRecords() {
		return records;
	}

	public void setRecords(Set<TrainRecord> records) {
		this.records = records;
	}

	public Set<Integral> getIntegrals() {
		return integrals;
	}

	public void setIntegrals(Set<Integral> integrals) {
		this.integrals = integrals;
	}

	public Member() {

	}

	public String getCardImage() {
		return cardImage;
	}

	public void setCardImage(String cardImage) {
		this.cardImage = cardImage;
	}

	@Override
	public String getMobilephone() {
		return mobilephone;
	}

	public void setMobilephone(String mobilephone) {
		this.mobilephone = mobilephone;
	}

	public String getRegisterType() {
		return registerType;
	}

	public void setRegisterType(String registerType) {
		this.registerType = registerType;
	}

	public String getThirdType() {
		return thirdType;
	}

	public void setThirdType(String thirdType) {
		this.thirdType = thirdType;
	}

	public Member(Long id) {
		setId(id);
	}

	public Set<Apply> getApplys() {
		return applys;
	}

	public void setApplys(Set<Apply> applys) {
		this.applys = applys;
	}

	public Set<Appraise> getAppraises() {
		return appraises;
	}

	public void setAppraises(Set<Appraise> appraises) {
		this.appraises = appraises;
	}

	public Set<Appraise> getAppraises1() {
		return appraises1;
	}

	public void setAppraises1(Set<Appraise> appraises1) {
		this.appraises1 = appraises1;
	}

	public Set<AlwaysAddr> getAlwaysAddrs() {
		return alwaysAddrs;
	}

	public void setAlwaysAddrs(Set<AlwaysAddr> alwaysAddrs) {
		this.alwaysAddrs = alwaysAddrs;
	}

	public Date getEndPublishTime() {
		return endPublishTime;
	}

	public void setEndPublishTime(Date endPublishTime) {
		this.endPublishTime = endPublishTime;
	}

	@JSON(serialize = false)
	public Set<Certificate> getCertificates() {
		return certificates;
	}

	public void setCertificates(Set<Certificate> certificates) {
		this.certificates = certificates;
	}

	@JSON(serialize = false)
	public Set<CourseInfo> getCourses() {
		return courses;
	}

	public void setCourses(Set<CourseInfo> courses) {
		this.courses = courses;
	}

	public Integer getOrderCount() {
		return orderCount;
	}

	public void setOrderCount(Integer orderCount) {
		this.orderCount = orderCount;
	}

	public Integer getAvgGrade() {
		return avgGrade;
	}

	public void setAvgGrade(Integer avgGrade) {
		this.avgGrade = avgGrade;
	}

	public Integer getIntegralCount() {
		return integralCount;
	}

	public void setIntegralCount(Integer integralCount) {
		this.integralCount = integralCount;
	}

	@Override
	public Long getCoachId() {
		if (coach != null)
			coachId = coach.getId();
		return coachId;
	}

	public void setCoachId(Long coachId) {
		this.coachId = coachId;
	}

	@Override
	public String getCoachName() {
		if (coach != null)
			coachName = coach.getNick();
		return coachName;
	}

	public void setCoachName(String coachName) {
		this.coachName = coachName;
	}

	@JSON(serialize = false)
	public Set<Product> getProducts() {
		return products;
	}

	public void setProducts(Set<Product> products) {
		this.products = products;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	@Override
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	@Override
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getTell() {
		return tell;
	}

	public void setTell(String tell) {
		this.tell = tell;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public String getWechatID() {
		return wechatID;
	}

	public void setWechatID(String wechatID) {
		this.wechatID = wechatID;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	@Override
	public String getCity() {
		return city;
	}

	@Override
	public void setCity(String city) {
		this.city = city;
	}

	public String getCounty() {
		return county;
	}

	public void setCounty(String county) {
		this.county = county;
	}

	public String getPostal() {
		return postal;
	}

	public void setPostal(String postal) {
		this.postal = postal;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Override
	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getSpeciality() {
		return speciality;
	}

	public void setSpeciality(String speciality) {
		this.speciality = speciality;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public Member getCoach() {
		return coach;
	}

	public void setCoach(Member coach) {
		this.coach = coach;
		if (coach != null) {
			setCoachId(coach.getId());
			setCoachName(coach.getNick());
		}
	}

	public Set<Course> getCourseMembers() {
		return courseMembers;
	}

	public void setCourseMembers(Set<Course> courseMembers) {
		this.courseMembers = courseMembers;
	}

	public Double getRate() {
		return rate;
	}

	public void setRate(Double rate) {
		this.rate = rate;
	}

	public String getWorkDate() {
		return workDate;
	}

	public void setWorkDate(String workDate) {
		this.workDate = workDate;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public String getVerifyCode() {
		return verifyCode;
	}

	public void setVerifyCode(String verifyCode) {
		this.verifyCode = verifyCode;
	}

	public Double getCountSocure() {
		return countSocure;
	}

	public void setCountSocure(Double countSocure) {
		this.countSocure = countSocure;
	}

	public Integer getCountEmp() {
		return countEmp;
	}

	public void setCountEmp(Integer countEmp) {
		this.countEmp = countEmp;
	}

	public Date getRecommTime() {
		return recommTime;
	}

	public void setRecommTime(Date recommTime) {
		this.recommTime = recommTime;
	}

	public Date getStickTime() {
		return stickTime;
	}

	public void setStickTime(Date stickTime) {
		this.stickTime = stickTime;
	}

	@Override
	public Double getLongitude() {
		return longitude;
	}

	@Override
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	@Override
	public Double getLatitude() {
		return latitude;
	}

	@Override
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getRadius() {
		return radius;
	}

	public void setRadius(Double radius) {
		this.radius = radius;
	}

	@Override
	public String getQqId() {
		return qqId;
	}

	public void setQqId(String qqId) {
		this.qqId = qqId;
	}

	@Override
	public String getSinaId() {
		return sinaId;
	}

	public void setSinaId(String sinaId) {
		this.sinaId = sinaId;
	}

	@Override
	public Long getChannelId() {
		return channelId;
	}

	@Override
	public void setChannelId(Long channelId) {
		this.channelId = channelId;
	}

	@Override
	public String getUserId() {
		return userId;
	}

	@Override
	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Override
	public Integer getTermType() {
		return termType;
	}

	@Override
	public void setTermType(Integer termType) {
		this.termType = termType;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@JSON(serialize = false)
	public Set<Member> getCoachMembers() {
		return coachMembers;
	}

	public void setCoachMembers(Set<Member> coachMembers) {
		this.coachMembers = coachMembers;
	}

	public Set<Friend> getAllFriends() {
		return allFriends;
	}

	public void setAllFriends(Set<Friend> allFriends) {
		this.allFriends = allFriends;
	}

	@JSON(serialize = false)
	public Set<Friend> getFriends() {
		return friends;
	}

	public void setFriends(Set<Friend> friends) {
		this.friends = friends;
	}

	public String getHeadPortrait() {
		return headPortrait;
	}

	public void setHeadPortrait(String headPortrait) {
		this.headPortrait = headPortrait;
	}

	@JSON(serialize = false)
	public Set<Friend> getClubs() {
		return clubs;
	}

	public void setClubs(Set<Friend> clubs) {
		this.clubs = clubs;
	}

	@JSON(serialize = false)
	public Set<Friend> getCoachs() {
		return coachs;
	}

	public void setCoachs(Set<Friend> coachs) {
		this.coachs = coachs;
	}

	@JSON(serialize = false)
	public Set<Friend> getMembers() {
		return members;
	}

	public void setMembers(Set<Friend> members) {
		this.members = members;
	}

	@JSON(serialize = false)
	public Set<Friend> getVisitors() {
		return visitors;
	}

	public void setVisitors(Set<Friend> visitors) {
		this.visitors = visitors;
	}

	@JSON(serialize = false)
	public Setting getSetting() {
		return setting;
	}

	public void setSetting(Setting setting) {
		this.setting = setting;
	}

	@JSON(serialize = false)
	public Set<WorkTime> getTimes() {
		return times;
	}

	public void setTimes(Set<WorkTime> times) {
		this.times = times;
	}

	public Set<Product> getProds() {
		return prods;
	}

	public void setProds(Set<Product> prods) {
		this.prods = prods;
	}

	public String getHasValid() {
		return hasValid;
	}

	public void setHasValid(String hasValid) {
		this.hasValid = hasValid;
	}

	public String getMobileValid() {
		return mobileValid;
	}

	public void setMobileValid(String mobileValid) {
		this.mobileValid = mobileValid;
	}

	public String getEmailValid() {
		return emailValid;
	}

	public void setEmailValid(String emailValid) {
		this.emailValid = emailValid;
	}

	public String getCardValid() {
		return cardValid;
	}

	public void setCardValid(String cardValid) {
		this.cardValid = cardValid;
	}

	public Date getRegDate() {
		return regDate;
	}

	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}

	@Override
	public Date getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}

	@Override
	public String getToKen() {
		return toKen;
	}

	public void setToKen(String toKen) {
		this.toKen = toKen;
	}

	public Set<Active> getActives() {
		return actives;
	}

	public void setActives(Set<Active> actives) {
		this.actives = actives;
	}

	public Set<TeamMember> getTeams() {
		return teams;
	}

	public void setTeams(Set<TeamMember> teams) {
		this.teams = teams;
	}

	public Set<ActiveOrder> getAos() {
		return aos;
	}

	public void setAos(Set<ActiveOrder> aos) {
		this.aos = aos;
	}

	public Set<MemberTicket> getTickets() {
		return tickets;
	}

	public void setTickets(Set<MemberTicket> tickets) {
		this.tickets = tickets;
	}

	public Set<Tickling> getTicklings() {
		return ticklings;
	}

	public void setTicklings(Set<Tickling> ticklings) {
		this.ticklings = ticklings;
	}

	public Set<ProductClub45> getApplyClubs() {
		return applyClubs;
	}

	public void setApplyClubs(Set<ProductClub45> applyClubs) {
		this.applyClubs = applyClubs;
	}

	public Set<ProductOrder45> getOrder45s() {
		return order45s;
	}

	public void setOrder45s(Set<ProductOrder45> order45s) {
		this.order45s = order45s;
	}

	public Set<ProductOrderBalance45> getBalanceFroms() {
		return balanceFroms;
	}

	public void setBalanceFroms(Set<ProductOrderBalance45> balanceFroms) {
		this.balanceFroms = balanceFroms;
	}

	public Set<ProductOrderBalance45> getBalanceTos() {
		return balanceTos;
	}

	public void setBalanceTos(Set<ProductOrderBalance45> balanceTos) {
		this.balanceTos = balanceTos;
	}

	public static DetachedCriteria getCriteriaQuery(Member query) {
		final DetachedCriteria dc = DetachedCriteria.forClass(Member.class);
		if (query != null) {
			if (query.getName() != null && !"".equals(query.getName()))
				dc.add(Restrictions.or(Restrictions.like("name", "%" + query.getName() + "%"),
						Restrictions.like("nick", "%" + query.getName() + "%")));
			if (query.getEmail() != null && !"".equals(query.getEmail()))
				dc.add(Restrictions.like("email", "%" + query.getEmail() + "%"));
			if (query.getRole() != null && !"".equals(query.getRole()))
				dc.add(Restrictions.eq("role", query.getRole()));
			if (query.getMobilephone() != null && !"".equals(query.getMobilephone()))
				dc.add(Restrictions.like("mobilephone", "%" + query.getMobilephone() + "%"));
			if (query.getRegisterType() != null && !"".equals(query.getRegisterType()))
				dc.add(Restrictions.like("registerType", "%" + query.getRegisterType() + "%"));
			if (query.getThirdType() != null && !"".equals(query.getThirdType()))
				dc.add(Restrictions.like("thirdType", "%" + query.getThirdType() + "%"));
			if (query.getEmail() != null && !"".equals(query.getEmail()))
				dc.add(Restrictions.like("email", "%" + query.getEmail() + "%"));
		}
		// final String exclude =
		// "aos,teams,actives,accounts,shops,signIns1,signIns,addrs,orders,messages1,messages,orderDetails1,orderDetails,courseGrades,appraises1,appraises,coach,coachMembers,friends,clubs,coachs,members,visitors,setting,times,products,certificates,integrals,courses,alwaysAddrs,applys,records,coursess,courseCoachs,allFriends,prods,"
		return dc;
	}

	public static String getMemberSql(String county, String speciality, String course, String mode, String style,
			String sex, String currentCity) {
		StringBuffer sql = new StringBuffer();
		sql.append(
				"SELECT * ,CASE WHEN c.appraise_num IS NULL THEN 0 ELSE c.appraise_num END countEmp,CASE WHEN c.avg_grade IS NULL THEN 0 ELSE ROUND(c.avg_grade) END member_grade,CASE WHEN d.num IS NULL THEN 0 ELSE d.num END salesNum FROM tb_member m ");
		sql.append(" LEFT JOIN (");
		sql.append(
				" SELECT b.member member1, SUM(a.grade), COUNT(*) appraise_num, SUM(a.grade) / COUNT(*) avg_grade FROM tb_member_appraise a LEFT JOIN (");
		sql.append(
				" SELECT ao.id, a.member, '1' order_type, a.NAME FROM tb_product_order ao LEFT JOIN tb_product a ON ao.product = a.id");
		sql.append(
				" UNION ALL SELECT bo.id, b.creator member,'2' order_type, b.NAME FROM tb_active_order bo LEFT JOIN tb_active b ON bo.active = b.id");
		sql.append(
				" UNION ALL SELECT co.id, c.member, '3' order_type, c.plan_name FROM tb_planrelease_order co LEFT JOIN tb_plan_release c  ON co.planrelease = c.id");
		sql.append(
				" UNION ALL SELECT cdo.id, d2.club member, '4' order_type, d2.name FROM tb_factory_order cdo LEFT JOIN tb_member_factory_costs d1 ON cdo.factoryCosts = d1.id LEFT JOIN tb_member_factory d2 ON d1.factory = d2.id");
		sql.append(
				" UNION ALL SELECT eo.id, e2.member, '5' order_type, e2.name FROM tb_courserelease_order eo LEFT JOIN tb_course e1 ON eo.course = e1.id LEFT JOIN tb_course_info e2 ON e1.courseId = e2.id ");
		sql.append(
				" UNION ALL SELECT fo.id, f.member, '6' order_type, f.NAME FROM tb_goods_order fo LEFT JOIN tb_goods f ON fo.goods=f.id");
		sql.append(
				" ) b ON a.order_id = b.id AND a.order_type = b.order_type group by b.member) c ON c.member1 = m.id");
		sql.append(" LEFT JOIN(");
		sql.append(" SELECT b.member member2, SUM(b.num) num  FROM (");
		sql.append(
				" SELECT pro.member member, COUNT(*) num FROM tb_product_order proo LEFT JOIN tb_product pro ON proo.product = pro.id GROUP BY pro.member");
		sql.append(
				" UNION ALL SELECT act.creator member, COUNT(*) num FROM tb_active_order acto LEFT JOIN tb_active act ON acto.active = act.id GROUP BY act.creator");
		sql.append(
				" UNION ALL SELECT goods.member member, COUNT(*) num FROM tb_goods_order goodo LEFT JOIN tb_goods goods ON goodo.goods = goods.id GROUP BY goods.member");
		sql.append(
				" UNION ALL SELECT plan.member member, COUNT(*) num FROM tb_planrelease_order plano LEFT JOIN tb_plan_release plan ON plano.planrelease = plan.id GROUP BY plan.member");
		sql.append(
				" UNION ALL SELECT fac.club member, COUNT(*) num FROM tb_factory_order faco LEFT JOIN tb_member_factory_costs mfo ON faco.factorycosts = mfo.id LEFT JOIN tb_member_factory fac ON mfo.factory = fac.id GROUP BY fac.club");
		sql.append(
				" UNION ALL SELECT coui.member member, COUNT(*) num FROM tb_courserelease_order couo LEFT JOIN tb_course cou ON couo.course = cou.id LEFT JOIN tb_course_info coui ON cou.courseId = coui.id GROUP BY coui.member");
		sql.append(" ) b GROUP BY b.member) d ON d.member2 = m.id");
		sql.append(
				" LEFT JOIN (SELECT course.member member3 ,GROUP_CONCAT(DISTINCT para.name  SEPARATOR ',') courses FROM tb_course_info course LEFT JOIN tb_parameter para ON para.id = course.type GROUP BY course.member");
		sql.append(" ) e ON e.member3 = m.id");
		sql.append(
				" LEFT JOIN (SELECT cert.member member4 ,GROUP_CONCAT(DISTINCT cert.name  SEPARATOR ',') certificates FROM TB_MEMBER_CERTIFICATE cert GROUP BY cert.member");
		sql.append(" ) f ON f.member4 = m.id ");
		sql.append(" where m.grade = '1'");
		if (county != null && !"".equals(county)) {
			sql.append(" and m.county = '").append(county).append("'");
		}
		if (currentCity != null && !"".equals(currentCity)) {
			sql.append(" and m.city = '").append(currentCity).append("'");
		}
		if (speciality != null && !"".equals(speciality)) {
			sql.append(" and  LOCATE('").append(speciality).append("',m.speciality) != 0  ");
		}
		if (course != null && !"".equals(course)) {
			sql.append(" and m.id in (select member from tb_course_info where type = '").append(course).append("')");
		}
		if (mode != null && !"".equals(mode)) {
			sql.append(" and m.mode = '").append(mode).append("'");
		}
		if (style != null && !"".equals(style)) {
			sql.append(" and  LOCATE('").append(style).append("',m.style) != 0  ");
		}
		if (sex != null && !"".equals(sex)) {
			sql.append(" and m.sex = '").append(sex).append("'");
		}
		return sql.toString();
	}
	
	
	
	public static String Sql4US(String county, String speciality, String course, String mode, String style,
			String sex, String currentCity) {
		StringBuffer sql = new StringBuffer();
		sql.append(
				"SELECT * ,CASE WHEN c.appraise_num IS NULL THEN 0 ELSE c.appraise_num END countEmp,CASE WHEN c.avg_grade IS NULL THEN 0 ELSE ROUND(c.avg_grade) END member_grade,CASE WHEN d.num IS NULL THEN 0 ELSE d.num END salesNum FROM tb_member m ");
		sql.append(" LEFT JOIN (");
		sql.append(
				" SELECT b.member member1, SUM(a.grade), COUNT(*) appraise_num, SUM(a.grade) / COUNT(*) avg_grade FROM tb_member_appraise a LEFT JOIN (");
		sql.append(
				" SELECT ao.id, a.member, '1' order_type, a.NAME FROM tb_product_order ao LEFT JOIN tb_product a ON ao.product = a.id");
		sql.append(
				" UNION ALL SELECT bo.id, b.creator member,'2' order_type, b.NAME FROM tb_active_order bo LEFT JOIN tb_active b ON bo.active = b.id");
		sql.append(
				" UNION ALL SELECT co.id, c.member, '3' order_type, c.plan_name FROM tb_planrelease_order co LEFT JOIN tb_plan_release c  ON co.planrelease = c.id");
		sql.append(
				" UNION ALL SELECT cdo.id, d2.club member, '4' order_type, d2.name FROM tb_factory_order cdo LEFT JOIN tb_member_factory_costs d1 ON cdo.factoryCosts = d1.id LEFT JOIN tb_member_factory d2 ON d1.factory = d2.id");
		sql.append(
				" UNION ALL SELECT eo.id, e2.member, '5' order_type, e2.name FROM tb_courserelease_order eo LEFT JOIN tb_course e1 ON eo.course = e1.id LEFT JOIN tb_course_info e2 ON e1.courseId = e2.id ");
		sql.append(
				" UNION ALL SELECT fo.id, f.member, '6' order_type, f.NAME FROM tb_goods_order fo LEFT JOIN tb_goods f ON fo.goods=f.id");
		sql.append(
				" ) b ON a.order_id = b.id AND a.order_type = b.order_type group by b.member) c ON c.member1 = m.id");
		sql.append(" LEFT JOIN(");
		sql.append(" SELECT b.member member2, SUM(b.num) num  FROM (");
		sql.append(
				" SELECT pro.member member, COUNT(*) num FROM tb_product_order proo LEFT JOIN tb_product pro ON proo.product = pro.id GROUP BY pro.member");
		sql.append(
				" UNION ALL SELECT act.creator member, COUNT(*) num FROM tb_active_order acto LEFT JOIN tb_active act ON acto.active = act.id GROUP BY act.creator");
		sql.append(
				" UNION ALL SELECT goods.member member, COUNT(*) num FROM tb_goods_order goodo LEFT JOIN tb_goods goods ON goodo.goods = goods.id GROUP BY goods.member");
		sql.append(
				" UNION ALL SELECT plan.member member, COUNT(*) num FROM tb_planrelease_order plano LEFT JOIN tb_plan_release plan ON plano.planrelease = plan.id GROUP BY plan.member");
		sql.append(
				" UNION ALL SELECT fac.club member, COUNT(*) num FROM tb_factory_order faco LEFT JOIN tb_member_factory_costs mfo ON faco.factorycosts = mfo.id LEFT JOIN tb_member_factory fac ON mfo.factory = fac.id GROUP BY fac.club");
		sql.append(
				" UNION ALL SELECT coui.member member, COUNT(*) num FROM tb_courserelease_order couo LEFT JOIN tb_course cou ON couo.course = cou.id LEFT JOIN tb_course_info coui ON cou.courseId = coui.id GROUP BY coui.member");
		sql.append(" ) b GROUP BY b.member) d ON d.member2 = m.id");
		sql.append(
				" LEFT JOIN (SELECT course.member member3 ,GROUP_CONCAT(DISTINCT para.name  SEPARATOR ',') courses FROM tb_course_info course LEFT JOIN tb_parameter para ON para.id = course.type GROUP BY course.member");
		sql.append(" ) e ON e.member3 = m.id");
		sql.append(
				" LEFT JOIN (SELECT cert.member member4 ,GROUP_CONCAT(DISTINCT cert.name  SEPARATOR ',') certificates FROM TB_MEMBER_CERTIFICATE cert GROUP BY cert.member");
		sql.append(" ) f ON f.member4 = m.id ");
		sql.append(" where ( m.grade = '1'");
		if (county != null && !"".equals(county)) {
			sql.append(" and m.county = '").append(county).append("'");
		}
		if (currentCity != null) {
			sql.append(" and m.city = '").append(currentCity).append("'");
		}
		if (speciality != null && !"".equals(speciality)) {
			sql.append(" and  LOCATE('").append(speciality).append("',m.speciality) != 0  ");
		}
		if (course != null && !"".equals(course)) {
			sql.append(" and m.id in (select member from tb_course_info where type = '").append(course).append("')");
		}
		if (mode != null && !"".equals(mode)) {
			sql.append(" and m.mode = '").append(mode).append("'");
		}
		if (style != null && !"".equals(style)) {
			sql.append(" and  LOCATE('").append(style).append("',m.style) != 0  ");
		}
		if (sex != null && !"".equals(sex)) {
			sql.append(" and m.sex = '").append(sex).append("'");
		}
		return sql.toString();
	}

	public static String getDoyenSql() {
		String sql = "SELECT r.partake, COUNT(*) workoutTimes,m.name,m.role,m.image FROM tb_plan_record r LEFT JOIN tb_member m ON r.partake = m.id GROUP BY r.partake ORDER BY COUNT(*) DESC LIMIT 4 ";
		return sql;
	}

	public static String getRanks() {
		String sql = "SELECT r.partake, COUNT(*) workoutTimes,m.name,m.role,m.image FROM tb_plan_record r LEFT JOIN tb_member m ON r.partake = m.id GROUP BY r.partake ORDER BY COUNT(*) DESC LIMIT 12 ";
		return sql;
	}

	@Override
	public String getTableName() {
		return null;
	}

	/**
	 * 把member转换为json
	 */
	public JSONObject toJson() {
		JSONObject o = new JSONObject();
		o.accumulate("id", this.getId()).accumulate("name", this.getName()).accumulate("city", this.getCity())
				.accumulate("county", this.getCounty()).accumulate("sex", this.getSex())
				.accumulate("province", this.getProvince()).accumulate("nick",this.getNick())
				.accumulate("image", this.getImage()).accumulate("role", this.getRole()).accumulate("lng", this.getLongitude())
				.accumulate("lat", this.getLatitude()).accumulate("mobilephone", this.getMobilephone())
				.accumulate("mobileValid", this.getMobileValid()).accumulate("count", this.getWorkoutTimes());
		return o;
	}

	@Override
	public String toString() {
		return "Member [email=" + email + ", nick=" + nick + ", name=" + name + ", sex=" + sex + ", password="
				+ password + ", birthday=" + birthday + ", cardImage=" + cardImage + ", mobilephone=" + mobilephone
				+ ", registerType=" + registerType + ", thirdType=" + thirdType + ", tell=" + tell + ", qq=" + qq
				+ ", wechatID=" + wechatID + ", province=" + province + ", city=" + city + ", county=" + county
				+ ", postal=" + postal + ", address=" + address + ", role=" + role + ", image=" + image
				+ ", headPortrait=" + headPortrait + ", coach=" + coach + ", speciality=" + speciality + ", mode="
				+ mode + ", style=" + style + ", rate=" + rate + ", workDate=" + workDate + ", grade=" + grade
				+ ", verifyCode=" + verifyCode + ", countSocure=" + countSocure + ", countEmp=" + countEmp
				+ ", recommTime=" + recommTime + ", description=" + description + ", stickTime=" + stickTime
				+ ", avgGrade=" + avgGrade + ", orderCount=" + orderCount + ", endPublishTime=" + endPublishTime
				+ ", integralCount=" + integralCount + ", workoutTimes=" + workoutTimes + ", trainTimes=" + trainTimes
				+ ", trainRate=" + trainRate + ", longitude=" + longitude + ", latitude=" + latitude + ", radius="
				+ radius + ", qqId=" + qqId + ", sinaId=" + sinaId + ", channelId=" + channelId + ", userId=" + userId
				+ ", termType=" + termType + ", coachId=" + coachId + ", coachName=" + coachName + ", hasValid="
				+ hasValid + ", mobileValid=" + mobileValid + ", emailValid=" + emailValid + ", cardValid=" + cardValid
				+ ", regDate=" + regDate + ", loginTime=" + loginTime + ", toKen=" + toKen + ", coachMembers="
				+ coachMembers + ", friends=" + friends + ", clubs=" + clubs + ", coachs=" + coachs + ", members="
				+ members + ", visitors=" + visitors + ", setting=" + setting + ", tickets=" + tickets + ", times="
				+ times + ", products=" + products + ", certificates=" + certificates + ", integrals=" + integrals
				+ ", courses=" + courses + ", alwaysAddrs=" + alwaysAddrs + ", applys=" + applys + ", records="
				+ records + ", coursess=" + coursess + ", courseCoachs=" + courseCoachs + ", courseMembers="
				+ courseMembers + ", allFriends=" + allFriends + ", prods=" + prods + ", appraises=" + appraises
				+ ", appraises1=" + appraises1 + ", orderDetails=" + orderDetails + ", orderDetails1=" + orderDetails1
				+ ", messages=" + messages + ", messages1=" + messages1 + ", orders=" + orders + ", addrs=" + addrs
				+ ", signIns=" + signIns + ", signIns1=" + signIns1 + ", shops=" + shops + ", accounts=" + accounts
				+ ", judges=" + judges + ", actives=" + actives + ", teams=" + teams + ", aos=" + aos + ", ticklings="
				+ ticklings + ", applyClubs=" + applyClubs + ", order45s=" + order45s + ", balanceFroms=" + balanceFroms
				+ ", balanceTos=" + balanceTos + ", agencyId=" + agencyId + ", agencyName=" + agencyName + "]";
	}
}
