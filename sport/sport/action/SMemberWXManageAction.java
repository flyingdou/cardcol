package sport.action;

import java.io.File;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.databaseConnection.DataBaseConnection;
import com.freegym.web.basic.Member;
import com.freegym.web.config.Setting;
import com.freegym.web.mobile.BasicJsonAction;
import com.freegym.web.order.PresentHeartRate;
import com.freegym.web.system.Tickling;
import com.sanmen.web.core.common.LogicException;
import com.sanmen.web.core.common.PageInfo;

import ecartoon.wx.util.loginCommons;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Namespace("")
@InterceptorRefs({ @InterceptorRef("customStack") })
@Results({
	@Result(name ="mySign", location = "/sport/myfooter.jsp"),
	@Result(name = "loadMe", location = "/sport/mine_xx.jsp")
	
})
public class SMemberWXManageAction extends BasicJsonAction {

	private static final long serialVersionUID = -4900715126840183203L;


	public String member;
	public String traindate;
	
	private File image;
	private String imageFileName;
	
	


	/**
	 * 查询用户最新运动心率
	 */
	public void ShowHeartRate() {
		try {
			Member mem = (Member) request.getSession().getAttribute("member");//获取当前登录的用户
			final JSONObject obj = new JSONObject();
			PresentHeartRate phr = new PresentHeartRate();
			SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
			phr = service.loadPresentHeartRate(mem.getId(), sdf1.parse(traindate));
			if (null == phr.getId()) {
				obj.accumulate("success", false).accumulate("message", "查无资料");
			} else {
				obj.accumulate("success", true).accumulate("message", "OK")
				   .accumulate("heartRates", phr.getHeartRates())
				;
			}
			response(obj);
		} catch (Exception e) {
			log.error("error", e);
			response(e);
		}
	
	}
	
	
	
