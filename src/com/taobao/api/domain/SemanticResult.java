package com.taobao.api.domain;

import com.taobao.api.internal.mapping.ApiField;
import com.taobao.api.TaobaoObject;


/**
 * 文本分析结果
 *
 * @author top auto create
 * @since 1.0, null
 */
public class SemanticResult extends TaobaoObject {

	private static final long serialVersionUID = 6212859259325253838L;

	/**
	 * 文本ID
	 */
	@ApiField("id")
	private String id;

	/**
	 * 物流包裹 0-包裹正常, 1-包裹有破损, 空-没有包裹信息
	 */
	@ApiField("logistics_package")
	private Long logisticsPackage;

	/**
	 * 物流服务 0-服务好, 1-服务差,  空-没有物流服务信息
	 */
	@ApiField("logistics_service")
	private Long logisticsService;

	/**
	 * 物流速度 0-速度快, 1- 速度慢,  空-没有物流速度信息
	 */
	@ApiField("logistics_speed")
	private Long logisticsSpeed;


	public String getId() {
		return this.id;
	}
	public void setId(String id) {
		this.id = id;
	}

	public Long getLogisticsPackage() {
		return this.logisticsPackage;
	}
	public void setLogisticsPackage(Long logisticsPackage) {
		this.logisticsPackage = logisticsPackage;
	}

	public Long getLogisticsService() {
		return this.logisticsService;
	}
	public void setLogisticsService(Long logisticsService) {
		this.logisticsService = logisticsService;
	}

	public Long getLogisticsSpeed() {
		return this.logisticsSpeed;
	}
	public void setLogisticsSpeed(Long logisticsSpeed) {
		this.logisticsSpeed = logisticsSpeed;
	}

}
