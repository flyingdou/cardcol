<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<!-- <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"> -->
<!-- 页面适应手机 -->
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />

		
		<link href="sport/css/mui.min.css" rel="stylesheet" />
		<script src="sport/js/jquery.min.js" type="text/javascript" charset="utf-8"></script>
		<script src="sport/js/vue.min.js" type="text/javascript"></script>
		
		<style type="text/css">
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
				/*-webkit-filter: grayscale(100%);
                -moz-filter: grayscale(100%);
                -ms-filter: grayscale(100%);
                -o-filter: grayscale(100%);
                filter: grayscale(100%)
                filter: black;*/
				/*高斯模糊*/
				filter: url(blur.svg#blur);
				/* FireFox, Chrome, Opera */
				-webkit-filter: blur(13px) brightness(50%);
				;
				-moz-filter: blur(3px) brightness(50%);
				;
				-o-filter: blur(3px) brightness(50%);
				;
				-ms-filter: blur(3px) brightness(50%);
				;
				filter: blur(3px) brightness(50%);
				;
				filter: progid:DXImageTransform.Microsoft.Blur(PixelRadius=3, MakeShadow=false);
				/* IE6~IE9 */
			}
			
			.userHeader {
				position: absolute;
				top: 0;
				z-index: 2;
				width: 100%;
				height: 100%;
				padding: 10px;
				display: -webkit-box;
				-webkit-box-orient: vertical;
				box-sizing: border-box;
				overflow: hidden;
			}
			
			.mt8 {
				margin-top: 8px;
			}
			
			.menu {
				height: 80%;
				margin: 5% auto;
				position: relative;
				display: -webkit-box;
				-webkit-box-orient: vertical;
				-webkit-box-pack: center;
			}
			
			.address_box {}
			
			.img-border {
				border: 2px solid rgba(255, 255, 255, .1);
				width: 79px;
				height: 79px;
				margin: 0 auto;
				border-radius: 50%;
			}
			
			.img-border1 {
				display: block;
				margin: 0 auto;
				border-radius: 50%;
				border: 4px solid rgba(255, 255, 255, .2);
			}
			
			.mine-showbox {
				height: 100px;
				margin: 10px 0;
				background: white;
				display: -webkit-box;
				-webkit-box-orient: horizontal;
			}
			
			.my-fight,
			.my-number,
			.my-footer {
				-webkit-box-flex: 1;
				height: 100%;
				text-align: center;
				color: #1e1e1e;
				position: relative;
				padding: 10px 0;
				box-sizing: border-box;
			}
			
			.my-fight:after {
				position: absolute;
				content: "";
				width: 100%;
				height: 20px;
				top: 40px;
				left: 0;
				border-right: 1px solid #f2f2f2;
			}
			
			.my-footer:after {
				position: absolute;
				content: "";
				width: 100%;
				height: 20px;
				top: 40px;
				left: 0px;
				border-right: 1px solid #f2f2f2;
			}
			
			.my-number-span {
				width: 42px;
				height: 42px;
				display: inline-block;
				line-height: 42px;
				color: #ff4401;
				font-size: 26px;
			}
			
			.cishu {
				font-size: 12px;
				color: #999999
			}
			
			.my_p {
				color: #1e1e1e!important
			}
			
			.mui-table-view-cell {
				padding: 14px 10px;
			}
			
			.mui-table-view-cell:after {
				left: 10px;
			}
			
			.mui-icon1 {
				display: inline-block;
			}
			
			.mine-icon {
				max-width: 16px!important;
				height: 16px!important;
				width: 16px;
				margin-top: 2px;
			}
			
			.mui-media-body {
				font-size: 14px!important;
			}
			
			.mine-li {
				padding: 12px 10px!important;
				box-sizing: border-box!important;
				height: 45px;
			}
			
			.mui-table-view-cell>a:not(.mui-btn) {
				position: relative;
				display: block;
				overflow: hidden;
				margin-top: -11px;
				margin-right: -15px;
				margin-bottom: -11px;
				margin-left: -10px;
				padding: inherit;
				white-space: nowrap;
				text-overflow: ellipsis;
				color: inherit;
			}
			
			
			
			
			
			
			
			
		</style>
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
				height: 45px!important;
				line-height: 45px!important;
				background: white;
				position: relative;
			}
			
			.mui-segmented-control.mui-segmented-control-inverted .mui-control-item {
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
				background: rgba(0, 0, 0, 0)!important;
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
				outline: 2px solid #f2f2f2!important;
			}
			
			.mui-slider-progress-bar {
				margin-bottom: 10px;
			}
			
			.p10 {
				padding: 10px 10px 5px 10px!important;
			}
			
			.p0 {
				padding-left: 0!important;
			}
			
			.m0 {
				margin: -10px 0!important;
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
			
			.mui-segmented-control.mui-segmented-control-inverted .mui-control-item.mui-active {
				color: #1e1e1e;
				background: white;
			}
			
			.mui-slider .mui-segmented-control.mui-segmented-control-inverted~.mui-slider-group .mui-slider-item {
				border: none;
			}
        </style>



</head>
<body ng-app="myApp"  >






<div class="mui-content" ng-controller="myCtrl" id="app">
			<!--滑动组件-->
			<div id="pullrefresh" class="mui-content mui-scroll-wrapper">
				<div class="mui-scroll">
               <!--滑动组件结束-->
               
               <div class="header-a ">
	<div class="header-div ">
		<img src="sport/img/banner.png " class="header-img " />
		<div class="userHeader">
			<div class="menu">
					<div class="img-border">
					    <a href="smemberwx!loadMe.asp" >
						<img src="picture/${orderLists.image}" width="75px" height="75px" class="img-border1" />
					    </a>
					</div>

					<div class="address_box f18 cW tcenter mt8">
						${orderLists.name}|<span class="mui-icon mui-icon-location font16"></span>
						<span class="font13">${orderLists.city}-${orderLists.county}</span>
					</div>
			 </div>

		 </div>
	 </div>
	 </div>
	 
	 
	 <div class="mine-showbox">
	    <div class="my-number">
				已健身<span class="my-number-span">${orderLists.trainRecordCount.count}<span class="cishu">次</span>
				</span>
		</div>
				<div class="my-footer">
				    <a href="smemberwx!mySign.asp">
					<img src="sport/img/mine/footprint.png" width="42" height="42" id = "myTo" />
			        <p class="my_p">我的足迹</p>
					</a>
				</div>
		</div>
               
               
					<div class="mui-slider">
						<div class="mui-slider-indicator mui-segmented-control mui-segmented-control-inverted">
							<a class="mui-control-item control-item cG mui-active" href="#item1">有效订单</a>
							<a class="mui-control-item control-item" href="#item2">未付款订单</a>
							<a class="mui-control-item" href="#item3">已完成订单</a>
						</div>
						<div id="sliderProgressBar" class="mui-slider-progress-bar mui-col-xs-4"></div>

						<div class="mui-slider-group">
							<div id="item1" class="mui-slider-item mui-control-content mui-active">
								<ul class="mui-table-view bg0">
									<li class="mui-table-view-cell p10 mt10 bgW" v-for="v in o" >
										<div class="bgw cG">
											订单编号：<span>{{v.no}}</span>
											<ul class="mui-table-view">
												<li class="mui-table-view-cell mui-media p0">
													<a href="javascript:;" class="m0">
														<img class="mui-media-object mui-pull-left" :src="'picture/'+v.image" width="72px" height="72px">
														<div class="mui-media-body font15 pd5 cB">
															<span>{{v.name}}</span>
															<p class="mui-ellipsis font13 cG" >开始时间：{{v.orderStartTime}}</p>
															<p class="mui-ellipsis colorR font12">￥ <span class="f18" >{{v.orderMoney}}</span>.00</p>
														</div>
													</a>
												</li>

											</ul>
										</div>
									</li>

								</ul>
							</div>
							<div id="item2" class="mui-slider-item mui-control-content">
								<ul class="mui-table-view  bg0">
									<li class="mui-table-view-cell p10 mt10 bgW" v-for="v in t">
										<div class="bgw cG">
											订单编号：<span>{{v.no}}</span>
											<ul class="mui-table-view">
												<li class="mui-table-view-cell mui-media p0">
													<a href="javascript:;" class="m0">
														<img class="mui-media-object mui-pull-left" :src="'picture/'+v.image" width="72px" height="72px">
														<div class="mui-media-body font15 pd5 cB">
															<span>{{v.name}}</span>
															<p class="mui-ellipsis font13 cG">开始时间：{{v.orderStartTime}}</p>
															<p class="mui-ellipsis colorR font12">￥ <span class="f18">{{v.orderMoney}}</span>.00</p>
														</div>
													</a>
												</li>

											</ul>
										</div>
									</li>

								</ul>
							</div>
							<div id="item3" class="mui-slider-item mui-control-content">
								<ul class="mui-table-view bg0">

									<li class="mui-table-view-cell p10 mt10 bgW" v-for="v in th">
										<div class="bgw cG">
											订单编号：<span>{{v.no}}</span>
											<ul class="mui-table-view">
												<li class="mui-table-view-cell mui-media p0">
													<a href="javascript:;" class="m0">
														<img class="mui-media-object mui-pull-left" :src="'picture/'+v.image" width="72px" height="72px">
														<div class="mui-media-body font15 pd5 cB">
															<span>{{v.name}}</span>
															<p class="mui-ellipsis font13 cG">开始时间：{{v.orderStartTime}}</p>
															<p class="mui-ellipsis colorR font12">￥ <span class="f18">{{v.orderMoney}}</span>.00</p>
														</div>
													</a>
												</li>

											</ul>
										</div>
									</li>
								</ul>
							</div>

						</div>

					</div>

				</div>
			</div>
		</div>

		<script src="sport/js/mui.min.js"></script>
		<script type="text/javascript">
		
		
		
		
		        var dd = ${orderLists};
                new Vue({
            	   el:"#app",
            	   data:{
            		   o:dd.b,
            		   t:dd.a,
            		  th:dd.c
            	   }
               })
            	   
             
		
			var count = 0; //默认第几个选项
			var number = 1; //默认加载条数
			var number1 = number; //不同选项初始加载的条数
			var number2 = number;
			var number3 = number;
			var k = 3 //滑动加载的条数
			/*下面是获取滑动到第几个订单*/

			//	var item2Show = false,item3Show = false;//子选项卡是否显示标志
			document.querySelector('.mui-slider').addEventListener('slide', function(event) {
				if(event.detail.slideNumber === 0) {
					debugger;
					count = 0;
					//切换到第三个选项卡
					//根据具体业务，动态获得第三个选项卡内容；
					// var content = ....
					//显示内容
					//  document.getElementById("item3").innerHTML = content;
					//改变标志位，下次直接显示
					// item3Show = true;
					mui('#pullrefresh').pullRefresh().enablePullupToRefresh();
					//解除控制上拉效果
				} else if(event.detail.slideNumber === 1) {
					count = 1;
					mui('#pullrefresh').pullRefresh().enablePullupToRefresh();
					//切换到第二个选项卡
					//根据具体业务，动态获得第二个选项卡内容；
					// var content = ....
					//显示内容
					//document.getElementById("item2").innerHTML = content;
					//改变标志位，下次直接显示
					// item2Show = true;
				} else if(event.detail.slideNumber === 2) {
					count = 2;
					mui('#pullrefresh').pullRefresh().enablePullupToRefresh();

					//切换到第三个选项卡
					//根据具体业务，动态获得第三个选项卡内容；
					// var content = ....
					//显示内容
					//  document.getElementById("item3").innerHTML = content;
					//改变标志位，下次直接显示
					// item3Show = true;
				}
				
				
			});
			

			mui.init({
				pullRefresh: {
					container: '#pullrefresh',
					down: {
						callback: pulldownRefresh
					},
					up: {
						contentrefresh: '正在加载...',
						callback: pullupRefresh
					}
				}
			});
			/**
			 * 下拉刷新具体业务实现
			 */
			function pulldownRefresh() {

				setTimeout(function() {

					
					
					
					var appElement = document.querySelector('[ng-controller=myCtrl]');
					var $scope = angular.element(appElement).scope();
					$scope.menu = data.o;
					$scope.menu1 = data.t;
					$scope.menu2 = data.th;
					$scope.num1 = number1;
					$scope.num2 = number2;
					$scope.num3 = number3;
					$scope.$apply();//强制刷新

					mui('#pullrefresh').pullRefresh().endPulldownToRefresh(); //refresh completed
				}, 1500);

			}

			/**
			 * 上拉加载具体业务实现
			 */

			function pullupRefresh() {
				add()

				// alert(number1)
				if(count == 0) {
					setTimeout(function() {
						mui('#pullrefresh').pullRefresh().endPullupToRefresh((number1 > data.o.length));
						var appElement = document.querySelector('[ng-controller=myCtrl]');
						var $scope = angular.element(appElement).scope();
						$scope.num1 = number1;
						var $scope = angular.element(appElement).scope();
						$scope.$apply();

					}, 1500);
				} else if(count == 1) {

					setTimeout(function() {
						mui('#pullrefresh').pullRefresh().endPullupToRefresh((number2 > data.t.length));
						var appElement = document.querySelector('[ng-controller=myCtrl]');
						var $scope = angular.element(appElement).scope();
						$scope.num2 = number2;
						var $scope = angular.element(appElement).scope();
						$scope.$apply();

					}, 1500);
				} else {
					setTimeout(function() {
						mui('#pullrefresh').pullRefresh().endPullupToRefresh((number3 > data.th.length));
						var appElement = document.querySelector('[ng-controller=myCtrl]');
						var $scope = angular.element(appElement).scope();
						$scope.num3 = number3;
						var $scope = angular.element(appElement).scope();
						$scope.$apply();

					}, 1500);
				}

			}

			function add() {
				switch(count) {
					case 0:
						number1 += k;
						return number1
						break;
					case 1:
						number2 += k;
						return number2
						break;
					default:
						number3 += k;
						return number3
						break;
				}

			}

		</script>



		<script type="text/javascript">
			mui.init()
			//处理头部的图片高度
			var headeraheight = $(".header-a").width() / 2;
			$('.header-a').css("height", headeraheight);
			var imgwidth = $('.header-div').width();
			var imgheight = imgwidth / 2 * 1;
			$('.header-img').css('height', imgheight);
		</script>



</body>
</html>