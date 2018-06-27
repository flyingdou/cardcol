package com.taobao.api.request;

import com.taobao.api.internal.mapping.ApiField;
import com.taobao.api.TaobaoObject;
import java.util.Map;

import com.taobao.api.ApiRuleException;
import com.taobao.api.BaseTaobaoRequest;
import com.taobao.api.internal.util.TaobaoHashMap;
import com.taobao.api.internal.util.json.JSONWriter;
import com.taobao.api.response.NlpSimilarityResponse;

/**
 * TOP API: taobao.nlp.similarity request
 * 
 * @author top auto create
 * @since 1.0, 2016.01.12
 */
public class NlpSimilarityRequest extends BaseTaobaoRequest<NlpSimilarityResponse> {
	
	

	/** 
	* 多文本内容
	 */
	private String texts;

	public void setTexts(String texts) {
		this.texts = texts;
	}

	public void setTexts(Texts texts) {
		this.texts = new JSONWriter(false,true).write(texts);
	}

	public String getTexts() {
		return this.texts;
	}

	public String getApiMethodName() {
		return "taobao.nlp.similarity";
	}

	public Map<String, String> getTextParams() {		
		TaobaoHashMap txtParams = new TaobaoHashMap();
		txtParams.put("texts", this.texts);
		if(this.udfParams != null) {
			txtParams.putAll(this.udfParams);
		}
		return txtParams;
	}

	public Class<NlpSimilarityResponse> getResponseClass() {
		return NlpSimilarityResponse.class;
	}

	public void check() throws ApiRuleException {
	}
	
	/**
	 * 多文本内容
	 *
	 * @author top auto create
	 * @since 1.0, null
	 */
	public static class Texts extends TaobaoObject {
		private static final long serialVersionUID = 1726615435152951123L;
		/**
		 * 文本相似度匹配文本内容模板
		 */
		@ApiField("content_dst")
		private String contentDst;
		/**
		 * 文本相似度匹配文本
		 */
		@ApiField("content_src")
		private String contentSrc;
		/**
		 * 业务处理ID
		 */
		@ApiField("id")
		private String id;
		/**
		 * 文本相似度匹配类型：1为余弦距离，2为编辑距离，3为simHash距离
		 */
		@ApiField("type")
		private Long type;
	
		public String getContentDst() {
			return this.contentDst;
		}
		public void setContentDst(String contentDst) {
			this.contentDst = contentDst;
		}
		public String getContentSrc() {
			return this.contentSrc;
		}
		public void setContentSrc(String contentSrc) {
			this.contentSrc = contentSrc;
		}
		public String getId() {
			return this.id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public Long getType() {
			return this.type;
		}
		public void setType(Long type) {
			this.type = type;
		}
	}
	

}