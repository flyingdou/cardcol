<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<s:include value="/share/meta.jsp" />
<meta name="keywords" content="选择角色" />
<meta name="description" content="选择角色页面" />
<link type="text/css" rel="stylesheet" href="css/validator.css" />
<script type="text/javascript" src="script/FormValidator/formValidator-4.1.1.js"></script>
<script type="text/javascript" src="script/FormValidator/formValidatorRegex.js"></script>
<script type="text/javascript" src="script/area.js"></script>
<script type="text/javascript" src="script/jquery.choosearea.js"></script>
<title>健身E卡通_帐户绑定</title>
<link href="css/index.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="css/register-2.css" />
<script type="text/javascript">
$(document).ready(function(){
	$('#birthday').datepicker({autoSize: true,showMonthAfterYear:true,changeMonth:true,changeYear:true});
	$('#divlog').hide();
	$.formValidator.initConfig({formID:"regform",theme:"ArrowSolidBox",submitOnce:true,mode:"AutoTip",onError:function(msg){},onSuccess:function(){
		var checked = $('#chkxy').attr('checked');
		if (!checked || checked != 'checked') {
			alert('如果您要注册成为本站用户，必须同意本站服务协议！');
			return false;
		};
		var role = $('input[name="member.role"]:checked').val();
		if (role != 'E') {
			var birthday = $('#birthday').val();
			if (birthday == '') {
				alert('您的出生日期必须输入！');
				return false;
			}
			var test = /^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2})/;
			if (!test.test(birthday)){ 
				alert("您输入的出生日期格式不正确，请重新输入!");
				return false;
			}
		}
		if ($('#province').val() == '' || $('#city').val() == '' || $('#county').val() == '') {
			alert('您所在的地区必须输入！');
			return false;
		}
		return true;
	}});
	$("#nick").formValidator({onShowText:"请输入用户名",onFocus:"用户名至少4个字符,最多20个字符"}).inputValidator({min:4,max:20,onError:"用户名不能为空,并且最少5个，最大20个长度。请确认"});
	$("#password").formValidator({onShowText:"请输入您登录本站的密码！"}).inputValidator({min:6,max:20,onError:"密码不能为空且您的密码最少6个字符，最大20个字符,请确认"});
	$("#password1").formValidator({onShowText:"请输入您登录本站的密码！"}).inputValidator({min:6,max:20,onError:"重复密码不能为空且您的密码最少6个字符，最大20个字符,请确认"}).compareValidator({desID:"password",operateor:"=",onError:"2次密码不一致,请确认"});
	$("#email").formValidator({defaultValue:"@"}).inputValidator({min:1,max:100,onError:"你输入的邮箱长度非法,请确认"}).regexValidator({regExp:"^([\\w-.]+)@(([[0-9]{1,3}.[0-9]{1,3}.[0-9]{1,3}.)|(([\\w-]+.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(]?)$",onError:"你输入的邮箱格式不正确"})
		.ajaxValidator({dataType: 'html', async: false, url: 'register!checkMail.asp', onWait : "正在对电子邮箱进行合法性校验，请稍候...",
			onError : "该电子邮箱已经被注册过，请重新输入",
			success: function(data) {
				if (data == 'OK') {
					return true;
				}
				return false;
			}
		}).defaultPassed();
	$('#selected').click(function(){
		var type = $('#type').val();
		var url = type == 'qq' ? 'qqlogin' : 'sina';
		$('#regform').attr('action', url + '!selected.asp');
		$('#regform').submit();
	});
	$('#selected1').click(function() {
		if ($('#email1').val() == '') {
			alert('请输入您的电子邮箱地址后在登录!');
			return;
		}
		if ($('#password1').val() == '') {
			alert('请输入您的密码后在登录!');
			return;
		}
		if ($('#verifyCode').val() == '') {
			alert('请输入验证码后在登录!');
			return;
		}
		var type = $('#type').val();
		var url = type=='sina' ? 'sina' : 'qqlogin';
		var parms = $('#logform').serialize() + '&openId=' + $('#openId').val();
		$.ajax({url: url + '!selected1.asp', type: 'post', data: parms, 
			success: function(resp) {
				if (resp == 'ok') {
					window.location.href = "login.asp";
				} else if (resp == 'noexist') {
					alert('您输入的账户不存在!');
				} else if (resp == 'verifyCodeError') {
					alert('您输入的验证码有误！');
				}
			}
		})
	});
	var chooseAreaApp1 = new $.choosearea({
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
function showDiv(obj, id) {
	$('div[id$=Tip]').hide();
	$('div[id^=div]').hide();
	$('#div' + id).show();
	$('span[class^=tit-tab-]').attr('class', 'tit-tab-unAct');
	$(obj).parent('span').attr('class', 'tit-tab-act');
}
</script>
</head>
<body>
	<s:include value="/share/home-header.jsp" />
	<div class="reg_box">
		<div class="tit-tab">
            <span class="tit-tab-act"><a href="javascript:void(0)" onclick="javascript:showDiv(this, 'reg')">完善个人资料</a></span>
            <span class="tit-tab-unAct"><a href="javascript:void(0);" onclick="javascript:showDiv(this, 'log')">已有帐号？绑定我的帐号</a></span>
        </div>
		<p class="tit-tips">
			<b class="orange1">提示：</b><span class="fc6">您正在绑定已有健身E卡通帐号，绑定完成后两帐号均可登录卡库网！</span>
		</p>
		<div id="divreg">
			<s:form id="regform" name="regform" theme="simple">
			<s:hidden name="openId" id="openId"/> 
			<input type="hidden" name="type" id="type" value="<s:property value="#request.type"/>"/>
			<div class="reg_form">
				<div class="clearfix">
					<div class="left_name">用户类型：</div><span class="left1-1"><s:radio name="member.role" id="role" list="#{'M':'健身会员','S':'健身教练','E':'健身俱乐部'}" value="'M'" /></span>
				</div>
				<div class="clearfix">
					<div class="left_name"><span>用&nbsp;户&nbsp;名：</span></div><s:textfield value="" name="member.nick" cssClass="at_text" id="nick"/>
				</div>
				<div class="clearfix">
					<div class="left_name"><span>邮箱地址：</span></div><s:textfield value="" name="member.email" cssClass="at_text" id="email"/>
				</div>
				<div class="clearfix">
					<div class="left_name"><span>设置密码：</span></div><s:password value="" name="member.password" cssClass="at_text" id="password"/>
				</div>
				<div class="clearfix">
					<div class="left_name"><span>确认密码：</span></div><s:password value="" name="password" cssClass="at_text" id="password1"/>
				</div>
				<div class="clearfix">
					<div class="left_name"><span>出生日期：</span></div><input type="text" name="member.birthday" class="input_public" id="birthday"/>
					 <br />
					<!--<span class="spanp">为了健身计划的科学性，请准确出入你的出生日期。格式：1982-02-11</span>-->
				</div>
				<div class="clearfix">
				<div class="left_name">所在地区： </div>
					<select name="member.province" id="province" ></select>
					<select name="member.city" id="city" ></select>
					<select name="member.county" id="county" ></select>
				</div>
				<div class="clearfix">
					<input type="checkbox" checked="chcked" class="auto_login" value="" id="chkxy">
					<span class="accept_cardcol">我接受<a>健身E卡通用户服务协议</a></span>
				</div>
				<div class="clearfix">
					<input id="selected" type="button" class="choose_over" value="完成，继续浏览">
				</div>
			</div>
			</s:form>
		</div>
		<div id="divlog">
			<s:form id="logform" name="logform" theme="simple">
			<div class="reg_form">
				<div class="clearfix">
					<div class="left_name"><span>用&nbsp;户&nbsp;名：</span></div><s:textfield value="" name="member.email" cssClass="input_public" id="email1"/>
				</div>
				<div class="clearfix">
					<div class="left_name"><span>登录密码：</span></div><s:password value="" name="member.password" cssClass="input_public" id="password1"/>
				</div>
				<div class="clearfix">
					<div class="left_name"><span>验 证 码：</span></div><s:textfield value="" name="verifyCode" cssClass="input_public" id="verifyCode"/><img src="verifycode.asp"></img>
				</div>
				<div class="clearfix">
					<input id="selected1" type="button" class="choose_over" value="完成，继续浏览">
				</div>
			</div>
			</s:form>
		</div>
	</div>
	<s:include value="/share/footer.jsp" />
</body>
</html>