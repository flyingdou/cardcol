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
<script type="text/javascript" src="script/uploadPreview.js"></script>
<script language="javascript">
$(document).ready(function() {
	new uploadPreview({
		UpBtn : "file",
		ImgShow : "preview"
	});
	$('#left-1 ul li a').css('cursor', 'pointer');
	$("div[id^=right-2-]").dialog({autoOpen: false, show: "blind", hide: "explode", modal: true, resizable: false, width: 480});
	$('.save-to').click(function(){
		$('#userform').attr('action', 'card!save.asp');
		$('#userform').submit();
	});
});
function onEdit() {
	var val = $('#mobilephone').val();
	$('#right-2-1').dialog('open');
	$('#oldmobilephone').html(val);
}
function getValidateForMobile(mobile){
	$.ajax({url:'link!getMobileValidCode.asp',type:'post',data: 'mobile=' + mobile + '&id=' + $('#memberId').val(),
		success:function(msg){
			if (msg == 'OK') {
				alert('当前验证码已经成功发送到您的手机上，请注意查收！');
			} else {
				alert('验证码发送失败！原因为：' + msg);
			}
		}
	});
}
function getValidCode(o){
	var mobile = $('#mobilephone').val();
	if (mobile == '') {
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
function getValidateForNew(o){
	var mobile = $('#newmobilephone').val();
	getValidateForMobile(mobile);
	time(o);
}
function validateMobile(){
	var mobile = $('#mobilephone').val();
	var code = $('#validcode').val();
	if (code == '') {
		alert('请先输入您的验证码再进行验证！');
		return;
	}
	loadMask();
	$.ajax({url: 'link!saveMobileValid.asp', type: 'post', data: 'mobile=' + mobile + '&code=' + code,
		success: function(msg) {
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
	$.ajax({url: 'link!saveMobileValid.asp', type: 'post', data: 'mobile=' + mobile + '&code=' + code,
		success: function(msg) {
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
function nextStep1(){
	loadMask();
	var code = $('#oldvalidate').val();
	var mobile = $('#oldmobilephone').html();
	$.ajax({url: 'link!validMobile.asp', type: 'post', data: 'mobile=' + mobile + '&code=' + code,
		success: function(msg) {
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
</script>
<style type="text/css">
.yz_input input{ border:1px solid #cccccc; width:240px; height:24px; line-height:24px; margin-left:5px;}

</style>
</head>
<body>
	<s:include value="/share/header.jsp" />
	<div id="content">
		<s:include value="/basic/nav.jsp" />
		<div id="right-2">
			<s:form name="userform" theme="simple" method="post"
				enctype="multipart/form-data" action="card!save.asp">
				<div id="container">
					<h1>身份证认证</h1>
					<div>
						<div class="p_div" style="">    <!-- 去掉此处的padding-left: 47px; -->
							<p>
								账&nbsp;&nbsp;&nbsp;户：
								<s:textfield name="member.nick" id="nick" cssClass="name-1"
									readonly="true" style=" float:none" />
							</p>
						</div>
						<div class="p_div">
							<p style="float: none">
								手 机号 码：
								<s:if test="member.mobileValid == '1'">
									<s:textfield name="member.mobilephone" cssClass="name-1"
										id="mobilephone" maxLength="11" disabled="true" />
									<s:hidden name="member.mobilephone" />
								</s:if>
								<s:else>
									<s:textfield name="member.mobilephone" cssClass="name-1"
										id="mobilephone" maxLength="11" />
								</s:else>
							</p>
							<s:if test="member.mobileValid == '1'">
								<a class="checkmobile-btn" onclick="onEdit()">修改</a>
							</s:if>
							<s:else>
								<input type="button" onclick="getValidCode(this)" value="获取验证码">
							</s:else>
							<div class="tishi">为了交易安全，请正确填写你的手机并进行验证。</div>
						</div>
						<s:if test="member.mobileValid != '1'">
							<div class="p_div_son">
								<span style="color:#000;" class="yz_input">
									输入验证码：<s:textfield cssClass="name-espa" id="validcode" /><a class="checkmobile-btn"
									onclick="javascript: validateMobile();" >&nbsp;立即验证</a>
								</span>
								
							</div>
						</s:if>
						</p>
					</div>
					<div class="p_div" style="padding-left: 40px;">
						身份证照片：<s:file name="file.file" accept="image/*" style="width: 300px;" id="file"/>
					</div>
					<div class="p_div">
						<p>
							<div class="tishi" style="float: left;">图片需清晰可见且正反面都有，大小不超过10M的jpg/gif/png格式图片</div>
						</p>
					</div>
					<div class="p_div_son">
						<img id="preview" onclick="javascript: $('#file').click();"  style="margin-left: 20px;"
							<s:if test="member.cardImage==null || member.cardImage == ''">width="-1" height="-1" style="diplay: none"</s:if>
							<s:else>width="120px" height="100px" src="picture/<s:property value="member.cardImage"/>"</s:else> />
					</div>
				</div>
				<input type="submit" name="botton" value="保存" class="save-to" />
			</s:form>
		</div>
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
						id="newvalidate"><input type="button"
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
</body>
</html>
