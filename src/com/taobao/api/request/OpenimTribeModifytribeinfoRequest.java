package com.taobao.api.request;

import com.taobao.api.domain.OpenImUser;
import com.taobao.api.internal.util.RequestCheckUtils;
import java.util.Map;

import com.taobao.api.ApiRuleException;
import com.taobao.api.BaseTaobaoRequest;
import com.taobao.api.internal.util.TaobaoHashMap;
import com.taobao.api.internal.util.json.JSONWriter;
import com.taobao.api.response.OpenimTribeModifytribeinfoResponse;

/**
 * TOP API: taobao.openim.tribe.modifytribeinfo request
 * 
 * @author top auto create
 * @since 1.0, 2015.08.26
 */
public class OpenimTribeModifytribeinfoRequest extends BaseTaobaoRequest<OpenimTribeModifytribeinfoResponse> {
	
	

	/** 
	* 群公告
	 */
	private String notice;

	/** 
	* 群id
	 */
	private Long tribeId;

	/** 
	* 群名称
	 */
	private String tribeName;

	/** 
	* 用户信息
	 */
	private String user;

	public void setNotice(String notice) {
		this.notice = notice;
	}

	public String getNotice() {
		return this.notice;
	}

	public void setTribeId(Long tribeId) {
		this.tribeId = tribeId;
	}

	public Long getTribeId() {
		return this.tribeId;
	}

	public void setTribeName(String tribeName) {
		this.tribeName = tribeName;
	}

	public String getTribeName() {
		return this.tribeName;
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
		return "taobao.openim.tribe.modifytribeinfo";
	}

	public Map<String, String> getTextParams() {		
		TaobaoHashMap txtParams = new TaobaoHashMap();
		txtParams.put("notice", this.notice);
		txtParams.put("tribe_id", this.tribeId);
		txtParams.put("tribe_name", this.tribeName);
		txtParams.put("user", this.user);
		if(this.udfParams != null) {
			txtParams.putAll(this.udfParams);
		}
		return txtParams;
	}

	public Class<OpenimTribeModifytribeinfoResponse> getResponseClass() {
		return OpenimTribeModifytribeinfoResponse.class;
	}

	public void check() throws ApiRuleException {
		RequestCheckUtils.checkNotEmpty(notice, "notice");
		RequestCheckUtils.checkNotEmpty(tribeId, "tribeId");
		RequestCheckUtils.checkNotEmpty(tribeName, "tribeName");
	}
	

}