package com.taobao.api.response;

import com.taobao.api.internal.mapping.ApiField;

import com.taobao.api.TaobaoResponse;

/**
 * TOP API: alibaba.dtdream.test.delete response.
 * 
 * @author top auto create
 * @since 1.0, null
 */
public class AlibabaDtdreamTestDeleteResponse extends TaobaoResponse {

	private static final long serialVersionUID = 8353768984855924727L;

	/** 
	 * id
	 */
	@ApiField("ret_id")
	private String retId;

	/** 
	 * 返回信息
	 */
	@ApiField("ret_msg")
	private String retMsg;


	public void setRetId(String retId) {
		this.retId = retId;
	}
	public String getRetId( ) {
		return this.retId;
	}

	public void setRetMsg(String retMsg) {
		this.retMsg = retMsg;
	}
	public String getRetMsg( ) {
		return this.retMsg;
	}
	


}
