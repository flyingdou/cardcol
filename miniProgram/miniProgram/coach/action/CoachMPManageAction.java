package miniProgram.coach.action;

import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.http.ProtocolType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.aliyuncs.push.model.v20160801.PushRequest;
import com.aliyuncs.push.model.v20160801.PushResponse;
import com.databaseConnection.DataBaseConnection;
import com.freegym.web.basic.Member;
import com.freegym.web.basic.MemberTicket;
import com.freegym.web.config.CourseInfo;
import com.freegym.web.course.Apply;
import com.freegym.web.course.Message;
import com.freegym.web.mobile.BasicJsonAction;
import com.freegym.web.order.Goods;
import com.freegym.web.order.Order;
import com.freegym.web.order.PlanOrder;
import com.freegym.web.order.Product;
import com.freegym.web.order.ProductOrder;
import com.freegym.web.plan.Course;
import com.freegym.web.plan.PlanRelease;
import com.freegym.web.system.Ticket;
import com.freegym.web.task.MessageThread;
import com.freegym.web.utils.EasyUtils;
import com.freegym.web.wechatPay.utils.MD5Util;
import com.sanmen.web.core.utils.DateUtil;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.OpenimCustmsgPushRequest;
import com.taobao.api.request.OpenimCustmsgPushRequest.CustMsg;
import com.taobao.api.response.OpenimCustmsgPushResponse;

import common.util.HttpRequestUtils;
import ecartoon.wx.util.SaveImgByUrl;
import expert.util.AES;
import expert.util.commentsUtil;
import miniProgram.coach.constant.Constants;
import miniProgram.coach.util.SDK_WX;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * @author hw
 * @version 创建时间：2018年4月20日 下午6:47:01
 * @ClassName 类名称
 * @Description 类描述
 */
@Namespace("")
@InterceptorRefs({ @InterceptorRef("customStack") })
public class CoachMPManageAction extends BasicJsonAction {
	private static final long serialVersionUID = 1L;

	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	/**
	 * 卡库教练小程序登录
	 */
	public void wechatLogin() {
		JSONObject ret = new JSONObject();
		try {
			// 初始化member
			Member member = new Member();

			// 处理请求参数
			String json = request.getParameter("json");
			JSONObject param = JSONObject.fromObject(json);

			// 获取小程序返回的code
			String code = param.getString("code");

			// 请求微信服务器获取unionId、session_key
			String requestUrl = "https://api.weixin.qq.com/sns/jscode2session?appid=" + Constants.APPID + "&secret="
					+ Constants.APP_SECERT + "&js_code=" + code + "&grant_type=authorization_code";
			// 请求返回的数据
			JSONObject resJsonObject = HttpRequestUtils.httpGet(requestUrl);

			// 从加密信息中解密出来
			String douStr = AES.wxDecrypt(param.getString("encryptedData"), resJsonObject.getString("session_key"),
					param.getString("iv"));
			String unionId = EasyUtils.getTargetFromAES(douStr, "unionId");

			// 前台传递过来的数据
			JSONObject douInfo = JSONObject.fromObject(param.getString("userInfo"));
			// 获取用户的unionId
			String sqlx = "select * from tb_member where wechatID = ?";
			Object[] objx = { unionId };
			List<Map<String, Object>> memberList = DataBaseConnection.getList(sqlx, objx);

			// 已有用户
			if (memberList.size() > 0) {
				member = (Member) service.load(Member.class, Long.valueOf(memberList.get(0).get("id").toString()));
				member.setLoginTime(new Date());
				member.setToKen(MD5Util.MD5Encode(sdf.format(new Date()), "utf-8"));

				// 覆盖用户的最新的图像
				if (!"".equals(douInfo.getString("avatarUrl"))) {
					String path = request.getSession().getServletContext().getRealPath("/picture");
					SaveImgByUrl.download(douInfo.getString("avatarUrl"), member.getImage(), path);
				}
				// 持久化数据
				service.saveOrUpdate(member);

			} else {
				// 新用户，进行注册操作
				String filename = commentsUtil.getRandomByDate(6) + ".jpg";
				String path = request.getSession().getServletContext().getRealPath("/picture");
				if ("".equals(douInfo.getString("avatarUrl"))) {
					filename = "user.png";
				} else {
					SaveImgByUrl.download(douInfo.getString("avatarUrl"), filename, path);
				}
				member.setNick(douInfo.getString("nickName"));
				member.setName(douInfo.getString("nickName"));

				if ("".equals(douInfo.getString("gender"))) {
					member.setSex("M");
				} else {
					member.setSex(douInfo.getString("gender").equals("1") ? "M" : "F");
				}
				if (!"".equals(douInfo.getString("city"))) {
					member.setCity(douInfo.getString("city"));
				}
				if (!"".equals(douInfo.getString("province"))) {
					member.setProvince(douInfo.getString("province"));
				}
				member.setWechatID(unionId);
				member.setImage(filename);
				member.setRegDate(new Date());
				member.setRole("M");
				member.setRegisterType("t");
				member.setThirdType("W");
				member.setLoginTime(new Date());
				// 持久化数据
				member = (Member) service.saveOrUpdate(member);

				// 给新注册用户发优惠券
				Ticket ticket = (Ticket) service.load(Ticket.class, Long.valueOf("17"));
				MemberTicket memberTicket = new MemberTicket();
				memberTicket.setMember(member);
				memberTicket.setTicket(ticket);
				memberTicket.setStatus(1);
				memberTicket.setActiveDate(new Date());
				memberTicket = (MemberTicket) service.saveOrUpdate(memberTicket);
				ret.accumulate("memberTicket", memberTicket.getId());
			}

			// 登录成功时需返回的数据
			ret.accumulate("success", true).accumulate("message", "OK").accumulate("key", member.getId())
					.accumulate("openid", resJsonObject.get("openid"))
					.accumulate("session_key", resJsonObject.get("session_key"));

		} catch (Exception e) {
			// 程序异常时返回的数据
			ret.accumulate("success", false).accumulate("message", e.toString());
			e.printStackTrace();
		}

		// 将数据返回
		response(ret);
	}

