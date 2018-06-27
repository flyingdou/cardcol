package com.cardcol.web.member.action;

import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;

import com.freegym.web.basic.Member;
import com.freegym.web.mobile.BasicJsonAction;
import com.sanmen.web.core.bean.BaseMember;
import com.sanmen.web.core.common.LogicException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.domain.Userinfos;
import com.taobao.api.request.OpenimUsersAddRequest;
import com.taobao.api.response.OpenimUsersAddResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Namespace("")
@InterceptorRefs({ @InterceptorRef("customStack") })
public class MRegister45ManageAction extends BasicJsonAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8133714484592031238L;

	@SuppressWarnings("unused")
	@Override
	public String save() {
		// jsons
		// ="[{\"mobile\":15623902903,\"password\":\"e10adc3949ba59abbe56e057f20f883e\",\"lng\":\"\",\"lat\":\"\"}]";
		final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			final JSONArray objs = JSONArray.fromObject(URLDecoder.decode(jsons, "UTF-8"));
			final JSONObject obj = objs.getJSONObject(0);
			final String nick = obj.getString("mobile");
			Member m = new Member();
			final String mobilephone = obj.getString("mobile");
			if (service.findObjectBySql("from Member m where m.mobilephone = ? ", mobilephone).size() > 0)
				throw new LogicException("当前注册的会员的手机已经存在，请重新输入后再注册！");
			m.setMobilephone(mobilephone);
			m.setMobileValid(MEMBER_VALIDATE_SUCCESS);
			m.setNick(nick);
			m.setName(nick);
			m.setRole("M");
			m.setPassword(obj.getString("password"));
			if (obj.containsKey("lng") && obj.containsKey("lat")) {
				m.setLongitude(obj.getDouble("lng"));
				m.setLatitude(obj.getDouble("lat"));
			}
			m = service.register(m);

			String url = "http://gw.api.taobao.com/router/rest";
			TaobaoClient client = new DefaultTaobaoClient(url, "23709320", "c5ab900afa162f0ef7dbe2f635b61ad2");
			OpenimUsersAddRequest req = new OpenimUsersAddRequest();

			Userinfos user = new Userinfos();
			user.setNick(m.getNick());
			user.setStatus((long) 0);
			user.setIconUrl(
					"http://" + request.getServerName() + ":" + request.getServerPort() + "/picture/" + m.getImage());
			user.setEmail(m.getEmail());
			user.setMobile(m.getMobilephone());
			user.setTaobaoid(m.getId().toString());
			user.setUserid(m.getId().toString());
			user.setPassword(m.getId().toString());
			user.setRemark("demo");
			user.setExtra("{}");
			user.setCareer("demo");
			user.setGmtModified("demo");
			user.setVip("{}");
			user.setAddress(m.getAddress());
			user.setName(m.getName());
			user.setAge(m.getBirthday() == null ? 0
					: (new Date().getTime() - m.getBirthday().getTime()) / (1000 * 60 * 60 * 24));
			user.setGender(
					"M".equals(m.getSex()) || "F".equals(m.getSex()) ? m.getSex() : "男".equals(m.getSex()) ? "M" : "F");
			user.setWechat(m.getWechatID());
			user.setQq(m.getQqId());
			user.setWeibo(m.getSinaId());
			List<Userinfos> ulist = new ArrayList<Userinfos>();
			ulist.add(user);
			req.setUserinfos(ulist);

			OpenimUsersAddResponse response = client.execute(req);
			final JSONObject ret = new JSONObject();
			ret.accumulate("success", true).accumulate("id", m.getId());
			if (response.getUidSucc() != null) {
				ret.accumulate("taobaoid", m.getId()).accumulate("taobaopwd", m.getId());
			} else {
				ret.accumulate("taobaoid", "").accumulate("taobaopwd", "");
			}
			addMobileUser(m);
			ret.accumulate("name", m.getName()).accumulate("nick", m.getNick()).accumulate("image", m.getImage())
					.accumulate("type", m.getRole()).accumulate("grade", m.getGrade())
					.accumulate("mobilephone", m.getMobilephone()).accumulate("mobileValid", m.getMobileValid())
					.accumulate("sex", m.getSex()).accumulate("message", m.getToKen());
			response(ret);
		} catch (Exception e) {
			log.error("error", e);
			response(e);
		}
		return null;
	}

	/**
	 * 获取手机验证码
	 */
	public void getMobileCode() {
		try {
			final String mobile = request.getParameter("mobile");
			// 新加入找回密码获取验证码
			final String flag = request.getParameter("flag");
			final String type = request.getParameter("type");
			if (flag != null && "0".equals(flag)) {
				final Long count = service.queryForLong(
						"select count(*) from tb_member where mobilephone = ? and mobile_valid = '1' and nick <> ''",
						mobile);
				if (count > 0) {
					sendSmsValidate(mobile, type);
				} else {
					throw new LogicException("当前手机号码未绑定至用户！");
				}
			} else if (flag != null && "1".equals(flag)) {
				sendSmsValidate(mobile, type);
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
	 * 2017-7-11 黄文俊 新增手机验证的时候判断该手机号是否被验证过了
	 */
	public void validMobile() {
		try {
			final JSONObject ret = new JSONObject();
			final String mobile = request.getParameter("mobile");
			final String code = request.getParameter("code");
			BaseMember baseMember = getMobileUser();
			
			if (baseMember != null) {
				// 代表用户已经登陆 要执行的是手机验证的操作
				final Long MobileValidated = service.queryForLong("select count(*) from tb_member where mobilephone = ? and mobile_valid = '1'", mobile);
				
				if (MobileValidated > 0) {
					ret.accumulate("success", false).accumulate("message", "该手机号码已经被绑定过了！");
					response(ret);
				} else {
					if (isRightful(mobile, code)) {
						Member m = (Member) service.load(Member.class, getMobileUser().getId());
						m.setMobilephone(request.getParameter("mobile"));
						m.setMobileValid("1");
						service.saveOrUpdate(m);
						
						ret.accumulate("success", true).accumulate("message", "OK");
						response(ret);
					} else {
						ret.accumulate("success", false).accumulate("message", "您的手机验证码已经过期，请重新发送手机验证码！");
						response(ret);
					}
				}
			} else {
				// 代表用户未登陆 要执行 注册 或者 忘记密码的操作
				// 查询该手机号码是否存在  
				final Long isMemberExist = service.queryForLong("select count(*) from tb_member where mobilephone = ?", mobile);
				// 查询该手机号码是否被绑定
				final Long MobileValidated = service.queryForLong("select count(*) from tb_member where mobilephone = ? and mobile_valid = '1'", mobile);
				
				if (isMemberExist > 0) {
					// 则要执行 忘记密码操作 或者以有帐户的注册
					if (MobileValidated > 0) {
						// 手机号码可用
						if (isRightful(mobile, code)) {
							ret.accumulate("success", true).accumulate("message", "OK");
							response(ret);
						} else { 
							ret.accumulate("success", false).accumulate("message", "您的手机验证码已经过期，请重新发送手机验证码！");
							response(ret);
						}
					} else {
						// 手机号不可用 这种情况极少出现 因为一个手机号码一旦存在 则基本上绑定了
						ret.accumulate("success", false).accumulate("message", "手机号不可用！");
						response(ret);
					}
				} else {
					// 手机号码可用
					if (isRightful(mobile, code)) {
						ret.accumulate("success", true).accumulate("message", "OK");
						response(ret);
					} else { 
						ret.accumulate("success", false).accumulate("message", "您的手机验证码已经过期，请重新发送手机验证码！");
						response(ret);
					}
				}
			} 
		} catch (Exception e) {
			log.error("error", e);
			response(e);
		}
	
			
//		try {
//			final JSONObject ret = new JSONObject();
//			final String mobile = request.getParameter("mobile");
//			// 根据手机号码查询会员
//			final Long MobileValidated = service.queryForLong("select count(*) from tb_member where mobilephone = ? and mobile_valid = '1'", mobile);
//			// 新增逻辑 获取手机号码的时候要判断该手机号码是否被验证过了
//			if (MobileValidated > 0) {
//				// 要判断nick 是否为空串 如果为空 则代没有被 第三方帐户绑定
//				final Long thridBinded = service.queryForLong("select count(*) from tb_member where mobilephone = ? and mobile_valid = '1' and nick !=''", mobile);
//				
//				if (thridBinded > 0) {
//					// 如果不为空则代表被第三方应用绑定 
//					// 判断用户是否登陆 如果登陆 则执行的是验证操作 如果没有登陆 则执行的是注册忘记密码操作
//					ret.accumulate("success", true).accumulate("message", "OK");
//					response(ret);
//				} else {
//					// 如果不为空则代表被第三方应用绑定 
//					ret.accumulate("success", false).accumulate("message", "您的手机号码已经被使用过了，请更换手机号码！");
//					response(ret);
//				}
//			} else {
//				final String code = request.getParameter("code");
//				if (isRightful(mobile, code)) {
//					// 2017-7-10 黄文俊 修复重启app手机验证失效的问题
//					BaseMember baseMember = getMobileUser();
//					if (baseMember != null) {
//						// 如果不为空 则代表的 不是注册 注册的时候也调用了这个接口
//						Member m = (Member) service.load(Member.class, getMobileUser().getId());
//						m.setMobilephone(request.getParameter("mobile"));
//						m.setMobileValid("1");
//						service.saveOrUpdate(m);
//					}
//					
//					ret.accumulate("success", true).accumulate("message", "OK");
//					response(ret);
//				} else {
//					ret.accumulate("success", false).accumulate("message", "您的手机验证码已经过期，请重新发送手机验证码！");
//					response(ret);
//				}
//			}
//				
//		} catch (Exception e) {
//			log.error("error", e);
//			response(e);
//		}
	}

	/**
	 * 修改密码
	 */
	@SuppressWarnings("unchecked")
	public void modifyPassword() {
		try {
			final JSONObject ret = new JSONObject();
			final String flag = request.getParameter("flag");
			if (flag != null && "0".equals(flag)) {
				final String mobile = request.getParameter("mobile");
				final String password = request.getParameter("password");
				List<Member> list = (List<Member>) service.findObjectBySql("from Member m where m.mobilephone = ?", mobile);
				Member m = list.get(0);
				m.setPassword(password);
				service.saveOrUpdate(m);
				ret.accumulate("success", true).accumulate("message", "OK");
				response(ret);
			} else {
				final String oldPassword = request.getParameter("oldPassword");
				Member m = (Member) service.load(Member.class, getMobileUser().getId());
				if (m.getPassword().equalsIgnoreCase(oldPassword)) {
					final String password = request.getParameter("password");
					m.setPassword(password);
					service.saveOrUpdate(m);
					ret.accumulate("success", true).accumulate("message", "OK");
					response(ret);
				} else {
					ret.accumulate("success", false).accumulate("code", -1).accumulate("message", "您的原密码不正确,请重新输入!");
					response(ret);
				}
			}
		} catch (Exception e) {
			log.error("error", e);
			response(e);
		}
	}

}
