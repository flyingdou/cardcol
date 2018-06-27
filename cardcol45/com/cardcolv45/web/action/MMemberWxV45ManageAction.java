package com.cardcolv45.web.action;

import java.io.File;
import java.net.URLDecoder;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.http.ProtocolType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.aliyuncs.push.model.v20160801.PushRequest;
import com.aliyuncs.push.model.v20160801.PushResponse;
import com.freegym.web.active.Active;
import com.freegym.web.active.TrainRecord;
import com.freegym.web.basic.Body;
import com.freegym.web.basic.Certificate;
import com.freegym.web.basic.Member;
import com.freegym.web.config.CourseInfo;
import com.freegym.web.config.Factory;
import com.freegym.web.config.Setting;
import com.freegym.web.course.Appraise;
import com.freegym.web.course.Message;
import com.freegym.web.course.SignIn;
import com.freegym.web.mobile.BasicJsonAction;
import com.freegym.web.order.ActiveOrder;
import com.freegym.web.order.CourseOrder;
import com.freegym.web.order.FactoryOrder;
import com.freegym.web.order.Goods;
import com.freegym.web.order.GoodsOrder;
import com.freegym.web.order.Order;
import com.freegym.web.order.PlanOrder;
import com.freegym.web.order.PresentHeartRate;
import com.freegym.web.order.Product;
import com.freegym.web.order.ProductOrder;
import com.freegym.web.plan.Course;
import com.freegym.web.plan.PlanRelease;
import com.freegym.web.plan.gen.IPlanGenerator;
import com.freegym.web.plan.gen.impl.SystemGeneratorImpl;
import com.freegym.web.system.Area1;
import com.freegym.web.utils.BodyUtils;
import com.freegym.web.utils.DBConstant;
import com.freegym.web.utils.JSONUtils;
import com.sanmen.web.core.bean.BaseMember;
import com.sanmen.web.core.common.LogicException;
import com.sanmen.web.core.system.Parameter;
import com.sanmen.web.core.utils.LnglatUtil;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.domain.OpenImUser;
import com.taobao.api.request.OpenimCustmsgPushRequest;
import com.taobao.api.request.OpenimCustmsgPushRequest.CustMsg;
import com.taobao.api.request.OpenimTribeInviteRequest;
import com.taobao.api.response.OpenimCustmsgPushResponse;
import com.taobao.api.response.OpenimTribeInviteResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Namespace("")
@InterceptorRefs({ @InterceptorRef("customStack") })
@Results({ 
	 @Result(name = "privateMealPackage", location = "/wxv45/private_meal_package.jsp"),// 私教套餐
	 @Result(name = "privateMealPackageDetail", location = "/wxv45/package_detail.jsp"),// 私教套餐详情
	 @Result(name = "buyPackage", location = "/wxv45/orderinfo1.jsp")// 购买私教套餐
})
public class MMemberWxV45ManageAction extends BasicJsonAction {

	private static final long serialVersionUID = 930483075883915954L;

	private static final SimpleDateFormat ymd = new SimpleDateFormat("yyyy-MM-dd");

	protected String judageArray;

	public String getJudageArray() {
		return judageArray;
	}

	public void setJudageArray(String judageArray) {
		this.judageArray = judageArray;
	}

	protected String member;
	protected String traindate;
	/**
	 * 坐标城市
	 */
	protected String city;

	protected String refereeType;

	/**
	 * 筛选条件
	 */
	protected String county; // 地区
	protected String proType; // 健身卡种类
	protected String typeId; // 服务项目
	protected String speciality; // 专长
	protected String mode; // 类型
	protected Boolean pageType = false; // true:不分页 false:分页
	protected Integer workoutTimes;
	protected String queryCriteria;

	/**
	 * 搜索关键字,登录账户，登录密码
	 */
	protected String keyword;

	private String username;

	private String password;

	/**
	 * 签到密码
	 */
	private String signPass;

	/**
	 * 经度，纬度，消费金额（主要指储值卡金额）
	 */
	private Double lng, lat, money;

	/**
	 * 订单ID号
	 */
	private Long orderId;

	/**
	 * 签到日间
	 */
	private Date signDate;

	private File imageSide, imageBack;

	private String imageSideFileName, imageBackFileName;

	private Long userId;

	/**
	 * 被签到用户ID
	 */
	private Long AuditId;
	/**
	 * 1:卡库健身计划 2：王严健身计划
	 */
	private Integer type;

	/**
	 * 分析日期
	 */
	private Date analyDate;

	/**
	 * 体态分析结论
	 */
	private String conclusion;

	/**
	 * 群添加用户的用户nick,群id
	 */
	private String nick;

	private Long tribeid;

	private String address;
	
	private String packageId;// 私教套餐 id
	
	public String getPackageId() {
		return packageId;
	}

