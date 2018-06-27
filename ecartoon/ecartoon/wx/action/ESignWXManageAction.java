package ecartoon.wx.action;

import java.io.File;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.cardcol.web.balance.impl.CourseBalanceImpl;
import com.cardcol.web.basic.Product45;
import com.cardcol.web.order.ProductOrder45;
import com.cardcol.web.utils.Base64Util;
import com.databaseConnection.DataBaseConnection;
import com.freegym.web.basic.Member;
import com.freegym.web.course.SignIn;
import com.freegym.web.mobile.BasicJsonAction;
import com.freegym.web.order.CourseOrder;
import com.freegym.web.order.Order;
import com.sanmen.web.core.common.LogicException;
import com.sanmen.web.core.utils.LnglatUtil;

import ecartoon.wx.util.loginCommons;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Namespace("")
@InterceptorRefs({ @InterceptorRef("customStack") })
@Results({ @Result(name = "findOrder", location = "/ecartoon-weixin/qiandao.jsp"),
		@Result(name = "error", location = "/ecartoon-weixin/codeError.jsp")

})
public class ESignWXManageAction extends BasicJsonAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3218500340516948574L;
	private File image1;

	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	@Resource
	private CourseBalanceImpl service1;

	private String image1FileName;

	private File memberHead;

	private String memberHeadFileName;

	public void saoma() {
		try {
			Member member = (Member) request.getSession().getAttribute("member");
			if (member == null) {
				request.setAttribute("status", 0);
				request.getRequestDispatcher("ecartoon-weixin/requestOpenId2.jsp").forward(request, response);
				member = (Member) request.getSession().getAttribute("member");
			} else {
				member = (Member) service.load(Member.class, member.getId());
				request.getSession().setAttribute("member", member);
			}
			String sql = "SELECT a.name,a.image,a.mobilephone,a.coach,a.province,a.city,a.county,a.longitude,a.latitude,"
					+ " count(b.member) AS signNum FROM tb_member a,tb_present_heartrate b WHERE a.id = b.member AND a.id = "
					+ member.getId();
			Long count = service.queryForLong("select count(*) from tb_sign_in s  where  s.memberSign= ?  ",
					member.getId());
			Map<String, Object> map = DataBaseConnection.getOne(sql, null);
			map.put("signNum", count);
			request.setAttribute("memberInfo", JSONObject.fromObject(map));
			request.getRequestDispatcher("ecartoon-weixin/saoma.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 上传图片
	 */
	public void uploadImage() {
		JSONObject ret = new JSONObject();
		try {
			String fileName = saveFile("picture", memberHead, memberHeadFileName, null);
			/*
			 * String path =
			 * request.getSession().getServletContext().getRealPath("/picture");
			 * File picFile = new File(path + "\\" + fileName); File picDir =
			 * new File("e://picture/picture/" + fileName);
			 * FileUtils.copyFile(picFile, picDir);
			 */
			ret.accumulate("success", true).accumulate("image", fileName);
			response(ret);
		} catch (Exception e) {
			ret.accumulate("success", false);
			e.printStackTrace();
		}
	}

	/**
	 * E卡通微信公众号签到 by dou 2018-01-08
	 */
	public String findOrder() {
		// 测试数据
		// Member member = (Member) service.load(Member.class,
		// Long.parseLong("454"));

		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

			String url = request.getParameter("url");
			String idx = Base64Util.decode(url);

			Member club = (Member) service.load(Member.class, Long.parseLong(idx));

			Member member = (Member) request.getSession().getAttribute("member");
			if (member == null) {
				loginCommons login = new loginCommons();
				member = login.setMember(request, service);
				member = (Member) service.load(Member.class, member.getId());
				request.getSession().setAttribute("member", member);
			} else {
				member = (Member) service.load(Member.class, member.getId());
				request.getSession().setAttribute("member", member);
			}

			JSONObject ret = new JSONObject();
			JSONObject objx = new JSONObject();

			// 返回用户个人基本信息
			Long count = service.queryForLong("select count(*) from tb_sign_in s  where  s.memberSign= ?  ",
					member.getId());

			JSONObject obj = new JSONObject();
			obj.accumulate("clubId", idx).accumulate("signLat", request.getParameter("signLat")).accumulate("signLng",
					request.getParameter("signLng"));

			Date signDate = new Date();
			String signdate = sdf.format(signDate);

			objx.accumulate("count", count).accumulate("signLng", obj.getDouble("signLng"))
					.accumulate("signLat", obj.getDouble("signLat"))
					.accumulate("signDateymd", signdate.substring(0, 10)).accumulate("memberName", member.getName())
					.accumulate("memberImage", member.getImage()).accumulate("headPortrait", member.getHeadPortrait())
					.accumulate("clubName", club.getName()).accumulate("clubId", club.getId())
					.accumulate("clubLng", club.getLongitude()).accumulate("clubLat", club.getLatitude())
					.accumulate("signDatehms", signdate.substring(11, 16)).accumulate("memberId", member.getId());

			Double distance = (club.getLongitude() == null || club.getLatitude() == null) ? 0.0
					: LnglatUtil.GetDistance(obj.getDouble("signLat"), obj.getDouble("signLng"), club.getLatitude(),
							club.getLongitude());
			Double dou = new Double("3000");
			// Double dou = distance + 1;
			if (distance < dou) {
				member = (Member) service.load(Member.class, member.getId());

				List<Map<String, Object>> list = service.findSignOrder(member.getId(), obj.get("clubId").toString());

				if (list.size() > 0) {
					List<?> fList = service.findObjectBySql(
							"from Friend f where f.member.id = ? and f.friend.id = ? and type = ?",
							new Object[] { member.getId(), club.getId(), "1" });
					final JSONArray jarr = new JSONArray();
					if (fList.size() > 0) {
						final JSONObject ob1 = new JSONObject();
						ob1.accumulate("id", 0).accumulate("member", member.getId()).accumulate("NAME", "线下会员订单")
								.accumulate("signType", 0);
						jarr.add(ob1);
					}

					for (Map<String, Object> map : list) {
						final JSONObject ob = new JSONObject();
						ob.accumulate("orderId", map.get("id")).accumulate("member", map.get("member"))
								.accumulate("NAME", map.get("NAME")).accumulate("signType", map.get("type"));
						jarr.add(ob);
					}

					objx.accumulate("signI", jarr);
					ret.accumulate("success", true).accumulate("jsons", objx);
				} else {
					ret.accumulate("success", false).accumulate("jsons", objx).accumulate("message",
							"您没有有效订单，无法签到，请购买相应商品！");
				}
			} else {
				ret.accumulate("success", false).accumulate("jsons", objx).accumulate("message", "请在俱乐部3000米范围内签到！");
			}

			// 将签到信息存到request中，方便前台调用
			request.setAttribute("signDetail", ret);
			return "findOrder";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	/**
	 * 签到，传入参数，{orderid:（被签到id）,signType：（签到订单类型），orderId:（签到订单）,
	 * money（金额）,signLng:(签到经度),signLat:(签到位置纬度),signType:(签到订单类型)}
	 */
	public void sign() {
		try {
			// jsons="{\"orderId\":10,\"culbId\":783,\"signType\":8,\"signLat\":30.0,\"signLng\":114.0}";
			JSONObject obj = JSONObject.fromObject(URLDecoder.decode(jsons, "UTF-8"));
			JSONObject ret = new JSONObject();
			if (!obj.containsKey("signLng") || !obj.containsKey("signLat"))
				throw new LogicException("您的经纬度未获取到，请确认！");
			if (!obj.containsKey("signType"))
				throw new LogicException("未获得签到订单类型，请确认！");
			Member member = (Member) request.getSession().getAttribute("member");

			member = (Member) service.load(Member.class, member.getId());// 当前登录会员
			Member me = (Member) service.load(Member.class, obj.getLong("clubId"));// 被签到的俱乐部

			// 查询该会员该订单当天的签到次数
			long count = service.queryForLong(
					"select count(*) from tb_sign_in s where s.orderId= ? and s.memberSign =? and s.signDate LIKE  '%"
							+ new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + "%'",
					obj.getLong("orderId"), member.getId());
			SignIn si = new SignIn();
			Order order = null;
			si.setOrderId(obj.getLong("orderId"));// 订单id

			if (obj.getString("signType").equals(ORDER_TYPE_ONECARD)) {
				// 如果当前订单开始时间 小于 当前时间 则修改订单状态
				Date date = new Date();
				ProductOrder45 po = (ProductOrder45) service.load(ProductOrder45.class,
						Long.parseLong(obj.getString("orderId")));
				if (po.getOrderStartTime().getTime() > date.getTime()) {
					po.setOrderStartTime(sdf.parse(sdf.format(date)));
					Product45 po45 = po.getProduct();
					int periodUnit = (po45.getPeriodUnit() == "A" ? 30
							: po45.getPeriodUnit() == "B" ? 3 * 30 : po45.getPeriodUnit() == "C" ? 12 * 30 : 1);

					long dateTime = (long) (po45.getPeriod() * periodUnit) - 1;

					if (dateTime != 0) {
						Date newDate = addDate(date, dateTime);
						po.setOrderEndTime(newDate);
					} else {
						po.setOrderEndTime(date);
					}

					service.saveOrUpdate(po);
				}
				order = (ProductOrder45) service.load(ProductOrder45.class, si.getOrderId());
				si.setOrderNo(order.getNo());// 订单编号
			} else if (obj.getString("signType").equals(ORDER_TYPE_COURSE)) {
				order = (CourseOrder) service.load(CourseOrder.class, si.getOrderId());
				si.setOrderNo(order.getNo());// 订单编号
			}

			si.setMemberAudit(me);// 被签到方id
			si.setMemberSign(member);// 签到方
			si.setSignDate(new Date());// 签到当前时间
			si.setSignLng(obj.getDouble("signLng"));// 签到经度
			si.setSignLat(obj.getDouble("signLat"));// 签到纬度
			si.setSignType(obj.getString("signType"));// 签到订单类型

			String fileName = obj.getString("image");

			if (member.getHeadPortrait() == null || "".equals(member.getHeadPortrait())) {
				if (fileName != null && !"null".equals(fileName) && !"".equals(fileName)) {

					member.setHeadPortrait(fileName);
					si = (SignIn) service.saveOrUpdate(si);
					member.setLongitude(obj.getDouble("signLng"));
					member.setLatitude(obj.getDouble("signLat"));
					service.saveOrUpdate(member);
					if (obj.getString("signType").equals("5")) {
						service1.execute(order);
					}
					// ret.accumulate("success", true).accumulate("message",
					// "OK")
					// .accumulate("clubId", si.getMemberAudit());
					// 2017-7-14 黄文俊 修复第一次签到返回值异常的bug
					ret.accumulate("success", true).accumulate("message", "OK").accumulate("signId", si.getId());
				} else {
					ret.accumulate("success", false).accumulate("message", "请先上传照片，再点击确定");
				}
			} else {
				if (count == 0) {
					try {
						si = (SignIn) service.saveOrUpdate(si);
						member.setLongitude(obj.getDouble("signLng"));
						member.setLatitude(obj.getDouble("signLat"));
						member.setHeadPortrait(fileName);
						service.saveOrUpdate(member);
						ret.accumulate("success", true).accumulate("message", "OK").accumulate("signId", si.getId());
					} catch (Exception e) {
						log.error("error", e);
						response(e);
					}
				} else {
					ret.accumulate("success", false).accumulate("message", "本健身卡每天只能签到一次!");
				}
			}
			response(ret);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 点评详情页的签到信息
	public void signInfo() {
		String type = request.getParameter("type");// 5为团体课 8为一卡通
		Map<String, Object> map = service.querySignInfo(id, type);
		JSONObject ret = new JSONObject();
		ret.accumulate("success", true).accumulate("map", map);
		response(ret);
	}

	// 时间相加的方法
	public static Date addDate(Date date, long day) {
		long time = date.getTime(); // 得到指定日期的毫秒数
		day = day * 24 * 60 * 60 * 1000; // 要加上的天数转换成毫秒数
		time += day; // 相加得到新的毫秒数
		return new Date(time); // 将毫秒数转换成日期
	}

	/**
	 * getter and setter
	 */
	public File getImage1() {
		return image1;
	}

	public void setImage1(File image1) {
		this.image1 = image1;
	}

	public String getImage1FileName() {
		return image1FileName;
	}

	public void setImage1FileName(String image1FileName) {
		this.image1FileName = image1FileName;
	}

	public File getMemberHead() {
		return memberHead;
	}

	public void setMemberHead(File memberHead) {
		this.memberHead = memberHead;
	}

	public String getMemberHeadFileName() {
		return memberHeadFileName;
	}

	public void setMemberHeadFileName(String memberHeadFileName) {
		this.memberHeadFileName = memberHeadFileName;
	}

}