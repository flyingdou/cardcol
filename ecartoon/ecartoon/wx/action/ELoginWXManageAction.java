package ecartoon.wx.action;

import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;

import com.cardcol.web.service.IClub45Service;
import com.cardcol.web.service.impl.Club45ServiceImpl;
import com.cardcol.web.utils.FileUtil;
import com.databaseConnection.DataBaseConnection;
import com.freegym.web.basic.Member;
import com.freegym.web.mobile.BasicJsonAction;
import com.freegym.web.utils.EasyUtils;
import com.sanmen.web.core.utils.StringUtils;

import common.util.HttpRequestUtils;
import ecartoon.wx.util.SDK_WX;
import ecartoon.wx.util.SaveImgByUrl;
import ecartoon.wx.util.loginCommons;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import sport.entity.Userof;
import sport.service.UserinfoService;

@Namespace("")
@InterceptorRefs({ @InterceptorRef("customStack") })
public class ELoginWXManageAction extends BasicJsonAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8422925989755578414L;

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
	@SuppressWarnings("static-access")
	@Override
	public String execute() {
		Member member = new Member();
		HttpServletRequest request = ServletActionContext.getRequest();
		session = request.getSession();
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		IClub45Service service = new Club45ServiceImpl();

		thirdType = loginCommons.thirdType;
		if (!StringUtils.isEmpty(thirdType) && thirdType.equals("W")) {
			thirdId = UUID.randomUUID().toString().replace("-", "");
			nick = "wx" + (int) ((Math.random() * 9 + 1) * 100000);// wx加上6位随机数
		}
		if (!StringUtils.isEmpty(thirdType) && !StringUtils.isEmpty(thirdId)) {
			// member = service.thirdLoginCheck(thirdType, thirdId, lng, lat);
			String wx = "";
			member = service.thirdLoginCheck(thirdType, thirdId, lng, lat, wx);
			if (member != null) {
				String imgName = getFileName("8888888.png");
				if (imgName != null) {
					try {
						FileUtil.download(url, this.webPath + "picture/" + imgName);
					} catch (Exception e) {
						e.printStackTrace();
					}
					member.setImage(imgName);
					if (member.getProvince() == null)
						member.setProvince("北京市");
					if (member.getCity() == null)
						member.setCity("北京市");
					if (member.getCounty() == null)
						member.setCounty("东城区");
					member.setName(nick);
					member = (Member) service.saveOrUpdate(member);
				}
			}
		} else {

			loginCommons.username = "".equals(username) ? "15623902901" : username;
			loginCommons.password = "".equals(password) ? "c33367701511b4f6020ec61ded352059" : password;

			member = service.login(loginCommons.username, loginCommons.password);
		}
		session.setAttribute("status", 2);
		session.setAttribute("member", member);
		final JSONObject ret = new JSONObject();
		if (member == null) {
			if (!StringUtils.isEmpty(thirdType)) {
				// 第三方登录部分逻辑 第一次登录，则注册用户
				try {
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
					member.setName(nick);

					member = service.thirdRegister(member, "wx");
					session.setAttribute("member", member);
					ret.accumulate("success", true).accumulate("message", member.getToKen()).accumulate("count", "0")
							.accumulate("key", member.getId());
					response(ret);
				} catch (Exception e) {
					log.error("error", e);
				}
				// return execute();
				session.setAttribute("member", member);
			} else {
				ret.accumulate("success", false).accumulate("message", "您输入的账户及密码不正确，请重新输入！");
				response(ret);
				return null;
			}
		}
		if (member.getRole().equals("E")) {
			ret.accumulate("success", false).accumulate("message", "您不能从手机平台登录到卡库网！请确认！");
			response(ret);
			return null;
		}

		return null;
	}

	public void shareLogin() {
		try {
			// 我们要的code
			String code = request.getParameter("code");
			SDK_WX sdk = new SDK_WX();
			// 生成获取openid的链接
			StringBuilder getOpenIdUrl = new StringBuilder("https://api.weixin.qq.com/sns/oauth2/access_token");
			getOpenIdUrl.append("?appid=").append(sdk.getAPPID());
			getOpenIdUrl.append("&secret=").append(sdk.getAPPSERCRET());
			getOpenIdUrl.append("&code=").append(code);
			getOpenIdUrl.append("&grant_type=authorization_code");
			// 请求微信服务器
			JSONObject json = HttpRequestUtils.httpGet(getOpenIdUrl.toString());
			if (json.containsKey("openid")) {
				// 登录
				wechat4Login(json.getString("openid"), request);
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	public void shareLogin2() {
		try {
			// 我们要的code
			String code = request.getParameter("code");
			SDK_WX sdk = new SDK_WX();
			// 生成获取openid的链接
			StringBuilder getOpenIdUrl = new StringBuilder("https://api.weixin.qq.com/sns/oauth2/access_token");
			getOpenIdUrl.append("?appid=").append(sdk.getAPPID());
			getOpenIdUrl.append("&secret=").append(sdk.getAPPSERCRET());
			getOpenIdUrl.append("&code=").append(code);
			getOpenIdUrl.append("&grant_type=authorization_code");
			// 请求微信服务器
			JSONObject json = HttpRequestUtils.httpGet(getOpenIdUrl.toString());
			if (json.containsKey("openid")) {
				// 登录
				wechat4Login(json.getString("openid"), request);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	

	/**
	 * 微信公众号登录
	 */
	public void wechatLogin() {
		try {
			HttpServletRequest request = ServletActionContext.getRequest();
			session = request.getSession();
			String openId = null;
			Member member = new Member();
			// 微信发来的code
			String code = request.getParameter("code");
			String coded = (String) session.getAttribute("coded");
			// 防止微信多次发起请求，导致程序报错
			if (!code.equals(coded)) {
				session.setAttribute("coded", code);
				// 获取微信用户信息
				Userof user = getWechatUserInfo(session, code, openId);
				// 查询记录E卡通微信公众号的openId表 , 判断当前用户是否有记录
				String sqlx = "select * from tb_openid_member where openid = ? and origin = 'EW'";
				Object[] objx = { user.getOpenId() };

				List<Map<String, Object>> listx = DataBaseConnection.getList(sqlx, objx);
				if (listx.size() < 1) {
					// 如果没有记录,创建一条用户数据和记录E卡通微信公众号的openId数据
					member.setName(user.getNickname());
					member.setNick(user.getNickname());
					member.setImage(user.getHeadImgUrl());
					member.setRole("M");
					member.setSex(user.getSex() == 1 ? "M" : "F");
					member.setCity(user.getCity());
					member.setProvince(user.getProvince());
					member.setThirdType("W");
					member.setWechatID(user.getOpenId());
					member.setLoginTime(new Date());
					member.setRegDate(new Date());
					member = (Member) service.saveOrUpdate(member);

					String addOpenId = "insert into tb_openid_member(id,member,openid,origin,login_date) values(null,?,?,?,?)";
					Object[] args = new Object[] { member.getId(), user.getOpenId(), "EW", new Date() };
					DataBaseConnection.updateData(addOpenId, args);
					session.setAttribute("member", member);
				} else {
					// 如果有记录,直接查询出来并返回
					String sqlq = "select m.* from tb_member m inner join tb_openid_member mo on m.id = mo.member where mo.openid = '"
							+ user.getOpenId() + "'";
					member = (Member) DataBaseConnection.load(sqlq, Member.class, 0);
					String sqlddd = "update tb_openid_member set login_date =? where id = ?";
					Object[] objddd = { new Date(), listx.get(0).get("id") };
					DataBaseConnection.updateData(sqlddd, objddd);
					session.setAttribute("member", member);
				}

				// 重定向回requestOpenid2.jsp页面
				session.setAttribute("status", 1);
				response.sendRedirect("ecartoon-weixin/requestOpenId2.jsp");

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	/**
	 * 微信公众号登录
	 */
	public void wechat2Login() {
		try {
			Thread.sleep(1000);
			HttpServletRequest request = ServletActionContext.getRequest();
			session = request.getSession();
			String openId = null;
			String sqlOpen = "select openid from tb_openid where id = 2";
			Map<String, Object> mapOpen = DataBaseConnection.getOne(sqlOpen, null);
			openId = String.valueOf(mapOpen.get("openid"));
			Member member = new Member();
			wechat4Login(openId, request);
			session.setAttribute("member", member);
			// 重定向回requestOpenId2.jsp页面
			session.setAttribute("status", 1);
			response.sendRedirect("ecartoon-weixin/requestOpenId2.jsp");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void wechat4Login(String openId, HttpServletRequest request) {
		Member member = new Member();
		try {
			session = request.getSession();

			// 获取微信用户信息
			Userof user = getWechatUserInfo(session, openId);
			if (user.getSubscribe() == 1 ){
				// 查询记录E卡通微信公众号的openId表 , 判断当前用户是否有记录
				String sqlx = "select * from tb_member where wechatID = ? ";
				Object[] objx = { user.getUnionId() };
				
				List<Map<String, Object>> listx = DataBaseConnection.getList(sqlx, objx);
				if (listx.size() < 1) {
					String path = request.getSession().getServletContext().getRealPath("/picture");
					String fileName = EasyUtils.getRandomName() + ".jpg";
					SaveImgByUrl.download(user.getHeadImgUrl(), fileName, path);
					// 如果没有记录,创建一条用户数据
					String  doux =  EasyUtils.cutStringEmoji(user.getNickname());
					member.setName(doux);
					member.setNick(doux);
					member.setImage(fileName);
					member.setRole("M");
					member.setSex(user.getSex() == 1 ? "M" : "F");
					member.setCity(user.getCity());
					member.setProvince(user.getProvince());
					member.setThirdType("W");
					member.setWechatID(user.getUnionId());
					member.setLoginTime(new Date());
					member.setRegDate(new Date());
					member.setRegisterType("t");
					member = (Member) service.saveOrUpdate(member);
					
				} else {
					// 如果有记录,直接查询出来并返回
					String sqlq = "select * from tb_member  where wechatID =  '" + user.getUnionId() + "'";
					member = (Member) DataBaseConnection.load(sqlq, Member.class, 0);
					member.setLoginTime(new Date());
					member = (Member) service.saveOrUpdate(member);
				}
				session.setAttribute("openId", user.getOpenId());
				session.setAttribute("member", member);
				session.setAttribute("status", 1);
				response.sendRedirect("ecartoon-weixin/requestOpenId2.jsp");
			} else {
				session.setAttribute("openId", user.getOpenId());
				response.sendRedirect("ecartoon-weixin/followQRCode.jsp");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void getAccessToken() {
		JSONObject ret = new JSONObject();
		SDK_WX sdk = new SDK_WX(request);
		String accessToken = sdk.getACCESS_TOKEN();
		if (!StringUtils.isEmpty(accessToken)) {
			ret.accumulate("success", true).accumulate("message", "OK").accumulate("accessToken", accessToken);
		} else {
			ret.accumulate("success", false).accumulate("message", "获取accessToken失败，请查看当前ip是否在IP白名单中");
		}
		response(ret.toString());
	}

	public Member setMember(HttpServletRequest request, IClub45Service service, String openId) {
		Member member = null;
		try {
			request.getSession().setAttribute("openId", openId);

			// 查询记录E卡通微信公众号的openId表 , 判断当前用户是否有记录
			String sqlx = "select * from tb_openid_member where openid = ? and origin = 'EW'";
			Object[] objx = { openId };

			List<Map<String, Object>> listx = DataBaseConnection.getList(sqlx, objx);
			if (listx.size() < 1) {
				// 如果没有记录,创建一条用户数据和记录E卡通微信公众号的openId数据
				member = new Member();
				member.setName("wx" + Math.round(Math.random() * 1000000));
				member.setThirdType("W");
				member.setRole("M");
				member.setSex("M");
				member.setImage("users.png");
				member = (Member) service.saveOrUpdate(member);

				String addOpenId = "insert into tb_openid_member(id,member,openid,origin,login_date) values(null,?,?,?,?)";
				Object[] args = new Object[] { member.getId(), openId, "EW", new Date() };
				DataBaseConnection.updateData(addOpenId, args);

				request.getSession().setAttribute("member", member);
			} else {
				// 如果有记录,直接查询出来并返回
				String sqlq = "select m.* from tb_member m inner join tb_openid_member mo on m.id = mo.member where mo.openid = '"
						+ openId + "'";
				member = (Member) DataBaseConnection.load(sqlq, Member.class, 0);
				String sqlddd = "update tb_openid_member set login_date =? where id = ?";
				Object[] objddd = { new Date(), listx.get(0).get("id") };
				DataBaseConnection.updateData(sqlddd, objddd);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return member;
	}

	private Userof getWechatUserInfo(HttpSession session, String code, String openId) {
		if (code != null) {
			openId = getOpenid(code);
		}
		// 获取基础刷新的接口访问凭证
		SDK_WX sdk = new SDK_WX(request);
		Userof userinfo = UserinfoService.getUserInfo(sdk.getACCESS_TOKEN(), openId);
		userinfo.setCountry(getChinaStr(userinfo.getCountry()));
		userinfo.setProvince(getChinaStr(userinfo.getProvince()));
		userinfo.setCity(getChinaStr(userinfo.getCity()));
		userinfo.setNickname(getChinaStr(userinfo.getNickname()));
		return userinfo;
	}

	private Userof getWechatUserInfo(HttpSession session, String openId) {
		// 获取基础刷新的接口访问凭证
		SDK_WX sdk = new SDK_WX(request);
		Userof userinfo = UserinfoService.getUserInfo(sdk.getACCESS_TOKEN(), openId);
		if (userinfo.getSubscribe() == 1){
			userinfo.setCountry(getChinaStr(userinfo.getCountry()));
			userinfo.setProvince(getChinaStr(userinfo.getProvince()));
			userinfo.setCity(getChinaStr(userinfo.getCity()));
			userinfo.setNickname(getChinaStr(userinfo.getNickname()));
		}

		return userinfo;
	}

	/**
	 * 识别得到用户id必须的一个值
	 * 
	 * @param code
	 * @return
	 */
	// 根据用户的code得到用户OpenId
	public String getOpenid(String code) {
		Map<String, Object> result = UserinfoService.oauth2GetOpenid(code);
		String OpenId = (String) result.get("Openid");// 得到用户id
		return OpenId;
	}
	
	
	
	
	/**
	 * 获取E卡通适用门店信息
	 */
	public void findStore() {
		JSONObject ret = new JSONObject();
		try {
			//八通卡适用门店
			JSONArray clubList0 = getStoreArray("1");
			
			//八达卡适用门店
			JSONArray clubList1 = getStoreArray("2");
			
			//八畅卡适用门店
			JSONArray clubList2 = getStoreArray("3");
			
			//返回数据
		    ret.accumulate("success", true).accumulate("message", "OK")
		       .accumulate("clubList0", clubList0)
		       .accumulate("clubList1", clubList1)
		       .accumulate("clubList2", clubList2)
		       ;
		} catch (Exception e) {
			ret.accumulate("success", false).accumulate("message", e);
			e.printStackTrace();
		}
		response(ret);
		
	}
	
	/**
	 * 查询门店信息
	 * @param eid
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public JSONArray getStoreArray(String eid){
		pageInfo = service.queryStore4EWX("北京市", pageInfo, 0.0, 0.0, eid);
		List<Map<String, Object>> mapList = pageInfo.getItems();
		JSONObject jsonObject;
		JSONArray jsonArray = new JSONArray();
		for (Map<String, Object> map : mapList) {
			jsonObject = new JSONObject();
			jsonObject.accumulate("name", map.get("name"))
			          .accumulate("longitude", map.get("longitude"))
			          .accumulate("latitude", map.get("latitude"))
			          ;
			jsonArray.add(jsonObject);
		}
		return jsonArray;
		
	}
	
	
	/**
	 * 获取手机验证码
	 * @return
	 */
	public void getMobileCode () {
		JSONObject ret = new JSONObject();
		try {
			String mobilephone = request.getParameter("mobilephone");
			mobilephone = mobilephone.trim();
			String dou = sendSmsValidate(mobilephone,"mobile.validate.open","dou");
			ret = JSONObject.fromObject(dou);
		} catch (Exception e) {
			ret.accumulate("success", false).accumulate("message", e);
		}
		response(ret);
		
	}
	
	
	/**
	 * 注册新用户或加载已有用户
	 * @return
	 */
	
	public void registerOrLoad () {
		JSONObject ret = new JSONObject();
		Member member = new Member();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			// 获取并处理，前端请求的参数
			JSONObject object = JSONObject.fromObject(URLDecoder.decode(jsons, "UTF-8"));
			
			// 校验短信验证码
			if( !isRightful(object.getString("mobilephone"), object.getString("code")) ) {
				ret.accumulate("success", false).accumulate("message", "验证码错误");
				response(ret);
				return;
			} 
			
			// 查询当前手机号是否有绑定的用户
			String sqlx = "select * from tb_member where mobilephone = ? ";
			Object[] objx = {object.get("mobilephone")};
			List<Map<String, Object>> memberList = DataBaseConnection.getList(sqlx, objx);
			if ( memberList.size() > 0 ) {
				// 有绑定用户
				member = (Member) service.load(Member.class, Long.valueOf(String.valueOf(memberList.get(0).get("id"))));
				member.setPassword(EasyUtils.MD5(object.getString("password")));
				member.setLoginTime(new Date());
				member.setToKen(EasyUtils.MD5(sdf.format(new Date())));
				member = (Member) service.saveOrUpdate(member);
			} else {
				// 没有绑定用户，需注册新用户
				member.setMobilephone(object.getString("mobilephone"));
				member.setMobileValid("1");
				member.setPassword(EasyUtils.MD5(object.getString("password")));
				member.setName("H5用户" + Math.round(Math.random()*1000000));
				member.setRole("M");
				member.setRegDate(new Date());
				member.setLoginTime(new Date());
				member.setToKen(EasyUtils.MD5(sdf.format(new Date())));
				member = (Member) service.saveOrUpdate(member);
			}
			
			// 将member存到session中
			session.setAttribute("member", member);
			ret.accumulate("success", true).accumulate("message", "OK");
			
		} catch (Exception e) {
			ret.accumulate("success", false).accumulate("message", e);
			e.printStackTrace();
		}
		// 返回数据
		response(ret);
		
	}
	
	
	/**
	 * 获取商品价格
	 * @return
	 */
	
	public void getPrice() {
		JSONObject ret = new JSONObject();
		try {
			// 查询后台E卡通商品价格
			String sqlx = 
					  " select SUM((CASE id WHEN 1 THEN PROD_PRICE END )) price1, "
					+ " SUM((CASE id WHEN 2 THEN PROD_PRICE END )) price2, "
					+ " SUM((CASE id WHEN 3 THEN PROD_PRICE END )) price3, "
					+ " (select count(*) as count from tb_product_club_v45 where product = 1 ) as count1 , "
					+ " (select count(*) as count from tb_product_club_v45 where product = 2 ) as count2,  "
					+ " (select count(*) as count from tb_product_club_v45 where product = 3 ) as count3 "
					+ " from tb_product_v45 ";
			Map<String, Object> priceMap = DataBaseConnection.getOne(sqlx, null);
			ret.accumulate("success", true)
			   .accumulate("message", "Ok")
			   .accumulate("price1", priceMap.get("price1"))
			   .accumulate("price2", priceMap.get("price2"))
			   .accumulate("price3", priceMap.get("price3"))
			   .accumulate("count1", priceMap.get("count1"))
			   .accumulate("count2", priceMap.get("count2"))
			   .accumulate("count3", priceMap.get("count3"))
			   ;
		} catch (Exception e) {
			ret.accumulate("success", false).accumulate("message", e);
			e.printStackTrace();
		}		
		response(ret);
	}
	

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
