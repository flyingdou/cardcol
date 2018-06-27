package com.taobao.api.response;

import com.taobao.api.domain.RoamingMessageResult;
import com.taobao.api.internal.mapping.ApiField;

import com.taobao.api.TaobaoResponse;

/**
 * TOP API: taobao.openim.chatlogs.get response.
 * 
 * @author top auto create
 * @since 1.0, null
 */
public class OpenimChatlogsGetResponse extends TaobaoResponse {

	private static final long serialVersionUID = 5629173548379121534L;

	/** 
	 * 聊天记录查询结果
	 */
	@ApiField("result")
	private RoamingMessageResult result;


	public void setResult(RoamingMessageResult result) {
		this.result = result;
	}
	public RoamingMessageResult getResult( ) {
		return this.result;
	}
	


}
