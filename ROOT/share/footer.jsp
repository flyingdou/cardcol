<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div id="footer">
    <div id="footer-content">
	    <ul>
		    <li><a href="law.asp">法律申明</a> |</li>
		    <li><a href="server.asp">服务条款</a> |</li>
		    <li><a href="declare.asp">隐私声明</a> |</li>
		    <li><a href="copy.asp">著作权与商标声明</a> |</li>
		    <li><a href="integrity.asp">诚信与安全</a></li>
	    </ul>
	    <p>◎ 2010 武汉好韵莱信息技术有限公司 All Rights Reserved. <a href="http://www.miibeian.gov.cn" style="color:blue;">鄂ICP备08004430号</a></p>
		<div class="footer-content1">
			<div class="word_mark">
				<div>
                  <div><a href='http://221.232.141.246/iciaicweb/dzbscheck.do?method=change&id=E2015042000071529'><img alt='网络经济主体信息' border='0' DRAGOVER='true' src='images/index.php.png' /></a></div>
<%-- 				  <script language="javaScript" src="http://59.173.86.53:9090/NCTS/SignHandle/JsDcts/121102101454912e971306a475adac3a.js"></script> --%>
				</div>
				<div class="wen_zi"><span>工商局网络<br>商品交易监管</span></div>
			</div>
			<div class="word_mark">
				<div><a href="http://wuhan.cyberpolice.cn/ga/"><img src="images/jinghui.jpg"></a></div>
				<div class="wen_zi"><span>公安局网络备案<br>420100003762</span></div>
			</div>
			<div class="word_mark">
				<div><a href="http://www.pbccrc.org.cn/index.html"><img src="images/china_bank.jpg"></a></div>
				<div class="wen_zi"><span>人民银行信用代码<br>G1042011101683760F</span></div>
			</div>
			<div class="word_mark ">
				<div>
				<a href="http://www.cardcol.com/detail.asp?channel=5&id=39"><img src="images/china_all_bank.jpg"></a>
				</div>
				<div class="wen_zi"><span>银行交易资金<br>第三方监管</span></div>
			</div>
			<div class="word_mark">
				<div><a href="http://cn.unionpay.com/"><img src="images/getimage.png"></a></div>
				<div class="wen_zi"><span>中国银联<br>在线支付特约商户</span></div>
			</div>
			<div class="word_mark">
				<div><a href="https://www.alipay.com/"><img src="images/alipay.jpg"></a></div>
				<div class="wen_zi"><span>支付宝<br>签约商户</span></div>
			</div>
		</div>
    </div>
</div>
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
<div style="margin:0px auto;width:200px;">
	<script src="http://s6.cnzz.com/stat.php?id=5250816&web_id=5250816" language="JavaScript"></script>
	<script src="http://s6.cnzz.com/stat.php?id=5250816&web_id=5250816&show=pic" language="JavaScript"></script>
	<script src="http://s6.cnzz.com/stat.php?id=5250816&web_id=5250816&online=1" language="JavaScript"></script>	
</div>
