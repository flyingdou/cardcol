package com.taobao.api.response;

import java.util.List;
import com.taobao.api.internal.mapping.ApiField;
import com.taobao.api.domain.OpenImUser;
import com.taobao.api.internal.mapping.ApiListField;

import com.taobao.api.TaobaoResponse;

/**
 * TOP API: taobao.openim.relations.get response.
 * 
 * @author top auto create
 * @since 1.0, null
 */
public class OpenimRelationsGetResponse extends TaobaoResponse {

	private static final long serialVersionUID = 5186685736647336127L;

	/** 
	 * 用户信息列表
	 */
	@ApiListField("users")
	@ApiField("open_im_user")
	private List<OpenImUser> users;


	public void setUsers(List<OpenImUser> users) {
		this.users = users;
	}
	public List<OpenImUser> getUsers( ) {
		return this.users;
	}
	


}