	/**
	 * 访问教练首页数据
	 */
	public void findCoachHome() {
		// 当前进入小程序的用户
		String memberId = request.getParameter("memberId");
		// 通过哪个教练分享进入小程序, 没有则显示默认教练的首页
		String coachId = request.getParameter("coachId");
		// 根据id查询会员数据
		Member member = (Member) service.load(Member.class, Long.valueOf(memberId));
		// 根据id查询教练数据
		Member coach = (Member) service.load(Member.class, Long.valueOf(coachId));
		// final JSONObject obj = getMemberJson(member);
		// 查询“我的私教套餐”
		String privateSql = "";
		if (coachId.equals(memberId)) {
			// 当访问者教练本人时
			privateSql = "select p.id,p.image1,p.cost,p.name,p.isClose from tb_product p ,tb_member m   where p.type = 3  and m.id = p.member and m.role = 'S' and p.member = ? ORDER BY createTime desc ";
		} else {
			// 当访问者为会员或其他教练时
			privateSql = "select p.id,p.image1,p.cost,p.name,p.isClose from tb_product p ,tb_member m   where p.type = 3  and m.id = p.member and m.role = 'S' and p.member = ? and p.isClose = 2 and p.audit = 1 ORDER BY createTime desc ";
		}
		List<Map<String, Object>> privateList1 = DataBaseConnection.getList(privateSql, new Object[] { coach.getId() });
		JSONArray privateList = JSONArray.fromObject(privateList1);

		// 查询“健身计划”
		String PlanSql = "";
		if (coachId.equals(memberId)) {
			// 当访问者教练本人时
			PlanSql = "select id,plan_name,image1,unit_price,briefing,isClose from tb_plan_release where member = ?  and audit = '1' ORDER BY publish_time desc ";
		} else {
			// 当访问者为会员或其他教练时
			PlanSql = "select id,plan_name,image1,unit_price,briefing,isClose from tb_plan_release where member = ?  and audit = '1' and isClose = 2  ORDER BY publish_time desc ";
		}
		// service.queryForList(PlanSql,new Object[]{member.getId()});
		List<Map<String, Object>> planList1 = DataBaseConnection.getList(PlanSql, new Object[] { coach.getId() });

		JSONArray planList = JSONArray.fromObject(planList1);
		final JSONObject obj = new JSONObject();
		String querySql = "select IFNULL(s.average,0) socure,(select count(memberSign) from tb_sign_in where memberAudit = "
				+ coachId + ") count" + " from ( select (t.totality_score / t.count) average from"
				+ " (select sum(e.totality_score) totality_score,count(e.id) count from tb_member_evaluate e inner join tb_sign_in s"
				+ " on e.signIn = s.id where s.memberAudit = " + coachId + ") t) s";
		Map<String, Object> map1 = service.queryForMap(querySql);
		double socure = Double.parseDouble(String.valueOf(map1.get("socure")));
		obj.accumulate("success", true).accumulate("type", coach.getRole()).accumulate("message", uuid)
				// 添加教练个人信息
				.accumulate("id", getMemberJson(coach).get("id")).accumulate("name", getMemberJson(coach).get("name"))
				.accumulate("image", getMemberJson(coach).get("image"))
				.accumulate("mobilephone", getMemberJson(coach).get("mobilephone"))
				// 计算教练 评分 评价人数
				.accumulate("appraiseCount", map1.get("count"))
				.accumulate("avgGrade", Integer.parseInt(String.valueOf(Math.round(socure))))
				// 添加“我的私教套餐”
				.accumulate("privateList", privateList)
				// 添加“我的健身计划”
				.accumulate("planList", planList)
				// 当前登录会员是否有教练
				.accumulate("hasCoach", member.getCoach() == null ? 0 : member.getCoach().getId());
		response(obj);
	}
	
