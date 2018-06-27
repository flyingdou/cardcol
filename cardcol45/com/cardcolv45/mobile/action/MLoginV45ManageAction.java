package com.cardcolv45.mobile.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;

import com.aliyun.api.gateway.demo.enums.Constantsms;
import com.cardcol.web.utils.FileUtil;
import com.databaseConnection.DataBaseConnection;
import com.freegym.web.active.Active;
import com.freegym.web.active.TrainRecord;
import com.freegym.web.basic.Member;
import com.freegym.web.config.Setting;
import com.freegym.web.mobile.BasicJsonAction;
import com.freegym.web.order.ActiveOrder;
import com.freegym.web.order.Product;
import com.sanmen.web.core.utils.StringUtils;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.domain.Userinfos;
import com.taobao.api.request.OpenimUsersAddRequest;
import com.taobao.api.request.OpenimUsersGetRequest;
import com.taobao.api.request.OpenimUsersUpdateRequest;
import com.taobao.api.response.OpenimUsersAddResponse;
import com.taobao.api.response.OpenimUsersGetResponse;
import com.taobao.api.response.OpenimUsersUpdateResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Namespace("")
@InterceptorRefs({ @InterceptorRef("customStack") })
public class MLoginV45ManageAction extends BasicJsonAction implements Constantsms {

	private static final long serialVersionUID = -4900715126840183203L;

	protected String username, password;
	private String nick;
	private String url;

	/**
	 * 登录时的经纬度
	 */
	private Double lng, lat;

	private String city;
	// 第三方登陆类别
	private String thirdType;
	// 第三方登录ID
	private String thirdId;

