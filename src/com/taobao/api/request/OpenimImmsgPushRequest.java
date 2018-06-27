package com.taobao.api.request;

import com.taobao.api.domain.ImMsg;
import java.util.Map;

import com.taobao.api.ApiRuleException;
import com.taobao.api.BaseTaobaoRequest;
import com.taobao.api.internal.util.TaobaoHashMap;
import com.taobao.api.internal.util.json.JSONWriter;
import com.taobao.api.response.OpenimImmsgPushResponse;

/**
 * TOP API: taobao.openim.immsg.push request
 * 
 * @author top auto create
 * @since 1.0, 2015.09.23
 */
public class OpenimImmsgPushRequest extends BaseTaobaoRequest<OpenimImmsgPushResponse> {
	
	

	/** 
	* openim消息结构体
	 */
	private String immsg;

	public void setImmsg(String immsg) {
		this.immsg = immsg;
	}

	public void setImmsg(ImMsg immsg) {
		this.immsg = new JSONWriter(false,true).write(immsg);
	}

	public String getImmsg() {
		return this.immsg;
	}

	public String getApiMethodName() {
		return "taobao.openim.immsg.push";
	}

	public Map<String, String> getTextParams() {		
		TaobaoHashMap txtParams = new TaobaoHashMap();
		txtParams.put("immsg", this.immsg);
		if(this.udfParams != null) {
			txtParams.putAll(this.udfParams);
		}
		return txtParams;
	}

	public Class<OpenimImmsgPushResponse> getResponseClass() {
		return OpenimImmsgPushResponse.class;
	}

	public void check() throws ApiRuleException {
	}
	

}