	/**
	 * 我的足迹
	 */
	@SuppressWarnings({ "unchecked", "rawtypes", "unused" })
	public String mySign() {
		//测试数据，默认登录
		Member member = (Member) service.load(Member.class, Long.parseLong("9388"));
		
//		Member member = (Member) request.getSession().getAttribute("member");
		if(member == null){
//			member = new  loginCommons().setMember(request, service);
			request.getSession().setAttribute("member", member);
		}
		
		
		try {
			final JSONObject obj = new JSONObject();
			PresentHeartRate phr = new PresentHeartRate();
			SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			
			
			int currentPage = 1;//默认显示第一页
			String currentP = request.getParameter("currentPage");
			if(!"null".equals(currentP) && null != currentP && "".equals(currentP)){
				currentPage = Integer.parseInt(currentP);
			}
			
			int pageSize = 20;//默认页面大小20条数据
			String pageS = request.getParameter("pageSize");
			if(!"null".equals(pageS) && null != pageS && "".equals(pageS)){
				pageSize = Integer.parseInt(pageS);
			}
			
			
			//暂时禁用
			//Member member = (Member) request.getSession().getAttribute("member");
			
			//打卡心率
			Map<String, Object> map = service.loadPresentHeartRate(member.getId(),currentPage,pageSize, "sport");
			List<Map<String, Object>> pList = (List<Map<String, Object>>) map.get("phrList");
			pageInfo = (PageInfo) map.get("pageInfo");
			if (pList.size()<0) {
				obj.accumulate("success", false).accumulate("message", "查无资料");
			} else {
				JSONArray cArray = new JSONArray();
				JSONObject objx = null;
				for (Map<String, Object> map2 : pList) {
					 objx = new JSONObject();
					 
					double heartRate =  Double.parseDouble(map2.get("heartRates").toString());
					double high =  Double.parseDouble(map2.get("high").toString());
					double low = Double.parseDouble(map2.get("low").toString());
					
					String xx="";
					if(heartRate< low){
						xx="C";
					}else if(heartRate>=low && heartRate <high){
						xx="B";
					}else {
						xx="A";
					}
					map2.put("xx", xx);
					
					String trainDate = sdf1.format((Date)map2.get("train_date"));
					//2017-05-20
					String year = trainDate.substring(0,4);
					String month = trainDate.substring(5,7);
					String day = trainDate.substring(8,10);
					
					map2.put("year", year);
					map2.put("month", month);
					map2.put("day", day);
					for (Iterator it = map2.keySet().iterator(); it.hasNext();) {
						Object key = it.next();
						 if ("train_date".equals(key)) {
							 Date dd = (Date) map2.get(key);
							 objx.accumulate(String.valueOf(key), sdf1.format(dd));
						 }else {
							 objx.accumulate(String.valueOf(key), String.valueOf(map2.get(key)));
						}
						 
					}
					cArray.add(objx);
				}
				
				obj.accumulate("success", true)
				.accumulate("message", "OK")
				.accumulate("phrList", cArray)
				.accumulate("pageInfo", getJsonForPageInfo());
				
			}
			
			//response(obj);
			request.setAttribute("myRate", obj);
		} catch (Exception e) {
			log.error("error", e);
			response(e);
		}
		
		return "mySign";

	}
	
	
	

	
	/**
	 * 加载个人信息
	 */
	public String loadMe() {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			// 获取session中的member
			Member member = (Member) request.getSession().getAttribute("member");
			if (member == null) {
				loginCommons.thirdType = "W";
				new loginCommons().setMember(request, service);
				member = (Member) request.getSession().getAttribute("member");
			} else {
				member = (Member) service.load(Member.class, member.getId());
				request.getSession().setAttribute("member", member);
			}
			
			String sqlx = "select image,nick,mobilephone,sex,birthday from tb_member where id = ?";
			Object[] objx = { member.getId() };
			Map<String, Object> mapx = (Map<String, Object>) DataBaseConnection.getList(sqlx, objx).get(0);

			// 查询height,heartRate
			String sql = "select id,ifnull(heartRate,0) heartRate,height from tb_plan_record where partake = ? order by done_date desc limit 1";
			Object[] obj1 = { member.getId() };
			Map<String, Object> map = (Map<String, Object>) DataBaseConnection.getOne(sql, obj1);

			// 查询bmiLow--bmiHigh heart
			Setting setting = new Setting();
			setting = service.loadSetting(member.getId());

			JSONObject obj = new JSONObject();
			obj.accumulate("success", true).accumulate("id", member.getId()).accumulate("image", mapx.get("image"))
					.accumulate("nick", mapx.get("nick")).accumulate("mobilephone", mapx.get("mobilephone"))
					.accumulate("birthday",  mapx.get("birthday") ==null ? null : sdf.format(mapx.get("birthday")))
					.accumulate("planId", map == null ? null : map.get("id"))
					.accumulate("height", map == null ? null : map.get("height"))
					.accumulate("heartRate", map == null ? null : map.get("heartRate"))
					.accumulate("setId", setting.getId()).accumulate("bmiLow", setting.getBmiLow())
					.accumulate("bmiHigh", setting.getBmiHigh());
			response(obj);
			request.setAttribute("member", obj);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "loadMe";
	}
	

	
	
	
	/**
	 * 加载个人信息
	 */
	public JSONObject loadMex() {
		JSONObject obj = new JSONObject();
		try {
			//测试数据，默认登录
//			Member member = (Member) service.load(Member.class, Long.parseLong("9562"));
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			// 获取session中的member
//			Member member = (Member) request.getSession().getAttribute("member");
			Member member = (Member) request.getServletContext().getAttribute("member");
			request.getServletContext().setAttribute("memberx", member);
			
			String sqlx = "select image,nick,mobilephone,sex,birthday from tb_member where id = ?";
			Object[] objx = { member.getId() };
			Map<String, Object> mapx = (Map<String, Object>) DataBaseConnection.getList(sqlx, objx).get(0);

			// 查询height,heartRate
			String sql = "select id,ifnull(heartRate,0) heartRate,height from tb_plan_record where partake = ? order by done_date desc limit 1";
			Object[] obj1 = { member.getId() };
			Map<String, Object> map = (Map<String, Object>) DataBaseConnection.getOne(sql, obj1);

			// 查询bmiLow--bmiHigh heart
			Setting setting = new Setting();
			setting = service.loadSetting(member.getId());

			
			obj.accumulate("success", true).accumulate("id", member.getId()).accumulate("image", mapx.get("image"))
					.accumulate("nick", mapx.get("nick")).accumulate("mobilephone", mapx.get("mobilephone"))
					.accumulate("birthday",  mapx.get("birthday") ==null ? null : sdf.format(mapx.get("birthday")))
					.accumulate("planId", map == null ? null : map.get("id"))
					.accumulate("height", map == null ? null : map.get("height"))
					.accumulate("heartRate", map == null ? null : map.get("heartRate"))
					.accumulate("setId", setting.getId()).accumulate("bmiLow", setting.getBmiLow())
					.accumulate("bmiHigh", setting.getBmiHigh());
			response(obj);
			request.setAttribute("member", obj);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return obj;
	}
	
	
	
	
	
	
	
	
	
	/**
	 * 保存个人信息
	 */
	public String saveMe() {
		//测试数据
		JSONObject jsonObject = new JSONObject();
		jsonObject.accumulate("nick", "dou").accumulate("birthday", "2017-10-25")
				  .accumulate("sex", "M")
				  .accumulate("bmi", 213)
				  .accumulate("setId", 6)
				  ;
		JSONArray jsonArray = new JSONArray();
		jsonArray.add(jsonObject);
		String jsons = jsonArray.toString();
		try {
			final JSONArray arr = JSONArray.fromObject(URLDecoder.decode(jsons, "UTF-8"));
			log.error("保存会员设置JSON串：" + arr.toString());
			final JSONObject obj = arr.getJSONObject(0);

			// 获取session中的member
			Member member = (Member) request.getSession().getAttribute("member");

			String sqlx = "update tb_member set image=?,nick=?,sex=?,birthday=? where id = ?";

//			String mobilephone = (String) obj.get("mobilephone");
			String nick = obj.get("nick").toString();
			String birthday = (String) obj.get("birthday");
//			mobilephone = !"0".equals(mobilephone) ? mobilephone : "";
			nick = !"0".equals(nick) ? nick : "";
			birthday = !"0".equals(birthday) ? birthday : "";

			String sex = obj.getString("sex");
			if ("M".equals(sex)) {
				sex = "M";
			} else {
				sex = "F";
			}
			String imageName = "";
			if (image != null) {
				imageName = saveFile("picture", image, imageFileName, null);
			}

			Object[] objx = { imageName, nick,sex, birthday, member.getId() };
			int x = DataBaseConnection.updateData(sqlx, objx);
			if (x > 0) {
				System.out.println("用户表更新成功");
			}

			// 查询height,heartRate
			String sql1 = "update  tb_plan_record set heartRate=?,height=?  where id=?";
			Object[] obj1 = { obj.get("heartRate"), obj.get("height"), obj.get("planId") };
			int r = DataBaseConnection.updateData(sql1, obj1);
			if (r > 0) {
				System.out.println("训练表更新成功");
			}

			//
			String bmi = (String) obj.get("bmi");
			String bmiLo = bmi.substring(0, bmi.indexOf("/"));
			int bmiLow = Integer.valueOf(bmiLo);
			String bmiHig = bmi.substring(bmi.indexOf("/") + 1);
			int bmiHigh = Integer.valueOf(bmiHig);

			long setId = Long.parseLong(obj.get("setId").toString());

			String sql2 = "update tb_member_setting set bmiLow=?,bmiHigh=? where id =?";

			Setting setting = new Setting();
			setting.setBmiLow(bmiLow);
			setting.setBmiHigh(bmiHigh);
			setting.setId(setId);
			Object[] obj2 = { bmiLow, bmiHigh, setId };

			int set = DataBaseConnection.updateData(sql2, obj2);

			if (set > 0) {
				System.out.println("setting 保存成功");
			}

			// 操作成功
			JSONObject done = new JSONObject();
			done.accumulate("success", "OK").accumulate("message", "保存信息成功！");
			System.err.println(done.toString());
			loadMe();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "loadMe";
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
						"select count(*) from tb_member where mobilephone = ? and mobile_valid = '1' and nick <> ''",
						mobile);
				if (count > 0) {
					sendSmsValidate(mobile, "mobile.validate.open");
				} else {
					throw new LogicException("当前手机号码未绑定至用户！");
				}
			} else if (flag != null && "1".equals(flag)) {
				// 手机注册获取验证码
				sendSmsValidate(mobile, "mobile.validate.open");
			} else if (flag != null && "2".equals(flag)) {
				// 修改手机号获取验证码 by dou 2017-09-29
				sendSmsValidate(mobile, "mobile.validate.open");
			} else {
				// 原3.5逻辑
				final Long count = service.queryForLong(
						"select count(*) from tb_member where mobilephone = ? and mobile_valid = '1' and id <> ?",
						mobile, getMobileUser().getId());
				if (count > 0)
					throw new LogicException("当前手机号码已经被其它用户使用，不得重复使用！");
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
				// 4.0   添加1为手机注册，0为找回密码 2为修改手机号
				if ("0".equals(flag) || "1".equals(flag)) {
					Member me = (Member) request.getServletContext().getAttribute("memberx");
					me.setMobilephone(mobile);
					me.setMobileValid("1");
					service.saveOrUpdate(me);
					ret.accumulate("success", true).accumulate("message", "添加、绑定手机号成功！");
					response(ret);
				} else if ("2".equals(flag)) {
					Member m = (Member) service.load(Member.class, getMobileUser().getId());
					m.setMobilephone(request.getParameter("mobile"));
					m.setMobileValid("1");
					service.saveOrUpdate(m);
					ret.accumulate("success", true).accumulate("message", "OK");
					response(ret);
				} else {
					Member m = (Member) service.load(Member.class, getMobileUser().getId());
					m.setMobilephone(request.getParameter("mobile"));
					m.setMobileValid("1");
					service.saveOrUpdate(m);
					ret.accumulate("success", true).accumulate("message", "OK");
					response(ret);
				}
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
	 * 测试环境登录
	 */
	public void login() {
		if (id == null) {
			id = Long.valueOf("9388");
		}
		session.setAttribute("member", service.load(Member.class, id));
		response("{'id':" + id + "}");
	}
	
	
	
	
	
	
	/**
	 * getter and setter
	 */
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
	

}
