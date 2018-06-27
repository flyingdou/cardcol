package miniProgram.wechatAPI;

public class PayRequest {
	
	private String appId;
	
	private String appSecret;
	
	private String MCH_ID;
	
	private String key;
	
	private String openId;
	
	

	/**
	 * 全量参数
	 * @param appId
	 * @param appSecret
	 * @param mCH_ID
	 * @param key
	 * @param openId
	 */
	public PayRequest(String appId, String appSecret, String mCH_ID, String key, String openId) {
		super();
		this.appId = appId;
		this.appSecret = appSecret;
		MCH_ID = mCH_ID;
		this.key = key;
		this.openId = openId;
	}
	
	
	/**
	 * 不需要appSecret的构造
	 * @param appId
	 * @param mCH_ID
	 * @param key
	 * @param openId
	 */
	public PayRequest(String appId, String mCH_ID, String key, String openId) {
		super();
		this.appId = appId;
		MCH_ID = mCH_ID;
		this.key = key;
		this.openId = openId;
	}
	

	/**
	 * setter && getter
	 */
	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getAppSecret() {
		return appSecret;
	}

	public void setAppSecret(String appSecret) {
		this.appSecret = appSecret;
	}

	public String getMCH_ID() {
		return MCH_ID;
	}

	public void setMCH_ID(String mCH_ID) {
		MCH_ID = mCH_ID;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}
	
	

}
