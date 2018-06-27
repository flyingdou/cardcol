package com.taobao.api.request;

import java.util.List;
import java.util.Map;

import com.taobao.api.ApiRuleException;
import com.taobao.api.BaseTaobaoRequest;
import com.taobao.api.TaobaoObject;
import com.taobao.api.internal.mapping.ApiField;
import com.taobao.api.internal.util.RequestCheckUtils;
import com.taobao.api.internal.util.TaobaoHashMap;
import com.taobao.api.internal.util.json.JSONWriter;
import com.taobao.api.response.OpenimChatlogsImportResponse;

/**
 * TOP API: taobao.openim.chatlogs.import request
 * 
 * @author top auto create
 * @since 1.0, 2015.12.31
 */
public class OpenimChatlogsImportRequest extends BaseTaobaoRequest<OpenimChatlogsImportResponse> {
	
	

	/** 
	* 消息序列
	 */
	private String messages;

	public void setMessages(String messages) {
		this.messages = messages;
	}

	public void setMessages(List<TextMessage> messages) {
		this.messages = new JSONWriter(false,true).write(messages);
	}

	public String getMessages() {
		return this.messages;
	}

	public String getApiMethodName() {
		return "taobao.openim.chatlogs.import";
	}

	public Map<String, String> getTextParams() {		
		TaobaoHashMap txtParams = new TaobaoHashMap();
		txtParams.put("messages", this.messages);
		if(this.udfParams != null) {
			txtParams.putAll(this.udfParams);
		}
		return txtParams;
	}

	public Class<OpenimChatlogsImportResponse> getResponseClass() {
		return OpenimChatlogsImportResponse.class;
	}

	public void check() throws ApiRuleException {
		RequestCheckUtils.checkObjectMaxListSize(messages, 20, "messages");
	}
	
	/**
	 * 消息序列
	 *
	 * @author top auto create
	 * @since 1.0, null
	 */
	public static class TextMessage extends TaobaoObject {
		private static final long serialVersionUID = 8871622784189495889L;
		/**
		 * 发送方userid
		 */
		@ApiField("from_id")
		private String fromId;
		/**
		 * 消息
		 */
		@ApiField("message")
		private String message;
		/**
		 * 消息时间。UTC时间，精确到秒。必须在一个月内
		 */
		@ApiField("time")
		private Long time;
		/**
		 * 接受方userid
		 */
		@ApiField("to_id")
		private String toId;
	
		public String getFromId() {
			return this.fromId;
		}
		public void setFromId(String fromId) {
			this.fromId = fromId;
		}
		public void setFromIdString(String fromId) {
			this.fromId = fromId;
		}
		
		public String getMessage() {
			return this.message;
		}
		public void setMessage(String message) {
			this.message = message;
		}
		public Long getTime() {
			return this.time;
		}
		public void setTime(Long time) {
			this.time = time;
		}
		public String getToId() {
			return this.toId;
		}
		public void setToId(String toId) {
			this.toId = toId;
		}
		public void setToIdString(String toId) {
			this.toId = toId;
		}
		
	}
	

}