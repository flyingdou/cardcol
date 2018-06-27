package com.cardcolv45.mobile.action;

import java.io.File;
import java.net.URLDecoder;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.aliyun.api.gateway.demo.enums.Constantsms;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.http.ProtocolType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.aliyuncs.push.model.v20160801.PushRequest;
import com.aliyuncs.push.model.v20160801.PushResponse;
import com.cardcol.web.utils.Base64Util;
import com.databaseConnection.DataBaseConnection;
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
import com.freegym.web.system.Tickling;
import com.freegym.web.utils.BodyUtils;
import com.freegym.web.utils.DBConstant;
import com.freegym.web.utils.EasyUtils;
import com.freegym.web.utils.JSONUtils;
import com.sanmen.web.core.bean.BaseMember;
import com.sanmen.web.core.common.LogicException;
import com.sanmen.web.core.common.PageInfo;
import com.sanmen.web.core.system.Parameter;
import com.sanmen.web.core.utils.LnglatUtil;
import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.domain.OpenImUser;
import com.taobao.api.domain.Userinfos;
import com.taobao.api.request.OpenimCustmsgPushRequest;
import com.taobao.api.request.OpenimCustmsgPushRequest.CustMsg;
import com.taobao.api.request.OpenimTribeInviteRequest;
import com.taobao.api.request.OpenimUsersAddRequest;
import com.taobao.api.request.OpenimUsersGetRequest;
import com.taobao.api.request.OpenimUsersUpdateRequest;
import com.taobao.api.response.OpenimCustmsgPushResponse;
import com.taobao.api.response.OpenimTribeInviteResponse;
import com.taobao.api.response.OpenimUsersAddResponse;
import com.taobao.api.response.OpenimUsersGetResponse;
import com.taobao.api.response.OpenimUsersUpdateResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 会员Action
 * 
 * @author hw
 */
@Namespace("")
@InterceptorRefs({ @InterceptorRef("mobileStack") })
public class MMemberV45ManageAction extends BasicJsonAction implements Constantsms {

	private static final long serialVersionUID = -4900715126840183203L;

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
	protected String province;
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

	private File imageFront, imageSide, imageBack, memberImage;

	private String imageFrontFileName, imageSideFileName, imageBackFileName, memberImageFileName;

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

	private Long tribeId;

	private String address;

	/**
	 * 更新用户经纬度信息
	 */
	public void updateLocation() {
		JSONObject ret = new JSONObject();
		try {
			// 处理请求参数
			String json = request.getParameter("json");
			JSONObject obj = JSONObject.fromObject(URLDecoder.decode(json, "UTF-8"));
			Member member = (Member) getMobileUser();
			String updateLngLat = "update tb_member set longitude = ?, latitude = ? where id = ?";
			Object[] objx = { obj.get("longitude").toString(), obj.get("latitude").toString(), member.getId() };
			int i = DataBaseConnection.updateData(updateLngLat, objx);
			if (i > 0) {
				ret.accumulate("success", true).accumulate("message", "OK");
			} else {
				ret.accumulate("success", false).accumulate("message", "更新用户经纬度信息异常");
			}

		} catch (Exception e) {
			ret.accumulate("success", false).accumulate("message", e);
		}
		response(ret);
	}

	/**
	 * 根据用户id，查询用户头像
	 */
	public void getImage() {
		JSONObject ret = new JSONObject();
		try {
			// 处理请求参数
			JSONObject param = JSONArray.fromObject(URLDecoder.decode(request.getParameter("jsons"), "UTF-8"))
					.getJSONObject(0);
			String base_url = "https://www.ecartoon.com.cn/picture/";

			// 用户
			Member member = (Member) service.load(Member.class, param.getLong("id"));
			JSONObject obj = new JSONObject();
			obj.accumulate("image", base_url + member.getImage()).accumulate("nick", member.getName());


			ret.accumulate("success", true).accumulate("message", "OK").accumulate("member", obj);
		} catch (Exception e) {
			ret.accumulate("success", false).accumulate("message", e.toString());
			e.printStackTrace();
		}

		// 返回数据
		response(ret);
	}

	/**
	 * 加载我的资料
	 */
	public void findMemberDetail() {
		// 输出参数应该包括“身高、体重、腰围、臀围”字段
		Map<String, Object> map = queryMemberDetail(getMobileUser().getId().toString());
		// 查询用户的训练记录，并取其中的“身高、体重、腰围、臀围”字段
		String sql = "select height,weight,waist,hip from tb_plan_record where partake = ? order by done_date desc limit 0,1";
		Object[] obj1 = { getMobileUser().getId() };
		List<Map<String, Object>> lists = DataBaseConnection.getList(sql, obj1);
		if (lists != null && lists.size() > 0) {
			map.put("trainRecord", lists.get(0));
		} else {
			map.put("trainRecord", null);
		}

		map.put("pickAccount", service.queryPickAccount(getMobileUser().getId().toString()));

		// 加入我的二维码
		session.setAttribute("spath", 7);
		Member member = (Member) getMobileUser();// 获取当前登录用户
		String mid = Base64Util.encode(member.getId().toString());
		String url = "http://www.cardcol.com/download.jsp?app=cardcol&id=" + mid;

		// 查询该教练的各项总评分
		JSONObject avgObj = new JSONObject();
		String AvgScore = "select AVG(e.totality_score) totalityScore, AVG(e.device_score) deviceScore,AVG(e.even_score) evenScore,AVG(e.service_score) serviceScore from tb_sign_in s,tb_member_evaluate e where e.signIn = s.id and s.memberAudit = ?";
		Object[] objt = { getMobileUser().getId() };
		Map<String, Object> AvgScoreMap = DataBaseConnection.getOne(AvgScore, objt);

		if (AvgScoreMap != null) {
			if (AvgScoreMap.get("totalityScore") != null) {
				avgObj.accumulate("totalityScore",
						Math.floor(Double.parseDouble(String.valueOf(AvgScoreMap.get("totalityScore")))));
			}
			if (AvgScoreMap.get("deviceScore") != null) {
				avgObj.accumulate("deviceScore",
						Math.floor(Double.parseDouble(String.valueOf(AvgScoreMap.get("deviceScore")))));
			}
			if (AvgScoreMap.get("evenScore") != null) {
				avgObj.accumulate("evenScore",
						Math.floor(Double.parseDouble(String.valueOf(AvgScoreMap.get("evenScore")))));
			}
			if (AvgScoreMap.get("evenScore") != null) {
				avgObj.accumulate("serviceScore",
						Math.floor(Double.parseDouble(String.valueOf(AvgScoreMap.get("serviceScore")))));
			}

		}
		JSONObject obj = new JSONObject();
		obj.accumulate("success", true).accumulate("url", url).accumulate("memberDetail", map).accumulate("avgScore",
				avgObj);
		;
		response(obj);
	}

