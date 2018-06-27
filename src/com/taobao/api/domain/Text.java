package com.taobao.api.domain;

import com.taobao.api.internal.mapping.ApiField;
import com.taobao.api.TaobaoObject;


/**
 * 文本内容
 *
 * @author top auto create
 * @since 1.0, null
 */
public class Text extends TaobaoObject {

	private static final long serialVersionUID = 7232571659527644396L;

	/**
	 * 文本内容
	 */
	@ApiField("content")
	private String content;

	/**
	 * 业务处理ID
	 */
	@ApiField("id")
	private String id;

	/**
	 * 文本类型1-评论 2-订单留言 9-其他
	 */
	@ApiField("type")
	private Long type;


	public String getContent() {
		return this.content;
	}
	public void setContent(String content) {
		this.content = content;
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
