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
	// function findPass() {
	// 	var valid = true;
	// 	$("input").each(function(){
	// 		if($(this).val() == '' && valid === true && $(this).attr('tag')) {
	// 			alert($(this).attr('tag') + '不能为空，请重新输入！');
	// 			$(this).focus();
	// 			valid = false;
	// 		}
	// 	});
	// 	if (!valid) return;
	// 	var code = $('#validatecode').val();
	// 	var mobile = $('#mobilephone').val();
	// 	$.ajax({url: 'findpassword!validMobile.asp', type: 'post', data: 'mobile=' + mobile + '&code=' + code,
	// 		success: function(msg) {
	// 			removeMask();
	// 			if (msg == 'OK') {
	// 				var parms = $('#form1').serialize();
	// 				$.ajax({url: 'findpassword!send.asp', type: 'post', data: parms,
	// 					success: function(msg){
	// 						if (msg == 'OK') {
	// 							alert('您的重置密码已经成功发送到您的邮箱中，请查看！');
	// 						} else {
	// 							alert('邮件发送错误，可能的原因为：' + msg);
	// 						}
	// 					}
	// 				});
	// 			} else {
	// 				alert('您输入的验证码有误，请重新输入！');
	// 			}
	// 		}
	// 	});
	// }
	// function getValidateForMobile(mobile){
	// 	$.ajax({url:'findpassword!getMobileValidCode.asp',type:'post',data: 'mobile=' + mobile ,
	// 		success:function(msg){
	// 			if (msg == 'OK') {
	// 				alert('当前验证码已经成功发送到您的手机上，请注意查收！');
	// 			} else {
	// 				alert('验证码发送失败！原因为：' + msg);
	// 			}
	// 		}
	// 	});
	// }
	// function getValidate() {
	// 	var mobile = $('#mobilephone').val();
	// 	if(mobile!= null && mobile!=""){
	// 		getValidateForMobile(mobile);
	// 	}else{
	// 		alert("请输入手机号！");
	// 	}
	// }

	function validate() {
		var type = $('#J_type_select').val();
		if (type == '1') {
			window.location.href = "findpassword!emailValidate.asp";
		} else {
			window.location.href = "findpassword!mobileValidate.asp";
		}
	}
</script>
</head>
<body>
	<s:include value="/share/home-header_1.jsp" />
	<div class="findmima">
		<ul id="num4">
			<li class="first current"><span><i>1</i><b>选择验证方式</b></span></li>
			<li><span><i>2</i><b>验证身份</b></span></li>
			<li><span><i>3</i><b>重置密码</b></span></li>
			<li class="last"><span><i>4</i><b>完成</b></span></li>
		</ul>
	</div>
	<div class="navcontent">
		<form id="form1" action="">
			<div class="cleft2">
				<div class="check_type">
					<label for="J_type">请选择验证方式</label> <select id="J_type_select">
						<option value="1">邮箱</option>
						<option value="2">手机</option>
					</select>
	<br>
	<br>
	 <input type="button" value="下一步" id="xiayibu" onclick="javascript:validate();">
				</div>

			</div>
		</form>
	</div>
	<s:include value="/share/footer.jsp" />
</body>
</html>
