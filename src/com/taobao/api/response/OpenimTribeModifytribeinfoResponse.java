package com.taobao.api.response;

import com.taobao.api.internal.mapping.ApiField;

import com.taobao.api.TaobaoResponse;

/**
 * TOP API: taobao.openim.tribe.modifytribeinfo response.
 * 
 * @author top auto create
 * @since 1.0, null
 */
public class OpenimTribeModifytribeinfoResponse extends TaobaoResponse {

	private static final long serialVersionUID = 8853848895267293642L;

	/** 
	 * 群服务code
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
