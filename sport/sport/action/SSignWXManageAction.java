package sport.action;

import java.io.File;
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
import com.cardcol.web.utils.Base64Util;
import com.databaseConnection.DataBaseConnection;
import com.freegym.web.basic.Member;
import com.freegym.web.course.SignIn;
import com.freegym.web.mobile.BasicJsonAction;
import com.freegym.web.order.Order;
import com.freegym.web.order.ProductOrder;
import com.sanmen.web.core.common.LogicException;
import com.sanmen.web.core.utils.LnglatUtil;

import ecartoon.wx.util.loginCommons;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Namespace("")
@InterceptorRefs({ @InterceptorRef("customStack") })
@Results({
	@Result(name = "findOrder" ,location = "/sport/qiandao.jsp")
	
})
public class SSignWXManageAction extends BasicJsonAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3218500340516948574L;
	private File image1;

	@Resource
	private CourseBalanceImpl service1;

	private String image1FileName;



	/**
	 * 签到时扫码及判断是否第一次签到 2017/02/28
	 */
	public String findOrder() {
		String url = request.getParameter("url");
		String id =  url.substring(url.lastIndexOf("=")+1,url.length()-1);
		String idx = Base64Util.decode(id);
		
		Member member = (Member) request.getSession().getAttribute("member");
		if(member == null){
			loginCommons.thirdType = "W";
			new loginCommons().setMember(request,service);
			member = (Member) request.getSession().getAttribute("member");
		}else{
			member = (Member) service.load(Member.class, member.getId());
			request.getSession().setAttribute("member", member);
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		JSONObject obj = new JSONObject();
		obj.accumulate("coachId", idx)
		   .accumulate("signLat", request.getParameter("signLat"))
		   .accumulate("signLng", request.getParameter("signLng"))
		;
		JSONObject ret = new JSONObject();
		
		try {
		long coachId = Long.valueOf(obj.get("coachId").toString());
		Member coach = (Member) service.load(Member.class, coachId);
		Double distance = (coach.getLongitude() == null || coach.getLatitude() == null) ? 0.0 : LnglatUtil.GetDistance(obj.getDouble("signLat"), obj.getDouble("signLng"), coach.getLatitude(), coach.getLongitude());
		Double dou =new Double("500");
		if (distance < dou) {
			member = (Member) service.load(Member.class, member.getId());
			Long count = service.queryForLong("select count(*) from tb_sign_in s  where  s.memberSign= ?  ", member.getId());
			List<Map<String, Object>> list = service.findSignOrder(member.getId(),obj.get("coachId").toString(),"wx");
			List<?> fList = service.findObjectBySql("from Friend f where f.member.id = ? and f.friend.id = ? and type = ?", new Object[] { member.getId(), coachId, "1" });
			final JSONArray jarr = new JSONArray();
			if (fList.size() > 0){
				final JSONObject ob1= new JSONObject();
				ob1.accumulate("id",0)
						.accumulate("member",member.getId())
						.accumulate("NAME", "线下会员订单")
						.accumulate("signType",0);
				jarr.add(ob1);
			}
			for (Map<String, Object> map : list) {
				final JSONObject ob = new JSONObject();
				ob.accumulate("orderId", map.get("orderId"))
						.accumulate("member", map.get("member"))
						.accumulate("NAME", map.get("NAME"))
						.accumulate("signType", map.get("type"));
				jarr.add(ob);
			}
			Date  signDate = new Date();
			String signdate = sdf.format(signDate);
			ret.accumulate("success", true)
			        .accumulate("count", count)
					.accumulate("signLng", obj.getDouble("signLng"))
					.accumulate("signLat", obj.getDouble("signLat"))
					.accumulate("signDateymd", signdate.substring(0, 10))
					.accumulate("signDatehms", signdate.substring(11,16))
					.accumulate("memberId", member.getId())
					.accumulate("memberName", member.getName())
					.accumulate("memberImage", member.getImage())
					.accumulate("coachId", coach.getId())
					.accumulate("coachName", coach.getName())
					.accumulate("coachLng", coach.getLongitude())
					.accumulate("coachLat", coach.getLatitude())
					.accumulate("signI", jarr);
		} else {
		       ret.accumulate("success", false).accumulate("message", "请在教练500米范围内签到！");
		}
		} catch (Exception e) {
			ret.accumulate("success", false).accumulate("message", "扫描的二维码不正确");
		}
		response(ret);
		request.setAttribute("signDetail", ret);//将签到信息存到request中，方便前台调用
		return "findOrder";
	}

	/**
	 * 签到，传入参数，{orderid:（被签到id）,signType：（签到订单类型），orderId:（签到订单）,
	 * money（金额）,signLng:(签到经度),signLat:(签到位置纬度),signType:(签到订单类型)}
	 */
	public void sign() {
		
//		JSONObject jsonObject = new JSONObject();
//		jsonObject.accumulate("orderId", 168)
//				  .accumulate("coachId", 494)
//				  .accumulate("signType", 1)
//				  .accumulate("signLat", 30.515870)
//				  .accumulate("signLng", 114.400570)
//				  .accumulate("heartRate", 80)
//		;
//		
//		String jsons = jsonObject.toString();
		
		//SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//jsons="{\"orderId\":10,\"culbId\":783,\"signType\":8,\"signLat\":30.0,\"signLng\":114.0}";
		JSONObject obj = JSONObject.fromObject(jsons);
		JSONObject ret = new JSONObject();
		if (!obj.containsKey("signLng") || !obj.containsKey("signLat"))
			throw new LogicException("您的经纬度未获取到，请确认！");
		if (!obj.containsKey("signType"))
			throw new LogicException("未获得签到订单类型，请确认！");
		Member member = (Member) request.getSession().getAttribute("member");
		
		member = (Member) service.load(Member.class, member.getId());//当前登录会员
		Member coach = (Member) service.load(Member.class, obj.getLong("coachId"));//被签到的教练
		// 查询该会员该订单当天的签到次数
		long count = service.queryForLong(
						"select count(*) from tb_sign_in s where s.orderId= ? and s.memberSign =? and s.signDate LIKE  '%"
								+ new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + "%'", obj.getLong("orderId"), member.getId());
		SignIn si = new SignIn();
		Order order = null;
		si.setOrderId(obj.getLong("orderId"));// 订单id
		
		if (obj.getString("signType").equals(ORDER_TYPE_PRODUCT)) {
			order = (ProductOrder) service.load(ProductOrder.class, si.getOrderId());
			si.setOrderNo(order.getNo());// 订单编号
		}
		
		si.setMemberAudit(coach);// 被签到方id
		si.setMemberSign(member);// 签到方
		si.setSignDate(new Date());// 签到当前时间
		si.setSignLng(obj.getDouble("signLng"));// 签到经度
		si.setSignLat(obj.getDouble("signLat"));// 签到纬度
		si.setSignType(obj.getString("signType"));// 签到订单类型
		
		//根据运动心率判断是否第一次签到
		//因为heart_rate字段在member实体类中没有对应的属性，而数据库中有该字段，所以直接从数据库中查询
		String sqlx=  "select * from tb_member where id=?";
		Object[] objx = {member.getId()};
		Map<String, Object> mapx = DataBaseConnection.getOne(sqlx, objx);
		
		if (mapx.get("heart_rate")  == null) {
			 String heartRate = obj.getString("heartRate")==null ? null : obj.getString("heartRate");
			if (heartRate != null || !"".equals(heartRate)) {
				Date date =new Date();
				ProductOrder po = (ProductOrder) service.load(ProductOrder.class, obj.getLong("orderId"));
				Date poStartDate = po.getOrderStartTime();
				// 如果当前订单开始时间 小于 当前时间 则修改订单状态
				if(poStartDate.getTime() < date.getTime()){
					po.setOrderStartTime(date);
					String sqld = "select DATEDIFF(orderEndTime,orderStartTime) dd  from tb_product_order where id=?";
					Object[] objd = {obj.get("orderId")};
					Map<String, Object> mapd = DataBaseConnection.getOne(sqld, objd);
					Object da = (Object)mapd.get("dd")==null ? 0 : mapd.get("dd");//给默认值0天
					long dateTime = (long) da;
					if (dateTime != 0) {
						Date newDate = addDate(date, dateTime);
						po.setOrderEndTime(newDate);
					} else {
						po.setOrderEndTime(date);
					}
					service.saveOrUpdate(po);
				}
				
				String sqlm = "update tb_member set heart_rate =? where id =? ";
				Object[] objm = {obj.get("heartRate"),member.getId()};
				int i = DataBaseConnection.updateData(sqlm, objm);
				if(i > 0){
					member = (Member) service.load(Member.class, member.getId());
				}
				si = (SignIn) service.saveOrUpdate(si);
				member.setLongitude(obj.getDouble("signLng"));
				member.setLatitude(obj.getDouble("signLat"));
				service.saveOrUpdate(member);
				if (obj.getString("signType").equals("5")) {
					service1.execute(order);
				}
				
				ret.accumulate("success", true).accumulate("message", "OK")
				.accumulate("signId", si.getId());
			} else {
				ret.accumulate("success", false).accumulate("message", "请先输入运动心率，再点击确定");
			 }
		  } else if("1".equals(obj.getString("signType"))){
			  if (count == 0){
				try {
					String sqlm = "update tb_member set heart_rate =? where id =? ";
					Object[] objm = {obj.get("heartRate"),member.getId()};
					int i = DataBaseConnection.updateData(sqlm, objm);
					if(i > 0){
						member = (Member) service.load(Member.class, member.getId());
					}
					si = (SignIn) service.saveOrUpdate(si);
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
				ret.accumulate("success", false).accumulate("message", "每天只能签到一次!");
			}
		}else{
			
			String sqlm = "update tb_member set heart_rate =? where id =? ";
			Object[] objm = {obj.get("heartRate"),member.getId()};
			int i = DataBaseConnection.updateData(sqlm, objm);
			if(i > 0){
				member = (Member) service.load(Member.class, member.getId());
			}
			si = (SignIn) service.saveOrUpdate(si);
			member.setLongitude(obj.getDouble("signLng"));
			member.setLatitude(obj.getDouble("signLat"));
			service.saveOrUpdate(member);
			/*if (obj.getString("signType").equals("5")) {
				order.setStatus(ORDER_STATUS_FINSH);
				service.saveOrUpdate(order);
			}*/
			ret.accumulate("success", true).accumulate("message", "OK").accumulate("signId", si.getId());
		}
		response(ret);
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
	public static Date addDate(Date date,long day) {
		 long time = date.getTime(); // 得到指定日期的毫秒数
		 day = day*24*60*60*1000; // 要加上的天数转换成毫秒数
		 time+=day; // 相加得到新的毫秒数
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
	
	
	
	
	
	

}