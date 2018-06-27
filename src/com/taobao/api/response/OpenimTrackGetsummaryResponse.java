package com.taobao.api.response;

import com.taobao.api.domain.Tracksummary;
import com.taobao.api.internal.mapping.ApiField;

import com.taobao.api.TaobaoResponse;

/**
 * TOP API: taobao.openim.track.getsummary response.
 * 
 * @author top auto create
 * @since 1.0, null
 */
public class OpenimTrackGetsummaryResponse extends TaobaoResponse {

	private static final long serialVersionUID = 1631175117876936816L;

	/** 
	 * 用户最近访问信息
	 */
	@ApiField("tracksummary")
	private Tracksummary tracksummary;


	public void setTracksummary(Tracksummary tracksummary) {
		this.tracksummary = tracksummary;
	}
	public Tracksummary getTracksummary( ) {
		return this.tracksummary;
	}
	


}
