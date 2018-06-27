<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<base href="<%=basePath%>" />
<meta charset="UTF-8">
<title>挑战详情</title>
<meta name="viewport"
	content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
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
	-webkit-filter: blur(13px) brightness(50%);;
	-moz-filter: blur(3px) brightness(50%);;
	-o-filter: blur(3px) brightness(50%);;
	-ms-filter: blur(3px) brightness(50%);;
	filter: blur(3px) brightness(50%);;
	filter: progid:DXImageTransform.Microsoft.Blur(PixelRadius=3, MakeShadow=false);
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

.mui-table-view-cell>a:not (.mui-btn ) {
	position: relative;
	display: block;
	overflow: hidden;
	margin: -10px -10px;
	padding: inherit;
	white-space: nowrap;
	text-overflow: ellipsis;
	color: inherit;
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

.begin {
	margin-top: 10px;
	background: white;
	padding: 10px;
	font-size: 15px;
}

.mui-table-view-cell:after {
	position: absolute;
	right: 0;
	bottom: 0;
	left: 10px;
	height: 1px;
	content: '';
	-webkit-transform: scaleY(.5);
	transform: scaleY(.5);
	background-color: #c8c7cc;
}

.thisli {
	height: 45px !important;
	padding: 12px 10px;
	font-size: 15px;
	color: #1e1e1e;
}

.last {
	margin-top: 6px;
	width: 100%;
	text-align: center;
	line-height: 21px;
	margin-bottom: 55px;
}

.w50 {
	width: 50% !important;
	height: 25px !important;
	padding: 0 !important;
	border: none !important;
	text-align: right;
}

footer {
	height: 44px;
	text-align: center;
	line-height: 44px;
	position: fixed;
	padding: 0 10px;
	bottom: 10px;
	width: 100%;
}

input[type='number']::-webkit-input-placeholder {
	color: #FF4401
}

input[type="submit"] {
	width: 100%;
	height: 100%;
	background: #FF4401;
	border: none;
}

.f13 {
	font-size: 13px
}
</style>
</head>
<body>
	<div class="mui-content" id="app">
		<div class="header-a ">
			<div class="header-div ">
				<img :src="'picture/'+active.image" class="header-img " />
				<div class="show_img">
					<ul class="mui-table-view">
						<li class="mui-table-view-cell mui-media"><a href="javascript:void(0);"> <img
								class="mui-media-object mui-pull-left" :src="'picture/'+active.image">
								<div class="mui-media-body">
									<p class="mui-ellipsis font15">{{active.name}}</p>
									<p class="mui-ellipsis font13">发布人：{{active.member.name}}</p>
									<p class="mui-ellipsis font13">参加人数：{{active.applyCount}}人</p>
								</div>
						</a></li>
					</ul>
				</div>
			</div>
		</div>
		<div class="begin">
			<ul class="mui-table-view">
				<li class="mui-table-view-cell thisli"><span
					class="mui-pull-left"
					style="height: 21px; line-height: 21px; padding: 2px;"><img
						src="ecartoon-weixin/img/target.png" width="15px" alt="" /></span> <span
					class="mui-pull-left">挑战目标</span> <span
					class="mui-pull-right colorR font13">
						{{active.target == 'A' ? active.days+'天减少体重'+active.value+'KG' : (active.target == 'B' ? active.days+'天增加体重'+active.value+'KG' : active.days+'天运动'+active.value+'次')}}
					</span></li>
				<li class="mui-table-view-cell thisli"><span
					class="mui-pull-left"
					style="height: 21px; line-height: 21px; padding: 2px;"><img
						src="ecartoon-weixin/img/reward.png" width="15px" alt="" /></span> <span
					class="mui-pull-left">成功奖励</span> <span
					class="mui-pull-right colorR font13">{{active.award}}</span></li>
				<li class="mui-table-view-cell thisli"><span
					class="mui-pull-left"
					style="height: 21px; line-height: 21px; padding: 2px;"><img
						src="ecartoon-weixin/img/punishment.png" width="15px" alt="" /></span> <span
					class="mui-pull-left">失败惩罚</span> <span
					class="mui-pull-right colorR  font13" style="text-align:right;width:70%;margin-top: -2px;">向{{active.institution.name}}捐款{{active.amerceMoney}}元</span></li>
			</ul>
		</div>
		<div class="begin">
			注意事项
			<p class="f13">
				{{active.memo}}
			</p>
		</div>
		<!-- <div class="last">
			<img src="ecartoon-weixin/img/mine/Look-cool.png" width="33" />
			<img src="img/mine/Look-surprised.png"width="33"alt="挑战正在进行中，加油哦！"/>
			<img src="img/mine/Look-not-happy.png"width="33"alt="您没能大道本次目标，请继续加油哦"/>
			
			<p class="font13">祝你挑战成功！</p>
		</div> -->
	</div>
	<footer id="footerButton"> 
		<input type="submit" onclick="goActive()" name="" id="footer" value="参加挑战" /> 
	</footer>
	<script src="ecartoon-weixin/js/vue.min.js" type="text/javascript"></script>
	<script src="ecartoon-weixin/js/mui.min.js"></script>
	<script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
	<script src="js/utils.elisa.js"></script>
	<script type="text/javascript">
		mui.init()
		//处理头部的图片高度
		var headeraheight = ($(".header-a").width())*(3/4);
		$('.header-a').css("height", headeraheight);
		var imgwidth = $('.header-div').width();
		var imgheight = (imgwidth) * (3/4);
		$('.header-img').css('height', imgheight);
	</script>
	<script type="text/javascript">
		var vue = new Vue({
			el:"#app",
			data:{
				active:${data == null ? 0 : data}
			},
			created:function(){
				var target = this.active.target == 'A' ? this.active.days+'天减少体重'+this.active.value+'KG' : (this.active.target == 'B' ? this.active.days+'天增加体重'+this.active.value+'KG' : this.active.days+'天运动'+this.active.value+'次');
				
				// 分享
				wxUtils.sign("ewechatwx!sign.asp");
 				wx.ready(function(){
 					wxUtils.share({
 						title : "我正在参加【"+ vue.active.name +"】健身挑战，请为我加油！",
 						link : "<%=basePath%>ecoursewx!getActive.asp" + location.search,
 						img : '<%=basePath%>picture/' + vue.active.image,
 						desc : "挑战目标：" + target + "。参与比胜负重要，过程比结果重要，超越比得失重要" 
 					});
 				});
			}
		});
		
		function goActive(){
			location.href="ecoursewx!goActive.asp?id=" + vue.active.id;
		}
		
		//判断是否从有效订单进入
		$(function(){
			var orderId = ${orderId};
			if(orderId != 0){
				$("#footerButton").hide();
			}
		});
	</script>
</body>
</html>