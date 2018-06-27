package com.taobao.api.response;

import com.taobao.api.internal.mapping.ApiField;
import com.taobao.api.domain.SimResult;

import com.taobao.api.TaobaoResponse;

/**
 * TOP API: taobao.nlp.similarity response.
 * 
 * @author top auto create
 * @since 1.0, null
 */
public class NlpSimilarityResponse extends TaobaoResponse {

	private static final long serialVersionUID = 2668471734918644778L;

	/** 
	 * 返回结果
	 */
	@ApiField("simresult")
	private SimResult simresult;


	public void setSimresult(SimResult simresult) {
		this.simresult = simresult;
	}
	public SimResult getSimresult( ) {
		return this.simresult;
	}
	


}
