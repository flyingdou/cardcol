package miniProgram.club.wechat;

import miniProgram.club.constant.Constants;

/**
 * 请求微信
 * @author dou
 *
 */
public class GetUserInfoRequest {
	
	/**
	 * 参数code
	 */
	private  String code;
	
	private String http_url;
	
	public  String getCode() {
		return code;
	}

	public  void setCode(String code) {
		this.code = code;
	}

	
	public String getHttp_url() {
		 this.http_url = Constants.GET_USERINFO_URL + "appid=" + Constants.APPID + "&secret=" + Constants.APP_SECRET 
		+ "&js_code=" + this.code + "&grant_type=authorization_code";
		return this.http_url;
	}

	

	/**
	 * 生成有参构造方法
	 */
	public GetUserInfoRequest(String code) {
		super();
		this.code = code;
	}
	
	
	
	
	
	 
			                    

}
