package com.taobao.api.domain;

import com.taobao.api.internal.mapping.ApiField;
import com.taobao.api.TaobaoObject;


/**
 * 文本相似度返回结果
 *
 * @author top auto create
 * @since 1.0, null
 */
public class SimResult extends TaobaoObject {

	private static final long serialVersionUID = 6584729979687218176L;

	/**
	 * 返回文本处理内容
	 */
	@ApiField("top_result")
	private String topResult;

	/**
	 * 返回结果为true则运行成功，为false则运行失败
	 */
	@ApiField("top_status")
	private Boolean topStatus;


	public String getTopResult() {
		return this.topResult;
	}
	public void setTopResult(String topResult) {
		this.topResult = topResult;
	}

	public Boolean getTopStatus() {
		return this.topStatus;
	}
	public void setTopStatus(Boolean topStatus) {
		this.topStatus = topStatus;
	}

}
