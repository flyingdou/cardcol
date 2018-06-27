package com.freegym.web;

import java.util.Date;

import com.aliyn.api.geteway.util.SingleSendSms;
import com.aliyun.api.gateway.demo.enums.Constantsms;
import com.freegym.web.basic.Member;
import com.freegym.web.common.Constants;
import com.freegym.web.common.Validate;
import com.freegym.web.utils.SessionConstant;
import com.freegym.web.utils.SmsUtils;
import com.sanmen.web.core.AbstractAction;

public abstract class BasicAction extends AbstractAction implements SessionConstant, Constants,Constantsms{

	private static final long serialVersionUID = -1438192657261008665L;

	/*
	 * @Autowired private UserMessageResource messageResource;
	 * 
	 * @Override public String getMessage(String code) { return
	 * messageResource.getMessage(code, null, Locale.CHINA); }
	 * 
	 * @Override public String getMessage(String code, Object... parms) { return
	 * messageResource.getMessage(code, parms, Locale.CHINA); }
	 */

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
	protected final void sendSmsValidate(final String mobile, final String type) {
		/*final String validCode = getRandom(6);
		final String content = getMessage(keyName, new Object[] { validCode });
		final String msg = SmsUtils.sendSms(mobile, content);
		final Validate validate = new Validate();
		validate.setGenTime(new Date());
		validate.setMobile(mobile);
		validate.setValidCode(validCode);
		session.setAttribute(mobile, validate);
		return msg;*/
		final String validCode = getRandom(6);
		SingleSendSms sms = new SingleSendSms();
		//type 1为提现验证码 其他的为获取验证码(注册,找回密码等)
		if("1".equals(type)){
			sms.sendMsg(mobile, "{\"code\":\'"+ validCode +"\'}", TEMPLATE_CASH_CODE);
		}else{
			sms.sendMsg(mobile, "{\"code\":\'"+ validCode +"\'}", TEMPLATE__CODE);
		}
		final Validate validate = new Validate();
		validate.setGenTime(new Date());
		validate.setMobile(mobile);
		validate.setValidCode(validCode);
		session.setAttribute(mobile, validate);
	}
	
	/**
	 * 发送短信验证码，与短信消息不同的是，短信验证码需要进行时间控制、
	 * 
	 * @param mobile
	 * @param keyName
	 * @param values
	 * @return
	 */
	protected final String sendSmsValidate(final String mobile, final String type ,String ss) {
		/*final String validCode = getRandom(6);
		final String content = getMessage(keyName, new Object[] { validCode });
		final String msg = SmsUtils.sendSms(mobile, content);
		final Validate validate = new Validate();
		validate.setGenTime(new Date());
		validate.setMobile(mobile);
		validate.setValidCode(validCode);
		session.setAttribute(mobile, validate);
		return msg;*/
		final String validCode = getRandom(6);
		SingleSendSms sms = new SingleSendSms();
		//type 1为提现验证码 其他的为获取验证码(注册,找回密码等)
		if("1".equals(type)){
			sms.sendMsg(mobile, "{\"code\":\'"+ validCode +"\'}", TEMPLATE_CASH_CODE);
		}else{
			sms.sendMsg(mobile, "{\"code\":\'"+ validCode +"\'}", TEMPLATE__CODE);
		}
		final Validate validate = new Validate();
		validate.setGenTime(new Date());
		validate.setMobile(mobile);
		validate.setValidCode(validCode);
		session.setAttribute(mobile, validate);
		return "";
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
		final Validate validate = (Validate) session.getAttribute(mobile);
		if (validate == null) {
			return false;
		} else {
			return !validate.isExpire() && validate.getValidCode().equals(code);
		}
	}

}
