<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() +  path +"/";
%>
<!doctype html>
<html>
<head>
<base href="<%=basePath%>">
<title>我的二维码</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
<div class="wraper" id="wraper">
	<img :src="qrcode.url"/>
</div>
<script src="ecartoon-weixin/js/vue.min.js"></script>
<script src="ecartoon-weixin/js/jquery.min.js"></script>
<script type="text/javascript">
	var vue = new Vue({
		el: "#wraper",
		data: {
			qrcode: {}
		},
		created: function(){
			$.post("http://www.ecartoon.com.cn/ewechatwx!createWechatQrcode.asp",{
					
				},function(res){
					vue.qrcode = JSON.parse(res);
			});
		}
	});
</script>
</body>
</html>