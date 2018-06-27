package com.taobao.api.request;

import java.util.List;
import com.taobao.api.domain.OpenImUser;
import com.taobao.api.internal.util.RequestCheckUtils;
import java.util.Map;

import com.taobao.api.ApiRuleException;
import com.taobao.api.BaseTaobaoRequest;
import com.taobao.api.internal.util.TaobaoHashMap;
import com.taobao.api.internal.util.json.JSONWriter;
import com.taobao.api.response.OpenimTribeCreateResponse;

/**
 * TOP API: taobao.openim.tribe.create request
 * 
 * @author top auto create
 * @since 1.0, 2015.10.22
 */
public class OpenimTribeCreateRequest extends BaseTaobaoRequest<OpenimTribeCreateResponse> {
	
	

	/** 
	* 创建群时候拉入群的成员tribe_type = 1（即为讨论组类型)时  该参数为必选tribe_type = 0 (即为普通群类型)时，改参数无效，可不填
	 */
	private String members;

	/** 
	* 群公告
	 */
	private String notice;

	/** 
	* 群名称
	 */
	private String tribeName;

	/** 
	* 群类型有两种tribe_type = 0  普通群  普通群有管理员角色，对成员加入有权限控制tribe_type = 1  讨论组  讨论组没有管理员，不能解散
	 */
	private Long tribeType;

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

	public void setNotice(String notice) {
		this.notice = notice;
	}

	public String getNotice() {
		return this.notice;
	}

	public void setTribeName(String tribeName) {
		this.tribeName = tribeName;
	}

	public String getTribeName() {
		return this.tribeName;
	}

	public void setTribeType(Long tribeType) {
		this.tribeType = tribeType;
	}

	public Long getTribeType() {
		return this.tribeType;
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
		return "taobao.openim.tribe.create";
	}

	public Map<String, String> getTextParams() {		
		TaobaoHashMap txtParams = new TaobaoHashMap();
		txtParams.put("members", this.members);
		txtParams.put("notice", this.notice);
		txtParams.put("tribe_name", this.tribeName);
		txtParams.put("tribe_type", this.tribeType);
		txtParams.put("user", this.user);
		if(this.udfParams != null) {
			txtParams.putAll(this.udfParams);
		}
		return txtParams;
	}

	public Class<OpenimTribeCreateResponse> getResponseClass() {
		return OpenimTribeCreateResponse.class;
	}

	public void check() throws ApiRuleException {
		RequestCheckUtils.checkObjectMaxListSize(members, 1000, "members");
		RequestCheckUtils.checkNotEmpty(notice, "notice");
		RequestCheckUtils.checkNotEmpty(tribeName, "tribeName");
		RequestCheckUtils.checkNotEmpty(tribeType, "tribeType");
	}
	

}