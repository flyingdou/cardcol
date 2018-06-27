package com.taobao.api.request;

import com.taobao.api.internal.util.RequestCheckUtils;
import java.util.Map;

import com.taobao.api.ApiRuleException;
import com.taobao.api.BaseTaobaoRequest;
import com.taobao.api.internal.util.TaobaoHashMap;

import com.taobao.api.response.OpenimAppChatlogsGetResponse;

/**
 * TOP API: taobao.openim.app.chatlogs.get request
 * 
 * @author top auto create
 * @since 1.0, 2015.09.25
 */
public class OpenimAppChatlogsGetRequest extends BaseTaobaoRequest<OpenimAppChatlogsGetResponse> {
	
	

	/** 
	* 查询结束时间。UTC时间。精度到秒
	 */
	private Long beg;

	/** 
	* 查询最大条数
	 */
	private Long count;

	/** 
	* 查询结束时间。UTC时间。精度到秒
	 */
	private Long end;

	/** 
	* 迭代key
	 */
	private String next;

	public void setBeg(Long beg) {
		this.beg = beg;
	}

	public Long getBeg() {
		return this.beg;
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

	public String getApiMethodName() {
		return "taobao.openim.app.chatlogs.get";
	}

	public Map<String, String> getTextParams() {		
		TaobaoHashMap txtParams = new TaobaoHashMap();
		txtParams.put("beg", this.beg);
		txtParams.put("count", this.count);
		txtParams.put("end", this.end);
		txtParams.put("next", this.next);
		if(this.udfParams != null) {
			txtParams.putAll(this.udfParams);
		}
		return txtParams;
	}

	public Class<OpenimAppChatlogsGetResponse> getResponseClass() {
		return OpenimAppChatlogsGetResponse.class;
	}

	public void check() throws ApiRuleException {
		RequestCheckUtils.checkNotEmpty(beg, "beg");
		RequestCheckUtils.checkNotEmpty(count, "count");
		RequestCheckUtils.checkMaxValue(count, 1000L, "count");
		RequestCheckUtils.checkMinValue(count, 1L, "count");
		RequestCheckUtils.checkNotEmpty(end, "end");
	}
	

}