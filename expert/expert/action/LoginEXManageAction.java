package expert.action;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;

import com.databaseConnection.DataBaseConnection;
import com.freegym.web.basic.Member;
import com.freegym.web.basic.MemberTicket;
import com.freegym.web.mobile.BasicJsonAction;
import com.freegym.web.system.Ticket;
import com.freegym.web.utils.EasyUtils;
import com.freegym.web.wechatPay.utils.MD5Util;

import common.util.HttpRequestUtils;
import ecartoon.wx.util.SaveImgByUrl;
import expert.constant.Constants;
import expert.util.AES;
import expert.util.commentsUtil;
import net.sf.json.JSONObject;

/**
 * 
 * @author dou
 * 用户登录注册
 */
@Namespace("")
@InterceptorRefs({ @InterceptorRef("customStack") })
public class LoginEXManageAction extends BasicJsonAction {

	private static final long serialVersionUID = -3272142418606255231L;
	
	
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
			
	
	public void wechatLogin () {
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
			String requestUrl = "https://api.weixin.qq.com/sns/jscode2session?appid=" + Constants.APPID
					+ "&secret=" + Constants.APP_SECERT
					+ "&js_code=" + code
					+ "&grant_type=authorization_code";
			// 请求返回的数据
			JSONObject resJsonObject =  HttpRequestUtils.httpGet(requestUrl);
			
			// 从加密信息中解密出来
			String douStr = AES.wxDecrypt(param.getString("encryptedData"), resJsonObject.getString("session_key"), param.getString("iv"));
			String unionId = EasyUtils.getTargetFromAES(douStr, "unionId");
			
			
			// 前台传递过来的数据
			JSONObject douInfo = JSONObject.fromObject(param.getString("userInfo"));
			// 获取用户的unionId
			String sqlx = "select * from tb_member where wechatID = ?";
			Object[] objx = {unionId};
			List<Map<String, Object>> memberList =  DataBaseConnection.getList(sqlx, objx);
			
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

			}  else {
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
					member.setSex(douInfo.getString("gender").equals("1") ? "M": "F");
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
			ret.accumulate("success", true)
			   .accumulate("message", "OK")
			   .accumulate("key", member.getId())
			   .accumulate("openid", resJsonObject.get("openid"))
			   .accumulate("session_key", resJsonObject.get("session_key"))
			   ;
			
		} catch (Exception e) {
		  // 程序异常时返回的数据
          ret.accumulate("success", false).accumulate("message", e.toString());
          e.printStackTrace();
		}
		
		// 将数据返回
		response(ret);
	}

}