	public void setPackageId(String packageId) {
		this.packageId = packageId;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getNick() {
		return nick;
	}

	public String getnick() {
		return nick;
	}

	public void setNick(String nick) {
		if (nick != null && !"".equals(nick)) nick = urlDecode(nick);
		this.nick = nick;
	}

	public Long getTribeid() {
		return tribeid;
	}

	public void setTribeid(Long tribeid) {
		this.tribeid = tribeid;
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

	public String getProType() {
		return proType;
	}

	public void setProType(String proType) {
		this.proType = proType;
	}

	public String getTypeId() {
		return typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
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

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		if (keyword != null && !"".equals(keyword)) keyword = urlDecode(keyword);
		this.keyword = keyword;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		if (username != null && !"".equals(username)) username = urlDecode(username);
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSignPass() {
		return signPass;
	}

	public void setSignPass(String signPass) {
		this.signPass = signPass;
	}

	public Double getLng() {
		return lng;
	}

	public void setLng(Double lng) {
		this.lng = lng;
	}

	public Double getLat() {
		return lat;
	}

	public void setLat(Double lat) {
		this.lat = lat;
	}

	public Double getMoney() {
		return money;
	}

	public void setMoney(Double money) {
		this.money = money;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Date getSignDate() {
		return signDate;
	}

	public File getImageSide() {
		return imageSide;
	}

	public File getImageBack() {
		return imageBack;
	}

	public void setImageSide(File imageSide) {
		this.imageSide = imageSide;
	}

	public void setImageBack(File imageBack) {
		this.imageBack = imageBack;
	}

	public void setSignDate(Date signDate) {
		this.signDate = signDate;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Date getAnalyDate() {
		return analyDate;
	}

	public void setAnalyDate(Date analyDate) {
		this.analyDate = analyDate;
	}

	public String getConclusion() {
		return conclusion;
	}

	public void setConclusion(String conclusion) {
		this.conclusion = conclusion;
	}

	public String getImageSideFileName() {
		return imageSideFileName;
	}

	public void setImageSideFileName(String imageSideFileName) {
		this.imageSideFileName = imageSideFileName;
	}

	public String getImageBackFileName() {
		return imageBackFileName;
	}

	public void setImageBackFileName(String imageBackFileName) {
		this.imageBackFileName = imageBackFileName;
	}

	public Boolean getPageType() {
		return pageType;
	}

	public void setPageType(Boolean pageType) {
		this.pageType = pageType;
	}

	public Long getAuditId() {
		return AuditId;
	}

	public void setAuditId(Long auditId) {
		AuditId = auditId;
	}

	public String getRefereeType() {
		return refereeType;
	}

	public void setRefereeType(String refereeType) {
		this.refereeType = refereeType;
	}

	/**
	 * 查找当前坐标点的城市所有俱乐部
	 */
	public void findClubs() {
		final DetachedCriteria dc = DetachedCriteria.forClass(Member.class);
		dc.add(Restrictions.eq("role", "E"));
		dc.add(Restrictions.eq("grade", "1"));
		if (keyword != null && !"".equals(keyword)) {
			final String where = "%" + keyword + "%";
			dc.add(this.or(Restrictions.like("name", where), Restrictions.like("nick", where), Restrictions.like("email", where), Restrictions.like("mobilephone", where)));
		} else {
			if (city != null && !"".equals(city)) dc.add(Restrictions.eq("city", city));

			if (county != null && !"".equals("county")) {
				dc.add(Restrictions.eq("county", county));
			}

			if (typeId != null && !"".equals(typeId)) {

				String[] typeIdArr = typeId.split(",");
				StringBuffer sql = new StringBuffer("((this_.id in (select member from tb_course_info where (");
				for (int i = 0; i < typeIdArr.length; i++) {
					if (i != typeIdArr.length - 1) {
						sql.append(" type = " + typeIdArr[i] + " or ");
					} else {
						sql.append(" type = " + typeIdArr[i]);
					}
				}
				sql.append("))) or (this_.id in (select club from tb_member_factory where ( ");
				for (int i = 0; i < typeIdArr.length; i++) {
					if (i != typeIdArr.length - 1) {
						sql.append(" project = " + typeIdArr[i] + " or ");
					} else {
						sql.append(" project = " + typeIdArr[i]);
					}
				}
				sql.append("))))");
				dc.add(Restrictions.sqlRestriction(sql.toString()));
				/*
				 * dc.add(Restrictions.sqlRestriction(
				 * "((this_.id in (select member from tb_course_info where type ="
				 * + typeId + ")) or " +
				 * " (this_.id in (select club from tb_member_factory where project = "
				 * + typeId + "))) "));
				 */
			}

			if (proType != null && !"".equals(proType)) {
				String[] proTypeArr = proType.split(",");
				StringBuffer sql = new StringBuffer("this_.id in (select distinct member from tb_product where (");
				for (int i = 0; i < proTypeArr.length; i++) {
					if (i != proTypeArr.length - 1) {
						sql.append(" proType = " + proTypeArr[i] + " or ");
					} else {
						sql.append(" proType = " + proTypeArr[i]);
					}
				}
				sql.append("))");
				dc.add(Restrictions.sqlRestriction(sql.toString()));
			}
		}

		pageInfo.setOrder("stickTime");
		pageInfo.setOrderFlag("desc");

		if (pageType) {
			pageInfo = service.findReportByCriteria(dc, pageInfo);
			final JSONArray jarr = new JSONArray();
			for (final Iterator<?> it = pageInfo.getItems().iterator(); it.hasNext();) {
				final Member m = (Member) it.next();
				final JSONObject obj = getAllData(m);
				jarr.add(obj);
			}
			final JSONObject ret = new JSONObject();
			ret.accumulate("success", true).accumulate("pageInfo", getJsonForPageInfo()).accumulate("items", jarr);
			response(ret);
		} else {
			pageInfo = service.findPageByCriteria(dc, pageInfo);
			final JSONArray jarr = new JSONArray();
			for (final Iterator<?> it = pageInfo.getItems().iterator(); it.hasNext();) {
				final Member m = (Member) it.next();
				final JSONObject obj = getMemberJson(m);
				obj.accumulate("tell", getString(m.getTell())).accumulate("address", getString(m.getAddress()))
						// 计算俱乐部评分 评价人数
						.accumulate("appraise", getGradeJson(m));
				jarr.add(obj);
			}
			final JSONObject ret = new JSONObject();
			ret.accumulate("success", true).accumulate("pageInfo", getJsonForPageInfo()).accumulate("items", jarr);
			response(ret);
		}
	}

	/**
	 * 查找当前选中的俱乐部的首页信息
	 */
	// public void findClubHome() {
	// try {
	// final Member club = (Member) service.load(Member.class, id);
	// final JSONObject ret = new JSONObject(), mObj = getMemberJson(club);
	// // mObj.accumulate("avgGrade", getInteger(club.getAvgGrade()))
	// //计算俱乐部评分 评价人数
	// mObj.accumulate("appraise", getGradeJson(club));
	// ret.accumulate("success", true).accumulate("item", mObj);
	// response(ret);
	// } catch (Exception e) {
	// log.error("error", e);
	// response(e);
	// }
	// }

	@SuppressWarnings("unchecked")
	public void listStarCoach() {
		final JSONArray jarr = new JSONArray();
		List<Map<String, Object>> list = (List<Map<String, Object>>) service.findRecommendBySectorCode(DBConstant.RECOMM_SECTOR_SLIDES_C, city, speciality);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if (!list.isEmpty()) {
			for (Map<String, Object> obj : list) {
				final JSONObject ob = new JSONObject();
				ob.accumulate("id", obj.get("id").toString()).accumulate("speciality", obj.get("speciality").toString()).accumulate("color", obj.get("color").toString())
						.accumulate("recomm_date", sdf.format(obj.get("recomm_date"))).accumulate("recomm_id", obj.get("recomm_id").toString())
						.accumulate("recomm_type", obj.get("recomm_type").toString()).accumulate("sector", obj.get("sector").toString())
						.accumulate("stick_time", sdf.format(obj.get("stick_time"))).accumulate("summary", obj.get("summary").toString())
						.accumulate("title", obj.get("title").toString()).accumulate("icon", obj.get("icon").toString()).accumulate("link", obj.get("link").toString())
						.accumulate("href", obj.get("href").toString());
				jarr.add(ob);
			}
		}
		final JSONObject ret = new JSONObject();
		ret.accumulate("success", true).accumulate("items", jarr);
		response(ret);
	}

	@SuppressWarnings("unchecked")
	public void findCoach() {
		try {
			final DetachedCriteria dc = Member.getCriteriaQuery(null);
			dc.add(Restrictions.eq("role", "S"));
			dc.add(Restrictions.eq("grade", "1"));

			if (keyword != null && !"".equals(keyword)) {
				final String where = "%" + keyword + "%";
				dc.add(this.or(Restrictions.like("name", where), Restrictions.like("nick", where), Restrictions.like("email", where), Restrictions.like("mobilephone", where)));
			} else {
				if (city != null && !"".equals(city)) dc.add(Restrictions.like("city", "%" + city + "%"));

				if (county != null && !"".endsWith("county")) {
					dc.add(Restrictions.eq("county", county));
				}

				if (typeId != null && !"".equals(typeId)) {
					String[] typeIdArr = typeId.split(",");
					StringBuffer sq = new StringBuffer("this_.id in (select c.member from TB_COURSE c " + "inner join TB_COURSE_INFO ci on c.courseid = ci.id where 1=1 and (");
					for (int i = 0; i < typeIdArr.length; i++) {
						if (i != typeIdArr.length - 1) {
							sq.append(" ci.type = " + typeIdArr[i] + " or ");
						} else {
							sq.append(" ci.type = " + typeIdArr[i]);
						}

					}
					sq.append("))");
					dc.add(Restrictions.sqlRestriction(sq.toString()));
				}

				if (speciality != null && !"".equals(speciality)) {
					String[] speArr = speciality.split(",");
					dc.add(Restrictions.in("speciality", speArr));
				}

				if (mode != null && !"".equals(mode)) {
					String[] modeArr = mode.split(",");
					dc.add(Restrictions.in("mode", modeArr));
				}
			}
			pageInfo.setOrder("stickTime");
			pageInfo.setOrderFlag("desc");
			if (pageType) {
				pageInfo = service.findReportByCriteria(dc, pageInfo);
				final JSONArray jarr = new JSONArray();

				final List<Object> arr = pageInfo.getItems();
				for (int i = 0; i < arr.size(); i++) {
					final Member m = (Member) arr.get(i);
					Double distance = 0.0;
					if (m.getLongitude() != null && m.getLatitude() != null) {
						distance = LnglatUtil.GetDistance(lng, lat, m.getLongitude(), m.getLatitude());
					}

					for (int j = i + 1; j < arr.size(); j++) {
						final Member m2 = (Member) arr.get(j);
						Double distance2 = 0.0;
						if (m2.getLongitude() != null && m2.getLatitude() != null) {
							distance2 = LnglatUtil.GetDistance(lng, lat, m2.getLongitude(), m2.getLatitude());
						}
						if (distance >= distance2) {
							Member ms = (Member) arr.get(i);
							arr.set(i, arr.get(j));
							arr.set(j, ms);
						}
					}
				}
				pageInfo.setItems(arr);
				for (final Iterator<?> it = pageInfo.getItems().iterator(); it.hasNext();) {
					final Member m = (Member) it.next();
					// 拿当前登录者的经纬度与教练经纬度算得距离
					Double distance = (m.getLongitude() == null || m.getLatitude() == null) ? 0.0 : LnglatUtil.GetDistance(lng, lat, m.getLongitude(), m.getLatitude());
					final JSONObject obj = getAllData(m);
					obj.accumulate("distance", distance);
					jarr.add(obj);
				}
				final JSONObject ret = new JSONObject();
				ret.accumulate("success", true).accumulate("pageInfo", getJsonForPageInfo()).accumulate("items", jarr);
				response(ret);
			} else {
				pageInfo = service.findPageByCriteria(dc, pageInfo);
				final List<Object> arr = pageInfo.getItems();
				for (int i = 0; i < arr.size(); i++) {
					final Member m = (Member) arr.get(i);
					Double distance = 0.0;
					if (m.getLongitude() != null && m.getLatitude() != null) {
						distance = LnglatUtil.GetDistance(lng, lat, m.getLongitude(), m.getLatitude());
					}

					for (int j = i + 1; j < arr.size(); j++) {
						final Member m2 = (Member) arr.get(j);
						Double distance2 = 0.0;
						if (m2.getLongitude() != null && m2.getLatitude() != null) {
							distance2 = LnglatUtil.GetDistance(lng, lat, m2.getLongitude(), m2.getLatitude());
						}
						if (distance >= distance2) {
							Member ms = (Member) arr.get(i);
							arr.set(i, arr.get(j));
							arr.set(j, ms);
						}
					}
				}
				pageInfo.setItems(arr);
				final JSONArray jarr = new JSONArray();
				for (final Iterator<?> it = pageInfo.getItems().iterator(); it.hasNext();) {
					final Member m = (Member) it.next();
					// 拿当前登录者的经纬度与教练经纬度算得距离
					Double distance = (m.getLongitude() == null || m.getLatitude() == null) ? 0.0 : LnglatUtil.GetDistance(lng, lat, m.getLongitude(), m.getLatitude());
					final JSONObject obj = getMemberJson(m).accumulate("description", m.getDescription());

					final List<?> list = service.findObjectBySql("from Certificate c where c.member = ? ", m.getId());

					final JSONArray jarr1 = new JSONArray();
					for (final Iterator<?> ct = list.iterator(); ct.hasNext();) {
						final JSONObject json1 = new JSONObject();
						final Certificate ce = (Certificate) ct.next();
						json1.accumulate("name", ce.getName());
						jarr1.add(json1);
					}

					String modeName = null;
					DecimalFormat df = new DecimalFormat("0.00");
					if (getString(m.getMode()).equals("A")) {
						modeName = "私人教练";
					} else if (getString(m.getMode()).equals("B")) {
						modeName = "团体教练";
					} else if (getString(m.getMode()).equals("A, B")) {
						modeName = "私人教练　　 团体教练";
					}
					// 计算教练 评分 评价人数
					obj.accumulate("appraise", getGradeJson(m))
							// 返回 教练类型 教练所教课程 A私人教练，B团体教练
							.accumulate("mode", modeName).accumulate("certificate", jarr1).accumulate("distance", df.format(distance / 1000));
					jarr.add(obj);
				}
				// for (int i = 0; i <jarr.size(); i++) {
				// final Map<String,Object> m1 = (Map<String, Object>)
				// jarr.get(i);
				// Double distance1 = 0.0;
				// String s=(String) m1.get("lng");
				// System.out.println(s);
				// System.out.println(Double.parseDouble(m1.get("lng").toString()));
				// if (m1.get("lng") != null && m1.get("lat") != null) {
				// distance1 = LnglatUtil.GetDistance(lng, lat,
				// Double.parseDouble(m1.get("lng").toString()),
				// Double.parseDouble(m1.get("lat").toString()));
				//
				// for (int j = i + 1; j < jarr.size(); j++) {
				// final Map<String,Object> m2 = (Map<String, Object>)
				// jarr.get(i);
				// Double distance2 = 0.0;
				// if (m2.get("lng") != null && m2.get("lat") != null) {
				// distance2 = LnglatUtil.GetDistance(lng, lat,
				// Double.parseDouble(m2.get("lng").toString()),
				// Double.parseDouble(m2.get("lat").toString()));
				// }
				// if (distance1 >= distance2) {
				// Member ms = (Member) jarr.get(i);
				// jarr.set(i, jarr.get(j));
				// jarr.set(j, ms);
				// }
				// }
				// }
				// }
				final JSONObject ret = new JSONObject();
				ret.accumulate("success", true).accumulate("pageInfo", getJsonForPageInfo()).accumulate("items", jarr);
				response(ret);
			}

		} catch (Exception e) {
			log.error("error", e);
			response(e);
		}
	}

	// /**
	// * 查找某教练首页数据
	// */
	// public void findCoachHome() {
	//
	// final Member member = (Member) service.load(Member.class, id);
	// final JSONObject obj = getMemberJson(member);
	// obj.accumulate("success", true).accumulate("type",
	// member.getRole()).accumulate("message", uuid)
	// //计算教练 评分 评价人数
	// .accumulate("appraise", getGradeJson(member));
	// response(obj);
	// }

	/**
	 * 裁判类型 条件referee
	 */
	public void ShowPlanDate() {
		try {
			final JSONObject obj = new JSONObject();
			final JSONArray jarrDate = new JSONArray();
			obj.accumulate("message", "OK");
			if (type == 1) {

				// planDate
				List<?> planDate = service.findObjectBySql("select distinct r.doneDate from TrainRecord r where r.partake.id =" + member + " order by r.doneDate desc");
				if (!planDate.isEmpty()) {
					for (Object o : planDate) {
						String date = o.toString();
						final JSONObject ob = new JSONObject();
						ob.accumulate("planDate", date.substring(0, 10));
						jarrDate.add(ob);
					}
				} else {
					obj.accumulate("message", "查无资料");
				}
			}
			obj.accumulate("success", true).accumulate("items", jarrDate);
			response(obj);
		} catch (Exception e) {
			log.error("error", e);
			response(e);
		}
	}

	/**
	 * 发送短信
	 */
	@SuppressWarnings("unused")
	public void notifyTheReferee() {
		try {
			final String mobile = request.getParameter("mobile");
			final String challengeName = request.getParameter("challengeName");

			if (mobile == null) {
				throw new LogicException("当前手机号码未绑定至用户！");
			} else {
				final Member m = (Member) service.load(Member.class, getMobileUser().getId());
				final String msg = sendSmsMessage(mobile, "mobile.validate.open", "｛" + m.getNick() + "｝参加了｛" + challengeName + "｝，已经指定您为他的成绩裁判。请您进入卡库健身的消息中心审核运动成绩");
				m.setMobilephone(mobile);
				m.setMobileValid("0");
				service.saveOrUpdate(m);
			}
			final JSONObject ret = new JSONObject();
			ret.accumulate("success", true).accumulate("message", "OK");
			response(ret);
		} catch (Exception e) {
			log.error("error", e);
			response(e);
		}
	}

	/**
	 * 裁判类型 条件referee
	 */
	public void findReferee() {
		try {
			final DetachedCriteria dc = Member.getCriteriaQuery(null);
			if (!StringUtils.isEmpty(refereeType)) {
				String[] array = refereeType.split(",");
				dc.add(Restrictions.in("role", array));
			}
			if (keyword != null && !"".equals(keyword)) {
				final String where = "%" + keyword + "%";
				dc.add(or(Restrictions.like("name", where), Restrictions.like("nick", where)));
			} else {
				if (!StringUtils.isEmpty(city)) {

					dc.add(Restrictions.like("city", "%" + city + "%"));
				}
			}
			pageInfo.setOrder("stickTime");
			pageInfo.setOrderFlag("desc");
			pageInfo = service.findPageByCriteria(dc, pageInfo);
			final JSONArray jarr = new JSONArray();
			for (final Iterator<?> it = pageInfo.getItems().iterator(); it.hasNext();) {
				final Member in = (Member) it.next();

				if (!StringUtils.isEmpty(judageArray)) {
					String[] a = judageArray.split(",");
					for (String b : a) {
						if (b.equals(in.getId().toString())) {
							final JSONObject obj = getMemberJson(in);
							jarr.add(obj);
						}
					}
				} else {
					final JSONObject obj = getMemberJson(in);
					jarr.add(obj);
				}
			}
			final JSONObject obj = new JSONObject();
			obj.accumulate("success", true).accumulate("pageInfo", getJsonForPageInfo()).accumulate("items", jarr);
			response(obj);
		} catch (Exception e) {
			log.error("error", e);
			response(e);
		}
	}

	/**
	 * 查找某教练或俱乐部首页数据
	 */
	public void findHome() {
		try {
			final Member member = (Member) service.load(Member.class, id);
			final JSONObject obj = getMemberJson(member);
			obj.accumulate("success", true).accumulate("type", member.getRole()).accumulate("message", uuid)
					// 计算 评分 评价人数
					.accumulate("appraise", getGradeJson(member)).accumulate("address", member.getAddress()).accumulate("description", member.getDescription())
					.accumulate("mode", member.getMode()).accumulate("mobilephone", member.getMobilephone()).accumulate("tell", member.getTell())
					.accumulate("course", findCourseInfo());
			response(obj);
		} catch (Exception e) {
			log.error("error", e);
			response(e);
		}
	}

	/**
	 * 查找指定 俱乐部或教练 设置课程
	 */
	public JSONArray findCourseInfo() {
		List<?> list = service.findObjectBySql("from CourseInfo ci where ci.member.id = ? order by ci.sort", id);
		final JSONArray jarr = new JSONArray();
		for (final Iterator<?> it = list.iterator(); it.hasNext();) {
			final JSONObject json = new JSONObject();
			final CourseInfo courseinfo = (CourseInfo) it.next();
			json.accumulate("id", courseinfo.getId()).accumulate("courseName", courseinfo.getName()).accumulate("memo", courseinfo.getMemo()).accumulate("image",
					courseinfo.getImage());
			jarr.add(json);
		}
		return jarr;
	}

	/**
	 * 查找指定 俱乐部或教练 的所有健身卡信息
	 */
	public String findProduct() {
		// TODO Test Data
		Member memberTest = new Member();
		memberTest.setId(Long.parseLong("458"));
		memberTest.setRole("M");
		id = Long.parseLong("290");// 教练id
		request.getSession().setAttribute("member", memberTest);
		
		try {
			final DetachedCriteria dc = DetachedCriteria.forClass(Product.class);
			final Member m = (Member) request.getSession().getAttribute("member");
			dc.add(Restrictions.eq("member.id", id));
			if (m.getRole().equals("S")) {
				dc.add(Restrictions.eq("audit", '1'));
			} else {
				dc.add(Restrictions.eq("audit", '1'));
				dc.add(Restrictions.eq("isClose", "2"));
			}
			pageInfo.setOrder("topTime");
			pageInfo.setOrderFlag("desc");
			pageInfo = service.findPageByCriteria(dc, pageInfo);
		/*	String memo = null;
			String isSelf = "0";
			final JSONArray jarr = new JSONArray();
			for (final Iterator<?> it = pageInfo.getItems().iterator(); it.hasNext();) {
				final Product p = (Product) it.next();
				List<Map<String, Object>> parameterList = service
						.queryForList("SELECT * FROM tb_parameter p WHERE p.parent = (SELECT id FROM tb_parameter WHERE CODE = 'card_type_c') and p.code = ?", p.getProType());
				if (parameterList != null && parameterList.size() > 0) {
					memo = parameterList.get(0).get("memo").toString();
				}
				if (p.getMember().getId() == getMobileUser().getId()) {
					isSelf = "1";
				}
				jarr.add(p.toJson().accumulate("memo", memo).accumulate("isSelf", isSelf));
			}
			final JSONObject ret = new JSONObject();
			ret.accumulate("success", true).accumulate("pageInfo", getJsonForPageInfo()).accumulate("items", jarr);
			response(ret);*/
			
			return "privateMealPackage";
		} catch (Exception e) {
			log.error("error", e);
			response(e);
			
			return "privateMealPackage";
		}
	}
	
	/**
	 * 查看私教套餐详情
	 */
	public String privateMealPackageDetail() {
		String sql = "SELECT *,(SELECT COUNT(id) FROM tb_product_order WHERE product = ?) AS count FROM tb_product p WHERE id = ?";
		pageInfo = service.findPageBySql(sql, pageInfo, new Object[]{ packageId, packageId });
		request.setAttribute("pageInfo", pageInfo);
		
		return "privateMealPackageDetail";
	}
	
	/**
	 * 购买私教套餐
	 */
	public String buyPackage(){
		// TODO Test Data
		Member memberTest = new Member();
		memberTest.setId(Long.parseLong("458"));
		memberTest.setName("Jun");
		memberTest.setMobilephone("110");
		request.getSession().setAttribute("member", memberTest);
		
		String sql = "SELECT name orderName, cost orderCost,(SELECT COUNT(id) FROM tb_product_order WHERE product = ?) AS count FROM tb_product p WHERE id = ?";
		pageInfo = service.findPageBySql(sql, pageInfo, new Object[]{ packageId, packageId });
		request.setAttribute("pageInfo", pageInfo);
		
		return "buyPackage";
	}

	/**
	 * 签到时订单展现 2014/12/23 增加其它类型商品的订单展现
	 */
	public void findOrder() {
		try {
			final BaseMember mu = getMobileUser();
			final List<?> list = service.findObjectBySql("from ProductOrder p where p.member.id = ? and p.product.member.id = ? and p.status = '1' order by p.orderDate desc",
					mu.getId(), id);
			final JSONArray jarr = new JSONArray();
			for (final Iterator<?> it = list.iterator(); it.hasNext();) {
				final ProductOrder po = (ProductOrder) it.next();
				final JSONObject obj = new JSONObject(), prodObj = new JSONObject();
				prodObj.accumulate("id", po.getProduct().getId()).accumulate("name", po.getProduct().getName()).accumulate("proType", po.getProduct().getProType());
				obj.accumulate("id", po.getId()).accumulate("no", po.getNo()).accumulate("product", prodObj);
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

	/**
	 * 签到登录，传入参数。username, password 2014/12/23 修改签到检核（签到密码为signPass)
	 */
	@SuppressWarnings("unused")
	public void login() {
		try {
			final Member m = service.sign(username, signPass);
			final JSONObject ret = new JSONObject();
			if (m == null) {
				ret.accumulate("success", false).accumulate("message", "您输入的账户或口令不存在，请重新输入！");
				response(ret);
			} else {
				// 判断是否有订单关系
				final BaseMember mu = getMobileUser();
				final String role = m.getRole();

				final boolean hasRel = service.registration(mu.getId(), m.getId());
				if (!hasRel) throw new LogicException("您没有购买该商户的健身卡，不能进行签单操作！");
				ret.accumulate("success", true).accumulate("message", "OK").accumulate("key", m.getId());
				response(ret);
			}
		} catch (Exception e) {
			log.error("error", e);
			response(e);
		}
	}

	/**
	 * 签到，传入参数，id（被签到订单所属会员）,signDate（签到时间）, orderId（签到订单）, money（金额）
	 */
	public void sign() {
		try {
			final SignIn si = new SignIn(id);
			si.setMemberSign(new Member(getMobileUser().getId()));
			si.setMemberSign(new Member(id));
			si.setOrderId(orderId);
			final ProductOrder po = (ProductOrder) service.load(ProductOrder.class, si.getOrderId());
			si.setOrderNo(po.getNo());
			si.setMemberAudit(po.getProduct().getMember());
			si.setOrderNick(si.getMemberAudit().getName());
			si.setMoney(money);
			si.setSignDate(signDate);
			service.saveOrUpdate(si);
			final JSONObject ret = new JSONObject();
			ret.accumulate("success", true).accumulate("message", "OK");
			response(ret);
		} catch (Exception e) {
			log.error("error", e);
			response(e);
		}
	}

	/**
	 * 查找会员列表
	 */
	@SuppressWarnings("unchecked")
	public void findMember() {
		try {
			String where = "%";
			BaseMember mu = getMobileUser();
			String sql = "select * from (select a.*, b.hip , b.waist , c.traincount / a.workoutTimes * 100 as finishrate ,c.traincount as traincount,c.time as time from tb_member a left join (select t.partake, t.hip , t.waist from tb_plan_record t  order by t.done_date desc limit 0,1) b on a.id = b.partake left join (select t.partake , sum(t.times) time, count(*) traincount from tb_plan_record t where t.done_date<=SYSDATE() group by t.partake ) c on a.id = c.partake where a.coach = ? or a.id = ?) s where s.name like ? or s.nick like ? or s.email like ? or s.mobilephone like ? ORDER BY abs(s.id-?)";
			if (keyword != null && !"".equals(keyword)) {
				where = "%" + keyword + "%";
			}
			pageInfo = service.findPageBySql(sql, pageInfo, new Object[] { mu.getId(), mu.getId(), where, where, where, where, mu.getId() });
			if (pageInfo.getItems() == null) pageInfo.setItems(new ArrayList<Member>());
			// if (mu.getRole().equals("S")) pageInfo.getItems().add(0,
			// service.load(Member.class, mu.getId()));
			final JSONArray jarr = new JSONArray();
			Member m = new Member();
			DecimalFormat df = new DecimalFormat("#.00");
			for (final Iterator<?> it = pageInfo.getItems().iterator(); it.hasNext();) {
				Map<String, Object> map1 = (Map<String, Object>) it.next();
				m = (Member) service.load(Member.class, Long.parseLong(map1.get("id").toString()));
				final Setting set = service.loadSetting(m.getId());
				int i = 0;
				Calendar cal = Calendar.getInstance();
				if (!cal.before(m.getBirthday())) {
					int year1 = cal.get(Calendar.YEAR);
					int month1 = cal.get(Calendar.MONTH) + 1;
					int day1 = cal.get(Calendar.DAY_OF_MONTH);
					cal.setTime(m.getBirthday());
					int year2 = cal.get(Calendar.YEAR);
					int month2 = cal.get(Calendar.MONTH) + 1;
					int day2 = cal.get(Calendar.DAY_OF_MONTH);
					i = year1 - year2;
					if (month1 <= month2) {
						if (month1 == month2) {
							if (day1 < day2) {
								i--;
							}
						} else {
							i--;
						}
					}
				}

				// hip waist
				double hip = 0.00;
				double waist = 0.00;
				if (map1.get("hip") != null) {
					hip = Double.parseDouble(map1.get("hip").toString());
				}
				if (map1.get("waist") != null) {
					waist = Double.parseDouble(map1.get("waist").toString());
				}
				final JSONObject obj = getMemberJson(m);
				obj.put("count", map1.get("traincount") == null ? 0 : map1.get("traincount"));
				obj.accumulate("finishrate", map1.get("finishrate") == null ? 0 : map1.get("finishrate")).accumulate("time",
						map1.get("finishrate") == null ? 0 : String.valueOf(df.format(Double.parseDouble(map1.get("time").toString()) / 60)));
				obj.accumulate("height", getInteger(set.getHeight())).accumulate("weight", getDouble(set.getWeight()));
				obj.accumulate("bmiHigh", set.getBmiHigh()).accumulate("bmiLow", set.getBmiLow());
				obj.accumulate("age", i).accumulate("heart", set.getHeart());
				obj.accumulate("hip", hip).accumulate("waist", waist);
				jarr.add(obj);
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
	 * 查找某个会员的首页
	 */
	@SuppressWarnings("all")
	public void findMemberHome() {
		try {
			String sql = "";
			List<Map<String, Object>> list = service.queryForList(
					"select  m.* ,t.hip,t.waist,c.traincount / m.workoutTimes * 100 as finishrate ,c.traincount as traincount,c.time as time from tb_member m LEFT JOIN (select p.hip,p.waist,p.partake from tb_plan_record p ORDER BY p.done_date desc limit 0,1)t on t.partake=m.id LEFT JOIN(select  tp.partake , sum(tp.times) time, count(*) traincount from tb_plan_record tp where  tp.done_date<=SYSDATE() GROUP BY tp.partake) c on c.partake=m.id where m.id=?",
					new Object[] { id });
			final JSONObject obj = new JSONObject();
			Long mid = Long.parseLong(list.get(0).get("id").toString());
			final Member m = (Member) service.load(Member.class, mid);
			final Setting set = service.loadSetting(mid);
			int i = 0;
			DecimalFormat df = new DecimalFormat("#.00");
			Calendar cal = Calendar.getInstance();
			if (!cal.before(m.getBirthday())) {
				int year1 = cal.get(Calendar.YEAR);
				int month1 = cal.get(Calendar.MONTH) + 1;
				int day1 = cal.get(Calendar.DAY_OF_MONTH);
				cal.setTime(m.getBirthday());
				int year2 = cal.get(Calendar.YEAR);
				int month2 = cal.get(Calendar.MONTH) + 1;
				int day2 = cal.get(Calendar.DAY_OF_MONTH);
				i = year1 - year2;
				if (month1 <= month2) {
					if (month1 == month2) {
						if (day1 < day2) {
							i--;
						}
					} else {
						i--;
					}
				}
			}
			obj.accumulate("success", true).accumulate("id", id).accumulate("name", getString(m.getName())).accumulate("nick", getString(m.getNick()))
					.accumulate("image", getString(m.getImage())).accumulate("count", list.get(0).get("traincount") == null ? 0 : list.get(0).get("traincount").toString())
					.accumulate("sex", m.getSex()).accumulate("age", i).accumulate("heart", set.getHeart()).accumulate("time", list.get(0).get("time") == null ? 0
							: String.valueOf(df.format(Double.parseDouble(list.get(0).get("time").toString()) / 60)))
					.accumulate("finishrate", list.get(0).get("finishrate") == null ? 0 : String.valueOf(df.format(Double.parseDouble(list.get(0).get("finishrate").toString()))))
					.accumulate("weight", set.getWeight()).accumulate("height", set.getHeight());
			response(obj);
		} catch (Exception e) {
			log.error("error", e);
			response(e);
		}
	}

	/**
	 * 健友查询,依据经纬度进行处理
	 */
	@SuppressWarnings("unchecked")
	public void findFriend() {
		try {
			setCity(uuid, lng, lat, city);
			final BaseMember mu = getMobileUser();
			final Double currLng = mu.getLongitude();
			final Double currLat = mu.getLatitude();
			double[] around = LnglatUtil.getAround(currLat, currLng, 5 * 1000);
			final List<Map<String, Object>> list = service.queryForList(
					"select *,tp.traincount/m.workoutTimes *100 finishrate from tb_member m LEFT JOIN (select t.partake , sum(t.times) time, count(*) traincount from tb_plan_record t GROUP BY t.partake) tp on m.id = tp.partake where m.id <> ? and m.role in ('S','M') and m.longitude between ? and ? and m.latitude between ? and ?",
					new Object[] { mu.getId(), around[1], around[3], around[0], around[2] });
			final JSONObject ret = new JSONObject();
			final JSONArray jarr = new JSONArray();
			DecimalFormat df = new DecimalFormat("#.00");
			for (final Iterator<?> it = list.iterator(); it.hasNext();) {
				final Map<String, Object> map = (Map<String, Object>) it.next();
				Long mid = Long.parseLong(map.get("id").toString());
				Member m = (Member) service.load(Member.class, mid);
				Setting set = service.loadSetting(mid);
				final Double distance = LnglatUtil.GetDistance(currLng, currLat, m.getLongitude(), m.getLatitude());
				final JSONObject obj = getMemberJson(m);
				int i = 0;
				Calendar cal = Calendar.getInstance();
				if (!cal.before(m.getBirthday())) {
					int year1 = cal.get(Calendar.YEAR);
					int month1 = cal.get(Calendar.MONTH) + 1;
					int day1 = cal.get(Calendar.DAY_OF_MONTH);
					cal.setTime(m.getBirthday());
					int year2 = cal.get(Calendar.YEAR);
					int month2 = cal.get(Calendar.MONTH) + 1;
					int day2 = cal.get(Calendar.DAY_OF_MONTH);
					i = year1 - year2;
					if (month1 <= month2) {
						if (month1 == month2) {
							if (day1 < day2) {
								i--;
							}
						} else {
							i--;
						}
					}
				}
				obj.accumulate("distance", distance)
						.accumulate("time", map.get("time") == null ? 0 : String.valueOf(df.format(Double.parseDouble(map.get("time").toString()) / 60)))
						.accumulate("finishrate", map.get("finishrate") == null ? 0 : map.get("finishrate").toString()).accumulate("age", i)
						.accumulate("heart", set.getHeart() == null ? 0 : set.getHeart()).accumulate("height", set.getHeight() == null ? 0 : set.getHeight())
						.put("count", map.get("traincount") == null ? 0
								: map.get("traincount").toString());
				jarr.add(obj);
			}
			JSONUtils.bubbleSort(jarr, true, "distance");
			ret.accumulate("success", true).accumulate("items", jarr);
			response(ret);
		} catch (Exception e) {
			log.error("error", e);
			response(e);
		}
	}

	/**
	 * 给教练评分
	 */
	public void grade() {
		try {
			JSONArray arr = JSONArray.fromObject(URLDecoder.decode(jsons, "UTF-8"));
			final JSONObject obj = arr.getJSONObject(0), ret = new JSONObject();
			final Long id = obj.getLong("id");
			final String memo = obj.getString("memo");
			final Double score = obj.getDouble("score");
			final Long cId = getMobileUser().getId();
			// 判断能否进行评分
			final List<?> fs = service.findObjectBySql("from Friend f where (f.friend.id = ? and f.member.id = ?) or (f.friend.id = ? and f.member.id = ?)", cId, id, id, cId);
			if (fs.size() <= 0) {
				final Member m = (Member) service.load(Member.class, id);
				ret.accumulate("success", false).accumulate("code", -1).accumulate("message", "您不是" + m.getNick() + "的会员，不能评分！");
				response(ret);
				return;
			}
			Appraise a = new Appraise();
			a.setAppDate(new Date());
			a.setMember(new Member(cId));
			a.setMemberTo(new Member(id));
			a.setContent(memo);
			a.setGrade(score);
			a.setIsRead("0");
			a = service.saveOrUpdateAppraise(a);
			ret.accumulate("success", true).accumulate("key", a.getId());
			response(ret);
		} catch (Exception e) {
			log.error("error", e);
			response(e);
		}
	}

	/**
	 * 解除私教关系
	 */
	@SuppressWarnings("unused")
	public void remove() {
		try {
			BaseMember mu = getMobileUser();
			final Member m = (Member) service.load(Member.class, mu.getId());
			// 将提醒类消息“解除”写入到MESSAGE中
			Date sendTime = new Date();
			List<Message> msgList = new ArrayList<Message>();
			msgList.add(new Message(null, m, sendTime, "您解除了您的私教【" + m.getCoach().getName() + "】", "3", "0", "1"));
			msgList.add(new Message(null, m.getCoach(), sendTime, m.getName() + "解除了与您的私教关系", "3", "0", "1"));
			service.saveOrUpdate(msgList);

			String uid = m.getCoach().getUserId();
			Long cid = m.getCoach().getChannelId();
			Long coachid = m.getCoach().getId();
			Integer type = m.getCoach().getTermType();
			m.setCoach(null);
			service.saveOrUpdate(m);
			// new MessageThread(uid, cid, type, "会员“" + mu.getName() +
			// "”解除了您的私教关系。");
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
			pushRequest.setTargetValue(coachid.toString()); // 根据Target来设定，如Target=DEVICE,
															// 则对应的值为
															// 设备id1,设备id2.
															// 多个值使用逗号分隔.(帐号与设备有一次最多100个的限制)
			pushRequest.setPushType("NOTICE"); // 消息类型 MESSAGE NOTICE
			pushRequest.setDeviceType("ALL"); // 设备类型 ANDROID iOS ALL.
			pushRequest.setTitle("解除私教通知"); // 消息的标题
			pushRequest.setBody(m.getName() + "解除了与您的私教关系"); // 消息的内容
			pushRequest.setiOSApnsEnv("PRODUCT");// DEV:开发模式; PRODUCT:生产模式
			pushRequest.setAndroidOpenType("APPLICATION"); // 点击通知后动作
															// "APPLICATION" :
															// 打开应用 "ACTIVITY" :
															// 打开AndroidActivity
															// "URL" : 打开URL
															// "NONE" : 无跳转
			pushRequest.setStoreOffline(true); // 离线消息是否保存,若保存,
												// 在推送时候，用户即使不在线，下一次上线则会收到
			PushResponse pushResponse = client.getAcsResponse(pushRequest);
			System.out.printf("RequestId: %s, MessageID: %s\n", pushResponse.getRequestId(), pushResponse.getMessageId());
			final JSONObject ret = new JSONObject();
			ret.accumulate("success", true).accumulate("message", "OK");
			response(ret);
		} catch (Exception e) {
			log.error("error", e);
			response(e);
		}
	}

	/**
	 * 申请加入俱乐部
	 */
	@SuppressWarnings({ "unused", "unchecked", "rawtypes" })
	public void request() {
		try {
			final BaseMember mu = getLoginMember();
			final Long mid = mu.getId();

			// 增加申请加入私教
			Member memto = (Member) service.load(Member.class, id);
			if (memto.getRole().equals("S")) {
				List<?> fList = service.findObjectBySql("from Member m where m.id = ? ", mid);
				for (final Iterator<?> it = fList.iterator(); it.hasNext();) {
					final Member mem = (Member) it.next();
					if (mem.getCoachId() == memto.getId()) {
						throw new LogicException("您已经加入了该私教，不得重复加入！");
					} else if (mem.getCoachId() != null) { throw new LogicException("您已经加入了私教，需要先解除后在加入！"); }
				}

			} else {
				List<?> fList = service.findObjectBySql("from Friend f where f.member.id = ? and f.friend.id = ? and type = ?", new Object[] { mid, id, "1" });
				if (fList.size() > 0) throw new LogicException("您已经加入了该俱乐部，不得重复加入！");
			}

			final Message msg = new Message();
			if (memto.getRole().equals("S")) {
				msg.setContent("申请加入私教！");
			} else {
				msg.setContent("申请加入俱乐部！");
			}

			msg.setIsRead("0");
			msg.setMemberFrom(new Member(mid));
			msg.setMemberTo(new Member(id));
			msg.setSendTime(new Date());
			msg.setStatus("1");
			msg.setType("1");
			service.saveOrUpdate(msg);
			final JSONObject ret = new JSONObject();

			PushRequest pushRequest = new PushRequest();
			IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", "LTAIEBpPW8aDSjKX", "x9wTapvynUDLiyHg3zdWuVjGs3JFxj");
			Long appkey = (long) 23580685;
			DefaultAcsClient client1 = new DefaultAcsClient(profile);
			pushRequest.setProtocol(ProtocolType.HTTPS);
			pushRequest.setMethod(MethodType.POST);
			pushRequest.setActionName("Push");
			pushRequest.setRegionId("cn-hangzhou");
			pushRequest.setVersion("2016-08-01");
			pushRequest.setAppKey(appkey);
			pushRequest.setTarget("ACCOUNT"); // 推送目标: DEVICE:按设备推送 ALIAS :
												// 按别名推送 ACCOUNT:按帐号推送
												// TAG:按标签推送; ALL: 广播推送
			pushRequest.setTargetValue(id.toString()); // 根据Target来设定，如Target=DEVICE,
														// 则对应的值为 设备id1,设备id2.
														// 多个值使用逗号分隔.(帐号与设备有一次最多100个的限制)
			pushRequest.setPushType("NOTICE"); // 消息类型 MESSAGE NOTICE
			pushRequest.setDeviceType("ALL"); // 设备类型 ANDROID iOS ALL.
			pushRequest.setTitle("卡库聘请私教通知"); // 消息的标题
			pushRequest.setBody(msg.getContent()); // 消息的内容
			pushRequest.setiOSApnsEnv("PRODUCT");// DEV:开发模式; PRODUCT:生产模式
			pushRequest.setAndroidOpenType("APPLICATION"); // 点击通知后动作
															// "APPLICATION" :
															// 打开应用 "ACTIVITY" :
															// 打开AndroidActivity
															// "URL" : 打开URL
															// "NONE" : 无跳转
			pushRequest.setStoreOffline(true); // 离线消息是否保存,若保存,
												// 在推送时候，用户即使不在线，下一次上线则会收到
			PushResponse pushResponse = client1.getAcsResponse(pushRequest);
			System.out.printf("RequestId: %s, MessageID: %s\n", pushResponse.getRequestId(), pushResponse.getMessageId());

			String url = "http://gw.api.taobao.com/router/rest";
			TaobaoClient client = new DefaultTaobaoClient(url, "23330566", "0c6b77f937d49f3a14ca98a6316d110e");
			OpenimCustmsgPushRequest req = new OpenimCustmsgPushRequest();
			CustMsg obj1 = new CustMsg();
			obj1.setFromUser("some_one");
			obj1.setToAppkey("0");

			ArrayList list = new ArrayList();
			list.add(memto.getId());
			obj1.setToUsers(list);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			obj1.setSummary(sdf.format(new Date()));
			obj1.setData("apply");
			obj1.setAps("{\"alert\":\"加入申请\"}");
			obj1.setApnsParam("apply");
			obj1.setInvisible(1L);
			obj1.setFromNick(mu.getName());
			req.setCustmsg(obj1);
			OpenimCustmsgPushResponse rsp = client.execute(req);

			ret.accumulate("success", true).accumulate("message", "OK");
			response(ret);
		} catch (Exception e) {
			log.error("error", e);
			response(e);
		}
	}

	/**
	 * 自动生成计划
	 */
	public void autoGen() {
		try {
			IPlanGenerator gen = new SystemGeneratorImpl(service, null, getMobileUser().getId(), new Date());
			gen.generator();
			final JSONObject ret = new JSONObject();
			ret.accumulate("success", true).accumulate("message", "OK");
			response(ret);
		} catch (Exception e) {
			log.error("error", e);
			response(e);
		}
	}

	/**
	 * 查找会员最新记录
	 */
	public void findRecord() {
		try {
			if (id == null) id = getMobileUser().getId();
			if (signDate == null) signDate = new Date();
			final TrainRecord record = service.getRecordByDate(id, signDate);
			final Setting set = service.loadSetting(id);
			final Member m = (Member) service.load(Member.class, id);
			final JSONObject obj = new JSONObject(), ret = new JSONObject(), rObj = new JSONObject();
			obj.accumulate("id", m.getId()).accumulate("birthday", m.getBirthday() == null ? "" : ymd.format(m.getBirthday())).accumulate("bmiLow", set.getBmiLow())
					.accumulate("bmiHigh", set.getBmiHigh()).accumulate("weight", set.getWeight()).accumulate("height", set.getHeight()).accumulate("sex", m.getSetting());
			if (record == null) {
				rObj.accumulate("member", obj);
				ret.accumulate("success", true).accumulate("record", rObj);
				response(ret);
				return;
			}
			final Double strength = service.findWeightByMember(id, record.getDoneDate());
			rObj.accumulate("id", record.getId()).accumulate("member", obj).accumulate("fat", getDouble(record.getFat())).accumulate("hip", getDouble(record.getHip()))
					.accumulate("waist", getDouble(record.getWaist())).accumulate("strength", strength);
			ret.accumulate("success", true).accumulate("record", rObj);
			response(ret);
		} catch (Exception e) {
			log.error("error", e);
			response(e);
		}
	}

	public void saveSetting() {
		try {
			Setting set = service.loadSetting(getMobileUser().getId());
			final JSONArray arrs = JSONArray.fromObject(URLDecoder.decode(jsons, "UTF-8"));
			final JSONObject obj = arrs.getJSONObject(0);
			// set.setBmiHigh(obj.getInt("bmiHigh"));
			// set.setBmiLow(obj.getInt("bmiLow"));
			// set.setBmiMode(obj.getString("bmiMode").charAt(0));
			set.setCardioDate(obj.getString("cardioDate"));
			set.setCardioDuration(obj.getString("cardioDuration"));
			set.setCurrGymStatus(obj.getString("currGymStatus"));
			set.setDiseaseReport(obj.getString("diseaseReport"));
			set.setFavoriateCardio(obj.getString("favoriateCardio"));
			// set.setHeart(obj.getInt("heart"));
			// set.setHeight(obj.getInt("height"));
			set.setStrengthDate(obj.getString("strengthDate"));
			set.setStrengthDuration(obj.getString("strengthDuration"));
			set.setTarget(obj.getString("target"));
			set = (Setting) service.saveOrUpdate(set);
			final JSONObject ret = new JSONObject();
			ret.accumulate("success", true).accumulate("key", set.getId());
			response(ret);
		} catch (Exception e) {
			log.error("error", e);
			response(e);
		}
	}

	/**
	 * 保存注册的百度账户及通道，用于推送通知
	 */
	public void bindChannel() {
		try {
			final String userId = request.getParameter("userId");
			final String channelId = request.getParameter("channelId");
			final String termType = request.getParameter("type");
			Member m = (Member) service.load(Member.class, getMobileUser().getId());
			m.setChannelId(new Long(channelId));
			m.setUserId(userId);
			m.setTermType(Integer.parseInt(termType));
			service.saveOrUpdate(m);
			session.setAttribute(LOGIN_MEMBER, m);
			setBaiduForMobileUser(uuid, m.getUserId(), m.getChannelId(), m.getTermType());
			final JSONObject ret = new JSONObject();
			ret.accumulate("success", true).accumulate("message", "OK");
			response(ret);
		} catch (Exception e) {
			log.error("保存注册的百度账户及通道", e);
		}
	}

	/**
	 * 图表
	 */
	public void chart() {
		try {
			if (id == null || id == 0) id = getMobileUser().getId();
			final String startDate = request.getParameter("startDate");
			final String endDate = request.getParameter("endDate");
			final Date sDate = ymd.parse(startDate), eDate = ymd.parse(endDate);
			final List<?> list = service.findObjectBySql("from TrainRecord tr where tr.partake.id = ? and tr.doneDate between ? and ? order by tr.doneDate", id, sDate, eDate);
			final JSONArray jarr = new JSONArray();
			for (final Iterator<?> it = list.iterator(); it.hasNext();) {
				final TrainRecord tr = (TrainRecord) it.next();
				final JSONObject obj = new JSONObject();
				obj.accumulate("id", tr.getId()).accumulate("doneDate", ymd.format(tr.getDoneDate())).accumulate("weight", getDouble(tr.getWeight()))
						.accumulate("fat", getDouble(tr.getFat())).accumulate("waist", getDouble(tr.getWaist())).accumulate("hip", getDouble(tr.getHip()))
						.accumulate("heartRate", getDouble(tr.getHeartRate()));
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

	/**
	 * 保存靶心率,上限下限
	 */
	public void saveBmi() {
		try {
			final String strLow = request.getParameter("bmiLow");
			final String strHigh = request.getParameter("bmiHigh");
			final Setting setting = service.loadSetting(getMobileUser().getId());
			setting.setBmiHigh(new Integer(strHigh));
			setting.setBmiLow(new Integer(strLow));
			service.saveOrUpdate(setting);
			final JSONObject ret = new JSONObject();
			ret.accumulate("success", true).accumulate("message", "OK");
			response(ret);
		} catch (Exception e) {
			log.error("error", e);
			response(e);
		}
	}

	/**
	 * 获取手机验证码
	 */
	public void getMobileCode() {
		try {
			final String mobile = request.getParameter("mobile");
			// 新加入找回密码获取验证码
			final String flag = request.getParameter("flag");
			if (flag != null && "0".equals(flag)) {
				final Long count = service.queryForLong("select count(*) from tb_member where mobilephone = ? and mobile_valid = '1' and nick <> ''", mobile);
				if (count > 0) {
					sendSmsValidate(mobile, "mobile.validate.open");
				} else {
					throw new LogicException("当前手机号码未绑定至用户！");
				}
			} else if (flag != null && "1".equals(flag)) {
				// 手机注册获取验证码
				sendSmsValidate(mobile, "mobile.validate.open");
			} else {
				/*// 原3.5逻辑
				final Long count = service.queryForLong("select count(*) from tb_member where mobilephone = ? and mobile_valid = '1' and id <> ?", mobile, getMobileUser().getId());
				if (count > 0) throw new LogicException("当前手机号码已经被其它用户使用，不得重复使用！");
				final String msg = sendSmsValidate(mobile, "mobile.validate.open");
				final Member m = (Member) service.load(Member.class, getMobileUser().getId());
				m.setMobilephone(mobile);
				m.setMobileValid("0");
				service.saveOrUpdate(m);
				if (msg.equalsIgnoreCase("ok")) {
					final Tickling tick = new Tickling();
					tick.setContent("用户" + m.getNick() + "已经申请了手机验证码，如果已经处理可以不用理会本反馈，否则请手动处理。");
					tick.setCreateTime(new Date());
					tick.setLink(m.getMobilephone());
					tick.setMember(m);
					tick.setStatus('0');
					getService().saveOrUpdate(tick);
				}*/
			}
			final JSONObject ret = new JSONObject();
			ret.accumulate("success", true).accumulate("message", "OK");
			response(ret);
		} catch (Exception e) {
			log.error("error", e);
			response(e);
		}
	}

	/**
	 * 验证手机号码
	 */
	public void validMobile() {
		try {
			final JSONObject ret = new JSONObject();
			final String flag = request.getParameter("flag");

			final String mobile = request.getParameter("mobile");
			final String code = request.getParameter("code");
			if (isRightful(mobile, code)) {
				// 4.0添加1为手机注册，0为找回密码
				if ("0".equals(flag) || "1".equals(flag)) {} else {
					Member m = (Member) service.load(Member.class, getMobileUser().getId());
					m.setMobilephone(request.getParameter("mobile"));
					m.setMobileValid("1");
					service.saveOrUpdate(m);
				}
				ret.accumulate("success", true).accumulate("message", "OK");
				response(ret);
			} else {
				ret.accumulate("success", false).accumulate("message", "您的手机验证码已经过期，请重新发送手机验证码！");
				response(ret);
			}
		} catch (Exception e) {
			log.error("error", e);
			response(e);
		}
	}

	/**
	 * 保存用户地区
	 */
	public void saveArea() {
		try {
			final String province = URLDecoder.decode(request.getParameter("province"), "utf-8");
			final String city = URLDecoder.decode(request.getParameter("city"), "utf-8");
			final String county = URLDecoder.decode(request.getParameter("county"), "utf-8");
			Member m = (Member) service.load(Member.class, getMobileUser().getId());
			m.setProvince(province);
			m.setCity(city);
			m.setCounty(county);
			service.saveOrUpdate(m);
			final JSONObject ret = new JSONObject();
			ret.accumulate("success", true).accumulate("message", "OK");
			response(ret);
		} catch (Exception e) {
			log.error("error", e);
			response(e);
		}
	}

	/**
	 * 保存静心率
	 */
	public void saveHeart() {
		try {
			final String heart = request.getParameter("heart");
			final Setting setting = service.loadSetting(getMobileUser().getId());
			setting.setHeart(new Integer(heart));
			service.saveOrUpdate(setting);
			final JSONObject ret = new JSONObject();
			ret.accumulate("success", true).accumulate("message", "OK");
			response(ret);
		} catch (Exception e) {
			log.error("error", e);
			response("{success: false, message: '" + e.getMessage() + "'}");
		}
	}

	/**
	 * 保存身高
	 */
	public void saveHeight() {
		try {
			final String height = request.getParameter("height");
			final Setting setting = service.loadSetting(getMobileUser().getId());
			setting.setHeight(new Integer(height));
			service.saveOrUpdate(setting);
			final JSONObject ret = new JSONObject();
			ret.accumulate("success", true).accumulate("message", "OK");
			response(ret);
		} catch (Exception e) {
			log.error("error", e);
			response(e);
		}
	}

	/**
	 * 保存生日
	 */
	public void saveBirthday() {
		try {
			final String birthday = request.getParameter("birthday");
			final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Member m = (Member) service.load(Member.class, getMobileUser().getId());
			m.setBirthday(sdf.parse(birthday));
			service.saveOrUpdate(m);
			final JSONObject ret = new JSONObject();
			ret.accumulate("success", true).accumulate("message", "OK");
			response(ret);
		} catch (Exception e) {
			log.error("error", e);
			response(e);
		}
	}

	/**
	 * 修改密码
	 */
	@SuppressWarnings("unchecked")
	public void modifyPassword() {
		try {
			final JSONObject ret = new JSONObject();
			final String flag = request.getParameter("flag");
			if (flag != null && "0".equals(flag)) {
				final String mobile = request.getParameter("mobile");
				final String password = request.getParameter("password");
				List<Member> list = (List<Member>) service.findObjectBySql("from Member m where m.mobilephone = ?", mobile);
				Member m = list.get(0);
				m.setPassword(password);
				service.saveOrUpdate(m);
				ret.accumulate("success", true).accumulate("message", "OK");
				response(ret);
			} else {
				// 3.5逻辑
				final String oldPassword = request.getParameter("oldPassword");
				Member m = (Member) service.load(Member.class, getMobileUser().getId());
				if (m.getPassword().equalsIgnoreCase(oldPassword)) {
					final String password = request.getParameter("password");
					m.setPassword(password);
					service.saveOrUpdate(m);
					ret.accumulate("success", true).accumulate("message", "OK");
					response(ret);
				} else {
					ret.accumulate("success", false).accumulate("code", -1).accumulate("message", "您的原密码不正确,请重新输入!");
					response(ret);
				}
			}
		} catch (Exception e) {
			log.error("error", e);
			response(e);
		}
	}

	/**
	 * 保存真实姓名
	 */
	public void saveName() {
		try {
			final String name = URLDecoder.decode(request.getParameter("name"), "utf-8");
			Member m = (Member) service.load(Member.class, getMobileUser().getId());
			m.setName(name);
			service.saveOrUpdate(m);
			final JSONObject ret = new JSONObject();
			ret.accumulate("success", true).accumulate("message", "OK");
			response(ret);
		} catch (Exception e) {
			log.error("error", e);
			response(e);
		}
	}

	/**
	 * 保存性别
	 */
	public void saveSex() {
		try {
			final String sex = request.getParameter("sex");
			Member m = (Member) service.load(Member.class, getMobileUser().getId());
			m.setSex(sex);
			service.saveOrUpdate(m);
			final JSONObject ret = new JSONObject();
			ret.accumulate("success", true).accumulate("message", "OK");
			response(ret);
		} catch (Exception e) {
			log.error("error", e);
			response(e);
		}
	}

	/**
	 * 城市切换列表
	 */
	public void changeCity() {
		try {

			final List<?> list = service.findObjectBySql("from Area1 ta where open = '1' and type = '1' order by ta.id");

			final JSONArray jarr = new JSONArray();
			for (final Iterator<?> it = list.iterator(); it.hasNext();) {
				final Area1 tr = (Area1) it.next();
				final JSONObject obj = new JSONObject();
				obj.accumulate("id", tr.getId()).accumulate("cityname", tr.getName());
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

	/**
	 * 查询会员体态分析数据
	 */
	public void loadAnalyseBody() {
		try {
			if (null == userId) {
				userId = getMobileUser().getId();
			}
			if (null != analyDate) {
				Body body = new Body();
				body.setMember(userId);
				body.setAnalyDate(analyDate);
				body = service.loadBodyData(body, userId);
				final JSONObject obj = new JSONObject();
				obj.accumulate("success", true).accumulate("item", body.toJson());
				response(obj);
			}
		} catch (Exception e) {
			log.error("error", e);
			response(e);
		}
	}

	/**
	 * 生成体态分析结论
	 */
	public void saveAnalyseSetting() {
		try {
			final JSONArray arr = JSONArray.fromObject(URLDecoder.decode(jsons, "UTF-8"));
			final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			log.error("生成体态分析结论JSON串：" + arr.toString());
			final JSONObject obj = arr.getJSONObject(0);
			Body body = new Body();
			body.setId(id);
			body.setMember(obj.getLong("member"));
			body.setAnalyDate(null != obj.getString("analyDate") ? sdf.parse(obj.getString("analyDate")) : null);
			body.setHeadSide(obj.getString("headSide"));
			body.setHeadBack(obj.getString("headBack"));
			body.setCervicalSide(obj.getString("cervicalSide"));
			body.setShoulderBack(obj.getString("shoulderBack"));
			body.setScapulaSide(obj.getString("scapulaSide"));
			body.setThoracicSide(obj.getString("thoracicSide"));
			body.setThoracicBack(obj.getString("thoracicBack"));
			body.setLumbarSide(obj.getString("lumbarSide"));
			body.setLumbarBack(obj.getString("lumbarBack"));
			body.setPelvisSide(obj.getString("pelvisSide"));
			body.setPelvisBack(obj.getString("pelvisBack"));
			body.setKneeSide(obj.getString("kneeSide"));
			body.setKneeBack(obj.getString("kneeBack"));
			body.setFootSide(obj.getString("footSide"));
			String fileName1 = imageSide != null ? saveFile("picture", imageSide, imageSideFileName, null) : null;
			String fileName2 = imageBack != null ? saveFile("picture", imageBack, imageBackFileName, null) : null;
			if (fileName1 != null) {
				body.setImageSide(fileName1);
			}
			if (fileName2 != null) {
				body.setImageBack(fileName2);
			}
			if (obj.containsKey("conclusion")) {
				body.setConclusion(obj.getString("conclusion"));
			} else {
				body.setConclusion(BodyUtils.handlerBody(body));
			}
			body = (Body) service.saveOrUpdate(body);
			final JSONObject ret = new JSONObject();
			ret.accumulate("success", true).accumulate("key", body.getId()).accumulate("conclusion", body.getConclusion());
			response(ret);
		} catch (Exception e) {
			log.error("error", e);
			response(e);
		}
	}

	/**
	 * 保存体态分析结论
	 * 
	 * @param String uuid: 用户唯一标识
	 * @param Long id: 体态分析主键                                
	 * @param conclusion: 分析结论
	 * 
	 * @return success: true/false,成功与否，key: 当前业务的ID号
	 */
	public void saveAnalyseBody() {
		try {
			Body body = new Body();
			body = (Body) service.load(Body.class, id);
			body.setConclusion(conclusion);
			body = (Body) service.saveOrUpdate(body);
			final JSONObject ret = new JSONObject();
			ret.accumulate("success", true).accumulate("key", body.getId());
			response(ret);
		} catch (Exception e) {
			log.error("error", e);
			response(e);
		}
	}

	/**
	 * 查询会员健身设置
	 */
	public void loadMemberSetting() {
		try {
			if (userId == null) userId = getMobileUser().getId();
			Member m = (Member) service.load(Member.class, userId);
			Setting setting = new Setting();
			setting = service.loadSetting(userId);
			final JSONObject obj = new JSONObject();
			obj.accumulate("success", true).accumulate("item", setting.toJson().accumulate("sex", null == m ? "M" : m.getSex()));
			response(obj);
		} catch (Exception e) {
			log.error("error", e);
			response(e);
		}
	}

	/**
	 * 保存会员设置
	 */
	public void saveMemberSetting() {
		try {
			final JSONArray arr = JSONArray.fromObject(URLDecoder.decode(jsons, "UTF-8"));
			log.error("保存会员设置JSON串：" + arr.toString());
			final JSONObject obj = arr.getJSONObject(0);
			Setting setting = new Setting();
			setting = service.loadSetting(obj.getLong("member"));
			if (null == setting.getId()) {
				setting.setId(id);
			}
			setting.setMember(obj.getLong("member"));
			if (1 == type) {
				setting.setTarget(obj.getString("target"));
				setting.setCurrGymStatus(obj.getString("currGymStatus"));
				setting.setStrengthDate(obj.getString("strengthDate"));
				setting.setStrengthDuration(obj.getString("strengthDuration"));
				setting.setCardioDate(obj.getString("cardioDate"));
				setting.setCardioDuration(obj.getString("cardioDuration"));
				setting.setFavoriateCardio(obj.getString("favoriateCardio"));
			} else if (2 == type) {
				setting.setTarget(obj.getString("target"));
				setting.setHeight(obj.getInt("height"));
				setting.setWeight(obj.getDouble("weight"));
				setting.setWaistline(obj.getDouble("waistline"));
				setting.setMaxwm(obj.getDouble("maxwm"));
				setting.setFavoriateCardio(obj.getString("favoriateCardio"));

				if (obj.containsKey("sex")) {
					Member m = (Member) service.load(Member.class, obj.getLong("member"));
					m.setSex(obj.getString("sex"));
					service.saveOrUpdate(m);
				}
			}
			setting = (Setting) service.saveOrUpdate(setting);
			final JSONObject ret = new JSONObject();
			ret.accumulate("success", true).accumulate("key", setting.getId());
			response(ret);
		} catch (Exception e) {
			log.error("error", e);
			response(e);
		}
	}

	/**
	 * 查找当前选中的俱乐部的首页信息-淘课吧
	 */
	public void findClubHomeCourse() {
		try {

			// 增加服务项目 增加类型 判断条件 A:服务项目 B：淘课吧
			/* String type = request.getParameter("type"); */

			final DetachedCriteria dc = DetachedCriteria.forClass(Course.class);

			dc.add(Restrictions.eq("member.id", id));

			/*
			 * if (type != null && !"".equals(type)){ if (type.equals("B")){
			 * dc.add(Restrictions.ge("planDate", sysDate)); } } else{
			 * dc.add(Restrictions.ge("planDate", sysDate)); }
			 */

			Date date = new Date();
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			String currDate = df.format(date);
			String afterDate = df.format(new Date(date.getTime() + 2 * 24 * 60 * 60 * 1000));
			String sysTime = String.format("%tH:%tM", date, date);
			dc.add(Restrictions.sqlRestriction("CONCAT(this_.planDate, this_.startTime) >= '" + currDate + sysTime + "'"));
			dc.add(Restrictions.le("planDate", afterDate));

			/*
			 * pageInfo.setOrder("planDate"); pageInfo.setOrderFlag("desc");
			 */
			pageInfo.setOrder("planDate,startTime");
			pageInfo.setOrderFlag("asc,asc");
			pageInfo = service.findPageByCriteria(dc, pageInfo);

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
	 * 会员校验
	 */
	public void checkMember() {
		try {
			final String productType = request.getParameter("productType");
			Order order = null;
			Long friendId = null;
			Product product = null;
			Active active = null;
			PlanRelease plan = null;
			Factory factory = null;
			Course course = null;
			Goods good = null;
			if (productType.equals("1")) {
				order = (ProductOrder) service.load(ProductOrder.class, id);
				if (order != null) {
					friendId = ((ProductOrder) order).getProduct().getMember().getId();
				} else {
					product = (Product) service.load(Product.class, id);
					if (product != null) {
						friendId = product.getMember().getId();
					}
				}
			} else if (productType.equals("2")) {
				order = (ActiveOrder) service.load(ActiveOrder.class, id);
				if (order != null) {
					friendId = ((ActiveOrder) order).getMember().getId();
				} else {
					active = (Active) service.load(Active.class, id);
					if (active != null) {
						friendId = active.getCreator().getId();
					}
				}
			} else if (productType.equals("3")) {
				order = (PlanOrder) service.load(PlanOrder.class, id);
				if (order != null) {
					friendId = ((PlanOrder) order).getPlanRelease().getMember().getId();
				} else {
					plan = (PlanRelease) service.load(PlanRelease.class, id);
					if (plan != null) {
						friendId = plan.getMember().getId();
					}
				}
			} else if (productType.equals("4")) {
				order = (FactoryOrder) service.load(FactoryOrder.class, id);
				if (order != null) {
					friendId = ((FactoryOrder) order).getFactoryCosts().getFactory().getClub();
				} else {
					factory = (Factory) service.load(Factory.class, id);
					if (factory != null) {
						friendId = factory.getClub();
					}
				}
			} else if (productType.equals("5")) {
				order = (CourseOrder) service.load(CourseOrder.class, id);
				if (order != null) {
					friendId = ((CourseOrder) order).getCourse().getMember().getId();
				} else {
					course = (Course) service.load(Course.class, id);
					if (course != null) {
						friendId = course.getMember().getId();
					}
				}

			} else if (productType.equals("6")) {
				order = (GoodsOrder) service.load(GoodsOrder.class, id);
				if (order != null) {
					friendId = ((GoodsOrder) order).getGoods().getMember();
				} else {
					good = (Goods) service.load(Goods.class, id);
					if (good != null) {
						friendId = good.getMember();
					}
				}
			}
			BaseMember mu = getMobileUser();
			JSONObject obj = new JSONObject();

			final List<?> list = service.findObjectBySql("from Friend fr where fr.friend.id = ? and fr.member.id = ? and fr.type = ? ", new Object[] { friendId, mu.getId(), "1" });
			if (list.size() > 0) {
				obj.accumulate("success", true).accumulate("isMember", "Y");
			} else {
				obj.accumulate("success", true).accumulate("isMember", "N");
			}
			response(obj);
		} catch (Exception e) {

			log.error("error", e);
			response(e);
		}

	}

	@SuppressWarnings("all")
	public void loadFilterCondition() {
		try {
			// 地区
			List<Area1> aList = (List<Area1>) service.findObjectBySql("from Area1 a where a.name like ? and a.type = ? ", new Object[] { "%" + city + "%", '1' });
			final JSONArray jarr1 = new JSONArray();
			if (aList != null && aList.size() > 0) {
				List<Area1> areaList = (List<Area1>) service.findObjectBySql("from Area1 a where a.parent = ?", aList.get(0).getId());
				for (final Iterator<?> it = areaList.iterator(); it.hasNext();) {
					final JSONObject obj = new JSONObject();
					final Area1 area = (Area1) it.next();
					jarr1.add(obj.accumulate("id", area.getId()).accumulate("name", area.getName()));
				}
			}
			// 健身计划类型
			List<Parameter> planTypeList = (List<Parameter>) service
					.findObjectBySql(" from Parameter p where p.parent = (select po.id from Parameter po where po.code = ?) order by p.id asc", "plan_type_c");
			final JSONArray jarr2 = new JSONArray();
			for (final Iterator<?> it = planTypeList.iterator(); it.hasNext();) {
				final JSONObject obj = new JSONObject();
				final Parameter parameter = (Parameter) it.next();
				jarr2.add(obj.accumulate("id", parameter.getId()).accumulate("name", parameter.getName()).accumulate("code", parameter.getCode()));
			}

			// 健身计划适用对象
			List<Parameter> applyObjectList = (List<Parameter>) service
					.findObjectBySql(" from Parameter p where p.parent = (select po.id from Parameter po where po.code = ?) order by p.id asc", "apply_object_c");
			final JSONArray jarr3 = new JSONArray();
			for (final Iterator<?> it = applyObjectList.iterator(); it.hasNext();) {
				final JSONObject obj = new JSONObject();
				final Parameter parameter = (Parameter) it.next();
				jarr3.add(obj.accumulate("id", parameter.getId()).accumulate("name", parameter.getName()).accumulate("code", parameter.getCode()));
			}

			// 健身计划场景
			List<Parameter> applySceneList = (List<Parameter>) service
					.findObjectBySql(" from Parameter p where p.parent = (select po.id from Parameter po where po.code = ?) order by p.id asc", "apply_scene_c");
			final JSONArray jarr4 = new JSONArray();
			for (final Iterator<?> it = applySceneList.iterator(); it.hasNext();) {
				final JSONObject obj = new JSONObject();
				final Parameter parameter = (Parameter) it.next();
				jarr4.add(obj.accumulate("id", parameter.getId()).accumulate("name", parameter.getName()).accumulate("code", parameter.getCode()));
			}

			// 健身计划周期
			List<Parameter> planCircleList = (List<Parameter>) service
					.findObjectBySql(" from Parameter p where p.parent = (select po.id from Parameter po where po.code = ?) order by p.id asc", "plan_circle_c");
			final JSONArray jarr5 = new JSONArray();
			for (final Iterator<?> it = planCircleList.iterator(); it.hasNext();) {
				final JSONObject obj = new JSONObject();
				final Parameter parameter = (Parameter) it.next();
				jarr5.add(obj.accumulate("id", parameter.getId()).accumulate("name", parameter.getName()).accumulate("code", parameter.getCode()));
			}

			// 健身卡种
			List<Parameter> proTypeList = (List<Parameter>) service
					.findObjectBySql(" from Parameter p where p.parent = (select po.id from Parameter po where po.code = ?) order by p.id asc", "card_type_c");
			final JSONArray jarr6 = new JSONArray();
			for (final Iterator<?> it = proTypeList.iterator(); it.hasNext();) {
				final JSONObject obj = new JSONObject();
				final Parameter parameter = (Parameter) it.next();
				jarr6.add(obj.accumulate("id", parameter.getId()).accumulate("name", parameter.getName()).accumulate("code", parameter.getCode()));
			}

			// 运动项目
			List<Parameter> typeList = (List<Parameter>) service
					.findObjectBySql(" from Parameter p where p.parent = (select po.id from Parameter po where po.code = ?) order by p.id asc", "course_type_c");
			final JSONArray jarr7 = new JSONArray();
			for (final Iterator<?> it = typeList.iterator(); it.hasNext();) {
				final JSONObject obj = new JSONObject();
				final Parameter parameter = (Parameter) it.next();
				jarr7.add(obj.accumulate("id", parameter.getCode()).accumulate("name", parameter.getName()).accumulate("code", parameter.getId()));
			}
			// 教练专长
			List<Parameter> coachExpertiesList = (List<Parameter>) service
					.findObjectBySql(" from Parameter p where p.parent = (select po.id from Parameter po where po.code = ?) order by p.id asc", "coach_experties_c");
			final JSONArray jarr8 = new JSONArray();
			for (final Iterator<?> it = coachExpertiesList.iterator(); it.hasNext();) {
				final JSONObject obj = new JSONObject();
				final Parameter parameter = (Parameter) it.next();
				jarr8.add(obj.accumulate("id", parameter.getId()).accumulate("name", parameter.getName()).accumulate("code", parameter.getCode()));
			}
			// 教练类型
			List<Parameter> coachTypeList = (List<Parameter>) service
					.findObjectBySql(" from Parameter p where p.parent = (select po.id from Parameter po where po.code = ?) order by p.id asc", "coach_type_c1");
			final JSONArray jarr9 = new JSONArray();
			for (final Iterator<?> it = coachTypeList.iterator(); it.hasNext();) {
				final JSONObject obj = new JSONObject();
				final Parameter parameter = (Parameter) it.next();
				jarr9.add(obj.accumulate("id", parameter.getId()).accumulate("name", parameter.getName()).accumulate("code", parameter.getCode()));
			}
			// 挑战模式
			List<Parameter> modeList = service.findParametersByCodes("active_mode_c");
			final JSONArray jarr10 = new JSONArray();
			for (final Iterator<?> it = modeList.iterator(); it.hasNext();) {
				final JSONObject obj = new JSONObject();
				final Parameter parameter = (Parameter) it.next();
				jarr10.add(obj.accumulate("id", parameter.getId()).accumulate("name", parameter.getName()).accumulate("code", parameter.getCode()));
			}
			// 挑战目标
			List<Parameter> activeTargetList = service.findParametersByCodes("active_target_c");
			final JSONArray jarr11 = new JSONArray();
			for (final Iterator<?> it = activeTargetList.iterator(); it.hasNext();) {
				final JSONObject obj = new JSONObject();
				final Parameter parameter = (Parameter) it.next();
				jarr11.add(obj.accumulate("id", parameter.getId()).accumulate("name", parameter.getName()).accumulate("code", parameter.getCode()));
			}
			// 挑战周期
			List<Parameter> activeCircleList = service.findParametersByCodes("active_circle_c");
			final JSONArray jarr12 = new JSONArray();
			for (final Iterator<?> it = activeCircleList.iterator(); it.hasNext();) {
				final JSONObject obj = new JSONObject();
				final Parameter parameter = (Parameter) it.next();
				jarr12.add(obj.accumulate("id", parameter.getId()).accumulate("name", parameter.getName()).accumulate("code", parameter.getCode()));
			}

			// 裁判类型refereeType
			List<Parameter> refereeTypeList = (List<Parameter>) service
					.findObjectBySql(" from Parameter p where p.parent = (select po.id from Parameter po where po.code = ?) order by p.id asc", "refereeType");
			final JSONArray jarr13 = new JSONArray();
			for (final Iterator<?> it = refereeTypeList.iterator(); it.hasNext();) {
				final JSONObject obj = new JSONObject();
				final Parameter parameter = (Parameter) it.next();
				jarr13.add(obj.accumulate("id", parameter.getId()).accumulate("name", parameter.getName()).accumulate("code", parameter.getCode()));
			}

			final JSONObject ret = new JSONObject();
			ret.accumulate("success", true).accumulate("area", jarr1).accumulate("planType", jarr2).accumulate("applyObject", jarr3).accumulate("applyScene", jarr4)
					.accumulate("planCircle", jarr5).accumulate("proType", jarr6).accumulate("sportType", jarr7).accumulate("coachExperties", jarr8).accumulate("coachType", jarr9)
					.accumulate("mode", jarr10).accumulate("activeTarget", jarr11).accumulate("activeCircle", jarr12).accumulate("refereeType", jarr13);
			response(ret);
		} catch (Exception e) {
			log.error("error", e);
			response(e);
		}
	}

	/**
	 * 教练发布计划验证
	 */
	public void checkCoachPlan() {
		try {
			BaseMember mu = getMobileUser();
			JSONObject obj = new JSONObject();
			Member coach = (Member) service.load(Member.class, mu.getId());
			obj.accumulate("success", true).accumulate("hasValid", coach.getHasValid());
			response(obj);
		} catch (Exception e) {
			log.error("error", e);
			response(e);
		}

	}

	public void SaveHeartRate() {
		try {
			final JSONArray arr = JSONArray.fromObject(URLDecoder.decode(jsons, "UTF-8"));
			final JSONObject obj = arr.getJSONObject(0);
			PresentHeartRate phr = new PresentHeartRate();
			SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			phr = service.loadPresentHeartRate(obj.getLong("member"), sdf1.parse(obj.getString("traindate")));

			phr.setMember((Member) service.load(Member.class, obj.getLong("member")));
			phr.setEnd_date(sdf2.parse(obj.getString("traindate") + " " + obj.getString("EndTime")));
			phr.setStart_date(sdf2.parse(obj.getString("traindate") + " " + obj.getString("StartTime")));
			phr.setTime_diff(obj.getInt("TimeDiffer"));
			phr.setTrain_date(sdf1.parse(obj.getString("traindate")));
			phr.setHeartRates(obj.getString("heartRates"));

			phr = (PresentHeartRate) service.saveOrUpdate(phr);
			final JSONObject ret = new JSONObject();
			ret.accumulate("success", true).accumulate("", phr.getId());
			response(ret);
		} catch (Exception e) {
			log.error("error", e);
			response(e);
		}
	}

	public void ShowHeartRate() {
		try {
			final JSONObject obj = new JSONObject();
			PresentHeartRate phr = new PresentHeartRate();
			SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			phr = service.loadPresentHeartRate(Long.parseLong(member), sdf1.parse(traindate));
			if (null == phr.getId()) {
				obj.accumulate("success", false).accumulate("message", "查无资料");
			} else {
				final JSONObject obj1 = new JSONObject();
				obj1.accumulate("member", phr.getMember().getId()).accumulate("traindate", sdf1.format(phr.getTrain_date()))
						.accumulate("StartTime", sdf2.format(phr.getStart_date()).substring(11, 19)).accumulate("EndTime", sdf2.format(phr.getEnd_date()).substring(11, 19))
						.accumulate("heartRates", phr.getHeartRates()).accumulate("TimeDiffer", phr.getTime_diff());
				obj.accumulate("items", obj1);
				obj.accumulate("success", true).accumulate("message", "OK");
			}
			response(obj);
		} catch (Exception e) {
			log.error("error", e);
			response(e);
		}

	}

	/**
	 * 健身达人
	 */

	@SuppressWarnings("unchecked")
	public void showTalent() {
		try {
			StringBuffer sql = new StringBuffer(
					"select * from (select m.id,m.city,m.name,m.image,count(m.id) as timeNum ,sum(t.times) as time ,COUNT(t.confrim)/m.workouttimes *100 as finishrate from tb_member m JOIN tb_plan_record t ON m.id=t.partake where ");
			Object[] args = new Object[1];
			if (queryCriteria == null) {
				queryCriteria = "A";
			}
			if (city == null || city.equals("")) {
				if (queryCriteria.equals("A")) {
					sql.append("m.city is not ? and t.done_date<=SYSDATE() group by m.id ) b order by b.timeNum desc");
				} else if (queryCriteria.equals("B")) {
					sql.append("m.city is not ? and t.done_date<=SYSDATE() group by m.id ) b order by b.time desc");
				} else {
					sql.append("m.city is not ? and t.done_date<=SYSDATE() group by m.id ) b order by b.finishrate desc");
				}
			} else if (city != null && !"".equals(city)) {
				args[0] = city;
				if (queryCriteria.equals("A")) {
					sql.append("m.city = ? and t.done_date<=SYSDATE() group by m.id ) b order by b.timeNum desc");
				} else if (queryCriteria.equals("B")) {
					sql.append("m.city = ? and t.done_date<=SYSDATE() group by m.id ) b order by b.time desc");
				} else {
					sql.append("m.city = ? and t.done_date<=SYSDATE() group by m.id ) b order by b.finishrate desc");
				}
			}
			pageInfo = service.findPageBySql(sql.toString(), pageInfo, args);
			final JSONArray jar = new JSONArray();
			final Member m = new Member();
			DecimalFormat df = new DecimalFormat("#.00");
			for (final Iterator<?> it = pageInfo.getItems().iterator(); it.hasNext();) {
				final Map<String, Object> map1 = (Map<String, Object>) it.next();
				Long mid = Long.parseLong(map1.get("id").toString());
				m.setId(mid);
				m.setName((String) map1.get("name"));
				m.setImage((String) map1.get("image"));
				m.setCity((String) map1.get("city"));
				final JSONObject obj1 = new JSONObject();
				obj1.accumulate("name", m.getName()).accumulate("image", m.getImage()).accumulate("count", map1.get("timeNum") == null ? 0 : map1.get("timeNum").toString())
						.accumulate("time", map1.get("time") == null ? 0 : String.valueOf(df.format(Double.parseDouble(map1.get("time").toString()) / 60)))
						.accumulate("finishrate", map1.get("finishrate") == null ? 0 : map1.get("finishrate").toString()).accumulate("city", m.getCity())
						.accumulate("id", m.getId());

				jar.add(obj1);
			}

			final JSONObject obj = new JSONObject();
			obj.accumulate("success", true).accumulate("message", "OK").accumulate("items", jar).accumulate("pageInfo", getJsonForPageInfo());
			response(obj);
		} catch (Exception e) {
			log.error("error", e);
			response(e);
		}
	}

	/**
	 * 20160426 add by 刘伟权 完善用户资料
	 */
	@SuppressWarnings("unused")
	public void restoreUserData() {
		try {
			// final JSONArray objs =
			// JSONArray.fromObject(URLDecoder.decode(jsons, "UTF-8"));
			// for (final Iterator<?> it = objs.iterator(); it.hasNext();) {
			// final JSONObject obj = (JSONObject) it.next();
			// }
			final String userType = request.getParameter("userType");
			final String gander = request.getParameter("gander");
			final String birthday = request.getParameter("birthday");
			final String userHeight = request.getParameter("userHeight");
			final String userWidth = request.getParameter("userWidth");
			final String userRate = request.getParameter("userRate");
			final String nickName = request.getParameter("nickName");
			final String key = request.getParameter("id");
			BaseMember mobileUser = getMobileUser();
			final Member m = (Member) service.load(Member.class, Long.parseLong(key));
			final Setting setting = service.loadSetting(Long.parseLong(key));
			m.setRole(userType);
			m.setSex(gander);
			m.setBirthday(ymd.parse(birthday));
			m.setName(nickName);
			setting.setHeight(new Integer(userHeight));
			setting.setWeight(new Double(userWidth));
			setting.setHeart(new Integer(userRate));
			service.saveOrUpdate(m);
			service.saveOrUpdate(setting);

			final JSONObject obj = new JSONObject();

			try {
				final Setting set = service.loadSetting(m.getId());
				obj.accumulate("key", m.getId());
				addMobileUser(m);
				// 增加教练未阅读消息总数
				final Integer messageCount = service.findMessageCount(m.getId());
				if (m.getRole().equals("S")) { // 教练
					JSONObject gradeJson = getGradeJson(m);
					obj.accumulate("success", true).accumulate("id", m.getId()).accumulate("name", getString(m.getName())).accumulate("nick", getString(m.getNick()))
							.accumulate("image", getString(m.getImage())).accumulate("type", m.getRole()).accumulate("mobilephone", getString(m.getMobilephone()))
							.accumulate("mobileValid", m.getMobileValid()).accumulate("message", uuid).accumulate("grade", getInteger(m.getGrade()))
							.accumulate("score", gradeJson.get("avgGrade")).accumulate("appraiseCount", gradeJson.get("appraiseCount"))
							// 取未阅读消息总数
							.accumulate("messageCount", messageCount)
							// 20160302
							.accumulate("sex", m.getSex());
					List<?> records = service.findObjectBySql("from TrainRecord r where r.partake.id =" + m.getId() + " order by r.doneDate desc");
					obj.accumulate("records", null == records ? 0 : records.size());
					TrainRecord lastRecord = new TrainRecord();
					if (null != records && 0 != records.size()) {
						lastRecord = (TrainRecord) records.get(0);
						obj.accumulate("weight", null == lastRecord.getWeight() ? set.getWeight() : lastRecord.getWeight())
								.accumulate("height", null == lastRecord.getHeight() ? set.getHeight() : lastRecord.getHeight())
								.accumulate("waist", null == lastRecord.getWaist() ? set.getWaistline() : lastRecord.getWaist()).accumulate("hip", lastRecord.getHip());
					} else {
						obj.accumulate("weight", set.getWeight()).accumulate("height", set.getHeight()).accumulate("waist", set.getWaistline()).accumulate("hip", null);
					}
					final JSONArray jarr = new JSONArray();
					// 取得其所有课程及每个课程购买次数
					final Set<Product> products = m.getProducts();
					for (final Product p : products) {
						final JSONObject obj2 = new JSONObject();
						obj2.accumulate("id", p.getId()).accumulate("name", p.getName()).accumulate("memo", "").accumulate("buys",
								p.getOrders() == null ? 0 : p.getOrders().size());
						jarr.add(obj2);
					}
					obj.accumulate("courses", jarr);
				} else { // 会员
					// 取得最新的活动数据
					final List<?> list = service.findObjectBySql(
							"from ActiveOrder ap where ap.member.id = ? and ap.orderEndTime >= ? and ap.status <> '0' order by ap.orderEndTime desc", m.getId(), new Date());
					final JSONArray actArr = new JSONArray();
					for (final Iterator<?> it = list.iterator(); it.hasNext();) {
						final ActiveOrder ap = (ActiveOrder) it.next();
						final Active act = ap.getActive();
						final JSONObject obj1 = new JSONObject();
						obj1.accumulate("id", ap.getId()).accumulate("member", getMemberJson(act.getCreator())).accumulate("target", act.getTarget())
								.accumulate("days", getInteger(act.getDays())).accumulate("action", getString(act.getAction())).accumulate("category", getString(act.getCategory()))
								.accumulate("name", act.getName()).accumulate("value", getDouble(act.getValue())).accumulate("judge", ap.getJudge())
								.accumulate("judgeMode", act.getJudgeMode()).accumulate("mode", act.getMode()).accumulate("teamNum", getInteger(act.getTeamNum()))
								.accumulate("award", getString(act.getAward())).accumulate("institution", getJsonForMember(act.getInstitution()))
								.accumulate("amerceMoney", getDouble(act.getAmerceMoney())).accumulate("startDate", ymd.format(ap.getOrderStartTime()))
								.accumulate("endDate", ymd.format(ap.getOrderEndTime())).accumulate("activeimage", act.getImage());
						actArr.add(obj1);
					}
					final Member coach = m.getCoach();
					final JSONObject coachObj = new JSONObject();
					if (coach != null) coachObj.accumulate("id", coach.getId()).accumulate("nick", getString(coach.getNick())).accumulate("name", getString(coach.getName()))
							.accumulate("image", getString(coach.getImage())).accumulate("appraiseCount", getInteger(coach.getCountEmp())).accumulate("score", coach.getAvgGrade());
					obj.accumulate("success", true).accumulate("id", m.getId()).accumulate("name", getString(m.getName())).accumulate("nick", getString(m.getNick()))
							.accumulate("image", getString(m.getImage())).accumulate("type", m.getRole()).accumulate("grade", m.getGrade())
							.accumulate("mobilephone", getString(m.getMobilephone())).accumulate("mobileValid", m.getMobileValid()).accumulate("message", uuid)
							.accumulate("sex", m.getSex()).accumulate("actions", actArr).accumulate("coach", coachObj)
							// 未阅读消息总数
							.accumulate("messageCount", messageCount);

					// final Setting set = service.loadSetting(member.getId());
					List<?> records = service.findObjectBySql("from TrainRecord r where r.partake.id =" + m.getId() + " order by r.doneDate desc");
					obj.accumulate("records", null == records ? 0 : records.size());
					TrainRecord lastRecord = new TrainRecord();
					if (null != records && 0 != records.size()) {
						lastRecord = (TrainRecord) records.get(0);
						obj.accumulate("weight", null == lastRecord.getWeight() ? set.getWeight() : lastRecord.getWeight())
								.accumulate("height", null == lastRecord.getHeight() ? set.getHeight() : lastRecord.getHeight())
								.accumulate("waist", null == lastRecord.getWaist() ? set.getWaistline() : lastRecord.getWaist()).accumulate("hip", lastRecord.getHip());
					} else {
						obj.accumulate("weight", set.getWeight()).accumulate("height", set.getHeight()).accumulate("waist", set.getWaistline()).accumulate("hip", null);
					}

				}
				if (set.getHeart() == null) {
					obj.accumulate("count", "0").accumulate("heart", 0);
				} else {
					obj.accumulate("count", "1").accumulate("heart", set.getHeart());
				}
				int i = 0;
				Calendar cal = Calendar.getInstance();
				if (m.getBirthday() != null && !cal.before(m.getBirthday())) {
					int year1 = cal.get(Calendar.YEAR);
					int month1 = cal.get(Calendar.MONTH) + 1;
					int day1 = cal.get(Calendar.DAY_OF_MONTH);
					cal.setTime(m.getBirthday());
					int year2 = cal.get(Calendar.YEAR);
					int month2 = cal.get(Calendar.MONTH) + 1;
					int day2 = cal.get(Calendar.DAY_OF_MONTH);
					i = year1 - year2;
					if (month1 <= month2) {
						if (month1 == month2) {
							if (day1 < day2) {
								i--;
							}
						} else {
							i--;
						}
					}
				}
				obj.accumulate("age", i);
				obj.accumulate("bmiHigh", set.getBmiHigh()).accumulate("bmiLow", set.getBmiLow());
				response(obj);
			} catch (Exception e) {
				log.error("error", e);
				response(e);
			}

			obj.accumulate("success", true).accumulate("message", "OK");
			response(obj);
		} catch (Exception e) {
			log.error("error", e);
			response(e);
		}

	}

	@SuppressWarnings("all")
	public void TribeAddUser() throws Exception {
		JSONObject obj = new JSONObject();
		try {
			final List<Member> mlist = (List<Member>) service.findObjectBySql("from Member m where m.nick=?", nick);
			if (mlist.isEmpty()) {
				obj.accumulate("success", false).accumulate("message", "添加用户失败");
				return;
			} else {
				final Long mid = mlist.get(0).getId();
				String url = "http://gw.api.taobao.com/router/rest";
				TaobaoClient tclient = new DefaultTaobaoClient(url, "23330566", "0c6b77f937d49f3a14ca98a6316d110e");
				OpenimTribeInviteRequest otir = new OpenimTribeInviteRequest();
				otir.setTribeId(tribeid);
				OpenImUser ufo1 = new OpenImUser();
				List<OpenImUser> list = new ArrayList<OpenImUser>();
				ufo1.setUid(mid.toString());
				ufo1.setTaobaoAccount(false);
				ufo1.setAppKey("23330566");
				list.add(ufo1);
				otir.setMembers(list);
				OpenImUser ufo = new OpenImUser();
				ufo.setUid(getMobileUser().getId().toString());
				ufo.setTaobaoAccount(false);
				ufo.setAppKey("23330566");
				otir.setUser(ufo);
				OpenimTribeInviteResponse otir1 = tclient.execute(otir);
				obj.accumulate("success", true).accumulate("message", "该用户已成功加入");
				response(obj);
			}
		} catch (Exception e) {
			// TODO: handle exception
			log.error("error", e);
			response(e);
		}
	}

	public void getOrderAddress() {
		JSONObject obj = new JSONObject();
		try {
			Member m = (Member) service.load(Member.class, getMobileUser().getId());
			m.setAddress(address);
			service.saveOrUpdate(m);
			obj.accumulate("success", true).accumulate("message", "OK");
		} catch (Exception e) {
			log.error("error", e);
			obj.accumulate("success", false).accumulate("message", "error");
			response(e);
		}
		response(obj);
	}

	public String getMember() {
		return member;
	}

	public void setMember(String member) {
		this.member = member;
	}

	public String getTraindate() {
		return traindate;
	}

	public void setTraindate(String traindate) {
		this.traindate = traindate;
	}

	public Integer getWorkoutTimes() {
		return workoutTimes;
	}

	public void setWorkoutTimes(Integer workoutTimes) {
		this.workoutTimes = workoutTimes;
	}

	public String getQueryCriteria() {
		return queryCriteria;
	}

	public void setQueryCriteria(String queryCriteria) {
		this.queryCriteria = queryCriteria;
	}

}
