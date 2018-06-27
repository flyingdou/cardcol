package com.taobao.api.request;

import com.taobao.api.internal.util.RequestCheckUtils;
import java.util.Map;

import com.taobao.api.ApiRuleException;
import com.taobao.api.BaseTaobaoRequest;
import com.taobao.api.internal.util.TaobaoHashMap;

import com.taobao.api.response.NlpFeedbackResponse;

/**
 * TOP API: taobao.nlp.feedback request
 * 
 * @author top auto create
 * @since 1.0, 2015.06.12
 */
public class NlpFeedbackRequest extends BaseTaobaoRequest<NlpFeedbackResponse> {
	
	

	/** 
	* api接口名称
	 */
	private String apiName;

	/** 
	* 文本内容
	 */
	private String content;

	/** 
	* 反馈的具体原因
	 */
	private String description;

	/** 
	* 反馈类型 1-物流信息判断错误
	 */
	private Long type;

	public void setApiName(String apiName) {
		this.apiName = apiName;
	}

	public String getApiName() {
		return this.apiName;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getContent() {
		return this.content;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDescription() {
		return this.description;
	}

	public void setType(Long type) {
		this.type = type;
	}

	public Long getType() {
		return this.type;
	}

	public String getApiMethodName() {
		return "taobao.nlp.feedback";
	}

	public Map<String, String> getTextParams() {		
		TaobaoHashMap txtParams = new TaobaoHashMap();
		txtParams.put("api_name", this.apiName);
		txtParams.put("content", this.content);
		txtParams.put("description", this.description);
		txtParams.put("type", this.type);
		if(this.udfParams != null) {
			txtParams.putAll(this.udfParams);
		}
		return txtParams;
	}

	public Class<NlpFeedbackResponse> getResponseClass() {
		return NlpFeedbackResponse.class;
	}

	public void check() throws ApiRuleException {
		RequestCheckUtils.checkNotEmpty(content, "content");
	}
	

}