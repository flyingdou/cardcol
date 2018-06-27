<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<s:include value="/share/meta.jsp"/>
<meta name="keywords" content="健身E卡通_注册账号" /> 
<meta name="description" content="健身E卡通_注册账号"/>  
<title>健身E卡通_注册账号</title>

<link type="text/css" rel="stylesheet" href="css/login.css" />
<link type="text/css" rel="stylesheet" href="css/validator.css" />
<script type="text/javascript" src="script/formValidator-4.0.1.min.js"></script>
<script type="text/javascript" src="script/formValidatorRegex.js"></script>
<script type="text/javascript" src="script/DateTimeMask.js" language="javascript" ></script>
<script language="javascript">
$(document).ready(function(){
	$("#birthday").datepicker({dateFormat: 'yy-mm-dd',changeYear: true,dayNamesMin: ['日','一','二','三','四','五','六']});
	$.formValidator.initConfig({formID:"regForm",alertMessage:true,onError:function(msg){alert(msg)}});
	$(":radio[name='user.role']").formValidator({defaultValue:["S"]}).inputValidator({min:1,max:1,onError:"用户类型忘记选了,请确认"});
	$("#name").formValidator().inputValidator({min:1,max:20,onError:"用户名称不能为空,请确认"});
	$("#password").formValidator().inputValidator({min:1,max:20,onError:"密码不能为空,请确认"});
	$("#password1").formValidator().inputValidator({min:1,max:20,onError:"重复密码不能为空,请确认"}).compareValidator({desID:"password",operateor:"=",onError:"2次密码不一致,请确认"});
	$("#birthday").DateTimeMask().formValidator().inputValidator({min:"1900-01-01",max:"2000-01-01",type:"value",onError:"日期必须在\"1900-01-01\"和\"2000-01-01\"之间"});
	$("#email").formValidator({defaultValue:"@"}).inputValidator({min:6,max:100,onError:"你输入的邮箱长度非法,请确认"}).regexValidator({regExp:"^([\\w-.]+)@(([[0-9]{1,3}.[0-9]{1,3}.[0-9]{1,3}.)|(([\\w-]+.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(]?)$",onError:"你输入的邮箱格式不正确"});
});
</script>
</head>
<body>
	<div class="all">
		<div id="top">
			<div id="top-content">
				    <p class="left"> 欢迎来到健身E卡通！</p>
					<p class="right"><a href="index.asp" id="coloa">网站导航</a></p>
			</div>
		</div>
		<div id="header">
			<div id="logo">
				<blockquote>
					<p><a href="index.html"><img src="images/logo.png" alt="健身E卡通LOGER" /></a> </p>
				</blockquote>
		</div>
		<div id="ad">
			<img src="images/ad.png" />
		</div>
        </div>
		<div id="user">
			<s:form name="regForm" id="regForm" method="post" theme="simple" action="register!save.asp">
			<div id="user-1">用户注册</div>
			<div id="user-left0">
			<!--
				<div class="left1">
					用户类型：<span class="left1-1"><s:radio name="user.role" id="role" list="#{'S':'健身会员','C':'健身教练','E':'健身俱乐部'}"/></span>
				</div>
			-->
				<p>
					<span>用&nbsp;户&nbsp;名：</span> <input type="text" name="user.name" id="name"/>
				</p>
				<p>
					<span>密&nbsp;&nbsp;&nbsp;&nbsp;码：</span> <input type="password" id="password" name="user.password" />
				</p>
				<p>
					<span>确认密码：</span> <input type="password" name="password1" class="list-2" id="password1"/>
				</p>
				<!--
				<p>
					<span>出生日期：</span> <input type="text" name="user.birthday" class="list-5" id="birthday"/> 
					</br></br><span class="spanp">为了健身计划的科学性，请准确出入你的出生日期。格式：1982-02-11</span>
				</p>
				-->
				<p>
					<span>电子邮箱：</span> <input type="text" name="user.email" class="list-2" id="email"/>
				</p>
				<div class="left2">
					<input name="checkbox" type="checkbox" class="pact-1" value="checkbox" checked="checked" /> 我看过并同意服务协议
				</div>
				<div>
					<input type="submit" name="Submit" value="" class="left3"/>
				</div>
			</div>
			</s:form>
			<div id="user-right0">
			    <div class="right1"><b>你已经是会员了，请登录</b></div>
				<div class="right2-1"> 
				  <input type="button" name="Submit" value="" class="right2"/>
				</div>
				<div class="updiv">
				     <div class="updiv1">使用合作网站账号登录：</div>
					 <div class="updiv2">
					    <div class="updiv2-1">
						    <div class="updiv2-1-1"><img src="images/xinla.jpg"/></div>
							<div class="updiv2-1-2"><a href="#" id="coloa">新浪微博</a></div></div>
						</div> 
				</div>
			</div>
		</div>
		<s:include value="/share/footer.jsp" />
		<div id="bottom">
		     <P><span>法律声明</span><span>服务条款</span><span>隐私声明</span><span>著作权与商标声明</span><span>诚信安全</span></P>
			 <p>◎ 2010 武汉好韵莱信息技术有限公司 All Rights Reserved.  鄂ICP备08004430号</p>
		</div>
	</div>
</body>
</html>
