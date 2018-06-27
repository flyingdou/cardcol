<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!doctype html>
<html>
<head>
<meta charset="UTF-8">
<title></title>
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
<link href="sport/css/mui.min.css" rel="stylesheet" />
		
<style>
.mui-pull-right {
	color: #999999;
}

textarea::-webkit-input-placeholder {
	color: #999999;
	font-size: 13px;
}
</style>
</head>
<body>
	<form action="scoursewx!cancelMake.asp" id="course">
	<div class="mui-content">
		<ul class="mui-table-view">
			<li class="mui-table-view-cell">
				<a class="">
					课程名称
					<span class="mui-pull-right">{{course.name}}</span>
					<input type="hidden" name="id" :value="course.id" />
				</a>
			</li>
			<li class="mui-table-view-cell">
				<a class="">
					课程地点
					<span class="mui-pull-right">{{course.place}}</span>
				</a>
			</li>
			<li class="mui-table-view-cell">
				<a class="">
					课程时间
					<span class="mui-pull-right">{{course.planDate}} {{course.startTime}} - {{course.endTime}}</span>
				</a>
			</li>
		</ul>
		<div style="width: 100%; padding: 10px; box-sizing: border-box;" v-if="isShow==1">
			<textarea style="" name="" rows="" cols="" placeholder="输入取消原因"></textarea>
		</div>
		<div class="footer">
			<a id="sub" href="javascript:cancelMake()" v-if="isShow==1">取消预约</a>
		</div>
	</div>
	</form>
	<script src="sport/js/mui.min.js"></script>
	<script src="sport/js/mui.picker.min.js"></script>
	<script src="sport/js/vue.min.js"></script>
	<script type="text/javascript">
		 mui.init();
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
	<script type="text/javascript">
		var data = ${res == null ? 0 : res};
		var member = ${member == null ? 0 : member.id}
		var bool = 0;
		if(data.member == member){
			bool = 1;
		}
		var course = new Vue({
			el:"#course",
			data:{
				course:data,
				isShow:bool
			}
		});
		function cancelMake(){
			document.getElementById("#course").submit();
		}
	</script>
</body>
</html>