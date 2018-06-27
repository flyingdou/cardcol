package com.taobao.api.response;

import java.util.List;
import com.taobao.api.internal.mapping.ApiField;
import com.taobao.api.domain.Userinfos;
import com.taobao.api.internal.mapping.ApiListField;

import com.taobao.api.TaobaoResponse;

/**
 * TOP API: taobao.openim.users.get response.
 * 
 * @author top auto create
 * @since 1.0, null
 */
public class OpenimUsersGetResponse extends TaobaoResponse {

	private static final long serialVersionUID = 1517783186955953961L;

	/** 
	 * 获取的用户信息列表
	 */
	@ApiListField("userinfos")
	@ApiField("userinfos")
	private List<Userinfos> userinfos;


	public void setUserinfos(List<Userinfos> userinfos) {
		this.userinfos = userinfos;
	
	}
	public List<Userinfos> getUserinfos( ) {
		return this.userinfos;
	}
	


}
