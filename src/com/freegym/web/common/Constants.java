package com.freegym.web.common;

public interface Constants {
	public static final String NORMAL_USER_LIST = "NORMAL_USER_LIST";
	public static final String KICK_OUT_USER_LIST = "KICK_OUT_USER_LIST";

	public static final String USER_KEY = "USER_KEY";
	public static final String REG_KEY = "REG_KEY";
	public static final String USER_APPLY_VALIDATE = "USER_APPLY_VALIDATE";
	public static final String CURRENT_CITY = "currentCity";

	public static final String ROLE_TYPE_USER = "1";
	public static final String ROLE_TYPE_SYSTEM = "0";

	public static final String OPER_DEL = "DEL.OPER.INFO";
	public static final String OPER_EDIT = "EDIT.OPER.INFO";
	public static final String OPER_ADD = "ADD.OPER.INFO";
	public static final String LOGIN = "LOGIN.OPER.INFO";
	public static final String LOGOUT = "LOGOUT.OPER.INFO";

	public static final String SAVE_ACTION_OK = "SAVE.ACTION.OK";
	public static final String DEL_ACTION_OK = "DEL.ACTION.OK";
	public static final String SAVE_ROLELIMIT_OK = "SAVE.ROLESLIMIT.OK";
	public static final String INFO_OK = "INFO.OK";
	public static final String INFO_ERROR = "INFO.ERROR";
	public static final String UPDATE_PASSWORD_ACTION_INFO = "UPDATE.PASSWORD.ACTION.INFO";
	public static final String UPDATE_PASSWORD_OK = "UPDATE.PASSWORD.OK";

	public static final String GLOBAL_ERROR_INFO = "GLOBAL_ERROR_INFO";
	public static final String DEPT_CODE = "DEPT_CODE";
	public static final String DEPT_NAME = "DEPT_NAME";
	public static final String USER_NAME = "USER_NAME";

	public static final int DATA_TYPE_CHAR = 1;
	public static final int DATA_TYPE_NUMBER = 2;
	public static final int DATA_TYPE_DATE = 3;
	public static final int DATA_TYPE_INTEGER = 4;

	public static final int EXPORT_USER = 1;

	public static final String TB_PRODUCT_ORDER_V45 = "22";
	public static final String TB_GOODS_ORDER = "44";
	public static final String TB_COURSE_ORDER = "55";
	public static final String TB_FACTORY_ORDER = "66";
	public static final String TB_PLAN_ORDER = "77";
	public static final String CARDCOL_ORDER_NO = "88";
	public static final String TB_ACTIVE_ORDER = "99";
	public static final String PRICECUTDOWN_PRODUCT_ORDER = "696969";

	public static final String ORDER_TYPE_PRODUCT = "1";
	public static final String ORDER_TYPE_ACTIVE = "2";
	public static final String ORDER_TYPE_PLAN = "3";
	public static final String ORDER_TYPE_FACTORY = "4";
	public static final String ORDER_TYPE_COURSE = "5";
	public static final String ORDER_TYPE_GOODS = "6";
	public static final String ORDER_TYPE_BARCELET = "7";
	public static final String ORDER_TYPE_ONECARD = "8";

	public static final String PRODUCT_STATUS_NOVALID = "A";
	public static final String PRODUCT_STATUS_VALID = "B";

	public static final Character ORDER_STATUS_BEPAID = '0';
	public static final Character ORDER_STATUS_PAID = '1';
	public static final Character ORDER_STATUS_FINSH = '2';
	public static final Character ORDER_STATUS_BALANCE = '3';

	public static final String ACCESS_KEY = "AA885151c042e11d929af76223f1fd90";
	public static final String SECRET_KEY = "D77ded4b0defa757a17f27a1613becb1";
	public static final String BAIDU_HOST = "bcs.duapp.com";
	public static final String BUCKET = "cardcol01";
	public static final String FOLDER = "cardcol";

	public static final String SMS_SERVICE_URL = "http://www.stongnet.com/sdkhttp/sendsms.aspx";
	public static final String SMS_SERVICE_USER = "101100-WEB-HUAX-886810";
	public static final String SMS_SERVICE_PASS = "MRGNMMTQ";

	public static int ACTIVE_DATE1 = 10;
	public static int ACTIVE_DATE2 = 30;
	public static int ACTIVE_DATE3 = 92;

	public static int DETAIL_SELLER1 = 8;
	public static int DETAIL_SELLER2 = 20;
	public static int DETAIL_BUYER = 3;

	public static Character INOUT_TYPE_IN = '1';
	public static Character INOUT_TYPE_OUT = '2';
	
	public final static String APP_KEY = "23726277"; //AppKey从控制台获取
	public final static String APP_SECRET = "1d2add4851b1c5eeddc6a39d2afaa5a6"; //AppSecret从控制台获取
	public final static String SIGN_NAME = "健身E卡通"; // 签名名称从控制台获取，必须是审核通过的
	public final static String TEMPLATE_CODE = "SMS_69860424"; //模板CODE从控制台获取，必须是审核通过的
	public final static String HOST = "sms.market.alicloudapi.com"; //API域名从控制台获取
	
	// 手环离线心率，以秒为单位
	public final static String RING_TIME_TYPE_SS = "second";
	
	// 手环离线心率，以分为单位
	public final static String RING_TIME_TYPE_MM = "minute";
	
	
	

}
