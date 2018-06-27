<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta name="keywords" content="健身E卡通,忘记密码,用户登录" />
<meta name="description" content="健身E卡通_忘记密码" />
<link rel="stylesheet" type="text/css" href="css/find.css" />
<s:include value="/share/meta.jsp" />
<title>健身E卡通—忘记密码</title>
<script type="text/javascript">
function savePassword(){
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
	if($('#password1').val() == $('#password2').val()){
		$.ajax({
			url : 'findpassword!reset.asp',
			type : 'post',
			data : 'mobile=' + $('#mobile').val() + '&email=' + $('#email').val()+ '&password=' + $('#password1').val(),
			success : function(msg) {
				if (msg == 'OK') {
					$('#form1').attr('action','findpassword!findSuccess.asp');
					$('#form1').submit();
				} else {
					$('#form1').attr('action','findpassword!findFail.asp');
					$('#form1').submit();
				}
			}
		});
	}else{
		alert("两次输入密码不一致，请重新输入！");
	}
}
</script>
</head>
<body>
<s:include value="/share/home-header_1.jsp" />
   <div class="findmima">
     <ul id="num4">
      <li class="first done">
	     <span><i>1</i><b>选择验证方式</b></span>
	  </li>
	   <li class="done">
	     <span><i>2</i><b>验证身份</b></span>
	  </li>
	   <li class="current">
	     <span><i>3</i><b>重置密码</b></span>
	  </li>
	   <li class="last">
	     <span><i>4</i><b>完成</b></span>
	  </li>
	 </ul>
  </div>
  <div class="navcontent">
        <form id="form1" action="" method="post">
        <s:hidden name="mobile" id="mobile"></s:hidden>
        <s:hidden name="email" id="email"></s:hidden>
		     <div class="cleft2">
			     <div class="check_box">
				     <p><span class="xin">新密码</span><input type="password" class="swp" id="password1" tag="新密码"/></p>
					 <p><span>再次输入</span><input type="password"  class="reswp" id="password2" tag="再次输入"/></p>
	                 <input type="button" value="提交" id="xiayibu" onclick="javascript:savePassword();">
				 </div>
              </div>
			
	   </form>
  
  </div>
  <s:include value="/share/footer.jsp" />
</body>
</html>
