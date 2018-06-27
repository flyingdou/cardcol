package com.taobao.api.request;

import com.taobao.api.internal.mapping.ApiField;
import com.taobao.api.internal.util.RequestCheckUtils;
import com.taobao.api.TaobaoObject;
import java.util.Map;

import com.taobao.api.ApiRuleException;
import com.taobao.api.BaseTaobaoRequest;
import com.taobao.api.internal.util.TaobaoHashMap;
import com.taobao.api.internal.util.json.JSONWriter;
import com.taobao.api.response.OpenimTribeSetmembernickResponse;

/**
 * TOP API: taobao.openim.tribe.setmembernick request
 * 
 * @author top auto create
 * @since 1.0, 2015.12.28
 */
public class OpenimTribeSetmembernickRequest extends BaseTaobaoRequest<OpenimTribeSetmembernickResponse> {
	
	

	/** 
	* 被设置昵称的群成员
	 */
	private String member;

	/** 
	* 设置的昵称
	 */
	private String nick;

	/** 
	* 群id
	 */
	private Long tribeId;

	/** 
	* 发起设置昵称的操作者，如果是设置其他成员的昵称，只有普通组的群主和管理员有权限
	 */
	private String user;

	public void setMember(String member) {
		this.member = member;
	}

	public void setMember(User member) {
		this.member = new JSONWriter(false,true).write(member);
	}

	public String getMember() {
		return this.member;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public String getNick() {
		return this.nick;
	}

	public void setTribeId(Long tribeId) {
		this.tribeId = tribeId;
	}

	public Long getTribeId() {
		return this.tribeId;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public void setUser(User user) {
		this.user = new JSONWriter(false,true).write(user);
	}

	public String getUser() {
		return this.user;
	}

	public String getApiMethodName() {
		return "taobao.openim.tribe.setmembernick";
	}

	public Map<String, String> getTextParams() {		
		TaobaoHashMap txtParams = new TaobaoHashMap();
		txtParams.put("member", this.member);
		txtParams.put("nick", this.nick);
		txtParams.put("tribe_id", this.tribeId);
		txtParams.put("user", this.user);
		if(this.udfParams != null) {
			txtParams.putAll(this.udfParams);
		}
		return txtParams;
	}

	public Class<OpenimTribeSetmembernickResponse> getResponseClass() {
		return OpenimTribeSetmembernickResponse.class;
	}

	public void check() throws ApiRuleException {
		RequestCheckUtils.checkNotEmpty(nick, "nick");
		RequestCheckUtils.checkNotEmpty(tribeId, "tribeId");
	}
	
	/**
	 * 发起设置昵称的操作者，如果是设置其他成员的昵称，只有普通组的群主和管理员有权限
	 *
	 * @author top auto create
	 * @since 1.0, null
	 */
	public static class User extends TaobaoObject {
		private static final long serialVersionUID = 4895827956244595392L;
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
	

}