<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<title>登录页面</title>
<link type="text/css" href="${pageContext.request.contextPath}/WX/css/bootstrap.css" rel="stylesheet">
<link type="text/css" href="${pageContext.request.contextPath}/WX/css/base.css" rel="stylesheet">
<link type="text/css" href="${pageContext.request.contextPath}/WX/css/form.css" rel="stylesheet">
<script type="text/javascript" src="${pageContext.request.contextPath}/WX/js/jquery-2.2.0.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/WX/js/style.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/WX/js/md5.js"></script>
<script type="text/javascript">
	$(document).ready(function(){
	    $("#btnLogin").click(function(){
	    	var mobilephone = $('input[name="mobilephone"]').val();
	    	var passq = $('input[name="password"]').val();
	    	var pass = hex_md5(passq);
	    	 $.ajax({
	             type:"POST",
	             url:"loginwx!dialog.asp",
	             data:{mobilephone:mobilephone,password:pass},
	             success:function(msg){
	            	  if( msg =="success"){
	            		 var _href = window.location.href;
		            		if(_href.indexOf('loginwx.asp')<0){
		            			 window.location.href = _href;
		            		 }else{
		            			 window.location.href = "personalwx.asp";
		            		 }
	            	 }else{
	            		 alert("你登录的账号或密码错误")
	            	 }
	             }            
	          });
	    });
	});
	
</script>
</head>
<body>
	<div class="container">
		<form method="post" id="from1">
			帐号:<input type="text" name="mobilephone" placeholder="请输入您的手机号"
				onfocus="this.placeholder=''" value=""> <a
				href="register.jsp">注册帐号</a><br> 密码:<input type="password"
				name="password" placeholder="请输入您的密码" onfocus="this.placeholder=''"
				onblur="this.placeholder='请输入您的帐号'" value=""><a href="forget_password.jsp">忘记密码</a><br>
			<input type="button" name="Submit" value="登录" class="send" id="btnLogin">
		</form>
	</div>
</body>
</html>