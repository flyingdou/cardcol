<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!doctype html>
<html>

<head>
<meta charset="UTF-8">
<title>私教套餐详情</title>
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
<link rel="stylesheet" type="text/css" href="sport/css/app.css" />
<link rel="stylesheet" type="text/css" href="sport/css/mui.min.css" />
<link rel="stylesheet" type="text/css" href="sport/css/mui.picker.min.css" />
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
	color: #ff4401
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
	height: 33%;
	padding: 5px;
	box-sizing: border-box;
	overflow: hidden;
	color: white
}

.header-smallimg {
	width: 100%;
	-webkit-box-align: center;
	height: 76%;
	padding: 5px 0 15px;
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
	background:
		url(${pageContext.request.contextPath }/wxv45/images/store@2x.png)
		no-repeat scroll 10px 15px/15px 15px white;
}

.jg {
	background:
		url(${pageContext.request.contextPath }/wxv45/images/price@2x.png)
		no-repeat scroll 10px 15px/15px 15px white;
}

.content-bottombox {
	box-sizing: border-box;
	width: 100%;
	height: 45px;
	line-height: 45px;
	font-size: 14px;
	padding: 0 10px;
	border-bottom: 1px solid #eaeaea;
	text-indent: 20px;
}

.img-card {
	display: block;
	float: left;
}

.jtnr {
	width: 100%;
	padding: 5px 10px;
	margin-top: 10px;
	background: white;
	font-size: 13px;
}

#result {
	text-indent: 2em;
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
	background: #FF4401;
	display: inline-block;
	height: 44px;
	line-height: 44px;
	float: right;
	text-align: center;
}
</style>
</head>

<body>
	<form action="sorderwx!saveProductType.asp" method="post" id="app">
		<footer class="mui-bar mui-bar-footer font-white"
			style="border: none; background: white; padding-right: 0;">
			<div class="font12" style="line-height: 44px;">
				选择开始时间
			    <span id="result" class="ui-alert corange">2017-06-16</span>
				<a id='demo4' data-options='{"type":"date"}' class="btn mui-btn mui-btn-block" style="position:absolute;width: 50%;padding: 0;top:.5em;right: 0;opacity: 0;" href="javascript:void(0)">
					选择日期 
				</a>
				<button type="button" id='buy'>购买</button>
			</div>
		</footer>
		<div class="mui-content ">
			<!-- 套餐详情开始 -->
			<div class="header-a">
				<div class="header-div">
				  <!--  <img src="images/banner.png" class="header-img" width="100%"  />-->
					<div class="header-say ">
						<div class="header-smallimg ">
							<img src="images/banner.png" height="100%" class="img-card" />
							<div class="fleft ml-10">
							</div>
						</div>
						<div class="header-where">
						</div>
					</div>
				</div>
			</div>
			<div style="margin-top: 10px;">
				<div class="content-bottombox jg">
					{{item.name}}
					<span class="fright corange font7 "> 
					</span>
				</div>
			</div>
			<div style="margin-top: 10px;">
				<div class="content-bottombox jg">
					有效期： 
					<span class="fright corange font7 "> 
						{{item.wellNum}} 天
					</span>
				</div>
			</div>
			<div style="margin-top: 10px;">
				<div class="content-bottombox jg">
					价格： <span class="fright corange font7 "> 
					￥  {{item.cost}} 元
					</span>
				</div>
			</div>
			<div class="jtnr ">
					<div>
						服务内容：</br>
							&nbsp;&nbsp;1  本卡不限次数,持卡人享有各俱乐部普通会员正常权益。具体详情请见公告。</br>
							&nbsp;&nbsp;2  持卡人享有适用俱乐部普通会员的正常权益, 各店面具体服务内容遵照该俱乐部执行规定 。</br>
						适用对象：</br>
							&nbsp;&nbsp;本卡仅仅限购卡本人使用， 不得以任何方式转让他人使用。
						<s:property value="freeProject" />
					</div>
				</div>
				<!-- 套餐详情结束 -->
			</div>
			<input type="hidden" name="jsons" id="jsons" />
	</form>
</body>
<script src="sport/js/mui.min.js"></script>
<script src="sport/js/jquery.js"></script>
<script src="sport/js/mui.picker.min.js"></script>
<script src="sport/js/vue.min.js"></script>
<script>
	window.onload = function() {
		//处理头部的图片高度
		var headeraheight = $(".header-a").width() / 2;
		$('.header-a').css("height", headeraheight);
		var imgwidth = $('.header-div').width();
		var imgheight = imgwidth / 2 * 1;
		$('.header-img').css('height', imgheight);
		var imgcardheight = $(".img-card").height();
		var imgcardwidth = imgcardheight / 3 * 4;
		$('.img-card').css("width", imgcardwidth)

		//处理默认开卡时间
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
				var picker = new $.DtPicker({
					type : "date ",//设置日历初始视图模式
					//真正的月份比写的多一个月。  type的类型你还是可以选择date, datetime month time  hour 

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
	var data = ${pageInfo == null ? 0 : pageInfo};
	var data1 = 0;
	if(data != 0){
		data1 = data.items[0];
	}
	var app = new Vue({
		el:"#app",
		data:{
			item:data1
		}
	});
	var time = "${date}"
	var jsons = '[{"product":'+data1.id+',"orderType":1,"productType":1,"quantity":1,"unitPrice":'+data1.cost+',"startTime":"'+time+'"}]';
	$("#jsons").val(jsons);
</script>
</html>