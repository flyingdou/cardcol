<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":"+ request.getServerPort() + path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>详情</title>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
<meta name="apple-mobile-web-app-capable" content="yes">
<style type="text/css">
	html,body{
		width:100%;
		height:100%;
		margin:0;
		padding:0;
	}
	
	.wraper{
		padding:0 10px;
	}
	
</style>
</head>
<body>
	<div class="wraper" id="product" v-html="product.PROD_CONTENT"></div>
	<script src="ecartoon-weixin/js/vue.min.js"></script>
	<script type="text/javascript">
		var vue = new Vue({
			el:"#product",
			data:{
				product:0
			},
			mounted:function(){
				var param = location.href.split("?")[1].split("=")[1];
				var json = JSON.parse(decodeURI(param));
				this.product = json;
			}
		});
	</script>
</body>
</html>