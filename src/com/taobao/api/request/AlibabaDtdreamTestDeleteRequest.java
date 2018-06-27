package com.taobao.api.request;

import java.util.Map;

import com.taobao.api.ApiRuleException;
import com.taobao.api.BaseTaobaoRequest;
import com.taobao.api.internal.util.TaobaoHashMap;

import com.taobao.api.response.AlibabaDtdreamTestDeleteResponse;

/**
 * TOP API: alibaba.dtdream.test.delete request
 * 
 * @author top auto create
 * @since 1.0, 2015.10.14
 */
public class AlibabaDtdreamTestDeleteRequest extends BaseTaobaoRequest<AlibabaDtdreamTestDeleteResponse> {
	
	

	/** 
	* id
	 */
	private String id;

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return this.id;
	}

	public String getApiMethodName() {
		return "alibaba.dtdream.test.delete";
	}

	public Map<String, String> getTextParams() {		
		TaobaoHashMap txtParams = new TaobaoHashMap();
		txtParams.put("id", this.id);
		if(this.udfParams != null) {
			txtParams.putAll(this.udfParams);
		}
		return txtParams;
	}

	public Class<AlibabaDtdreamTestDeleteResponse> getResponseClass() {
		return AlibabaDtdreamTestDeleteResponse.class;
	}

	public void check() throws ApiRuleException {
	}
	

}