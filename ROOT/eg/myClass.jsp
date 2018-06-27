<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>
<head>
<title>我的课程</title>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no">
<link rel="stylesheet" href="${pageContext.request.contextPath}/eg/css/mui.min.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/eg/css/weui.min.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/eg/css/jquery-weui.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/eg/css/demos.css">
<style>
.mui-table-view {
	margin-top: 15px;
}
/*修改原图片的样式*/
.mui-table-view .mui-media-object {
	line-height: 42px;
	max-width: none;
	height: 60px;
}

.user_img {
	width: 60px;
	height: 60px;
	border-radius: 50%;
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
	margin: 5px 0;
	text-align: right;
}

.time {
	font-size: 12px;
	text-align: right;
}
</style>

</head>

<body ontouchstart>
	<!--日历表-->
	<div class="weui-cells weui-cells_form" style="display: none;">
		<div class="weui-cell">
			<div class="weui-cell__hd">
				<label for="date3" class="weui-label">日期</label>
			</div>
			<div class="weui-cell__bd">
				<input class="weui-input" id="date3" type="text">
			</div>
		</div>
	</div>
	<div id="inline-calendar"></div>
	<!--课程名称-->
	<div class="weui-cells" style="margin: 0;">
		<ul class="mui-table-view ul2" style="margin: 2px;">
			<!-- 我的课程列表开始 -->
			<s:iterator value="pageInfo.items"> 
				<li class="mui-table-view-cell mui-media">
					<img class="mui-media-object mui-pull-left user_img" src="../images/shuijiao.jpg" />
					<div class="mui-media-body">
						<div class="bt"><s:property value="name"/></div>
						<p class="time"><s:property value="startTime"/>-<s:property value="endTime"/></p>
					</div>
				</li>
			</s:iterator>
			<!-- 我的课程列表结束 -->
		</ul>
	</div>
	<script src="${pageContext.request.contextPath}/eg/js/jquery-2.1.4.js"></script>
	<script src="${pageContext.request.contextPath}/eg/js/fastclick.js"></script>
	<script>
		$(function() {
			FastClick.attach(document.body);
		});
	</script>
	<script src="${pageContext.request.contextPath}/eg/js/jquery-weui.js"></script>
	<script>
		$("#date").calendar({
			onChange : function(p, values, displayValues) {
				console.log(values, displayValues);
			}
		});
		$("#date2").calendar({
			value : [ '2016-12-12' ],
			dateFormat : 'yyyy年mm月dd日' // 自定义格式的时候，不能通过 input 的value属性赋值 '2016年12月12日' 来定义初始值，这样会导致无法解析初始值而报错。只能通过js中设置 value 的形式来赋值，并且需要按标准形式赋值（yyyy-mm-dd 或者时间戳)
		});
		$("#date-multiple").calendar({
			multiple : true,
			onChange : function(p, values, displayValues) {
				console.log(values, displayValues);
			}
		});
		$("#date3").calendar({
			container : "#inline-calendar" //顶部显示选择日期的值
		});
	</script>
</body>
</html>
