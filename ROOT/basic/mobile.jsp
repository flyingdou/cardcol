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

<style>
   #remark-div{
	  margin-top: 10px;
   }

   #remark{
      font-size: 12px;
      color:red;
   }
   
   

</style>

<script language="javascript">
	$(document).ready(function() {
		$('#left-1 ul li a').css('cursor', 'pointer');
	});
	function onEdit() {
		var oldmobilephone = $('#mobilephone').val();
		$('#oldMobile').val(oldmobilephone);
		$('#userform').attr('action', 'mobile!mobile1.asp');
		$('#userform').attr('method', 'post');
		$('#userform').submit();
	}
	function getValidateForMobile(mobile) {
		$.ajax({
			url : 'mobile!getMobileValidCode.asp',
			type : 'post',
			data : 'mobile=' + mobile + '&id=' + $('#memberId').val(),
			success : function(msg) {
				if (msg == 'OK') {
					alert('当前验证码已经成功发送到您的手机上，请注意查收！');
					
				} else {
					alert('验证码发送失败！原因为：' + msg);
					return;
				}
			}
		});
	}
	function getValidCode(o) {
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
	
	function validateMobile() {
		var mobile = $('#mobilephone').val();
		var code = $('#validcode').val();
		if (code == '') {
			alert('请先输入您的验证码再进行验证！');
			return;
		}
		loadMask();
		$.ajax({
			url : 'mobile!saveMobileValid.asp',
			type : 'post',
			data : 'mobile=' + mobile + '&code=' + code,
			success : function(msg) {
				removeMask();
				if (msg == 'OK') {
					alert('手机号验证成功！');
					window.history.go(-1);
				} else {
					alert('您输入的验证码有误，请重新输入！');
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
				<h1>手机认证</h1>
				<s:form theme="simple" id="userform" name="userform" method="post">
					<s:hidden name="code" id="code" />
					<s:hidden name="member.id" id="memberId" />
					<s:hidden name="oldMobile" id="oldMobile" />
					<s:hidden name="member.mobilephone" id="dou-mobilephone" />
					<s:hidden name="member.mobileValid" id="dou-mobileValid" />
					<div>
						<div class="p_div">
							<p>
								手<span style="padding-left: 40px;">机:</span> 
								<s:if test="member.mobileValid == 1 ">
									<s:textfield value="" id="mobilephone" maxLength="11" disabled="true" />
									<s:hidden name="member.mobilephone" />
								</s:if>
								<s:else>
									<s:textfield name="member.mobilephone" cssClass="name-1"
										id="mobilephone" maxLength="11" style="width: 230px;line-height: 17px;height: 17px;border-color: #aaa;margin-left: 10px;padding-left: 1px;" />
								</s:else>
							</p>
							<%-- 
								<s:if test="member.mobileValid == 1 ">
									<a class="checkmobile-btn" onclick="onEdit()">修改</a>
								</s:if>
							 
								<s:else>
								    <s:if test="member.mobileValid == null ">
									       <input type="button" onclick="getValidCode(this)" value="获取验证码" />
								    </s:if>
								</s:else>
							--%>
							<s:if test="member.mobileValid != 1 " >
							   <input type="button" onclick="getValidCode(this)" value="获取验证码" />
							   <div class="tishi">为了交易安全，请正确填写你的手机并进行验证。</div>
							</s:if>
						</div>
						<s:if test="member.mobileValid != 1">
							<div class="p_div_son" style="margin-left: 0px;">
								<p>
									输入验证码:
									<s:textfield cssClass="name-espa" style="margin-left:12px;"
										id="validcode" />
									<a class="checkmobile-btn"
										onclick="javascript: validateMobile();">立即验证</a>
								</p>

							</div>
						</s:if>
						<s:if test="member.mobileValid == 1">
						<div id = "remark-div">
						   <span id="remark" >手机已绑定。如需修改手机请联系客服。</span>
						</div>
						</s:if>
					</div>
				</s:form>
			</div>
		</div>
	</div>
	<s:include value="/share/footer.jsp" />
	
	<script>
	    $(function(){
	    	var mobileValid = $("#dou-mobileValid").val();
	    	if (mobileValid == 1){
	    	var mobilephone = $("#dou-mobilephone").val();
	    	mobilephone = mobilephone.substring(0,3) + "*****" + mobilephone.substring(8,11);
	    	$("#mobilephone").val(mobilephone);
	    	}
	    })
	</script>
</body>
</html>
