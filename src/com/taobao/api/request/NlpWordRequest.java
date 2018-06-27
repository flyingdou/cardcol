package com.taobao.api.request;

import com.taobao.api.internal.util.RequestCheckUtils;
import com.taobao.api.domain.Text;
import java.util.Map;

import com.taobao.api.ApiRuleException;
import com.taobao.api.BaseTaobaoRequest;
import com.taobao.api.internal.util.TaobaoHashMap;
import com.taobao.api.internal.util.json.JSONWriter;
import com.taobao.api.response.NlpWordResponse;

/**
 * TOP API: taobao.nlp.word request
 * 
 * @author top auto create
 * @since 1.0, 2016.01.12
 */
public class NlpWordRequest extends BaseTaobaoRequest<NlpWordResponse> {
	
	

	/** 
	* 文本内容
	 */
	private String text;

	/** 
	* 功能类型选择：1)wType=1时提供分词功能，type=0时为基本粒度，type=1时为混合粒度，type=3时为基本粒度和混合粒度共同输出；
	 */
	private Long wType;

	public void setText(String text) {
		this.text = text;
	}

	public void setText(Text text) {
		this.text = new JSONWriter(false,true).write(text);
	}

	public String getText() {
		return this.text;
	}

	public void setwType(Long wType) {
		this.wType = wType;
	}

	public Long getwType() {
		return this.wType;
	}

	public String getApiMethodName() {
		return "taobao.nlp.word";
	}

	public Map<String, String> getTextParams() {		
		TaobaoHashMap txtParams = new TaobaoHashMap();
		txtParams.put("text", this.text);
		txtParams.put("w_type", this.wType);
		if(this.udfParams != null) {
			txtParams.putAll(this.udfParams);
		}
		return txtParams;
	}

	public Class<NlpWordResponse> getResponseClass() {
		return NlpWordResponse.class;
	}

	public void check() throws ApiRuleException {
		RequestCheckUtils.checkNotEmpty(wType, "wType");
	}
	

}