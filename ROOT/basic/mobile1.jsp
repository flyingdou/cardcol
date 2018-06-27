<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<s:include value="/share/meta.jsp" />
<meta name="keywords" content="健身E卡通-我的账户" />
<meta name="description" content="健身E卡通-我的账户" />
<title>健身E卡通-我的账户</title>
<link rel="stylesheet" type="text/css" href="css/user-account.css" />
<link rel="stylesheet" type="text/css" href="css/pulicstyle.css" />
<script language="javascript">
	$(document).ready(function() {
		$('#left-1 ul li a').css('cursor', 'pointer');
		$('#oldmobilephone').val(<s:property value="oldMobile"/>);
	});
	function getValidateForMobile(mobile) {
		$.ajax({
			url : 'mobile!getMobileValidCode.asp',
			type : 'post',
			data : 'mobile=' + mobile + '&id=' + $('#memberId').val(),
			success : function(msg) {
				if (msg == 'OK') {
					alert('当前验证码已经成功发送到您的手机上，请注意查收！');
				} else {
					alert('验证码发送失败！原因为：' + msg);
				}
			}
		});
	}
	
	function getOldValidate(o) {
		var mobile = $('#oldmobilephone').val();
		getValidateForMobile(mobile);
		time(o);
	}
	
	var wait = 120;
	function time(o){
		 if (wait == 0) {
		　 　 　 o.removeAttribute("disabled");
		　 　 　 o.value = "获取验证码";
		　 　 　 wait = 120;
		 } else {
		　 　 　 o.setAttribute("disabled", true);
		　 　 　 o.value = "重新发送(" + wait + ")";
		　 　 　 wait--;
		　 　 　 setTimeout(function() {
		　 　 　 time(o);
		　 　 　 }, 1000);
		　};
	}
	function nextStep1() {
		loadMask();
		var code = $('#oldvalidate').val();
		var mobile = $('#oldmobilephone').val();
		$.ajax({
			url : 'mobile!validMobile.asp',
			type : 'post',
			data : 'mobile=' + mobile + '&code=' + code,
			success : function(msg) {
				removeMask();
				if (msg == 'OK') {
					window.location.href = "mobile!mobile2.asp";
				} else {
					alert('您输入的验证码有误，请重新输入！');
				}
			}
		});
	}
</script>
</head>
<body>
	<s:include value="/share/header.jsp" />
	<div id="content">
		<s:include value="/basic/nav.jsp" />
		<div id="right-2">
			<div id="container">
				<h1>手机认证</h1>
				<div class="steps">
					<ul class="stepcont">
						<li class="current"><span>1.验证原手机</span></li>
						<li><span>2.输入新的手机号码</span></li>
						<li><span>3.修改成功</span></li>
					</ul>
				</div>
				<div class="change-mail">
					<p class="mail-warning">修改手机前须先验证原手机的信息。</p>
					<p class="Original-email">
						原手机号：<input type="text" id="oldmobilephone" readonly="readonly" >
					</p>
					<p class="Original-email">
						验证码：<input type="text" id="oldvalidate" class="yanzhengma"><input
							type="button" onclick="getOldValidate(this)" value="获取验证码">
					</p>
					<p class="nextp">
						<a class="nextstep" onclick="nextStep1()">下一步</a>
					</p>
				</div>
				<div class="bottom-mail">
					<span class="">常见问题</span><br> <span class="detail">①若您1分钟之内没有收到短信，建议您重发验证码</span><br> <span class="detail">②手机卡号遗失，无法使用手机接受短信验证码-请致电客服热线027-87573247</span><br>
				</div>
			</div>
		</div>
	</div>
	<s:include value="/share/footer.jsp" />
</body>
</html>
