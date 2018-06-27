package com.freegym.web.weixin.action;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;

import com.cardcol.web.balance.impl.CourseBalanceImpl;
import com.cardcol.web.order.ProductOrder45;
import com.cardcol.web.service.IClub45Service;
import com.freegym.web.BaseBasicAction;
import com.freegym.web.basic.Member;
import com.freegym.web.course.SignIn;
import com.freegym.web.order.CourseOrder;
import com.freegym.web.order.Order;
import com.sanmen.web.core.common.LogicException;
import com.sanmen.web.core.utils.LnglatUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 健身打卡
 * 
 * @author Administrator
 *
 */
@Namespace("")
@InterceptorRefs({ @InterceptorRef("customStack") })
@Results({ @Result(name = "success", location = "/WX/fitness_punch.jsp"),// 健身打卡
		@Result(name = "login", location = "/WX/login.jsp"),
		@Result(name = "punchCard", location = "/WX/punch_card.jsp") }) // 开始打卡
public class FitnessPunchWXManageAction extends BaseBasicAction {

	private static final long serialVersionUID = -1355471691824221906L;
	
	@Resource
	private CourseBalanceImpl service1;
	
	@Autowired
	private IClub45Service clubService;
	
	private Member member;

	private File image1;
	
	private String image1FileName;

	public String getImage1FileName() {
		return image1FileName;
	}

	public void setImage1FileName(String image1FileName) {
		this.image1FileName = image1FileName;
	}

	public File getImage1() {
		return image1;
	}