	/**
	 * 20160429 update by liuweiquan 加入三方登录的内容，这里要注意的三方第一次登录，包含注册
	 */
	@SuppressWarnings({ "static-access", "unused" })
	@Override
	public String execute() {
		try {
			Member member = null;
			String uuid = null;
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			if (!StringUtils.isEmpty(thirdType) && !StringUtils.isEmpty(thirdId)) {
				// 第三方登录
				member = service.thirdLoginCheck(thirdType, thirdId, lng, lat);
				
				// 第三方登录，每次更新用户图像
				if (member != null) {
					String imgName = org.apache.commons.lang3.StringUtils.isEmpty(member.getImage()) ? getFileName("8888888.png") : member.getImage();
					FileUtil.download(url, this.webPath + "picture/" + imgName);
					member.setImage(imgName);
					member = (Member) service.saveOrUpdate(member);
				}
				
			} else {
				member = service.login(username, password, lng, lat);
			}
			session.setAttribute("status", 2);
			final JSONObject ret = new JSONObject();
			if (member == null) {
				if (!StringUtils.isEmpty(thirdType)) {
					// 第三方登录部分逻辑 第一次登录，则注册用户
					member = new Member();
					if ("Q".equals(thirdType)) {
						member.setQqId(thirdId);
					} else if ("S".equals(thirdType)) {
						member.setSinaId(thirdId);
					} else if ("W".equals(thirdType)) {
						member.setWechatID(thirdId);
					}
					member.setThirdType(thirdType);
					member.setRegisterType("t");
					member.setLongitude(lng);
					member.setLatitude(lat);
					member.setNick(nick);
					String imgName = getFileName("8888888.png");
					if (imgName != null) {
						FileUtil.download(url, this.webPath + "picture/" + imgName);
						member.setImage(imgName);
					}
					member = service.thirdRegister(member);
					addMobileUser(member);
					// count:0表示首次登录 返回APP一个标识
					ret.accumulate("success", true).accumulate("message", member.getToKen()).accumulate("count", "0")
							.accumulate("key", member.getId());
					// 2017-7-1 增加返回的值 message .accumulate("message",
					// member.getToKen()) 处理 Ios 第三方登陆第一次闪退的bug
					// 2017-7-6 完全修复第三方第一次登陆闪退的问题 注释了 response(ret)
					// response(ret);
					return execute();
				} else {
					ret.accumulate("success", false).accumulate("message", "您输入的账户及密码不正确，请重新输入！");
					response(ret);
					return null;
				}
			}
			uuid = member.getToKen();
			if ("E".equals(member.getRole())) {
				ret.accumulate("success", false).accumulate("message", "您不能从手机平台登录到卡库网！请确认！");
				response(ret);
				return null;
			}

			final Setting set = service.loadSetting(member.getId());
			ret.accumulate("key", member.getId());
			addMobileUser(member);
			// 增加教练未阅读消息总数
			final Integer messageCount = service.findMessageCount(member.getId());
			String birthday = member.getBirthday() == null ? "" : sdf.format(member.getBirthday());// 出生日期
			if ("S".equals(member.getRole())) { // 教练
				JSONObject gradeJson = getGradeJson(member);
				ret.accumulate("success", true).accumulate("id", member.getId())
						.accumulate("name", getString(member
								.getName()))/*
											 * .accumulate("nick",
											 * getString(member.getNick()))
											 */
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
				List<?> records = service.findObjectBySql(
						"from TrainRecord r where r.partake.id =" + member.getId() + " and r.doneDate <= '" + sdf.format(new Date()) + "' order by r.doneDate desc");
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
						avgObj.accumulate("totalityScore",
								Math.floor(Double.parseDouble(String.valueOf(AvgScoreMap.get("totalityScore") == null
										? 0 : AvgScoreMap.get("totalityScore")))))
								.accumulate("deviceScore", Math.floor(
										Double.parseDouble(String.valueOf(AvgScoreMap.get("deviceScore") == null ? 0
												: AvgScoreMap.get("deviceScore")))))
								.accumulate("evenScore", Math.floor(
										Double.parseDouble(String.valueOf(AvgScoreMap.get("evenScore") == null ? 0
												: AvgScoreMap.get("evenScore")))))
								.accumulate("serviceScore", Math.floor(
										Double.parseDouble(String.valueOf(AvgScoreMap.get("serviceScore") == null ? 0
												: AvgScoreMap.get("serviceScore")))));
					}
					coachObj.accumulate("id",
							coach.getId())/*
											 * .accumulate("nick",
											 * getString(coach.getNick()))
											 */
							.accumulate("name", getString(coach.getName()))
							.accumulate("image", getString(coach.getImage()))
							.accumulate("appraiseCount", getInteger(coach.getCountEmp()))
							.accumulate("score", coach.getAvgGrade()).accumulate("avgScore", avgObj);
				}

				ret.accumulate("success", true).accumulate("id", member.getId())
						.accumulate("name", getString(member
								.getName()))/*
											 * .accumulate("nick",
											 * getString(member.getNick()))
											 */
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
				List<?> records = service.findObjectBySql(
						"from TrainRecord r where r.partake.id =" + member.getId() + " and r.doneDate <= '" + sdf.format(new Date()) + "' order by r.doneDate desc");
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
		} catch (Exception e) {
			log.error("error", e);
			e.printStackTrace();
			response(e);
		}
		return null;
	}

	public void updateLnglat() {
		try {
			final Member m = (Member) service.load(Member.class, getMobileUser().getId());
			m.setLongitude(lng);
			m.setLatitude(lat);
			service.saveOrUpdate(m);
			setCity(uuid, lng, lat, city);
			final JSONObject ret = new JSONObject();
			ret.accumulate("success", true).accumulate("message", "OK");
			response(ret);
		} catch (Exception e) {
			log.error("error", e);
			response(e);
		}
	}

	/**
	 * 找回用户密码时候验证手机号码 是否已经注册
	 */
	public void checkUserExist() {
		try {
			String userPhone = request.getParameter("userPhone");
			boolean flag = service.checkUserExist(userPhone);
			final JSONObject ret = new JSONObject();
			ret.accumulate("success", flag).accumulate("message", "OK");
			response(ret);
		} catch (Exception e) {
			log.error("error", e);
			response(e);
		}

	}

	/**
	 * 修改用户密码
	 */
	public void updateUserPwd() {
		final JSONObject ret = new JSONObject();
		try {
			String userPhone = request.getParameter("userPhone");
			String userPassword = request.getParameter("userPassword");
			final Member m = (Member) service.load(Member.class, getMobileUser().getId());
			m.setMobilephone(userPhone);
			m.setPassword(userPassword);
			service.updateUserPwd(m);
			ret.accumulate("success", true).accumulate("message", "OK");
			response(ret);
		} catch (Exception e) {
			log.error("error", e);
			ret.accumulate("success", false).accumulate("message", e);
			response(ret);
		}

	}

	/**
	 * 控制直播和第三方登录的状态
	 */
	public void getStatus() {
		JSONObject result = new JSONObject();
		// liveStatus:控制直播显示和隐藏,0:隐藏,1:显示
		result.accumulate("liveStatus", 1);
		// thirdStatus:控制第三方登录显示和隐藏,0:隐藏,1:显示
		result.accumulate("thirdStatus", 1);
		response(result);
	}

	/**
	 * setter && getter
	 */
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = urlDecode(username);
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getThirdType() {
		return thirdType;
	}

	public void setThirdType(String thirdType) {
		this.thirdType = thirdType;
	}

	public String getThirdId() {
		return thirdId;
	}

	public void setThirdId(String thirdId) {
		this.thirdId = thirdId;
	}

}
