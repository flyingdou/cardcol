<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8">
<meta name="keywords" content="健身E卡通,北京健身E卡通,健身卡,健身计划" />
<meta name="description" content="健身E卡通可以在多家适用门店健身，解决居家、办公健身地点限制问题。按月购买，规避预付消费风险。与慈善相结合的健身挑战为您建立明确的健身目标，提供源源不绝的健身动力！健身专家系统可以根据您的身体数据自动生成的个性化健身方案，提供科学健身的指导！健身淘课随时都有想不到的惊喜！健身足迹忠实的记录您的每次健身的历程！" />
	<link href="${pageContext.request.contextPath}/css/bootstrap.min.css" type="text/css" rel="stylesheet" />
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/loginJbl.css"/>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/md5.js"></script>
	<title>登录</title>
	<style>
	.navUla{
background:none;

border-radius:0;
}
.navUla:hover{
background-color:rgba(0,0,0,0)!important;
color:red!important
}
.navUla:hover .navUlaa{
background-color:rgba(0,0,0,0)!important;
color:white!important
}

.navLast{
border:none 
}
/* 站长统计隐藏 */
#cnzz_stat_icon_1262618834{
display:none
}

</style>	

	<script language="javascript">
function login() {
	var mobile = $('input[name="mobilephone"]').val();
	var pass = $('input[name="password"]').val();
	if (mobile == '') {
		alert('请输入您的手机号或账号！');
		$('input[name="mobilephone"]').focus();
		$('input[name="mobilephone"]').select();
		return;
	}
	if (pass == '') {
		alert('请输入您的密码！');
		$('input[name="password"]').focus();
		return;
	}
	pass = hex_md5(pass);
	$.ajax({type: 'post', url: 'login!check.asp?v=' + Math.round(Math.random() * 10000), data: 'mobilephone=' + mobile + '&password=' + pass,
		success: function(resp) {
			if (resp === 'success') {
				var _href = window.location.href;
				if (_href.indexOf('login.asp') < 0) {
					var index = _href.indexOf('#');
					if (index > 0) _href = _href.substring(0, index);
					window.location.href = _href;
					return;
				}
				window.location.href = 'login.asp?v=' +Math.round(Math.random() * 10000);
			} else {
				alert('您的账户或密码不正确！');
				$('input[name="mobilephone"]').focus();
			}
		}
	});
}

</script>
</head>
<body>
	<nav class="navbar navbar-fixed-top"style="rgba(0,0,0,.5)">
		<div class="container">
			<div class="navbar-header">
				<%-- <a href="#"><img src="${pageContext.request.contextPath}/img/logo.png" height="30" alt="图标" /></a> --%>
				 <span style="color:white;display:inline-block;height:30px;line-height:30px;margin-left:2em;">
            服务热线：13908653155 &nbsp;&nbsp;18171294806
            </span>	
			</div>
			<ul class="nav navbar-nav navbar-right navUl">
					         <li style="margin-top: 12px!important;padding:0px"class="kkk">
                <a href="index.asp" class="navUla">首页&nbsp;&nbsp;&nbsp;<span class="navUlaa">|</span></a>
            </li>
             <li style="margin-top: 12px!important;padding:0px">
                <a href="login.asp" class="navUla">用户中心&nbsp;&nbsp;&nbsp;<span class="navUlaa">|</span></a>
            </li>
             <!-- <li style="margin-top: 12px!important;padding:0px">
                <a href="login.asp" class="navUla">消息&nbsp;<span class="navUlaa">|</span></a>
            </li> -->
            <li style="margin-top: 12px!important;padding:0px">
                <a href="login.asp"" class="navUla navLast">登录/注册</a>
            </li>
			</ul>
		</div>
	</nav>
	<!----------------------------------------登录------------------------------------------->
	<div class="row formDiv" id="dl">
		<div
			class="col-xs-10 col-xs-offset-1 col-sm-4 col-sm-offset-7 col-md-4 col-md-offset-7 col-lg-3 col-lg-offset-8 box">
			<div class="box_1">
				<span class="dl">登录</span> <span class="zc">注册</span>
			</div>
			<form class="form-horizontal" role="form"  method="post" 
			>
