<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%
  String path = request.getContextPath();
  String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<base href="<%=basePath%>" >
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0"/>
<script src="ecartoon-weixin/js/jquery-2.1.1.min.js"></script>
<script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js "></script>
<script src="js/utils.elisa.js" ></script>
<title>验证手机号</title>
<style type="text/css">
   html,body{
     margin: 0;
     padding: 0;
     width: 100%;
     height: 100%;
     background-color: #F3F3F4;
   }
   
input::-webkit-input-placeholder{
   color:#9E9E9E;
   font-size: 16px;
} 
input {
  padding-top:10px;
  padding-bottom:10px;
  padding-left: 12px;
  font-size:16px;
  width: 100%;
  border: none;
}

#input{
  margin-top: 12px;
}

#code{
  position: relative;
  margin-top: 1px;
}
#getCode{
   background: url("ecartoon-weixin/img/Verification-code@2x.png") no-repeat;
   background-size:100% 100%;
   position: absolute;
   width: 86px;
   height: 25px;
   text-align: center;
   border: none;
   right:10px;
   top:71px;
   color: #FE4304;
   padding: 0px;
   font-size: 12px;
}

#image{
   text-align: center;
}

#imageDou{
   width: 35px;
}

#message{
  text-align: center;
  
}

#luckWords{
  color: #9E9E9E;
  font-size: 12px;
}

#footer{
   position: fixed;
   bottom: 0;
   text-align: center;
}
#checkCode{
  background: url("ecartoon-weixin/img/checkCodeButton.png") no-repeat;
  background-size: 100% 100%;
  height: 50px;
  text-align: center;
  color: white;
  font-size: 20px;
  border: none;
  margin: 0 8px;
}
</style>
</head>
<body>
  <div>
     <div id = "input">
	     <input type="number" id="mobile" name="mobilephone" style="width: 100%"/><br/>
	     <input type="number" id="code"  name = "code"/>
	     <input type = "button" id="getCode" value="获取验证码" onclick="getCode(this)" />
     </div>
    
    <div id = "message">
      <div id = "image">
         <img src="ecartoon-weixin/img/Look-surprised@2x.png" id = "imageDou">
      </div>
      <span id = "luckWords">
        完成手机验证，第一时间领取更多福利！
      </span>
    </div>
    
    <div id = "footer">
       <button id = "checkCode" onclick="saveMobile()">完成</button>
    </div>
    
    <form action="#" method="post"  id = "drt">
         <input type="hidden" name="jsons" value='${jsons}' />
     </form>
    
  </div>




<script type="text/javascript">
var latitude;
var longitude;

	$(function(){
		  $("#mobile").attr({"placeholder":"请输入手机号"});
		  $("#code").attr({"placeholder":"请输入短信验证码"});
		  var ww = window.screen.width;
		  $("#checkCode").css({"width":ww*0.96});
		  
		  var mobileHeight = $("#mobile").innerHeight();
		  var codeHeight = $("#code").innerHeight();
		  var getCodeHeight = $("#getCode").height();
		  var toHeight = (12 + mobileHeight + 1 + ((codeHeight - getCodeHeight)/2));
		  
		  var sUserAgent = navigator.userAgent.toLowerCase();
		  var bIsIphoneOs = sUserAgent.match(/iphone os/i) == "iphone os";
		  if (bIsIphoneOs){
			  toHeight = (toHeight + 3.5);
		  }
		  $("#getCode").css({"top":toHeight});
		
		
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
						$("#getCode").val(x);
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