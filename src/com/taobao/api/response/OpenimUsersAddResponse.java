package com.taobao.api.response;

import java.util.List;
import com.taobao.api.internal.mapping.ApiField;
import com.taobao.api.internal.mapping.ApiListField;

import com.taobao.api.TaobaoResponse;

/**
 * TOP API: taobao.openim.users.add response.
 * 
 * @author top auto create
 * @since 1.0, null
 */
public class OpenimUsersAddResponse extends TaobaoResponse {

	private static final long serialVersionUID = 2416353342967319254L;

	/** 
	 * 成功用户列表
	 */
	@ApiListField("uid_succ")
	@ApiField("string")
	private List<String> uidSucc;


	public void setUidSucc(List<String> uidSucc) {
		this.uidSucc = uidSucc;
	}
	public List<String> getUidSucc( ) {
		return this.uidSucc;
	}
	


}
