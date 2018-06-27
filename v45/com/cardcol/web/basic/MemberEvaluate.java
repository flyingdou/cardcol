package com.cardcol.web.basic;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.freegym.web.basic.Member;
import com.freegym.web.course.SignIn;
import com.sanmen.web.core.bean.CommonId;
@Entity
@Table(name = "TB_MEMBER_EVALUATE")
public class MemberEvaluate extends CommonId {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8404103070959900853L;
	/**
	 * 评价会员
	 */
	@ManyToOne(targetEntity = Member.class)
	@JoinColumn(name="member")
	private Member member;
	/**
	 *签到主表
	 */
	@ManyToOne(targetEntity = SignIn.class)
	@JoinColumn(name="signIn")
	private SignIn signIn;
	/**
	 * 总体评分
	 */
	@Column(name = "TOTALITY_SCORE", nullable = false)
	private Integer totailtyScore;
	/**
	 * 环境分
	 */
	@Column(name = "EVEN_SCORE", nullable = false)
	private Integer evenScore;
	/**
	 * 设备分
	 */
	@Column(name = "DEVICE_SCORE", nullable = false)
	private Integer deviceScore;
	/**
	 * 服务分
	 */
	@Column(name = "SERVICE_SCORE", nullable = false)
	private Integer serviceScore;
	/**
	 * 评价内容
	 */
	@Column(name = "EVAL_CONTENT", nullable = false)
	private String content;
	/**
	 * 评价时间
	 */
	@Column(name = "EVAL_TIME", nullable = false)
	private Date evalTime;
	
	// 上传图片
	@Column(name = "image1",length = 50)
	private String image1;
	
	// 上传图片
	@Column(name = "image2",length = 50)
	private String image2;
	
	// 上传图片
	@Column(name = "image3",length = 50)
	private String image3;
	
	
	
	public String getContent() {
		return content;
	}
	public String getImage1() {
		return image1;
	}
	public void setImage1(String image1) {
		this.image1 = image1;
	}
	public String getImage2() {
		return image2;
	}
	public void setImage2(String image2) {
		this.image2 = image2;
	}
	public String getImage3() {
		return image3;
	}
	public void setImage3(String image3) {
		this.image3 = image3;
	}
	public Member getMember() {
		return member;
	}
	public void setMember(Member member) {
		this.member = member;
	}
	public SignIn getSignIn() {
		return signIn;
	}
	public void setSignIn(SignIn signIn) {
		this.signIn = signIn;
	}
	public Integer getTotailtyScore() {
		return totailtyScore;
	}
	public void setTotailtyScore(Integer totailtyScore) {
		this.totailtyScore = totailtyScore;
	}
	public Integer getEvenScore() {
		return evenScore;
	}
	public void setEvenScore(Integer evenScore) {
		this.evenScore = evenScore;
	}
	public Integer getDeviceScore() {
		return deviceScore;
	}
	public void setDeviceScore(Integer deviceScore) {
		this.deviceScore = deviceScore;
	}
	public Integer getServiceScore() {
		return serviceScore;
	}
	public void setServiceScore(Integer serviceScore) {
		this.serviceScore = serviceScore;
	}
	public Date getEvalTime() {
		return evalTime;
	}
	public void setEvalTime(Date evalTime) {
		this.evalTime = evalTime;
	}
	
	public String getContent(String string) {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	@Override
	public String getTableName() {
		return "一卡通评价表";
	}
	
	
}