	/**
	 * 用户个人信息接口
	 */
	@SuppressWarnings("unused")
	public void findMemberDetail45() {
		BaseMember bm = getMobileUser();
		Member member = (Member) service.load(Member.class, Long.valueOf(bm.getId()));
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		JSONObject ret = new JSONObject();

		try {
			final Setting set = service.loadSetting(member.getId());
			ret.accumulate("key", member.getId());
			addMobileUser(member);
			// 增加教练未阅读消息总数
			final Integer messageCount = service.findMessageCount(member.getId());
			String birthday = member.getBirthday() == null ? "" : sdf.format(member.getBirthday());// 出生日期
			if (member.getRole().equals("S")) { // 教练
				JSONObject gradeJson = getGradeJson(member);
				ret.accumulate("success", true).accumulate("id", member.getId())
						.accumulate("name", getString(member.getName()))
						.accumulate("image", getString(member.getImage())).accumulate("type", member.getRole())
						.accumulate("mobilephone", getString(member.getMobilephone()))
						.accumulate("mobileValid", member.getMobileValid()).accumulate("message", uuid)
						.accumulate("birthday", getString(birthday)).accumulate("province", member.getProvince())
						.accumulate("city", member.getCity()).accumulate("county", member.getCounty())
						.accumulate("grade", getInteger(member.getGrade()))
						.accumulate("score", gradeJson.get("avgGrade"))
						.accumulate("appraiseCount", gradeJson.get("appraiseCount"))
						// 取未阅读消息总数
						.accumulate("messageCount", messageCount)
						// 20160302

						.accumulate("sex", member.getSex());
				// 在用户登录时返回身高、体重、腰围、臀围的最新数据
				List<?> records = service.findObjectBySql("from TrainRecord r where r.partake.id =" + member.getId()
						+ " and r.doneDate <= '" + sdf.format(new Date()) + "' order by r.doneDate desc");
				String sqlxx = "select count(*) count from TB_PRESENT_HEARTRATE  where member = ? ";
				Object[] objxx = { member.getId() };
				Map<String, Object> mapxx = DataBaseConnection.getOne(sqlxx, objxx);

				ret.accumulate("records", null == mapxx ? 0 : mapxx.get("count"));
				TrainRecord lastRecord = new TrainRecord();
				if (null != records && 0 != records.size()) {
					lastRecord = (TrainRecord) records.get(0);
					ret.accumulate("weight", null == lastRecord.getWeight() ? set.getWeight() : lastRecord.getWeight())
							.accumulate("height",
									null == lastRecord.getHeight() ? set.getHeight() : lastRecord.getHeight())
							.accumulate("waist",
									null == lastRecord.getWaist() ? set.getWaistline() : lastRecord.getWaist())
							.accumulate("hip", lastRecord.getHip());
				} else {
					ret.accumulate("weight", set.getWeight()).accumulate("height", set.getHeight())
							.accumulate("waist", set.getWaistline()).accumulate("hip", null);
				}

				final JSONArray jarr = new JSONArray();
				// 取得其所有课程及每个课程购买次数
				final Set<Product> products = member.getProducts();
				for (final Product p : products) {
					final JSONObject obj = new JSONObject();
					obj.accumulate("id", p.getId()).accumulate("name", p.getName()).accumulate("memo", "")
							.accumulate("buys", p.getOrders() == null ? 0 : p.getOrders().size());
					jarr.add(obj);
				}
				ret.accumulate("courses", jarr);
			} else { // 会员
				// 取得最新的活动数据
				final List<?> list = service.findObjectBySql(
						"from ActiveOrder ap where ap.member.id = ? and ap.orderEndTime >= ? and ap.status <> '0' order by ap.orderEndTime desc",
						member.getId(), new Date());
				final JSONArray actArr = new JSONArray();
				for (final Iterator<?> it = list.iterator(); it.hasNext();) {
					final ActiveOrder ap = (ActiveOrder) it.next();
					final Active act = ap.getActive();
					final JSONObject obj = new JSONObject();
					obj.accumulate("id", ap.getId()).accumulate("member", getMemberJson(act.getCreator()))
							.accumulate("target", act.getTarget()).accumulate("days", getInteger(act.getDays()))
							.accumulate("action", getString(act.getAction()))
							.accumulate("category", getString(act.getCategory())).accumulate("name", act.getName())
							.accumulate("value", getDouble(act.getValue())).accumulate("judge", ap.getJudge())
							.accumulate("judgeMode", act.getJudgeMode()).accumulate("mode", act.getMode())
							.accumulate("teamNum", getInteger(act.getTeamNum()))
							.accumulate("award", getString(act.getAward()))
							.accumulate("institution", getJsonForMember(act.getInstitution()))
							.accumulate("province", member.getProvince()).accumulate("city", member.getCity())
							.accumulate("county", member.getCounty())
							.accumulate("amerceMoney", getDouble(act.getAmerceMoney()))
							.accumulate("startDate", ymd.format(ap.getOrderStartTime()))
							.accumulate("endDate", ymd.format(ap.getOrderEndTime()))
							.accumulate("activeimage", act.getImage());
					actArr.add(obj);
				}
				final Member coach = member.getCoach();
				final JSONObject coachObj = new JSONObject();
				if (coach != null) {
					// 查询该教练的各项总评分
					JSONObject avgObj = new JSONObject();
					String AvgScore = "select AVG(e.totality_score) totalityScore, AVG(e.device_score) deviceScore,AVG(e.even_score) evenScore,AVG(e.service_score) serviceScore from tb_sign_in s,tb_member_evaluate e where e.signIn = s.id and s.memberAudit = ?";
					Object[] objt = { coach.getId() };
					Map<String, Object> AvgScoreMap = DataBaseConnection.getOne(AvgScore, objt);

					if (AvgScoreMap != null) {
						avgObj.accumulate("totalityScore", Math.floor(Double.parseDouble(String.valueOf(
								AvgScoreMap.get("totalityScore") == null ? 0 : AvgScoreMap.get("totalityScore")))))
								.accumulate("deviceScore", Math.floor(Double.parseDouble(String.valueOf(
										AvgScoreMap.get("deviceScore") == null ? 0 : AvgScoreMap.get("deviceScore")))))
								.accumulate("evenScore", Math.floor(Double.parseDouble(String.valueOf(
										AvgScoreMap.get("evenScore") == null ? 0 : AvgScoreMap.get("evenScore")))))
								.accumulate("serviceScore",
										Math.floor(Double
												.parseDouble(String.valueOf(AvgScoreMap.get("serviceScore") == null ? 0
														: AvgScoreMap.get("serviceScore")))));
					}
					coachObj.accumulate("id", coach.getId())/* .accumulate("nick", getString(coach.getNick())) */
							.accumulate("name", getString(coach.getName()))
							.accumulate("image", getString(coach.getImage()))
							.accumulate("appraiseCount", getInteger(coach.getCountEmp()))
							.accumulate("score", coach.getAvgGrade()).accumulate("avgScore", avgObj);
				}

				ret.accumulate("success", true).accumulate("id", member.getId()).accumulate("name",
						getString(member.getName()))/* .accumulate("nick", getString(member.getNick())) */
						.accumulate("image", getString(member.getImage())).accumulate("type", member.getRole())
						.accumulate("grade", member.getGrade())
						.accumulate("mobilephone", getString(member.getMobilephone()))
						.accumulate("mobileValid", member.getMobileValid()).accumulate("message", uuid)
						.accumulate("birthday", birthday).accumulate("sex", member.getSex())
						.accumulate("province", member.getProvince()).accumulate("city", member.getCity())
						.accumulate("county", member.getCounty()).accumulate("actions", actArr)
						.accumulate("coach", coachObj)
						// 未阅读消息总数
						.accumulate("messageCount", messageCount);

				final Setting set1 = service.loadSetting(member.getId());
				List<?> records = service.findObjectBySql("from TrainRecord r where r.partake.id =" + member.getId()
						+ " and r.doneDate <= '" + sdf.format(new Date()) + "' order by r.doneDate desc");
				String sqlxx = "select count(*) count from TB_PRESENT_HEARTRATE  where member = ? ";
				Object[] objxx = { member.getId() };
				Map<String, Object> mapxx = DataBaseConnection.getOne(sqlxx, objxx);
				ret.accumulate("records", 0 == records.size() ? mapxx.get("count") : records.size());
				TrainRecord lastRecord = new TrainRecord();
				if (null != records && 0 != records.size()) {
					lastRecord = (TrainRecord) records.get(0);
					ret.accumulate("weight", null == lastRecord.getWeight() ? set1.getWeight() : lastRecord.getWeight())
							.accumulate("height",
									null == lastRecord.getHeight() ? set1.getHeight() : lastRecord.getHeight())
							.accumulate("waist",
									null == lastRecord.getWaist() ? set1.getWaistline() : lastRecord.getWaist())
							.accumulate("hip", lastRecord.getHip());
				} else {
					ret.accumulate("weight", set1.getWeight()).accumulate("height", set1.getHeight())
							.accumulate("waist", set1.getWaistline()).accumulate("hip", null);
				}

			}
			if (set.getHeart() == null) {
				ret.accumulate("count", "0").accumulate("heart", 0);
			} else {
				ret.accumulate("count", "1").accumulate("heart", set.getHeart());
			}
			int i = 0;
			Calendar cal = Calendar.getInstance();
			if (member.getBirthday() != null && !cal.before(member.getBirthday())) {
				int year1 = cal.get(Calendar.YEAR);
				int month1 = cal.get(Calendar.MONTH) + 1;
				int day1 = cal.get(Calendar.DAY_OF_MONTH);
				cal.setTime(member.getBirthday());
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
			// 20160701
			String url = "http://gw.api.taobao.com/router/rest";
			TaobaoClient client = new DefaultTaobaoClient(url, "23330566", "0c6b77f937d49f3a14ca98a6316d110e");
			OpenimUsersGetRequest req = new OpenimUsersGetRequest();
			req.setUserids(member.getId().toString());
			OpenimUsersGetResponse response = client.execute(req);

			if (response.getUserinfos() != null && !response.getUserinfos().isEmpty()) {
				String taobaoid = response.getUserinfos().get(0).getUserid();
				String taobaopwd = response.getUserinfos().get(0).getPassword();
				String iconurl = "http://" + request.getServerName() + ":" + request.getServerPort() + "/picture/"
						+ member.getImage();
				ret.accumulate("taobaoid", taobaoid).accumulate("taobaopwd", taobaopwd);
				TaobaoClient tclient = new DefaultTaobaoClient(url, "23330566", "0c6b77f937d49f3a14ca98a6316d110e");
				OpenimUsersUpdateRequest req_update = new OpenimUsersUpdateRequest();
				Userinfos user = new Userinfos();
				user.setNick(member.getName());
				user.setStatus((long) 0);
				user.setIconUrl("http://" + request.getServerName() + ":" + request.getServerPort() + "/picture/"
						+ member.getImage());
				user.setEmail(member.getEmail());
				user.setMobile(member.getMobilephone());
				user.setTaobaoid(member.getId().toString());
				user.setUserid(member.getId().toString());
				user.setPassword(member.getId().toString());
				user.setRemark("demo");
				user.setExtra("{}");
				user.setCareer("demo");
				user.setGmtModified("demo");
				user.setVip("{}");
				user.setAddress(member.getAddress());
				user.setName(member.getName());
				user.setAge(member.getBirthday() == null ? 20
						: (new Date().getTime() - member.getBirthday().getTime()) / (1000 * 60 * 60 * 24) == 0 ? 25
								: (new Date().getTime() - member.getBirthday().getTime()) / (1000 * 60 * 60 * 24));
				user.setGender("M".equals(member.getSex()) || "F".equals(member.getSex()) ? member.getSex()
						: "男".equals(member.getSex()) ? "M" : "F");
				user.setWechat("demo");
				user.setQq("demo");
				user.setWeibo("demo");
				List<Userinfos> ulist = new ArrayList<Userinfos>();
				ulist.add(user);
				req_update.setUserinfos(ulist);
				OpenimUsersUpdateResponse ouar = tclient.execute(req_update);
				// }
			} else {
				TaobaoClient client_register = new DefaultTaobaoClient(url, "23330566",
						"0c6b77f937d49f3a14ca98a6316d110e");
				OpenimUsersAddRequest req_register = new OpenimUsersAddRequest();
				Userinfos user = new Userinfos();
				user.setNick(member.getNick());
				user.setStatus((long) 0);
				user.setIconUrl("http://" + request.getServerName() + ":" + request.getServerPort() + "/picture/"
						+ member.getImage());

				user.setEmail(member.getEmail());
				user.setMobile(member.getMobilephone());
				user.setTaobaoid(member.getId().toString());
				user.setUserid(member.getId().toString());
				user.setPassword(member.getId().toString());
				user.setRemark("demo");
				user.setExtra("{}");
				user.setCareer("demo");
				user.setGmtModified("demo");
				user.setVip("{}");
				user.setAddress(member.getAddress());
				user.setName(member.getName());
				user.setAge(member.getBirthday() == null ? 20
						: (new Date().getTime() - member.getBirthday().getTime()) / (1000 * 60 * 60 * 24) == 0 ? 25
								: (new Date().getTime() - member.getBirthday().getTime()) / (1000 * 60 * 60 * 24));
				user.setGender("M".equals(member.getSex()) || "F".equals(member.getSex()) ? member.getSex()
						: "男".equals(member.getSex()) ? "M" : "F");
				user.setWechat("demo");
				user.setQq("demo");
				user.setWeibo("demo");
				List<Userinfos> ulist = new ArrayList<Userinfos>();
				ulist.add(user);
				req_register.setUserinfos(ulist);
				OpenimUsersAddResponse response_register = client_register.execute(req_register);
				if (response_register.getUidSucc() != null) {
					url = "http://gw.api.taobao.com/router/rest";
					client = new DefaultTaobaoClient(url, "23330566", "0c6b77f937d49f3a14ca98a6316d110e");
					req = new OpenimUsersGetRequest();
					req.setUserids(member.getId().toString());
					response = client.execute(req);
					ret.accumulate("taobaoid", response.getUserinfos().get(0).getUserid()).accumulate("taobaopwd",
							response.getUserinfos().get(0).getPassword());
				}
			}
			// 查询提现绑定账户（支付宝或银行卡）
			Map<String, Object> map = service.queryPickAccount(member.getId().toString());
			ret.accumulate("pickAccount", map);
			ret.accumulate("age", i);
			ret.accumulate("bmiHigh", set.getBmiHigh()).accumulate("bmiLow", set.getBmiLow());
			ret.accumulate("signCount", service
					.queryForLong("SELECT count(id) FROM tb_present_heartrate where member = ?", member.getId()));
			response(ret);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (ApiException e) {
			e.printStackTrace();
		}
	}

	// 查询用户基本信息
	private Map<String, Object> queryMemberDetail(String id) {
		String sql = "SELECT a.name,a.image,a.mobilephone,a.coach,a.province,a.city,a.county,a.longitude,a.latitude,"
				+ " count(b.member) AS signNum FROM tb_member a,tb_present_heartrate b WHERE a.id = b.member AND a.id = "
				+ id;
		Map<String, Object> map = DataBaseConnection.getOne(sql, null);
		return map;
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
			dc.add(this.or(Restrictions.like("name", where), Restrictions.like("nick", where),
					Restrictions.like("email", where), Restrictions.like("mobilephone", where)));
		} else {
			if (city != null && !"".equals(city))
				dc.add(Restrictions.eq("city", city));

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
				 * "((this_.id in (select member from tb_course_info where type =" + typeId +
				 * ")) or " +
				 * " (this_.id in (select club from tb_member_factory where project = " + typeId
				 * + "))) "));
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

	@SuppressWarnings("unchecked")
	public void listStarCoach() {
		final JSONArray jarr = new JSONArray();
		List<Map<String, Object>> list = (List<Map<String, Object>>) service
				.findRecommendBySectorCode(DBConstant.RECOMM_SECTOR_SLIDES_C, city, speciality);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if (!list.isEmpty()) {
			for (Map<String, Object> obj : list) {
				final JSONObject ob = new JSONObject();
				ob.accumulate("id", obj.get("id").toString()).accumulate("speciality", obj.get("speciality").toString())
						.accumulate("color", obj.get("color").toString())
						.accumulate("recomm_date", sdf.format(obj.get("recomm_date")))
						.accumulate("recomm_id", obj.get("recomm_id").toString())
						.accumulate("recomm_type", obj.get("recomm_type").toString())
						.accumulate("sector", obj.get("sector").toString())
						.accumulate("stick_time", sdf.format(obj.get("stick_time")))
						.accumulate("summary", obj.get("summary").toString())
						.accumulate("title", obj.get("title").toString()).accumulate("icon", obj.get("icon").toString())
						.accumulate("link", obj.get("link").toString()).accumulate("href", obj.get("href").toString());
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
				dc.add(this.or(Restrictions.like("name", where), Restrictions.like("nick", where),
						Restrictions.like("email", where), Restrictions.like("mobilephone", where)));
			} else {
				if (city != null && !"".equals(city))
					dc.add(Restrictions.like("city", "%" + city + "%"));

				if (county != null && !"".endsWith("county")) {
					dc.add(Restrictions.eq("county", county));
				}

				if (typeId != null && !"".equals(typeId)) {
					String[] typeIdArr = typeId.split(",");
					StringBuffer sq = new StringBuffer("this_.id in (select c.member from TB_COURSE c "
							+ "inner join TB_COURSE_INFO ci on c.courseid = ci.id where 1=1 and (");
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
					Double distance = (m.getLongitude() == null || m.getLatitude() == null) ? 0.0
							: LnglatUtil.GetDistance(lng, lat, m.getLongitude(), m.getLatitude());
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
					Double distance = (m.getLongitude() == null || m.getLatitude() == null) ? 0.0
							: LnglatUtil.GetDistance(lng, lat, m.getLongitude(), m.getLatitude());
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
							.accumulate("mode", modeName).accumulate("certificate", jarr1)
							.accumulate("distance", df.format(distance / 1000));
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
				List<?> planDate = service
						.findObjectBySql("select distinct r.doneDate from TrainRecord r where r.partake.id =" + member
								+ " order by r.doneDate desc");
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
				final String msg = sendSmsMessage(mobile, "mobile.validate.open",
						"｛" + m.getNick() + "｝参加了｛" + challengeName + "｝，已经指定您为他的成绩裁判。请您进入卡库健身的消息中心审核运动成绩");
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
					.accumulate("appraise", getGradeJson(member)).accumulate("address", member.getAddress())
					.accumulate("description", member.getDescription()).accumulate("mode", member.getMode())
					.accumulate("mobilephone", member.getMobilephone()).accumulate("tell", member.getTell())
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
			json.accumulate("id", courseinfo.getId()).accumulate("courseName", courseinfo.getName())
					.accumulate("memo", courseinfo.getMemo()).accumulate("image", courseinfo.getImage());
			jarr.add(json);
		}
		return jarr;
	}

	/**
	 * 查找指定 教练 的所有健身套餐信息
	 */
	public void findProduct() {
		try {
			final DetachedCriteria dc = DetachedCriteria.forClass(Product.class);
			final Member m = (Member) service.load(Member.class, getMobileUser().getId());
			dc.add(Restrictions.eq("member.id", id));
			if (m.getRole().equals("S")) {
				dc.add(Restrictions.eq("audit", '1'));
			} else {
				dc.add(Restrictions.eq("audit", '1'));
				dc.add(Restrictions.eq("isClose", "2"));
			}
			dc.add(Restrictions.eq("type", "1"));// 1 为健身

			pageInfo.setOrder("topTime");
			pageInfo.setOrderFlag("desc");

			pageInfo = service.findPageByCriteria(dc, pageInfo);
			String memo = null;
			String isSelf = "0";
			final JSONArray jarr = new JSONArray();
			for (final Iterator<?> it = pageInfo.getItems().iterator(); it.hasNext();) {
				final Product p = (Product) it.next();
				List<Map<String, Object>> parameterList = service.queryForList(
						"SELECT * FROM tb_parameter p WHERE p.parent = (SELECT id FROM tb_parameter WHERE CODE = 'card_type_c') and p.code = ?",
						p.getProType());
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
			response(ret);
		} catch (Exception e) {
			log.error("error", e);
			response(e);
		}
	}

	/**
	 * 签到时订单展现 2014/12/23 增加其它类型商品的订单展现
	 */
	public void findOrder() {
		try {
			final BaseMember mu = getMobileUser();
			final List<?> list = service.findObjectBySql(
					"from ProductOrder p where p.member.id = ? and p.product.member.id = ? and p.status = '1' order by p.orderDate desc",
					mu.getId(), id);
			final JSONArray jarr = new JSONArray();
			for (final Iterator<?> it = list.iterator(); it.hasNext();) {
				final ProductOrder po = (ProductOrder) it.next();
				final JSONObject obj = new JSONObject(), prodObj = new JSONObject();
				prodObj.accumulate("id", po.getProduct().getId()).accumulate("name", po.getProduct().getName())
						.accumulate("proType", po.getProduct().getProType());
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
				if (!hasRel)
					throw new LogicException("您没有购买该商户的健身卡，不能进行签单操作！");
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
	@SuppressWarnings({ "unchecked", "unused" })
	public void findMember() {
		try {
			BaseMember mu = getMobileUser();
			String where = "%";
			if (keyword != null && !"".equals(keyword)) {
				where = "%" + keyword + "%";
			}

			// String sql = " select * from (select a.*, b.hip , b.waist , c.traincount /
			// a.workoutTimes * 100 as finishrate ,d.phCount as traincount,c.time as time
			// from tb_member a "
			// + " left join (select t.partake, t.hip , t.waist from tb_plan_record t order
			// by t.done_date desc limit 0,1) b on a.id = b.partake "
			// + " left join (select t.partake , sum(t.times) time, count(*) traincount from
			// tb_plan_record t where t.done_date<=SYSDATE() group by t.partake ) c on a.id
			// = c.partake "
			// + " left join (select ph.member as member,count(ph.id) phCount from
			// tb_present_heartrate ph GROUP BY ph.member ) d ON a.id = d.member where
			// a.coach = ? or a.id = ?) s "
			// + " where s.name like ? or s.nick like ? or s.email like ? or s.mobilephone
			// like ? ORDER BY abs(s.id-?)";
			// Object[] objs = new Object[] { mu.getId(), mu.getId(), where, where, where,
			// where, mu.getId() };
			// pageInfo = service.findPageBySql(sql, pageInfo, objs);

			String queryMemberList = "  select * from (select a.*, b.hip , b.waist , c.traincount / a.workoutTimes * 100 as finishrate ,d.phCount as traincount,c.time as time from tb_member a   "
					+ "  left join (select t.partake, t.hip , t.waist from tb_plan_record t  order by t.done_date desc limit 0,1) b on a.id = b.partake   "
					+ "  left join (select t.partake , sum(t.times) time, count(*) traincount from tb_plan_record t where t.done_date<=SYSDATE() group by t.partake ) c on a.id = c.partake  "
					+ "  left join (select ph.member as member,count(ph.id) phCount from tb_present_heartrate ph GROUP BY ph.member ) d ON a.id = d.member   where a.coach = "
					+ mu.getId() + " or a.id = " + mu.getId() + ") s   " + "  where s.name like '" + where
					+ "' or s.nick like '" + where + "' or s.email like '" + where + "' or s.mobilephone like '" + where
					+ "' ORDER BY abs(s.id-" + mu.getId() + ")";
			List<Map<String, Object>> queryList = service.queryForList(queryMemberList);
			pageInfo.setItems(queryList);

			if (pageInfo.getItems() == null)
				pageInfo.setItems(new ArrayList<Member>());
			// if (mu.getRole().equals("S")) pageInfo.getItems().add(0,
			// service.load(Member.class, mu.getId()));
			final JSONArray jarr = new JSONArray();
			Member m = new Member();
			DecimalFormat df = new DecimalFormat("#.00");
			for (final Iterator<?> it = queryList.iterator(); it.hasNext();) {
				Map<String, Object> map1 = (Map<String, Object>) it.next();
				m = (Member) service.load(Member.class, Long.parseLong(map1.get("id").toString()));
				final Setting set = service.loadSetting(m.getId());
				int i = 0;
				Calendar cal = Calendar.getInstance();
				if (!cal.before(m.getBirthday())) {
					int year1 = cal.get(Calendar.YEAR);
					int month1 = cal.get(Calendar.MONTH) + 1;
					int day1 = cal.get(Calendar.DAY_OF_MONTH);
					if (m.getBirthday() != null) {
						cal.setTime(m.getBirthday());
					}
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
				// 注释掉time、finishrate的输出
				// obj.accumulate("finishrate", map1.get("finishrate") == null ?
				// 0 : map1.get("finishrate")).accumulate("time",
				// map1.get("finishrate") == null ? 0 :
				// String.valueOf(df.format(Double.parseDouble(map1.get("time").toString())
				// / 60)));
				String querySql = "select height,weight,waist,hip from tb_plan_record where partake = " + m.getId()
						+ " order by done_date desc limit 0,1";
				Map<String, Object> queryMap = service.queryForMap(querySql);
				if (queryMap == null) {
					queryMap = new HashMap<String, Object>();
					queryMap.put("height", 0);
					queryMap.put("weight", 0);
					queryMap.put("hip", 0);
					queryMap.put("waist", 0);
				}

				if (queryMap.get("height") == null) {
					queryMap.put("height", 0);
				}

				if (queryMap.get("weight") == null) {
					queryMap.put("weight", 0);
				}

				if (queryMap.get("hip") == null) {
					queryMap.put("hip", 0);
				}

				if (queryMap.get("waist") == null) {
					queryMap.put("waist", 0);
				}

				obj.accumulate("height", queryMap.get("height")).accumulate("weight", queryMap.get("weight"));
				obj.accumulate("hip", queryMap.get("hip")).accumulate("waist", queryMap.get("waist"));
				obj.accumulate("bmiHigh", set.getBmiHigh()).accumulate("bmiLow", set.getBmiLow());
				obj.accumulate("age", i).accumulate("heart", set.getHeart());
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
	@SuppressWarnings("unused")
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
			obj.accumulate("success", true).accumulate("id", id).accumulate("name", getString(m.getName()))
					.accumulate("nick", getString(m.getNick())).accumulate("image", getString(m.getImage()))
					.accumulate("count",
							list.get(0).get("traincount") == null ? 0 : list.get(0).get("traincount").toString())
					.accumulate("sex", m.getSex()).accumulate("age", i).accumulate("heart", set.getHeart())
					.accumulate("time",
							list.get(0).get("time") == null ? 0
									: String.valueOf(
											df.format(Double.parseDouble(list.get(0).get("time").toString()) / 60)))
					.accumulate("finishrate",
							list.get(0).get("finishrate") == null ? 0
									: String.valueOf(
											df.format(Double.parseDouble(list.get(0).get("finishrate").toString()))))
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
	@SuppressWarnings({ "unchecked", "unused" })
	public void findFriend() {
		try {
			setCity(uuid, lng, lat, city);
			final BaseMember mu = getMobileUser();
			final Double currLng = lng;
			final Double currLat = lat;
			double[] around = LnglatUtil.getAround(currLat, currLng, 5 * 1000);
			final List<Map<String, Object>> list = service.queryForList(
					"select *,tp.traincount/m.workoutTimes *100 finishrate from tb_member m LEFT JOIN (select t.partake , sum(t.times) time, count(*) traincount from tb_plan_record t GROUP BY t.partake) tp on m.id = tp.partake where m.id <> ? and m.role in ('S','M') and m.longitude between ? and ? and m.latitude between ? and ?",
					new Object[] { mu.getId(), around[1], around[3], around[0], around[2] });
			final JSONObject ret = new JSONObject();
			final JSONArray jarr = new JSONArray();
			DecimalFormat df = new DecimalFormat("#.00");

			// 身高，体重，腰围，臀围sql语句
			String sql = "select height,weight,waist,hip from tb_plan_record where partake = ? order by done_date desc limit 0,1";
			for (final Iterator<?> it = list.iterator(); it.hasNext();) {
				final Map<String, Object> map = (Map<String, Object>) it.next();
				Long mid = Long.parseLong(map.get("id").toString());
				Member m = (Member) service.load(Member.class, mid);
				Setting set = service.loadSetting(mid);
				final Double distance = LnglatUtil.GetDistance(currLng, currLat, m.getLongitude(), m.getLatitude());
				// final JSONObject obj = getMemberJson(m);
				final JSONObject obj = new JSONObject();
				int i = 0;
				Calendar cal = Calendar.getInstance();
				if (!cal.before(m.getBirthday())) {
					int year1 = cal.get(Calendar.YEAR);
					int month1 = cal.get(Calendar.MONTH) + 1;
					int day1 = cal.get(Calendar.DAY_OF_MONTH);
					cal.setTime(m.getBirthday() == null ? new Date() : m.getBirthday());
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

				// 身高，体重，腰围，臀围
				Object[] objx = { mid };
				List<Map<String, Object>> trainList = DataBaseConnection.getList(sql, objx);
				if (trainList.size() > 0) {
					Map<String, Object> trainRecord = trainList.get(0);
					obj.accumulate("id", m.getId()).accumulate("role", m.getRole()).accumulate("name", m.getName())
							.accumulate("province", m.getProvince()).accumulate("county", m.getCounty())
							.accumulate("distance", distance)
							.accumulate("count", map.get("traincount") == null ? 0 : map.get("traincount").toString())
							.accumulate("image", m.getImage()).accumulate("height", trainRecord.get("height"))
							.accumulate("weight", trainRecord.get("weight"))
							.accumulate("waist", trainRecord.get("waist")).accumulate("hip", trainRecord.get("hip"))
					/*
					 * .accumulate("time", map.get("time") == null ? 0 : String.valueOf(
					 * df.format(Double.parseDouble(map.get("time").toString()) / 60)))
					 * .accumulate("finishrate", map.get("finishrate") == null ? 0 :
					 * map.get("finishrate").toString()) .accumulate("age", i).accumulate("heart",
					 * set.getHeart() == null ? 0 : set.getHeart()) .accumulate("height",
					 * set.getHeight() == null ? 0 : set.getHeight())
					 */
					// .put("evaluatecount", map.get("traincount") == null ? 0 :
					// map.get("traincount").toString());
					;
					jarr.add(obj);
				} else {
					obj.accumulate("id", m.getId()).accumulate("role", m.getRole()).accumulate("name", m.getName())
							.accumulate("province", m.getProvince()).accumulate("county", m.getCounty())
							.accumulate("distance", distance)
							.accumulate("count", map.get("traincount") == null ? 0 : map.get("traincount").toString())
							.accumulate("image", m.getImage()).accumulate("height", 0).accumulate("weight", 0)
							.accumulate("waist", 0).accumulate("hip", 0);
					jarr.add(obj);
				}

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
			final List<?> fs = service.findObjectBySql(
					"from Friend f where (f.friend.id = ? and f.member.id = ?) or (f.friend.id = ? and f.member.id = ?)",
					cId, id, id, cId);
			if (fs.size() <= 0) {
				final Member m = (Member) service.load(Member.class, id);
				ret.accumulate("success", false).accumulate("code", -1).accumulate("message",
						"您不是" + m.getNick() + "的会员，不能评分！");
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
			IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", "LTAIEBpPW8aDSjKX",
					"x9wTapvynUDLiyHg3zdWuVjGs3JFxj");
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
			System.out.printf("RequestId: %s, MessageID: %s\n", pushResponse.getRequestId(),
					pushResponse.getMessageId());
			final JSONObject ret = new JSONObject();
			ret.accumulate("success", true).accumulate("message", "OK");
			response(ret);
		} catch (Exception e) {
			log.error("error", e);
			response(e);
		}
	}

	/**
	 * 申请加入私教/俱乐部
	 */
	@SuppressWarnings({ "unchecked", "rawtypes", "unused" })
	public void request() {
		try {
			final BaseMember mu = getLoginMember();
			final Long mid = mu.getId();

			// 当前登录用户
			Member mem = (Member) service.load(Member.class, mid);

			// 增加申请加入私教
			Member memto = (Member) service.load(Member.class, id);
			if (memto.getRole().equals("S")) {

				if (mem.getCoachId() == memto.getId()) {
					throw new LogicException("您已经加入了该私教，不得重复加入！");
				} else if (mem.getCoachId() != null) {
					throw new LogicException("您已经加入了私教，需要先解除后在加入！");
				}

			} else {
				List<?> fList = service.findObjectBySql(
						"from Friend f where f.member.id = ? and f.friend.id = ? and type = ?",
						new Object[] { mid, id, "1" });
				if (fList.size() > 0)
					throw new LogicException("您已经加入了该俱乐部，不得重复加入！");
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

			// 向私教推送消息
			PushRequest pushRequest = new PushRequest();
			IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", "LTAIEBpPW8aDSjKX",
					"x9wTapvynUDLiyHg3zdWuVjGs3JFxj");
			Long appkey = (long) 23580685;
			DefaultAcsClient client1 = new DefaultAcsClient(profile);
			pushRequest.setProtocol(ProtocolType.HTTPS);
			pushRequest.setMethod(MethodType.POST);
			pushRequest.setActionName("Push");
			pushRequest.setRegionId("cn-hangzhou");
			pushRequest.setVersion("2016-08-01");
			pushRequest.setAppKey(appkey);
			pushRequest.setTarget("ACCOUNT");
			pushRequest.setTargetValue(id.toString());
			pushRequest.setPushType("NOTICE");
			pushRequest.setDeviceType("ALL"); // 设备类型 ANDROID iOS ALL.
			pushRequest.setTitle("卡库聘请私教通知"); // 消息的标题
			pushRequest.setBody(msg.getContent()); // 消息的内容
			pushRequest.setiOSApnsEnv("PRODUCT");// DEV:开发模式; PRODUCT:生产模式
			pushRequest.setAndroidOpenType("APPLICATION"); // 点击通知后动作
			pushRequest.setStoreOffline(true); // 离线消息是否保存,若保存,
			PushResponse pushResponse = client1.getAcsResponse(pushRequest);
			System.out.printf("RequestId: %s, MessageID: %s\n", pushResponse.getRequestId(),
					pushResponse.getMessageId());
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
			if (id == null)
				id = getMobileUser().getId();
			if (signDate == null)
				signDate = new Date();
			final TrainRecord record = service.getRecordByDate(id, signDate);
			final Setting set = service.loadSetting(id);
			final Member m = (Member) service.load(Member.class, id);
			final JSONObject obj = new JSONObject(), ret = new JSONObject(), rObj = new JSONObject();
			obj.accumulate("id", m.getId())
					.accumulate("birthday", m.getBirthday() == null ? "" : ymd.format(m.getBirthday()))
					.accumulate("bmiLow", set.getBmiLow()).accumulate("bmiHigh", set.getBmiHigh())
					.accumulate("weight", set.getWeight()).accumulate("height", set.getHeight())
					.accumulate("sex", m.getSetting());
			if (record == null) {
				rObj.accumulate("member", obj);
				ret.accumulate("success", true).accumulate("record", rObj);
				response(ret);
				return;
			}
			final Double strength = service.findWeightByMember(id, record.getDoneDate());
			rObj.accumulate("id", record.getId()).accumulate("member", obj)
					.accumulate("fat", getDouble(record.getFat())).accumulate("hip", getDouble(record.getHip()))
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
			if (id == null || id == 0)
				id = getMobileUser().getId();
			final String startDate = request.getParameter("startDate");
			final String endDate = request.getParameter("endDate");
			final Date sDate = ymd.parse(startDate), eDate = ymd.parse(endDate);
			final List<?> list = service.findObjectBySql(
					"from TrainRecord tr where tr.partake.id = ? and tr.doneDate between ? and ? order by tr.doneDate",
					id, sDate, eDate);
			final JSONArray jarr = new JSONArray();
			for (final Iterator<?> it = list.iterator(); it.hasNext();) {
				final TrainRecord tr = (TrainRecord) it.next();
				final JSONObject obj = new JSONObject();
				obj.accumulate("id", tr.getId()).accumulate("doneDate", ymd.format(tr.getDoneDate()))
						.accumulate("weight", getDouble(tr.getWeight())).accumulate("fat", getDouble(tr.getFat()))
						.accumulate("waist", getDouble(tr.getWaist())).accumulate("hip", getDouble(tr.getHip()))
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
	 * 修改用户头像
	 */
	public void saveImage() {
		Member member = (Member) getMobileUser();
		String memberImageName = memberImage != null ? saveFile("picture", memberImage, memberImageFileName, null)
				: null;
		JSONObject obj = new JSONObject();
		if (!"".equals(memberImageName)) {// 图片上传成功，将图片名称保存到数据库
			member.setImage(memberImageName);
			service.saveOrUpdate(member);
			obj.accumulate("success", true).accumulate("image", memberImageName).accumulate("message", "OK");
		} else {// 图片上传失败
			obj.accumulate("success", false).accumulate("message", "头像上传失败");
		}
		response(obj);
	}

	/**
	 * 保存昵称
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
	 * 修改密码 by dou 2017-09-29
	 */
	@SuppressWarnings("unchecked")
	public void modifyPasswordByMobile() {
		try {
			final JSONObject ret = new JSONObject();
			final String flag = request.getParameter("flag");
			if (flag != null && "0".equals(flag)) {
				final String mobile = request.getParameter("mobile");
				final String password = request.getParameter("password");
				final String code = request.getParameter("code");// 手机接收到的验证码
				if (isRightful(mobile, code)) {// 手机验证通过
					List<Member> list = (List<Member>) service.findObjectBySql("from Member m where m.mobilephone = ?",
							mobile);
					Member m = list.get(0);
					m.setPassword(password);
					service.saveOrUpdate(m);
					ret.accumulate("success", true).accumulate("message", "OK");
					response(ret);
				}
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
	 * 获取手机验证码
	 */
	public void getMobileCode() {
		try {
			final String mobile = request.getParameter("mobile");
			// 新加入找回密码获取验证码，0重置密码，1注册，2修改手机号
			final String flag = request.getParameter("flag");
			if (flag != null && "0".equals(flag)) {
				final Long count = service.queryForLong(
						"select count(*) from tb_member where mobilephone = ? and mobile_valid = '1'", mobile);
				if (count > 0) {
					sendSmsValidateC(mobile, "mobile.validate.open");
				} else {
					throw new LogicException("当前手机号码未绑定至用户！");
				}
			} else if (flag != null && "1".equals(flag)) {
				// 手机注册获取验证码
				sendSmsValidateC(mobile, "mobile.validate.open");
			} else if (flag != null && "2".equals(flag)) {
				// 修改手机号获取验证码 by dou 2017-09-29
				sendSmsValidateC(mobile, "mobile.validate.open");
			} else {
				// 原3.5逻辑
				final Long count = service.queryForLong(
						"select count(*) from tb_member where mobilephone = ? and mobile_valid = '1' and id <> ?",
						mobile, getMobileUser().getId());
				if (count > 0)
					throw new LogicException("当前手机号码已经被其它用户使用，不得重复使用！");
				final String msg = sendSmsValidateC(mobile, "mobile.validate.open");
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
				}
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
				// 4.0添加1为手机注册，0为找回密码 2为修改手机号
				if ("0".equals(flag) || "1".equals(flag)) {
				} else if ("2".equals(flag)) {
					Member m = (Member) service.load(Member.class, getMobileUser().getId());
					m.setMobilephone(request.getParameter("mobile"));
					m.setMobileValid("1");
					service.saveOrUpdate(m);
				} else {
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
	 * 保存出生日期
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
	 * 保存身高
	 */
	public void saveHeight() {
		try {
			final String height = request.getParameter("height");
			final Setting setting = service.loadSetting(getMobileUser().getId());
			setting.setHeight(new Integer(height));
			service.saveOrUpdate(setting);

			// 再将最新的身高信息更新到trainRecord中
			String trainR = "update tb_plan_record a set a.height = ? where a.id  =  (select e.id from (select id from tb_plan_record where partake = ? ORDER BY done_date desc limit 1) e)";
			Object[] objR = { height, getMobileUser().getId() };
			int i = DataBaseConnection.updateData(trainR, objR);
			if (i == 0) {
				TrainRecord tr = new TrainRecord();
				tr.setHeight(Double.valueOf(height));
				tr.setPartake((Member) getMobileUser());
				service.saveOrUpdate(tr);
				i = 1;
			}
			final JSONObject ret = new JSONObject();
			if (i > 0) {
				ret.accumulate("success", true).accumulate("message", "OK");
			} else {
				ret.accumulate("success", false).accumulate("message", "保存身高异常");
			}
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
	 * 城市切换列表
	 */
	public void changeCity() {
		try {

			final List<?> list = service
					.findObjectBySql("from Area1 ta where open = '1' and type = '1' order by ta.id");

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
			if (obj.containsKey("id") && obj.get("id") != null) {
				body = (Body) service.load(Body.class, obj.getLong("id"));
			}
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
			String fileName1 = imageFront != null ? saveFile("picture", imageFront, imageFrontFileName, null) : null;
			String fileName2 = imageSide != null ? saveFile("picture", imageSide, imageSideFileName, null) : null;
			if (fileName1 != null) {
				body.setImageFront(fileName1);
			}
			if (fileName2 != null) {
				body.setImageSide(fileName2);
			}
			if (obj.containsKey("conclusion")) {
				body.setConclusion(obj.getString("conclusion"));
			} else {
				body.setConclusion(BodyUtils.handlerBody(body));
			}
			body = (Body) service.saveOrUpdate(body);
			final JSONObject ret = new JSONObject();
			ret.accumulate("success", true).accumulate("key", body.getId())
					.accumulate("conclusion", body.getConclusion()).accumulate("imageFront", body.getImageFront())
					.accumulate("imageSide", body.getImageSide());
			response(ret);
		} catch (Exception e) {
			log.error("error", e);
			response(e);
		}
	}

	/**
	 * 保存体态分析
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
	 * 保存用户身体数据&&体态分析结论
	 */
	@SuppressWarnings({ "unchecked" })
	public void saveMemberData() {
		final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			String jsons = request.getParameter("jsons") == null ? request.getParameter("Jsons")
					: request.getParameter("jsons");
			jsons = jsons.replaceAll("\r|\n", "");
			JSONArray arr = JSONArray.fromObject(URLDecoder.decode(jsons, "UTF-8"));
			log.error("保存会员设置JSON串：" + arr.toString());
			final JSONObject obj = arr.getJSONObject(0);
			// 保存体态分析结论
			Body body = new Body();
			if (obj.containsKey("bodyId")) {
				Long bodyId = Long.parseLong(obj.get("bodyId").toString());
				body = (Body) service.load(Body.class, bodyId);
				if (obj.containsKey("conclusion")) {
					String conclusion = URLDecoder.decode(obj.get("conclusion").toString(), "UTF-8");
					body.setConclusion(conclusion);
				}
				if (obj.containsKey("doneDate")) {
					String analyDate = obj.get("doneDate").toString();
					body.setAnalyDate(sdf.parse(analyDate));
				}
				body.setMember(obj.getLong("memberId"));
				body = (Body) service.saveOrUpdate(body);

			} else {

				if (obj.containsKey("conclusion")) {
					String conclusion = URLDecoder.decode(obj.get("conclusion").toString(), "UTF-8");
					body.setConclusion(conclusion);
				}
				if (obj.containsKey("doneDate")) {
					String analyDate = obj.get("doneDate").toString();
					body.setAnalyDate(sdf.parse(analyDate));
				}
				body.setMember(obj.getLong("memberId"));
				body = (Body) service.saveOrUpdate(body);
			}

			// 保存用户身体数据
			Long memberid = Long.parseLong(obj.get("memberId").toString());
			String recordtime = obj.getString("doneDate").replaceAll("-", "");
			List<?> blist = service.queryForList(
					"select * from tb_active_order ao where ao.member=? and ? >= DATE_FORMAT(ao.orderStartTime,'%Y%m%d') and DATE_FORMAT(ao.orderEndTime,'%Y%m%d')>? and ao.status=1",
					new Object[] { memberid, recordtime, recordtime });
			if (blist.size() > 0) {
				StringBuffer sb = new StringBuffer();
				for (Iterator<?> it = blist.iterator(); it.hasNext();) {
					Map<String, Object> map = (Map<String, Object>) it.next();
					sb = sb.append(map.get("id").toString());
					sb.append(",");
				}
				String s = sb.toString().substring(0, sb.length() - 1);
				obj.accumulate("activeId", s);
			}
			final List<TrainRecord> rs = new ArrayList<TrainRecord>();
			final String doneDate = obj.getString("doneDate");
			long judgeId;
			if (obj.containsKey("memberId") && obj.getString("memberId") != null) {
				judgeId = obj.getLong("memberId");
			} else {
				judgeId = id != null ? id : getMobileUser().getId();
			}
			final Long key = judgeId;
			final Setting set = service.loadSetting(key);
			if (obj.containsKey("activeId")) {
				final String[] strIds = obj.getString("activeId").split(",");
				for (int i = 0; i < strIds.length; i++) {
					final TrainRecord record = new TrainRecord();
					record.setHeight(Double.valueOf(set.getHeight()));
					record.setActiveOrder(new ActiveOrder(new Long(strIds[i].trim())));
					record.setDoneDate(sdf.parse(doneDate));
					if (obj.containsKey("action"))
						record.setAction(obj.getString("action"));
					if (obj.containsKey("actionQuan") && !StringUtils.isEmpty(obj.getString("actionQuan")))
						record.setActionQuan(obj.getInt("actionQuan"));
					if (obj.containsKey("fat") && !StringUtils.isEmpty(obj.getString("fat")))
						record.setFat(obj.getDouble("fat"));
					if (obj.containsKey("hip") && !StringUtils.isEmpty(obj.getString("hip")))
						record.setHip(obj.getDouble("hip"));
					record.setPartake(new Member(key));
					if (obj.containsKey("waist") && !StringUtils.isEmpty(obj.getString("waist")))
						record.setWaist(obj.getDouble("waist"));
					if (obj.containsKey("times") && !StringUtils.isEmpty(obj.getString("times")))
						record.setTimes(obj.getDouble("times"));
					if (obj.containsKey("weight") && !StringUtils.isEmpty(obj.getString("weight")))
						record.setWeight(obj.getDouble("weight"));
					if (obj.containsKey("memo"))
						record.setMemo(obj.getString("memo"));
					if (obj.containsKey("heartRate") && !StringUtils.isEmpty(obj.getString("heartRate")))
						record.setHeartRate(obj.getDouble("heartRate"));
					if (obj.containsKey("SportTime") && !StringUtils.isEmpty(obj.getString("SportTime")))
						record.setTimes(obj.getDouble("SportTime"));
					if (obj.containsKey("SportUnit"))
						record.setUnit(obj.getString("SportUnit"));
					if (obj.containsKey("SportScore") && !StringUtils.isEmpty(obj.getString("SportScore")))
						record.setScore(obj.getInt("SportScore"));
					rs.add(record);
				}
			} else {
				final TrainRecord record = new TrainRecord();
				record.setHeight(Double.valueOf(set.getHeight()));
				record.setDoneDate(sdf.parse(doneDate));
				if (obj.containsKey("action"))
					record.setAction(obj.getString("action"));
				if (obj.containsKey("actionQuan") && !StringUtils.isEmpty(obj.getString("actionQuan")))
					record.setActionQuan(obj.getInt("actionQuan"));
				if (obj.containsKey("fat") && !StringUtils.isEmpty(obj.getString("fat")))
					record.setFat(obj.getDouble("fat"));
				if (obj.containsKey("hip") && !StringUtils.isEmpty(obj.getString("hip")))
					record.setHip(obj.getDouble("hip"));
				record.setPartake(new Member(key));
				if (obj.containsKey("waist") && !StringUtils.isEmpty(obj.getString("waist")))
					record.setWaist(obj.getDouble("waist"));
				if (obj.containsKey("times") && !StringUtils.isEmpty(obj.getString("times")))
					record.setTimes(obj.getDouble("times"));
				if (obj.containsKey("weight") && !StringUtils.isEmpty(obj.getString("weight")))
					record.setWeight(obj.getDouble("weight"));
				if (obj.containsKey("memo"))
					record.setMemo(obj.getString("memo"));

				if (obj.containsKey("heartRate") && !StringUtils.isEmpty(obj.getString("heartRate")))
					record.setHeartRate(obj.getDouble("heartRate"));
				if (obj.containsKey("SportTime") && !StringUtils.isEmpty(obj.getString("SportTime")))
					record.setTimes(obj.getDouble("SportTime"));
				if (obj.containsKey("SportUnit") && !StringUtils.isEmpty(obj.getString("SportUnit")))
					record.setUnit(obj.getString("SportUnit"));
				if (obj.containsKey("SportScore") && !StringUtils.isEmpty(obj.getString("SportScore")))
					record.setScore(obj.getInt("SportScore"));

				rs.add(record);
			}
			service.saveOrUpdateRecord(rs, new Member(key), doneDate);

			final JSONObject obj1 = new JSONObject();
			obj1.accumulate("success", true).accumulate("message", "OK").accumulate("recordId", rs.get(0).getId())
					.accumulate("bodyId", body == null ? 0 : String.valueOf(body.getId()));
			response(obj1);
		} catch (Exception e) {
			log.error("error", e);
			response(e);
		}
	}

	/**
	 * 查询用户身体数据&&体态数据
	 */
	public void showMemberData() {
		try {

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date date = new Date();
			String sysDate = String.format("%tY-%tm-%td", date, date, date);

			Date doneDate = org.apache.commons.lang3.StringUtils.isEmpty(request.getParameter("doneDate")) ? null
					: sdf.parse(request.getParameter("doneDate"));

			if (doneDate == null) {
				doneDate = sdf.parse(sysDate);
			}
			long memberId = getMobileUser().getId();
			String xx = request.getParameter("memberId");
			if (org.apache.commons.lang3.StringUtils.isNotEmpty(xx)) {
				memberId = Long.parseLong(request.getParameter("memberId"));
			}

			Body body = new Body();
			body.setMember(memberId);
			body.setAnalyDate(doneDate);
			body = service.loadBodyData(body, memberId);

			final List<?> list = service.findObjectBySql("from TrainRecord r where r.partake.id = ? and r.doneDate <= '"
					+ sdf.format(new Date()) + "' order by r.doneDate desc ", new Object[] { memberId });
			JSONArray jarr = new JSONArray();
			for (final Iterator<?> it = list.iterator(); it.hasNext();) {
				final TrainRecord tr = (TrainRecord) it.next();
				final JSONObject obj = new JSONObject();

				// id:主键 , doneDate:完成时间, weight:体重, waist:腰围, hip:臀围, height:身高
				obj.accumulate("id", tr.getId()).accumulate("doneDate", sdf.format(tr.getDoneDate()))
						.accumulate("weight", getDouble(tr.getWeight())).accumulate("waist", getDouble(tr.getWaist()))
						.accumulate("hip", getDouble(tr.getHip())).accumulate("height", tr.getHeight());
				jarr.add(obj);
			}

			Setting setting = new Setting();
			setting = service.loadSetting(memberId);

			final JSONObject ret = new JSONObject();
			ret.accumulate("success", true).accumulate("trainRecord", jarr).accumulate("bmiLow", setting.getBmiLow())
					.accumulate("bmiHigh", setting.getBmiHigh()).accumulate("body", body.toJson());
			response(ret);

		} catch (Exception e) {
			log.error("error", e);
			response(e);
		}
	}

	@SuppressWarnings("deprecation")
	public void listMemberData() {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String doneDate = request.getParameter("doneDate");
			if (StringUtils.isNotEmpty(doneDate)) {
				doneDate = new SimpleDateFormat("yyyy-MM").format(new Date(doneDate.replace("-", "/")));
			}
			if (id == null) {
				id = getMobileUser().getId();
			}
			List<?> list = service.findObjectBySql(
					"from TrainRecord r where r.partake.id = ? and r.doneDate like '%" + doneDate + "%'",
					new Object[] { id });
			JSONArray jarr = new JSONArray();
			for (final Iterator<?> it = list.iterator(); it.hasNext();) {
				final TrainRecord tr = (TrainRecord) it.next();
				final JSONObject obj = new JSONObject();
				// id:主键 ，doneDate:完成时间,weight:体重,waist:腰围,hip:臀围
				obj.accumulate("id", tr.getId()).accumulate("doneDate", sdf.format(tr.getDoneDate()))
						.accumulate("weight", getDouble(tr.getWeight())).accumulate("waist", getDouble(tr.getWaist()))
						.accumulate("hip", getDouble(tr.getHip())).accumulate("height", tr.getHeight());
				jarr.add(obj);
			}
			response(new JSONObject().accumulate("success", true).accumulate("items", jarr));
		} catch (Exception e) {
			response(e);
		}
	}

	/**
	 * 查询会员健身设置
	 */
	public void loadMemberSetting() {
		try {
			if (userId == null)
				userId = getMobileUser().getId();
			Member m = (Member) service.load(Member.class, userId);
			Setting setting = new Setting();
			setting = service.loadSetting(userId);
			final JSONObject obj = new JSONObject();
			obj.accumulate("success", true).accumulate("item",
					setting.toJson().accumulate("sex", null == m ? "M" : m.getSex()));
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
		// 测试数据
		// String jsons =
		// "[{'member':819,'weight':65,'height':172,'target':1,'maxwm':50,'waistline':120,'sex':'M','favoriateCardio':'游泳,网球'}]";
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
			dc.add(Restrictions
					.sqlRestriction("CONCAT(this_.planDate, this_.startTime) >= '" + currDate + sysTime + "'"));
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
				jarr.add(course.toJson().accumulate("coach", getMemberJson(course.getCoach()))
						.accumulate("lng", getDouble(course.getCourseInfo().getMember().getLongitude()))
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

			final List<?> list = service.findObjectBySql(
					"from Friend fr where fr.friend.id = ? and fr.member.id = ? and fr.type = ? ",
					new Object[] { friendId, mu.getId(), "1" });
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

	@SuppressWarnings("unchecked")
	public void loadFilterCondition() {
		try {
			// 地区
			List<Area1> aList = (List<Area1>) service.findObjectBySql(
					"from Area1 a where a.name like ? and a.type = ? ", new Object[] { "%" + city + "%", '1' });
			final JSONArray jarr1 = new JSONArray();
			if (aList != null && aList.size() > 0) {
				List<Area1> areaList = (List<Area1>) service.findObjectBySql("from Area1 a where a.parent = ?",
						aList.get(0).getId());
				for (final Iterator<?> it = areaList.iterator(); it.hasNext();) {
					final JSONObject obj = new JSONObject();
					final Area1 area = (Area1) it.next();
					jarr1.add(obj.accumulate("id", area.getId()).accumulate("name", area.getName()));
				}
			}
			// 健身计划类型
			List<Parameter> planTypeList = (List<Parameter>) service.findObjectBySql(
					" from Parameter p where p.parent = (select po.id from Parameter po where po.code = ?) order by p.id asc",
					"plan_type_c");
			final JSONArray jarr2 = new JSONArray();
			for (final Iterator<?> it = planTypeList.iterator(); it.hasNext();) {
				final JSONObject obj = new JSONObject();
				final Parameter parameter = (Parameter) it.next();
				jarr2.add(obj.accumulate("id", parameter.getId()).accumulate("name", parameter.getName())
						.accumulate("code", parameter.getCode()));
			}

			// 健身计划适用对象
			List<Parameter> applyObjectList = (List<Parameter>) service.findObjectBySql(
					" from Parameter p where p.parent = (select po.id from Parameter po where po.code = ?) order by p.id asc",
					"apply_object_c");
			final JSONArray jarr3 = new JSONArray();
			for (final Iterator<?> it = applyObjectList.iterator(); it.hasNext();) {
				final JSONObject obj = new JSONObject();
				final Parameter parameter = (Parameter) it.next();
				jarr3.add(obj.accumulate("id", parameter.getId()).accumulate("name", parameter.getName())
						.accumulate("code", parameter.getCode()));
			}

			// 健身计划场景
			List<Parameter> applySceneList = (List<Parameter>) service.findObjectBySql(
					" from Parameter p where p.parent = (select po.id from Parameter po where po.code = ?) order by p.id asc",
					"apply_scene_c");
			final JSONArray jarr4 = new JSONArray();
			for (final Iterator<?> it = applySceneList.iterator(); it.hasNext();) {
				final JSONObject obj = new JSONObject();
				final Parameter parameter = (Parameter) it.next();
				jarr4.add(obj.accumulate("id", parameter.getId()).accumulate("name", parameter.getName())
						.accumulate("code", parameter.getCode()));
			}

			// 健身计划周期
			List<Parameter> planCircleList = (List<Parameter>) service.findObjectBySql(
					" from Parameter p where p.parent = (select po.id from Parameter po where po.code = ?) order by p.id asc",
					"plan_circle_c");
			final JSONArray jarr5 = new JSONArray();
			for (final Iterator<?> it = planCircleList.iterator(); it.hasNext();) {
				final JSONObject obj = new JSONObject();
				final Parameter parameter = (Parameter) it.next();
				jarr5.add(obj.accumulate("id", parameter.getId()).accumulate("name", parameter.getName())
						.accumulate("code", parameter.getCode()));
			}

			// 健身卡种
			List<Parameter> proTypeList = (List<Parameter>) service.findObjectBySql(
					" from Parameter p where p.parent = (select po.id from Parameter po where po.code = ?) order by p.id asc",
					"card_type_c");
			final JSONArray jarr6 = new JSONArray();
			for (final Iterator<?> it = proTypeList.iterator(); it.hasNext();) {
				final JSONObject obj = new JSONObject();
				final Parameter parameter = (Parameter) it.next();
				jarr6.add(obj.accumulate("id", parameter.getId()).accumulate("name", parameter.getName())
						.accumulate("code", parameter.getCode()));
			}

			// 运动项目
			List<Parameter> typeList = (List<Parameter>) service.findObjectBySql(
					" from Parameter p where p.parent = (select po.id from Parameter po where po.code = ?) order by p.id asc",
					"course_type_c");
			final JSONArray jarr7 = new JSONArray();
			for (final Iterator<?> it = typeList.iterator(); it.hasNext();) {
				final JSONObject obj = new JSONObject();
				final Parameter parameter = (Parameter) it.next();
				jarr7.add(obj.accumulate("id", parameter.getCode()).accumulate("name", parameter.getName())
						.accumulate("code", parameter.getId()));
			}
			// 教练专长
			List<Parameter> coachExpertiesList = (List<Parameter>) service.findObjectBySql(
					" from Parameter p where p.parent = (select po.id from Parameter po where po.code = ?) order by p.id asc",
					"coach_experties_c");
			final JSONArray jarr8 = new JSONArray();
			for (final Iterator<?> it = coachExpertiesList.iterator(); it.hasNext();) {
				final JSONObject obj = new JSONObject();
				final Parameter parameter = (Parameter) it.next();
				jarr8.add(obj.accumulate("id", parameter.getId()).accumulate("name", parameter.getName())
						.accumulate("code", parameter.getCode()));
			}
			// 教练类型
			List<Parameter> coachTypeList = (List<Parameter>) service.findObjectBySql(
					" from Parameter p where p.parent = (select po.id from Parameter po where po.code = ?) order by p.id asc",
					"coach_type_c1");
			final JSONArray jarr9 = new JSONArray();
			for (final Iterator<?> it = coachTypeList.iterator(); it.hasNext();) {
				final JSONObject obj = new JSONObject();
				final Parameter parameter = (Parameter) it.next();
				jarr9.add(obj.accumulate("id", parameter.getId()).accumulate("name", parameter.getName())
						.accumulate("code", parameter.getCode()));
			}
			// 挑战模式
			List<Parameter> modeList = service.findParametersByCodes("active_mode_c");
			final JSONArray jarr10 = new JSONArray();
			for (final Iterator<?> it = modeList.iterator(); it.hasNext();) {
				final JSONObject obj = new JSONObject();
				final Parameter parameter = (Parameter) it.next();
				jarr10.add(obj.accumulate("id", parameter.getId()).accumulate("name", parameter.getName())
						.accumulate("code", parameter.getCode()));
			}
			// 挑战目标
			List<Parameter> activeTargetList = service.findParametersByCodes("active_target_c");
			final JSONArray jarr11 = new JSONArray();
			for (final Iterator<?> it = activeTargetList.iterator(); it.hasNext();) {
				final JSONObject obj = new JSONObject();
				final Parameter parameter = (Parameter) it.next();
				jarr11.add(obj.accumulate("id", parameter.getId()).accumulate("name", parameter.getName())
						.accumulate("code", parameter.getCode()));
			}
			// 挑战周期
			List<Parameter> activeCircleList = service.findParametersByCodes("active_circle_c");
			final JSONArray jarr12 = new JSONArray();
			for (final Iterator<?> it = activeCircleList.iterator(); it.hasNext();) {
				final JSONObject obj = new JSONObject();
				final Parameter parameter = (Parameter) it.next();
				jarr12.add(obj.accumulate("id", parameter.getId()).accumulate("name", parameter.getName())
						.accumulate("code", parameter.getCode()));
			}

			// 裁判类型refereeType
			List<Parameter> refereeTypeList = (List<Parameter>) service.findObjectBySql(
					" from Parameter p where p.parent = (select po.id from Parameter po where po.code = ?) order by p.id asc",
					"refereeType");
			final JSONArray jarr13 = new JSONArray();
			for (final Iterator<?> it = refereeTypeList.iterator(); it.hasNext();) {
				final JSONObject obj = new JSONObject();
				final Parameter parameter = (Parameter) it.next();
				jarr13.add(obj.accumulate("id", parameter.getId()).accumulate("name", parameter.getName())
						.accumulate("code", parameter.getCode()));
			}

			final JSONObject ret = new JSONObject();
			ret.accumulate("success", true).accumulate("area", jarr1).accumulate("planType", jarr2)
					.accumulate("applyObject", jarr3).accumulate("applyScene", jarr4).accumulate("planCircle", jarr5)
					.accumulate("proType", jarr6).accumulate("sportType", jarr7).accumulate("coachExperties", jarr8)
					.accumulate("coachType", jarr9).accumulate("mode", jarr10).accumulate("activeTarget", jarr11)
					.accumulate("activeCircle", jarr12).accumulate("refereeType", jarr13);
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

	/**
	 * 从运动手环获取心率、打卡时间，并保存数据
	 */
	@SuppressWarnings("unused")
	public void SaveHeartRate() {
		try {
			JSONObject jsonObject = new JSONObject();
			final JSONArray arr = JSONArray.fromObject(URLDecoder.decode(jsons, "UTF-8"));
			log.error("保存健身打卡数据的参数：" + arr.toString());
			final JSONObject obj = arr.getJSONObject(0);
			PresentHeartRate phr = new PresentHeartRate();
			SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			phr = service.loadPresentHeartRate(obj.getLong("member"), sdf1.parse(obj.getString("traindate")));

			phr.setMember((Member) service.load(Member.class, obj.getLong("member")));
			phr.setStart_date(sdf2.parse(obj.getString("traindate") + " " + obj.getString("StartTime")));
			phr.setTime_diff(obj.getInt("TimeDiffer"));
			phr.setTrain_date(sdf1.parse(obj.getString("traindate")));
			phr.setHeartRates(obj.getString("heartRates"));
			phr.setEnd_date(sdf2.parse(obj.getString("traindate") + " " + obj.getString("EndTime")));

			phr = (PresentHeartRate) service.saveOrUpdate(phr);
			final JSONObject ret = new JSONObject();

			String sqlxx = "select count(*) count from TB_PRESENT_HEARTRATE  where member = ? ";
			Object[] objxx = { getMobileUser().getId() };
			Map<String, Object> mapxx = DataBaseConnection.getOne(sqlxx, objxx);

			ret.accumulate("success", true)
					/*
					 * .accumulate("key", phr.getId()) .accumulate("member",
					 * phr.getMember().getId())
					 */
					.accumulate("message", "OK").accumulate("key", phr.getId())
					.accumulate("heartRates", phr.getHeartRates()).accumulate("records", mapxx.get("count"))
					.accumulate("traindate", sdf3.format(phr.getStart_date()));// 返回的打卡时间
			response(ret);
		} catch (Exception e) {
			log.error("error", e);
			response(e);
		}
	}

	/**
	 * 获取离线打卡心率详细数据
	 */
	public void getRingHeartRates() {
		JSONObject ret = new JSONObject();
		try {
			// 处理请求参数
			JSONObject obj = JSONArray.fromObject(URLDecoder.decode(request.getParameter("json"), "UTF-8"))
					.getJSONObject(0);

			// 用户登录
			Member member = (Member) service.load(Member.class, getMobileUser().getId());

			// 获取离线心率
			String sqlx = "select * from tb_present_heartrate where member = ? and train_date = ? ";
			Object[] objx = { member.getId(), obj.getString("train_date") };
			Map<String, Object> heartRateMap = DataBaseConnection.getOne(sqlx, objx);

			// 获取当前用户的静心率
			String sqly = "select heart from tb_member_setting where member = ? ";
			Object[] objy = { member.getId() };
			Map<String, Object> heartMap = DataBaseConnection.getOne(sqly, objy);
			Double heartIntD = (Double) (heartMap.get("heart"));
			Integer heartInt = heartIntD.intValue();
			// 计算用户年龄
			Date birthday = member.getBirthday();
			int age = EasyUtils.getAge(birthday);

			// 计算值
			if (StringUtils.isNotEmpty((String) heartRateMap.get("heartRates"))) {
				// 心率不为空，即查询到了数据
				String heartRateStr = (String) heartRateMap.get("heartRates");

				// 将String转换成int数组
				Integer[] heartRateIntNo = EasyUtils.StringConvert2Integer(heartRateStr);
				Integer[] douxx = EasyUtils.StringConvert2Integer(heartRateStr);

				if (douxx.length == 1) {
					ret.accumulate("success", false).accumulate("message", "该数据不是，离线手环上传的数据");
					response(ret);
					return;
				}
				// 计算运动时间
				Date startDate = (Date) heartRateMap.get("start_date");
				Date endDate = (Date) heartRateMap.get("end_date");

				Integer diffS = EasyUtils.timeDiff(startDate, endDate);
				Integer diffM = diffS % 60 == 0 ? diffS / 60 : (diffS / 60) + 1;

				// 将数组排序
				Integer[] heartRateInt = EasyUtils.bubbleSort(heartRateIntNo, "desc");

				// 最大心率，取最大的前5个数据的平均值
				Integer maximunRate = EasyUtils.avgInteger(heartRateInt, 5);

				// 平均心率
				Integer avgHeartRate = EasyUtils.avgInteger(heartRateInt, heartRateInt.length);

				// 热身恢复区间
				List<Double> rate0 = new ArrayList<Double>();
				rate0.add(0.59);

				// 脂肪燃烧区间
				List<Double> rate1 = new ArrayList<Double>();
				rate1.add(0.60);
				rate1.add(0.74);

				// 心肺强化
				List<Double> rate2 = new ArrayList<Double>();
				rate2.add(0.75);
				rate2.add(0.82);

				// 肌肉耐力
				List<Double> rate3 = new ArrayList<Double>();
				rate3.add(0.83);
				rate3.add(0.88);

				// 极限警戒
				List<Double> rate4 = new ArrayList<Double>();
				rate4.add(0.89);
				rate4.add(1.00);

				List<List<Double>> listx = new ArrayList<List<Double>>();
				listx.add(rate0);
				listx.add(rate1);
				listx.add(rate2);
				listx.add(rate3);
				listx.add(rate4);

				// 分区数据
				Map<String, Object> levelMap = EasyUtils.getDt(age, heartInt, listx, heartRateInt, RING_TIME_TYPE_MM);

				// 将查询到的数据返回
				ret.accumulate("success", true).accumulate("message", "OK").accumulate("heartRates", douxx)
						.accumulate("throughTime", diffM).accumulate("maximunRate", maximunRate)
						.accumulate("avgHeartRate", avgHeartRate).accumulate("levelMap", levelMap);

			} else {
				ret.accumulate("success", false).accumulate("message", "心率数据缺失");
			}

		} catch (Exception e) {
			ret.accumulate("success", false).accumulate("message", e.toString());
			e.printStackTrace();
		}

		response(ret);
	}

	/**
	 * 显示打卡记录（心率）
	 */
	@SuppressWarnings({ "unchecked", "unused", "rawtypes" })
	public void ShowHeartRate() {
		try {
			final JSONObject obj = new JSONObject();
			PresentHeartRate phr = new PresentHeartRate();
			SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

			int currentPage = 1;// 默认显示第一页
			int cp = Integer.valueOf(request.getParameter("currentPage"));
			if (cp > 0) {
				currentPage = cp;
			}

			int pageSize = 20;// 默认页面大小20条数据
			int ps = Integer.valueOf(request.getParameter("pageSize"));
			if (ps > 0) {
				pageSize = ps;
			}

			Map<String, Object> map = service.loadPresentHeartRate(Long.parseLong(member), currentPage, pageSize);
			List<Map<String, Object>> pList = (List<Map<String, Object>>) map.get("phrList");
			pageInfo = (PageInfo) map.get("pageInfo");
			if (pList.size() < 0) {
				obj.accumulate("success", false).accumulate("message", "查无资料");
			} else {
				JSONArray cArray = new JSONArray();
				JSONObject objx = null;
				for (Map map2 : pList) {
					objx = new JSONObject();
					Integer maximunRate = 0;
					
					// 计算最大心率
					String heartRateStr = String.valueOf(map2.get("heartRates"));
					
					// 转换成Int数组
					Integer[] heartRateIntNo = EasyUtils.StringConvert2Integer(heartRateStr);
					if (heartRateIntNo.length > 1) {
						// 离线心率，求最大心率
						// 将数组排序
						Integer[] heartRateInt = EasyUtils.bubbleSort(heartRateIntNo, "desc");
						
						// 最大心率，取最大的前5个数据的平均值
						maximunRate = EasyUtils.avgInteger(heartRateInt, 5);
						
					} else {
						// 非离线心率，不做处理
						maximunRate = heartRateIntNo[0];
					}
					for (Iterator it = map2.keySet().iterator(); it.hasNext();) {
						Object key = it.next();
						if ("train_date".equals(key)) {
							Date dd = (Date) map2.get(key);
							objx.accumulate(String.valueOf(key), sdf1.format(dd));
						} else if ("heartRates".equals(key)) {
							objx.accumulate(String.valueOf(key), maximunRate);
						} else {
							objx.accumulate(String.valueOf(key), String.valueOf(map2.get(key)));
						}
						

					}
					cArray.add(objx);
				}

				obj.accumulate("success", true).accumulate("message", "OK").accumulate("phrList", cArray)
						.accumulate("pageInfo", getJsonForPageInfo());

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
					sql.append(
							"m.city is not ? and t.done_date<=SYSDATE() group by m.id ) b order by b.finishrate desc");
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
				obj1.accumulate("name", m.getName()).accumulate("image", m.getImage())
						.accumulate("count", map1.get("timeNum") == null ? 0 : map1.get("timeNum").toString())
						.accumulate("time",
								map1.get("time") == null ? 0
										: String.valueOf(
												df.format(Double.parseDouble(map1.get("time").toString()) / 60)))
						.accumulate("finishrate",
								map1.get("finishrate") == null ? 0 : map1.get("finishrate").toString())
						.accumulate("city", m.getCity()).accumulate("id", m.getId());

				jar.add(obj1);
			}

			final JSONObject obj = new JSONObject();
			obj.accumulate("success", true).accumulate("message", "OK").accumulate("items", jar).accumulate("pageInfo",
					getJsonForPageInfo());
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
		// 测试数据
		// JSONObject jsonObject = new JSONObject();
		// jsonObject.accumulate("userType", "S")
		// .accumulate("gander", "男")
		// .accumulate("birthday", "2017-11-13")
		// .accumulate("userHeight", 187)
		// .accumulate("userWidth", 82)
		// .accumulate("userRate", 88)
		// .accumulate("nickName", "douyun")
		// .accumulate("id", 9388)
		// ;
		// JSONArray jsonArray = new JSONArray();
		// jsonArray.add(jsonObject);
		// String jsons = jsonArray.toString();

		try {
			final JSONArray arr = JSONArray.fromObject(URLDecoder.decode(jsons, "UTF-8"));
			JSONObject objx = (JSONObject) arr.get(0);
			final String userType = objx.getString("userType");
			final String gander = objx.getString("gander");
			final String birthday = objx.getString("birthday");
			final String userHeight = objx.getString("userHeight");
			final String userWidth = objx.getString("userWidth");
			final String userRate = objx.getString("userRate");
			final String nickName = objx.getString("nickName");
			Member m = (Member) getMobileUser();
			Setting setting = service.loadSetting(m.getId());

			m.setRole(userType);
			m.setSex(gander);
			m.setBirthday(ymd.parse(birthday));
			m.setName(nickName);
			setting.setHeight(new Integer(userHeight));
			setting.setWeight(new Double(userWidth));
			setting.setHeart(new Integer(userRate));
			setting.setMember(m.getId());

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
					obj.accumulate("success", true).accumulate("id", m.getId())
							.accumulate("name", getString(m.getName())).accumulate("nick", getString(m.getNick()))
							.accumulate("image", getString(m.getImage())).accumulate("type", m.getRole())
							.accumulate("mobilephone", getString(m.getMobilephone()))
							.accumulate("mobileValid", m.getMobileValid()).accumulate("message", uuid)
							.accumulate("grade", getInteger(m.getGrade()))
							.accumulate("score", gradeJson.get("avgGrade"))
							.accumulate("appraiseCount", gradeJson.get("appraiseCount"))
							// 取未阅读消息总数
							.accumulate("messageCount", messageCount)
							// 20160302
							.accumulate("sex", m.getSex());
					List<?> records = service.findObjectBySql(
							"from TrainRecord r where r.partake.id =" + m.getId() + " order by r.doneDate desc");
					obj.accumulate("records", null == records ? 0 : records.size());
					TrainRecord lastRecord = new TrainRecord();
					if (null != records && 0 != records.size()) {
						lastRecord = (TrainRecord) records.get(0);
						obj.accumulate("weight",
								null == lastRecord.getWeight() ? set.getWeight() : lastRecord.getWeight())
								.accumulate("height",
										null == lastRecord.getHeight() ? set.getHeight() : lastRecord.getHeight())
								.accumulate("waist",
										null == lastRecord.getWaist() ? set.getWaistline() : lastRecord.getWaist())
								.accumulate("hip", lastRecord.getHip());
					} else {
						obj.accumulate("weight", set.getWeight()).accumulate("height", set.getHeight())
								.accumulate("waist", set.getWaistline()).accumulate("hip", null);
					}
					final JSONArray jarr = new JSONArray();
					// 取得其所有课程及每个课程购买次数
					// final Set<Product> products = m.getProducts();
					// for (final Product p : products) {
					// final JSONObject obj2 = new JSONObject();
					// obj2.accumulate("id", p.getId()).accumulate("name",
					// p.getName()).accumulate("memo", "")
					// .accumulate("buys", p.getOrders() == null ? 0 : p.getOrders().size());
					// jarr.add(obj2);
					// }
					// obj.accumulate("courses", jarr);
				} else { // 会员
					// 取得最新的活动数据
					final List<?> list = service.findObjectBySql(
							"from ActiveOrder ap where ap.member.id = ? and ap.orderEndTime >= ? and ap.status <> '0' order by ap.orderEndTime desc",
							m.getId(), new Date());
					final JSONArray actArr = new JSONArray();
					for (final Iterator<?> it = list.iterator(); it.hasNext();) {
						final ActiveOrder ap = (ActiveOrder) it.next();
						final Active act = ap.getActive();
						final JSONObject obj1 = new JSONObject();
						obj1.accumulate("id", ap.getId()).accumulate("member", getMemberJson(act.getCreator()))
								.accumulate("target", act.getTarget()).accumulate("days", getInteger(act.getDays()))
								.accumulate("action", getString(act.getAction()))
								.accumulate("category", getString(act.getCategory())).accumulate("name", act.getName())
								.accumulate("value", getDouble(act.getValue())).accumulate("judge", ap.getJudge())
								.accumulate("judgeMode", act.getJudgeMode()).accumulate("mode", act.getMode())
								.accumulate("teamNum", getInteger(act.getTeamNum()))
								.accumulate("award", getString(act.getAward()))
								.accumulate("institution", getJsonForMember(act.getInstitution()))
								.accumulate("amerceMoney", getDouble(act.getAmerceMoney()))
								.accumulate("startDate", ymd.format(ap.getOrderStartTime()))
								.accumulate("endDate", ymd.format(ap.getOrderEndTime()))
								.accumulate("activeimage", act.getImage());
						actArr.add(obj1);
					}
					final Member coach = m.getCoach();
					final JSONObject coachObj = new JSONObject();
					if (coach != null)
						coachObj.accumulate("id", coach.getId()).accumulate("nick", getString(coach.getNick()))
								.accumulate("name", getString(coach.getName()))
								.accumulate("image", getString(coach.getImage()))
								.accumulate("appraiseCount", getInteger(coach.getCountEmp()))
								.accumulate("score", coach.getAvgGrade());
					obj.accumulate("success", true).accumulate("id", m.getId())
							.accumulate("name", getString(m.getName())).accumulate("nick", getString(m.getNick()))
							.accumulate("image", getString(m.getImage())).accumulate("type", m.getRole())
							.accumulate("grade", m.getGrade()).accumulate("mobilephone", getString(m.getMobilephone()))
							.accumulate("mobileValid", m.getMobileValid()).accumulate("message", uuid)
							.accumulate("sex", m.getSex()).accumulate("actions", actArr).accumulate("coach", coachObj)
							// 未阅读消息总数
							.accumulate("messageCount", messageCount);

					// final Setting set = service.loadSetting(member.getId());
					List<?> records = service.findObjectBySql(
							"from TrainRecord r where r.partake.id =" + m.getId() + " order by r.doneDate desc");
					obj.accumulate("records", null == records ? 0 : records.size());
					TrainRecord lastRecord = new TrainRecord();
					if (null != records && 0 != records.size()) {
						lastRecord = (TrainRecord) records.get(0);
						obj.accumulate("weight",
								null == lastRecord.getWeight() ? set.getWeight() : lastRecord.getWeight())
								.accumulate("height",
										null == lastRecord.getHeight() ? set.getHeight() : lastRecord.getHeight())
								.accumulate("waist",
										null == lastRecord.getWaist() ? set.getWaistline() : lastRecord.getWaist())
								.accumulate("hip", lastRecord.getHip());
					} else {
						obj.accumulate("weight", set.getWeight()).accumulate("height", set.getHeight())
								.accumulate("waist", set.getWaistline()).accumulate("hip", null);
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

	@SuppressWarnings("unused")
	public void TribeAddUser() throws Exception {
		JSONObject obj = new JSONObject();
		try {
			@SuppressWarnings("unchecked")
			List<Member> mlist = (List<Member>) service.findObjectBySql("from Member m where m.id=?",
					getMobileUser().getId());
			if (mlist.isEmpty()) {
				obj.accumulate("success", false).accumulate("message", "添加用户失败");
				return;
			} else {
				Long mid = mlist.get(0).getId();
				String url = "http://gw.api.taobao.com/router/rest";
				TaobaoClient tclient = new DefaultTaobaoClient(url, "23330566", "0c6b77f937d49f3a14ca98a6316d110e");
				OpenimTribeInviteRequest otir = new OpenimTribeInviteRequest();
				otir.setTribeId(tribeId);
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
			log.error("error", e);
			response(new JSONObject().accumulate("success", false).accumulate("message", e));
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

	/**
	 * getter && setter
	 */
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
		if (nick != null && !"".equals(nick))
			nick = urlDecode(nick);
		this.nick = nick;
	}

	public Long getTribeId() {
		return tribeId;
	}

	public void setTribeId(Long tribeId) {
		this.tribeId = tribeId;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCounty() {
		return county;
	}

	public void setCounty(String county) {
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
		if (keyword != null && !"".equals(keyword))
			keyword = urlDecode(keyword);
		this.keyword = keyword;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		if (username != null && !"".equals(username))
			username = urlDecode(username);
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

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getMemberImageFileName() {
		return memberImageFileName;
	}

	public void setMemberImageFileName(String memberImageFileName) {
		this.memberImageFileName = memberImageFileName;
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

	public File getImageFront() {
		return imageFront;
	}

	public void setImageFront(File imageFront) {
		this.imageFront = imageFront;
	}

	public File getImageBack() {
		return imageBack;
	}

	public void setImageBack(File imageBack) {
		this.imageBack = imageBack;
	}

	public String getImageFrontFileName() {
		return imageFrontFileName;
	}

	public void setImageFrontFileName(String imageFrontFileName) {
		this.imageFrontFileName = imageFrontFileName;
	}

	public File getImageSide() {
		return imageSide;
	}

	public void setImageSide(File imageSide) {
		this.imageSide = imageSide;
	}

	public File getMemberImage() {
		return memberImage;
	}

	public void setMemberImage(File memberImage) {
		this.memberImage = memberImage;
	}

}
