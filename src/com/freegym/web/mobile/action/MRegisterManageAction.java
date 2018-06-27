package com.freegym.web.mobile.action;

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
public class MRegisterManageAction extends BasicJsonAction {

	private static final long serialVersionUID = -4900715126840183203L;

	@SuppressWarnings("unused")
	@Override
	public String save() {
		final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			final JSONArray objs = JSONArray.fromObject(URLDecoder.decode(jsons, "UTF-8"));
			final JSONObject obj = objs.getJSONObject(0);

			final String registerType = obj.getString("registerType");
			// final String thirdType = obj.getString("thirdType");
			// 使用手机号注册,p是手机号码，u是用户名，t是第三方登陆
			final String nick = obj.getString("nick");
			Member m = new Member();
			m.setRegisterType(registerType);
			if ("p".equals(registerType)) {
				final String mobilephone = obj.getString("nick");
				if (service.findObjectBySql("from Member m where m.mobilephone = ? ", mobilephone).size() > 0) throw new LogicException("当前注册的会员的手机已经存在，请重新输入后再注册！");
				m.setMobilephone(mobilephone);
				m.setMobileValid(MEMBER_VALIDATE_SUCCESS);
			} else if ("u".equals(registerType)) {
				if (service.findObjectBySql("from Member m where m.nick = ? ", nick).size() > 0) throw new LogicException("当前注册的会员的账户已经存在，请重新输入后再注册！");
			}
			m.setRegisterType(registerType);
			m.setNick(nick);
			m.setName(nick);
			// m.setBirthday(sdf.parse(obj.getString("birthday")));
			/*
			 * if (obj.containsKey("province")){
			 * m.setProvince(obj.getString("province")); }else
			 * if(obj.containsKey("city")){ m.setCity(obj.getString("city"));
			 * }else if(obj.containsKey("county")){
			 * m.setCounty(obj.getString("county")); }
			 */
			m.setRole("M");
			m.setPassword(obj.getString("password"));
			m.setRate(0d);
			m.setCountSocure(0d);
			m.setIntegralCount(0);
			m.setCountEmp(0);
			m.setOrderCount(0);
			m.setLongitude(0d);
			m.setLatitude(0d);
			m.setRegDate(new Date());
			m = service.register(m);

			String url = "http://gw.api.taobao.com/router/rest";
			TaobaoClient client = new DefaultTaobaoClient(url, "23330566", "0c6b77f937d49f3a14ca98a6316d110e");
			OpenimUsersAddRequest req = new OpenimUsersAddRequest();

			Userinfos user = new Userinfos();
			user.setNick(m.getNick());
			user.setStatus((long) 0);
			user.setIconUrl("http://www.cardcol.com/picture/" + m.getImage());
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
			user.setAge(m.getBirthday() == null ? 0 : (new Date().getTime() - m.getBirthday().getTime()) / (1000 * 60 * 60 * 24));
			user.setGender("M".equals(m.getSex()) || "F".equals(m.getSex()) ? m.getSex() : "男".equals(m.getSex()) ? "M" : "F");
			user.setWechat(m.getWechatID());
			user.setQq(m.getQqId());
			user.setWeibo(m.getSinaId());
			List<Userinfos> ulist = new ArrayList<Userinfos>();
			ulist.add(user);
			req.setUserinfos(ulist);

			OpenimUsersAddResponse response = client.execute(req);
			final JSONObject ret = new JSONObject();
			ret.accumulate("success", true).accumulate("key", m.getId());
			if (response.getUidSucc() != null) {
				ret.accumulate("taobaoid", m.getId()).accumulate("taobaopwd", m.getId());
			} else {
				ret.accumulate("taobaoid", "").accumulate("taobaopwd", "");
			}

			response("{success: true, key: " + m.getId() + "}");
		} catch (Exception e) {
			log.error("error", e);
			response(e);
		}
		return null;
	}
}
