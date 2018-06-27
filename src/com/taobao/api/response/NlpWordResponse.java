package com.taobao.api.response;

import com.taobao.api.internal.mapping.ApiField;
import com.taobao.api.domain.WordResult;

import com.taobao.api.TaobaoResponse;

/**
 * TOP API: taobao.nlp.word response.
 * 
 * @author top auto create
 * @since 1.0, null
 */
public class NlpWordResponse extends TaobaoResponse {

	private static final long serialVersionUID = 5634782298319496947L;

	/** 
	 * 返回词法分析的结果
	 */
	@ApiField("wordresult")
	private WordResult wordresult;


	public void setWordresult(WordResult wordresult) {
		this.wordresult = wordresult;
	}
	public WordResult getWordresult( ) {
		return this.wordresult;
	}
	


}