	public void setImage1(File image1) {
		this.image1 = image1;
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	/**
	 * 个人信息查询
	 * 
	 */
	@Override
	public String execute() {
		// Test Data
		Member memberTest = new Member();
		memberTest.setId(Long.parseLong("335"));
		memberTest.setRole("M");
		session.setAttribute("member", memberTest);

		// 个人简介
		member = (Member) session.getAttribute("member");
		// 查询个人的次数、时间、完成率
		if (member != null) {
			long m = member.getId();
			StringBuffer sb = new StringBuffer();
			sb.append(
					"select m.id,m.city,m.name,m.image,count(m.id) as timeNum ,sum(round((t.times/60),2)) as time ,round((COUNT(t.confrim)/m.workouttimes *100),1) as finishrate from tb_member m JOIN tb_plan_record t ON m.id=t.partake where t.done_date<=SYSDATE() ");
			sb.append("and m.id ='" + m + "' group by m.id");
			final List<Object> parm = new ArrayList<Object>();
			pageInfo = service.findPageBySql(sb.toString(), pageInfo, parm.toArray());
		} else {
			return "login";
		}
		return SUCCESS;
	}

	/**
	 * 微信平台扫码接口
	 * 
	 * @param Json jsons={cid:（门店id），signLng:(签到位置经度),signLat:(签到位置纬度))}
	 * 
	 * @return {"success":true,"count"："0"signLng":114,"signLat":30,"clubId":298,"clubName":"凯撒国际健身徐东路店","clubLng":114.350273,"clubLat":30.595039,"headPortrait":null,"items":[{"id":1,"member":9355,"NAME":"武汉健身一卡通","signType":"8"}]}
	 * false  返回的信息是：必须距离俱乐500米之内签到
	 * 
	 */
	public String punchCard() {
		// TODO Test Data
		Member memberTest = new Member();
		memberTest.setName("jun");
		memberTest.setId(Long.valueOf(470));
		session.setAttribute("member", memberTest);
		String jsons = "{\"cid\":503,\"signLat\":30.595039,\"signLng\":114.350273}";
		
		JSONObject obj = JSONObject.fromObject(jsons);
		JSONObject ret = new JSONObject();
		try {
			long cid = Long.valueOf(obj.getString("cid"));
			Member club = (Member) service.load(Member.class, cid);
			Double distance = (club.getLongitude() == null || club.getLatitude() == null) ? 0.0
					: LnglatUtil.GetDistance(obj.getDouble("signLat"), obj.getDouble("signLng"), club.getLatitude(),
							club.getLongitude());
			Double dou = new Double("500");
			if (distance < dou) {
				Member member = (Member) session.getAttribute("member");
				Long count = service.queryForLong("select count(*) from tb_sign_in s  where  s.memberSign= ?",
						member.getId());
				
				// TODO 要改动的部分
				/*List<Map<String, Object>> list = clubService.findSignOrder(member.getId(), obj.getString("cid"));*/
				String sql = "SELECT * FROM tb_product p WHERE id IN ( SELECT product FROM tb_product_order WHERE member = ? AND `status` = '1' AND product IN ( SELECT id FROM tb_product WHERE member = ?))";
				List<Map<String, Object>> list = clubService.queryForList(sql, member.getId(), obj.getString("cid"));
				
				List<?> fList = service.findObjectBySql(
						"from Friend f where f.member.id = ? and f.friend.id = ? and type = ?",
						new Object[] { member.getId(), cid, "1" });
				final JSONArray jarr = new JSONArray();
				
				if (fList.size() > 0) {
					final JSONObject ob1 = new JSONObject();
					ob1.accumulate("id", 0).accumulate("member", member.getId()).accumulate("NAME", "线下会员订单")
							.accumulate("signType", 0);
					jarr.add(ob1);
				}
				
				// 健身卡列表
				for (Map<String, Object> map : list) {
					final JSONObject ob = new JSONObject();
					ob.accumulate("id", map.get("id")).accumulate("member", map.get("member"))
							.accumulate("NAME", map.get("NAME")).accumulate("signType", map.get("type"));
					jarr.add(ob);
				}
				
				ret.accumulate("success", true).accumulate("count", count)
						.accumulate("signLng", obj.getDouble("signLng")).accumulate("signLat", obj.getDouble("signLat"))
						.accumulate("clubID", club.getId()).accumulate("clubName", club.getName())
						.accumulate("clubLng", club.getLongitude()).accumulate("clubLat", club.getLatitude())
						.accumulate("headPortrait" + "", member.getHeadPortrait()).accumulate("items", jarr);
			} else {
				ret.accumulate("success", false).accumulate("message", "请在俱乐部500米范围内签到！");
			}
		} catch (Exception e) {
			ret.accumulate("success", false).accumulate("message", "扫描的二维码不正确");
		}
		request.setAttribute("pageInfo", ret);
		
		return "punchCard";
	}
	

	/**
	 * 签到，传入参数，{orderid:（被签到id）,signType：（签到订单类型），orderId:（签到订单）,
	 * money（金额）,signLng:(签到经度),signLat:(签到位置纬度),signType:(签到订单类型)}
	 */
	public void sign() {
		String jsons = "{\"orderId\":10,\"culbId\":783,\"signType\":8,\"signLat\":30.0,\"signLng\":114.0}";
		JSONObject obj = JSONObject.fromObject(jsons);
		JSONObject ret = new JSONObject();
		if (!obj.containsKey("signLng") || !obj.containsKey("signLat"))
			throw new LogicException("您的经纬度未获取到，请确认！");
		if (!obj.containsKey("signType"))
			throw new LogicException("未获得签到订单类型，请确认！");
		Member member = (Member) session.getAttribute("member");
		Member me = (Member) service.load(Member.class, obj.getLong("culbId"));
		long count = service.queryForLong(
				"select count(*) from tb_sign_in s where s.orderId= ? and s.memberSign =? and s.signDate LIKE  '%"
						+ new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + "%'",
				obj.getLong("orderId"), member.getId());
		SignIn si = new SignIn();
		Order order = null;
		si.setOrderId(obj.getLong("orderId"));// 订单id
		if (obj.getString("signType").equals(ORDER_TYPE_ONECARD)) {
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
		if (member.getHeadPortrait() == null) {
			String fileName = image1 != null ? saveFile("picture", image1, image1FileName, null) : null;
			if (fileName != null) {
				member.setHeadPortrait(fileName);
				service.saveOrUpdate(member);
				si = (SignIn) service.saveOrUpdate(si);
				member.setLongitude(obj.getDouble("signLng"));
				member.setLatitude(obj.getDouble("signLat"));
				service.saveOrUpdate(member);
				if (obj.getString("signType").equals("5")) {
					service1.execute(order);
				}
				ret.accumulate("success", true).accumulate("message", "OK").accumulate("clubId", si.getMemberAudit());
			} else {
				ret.accumulate("success", false).accumulate("message", "请先上传照片，再点击确定");
			}
		} else if ("8".equals(obj.getString("signType"))) {
			if (count == 0) {
				try {
					si = (SignIn) service.saveOrUpdate(si);
					ProductOrder45 po = (ProductOrder45) service.load(ProductOrder45.class, obj.getLong("orderId"));
					Date date = new Date();
					if (po.getOrderStartTime().getTime() > date.getTime()) {
						po.setOrderStartTime(date);
						service.saveOrUpdate(po);
					}
					member.setLongitude(obj.getDouble("signLng"));
					member.setLatitude(obj.getDouble("signLat"));
					service.saveOrUpdate(member);
					if (obj.getString("signType").equals("5")) {
						order.setStatus(ORDER_STATUS_FINSH);
						service.saveOrUpdate(order);
					}
					ret.accumulate("success", true).accumulate("message", "OK").accumulate("signId", si.getId());
				} catch (Exception e) {
					log.error("error", e);
					response(e);
				}
			} else {
				ret.accumulate("success", false).accumulate("message", "本健身卡每天只能签到一次!");
			}
		} else {
			si = (SignIn) service.saveOrUpdate(si);
			member.setLongitude(obj.getDouble("signLng"));
			member.setLatitude(obj.getDouble("signLat"));
			service.saveOrUpdate(member);
			if (obj.getString("signType").equals("5")) {
				order.setStatus(ORDER_STATUS_FINSH);
				service.saveOrUpdate(order);
			}
			ret.accumulate("success", true).accumulate("message", "OK").accumulate("signId", si.getId());
		}
		response(ret);
	}
}