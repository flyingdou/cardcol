package com.taobao.api.request;

import com.taobao.api.domain.OpenImUser;
import com.taobao.api.internal.util.RequestCheckUtils;
import java.util.Map;

import com.taobao.api.ApiRuleException;
import com.taobao.api.BaseTaobaoRequest;
import com.taobao.api.internal.util.TaobaoHashMap;
import com.taobao.api.internal.util.json.JSONWriter;
import com.taobao.api.response.OpenimTribeGetalltribesResponse;

/**
 * TOP API: taobao.openim.tribe.getalltribes request
 * 
 * @author top auto create
 * @since 1.0, 2015.08.26
 */
public class OpenimTribeGetalltribesRequest extends BaseTaobaoRequest<OpenimTribeGetalltribesResponse> {
	
	

	/** 
	* 群类型
	 */
	private String tribeTypes;

	/** 
	* 用户信息
	 */
	private String user;

	public void setTribeTypes(String tribeTypes) {
		this.tribeTypes = tribeTypes;
	}

	public String getTribeTypes() {
		return this.tribeTypes;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public void setUser(OpenImUser user) {
		this.user = new JSONWriter(false,true).write(user);
	}

	public String getUser() {
		return this.user;
	}

	public String getApiMethodName() {
		return "taobao.openim.tribe.getalltribes";
	}

	public Map<String, String> getTextParams() {		
		TaobaoHashMap txtParams = new TaobaoHashMap();
		txtParams.put("tribe_types", this.tribeTypes);
		txtParams.put("user", this.user);
		if(this.udfParams != null) {
			txtParams.putAll(this.udfParams);
		}
		return txtParams;
	}

	public Class<OpenimTribeGetalltribesResponse> getResponseClass() {
		return OpenimTribeGetalltribesResponse.class;
	}

	public void check() throws ApiRuleException {
		RequestCheckUtils.checkNotEmpty(tribeTypes, "tribeTypes");
		RequestCheckUtils.checkMaxListSize(tribeTypes, 20, "tribeTypes");
	}
	

}