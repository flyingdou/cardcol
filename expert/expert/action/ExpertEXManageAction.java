package expert.action;

import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;

import com.databaseConnection.DataBaseConnection;
import com.freegym.web.basic.Member;
import com.freegym.web.basic.MemberTicket;
import com.freegym.web.config.Setting;
import com.freegym.web.mobile.BasicJsonAction;
import com.freegym.web.order.Goods;
import com.freegym.web.order.GoodsOrder;
import com.freegym.web.order.Order;
import com.freegym.web.plan.Course;
import com.freegym.web.plan.Workout;
import com.freegym.web.plan.WorkoutDetail;
import com.freegym.web.system.Ticket;
import com.freegym.web.utils.EasyUtils;
import com.sanmen.web.core.utils.DateUtil;

import expert.util.AES;
import expert.util.SDK_WX;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * @author hw
 * @version 创建时间：2018年3月30日 上午11:00:19
 * @ClassName 类名称
 * @Description 类描述
 */
@Namespace("")
@InterceptorRefs({ @InterceptorRef("customStack") })
public class ExpertEXManageAction extends BasicJsonAction {
	private static final long serialVersionUID = 1L;

	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	/**
	 * 私人订制详情 传参数 id=1为王严专家
	 */
	public void loadPlan() {
		try {
			JSONObject obj = new JSONObject();
			Goods good = (Goods) service.load(Goods.class, Long.valueOf(1));
			obj.accumulate("success", true).accumulate("item", good.toJsons());
			response(obj);
		} catch (Exception e) {
			log.error("error", e);
		}
	}

	/**
	 * 保存会员设置
	 */
	public void saveMemberSetting() {
		try {
			Member member = (Member) service.load(Member.class, Long.valueOf(request.getParameter("memberId")));
			JSONObject obj = JSONObject.fromObject(URLDecoder.decode(jsons, "UTF-8"));
			Setting setting = new Setting();
			setting = service.loadSetting(member.getId());
			if (null == setting.getId()) {
				setting.setId(id);
			}
			setting.setMember(member.getId());
			String target = obj.getString("target");
			if ("A".equals(target)) {
				target = "1";
			} else if ("B".equals(target)) {
				target = "2";
			} else if ("C".equals(target)) {
				target = "3";
			} else if ("D".equals(target)) {
				target = "4";
			}
			setting.setTarget(target);
			setting.setStrengthDate(obj.getString("strengthDate"));
			setting.setHeight(obj.getInt("height"));
			setting.setWeight(obj.getDouble("weight"));
			setting.setWaistline(obj.getDouble("waistline"));
			setting.setMaxwm(obj.getDouble("maxwm"));
			setting.setFavoriateCardio(obj.getString("favoriateCardio"));
			if (obj.containsKey("st")) {
				member.setSex(obj.getString("st"));
				service.saveOrUpdate(member);
			}
			setting = (Setting) service.saveOrUpdate(setting);
			response(new JSONObject().accumulate("success", true));
		} catch (Exception e) {
			log.error("error", e);
		}
	}

