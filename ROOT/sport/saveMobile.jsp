<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<base href="<%=basePath%>" >
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>绑定手机号</title>
<link rel="stylesheet" type="text/css" href="sport/css/mui.min.css" />
<script type="text/javascript" src="sport/js/jquery-2.1.1.min.js" ></script>
<script type="text/javascript" src="sport/js/mui.min.js"></script>
</head>
<body>
    <div class="mui-card">
    <p align="center" style="font: 18px; top:2px;right: 0;" >绑定手机号</p>
	<div class="mui-card-content">
		<div id = "app" >
			<form action="#" method="get" style="position: relative;">
				<input type="text" name = "mobile" id = "mobile" style="display:inline-block;width:100%" />
				<input type="button" value="获取验证码" id = "getcode" onclick = "getCode(this)" style="position:absolute;top:0;right:0;background-color:rgba(255,255,255,.3);display:inline-block;height:40px;" />
				<input type="text" name = "code" id = "code" />
				<input type="button" value="绑定" onclick="saveMobile()" style="position:absolute;top:55px;right:0;background-color:rgba(255,255,255,.3);display:inline-block;height:40px;"  />
			</form>
		</div>
	</div>
</div>


	
	
	
	
	
	<script type="text/javascript">
	function getCode(val){
		var mobile = $("#mobile").val();
		var flag = 0;
		$.ajax({
			url:"smemberwx!getMobileCode.asp",
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
	
	
	function saveMobile(){
		var mobile = $("#mobile").val();
		var code = $("#code").val();
		$.ajax({
			url:"smemberwx!validMobile.asp",
			type:"post",
			data:{"mobile":mobile,"code":code,"flag":1},
			success:function(res){//绑定手机成功
				var json = JSON.parse(res); 
			    if(json.success){
			    	alert(json.message);
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