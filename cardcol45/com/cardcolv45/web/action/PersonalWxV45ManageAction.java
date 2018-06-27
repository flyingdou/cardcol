package com.cardcolv45.web.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.freegym.web.BaseBasicAction;
import com.freegym.web.basic.Member;
import com.freegym.web.config.Setting;

@Namespace("")
@InterceptorRefs({ @InterceptorRef("customStack") })
@Results({ @Result(name = "success", location = "/wxv45/mine_info.jsp"),
		@Result(name = "login", location = "/WX/login.jsp"),
		@Result(name = "pwd", location = "/WX/personalPassworde.jsp") })
public class PersonalWxV45ManageAction extends BaseBasicAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1519271609208592302L;
	private Member member;
	private Setting setting;
	private String name;// 昵称
	private String password;// 密码
	private String sex;// 性别
	private Date birthday;// 生日
	private String add;// 地址
	private String province;// 省
	private String city;// 城
	private String county;// 县
	private int height;// 身高
	private int heart;// 静心率
	private int bmiLow;// 靶心率阈值下限
	private int bmiHigh;// 靶心率阈值上限

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public Setting getSetting() {
		return setting;
	}

	public void setSetting(Setting setting) {
		this.setting = setting;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getAdd() {
		return add;
	}

	public void setAdd(String add) {
		this.add = add;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCounty() {
		return county;
	}

	public void setCounty(String county) {
		this.county = county;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getHeart() {
		return heart;
	}

	public void setHeart(int heart) {
		this.heart = heart;
	}

	public int getBmiLow() {
		return bmiLow;
	}

	public void setBmiLow(int bmiLow) {
		this.bmiLow = bmiLow;
	}

	public int getBmiHigh() {
		return bmiHigh;
	}

	public void setBmiHigh(int bmiHigh) {
		this.bmiHigh = bmiHigh;
	}

	/*
	 * 个人信息首页 (non-Javadoc)
	 * 
	 * @see com.opensymphony.xwork2.ActionSupport#execute()
	 */
	@Override
	public String execute() {
		// Test Data
		Member memberTest = new Member();
		memberTest.setId(Long.parseLong("335"));
		memberTest.setRole("M");
		session.setAttribute("member", memberTest);
		
		// 个人简介
		member = (Member) session.getAttribute("member");
		// 查询个人的次数、时间、完成率
		if (member != null) {
			long m = member.getId();
			StringBuffer sb = new StringBuffer();
			sb.append(
					"select m.id,m.city,m.name,m.image,count(m.id) as timeNum ,sum(round((t.times/60),2)) as time ,round((COUNT(t.confrim)/m.workouttimes *100),1) as finishrate from tb_member m JOIN tb_plan_record t ON m.id=t.partake where t.done_date<=SYSDATE() ");
			sb.append("and m.id ='" + m + "' group by m.id");
			final List<Object> parm = new ArrayList<Object>();
			pageInfo = service.findPageBySql(sb.toString(), pageInfo, parm.toArray());
		} else {
			return "login";
		}
		return SUCCESS;
	}

	/*
	 * 修改昵称
	 */
	public String savename() {
		Member m = (Member) session.getAttribute("member");
		m.setName(member.getName());
		m = (Member) service.saveOrUpdate(m);
		session.setAttribute("member", m);
		return execute();
	}

	/*
	 * 修改密码
	 */
	public String savepwd() {
		Member m = (Member) session.getAttribute("member");
		m.setPassword(password);
		m = (Member) service.saveOrUpdate(m);
		session.setAttribute("member", m);
		return execute();
	}

	public void check() {
		String msg = "";
		member = (Member) session.getAttribute("member");
		if (member.getPassword().equals(password)) {
			msg = "ok";
		} else {
			msg = "error";
		}
		response(msg);
	}

	public String pwd() {
		member = (Member) session.getAttribute("member");
		return "pwd";
	}

	// 修改性别
	public String savesex() {
		Member m = (Member) session.getAttribute("member");
		m.setSex(member.getSex());
		m = (Member) service.saveOrUpdate(m);
		session.setAttribute("member", m);
		return execute();
	}

	// 修改出生年月
	public String savebirthday() {
		Member m = (Member) session.getAttribute("member");
		m.setBirthday(birthday);
		m = (Member) service.saveOrUpdate(m);
		session.setAttribute("member", m);
		return execute();
	}

	// 修改地址
	public String saveAdd() {
		if (add.equals("台湾省")) {
			add = "台湾，台湾，台湾";
		} else if (add.equals("澳门特别行政区")) {
			add = "澳门，澳门,澳门";
		} else if (add.equals("香港特别行政区")) {
			add = "香港,香港,香港";
		}
		String[] strArray = add.split(",");
		Member m = (Member) session.getAttribute("member");
		m.setProvince(strArray[0]);
		if ("市辖区".equals(strArray[1]) || "县".equals(strArray[1])) {
			m.setCity(strArray[0]);
		} else {
			m.setCity(strArray[1]);
		}
		m.setCounty(strArray[2]);
		m = (Member) service.saveOrUpdate(m);
		session.setAttribute("member", m);
		return execute();
	}

	// 修改身高
	public String saveHeight() {
		Member m = (Member) session.getAttribute("member");
		Setting s = service.loadSetting(m.getId());
		s.setHeight(height);
		s = (Setting) service.saveOrUpdate(s);
		m.setSetting(s);
		m = (Member) service.saveOrUpdate(m);
		session.setAttribute("member", m);
		return execute();
	}

	// 修改静心率
	public String saveHeart() {
		Member m = (Member) session.getAttribute("member");
		Setting s = service.loadSetting(m.getId());
		s.setHeart(heart);
		s = (Setting) service.saveOrUpdate(s);
		m.setSetting(s);
		m = (Member) service.saveOrUpdate(m);
		session.setAttribute("member", m);
		return execute();
	}

	// 修改靶心率阈
	public String saveBmi() {
		Member m = (Member) session.getAttribute("member");
		Setting s = service.loadSetting(m.getId());
		s.setBmiHigh(bmiHigh);
		s.setBmiLow(bmiLow);
		s = (Setting) service.saveOrUpdate(s);
		m.setSetting(s);
		m = (Member) service.saveOrUpdate(m);
		session.setAttribute("member", m);
		return execute();
	}
}
