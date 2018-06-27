<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() +  path +"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
	  <base href="<%=basePath%>">
		<meta charset="UTF-8">
		<title>我的</title>
		<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
		<link href="ecartoon-weixin/css/mui.min.css" rel="stylesheet" />
		<script src="ecartoon-weixin/js/jquery.min.js" type="text/javascript" charset="utf-8"></script>
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
			
			 .header-im {
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
				filter: blur(3px) brightness(90%);
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
	</head>
	<body>
		<div class="header-a ">
			<div class="header-div ">
				<img src="ecartoon-weixin/img/wxbackground.jpg" class="header-img "/>
				<div class="userHeader">
					<div class="menu">
						<div class="img-border">
							<img id="memberImage" src="${member.image}" width="75px" height="75px" class="img-border1" />
						</div>
						<div class="address_box f13 cW tcenter mt8">
							${member.name}|<span class="mui-icon mui-icon-location font16"></span><span id="city" class="font13">{{city}}-{{county}}</span>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="mine-showbox">
			<div class="my-footer">
			    <a href="ememberwx!mySignIn.asp">
				<img src="ecartoon-weixin/img/mine/footprint.png" width="42" height="42" />
				<p class="my_p">我的足迹</p>
				</a>
			</div>
			<div class="my-fight">
				<a href="ecoursewx!myActive.asp?type=2">
				<img src="ecartoon-weixin/img/mine/challenge.png" width="42" height="42" />
				<p class="my_p">我的挑战</p>
				</a>
			</div>
			<div class="my-number">
				<span class="my-number-span">${memberInfo.trainRecordCount}<span class="cishu">次</span>
				</span>
				<p class="my_p">健身次数</p>
			</div>
		</div>
		<ul class="mui-table-view">
			<li class="mui-table-view-cell mui-media mine-li">
				<a href="eproductorderwx!findOrder.asp" class="mui-navigate-right">
					<img class="mui-media-object mui-pull-left mine-icon" src="ecartoon-weixin/img/mine/order.png">
					<div class="mui-media-body">
						我的订单
					</div>
				</a>
			</li>
			<li class="mui-table-view-cell mui-media mine-li">
				<a href="eorderdetailwx!income.asp" class="mui-navigate-right">
					<img class="mui-media-object mui-pull-left mine-icon" src="ecartoon-weixin/img/mine/wallet.png">
					<div class="mui-media-body">
						我的钱包 <span class="mui-pull-right cG font12" style="padding-right: 30px;">收入\支出\提现</span>
					</div>
				</a>
			</li>
			<li class="mui-table-view-cell mui-media mine-li">
				<a href="eaccountswx!ticketAll.asp" class="mui-navigate-right">
					<img class="mui-media-object mui-pull-left mine-icon" src="ecartoon-weixin/img/mine/coupons.png">
					<div class="mui-media-body">
						我的优惠券
					</div>
				</a>
			</li>
		</ul>
		<script src="ecartoon-weixin/js/mui.min.js"></script>
		<script src="ecartoon-weixin/js/vue.min.js"></script>
		<script src="ecartoon-weixin/js/jquery.min.js"></script>
		<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js "></script>
	<script type="text/javascript" src="http://www.ecartoon.com.cn/js/utils.elisa.js"></script>
		<script type="text/javascript">
		wxUtils.sign("ewechatwx!sign.asp");
		wx.ready(function(){
			wxUtils.share({
				title : "健身E卡通—我的账户",
				link : "<%=basePath%>ememberwx!findMe.asp"+ location.search,
				img : "<%=basePath%>img/shareLogo.png",
				desc : "一卡在手，全城都有。健身E卡通为您打造健康生命第三空间……"
			});
		});
		
		$(function () {
			var image = "${member.image}";
			if (image.indexOf("http") == -1){
				$("#memberImage").attr({"src":"picture/"+image});
			}
		})
		
		
			mui.init()
			//处理头部的图片高度
			var headeraheight = $(".header-a").width() / 2;
			$('.header-a').css("height", headeraheight);
			var imgwidth = $('.header-div').width();
			var imgheight = imgwidth / 2 * 1;
			$('.header-img').css('height', (imgheight + 10));
			
			var city = new Vue({
				el:"#city",
				data:{
					city:"${memberInfo.city}",
					county:"${memberInfo.county}"
				}		
			});
			
			(function(){
				wx.ready(function(){
					 wx.getLocation({
						    success: function (resx) {
									$.ajax({
										url:"ememberwx!changeLocation.asp",
										type:"post",
										data:{
											"longitude":resx.longitude,
											"latitude":resx.latitude
										},
										dataType:"json",
										success:function(data){
											city.city = data.city;
											city.county = data.county;
										},
										error:function(e){
											/* alert(JSON.stringify(e)); */
										}
									});
						    },
						    cancel: function (res) {
						        console.log('拒绝接受地理位置');
						    }
					 });
				}); 
			})();
		</script>
	</body>
</html>