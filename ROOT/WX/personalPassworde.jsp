<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<title>密码修改</title>
<link type="text/css" href="${pageContext.request.contextPath}/WX/css/bootstrap.css" rel="stylesheet">
<link type="text/css" href="${pageContext.request.contextPath}/WX/css/base.css" rel="stylesheet">
<link type="text/css" href="${pageContext.request.contextPath}/WX/css/form.css" rel="stylesheet">
<script type="text/javascript" src="${pageContext.request.contextPath}/WX/js/jquery-2.2.0.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/WX/js/style.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/WX/js/md5.js"></script>
<script>
		$(document).ready(function(){
			$("#pwd").blur(function(){
				if ($("#pwd").val() == '')
					return;
				$.ajax({
					type:"post",
					url:"personalwx!check.asp",
					data : "password=" + hex_md5($("#pwd").val()),
					success:function(msg){
						if(msg=="error"){
							alert("您输入的原始密码不正确！");
							 $('#pwd1').attr('disabled',true);
							 $('#pwd2').attr('disabled',true);
							return;
						}else{
							 $('#pwd1').attr('disabled',false);
							 $('#pwd2').attr('disabled',false);
						}
					}
						
				});
			})
		
			$('.send').click(function() {
				if ($('#pwd').val() == '') {
					alert('请输入原密码！');
					$('#pwd').focus();
					return;
				}
				if ($('#pwd1').val() != $('#pwd2').val()) {
					alert('两次输入的密码不一样，请重新输入！');
					$('#pwd1').focus();
					return;
				}
				if ($('#pwd1').val() == '') {
					alert('新密码不能为空，请输入！');
					$('#pwd1').focus();
					return;
				}
			var pwd = hex_md5($('#pwd1').val())
			var val = $("#pwd3").val(pwd)
			url = 'personalwx!savepwd.asp'
			$('#perForm').attr("action", url);
			$('#perForm').submit();
			});
			
		});
    </script>
</head>
<body>
	<div class="container">
		<form id="perForm" action="personalwx!savepwd.asp" method="post"> 
			<span>旧密码:</span><input type="password" placeholder="请输入旧密码" onfocus="this.placeholder=''" onblur="this.placeholder='请输入旧密码'" value="" id="pwd"><br>
			<span>新密码:</span><input type="password" placeholder="请输入您新密码" onfocus="this.placeholder=''" onblur="this.placeholder='请输入您新密码'" value="" id="pwd1"><br>
			<span>确认密码:</span><input type="password" placeholder="请重新输入新密码" onfocus="this.placeholder=''" onblur="this.placeholder='请重新输入新密码'" value="" id="pwd2"><br> 
			<input type="hidden" name="password" value="" id="pwd3"> 
			<input type="submit" value="保存" class="send">
		</form>
	</div>
</body>