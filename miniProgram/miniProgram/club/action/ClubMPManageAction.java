package miniProgram.club.action;

import java.io.File;
import java.net.URLDecoder;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.cardcol.web.basic.MemberEvaluate;
import com.cardcol.web.utils.DistanceUtil;
import com.databaseConnection.DataBaseConnection;
import com.freegym.web.active.Active;
import com.freegym.web.basic.Friend;
import com.freegym.web.basic.Member;
import com.freegym.web.basic.MemberTicket;
import com.freegym.web.basic.priceCutDown;
import com.freegym.web.config.CourseInfo;
import com.freegym.web.course.Apply;
import com.freegym.web.course.Message;
import com.freegym.web.course.SignIn;
import com.freegym.web.mobile.BasicJsonAction;
import com.freegym.web.order.ActiveOrder;
import com.freegym.web.order.CourseOrder;
import com.freegym.web.order.Goods;
import com.freegym.web.order.GoodsOrder;
import com.freegym.web.order.Order;
import com.freegym.web.order.PickAccount;
import com.freegym.web.order.PickDetail;
import com.freegym.web.order.Product;
import com.freegym.web.order.ProductOrder;
import com.freegym.web.plan.Course;
import com.freegym.web.system.Ticket;
import com.freegym.web.task.MessageThread;
import com.freegym.web.utils.Application;
import com.freegym.web.utils.EasyUtils;
import com.freegym.web.utils.SessionConstant;
import com.freegym.web.wechatPay.utils.MD5Util;
import com.sanmen.web.core.common.LogicException;
import com.sanmen.web.core.utils.LnglatUtil;

import common.util.HttpRequestUtils;
import ecartoon.wx.util.MathRandom4dou;
import ecartoon.wx.util.SaveImgByUrl;
import expert.util.commentsUtil;
import miniProgram.club.constant.Constants;
import miniProgram.club.util.AES;
import miniProgram.club.util.SDK_WX;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * @author hw
 * @version 创建时间：2018年5月3日 上午9:35:32
 * @ClassName 类名称
 * @Description 类描述
 */
@Namespace("")
@InterceptorRefs({ @InterceptorRef("customStack") })
@Results({ @Result(name = "price_cutdown_list", location = "/active/price_cutdown_list.jsp"),
		@Result(name = "clubWifi", location = "/active/clubWifi.jsp") })
public class ClubMPManageAction extends BasicJsonAction implements SessionConstant {
	private static final long serialVersionUID = 1L;

	/**
	 * 上传图片
	 */
	private File image;

	/**
	 * 图片名
	 */
	private String imageFileName;

	/**
	 * 富文本编辑器提交的类容(remark)
	 */
	private String remark;

	/**
	 * 时间格式
	 */
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	private SimpleDateFormat sdfHHmm = new SimpleDateFormat("HH:mm");

	/**
	 * 数据格式化(保留两位小数)
	 */
	private DecimalFormat df = new DecimalFormat("#.00");

