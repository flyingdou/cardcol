<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div id="left-1">
	<h1 style="background-image:none;background:#ff4401;color:white">我的帐户</h1>
  <div>
    <ul class="order">
      <li> <a href="#"><h1 style=" background:none; border-bottom:0px;"><b>账户信息</b></h1></a>
        <ul class="ordermanagement">
          <li><a href="account.asp" id="coloa">基本信息</a></li>
		  <li><a href="linkemp.asp" id="coloa">联系方式</a></li>
		  <li><a href="picture.asp" id="coloa">头像/背景图</a></li>
		  <li><a href="clubmp!wifi.asp" id="coloa">设置WIFI</a></li>
 	  	  <s:if test="#session.loginMember.role != \"M\"">
		  	<li><a id="coloa" href="locat.asp">地理位置</a></li>
		  	<li><a id="coloa" href="twocode.asp">我的二维码</a></li>
	  	  </s:if>
        </ul>
      </li>
      
      <li> <a href="#"><h1 style=" background:none; border-bottom:0px;"><b>安全设置</b></h1></a>
        <ul class="ordermanagement">
		  <li><a href="password.asp" id="coloa">登陆密码</a></li>
<%-- 		  <s:if test="#session.loginMember.role != \"M\"">
		  <li><a href="signpassword.asp" id="coloa">签到密码</a></li>
		  </s:if> --%>
          <li><a href="mobile.asp" id="coloa">手机认证</a></li>
		  <li><a href="email.asp" id="coloa">邮箱认证</a></li>
		  <s:if test="#session.loginMember.role != \"E\"">
		  <li><a href="card.asp" id="coloa">身份证认证</a></li>
		  </s:if>
		  <s:if test="#session.loginMember.role != \"M\"">
		  <li><a id="coloa" href="cert.asp">证书验证</a></li>
		  </s:if>
    	</ul>
      </li>
    </ul>
  </div>
</div>
