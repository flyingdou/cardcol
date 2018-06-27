package com.taobao.api.domain;

import java.util.List;
import com.taobao.api.internal.mapping.ApiField;
import com.taobao.api.TaobaoObject;
import com.taobao.api.internal.mapping.ApiListField;


/**
 * 漫游消息
 *
 * @author top auto create
 * @since 1.0, null
 */
public class RoamingMessage extends TaobaoObject {

	private static final long serialVersionUID = 3399962587894112762L;

	/**
	 * 消息内容
	 */
	@ApiListField("content_list")
	@ApiField("roaming_message_item")
	private List<RoamingMessageItem> contentList;

	/**
	 * 消息方向。user1 -> user2 = 0 , user2->user1 = 1
	 */
	@ApiField("direction")
	private Long direction;

	/**
	 * 消息时间（UTC时间）
	 */
	@ApiField("time")
	private Long time;

	/**
	 * 消息类型
	 */
	@ApiField("type")
	private Long type;

	/**
	 * 消息唯一ID
	 */
	@ApiField("uuid")
	private Long uuid;


	public List<RoamingMessageItem> getContentList() {
		return this.contentList;
	}
	public void setContentList(List<RoamingMessageItem> contentList) {
		this.contentList = contentList;
	}

	public Long getDirection() {
		return this.direction;
	}
	public void setDirection(Long direction) {
		this.direction = direction;
	}

	public Long getTime() {
		return this.time;
	}
	public void setTime(Long time) {
		this.time = time;
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
