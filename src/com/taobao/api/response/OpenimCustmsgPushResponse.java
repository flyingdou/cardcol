package com.taobao.api.response;

import com.taobao.api.internal.mapping.ApiField;

import com.taobao.api.TaobaoResponse;

/**
 * TOP API: taobao.openim.custmsg.push response.
 * 
 * @author top auto create
 * @since 1.0, null
 */
public class OpenimCustmsgPushResponse extends TaobaoResponse {

	private static final long serialVersionUID = 8761996658679345462L;

	/** 
	 * 消息id，用于定位问题
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
