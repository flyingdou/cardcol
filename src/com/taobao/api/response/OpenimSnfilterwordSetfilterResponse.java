package com.taobao.api.response;

import com.taobao.api.internal.mapping.ApiField;

import com.taobao.api.TaobaoResponse;

/**
 * TOP API: taobao.openim.snfilterword.setfilter response.
 * 
 * @author top auto create
 * @since 1.0, null
 */
public class OpenimSnfilterwordSetfilterResponse extends TaobaoResponse {

	private static final long serialVersionUID = 4799267428888833792L;

	/** 
	 * 成功
	 */
	@ApiField("errid")
	private String errid;

	/** 
	 * 错误原因
	 */
	@ApiField("errmsg")
	private String errmsg;


	public void setErrid(String errid) {
		this.errid = errid;
	}
	public String getErrid( ) {
		return this.errid;
	}

	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}
	public String getErrmsg( ) {
		return this.errmsg;
	}
	


}
