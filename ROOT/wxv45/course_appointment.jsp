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
<link rel="stylesheet" href="${pageContext.request.contextPath }/eg/css/mui.min.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath }/eg/css/weui.min.css">
<link rel="stylesheet" href="${pageContext.request.contextPath }/eg/css/jquery-weui.css">
<link rel="stylesheet" href="${pageContext.request.contextPath }/eg/css/demos.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/eg/css/mui.picker.min.css" />


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
	background: url(images/arrowR.png) no-repeat scroll 1px 1px/14px 14px;
}

i.icon.icon-prev {
	background: url(images/arrowL.png) no-repeat scroll 1px 1px/14px 14px;
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
	<!--课程名称-->

	<ul class="mui-table-view">
		<s:iterator value="pageInfo.items">
			<li class="mui-table-view-cell">
				<a class="">
					课程名称：
					<%-- <span class="mui-pull-right" style="color: #999; font-size: 13px"> <s:property value="courseInfo.name" /></span> --%>
					<input class="mui-pull-right" style="color: #999; font-size: 13px;border:none;outline:none;background:rgba(0,0,0,0);height:21px;line-height:23px;margin:0;padding:0;text-align:right" value="<s:property value="courseInfo.name" />" />
				</a>
			</li>
			
			<li class="mui-table-view-cell list"style="height:37px;box-sizing: border-box;">
								
								<span class="xm">开始时间</span>
								<span class="mui-pull-right zt" id="goal1">
																	<span id='result' class="ui-alert"style="position:relative!important"></span>
								</span>
								<div style="position: absolute;width: 100px;z-index:333;padding: 0;opacity: 0;"id='demo41' data-options='{"type":"time"}' class="btn mui-btn mui-btn-block btn1">1111111</div>
						</li>
						<li class="mui-table-view-cell list"style="height:37px;box-sizing: border-box;">
							
								<span class="xm">结束时间</span>
								<span class="mui-pull-right zt" id="goal"style="position:relative">	
									<span id='result1' class="ui-alert"style="position:relative!important"></span>
								</span>
									
										<div style="position: absolute;width: 100px;z-index:333;padding: 0;opacity: 0;"id='demo4' data-options='{"type":"time"}' class="btn mui-btn mui-btn-block btn2">1111111</div>
						</li>
			
			
			
			
			
			
			
			<%-- <li class="mui-table-view-cell">
				<a class="">
					开始时间：
					<span class="mui-pull-right" style="color: #999; font-size: 13px"> <s:property value="startTime" /> </span>
					<input class="mui-pull-right" style="color: #999; font-size: 13px;border:none;outline:none;background:rgba(0,0,0,0);height:21px;line-height:23px;margin:0;padding:0;text-align:right" value="<s:property value="startTime" />" />
				</a>
			</li>
			<li class="mui-table-view-cell">
				<a class="">
					结束时间：
					<span class="mui-pull-right" style="color: #999; font-size: 13px"> <s:property value="endTime" /> </span>
					<input class="mui-pull-right" style="color: #999; font-size: 13px;border:none;outline:none;background:rgba(0,0,0,0);height:21px;line-height:23px;margin:0;padding:0;text-align:right" value="<s:property value="endTime" />" />
				</a>
			</li> --%>
			<li class="mui-table-view-cell">
				<a class="#">
					课程地点：
					<%-- <span class="mui-pull-right" style="color: #999; font-size: 13px"><s:property value="place" /> </span> --%>
					<input class="mui-pull-right" style="color: #999; font-size: 13px;border:none;outline:none;background:rgba(0,0,0,0);height:21px;line-height:23px;margin:0;padding:0;text-align:right" value="<s:property value="place" />" />
				</a>
			</li>
		<div class="footer">
			<a href="mmemberwxv45!buyPackage.asp?packageId=679">预约课程</a>
		</div>
		</s:iterator>
	</ul>

	<script src="${pageContext.request.contextPath }/eg/js/jquery-2.1.4.js"></script>
	<script src="${pageContext.request.contextPath }/eg/js/fastclick.js"></script>
	<script>
		$(function() {
			FastClick.attach(document.body);
		});
	</script>
	<script src="${pageContext.request.contextPath }/eg/js/jquery-weui.js"></script>
	<script src="${pageContext.request.contextPath }/eg/js/mui.min.js"></script>
	<script src="${pageContext.request.contextPath }/eg/js/mui.picker.min.js"></script>
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
		
		
		/* mui.init()； */
		(function($) {
			$.init();
			var result = $('#result')[0];
			var btns = $('.btn1');
			btns.each(function(i, btn) {
				btn.addEventListener('tap', function() {
					var optionsJson = this.getAttribute('data-options') || '{}';
					var options = JSON.parse(optionsJson);
					var id = this.getAttribute('id');
					/*
					 * 首次显示时实例化组件
					 * 示例为了简洁，将 options 放在了按钮的 dom 上
					 * 也可以直接通过代码声明 optinos 用于实例化 DtPicker
					 */
					var picker = new $.DtPicker(options);
					picker.show(function(rs) {
						/*
						 * rs.value 拼合后的 value
						 * rs.text 拼合后的 text
						 * rs.y 年，可以通过 rs.y.vaue 和 rs.y.text 获取值和文本
						 * rs.m 月，用法同年
						 * rs.d 日，用法同年
						 * rs.h 时，用法同年
						 * rs.i 分（minutes 的第二个字母），用法同年
						 */
						result.innerText = rs.text;
						/* 
						 * 返回 false 可以阻止选择框的关闭
						 * return false;
						 */
						/*
						 * 释放组件资源，释放后将将不能再操作组件
						 * 通常情况下，不需要示放组件，new DtPicker(options) 后，可以一直使用。
						 * 当前示例，因为内容较多，如不进行资原释放，在某些设备上会较慢。
						 * 所以每次用完便立即调用 dispose 进行释放，下次用时再创建新实例。
						 */
						picker.dispose();
					});
				}, false);
			});
		})(mui);
		
			(function($) {
			$.init();
			var result = $('#result1')[0];
			var btns = $('.btn2');
			btns.each(function(i, btn) {
				btn.addEventListener('tap', function() {
					var optionsJson = this.getAttribute('data-options') || '{}';
					var options = JSON.parse(optionsJson);
					var id = this.getAttribute('id');
					/*
					 * 首次显示时实例化组件
					 * 示例为了简洁，将 options 放在了按钮的 dom 上
					 * 也可以直接通过代码声明 optinos 用于实例化 DtPicker
					 */
					var picker = new $.DtPicker(options);
					picker.show(function(rs) {
						/*
						 * rs.value 拼合后的 value
						 * rs.text 拼合后的 text
						 * rs.y 年，可以通过 rs.y.vaue 和 rs.y.text 获取值和文本
						 * rs.m 月，用法同年
						 * rs.d 日，用法同年
						 * rs.h 时，用法同年
						 * rs.i 分（minutes 的第二个字母），用法同年
						 */
						result.innerText = rs.text;
						/* 
						 * 返回 false 可以阻止选择框的关闭
						 * return false;
						 */
						/*
						 * 释放组件资源，释放后将将不能再操作组件
						 * 通常情况下，不需要示放组件，new DtPicker(options) 后，可以一直使用。
						 * 当前示例，因为内容较多，如不进行资原释放，在某些设备上会较慢。
						 * 所以每次用完便立即调用 dispose 进行释放，下次用时再创建新实例。
						 */
						picker.dispose();
					});
				}, false);
			});
		})(mui);
		
		
		
		
	</script>
	
	
	
	
	
	
	
	
</body>
</html>