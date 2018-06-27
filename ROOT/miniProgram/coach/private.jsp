<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<base href="<%=basePath%>">
<meta charset="UTF-8">
<title>私教套餐详情</title>
<meta name="description" content='暂无简介' />
<meta name="viewport"
	content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
<link rel="stylesheet" type="text/css"
	href="miniProgram/coach/css/app.css" />
<link rel="stylesheet" type="text/css"
	href="miniProgram/coach/css/mui.min.css" />
<link rel="stylesheet" type="text/css"
	href="miniProgram/coach/css/mui.picker.min.css" />
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

.cb {
	color: #1e1e1e;
}

.cgrey {
	color: #999999
}

.corange {
	color: #ff4401;
	font-size: 14px;
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

.font13 {
	font-size: 13px;
}

.font15 {
	font-size: 15px;
}

.colorR {
	color: #ff4401;
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
	width: 120%;
	height: 100%;
	position: relative;
	box-sizing: border-box;
	overflow: hidden;
}

.header-img {
	/*-webkit-filter: grayscale(100%);
             -moz-filter: grayscale(100%);
         -ms-filter: grayscale(100%);
            -o-filter: grayscale(100%);
    
            filter: grayscale(100%);
	
               filter: black;*/
	/*高斯模糊*/
	filter: url(blur.svg#blur);
	/* FireFox, Chrome, Opera */
	-webkit-filter: blur(13px) brightness(50%);;
	-moz-filter: blur(3px) brightness(50%);;
	-o-filter: blur(3px) brightness(50%);;
	-ms-filter: blur(3px) brightness(50%);;
	filter: blur(3px) brightness(50%);;
	filter: progid:DXImageTransform.Microsoft.Blur(PixelRadius=3, MakeShadow=false);
	/* IE6~IE9 */
	
	width: 100%;
}

.header-say {
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

.ml-10 {
	height: 100%;
	margin-left: 10px;
}

.h33 {
	/* height: 33%; */
	/* padding: 5px; */
	box-sizing: border-box;
	overflow: hidden;
	color: white
}

.header-smallimg {
	width: 100%;
	-webkit-box-align: center;
	height: 76%;
	padding: 5px 0 15px;
	margin-top: 30px;
	margin-left: 10px;
	box-sizing: border-box;
	overflow: hidden;
}

.header-where {
	width: 100%;
	box-sizing: border-box;
	border-top: 1px solid #f2f2f2;
	height: 24%;
	display: -webkit-box;
	-webkit-box-orient: vertical;
	-webkit-box-pack: center
}

.cishu {
	color: white;
	font-size: 13px;
}

.content-say {
	width: 100%;
	box-sizing: border-box;
}

.srdm {
	background: url(ecartoon-weixin/img/shouye/taoke/store@2x.png) no-repeat
		scroll 10px 15px/15px 15px white;
}

.jg {
	background: url(ecartoon-weixin/img/shouye/taoke/price@2x.png) no-repeat
		scroll 10px 15px/15px 15px white;
}

.content-bottombox {
	box-sizing: border-box;
	width: 100%;
	height: 45px;
	line-height: 45px;
	font-size: 14px;
	padding: 0 10px;
	border-bottom: 1px solid #eaeaea;
	color: #545556;
}

.img-card {
	display: block;
	float: left;
}

.jtnr {
	width: 100%;
	padding: 10px 10px;
	margin-top: 10px;
	background: white;
	font-size: 13px;
}

#result {
	display: inline-block;
}

#demo4 {
	width: 100px;
	position: absolute;
	left: 5em;
	top: 0em;
	opacity: 0;
	height: 100%;
	z-index: 33
}

#buy {
	font-size: 15px;
	width: 120px;
	color: white;
	background: #28B2D9;
	display: inline-block;
	height: 44px;
	line-height: 44px;
	float: right;
	text-align: center;
	font-size: 14px;
}

a {
	text-decoration: none;
	color: #000;
}
</style>
</head>

