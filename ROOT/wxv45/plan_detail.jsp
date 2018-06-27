<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!doctype html>
<html>
<head>
<meta charset="UTF-8">
<title></title>
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
<link href="${pageContext.request.contextPath }/eg/css/mui.min.css" rel="stylesheet" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/eg/css/app.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/eg/css/mui.picker.min.css" />
<style>
.fright {
	float: right;
}

.fleft {
	float: left;
}

.red {
	color: red
}

.cgrey {
	color: #a6a2a6
}

.corange {
	color: #fb8c00
}

.cgreen {
	color: green;
}

.font7 {
	font-weight: 700;
}

.font12 {
	font-size: 12px;
}

.header-img {
	width: 100%;
	height: 200px;
	background: url(${pageContext.request.contextPath }/wxv45/images/banner.png) scroll center center/100%
		auto;
	box-sizing: border-box;
	position: relative
}

.header-text {
	width: 100%;
	height: 50px;
	line-height: 25px;
	padding: 0 10px;
	font-weight: 700;
	position: absolute;
	bottom: 0;
	box-sizing: border-box;
	overflow: hidden;
	overflow: hidden;
	text-overflow: ellipsis;
	display: -webkit-box;
	-webkit-line-clamp: 2;
	-webkit-box-orient: vertical;
	font-size: 15px;
	color: white;
	text-overflow: ellipsis;
}

.content-say {
	width: 100%;
	box-sizing: border-box;
}

.content-bottombox {
	box-sizing: border-box;
	width: 100%;
	height: 30px;
	line-height: 30px;
	font-size: 14px;
	padding: 0 10px;
	border-bottom: 1px solid #eaeaea;
	background: white;
}

.jtnr {
	width: 100%;
	padding: 5px 10px;
	margin-top: 10px;
	background: white;
	font-size: 13px;
}

.fright {
	float: right;
}

.fleft {
	float: left;
}

.red {
	color: red
}

.cb {
	color: #1e1e1e;
}

.cG {
	color: #999999
}

.corange {
	color: #ff4401
}

.cgreen {
	color: green;
}

.cW {
	color: white
}

.font7 {
	font-weight: 700;
}

.font12 {
	font-size: 12px;
}

.font13 {
	font-size: 13px;
}

.font15 {
	font-size: 15px;
}

.font16 {
	font-size: 16px;
}

.f18 {
	font-size: 18px;
}

.fright {
	float: right;
}

.tcenter {
	text-align: center;
}

.clear {
	zoom: 1;
}
/*==for IE6/7 Maxthon2==*/
.clear :after {
	clear: both;
	content: '.';
	display: block;
	width: 0;
	height: 0;
	visibility: hidden;
}

.colorR {
	color: #ff4401;
}

.bgW {
	background: white;
}

.border-radiu8 {
	border: 1px solid #ff4401;
	border-radius: 8px;
	padding: 0px 5px;
	color: #FF4401;
}

.header-a {
	width: 100%;
	overflow: hidden;
}

.header-div {
	width: 100%;
	height: 100%;
	position: relative;
	box-sizing: border-box;
	overflow: hidden;
}

