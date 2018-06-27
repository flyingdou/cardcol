package com.freegym.web.basic;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import net.sf.json.JSONObject;

import com.sanmen.web.core.bean.CommonId;

@Entity
@Table(name = "TB_MEMBER_BODY")
public class Body extends CommonId {

	private static final long serialVersionUID = 6907796075042019733L;

	/**
	 * 当前会员
	 */
	@Column(name = "member", nullable = false)
	private Long member;

	/**
	 * 分析日期
	 */
	@Column(name = "analy_date")
	@Temporal(TemporalType.DATE)
	private Date analyDate;

	/**
	 * 头部侧面
	 */
	@Column(name = "head_side", length = 1)
	private String headSide;

	/**
	 * 头部背面
	 */
	@Column(name = "head_back", length = 1)
	private String headBack;

	/**
	 * 颈椎侧面
	 */
	@Column(name = "cervical_side", length = 1)
	private String cervicalSide;

	/**
	 * 肩部背面
	 */
	@Column(name = "shoulder_back", length = 1)
	private String shoulderBack;

	/**
	 * 肩胛骨侧面
	 */
	@Column(name = "scapula_side", length = 1)
	private String scapulaSide;

	/**
	 * 胸椎侧面
	 */
	@Column(name = "thoracic_side", length = 1)
	private String thoracicSide;

	/**
	 * 胸椎背面
	 */
	@Column(name = "thoracic_back", length = 1)
	private String thoracicBack;

	/**
	 * 腰椎侧面
	 */
	@Column(name = "lumbar_side", length = 1)
	private String lumbarSide;

	/**
	 * 腰椎背面
	 */
	@Column(name = "lumbar_back", length = 1)
	private String lumbarBack;

	/**
	 * 骨盆侧面
	 */
	@Column(name = "pelvis_side", length = 1)
	private String pelvisSide;

	/**
	 * 骨盆背面
	 */
	@Column(name = "pelvis_back", length = 1)
	private String pelvisBack;

	/**
	 * 膝关节侧面
	 */
	@Column(name = "knee_side", length = 1)
	private String kneeSide;

	/**
	 * 膝关节背面
	 */
	@Column(name = "knee_back", length = 1)
	private String kneeBack;

	/**
	 * 足部侧面
	 */
	@Column(name = "foot_side", length = 1)
	private String footSide;

	/**
	 * 正面图片
	 */
	@Column(name = "image_front", length = 50)
	private String imageFront;

	/**
	 * 侧面图片
	 */
	@Column(name = "image_side", length = 50)
	private String imageSide;

	/**
	 * 背面图片
	 */
	@Column(name = "image_back", length = 50)
	private String imageBack;

	/**
	 * 分析结论
	 */
	@Lob
	@Column(name = "conclusion")
	private String conclusion;

	public Long getMember() {
		return member;
	}

	public void setMember(Long member) {
		this.member = member;
	}

	public Date getAnalyDate() {
		return analyDate;
	}

	public void setAnalyDate(Date analyDate) {
		this.analyDate = analyDate;
	}

	public String getHeadSide() {
		return headSide;
	}

	public void setHeadSide(String headSide) {
		this.headSide = headSide;
	}

	public String getHeadBack() {
		return headBack;
	}

	public void setHeadBack(String headBack) {
		this.headBack = headBack;
	}

	public String getCervicalSide() {
		return cervicalSide;
	}

	public void setCervicalSide(String cervicalSide) {
		this.cervicalSide = cervicalSide;
	}

	public String getShoulderBack() {
		return shoulderBack;
	}

	public void setShoulderBack(String shoulderBack) {
		this.shoulderBack = shoulderBack;
	}

	public String getScapulaSide() {
		return scapulaSide;
	}

	public void setScapulaSide(String scapulaSide) {
		this.scapulaSide = scapulaSide;
	}

	public String getThoracicSide() {
		return thoracicSide;
	}

	public void setThoracicSide(String thoracicSide) {
		this.thoracicSide = thoracicSide;
	}

	public String getThoracicBack() {
		return thoracicBack;
	}

	public void setThoracicBack(String thoracicBack) {
		this.thoracicBack = thoracicBack;
	}

	public String getLumbarSide() {
		return lumbarSide;
	}

	public void setLumbarSide(String lumbarSide) {
		this.lumbarSide = lumbarSide;
	}

	public String getLumbarBack() {
		return lumbarBack;
	}

	public void setLumbarBack(String lumbarBack) {
		this.lumbarBack = lumbarBack;
	}

	public String getPelvisSide() {
		return pelvisSide;
	}

	public void setPelvisSide(String pelvisSide) {
		this.pelvisSide = pelvisSide;
	}

	public String getPelvisBack() {
		return pelvisBack;
	}

	public void setPelvisBack(String pelvisBack) {
		this.pelvisBack = pelvisBack;
	}

	public String getKneeSide() {
		return kneeSide;
	}

	public void setKneeSide(String kneeSide) {
		this.kneeSide = kneeSide;
	}

	public String getKneeBack() {
		return kneeBack;
	}

	public void setKneeBack(String kneeBack) {
		this.kneeBack = kneeBack;
	}

	public String getFootSide() {
		return footSide;
	}

	public void setFootSide(String footSide) {
		this.footSide = footSide;
	}

	public String getImageFront() {
		return imageFront;
	}

	public void setImageFront(String imageFront) {
		this.imageFront = imageFront;
	}

	public String getImageSide() {
		return imageSide;
	}

	public void setImageSide(String imageSide) {
		this.imageSide = imageSide;
	}

	public String getImageBack() {
		return imageBack;
	}

	public void setImageBack(String imageBack) {
		this.imageBack = imageBack;
	}

	public String getConclusion() {
		return conclusion;
	}

	public void setConclusion(String conclusion) {
		this.conclusion = conclusion;
	}

	@Override
	public String getTableName() {
		return "会员体态表";
	}
	
	public JSONObject toJson() {
		final SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		final JSONObject obj = new JSONObject();
		obj.accumulate("id", getId())
			.accumulate("member", getMember())
			.accumulate("analyDate", sdf.format(getAnalyDate()))
			.accumulate("headSide", getHeadSide())
			.accumulate("headBack", getHeadBack())
			.accumulate("cervicalSide", getCervicalSide())
			.accumulate("shoulderBack", getShoulderBack())
			.accumulate("scapulaSide", getScapulaSide())
			.accumulate("thoracicSide", getThoracicSide())
			.accumulate("thoracicBack", getThoracicBack())
			.accumulate("lumbarSide", getLumbarSide())
			.accumulate("lumbarBack", getLumbarBack())
			.accumulate("pelvisSide", getPelvisSide())
			.accumulate("pelvisBack", getPelvisBack())
			.accumulate("kneeSide", getKneeSide())
			.accumulate("kneeBack", getKneeBack())
			.accumulate("footSide", getFootSide())
			.accumulate("imageFrontName", getImageFront())
			.accumulate("imageSideName", getImageSide())
			.accumulate("imageBackName", getImageBack())
			.accumulate("conclusion", getConclusion());		
		return obj;
	}

}