<body>
	<form action="" method="post" id="app">
		<footer class="mui-bar mui-bar-footer font-white"
			style="border:none;background: white;padding-right: 0;padding-left: 0;">
		<div class="font12" style="line-height: 44px;font-size: 14px;color: #fff;background-color: #222C30;text-align: center;">
			设置开始时间 : <span id="result" class="ui-alert corange" style="font-size: 14px;color: #fff;"></span>
			<button id="demo4" data-options='{"type":"date"}'
				class="btn mui-btn mui-btn-block"></button>
			<a href="javascript:goBuy()" id='buy'>立即购买</a>
		</div>
		</footer>
		<div class="mui-content">
			<div class="header-a">
				<div class="header-div">
					<img :src="'picture/'+product.image" class="header-img" />
					<div class="header-say">
						<div class="header-smallimg ">
							<div id="product-img-box" style="width:114px;height:73.2px;overflow: hidden;float: left;">
								<img id="product-img" :src="'picture/'+product.image" width="100%;" class="img-card" />
							</div>
							<div class="fleft ml-10">
								<!-- <div class="h33 ">
									<span class="border-radiu8 font12">已售1</span>
								</div> -->
								<div class="h33 font15" style="margin-top:15px;font-size:14px;height: 30px;">
									{{product.name}}
								</div>
								<div class="h33 font13" style="font-size:12px;">
									教练名称 : {{product.memberName}}
								</div>
							</div>
						</div>
						<!-- <div class="header-where">
							<div class="cishu">暂无简介</div>
						</div> -->
					</div>
				</div>
			</div>
			<div style="margin-top: 10px;">
				<div class="content-bottombox" style="background-color: #FFF;">
					有效期<span class="fright corange" id="moneyDou" style="color: #545556;">{{product.wellNum}}天</span>
				</div>
				<a href="eproductwx!mdfbLogin.asp?id=1"><div
						class="content-bottombox font15" style="background-color: #FFF;">
						价格<span id="fitStore" class="fright cgrey font13" style="color: #28b2d9;">￥{{product.price}}元</span>
					</div></a>
			</div>
			<div class="jtnr ">
				<div>
					<div class="font12" style="font-size: 14px;color: #545556;">
						服务内容
						<div style="padding: 10px;" v-html="product.remark"></div>
					</div>
				</div>
			</div>
		</div>
	</form>
</body>
<script src="miniProgram/coach/js/mui.min.js"></script>
<script src="miniProgram/coach/js/vue.min.js"></script>
<script src="miniProgram/coach/js/jquery.min.js"></script>
<script src="miniProgram/coach/js/jquery.js"></script>
<script src="miniProgram/coach/js/mui.picker.min.js"></script>
<script src="https://res.wx.qq.com/open/js/jweixin-1.3.2.js"></script>
<script src="js/utils.elisa.js"></script>

<script>
	var vue = new Vue({
		el: "#app",
		data: {
			product: {
				name: '',
				image: ''
			},
			time: ''
		},
		created: function(){
			var productId = location.search.split("=")[1];
			$.post("coachmp!getPrivateInfo.asp",{
					id: productId
				},function(res){
					vue.product = JSON.parse(res);
			});
		}
	});
	
	//当用户点击购买按钮时
	function goBuy() {
		var param = {
				productId: vue.product.id,
				productType: 'private',
				image: vue.product.image,
				productName: vue.product.name,
				productPrice: vue.product.price,
				time: vue.time
		}
		
		wx.miniProgram.navigateTo({
			url : '../order/order?json=' + encodeURI(JSON.stringify(param))
		});
	}

	window.onload = function() {
		//处理头部的图片高度
		setTimeout(function(){
			var headeraheight = $(".header-a").width() / 2;
			$('.header-a').css("height", headeraheight);
			var imgwidth = $('.header-a').width();
			var imgHeight = $('.header-img').height();
			var margin1 = (0 - ((imgHeight - headeraheight) / 2));
			$('.header-img').css({'width':$(".header-a").width(),'margin-top':margin1 + "px"});
			
			$('.header-smallimg').css("margin-top", (headeraheight - $('.header-smallimg').height()) / 2 + "px");
			
			var boxHeight = $("#product-img-box").height();
			var productImgHeight = $("#product-img").height();
			var margin2 = (0 - ((productImgHeight - boxHeight) / 2));
			$("#product-img").css("margin-top", margin2 + "px");
		}, 400);
		

		//处理默认开卡时间
		var today = new Date()
		var y = today.getFullYear()
		var M = today.getMonth() + 1
		var d = today.getDate()
		M = add(M)
		d = add(d)

		function add(i) {
			if (i < 10) {
				i = "0" + i;
			}
			return i;
		}
		var time = y + "-" + M + "-" + d;
		var rst = document.getElementById("result");
		rst.innerHTML = time;
		vue.time = time;
	}

	mui.init();

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
				//var picker = new $.DtPicker(options);
				var today = new Date();
				var y = today.getFullYear();
				var M = today.getMonth() + 1;
				var d = today.getDate();
				var picker = new $.DtPicker({//设置日历初始视图模式
					type : "date",//真正的月份比写的多一个月。  type的类型你还是可以选择date, datetime month time  hour 
					beginDate : new Date(),//设置开始日期   
					endDate : new Date(y, M, d)
				//设置结束日期    //真正的是10.21
				});

				picker.show(function(e) {
					/*
					 * rs.value 拼合后的 value
					 * rs.text 拼合后的 text
					 * rs.y 年，可以通过 rs.y.vaue 和 rs.y.text 获取值和文本
					 * rs.m 月，用法同年
					 * rs.d 日，用法同年
					 * rs.h 时，用法同年
					 * rs.i 分（minutes 的第二个字母），用法同年
					 */
					var datetext = e.y.text + "-" + e.m.text + "-" + e.d.text;
					result.innerText = datetext;
					vue.time = datetext;

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