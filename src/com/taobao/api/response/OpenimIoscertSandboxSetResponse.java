package com.taobao.api.response;

import com.taobao.api.internal.mapping.ApiField;

import com.taobao.api.TaobaoResponse;

/**
 * TOP API: taobao.openim.ioscert.sandbox.set response.
 * 
 * @author top auto create
 * @since 1.0, null
 */
public class OpenimIoscertSandboxSetResponse extends TaobaoResponse {

	private static final long serialVersionUID = 6152691149739129164L;

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
