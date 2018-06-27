<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" charset="utf-8">
<meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0"/>
<title>绑定手机号</title>
<script type="text/javascript" src="ecartoon-weixin/js/jquery-2.1.1.min.js" ></script>
<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js "></script>
<script type="text/javascript" src="js/utils.elisa.js" ></script>
<style type="text/css">
 #body{
    background:url("ecartoon-weixin/img/wx-background.png") no-repeat;
    background-size:100% auto;
    overflow: scroll;
 }

#logo{
  text-align: center;
  margin-top: 65px;
}

#logox{
   width: 76px;
   height: 76px;
}


.phonexx{
     width: 9px;
     height: 14px;
}

.codexx{
    width: 9px;
    height: 11px;
}

#input{
   text-align: center;
   margin-top: 30%;
   margin-left: 18%;
  
}

#mobile{
   background-color: transparent;
   border : 0;
   outline:none;
   border-bottom: 1px solid #FFF;
   color: #FFF;
   height: 22px;
   font-size: 16px;
   width:250px;
   
}

#code{
   background-color: transparent;
   border : 0;
   outline:none;
   border-bottom: 1px solid #FFF;
   color: #FFF;
   height: 22px;
   font-size: 16px;
   margin-top: 16px;
   width: 135px;
}

input::-webkit-input-placeholder{
   color:#FFF;
   font-size: 15px;

}

#getCodedd{
    background-color: transparent;
    background:url("ecartoon-weixin/img/wx-check.png") no-repeat;
    background-size:100% 100%;
    border: 0;
    width: 100px;
    height: 27px;
    margin-left: 15px;
    color:#FFF;
    font-size: 15px;
    
}


.saveMobile{
   background:url("ecartoon-weixin/img/wx-button.png") no-repeat;
   background-size:100% 100%;
   border: 0;
   width: 44px;
   height: 44px;
}

#sure{
   text-align: center;
   margin-top: 76px;
}


</style>

</head>
<body id = "body" >
<div>
     <div id="logo">
        <img id="logox" src="ecartoon-weixin/img/wx-logo.png">
     </div>
     <form action="#" method="get">
	     <div id="input">
	        	<img  src="ecartoon-weixin/img/wx-phone.png" class = "phonexx">
	            <input type="number" name = "mobile" id = "mobile" class = "inputx" /></br>
	            <img  src="ecartoon-weixin/img/wx-pw.png" class = "codexx">
	            <input type="number" name = "code" id= "code" class = "inputx" />
	            <input type="button" value="获取验证码" id="getCodedd" onclick="getCode(this)"  />
	     </div>
	     
	     <div id="sure">
	            <input type="button" onclick="saveMobile()" class = "saveMobile" />
	     </div>
     </form>
     
     <form action="#" method="post"  id = "drt">
         <input type="hidden" name="jsons" value='${jsons}' />
     </form>
</div>
<script type="text/javascript">
var latitude;
var longitude;

	$(function(){
		$("#mobile").attr({"placeholder":"请输入手机号码"});
		$("#code").attr({"placeholder":"请输入验证码"});
		
		
		
		wxUtils.sign("ewechatwx!sign.asp");
		wx.ready(function(){
	    	wx.getLocation({
			    success: function (data) {
			    	latitude = data.latitude;
			    	longitude = data.longitude;
			    },
			    cancel: function (data) {
			        alert('用户拒绝授权获取地理位置');
			    }
			});
	    });
		
		
		
	})
	
	
	function getCode(val){
		var mobile = $("#mobile").val();
		var flag = 0;
		$.ajax({
			url:"ememberwx!getMobileCode.asp",
			type:"post",
			data:{"mobile":mobile,"flag":1},
			success:function(res){
				//获取到验证码
				var json = JSON.parse(res);
				if(json.success){
					alert("验证码已发送至您的手机，请注意查收！");
					var x = 60;
					setTimeout(function(){
						$("#getcode").val(x);
						x--;
						if( x > 0){
							val.setAttribute("disabled", true); 
							val.value="重新发送(" + x + ")"; 
							setTimeout(arguments.callee,1000);
						}else{
							val.removeAttribute("disabled");    
							val.value="获取验证码"; 
							x = 60; 
						}
					},0);
				}else{
					alert(json.message);
				}
				
			},
			error:function(res){
				console.log(res);
			}
			
		});
		
	}
	
	function saveLocation(){
		wxUtils.sign("ewechatwx!sign.asp");
		wx.ready(function(){
	    	wx.getLocation({
			    success: function (data) {
			    	$.ajax({
						url:"ememberwx!updateLocation.asp",
						type:"post",
						data:{"latitude":data.latitude,"longitude":data.longitude},
						success:function(res){
							
						},
						error:function(res){
							console.log(res);
						} 
					});
			    	
			    	
			    },
			    cancel: function (data) {
			        alert('用户拒绝授权获取地理位置');
			    }
			});
	    });
	}
		
		

	
	
	
	function saveMobile(){
		var mobile = $("#mobile").val();
		var code = $("#code").val();
		var upUrl = location.href.split('#')[0];
		if(upUrl.indexOf("eproductwx!findProduct45Member") != -1 ){
			upUrl = "http://www.ecartoon.com.cn/ememberwx!findMe.asp";
		}
		
		
		
		$.ajax({
			url:"ememberwx!validMobile.asp",
			type:"post",
			data:{"mobile":mobile,"code":code,"flag":1,"latitude":latitude,"longitude":longitude},
			success:function(res){
				//绑定手机成功
				var json = JSON.parse(res); 
			    if(json.success){
			    	//跳转到绑定手机号之前访问的页面
			    	alert(json.message);
			    	$("#drt").attr("action",upUrl);
			    	$("#drt").submit();
			    }else{
			    	alert(json.message);
			    }
			},
			error:function(res){
				console.log(res);
			}
		});
		
	}
	
	
</script>
	
</body>
</html>