package com.taobao.api.request;

import com.taobao.api.domain.OpenImUser;
import com.taobao.api.internal.util.RequestCheckUtils;
import java.util.Map;

import com.taobao.api.ApiRuleException;
import com.taobao.api.BaseTaobaoRequest;
import com.taobao.api.internal.util.TaobaoHashMap;
import com.taobao.api.internal.util.json.JSONWriter;
import com.taobao.api.response.OpenimChatlogsGetResponse;

/**
 * TOP API: taobao.openim.chatlogs.get request
 * 
 * @author top auto create
 * @since 1.0, 2015.06.16
 */
public class OpenimChatlogsGetRequest extends BaseTaobaoRequest<OpenimChatlogsGetResponse> {
	
	

	/** 
	* 查询开始时间（UTC时间）
	 */
	private Long begin;

	/** 
	* 查询条数
	 */
	private Long count;

	/** 
	* 查询结束时间（UTC时间）
	 */
	private Long end;

	/** 
	* 迭代key
	 */
	private String nextKey;

	/** 
	* 用户1信息
	 */
	private String user1;

	/** 
	* 用户2信息
	 */
	private String user2;

	public void setBegin(Long begin) {
		this.begin = begin;
	}

	public Long getBegin() {
		return this.begin;
	}

	public void setCount(Long count) {
		this.count = count;
	}

	public Long getCount() {
		return this.count;
	}

	public void setEnd(Long end) {
		this.end = end;
	}

	public Long getEnd() {
		return this.end;
	}

	public void setNextKey(String nextKey) {
		this.nextKey = nextKey;
	}

	public String getNextKey() {
		return this.nextKey;
	}

	public void setUser1(String user1) {
		this.user1 = user1;
	}

	public void setUser1(OpenImUser user1) {
		this.user1 = new JSONWriter(false,true).write(user1);
	}

	public String getUser1() {
		return this.user1;
	}

	public void setUser2(String user2) {
		this.user2 = user2;
	}

	public void setUser2(OpenImUser user2) {
		this.user2 = new JSONWriter(false,true).write(user2);
	}

	public String getUser2() {
		return this.user2;
	}

	public String getApiMethodName() {
		return "taobao.openim.chatlogs.get";
	}

	public Map<String, String> getTextParams() {		
		TaobaoHashMap txtParams = new TaobaoHashMap();
		txtParams.put("begin", this.begin);
		txtParams.put("count", this.count);
		txtParams.put("end", this.end);
		txtParams.put("next_key", this.nextKey);
		txtParams.put("user1", this.user1);
		txtParams.put("user2", this.user2);
		if(this.udfParams != null) {
			txtParams.putAll(this.udfParams);
		}
		return txtParams;
	}

	public Class<OpenimChatlogsGetResponse> getResponseClass() {
		return OpenimChatlogsGetResponse.class;
	}

	public void check() throws ApiRuleException {
		RequestCheckUtils.checkNotEmpty(begin, "begin");
		RequestCheckUtils.checkNotEmpty(count, "count");
		RequestCheckUtils.checkNotEmpty(end, "end");
	}
	

}