<!-- 				action="login ! check.asp" -->
				<!-- onsubmit="javascript:return dlyz()" -->
				<div class="form-group" style="margin: 10% 0;">
					<label for="phone"
						class="col-xs-2 col-sm-2 col-md-2 col-lg-2 control-label Mobole"></label>
					<div class="col-xs-8 col-sm-8 col-md-8 col-lg-8">
						<input type="text" class="form-control putC" id="phone" value="" name="mobilephone"
							placeholder="请输入手机号或账号">
					</div>
				</div>
				<div class="form-group " style="margin: 5% 0;">
					<label for="dlpsw"
						class="col-xs-2 col-sm-2 col-md-2 col-lg-2 control-label psw"></label>
					<div class="col-xs-8 col-sm-8 col-md-8 col-lg-8">
						<input type="password" class="form-control putC" id="dlpsw" value="" name="password"
							placeholder="请输入密码" >
					</div>
				</div>
				<div class="form-group" style="margin: 5% 0 10%;">
					<div class="col-sm-offset-2 col-sm-10 wlmm">
						<span class="col-xs-5 col-sm-5 col-md-5 col-lg-5 error"></span> <span
							class="col-xs-5 col-sm-5 col-md-5 col-lg-5 noM" id="wlmm"><a
							href="#" style="color: #bfbfbf; padding-right: 10px">忘了密码?</a></span>
					</div>
				</div>
				<div class="form-group">
					<div
						class="col-xs-offset-2 col-xs-8 col-sm-offset-2 col-sm-8 col-md-offset-2 col-md-8 col-lg-offset-2 col-lg-8 buttonBox">
						<button type="button"  id="b01"  class="btn btn-default butto" onclick="login()">
							<img src="${pageContext.request.contextPath}/img/successful.png" alt="" width="56" />
						</button>
					</div>
				</div>
			</form>
		</div>


	</div>
	<!-------------------------------------------注册---------------------------------------------->
	<div class="row formDiv" style="display: none" id="zc">
		<div
			class="col-xs-10 col-xs-offset-1 col-sm-4 col-sm-offset-7 col-md-4 col-md-offset-7 col-lg-3 col-lg-offset-8 box">
			<div class="box_1" style="margin-bottom: 12%">
				<span class="dl hui">登录</span> <span class="zc hong">注册</span>
			</div>

			<form class="form-horizontal" role="form1" action="register!savemember.asp" method="post">
				<!-- onsubmit="javascript:return zcyz();" -->

				<div class="form-group rAd" style="margin: 5% 0;">
					<div class="col-xs-1 col-sm-1 col-md-1 col-lg-1 ">
						<input type="radio" id="nba" checked="checked" name="member.role"
							value="E">
					</div>
					<label name="nba"
						class="col-xs-4 col-sm-4 col-md-4 col-lg-4 control-label radioC checked jlb"
						for="nba">俱乐部</label>
					<div class="col-xs-2  col-sm-2  col-md-2  col-lg-2 ">
						<input type="radio" id="cba" name="member.role" value="I">
					</div>
					<label name="cba" for="cba"
						class="col-xs-4 col-sm-4 col-md-4 col-lg-4 radioC control-label csjg">慈善机构</label>
				</div>


				<div class="form-group" style="margin: 5% 0;" >
					<label for="phone1"
						class="col-xs-1 col-sm-1 col-md-2 col-lg-2 control-label Mobole"></label>
					<div class="col-xs-10 col-sm-10 col-md-8 col-lg-8">
						<input type="text" class="form-control putC" name="member.mobilephone" id="phone1" 
							placeholder="请输入手机号">
					</div>
				</div>




				<div class="form-group " style="margin: 5% 0;">
					<label class="col-xs-1 col-sm-1 col-md-2 col-lg-2 control-label"></label>
					<div class="col-xs-10 col-sm-10 col-md-8 col-lg-8" style="">
						<div class="row yzm">
							<div class="col-xs-7  col-sm-7 col-md-7 col-lg-7 zcyz">
								<input type="text" class="form-control putC zcyzm" name="code" id="zcyzm"
									placeholder="请输入验证码">
							</div>

							<div class="col-xs-5 col-sm-5 col-md-5 col-lg-5 buttonBox"
								style="padding-left: 3px">
									<!--  获取验证码</button> -->
									<!-- <input type="button" disabled="disabled" id="registerBtn1" value="获取验证码" class="btn btn-default getyz"  onclick="settime(this)" /> -->
									<input type="button" disabled="disabled" id="registerBtn" value="获取验证码" class="btn btn-default getyz"  onclick="settime(this)" />
							</div>
						</div>

					</div>
				</div>

				<div class="form-group " style="margin: 5% 0;">
					<label for="dlpsw1"
						class="col-xs-1 col-sm-1 col-md-2 col-lg-2 control-label psw"></label>
					<div class="col-xs-10 col-sm-10 col-md-8 col-lg-8">
						<input name="member.password" type="password" class="form-control putC"  id="dlpsw1"
							placeholder="请输入密码">
					</div>
				</div>

				<div class="form-group" style="margin: 5% 0 10%;">
					<div class="col-sm-offset-2 col-sm-10 wlmm">
						<span class="col-xs-5 col-sm-5 col-md-5 col-lg-5 error"></span>
					</div>
				</div>

				<div class="form-group"
					style="margin-top: 40px; margin-bottom: 60px">
					<div
						class="col-xs-offset-1 col-xs-10 col-sm-offset-1 col-sm-10 col-md-offset-1 col-md-10 col-lg-offset-1 col-lg-10 buttonBox">
						<button type="submit" class="btn btn-default zcdl">注册</button>
					</div>
				</div>

			</form>
		</div>


	</div>
	<!----------------------------------------忘记密码------------------------------------------->
	<div class="row formDiv" id="wjmm" style="display: none">
		<div
			class="col-xs-10 col-xs-offset-1 col-sm-4 col-sm-offset-7 col-md-4 col-md-offset-7 col-lg-3 col-lg-offset-8 box">
			<div class="box_1 " style="margin-bottom: 10% !important;">
				<div class="dll" style="width: 100%">
					<div class="arrowR"></div>
					<span class="forgotMM">忘记密码</span>
				</div>
			</div>
			<form class="form-horizontal" role="form2" action="findpassword!updatepassword.asp" method="post"
				onsubmit="javascript:return wlmm();">
				<div class="form-group" style="margin: 5% 0;">
					<label for="phone2"
						class="col-xs-1 col-sm-1 col-md-2 col-lg-2 control-label Mobole"></label>
					<div class="col-xs-10 col-sm-10 col-md-8 col-lg-8">
						<input type="text" name="mobile" class="form-control putC" id="phone2"
							placeholder="请输入手机号" >
					</div>
				</div>
				<div class="form-group " style="margin: 5% 0;">
					<label class="col-xs-1 col-sm-1 col-md-2 col-lg-2 control-label"></label>
					<div class="col-xs-10 col-sm-10 col-md-8 col-lg-8" style="">
						<div class="row wjsryz">
							<div class="col-xs-7  col-sm-7 col-md-7 col-lg-7 wjyz">
								<input type="text" name="code" class="form-control putC zcyzm wlmmyz"
									id="a1" placeholder="请输入验证码" >
							</div>

							<div class="col-xs-5 col-sm-5 col-md-5 col-lg-5 buttonBox"
								style="padding-left: 3px">
									<input type="button" disabled="disabled" id="forgetPasswordBtn" value="获取验证码" class="btn btn-default getyz"  onclick="settimeForget(this)" />
							</div>
						</div>

					</div>
				</div>

				<div class="form-group " style="margin: 5% 0;">
					<label for="wlpsw"
						class="col-xs-1 col-sm-1 col-md-2 col-lg-2 control-label psw"></label>
					<div class="col-xs-10 col-sm-10 col-md-8 col-lg-8">
						<input name="password" type="password" class="form-control putC" id="wlpsw"
							placeholder="请输入密码">
					</div>
				</div>

				<div class="form-group " style="margin: 5% 0;">
					<label for="wlpsw1"
						class="col-xs-1 col-sm-1 col-md-2 col-lg-2 control-label "></label>
					<div class="col-xs-10 col-sm-10 col-md-8 col-lg-8">
						<input type="password" class="form-control putC" id="wlpsw1"
							placeholder="请再次输入密码">
					</div>
				</div>
				<div class="form-group" style="margin: 5% 0 10%;">
					<div class="col-sm-offset-2 col-sm-10 wlmm">
						<span class="col-xs-5 col-sm-5 col-md-5 col-lg-5 error"></span>

					</div>
				</div>


				<div class="form-group"
					style="margin-top: 40px; margin-bottom: 60px">
					<div
						class="col-xs-offset-1 col-xs-10 col-sm-offset-1 col-sm-10 col-md-offset-1 col-md-10 col-lg-offset-1 col-lg-10 buttonBox">
						<button type="submit" class="btn btn-default zcdl">确定</button>
					</div>
				</div>
			</form>
		</div>
	
	
	</div>
	
	
	<script type="text/javascript">

