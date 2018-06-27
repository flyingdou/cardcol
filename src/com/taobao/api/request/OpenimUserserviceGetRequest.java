package com.taobao.api.request;

import java.util.Date;
import com.taobao.api.internal.util.RequestCheckUtils;
import java.util.Map;

import com.taobao.api.ApiRuleException;
import com.taobao.api.BaseTaobaoRequest;
import com.taobao.api.internal.util.TaobaoHashMap;

import com.taobao.api.response.OpenimUserserviceGetResponse;

/**
 * TOP API: taobao.openim.userservice.get request
 * 
 * @author top auto create
 * @since 1.0, 2016.03.07
 */
public class OpenimUserserviceGetRequest extends BaseTaobaoRequest<OpenimUserserviceGetResponse> {
	
	

	/** 
	* 某一天
	 */
	private Date date;

	/** 
	* 当前页
	 */
	private Long pageNo;

	/** 
	* 页面记录数
	 */
	private Long pageSize;

	public void setDate(Date date) {
		this.date = date;
	}

	public Date getDate() {
		return this.date;
	}

	public void setPageNo(Long pageNo) {
		this.pageNo = pageNo;
	}

	public Long getPageNo() {
		return this.pageNo;
	}

	public void setPageSize(Long pageSize) {
		this.pageSize = pageSize;
	}

	public Long getPageSize() {
		return this.pageSize;
	}

	public String getApiMethodName() {
		return "taobao.openim.userservice.get";
	}

	public Map<String, String> getTextParams() {		
		TaobaoHashMap txtParams = new TaobaoHashMap();
		txtParams.put("date", this.date);
		txtParams.put("page_no", this.pageNo);
		txtParams.put("page_size", this.pageSize);
		if(this.udfParams != null) {
			txtParams.putAll(this.udfParams);
		}
		return txtParams;
	}

	public Class<OpenimUserserviceGetResponse> getResponseClass() {
		return OpenimUserserviceGetResponse.class;
	}

	public void check() throws ApiRuleException {
		RequestCheckUtils.checkNotEmpty(date, "date");
	}
	

}