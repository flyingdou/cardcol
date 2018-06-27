package com.taobao.api.domain;

import com.taobao.api.internal.mapping.ApiField;
import com.taobao.api.TaobaoObject;


/**
 * 系统自动生成
 *
 * @author top auto create
 * @since 1.0, null
 */
public class Userinfos extends TaobaoObject {

	private static final long serialVersionUID = 8727283714542227691L;

	/**
	 * 地址，最大长度512
	 */
	@ApiField("address")
	private String address;

	/**
	 * 年龄
	 */
	@ApiField("age")
	private Long age;

	/**
	 * 职位，最大长度128字节
	 */
	@ApiField("career")
	private String career;

	/**
	 * email地址，最大长度128字节
	 */
	@ApiField("email")
	private String email;

	/**
	 * 扩展字段（Json），最大长度4096字节
	 */
	@ApiField("extra")
	private String extra;

	/**
	 * 性别。M: 男。 F：女
	 */
	@ApiField("gender")
	private String gender;

	/**
	 * 最后更新时间
	 */
	@ApiField("gmt_modified")
	private String gmtModified;

	/**
	 * 头像url，最大长度512字节
	 */
	@ApiField("icon_url")
	private String iconUrl;

	/**
	 * 手机号码，最大长度16字节
	 */
	@ApiField("mobile")
	private String mobile;

	/**
	 * 名字，最大长度64
	 */
	@ApiField("name")
	private String name;

	/**
	 * 昵称，最大长度64字节
	 */
	@ApiField("nick")
	private String nick;

	/**
	 * im密码，最大长度64字节
	 */
	@ApiField("password")
	private String password;

	/**
	 * qq，最大长度20字节
	 */
	@ApiField("qq")
	private String qq;

	/**
	 * remark，最大长度128字节
	 */
	@ApiField("remark")
	private String remark;

	/**
	 * 用户激活状态，0表示未激活，1表示激活
	 */
	@ApiField("status")
	private Long status;

	/**
	 * 淘宝账号，最大长度64字节
	 */
	@ApiField("taobaoid")
	private String taobaoid;

	/**
	 * im用户名，最大长度64字节
	 */
	@ApiField("userid")
	private String userid;

	/**
	 * vip（Json），最大长度512字节
	 */
	@ApiField("vip")
	private String vip;

	/**
	 * 微信，最大长度64字节
	 */
	@ApiField("wechat")
	private String wechat;

	/**
	 * 微博，最大长度256字节
	 */
	@ApiField("weibo")
	private String weibo;


	public String getAddress() {
		return this.address;
	}
	public void setAddress(String address) {
		this.address = address;
	}

	public Long getAge() {
		return this.age;
	}
	public void setAge(Long age) {
		this.age = age;
	}

	public String getCareer() {
		return this.career;
	}
	public void setCareer(String career) {
		this.career = career;
	}

	public String getEmail() {
		return this.email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	public String getExtra() {
		return this.extra;
	}
	public void setExtra(String extra) {
		this.extra = extra;
	}

	public String getGender() {
		return this.gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getGmtModified() {
		return this.gmtModified;
	}
	public void setGmtModified(String gmtModified) {
		this.gmtModified = gmtModified;
	}

	public String getIconUrl() {
		return this.iconUrl;
	}
	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}

	public String getMobile() {
		return this.mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getName() {
		return this.name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getNick() {
		return this.nick;
	}
	public void setNick(String nick) {
		this.nick = nick;
	}

	public String getPassword() {
		return this.password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	public String getQq() {
		return this.qq;
	}
	public void setQq(String qq) {
		this.qq = qq;
	}

	public String getRemark() {
		return this.remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Long getStatus() {
		return this.status;
	}
	public void setStatus(Long status) {
		this.status = status;
	}

	public String getTaobaoid() {
		return this.taobaoid;
	}
	public void setTaobaoid(String taobaoid) {
		this.taobaoid = taobaoid;
	}

	public String getUserid() {
		return this.userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getVip() {
		return this.vip;
	}
	public void setVip(String vip) {
		this.vip = vip;
	}

	public String getWechat() {
		return this.wechat;
	}
	public void setWechat(String wechat) {
		this.wechat = wechat;
	}

	public String getWeibo() {
		return this.weibo;
	}
	public void setWeibo(String weibo) {
		this.weibo = weibo;
	}

}
