package com.taobao.api.domain;

import java.util.List;
import com.taobao.api.internal.mapping.ApiField;
import com.taobao.api.TaobaoObject;
import com.taobao.api.internal.mapping.ApiListField;


/**
 * 消息查询结果
 *
 * @author top auto create
 * @since 1.0, null
 */
public class RoamingMessageResult extends TaobaoObject {

	private static final long serialVersionUID = 1513696794484467466L;

	/**
	 * 消息列表
	 */
	@ApiListField("messages")
	@ApiField("roaming_message")
	private List<RoamingMessage> messages;

	/**
	 * 下次迭代key
	 */
	@ApiField("next_key")
	private String nextKey;


	public List<RoamingMessage> getMessages() {
		return this.messages;
	}
	public void setMessages(List<RoamingMessage> messages) {
		this.messages = messages;
	}

	public String getNextKey() {
		return this.nextKey;
	}
	public void setNextKey(String nextKey) {
		this.nextKey = nextKey;
	}

}
