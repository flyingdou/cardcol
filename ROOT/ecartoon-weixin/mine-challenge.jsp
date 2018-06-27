<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="UTF-8">
<title>我的挑战</title>
<meta name="viewport"
	content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
<link href="ecartoon-weixin/css/mui.min.css" rel="stylesheet" />
<script src="ecartoon-weixin/js/jquery.min.js" type="text/javascript" charset="utf-8"></script>
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
	line-height: 80px;
	max-width: 106px;
	height: 80px;
}

.pd5 {
	padding-top: 5px;
}

.mui-table-view-cell:after {
	position: absolute;
	right: 0;
	bottom: 0;
	left: 10px;
	content: '';
	transform: scaleY(.5);
	background-color: #f2f2f2;
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

.img_success {
	position: absolute;
	top: 0;
	right: 0;
	z-index: 2;
	width: 50px !important;
	height: 50px !important;
}
</style>
</head>

<body>
	<div class="mui-content" id="app">
		<div class="mui-slider">
			<div
				class="mui-slider-indicator mui-segmented-control mui-segmented-control-inverted">
				<a class="mui-control-item control-item cG mui-active" href="#item1">我参加的挑战</a>
				<a class="mui-control-item control-item" href="#item2">我发起的挑战</a>
			</div>
			<div id="sliderProgressBar"
				class="mui-slider-progress-bar mui-col-xs-6"></div>
			<div class="mui-slider-group">
				<div id="item1"
					class="mui-slider-item mui-control-content mui-active">
					<ul class="mui-table-view bg0">
						<li class="mui-table-view-cell p10  bgW" v-for="v in active1">
							<div class="bgw cG">
								<ul class="mui-table-view">
									<li class="mui-table-view-cell mui-media p0"><a
										:href="'ecoursewx!getActive.asp?type=1&id='+v.id+'&result='+v.result" class="m0"><img
											class="mui-media-object mui-pull-left"
											:src="'picture/'+v.image" width="106px" height="80px">
											<div class="mui-media-body font15 pd5 cB">
												{{v.name}}
												<p class="mui-ellipsis font13 cG">{{v.applyCount}}人参加</p>
												<p class="mui-ellipsis font13 cG">目标：{{v.memo}}</p>
											</div>
									</a></li>
								</ul>
							</div>
							<img v-if="v.result==0" src="ecartoon-weixin/img/mine/ongoing.png" class="img_success" />
							<img v-if="v.result==1" src="ecartoon-weixin/img/mine/successful.png" class="img_success" />
							<img v-if="v.result==2" src="ecartoon-weixin/img/mine/failure.png" class="img_success" />
							<img v-if="v.result==3" src="ecartoon-weixin/img/mine/ended.png" class="img_success" />
						</li>
					</ul>
				</div>
				<div id="item2" class="mui-slider-item mui-control-content">
					<ul class="mui-table-view  bg0">
						<li class="mui-table-view-cell p10 mt10 bgW">
							<div class="bgw cG">
								<ul class="mui-table-view">
									<li class="mui-table-view-cell p10  bgW" v-for="v in active2">
							<div class="bgw cG">
								<ul class="mui-table-view">
									<li class="mui-table-view-cell mui-media p0"><a
										:href="'ecoursewx!getActive.asp?type=3&id='+v.id" class="m0"> <img
											class="mui-media-object mui-pull-left"
											:src="'picture/'+v.image" width="106px" height="80px">
											<div class="mui-media-body font15 pd5 cB">
												{{v.name}}
												<p class="mui-ellipsis font13 cG">{{v.applyCount}}人参加</p>
												<p class="mui-ellipsis font13 cG">目标：{{v.memo}}</p>
											</div>
									</a></li>
								</ul>
							</div> 
							<!-- <img v-if="v.success==0" src="ecartoon-weixin/img/mine/ongoing.png" class="img_success" />
							<img v-if="v.success==1" src="ecartoon-weixin/img/mine/successful.png" class="img_success" />
							<img v-if="v.success==2" src="ecartoon-weixin/img/mine/failure.png" class="img_success" />
							<img v-if="v.success==3" src="ecartoon-weixin/img/mine/ended.png" class="img_success" /> -->
						</li>
					</ul>
				</div>
			</div>
		</div>
	</div>
	</div>
	<script src="ecartoon-weixin/js/mui.min.js"></script>
	<script src="ecartoon-weixin/js/vue.min.js"></script>
	<script type="text/javascript">
		mui.init()
	</script>
	<script type="text/javascript">
	 var data = ${data == null ? 0 : data};
	 var active =	new Vue({
			el : "#app",
			data : {
				active1 : data.items,
				active2 : 0
			}
		});
		
		$.ajax({
			url:"ecoursewx!myActive.asp",
			type:"post",
			data:{"type":1,"ajax":"1"},
			dataType:"json",
			success:function(res){
				active.active2 = res.items
			},
			error : function(e){
				eonsole.log(JSON.stringify(e))
			}
		});
	</script>
</body>
</html>