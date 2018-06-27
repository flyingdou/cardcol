<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html>
<html>

	<head>
		<meta charset="UTF-8">
		<title></title>
		<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
		<link href="${pageContext.request.contextPath}/eg/css/mui.min.css" rel="stylesheet" />
		<style>
			.mui-content{
				border:none;
				background:white;
			}
			.input_box{
				width: 80%;
				margin: 0 auto;
				border-bottom:1px solid #eaeaea;
				padding: 10px;
			}
			.div_left{
			    width: 40%;
			    float:left;
			    line-height: 40px!important;
			    font-size:15px
			}
			.div_right{
				width: 60%;
				float:left
			}
			.input_css{
				margin-bottom: 0px!important;
				border:none!important;
				text-align: right;
				padding: 0!important;
			}
				.input_css1{
				margin-bottom: 0px!important;
				border:none!important;
				text-align: left;
				padding: 0!important;
			}
			.hq_div{
				width: 60%;
				float:left;
				line-height: 40px;
				text-align: right;
			}
		    .span_hq{
		    	border: 1px solid #ff4401;
		    	padding: 5px;
		    	border-radius:4px;
		    	background: #ff4401;
		    	color:white;
		    	font-size:13px
		    }
		    .errorbox{
		    	width:80%;
		    	text-align: center;	
		    }
		    .error{
		    	padding: 10px;
		    	display: none;
		    }
		    .ljzc{
		    	width:100%;
		    	height:100%;
		    	matgin:0;
		    	padding: 0;
		    	background: #ff4401;
		    	border:none;
		    	font-size: 15px;
		    	color:white
		    }
		</style>
	</head>

	<body>
		<script src="${pageContext.request.contextPath}/eg/js/mui.min.js"></script>
		<script type="text/javascript">
			mui.init()
		</script>
		<form action="../findpasswordwx!updatepassword.asp" method="post" onsubmit="javascript:return login_1();">
	
		<div class="mui-content">
		    <div class="phone"style="">
		    	<div class="input_box">
		    	   <div class="div_left" >
		    	   	<label for="phone1">手机号码：</label>
		    	   </div>
		    	    <div class="div_right">
		    		       <input type="number" id="phone1" name="mobile" placeholder="请输入手机号码" value=""class="input_css" />
		    	    </div>
		    	                     <div style="clear: both;">	</div>
		    	</div>
		    	
		    		<div class="input_box">
		    	   <div class="div_left">
		    	   	   <input type="text" name="code" id="yzm1" value=""placeholder="请输入验证码" class="input_css1"/>
		    	   </div>
		    	    <div class="hq_div">
		    		     <button id="span_hq" class="span_hq" onclick="setTime(this)">获取验证码</button>
		    	    </div>
		    	     <div style="clear: both;">	</div>
		    	</div>
		    	
		    	
		    		<div class="input_box">
		    	   <div class="div_left">
		    	   	<label for="password">输入密码：</label>
		    	   </div>
		    	    <div class="div_right">
		    		       <input type="password" name="password" id="password" value=""placeholder="请输入密码"class="input_css" />
		    	    </div>
		    	                     <div style="clear: both;">	</div>
		    	</div>
		    	
		    	
		    		<div class="input_box"style="border:none">
		    	   <div class="div_left">
		    	   	<label for="password1">再次输入密码：</label>
		    	   </div>
		    	    <div class="div_right">
		    		       <input type="password" name="" id="password1" value=""placeholder="请输入密码"class="input_css" />
		    	    </div>
		    	                     <div style="clear: both;">	</div>
		    	</div>
		    	
		    	<div class="errorbox"style="text-align: center;border:none">
		            <span class="error"></span>
		    	</div>
		    	
		    </div>
		</div>
			<footer class="mui-bar mui-bar-footer font-white"style="border:none;background: white;padding: 0;border:none">
              <button  type="submit" class="ljzc" >
                       完成修改
                    </button>
	     		
			</footer>
			</form>
	</body>
	<script src="${pageContext.request.contextPath}/eg/js/jquery-1.9.1.js"></script>
	<script type="text/javascript">
		$("#span_hq").click(function(){
	    	var mobile = $('input[name="member.mobilephone"]').val();
	    	var flag = 1; // 1 代表注册 , 0 代表忘记密码
	    	
	    	if (mobile == '') {
	    		alert('请输入您的手机号！');
	    		return;
	    	}
	    	/* $.ajax({
	            type:"POST",
	            url:"register!MobileCode.asp",
	            data:{mobilephone:mobile,flag:flag},
	         }); */
	    }) 
	</script>
	<script type="text/javascript">
		
			//=======================验证时间倒数计时====================
	var waitTime=59;
	$(".span_hq").click(function(){
		   var nc=/^(13[0-9]|14[5|7]|15[0|1|2|3|5|6|7|8|9]|18[0|1|2|3|5|6|7|8|9])\d{8}$/;
		if(nc.test($("#phone").val())){
			
		}
	})
	function setTime(obj){
	    var nc=/^(13[0-9]|14[5|7]|15[0|1|2|3|5|6|7|8|9]|18[0|1|2|3|5|6|7|8|9])\d{8}$/;
	    if(nc.test($("#phone1").val())){
			
		if(waitTime==0){
		obj.removeAttribute("disabled");    
        obj.innerText="获取验证码"; 
        waitTime = 59; 
        return;	
		}else{
			obj.setAttribute("disabled", true); 
	        obj.innerText="重新发送(" + waitTime + ")"; 
	        waitTime--; 
		};
	
		setTimeout(function(){
			setTime(obj)},1000)
		}
		
	}
		//=======================验证时间倒数计时====================	
		
		
		$(function(){
			  $("#phone1").blur(function(){
        var nc=/^(13[0-9]|14[5|7]|15[0|1|2|3|5|6|7|8|9]|18[0|1|2|3|5|6|7|8|9])\d{8}$/;
        if ($(this).val() == ""
        ) {
        	$(".error").css("display","block");
            $(".error").html("号码不能为空").css("color", "red");
            return false;
        } else if (!nc.test($(this).val())) {
        	$(".error").css("display","block");
            $(".error").html("号码有误！").css("color", "red");
            return false;
        } else {
        	$(".error").css("display","block");
            $('.error').html("").css("color", "blue");
            $(".span_hq").attr("disabled",false);  
            return true;
        }
    });

    $("#yzm1").blur(function(){
        if ($(this).val() == ""
        ) {
        	$(".error").css("display","block");
            $(".error").html("验证不能为空").css("color", "red");
            return false;
        }else{
            return true;
        }
    });
    $("#password").blur(function(){
        var nc=/^\w{6,13}$/;
//            5-12位数字，字母
        if ($(this).val() == ""
        ) {
        	$(".error").css("display","block");
            $(".error").html("密码不能为空").css("color", "red");
            return false;
        } else if (!nc.test($(this).val())) {
        	$(".error").css("display","block");
            $(".error").html("密码为6-13位").css("color", "red");
            return false;
        } else {
        	$(".error").css("display","block");
            $('.error').html("").css("color", "blue");
            return true;
        }
    });
    $("#password1").blur(function(){
        if ($(this).val() == ""
        ) {
        	$(".error").css("display","block");
            $(".error").html("密码不能为空").css("color", "red");
            return false;
        }else if ($(this).val()!=$("#password").val()){
        	$(".error").css("display","block");
            $(".error").html("两次输入不一致").css("color", "red");
            return false;
        }else{
            return true;
        }
    })
    
 //------------------拦截不合法的输入禁止提交--------------   


		});
	function login_1(){
    var nc = /^(13[0-9]|14[5|7]|15[0|1|2|3|5|6|7|8|9]|18[0|1|2|3|5|6|7|8|9])\d{8}$/;
    var nc1 =/^\w{6,13}$/;
    if(!nc.test($("#phone1").val())){
        return false
    }else if(!nc1.test($("#password").val())){
        return false
    }else if($("#password").val()!=$("#password1").val()){
        return false
    }else if($("#yzm1").val()==""){
        return false
    } else{
        return true
    }
}

	</script>

</html>