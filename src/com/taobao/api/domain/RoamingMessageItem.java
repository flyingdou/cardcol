package com.taobao.api.domain;

import com.taobao.api.internal.mapping.ApiField;
import com.taobao.api.TaobaoObject;


/**
 * 消息节点
 *
 * @author top auto create
 * @since 1.0, null
 */
public class RoamingMessageItem extends TaobaoObject {

	private static final long serialVersionUID = 2728753719332183885L;

	/**
	 * 节点类型
	 */
	@ApiField("type")
	private String type;

	/**
	 * 值
	 */
	@ApiField("value")
	private String value;


	public String getType() {
		return this.type;
	}
	public void setType(String type) {
		this.type = type;
	}

	public String getValue() {
		return this.value;
	}
	public void setValue(String value) {
		this.value = value;
	}

}
