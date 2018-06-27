package com.taobao.api.request;

import java.util.List;
import com.taobao.api.internal.mapping.ApiField;
import com.taobao.api.TaobaoObject;
import com.taobao.api.internal.mapping.ApiListField;
import java.util.Map;

import com.taobao.api.ApiRuleException;
import com.taobao.api.BaseTaobaoRequest;
import com.taobao.api.internal.util.TaobaoHashMap;
import com.taobao.api.internal.util.json.JSONWriter;
import com.taobao.api.response.OpenimCustmsgPushResponse;

/**
 * TOP API: taobao.openim.custmsg.push request
 * 
 * @author top auto create
 * @since 1.0, 2016.01.28
 */
public class OpenimCustmsgPushRequest extends BaseTaobaoRequest<OpenimCustmsgPushResponse> {
	
	

	/** 
	* 自定义消息内容
	 */
	private String custmsg;

	public void setCustmsg(String custmsg) {
		this.custmsg = custmsg;
	}

	public void setCustmsg(CustMsg custmsg) {
		this.custmsg = new JSONWriter(false,true).write(custmsg);
	}

	public String getCustmsg() {
		return this.custmsg;
	}

	public String getApiMethodName() {
		return "taobao.openim.custmsg.push";
	}

	public Map<String, String> getTextParams() {		
		TaobaoHashMap txtParams = new TaobaoHashMap();
		txtParams.put("custmsg", this.custmsg);
		if(this.udfParams != null) {
			txtParams.putAll(this.udfParams);
		}
		return txtParams;
	}

	public Class<OpenimCustmsgPushResponse> getResponseClass() {
		return OpenimCustmsgPushResponse.class;
	}

	public void check() throws ApiRuleException {
	}
	
	/**
	 * 自定义消息内容
	 *
	 * @author top auto create
	 * @since 1.0, null
	 */
	public static class CustMsg extends TaobaoObject {
		private static final long serialVersionUID = 2834362289682145726L;
		/**
		 * apns推送的附带数据。客户端收到apns消息后，可以从apns结构体的"d"字段中取出该内容。aps.size() + apns_param.size() < 200
		 */
		@ApiField("apns_param")
		private String apnsParam;
		/**
		 * apns推送时，里面的aps结构体json字符串，aps.alert为必填字段。本字段为可选，若为空，则表示不进行apns推送。aps.size() + apns_param.size() < 200
		 */
		@ApiField("aps")
		private String aps;
		/**
		 * 发送的自定义数据，sdk默认无法解析消息，该数据需要客户端自己解析
		 */
		@ApiField("data")
		private String data;
		/**
		 * 可以指定发送方的显示昵称，默认为空，自动使用发送方用户id作为nick
		 */
		@ApiField("from_nick")
		private String fromNick;
		/**
		 * 发送方userid
		 */
		@ApiField("from_user")
		private String fromUser;
		/**
		 * 表示消息是否在最近会话列表里面展示。如果为1，则消息不在列表展示，可以认为服务端透明的给客户端下法了一个数据，ios的提示任然通过aps字段控制
		 */
		@ApiField("invisible")
		private Long invisible;
		/**
		 * 客户端最近消息里面显示的消息摘要
		 */
		@ApiField("summary")
		private String summary;
		/**
		 * 接收方appkey，不填默认是发送方appkey，如需跨app发送，需要走审批流程，请联系技术答疑
		 */
		@ApiField("to_appkey")
		private String toAppkey;
		/**
		 * 接受者userid列表，单次发送用户数小于100
		 */
		@ApiListField("to_users")
		@ApiField("string")
		private List<String> toUsers;
	
		public String getApnsParam() {
			return this.apnsParam;
		}
		public void setApnsParam(String apnsParam) {
			this.apnsParam = apnsParam;
		}
		public String getAps() {
			return this.aps;
		}
		public void setAps(String aps) {
			this.aps = aps;
		}
		public String getData() {
			return this.data;
		}
		public void setData(String data) {
			this.data = data;
		}
		public String getFromNick() {
			return this.fromNick;
		}
		public void setFromNick(String fromNick) {
			this.fromNick = fromNick;
		}
		public String getFromUser() {
			return this.fromUser;
		}
		public void setFromUser(String fromUser) {
			this.fromUser = fromUser;
		}
		public Long getInvisible() {
			return this.invisible;
		}
		public void setInvisible(Long invisible) {
			this.invisible = invisible;
		}
		public String getSummary() {
			return this.summary;
		}
		public void setSummary(String summary) {
			this.summary = summary;
		}
		public String getToAppkey() {
			return this.toAppkey;
		}
		public void setToAppkey(String toAppkey) {
			this.toAppkey = toAppkey;
		}
		public List<String> getToUsers() {
			return this.toUsers;
		}
		public void setToUsers(List<String> toUsers) {
			this.toUsers = toUsers;
		}
	}
	

}