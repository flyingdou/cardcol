package com.taobao.api.request;

import java.util.List;
import com.taobao.api.internal.mapping.ApiField;
import com.taobao.api.internal.util.RequestCheckUtils;
import com.taobao.api.TaobaoObject;
import java.util.Map;

import com.taobao.api.ApiRuleException;
import com.taobao.api.BaseTaobaoRequest;
import com.taobao.api.internal.util.TaobaoHashMap;
import com.taobao.api.internal.util.json.JSONWriter;
import com.taobao.api.response.OpenimTribelogsImportResponse;

/**
 * TOP API: taobao.openim.tribelogs.import request
 * 
 * @author top auto create
 * @since 1.0, 2015.12.31
 */
public class OpenimTribelogsImportRequest extends BaseTaobaoRequest<OpenimTribelogsImportResponse> {
	
	

	/** 
	* 消息列表
	 */
	private String messages;

	/** 
	* 群号。必须为已存在的群，且群主属于本app
	 */
	private Long tribeId;

	public void setMessages(String messages) {
		this.messages = messages;
	}

	public void setMessages(List<TribeTextMessage> messages) {
		this.messages = new JSONWriter(false,true).write(messages);
	}

	public String getMessages() {
		return this.messages;
	}

	public void setTribeId(Long tribeId) {
		this.tribeId = tribeId;
	}

	public Long getTribeId() {
		return this.tribeId;
	}

	public String getApiMethodName() {
		return "taobao.openim.tribelogs.import";
	}

	public Map<String, String> getTextParams() {		
		TaobaoHashMap txtParams = new TaobaoHashMap();
		txtParams.put("messages", this.messages);
		txtParams.put("tribe_id", this.tribeId);
		if(this.udfParams != null) {
			txtParams.putAll(this.udfParams);
		}
		return txtParams;
	}

	public Class<OpenimTribelogsImportResponse> getResponseClass() {
		return OpenimTribelogsImportResponse.class;
	}

	public void check() throws ApiRuleException {
		RequestCheckUtils.checkObjectMaxListSize(messages, 20, "messages");
		RequestCheckUtils.checkNotEmpty(tribeId, "tribeId");
	}
	
	/**
	 * 消息列表
	 *
	 * @author top auto create
	 * @since 1.0, null
	 */
	public static class TribeTextMessage extends TaobaoObject {
		private static final long serialVersionUID = 7881117956465926419L;
		/**
		 * 发送方userid。必须为本app已导入的账号
		 */
		@ApiField("from_id")
		private String fromId;
		/**
		 * 消息
		 */
		@ApiField("message")
		private String message;
		/**
		 * 消息时间。UTC时间，精确到秒。时间范围必须在当前时间30天内
		 */
		@ApiField("time")
		private Long time;
	
		public String getFromId() {
			return this.fromId;
		}
		public void setFromId(String fromId) {
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
	}
	

}