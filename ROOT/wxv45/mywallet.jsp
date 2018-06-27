<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!doctype html>
<html>
<head>
<meta charset="UTF-8">
<title>我的账户</title>
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
<link href="${pageContext.request.contextPath}/eg/css/mui.min.css" rel="stylesheet" />
<script src="${pageContext.request.contextPath}/eg/js/jquery-2.1.4.js" type="text/javascript" charset="utf-8"></script>
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
	padding-right: 0 !important;
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

.mt-10 {
	margin: 5px 0;
}

.a_box {
	margin: -10px 0 !important;
}

.img_css {
	width: 19px !important;
	height: 19px !important;
	max-width: 19px !important;
	margin-top: 10px;
}

.line42 {
	line-height: 42px;
}

.mui-media {
	border-bottom: 1px solid #f1f1f1;
	margin-bottom: 5px;
}

.mui-table-view:after {
	position: absolute;
	right: 0;
	bottom: 0;
	left: 0;
	height: 1px;
	content: '';
	transform: scaleY(.5);
	background-color: rgba(0, 0, 0, 0);
}

.footer_css {
	margin: 0;
	padding: 0;
	border: none !important;
	outline: none !important;
	line-height: 44px;
	background: white !important;
	color: #FF4401;
	text-align: right;
	display: none;
}

.footer_button {
	width: 40% !important;
	height: 100% !important;
	margin: 0 !important;
	padding: 0 !important;
	line-height: 44px !important;
	background: #ff4401 !important;
	border: none !important;
	outline: none;
	color: white !important;
	text-align: center !important;
	border-radius: 0 !important;
}
</style>
</head>

<body>
	<div class="mui-content">
		<div class="mui-slider" id="slider">
			<div class="mui-slider-indicator mui-segmented-control mui-segmented-control-inverted">
				<a class="mui-control-item control-item cG mui-active" href="#item1">收入</a>
				<a class="mui-control-item control-item" href="#item2">支出</a>
				<a class="mui-control-item" id="ca" href="#item3">提现</a>
			</div>
			<div id="sliderProgressBar" class="mui-slider-progress-bar mui-col-xs-4"></div>
			<div class="mui-slider-group">
				<div id="item1" class="mui-slider-item mui-control-content mui-active">
					<ul class="mui-table-view bg0" id="reflash0">
						<!-- 收入列表 -->
						
						<!-- 收入列表 -->
					</ul>
				</div>
				<div id="item2" class="mui-slider-item mui-control-content">
					<ul class="mui-table-view  bg0" id="reflash1">
						<!-- 支出列表 -->
						
						<!-- 支出列表 -->
					</ul>
				</div>
				<div id="item3" class="mui-slider-item mui-control-content">
					<ul class="mui-table-view bg0" id="reflash2">
						<!-- 提现列表 -->
						
						<!-- 提现列表 -->
					</ul>
				</div>
			</div>
		</div>
	</div>
	<input id="income" type="hidden" value="<s:property value='#request.income'/>" />
	<input id="expenditure" type="hidden" value="<s:property value='#request.expenditure'/>" />
	<input id="pickDetail" type="hidden" value="<s:property value='#request.pickDetail'/>" />
	<footer class="mui-bar mui-bar-footer footer_css">
		<span style="margin-right: 10px;" class="font12">
			<span class="font15">余额：</span>
			￥
			<span class="f18">${pickMoneyCount}</span>
		</span>
		<input type="button" name="" id="" value="提现" class="mui-pull-right footer_button" />
	</footer>

	<script src="${pageContext.request.contextPath}/eg/js/mui.min.js"></script>
	<script src="${pageContext.request.contextPath}/wxv45/js/template-web.js"></script>
	<script type="text/javascript">
		mui.init()
		document.getElementById('slider').addEventListener('slide', function(e) {
			if (e.detail.slideNumber === 2) {
				$(".mui-bar-footer").css("display", "block")

			} else {
				$(".mui-bar-footer").css("display", "none")
			}
		});
	</script>
	<script type="text/javascript">
		$(function() {
			var income = jQuery.parseJSON($("#income").val());
			var expenditure = jQuery.parseJSON($("#expenditure").val());
			var pickDetail = jQuery.parseJSON($("#pickDetail").val());
			
			var data0 = {
				list : income
			}
			var data1 = {
				list : expenditure
			}
			var data2 = {
				list : pickDetail
			}
			
			// 根据模板生成 hteml
			var html0 = template('template0', data0)
			var html1 = template('template1', data1)
			var html2 = template('template2', data2)
			
			// 渲染页面			
		 	$("#reflash0").html(html0);
			$("#reflash1").html(html1);
			$("#reflash2").html(html2); 
		})
	 </script>
	<script id='template0' type="text/html">
	{{each list}}	
		<li class="mvui-table-view-cell p10 mt10 bgW">
							<p class="font15 cB">
								 {{$value.prodName}}
								<span class="mui-pull-right font12 cG">{{$value.balanceTime}}</span>
							</p>
							<p class="font13 cG">付款人：{{$value.fromName}}</p>
							<p class="font12 colorR mt-10">
								￥
								<span class="font15">9{{$value.balanceMoney}}</span>
							</p>
		</li>
	{{/each}}
  	</script>
  	<script id='template1' type="text/html">
	{{each list}}	
		<li class="mvui-table-view-cell p10 mt10 bgW">
							<p class="font15 cB">
								 {{$value.NAME}}
								<span class="mui-pull-right font12 cG">{{$value.payTime}}</span>
							</p>
							<p class="font13 cG">付款人：{{$value.toName}}</p>
							<p class="font12 colorR mt-10">
								￥
								<span class="font15">9{{$value.orderMoney}}</span>
							</p>
		</li>
	{{/each}}
  	</script>
  	
  	<script id='template2' type="text/html">
	{{each list}}
		<li class="mui-table-view-cell p10 mt10 bgW">
							<div class="bgw cG">
								<ul class="mui-table-view">
									<li class="mui-table-view-cell mui-media p0">
										<a href="javascript:;" class="a_box">
											<img class="mui-media-object mui-pull-left img_css" src="images/weixin.png">
											<div class="mui-media-body cB">
												<div class="mui-pull-left">
													{{$value.bankName}}
													<p class="mui-ellipsis">{{$value.account}}</p>
												</div>
												<span class="mui-pull-right line42 font16 colorR">+{{$value.pickMoney}}RMB</span>

											</div>
										</a>
									</li>
								</ul>
								<span class="mui-pull-right font12">提现时间：{{$value.evalTime}}</span>
							</div>
		</li>
	{{/each}}
  	</script>
</body>
</html>