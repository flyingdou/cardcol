package com.taobao.api.response;

import com.taobao.api.domain.EsMessageResult;
import com.taobao.api.internal.mapping.ApiField;

import com.taobao.api.TaobaoResponse;

/**
 * TOP API: taobao.openim.app.chatlogs.get response.
 * 
 * @author top auto create
 * @since 1.0, null
 */
public class OpenimAppChatlogsGetResponse extends TaobaoResponse {

	private static final long serialVersionUID = 1246864437178285539L;

	/** 
	 * 查询结果
	 */
	@ApiField("result")
	private EsMessageResult result;


	public void setResult(EsMessageResult result) {
		this.result = result;
	}
	public EsMessageResult getResult( ) {
		return this.result;
	}
	


}
