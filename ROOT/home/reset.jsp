<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta name="keywords" content="健身E卡通,忘记密码,用户登录" />
<meta name="description" content="健身E卡通_忘记密码" />
<link rel="stylesheet" type="text/css" href="css/lostpassword.css" />
<s:include value="/share/meta.jsp" />
<title>健身E卡通_忘记密码</title>
<script type="text/javascript">
function resetPass() {
	var valid = true;
	$("input").each(function(){
		if($(this).val() == '' && valid === true && $(this).attr('tag')) {
			alert($(this).attr('tag') + '不能为空，请重新输入！');
			$(this).focus();
			valid = false;
		}
	});
	if (!valid) return;
	if ($('#p1').val() != $('#p2').val()) {
		alert('两次密码不一致，请重新输入！');
		$('#p1').focus();
		return;
	}
	$('#p1').val(md5($('#p1').val()));
	var parms = $('#form1').serialize();
	$.ajax({url: 'reset!save.asp', type: 'post', data: parms,
		success: function(msg){
			var _json = $.parseJSON(msg);
			if (_json.success === true) {
				alert('您的密码已经成功重置，请使用新密码登录！');
			} else {
				alert('您的密码，可能的原因为：' + _json.message);
			}
		}
	});
}
</script>
</head>
<body>
	<s:include value="/share/home-header_1.jsp"/>
	<div id="divcontent">
		<div id="cont">
			<h2>
				<b>重置密码</b>
			</h2>
			<div class="cleft">
				<!--<div class="cleft1">
					<p>
						<span> 1. 填写绑定邮箱 </span> <b>＞＞</b> 2. 接收找回密码邮件 <b>＞＞</b> 3. 重置密码
					</p>
				</div>-->
				<s:form name="form1" id="form1" action="" theme="simple">
					<s:hidden name="id" />
					<s:hidden name="email"/>
					<s:hidden name="verify" />
					<div class="cleft2">
						<!--
							<p>
								<div class="pdiv">用户昵称：</div>
								<div class="inpt"><input type="text" name="username" id="username" value="" tag="用户名"/></div>
							</p>
						-->
						<p>
							<div class="pdiv">您的新密码：</div>
							<div class="inpt"><input type="password" name="password" id="p1" tag="您的新密码" /></div>
						</p>
						<p>
							<div class="pdiv">确认新密码：</div>
							<div class="inpt"><input type="password" id="p2" tag="确认新密码"/></div>
						</p>
						<div class="inzp">
							<a href="javascript:resetPass();"> <img src="images/wc.jpg" /></a>
						</div>
					</div>
				</s:form>

			</div>
			<div class="cright">
				<div class="cright1">
					算了返回 <a href="index.asp">首页</a>
				</div>
				<div class="cright2">
					<p>
						<b>获取密码</b>
					</p>
					<p class="p1">填写您的用户名和当时注册时填写的email。我们会发邮件给您，邮件中会有你的密码</p>
				</div>
				<div class="cright3">
					<p>
						<b>想起密码</b>
					</p>
					<p class="p1">
						如果你已经记起密码，请点击此处 <a href="login.asp">登录</a>
					</p>
				</div>
				<div class="cright4">
					<p>
						<b>如果有任何疑问</b>
					</p>
					<p class="p2">
						请访问 <a href="service.asp">服务中心</a>
					</p>
					<p class="p3">
						客服邮箱：<span>952078381@qq.com</span>
					</p>
					<p class="p4">
						客服热线：<span>027-87573247</span>
					</p>
					<p class="p4">（仅收市话费，客服工作时间：7×24小时）</p>
				</div>
				<div></div>
			</div>
		</div>
	</div>
	<s:include value="/share/footer.jsp" />
</body>
</html>
