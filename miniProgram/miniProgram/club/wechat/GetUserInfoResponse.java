package miniProgram.club.wechat;

import com.alibaba.fastjson.JSONObject;

import common.util.HttpRequestUtils;

/**
 * 发起获取用户信息的请求
 * @author dou
 *
 */
public class GetUserInfoResponse {
	
	private String openid;
	
	private String session_key;
	
	private String union_id;
	
	
	
	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getSession_key() {
		return session_key;
	}

	public void setSession_key(String session_key) {
		this.session_key = session_key;
	}

	public String getUnion_id() {
		return union_id;
	}

	public void setUnion_id(String union_id) {
		this.union_id = union_id;
	}



	public GetUserInfoResponse getUserInfo (String code) {
		GetUserInfoRequest request = new GetUserInfoRequest(code);
		String ret = HttpRequestUtils.httpGet(request.getHttp_url()).toString();
		return JSONObject.parseObject(ret, GetUserInfoResponse.class);
		
	}

}
