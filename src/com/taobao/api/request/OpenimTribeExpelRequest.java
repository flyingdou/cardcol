package com.taobao.api.request;

import com.taobao.api.domain.OpenImUser;
import com.taobao.api.internal.util.RequestCheckUtils;
import java.util.Map;

import com.taobao.api.ApiRuleException;
import com.taobao.api.BaseTaobaoRequest;
import com.taobao.api.internal.util.TaobaoHashMap;
import com.taobao.api.internal.util.json.JSONWriter;
import com.taobao.api.response.OpenimTribeExpelResponse;

/**
 * TOP API: taobao.openim.tribe.expel request
 * 
 * @author top auto create
 * @since 1.0, 2015.08.26
 */
public class OpenimTribeExpelRequest extends BaseTaobaoRequest<OpenimTribeExpelResponse> {
	
	

	/** 
	* 用户信息
	 */
	private String member;

	/** 
	* 群id
	 */
	private Long tribeId;

	/** 
	* 用户信息
	 */
	private String user;

	public void setMember(String member) {
		this.member = member;
	}

	public void setMember(OpenImUser member) {
		this.member = new JSONWriter(false,true).write(member);
	}

	public String getMember() {
		return this.member;
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
		return "taobao.openim.tribe.expel";
	}

	public Map<String, String> getTextParams() {		
		TaobaoHashMap txtParams = new TaobaoHashMap();
		txtParams.put("member", this.member);
		txtParams.put("tribe_id", this.tribeId);
		txtParams.put("user", this.user);
		if(this.udfParams != null) {
			txtParams.putAll(this.udfParams);
		}
		return txtParams;
	}

	public Class<OpenimTribeExpelResponse> getResponseClass() {
		return OpenimTribeExpelResponse.class;
	}

	public void check() throws ApiRuleException {
		RequestCheckUtils.checkNotEmpty(tribeId, "tribeId");
	}
	

}