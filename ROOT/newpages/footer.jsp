<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>   
<style type="text/css">
.clearfix a:link{
color:black;
}
.col-md-2 a:link{
color:black;
}
.clearfix a:visited{
color:black;
}
.col-md-2 a:visited{
color:black;
}


</style>
	<footer>
	<div class="container">
		<div class="footer_txt">
			<ul class="clearfix">
				<li><a href="law.asp">法律申明</a></li>
				<li><a href="server.asp">服务条款</a></li>
				<li><a href="declare.asp">隐私声明</a></li>
				<li><a href="copy.asp">著作权与商标声明</a></li>
				<li class="last_li"><a href="integrity.asp">诚信与安全</a></li>
			</ul>
			<div>
				<p style="text-align: center;margin-bottom: 20px;">
					<a href='javascript:void(0)'>友情链接 : </a>
				</p>
			</div>
			<div class="txt_two row">
				<div class="col-md-2">
					<p>
						<a href='http://221.232.141.246/iciaicweb/dzbscheck.do?method=change&id=E2015042000071529'>工商局网络<br>商品交易监管</a>
					</p>
				</div>
				<div class="col-md-2">
					<p>
						<a href="http://wuhan.cyberpolice.cn/ga/">公安局网络备案<br>420100003762</a>
					</p>
				</div>
				<div class="col-md-2">
					<p>
						<a href="http://www.pbccrc.org.cn/index.html">人民银行信用代码<br>G1042011101683760F</a>
					</p>
				</div>
				<div class="col-md-2">
					<p>
						<a href="http://www.cardcol.com/detail.asp?channel=5&id=39">银行交易资金<br>第三方监管</a>
					</p>
				</div>
				<div class="col-md-2">
					<p>
						<a href="http://cn.unionpay.com/">中国银联<br>在线支付特约商户</a>
					</p>
				</div>
				<div class="col-md-2">
					<p>
						<a href="https://www.alipay.com/">支付宝<br>签约商户</a>
					</p>
				</div>
			</div>
			<p class="bot_p">◎ 2010 健易通信息科技（北京）有限公司 All Rights Reserved.
				<a href="http://www.miibeian.gov.cn">鄂ICP备08004430号</a></p>
		</div>
	</div>
	<div class="container">
		<div class="share">
			<div class="share_pic">
				<img src="images/icon_2_08.png">
			</div>
			<p>13908653155</p>
			<ul>
				<li><img src="images/wb_134.png"></li>
				<li><img src="images/wx_136.png"></li>
				<li><img src="images/qq_131.png"></li>
			</ul>
			<!-- <a href="#top" id="return_top"><img src="images/zd_128.png"> -->
			</a>
		</div>
	</div>
	
</footer>
<script type="text/javascript">
$.ajaxSetup({ 
    error:function(xhr,status){ 
 	   if(xhr.status == "1002"){
 		   removeMask();
 		   $(".ui-dialog-content").each(function(index){
 			   if($(this).attr("id") != "loginDialog"){
 				  $('#'+$(this).attr("id")).dialog('close');
 			   }
 		   });
 		   openLogin();
 		   return;
 	   }
 	}
});
$.fx.speeds._default = 1000;
$(function() {
	$('#loginDialog').dialog({autoOpen: false, show: "blind", hide: "explode",modal: true, resizable: false});
});
function openLogin(){
	$("input[id='username']").val("");
	$("input[id='password']").val("");
	$('#loginDialog').dialog('open');
}
function closeLogin(){
	$('#loginDialog').dialog('close');
}
function loginss(){
	if(!$("input[id='username']").val()){
		alert("会员账号不能为空，请确认！");
		$("input[id='username']").select();
		$("input[id='username']").focus();
		return;
	}
	if(!$("input[id='password']").val()){
		alert("会员密码不能为空，请确认！");
		$("input[id='password']").select();
		$("input[id='password']").focus();
		return;
	}
	var parms = 'username=' + $('#username').val() + '&password=' + md5($('#password').val());
	$.ajax({type:'post',url: 'login!check.asp',data: parms, 
		success: function(msg){
			if(msg == "success"){
				closeLogin();
// 				window.location.href=window.location.href;
				window.location.reload(true);
			}else if(msg == "errorUserPassword") {
				alert("会员帐号或会员密码错误！");
				$("input[name='username']").select();
				$("input[name='username']").focus();
			}else{
				alert(msg);
			}
		}
	});
}
</script>
<div id="loginDialog" title="会员登录" style="display:none;">
<form id="loginForm" name="loginForm" method="post" action="login!check.asp">
	<p>
		会员帐号：<input type="text" id="username" name="username" class="uidpwd" style="margin-top:10px;" />
	</p>
	<p>
		会员密码：<input type="password" id="password" name="password" class="uidpwd" /><a id="coloa" href="findpassword.asp" style="margin-left:5px;">忘记密码</a>
	</p>
	<p class="pa">
	   <input style="" type="button" onclick="loginss();" value="确定" class="button_sure"/>
	   <a id="coloa" href="register.asp" style="margin-left:15px;">新用户注册</a>
	   <!--
	   <input type="button" value="取消" onclick="closeLogin();" class="butok"/>
	   -->
	</p>
</form>
</div>
