package com.taobao.api.request;

import com.taobao.api.domain.OpenImUser;
import com.taobao.api.internal.util.RequestCheckUtils;
import java.util.Map;

import com.taobao.api.ApiRuleException;
import com.taobao.api.BaseTaobaoRequest;
import com.taobao.api.internal.util.TaobaoHashMap;
import com.taobao.api.internal.util.json.JSONWriter;
import com.taobao.api.response.OpenimTribeSetmanagerResponse;

/**
 * TOP API: taobao.openim.tribe.setmanager request
 * 
 * @author top auto create
 * @since 1.0, 2015.08.26
 */
public class OpenimTribeSetmanagerRequest extends BaseTaobaoRequest<OpenimTribeSetmanagerResponse> {
	
	

	/** 
	* 用户信息
	 */
	private String member;

	/** 
	* 群id
	 */
	private Long tid;

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

	public void setTid(Long tid) {
		this.tid = tid;
	}

	public Long getTid() {
		return this.tid;
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
		return "taobao.openim.tribe.setmanager";
	}

	public Map<String, String> getTextParams() {		
		TaobaoHashMap txtParams = new TaobaoHashMap();
		txtParams.put("member", this.member);
		txtParams.put("tid", this.tid);
		txtParams.put("user", this.user);
		if(this.udfParams != null) {
			txtParams.putAll(this.udfParams);
		}
		return txtParams;
	}

	public Class<OpenimTribeSetmanagerResponse> getResponseClass() {
		return OpenimTribeSetmanagerResponse.class;
	}

	public void check() throws ApiRuleException {
		RequestCheckUtils.checkNotEmpty(tid, "tid");
	}
	

}