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
	function getValidateForNew(o) {
		var mobile = $('#newmobilephone').val();
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
	function submitMobile() {
		var mobile = $('#newmobilephone').val();
		var code = $('#newvalidate').val();
		loadMask();
		$.ajax({
			url : 'mobile!saveMobileValid.asp',
			type : 'post',
			data : 'mobile=' + mobile + '&code=' + code,
			success : function(msg) {
				removeMask();
				if (msg == 'OK') {
					$('#newMobile').val(mobile);
					$('#userform2').attr('action', 'mobile!mobile3.asp');
					$('#userform2').attr('method', 'post');
					$('#userform2').submit();
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
		<div id="right-2" title="手机验证">
			<div id="container">
				<div class="steps">
					<ul class="stepphoneyy">
						<li><span>1.验证原手机</span></li>
						<li class="current"><span>2.输入新的手机号码</span></li>
						<li><span>3.修改成功</span></li>
					</ul>
				</div>
				<s:form theme="simple" id="userform2" name="userform2" method="post">
					<s:hidden name="newMobile" id="newMobile" />
					<div class="change-mail">
						<p>
							请输入新的手机号码：<input type="text" id="newmobilephone">
						</p>
						<p class="Original-email">
							验&nbsp;证&nbsp;码：<input class="yanzhengma" type="text"
								id="newvalidate"> <input type="button"
								onclick="getValidateForNew(this)" value="获取验证码">
						</p>
						<p class="nextp">
							<a class="nextstep" onclick="submitMobile()">提&nbsp;&nbsp;&nbsp;交</a>
						</p>
					</div>
				</s:form>
				<div class="bottom-mail">
					<span class="">提示：</span><br> <span class="detail">①若您1分钟之内没有收到短信，建议您重发验证码</span><br>
							<span class="detail">②绑定成功后，可享用重要事件提醒及账号保护</span><br>
				</div>
			</div>
		</div>
	</div>
	<s:include value="/share/footer.jsp" />
</body>
</html>
