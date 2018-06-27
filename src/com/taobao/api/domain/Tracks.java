package com.taobao.api.domain;

import com.taobao.api.internal.mapping.ApiField;
import com.taobao.api.TaobaoObject;


/**
 * 用户轨迹
 *
 * @author top auto create
 * @since 1.0, null
 */
public class Tracks extends TaobaoObject {

	private static final long serialVersionUID = 5745155241425726646L;

	/**
	 * 轨迹发生的时间，即用户进入页面的时间
	 */
	@ApiField("enter_time")
	private String enterTime;

	/**
	 * referer_keyword
	 */
	@ApiField("referer_keyword")
	private String refererKeyword;

	/**
	 * referer_url
	 */
	@ApiField("referer_url")
	private String refererUrl;

	/**
	 * 标题
	 */
	@ApiField("title")
	private String title;

	/**
	 * url
	 */
	@ApiField("url")
	private String url;


	public String getEnterTime() {
		return this.enterTime;
	}
	public void setEnterTime(String enterTime) {
		this.enterTime = enterTime;
	}

	public String getRefererKeyword() {
		return this.refererKeyword;
	}
	public void setRefererKeyword(String refererKeyword) {
		this.refererKeyword = refererKeyword;
	}

	public String getRefererUrl() {
		return this.refererUrl;
	}
	public void setRefererUrl(String refererUrl) {
		this.refererUrl = refererUrl;
	}

	public String getTitle() {
		return this.title;
	}
	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return this.url;
	}
	public void setUrl(String url) {
		this.url = url;
	}

}
