<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!doctype html>
<html>
<head>
<meta charset="UTF-8">
<title>我的订单</title>
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
<link href="${pageContext.request.contextPath}/eg/css/mui.min.css" rel="stylesheet" />
<style type="text/css">
.colorR {
	color: #ff4401;
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

.mui-control-item {
	height: 45px !important;
	line-height: 45px !important;
	background: white;
	position: relative;
}

.mui-segmented-control.mui-segmented-control-inverted .mui-control-item
	{
	color: #999999
}

.control-item:after {
	content: "";
	position: absolute;
	width: 100%;
	height: 33%;
	top: 33%;
	left: -1px;
	border-right: 1px solid #f2f2f2;
}

.mui-slider-progress-bar {
	background: rgba(0, 0, 0, 0) !important;
	position: relative;
}

.mui-slider-progress-bar:after {
	content: "";
	position: absolute;
	width: 30%;
	height: 100%;
	background: #FF4401;
	left: 35%;
}

.mui-slider-indicator {
	outline: 2px solid #f2f2f2 !important;
}

.mui-slider-progress-bar {
	margin-bottom: 10px;
}

.p10 {
	padding: 10px 10px 5px 10px !important;
}

.p0 {
	padding-left: 0 !important;
}

.m0 {
	margin: -10px 0 !important;
}

.mui-table-view .mui-media-object {
	line-height: 72px;
	max-width: 72px;
	height: 72px;
}

.pd5 {
	padding-top: 5px;
}

.mui-table-view-cell:after {
	position: absolute;
	right: 0;
	bottom: 0;
	left: 0px;
	content: '';
	transform: scaleY(.5);
	background-color: rgba(0, 0, 0, 0);
}

.bg0 {
	background: rgba(0, 0, 0, 0);
}

.mt10 {
	margin-bottom: 10px;
}

.bgW {
	background: white;
}

.cG {
	color: #999999
}

.cB {
	color: #1e1e1e
}

.mui-segmented-control.mui-segmented-control-inverted .mui-control-item.mui-active
	{
	color: #1e1e1e;
	background: white;
}

.mui-slider .mui-segmented-control.mui-segmented-control-inverted ~.mui-slider-group .mui-slider-item
	{
	border: none;
}
</style>
</head>

<body>
	<div class="mui-content">
		<div class="mui-slider" id="slider">
			<!-- 订单详情 -->
			<div class="mui-slider-indicator mui-segmented-control mui-segmented-control-inverted">
				<a class="mui-control-item control-item cG mui-active" href="#item1">有效订单</a>
				<a class="mui-control-item control-item" href="#item2">未付款订单</a>
				<a class="mui-control-item" href="#item3">已完成订单</a>
			</div>
			<div id="sliderProgressBar" class="mui-slider-progress-bar mui-col-xs-4"></div>
			<div class="mui-slider-group">
				<div id="item1" class="mui-slider-item mui-control-content mui-active">
					<div id="reflash0"></div>
				</div>

				<div id="item2" class="mui-slider-item mui-control-content">
						<div id="reflash1"></div>
				</div>
				<div id="item3" class="mui-slider-item mui-control-content">
					<div id="reflash2"></div>
				</div>
			</div>
		</div>
	</div>
	<input id="pageInfo0" type="hidden" value="<s:property value='#request.pageInfo0'/>" />
	<input id="pageInfo1" type="hidden" value="<s:property value='#request.pageInfo1'/>" />
	<input id="pageInfo2" type="hidden" value="<s:property value='#request.pageInfo2'/>" />
	<script src="${pageContext.request.contextPath}/eg/js/mui.min.js"></script>
	<script src="${pageContext.request.contextPath }/eg/js/jquery-1.9.1.js" type="text/javascript" charset="utf-8"></script>
	<script src="${pageContext.request.contextPath}/wxv45/js/template-web.js"></script>
	<script type="text/javascript">
		$(function() {
			var pageInfoObj0 = jQuery.parseJSON($("#pageInfo0").val());
			var pageInfoObj1 = jQuery.parseJSON($("#pageInfo1").val());
			var pageInfoObj2 = jQuery.parseJSON($("#pageInfo2").val());
			
			var data0 = {
				list : pageInfoObj0
			}
			var data1 = {
				list : pageInfoObj1
			}
			var data2 = {
				list : pageInfoObj2
			}
			// 根据模板生成 hteml
			var html0 = template('template', data0)
			var html1 = template('template', data1)
			var html2 = template('template', data2)
			
			// 渲染页面			
			$("#reflash0").html(html1);
			
			var item2Show = false,item3Show = false;//子选项卡是否显示标志
			
			document.querySelector('#slider').addEventListener('slide', function(event) {
				  if (event.detail.slideNumber === 1 && !item2Show) {
				    //切换到第二个选项卡
				    //根据具体业务，动态获得第二个选项卡内容；
				    var content = html0;
				    //显示内容
				    document.getElementById("item2").innerHTML = content;
				    //改变标志位，下次直接显示
				    item2Show = true;
				  } else if (event.detail.slideNumber === 2 &&!item3Show) {
				    //切换到第三个选项卡
				    //根据具体业务，动态获得第三个选项卡内容；
				    var content = html2
				    //显示内容
				    document.getElementById("item3").innerHTML = content;
				    //改变标志位，下次直接显示
				    item3Show = true;
				  }
			}); 

		})
	</script>
	<script type="text/javascript">
		mui.init()
	</script>
	<script id='template' type="text/html">
	{{each list}}
		<div id='item1' class='mui-slider-item mui-control-content mui-active'>
			<ul class='mui-table-view bg0'>
				<li class='mui-table-view-cell p10 mt10 bgW' v-for='order in orders'>
					<div class='bgw cG'>
						订单编号 {{$value.orderNo}}
						<ul class='mui-table-view'>
							<li class='mui-table-view-cell mui-media p0'>
								<a href='javascript:;' class='m0'>
									<img class='mui-media-object mui-pull-left' src='${pageContext.request.contextPath}/wxv45/images/yxdd.png' width='72px' height='72px'>
									<div class='mui-media-body font15 pd5 cB'>
										{{$value.orderName}}
										<p class='mui-ellipsis font13 cG'>开始时间：{{$value.orderStartTime}}</p>
										<p class='mui-ellipsis colorR font12'>
											￥
											<span class='f18'>{{$value.orderPrice}}</span>
										</p>
									</div>
								</a>
							</li>
						</ul>
					</div>
				</li>
			</ul>
		</div>
	{{/each}}
	</script>
</body>
</html>