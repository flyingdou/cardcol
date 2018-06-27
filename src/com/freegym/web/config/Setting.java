package com.freegym.web.config;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import net.sf.json.JSONObject;

import com.sanmen.web.core.bean.CommonId;

@Entity
@Table(name = "TB_MEMBER_SETTING")
public class Setting extends CommonId {

	private static final long serialVersionUID = -2102418163722404646L;

	/**
	 * 会员
	 */
	private Long member;

	/**
	 * 健身目的，1减脂塑形，2健美增肌，3增加力量，4提高运动表现，
	 */
	@Column(length = 1)
	private String target;

	/**
	 * 高度cm
	 */
	@Column(name = "height", length = 11)
	private Integer height;

	/**
	 * 体重
	 */
	@Column(name = "weight")
	private Double weight;

	/**
	 * 静心率
	 */
	@Column(name = "heart", length = 11)
	private Integer heart;

	public Integer getHeight() {
		return height;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}

	public Double getWeight() {
		return weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}

	public Integer getHeart() {
		return heart;
	}

	public void setHeart(Integer heart) {
		this.heart = heart;
	}

	/**
	 * 腰围
	 */
	@Column(name = "waist_line", length = 9, precision = 2)
	private Double waistline;

	/**
	 * 健身状况
	 */
	@Column(length = 10)
	private String currGymStatus;

	/**
	 * 力量训练频率
	 */
	@Column(length = 20)
	private String strengthDate;

	/**
	 * 力量训练时间
	 */
	@Column(length = 20)
	private String strengthDuration;

	/**
	 * 有氧训练频率
	 */
	@Column(length = 20)
	private String cardioDate;

	/**
	 * 有氧训练时间
	 */
	@Column(length = 20)
	private String cardioDuration;

	/**
	 * 健康状况
	 */
	@Column(length = 20)
	private String diseaseReport;

	/**
	 * 喜爱的有氧运动
	 */
	@Column(length = 200)
	private String favoriateCardio;

	/**
	 * 运动强度评估方法,0常用估值法,1卡尔沃宁（karvonen）法
	 */
	@Column(length = 1)
	private Character bmiMode;

	/**
	 * 靶心率阈值下限
	 */
	private Integer bmiLow = 55;

	/**
	 * 靶心率阈值上限
	 */
	private Integer bmiHigh = 85;
	
	/**
	 * 最大卧推重量
	 */
	private Double maxwm;

	@Column(length = 1)
	private String intensityMode;

	public Double getMaxwm() {
		return maxwm;
	}

	public void setMaxwm(Double maxwm) {
		this.maxwm = maxwm;
	}

	public Long getMember() {
		return member;
	}

	public void setMember(Long member) {
		this.member = member;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public Double getWaistline() {
		return waistline;
	}

	public void setWaistline(Double waistline) {
		this.waistline = waistline;
	}

	public String getCurrGymStatus() {
		return currGymStatus;
	}

	public void setCurrGymStatus(String currGymStatus) {
		this.currGymStatus = currGymStatus;
	}

	public String getStrengthDate() {
		return strengthDate;
	}

	public void setStrengthDate(String strengthDate) {
		this.strengthDate = strengthDate;
	}

	public String getStrengthDuration() {
		return strengthDuration;
	}

	public void setStrengthDuration(String strengthDuration) {
		this.strengthDuration = strengthDuration;
	}

	public String getCardioDate() {
		return cardioDate;
	}

	public void setCardioDate(String cardioDate) {
		this.cardioDate = cardioDate;
	}

	public String getCardioDuration() {
		return cardioDuration;
	}

	public void setCardioDuration(String cardioDuration) {
		this.cardioDuration = cardioDuration;
	}

	public String getDiseaseReport() {
		return diseaseReport;
	}

	public void setDiseaseReport(String diseaseReport) {
		this.diseaseReport = diseaseReport;
	}

	public String getFavoriateCardio() {
		return favoriateCardio;
	}

	public void setFavoriateCardio(String favoriateCardio) {
		this.favoriateCardio = favoriateCardio;
	}

	public Character getBmiMode() {
		return bmiMode;
	}

	public void setBmiMode(Character bmiMode) {
		this.bmiMode = bmiMode;
	}

	public Integer getBmiLow() {
		return bmiLow;
	}

	public void setBmiLow(Integer bmiLow) {
		this.bmiLow = bmiLow;
	}

	public Integer getBmiHigh() {
		return bmiHigh;
	}

	public void setBmiHigh(Integer bmiHigh) {
		this.bmiHigh = bmiHigh;
	}

	public String getIntensityMode() {
		return intensityMode;
	}

	public void setIntensityMode(String intensityMode) {
		this.intensityMode = intensityMode;
	}

	@Override
	public String getTableName() {
		return null;
	}

	public JSONObject toJson() {
		final JSONObject obj = new JSONObject();
		obj.accumulate("id", getId()).accumulate("member", getMember()).accumulate("target", getTarget()) 
			.accumulate("height", getHeight()).accumulate("weight", getWeight()).accumulate("heart", getHeart())
			.accumulate("waistline", getWaistline()).accumulate("currGymStatus", getCurrGymStatus())
			.accumulate("strengthDate", getStrengthDate()).accumulate("strengthDuration", getStrengthDuration())
			.accumulate("cardioDate", getCardioDate()).accumulate("cardioDuration", getCardioDuration())
			.accumulate("diseaseReport", getDiseaseReport()).accumulate("favoriateCardio", getFavoriateCardio())
			.accumulate("bmiMode", getBmiMode()).accumulate("bmiLow", getBmiLow()).accumulate("bmiHigh", getBmiHigh())
			.accumulate("maxwm", getMaxwm());		
		return obj;
	}
}
