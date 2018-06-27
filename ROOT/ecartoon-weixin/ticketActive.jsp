<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
     String path = request.getContextPath();
     String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<base href="<%=basePath%>" >
<title>激活</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport"
	content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
<link href="ecartoon-weixin/css/mui.min.css" rel="stylesheet" />
<style type="text/css">
body{
	background-color:#EFEFEF;
}

#box{
	width:98%;
	height:200px;
	margin:0 auto;
	background-color:rgb(253,253,253);
	border-radius:3%;
	text-align: center;
}

#box > img{
	width:40px;
	height:30px;
	margin-top:30px;
}

#box > p{
	margin:0;
	margin-top:30px;
}

#box > input{
	margin-top:30px;
	background-color: transparent;
  border : 0;
  outline:none;
  border-bottom: 1px solid #EFEFEF;
  height: 22px;
  font-size: 14px;
  width:250px;
  text-align:center;
}

footer {
	height: 44px;
	text-align: center;
	line-height: 44px;
	margin: 10px 10px;
}

input[type="submit"] {
	width: 100%;
	height: 100%;
	background: #FF4401;
	border: none;
	color:#FFF;
	border-radius:2px;
}
</style>
</head>
<body>
	<div id="box">
		<img src="ecartoon-weixin/img/activetion-code.png">
		<p><b>激活码</b></p>
		<input type="number" id="code" placeholder="请输入您的激活码" />
	</div>
	<footer>
		<input type="submit" onclick="activeTicket()" id="footer" value="激活" /> 
	</footer>
	<script src="ecartoon-weixin/js/jquery.min.js" ></script>
	<script src="ecartoon-weixin/js/mui.min.js"></script>
	<script type="text/javascript">
		function activeTicket(){
			$.ajax({
				url:"eaccountswx!activeTicket.asp",
				type:"post",
				data:{activeCode:$("#code").val()},
				dataType:"json",
				success:function(res){
					mui.alert(res.message,"提示","确定",function(){
						if(res.success){
							location.href = "eaccountswx!ticketAll.asp";
						}
					});
				},
				error:function(e){
					//alert(JSON.stringify(e));
				}
			});
		}
	</script>
</body>
</html>