package sport.action;

import java.io.File;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.freegym.web.active.TrainRecord;
import com.freegym.web.basic.Member;
import com.freegym.web.config.Setting;
import com.freegym.web.course.Message;
import com.freegym.web.mobile.BasicJsonAction;
import com.freegym.web.order.ActiveOrder;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.OpenimCustmsgPushRequest;
import com.taobao.api.request.OpenimCustmsgPushRequest.CustMsg;
import com.taobao.api.response.OpenimCustmsgPushResponse;

import ecartoon.wx.util.loginCommons;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Namespace("")
@InterceptorRefs({ @InterceptorRef("customStack") })
@Results({
	@Result(name = "showRecord", location = "/sport/body.jsp")
	
})
public class SActiveWXManageAction extends BasicJsonAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1599037108036622362L;
	
	
	
	// 筛选条件
		// 城市
		public String city;
		// 类型
		public String type;
		// 专长
		public String expertise;
		// 项目Id
		public Integer project;
		// 经度，纬度
		public Double longitude, latitude;
		// 图片
		public File image1;
		// 栏目代码值
		public String[] code;
	
	
	
	
	
	
	
	/**
	 * 展示运动记录
	 */
	public String showRecord() {
		try {
			
			//测试数据
			Member member = (Member) service.load(Member.class, Long.parseLong("9388"));
			
//			Member member = (Member) request.getSession().getAttribute("member");
			if(member == null){
				loginCommons.thirdType = "W";
				new loginCommons().setMember(request,service);
				member = (Member) request.getSession().getAttribute("member");
			}else{
				member = (Member) service.load(Member.class, member.getId());
				request.getSession().setAttribute("member", member);
			}
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date date = new Date();
			String sysDate = String.format("%tY-%tm-%td", date, date, date);
			Date doneDate = null;
			String  doneD = request.getParameter("doneDate");
			if(!"".equals(doneD) && !"null".equals(doneD) && null != doneD){
				doneDate = sdf.parse(doneD);
			}

			if (doneDate == null) {
				doneDate = sdf.parse(sysDate);
			}
			long memberId = member.getId();
			if (request.getParameter("memberId") != null) {
				memberId = Long.parseLong(request.getParameter("memberId").toString());
			}
			final List<?> list = service.findObjectBySql(
					"from TrainRecord r where r.partake.id = ? and r.doneDate = ? ",
					new Object[] { memberId, doneDate });

			final JSONArray jarr = new JSONArray();
			for (final Iterator<?> it = list.iterator(); it.hasNext();) {
				final TrainRecord tr = (TrainRecord) it.next();
				final JSONObject obj = new JSONObject();

				// id:主键 ，doneDate:完成时间,weight:体重,waist:腰围,hip:臀围
				obj.accumulate("id", tr.getId()).accumulate("doneDate", sdf.format(tr.getDoneDate()))
						.accumulate("weight", getDouble(tr.getWeight())).accumulate("waist", getDouble(tr.getWaist()))
						.accumulate("hip", getDouble(tr.getHip()));
				jarr.add(obj);
			}

			Setting setting = new Setting();
			setting = service.loadSetting(memberId);

			final JSONObject ret = new JSONObject();
			 ret.accumulate("success", true)
			    .accumulate("memberId", memberId)
				.accumulate("items", jarr)
				.accumulate("bmiLow", setting.getBmiLow())
				.accumulate("bmiHigh", setting.getBmiHigh());
			//response(ret);
			request.setAttribute("record", ret);

		} catch (Exception e) {
			log.error("error", e);
			response(e);
		}
		
		return "showRecord";
	}
	
	
	
	/**
	 * 保存活动记录，jsons=[{weight: 80, waist: 30, hip: 50, fat: 30}]
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String saveRecord() {
		
		Member member = (Member) request.getSession().getAttribute("member");
		member = (Member) service.load(Member.class, member.getId());
		final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			final JSONArray arr = JSONArray.fromObject(URLDecoder.decode(jsons, "UTF-8"));
			log.error("保存会员设置JSON串：" + arr.toString());
			final JSONObject obj = arr.getJSONObject(0);
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
				judgeId = id != null ? id : member.getId() ;
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

			
			if (obj.containsKey("activeId")) {
				final String[] strIds = obj.getString("activeId").split(",");
				for (String id : strIds) {
					StringBuffer sb = new StringBuffer("from ActiveOrder A where 1=1 and ");
					ArrayList list_fir = new ArrayList();
					sb.append(" A.id = ? ");
					list_fir.add(Long.parseLong(id));
					List<?> list = service.findObjectBySql(sb.toString(), list_fir.toArray());
					ArrayList userList = new ArrayList();
					for (Object ob : list) {
						ActiveOrder a = (ActiveOrder) ob;
						Long jid = Long.parseLong(a.getJudge().toString());
						Message msg = new Message();
						msg.setContent(getMobileUser().getName() + doneDate + "的训练日志");
						msg.setIsRead("0");
						msg.setMemberFrom(new Member(getMobileUser().getId()));
						msg.setMemberTo(new Member(jid));
						msg.setSendTime(new Date());
						msg.setStatus("1");
						msg.setType("4");
						service.saveOrUpdate(msg);
						if (jid != null) {
							userList.add(jid);
							break;
						}
					}
					if (!userList.isEmpty()) {
						String url = "http://gw.api.taobao.com/router/rest";
						TaobaoClient client = new DefaultTaobaoClient(url, "23330566", "0c6b77f937d49f3a14ca98a6316d110e");
						OpenimCustmsgPushRequest req = new OpenimCustmsgPushRequest();
						CustMsg obj11 = new CustMsg();
						obj11.setFromUser("some_one");
						obj11.setToAppkey("0");
						obj11.setToUsers(userList);
						obj11.setSummary("用户" + doneDate + "的运动成绩");
						obj11.setData("challenge");
						obj11.setAps("{\"alert\":\"裁判挑战成绩\"}");
						obj11.setApnsParam("challenge");
						obj11.setInvisible(1L);
						obj11.setFromNick(getMobileUser().getName());
						req.setCustmsg(obj11);
						OpenimCustmsgPushResponse rsp = client.execute(req);
						System.out.println(rsp.getBody());
					}
				}
			}
			final JSONObject obj1 = new JSONObject();
			obj1.accumulate("success", true).accumulate("message", "OK").accumulate("id", rs.get(0).getId());
			response(obj1);
		} catch (Exception e) {
			log.error("error", e);
			response(e);
		}
		
		return "showRecord";
	}
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * getter and setter
	 */
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getExpertise() {
		return expertise;
	}

	public void setExpertise(String expertise) {
		this.expertise = expertise;
	}

	public Integer getProject() {
		return project;
	}

	public void setProject(Integer project) {
		this.project = project;
	}

	public File getImage1() {
		return image1;
	}

	public void setImage1(File image1) {
		this.image1 = image1;
	}

	public String[] getCode() {
		return code;
	}

	public void setCode(String[] code) {
		this.code = code;
	}
	
	
	
	
	

}
