<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>团体课程预约</title>
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
<meta name="apple-mobile-web-app-capable" content="yes">
<link rel="stylesheet" href="${pageContext.request.contextPath}/eg/css/mui.min.css" />

<style type="text/css">
/*字体为微软雅黑*/
body {
	font-family: "微软雅黑";
}

.mr {
	margin-right: 30px;
}

.mr1 {
	text-align: right !important;
}
/*整体与顶部的距离为20px*/
.mui-table-view {
	margin-top: 20px;
}
/*定位图标*/
.mui-icon {
	font-size: 20px;
}
/*底部样式*/
.fu {
	line-height: 45px;
	width: 100%;
	float: left;
	text-align: center;
	font-size: 13px;
	background-color: #FE9E3A;
	color: #ffffff;
}
/*去掉底部左右两边的padding值*/
.mui-bar {
	padding-right: 0px;
	padding-left: 0px;
}
</style>
</head>
<body>
	<footer class="mui-bar mui-bar-footer">
		<span class="mui-col-sm-12 fu" id="go">预约</span>
	</footer>
	<div class="mui-content">
		<div class="mui-scroll-wrapper">
			<div class="mui-scroll">
				<ul class="mui-table-view">
					<s:iterator value="pageInfo.items">
						<li class="mui-table-view-cell">
							<span class="mui-pull-left mr">课程名称：</span>
							<p class="mr1"><s:property value="name"/></p>
						</li>
	
						<li class="mui-table-view-cell">
							<span class="mui-pull-left mr">上课时间：</span>
							<p class="mr1"><s:property value="startTime"/>-<s:property value="endTime"/></p>
						</li>
						<li class="mui-table-view-cell">
							<span class="mui-pull-left mr">课程地点：</span>
							<p class="mr1"><s:property value="place"/></p>
	
						</li>
						<li class="mui-table-view-cell">
							<span class="mui-pull-left mr">人&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;数：</span>
							<p class="mr1"><s:property value="num"/></p>
						</li>
					</s:iterator>
				</ul>
			</div>
		</div>

	</div>
</body>
<script src="${pageContext.request.contextPath}/eg/js/mui.min.js"></script>
<script type="text/javascript">
	mui('.mui-scroll-wrapper').scroll({
		deceleration : 0.0005
	})
</script>
</html>
