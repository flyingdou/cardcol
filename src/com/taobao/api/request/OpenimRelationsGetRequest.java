package com.taobao.api.request;

import com.taobao.api.domain.OpenImUser;
import com.taobao.api.internal.util.RequestCheckUtils;
import java.util.Map;

import com.taobao.api.ApiRuleException;
import com.taobao.api.BaseTaobaoRequest;
import com.taobao.api.internal.util.TaobaoHashMap;
import com.taobao.api.internal.util.json.JSONWriter;
import com.taobao.api.response.OpenimRelationsGetResponse;

/**
 * TOP API: taobao.openim.relations.get request
 * 
 * @author top auto create
 * @since 1.0, 2015.06.17
 */
public class OpenimRelationsGetRequest extends BaseTaobaoRequest<OpenimRelationsGetResponse> {
	
	

	/** 
	* 查询起始日期。格式yyyyMMdd。不得早于一个月
	 */
	private String begDate;

	/** 
	* 查询结束日期。格式yyyyMMdd。不得早于一个月
	 */
	private String endDate;

	/** 
	* 用户信息
	 */
	private String user;

	public void setBegDate(String begDate) {
		this.begDate = begDate;
	}

	public String getBegDate() {
		return this.begDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getEndDate() {
		return this.endDate;
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
		return "taobao.openim.relations.get";
	}

	public Map<String, String> getTextParams() {		
		TaobaoHashMap txtParams = new TaobaoHashMap();
		txtParams.put("beg_date", this.begDate);
		txtParams.put("end_date", this.endDate);
		txtParams.put("user", this.user);
		if(this.udfParams != null) {
			txtParams.putAll(this.udfParams);
		}
		return txtParams;
	}

	public Class<OpenimRelationsGetResponse> getResponseClass() {
		return OpenimRelationsGetResponse.class;
	}

	public void check() throws ApiRuleException {
		RequestCheckUtils.checkNotEmpty(begDate, "begDate");
		RequestCheckUtils.checkNotEmpty(endDate, "endDate");
	}
	

}