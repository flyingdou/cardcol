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
<script type="text/javascript" src="script/area.js"></script>
<script type="text/javascript" src="script/jquery.choosearea.js"></script>
<script language="javascript">
	$(document)
			.ready(
					function() {
						$('#left-1 ul li a').css('cursor', 'pointer');
						$("div[id^=right-2-]").dialog({
							autoOpen : false,
							show : "blind",
							hide : "explode",
							modal : true,
							resizable : false,
							width : 480
						});
						$('.save-to').click(function() {
							var reg = new RegExp("^[0-9]*$");
							if (!reg.test($('#tell').val())) {
								alert('您输入的联系电话错误，请重新输入！');
								$('#tell').select();
								return;
							}
							var params = $('#userform').serialize();
							$.ajax({
								url : 'linkemp!save.asp',
								type : 'post',
								data : params,
								success : function(msg) {
									if (msg === 'ok') {
										alert('数据已经成功保存！');
									} else {
										alert(msg);
									}
								}
							});
						});
						var chooseAreaApp1 = new $.choosearea(
								{
									selectDomId : {
										province : "province",
										city : "city",
										county : "county"
									},
									initAreaNames : {
										province : '<s:property value="member.province" escape="false"/>',
										city : '<s:property value="member.city" escape="false"/>',
										county : '<s:property value="member.county" escape="false"/>'
									},
									data : data
								});
					});
	function onEdit() {
		var val = $('#mobilephone').val();
		$('#right-2-1').dialog('open');
		$('#oldmobilephone').html(val);
	}
	function getValidateForMobile(mobile) {
		$.ajax({
			url : 'linkemp!getMobileValidCode.asp',
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
	function getValidCode() {
		var mobile = $('#mobilephone').val();
		if (mobile == '') {
			alert('请先输入需要验证的手机号码！');
			$('#mobilephone').focus();
			return;
		}
		getValidateForMobile(mobile);
	}
	function getOldValidate(o) {
		var mobile = $('#oldmobilephone').html();
		getValidateForMobile(mobile);
		time(o);
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
	function getValidateForNew(o) {
		var mobile = $('#newmobilephone').val();
		getValidateForMobile(mobile);
		tome(o);
	}
	function validateMobile() {
		var mobile = $('#mobilephone').val();
		var code = $('#validcode').val();
		if (code == '') {
			alert('请先输入您的验证码再进行验证！');
			return;
		}
		loadMask();
		$.ajax({
			url : 'linkemp!saveMobileValid.asp',
			type : 'post',
			data : 'mobile=' + mobile + '&code=' + code,
			success : function(msg) {
				removeMask();
				if (msg == 'OK') {
					window.location.reload();
				} else {
					alert('您输入的验证码有误，请重新输入！');
				}
			}
		});
	}
	function submitMobile() {
		var mobile = $('#newmobilephone').val();
		var code = $('#newvalidate').val();
		loadMask();
		$.ajax({
			url : 'linkemp!saveMobileValid.asp',
			type : 'post',
			data : 'mobile=' + mobile + '&code=' + code,
			success : function(msg) {
				removeMask();
				if (msg == 'OK') {
					$('#mobilephone').val(mobile);
					$('#right-2-2').dialog('close');
					$('#right-2-3').dialog('open');
					$('.newmobile1').html(mobile);
				} else {
					alert('您输入的验证码有误，请重新输入！');
				}
			}
		});
	}
	function nextStep1() {
		loadMask();
		var code = $('#oldvalidate').val();
		var mobile = $('#oldmobilephone').html();
		$.ajax({
			url : 'linkemp!validMobile.asp',
			type : 'post',
			data : 'mobile=' + mobile + '&code=' + code,
			success : function(msg) {
				removeMask();
				if (msg == 'OK') {
					$('#right-2-1').dialog('close');
					$('#right-2-2').dialog('open');
				} else {
					alert('您输入的验证码有误，请重新输入！');
				}
			}
		});
	}
	function closeMobile() {
		$('#right-2-3').dialog('close');
	}
	function validateMail() {
		$('#oldemail').html($('#email').val());
		$('#right-2-5').dialog('open');
	}
	function emailValid() {
		loadMask();
		var email = $('#email').val();
		if (email == '')
			return;
		$.ajax({
			url : 'linkemp!validEmail.asp',
			type : 'post',
			data : 'email=' + email + '&id=' + $('#memberId').val(),
			success : function(msg) {
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
		$.ajax({
			url : 'linkemp!modifyEmail.asp',
			type : 'post',
			data : parms,
			success : function(msg) {
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
	<s:include value="/share/header.jsp" />
	<div id="content">
		<s:include value="/basic/nav.jsp" />
		<div id="right-2">
			<div id="container">
				<h1>联系方式</h1>
				<s:form theme="simple" id="userform" name="userform" method="post">
					<s:hidden name="code" id="code" />
					<s:hidden name="member.id" id="memberId" />
					<div>
						<div class="p_div">
							所在地区: <select name="member.province" id="province"></select> <select
								name="member.city" id="city"></select> <select
								name="member.county" id="county"></select>
						</div>
						<div class="p_div">
							<p>
								地&nbsp;&nbsp;址:
								<s:textfield name="member.address" cssClass="name-1"
									id="address" maxlength="200" />
							</p>
						</div>
						<div class="p_div">
							<p>
								联系电话:
								<s:textfield name="member.tell" cssClass="name-1" id="tell"
									maxLength="100" />
							</p>
						</div>
<!-- 						<div class="p_div"> -->
<!-- 							<p> -->
<!-- 								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;QQ: -->
<%-- 								<s:textfield name="member.qq" cssClass="name-1" id="qq" --%>
<%-- 									maxLength="30" /> --%>
<!-- 							</p> -->
<!-- 						</div> -->
					</div>
				</s:form>
			</div>
			<input type="button" name="botton" value="保存" class="save-to" />
		</div>
	</div>
	<s:include value="/share/footer.jsp" />
	<div id="right-2-1" title="手机验证">
		<div id="container">
			<div class="steps">
				<ul>
					<li class="current"><span>1.验证原手机</span></li>
					<li><span>2.输入新的手机号码</span></li>
					<li><span>3.修改成功</span></li>
				</ul>
			</div>
			<div class="change-mail">
				<p class="mail-warning">修改手机前须先验证原手机的信息。</p>
				<p class="Original-email">
					原手机号：<span id="oldmobilephone" phoneno="">136*****642</span>
				</p>
				<p class="Original-email">
					&nbsp;&nbsp;&nbsp;验证码：<input type="text" id="oldvalidate"
						class="yanzhengma"> <input type="button"
						onclick="getOldValidate(this)" value="获取验证码">
				</p>
				<p class="nextp">
					<a class="nextstep" onclick="nextStep1()">下一步</a>
				</p>
			</div>
			<div class="bottom-mail">
				<span class="">常见问题</span><br> <span class="detail">①若您1分钟之内没有收到短信，建议您重发验证码</span><br>
						<span class="detail">②手机卡号遗失，无法使用手机接受短信验证码-请致电客服热线027-87573247</span><br>
			</div>
		</div>
	</div>
	<div id="right-2-2" title="手机验证">
		<div id="container">
			<div class="steps">
				<ul>
					<li><span>1.验证原手机</span></li>
					<li class="current"><span>2.输入新的手机号码</span></li>
					<li><span>3.修改成功</span></li>
				</ul>
			</div>
			<div class="change-mail">
				<p>
					请输入新的手机号码：<input type="text" id="newmobilephone">
				</p>
				<p class="Original-email">
					验&nbsp;证&nbsp;码：<input class="yanzhengma" type="text"
						id="newvalidate"> <input type="button"
						onclick="getValidateForNew(this)" value="获取验证码">
				</p>
				<p class="nextp">
					<a class="nextstep" onclick="submitMobile()">提&nbsp;&nbsp;&nbsp;交</a>
				</p>
			</div>
			<div class="bottom-mail">
				<span class="">提示：</span><br> <span class="detail">①若您1分钟之内没有收到短信，建议您重发验证码</span><br>
						<span class="detail">②绑定成功后，可享用重要事件提醒及账号保护</span><br>
			</div>
		</div>
	</div>
	<div id="right-2-3" title="手机验证">
		<div id="container">
			<div class="steps">
				<ul>
					<li><span>1.验证原手机</span></li>
					<li><span>2.输入新的手机号码</span></li>
					<li class="current"><span>3.修改成功</span></li>
				</ul>
			</div>
			<div class="change-mail">
				<p class="mail-warning">您已经完成了手机修改绑定！</p>
				<p class="Original-email">
					您绑定的手机号是：<span class="newmobile1">136*****642</span>
				</p>
				<p class="nextp">
					<a class="nextstep" onclick="closeMobile()">完&nbsp;&nbsp;&nbsp;成</a>
				</p>
			</div>
			<div class="bottom-mail">
				<span class="">手机绑定成功后，您可以享有以下服务：</span><br> <span
					class="detail">①重要事件提醒：进行（支付/提现/结算/训练周期/投诉/课程安排）时，可及时收到短信提醒</span><br>
						<span class="detail">②账号保护： 在您进行登录及修改密码等敏感操作时，未经您授权的操作将不被允许</span><br>
			</div>
		</div>
	</div>
	<div id="right-2-5" title="修改邮箱">
		<div id="container">
			<div class="change-mail">
				<p class="mail-warning">请输入新的邮箱地址</p>
				<p class="Original-email">
					新邮箱地址：<input id="newemail">
				</p>
				<p class="nextp">
					<a class="nextstep nextsteppyj"
						onclick="javascript: onModifyEmail()">发验证邮件</a>
				</p>
			</div>
			<div class="bottom-mail">
				<span class="">邮箱绑定成功后，您可以享有以下服务：</span><br> <span
					class="detail">①邮件到达需要2-3分钟，若您长时间未收到邮件，建议您检查邮件中的垃圾邮件或者<a
						class="cfyzm">重发验证信</a></span><br> <span class="detail">②如果您的原邮箱已经无法使用，可以通过账号申诉进行邮箱更换。</span><br>
			</div>
		</div>
	</div>
</body>
</html>
