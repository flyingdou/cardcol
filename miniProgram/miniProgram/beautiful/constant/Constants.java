package miniProgram.beautiful.constant;

/**
 * 俱乐部小程序常量类
 * 
 * @author dou
 *
 */
public class Constants {

	/**
	 * 俱乐部小程序APPID
	 */
	public static final String APPID = "wx9190b744b91f7c37";

	/**
	 * 俱乐部小程序APPSECERT
	 */
	public static final String APP_SECRET = "fb252058a4e47efa4b839f1f33863cba";
	
	/**
	 * mch_id
	 * 商户号
	 */
	public static final String MCH_ID = "1508275271";
	
	/**
	 * 支付app_key
	 */
	public static final String APP_KEY = "7203F1ED72622326344E70A491B46826";
	

	/**
	 * 俱乐部ID
	 */
	public static final Integer CLUB_ID = 433;

	/**
	 * 俱乐部签到距离限制
	 */
	public static final Double SIGN_DISTANCE = 3000.0;

	/**
	 * 俱乐部健身卡订单签到类型
	 */
	public static final String PRODUCT_ORDER_SIGN_TYPE = "1";

	/**
	 * 俱乐部会员签到类型
	 */
	public static final String MEMBER_ORDER_SIGN_TYPE = "0";

	/**
	 * 健身卡商品类型
	 */
	public static final String PRODUCT_TYPE_PRODUCT = "product";

	/**
	 * 健身挑战商品类型
	 */
	public static final String PRODUCT_TYPE_ACTIVE = "active";

	/**
	 * 课程(course)商品类型
	 */
	public static final String PRODUCT_TYPE_COURSE = "course";
	
	/**
	 * 王严专家系统商品类型
	 */
	public static final String PRODUCT_TYPE_GOODS = "goods";
	
	/**
	 * 砍价活动订单
	 */
	public static final String PRODUCT_TYPE_PRICECUTDOWN_PRODUCT = "priceCutdownProduct";

	/**
	 * String null
	 */
	public static final String NULL = "null";

	/**
	 * 俱乐部小程序客服消息类型(用户在小程序“客服会话按钮”进入客服会话)
	 */
	public static final String MSG_TYPE_EVENT = "event";

	/**
	 * 俱乐部小程序欢迎消息模板
	 */
	public static final String CLUB_MINI_PROGRAM_WELLCOME_MESSAGE = "欢迎来到丽健游泳健身俱乐部，丽健游泳健身俱乐部涵盖有游泳，跑步，乒乓球，动感单车，组合器械，自由重量等多种锻炼方式，以及瑜伽、拉丁、普拉提、健美操、舞蹈等各项操课！咨询电话：13311204081";

	/**
	 * 俱乐部小程序普通消息模板
	 */
	public static final String CLUB_MINI_PROGRAM_PUBLIC_MESSAGE = "欢迎来到丽健游泳健身俱乐部，丽健游泳健身俱乐部涵盖有游泳，跑步，乒乓球，动感单车，组合器械，自由重量等多种锻炼方式，以及瑜伽、拉丁、普拉提、健美操、舞蹈等各项操课！咨询电话：13311204081";
	
	/**
	 * test
	 */
	public static final String CLUB_X = "12334";
	
	/**
	 * 俱乐部小程序订单优惠九折
	 */
	public static final Double ORDER_DISCOUNT_NINETY_PERCENT = 0.9;

	public static String GET_USERINFO_URL = " https://api.weixin.qq.com/sns/jscode2session?";
}
