<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport"
	content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
<title>我的优惠券</title>
<link href="ecartoon-weixin/css/mui.min.css" rel="stylesheet"/>
<script type="text/javascript" src="ecartoon-weixin/js/vue.min.js"></script>
<style>
 footer {
	height: 44px;
	text-align: center;
	line-height: 44px;
	position: fixed;
	padding: 0 10px;
	bottom: 10px;
	width: 100%;
}

input[type="submit"] {
	width: 100%;
	height: 100%;
	background: #FF4401;
	border: none;
}
</style>
</head>
<body style="overflow: scroll;">
	<div id="app" style="overflow: scroll;">
		<div class="mui-card" v-for="v in ticketList">
			<a href="javascript:void(0)" style="text-decoration:none;">
				<!--页眉，放置标题-->
				<div class="mui-card-header mui-media-body header" style="position: relative;">
					<div style="height:125px;">
					   <p><h3>{{v.name}}</h3></p>
					   <p>使用时间:{{v.becomeDate}} 至  {{v.endDate}}</p>
					   <p>适用范围:{{v.applyRange}}</p>
					</div>
					<span style="color:#00CCFF;font-size:35px;position:absolute;right:5px;bottom:10px;">￥{{v.price}}</span>
				</div>
				<!--内容区-->
				<div class="mui-card-content"></div>
				<!--页脚，放置补充信息或支持的操作-->
				<!-- <div class="mui-card-footer footer"><span style="display:block;margin:0 auto;">立即使用</span></div> -->
			</a>
		</div>
		<div style="height:50px;"></div>
	</div>
	<footer> 
		<input type="submit" onclick="location.href='ecartoon-weixin/ticketActive.jsp'" name="" id="footer" value="激活" /> 
	</footer>
	<script type="text/javascript">
			var data = ${data == null?0:data};
			new Vue({
				el:"#app",
				data:{
					ticketList:data.tickets
				}
			});
	</script>
</body>
</html>