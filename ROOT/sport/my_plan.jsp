<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>
<head>
<title>我的计划详情</title>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no">
<link rel="stylesheet" href="sport/css/mui.min.css" />
<link rel="stylesheet" href="sport/css/weui.min.css">
<link rel="stylesheet" href="sport/css/jquery-weui.css">
<link rel="stylesheet" href="sport/css/demos.css">
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
/*日历改变样式*/
.toolbar {
	background: white;
	color: #ff4401;
}

.picker-calendar-week-days {
	background: white;
}

i.icon.icon-next {
	background: url(sport/img/arrowR.png) no-repeat scroll 1px 1px/14px 14px;
}

i.icon.icon-prev {
	background: url(sport/img/arrowL.png) no-repeat scroll 1px 1px/14px 14px;
}

.picker-calendar-day.picker-calendar-day-selected span {
	background: #FF4401;
	color: #fff;
}

.mui-table-view:before {
	position: absolute;
	right: 0;
	left: 0;
	height: 1px;
	content: '';
	-webkit-transform: scaleY(.5);
	transform: scaleY(.5);
	background: rgba(0, 0, 0, 0);
	top: -1px;
}

.weui-cells:before {
	top: 0;
	border-top: none;
	-webkit-transform-origin: 0 0;
	transform-origin: 0 0;
	-webkit-transform: scaleY(.5);
	transform: scaleY(.5);
}

.weui-cells {
	margin-top: 10px !important;
}

.mui-table-view:last-child::after {
	position: absolute;
	right: 0;
	bottom: 0;
	left: 0;
	height: 1px;
	content: '';
	-webkit-transform: scaleY(.5);
	transform: scaleY(.5);
	background-color: rgba(0, 0, 0, 0);
}

.footer {
	height: 44px;
	line-height: 44px;
	background: #FF4401;
	text-align: center;
	position: fixed;
	bottom: 0;
	width: 100%;
}

.footer a {
	color: white;
}
</style>
</head>
<body ontouchstart>
	<div id="course">
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
		<ul class="mui-table-view" v-if="course.id != null">
			<!-- 计划详情开始 -->
			<li class="mui-table-view-cell">
				<a class="">
					课程名称：
					<span class="mui-pull-right" style="color: #999; font-size: 13px"> {{ course.name }}</span>
				</a>
			</li>
			<li class="mui-table-view-cell">
				<a class="">
					课程时间：
					<span class="mui-pull-right" style="color: #999; font-size: 13px"> {{ course.startTime }}</span>
				</a>
			</li>
			<li class="mui-table-view-cell">
				<a class="">
					课程地点：
					<span class="mui-pull-right" style="color: #999; font-size: 13px"> {{ course.place }} </span>
				</a>
			</li>
			<li class="mui-table-view-cell">
				<a class="">
					课程备注：
					<p style="color: #999; font-size: 13px; line-height: 21px; white-space: pre-line; word-break: normal !important; word-wrap: break-word !important; box-sizing: border-box; overflow: hidden;">
						{{ course.memo == null ? "暂无备注":course.memo }}</p>
				</a>
			</li>

		</ul>
		<div class="footer" v-if="course.id">
			<span id="planId" style="display: none">{{ course.id }}</span>
			<a href="javascript:void(0)" onclick="showPlanDetail()">查看训练计划</a>
		</div>
	</div>
	<script src="sport/js/jquery-2.1.1.min.js"></script>
	<script src="sport/js/jquery-weui.js"></script>
	<script src="sport/js/fastclick.js"></script>
	<script src="sport/js/vue.min.js"></script>
	<script type="text/javascript">
		var res = $(res==null?0:res);
		var course = new Vue({
			el:"#course",
			data:{
				course:res
			}
		});
		
		$(function() {
			FastClick.attach(document.body);
			
			//切换日期
			$(".picker-calendar-day").click(function(){
				var date = $(this).attr("data-year")+"-"+(parseInt($(this).attr("data-month"))+1)
							+"-"+$(this).attr("data-day");
				
				$.ajax({
					url:"scoursewx!myPlan.asp",
					type:"post",
					data:{"planDate":date,"ajax":1},
					dataType:"json",
					success:function(res){
						if(res.id){
							course.course = res;
						}else{
							course.course = 0;
						}
					}
				});
			});
		});
		//查看组次
		function showPlanDetail() {
			var id = $("#planId").html();
			window.location.href="scoursewx!findPlanByCourse.asp?id=" + id; 
		}
	</script>
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