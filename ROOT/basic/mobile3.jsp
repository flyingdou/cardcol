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
	$('#mobilephone').val("<s:property value="newMobile"/>");
	$('.newmobile1').html("<s:property value="newMobile"/>");
});
function closeMobile() {
	window.location.href = "mobile.asp";
}
</script>
</head>
<body>
	<s:include value="/share/header.jsp"/>
	<div id="content">
		<s:include value="/basic/nav.jsp" />
		<div id="right-2" title="手机验证">
		<div id="container">
                      <h1>手机认证</h1>
			<div class="steps">
				<ul class="stepsyanzheng">
					<li><span>1.验证原手机</span></li>
					<li><span>2.输入新的手机号码</span></li>
					<li class="current"><span>3.修改成功</span></li>
				</ul>
			</div>
			<div class="change-mail">
				<p class="mail-warning">您已经完成了手机修改绑定！</p>
				<p class="Original-email">您绑定的手机号是：<span class="newmobile1" ></span></p>
				<p class="nextp"><a class="nextstep" onclick="closeMobile()">完&nbsp;&nbsp;&nbsp;成</a></p>
			</div>
			<div class="bottom-mail">
				<span class="">手机绑定成功后，您可以享有以下服务：</span><br>
				<span class="detail">①重要事件提醒：进行（支付/提现/结算/训练周期/投诉/课程安排）时，可及时收到短信提醒</span><br>
				<span class="detail">②账号保护： 在您进行登录及修改密码等敏感操作时，未经您授权的操作将不被允许</span><br>
			</div>
		</div>
	</div>
	</div>
	<s:include value="/share/footer.jsp"/>
 </body>
</html>
