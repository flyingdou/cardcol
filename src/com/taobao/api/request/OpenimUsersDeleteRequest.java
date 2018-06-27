package com.taobao.api.request;

import com.taobao.api.internal.util.RequestCheckUtils;
import java.util.Map;

import com.taobao.api.ApiRuleException;
import com.taobao.api.BaseTaobaoRequest;
import com.taobao.api.internal.util.TaobaoHashMap;

import com.taobao.api.response.OpenimUsersDeleteResponse;

/**
 * TOP API: taobao.openim.users.delete request
 * 
 * @author top auto create
 * @since 1.0, 2015.07.13
 */
public class OpenimUsersDeleteRequest extends BaseTaobaoRequest<OpenimUsersDeleteResponse> {
	
	

	/** 
	* 需要删除的用户列表，多个用户用半角逗号分隔，最多一次可以删除100个用户
	 */
	private String userids;

	public void setUserids(String userids) {
		this.userids = userids;
	}

	public String getUserids() {
		return this.userids;
	}

	public String getApiMethodName() {
		return "taobao.openim.users.delete";
	}

	public Map<String, String> getTextParams() {		
		TaobaoHashMap txtParams = new TaobaoHashMap();
		txtParams.put("userids", this.userids);
		if(this.udfParams != null) {
			txtParams.putAll(this.udfParams);
		}
		return txtParams;
	}

	public Class<OpenimUsersDeleteResponse> getResponseClass() {
		return OpenimUsersDeleteResponse.class;
	}

	public void check() throws ApiRuleException {
		RequestCheckUtils.checkNotEmpty(userids, "userids");
		RequestCheckUtils.checkMaxListSize(userids, 100, "userids");
	}
	

}