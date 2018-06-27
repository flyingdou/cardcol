package com.taobao.api.response;

import com.taobao.api.internal.mapping.ApiField;

import com.taobao.api.TaobaoResponse;

/**
 * TOP API: taobao.openim.tribelogs.import response.
 * 
 * @author top auto create
 * @since 1.0, null
 */
public class OpenimTribelogsImportResponse extends TaobaoResponse {

	private static final long serialVersionUID = 1188568155148129772L;

	/** 
	 * 错误信息
	 */
	@ApiField("message")
	private String message;

	/** 
	 * 错误码
	 */
	@ApiField("ret")
	private Long ret;

	/** 
	 * 是否成功
	 */
	@ApiField("succ")
	private Boolean succ;


	public void setMessage(String message) {
		this.message = message;
	}
	public String getMessage( ) {
		return this.message;
	}

	public void setRet(Long ret) {
		this.ret = ret;
	}
	public Long getRet( ) {
		return this.ret;
	}

	public void setSucc(Boolean succ) {
		this.succ = succ;
	}
	public Boolean getSucc( ) {
		return this.succ;
	}
	


}