var _hmt = _hmt || [];
(function() {
  var hm = document.createElement("script");
  hm.src = "https://hm.baidu.com/hm.js?c1541a9c717b36d9e0ba03b4fcaa01ba";
  var s = document.getElementsByTagName("script")[0]; 
  s.parentNode.insertBefore(hm, s);
})();




var cnzz_protocol = (("https:" == document.location.protocol) ? " https://" : " http://");document.write(unescape("%3Cspan id='cnzz_stat_icon_1262618834'%3E%3C/span%3E%3Cscript src='" + cnzz_protocol + "s13.cnzz.com/z_stat.php%3Fid%3D1262618834' type='text/javascript'%3E%3C/script%3E"));</script>
</body>

<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/js/loginJbl.js"></script>

<script type="text/javascript">
	//获取地址栏的参数数组
	function getUrlParams()
	{
	    var search = window.location.search ;
	    // 写入数据字典
	    var tmparray = search.substr(1,search.length).split("&");
	    var paramsArray = new Array;
	    if( tmparray != null)
	    {
	        for(var i = 0;i<tmparray.length;i++)
	        {
	            var reg = /[=|^==]/;    // 用=进行拆分，但不包括==
	            var set1 = tmparray[i].replace(reg,'&');
	            var tmpStr2 = set1.split('&');
	            var array = new Array ;
	            array[tmpStr2[0]] = tmpStr2[1] ;
	            paramsArray.push(array);
	        }
	    }
	    // 将参数数组进行返回
	    return paramsArray ;    
	}

	// 根据参数名称获取参数值
	function getParamValue(name)
	{
	    var paramsArray = getUrlParams();
	    if(paramsArray != null)
	    {
	        for(var i = 0 ; i < paramsArray.length ; i ++ )
	        {
	            for(var  j in paramsArray[i] )
	            {
	                if( j == name )
	                {
	                    return paramsArray[i][j] ;
	                }
	            }
	        }
	    }
	    return null ;
	}
	
	if(getParamValue("updateStatus") != null && getParamValue("updateStatus") != ""){
		var status = getParamValue("updateStatus");
		
		if (status == "1") {
			alert("修改密码成功！")
		} else if (status == "0") {
			alert("修改密码失败！请检查验证码是否正确！")
		}
	} 
	
	var countdownForget=60; // 用于忘记密码的倒计时
	var timeIdForget; // 忘记密码的定时器对象
	function settimeForget(val) {
	    if (countdownForget == 0) {
	        val.removeAttribute("disabled");
	        val.value="免费获取验证码";
	        countdownForget = 60;
	        clearTimeout(timeIdForget);
	        return;
	    } else {
	        val.setAttribute("disabled", true);
	        val.value="重新发送(" + countdownForget + ")";
	        countdownForget--;
	    }
	    timeIdForget = setTimeout(function() {
	        settime(val);
	    },1000)
	}

    var countdown=60; // 用户注册的倒计时
    var timeId;
    function settime(val) {
        if (countdown == 0) {
            val.removeAttribute("disabled");
            val.value="免费获取验证码";
            countdown = 60;
            clearTimeout(timeId);
            return;
        } else {
            val.setAttribute("ddisabled", true);
            val.value="重新发送(" + countdown + ")";
            countdown--;
        }
        timeId = setTimeout(function() {
            settime(val);
        },1000)
    }
  	
    $("#registerBtn").click(function(){
    	var mobile = $('input[name="member.mobilephone"]').val();
    	var flag = 1; // 1 代表注册 , 0 代表忘记密码
    	
    	if (mobile == '') {
    		alert('请输入您的手机号！');
    		return;
    	}
    	
    	$.ajax({
            type:"POST",
            url:"register!MobileCode.asp",
            data:{mobilephone:mobile,flag:flag},
         });
    })
    
    $("#forgetPasswordBtn").click(function(){
    	var mobile = $("#phone2").val();
    	var flag = 0; // 1 代表注册 , 0 代表忘记密码
    	
    	if (mobile == '') {
    		alert('请输入您的手机号！');
    		return;
    	}
    	
    	$.ajax({
            type:"POST",
            url:"register!MobileCode.asp",
            data:{mobilephone:mobile,flag:flag},
         }); 
    })
    
    
   $(function(){
	     $("#phone1").blur(function(){
	       var nc=/^(13[0-9]|14[5|7]|15[0|1|2|3|5|6|7|8|9]|18[0|1|2|3|5|6|7|8|9])\d{8}$/;
	       
	       if(!nc.test($(this).val())){
	           $('#registerBtn').attr("disabled",true)
	       }else{
	           $('#registerBtn').attr("disabled",false)
	       }
	     })
	     
	      $("#phone2").blur(function(){
	       var nc=/^(13[0-9]|14[5|7]|15[0|1|2|3|5|6|7|8|9]|18[0|1|2|3|5|6|7|8|9])\d{8}$/;
	       
	       if(!nc.test($(this).val())){
	           $('#forgetPasswordBtn').attr("disabled",true)
	       }else{
	           $('#forgetPasswordBtn').attr("disabled",false)
	       }
	     })
    })
    

	function savePassword(){
    	var mobile = $('input[name="member.mobilephone"]').val();
    	if (mobile == '') {
    		alert('请输入您的手机号！');
    		return;
    	}
	if($('#wlpsw').val() == $('#wlpsw1').val()){
		$.ajax({
			url : 'findpassword!reset.asp',
			type : 'post',
			data : 'mobile=' + $('#mobile').val() + '&code=' + $('#a1').val()+ '&password=' + $('#wlpsw1').val(),
			success : function(msg) {
				if (msg == 'OK') {
					$('#form2').attr('action','login.asp');
					$('#form2').submit();
				} else if (msg == 'CODE'){
					alert("您输入的验证码有误，请重新输入！");
					return;
				}else{
					alert("您修改密码失败，请重新修改！");
					return;
				}
			}
		});
	}else{
		alert("两次输入密码不一致，请重新输入！");
	}
	}
    </script>

</html>