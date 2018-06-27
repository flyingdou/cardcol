package com.freegym.web.mobile;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.aliyn.api.geteway.util.SingleSendSms;
import com.aliyun.api.gateway.demo.enums.Constantsms;
import com.freegym.web.basic.Member;
import com.freegym.web.common.Validate;
import com.freegym.web.course.BaseAppraise;
import com.freegym.web.utils.SessionConstant;
import com.freegym.web.utils.SmsUtils;
import com.sanmen.web.core.BaseJsonAction;
import com.sanmen.web.core.bean.BaseMember;
import com.sanmen.web.core.component.UserMessageResource;
import com.sanmen.web.core.listeners.PlatformUser;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public abstract class AbstractJsonAction extends BaseJsonAction implements SessionConstant,Constantsms {

	private static final long serialVersionUID = -2993219791016482836L;

	protected static final SimpleDateFormat ymd = new SimpleDateFormat("yyyy-MM-dd");

	protected static final Map<String, Validate> validates = new HashMap<String, Validate>();

	@Autowired
	private UserMessageResource messageResource;

	protected String uuid;

	private Integer pageSize, currentPage;

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		pageInfo.setPageSize(pageSize);
		this.pageSize = pageSize;
	}

	public Integer getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(Integer currentPage) {
		pageInfo.setCurrentPage(currentPage);
		this.currentPage = currentPage;
	}

	@Override
	public String execute() {
		try {
			list();
		} catch (Exception e) {
			log.error("error", e);
			response("{success: false, code: -1, message:'" + e.getMessage() + "'}");
		}
		return null;
	}

	protected void list() {

	}

	@Override
	public String save() {
		try {
			final JSONArray objs = JSONArray.fromObject(URLDecoder.decode(jsons, "UTF-8"));
			final Long id = executeSave(objs);
			response("{success: true, key: " + id + "}");
		} catch (Exception e) {
			log.error("error", e);
			response("{success: false, code: -1, message: '" + e.getMessage() + "'}");
		}
		return null;
	}

	protected Long executeSave(JSONArray objs) {
		return 0l;
	}

	@Override
	public String delete() {
		try {
			executeDelete();
			response("{success: true}");
		} catch (Exception e) {
			log.error("error", e);
			response("{success: false, code: -1, message: '" + e.getMessage() + "'}");
		}
		return null;
	}

	protected void executeDelete() {
		getService().delete(getEntityClass(), ids);
	}

	protected String getJsonForPageInfo() {
		final JSONObject obj = new JSONObject();
		obj.accumulate("currentPage", pageInfo.getCurrentPage()).accumulate("pageSize", pageInfo.getPageSize()).accumulate("totalCount", pageInfo.getTotalCount())
				.accumulate("totalPage", pageInfo.getTotalPage());
		return obj.toString();
	}

	protected PlatformUser getPlatformUser() {
		final PlatformUser pu = (PlatformUser) request.getServletContext().getAttribute("platformuser");
		return pu;
	}

	protected void addMobileUser(Member member) {
		getPlatformUser().addUser(member);
	}

	protected void setBaiduForMobileUser(String uuid, String userId, Long channelId, Integer termType) {
		getPlatformUser().setBaiduInfo(uuid, userId, channelId, termType);
	}

	protected BaseMember getMobileUser() {
		return getPlatformUser().getUser(uuid);
	}

	protected void setCity(String uuid, Double lng, Double lat, String city) {
		getPlatformUser().setLnglat(uuid, lng, lat, city);
	}

	@Override
	protected void response(String json) {
		if (json.indexOf('[') == 0) {
			JSONArray arr = JSONArray.fromObject(json);
			super.response(arr.toString());
		} else {
			final JSONObject obj = JSONObject.fromObject(json);
			super.response(obj.toString());
		}
	}

	protected String getChinaStr(String str) {
		try {
			return new String(str.getBytes("iso8859-1"), "utf-8");
		} catch (UnsupportedEncodingException e1) {
			log.error("error", e1);
		}
		return null;
	}

	protected String urlDecode(String str) {
		try {
			return URLDecoder.decode(str, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			log.error("error", e);
		}
		return "";
	}

	protected Long[] jsonToLong() {
		Long[] ids = null;
		try {
			final JSONArray arrs = JSONArray.fromObject(jsons);
			ids = new Long[arrs.size()];
			int i = 0;
			for (final Iterator<?> it = arrs.iterator(); it.hasNext(); i++) {
				final JSONObject obj = (JSONObject) it.next();
				ids[i] = obj.getLong("id");
			}
		} catch (Exception e) {
			log.error("error", e);
		}
		return ids;
	}

	public String getJson(Object obj) {
		return getJsonString(obj);
	}

	@Override
	public String getMessage(String code) {
		return messageResource.getMessage(code, null, Locale.CHINA);
	}

	@Override
	public String getMessage(String code, Object... parms) {
		return messageResource.getMessage(code, parms, Locale.CHINA);
	}

	protected Member toMember() {
		return (Member) session.getAttribute(LOGIN_MEMBER);
	}

	/**
	 * 发送短信验证码，与短信消息不同的是，短信验证码需要进行时间控制、
	 * 
	 * @param mobile
	 * @param keyName
	 * @param values
	 * @return
	 */
	protected final String sendSmsValidate(final String mobile,final String type) {
		final String validCode = getRandom(6);
		/*final String content = getMessage(keyName, new Object[] { validCode });
		final String msg = SmsUtils.sendSms(mobile, content);*/
		SingleSendSms sms = new SingleSendSms();
		//type 1为提现验证码 2 为提现通知 其他的为获取验证码(注册,找回密码等)
		if("1".equals(type)){
			sms.sendMsg(mobile, "{\"code\":\'"+ validCode +"\'}", TEMPLATE_CASH_CODE);
		}else{
			sms.sendMsg(mobile, "{\"code\":\'"+ validCode +"\'}", TEMPLATE__CODE);
		}
		final Validate validate = new Validate();
		validate.setGenTime(new Date());
		validate.setMobile(mobile);
		validate.setValidCode(validCode);
		validates.put(mobile, validate);
		
		return "ok";
	}
	
	
	/**
	 * 发送短信验证码，与短信消息不同的是，短信验证码需要进行时间控制、
	 * 
	 * @param mobile
	 * @param keyName
	 * @param values
	 * @return
	 */
	protected  String sendSmsValidate( String mobile, String type,String dou) {
		final String validCode = getRandom(6);
		/*final String content = getMessage(keyName, new Object[] { validCode });
		final String msg = SmsUtils.sendSms(mobile, content);*/
		SingleSendSms sms = new SingleSendSms();
		//type 1为提现验证码 2 为提现通知 其他的为获取验证码(注册,找回密码等)
		if("1".equals(type)){
			 dou = sms.sendMsg(mobile, "{\"code\":\'"+ validCode +"\'}", TEMPLATE_CASH_CODE, "dou");
		}else{
			 dou = sms.sendMsg(mobile, "{\"code\":\'"+ validCode +"\'}", TEMPLATE__CODE, "dou");
		}
		final Validate validate = new Validate();
		validate.setGenTime(new Date());
		validate.setMobile(mobile);
		validate.setValidCode(validCode);
		validates.put(mobile, validate);
		
		return dou;
	}
	
	
	/**
	 * 发送短信验证码，与短信消息不同的是，短信验证码需要进行时间控制、
	 * 
	 * @param mobile
	 * @param keyName
	 * @param values
	 * @return
	 */
	protected final String sendSmsValidateC(final String mobile,final String type) {
		final String validCode = getRandom(6);

		SingleSendSms sms = new SingleSendSms();
		//type 1为提现验证码 2 为提现通知 其他的为获取验证码(注册,找回密码等)
		if("1".equals(type)){
			sms.sendMsgC(mobile, "{\"code\":\'"+ validCode +"\'}", TEMPLATE_CASH_CODE_C);
		}else{
			sms.sendMsgC(mobile, "{\"code\":\'"+ validCode +"\'}", TEMPLATE_CODE_C);
		}
		final Validate validate = new Validate();
		validate.setGenTime(new Date());
		validate.setMobile(mobile);
		validate.setValidCode(validCode);
		validates.put(mobile, validate);
		
		return "ok";
	}
	
	protected String getJsonForMember(final Member m) {
		return getMemberJson(m).toString();
	}

	protected JSONObject getMemberJson(final Member m) {
		final JSONObject o = new JSONObject();
		if (m != null) o.accumulate("id", m.getId()).accumulate("name", getString(m.getName())).accumulate("city", m.getCity()).accumulate("county", m.getCounty()).accumulate("sex", m.getSex()).accumulate("province",getString(m.getProvince()))
				.accumulate("nick", getString(m.getNick())).accumulate("image", getString(m.getImage())).accumulate("role", m.getRole()).accumulate("lng", m.getLongitude())
				.accumulate("lat", m.getLatitude()).accumulate("mobilephone", m.getMobilephone()).accumulate("mobileValid", m.getMobileValid())
				.accumulate("count", m.getWorkoutTimes());
		return o;
	}

	// 移动端地图显示数据
	protected JSONObject getAllData(final Member m) {
		final JSONObject o = new JSONObject();
		if (m != null) o.accumulate("id", m.getId()).accumulate("name", getString(m.getName())).accumulate("lng", m.getLongitude()).accumulate("lat", m.getLatitude());
		return o;
	}

	/**
	 * 发送短信消息
	 * 
	 * @param content
	 * @return
	 */
	protected final String sendSmsMessage(final String mobile, final String keyName, final Object... values) {
		final String content = getMessage(keyName, values);
		final String msg = SmsUtils.sendSms(mobile, content);
		return msg;
	}

	/**
	 * 判断当前短信验证码是否已经过期，如果未过期，则判断是否与输入的一致
	 * 
	 * @param mobile
	 * @param code
	 * @return
	 */
	protected final boolean isRightful(final String mobile, final String code) {
		final Validate validate = validates.get(mobile);
		if (validate == null) {
			return false;
		} else {
			return !validate.isExpire() && validate.getValidCode().equals(code);
		}
	}

	public final Integer toInt(final String str) {
		if (null == str || "".equals(str)) { return 0; }
		return Integer.parseInt(str);
	}

	public final Double toDouble(final String str) {
		if (null == str || "".equals(str)) { return 0d; }
		return Double.parseDouble(str);
	}

	protected abstract BaseMember getLoginMember();

	protected JSONObject getGradeJson(Long id, String type) {
		final JSONObject json = new JSONObject();
		if (null != id) {
			StringBuffer appraiseSql = new StringBuffer("select distinct cnt,avgGrade from (").append(BaseAppraise.getAppraiseInfo()).append(") t ");
			appraiseSql.append("where t.orderType = ").append(type).append(" AND t.productId = ").append(id);

			final List<Map<String, Object>> maps = getService().queryForList(appraiseSql.toString());

			if (maps.size() == 0) {
				json.accumulate("sumper", 0).accumulate("sgrade", 0);
			}
			for (final Map<String, Object> map : maps) {
				json.accumulate("sumper", null == map.get("cnt") || map.size() == 0 ? 0 : map.get("cnt")).accumulate("sgrade",
						null == map.get("avgGrade") || map.size() == 0 ? 0 : map.get("avgGrade"));
			}
		}
		return json;
	}

}
