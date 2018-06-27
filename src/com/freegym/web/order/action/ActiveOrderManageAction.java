package com.freegym.web.order.action;

import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.freegym.web.OrderBasicAction;
import com.freegym.web.basic.Member;
import com.freegym.web.config.Setting;
import com.freegym.web.order.Order;

@Namespace("")
@InterceptorRefs({ @InterceptorRef("foreStack") })
@Results({ @Result(name = "shopim", location = "/order/shopim.jsp") })
public class ActiveOrderManageAction extends OrderBasicAction {

	private static final long serialVersionUID = -3769475961884283745L;

	// 场地预约开始时间
	private String factoryStartTime;
	// 场地预约结束时间
	private String factoryEndTime;
	// 计划、自动订单、健身卡开始日期
	private String startDate;
	
	private Long activeId;

	/**
	 * 团队名称
	 */
	private Long teamId;

	/**
	 * 判断是新建团队，还是加入团队
	 */
	private Character createMode;

	/**
	 * 团队名称
	 */
	private String teamName;

	/**
	 * 新建团队时所有选择的会员
	 */
	private String members;

	private String judge, prodType;

	private Setting setting;

	private Member member;

	public String getFactoryStartTime() {
		return factoryStartTime;
	}

	public void setFactoryStartTime(String factoryStartTime) {
		this.factoryStartTime = factoryStartTime;
	}

	public String getFactoryEndTime() {
		return factoryEndTime;
	}

	public void setFactoryEndTime(String factoryEndTime) {
		this.factoryEndTime = factoryEndTime;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public Long getTeamId() {
		return teamId;
	}

	public void setTeamId(Long teamId) {
		this.teamId = teamId;
	}

	public Character getCreateMode() {
		return createMode;
	}

	public void setCreateMode(Character createMode) {
		this.createMode = createMode;
	}

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public String getMembers() {
		return members;
	}

	public void setMembers(String members) {
		this.members = members;
	}

	public String getJudge() {
		return judge;
	}

	public void setJudge(String judge) {
		this.judge = judge;
	}

	public Setting getSetting() {
		return setting;
	}

	public void setSetting(Setting setting) {
		this.setting = setting;
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public String getProdType() {
		return prodType;
	}

	public void setProdType(String prodType) {
		this.prodType = prodType;
	}

	public Long getActiveId() {
		return activeId;
	}

	public void setActiveId(Long activeId) {
		this.activeId = activeId;
	}

	public String execute() {
		String factoryMoney = request.getParameter("factoryMoney");
		this.setFactoryStartTime(factoryStartTime);
		this.setFactoryEndTime(factoryEndTime);
		Member m = toMember();
		String sql = "SELECT * FROM (" + Order.getProdSql(prodType, m.getId(), factoryMoney, startDate) + ") t where t.pro_id =?";
		request.setAttribute("list", service.queryForList(sql, activeId));

		if (m.getMobileValid() != null && m.getMobileValid().equals('1') && m.getMobilephone() != null && m.getMobilephone().length() > 7) {
			request.setAttribute("mobilephone", m.getMobilephone().replaceAll(m.getMobilephone().substring(3, 7), "*****"));
		}
		return "shopim";
	}

}
