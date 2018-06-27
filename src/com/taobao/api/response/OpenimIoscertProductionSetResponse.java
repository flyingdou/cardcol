package com.taobao.api.response;

import com.taobao.api.internal.mapping.ApiField;

import com.taobao.api.TaobaoResponse;

/**
 * TOP API: taobao.openim.ioscert.production.set response.
 * 
 * @author top auto create
 * @since 1.0, null
 */
public class OpenimIoscertProductionSetResponse extends TaobaoResponse {

	private static final long serialVersionUID = 5113343378145966492L;

	/** 
	 * 操作成功
	 */
	@ApiField("code")
	private String code;


	public void setCode(String code) {
		this.code = code;
	}
	public String getCode( ) {
		return this.code;
	}
	


}
