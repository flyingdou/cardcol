package com.taobao.api.response;

import com.taobao.api.internal.mapping.ApiField;
import com.taobao.api.domain.ProcessResult;

import com.taobao.api.TaobaoResponse;

/**
 * TOP API: taobao.nlp.preprocess response.
 * 
 * @author top auto create
 * @since 1.0, null
 */
public class NlpPreprocessResponse extends TaobaoResponse {

	private static final long serialVersionUID = 3733789964984599926L;

	/** 
	 * 返回结果
	 */
	@ApiField("processresult")
	private ProcessResult processresult;


	public void setProcessresult(ProcessResult processresult) {
		this.processresult = processresult;
	}
	public ProcessResult getProcessresult( ) {
		return this.processresult;
	}
	


}
