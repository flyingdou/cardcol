package sport.action;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.cardcol.web.service.IClub45Service;
import com.cardcol.web.service.impl.Club45ServiceImpl;
import com.databaseConnection.DataBaseConnection;
import com.freegym.web.basic.Member;
import com.freegym.web.mobile.BasicJsonAction;
import com.sanmen.web.core.utils.StringUtils;

import common.util.HttpRequestUtils;
import net.sf.json.JSONObject;
import sport.entity.Userof;
import sport.service.UserinfoService;
import sport.util.SDK_WX;
import sport.util.loginCommons;

@Namespace("")
@InterceptorRefs({ @InterceptorRef("customStack") })
@Results({ @Result(name = "init", location = "/sport/init.jsp"),
		@Result(name = "loadMe", location = "/sport/mine_xx.jsp")

})
public class SLoginWXManageAction extends BasicJsonAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8644944109732609935L;

	/**
	 * 用户名，密码，昵称，下载链接
	 */
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
			thirdId = (String) session.getAttribute("openId");
		}
		if (!StringUtils.isEmpty(thirdType) && !StringUtils.isEmpty(thirdId)) {
			String wx = "";
			member = service.thirdLoginCheck(thirdType, thirdId, lng, lat, wx);
			if (member != null) {
				if (member.getMobilephone() != null) {
				}

				String name = session.getAttribute("nickName").toString();
				String role = "M";
				String nick = session.getAttribute("nickName").toString();
				String province = session.getAttribute("province").toString();
				String city = session.getAttribute("city").toString();
				Double latitude = Double.parseDouble(session.getAttribute("latitude").toString());
				Double longitude = Double.parseDouble(session.getAttribute("longitude").toString());
				member.setName(name);
				member.setRole("M");
				member.setNick(nick);
				member.setProvince(province);
				member.setCity(city);
				member.setLatitude(latitude);
				member.setLongitude(longitude);

				String saveM = "update tb_member set nick = ?,name = ?,role = ?,province = ?,city = ? where id = ?";
				Object[] objM = { nick, name, role, province, city, member.getId() };
				DataBaseConnection.updateData(saveM, objM);
			}
		} else {

			loginCommons.username = "".equals(username) ? "15623902901" : username;
			loginCommons.password = "".equals(password) ? "c33367701511b4f6020ec61ded352059" : password;
			member = service.login(loginCommons.username, loginCommons.password);
		}
		session.setAttribute("status", 2);
		session.setAttribute("member", member);
		request.getServletContext().setAttribute("member", member);
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
				session.setAttribute("member", member);
				request.getServletContext().setAttribute("member", member);
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

	/**
	 * set latitude && longitude
	 */
	public String getLocation() {
		String longitude = request.getParameter("longitude");
		String latitude = request.getParameter("latitude");
		request.getSession().setAttribute("longitude", longitude);
		request.getSession().setAttribute("latitude", latitude);
		// 调用登录方法
		loginCommons.thirdType = "W";
		execute();
		JSONObject mem = HttpRequestUtils.httpGetx("http://1829840kl0.iask.in/cardcolv4/smemberwx!loadMex.asp");
		request.setAttribute("member", mem);
		return "loadMe";
	}

	/**
	 * init
	 */
	public String init() {
		return "init";
	}

	/**
	 * get openId
	 */
	public String getOpenId() {
		HttpServletRequest request = ServletActionContext.getRequest();
		session = request.getSession();
		String openId = null;
		if (null == loginCommons.code) {
			String code = request.getParameter("code");// 我们要的code
			loginCommons.code = code;
			try {
				openId = getTheCode(session, code, openId);
				JSONObject result = new JSONObject();
				result.accumulate("SUCCESS", "OK");
				JSONObject openid = new JSONObject();
				openid.accumulate("openId", openId);
				// 将请求到的openId存到session中
				request.getSession().setAttribute("openId", openId);
			} catch (Exception e) {
				e.printStackTrace();
			}

			// 调用登录方法
			loginCommons.thirdType = "W";
			execute();
			JSONObject mem = HttpRequestUtils.httpGetx("http://1829840kl0.iask.in/cardcolv4/smemberwx!loadMex.asp");
			request.setAttribute("member", mem);
			return "loadMe";
		}
		return "";
	}

	private String getTheCode(HttpSession session, String code, String openId) {
		if (code != null) {
			openId = getOpenid(code);// 调用根据用户的code得到信息
		}
		// 获取基础刷新的接口访问凭证
		SDK_WX sdkt = new SDK_WX(request);

		// AccessToken accessToken = AccessTokenUtil.queryAccessToken();
		Userof userinfo = UserinfoService.getUserInfo(sdkt.getACCESS_TOKEN(), openId);

		String nick = getChinaStr(userinfo.getNickname());
		String province = getChinaStr(userinfo.getProvince());
		String city = getChinaStr(userinfo.getCity());
		String country = getChinaStr(userinfo.getCountry());
		System.out.println("昵称：" + nick + ",province: " + province + ",city:" + city + ",country:" + country);

		session.setAttribute("province", province);
		session.setAttribute("city", city);
		session.setAttribute("country", country);
		session.setAttribute("nickName", nick);

		if (userinfo.getOpenId() == null) {
			request.getSession().setAttribute("openId", userinfo.getOpenId());
		}
		return openId;
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
	 * getter and setter
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
