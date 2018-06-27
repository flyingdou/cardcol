package com.taobao.api.request;

import java.util.List;
import com.taobao.api.internal.util.RequestCheckUtils;
import com.taobao.api.domain.Text;
import java.util.Map;

import com.taobao.api.ApiRuleException;
import com.taobao.api.BaseTaobaoRequest;
import com.taobao.api.internal.util.TaobaoHashMap;
import com.taobao.api.internal.util.json.JSONWriter;
import com.taobao.api.response.NlpSemanticTextsAnalyzeResponse;

/**
 * TOP API: taobao.nlp.semantic.texts.analyze request
 * 
 * @author top auto create
 * @since 1.0, 2015.06.12
 */
public class NlpSemanticTextsAnalyzeRequest extends BaseTaobaoRequest<NlpSemanticTextsAnalyzeResponse> {
	
	

	/** 
	* 文本内容
	 */
	private String texts;

	/** 
	* 需要获取的语义分析结果类型，以半角逗号(,)分隔,可以指定获取不同类型值的结果,以(=)号分割。如logistics_speed=0,logistics_speed=1;logistics_speed-物流速度分析;logistics_service-物流服务态度分析;logistics_package-物流包裹破损分析;
	 */
	private String types;

	public void setTexts(String texts) {
		this.texts = texts;
	}

	public void setTexts(List<Text> texts) {
		this.texts = new JSONWriter(false,true).write(texts);
	}

	public String getTexts() {
		return this.texts;
	}

	public void setTypes(String types) {
		this.types = types;
	}

	public String getTypes() {
		return this.types;
	}

	public String getApiMethodName() {
		return "taobao.nlp.semantic.texts.analyze";
	}

	public Map<String, String> getTextParams() {		
		TaobaoHashMap txtParams = new TaobaoHashMap();
		txtParams.put("texts", this.texts);
		txtParams.put("types", this.types);
		if(this.udfParams != null) {
			txtParams.putAll(this.udfParams);
		}
		return txtParams;
	}

	public Class<NlpSemanticTextsAnalyzeResponse> getResponseClass() {
		return NlpSemanticTextsAnalyzeResponse.class;
	}

	public void check() throws ApiRuleException {
		RequestCheckUtils.checkObjectMaxListSize(texts, 50, "texts");
	}
	

}