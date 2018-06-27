package com.taobao.api.response;

import java.util.List;
import com.taobao.api.internal.mapping.ApiField;
import com.taobao.api.TaobaoObject;
import com.taobao.api.internal.mapping.ApiListField;

import com.taobao.api.TaobaoResponse;

/**
 * TOP API: taobao.openim.tribelogs.get response.
 * 
 * @author top auto create
 * @since 1.0, null
 */
public class OpenimTribelogsGetResponse extends TaobaoResponse {

	private static final long serialVersionUID = 4659463775188242966L;

	/** 
	 * 返回结构
	 */
	@ApiField("data")
	private TribeMessageResult data;

	/** 
	 * 错误信息
	 */
	@ApiField("message")
	private String message;

	/** 
	 * 错误原因
	 */
	@ApiField("reason")
	private String reason;

	/** 
	 * 错误码
	 */
	@ApiField("retCode")
	private Long retCode;

	/** 
	 * 是否成功
	 */
	@ApiField("succ")
	private Boolean succ;


	public void setData(TribeMessageResult data) {
		this.data = data;
	}
	public TribeMessageResult getData( ) {
		return this.data;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	public String getMessage( ) {
		return this.message;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getReason( ) {
		return this.reason;
	}

	public void setRetCode(Long retCode) {
		this.retCode = retCode;
	}
	public Long getRetCode( ) {
		return this.retCode;
	}

	public void setSucc(Boolean succ) {
		this.succ = succ;
	}
	public Boolean getSucc( ) {
		return this.succ;
	}
	
	/**
	 * 消息内容节点序列
	 *
	 * @author top auto create
	 * @since 1.0, null
	 */
	public static class MessageItem extends TaobaoObject {
		private static final long serialVersionUID = 2665396322332963865L;
		/**
		 * 节点类型
		 */
		@ApiField("type")
		private String type;
		/**
		 * 节点值
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
	
	/**
	 * 发送方
	 *
	 * @author top auto create
	 * @since 1.0, null
	 */
	public static class User extends TaobaoObject {
		private static final long serialVersionUID = 3792534219292229832L;
		/**
		 * 发送方所属app
		 */
		@ApiField("app_key")
		private String appKey;
		/**
		 * 是否为淘宝账号
		 */
		@ApiField("taobao_account")
		private Boolean taobaoAccount;
		/**
		 * userid
		 */
		@ApiField("uid")
		private String uid;
	
		public String getAppKey() {
			return this.appKey;
		}
		public void setAppKey(String appKey) {
			this.appKey = appKey;
		}
		public Boolean getTaobaoAccount() {
			return this.taobaoAccount;
		}
		public void setTaobaoAccount(Boolean taobaoAccount) {
			this.taobaoAccount = taobaoAccount;
		}
		public String getUid() {
			return this.uid;
		}
		public void setUid(String uid) {
			this.uid = uid;
		}
	}
	
	/**
	 * 消息列表
	 *
	 * @author top auto create
	 * @since 1.0, null
	 */
	public static class TribeMessage extends TaobaoObject {
		private static final long serialVersionUID = 4385582256275495312L;
		/**
		 * 消息内容节点序列
		 */
		@ApiListField("content")
		@ApiField("message_item")
		private List<MessageItem> content;
		/**
		 * 发送方
		 */
		@ApiField("from_id")
		private User fromId;
		/**
		 * 消息时间。UTC时间
		 */
		@ApiField("time")
		private Long time;
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
	
		public List<MessageItem> getContent() {
			return this.content;
		}
		public void setContent(List<MessageItem> content) {
			this.content = content;
		}
		public User getFromId() {
			return this.fromId;
		}
		public void setFromId(User fromId) {
			this.fromId = fromId;
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
	
	/**
	 * 返回结构
	 *
	 * @author top auto create
	 * @since 1.0, null
	 */
	public static class TribeMessageResult extends TaobaoObject {
		private static final long serialVersionUID = 1467282768127658342L;
		/**
		 * 消息列表
		 */
		@ApiListField("messages")
		@ApiField("tribe_message")
		private List<TribeMessage> messages;
		/**
		 * 迭代key
		 */
		@ApiField("next_key")
		private String nextKey;
	
		public List<TribeMessage> getMessages() {
			return this.messages;
		}
		public void setMessages(List<TribeMessage> messages) {
			this.messages = messages;
		}
		public String getNextKey() {
			return this.nextKey;
		}
		public void setNextKey(String nextKey) {
			this.nextKey = nextKey;
		}
	}
	


}