	/**
	 * 获取私人订制商品详情
	 */
	public void getProducDetail() {
		Goods good = (Goods) service.load(Goods.class, Long.valueOf("1"));
		response(new JSONObject().accumulate("productName", good.getName()).accumulate("price", good.getPrice()));
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
	 * 取得当前用户当前订单类型所有适用的优惠券
	 */
	public void findTicketByType() {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Member member = (Member) service.load(Member.class, Long.valueOf(request.getParameter("memberId")));
			String orderType = "6";
			List<Map<String, Object>> mts = service.queryForList(
					"SELECT a.id tid, a.active_date activeDate, b.*,DATE_ADD(a.active_date, INTERVAL b.period DAY) useDate,b.scope FROM tb_member_ticket a LEFT JOIN tb_ticket b ON a.ticket = b.id WHERE DATE_ADD(a.active_date, INTERVAL b.period DAY) >= CURRENT_DATE() and a.status = ? and a.member = ? and b.scope like ?",
					STATUS_TICKET_USE, member.getId(), "%" + orderType + "%");
			final JSONArray jarr = new JSONArray();
			for (final Map<String, Object> mt : mts) {
				final JSONObject obj = new JSONObject();
				obj.accumulate("id", mt.get("id")).accumulate("name", mt.get("name"))
						.accumulate("price", mt.get("price")).accumulate("activeDate", sdf.format(mt.get("activeDate")))
						.accumulate("useDate", EasyUtils.dateFormat((Date) mt.get("useDate"), "yyyy-MM-dd"))
						.accumulate("ticketId", mt.get("tid")).accumulate("scope", mt.get("scope"));
				jarr.add(obj);
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
	 * 生成私人订制订单
	 */
	public void createGoodsOrder() {
		try {
			JSONObject json = JSONObject.fromObject(URLDecoder.decode(request.getParameter("json"), "UTF-8"));
			Member member = (Member) service.load(Member.class, json.getLong("memberId"));
			Order order = new GoodsOrder();
			Goods goods = (Goods) service.load(Goods.class, Long.valueOf("1"));
			MemberTicket ticket = new MemberTicket();
			double ticketMoney = 0;
			if (json.containsKey("ticket")) {
				ticket = (MemberTicket) service.load(MemberTicket.class, json.getLong("ticket"));
				ticket.setStatus(2);
				service.saveOrUpdate(ticket);
				ticketMoney = ticket.getTicket().getPrice();
				order.setTicketId(ticket.getId());
			} else {
				order.setTicketId(Long.valueOf("0"));
			}
			// 创建私人订制订单对象,补全属性
			((GoodsOrder) order).setGoods(goods);
			order.setPayNo(service.getKeyNo("", "TB_GOODS_ORDER", 28));
			order.setNo(service.getKeyNo("", "TB_GOODS_ORDER", 14));
			order.setTicketAmount(ticketMoney);
			order.setOrderDate(new Date());
			order.setMember(member);
			order.setUnitPrice(goods.getPrice());
			order.setCount(1);
			order.setContractMoney(goods.getPrice());
			order.setPayServiceCostE(0.0);
			order.setPayServiceCostM(0.0);
			order.setPayType("1");
			order.setShipTimeType("1");
			order.setShipType("1");
			order.setSurplusMoney(goods.getPrice());
			order.setOrderMoney(Double.valueOf(EasyUtils.decimalFormat(goods.getPrice() - ticketMoney)));
			order.setOrderStartTime(EasyUtils.formatStringToDate(json.getString("strengthDate")));
			order.setOrderBalanceTime(EasyUtils.formatStringToDate(json.getString("strengthDate")));
			order.setOrderDate(new Date());
			order.setStatus('0');
			// E卡通订单
			order.setOrigin('E');
			order = (GoodsOrder) service.saveOrUpdate(order);
			// 绑定用户手机号
			member.setMobilephone(json.getString("phoneNumber"));
			member.setMobileValid("1");
			service.saveOrUpdate(member);
			// 支付签名
			SDK_WX sdk = new SDK_WX(request);
			String result = sdk.paySign(json.getString("openId"), order);
			response(JSONObject.fromObject(result).accumulate("orderId", order.getId()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * 
	 * 我的计划
	 */
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
			Long userId = mu.getId();
			if ("M".equals(mu.getRole())) {
				list = service.findObjectBySql(
						"from Course cs where (cs.member.id = ? or cs.id in (select a.course.id from Apply a where a.member.id = ? and a.status = ?)) and cs.planDate between ? and ? order by cs.planDate, cs.startTime",
						new Object[] { userId, userId, "2", sDate, eDate });
			}
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
	 * 选中课程的动作列表
	 */
	@SuppressWarnings({ "rawtypes", "unchecked", "unused" })
	public void findPlanByCourse() {
		try {
			// 处理请求参数
			JSONObject objx = JSONObject.fromObject(URLDecoder.decode(request.getParameter("json"), "UTF-8"));

			// 确保用户已登录
			Member member = (Member) service.load(Member.class, Long.valueOf(objx.getString("memberId")));
			final List<?> list = service.findObjectBySql("from Workout wo where wo.course.id = ? order by wo.sort",
					Long.valueOf(objx.getString("courseId")));
			
			// 课程总时长
			long sum = service.queryForLong(
					"SELECT SUM(planDuration) from tb_workout_detail d  LEFT JOIN tb_workout  w ON d.workout = w.id where w.course = ?",
					Long.valueOf(objx.getString("courseId")));

			String cycleCount = "select cycleCount from tb_course where id = ?";
			Object[] objd = { objx.get("courseId") };
			Map<String, Object> cycleCountMap = DataBaseConnection.getOne(cycleCount, objd);

			final JSONObject ret = new JSONObject();
			final JSONArray jarr = new JSONArray();

			// 单个动作时长
			String preSql = " select * from ( "
					      + " select SUM(planDuration) as preTime from tb_workout_detail wd LEFT JOIN tb_workout w ON wd.workout = w.id where w.id = ?  "
					      + " ) temp  ";
			for (final Iterator<?> it = list.iterator(); it.hasNext();) {
				final Workout wo = (Workout) it.next();
				
				// 获取单个动作时长
				Object[] preObj = {wo.getId()};
				Map<String, Object> preMap = DataBaseConnection.getOne(preSql, preObj);
				
				
				final JSONObject obj = new JSONObject(), actObj = new JSONObject();
				String imagePath = wo.getAction().getImage();
				if (imagePath.contains("picture")) {
					imagePath = imagePath.replaceAll("picture/", "");
				}
				actObj.accumulate("id", wo.getAction().getId()).accumulate("name", wo.getAction().getName())
						.accumulate("image", imagePath);
				final List<?> detailList = service.findObjectBySql("from WorkoutDetail wo where wo.workout.id = ?",
						wo.getId());
				StringBuilder str = new StringBuilder();
				if (detailList.size() > 0) {
					List planTimesList = new ArrayList<>();
					List planWeightList = new ArrayList<>();
					boolean planTimesIsnumber = false;
					boolean planWeightIsnumber = false;
					for (final Iterator<?> it2 = detailList.iterator(); it2.hasNext();) {
						WorkoutDetail detail = (WorkoutDetail) it2.next();
						if (!isNumeric(detail.getPlanTimes() == null ? "0" : detail.getPlanTimes())) {
							planTimesIsnumber = true;
							planTimesList.add(detail.getPlanTimes());
						} else {
							planTimesList.add(toInt(detail.getPlanTimes()));
						}

						if (!isNumeric(detail.getPlanWeight() == null ? "0" : detail.getPlanWeight())) {
							planWeightIsnumber = true;
							planWeightList.add(detail.getPlanWeight());
						} else {
							planWeightList.add(toDouble(detail.getPlanWeight()));
						}
					}


					str.append(detailList.size() + "组,");
					if (planTimesIsnumber) {
						str.append("每组" + planTimesList.get(0) + ",");
					} else {
						Collections.sort(planTimesList);
						if (planTimesList.get(0) == planTimesList.get(planTimesList.size() - 1)) {
							str.append("每组" + planTimesList.get(0) + "次,");
						} else {
							str.append("每组" + planTimesList.get(0) + "-" + planTimesList.get(planTimesList.size() - 1)
									+ "次,");
						}
					}

					if (planWeightIsnumber) {
						str.append("重量" + planWeightList.get(0).toString().substring(0,
								planWeightList.get(0).toString().lastIndexOf(".")) + ",");
					} else {
						Collections.sort(planWeightList);
						if (planWeightList.get(0) == planWeightList.get(planWeightList.size() - 1)) {
							str.append("重量" + planWeightList.get(0).toString().substring(0,
									planWeightList.get(0).toString().lastIndexOf(".")) + "公斤");
						} else {
							// 2017-7-18 hwj 修改成只显示最大值
							str.append("重量"
									+ planWeightList.get(planWeightList.size() - 1).toString().substring(0,
											planWeightList.get(planWeightList.size() - 1).toString().lastIndexOf("."))
									+ "公斤");
						}
					}
				}
				

				
				

				String douStr = str.toString();
				Character mode = wo.getAction().getPart().getProject().getMode();
			    String	strs = douStr.split("重量")[1];
			    strs = strs.split("公斤")[0];
				Integer douWeight = Integer.valueOf(strs);
				if (douWeight == 0 ){
					if (mode == '0') {
					 // 有氧运动，显示时间
					 Integer preTime = Double.valueOf(String.valueOf(preMap.get("preTime"))).intValue();
					 String timeStr =  preTime%60 == 0 ? (preTime/60 + "分") : ((preTime/60) + "分" + preTime%60 + "秒");
					 timeStr = "时间: " + timeStr;
					 str = new StringBuilder(timeStr);
					} else {
					 // 非有氧运动，显示组数，力竭
					 String[] douStrs = douStr.split(",");
					 douStr = douStrs[0] + ",力竭";
					 str = new StringBuilder(douStr);
					}
				}
				obj.accumulate("workoutId", wo.getId())
						.accumulate("mode", wo.getAction().getPart().getProject().getMode())
						.accumulate("action", actObj).accumulate("detail", str.toString());
				jarr.add(obj);
			}
			ret.accumulate("success", true).accumulate("items", jarr).accumulate("sumTime", sum)
					.accumulate("cycleCount", cycleCountMap.get("cycleCount"));
			response(ret);
		} catch (Exception e) {
			log.error("error", e);
			response(e);
		}
	}

	/**
	 * 输入激活码激活对应的优惠券。
	 */
	public void activeTicket() {
		JSONObject ret = new JSONObject();
		try {
			// 处理请求参数
			JSONObject obj = JSONObject.fromObject(URLDecoder.decode(request.getParameter("json"), "UTF-8"));

			// 处理获取的参数
			final String activeCode = obj.getString("activeCode");
			Member member = (Member) service.load(Member.class, obj.getLong("memberId"));

			// 验证激活码的有效性
			final Ticket tk = service.findTicketByActiveCode(activeCode);
			if (tk == null) {
				ret.accumulate("success", false).accumulate("message", "您的激活码无效，请确认！");
				response(ret);
				return;
			}
			final List<?> list = service.findMemberTicketByCode(activeCode, member.getId());
			if (list.size() > 0) {
				ret.accumulate("success", false).accumulate("message", "当前激活码已经被您激活过,请确认!");
				response(ret);
				return;
			}

			// 激活码有效，给该用户一个优惠券
			MemberTicket mt = new MemberTicket();
			mt.setMember(member);
			mt.setTicket(tk);
			mt.setActiveDate(new Date());
			mt.setStatus(STATUS_TICKET_USE);
			mt.setActiveCode(activeCode);

			// 持久化数据到数据库
			mt = (MemberTicket) service.saveOrUpdate(mt);
			ret.accumulate("success", true).accumulate("message", "激活成功");
		} catch (Exception e) {
			ret.accumulate("success", false).accumulate("message", e.toString());
		}
		response(ret);
	}

}
