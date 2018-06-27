package com.taobao.api.response;

import com.taobao.api.internal.mapping.ApiField;

import com.taobao.api.TaobaoResponse;

/**
 * TOP API: taobao.openim.tribe.setmembernick response.
 * 
 * @author top auto create
 * @since 1.0, null
 */
public class OpenimTribeSetmembernickResponse extends TaobaoResponse {

	private static final long serialVersionUID = 7722319637219821548L;

	/** 
	 * 是否成功
	 */
	@ApiField("tribe_code")
	private Long tribeCode;


	public void setTribeCode(Long tribeCode) {
		this.tribeCode = tribeCode;
	}
	public Long getTribeCode( ) {
		return this.tribeCode;
	}
	


}
