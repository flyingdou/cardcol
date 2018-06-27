package com.taobao.api.request;

import java.util.List;
import com.taobao.api.domain.OpenImUser;
import com.taobao.api.internal.util.RequestCheckUtils;
import java.util.Map;

import com.taobao.api.ApiRuleException;
import com.taobao.api.BaseTaobaoRequest;
import com.taobao.api.internal.util.TaobaoHashMap;
import com.taobao.api.internal.util.json.JSONWriter;
import com.taobao.api.response.OpenimTribeInviteResponse;

/**
 * TOP API: taobao.openim.tribe.invite request
 * 
 * @author top auto create
 * @since 1.0, 2015.10.10
 */
public class OpenimTribeInviteRequest extends BaseTaobaoRequest<OpenimTribeInviteResponse> {
	
	

	/** 
	* 用户信息
	 */
	private String members;

	/** 
	* 群id
	 */
	private Long tribeId;

	/** 
	* 用户信息
	 */
	private String user;

	public void setMembers(String members) {
		this.members = members;
	}

	public void setMembers(List<OpenImUser> members) {
		this.members = new JSONWriter(false,true).write(members);
	}

	public String getMembers() {
		return this.members;
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

	public void setUser(OpenImUser user) {
		this.user = new JSONWriter(false,true).write(user);
	}

	public String getUser() {
		return this.user;
	}

	public String getApiMethodName() {
		return "taobao.openim.tribe.invite";
	}

	public Map<String, String> getTextParams() {		
		TaobaoHashMap txtParams = new TaobaoHashMap();
		txtParams.put("members", this.members);
		txtParams.put("tribe_id", this.tribeId);
		txtParams.put("user", this.user);
		if(this.udfParams != null) {
			txtParams.putAll(this.udfParams);
		}
		return txtParams;
	}

	public Class<OpenimTribeInviteResponse> getResponseClass() {
		return OpenimTribeInviteResponse.class;
	}

	public void check() throws ApiRuleException {
		RequestCheckUtils.checkObjectMaxListSize(members, 1000, "members");
		RequestCheckUtils.checkNotEmpty(tribeId, "tribeId");
	}
	

}