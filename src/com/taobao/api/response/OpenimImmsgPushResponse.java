package com.taobao.api.response;

import com.taobao.api.internal.mapping.ApiField;

import com.taobao.api.TaobaoResponse;

/**
 * TOP API: taobao.openim.immsg.push response.
 * 
 * @author top auto create
 * @since 1.0, null
 */
public class OpenimImmsgPushResponse extends TaobaoResponse {

	private static final long serialVersionUID = 4175995489592824485L;

	/** 
	 * 消息id，方便定位问题
	 */
	@ApiField("msgid")
	private Long msgid;


	public void setMsgid(Long msgid) {
		this.msgid = msgid;
	}
	public Long getMsgid( ) {
		return this.msgid;
	}
	


}
