package com.taobao.api.request;

import com.taobao.api.domain.OpenImUser;
import com.taobao.api.internal.util.RequestCheckUtils;
import java.util.Map;

import com.taobao.api.ApiRuleException;
import com.taobao.api.BaseTaobaoRequest;
import com.taobao.api.internal.util.TaobaoHashMap;
import com.taobao.api.internal.util.json.JSONWriter;
import com.taobao.api.response.OpenimTribeDismissResponse;

/**
 * TOP API: taobao.openim.tribe.dismiss request
 * 
 * @author top auto create
 * @since 1.0, 2015.08.26
 */
public class OpenimTribeDismissRequest extends BaseTaobaoRequest<OpenimTribeDismissResponse> {
	
	

	/** 
	* 群id
	 */
	private Long tribeId;

	/** 
	* 用户信息
	 */
	private String user;

	public void setTribeId(Long tribeId) {
		this.tribeId = tribeId;
	}

	public Long getTribeId() {
		return this.tribeId;
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
		return "taobao.openim.tribe.dismiss";
	}

	public Map<String, String> getTextParams() {		
		TaobaoHashMap txtParams = new TaobaoHashMap();
		txtParams.put("tribe_id", this.tribeId);
		txtParams.put("user", this.user);
		if(this.udfParams != null) {
			txtParams.putAll(this.udfParams);
		}
		return txtParams;
	}

	public Class<OpenimTribeDismissResponse> getResponseClass() {
		return OpenimTribeDismissResponse.class;
	}

	public void check() throws ApiRuleException {
		RequestCheckUtils.checkNotEmpty(tribeId, "tribeId");
	}
	

}