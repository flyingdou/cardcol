package com.taobao.api.response;

import java.util.List;
import com.taobao.api.domain.SemanticResult;
import com.taobao.api.internal.mapping.ApiField;
import com.taobao.api.internal.mapping.ApiListField;

import com.taobao.api.TaobaoResponse;

/**
 * TOP API: taobao.nlp.semantic.texts.analyze response.
 * 
 * @author top auto create
 * @since 1.0, null
 */
public class NlpSemanticTextsAnalyzeResponse extends TaobaoResponse {

	private static final long serialVersionUID = 2434285113814112622L;

	/** 
	 * 文本语义分析结果
	 */
	@ApiListField("semantic_list")
	@ApiField("semantic_result")
	private List<SemanticResult> semanticList;


	public void setSemanticList(List<SemanticResult> semanticList) {
		this.semanticList = semanticList;
	}
	public List<SemanticResult> getSemanticList( ) {
		return this.semanticList;
	}
	


}
