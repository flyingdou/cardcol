<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!doctype html>
<html>
<head>
<meta charset="UTF-8">
<title>填写预约信息</title>
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
<link href="sport/css/mui.min.css" rel="stylesheet" />	
<link rel="stylesheet" href="sport/css/weui.min.css">
<link rel="stylesheet" href="sport/css/jquery-weui.css">
<link rel="stylesheet" href="sport/css/demos.css">
<style>
.mui-pull-right {
	color: #999999;
}

textarea::-webkit-input-placeholder {
	color: #999999;
	font-size: 13px;
}
.input_css{
	height: 21px!important;
	line-height: 21px!important;
	color:#999!important;
	margin: 0!important;
	padding: 0!important;
	text-align: right!important;
	border:none!important;
	outline:none!important;
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
<body>
	<div class="mui-content" id="app">
		<ul class="mui-table-view">
			<li class="mui-table-view-cell">
				<a href="javascript:void(0)">
					课程名称
					<span class="mui-pull-right" id="selector" courseId="" style="cursor:pointer;">点击选择课程</span>
				</a>
			</li>
			<li class="mui-table-view-cell">
				<a href="javascript:void(0)">
					开始时间
					<span class="mui-pull-right" id="productTime">
						<input type="text" id="startTime" class="input_css" placeholder="请输入开始时间"/>
					</span>
				</a>
			</li>
			<li class="mui-table-view-cell">
				<a href="javascript:void(0)">
					结束时间
					<span class="mui-pull-right" id="productTime">
						<input type="text" id="endTime" class="input_css" placeholder="请输入结束时间"/>
					</span>
				</a>
			</li>
			<li class="mui-table-view-cell">
				<a href="javascript:void(0)">
					课程地点
					<span class="mui-pull-right">
						<input type="text" id="place" class="input_css" placeholder="请输入课程地点"/>
					</span>
				</a>
			</li>
		</ul>
		<div id="popover" class="mui-popover" style="left: 15%;">
			<div style="height: 40px;line-height: 40px;text-align: center;">
				选择商品
			</div>
			<ul id="product_list" class="mui-table-view" style="max-height: 200px;overflow-y: scroll;margin-bottom: 50px;">
				<li class="mui-table-view-cell" id="product_li">
					<label class="productName"></label>
					<input type="radio" style="float:right"/>
				</li>
			</ul>
			<div style="position:absolute;bottom: 0;left: 0;height: 40px;width: 100%;line-height: 40px;border-top:1px solid #999">
			<div id="false"style="width: 50%;float: left;text-align: center;border-right: 1px solid #999;">取消</div>
				<div id="true" style="width: 50%;float: left;text-align: center;color:red">确定</div>
				<div style="clear: both;"></div>
			</div>
		</div>
	</div>
	<div class="footer">
		<a id="sub" href="javascript:toMake()" >预约</a>
	</div>
	<script src="sport/js/mui.min.js"></script>
	<script src="sport/js/mui.picker.min.js"></script>
	<script src="sport/js/vue.min.js"></script>
	<script src="sport/js/jquery.js"></script>
	<script src="js/utils.elisa.js"></script>
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
			
	document.getElementById("selector").addEventListener('tap', function() {
		mui('#popover').popover('toggle'); // 将id为list的元素放在想要弹出的位置
	});
	document.getElementById("true").addEventListener('tap', function() {
		var index = $(".productSelector").index($(".productSelector:checked"));
		$("#selector").attr("courseId",$(".productSelector").val())
			.html($(".productName").eq(index).html());
		mui('#popover').popover('hide'); // 将id为list的元素放在想要弹出的位置
	});
	document.getElementById("false").addEventListener('tap', function() {
		mui('#popover').popover('hide'); // 将id为list的元素放在想要弹出的位置
	});
	
	var data = ${products != null?products:0};
	var $li = $("#product_li");
	var $elem = 0;
	var json = 0;
	data.forEach(function(item,i){
		$elem = $li.clone();
		$elem.find("label").html(item.name);
		$elem.find("input[type='radio']").val(item.id)
				.attr({"name":"productSelector","class":"productSelector"});
		if(i>0){
			$("#product_list").append($elem);
		}else{
			$("#product_list").html($elem);
		}
	});
	if(data.member){
		$(".footer").hide();
	}else if(data.member.role){
		$(".footer").hide();
	}else if(data.member.role != "M"){
		$(".footer").hide();
	}
	
	function toMake(){
		var json = new JsonObject();
		json.put("id",$("#courseId").val())
			.put("startTime",$("startTime").val())
			.put("endTime",$("endTime").val())
			.put("place",$("place").val());
		$.ajax({
			url:"scoursewx!make.asp",
			data:{"json":json.toString()},
			type:"post",
			dataType:"json",
			success:function(res){
				if(res.success){
					location.href="scoursewx!products.asp"
				}else{
					alert(res.msg);
				}
			}
		});
	}
	</script>
</body>
</html>