package com.taobao.api.request;

import com.taobao.api.internal.util.RequestCheckUtils;
import java.util.Map;

import com.taobao.api.ApiRuleException;
import com.taobao.api.BaseTaobaoRequest;
import com.taobao.api.internal.util.TaobaoHashMap;

import com.taobao.api.response.OpenimSnfilterwordSetfilterResponse;

/**
 * TOP API: taobao.openim.snfilterword.setfilter request
 * 
 * @author top auto create
 * @since 1.0, 2016.03.14
 */
public class OpenimSnfilterwordSetfilterRequest extends BaseTaobaoRequest<OpenimSnfilterwordSetfilterResponse> {
	
	

	/** 
	* 上传者身份
	 */
	private String creator;

	/** 
	* 过滤原因描述
	 */
	private String desc;

	/** 
	* 需要过滤的关键词
	 */
	private String filterword;

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getCreator() {
		return this.creator;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getDesc() {
		return this.desc;
	}

	public void setFilterword(String filterword) {
		this.filterword = filterword;
	}

	public String getFilterword() {
		return this.filterword;
	}

	public String getApiMethodName() {
		return "taobao.openim.snfilterword.setfilter";
	}

	public Map<String, String> getTextParams() {		
		TaobaoHashMap txtParams = new TaobaoHashMap();
		txtParams.put("creator", this.creator);
		txtParams.put("desc", this.desc);
		txtParams.put("filterword", this.filterword);
		if(this.udfParams != null) {
			txtParams.putAll(this.udfParams);
		}
		return txtParams;
	}

	public Class<OpenimSnfilterwordSetfilterResponse> getResponseClass() {
		return OpenimSnfilterwordSetfilterResponse.class;
	}

	public void check() throws ApiRuleException {
		RequestCheckUtils.checkNotEmpty(creator, "creator");
		RequestCheckUtils.checkNotEmpty(filterword, "filterword");
	}
	

}