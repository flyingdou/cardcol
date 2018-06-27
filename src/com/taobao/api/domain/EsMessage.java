package com.taobao.api.domain;

import java.util.List;
import com.taobao.api.internal.mapping.ApiField;
import com.taobao.api.TaobaoObject;
import com.taobao.api.internal.mapping.ApiListField;


/**
 * openim应用消息
 *
 * @author top auto create
 * @since 1.0, null
 */
public class EsMessage extends TaobaoObject {

	private static final long serialVersionUID = 4375949245372426467L;

	/**
	 * 消息内容
	 */
	@ApiListField("content")
	@ApiField("roaming_message_item")
	private List<RoamingMessageItem> content;

	/**
	 * 发送方
	 */
	@ApiField("from_id")
	private OpenImUser fromId;

	/**
	 * 消息时间，UTC时间
	 */
	@ApiField("time")
	private Long time;

	/**
	 * 接收方
	 */
	@ApiField("to_id")
	private OpenImUser toId;

	/**
	 * 消息类型
	 */
	@ApiField("type")
	private Long type;

	/**
	 * 消息UUID
	 */
	@ApiField("uuid")
	private Long uuid;


	public List<RoamingMessageItem> getContent() {
		return this.content;
	}
	public void setContent(List<RoamingMessageItem> content) {
		this.content = content;
	}

	public OpenImUser getFromId() {
		return this.fromId;
	}
	public void setFromId(OpenImUser fromId) {
		this.fromId = fromId;
	}

	public Long getTime() {
		return this.time;
	}
	public void setTime(Long time) {
		this.time = time;
	}

	public OpenImUser getToId() {
		return this.toId;
	}
	public void setToId(OpenImUser toId) {
		this.toId = toId;
	}

	public Long getType() {
		return this.type;
	}
	public void setType(Long type) {
		this.type = type;
	}

	public Long getUuid() {
		return this.uuid;
	}
	public void setUuid(Long uuid) {
		this.uuid = uuid;
	}

}
