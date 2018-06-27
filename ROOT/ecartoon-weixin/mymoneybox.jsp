<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!doctype html>
<html>

	<head>
		<meta charset="UTF-8">
		<title>我的钱包</title>
		<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
		<link href="ecartoon-weixin/css/mui.min.css" rel="stylesheet" />
		<script src="ecartoon-weixin/js/jquery.min.js" type="text/javascript" charset="utf-8"></script>
		<script src="ecartoon-weixin/js/vue.min.js" type="text/javascript"></script>
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
				padding-right: 0!important;
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
			
			.mt-10 {
				margin: 5px 0;
			}
			
			.a_box {
				margin: -10px 0!important;
			}
			
			.img_css {
				width: 19px!important;
				height: 19px!important;
				max-width: 19px!important;
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
				border: none!important;
				outline: none!important;
				line-height: 44px;
				background: white!important;
				color: #FF4401;
				text-align: right;
				display: none;
			}
			
			.footer_button {
				width: 40%!important;
				height: 100%!important;
				margin: 0!important;
				padding: 0!important;
				line-height: 44px!important;
				background: #ff4401!important;
				border: none!important;
				outline: none;
				color: white!important;
				text-align: center!important;
				border-radius: 0!important;
			}
		</style>
	</head>

	<body>
		<div class="mui-content" id="newpp">
			<!--滑动组件-->
			<div id="pullrefresh" class="mui-content mui-scroll-wrapper">
				<div class="mui-scroll">
					<!--滑动组件结束-->

					<div class="mui-slider" id="slider">
						<div class="mui-slider-indicator mui-segmented-control mui-segmented-control-inverted">
							<a class="mui-control-item control-item cG mui-active" href="#item1">收入</a>
							<a class="mui-control-item control-item" href="#item2">支出</a>
							<a class="mui-control-item" id="ca" href="#item3">提现</a>
						</div>
						<div id="sliderProgressBar" class="mui-slider-progress-bar mui-col-xs-4"></div>
						<div class="mui-slider-group">
							<div id="item1" class="mui-slider-item mui-control-content mui-active">
								<ul class="mui-table-view bg0">
									<li class="mui-table-view-cell p10 mt10 bgW" v-for="yy in income">
										<p class="font15 cB">{{yy.prodName}}<span class="mui-pull-right font12 cG">日期：{{yy.balanceTime}}</span></p>
										<p class="font13 cG">付款人：{{yy.fromName}}</p>
										<p class="font12 colorR mt-10">￥ <span class="font15">{{yy.balanceMoney | NAAN}}</span></p>
									</li>

								</ul>
							</div>
							<div id="item2" class="mui-slider-item mui-control-content">
								<ul class="mui-table-view  bg0">
									<li class="mui-table-view-cell p10 mt10 bgW" v-for="yy in out">
										<p class="font15 cB">{{yy.name}}<span class="mui-pull-right font12 cG">日期：{{yy.payTime}}</span></p>
										<p class="font13 cG">收款人：{{yy.toName}}</p>
										<p class="font12 colorR mt-10">￥ <span class="font15">{{yy.orderMoney | NAAN}}</span></p>
									</li>

								</ul>
							</div>
							<div id="item3" class="mui-slider-item mui-control-content">
								<ul class="mui-table-view bg0">
									<li class="mui-table-view-cell p10 mt10 bgW" v-for="x in pick">
										<div class="bgw cG">
											<ul class="mui-table-view">
												<li class="mui-table-view-cell mui-media p0">
													<a href="javascript:;" class="a_box">
														<img class="mui-media-object mui-pull-left img_css" :src="'ecartoon-weixin/'+x.imgsrc" />
														<div class="mui-media-body cB">
															<div class="mui-pull-left">
																<p class="mui-ellipsis">{{x.bankName}}</p>
																<p class="mui-ellipsis">{{x.nick}} {{x.account}}</p>
															</div>
															<span class="mui-pull-right line42 font16 colorR">+{{x.pickMoney}}RMB</span>

														</div>
													</a>
												</li>

											</ul>

											<span class="mui-pull-right font12" onclick="downloadAPP()">提现时间：{{x.evalTime}}</span>
										</div>
									</li>

								</ul>

							</div>
						</div>
					</div>
				</div>
			<div>
		</div>
	</div>
