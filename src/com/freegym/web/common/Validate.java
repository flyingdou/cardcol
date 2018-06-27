package com.freegym.web.common;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

/**
 * 用于存放验证码
 */
public class Validate implements Serializable {

	private static final long serialVersionUID = -7085004445613342784L;

	// 手机号码
	private String mobile;

	// 验证码
	private String validCode;

	// 生成时间
	private Date genTime;

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getValidCode() {
		return validCode;
	}

	public void setValidCode(String validCode) {
		this.validCode = validCode;
	}

	public Date getGenTime() {
		return genTime;
	}

	public void setGenTime(Date genTime) {
		this.genTime = genTime;
	}

	/**
	 * 判断是否过期
	 * 
	 * @return 过期：返回true 不过期 ：返回 false
	 */
	public boolean isExpire() {
		final Calendar c = Calendar.getInstance();
		final Calendar c1 = Calendar.getInstance();
		c.setTime(genTime);
		c.add(Calendar.MINUTE, 30);
		return c.compareTo(c1) <= 0;
	}
}