	/**
	 * 查询教练的简介
	 */
	public void detail() {

		try {
			String sql1 = "SELECT id, `name`, style, speciality, description FROM tb_member WHERE id = ?";
			String sql2 = "SELECT c.id projectId, c.name projectName, c.image projectImage, c.type projectType,p.name projectTypeName  FROM tb_course_info c,tb_parameter p WHERE c.type = p.id and c.member = ? order by projectId desc";
			String sql3 = "SELECT id certificateId, `name` certificateName, fileName certificateImage FROM tb_member_certificate WHERE member = ? order by certificateId desc";
			// 接收传递过来的教练Id
			int coachId = Integer.parseInt(request.getParameter("id"));
			// 教练的基本信息
			List<Map<String, Object>> baseInfo = service.queryForList(sql1, coachId);
			JSONArray baseInfoJarr = new JSONArray();
			if (baseInfo.size() > 0) {
				JSONObject objx = null;
				for (Map<String, Object> map : baseInfo) {
					objx = new JSONObject();
					objx.accumulate("id", map.get("id")).accumulate("name", map.get("name"))
							.accumulate("style", map.get("style")).accumulate("speciality", map.get("speciality"))
							.accumulate("description", map.get("description"));
					baseInfoJarr.add(objx);
				}
			}

			// 该教练的服务项目
			List<Map<String, Object>> project = service.queryForList(sql2, coachId);
			JSONArray projectJarr = new JSONArray();
			if (project.size() > 0) {
				JSONObject objx = null;
				for (Map<String, Object> map : project) {
					objx = new JSONObject();
					objx.accumulate("projectId", map.get("projectId")).accumulate("projectName", map.get("projectName"))
							.accumulate("projectType", map.get("projectType"))
							.accumulate("projectTypeName", map.get("projectTypeName"))
							.accumulate("projectImage", map.get("projectImage"));
					projectJarr.add(objx);
				}
			}

			// 该教练的证书
			List<Map<String, Object>> certificate = service.queryForList(sql3, coachId);
			JSONArray certificateJarr = new JSONArray();
			if (certificate.size() > 0) {
				JSONObject objx = null;
				for (Map<String, Object> map : certificate) {
					objx = new JSONObject();
					objx.accumulate("certificateId", map.get("certificateId"))
							.accumulate("certificateName", map.get("certificateName"))
							.accumulate("certificateImage", map.get("certificateImage"));
					certificateJarr.add(objx);
				}
			}

			JSONObject ret = new JSONObject();
			ret.accumulate("success", true).accumulate("message", "OK").accumulate("baseList", baseInfoJarr)
					.accumulate("projectList", projectJarr).accumulate("certificateList", certificateJarr);
			response(ret);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 申请加入私教
	 */
	@SuppressWarnings({ "unchecked", "rawtypes", "unused" })
	public void request() {
		try {
			// 当前进入小程序的用户
			String memberId = request.getParameter("memberId");
			// 通过哪个教练分享进入小程序, 没有则显示默认教练的首页
			String coachId = request.getParameter("coachId");
			// load当前用户
			Member mu = (Member) service.load(Member.class, Long.valueOf(memberId));
			// 增加申请加入私教
			Member memto = (Member) service.load(Member.class, Long.valueOf(coachId));
			if (Constants.COACH_TYPE.equals(mu.getRole())) {
				return;
			}
			Message msg = new Message();
			if (Constants.COACH_TYPE.equals(memto.getRole())) {
				msg.setContent("申请加入私教！");
			}
			msg.setIsRead("0");
			msg.setMemberFrom(mu);
			msg.setMemberTo(memto);
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
			pushRequest.setTargetValue(coachId);
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
	 * 解除私教关系
	 */
	@SuppressWarnings("unused")
	public void remove() {
		try {
			// 当前进入小程序的用户
			String memberId = request.getParameter("memberId");
			// 通过哪个教练分享进入小程序, 没有则显示默认教练的首页
			String coachId = request.getParameter("coachId");
			// load当前用户
			Member m = (Member) service.load(Member.class, Long.valueOf(memberId));
			// 将提醒类消息“解除”写入到MESSAGE中
			Date sendTime = new Date();
			List<Message> msgList = new ArrayList<Message>();
			msgList.add(new Message(null, m, sendTime, "您解除了您的私教【" + m.getCoachName() + "】", "3", "0", "1"));
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
			// 推送目标: DEVICE:按设备推送 ALIAS :
			// 按别名推送 ACCOUNT:按帐号推送
			// TAG:按标签推送; ALL: 广播推送
			pushRequest.setTarget("ACCOUNT");
			// 根据Target来设定，如Target=DEVICE,
			// 则对应的值为
			// 设备id1,设备id2.
			// 多个值使用逗号分隔.(帐号与设备有一次最多100个的限制)
			pushRequest.setTargetValue(coachid.toString());
			// 消息类型 MESSAGE NOTICE
			pushRequest.setPushType("NOTICE");
			// 设备类型 ANDROID iOS ALL.
			pushRequest.setDeviceType("ALL");
			// 消息的标题
			pushRequest.setTitle("解除私教通知");
			// 消息的内容
			pushRequest.setBody(m.getName() + "解除了与您的私教关系");
			// DEV:开发模式; PRODUCT:生产模式
			pushRequest.setiOSApnsEnv("PRODUCT");
			// 点击通知后动作
			// "APPLICATION" :
			// 打开应用 "ACTIVITY" :
			// 打开AndroidActivity
			// "URL" : 打开URL
			// "NONE" : 无跳转
			pushRequest.setAndroidOpenType("APPLICATION");
			// 离线消息是否保存,若保存,
			// 在推送时候，用户即使不在线，下一次上线则会收到
			pushRequest.setStoreOffline(true);
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
	 * 查看私教套餐详情
	 */
	public void getPrivateInfo() {
		try {
			Product product = (Product) service.load(Product.class, id);
			response(new JSONObject().accumulate("success", true).accumulate("id", product.getId())
					.accumulate("name", product.getName()).accumulate("image", product.getImage1())
					.accumulate("memberId", product.getMember().getId())
					.accumulate("memberName", product.getMember().getName()).accumulate("wellNum", product.getWellNum())
					.accumulate("price", product.getCost()).accumulate("remark", product.getRemark())
					.accumulate("isClose", product.getIsClose()));
		} catch (Exception e) {
			e.printStackTrace();
			response(e);
		}
	}

	/**
	 * 私人订制详情 传参数 id=1为王严专家
	 */
	public void loadPlan() {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			int type = 0;
			if (request.getParameter("type") != null) {
				type = Integer.parseInt(request.getParameter("type"));
			}
			JSONObject obj = new JSONObject();
			if (type == 6) {
				Goods good = (Goods) service.load(Goods.class, id);
				Member member = (Member) service.load(Member.class, good.getMember());
				List<Map<String, Object>> list = DataBaseConnection.getList(
						"select count(t.id) as count from tb_goods_order as t where goods=?", new Object[] { id });
				Object goodsCount = 0;
				if (list.size() > 0) {
					goodsCount = list.get(0).get("count");
				}
				obj.accumulate("success", true).accumulate("item",
						good.toJsons().accumulate("memberName", member.getName()).accumulate("goodsCount", goodsCount));
			}
			if (type == 3) {
				PlanRelease planRelease = (PlanRelease) service.load(PlanRelease.class, id);
				Member member = planRelease.getMember();
				long count = (long) DataBaseConnection
						.getOne("select count(t.id) as count from tb_planrelease_order as t where planrelease="
								+ planRelease.getId(), null)
						.get("count");
				obj.accumulate("success", true).accumulate("item", new JSONObject()
						.accumulate("id", planRelease.getId()).accumulate("memberId", member.getId())
						.accumulate("memberName", member.getName()).accumulate("name", planRelease.getPlanName())
						.accumulate("planType", planRelease.getPlanType()).accumulate("scene", planRelease.getScene())
						.accumulate("applyObject", planRelease.getApplyObject())
						.accumulate("apparatuses", planRelease.getApparatuses())
						.accumulate("price", planRelease.getUnitPrice())
						.accumulate("summary", planRelease.getBriefing()).accumulate("image1", planRelease.getImage1())
						.accumulate("plancircle", planRelease.getPlanDay()).accumulate("goodsCount", count))
						.accumulate("startDate",
								sdf.format(
										planRelease.getStartDate() == null ? new Date() : planRelease.getStartDate()))
						.accumulate("endDate",
								sdf.format(planRelease.getEndDate() == null ? new Date() : planRelease.getEndDate()))
						.accumulate("isClose", planRelease.getIsClose());
				// .accumulate("plan_participantId",
				// planRelease.getPlan_participant().getId())
			}
			if (type == 0) {
				obj.accumulate("success", true).accumulate("message", "请指定'type'的类型");
			}
			response(obj);
		} catch (Exception e) {
			log.error("error", e);
			response(e);
		}
	}

	/*
	 * 我的计划
	 */
	@SuppressWarnings("unused")
	public void list() {
		JSONObject ret = new JSONObject();
		try {
			// 处理请求参数
			String json = request.getParameter("json");
			JSONObject objx = JSONObject.fromObject(URLDecoder.decode(json, "UTF-8"));
			Date planDate = sdf.parse(objx.getString("planDate"));
			if (planDate == null) {
				planDate = new Date();
			}

			Date[] ds = null;
			ds = DateUtil.getMinMaxDateByMonth(planDate);

			final String sDate = sdf.format(ds[0]);
			final String eDate = sdf.format(ds[1]);

			List<?> list = null;
			Member mu = (Member) service.load(Member.class, Long.valueOf(objx.getLong("memberId")));
			// 查询教练的课程
			Long coachId = objx.getLong("coachId");
			list = service.findObjectBySql(
					"from Course cs where cs.coach.id = ? and cs.planDate between ? and ? order by cs.planDate, cs.startTime",
					new Object[] { coachId, sDate, eDate });
			JSONArray jarr = new JSONArray();

			for (Iterator<?> it = list.iterator(); it.hasNext();) {
				Course c = (Course) it.next();
				JSONObject obj = new JSONObject();
				obj.accumulate("id", c.getId()).accumulate("image", c.getCourseInfo().getImage())
						.accumulate("member", getMemberJson(c.getMember()))
						.accumulate("coach", getMemberJson(c.getCoach())).accumulate("planDate", c.getPlanDate())
						.accumulate("startTime", c.getStartTime()).accumulate("endTime", c.getEndTime())
						.accumulate("courseId", c.getCourseInfo().getId())
						.accumulate("course", c.getCourseInfo().getName())
						.accumulate("place", c.getPlace() == null ? "" : c.getPlace())
						.accumulate("count", getInteger(c.getCount())).accumulate("joinNum", getInteger(c.getJoinNum()))
						.accumulate("memberPrice", getDouble(c.getMemberPrice()))
						.accumulate("hourPrice", getDouble(c.getHourPrice()))
						.accumulate("memo", c.getMemo() == null ? "" : c.getMemo().replaceAll("\n", "<br/>"))
						.accumulate("music", c.getMusic() != null ? c.getMusic().toJson() : null)
						.accumulate("countdown", c.getCountdown() == null ? "" : c.getCountdown())
						.accumulate("cycleCount", c.getCycleCount() == null ? "" : c.getCycleCount());
				jarr.add(obj);
			}
			System.out.println(jarr);
			ret.accumulate("success", true).accumulate("items", jarr);

		} catch (Exception e) {
			e.printStackTrace();
		}

		response(ret);
	}

	/**
	 * 生成卡库教练小程序订单
	 */
	public void createCoachMPOrder() {
		try {
			JSONObject json = JSONObject.fromObject(URLDecoder.decode(request.getParameter("json"), "UTF-8"));
			Member member = (Member) service.load(Member.class, json.getLong("memberId"));
			Order order = new Order();
			MemberTicket ticket = new MemberTicket();
			double productPrice = 0;
			double ticketMoney = 0;
			if (Constants.PRODUCT_TYPE_PRIVATE.equals(json.getString("productType"))) {
				order = new ProductOrder();
				Product product = (Product) service.load(Product.class, json.getLong("productId"));
				((ProductOrder) order).setProduct(product);
				order.setPayNo(service.getKeyNo("", "CARDCOL_ORDER_NO", 28));
				order.setNo(service.getKeyNo("", "CARDCOL_ORDER_NO", 14));
				productPrice = product.getCost();
			} else if (Constants.PRODUCT_TYPE_PLAN.equals(json.getString("productType"))) {
				order = new PlanOrder();
				PlanRelease plan = (PlanRelease) service.load(PlanRelease.class, json.getLong("productId"));
				((PlanOrder) order).setPlanRelease(plan);
				order.setPayNo(service.getKeyNo("", "TB_GOODS_ORDER", 28));
				order.setNo(service.getKeyNo("", "TB_GOODS_ORDER", 14));
				productPrice = plan.getUnitPrice();
			}
			if (json.containsKey("ticket")) {
				ticket = (MemberTicket) service.load(MemberTicket.class, json.getLong("ticket"));
				ticket.setStatus(2);
				service.saveOrUpdate(ticket);
				ticketMoney = ticket.getTicket().getPrice();
				order.setTicketId(ticket.getId());
			}
			// 补全属性
			order.setTicketAmount(ticketMoney);
			order.setOrderDate(new Date());
			order.setMember(member);
			order.setUnitPrice(productPrice);
			order.setCount(1);
			order.setContractMoney(productPrice);
			order.setPayServiceCostE(0.0);
			order.setPayServiceCostM(0.0);
			order.setPayType("1");
			order.setShipTimeType("1");
			order.setShipType("1");
			order.setSurplusMoney(productPrice);
			order.setOrderMoney(Double.valueOf(EasyUtils.decimalFormat(productPrice - ticketMoney)));
			order.setOrderStartTime(EasyUtils.formatStringToDate(json.getString("strengthDate")));
			order.setOrderBalanceTime(EasyUtils.formatStringToDate(json.getString("strengthDate")));
			order.setOrderDate(new Date());
			order.setStatus('0');
			// E卡通订单
			order.setOrigin('C');
			order = (Order) service.saveOrUpdate(order);
			// 绑定用户手机号
			member.setMobilephone(json.getString("phoneNumber"));
			member.setMobileValid("1");
			service.saveOrUpdate(member);
			// 支付签名
			SDK_WX sdk = new SDK_WX(request, "卡库教练小程序");
			String result = sdk.paySign(json.getString("openId"), order);
			response(JSONObject.fromObject(result).accumulate("orderId", order.getId()));
		} catch (Exception e) {
			e.printStackTrace();
			response(e);
		}
	}
	
	/**
	 * 查找当前用户的所有课程
	 */
	public void loadCourseInfo() {
		JSONObject ret = new JSONObject();
		try {
			// 处理请求参数
			JSONObject obj = JSONObject.fromObject(URLDecoder.decode(request.getParameter("json"), "UTF-8"));
			Long coachId = obj.getLong("coachId");
			
			List<?> list = null;
			if ("".equals(coachId)) {
				 Long userId = obj.getLong("memberId");
				 Member m = (Member) service.load(Member.class, userId);
				if (m.getRole().equals("M")) {// 会员没有私教，则取系统默认的课程
					if (m.getCoach() == null) {
						list = service.findObjectBySql("from CourseInfo ci where ci.member.id = ?", 1l);
					} else {
						list = service.findObjectBySql("from CourseInfo ci where ci.member.id = ?", m.getCoach().getId());
					}
				} else {
					list = service.findObjectBySql("from CourseInfo ci where ci.member.id = ?", userId);
					if (list.size() <= 0) {
						list = service.findObjectBySql("from CourseInfo ci where ci.member.id = ?", 1l);
					}
				}
			} else {
				list = service.findObjectBySql("from CourseInfo ci where ci.member.id = ?", coachId);
				if (list.size() <= 0) {
					list = service.findObjectBySql("from CourseInfo ci where ci.member.id = ?", 1l);
				}
			}
			final JSONArray jarr = new JSONArray();
			for (final Iterator<?> it = list.iterator(); it.hasNext();) {
				final CourseInfo ci = (CourseInfo) it.next();
				final JSONObject objx = new JSONObject();
				objx.accumulate("id", ci.getId()).accumulate("name", ci.getName());
				jarr.add(objx);
			}
			 
			ret.accumulate("success", true).accumulate("items", jarr);
		} catch (Exception e) {
			log.error("error", e);
			e.printStackTrace();
			ret.accumulate("success", false);
			response(e);
		}
		response(ret);
	}
	
	
	
	/**
	 * 预约课程
	 */
	public void appointment() {
		final JSONObject ret = new JSONObject();
		try {
				// 会员在教练课表中进行预约
			    JSONObject obj = JSONObject.fromObject(URLDecoder.decode(request.getParameter("json"), "UTF-8"));
			    Member member = (Member) service.load(Member.class, Long.valueOf(obj.getLong("memberId")));
				Long userId = member.getId();
				Course course = new Course();
				
				CourseInfo courseInfo = (CourseInfo) service.load(CourseInfo.class, obj.getLong("courseId"));
				course.setCourseInfo(courseInfo);
				course.setPlanDate(obj.getString("planDate"));
				course.setStartTime(obj.getString("startTime"));
				course.setEndTime(obj.getString("endTime"));
				course.setPlace(obj.getString("courseAddress"));
				course.setColor("#ffcc00");
				course.setMember(new Member(userId));
				course.setCoach(new Member(obj.getLong("coachId")));

				boolean isExist = false;
				Long requestMember = member.getId();
				for (Apply apply : course.getApplys()) {
					final String status = apply.getStatus();
					System.out.println(status);
					if (apply.getMember().getId().equals(requestMember) && (status.equals("1") || status.equals("2"))) {
						isExist = true;
						break;
					}
				}
				if (isExist) {
					ret.accumulate("success", false).accumulate("message", "joining");// joining:您已经预约过此课程，不再再次进行预约！
					response(ret);
					return;
				}
				// 判断该会员预约的该课程时间段的是否还有其它课程
				final List<?> list = service.findObjectBySql(
						"from Course c where c.member.id = ? and c.planDate = ? and ((c.startTime = ? and c.endTime = ?) or (? between c.startTime and c.endTime) or (? between c.startTime and c.endTime))",
						requestMember, course.getPlanDate(), course.getStartTime(), course.getEndTime(),
						course.getStartTime(), course.getEndTime());
				if (list.size() > 0) {
					ret.accumulate("success", false).accumulate("message", "exist");// exist:您当前时间有其它的课程，请确认！
					response(ret);
				} else {
					course = service.saveAppointment(course);
					final Member m = (Member) service.load(Member.class, obj.getLong("coachId"));// 加载教练信息
					new MessageThread(m.getUserId(), m.getChannelId(), m.getTermType(), "会员“" + member.getName() + "”预约了您"
							+ course.getPlanDate() + "日" + course.getStartTime() + "分“的课程!");
					ret.accumulate("success", true).accumulate("key", course.getId());
					response(ret);
				}
			
		} catch (Exception e) {
			log.error("error", e);
			response(e);
		}
	}
	
	
	
	
	
	
}
