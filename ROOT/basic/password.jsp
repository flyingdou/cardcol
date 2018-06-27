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
<style type="text/css">
input{ border:1px solid #999;}
</style>
<script language="javascript">
$(document).ready(function() {
	$('#left-1 ul li a').css('cursor', 'pointer');
	$('input[type="button"]').click(function(){
		if ($('#password').val() == '') {
			alert('请输入原密码！');
			$('#password').focus();
			return;
		}
		if ($('#password1').val() != $('#password2').val()) {
			alert('两次输入的密码不一样，请重新输入！');
			$('#password1').focus();
			return;
		}
		if ($('#password1').val() == '') {
			alert('新密码不能为空，请输入！');
			$('#password1').focus();
			return;
		}
		loadMask();
		var params = {'member.id': $('#memberId').val(), 'member.password': md5($('#password1').val())};
		$.ajax({url:'password!save.asp',type:'post',data: params,
			success:function(msg){
				removeMask();
				alert('数据已经成功保存！');
			}
		});
	});
	$('#password').blur(function(){
		var $the = $(this);
		var val = $the.val();
		if (val == '') return;
		$.ajax({type: 'post',url: 'password!check.asp',data: 'password=' + md5(val),
			success: function(msg) {
				if (msg != 'ok') {
					alert('您输入的原密码不正确，请重新输入！');
					$the.focus();
					$the.select();
				}
			}
		});
	});
});
</script>
</head>
<body>
	<s:include value="/share/header.jsp"/>
	<div id="content">
		<s:include value="/basic/nav.jsp" />
		<div id="right-2">
			<div id="container">
				<h1>登录密码</h1>
				<s:form theme="simple" id="userform" name="userform" action="account.asp" method="post">
				<s:hidden name="code" id="code"/>
				<s:hidden name="member.id"/>
				<div>
					<div class="p_div"><p>旧 　密　 码：<input type="password" name="text" id="password"/></p></div>
					<div class="p_div"><p>新 　密　 码：<input type="password" name="member.password" id="password1" /></p></div>
					<div class="p_div"><p>再次输入密码：<input type="password" name="text" id="password2" /></p></div>
				</div>
				</s:form>
			</div>
			<input type="button" name="botton" value="保存" class="save-to" />
		</div>
	</div>
	<s:include value="/share/footer.jsp"/>
</body>
</html>
