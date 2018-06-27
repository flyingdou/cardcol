package com.taobao.api.domain;

import com.taobao.api.internal.mapping.ApiField;
import com.taobao.api.TaobaoObject;


/**
 * 用户信息
 *
 * @author top auto create
 * @since 1.0, null
 */
public class OpenImUser extends TaobaoObject {

	private static final long serialVersionUID = 7749575552839814495L;

	/**
	 * 账户appkey
	 */
	@ApiField("app_key")
	private String appKey;

	/**
	 * 是否为淘宝账号
	 */
	@ApiField("taobao_account")
	private Boolean taobaoAccount;

	/**
	 * 用户id
	 */
	@ApiField("uid")
	private String uid;


	public String getAppKey() {
		return this.appKey;
	}
	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}

	public Boolean getTaobaoAccount() {
		return this.taobaoAccount;
	}
	public void setTaobaoAccount(Boolean taobaoAccount) {
		this.taobaoAccount = taobaoAccount;
	}

	public String getUid() {
		return this.uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}

}