</div>
				<footer class="mui-bar mui-bar-footer footer_css">
					<span style="margin-right: 10px;" class="font12">
            		<span class="font15">余额：</span>￥<span class="f18">${dataLists.pickMoneyCount}</span>
					</span>
					<input type="button" name="" id="" value="提现" onclick="javascript:location.href='../share45/share45.html'" class="mui-pull-right footer_button" />
				</footer>
				<script src="ecartoon-weixin/js/mui.min.js"></script>
				<script type="text/javascript">
					var l = []; //初始加载的数据
					var b = []; //上拉加载的数据
					var datajson = [{
						"zl": 1,
						"time": "2017-08-15",
						"name": "黄大仙",
						"money": 8
					}, {
						"zl": 2,
						"time": "2017-08-15",
						"name": "黄大仙",
						"money": 8
					}, {
						"zl": 3,
						"time": "2017-08-15",
						"name": "黄大仙",
						"money": 8
					}, {
						"zl": 4,
						"time": "2017-08-15",
						"name": "黄大仙",
						"money": 8
					}, {
						"zl": 5,
						"time": "2017-08-15",
						"name": "黄大仙",
						"money": 8
					}, {
						"zl": 6,
						"time": "2017-08-15",
						"name": "黄大仙",
						"money": 8
					}];
					var qx = [{
						"imgsrc": "img/weixin.png",
						"nick": "黄二仙",
						"phone": "321654654",
						"money": 1464,
						"time": "1992-12-13 10:12"
					}, {
						"imgsrc": "img/zhifubao.png",
						"nick": "黄二仙",
						"phone": "321654654",
						"money": 1464,
						"time": "1992-12-13 10:12"
					}, {
						"imgsrc": "img/shouye/taoke/UnionPay@2x.png",
						"nick": "黄二仙",
						"phone": "321654654",
						"money": 1464,
						"time": "1992-12-13 10:12"
					}];
					var numA = 3;
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
					var count=0;
							//	var item2Show = false,item3Show = false;//子选项卡是否显示标志
			document.querySelector('.mui-slider').addEventListener('slide', function(event) {
				if(event.detail.slideNumber === 0) {
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
					
                        //下拉刷新
					function pulldownRefresh() {
                           // 重新取出数据data=??
						setTimeout(function() {

						}, 1500)
					};
                           //上拉加载
					function pullupRefresh() {
						numA += 3;
						//  alert(numA)
						if(count == 0) {
						
						setTimeout(function() {
							mui('#pullrefresh').pullRefresh().endPullupToRefresh((numA > datajson.length));
							for(i = 0; i < numA; i++) {
								b.push(datajson[i])
							}
							bb.message = b;
						}, 1500)
						}

					};


					for(i = 0; i < numA; i++) {
						l.push(datajson[i])
					}
					var dd = ${dataLists};
					var bb = new Vue({
						el: "#newpp",
						data: {
							income: dd.incomeDetail ,
							   out: dd.outDetail,
							  pick: dd.pickDetail
						},
						filters:{
							NAAN: function (value){
								if (value == "null" || value == null || value == undefined || value == "0"){
									return "0.00";
								} else {
									return value.toFixed(2);
								}
								
							}
							
						}

					})
					
					
					document.getElementById('slider').addEventListener('slide', function(e) {
						if(e.detail.slideNumber === 2) {
							$(".mui-bar-footer").css("display", "block")

						} else {
							$(".mui-bar-footer").css("display", "none")
						}
					});
					
					function downLoadAPP(){
						var sUserAgent = navigator.userAgent.toLowerCase();
						var bIsIpad = sUserAgent.match(/ipad/i) == "ipad";
						var bIsIphoneOs = sUserAgent.match(/iphone os/i) == "iphone os";
						var bIsAndroid = sUserAgent.match(/android/i) == "android";
						if (bIsIpad || bIsIphoneOs) {
							location.href = "https://itunes.apple.com/cn/app/%E5%81%A5%E8%BA%ABe%E5%8D%A1%E9%80%9A/id1218667055?mt=8";
						}else{
							location.href = "http://www.ecartoon.com.cn/app/ecartoon-V1.4.apk";	
						}
					}
				</script>
	</body>

</html>