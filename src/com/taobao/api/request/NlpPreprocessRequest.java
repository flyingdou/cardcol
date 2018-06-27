package com.taobao.api.request;

import com.taobao.api.internal.util.RequestCheckUtils;
import com.taobao.api.domain.Text;
import java.util.Map;

import com.taobao.api.ApiRuleException;
import com.taobao.api.BaseTaobaoRequest;
import com.taobao.api.internal.util.TaobaoHashMap;
import com.taobao.api.internal.util.json.JSONWriter;
import com.taobao.api.response.NlpPreprocessResponse;

/**
 * TOP API: taobao.nlp.preprocess request
 * 
 * @author top auto create
 * @since 1.0, 2016.01.12
 */
public class NlpPreprocessRequest extends BaseTaobaoRequest<NlpPreprocessResponse> {
	
	

	/** 
	* 1)繁简字转换：func_type=1，对应type =1 繁转简 type=2 简转繁；2)拆分字转换：func_type =2，对应type=1 文字拆分 type=2 拆分字合并；3)文字转拼音：func_type =3，对应type=1 文字转拼音 type=2 拼音+声调；4)谐音同音字替换：func_type =4，对应type=1 谐音字替换 type=2 同音字替换；5)形似字替换：func_type =5，对应type=1 形似字替换;
	 */
	private Long funcType;

	/** 
	* 谐音字转换、形似字转换需提供关键词进行替换，关键词之间以#分隔。keyword示例：兼职#招聘#微信、天猫#日结#微信#招聘#加微
	 */
	private String keyword;

	/** 
	* 文本内容
	 */
	private String text;

	public void setFuncType(Long funcType) {
		this.funcType = funcType;
	}

	public Long getFuncType() {
		return this.funcType;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getKeyword() {
		return this.keyword;
	}

	public void setText(String text) {
		this.text = text;
	}

	public void setText(Text text) {
		this.text = new JSONWriter(false,true).write(text);
	}

	public String getText() {
		return this.text;
	}

	public String getApiMethodName() {
		return "taobao.nlp.preprocess";
	}

	public Map<String, String> getTextParams() {		
		TaobaoHashMap txtParams = new TaobaoHashMap();
		txtParams.put("func_type", this.funcType);
		txtParams.put("keyword", this.keyword);
		txtParams.put("text", this.text);
		if(this.udfParams != null) {
			txtParams.putAll(this.udfParams);
		}
		return txtParams;
	}

	public Class<NlpPreprocessResponse> getResponseClass() {
		return NlpPreprocessResponse.class;
	}

	public void check() throws ApiRuleException {
		RequestCheckUtils.checkNotEmpty(funcType, "funcType");
	}
	

}