package com.taobao.api.domain;

import java.util.List;
import com.taobao.api.internal.mapping.ApiField;
import com.taobao.api.TaobaoObject;
import com.taobao.api.internal.mapping.ApiListField;


/**
 * 应用消息查询结果
 *
 * @author top auto create
 * @since 1.0, null
 */
public class EsMessageResult extends TaobaoObject {

	private static final long serialVersionUID = 4416548367553186886L;

	/**
	 * 消息序列
	 */
	@ApiListField("messages")
	@ApiField("es_message")
	private List<EsMessage> messages;

	/**
	 * nextkey
	 */
	@ApiField("next_key")
	private String nextKey;


	public List<EsMessage> getMessages() {
		return this.messages;
	}
	public void setMessages(List<EsMessage> messages) {
		this.messages = messages;
	}

	public String getNextKey() {
		return this.nextKey;
	}
	public void setNextKey(String nextKey) {
		this.nextKey = nextKey;
	}

}
