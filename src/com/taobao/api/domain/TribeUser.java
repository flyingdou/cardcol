package com.taobao.api.domain;

import com.taobao.api.internal.mapping.ApiField;
import com.taobao.api.TaobaoObject;


/**
 * OPENIM群成员信息
 *
 * @author top auto create
 * @since 1.0, null
 */
public class TribeUser extends TaobaoObject {

	private static final long serialVersionUID = 5138227724861934557L;

	/**
	 * 账户appkey
	 */
	@ApiField("app_key")
	private String appKey;

	/**
	 * 群成员角色
	 */
	@ApiField("role")
	private String role;

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

	public String getRole() {
		return this.role;
	}
	public void setRole(String role) {
		this.role = role;
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
