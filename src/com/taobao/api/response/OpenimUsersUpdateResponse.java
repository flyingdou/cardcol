package com.taobao.api.response;

import java.util.List;
import com.taobao.api.internal.mapping.ApiField;
import com.taobao.api.internal.mapping.ApiListField;

import com.taobao.api.TaobaoResponse;

/**
 * TOP API: taobao.openim.users.update response.
 * 
 * @author top auto create
 * @since 1.0, null
 */
public class OpenimUsersUpdateResponse extends TaobaoResponse {

	private static final long serialVersionUID = 5649611467133616192L;

	/** 
	 * 失败的uid列表
	 */
	@ApiListField("uid_fail")
	@ApiField("string")
	private List<String> uidFail;

	/** 
	 * 成功的uid列表
	 */
	@ApiListField("uid_succ")
	@ApiField("string")
	private List<String> uidSucc;


	public void setUidFail(List<String> uidFail) {
		this.uidFail = uidFail;
	}
	public List<String> getUidFail( ) {
		return this.uidFail;
	}

	public void setUidSucc(List<String> uidSucc) {
		this.uidSucc = uidSucc;
	}
	public List<String> getUidSucc( ) {
		return this.uidSucc;
	}
	


}
