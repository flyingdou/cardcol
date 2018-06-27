package com.taobao.api.response;

import java.util.List;
import com.taobao.api.internal.mapping.ApiField;
import com.taobao.api.internal.mapping.ApiListField;

import com.taobao.api.TaobaoResponse;

/**
 * TOP API: taobao.openim.users.delete response.
 * 
 * @author top auto create
 * @since 1.0, null
 */
public class OpenimUsersDeleteResponse extends TaobaoResponse {

	private static final long serialVersionUID = 5569758691189434136L;

	/** 
	 * 操作成功
	 */
	@ApiListField("result")
	@ApiField("string")
	private List<String> result;


	public void setResult(List<String> result) {
		this.result = result;
	}
	public List<String> getResult( ) {
		return this.result;
	}
	


}
