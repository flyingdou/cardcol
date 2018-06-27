package com.taobao.api.response;

import com.taobao.api.internal.mapping.ApiField;
import com.taobao.api.domain.TribeInfo;

import com.taobao.api.TaobaoResponse;

/**
 * TOP API: taobao.openim.tribe.create response.
 * 
 * @author top auto create
 * @since 1.0, null
 */
public class OpenimTribeCreateResponse extends TaobaoResponse {

	private static final long serialVersionUID = 1559682869378216364L;

	/** 
	 * 创建群的信息
	 */
	@ApiField("tribe_info")
	private TribeInfo tribeInfo;


	public void setTribeInfo(TribeInfo tribeInfo) {
		this.tribeInfo = tribeInfo;
	}
	public TribeInfo getTribeInfo( ) {
		return this.tribeInfo;
	}
	


}
