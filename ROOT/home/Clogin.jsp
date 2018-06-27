<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<s:include value="/share/meta.jsp"/>
<meta name="keywords" content="卡库网-健身电子商务-服务平台" />
<meta name="description" content="卡库网-用户登录" />
<title>卡库网-用户登录</title>
<link type="text/css" href="css/bootstrap.css" rel="stylesheet">
<link type="text/css" href="css/base.css" rel="stylesheet">
<link rel="stylesheet" type="text/css" href="css/login.css" />
<script language="javascript">
$(function(){
	$('#divrole').dialog({autoOpen: false, width: 500, height: 300});
	$('.inputs').keypress(function(e){
		if (e.keyCode == 13) {
			var inputs = $('.inputs');
			var idx = inputs.index(this);
			if (idx == inputs.length - 1) {
				login();
			} else {
				inputs[idx + 1].focus();
				inputs[idx + 1].select();
				return false;
			}
		}
		return true;
	});
});
function login() {
	var uname = $('input[name="username"]').val();
	var pass = $('input[name="password"]').val();
	if (uname == '') {
		alert('请输入您的用户名！');
		$('input[name="username"]').focus();
		$('input[name="username"]').select();
		return;
	}
	if (pass == '') {
		alert('请输入您的密码！');
		$('input[name="password"]').focus();
		return;
	}
	pass = md5(pass);
	$.ajax({type: 'post', url: 'login!check.asp?v=' + Math.round(Math.random() * 10000), data: 'username=' + uname + '&password=' + pass,
		success: function(resp) {
			if (resp === 'success') {
				var _href = window.location.href;
				if (_href.indexOf('login.asp') < 0) {
					var index = _href.indexOf('#');
					if (index > 0) _href = _href.substring(0, index);
					window.location.href = _href;
					return;
				}
				window.location.href = 'login.asp?v=' +Math.round(Math.random() * 10000);
			} else {
				alert('您的账户或密码不正确！');
				$('input[name="username"]').focus();
			}
		}
	});
}
function reg(){
	window.location.href = 'register.asp';
}
</script>
</head>
<body>          
        <s:include value="/newpages/head.jsp"></s:include>
		<div class="top_bg"></div>
		
		<div id="user">
			<div id="user-1">用户登录</div>
			<div id="user-left">
			  <img src="images/jlb.jpg"/>
			</div>
			<div id="user-right">
			    <div class="logdiv">
				    <div class="logdiv1"><b>登录卡库网</b></div>
					<!--<div class="logdiv2">还不是卡库网用户？</div>-->
					<div class="logdiv3"></div>
				</div>
				
				<p>用户名: <input type="text" name="username" class="name-1 inputs" value="邮箱/手机/用户名登陆"
				onmouseover=this.focus();this.select(); 
				onclick="if(value==defaultValue){value='';this.style.color='#000'}" 
				onBlur="if(!value){value=defaultValue;this.style.color='#999'}" style="color:#999"/></p>
				<p>密　码: <input type="password" name="password" class="name-1 inputs" /> <a href="findpassword.asp" id="coloa">忘记密码</a></p>
				<p id="end">
					<!--<input type="checkbox" name="checkbox" style="float:left;  width:20px;"/>绑定账号-->
					<input type="button" name="Submit" value="" class="name-4" onclick="login()"/> 
					<input type="button" onclick="reg()"  class="name-5"/>
				</p>
				<div class="cooperation-login">
					<div class="">
						<span id="tishi">使用合作网站登录</span>
					</div>
					
					<div class="other_accont">
						<div id="qqLoginBtn" class="qqloginbtn" onclick="javascript:window.location.href='qqlogin.asp';">
							<span>QQ</span>
						</div>
						<!-- 
						<div id="qqLoginBtn" class="qqloginbtn" onclick="javascript:window.location.href='https://graph.qq.com/oauth2.0/authorize?response_type=code&client_id=100332016&redirect_uri=http://www.cardcol.com/qqcallback.asp&state=123456';">
							<span>QQ</span>
						</div>
						 -->
					</div>
					<div class="other_accont">
						<div id="sina_LoginBtn" class="sina_LoginBtn">
							<span><a href="<s:property value="#request.sinaurl"/>">新浪</a></span>
						</div>
					</div>
				</div>
				<!--<div class="updiv">
				     <div class="updiv1">使用合作网站账号登录：</div>
					 <div class="updiv2">
					    <div class="updiv2-1">
						    <div class="updiv2-1-1"><img src="images/xinla.jpg"/></div>
							<div class="updiv2-1-2"><a href="#" id="coloa">新浪微博</a></div></div>
						</div> 
				</div>-->
			</div>
		</div>
		<s:include value="/newpages/footer.jsp"></s:include>
</body>
</html>

