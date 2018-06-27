package com.taobao.api.request;

import com.taobao.api.internal.util.RequestCheckUtils;
import java.util.Map;

import com.taobao.api.ApiRuleException;
import com.taobao.api.BaseTaobaoRequest;
import com.taobao.api.internal.util.TaobaoHashMap;

import com.taobao.api.response.OpenAccountSearchResponse;

/**
 * TOP API: taobao.open.account.search request
 * 
 * @author top auto create
 * @since 1.0, 2015.08.10
 */
public class OpenAccountSearchRequest extends BaseTaobaoRequest<OpenAccountSearchResponse> {
	
	

	/** 
	* solr查询
	 */
	private String query;

	public void setQuery(String query) {
		this.query = query;
	}

	public String getQuery() {
		return this.query;
	}

	public String getApiMethodName() {
		return "taobao.open.account.search";
	}

	public Map<String, String> getTextParams() {		
		TaobaoHashMap txtParams = new TaobaoHashMap();
		txtParams.put("query", this.query);
		if(this.udfParams != null) {
			txtParams.putAll(this.udfParams);
		}
		return txtParams;
	}

	public Class<OpenAccountSearchResponse> getResponseClass() {
		return OpenAccountSearchResponse.class;
	}

	public void check() throws ApiRuleException {
		RequestCheckUtils.checkNotEmpty(query, "query");
	}
	

}