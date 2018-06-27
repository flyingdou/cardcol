<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <title>绑定手机号</title>
    <link type="text/css" href="css/bootstrap.css" rel="stylesheet">
    <link type="text/css" href="css/base.css" rel="stylesheet">
    <link type="text/css" href="css/form.css" rel="stylesheet">
    <script type="text/javascript" src="js/jquery-2.2.0.min.js"></script>
    <script type="text/javascript" src="js/style.js"></script>
    <script type="text/javascript">
    function getValidateForMobile(mobile) {
		$.ajax({
			url : 'mobilewx!getMobileValidCode.asp',
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
    
    function getValidCode(o){
    	var mobile = $("#mobilephone").val();
    	if(mobile==''){
    		alert('请先输入需要验证的手机号码！');
    		$('#mobilephone').focus();
			return;
    	}
    	getValidateForMobile(mobile);
    	time(o);
    }
    var wait = 120;
    function time(o){
    	  if (wait == 0) {
		　 　 　 o.removeAttribute("disabled");
		   		o.value="获取验证码";
		　 　 　 wait = 120;
		 } else {
		　 　 　 o.setAttribute("disabled", true);
		  		 o.value="重新发送(" + wait + ")";
		　 　 　 wait--;
		　 　 　 setTimeout(function() {
		　 　 　 time(o);
		　 　 　 }, 1000);
		　};
	}
    
	 function validateMobile(){
		var mobile = $("#mobilephone").val();
		var code = $("#validcode").val();
		if(code == ''){
			alert('请先输入您的验证码再进行验证！');
			return;
		}
		$.ajax({
			url : 'mobilewx!saveMobileValid.asp',
			type : 'post',
			data :'mobile=' + mobile + '&code=' + code,
			success : function(data){
				if (data == 'OK') {
					window.location.href ="personalwx.asp";
				} else {
					alert('您输入的验证码有误，请重新输入！');
				}
			}
		});
	}
    </script>
</head>
<body>
<div class="container">
    <s:form theme="simple" id="userform" name="userform" method="post">
    	<s:hidden name="code" id="code" />
		<s:hidden name="member.id" id="memberId" />
		<s:hidden name="oldMobile" id="oldMobile" />
        <span>手机号:</span><input type="text" name="" placeholder="请输入手机号" onfocus="this.placeholder=''" onblur="this.placeholder='请输入手机号'" value="" id="mobilephone">
        <input type="button"  onclick="getValidCode(this)" value="发送验证码" class="phone"><br>
        <span>验证码:</span><input type="text" placeholder="请输入验证码" onfocus="this.placeholder=''" onblur="this.placeholder='请输入验证码'" value="" id="validcode"><br>
        <input type="submit"  value="绑定" class="send" onclick="validateMobile();">
    </s:form>
</div>
</body>
</html>