package com.taobao.api.request;

import com.taobao.api.internal.util.RequestCheckUtils;
import java.util.Map;

import com.taobao.api.ApiRuleException;
import com.taobao.api.BaseTaobaoRequest;
import com.taobao.api.internal.util.TaobaoHashMap;

import com.taobao.api.response.OpenimTribelogsGetResponse;

/**
 * TOP API: taobao.openim.tribelogs.get request
 * 
 * @author top auto create
 * @since 1.0, 2015.12.24
 */
public class OpenimTribelogsGetRequest extends BaseTaobaoRequest<OpenimTribelogsGetResponse> {
	
	

	/** 
	* 查询起始时间，UTC秒数。必须在一个月内。
	 */
	private Long begin;

	/** 
	* 查询条数
	 */
	private Long count;

	/** 
	* 查询结束时间，UTC秒数。必须大于起始时间并小于当前时间
	 */
	private Long end;

	/** 
	* 迭代key
	 */
	private String next;

	/** 
	* 群号
	 */
	private String tribeId;

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

	public void setNext(String next) {
		this.next = next;
	}

	public String getNext() {
		return this.next;
	}

	public void setTribeId(String tribeId) {
		this.tribeId = tribeId;
	}

	public String getTribeId() {
		return this.tribeId;
	}

	public String getApiMethodName() {
		return "taobao.openim.tribelogs.get";
	}

	public Map<String, String> getTextParams() {		
		TaobaoHashMap txtParams = new TaobaoHashMap();
		txtParams.put("begin", this.begin);
		txtParams.put("count", this.count);
		txtParams.put("end", this.end);
		txtParams.put("next", this.next);
		txtParams.put("tribe_id", this.tribeId);
		if(this.udfParams != null) {
			txtParams.putAll(this.udfParams);
		}
		return txtParams;
	}

	public Class<OpenimTribelogsGetResponse> getResponseClass() {
		return OpenimTribelogsGetResponse.class;
	}

	public void check() throws ApiRuleException {
		RequestCheckUtils.checkNotEmpty(begin, "begin");
		RequestCheckUtils.checkNotEmpty(count, "count");
		RequestCheckUtils.checkNotEmpty(end, "end");
		RequestCheckUtils.checkNotEmpty(tribeId, "tribeId");
	}
	

}