	/**
	 * 俱乐部小程序登录
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
					+ Constants.APP_SECRET + "&js_code=" + code + "&grant_type=authorization_code";
			// 请求返回的数据
			JSONObject resJsonObject = HttpRequestUtils.httpGet(requestUrl);

			// 从加密信息中解密出来
			String douStr = "";
			douStr = AES.wxDecrypt(param.getString("encryptedData"), resJsonObject.getString("session_key"),
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
				member.setToKen(MD5Util.MD5Encode(EasyUtils.dateFormat(new Date(), "yyyy-MM-dd"), "utf-8"));

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
				member.setNick(EasyUtils.cutStringEmoji(douInfo.getString("nickName")));
				member.setName(EasyUtils.cutStringEmoji(douInfo.getString("nickName")));

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
					.accumulate("openid", resJsonObject.getString("openid"))
					.accumulate("session_key", resJsonObject.getString("session_key"));

		} catch (Exception e) {
			// 程序异常时返回的数据
			ret.accumulate("success", false).accumulate("message", e.toString());
			e.printStackTrace();
		}

		// 将数据返回
		response(ret);
	}

	/**
	 * 门店明细
	 */
	@SuppressWarnings("unchecked")
	public void findClubById() {
		try {
			// 门店id
			// 测试数据
			// String id = "11865";
			String id = request.getParameter("id");

			// 门店明细信息
			pageInfo = service.queryClubById(pageInfo, id);
			List<Map<String, Object>> clubList0 = pageInfo.getItems();
			Map<String, Object> map = new HashMap<String, Object>();
			DecimalFormat df2 = new DecimalFormat("###");

			// 查询门店签到数
			String sql = "select count(id) count from tb_sign_in where memberAudit = ?";
			Object[] oobb = { id };
			int signCount = Integer.parseInt(DataBaseConnection.getOne(sql, oobb).get("count").toString());

			JSONObject objx = new JSONObject();
			if (clubList0.size() > 0) {
				map = clubList0.get(0);
				int totalScore = Integer.valueOf(df2.format(map.get("totalScore")).toString());
				int deviceScore = Integer.valueOf(df2.format(map.get("deviceScore")).toString());
				int evenScore = Integer.valueOf(df2.format(map.get("evenScore")).toString());
				int serviceScore = Integer.valueOf(df2.format(map.get("serviceScore")).toString());

				// 查询当前俱乐部的服务项目
				String sql2 = "SELECT c.id projectId, c.name projectName, c.image projectImage,"
						+ " c.type projectType,p.name projectTypeName,c.memo FROM tb_course_info c,tb_parameter p"
						+ " WHERE c.type = p.id and c.member = " + id + " order by c.sort";
				List<Map<String, Object>> projectList = service.queryForList(sql2);

				// 查询当前俱乐部的营业时间
				String sql3 = "select m.id,mw.startTime,mw.endTime,m.workDate from tb_member m inner join tb_member_worktime mw"
						+ " on m.id = mw.member where mw.member = " + id;
				Map<String, Object> workTime = service.queryForMap(sql3);

				// 查询当前俱乐部的会员排行榜
				String sql4 = "select t.*,(select count(id) from tb_sign_in where memberSign = t.id) count from ("
						+ " select m.id,m.name,m.image from tb_member_friend mf inner join tb_member m on mf.member = m.id"
						+ " where m.role = 'M' and type = 1 and friend = " + id + ") t order by count desc";
				List<Map<String, Object>> memberRanking = service.queryForList(sql4);

				// 查询俱乐部的banner
				String sql5 = "select banner_image from tb_club_wifi where club = ?";
				Map<String, Object> mm = service.queryForMap(sql5, id);
				String banner = null;
				if (mm != null && mm.containsKey("banner_image")) {
					banner = (String) mm.get("banner_image");
				}

				objx.accumulate("id", map.get("id")).accumulate("name", map.get("name"))
						.accumulate("image", map.get("image")).accumulate("longitude", map.get("longitude"))
						.accumulate("latitude", map.get("latitude")).accumulate("address", map.get("address"))
						.accumulate("mobilephone", map.get("mobilephone")).accumulate("deviceScore", deviceScore)
						.accumulate("evenScore", evenScore).accumulate("serviceScore", serviceScore)
						.accumulate("evaluateNum", map.get("evaluateNum")).accumulate("signCount", signCount)
						.accumulate("star", totalScore).accumulate("workDate", workTime.get("workDate"))
						.accumulate("projectList", projectList).accumulate("startTime", workTime.get("startTime"))
						.accumulate("endTime", workTime.get("endTime")).accumulate("memberRanking", memberRanking)
						.accumulate("banner", banner);
			}

			// 门店明细中的服务项目
			List<Map<String, Object>> list = service.queryProduct45MemberCourse(id);// 门店课程
			int courseCount = list.size();

			// 门店明细内评论列表

			// 评论数
			Map<String, Object> mapEvalCount = service.queryClubEvaluateNum(id);
			String type = "";

			// 好评 type = 1
			type = "1";
			pageInfo = service.queryStoreEvaluate(pageInfo, id, type, "1");// 评论列表
			JSONArray memberEvaluate1 = JSONArray.fromObject(pageInfo.getItems());
			// 好评数
			int evalCount1 = Integer.valueOf(mapEvalCount.get("Aevaluate").toString());
			JSONArray jarr1 = new JSONArray();
			if (memberEvaluate1.size() > 0) {
				for (int i = 0; i < memberEvaluate1.size(); i++) {
					JSONObject job = memberEvaluate1.getJSONObject(i);
					Map<String, Object> map1 = job;
					Member m = (Member) service.load(Member.class, job.getLong("mid"));
					getJsonForMember(m);
					map1.put("eval_content", DistanceUtil.decodeFromNonLossyAscii((String) map1.get("eval_content")));
					map1.put("taobaoId", m.getId().toString());
					jarr1.add(map1);
				}
			}

			// 中评 type= 2
			type = "2";
			pageInfo = service.queryStoreEvaluate(pageInfo, id, type, "1");// 评论列表
			JSONArray memberEvaluate2 = JSONArray.fromObject(pageInfo.getItems());
			// 中评数
			int evalCount2 = Integer.valueOf(mapEvalCount.get("Bevaluate").toString());
			JSONArray jarr2 = new JSONArray();
			if (memberEvaluate2.size() > 0) {
				for (int i = 0; i < memberEvaluate2.size(); i++) {
					JSONObject job = memberEvaluate2.getJSONObject(i);
					Map<String, Object> map2 = job;
					Member m = (Member) service.load(Member.class, job.getLong("mid"));
					getJsonForMember(m);
					map2.put("eval_content", DistanceUtil.decodeFromNonLossyAscii((String) map2.get("eval_content")));
					map2.put("taobaoId", m.getId().toString());
					jarr2.add(map2);
				}
			}

			// 差评 type= 3
			type = "3";
			pageInfo = service.queryStoreEvaluate(pageInfo, id, type, "1");// 评论列表
			JSONArray memberEvaluate3 = JSONArray.fromObject(pageInfo.getItems());
			// 差评数
			int evalCount3 = Integer.valueOf(mapEvalCount.get("Cevaluate").toString());
			JSONArray jarr3 = new JSONArray();
			if (memberEvaluate2.size() > 0) {
				for (int i = 0; i < memberEvaluate3.size(); i++) {
					JSONObject job = memberEvaluate3.getJSONObject(i);
					Map<String, Object> map3 = job;
					Member m = (Member) service.load(Member.class, job.getLong("mid"));
					getJsonForMember(m);
					map3.put("eval_content", DistanceUtil.decodeFromNonLossyAscii((String) map3.get("eval_content")));
					map3.put("taobaoId", m.getId().toString());
					jarr3.add(map3);
				}
			}

			// 当前俱乐部可用的优惠券
			String querySql = "select t.id,tl.image,0 state from tb_ticket t inner join tb_ticket_limit tl on t.id = tl.ticket"
					+ " where t.effective = 1 and tl.limit_club like '%" + id + "%'";
			List<Map<String, Object>> ticketList = service.queryForList(querySql);

			// 当前俱乐部发布的健身卡
			querySql = "select t.*,(select count(no) from tb_product_order where product = t.id and status > 0) sell_out"
					+ " from (select id,name,image1,wellNum,remark,cost,freeProject,useRange from tb_product"
					+ " where member = " + id + " and audit = 1 and isClose = 2 order by topTime desc) t";
			List<Map<String, Object>> cardList = service.queryForList(querySql);

			final JSONObject obj = new JSONObject();
			obj.accumulate("success", true).accumulate("club", objx).accumulate("course", list)
					.accumulate("courseCount", courseCount).accumulate("evaluate1", jarr1)
					.accumulate("evaluate2", jarr2).accumulate("evaluate3", jarr3).accumulate("evalCount1", evalCount1)
					.accumulate("evalCount2", evalCount2).accumulate("evalCount3", evalCount3)
					.accumulate("evaluateNum", mapEvalCount).accumulate("pageInfo", getJsonForPageInfo())
					.accumulate("ticketList", ticketList).accumulate("cardList", cardList);
			response(obj);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 根据id获取商品
	 */
	public void getProductById() {
		try {
			String productId = request.getParameter("id");
			String querySql = "select t.*,(select count(no) from tb_product_order where product = t.id and status > 0) sell_out"
					+ " from (select id,name,image1,wellNum,remark,cost,freeProject,useRange from tb_product"
					+ " where id = " + productId + ") t";
			Map<String, Object> product = service.queryForMap(querySql);
			response(new JSONObject().accumulate("success", true).accumulate("product", product));
		} catch (Exception e) {
			e.printStackTrace();
			response(e);
		}
	}

	/**
	 * 查询健身卡的适用俱乐部列表
	 */
	public void getClubListByCard() {
		try {
			JSONObject param = JSONObject.fromObject(URLDecoder.decode(request.getParameter("json"), "UTF-8"));
			String queryClub = "select c.*,(select IFNULL(avg(e.TOTALITY_SCORE), 0) from tb_member_evaluate e inner join tb_sign_in s on e.signIn = s.id where s.memberAudit = c.id) totalityScore,"
					+ " (select IFNULL(avg(e.DEVICE_SCORE), 0) from tb_member_evaluate e inner join tb_sign_in s on e.signIn = s.id where s.memberAudit = c.id) deviceScore,"
					+ " (select IFNULL(avg(e.EVEN_SCORE), 0) from tb_member_evaluate e inner join tb_sign_in s on e.signIn = s.id where s.memberAudit = c.id) evenScore,"
					+ " (select IFNULL(avg(e.SERVICE_SCORE), 0) from tb_member_evaluate e inner join tb_sign_in s on e.signIn = s.id where s.memberAudit = c.id) serviceScore,"
					+ " (select count(e.id) from tb_member_evaluate e inner join tb_sign_in s on e.signIn = s.id where s.memberAudit = c.id) evaluateCount,"
					+ " ROUND(6378.138 * 2 * ASIN(SQRT(POW(SIN((c.latitude * PI() / 180 - c.mLatitude * PI() / 180) / 2),2) + COS(c.mLatitude * PI() / 180) * COS(c.latitude * PI() / 180) * POW(SIN((c.longitude * PI() / 180 - c.mLongitude * PI() / 180) / 2),2))) * 1000) AS distance"
					+ " from (select id,image,name,latitude,longitude," + param.getString("latitude") + " mLatitude,"
					+ param.getString("longitude") + " mLongitude from tb_member where id in ("
					+ param.getString("clubIds") + ")) c";
			List<Map<String, Object>> clubList = service.queryForList(queryClub);
			JSONObject result = new JSONObject();
			result.accumulate("success", true).accumulate("clubList", clubList);
			response(result);
		} catch (Exception e) {
			e.printStackTrace();
			response(e);
		}
	}

	/**
	 * 检查加入状态
	 */
	public void checkMemberToClub() {
		try {
			String memberId = request.getParameter("memberId");
			String clubId = request.getParameter("clubId");
			String querySql = "select count(id) from tb_member_friend where friend = " + clubId + " and member = "
					+ memberId;
			long count = service.queryForLong(querySql);
			response(new JSONObject().accumulate("success", true).accumulate("status", count));
		} catch (Exception e) {
			e.printStackTrace();
			response(e);
		}
	}

	/**
	 * 申请加入俱乐部
	 */
	public void request() {
		try {
			String memberId = request.getParameter("memberId");
			String clubId = request.getParameter("clubId");
			// 当前登录的会员
			Member member = (Member) service.load(Member.class, Long.valueOf(memberId));
			// 申请加入的俱乐部
			Member club = (Member) service.load(Member.class, Long.valueOf(clubId));
			List<?> fList = service.findObjectBySql(
					"from Friend f where f.member.id = ? and f.friend.id = ? and type = ?",
					new Object[] { Long.valueOf(memberId), Long.valueOf(memberId), "1" });
			if (fList.size() > 0) {
				response(new JSONObject().accumulate("success", false).accumulate("message", "您已经加入了该俱乐部，不得重复加入！"));
			}
			final Message msg = new Message();
			msg.setContent("申请加入俱乐部！");
			msg.setIsRead("0");
			msg.setMemberFrom(member);
			msg.setMemberTo(club);
			msg.setSendTime(new Date());
			msg.setStatus("1");
			msg.setType("1");
			service.saveOrUpdate(msg);
			final JSONObject ret = new JSONObject();
			ret.accumulate("success", true).accumulate("message", "OK");
			response(ret);
		} catch (Exception e) {
			log.error("error", e);
			response(e);
		}
	}

	/**
	 * 解除关系
	 */
	public void saveRelieve() {
		try {
			String memberId = request.getParameter("memberId");
			String clubId = request.getParameter("clubId");
			String querySql = "select id from tb_member_friend where friend = ? and member = ?";
			long id = service.queryForLong(querySql, clubId, memberId);
			Member member = (Member) service.load(Member.class, Long.valueOf(memberId));
			Friend friend = (Friend) service.load(Friend.class, id);
			service.saveRelieve("4", member, friend);
			response(new JSONObject().accumulate("success", true));
		} catch (Exception e) {
			e.printStackTrace();
			response(e);
		}
	}

	/**
	 * 接收消息推送
	 */
	public void receive() {
		try {
			// 获取微信传来的参数
			JSONObject param = JSONObject.fromObject(EasyUtils.getInputStreamParam(request));
			// 判断消息类型
			if (!param.containsKey("MsgType") && StringUtils.isEmpty(param.getString("MsgType"))) {
				return;
			}
			JSONObject result = new JSONObject();
			// 第一步:请求AccessToken
			String getAccessTokenURL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="
					+ Constants.APPID + "&secret=" + Constants.APP_SECRET;
			JSONObject accessTokenResult = HttpRequestUtils.httpGet(getAccessTokenURL);
			String accessToken = accessTokenResult.getString("access_token");
			// 第二步:创建消息数据包
			JSONObject sendParam = new JSONObject();
			JSONObject text = new JSONObject();
			sendParam.accumulate("touser", param.get("FromUserName"));
			sendParam.accumulate("msgtype", "text");
			// 用户进入俱乐部小程序页面(发送欢迎消息)
			if (Constants.MSG_TYPE_EVENT.equals(param.getString("MsgType"))) {
				text.accumulate("content", Constants.CLUB_MINI_PROGRAM_WELLCOME_MESSAGE);
				result.accumulate("success", true);
			} else {
				// 用户发送消息(回应普通消息模板)
				text.accumulate("content", Constants.CLUB_MINI_PROGRAM_PUBLIC_MESSAGE);
				result.accumulate("ToUserName", param.get("FromUserName"))
						.accumulate("FromUserName", param.get("ToUserName"))
						.accumulate("CreateTime", param.get("CreateTime"))
						.accumulate("MsgType", "transfer_customer_service");
			}
			sendParam.accumulate("text", text);
			// 第三步:请求微信小程序服务器发送消息接口(发送消息)
			String sendMessageURL = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=" + accessToken;
			HttpRequestUtils.httpPost(sendMessageURL, sendParam);
			response(result);
		} catch (Exception e) {
			e.printStackTrace();
			response(e);
		}
	}

	/**
	 * 给用户添加一张优惠券
	 */
	public void setTicketToMember() {
		try {
			String memberId = request.getParameter("memberId");
			String ticketId = request.getParameter("ticketId");
			String phoneNumber = request.getParameter("phoneNumber");
			String shareMember = request.getParameter("shareMember");
			if (shareMember == null) {
				Member member = (Member) service.load(Member.class, Long.valueOf(memberId));
				Ticket ticket = (Ticket) service.load(Ticket.class, Long.valueOf(ticketId));
				MemberTicket memberTicket = new MemberTicket();
				memberTicket.setActiveDate(new Date());
				memberTicket.setMember(member);
				memberTicket.setTicket(ticket);
				memberTicket.setStatus(1);
				if (phoneNumber != null) {
					member.setMobilephone(phoneNumber);
					member.setMobileValid("1");
				}
				service.saveOrUpdate(memberTicket);
			} else {
				MemberTicket memberTicket = (MemberTicket) service.load(MemberTicket.class, Long.valueOf(ticketId));
				Member member = (Member) service.load(Member.class, Long.valueOf(memberId));
				memberTicket.setMember(member);
				memberTicket.setActiveCode(shareMember);
				if (phoneNumber != null) {
					member.setMobilephone(phoneNumber);
					member.setMobileValid("1");
				}
				service.saveOrUpdate(memberTicket);
			}
			response(new JSONObject().accumulate("success", true));
		} catch (Exception e) {
			e.printStackTrace();
			response(e);
		}
	}

	public void getSignTime() {
		try {
			String memberId = request.getParameter("memberId");
			String clubId = request.getParameter("clubId");
			String currentDate = EasyUtils.dateFormat(new Date(), "yyyy-MM-dd");
			// 查询当前签到记录
			String querySignIn = "select * from tb_sign_in where memberSign = " + memberId + " and signDate LIKE '%"
					+ currentDate + "%'";
			Map<String, Object> signIn = service.queryForMap(querySignIn);
			// 查询当前签出记录
			String querySignOut = "select * from tb_sign_in where f_money = 1 and memberSign = " + memberId
					+ " and signDate LIKE '%" + currentDate + "%'";
			Map<String, Object> signOut = service.queryForMap(querySignOut);
			// 查询俱乐部设定的时间
			String queryTimeout = "select msn from tb_member where id = " + clubId;
			long clubTimeout = service.queryForLong(queryTimeout);
			// 返回结果
			JSONObject result = new JSONObject();
			if (signOut != null && signOut.containsKey("id")
					&& StringUtils.isNotEmpty(String.valueOf(signOut.get("id")))) {
				result.accumulate("status", 2);
			} else if (signIn != null && signIn.containsKey("id")
					&& StringUtils.isNotEmpty(String.valueOf(signIn.get("id")))) {
				Date signDate = (Date) signIn.get("signDate");
				long time = (new Date().getTime() - signDate.getTime()) / 1000;
				if (time < (clubTimeout * 60)) {
					time = (clubTimeout * 60) - time;
				} else {
					time = 0;
				}
				result.accumulate("status", 1).accumulate("time", time);
			} else {
				result.accumulate("status", 0).accumulate("time", clubTimeout * 60).accumulate("totalTime",
						clubTimeout);
			}
			response(result);
		} catch (Exception e) {
			e.printStackTrace();
			response(e);
		}
	}

	/**
	 * 俱乐部小程序签到接口
	 */
	public void sign() {
		try {
			JSONObject param = JSONObject.fromObject(URLDecoder.decode(request.getParameter("json"), "UTF-8"));
			JSONObject result = new JSONObject();
			// 查询出当前登录用户和俱乐部
			Member member = (Member) service.load(Member.class, param.getLong("memberId"));
			Member club = (Member) service.load(Member.class, param.getLong("clubId"));
			// 校验1: 手机终端的位置是否在当前俱乐部的3000米内
			if (!param.containsKey("longitude") || !param.containsKey("latitude")) {
				result.accumulate("success", false).accumulate("message", "服务端没有获取到经纬度信息!");
				response(result);
				return;
			} else {
				// 计算距离
				Double distance = LnglatUtil.GetDistance(param.getDouble("longitude"), param.getDouble("latitude"),
						club.getLongitude(), club.getLatitude());
				if (distance > Constants.SIGN_DISTANCE) {
					result.accumulate("success", false).accumulate("message", "请在俱乐部3000米范围内签到!");
					response(result);
					return;
				}
			}
			// 校验2: 当前用户是否购买过该俱乐部的健身卡或者是当前俱乐部的会员
			String querySign = "select po.id orderId, p.id productId, po.member memberId, p.member clubId"
					+ " from tb_product_order po inner join tb_product p on po.product = p.id inner join tb_member m"
					+ " on po.member = m.id where po.status = 1 and po.member = " + param.getString("memberId")
					+ " and p.member = " + param.getString("clubId") + " and po.orderEndTime >= '"
					+ EasyUtils.dateFormat(new Date(), "yyyy-MM-dd") + "'";
			List<Map<String, Object>> productList = service.queryForList(querySign);
			// 查询是否为当前俱乐部的会员
			String queryMemberResult = "select count(id) count from tb_member_friend where friend = "
					+ param.getString("clubId") + " and member = " + param.getString("memberId");
			int memberReslut = service.queryForDouble(queryMemberResult).intValue();
			// 首先判断有没有购买订单
			if (productList.size() < 1) {
				// 如何没有订单就判断是否为当前俱乐部的会员
				if (memberReslut < 1) {
					result.accumulate("success", false).accumulate("message", "您没有有效订单，无法签到，请购买健身卡！");
					response(result);
					return;
				}
			}
			// 校验3: 当前用户是否已经签到签出过(每天只能签到签出一次)
			// 查询该会员该订单当天的签出次数
			String querySql = "select count(s.id) from tb_sign_in s where s.f_money = 1 and s.memberSign =? and s.signDate LIKE  '%"
					+ EasyUtils.dateFormat(new Date(), "yyyy-MM-dd") + "%' and memberAudit = ?";
			long count = service.queryForLong(querySql, param.getLong("memberId"), param.getLong("clubId"));
			if (count > 0) {
				result.accumulate("success", false).accumulate("message", "每天只能签到签出一次!");
				response(result);
				return;
			}

			// 查询当天是否已经签到过
			querySql = "select t.id from (select IFNULL(id, 0) id,count(id) count from tb_sign_in where"
					+ " memberSign = ? and signDate LIKE '%" + EasyUtils.dateFormat(new Date(), "yyyy-MM-dd") + "%') t";
			long signId = service.queryForLong(querySql, param.getLong("memberId"));
			// 校验通过, 执行签到逻辑
			SignIn signIn = null;
			if (signId > 0) {
				signIn = (SignIn) service.load(SignIn.class, signId);
				signIn.setMoney(Double.valueOf("1"));
				result.accumulate("sign_out", true);
			} else {
				signIn = new SignIn();
			}
			signIn.setMemberAudit(club);
			signIn.setMemberSign(member);
			signIn.setSignDate(new Date());
			signIn.setSignLng(param.getDouble("longitude"));
			signIn.setSignLat(param.getDouble("latitude"));
			// 如果有订单填写订单信息, 没有签到类型设置为0:俱乐部会员签到类型
			if (productList.size() > 0) {
				String orderId = String.valueOf(productList.get(0).get("orderId"));
				ProductOrder productOrder = (ProductOrder) service.load(ProductOrder.class, Long.valueOf(orderId));
				// 如果当前订单开始时间大于当前时间则提前订单的开始时间
				if (productOrder.getOrderStartTime().getTime() > new Date().getTime()) {
					productOrder.setOrderStartTime(new Date());
					service.saveOrUpdate(productOrder);
				}
				signIn.setOrderId(productOrder.getId());
				signIn.setOrderNo(productOrder.getNo());
				signIn.setSignType(Constants.PRODUCT_ORDER_SIGN_TYPE);
			} else {
				signIn.setSignType(Constants.MEMBER_ORDER_SIGN_TYPE);
			}
			service.saveOrUpdate(signIn);
			// 返回成功信息
			result.accumulate("success", true).accumulate("message", "OK");
			response(result);
		} catch (Exception e) {
			e.printStackTrace();
			response(e);
		}
	}

	/**
	 * 查询该俱乐部发布的挑战
	 */
	public void findActiveAndDetailByClub() {
		try {
			String clubId = request.getParameter("clubId");
			String querySql = "select t.*,(select count(id) from tb_active_order where active = t.id and `status` > 0)"
					+ " count from (select a.id,a.name,a.target,a.days,a.value,a.active_image image,a.amerce_money,"
					+ " a.award,mm.name institution,a.memo,m.name creator from tb_active a inner join tb_member m"
					+ " on m.id = a.creator inner join tb_member mm on mm.id = a.institution where creator = " + clubId
					+ " and status = 'B') t";
			List<Map<String, Object>> activeList = service.queryForList(querySql);
			response(new JSONObject().accumulate("success", true).accumulate("activeList", activeList));
		} catch (Exception e) {
			e.printStackTrace();
			response(e);
		}
	}

	/**
	 * 查询我的挑战和详情
	 */
	public void findActiveAndDetailByMember() {
		try {
			String memberId = request.getParameter("memberId");
			String queryActive = "select t.*,(select count(id) from tb_active_order where status > 0 and active = t.id) count,"
					+ " (select count(id) from tb_sign_in where memberSign = t.memberId and signDate BETWEEN t.orderStartTime"
					+ " and t.orderEndTime) signCount from (select a.id,ao.id orderId, ao.member memberId, a.name,a.target,a.days,"
					+ " a.value, a.active_image image,a.amerce_money,a.award,mm.name institution,a.memo,m.name creator,ao.result,"
					+ " ao.orderStartTime,ao.orderEndTime,ao.weight,ao.LAST_WEIGHT lastWeight from tb_active a inner join tb_active_order ao"
					+ " on a.id = ao.active inner join tb_member m on a.creator = m.id inner join tb_member mm on a.institution = mm.id"
					+ " where ao.status > 0 and ao.member = " + memberId + ") t";
			List<Map<String, Object>> activeList = service.queryForList(queryActive);
			response(new JSONObject().accumulate("success", true).accumulate("activeList",
					EasyUtils.dateFormat(activeList, "yyyy-MM-dd")));
		} catch (Exception e) {
			e.printStackTrace();
			response(e);
		}
	}

	public void getActiveById() {
		try {
			String activeId = request.getParameter("activeId");
			String querySql = "select t.*,(select count(id) from tb_active_order where active = t.id and `status` > 0)"
					+ " count from (select a.id,a.name,a.target,a.days,a.value,a.active_image image,a.amerce_money,"
					+ " a.award,mm.name institution,a.memo,m.name creator from tb_active a inner join tb_member m"
					+ " on m.id = a.creator inner join tb_member mm on mm.id = a.institution where a.id = " + activeId
					+ " ) t";
			Map<String, Object> active = service.queryForMap(querySql);
			response(new JSONObject().accumulate("success", true).accumulate("active",
					EasyUtils.dateFormat(active, "yyyy-MM-dd")));
		} catch (Exception e) {
			e.printStackTrace();
			response(e);
		}
	}

	/**
	 * 挑战成绩提交
	 */
	public void submitWeight() {
		// 用户填写的体重
		Double weight = new Double(request.getParameter("weight"));
		service.saveActiveResult(weight, id);
		ActiveOrder ao = (ActiveOrder) service.load(ActiveOrder.class, id);
		JSONObject obj = new JSONObject();
		ao.setLastWeight(weight);
		ao = (ActiveOrder) service.saveOrUpdate(ao);
		obj.accumulate("success", true).accumulate("code", ao.getResult());
		response(obj);
	}

	/**
	 * 我的足迹
	 */
	public void myFooter() {
		try {
			String memberId = request.getParameter("memberId");
			String clubId = request.getParameter("clubId");
			String querySign = "select s.id,m.id clubId,m.name,m.image,s.signDate,me.TOTALITY_SCORE totalityScore,"
					+ " me.SERVICE_SCORE servieScore,me.DEVICE_SCORE deviceScore,me.EVEN_SCORE evenScore from tb_sign_in s"
					+ " inner join tb_member m on s.memberAudit = m.id left join tb_member_evaluate me on s.id = me.signIn "
					+ " where s.f_money = 1 and s.memberAudit = ? and s.memberSign =  " + memberId
					+ " order by s.signDate desc";
			List<Map<String, Object>> signList = EasyUtils.dateFormat(service.queryForList(querySign, clubId),
					"yyyy-MM-dd");
			response(new JSONObject().accumulate("succees", true).accumulate("signList", signList));
		} catch (Exception e) {
			e.printStackTrace();
			response(e);
		}
	}

	/**
	 * 我的点评详情
	 */
	public void memberEvaluate() {
		try {
			String signId = request.getParameter("signId");
			String queryEvaluate = "select t.*,(select count(id) from tb_sign_in where memberSign = t.memberId) signCount from ("
					+ " select s.id,m.id clubId,m.name,m.image,m.address,s.signDate,p.name productName,me.TOTALITY_SCORE totalityScore,"
					+ " me.SERVICE_SCORE serviceScore,me.DEVICE_SCORE deviceScore,me.EVEN_SCORE evenScore,me.EVAL_TIME evalTime,"
					+ " me.EVAL_CONTENT evalContent,me.image1 evaluateImage1,me.image2 evaluateImage2,me.image3 evaluateImage3,"
					+ " mm.id memberId,mm.name memberName,mm.image memberImage from tb_sign_in s inner join tb_member m on"
					+ " s.memberAudit = m.id inner join tb_member mm  on s.memberSign = mm.id left join tb_product_order po on"
					+ " s.orderId = po.id left join tb_product p on po.product = p.id left join tb_member_evaluate me on s.id = me.signIn"
					+ " where s.id = " + signId + ") t";
			Map<String, Object> evaluate = service.queryForMap(queryEvaluate);
			JSONObject result = new JSONObject();
			result.accumulate("memberEvaluate", EasyUtils.dateFormat(evaluate, "yyyy-MM-dd HH:mm"));
			result.accumulate("success", true);
			response(result);
		} catch (Exception e) {
			e.printStackTrace();
			response(e);
		}
	}

	/**
	 * 俱乐部小程序会员评价俱乐部
	 */
	public void memberEvaluateToClub() {
		try {
			String json = request.getParameter("json");
			JSONObject obj = JSONObject.fromObject(URLDecoder.decode(json, "UTF-8"));
			MemberEvaluate me = new MemberEvaluate();
			// 设备分
			me.setDeviceScore(obj.getInt("deviceScore"));
			// 评价时间
			me.setEvalTime(new Date());
			// 环境分
			me.setEvenScore(obj.getInt("evenScore"));
			// 签到方(签到订单id)
			Member member = (Member) service.load(Member.class, obj.getLong("memberId"));
			me.setMember(member);
			// 服务分
			me.setServiceScore(obj.getInt("serviceScore"));
			// 被签到方
			SignIn signIn = (SignIn) service.load(SignIn.class, obj.getLong("id"));
			me.setSignIn(signIn);
			// 评价总分
			me.setTotailtyScore(obj.getInt("totalityScore"));
			String content = obj.getString("evalContent");
			me.setContent(content);
			if (obj.containsKey("evaluateImage1") && !Constants.NULL.equals(obj.getString("evaluateImage1"))) {
				me.setImage1(obj.getString("evaluateImage1"));
			}
			if (obj.containsKey("evaluateImage2") && !Constants.NULL.equals(obj.getString("evaluateImage2"))) {
				me.setImage2(obj.getString("evaluateImage2"));
			}
			if (obj.containsKey("evaluateImage3") && !Constants.NULL.equals(obj.getString("evaluateImage3"))) {
				me.setImage3(obj.getString("evaluateImage3"));
			}
			me = (MemberEvaluate) service.saveOrUpdate(me);
			final JSONObject ob = new JSONObject();
			ob.accumulate("success", true);
			response(ob);
		} catch (Exception e) {
			log.error("error", e);
			response(e);
		}
	}

	/**
	 * 解密手机号
	 */
	public void decodePhoneNumber() {
		try {
			JSONObject json = JSONObject.fromObject(request.getParameter("json"));
			JSONObject detail = json.getJSONObject("detail");
			String encryptedData = detail.getString("encryptedData");
			String session_key = json.getString("session_key");
			String iv = detail.getString("iv");
			String result = AES.wxDecrypt(encryptedData, session_key, iv);
			response(result);
		} catch (Exception e) {
			e.printStackTrace();
			response(e);
		}
	}

	/**
	 * 更新用户手机号
	 */
	public void updateMobilephone() {
		JSONObject ret = new JSONObject();
		try {
			// 处理请求参数
			JSONObject param = JSONObject.fromObject(URLDecoder.decode(request.getParameter("json"), "UTF-8"));

			// 用户登录
			Member member = (Member) service.load(Member.class, param.getLong("memberId"));

			member.setMobilephone(param.getString("mobilephone"));
			member.setMobileValid("1");

			// 持久化到数据库
			member = (Member) service.saveOrUpdate(member);

			ret.accumulate("success", true).accumulate("message", "OK");

		} catch (Exception e) {
			ret.accumulate("success", false).accumulate("message", e.toString());
			e.printStackTrace();
		}
		response(ret);
	}

	/**
	 * 我的优惠券
	 */
	public void findMyTicket() {
		try {
			String memberId = request.getParameter("memberId");
			String queryTicket = "select t.id,mt.id ticketId,t.name,t.price,t.scope,DATE_ADD(mt.active_date, INTERVAL t.period DAY) useDate,"
					+ " tl.image,mt.active_date activeDate,t.period,m.name clubName,tl.limit_price limitPrice from tb_member_ticket mt inner join tb_ticket t on mt.ticket = t.id inner join"
					+ " tb_ticket_limit tl on t.id = tl.ticket inner join tb_member m on tl.limit_club = m.id where DATE_ADD(mt.active_date, INTERVAL t.period DAY) >= CURRENT_DATE()"
					+ " and mt.member = " + memberId + " and status = 1";
			List<Map<String, Object>> ticketList = service.queryForList(queryTicket);
			response(new JSONObject().accumulate("success", true).accumulate("ticketList",
					EasyUtils.dateFormat(ticketList, "yyyy-MM-dd")));
		} catch (Exception e) {
			e.printStackTrace();
			response(e);
		}
	}

	/**
	 * 通过id查询优惠券
	 */
	public void getTicketById() {
		try {
			String ticketId = request.getParameter("ticketId");
			String shareMember = request.getParameter("shareMember");
			if (shareMember == null) {
				shareMember = "0";
			}

			String queryTicket = "select t.*,m.name shareMemberName,m.image shareMemberImage from ("
					+ " select t.id,mt.id ticketId,t.name,t.price,t.scope,DATE_ADD(mt.active_date, INTERVAL t.period DAY) useDate,"
					+ " tl.image,mt.active_date activeDate,t.period,m.name clubName,tl.limit_price limitPrice,tl.limit_club limitClub, "
					+ " mm.name collector," + shareMember
					+ " shareMember from tb_member_ticket mt inner join tb_ticket t on mt.ticket = t.id inner join"
					+ " tb_ticket_limit tl on t.id = tl.ticket inner join tb_member m on tl.limit_club = m.id left join tb_member mm "
					+ " on mt.active_code = mm.id  where mt.id = " + ticketId
					+ " ) t left join tb_member m on t.shareMember = m.id";
			Map<String, Object> ticket = service.queryForMap(queryTicket);

			String queryClub = "select id,name,image from tb_member where id in(" + ticket.get("limitClub") + ")";
			List<Map<String, Object>> clubList = service.queryForList(queryClub);

			response(new JSONObject().accumulate("success", true)
					.accumulate("ticket", EasyUtils.dateFormat(ticket, "yyyy-MM-dd"))
					.accumulate("clubList", EasyUtils.dateFormat(clubList, "yyyy-MM-dd")));
		} catch (Exception e) {
			e.printStackTrace();
			response(e);
		}
	}

	/**
	 * 清除优惠券的领取人
	 */
	public void clearTicketCollector() {
		try {
			String ticketId = request.getParameter("ticketId");
			MemberTicket memberTicket = (MemberTicket) service.load(MemberTicket.class, Long.valueOf(ticketId));
			memberTicket.setActiveCode(null);
			service.saveOrUpdate(memberTicket);
			response(new JSONObject().accumulate("success", true));
		} catch (Exception e) {
			e.printStackTrace();
			response(e);
		}
	}

	/**
	 * 取得当前用户当前订单类型所有适用的优惠券
	 */
	@SuppressWarnings("unused")
	public void findTicketByType() {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Member member = (Member) service.load(Member.class, Long.valueOf(request.getParameter("memberId")));
			String productId = request.getParameter("productId");
			String productType = request.getParameter("productType");
			String clubId = request.getParameter("clubId");
			double productPrice = 0.0;
			String orderType = "";
			if (Constants.PRODUCT_TYPE_PRODUCT.equals(productType)) {
				Product product = (Product) service.load(Product.class, Long.valueOf(productId));
				orderType = "1";
				productPrice = product.getCost();
			} else {
				Active a = (Active) service.load(Active.class, Long.valueOf(productId));
				orderType = "2";
				productPrice = a.getAmerceMoney();
			}
			List<Map<String, Object>> mts = service.queryForList(
					"SELECT a.id tid, a.active_date activeDate, b.*,DATE_ADD(a.active_date, INTERVAL b.period DAY) useDate,b.scope FROM tb_member_ticket a LEFT JOIN tb_ticket b ON a.ticket = b.id WHERE DATE_ADD(a.active_date, INTERVAL b.period DAY) >= CURRENT_DATE() and a.status = ? and a.member = ? and b.scope like ?",
					STATUS_TICKET_USE, member.getId(), "%" + orderType + "%");
			final JSONArray jarr = new JSONArray();
			for (final Map<String, Object> mt : mts) {
				String querySql = "select t.id,t.name,t.price,tl.image,tl.limit_price limitPrice,limit_club,mt.id ticketId,m.name clubName"
						+ " from tb_ticket t inner join tb_ticket_limit tl  on t.id = tl.ticket inner join tb_member_ticket mt"
						+ " on mt.ticket = tl.ticket inner join tb_member m on tl.limit_club = m.id where mt.id = "
						+ mt.get("tid");
				Map<String, Object> map = service.queryForMap(querySql);
				if (map != null && map.containsKey("id") && map.get("id") != null) {
					if (!clubId.equals(map.get("limit_club"))) {
						continue;
					}
					double limitPrice = Double.parseDouble(String.valueOf(map.get("limitPrice")));
					if (productPrice >= limitPrice) {
						map.put("scope", mt.get("scope"));
						map.put("useDate", mt.get("useDate"));
						jarr.add(EasyUtils.dateFormat(map, "yyyy-MM-dd"));
					}
				}
			}
			JSONObject obj = new JSONObject();
			obj.accumulate("success", true).accumulate("tickets", jarr);
			response(obj);
		} catch (Exception e) {
			log.error("error", e);
			response(e);
		}
	}

	/**
	 * 获得优惠券列表
	 */
	public void getTicketList() {
		try {
			Member member = toMember();
			String querySql = "select t.id,t.name,t.price,t.kind,t.period,t.scope,t.active_code activeCode,t.effective,"
					+ " tl.limit_price,tl.limit_club,tl.image from tb_ticket t "
					+ " left join tb_ticket_limit tl on t.id = tl.ticket where tl.creator = " + member.getId()
					+ " order by t.id desc";
			List<Map<String, Object>> ticketList = service.queryForList(querySql);
			session.setAttribute("spath", 5);
			session.setAttribute("ticketList", JSONArray.fromObject(ticketList));
			request.getRequestDispatcher("/active/ticket_start_list.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 */
	public void editTicket() {
		try {
			String ticketId = request.getParameter("ticketId");
			Member member = toMember();
			if (ticketId != null) {
				String querySql = "select t.id,t.name,t.price,t.kind,t.period,t.scope,t.active_code activeCode,t.effective,"
						+ " tl.limit_price,tl.limit_club,tl.image from tb_ticket t "
						+ " left join tb_ticket_limit tl on t.id = tl.ticket where t.id = " + ticketId
						+ " order by t.id desc";
				Map<String, Object> map = service.queryForMap(querySql);
				request.setAttribute("ticket", JSONObject.fromObject(map));
			}
			if (request.getParameter("look") != null) {
				request.setAttribute("look", 1);
			}

			String querySql = "select id,name from tb_member where id = ? UNION ALL select m.id,m.name from tb_member m"
					+ " inner join tb_member_friend f on m.id = f.member where m.role = 'E' and f.friend = ?";
			List<Map<String, Object>> list = service.queryForList(querySql, member.getId(), member.getId());
			request.setAttribute("clubList", JSONArray.fromObject(list));
			request.setAttribute("member", member);
			session.setAttribute("spath", 5);
			request.getRequestDispatcher("active/ticket_edit.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void saveTicket() {
		try {
			String json = request.getParameter("json");
			JSONObject param = JSONObject.fromObject(URLDecoder.decode(json, "UTF-8"));
			Member member = toMember();
			if (!param.containsKey("id")) {
				Ticket ticket = new Ticket();
				ticket.setName(param.getString("name"));
				ticket.setPrice(param.getDouble("price"));
				ticket.setPeriod(param.getInt("period"));
				ticket.setScope("1,2,3,4,5,6,7");
				ticket.setActiveCode(Application.getRandom());
				ticket.setEffective("1");
				ticket.setKind("A");
				ticket = (Ticket) service.saveOrUpdate(ticket);

				imageFileName = saveFile("picture", image, imageFileName, null);
				if (imageFileName == null) {
					imageFileName = "";
				}
				String insertSql = "insert into tb_ticket_limit(ticket,limit_price,limit_club,image,creator) values("
						+ ticket.getId() + "," + param.getDouble("limit_price") + ",'" + param.getString("limit_club")
						+ "','" + imageFileName + "'," + member.getId() + ")";
				service.executeUpdate(insertSql);
				this.getTicketList();
			} else {
				Ticket ticket = (Ticket) service.load(Ticket.class, Long.valueOf(param.getLong("id")));
				ticket.setName(param.getString("name"));
				ticket.setPrice(param.getDouble("price"));
				ticket.setPeriod(param.getInt("period"));
				ticket = (Ticket) service.saveOrUpdate(ticket);

				imageFileName = saveFile("picture", image, imageFileName, null);
				if (imageFileName == null) {
					imageFileName = "";
				}
				StringBuilder updateSql = new StringBuilder("update tb_ticket_limit set");
				updateSql.append(" limit_price=").append(param.getDouble("limit_price")).append(",");
				updateSql.append(" limit_club='").append(param.getString("limit_club")).append("',");
				updateSql.append(" image='").append(imageFileName).append("'");
				updateSql.append(" where ticket=").append(ticket.getId());
				service.executeUpdate(updateSql.toString());
				this.getTicketList();
			}
		} catch (Exception e) {
			e.printStackTrace();
			this.getTicketList();
		}
	}

	public void changeTicketStatus() {
		try {
			String ticketId = request.getParameter("ticketId");
			Ticket ticket = (Ticket) service.load(Ticket.class, Long.valueOf(ticketId));
			if (ticket.getEffective().equals("1")) {
				ticket.setEffective("0");
			} else {
				ticket.setEffective("1");
			}
			ticket = (Ticket) service.saveOrUpdate(ticket);
			response(new JSONObject().accumulate("success", true).accumulate("effective", ticket.getEffective()));
		} catch (Exception e) {
			e.printStackTrace();
			response(e);
		}
	}

	/**
	 * 生成俱乐部小程序订单
	 */
	public void createClubMPOrder() {
		try {
			JSONObject json = JSONObject.fromObject(URLDecoder.decode(request.getParameter("json"), "UTF-8"));
			Member member = (Member) service.load(Member.class, json.getLong("memberId"));
			Order order = new Order();
			MemberTicket ticket = new MemberTicket();
			double productPrice = 0;
			double ticketMoney = 0;
			int days = 0;
			Date strengthDate = EasyUtils.formatStringToDate(json.getString("strengthDate"));
			if (Constants.PRODUCT_TYPE_PRODUCT.equals(json.getString("productType"))) {
				order = new ProductOrder();
				Product product = (Product) service.load(Product.class, json.getLong("productId"));
				((ProductOrder) order).setProduct(product);
				order.setPayNo(service.getKeyNo("", "CARDCOL_ORDER_NO", 28));
				order.setNo(service.getKeyNo("", "CARDCOL_ORDER_NO", 14));
				productPrice = product.getCost();
				days = product.getWellNum();
			} else if (Constants.PRODUCT_TYPE_ACTIVE.equals(json.getString("productType"))) {
				order = new ActiveOrder();
				Active a = (Active) service.load(Active.class, json.getLong("productId"));
				((ActiveOrder) order).setActive(a);
				if (json.containsKey("weight")) {
					((ActiveOrder) order).setValue(json.getDouble("weight"));
				}
				order.setPayNo(service.getKeyNo("", "TB_ACTIVE_ORDER", 28));
				order.setNo(service.getKeyNo("", "TB_ACTIVE_ORDER", 14));
				productPrice = a.getAmerceMoney();
				days = a.getDays();
			} else if (Constants.PRODUCT_TYPE_COURSE.equals(json.getString("productType"))) {
				order = new CourseOrder();
				Course c = (Course) service.load(Course.class, json.getLong("productId"));
				((CourseOrder) order).setCourse(c);
				order.setPayNo(service.getKeyNo("", "TB_CourseRelease_ORDER", 28));
				order.setNo(service.getKeyNo("", "TB_CourseRelease_ORDER", 14));
				productPrice = json.getDouble("price");
				days = 0;
			} else if (Constants.PRODUCT_TYPE_GOODS.equals(json.getString("productType"))) {
				order = new GoodsOrder();
				Goods g = (Goods) service.load(Goods.class, json.getLong("productId"));
				((GoodsOrder) order).setGoods(g);
				order.setPayNo(service.getKeyNo("", "TB_GOODS_ORDER", 28));
				order.setNo(service.getKeyNo("", "TB_GOODS_ORDER", 14));
				productPrice = g.getPrice();
				// 获取时间
				String circle = g.getPlancircle();
				String dayStr = circle.substring(0, 1);
				String Unit = circle.substring(1, 2);
				if ("月".equals(Unit)) {
					days = Integer.valueOf(dayStr) * 30;
				}

				if ("周".equals(Unit)) {
					days = Integer.valueOf(dayStr) * 7;
				}

				if ("天".equals(Unit) || "日".equals(Unit)) {
					days = Integer.valueOf(dayStr) * 1;
				}
			} else if (Constants.PRODUCT_TYPE_PRICECUTDOWN_PRODUCT.equals(json.getString("productType"))) {
				order = new ProductOrder();
				Product product = (Product) service.load(Product.class, json.getLong("productId"));
				((ProductOrder) order).setProduct(product);
				order.setPayNo("696969" + service.getKeyNo("", "CARDCOL_ORDER_NO", 18) + json.getString("priceId"));
				order.setNo(service.getKeyNo("", "CARDCOL_ORDER_NO", 14));
				productPrice = json.getDouble("price");
				days = product.getWellNum();
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
			order.setSurplusMoney(productPrice);
			order.setCount(1);
			order.setContractMoney(productPrice);
			order.setPayServiceCostE(0.0);
			order.setPayServiceCostM(0.0);
			order.setPayType("1");
			order.setShipTimeType("1");
			order.setShipType("1");
			order.setOrderStartTime(strengthDate);
			order.setOrderBalanceTime(strengthDate);
			order.setOrderEndTime(EasyUtils.addDate(strengthDate, days));
			order.setOrderDate(new Date());
			order.setStatus('0');
			// E卡通订单
			order.setOrigin('C');
			if (json.containsKey("shareMember") && StringUtils.isNotEmpty(json.getString("shareMember"))) {
				// 如果是分享订单在优惠券的基础上再打九折
				double money = Double.valueOf(EasyUtils
						.decimalFormat((productPrice - ticketMoney) * Constants.ORDER_DISCOUNT_NINETY_PERCENT));
				order.setOrderMoney(money < 0 ? 0 : money);
				// 更改标识为分享订单
				order.setOrigin('S');
				order.setContractMoney(json.getDouble("shareMember"));
			} else {
				double money = Double.valueOf(EasyUtils.decimalFormat((productPrice - ticketMoney)));
				order.setOrderMoney(money < 0 ? 0 : money);
			}
			order = (Order) service.saveOrUpdate(order);
			// 绑定用户手机号
			member.setMobilephone(json.getString("phoneNumber"));
			member.setMobileValid("1");
			service.saveOrUpdate(member);
			// 支付签名
			SDK_WX sdk = new SDK_WX(request, "俱乐部小程序");
			String result = sdk.paySign(json.getString("openId"), order);
			response(JSONObject.fromObject(result).accumulate("orderId", order.getId()));
		} catch (Exception e) {
			e.printStackTrace();
			response(e);
		}
	}

	/**
	 * 团体课表
	 */
	public void findCourse() {
		JSONObject ret = new JSONObject();
		try {

			// 处理请求参数
			JSONObject obj = JSONObject.fromObject(URLDecoder.decode(request.getParameter("json"), "UTF-8"));

			String date = obj.getString("date");
			String[] datex = date.split(",");

			String nowHHmm = sdfHHmm.format(new Date());

			String sqlx = " select * from ( "
					+ "      SELECT a.id, a.startTime, a.endTime, b.name AS courseName,b.image,c.name AS clubName, a.planDate, a.count as total, "
					+ "					        mm.name as coachName, a.hour_price AS normol_price, a.member_price AS vip_price, c.id as clubId, "
					+ "					        (count(ca.id) + count(cord.id)) as joinNum, c.id as cid, c.role as crole   "
					+ "					 FROM tb_course a LEFT JOIN tb_course_info b ON a.courseId = b.id "
					+ "					                  LEFT JOIN tb_member c ON a.member = c.id "
					+ "					                  LEFT JOIN tb_member mm ON a.coach = mm.id "
					+ "					                  LEFT JOIN tb_course_apply ca ON ca.course = a.id AND ca.status = 2 "
					+ "					                  LEFT JOIN tb_member cus ON cus.id = ca.member "
					+ "					                  LEFT JOIN tb_courserelease_order cord ON cord.course = a.id "
					+ "					                  AND cord.status != 0  "
					+ "					 WHERE  a.planDate in(?) AND a.startTime >= ?   GROUP BY a.id "
					+ "			UNION ALL "
					+ "			SELECT a.id, a.startTime, a.endTime, b.name AS courseName,b.image,c.name AS clubName, a.planDate, a.count as total, "
					+ "												mm.name as coachName, a.hour_price AS normol_price, a.member_price AS vip_price, c.id as clubId, "
					+ "												(count(ca.id) + count(cord.id)) as joinNum, c.id as cid, c.role as crole   "
					+ "								 FROM tb_course a LEFT JOIN tb_course_info b ON a.courseId = b.id "
					+ "																	LEFT JOIN tb_member c ON a.member = c.id "
					+ "																	LEFT JOIN tb_member mm ON a.coach = mm.id "
					+ "																	LEFT JOIN tb_course_apply ca ON ca.course = a.id AND ca.status = 2 "
					+ "																	LEFT JOIN tb_member cus ON cus.id = ca.member "
					+ "																	LEFT JOIN tb_courserelease_order cord ON cord.course = a.id "
					+ "																	AND cord.status != 0 "
					+ "								 WHERE  a.planDate in(?,?,?,?,?,?)  GROUP BY a.id "
					+ " ) as temx where temx.cid = ? and temx.crole = 'E'  ORDER BY temx.planDate desc, temx.startTime asc ";
			Object[] objx = { datex[0], nowHHmm, datex[1], datex[2], datex[3], datex[4], datex[5], datex[6],
					Constants.CLUB_ID, };
			List<Map<String, Object>> courseList = DataBaseConnection.getList(sqlx, objx);

			courseList = EasyUtils.dateANDdecimalFormat(courseList, "yyyy-MM-dd");

			JSONArray course = JSONArray.fromObject(courseList);
			ret.accumulate("success", true).accumulate("course", course);

		} catch (Exception e) {
			ret.accumulate("success", false).accumulate("message", e);
		}
		response(ret);

	}

	/**
	 * 根据id查询需要预约的课程
	 */
	public void findCourseById() {
		JSONObject ret = new JSONObject();
		try {
			// 处理请求参数
			JSONObject obj = JSONObject.fromObject(URLDecoder.decode(request.getParameter("json"), "UTF-8"));
			String sqlx = " select * from ( "
					+ " SELECT a.id, a.startTime, a.endTime, b.name AS courseName,c.name AS clubName, a.planDate, a.count as total, "
					+ "        IFNULL(a.joinNum, 0) as joinNum, mm.name as coachName, mm.image as image, "
					+ "        a.hour_price AS normol_price, a.member_price AS vip_price, c.id as clubId, a.memo "
					+ " FROM tb_course a LEFT JOIN tb_course_info b ON a.courseId = b.id LEFT JOIN tb_member c ON a.member = c.id LEFT JOIN tb_member mm ON a.coach = mm.id  "
					+ " WHERE  a.id = ? " + " ) temp ";
			Object[] objx = { obj.getLong("courseId") };
			Map<String, Object> course = DataBaseConnection.getOne(sqlx, objx);

			// 查询当前用户是不是所查询俱乐部的会员
			String sqld = " select * from ( "
					+ "        select mf.friend as club from tb_member_friend mf LEFT JOIN tb_member m ON mf.friend = m.id where mf.member = ?  and m.role = 'E' "
					+ " ) temp ";
			Object[] objd = { obj.getLong("memberId") };
			List<Map<String, Object>> clubList = DataBaseConnection.getList(sqld, objd);
			Integer club = Integer.valueOf(String.valueOf(course.get("clubId")));
			course.put("isVip", false);
			for (Map<String, Object> map : clubList) {
				Integer clubId = Integer.valueOf(String.valueOf(map.get("club")));
				if (club.equals(clubId)) {
					course.put("isVip", true);
				}

			}

			course = EasyUtils.dateFormat(course, "yyyy-MM-dd");
			ret.accumulate("success", true).accumulate("message", "OK").accumulate("course", course);
			;

		} catch (Exception e) {
			ret.accumulate("success", false).accumulate("message", e.toString());
			e.printStackTrace();
		}

		response(ret);
	}

	/**
	 * 预约课程
	 */
	@SuppressWarnings("unused")
	public void appointment() {
		JSONObject ret = new JSONObject();
		try {

			// 处理请求参数
			JSONObject obj = JSONObject.fromObject(URLDecoder.decode(request.getParameter("json"), "UTF-8"));

			// 用户登录
			Member mu = (Member) service.load(Member.class, Long.valueOf(obj.getLong("memberId")));
			// 预约类型
			int type = obj.getInt("type");

			// 预约的课程id
			Long courseId = obj.getLong("courseId");

			// 会员在教练课表中进行预约
			if (type == 1) {
				final Long userId = mu.getId();
				Course course = new Course();
				course.setCourseInfo(new CourseInfo(obj.getLong("course")));
				course.setPlanDate(obj.getString("planDate"));
				course.setStartTime(obj.getString("startTime"));
				course.setEndTime(obj.getString("endTime"));
				course.setPlace(obj.getString("place"));
				course.setColor("#ffcc00");
				course.setMember(new Member(userId));
				course.setCoach(new Member(obj.getLong("member")));
				// 2014/12/26 增加备注字段
				course.setMemo(obj.getString("memo"));
				course = service.saveAppointment(course);
				final Member m = (Member) service.load(Member.class, obj.getLong("member"));
				new MessageThread(m.getUserId(), m.getChannelId(), m.getTermType(),
						"会员“" + mu.getName() + "”预约了您" + course.getPlanDate() + "日" + course.getStartTime() + "分“的课程!");
				ret.accumulate("success", true).accumulate("key", course.getId());
			} else {
				// 会员预约俱乐部的课程
				if (courseId == null) {
					throw new LogicException("未传入课程ID号");
				}
				final Course course = (Course) service.load(Course.class, courseId);
				if (course.getJoinNum() == null)
					course.setJoinNum(0);
				if (course.getJoinNum() == course.getCount()) {
					ret.accumulate("success", false).accumulate("message", "当前课程已经满员，不能进行预约，请确认！");
					response(ret);
					return;
				}
				boolean isExist = false;
				final Long requestMember = mu.getId();
				for (Apply apply : course.getApplys()) {
					final String status = apply.getStatus();
					if (apply.getMember().getId().equals(requestMember) && (status.equals("1") || status.equals("2"))) {
						isExist = true;
						break;
					}
				}
				if (isExist) {
					ret.accumulate("success", false).accumulate("message", "您已经预约过此课程，不再再次进行预约！");
					response(ret);
					return;
				}
				// 判断该会员预约的该课程时间段的是否还有其它课程
				final List<?> list = service.findObjectBySql(
						"from Course c where c.member.id = ? and c.planDate = ? and ((c.startTime = ? and c.endTime = ?) or (? between c.startTime and c.endTime) or (? between c.startTime and c.endTime))",
						requestMember, course.getPlanDate(), course.getStartTime(), course.getEndTime(),
						course.getStartTime(), course.getEndTime());
				if (list.size() > 0) {
					ret.accumulate("success", false).accumulate("message", "您当前时间有其它的课程，请确认！");
				} else {
					final Integer joinNum = service.saveRequest(course, new Member(requestMember));
					ret.accumulate("success", true).accumulate("message", "OK").accumulate("value", joinNum);
				}
			}
		} catch (Exception e) {
			ret.accumulate("success", false).accumulate("message", e.toString());
		}

		response(ret);
	}

	/**
	 * 撤消预约
	 */
	public void undoAppointment() {
		JSONObject ret = new JSONObject();
		try {
			// 处理请求参数
			JSONObject obj = JSONObject.fromObject(URLDecoder.decode(request.getParameter("json"), "UTF-8"));
			// 用户登录
			Member member = (Member) service.load(Member.class, obj.getLong("memberId"));

			Message msg = new Message();
			Course c = (Course) service.load(Course.class, obj.getLong("courseId"));

			msg.setContent("会员【" + member.getName() + "】取消了您的" + c.getPlanDate() + "日" + c.getStartTime() + "的名为【"
					+ c.getCourseInfo().getName() + "】的课程的预约！");
			msg.setMemberFrom(member);
			msg.setIsRead("0");
			msg.setSendTime(new Date());
			msg.setStatus("2");
			msg.setType("3");

			// 撤消团体课预约
			final List<?> list = service.findObjectBySql(
					"from Apply a where " + " a.member.id = ? and a.course.id = ? ", member.getId(),
					obj.getLong("courseId"));
			if (list.size() > 0) {
				final Apply app = (Apply) list.get(0);
				c.setJoinNum(c.getJoinNum() - 1);
				service.saveOrUpdate(c);
				service.delete(app);
			}
			msg.setMemberTo(c.getMember());

			service.saveOrUpdate(msg);
			ret.accumulate("success", true).accumulate("message", "OK");
		} catch (Exception e) {
			ret.accumulate("success", false).accumulate("message", e.toString());
			e.printStackTrace();
		}

		response(ret);
	}

	/**
	 * 查询用户的订单
	 */
	@SuppressWarnings("unchecked")
	public void findOrders() {
		JSONObject ret = new JSONObject();
		try {
			// 处理请求参数
			JSONObject obj = JSONObject.fromObject(URLDecoder.decode(request.getParameter("json"), "UTF-8"));

			// 用户登录
			Member member = (Member) service.load(Member.class, Long.valueOf(obj.getLong("memberId")));

			Long memberId = member.getId();

			// 未付款订单
			String status = "0";
			pageInfo = service.queryOrderInStatus(pageInfo, memberId, status);
			List<Map<String, Object>> orderList0 = pageInfo.getItems();

			JSONArray jsonArray0 = new JSONArray();
			JSONObject objx0 = null;
			if (orderList0.size() > 0) {
				for (Map<String, Object> map : orderList0) {
					objx0 = new JSONObject();
					objx0.accumulate("id", map.get("id")).accumulate("prodName", map.get("name"))
							.accumulate("orderNo", map.get("no")).accumulate("orderDate", map.get("orderDate"))
							.accumulate("orderMoney", map.get("orderMoney")).accumulate("image", map.get("image"));
					jsonArray0.add(objx0);
				}
			}

			// 有效订单
			status = "1,2";
			pageInfo = service.queryOrderInStatus(pageInfo, memberId, status);
			List<Map<String, Object>> orderList1 = pageInfo.getItems();

			JSONArray jsonArray1 = new JSONArray();
			JSONObject objx1 = null;
			if (orderList1.size() > 0) {
				for (Map<String, Object> map : orderList1) {
					objx1 = new JSONObject();
					objx1.accumulate("id", map.get("id")).accumulate("prodName", map.get("name"))
							.accumulate("orderNo", map.get("no")).accumulate("orderDate", map.get("orderDate"))
							.accumulate("orderMoney", map.get("orderMoney")).accumulate("image", map.get("image"));
					jsonArray1.add(objx1);
				}
			}

			// 已完成订单
			status = "3";
			pageInfo = service.queryOrderInStatus(pageInfo, memberId, status);
			List<Map<String, Object>> orderList2 = pageInfo.getItems();

			JSONArray jsonArray2 = new JSONArray();
			JSONObject objx2 = null;
			if (orderList2.size() > 0) {
				for (Map<String, Object> map : orderList2) {
					objx2 = new JSONObject();
					objx2.accumulate("id", map.get("id")).accumulate("prodName", map.get("name"))
							.accumulate("orderNo", map.get("no")).accumulate("orderDate", map.get("orderDate"))
							.accumulate("orderMoney", map.get("orderMoney")).accumulate("image", map.get("image"));
					jsonArray2.add(objx2);
				}
			}

			ret.accumulate("success", true).accumulate("a", jsonArray0).accumulate("b", jsonArray1).accumulate("c",
					jsonArray2);

		} catch (Exception e) {
			ret.accumulate("success", false).accumulate("message", e.toString());
			e.printStackTrace();
		}
		response(ret);
	}

	/**
	 * 查询用户个人信息
	 */

	public void findMe() {
		JSONObject ret = new JSONObject();
		try {
			// 处理请求参数
			JSONObject obj = JSONObject.fromObject(URLDecoder.decode(request.getParameter("json"), "UTF-8"));

			// 当前用户登录
			Member member = (Member) service.load(Member.class, Long.valueOf(obj.getLong("memberId")));

			// 查询当前用户健身次数
			String sqlx = " select * from ( " + " select count(id) as count from tb_sign_in where memberSign = ? "
					+ " ) temp";
			Object[] objx = { member.getId() };
			Map<String, Object> countMap = DataBaseConnection.getOne(sqlx, objx);
			JSONObject memberData = new JSONObject();
			memberData.accumulate("nickName", member.getName()).accumulate("image", member.getImage())
					.accumulate("count", countMap.get("count")).accumulate("city", member.getCity())
					.accumulate("mobilephone", member.getMobilephone())
					.accumulate("mobileValid", member.getMobileValid()).accumulate("county", member.getCounty());
			ret.accumulate("success", true).accumulate("memberData", memberData);

		} catch (Exception e) {
			ret.accumulate("success", false).accumulate("message", e.toString());
			e.printStackTrace();
		}

		// 返回数据
		response(ret);
	}

	/**
	 * 我的钱包，收入、支出、提现记录、账户余额
	 */
	@SuppressWarnings("unchecked")
	public void myWallet() {
		JSONObject ret = new JSONObject();
		try {
			// 处理请求参数
			JSONObject obj = JSONObject.fromObject(URLDecoder.decode(request.getParameter("json"), "UTF-8"));

			Member member = (Member) service.load(Member.class, Long.valueOf(obj.getLong("memberId")));
			long memberId = member.getId();

			// 收入
			pageInfo = service.income(pageInfo, memberId);

			List<Map<String, Object>> incomeList = pageInfo.getItems();
			final JSONArray incomeDetail = new JSONArray();

			if (incomeList.size() > 0) {
				JSONObject objx = null;
				for (Map<String, Object> map : incomeList) {
					objx = new JSONObject();
					objx.accumulate("id", map.get("id")).accumulate("prodName", map.get("prodName"))
							.accumulate("balanceTime", map.get("balanceTime"))
							.accumulate("fromName", map.get("fromName"))
							.accumulate("balanceMoney", map.get("balanceMoney"));
					incomeDetail.add(objx);
				}
			}

			// 支出
			final List<Object> parms = new ArrayList<Object>();
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT * FROM (").append(Order.getOrderSqls()).append(") t where 1 = 1");
			sql.append(" and (fromId = ?) and STATUS !=0 ");
			parms.add(memberId);
			sql.append("order by orderDate desc");
			System.out.println(sql.toString());
			pageInfo = service.findPageBySql(sql.toString(), pageInfo, parms.toArray());

			List<Map<String, Object>> outList = pageInfo.getItems();
			final JSONArray outDetail = new JSONArray();
			if (outList.size() > 0) {
				JSONObject objx = null;
				for (Map<String, Object> map : outList) {
					objx = new JSONObject();
					objx.accumulate("id", map.get("id")).accumulate("payTime", map.get("payTime"))
							.accumulate("toName", map.get("toName")).accumulate("name", map.get("NAME"))
							.accumulate("orderMoney", map.get("orderMoney")).accumulate("status", map.get("STATUS"));
					outDetail.add(objx);
				}
			}

			// 提现
			// 查询余额
			Double pickMoneyCount = service.findPickMoneyCountByMember((Member) member);
			DecimalFormat df = new DecimalFormat("0.00");
			// 查询提现记录
			pageInfo = service.queryPickDetail(pageInfo, memberId + "");
			List<Map<String, Object>> pickList = pageInfo.getItems();
			final JSONArray pickDetail = new JSONArray();
			if (pickList.size() > 0) {
				JSONObject objx = null;
				for (Map<String, Object> map : pickList) {
					if ("支付宝".equals(map.get("bankName"))) {
						map.put("image", "img/zhifubao.png");
					}
					if ("微信".equals(map.get("bankName"))) {
						map.put("image", "img/weixin.png");
					}
					if ("银联".equals(map.get("bankName"))) {
						map.put("image", "img/shouye/taoke/UnionPay@2x.png");
					}
					objx = new JSONObject();
					String etime = (String) map.get("evalTime");
					etime = etime.substring(0, 10);
					objx.accumulate("bankName", map.get("bankName")).accumulate("account", map.get("account"))
							.accumulate("evalTime", etime).accumulate("cashMoney", map.get("pickMoney"))
							.accumulate("nick", member.getName()).accumulate("imgsrc", map.get("image"))
							.accumulate("status", map.get("status"));

					pickDetail.add(objx);
				}
			}
			ret.accumulate("success", true).accumulate("incomeList", incomeDetail).accumulate("outList", outDetail)
					.accumulate("cashList", pickDetail).accumulate("balance", df.format(pickMoneyCount))
					.accumulate("pageInfo", getJsonForPageInfo());

		} catch (Exception e) {
			ret.accumulate("success", false).accumulate("message", e.toString());
			e.printStackTrace();
		}

		response(ret);

	}

	/**
	 * 查询提现账户
	 */
	public void findCashAccount() {
		JSONObject ret = new JSONObject();
		try {
			// 处理请求参数
			JSONObject obj = JSONObject.fromObject(URLDecoder.decode(request.getParameter("json"), "UTF-8"));

			// 用户登录
			Member member = (Member) service.load(Member.class, obj.getLong("memberId"));

			// 查询余额
			Double pickMoneyCount = service.findPickMoneyCountByMember(member);
			DecimalFormat df = new DecimalFormat("0.00");

			// 查询提现账户
			String sqlx = " select * from tb_pick_account where member = ? order by id desc limit 1 ";
			Object[] objx = { member.getId() };

			Map<String, Object> accountMap = DataBaseConnection.getOne(sqlx, objx);

			ret.accumulate("success", true).accumulate("message", "OK").accumulate("account", accountMap)
					.accumulate("mobilephone", member.getMobilephone())
					.accumulate("balance", df.format(pickMoneyCount));

		} catch (Exception e) {
			ret.accumulate("success", false).accumulate("message", e.toString());
			e.printStackTrace();
		}

		response(ret);

	}

	/**
	 * 获取手机验证码
	 */
	public void getMobileCode() {
		JSONObject ret = new JSONObject();
		try {
			// 处理请求参数
			JSONObject obj = JSONObject.fromObject(URLDecoder.decode(request.getParameter("json"), "UTF-8"));
			sendSmsValidate(obj.getString("mobilephone"), obj.getString("type"));
			ret.accumulate("success", true).accumulate("message", "ok");

		} catch (Exception e) {
			ret.accumulate("success", false).accumulate("message", e.toString());
			e.printStackTrace();
		}
		response(ret);
	}

	/**
	 * 申请提现
	 */
	public void savePickDetail() {
		JSONObject ret = new JSONObject();
		try {
			// 处理请求参数
			JSONObject obj = JSONObject.fromObject(URLDecoder.decode(request.getParameter("json"), "UTF-8"));

			// 获取基本参数
			Double pickMoney = obj.getDouble("pickMoney");
			String mobile = obj.getString("mobilephone");
			String code = obj.getString("code");

			// 获取提现账户
			Map<String, Object> map = service.queryPickAccount(obj.getString("memberId"));
			PickAccount pickAccount = (PickAccount) service.load(PickAccount.class, (Long) map.get("id"));

			// 用户登录
			Member member = (Member) service.load(Member.class, obj.getLong("memberId"));

			PickDetail pickDetail = new PickDetail();

			if (isRightful(mobile, code)) {
				pickDetail.setNo(service.getKeyNo("", "TB_PICK_DETAIL", 14));
				pickDetail.setType("1");
				pickDetail.setFlowType("1");
				pickDetail.setMember(member);
				pickDetail.setPickDate(new Date());
				pickDetail.setStatus("1");
				pickDetail.setPickMoney(pickMoney);
				pickDetail.setPickAccount(pickAccount);

				// 持久化到数据库
				service.saveOrUpdate(pickDetail);
				ret.accumulate("success", true).accumulate("message", "ok");

			} else {

				ret.accumulate("success", false).accumulate("message", "您输入的验证码不正确，请重新输入");
			}

		} catch (Exception e) {
			ret.accumulate("success", false).accumulate("message", e.toString());
			e.printStackTrace();
		}

		response(ret);
	}

	/**
	 * 添加提现账户
	 */
	public void savePickAccount() {
		JSONObject ret = new JSONObject();
		try {
			PickAccount pickAccount = new PickAccount();

			// 处理请求参数
			JSONObject obj = JSONObject.fromObject(URLDecoder.decode(request.getParameter("json"), "UTF-8"));
			Member member = (Member) service.load(Member.class, obj.getLong("memberId"));
			String mobile = obj.getString("mobile");
			String code = obj.getString("code");
			Integer accountType = obj.getInt("accountType");
			String account = obj.getString("account");

			// 验证码有误
			if (!isRightful(mobile, code)) {
				ret.accumulate("success", false).accumulate("message", "验证码输入不正确，请重新输入！");
				response(ret);
				return;
			}

			pickAccount.setMember(member);
			pickAccount.setAccount(account);
			if (accountType == 0) {
				// 支付宝
				pickAccount.setBankName("支付宝");
			}
			if (accountType == 1) {
				// 银联账号
				pickAccount.setBankName(obj.getString("bankName"));
				pickAccount.setName(obj.getString("name"));
			}

			pickAccount = (PickAccount) service.saveOrUpdate(pickAccount);

			ret.accumulate("success", true).accumulate("message", "OK").accumulate("id", pickAccount.getId())
					.accumulate("bankName", pickAccount.getBankName())
					.accumulate("member", pickAccount.getMember().getId())
					.accumulate("account", pickAccount.getAccount()).accumulate("name", pickAccount.getName());

		} catch (Exception e) {
			ret.accumulate("success", false).accumulate("message", e.toString());
			e.printStackTrace();
		}

		response(ret);

	}

	/**
	 * 查询用户的预约数据
	 */
	public void findAppointmentByMember() {
		JSONObject ret = new JSONObject();
		try {
			// 处理请求参数
			JSONObject obj = JSONObject.fromObject(URLDecoder.decode(request.getParameter("json"), "UTF-8"));

			// 预约审核状态
			Integer status = 0;

			String sqlx = " select * from ( " + " select "
					+ "      coi.image as image, coi.name as courseName, mm.name as coachName, ca.applyDate, "
					+ "      c.startTime, c.endTime, c.id as courseId, ca.status, m.id as member  " + " from "
					+ "		 tb_member m LEFT JOIN tb_course_apply ca ON m.id = ca.member "
					+ "                  LEFT JOIN tb_course c ON ca.course = c.id "
					+ "					 LEFT JOIN tb_course_info coi ON c.courseId = coi.id "
					+ "					 LEFT JOIN tb_member mm ON c.coach = mm.id  ) temp "
					+ "      where temp.member = ? and temp.status = ? ";
			// 待审核中的预约
			status = Integer.valueOf(STATUS_REQUEST_COURSE_WAIT);
			Object[] obj1 = { obj.getInt("memberId"), status };
			List<Map<String, Object>> appointmentListApplying = DataBaseConnection.getList(sqlx, obj1);
			appointmentListApplying = EasyUtils.dateFormat(appointmentListApplying, "yyyy-MM-dd");

			// 审核通过的预约
			status = Integer.valueOf(STATUS_REQUEST_COURSE_SUCCESS);
			Object[] obj2 = { obj.getInt("memberId"), status };
			List<Map<String, Object>> appointmentListApplied = DataBaseConnection.getList(sqlx, obj2);
			appointmentListApplied = EasyUtils.dateFormat(appointmentListApplied, "yyyy-MM-dd");
			ret.accumulate("success", true).accumulate("message", "OK")
					.accumulate("appointmentListApplying", appointmentListApplying)
					.accumulate("appointmentListApplied", appointmentListApplied);
			;

		} catch (Exception e) {
			ret.accumulate("success", false).accumulate("message", e.toString());
			e.printStackTrace();
		}
		// 返回数据
		response(ret);
	}

	/**
	 * 获取王严专家系统的商品参数
	 */
	public void findExpertProduct() {
		JSONObject ret = new JSONObject();
		try {
			// 处理请求参数
			JSONObject param = JSONObject.fromObject(URLDecoder.decode(request.getParameter("json"), "UTF-8"));

			String sqlx = " select * from tb_goods where id = ? ";
			Object[] objx = { param.getLong("goodsId") };
			Map<String, Object> goodsMap = DataBaseConnection.getOne(sqlx, objx);
			goodsMap = EasyUtils.dateFormat(goodsMap, "yyyy-MM-dd HH:mm:ss");

			ret.accumulate("success", true).accumulate("message", "OK").accumulate("goods", goodsMap);

		} catch (Exception e) {
			ret.accumulate("success", false).accumulate("message", e.toString());
		}
		response(ret);
	}

	/**
	 * 获取俱乐部的wifi信息
	 */
	public void getClubWifi() {
		JSONObject ret = new JSONObject();
		try {
			String sqlx = " select id, club, ssid, bssid, password from tb_club_wifi where club = ? ";
			Object[] objx = { Constants.CLUB_ID };

			Map<String, Object> clubWifi = DataBaseConnection.getOne(sqlx, objx);

			JSONObject wifi = JSONObject.fromObject(clubWifi);
			ret.accumulate("success", true).accumulate("message", "OK").accumulate("clubWifi", wifi);

		} catch (Exception e) {
			ret.accumulate("success", false).accumulate("message", e.toString());
			e.printStackTrace();
		}
		response(ret);
	}

	/**
	 * 发起砍价活动
	 */
	public void releasePriceCutdown() {
		JSONObject ret = new JSONObject();
		try {
			// 处理请求参数
			JSONObject param = JSONObject.fromObject(URLDecoder.decode(request.getParameter("jsons"), "UTF-8"));

			// 用户登录
			Member member = toMember();

			// 砍价商品(俱乐部发布的健身卡)
			Product product = (Product) service.load(Product.class, param.getLong("product"));

			// 获取source, 初始化priceCutdown
			priceCutDown pcd = new priceCutDown();
			String source = param.getString("source");
			if ("edit".equals(source)) {
				pcd = (priceCutDown) service.load(priceCutDown.class, param.getLong("id"));
			}

			String poster = "";
			if (image == null) {
				if ("edit".equals(source)) {
					poster = pcd.getPoster();
				}
			}

			// 上传砍价活动海报
			if (image != null) {
				poster = saveFile("picture", image, imageFileName, null);
			}

			pcd.setProduct(product.getId());
			pcd.setMoney(product.getCost());
			pcd.setLowPrice(param.getDouble("lowPrice"));
			pcd.setStatus("0");
			// 砍价有效期
			pcd.setPeriod(param.get("period") == null ? 3 : param.getInt("period"));
			pcd.setTime(new Date());
			pcd.setPoster(poster);
			pcd.setCreator(member.getId());
			pcd.setTotalTimes(param.getInt("totalTimes"));

			// 砍价活动说明
			pcd.setRemark(remark);
			pcd.setActiveName(param.getString("activeName"));

			// 持久化数据
			pcd = (priceCutDown) service.saveOrUpdate(pcd);

			ret.accumulate("success", true).accumulate("message", "OK").accumulate("id", pcd.getId());
		} catch (Exception e) {
			e.printStackTrace();
		}

		this.getPriceActiveList();
	}

	/**
	 * 查询砍价活动
	 */
	public void getPriceActive() {
		JSONObject ret = new JSONObject();
		try {
			// 处理请求参数
			JSONObject param = JSONObject.fromObject(URLDecoder.decode(request.getParameter("json"), "UTF-8"));
			String sqlx = " select dx.*, (select count(id) from tb_price_cutdown where priceActive = dx.id) as count from ( "
					+ "			select 				" + "								pc.* " + "			from  "
					+ "											tb_price_cutdown pc   "
					+ "			where 				"
					+ "											pc.creator = ? and pc.status = 0  " + " ) dx ";
			Object[] objx = { param.getLong("clubId") };
			List<Map<String, Object>> priceActiveList = DataBaseConnection.getList(sqlx, objx);
			priceActiveList = EasyUtils.dateFormat(priceActiveList, "yyyy-MM-dd HH:mm:ss");

			ret.accumulate("success", true).accumulate("message", "OK").accumulate("priceActiveList",
					JSONArray.fromObject(priceActiveList));
		} catch (Exception e) {
			ret.accumulate("success", false).accumulate("message", e.toString());
			e.printStackTrace();
		}
		// 返回结果
		response(ret);
	}

	/**
	 * 获取砍价页面基本信息
	 */
	public void getCutdownInfo() {
		JSONObject ret = new JSONObject();
		try {
			// 处理请求参数
			JSONObject param = JSONObject.fromObject(URLDecoder.decode(request.getParameter("json"), "UTF-8"));

			priceCutDown parent = (priceCutDown) service.load(priceCutDown.class, param.getLong("id"));

			// activePrice
			priceCutDown priceActive = (priceCutDown) service.load(priceCutDown.class, param.getLong("priceActive"));

			// product信息
			Product product = (Product) service.load(Product.class, priceActive.getProduct());

			// 计算过期时间
			Date time = parent.getTime();
			Date expiration = EasyUtils.addDate(time, priceActive.getPeriod());

			// 该活动已过期
			if (new Date().getTime() > expiration.getTime() || "1".equals(parent.getStatus())) {
				String sqlx = " update tb_price_cutdown set status = 1 where id = ? ";
				String sqly = " update tb_price_cutdown set status = 1 where parent = ? ";
				Object[] objx = { parent.getId() };
				DataBaseConnection.updateData(sqlx, objx);
				DataBaseConnection.updateData(sqly, objx);
				ret.accumulate("hasExpiration", true).accumulate("message", "该活动已过期或已被购买，请发起新的砍价活动！");
			}

			// 查询当前价
			String sqlx = " select "
					+ "			IFNULL(pc.cutMoney, 0) as selfCut, sum(IFNULL(pcp.cutMoney, 0)) as otherCut, "
					+ "			( IFNULL(pc.cutMoney, 0) + sum(IFNULL(pcp.cutMoney, 0)) ) as totalCut, "
					+ "			(pc.money - ( IFNULL(pc.cutMoney, 0) + sum(IFNULL(pcp.cutMoney, 0))  ) ) currentMoney, "
					+ "         m.name, p.image1 " + " from "
					+ "			tb_price_cutdown pc LEFT JOIN tb_price_cutdown pcp ON pc.id = pcp.parent "
					+ "                             LEFT JOIN tb_member m ON pc.Member = m.id  "
					+ "                             LEFT JOIN tb_product p ON pc.product = p.id  " + " WHERE "
					+ " 		pc.id = ?  ";
			Object[] objx = { parent.getId() };
			Map<String, Object> currentMap = DataBaseConnection.getOne(sqlx, objx);

			// 查询砍价活动排名
			JSONArray sortList = JSONArray.fromObject(getCutRanking(priceActive.getId()));
			ret.accumulate("activePoster", priceActive.getPoster()).accumulate("prodName", product.getName())
					.accumulate("prodId", product.getId()).accumulate("expiration", sdf.format(expiration))
					.accumulate("money", priceActive.getMoney()).accumulate("lowPrice", priceActive.getLowPrice())
					.accumulate("currentMoney", currentMap.get("currentMoney"))
					.accumulate("remark", priceActive.getRemark()).accumulate("id", param.getLong("id"))
					.accumulate("priceActive", param.getLong("priceActive")).accumulate("sortList", sortList)
					.accumulate("name", currentMap.get("name")).accumulate("activeName", priceActive.getActiveName())
					.accumulate("prodImage", currentMap.get("image1"));
		} catch (Exception e) {
			ret.accumulate("success", false).accumulate("message", e.toString());
			e.printStackTrace();
		}

		// 返回数据
		response(ret);
	}

	/**
	 * 砍价逻辑，有次数限制
	 */
	public void cutdownPrice() {
		JSONObject ret = new JSONObject();
		try {
			// 处理请求参数
			JSONObject param = JSONObject.fromObject(URLDecoder.decode(request.getParameter("json"), "UTF-8"));

			// 用户登录
			Member member = (Member) service.load(Member.class, param.getLong("memberId"));

			// 所属砍价活动
			priceCutDown priceActive = (priceCutDown) service.load(priceCutDown.class, param.getLong("priceActive"));

			// 可砍价格
			Double canMoney = priceActive.getMoney() - priceActive.getLowPrice();

			// 是否传递id过来
			String id = param.containsKey("id") ? param.getString("id") : "";

			ret.accumulate("priceActive", priceActive.getId());

			// id, 为空自己发起砍价活动
			if (StringUtils.isEmpty(id)) {
				ret.accumulate("cutActive", true);

				String sqlx = "select * from tb_price_cutdown where member = ? and status in(0,2)  AND ISNULL(parent) and priceActive = ? ";
				Object[] objx = { member.getId(), priceActive.getId() };
				List<Map<String, Object>> hasMap = DataBaseConnection.getList(sqlx, objx);
				if (hasMap.size() > 0) {
					// 已经发起了活动，提示用户购买或继续转发，让别人帮忙砍价
					ret.accumulate("success", false).accumulate("message", "您已经发起过该砍价活动，请购买商品或发起新的砍价活动！")
							.accumulate("already", true).accumulate("id", hasMap.get(0).get("id"));
					response(ret);
					return;
				}

				// 用户未发起过砍价活动，则发起一个砍价活动
				MathRandom4dou mathRandom = new MathRandom4dou();
				String[] cutRates = mathRandom.getCutMoney(0.01, 1, priceActive.getTotalTimes());
				String cutRatess = EasyUtils.getStr(cutRates, ",");
				Double[] cutRateD = EasyUtils.StringConvert2Double(cutRatess);

				priceCutDown pcd = new priceCutDown();
				pcd.setMoney(priceActive.getMoney());
				pcd.setMember(member.getId());
				pcd.setProduct(priceActive.getProduct());
				pcd.setStatus("0");
				pcd.setTime(new Date());
				pcd.setCutRates(cutRatess);
				pcd.setCutMoney(Double.valueOf(df.format(canMoney * cutRateD[0])));
				pcd.setLowPrice(priceActive.getLowPrice());
				pcd.setPriceActive(priceActive.getId());
				pcd.setPeriod(priceActive.getPeriod());
				pcd.setTotalTimes(priceActive.getTotalTimes());
				pcd.setCurrentTimes(1);
				pcd = (priceCutDown) service.saveOrUpdate(pcd);

				ret.accumulate("success", true)
						.accumulate("message", "你已成功为自己砍掉: " + EasyUtils.decimalFormat(pcd.getCutMoney()) + "元！")
						.accumulate("id", pcd.getId()).accumulate("already", false);
				response(ret);
				return;
			}

			// 传递了id, 帮人砍价
			priceCutDown parent = (priceCutDown) service.load(priceCutDown.class, Long.valueOf(id));

			ret.accumulate("cutActive", false).accumulate("id", Long.valueOf(id));
			if ("1".equals(parent.getStatus())) {
				// 该砍价活动，已被支付，或已过期
				ret.accumulate("success", false).accumulate("message", "该砍价活动已购买或已过期，请发起新的砍价活动！").accumulate("already",
						true);
				response(ret);
				return;
			}

			// 被帮砍价者
			Member parentMember = (Member) service.load(Member.class, parent.getMember());

			if (String.valueOf(member.getId()).equals(String.valueOf(parentMember.getId()))) {
				// 两者是同一人，不让砍价
				ret.accumulate("success", false).accumulate("message", "当前砍价活动是由您发起的，请购买或找人帮砍！")
						.accumulate("already", true).put("cutActive", true);
				response(ret);
				return;
			}

			// 判断当前用户是否已经帮忙砍价了的
			String sqly = " select * from tb_price_cutdown where member = ? and parent = ? and status in (0,2) order by id desc limit 1 ";
			Object[] objy = { member.getId(), Long.valueOf(id) };
			List<Map<String, Object>> hasMapx = DataBaseConnection.getList(sqly, objy);

			if (hasMapx.size() > 0) {
				// 用户已经帮这个活动砍价过
				ret.accumulate("success", false).accumulate("message", "您已经参加过该砍价活动，请发起新的砍价活动！").accumulate("already",
						true);
				response(ret);
				return;
			}

			// 判断当前活动是否已经砍到低价了
			String sqlz = " select * from ( " + "	    select 	"
					+ "				1 as pcCount, count(pcp.id) pcpCount, "
					+ "             ( 1 + count(pcp.id) ) as currentTimes, " + "             pc.cutRates " + " 	from "
					+ "				tb_price_cutdown pc LEFT JOIN tb_price_cutdown pcp ON pc.id = pcp.parent "
					+ " 	where " + "				pc.id = ? and pc.status = 0 and not ISNULL(pc.Member) "
					+ " ) temp ";
			Object[] objz = { parent.getId() };
			Map<String, Object> currentMap = DataBaseConnection.getOne(sqlz, objz);
			JSONObject currentJsons = JSONObject.fromObject(currentMap);
			if (currentJsons.getInt("currentTimes") >= parent.getTotalTimes()) {
				// 商品已砍到低价了，提示用户不能砍价了
				ret.accumulate("success", false).accumulate("message", "该商品已购买或价格已砍到最低价不能再砍价啦！").accumulate("already",
						true);
				response(ret);
				return;
			}

			// 上述情况都不存在的情况下，帮人砍价
			priceCutDown pcd = new priceCutDown();
			String strx = currentJsons.getString("cutRates");
			Double[] cutRateD = EasyUtils.StringConvert2Double(strx);
			String status = "0";
			int index = currentJsons.getInt("currentTimes");
			if (index == parent.getTotalTimes() - 1) {
				status = "2";
			}

			pcd.setParent(parent);
			pcd.setMoney(priceActive.getMoney());
			pcd.setMember(member.getId());
			pcd.setProduct(priceActive.getProduct());
			pcd.setTime(new Date());
			pcd.setCutMoney(Double.valueOf(df.format(canMoney * cutRateD[index])));
			pcd.setPeriod(priceActive.getPeriod());

			// 增加，砍价所属活动
			pcd.setPriceActive(priceActive.getId());
			pcd.setStatus(status);
			pcd.setLowPrice(parent.getLowPrice());
			pcd.setCurrentTimes(index);
			pcd.setTotalTimes(parent.getTotalTimes());
			pcd = (priceCutDown) service.saveOrUpdate(pcd);

			ret.accumulate("success", true).accumulate("message",
					"您已经帮[" + parentMember.getName() + "]砍了" + EasyUtils.decimalFormat(pcd.getCutMoney()) + "元，谢谢您的参与！")
					.accumulate("already", true);
			response(ret);
			return;

		} catch (Exception e) {
			ret.accumulate("success", false).accumulate("message", e.toString());
			e.printStackTrace();
		}

		// 返回结果
		response(ret);
	}

	/**
	 * 砍价排行
	 */
	public List<Map<String, Object>> getCutRanking(Long priceAcitve) {
		List<Map<String, Object>> sortList = new ArrayList<Map<String, Object>>();
		try {
			String sqlx = " select ttx.* from ( "
					+ "      select tdd.*, (select (money - SUM(cutMoney)) as currentMoney from tb_price_cutdown where parent = tdd.parent  or id = tdd.parent ) as currentMoney from ( "
					+ "          select pc.id, pc.money, pc.cutMoney as totalCut, m.name, m.image, pc.priceActive, IFNULL(pc.parent, pc.id) as parent from tb_price_cutdown pc  LEFT JOIN tb_member m ON pc.member = m.id  "
					+ "			) tdd " + " ) ttx  " + " where ttx.priceActive = ? " + " ORDER BY ttx.totalCut desc "
					+ " limit 30 ";
			Object[] objx = { priceAcitve };
			sortList = DataBaseConnection.getList(sqlx, objx);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return sortList;

	}

	/**
	 * 查询俱乐部发起的砍价活动
	 */
	public void getPriceActiveList() {
		JSONObject ret = new JSONObject();
		try {

			Member member = toMember();

			String sqlx = " SELECT " + "			    id, money, lowPrice, status, creator, activeName " + " FROM "
					+ "				tb_price_cutdown " + " WHERE " + "				creator = ? AND ISNULL(Member) "
					+ " ORDER BY id desc  ";
			Object[] objx = { member.getId() };

			List<Map<String, Object>> priceActiveList = DataBaseConnection.getList(sqlx, objx);
			priceActiveList = EasyUtils.dateANDdecimalFormat(priceActiveList, "yyyy-MM-dd HH:mm:ss");
			JSONArray priceActiveJson = JSONArray.fromObject(priceActiveList);
			ret.accumulate("success", true).accumulate("message", "OK").accumulate("priceActiveList", priceActiveJson);

			request.setAttribute("ret", ret);
			request.getRequestDispatcher("active/price_cutdown_list.jsp").forward(request, response);
		} catch (Exception e) {
			ret.accumulate("success", false).accumulate("message", e.toString());
			e.printStackTrace();
		}

	}

	/**
	 * 修改砍价活动的状态
	 */
	public void changePriceStatus() {
		JSONObject ret = new JSONObject();
		try {
			// 处理请求参数
			JSONObject param = JSONObject.fromObject(URLDecoder.decode(request.getParameter("json"), "UTF-8"));

			// 将砍价活动load出来
			priceCutDown pcd = (priceCutDown) service.load(priceCutDown.class, param.getLong("priceId"));

			String status = "";
			if ("0".equals(pcd.getStatus())) {
				status = "1";
			}
			if ("1".equals(pcd.getStatus())) {
				status = "0";
			}

			pcd.setStatus(status);

			// 持久化数据
			pcd = (priceCutDown) service.saveOrUpdate(pcd);

			ret.accumulate("success", true).accumulate("message", "OK").accumulate("status", pcd.getStatus());
		} catch (Exception e) {
			ret.accumulate("success", false).accumulate("message", e.toString());
			e.printStackTrace();
		}

		response(ret);
	}

	public void launchPriceActive() {
		JSONObject ret = new JSONObject();
		try {
			// 处理请求参数
			JSONObject param = JSONObject.fromObject(URLDecoder.decode(request.getParameter("json"), "UTF-8"));

			// 用户登录
			Member member = toMember();

			// 获取参数
			String source = param.getString("source");

			// 将当前健身俱乐部发布的健身卡，查询出来
			String sqlx = " select * from tb_product where isClose = 2 AND audit = 1 AND member = ? order by id desc ";
			Object[] objx = { member.getId() };
			List<Map<String, Object>> productList = DataBaseConnection.getList(sqlx, objx);
			productList = EasyUtils.dateANDdecimalFormat(productList, "yyyy-MM-dd HH:mm:ss");
			ret.accumulate("success", true).accumulate("message", "OK").accumulate("source", source)
					.accumulate("productList", JSONArray.fromObject(productList));
			if (!"release".equals(source)) {
				String sqly = " select * from tb_price_cutdown where id = ? ";
				Object[] objy = { param.getLong("priceId") };
				Map<String, Object> priceActiveMap = DataBaseConnection.getOne(sqly, objy);
				priceActiveMap = EasyUtils.dateFormat(priceActiveMap, "yyyy-MM-dd HH:mm:ss");
				ret.accumulate("priceActive", JSONObject.fromObject(priceActiveMap));
			}
			request.setAttribute("ret", ret);
			request.getRequestDispatcher("/active/price_cutdown.jsp").forward(request, response);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 跳转俱乐部wifi页面
	 */
	public String wifi() {
		return "clubWifi";
	}

	/**
	 * 但情感俱乐部wifi信息
	 */
	public void getClubWifiById() {
		JSONObject ret = new JSONObject();
		try {
			String sqlx = "select id, club, ssid, bssid, password from tb_club_wifi where club = ? ";
			Object[] objx = { toMember().getId() };
			ret.accumulate("success", true).accumulate("message", "OK").accumulate("clubWifi",
					EasyUtils.dateFormat(DataBaseConnection.getOne(sqlx, objx), "yyyy-MM-dd HH:mm:ss"));
		} catch (Exception e) {
			ret.accumulate("success", false).accumulate("message", e.toString());
			e.printStackTrace();
		}
		// 返回数据
		response(ret);

	}

	/**
	 * 保存或修改wifi
	 */
	public void saveWifi() {
		JSONObject ret = new JSONObject();
		try {
			// 处理请求参数
			JSONObject param = JSONObject.fromObject(URLDecoder.decode(request.getParameter("json"), "UTF-8"));
			Long clubId = toMember().getId();
			String ssid = param.getString("ssid");
			String password = param.getString("password");
			// 查询当前是否已有wifi信息
			String sqlx = " select * from tb_club_wifi where club = ? ";
			Object[] objx = { clubId };
			Map<String, Object> wifiInfo = DataBaseConnection.getOne(sqlx, objx);
			if (wifiInfo.size() > 0) {
				// 修改
				String sqlu = " update tb_club_wifi set ssid = ?, bssid = ?, password = ?, auto_date = ? where id = ? ";
				Object[] obju = { ssid, ssid, password, new Date(), wifiInfo.get("id") };
				DataBaseConnection.updateData(sqlu, obju);
			} else {
				// 新增数据
				String sqli = " insert into tb_club_wifi (club, ssid, bssid, password, auto_date) "
						+ " values(?,?,?,?,?) ";
				Object[] obji = { clubId, ssid, ssid, password, new Date() };
				DataBaseConnection.updateData(sqli, obji);
			}
			ret.accumulate("success", true).accumulate("message", "OK");

		} catch (Exception e) {
			ret.accumulate("success", false).accumulate("message", e.toString());
			e.toString();
		}
		response(ret);
	}

	/**
	 * setter && getter
	 */
	public File getImage() {
		return image;
	}

	public void setImage(File image) {
		this.image = image;
	}

	public String getImageFileName() {
		return imageFileName;
	}

	public void setImageFileName(String imageFileName) {
		this.imageFileName = imageFileName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
