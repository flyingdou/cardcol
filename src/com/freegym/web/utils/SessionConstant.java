package com.freegym.web.utils;

import com.freegym.web.common.Constants;

public interface SessionConstant extends Constants {
	public final static String LOGIN_SITE = "loginSite";

	public static final String CURRENT_COMPANY = "currentCompany";
	public static final String ACCESS_DOMAIN = "accessDomain";
	public static final String SYSTEM_SETTING = "setting";

	public static final int EMPLOYEE_IMPORT = 1;

	public static final Long SYSTEM_PROJECT_ID = 1l;

	public static final String TYPE_AUDIT = "1";

	public static final String TYPE_AUDIT_NO = "0";

	public static final String STATUS_PROJECT_NEW = "1";
	public static final String STATUS_PROJECT_EXTRACT = "2";
	public static final String STATUS_PROJECT_REPLENISH = "3";

	public static final String STATUS_REQUEST_COURSE_WAIT = "1";
	public static final String STATUS_REQUEST_COURSE_SUCCESS = "2";
	public static final String STATUS_REQUEST_COURSE_REFUSE = "3";

	public static final String STATUS_REQUEST_NOREAD = "0";
	public static final String STATUS_REQUEST_ISREAD = "1";

	public static final String SYSTEM_DEFAULT_CITY = "武汉市";

	public static final String STATUS_OUTBID = "1"; // 已中标
	public static final String STATUS_OUTBID_GIVEUP = "0"; // 已放弃
	/**
	 * 会员校验成功
	 */
	public static final String MEMBER_VALIDATE_SUCCESS = "1";
	/**
	 * 会员校验失败
	 */
	public static final String MEMBER_VALIDATE_FAILED = "2";

	/**
	 * 未进行校验
	 */
	public static final String MEMBER_VALIDATE_UNVALID = "0";

	public static final String PLAN_SOURCE_SELF = "2";
	public static final String PLAN_SOURCE_COACH = "1";
	public static final String PLAN_SOURCE_AUTO = "3";

	public static final Long PAYMENT_FACTORY_TIME = 30L;

	public static final Character PRODUCT_TYPE_CARD = '1';
	public static final Character PRODUCT_TYPE_ACTIVE = '2';
	public static final Character PRODUCT_TYPE_PLAN = '3';
	public static final Character PRODUCT_TYPE_FACTORY = '4';
	public static final Character PRODUCT_TYPE_COURSE = '5';
	public static final Character PRODUCT_TYPE_AUTO = '6';
	public static final Character PRODUCT_TYPE_MEMBER = '7';
	public static final Character PRODUCT_TYPE_ARTICLE = '8';
	public static final Character PRODUCT_TYPE_BANNER = '9';
	public static final Character PRODUCT_TYPE_ONECARD = '0';

	public static final Integer STATUS_TICKET_USE = 1;
	public static final Integer STATUS_TICKET_USED = 2;
	public static final Integer STATUS_TICKET_NOVALID = 3;

}
