<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="UTF-8">
<title>训练计划</title>
<meta name="viewport"
	content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
<link href="../css/mui.min.css" rel="stylesheet" />
<style>
.mui-table-view .mui-media-object {
	max-width: 80px !important;
	height: 60px;
}

.user_img {
	width: 120px !important;
	height: 60px;
	position: relative;
}

.free {
	width: 20px;
	height: 20px;
	position: absolute;
	top: 11px;
	left: 15px;
}

.bt {
	font-size: 12px;
	margin: 10px 0;
}

.time {
	font-size: 12px;
}
</style>
</head>

<body>
	<script src="../js/mui.min.js"></script>
	<script type="text/javascript">
		mui.init()
	</script>
	<ul class="mui-table-view ul2" style="margin: 2px;">
		<li class="mui-table-view-cell mui-media"><img
			class="mui-media-object mui-pull-left user_img"
			src="../images/shuijiao.jpg" />

			<div class="mui-media-body">
				<div class="bt">器械训练</div>
				<p class="time">引体向上 每次8个 每次1分钟</p>

			</div></li>
		<li class="mui-table-view-cell mui-media"><img
			class="mui-media-object mui-pull-left user_img"
			src="../images/shuijiao.jpg" />
			<div class="mui-media-body">
				<div class="bt">器械训练 12:09</div>
				<p class="time">引体向上 每次8个 每次1分钟</p>

			</div></li>
		<li class="mui-table-view-cell mui-media"><img
			class="mui-media-object mui-pull-left user_img"
			src="../images/shuijiao.jpg" />
			<div class="mui-media-body">
				<div class="bt">器械训练</div>
				<p class="time">引体向上 每次8个 每次1分钟/p>
			</div></li>
	</ul>
</body>
</html>