.header-img {
	width: 120%;
	/*高斯模糊*/
	filter: url(blur.svg#blur);
	/* FireFox, Chrome, Opera */
	-webkit-filter: blur(13px) brightness(50%);
	-moz-filter: blur(3px) brightness(50%);
	-o-filter: blur(3px) brightness(50%);
	-ms-filter: blur(3px) brightness(50%);
	filter: blur(3px) brightness(50%);
	filter: progid:DXImageTransform.Microsoft.Blur(PixelRadius=3, MakeShadow=false);
	background:rgba(1,1,1,0.5);
	/* IE6~IE9 */
}

.show_img {
	height: 100%;
	width: 100%;
	position: absolute;
	top: 0;
	display: -webkit-box;
	-webkit-box-orient: vertical;
	-webkit-box-pack: center;
}

.mui-table-view .mui-media-object {
	line-height: 80px;
	max-width: 106px;
	height: 80px;
}

.mui-table-view-cell {
	position: relative;
	overflow: hidden;
	padding: 10px 10px;
	-webkit-touch-callout: none;
}

.mui-table-view-cell>a:not(
.mui-btn
)
{
position






:



 



relative






;
display






:



 



block






;
overflow






:

hidden






;
margin






:



 



-10
px



 



-10
px






;
padding






:



 



inherit






;
white-space






:



 



nowrap






;
text-overflow






:



 



ellipsis






;
color






:



 



inherit






;
}
.mui-ellipsis {
	line-height: 26px;
	color: white
}

.mui-table-view {
	background: rgba(0, 0, 0, 0);
	border: none
}

.mui-table-view:after, .mui-table-view:before {
	position: absolute;
	right: 0;
	bottom: 0;
	left: 0;
	height: 1px;
	content: '';
	-webkit-transform: scaleY(.5);
	transform: scaleY(.5);
	background: rgba(0, 0, 0, 0);
}

.mui-table-view-cell
>
a


:not

 

(
.mui-btn

 

)
{
position


:

 

relative


;
display


:

 

block


;
overflow


:

 

hidden


;
margin


:

 

-10
px

 

-10
px


;
padding


:

 

inherit


;
white-space


:

 

nowrap


;
text-overflow


:

 

ellipsis


;
color


:

 

inherit


;
}
.mui-pull-right {
	margin-right: 0px;
	color: #999999;
	font-size: 13px;
}

.cr {
	color: #ff4401;
	font-size: 13px;
}

.mui-table-view-cell>a:not(.mui-btn) {
    position: relative;
    display: block;
    overflow: hidden;
    margin: -11px -5px;
    padding: inherit;
    white-space: nowrap;
    text-overflow: ellipsis;
    color: inherit;
    font-size:14px
}

</style>
</head>
<body>
	<script type="text/javascript">
		window.onload = function() {
			var today = new Date()
			var y = today.getFullYear()
			var M = today.getMonth() + 1
			var d = today.getDate()
			M = jia(M)
			d = jia(d)
			function jia(i) {
				if (i < 10) {
					i = "0" + i;
				}
				return i;
			}
			document.getElementById('result').innerHTML = y + "-" + M + "-" + d

		}
	</script>
	<form action="" method="post">
		<div class="mui-content" style="background: white;">
			<div id="planDetail">
				<footer class="mui-bar mui-bar-footer font-white"
					style="border: none; background: white; padding: 0 0 0 10px;">
					<div class="font12" style="line-height: 44px;">
						选择开始时间 <span id="result" class="ui-alert corange"
							style="text-indent: 2em; display: inline-block;">2017-06-16</span>
						<button id="demo4"
							data-options="{&quot;type&quot;:&quot;date&quot;}"
							class="btn mui-btn mui-btn-block"
							style="width: 80px; position: absolute; left: 5.5em; top: 0em; opacity: 0; height: 100%;"></button>
						<a v-if="unitPrice == 0" href=""
							style="width: 100px; color: white; background: #FF4401; display: inline-block; height: 100%; float: right; text-align: center; border-radius: 0px;">
							下载
						</a>
						<a v-if="unitPrice != 0" href="mcoursewxv45!orderInfo.asp"
							style="width: 100px; color: white; background: #FF4401; display: inline-block; height: 100%; float: right; text-align: center; border-radius: 0px;">
							购买
						</a>
					</div>
				</footer>
				<div class="header-a">
					<div class="header-div ">
						<img src="${pageContext.request.contextPath }/wxv45/images/banner.png" class="header-img " />
						<div class="show_img">
							<ul class="mui-table-view">
								<li class="mui-table-view-cell mui-media">
									<a href="javascript:;"style="margin:0px">
										<img class="mui-media-object mui-pull-left imgf" src="${pageContext.request.contextPath }/wxv45/images/banner.png" >
										<div class="mui-media-body">
											<p class="mui-ellipsis font15">{{ planName }}</p>
											<p class="mui-ellipsis font13">发布人：{{ memberName }}</p>
											<p class="mui-ellipsis font13">下载数量：{{ saleout }} 次</p>
										</div>
									</a>
								</li>

							</ul>
						</div>
					</div>
				</div>
				<ul class="mui-table-view">
					<li class="mui-table-view-cell">
						<a>
							计划类型
							<span class="mui-pull-right"style="font-size:13px">
							{{  planType == "A" ? 
							 		'瘦身减重'
							 	:
							 		 planType  == "B" ?
							 			'健美增肌'
							 		:
							 			 planType  == "C" ?
							 				'运动康复'
							 			:
							 				'提高运动表现'
							 }}
							 </span>
						</a>
					</li>
					<li class="mui-table-view-cell">
						<a>
							适用对象
							<span class="mui-pull-right"style="font-size:13px">
								 {{
									  applyObject == "A" ? 
											'初级' 
										: 
											applyObject  == "B" ?
												'中级'
											:
												'高级'  
								  }}
							</span>
						</a>
					</li>
					<li class="mui-table-view-cell">
						<a>
							适用场景
							<span id="scenes" style="display:none">{{ scene.split(',') }}</span>
							<span id="reflash" class="mui-pull-right"style="font-size:13px">1</span>
						</a>
					</li>
					<li class="mui-table-view-cell">
						<a>
							所需器材
							<span class="mui-pull-right"style="font-size:13px">{{ apparatuses }}</span>
						</a>
					</li>
					<li class="mui-table-view-cell">
						<a>
							计划内容
							<span class="mui-pull-right"style="font-size:13px">{{ details }}</span>
						</a>
					</li>
					<li class="mui-table-view-cell">
						<a class="">
							价格
							<span class="mui-pull-right cr"style="font-size:13px">{{ unitPrice }}</span>
						</a>
					</li>
				</ul>
				<div style="border-top: 1px solid #f3f3f3; padding: 11px 15px;font-size:14px">
					计划简介
					<p style="padding: 1px 15px;font-size:13px">{{ briefing }}</p>
				</div>
			</div>
		</div>
	</form>
	<input id="pageInfo" type="hidden" value="<s:property value='#request.pageInfo'/>" />
</body>
<script src="${pageContext.request.contextPath }/eg/js/mui.min.js"></script>
<script src="${pageContext.request.contextPath }/eg/js/mui.picker.min.js"></script>
<script src="${pageContext.request.contextPath }/eg/js/jquery-2.1.4.js" type="text/javascript" charset="utf-8"></script>
<script src="https://cdn.bootcss.com/vue/2.2.2/vue.min.js"></script>

<script type="text/javascript">
	$(function() {
		var pageInfoObj = jQuery.parseJSON($("#pageInfo").val());
		
		new Vue({
			  el: '#planDetail',
			  data: pageInfoObj.item
		})
		
		var scenes = jQuery.parseJSON($("#scenes").html());
		
		var sceneStr = ""
		
		for (var i = 0; i < scenes.length; i++) {
			if (scenes[i] == "A") {
				sceneStr +="办公室,";
			} else if (scenes[i] == "B") {
				sceneStr +="健身房,";
			} else if (scenes[i] == "C") {
				sceneStr +="家庭,";
			} else if (scenes[i] == "D") {
				sceneStr +="户外,";
			}
		}
		sceneStr = sceneStr.substring(0, sceneStr.length - 1);
		$("#reflash").html(sceneStr)
	})
</script>

<script>
	mui.init();

	//处理头部的图片高度
	var headeraheight = $(".header-a").width() / 2;
	$('.header-a').css("height", headeraheight);
	var imgwidth = $('.header-div').width();
	var imgheight = imgwidth / 2 * 1;
	$('.header-img').css('height', imgheight);

	(function($) {
		$.init();
		var result = $('#result')[0];
		var btns = $('.btn');
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
</html>