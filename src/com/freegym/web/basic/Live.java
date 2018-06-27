package com.freegym.web.basic;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.freegym.web.basic.Member;

/**
 * @author hw
 * @version 创建时间：2018年3月12日 上午9:53:16
 * @ClassName 类名称
 * @Description 类描述
 */
@Entity
@Table(name = "TB_LIVE")
public class Live {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long id;

	// 直播间所属用户id,外键
	@ManyToOne(targetEntity = Member.class)
	@JoinColumn(name = "member")
	private Member member;

	// 直播名称
	@Column(length = 50, name = "live_name")
	private String liveName;

	// 直播费用
	@Column(length = 50, name = "live_cost")
	private Double liveCost;

	// 直播封面
	@Column(length = 50, name = "live_image")
	private String liveImage;

	// 直播公告
	@Column(length = 50, name = "live_notice")
	private String liveNotice;

	// 直播状态(0.未直播,1.直播中)
	@Column(length = 50, name = "live_state")
	private Integer liveState;

	// 最后一次直播时间
	@Column(length = 50, name = "live_history_time")
	private Timestamp liveHistoryTime;

	// 直播的房间号
	@Column(length = 50, name = "live_number")
	private String liveNumber;

	// 直播过期时间
	@Column(length = 50, name = "time_out")
	private String timeOut;

	// 直播开始时间
	@Column(length = 50, name = "start_time")
	private String startTime;

	// 群组id
	@Column(length = 50, name = "tribe_id")
	private String tribeId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public String getLiveName() {
		return liveName;
	}

	public void setLiveName(String liveName) {
		this.liveName = liveName;
	}

	public Double getLiveCost() {
		return liveCost;
	}

	public void setLiveCost(Double liveCost) {
		this.liveCost = liveCost;
	}

	public String getLiveImage() {
		return liveImage;
	}

	public void setLiveImage(String liveImage) {
		this.liveImage = liveImage;
	}

	public String getLiveNotice() {
		return liveNotice;
	}

	public void setLiveNotice(String liveNotice) {
		this.liveNotice = liveNotice;
	}

	public Integer getLiveState() {
		return liveState;
	}

	public void setLiveState(Integer liveState) {
		this.liveState = liveState;
	}

	public Timestamp getLiveHistoryTime() {
		return liveHistoryTime;
	}

	public void setLiveHistoryTime(Timestamp liveHistoryTime) {
		this.liveHistoryTime = liveHistoryTime;
	}

	public String getLiveNumber() {
		return liveNumber;
	}

	public void setLiveNumber(String liveNumber) {
		this.liveNumber = liveNumber;
	}

	public String getTimeOut() {
		return timeOut;
	}

	public void setTimeOut(String timeOut) {
		this.timeOut = timeOut;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getTribeId() {
		return tribeId;
	}

	public void setTribeId(String tribeId) {
		this.tribeId = tribeId;
	}

}
