<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta name="keywords" content="健身E卡通,忘记密码,用户登录" />
<meta name="description" content="健身E卡通_忘记密码" />
<link rel="stylesheet" type="text/css" href="css/find.css" />
<s:include value="/share/meta.jsp" />
<title>健身E卡通_忘记密码</title>
<script type="text/javascript">
function findPass() {
	var valid = true;
	$("input").each(function() {
		if ($(this).val() == '' && valid === true && $(this).attr('tag')) {
			alert($(this).attr('tag') + '不能为空，请重新输入！');
			$(this).focus();
			valid = false;
		}
	});
	if (!valid)
		return;
	var code = $('#validatecode').val();
	var email = $('#validateEmail').val();
	$('#email').val(email);
	$.ajax({
		url : 'findpassword!validateEmail.asp',
		type : 'post',
		data : 'email=' + email + '&code=' + code,
		success : function(msg) {
			if (msg == 'OK') {
				$('#form1').attr('action','findpassword!resetPassword.asp');
				$('#form1').submit();
			} else {
				alert('您输入的验证码有误，请重新输入！');
			}
		}
	});
}

function getValidateForEmail(email,o) {
	$.ajax({
		url : 'findpassword!send.asp',
		type : 'post',
		data : 'email=' + email,
		success : function(msg) {
			if (msg == 'OK') {
				alert('当前验证码已经成功发送到您的邮箱，请注意查收！');
				time(o);
			} else {
				alert(msg);
			}
		}
	});
}

function getValidate(o) {
	var email = $('#validateEmail').val();
	if (email != null && email != "") {
		getValidateForEmail(email,o);
	} else {
		alert("请输入邮箱账号！");
	}
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
</script>
<body>
<s:include value="/share/home-header_1.jsp" />
  <div class="findmima">
     <ul id="num4">
      <li class="first done">
	     <span><i>1</i><b>选择验证方式</b></span>
	  </li>
	   <li class="current">
	     <span><i>2</i><b>验证身份</b></span>
	  </li>
	   <li>
	     <span><i>3</i><b>重置密码</b></span>
	  </li>
	   <li class="last">
	     <span><i>4</i><b>完成</b></span>
	  </li>
	 </ul>
  </div>
  <div class="navcontent">
        <form id="form1" action="" method="post">
        <s:hidden name="email" id="email"/>
		     <div class="cleft2">
				 <div class="check_box">
				     <p><span>您的邮箱:</span><input type="text" id="validateEmail" class="emals" tag="邮箱"/></p>
					 <p><input type="button"  value="获取验证码" id="mifei" onclick="getValidate(this)"/></p>
					 <p><span>&nbsp;验证码：</span><input type="text" id="validatecode" tag="验证码"/></p><br>
                     <input type="button" value="下一步" id="xiayibu" onclick="javascript:findPass();">
				 </div>
              </div>
	   </form>
  </div>
    
  <s:include value="/share/footer.jsp" />
</body>
</html>
