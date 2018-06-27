package com.freegym.web.weixin.action;

import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.freegym.web.BaseBasicAction;
import com.freegym.web.basic.Member;
@Namespace("")
@InterceptorRefs({ @InterceptorRef("customStack") })
@Results({@Result(name="success",location="/WX/login.jsp"),
	@Result(name="stage",location="/WX/stage_challenge.jsp"),
		@Result(name = "memberall", location = "/WX/choiceCp1.jsp"),
		   @Result(name = "punisher",location ="/WX/success_lose.jsp")})
public class ActiveSaveWXManageAction extends BaseBasicAction {
	private String keyword;
	private String city;
	private String role;
	private Member member;
	
	public Member getMember() {
		return member;
	}
	public void setMember(Member member) {
		this.member = member;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -1697946119092870887L;
	
	public String  execute() {
		member = (Member) session.getAttribute("member");
		if(member == null){
			return SUCCESS;
		}else {
			return "stage";
		}
	}
	
	
	/**
	 * 所有裁判候选人
	 */
	public String  memberall() {
		StringBuffer sb =new StringBuffer();
		sb.append("select * from tb_member where 1=1 ");
		sb.append("ORDER BY id DESC");
		pageInfo = service.findPageBySql(sb.toString(), pageInfo,
				new Object[] {});
		return "memberall";
	}
	/**
	 * 筛选裁判候选人
	 */
	public String tmember(){
		StringBuffer sb = new StringBuffer();
		sb.append("select * from tb_member m where 1=1 ");
		if(keyword !=" "){
			sb.append("and m.name like '%" + keyword+ "%' ");
		}else if(city !=" "){
			sb.append("and m.city like '%" + city+ "%' ");
		}else if(role != " "){
			sb.append("and m.role = '" + role+ "' ");
		}
		sb.append("order by m.id desc");
		pageInfo = service.findPageBySql(sb.toString(), pageInfo ,
				new Object[] {});
		System.out.println(pageInfo.getItems());
		System.out.println(pageInfo.getItems().iterator());
		System.out.println(pageInfo.getItems().size());
		return "memberall";
	}
	/**
	 * 选择挑战失败惩罚
	 */
	public String punisher(){
		StringBuffer sb =new StringBuffer();
		sb.append("select * from tb_member where role = 'I' ");
		pageInfo = service.findPageBySql(sb.toString(), pageInfo ,
				new Object[] {});
		System.out.println(pageInfo.getItems());
		System.out.println(sb.toString());
		return "punisher";
	}
	
	
}
