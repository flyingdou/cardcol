package com.taobao.api.domain;

import com.taobao.api.internal.mapping.ApiField;
import com.taobao.api.TaobaoObject;


/**
 * 群信息
 *
 * @author top auto create
 * @since 1.0, null
 */
public class TribeInfo extends TaobaoObject {

	private static final long serialVersionUID = 6725167484225137584L;

	/**
	 * 群验证模式
	 */
	@ApiField("check_mode")
	private Long checkMode;

	/**
	 * 群头像URL地址
	 */
	@ApiField("icon")
	private String icon;

	/**
	 * 群名称
	 */
	@ApiField("name")
	private String name;

	/**
	 * 群公告
	 */
	@ApiField("notice")
	private String notice;

	/**
	 * 群接收标记
	 */
	@ApiField("recv_flag")
	private Long recvFlag;

	/**
	 * 群ID
	 */
	@ApiField("tribe_id")
	private Long tribeId;

	/**
	 * 群类型
	 */
	@ApiField("tribe_type")
	private Long tribeType;


	public Long getCheckMode() {
		return this.checkMode;
	}
	public void setCheckMode(Long checkMode) {
		this.checkMode = checkMode;
	}

	public String getIcon() {
		return this.icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getName() {
		return this.name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getNotice() {
		return this.notice;
	}
	public void setNotice(String notice) {
		this.notice = notice;
	}

	public Long getRecvFlag() {
		return this.recvFlag;
	}
	public void setRecvFlag(Long recvFlag) {
		this.recvFlag = recvFlag;
	}

	public Long getTribeId() {
		return this.tribeId;
	}
	public void setTribeId(Long tribeId) {
		this.tribeId = tribeId;
	}

	public Long getTribeType() {
		return this.tribeType;
	}
	public void setTribeType(Long tribeType) {
		this.tribeType = tribeType;
	}

}
