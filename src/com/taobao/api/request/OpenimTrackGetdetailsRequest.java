package com.taobao.api.request;

import com.taobao.api.internal.util.RequestCheckUtils;
import java.util.Map;

import com.taobao.api.ApiRuleException;
import com.taobao.api.BaseTaobaoRequest;
import com.taobao.api.internal.util.TaobaoHashMap;

import com.taobao.api.response.OpenimTrackGetdetailsResponse;

/**
 * TOP API: taobao.openim.track.getdetails request
 * 
 * @author top auto create
 * @since 1.0, 2015.11.03
 */
public class OpenimTrackGetdetailsRequest extends BaseTaobaoRequest<OpenimTrackGetdetailsResponse> {
	
	

	/** 
	* 查询结束时间(UTC时间，以毫秒为单位)
	 */
	private String endtime;

	/** 
	* 用户所属app的prefix
	 */
	private String prefix;

	/** 
	* 查询开始时间(UTC时间，以毫秒为单位)
	 */
	private String starttime;

	/** 
	* 用户ID
	 */
	private String uid;

	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}

	public String getEndtime() {
		return this.endtime;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public String getPrefix() {
		return this.prefix;
	}

	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}

	public String getStarttime() {
		return this.starttime;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getUid() {
		return this.uid;
	}

	public String getApiMethodName() {
		return "taobao.openim.track.getdetails";
	}

	public Map<String, String> getTextParams() {		
		TaobaoHashMap txtParams = new TaobaoHashMap();
		txtParams.put("endtime", this.endtime);
		txtParams.put("prefix", this.prefix);
		txtParams.put("starttime", this.starttime);
		txtParams.put("uid", this.uid);
		if(this.udfParams != null) {
			txtParams.putAll(this.udfParams);
		}
		return txtParams;
	}

	public Class<OpenimTrackGetdetailsResponse> getResponseClass() {
		return OpenimTrackGetdetailsResponse.class;
	}

	public void check() throws ApiRuleException {
		RequestCheckUtils.checkNotEmpty(endtime, "endtime");
		RequestCheckUtils.checkNotEmpty(starttime, "starttime");
		RequestCheckUtils.checkNotEmpty(uid, "uid");
	}
	

}