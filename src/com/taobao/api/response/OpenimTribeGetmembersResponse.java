package com.taobao.api.response;

import java.util.List;
import com.taobao.api.internal.mapping.ApiField;
import com.taobao.api.internal.mapping.ApiListField;
import com.taobao.api.domain.TribeUser;

import com.taobao.api.TaobaoResponse;

/**
 * TOP API: taobao.openim.tribe.getmembers response.
 * 
 * @author top auto create
 * @since 1.0, null
 */
public class OpenimTribeGetmembersResponse extends TaobaoResponse {

	private static final long serialVersionUID = 6644938436142889823L;

	/** 
	 * OPENIM群成员列表
	 */
	@ApiListField("tribe_user_list")
	@ApiField("tribe_user")
	private List<TribeUser> tribeUserList;


	public void setTribeUserList(List<TribeUser> tribeUserList) {
		this.tribeUserList = tribeUserList;
	}
	public List<TribeUser> getTribeUserList( ) {
		return this.tribeUserList;
	}
	


}
