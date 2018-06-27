package com.taobao.api.domain;

import com.taobao.api.internal.mapping.ApiField;
import com.taobao.api.TaobaoObject;


/**
 * 用户最近轨迹信息
 *
 * @author top auto create
 * @since 1.0, null
 */
public class Tracksummary extends TaobaoObject {

	private static final long serialVersionUID = 1289279213432745292L;

	/**
	 * 浏览器
	 */
	@ApiField("browser")
	private String browser;

	/**
	 * 城市
	 */
	@ApiField("city")
	private String city;

	/**
	 * 国家
	 */
	@ApiField("country")
	private String country;

	/**
	 * 设备
	 */
	@ApiField("device")
	private String device;

	/**
	 * 额外参数
	 */
	@ApiField("extra_param")
	private String extraParam;

	/**
	 * UI参数
	 */
	@ApiField("extra_ui")
	private String extraUi;

	/**
	 * 渠道
	 */
	@ApiField("from_channel")
	private String fromChannel;

	/**
	 * ip地址
	 */
	@ApiField("ip")
	private String ip;

	/**
	 * 运营商，如电信，联通
	 */
	@ApiField("isp")
	private String isp;

	/**
	 * 操作系统
	 */
	@ApiField("os")
	private String os;

	/**
	 * referer_关键字
	 */
	@ApiField("referer_keyword")
	private String refererKeyword;

	/**
	 * referer_url
	 */
	@ApiField("referer_url")
	private String refererUrl;

	/**
	 * 省份，如浙江省
	 */
	@ApiField("region")
	private String region;

	/**
	 * 访问总时间，以毫秒为单位
	 */
	@ApiField("total_time")
	private Long totalTime;

	/**
	 * 访问总次数
	 */
	@ApiField("visit_count")
	private Long visitCount;


	public String getBrowser() {
		return this.browser;
	}
	public void setBrowser(String browser) {
		this.browser = browser;
	}

	public String getCity() {
		return this.city;
	}
	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return this.country;
	}
	public void setCountry(String country) {
		this.country = country;
	}

	public String getDevice() {
		return this.device;
	}
	public void setDevice(String device) {
		this.device = device;
	}

	public String getExtraParam() {
		return this.extraParam;
	}
	public void setExtraParam(String extraParam) {
		this.extraParam = extraParam;
	}

	public String getExtraUi() {
		return this.extraUi;
	}
	public void setExtraUi(String extraUi) {
		this.extraUi = extraUi;
	}

	public String getFromChannel() {
		return this.fromChannel;
	}
	public void setFromChannel(String fromChannel) {
		this.fromChannel = fromChannel;
	}

	public String getIp() {
		return this.ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getIsp() {
		return this.isp;
	}
	public void setIsp(String isp) {
		this.isp = isp;
	}

	public String getOs() {
		return this.os;
	}
	public void setOs(String os) {
		this.os = os;
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

	public String getRegion() {
		return this.region;
	}
	public void setRegion(String region) {
		this.region = region;
	}

	public Long getTotalTime() {
		return this.totalTime;
	}
	public void setTotalTime(Long totalTime) {
		this.totalTime = totalTime;
	}

	public Long getVisitCount() {
		return this.visitCount;
	}
	public void setVisitCount(Long visitCount) {
		this.visitCount = visitCount;
	}

}
