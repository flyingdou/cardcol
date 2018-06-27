package com.taobao.api.response;

import java.util.List;
import com.taobao.api.internal.mapping.ApiField;
import com.taobao.api.domain.TribeInfo;
import com.taobao.api.internal.mapping.ApiListField;

import com.taobao.api.TaobaoResponse;

/**
 * TOP API: taobao.openim.tribe.getalltribes response.
 * 
 * @author top auto create
 * @since 1.0, null
 */
public class OpenimTribeGetalltribesResponse extends TaobaoResponse {

	private static final long serialVersionUID = 2676187669478543531L;

	/** 
	 * 群列表信息
	 */
	@ApiListField("tribe_info_list")
	@ApiField("tribe_info")
	private List<TribeInfo> tribeInfoList;


	public void setTribeInfoList(List<TribeInfo> tribeInfoList) {
		this.tribeInfoList = tribeInfoList;
	}
	public List<TribeInfo> getTribeInfoList( ) {
		return this.tribeInfoList;
	}
	


}
