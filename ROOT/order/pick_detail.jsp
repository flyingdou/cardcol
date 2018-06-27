<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<s:include value="/share/meta.jsp" />
<meta name="keywords" content="瘦身减肥课程,肌肉健美课程,增强体质" />
<meta name="description"
	content="健身教练的商务中心是健身教练制定的健身套餐,健身课程，如：瘦身减肥课程,肌肉健身课程,增强体质课程等。" />
<title>提现</title>
<link href="css/club-order.css" rel="stylesheet" type="text/css" />
<link type="text/css" rel="stylesheet" href="css/pulicstyle.css" />
<script type="text/javascript">
function addAccount(){
	var index = $('.tidiv').length;
	var tidivHtml = '<div class="tidiv" id="div'+index+'">'
	  			  +'<input type="hidden" name="pickAccountList['+index+'].id" value="">'
	  			  +'<input type="hidden" name="pickAccountList['+index+'].member.id" value="<s:property value="#session.loginMember.id"/>">'
				  +'<span class="tispana">'
				  +'<input type="radio" name="radiobutton" value="" id="radio" class="leinp1" />'
				  +'<input type="text" name="pickAccountList['+index+'].name" value=""></span>'
				  +'<span class="tispanb">'
				  +'<input type="text" name="pickAccountList['+index+'].bankName" value="">'
				  +'</span>'
				  +'<span class="tispanbb">'
				  +'<input type="text" name="pickAccountList['+index+'].account" value="">'
				  +'</span>'
				 /*  +'<span class="tispanc"><a href="javascript:delAccount('+index+');" id="colotoa">删除</a></span>' */
				  +'</div>';
	$("#pickDiv").append(tidivHtml);
}
function saveAccount(){
	$.ajax({type:"post",url:'pickdetail!savePickAccount.asp',data:$('#form1').serialize(),
		success:function(msg){
			alert("当前数据已成功保存！");
			//刷新当前页面
			var url = "pickdetail!query.asp";
			location.href = url;
			/* $("#right-2").html(msg); */
		}
	});
}
function delAccount(divId,id){
	if(id){
		$.ajax({type:'post',url:'pickdetail!delete.asp',data:'pickAccount.id='+id,
			success:function(msg){
				if(msg == "ok"){
					$("#div"+divId).remove();
					changeName();
				}else{
					alert(msg);
				}
			}
		});
	}else{
		$("#div"+divId).remove();
		changeName();
	}

}
function changeName(){
	$('.tidiv').each(function(index){
		var idVal = $(this).attr("id").substring(3);
		$(this).attr("id","div"+index);
		$(this).find("input").each(function(index1){
			if($(this).attr("name").indexOf("pickAccountList") != -1){
				$(this).attr("name",$(this).attr("name").replace("pickAccountList["+idVal+"]","pickAccountList["+index+"]"));
			}
		});
	});
}
function pick(){
	var pickAccountDiv = $(".tidiv");
	var pickAccount = $('input[name="radiobutton"]:checked').val();
	var pickMoney = $('input[name="pickDetail.pickMoney"]').val();
	
	if(pickAccountDiv.length == 0){
		alert("请先添加提现账户！");
		return;
	}
    if(!pickAccount){
		alert("请先选择提现账户！");
		return;
	}
	if(!pickMoney){
		alert("请输入提现金额！");
		$('input[name="pickDetail.pickMoney"]').select();
		$('input[name="pickDetail.pickMoney"]').focus();
		return;
	}
	var reg = new RegExp(/^[0-9]+(.[0-9]{1,2})?$/);//"/^[0-9]+(.[0-9]{1,2})?$/";
	if(!reg.test(pickMoney)){
		alert("请输入正确的提现金额数据！");
		$('input[name="pickDetail.pickMoney"]').select();
		$('input[name="pickDetail.pickMoney"]').focus();
		return;
	}
	if(parseFloat(pickMoney) < 0){
		alert("提现单笔金额不少于0.01元！");
		$('input[name="pickDetail.pickMoney"]').select();
		$('input[name="pickDetail.pickMoney"]').focus();
		return;
	}
	
	if(parseFloat(pickMoney) > parseFloat($('#pickMoneyCountSpan').html())){
		alert("单笔提现金额不能高于提现余额！");
		$('input[name="pickDetail.pickMoney"]').select();
		$('input[name="pickDetail.pickMoney"]').focus();
		return;
	}
	
	if(!$('input[name="pickDetail.identificationCode"]').val()){
		alert("请输入验证码！");
		$('input[name="pickDetail.identificationCode"]').select();
		$('input[name="pickDetail.identificationCode"]').focus();
		return;
	}
	reg = new RegExp(/^[0-9]{6}?$/);
	if(!reg.test($('input[name="pickDetail.identificationCode"]').val())){
		alert("验证码必须为6位数字！");
		$('input[name="pickDetail.identificationCode"]').select();
		$('input[name="pickDetail.identificationCode"]').focus();
		return;
	}
	$.ajax({type:"post",url:'pickdetail!save.asp',data:'pickDetail.pickMoney='+pickMoney+'&pickDetail.pickAccount.id='+pickAccount+"&pickDetail.identificationCode="+$('input[name="pickDetail.identificationCode"]').val(),
		success:function(msg){
			if(msg == "ok"){
				alert("您的提现申请已发送！");
				var url = "pickdetail!query.asp";
				location.href=url;
			}else if(msg == "no"){
				alert("验证码不正确！");
				$('input[name="pickDetail.identificationCode"]').select();
				$('input[name="pickDetail.identificationCode"]').focus();
			}else if(msg == "NoMobile"){
				alert("您还没有设置手机号码！");
			}else{
				alert(msg);
			}
		}
	});
}
function getMobileValidCode(o){
	$.ajax({type:"post",url:'pickdetail!getMobileValidCode.asp',data:'',
		success:function(msg){
			if(msg == "OK"){
				alert("验证码已成功发送到您的手机！");
			}else if(msg == "NoMobile"){
				alert("您还没有设置手机号码！");
			}else{
				alert(msg);
			}
		}
	});
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

//载入函数
$(function(){
    var mobilephone = '<s:property value="#session.loginMember.mobilephone"/>';
	mobilephone = mobilephone + "";
	if (mobilephone != "" && mobilephone != null && mobilephone != undefined){
		$("#hasMobileSpan").html("绑定手机： " + mobilephone.substring(0,3) + "*****" + mobilephone.substring(8,11));
		$("#hasMobileSpan").css({"color":"black"});
	} else {
		var urlx = "mobile.asp"
		$("#noMobileSpan").html("绑定手机： 未绑定手机，请前往<a href="+urlx+" style='color:red' >我的账户</a>绑定。");
		$("#noMobileSpan").css({"color":"black"});
	} 
	
})

</script>
</head>
<body>
	<s:include value="/share/header.jsp" />
	<div id="content">
	<s:include value="/order/nav.jsp" />
		<div id="right-2">
			<h1>我要提现</h1>
<h4>
	<span class="spanh3a"
		style="width: 200px; padding-left: 50px; padding-right: 80px;"">账户名称</span><span
		class="spanh3b" style="padding-left: 140px; padding-right: 140px;">开户行</span><span
		class="spanh3bb" style=""padding-left:100px;padding-right:100px;">账号</span><%-- <span
		class="spanh3c">操作</span> --%>
</h4>
<form id="form1" name="form1" method="post" action="">
	<div id="pickDiv">
		<s:iterator value="pickAccountList" status="sta">
			<div class="tidiv" id="div<s:property value="#sta.index"/>">
				<input type="hidden"
					name="pickAccountList[<s:property value="#sta.index"/>].id"
					value="<s:property value="id"/>"> <input type="hidden"
					name="pickAccountList[<s:property value="#sta.index"/>].member.id"
					value="<s:property value="member.id"/>"> <span
					class="tispana"> <input type="radio" name="radiobutton"
					value="<s:property value="id"/>" id="radio" class="leinp1" /> <input
					type="text"
					name="pickAccountList[<s:property value="#sta.index"/>].name"
					value="<s:property value="name"/>">
				</span> <span class="tispanb"> <input type="text"
					name="pickAccountList[<s:property value="#sta.index"/>].bankName"
					value="<s:property value="bankName"/>">
				</span> <span class="tispanbb"> <input type="text"
					name="pickAccountList[<s:property value="#sta.index"/>].account"
					value="<s:property value="account"/>">
				</span> <%-- <span class="tispanc"><a
					href="javascript:delAccount(<s:property value="#sta.index"/>,<s:property value="id"/>);"
					id="colotoa">删除</a></span> --%>
			</div>
		</s:iterator>
	</div>
</form>
<div class="adda">
	<a href="javascript:addAccount();" id="colotoa">添加账户</a>&nbsp;&nbsp;<a
		href="javascript:saveAccount();" id="colotoa">保存</a>
</div>
<div class="txdiv">
	提现金额:<input type="text" name="pickDetail.pickMoney" class="leinp3" />元
	（你可以提现的余额为：<span id="pickMoneyCountSpan"><s:property
			value="pickMoneyCount" /></span>元）
	<div>
		<p id = "hasMobile" >
		   <span id = "hasMobileSpan" ></span>
		   <span id = "noMobileSpan" ></span>
		</p>
		<p class="moble-no">
			<span>请输入手机的动态验证码：</span><input type="text"
				name="pickDetail.identificationCode" /><input type="button"
				onclick="getMobileValidCode(this)" value="获取验证码">
		</p>
		<br>
		<p class="warning-word">
			<span>如果要更换绑定的手机请联系客服。</span>
		</p>
	</div>
</div>
<div class="secdiv">
	<input name="button" type="button" class="inpdiv1" value="确定"
		onclick="pick();" />
</div>
		</div>
	</div>
	</div>
</body>
</html>




