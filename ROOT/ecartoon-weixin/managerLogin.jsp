<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":"+ request.getServerPort() + path + "/";
%>
<!doctype html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>登录</title>
<style>
.STYLE1 {
	font-size: 11pt;
	font-weight: bold;
}
</style>
</head>
<body>
<body style="background-image:url(ecartoon-weixin/img/admin_login_bg.gif); margin:0 auto; width:500px;">
<div style="background-image:url(ecartoon-weixin/img/admin_login.png); width:500px; height:200px; margin-top:130px;">
  <form id="form1" name="form1" method="post" action="">
    <table width="500" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="250">&nbsp;</td>
        <td colspan="2">&nbsp;</td>
      </tr>
      <tr>
        <td height="40"><div align="right" class="STYLE1">用户名：</div></td>
        <td height="40" colspan="2"><input type="text" id="account" name="textfield" style="height:25px; width:200px; font-size:15pt; font-weight:bold;" /></td>
      </tr>
      <tr>
        <td height="40"><div align="right" class="STYLE1">密&nbsp;码：</div></td>
        <td height="40" colspan="2"><input type="password" id="password" name="textfield2" style="height:25px; width:200px; font-size:15pt; font-weight:bold;" /></td>
      </tr>
     <!--  <tr>
        <td height="40"><div align="right" class="STYLE1">验证码：</div></td>
        <td width="100" height="40"><input type="text" name="textfield3" style="height:25px; width:85px; font-size:15pt; font-weight:bold;" /></td>
        <td width="150">&nbsp;</td>
      </tr> -->
      <tr>
        <td height="40">&nbsp;</td>
        <td height="40" colspan="2">
        	<input type="button" name="Submit" value="" style="width:130px; height:30px; background-image:url(ecartoon-weixin/img/admin_login_button.png); border:0; background-color: transparent; " onclick="login()"/>
        </td>
      </tr>
    </table>
  </form>
  <script src="ecartoon-weixin/js/jquery.min.js" type="text/javascript"></script>
  <script type="text/javascript">
  	function login(){
  		var account = $("#account").val();
  		var password = $("#password").val();
  		$.ajax({
  			url:"balancemanager.asp",
  			type:"post",
  			data:{
  				account:account,
  				password:password
  			},
  			dataType:"json",
  			success:function(res){
  				if(res.success){
  					location.href = "ecartoon-weixin/balanceManager.jsp";
  				}else{
  					alert(res.message);
  				}
  			}
  		});
  	}
  </script>
</div>
</body>
</html>