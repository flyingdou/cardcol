package com.taobao.api.response;

import com.taobao.api.internal.mapping.ApiField;

import com.taobao.api.TaobaoResponse;

/**
 * TOP API: taobao.baichuan.user.logindoublecheck response.
 * 
 * @author top auto create
 * @since 1.0, null
 */
public class BaichuanUserLogindoublecheckResponse extends TaobaoResponse {

	private static final long serialVersionUID = 3733693953749257732L;

	/** 
	 * name
	 */
	@ApiField("name")
	private String name;


	public void setName(String name) {
		this.name = name;
	}
	public String getName( ) {
		return this.name;
	}
	


}
