package com.taobao.api.request;

import com.taobao.api.internal.util.RequestCheckUtils;
import java.util.Map;

import com.taobao.api.ApiRuleException;
import com.taobao.api.BaseTaobaoRequest;
import com.taobao.api.internal.util.TaobaoHashMap;

import com.taobao.api.response.OpenimTrackGetsummaryResponse;

/**
 * TOP API: taobao.openim.track.getsummary request
 * 
 * @author top auto create
 * @since 1.0, 2015.11.03
 */
public class OpenimTrackGetsummaryRequest extends BaseTaobaoRequest<OpenimTrackGetsummaryResponse> {
	
	

	/** 
	* 用户所属app的prefix
	 */
	private String prefix;

	/** 
	* 用户id
	 */
	private String uid;

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public String getPrefix() {
		return this.prefix;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getUid() {
		return this.uid;
	}

	public String getApiMethodName() {
		return "taobao.openim.track.getsummary";
	}

	public Map<String, String> getTextParams() {		
		TaobaoHashMap txtParams = new TaobaoHashMap();
		txtParams.put("prefix", this.prefix);
		txtParams.put("uid", this.uid);
		if(this.udfParams != null) {
			txtParams.putAll(this.udfParams);
		}
		return txtParams;
	}

	public Class<OpenimTrackGetsummaryResponse> getResponseClass() {
		return OpenimTrackGetsummaryResponse.class;
	}

	public void check() throws ApiRuleException {
		RequestCheckUtils.checkNotEmpty(uid, "uid");
	}
	

}