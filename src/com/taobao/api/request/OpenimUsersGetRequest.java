package com.taobao.api.request;

import com.taobao.api.internal.util.RequestCheckUtils;
import java.util.Map;

import com.taobao.api.ApiRuleException;
import com.taobao.api.BaseTaobaoRequest;
import com.taobao.api.internal.util.TaobaoHashMap;

import com.taobao.api.response.OpenimUsersGetResponse;

/**
 * TOP API: taobao.openim.users.get request
 * 
 * @author top auto create
 * @since 1.0, 2015.12.09
 */
public class OpenimUsersGetRequest extends BaseTaobaoRequest<OpenimUsersGetResponse> {
	
	

	/** 
	* 用户id序列
	 */
	private String userids;

	public void setUserids(String userids) {
		this.userids = userids;
	}

	public String getUserids() {
		return this.userids;
	}

	public String getApiMethodName() {
		return "taobao.openim.users.get";
	}

	public Map<String, String> getTextParams() {		
		TaobaoHashMap txtParams = new TaobaoHashMap();
		txtParams.put("userids", this.userids);
		if(this.udfParams != null) {
			txtParams.putAll(this.udfParams);
		}
		return txtParams;
	}

	public Class<OpenimUsersGetResponse> getResponseClass() {
		return OpenimUsersGetResponse.class;
	}

	public void check() throws ApiRuleException {
		RequestCheckUtils.checkNotEmpty(userids, "userids");
		RequestCheckUtils.checkMaxListSize(userids, 100, "userids");
	}
	

}