<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>训练计划</title>
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
<link href="${pageContext.request.contextPath}/eg/css/mui.min.css" rel="stylesheet" />
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
	<script src="${pageContext.request.contextPath}/eg/js/mui.min.js"></script>
	<script type="text/javascript">
		mui.init()
	</script>
	<ul class="mui-table-view ul2" style="margin: 2px;">
		<!-- 训练计划展现开始 -->
		<s:iterator value="pageInfo.items">
			<li class="mui-table-view-cell mui-media">
				<img class="mui-media-object mui-pull-left user_img" src="..<s:property value="actionImage" />" />
	
				<div class="mui-media-body">
					<div class="bt"><s:property value="actionName" /></div>
					<p class="time"><s:property value="groupTimes" /> 组, 每组 <s:property value="actionCount" /> 次, 重量 <s:property value="planWeight" /> 公斤</p>
				</div>
			</li>
		</s:iterator>	
		<!-- 训练计划展现结束 -->
	</ul>
</body>

</html>