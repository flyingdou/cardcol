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
	if ('<s:property value="message"/>' != '') {
		alert('<s:property value="message"/>');
	}
	$('#left-1 ul li a').css('cursor', 'pointer');
	$("div[id^=right-2-]").dialog({autoOpen: false, show: "blind", hide: "explode", modal: true, resizable: false, width: 480});
	$('.save-to').click(function(){
		if (!$('#email').val().isEmail()) {
			alert('您输入的邮箱地址错误，请重新输入！');
			$('#email').select();
			return;
		}
		var params = $('#userform').serialize();
		$.ajax({url:'email!save.asp',type:'post',data: params,
			success:function(msg){
				if (msg === 'ok') {
					alert('数据已经成功保存！');
				} else {
					alert(msg);
				}
			}
		});
	});
});
function validateMail() {
	$('#oldemail').html($('#email').val());
	$('#right-2-5').dialog('open');
}
function emailValid() {
	loadMask();
	var email = $('#email').val();
	if (email == '')return;
	$.ajax({url: 'email!validEmail.asp', type: 'post', data: 'email=' + email + '&id=' + $('#memberId').val(), 
		success: function(msg){
			removeMask();
			if (msg == 'OK') {
				alert('您的邮箱验证信息已经发到您的邮箱中,请到您的邮箱中进行验证!');
			} else {
				alert('验证出错，可能的原因为：' + msg);
			}
		}
	});
}

function onModifyEmail() {
	var email = $('#newemail').val();
	if (email == '') {
		alert('请先输入新的邮件地址再进行验证！');
		return;
	}
	var parms = $('#userform').serialize() + '&email=' + email;
	$.ajax({url: 'email!modifyEmail.asp', type: 'post', data: parms, 
		success: function(msg){
			removeMask();
			if (msg == 'ok') {
				alert('您的邮箱验证信息已经发到您的邮箱中,请到您的邮箱中进行验证!');
			} else if (msg == 'exist') {
				alert('该邮箱地址已经注册过，请重新输入您的邮箱地址！');
				return;
			} else {
				alert('验证出错，可能的原因为：' + msg);
			}
		}
	});
}
</script>
</head>
<body>
	<s:include value="/share/header.jsp"/>
	<div id="content">
		<s:include value="/basic/nav.jsp" />
		<div id="right-2">
			<div id="container">
				<h1>邮箱认证</h1>
				<s:form theme="simple" id="userform" name="userform" method="post">
				<s:hidden name="code" id="code"/>
				<s:hidden name="member.id" id="memberId"/>
				<div>
					<div class="p_div"><p>电子邮件: 
					<s:if test="member.emailValid == '1'">
						<s:textfield name="member.email" cssClass="name-1" id="email" maxLength="100" disabled="true"/>
						<s:hidden name="member.email" />
					</s:if>
					<s:else><s:textfield name="member.email" cssClass="name-1" id="email" maxLength="100" /></s:else></p>
						<s:if test="member.emailValid == '1'"><a class="checkmobile-btn" onclick="validateMail()">修改</a></s:if>
						<s:else><a class="checkmobile-btn" onclick="emailValid()">立即验证</a></s:else>
					</div>
				</div>
				</s:form>
			</div>
			<input type="button" name="botton" value="保存" class="save-to" />
		</div>
	</div>
	<s:include value="/share/footer.jsp"/>
	<div id="right-2-5" title="修改邮箱">
		<div id="container">
			<div class="change-mail">
				<p class="mail-warning">请输入新的邮箱地址</p>
				<p class="Original-email">新邮箱地址：<input id="newemail" /></p>
				<p class="nextp"><a class="nextstep nextsteppyj" onclick="javascript: onModifyEmail()">发验证邮件</a></p>
			</div>
			<div class="bottom-mail">
				<span class="">邮箱绑定成功后，您可以享有以下服务：</span><br>
				<span class="detail">①邮件到达需要2-3分钟，若您长时间未收到邮件，建议您检查邮件中的垃圾邮件或者<a class="cfyzm">重发验证信</a></span><br>
				<span class="detail">②如果您的原邮箱已经无法使用，可以通过账号申诉进行邮箱更换。</span><br>
			</div>
		</div>	
	</div>
 </body>
</html>
