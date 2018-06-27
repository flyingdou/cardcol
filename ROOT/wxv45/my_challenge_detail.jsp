<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!doctype html>
<html>
<head>
<meta charset="UTF-8">
<title>挑战详情</title>
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
<link href="${pageContext.request.contextPath }/eg/css/mui.min.css" rel="stylesheet" />
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

#thisplaceholder::-webkit-input-placeholder {
	color: #FF4401
}

input[type="submit"] {
	width: 100%;
	height: 100%;
	background: #FF4401;
	border: none;
}
</style>

</head>

<body>
	<div class="mui-content" id="app">
		<form action="" v-for="x in datajson">

			<div class="header-a ">
				<div class="header-div ">
					<!-- <img :src="x.imgsrc" class="header-img " /> -->
					<!-- Test Data -->
					<img alt="" src="${pageContext.request.contextPath }/wxv45/images/tzxq.png" class="header-img" >
					<div class="show_img">
						<ul class="mui-table-view">
							<li class="mui-table-view-cell mui-media">
								<a href="javascript:;">
									<!-- <img class="mui-media-object mui-pull-left" :src="x.imgsrc"> -->
									<img alt="" src="${pageContext.request.contextPath }/wxv45/images/tzxq.png" class="mui-media-object mui-pull-left" >
									<div class="mui-media-body">
										<p class="mui-ellipsis font15">{{x.tzmc}}</p>
										<p class="mui-ellipsis font13">发布人：{{x.fbr}}</p>
										<p class="mui-ellipsis font13">参加人数：{{x.num}}人</p>
									</div>
								</a>
							</li>

						</ul>
					</div>
				</div>
			</div>

			<div class="begin">
				<ul class="mui-table-view">
					<li class="mui-table-view-cell thisli">
						<span class="mui-pull-left">开始时间</span>
						<span class="mui-pull-right cG font13">{{x.btime}}</span>
					</li>
					<li class="mui-table-view-cell thisli">
						<span class="mui-pull-left">结束时间</span>
						<span class="mui-pull-right cG font13">{{x.etime}}</span>
					</li>
					<li class="mui-table-view-cell thisli">
						<span class="mui-pull-left">挑战目标</span>
						<span class="mui-pull-right cG font13">{{x.title}}</span>
					</li>
					<li class="mui-table-view-cell thisli">
						<span class="mui-pull-left">开始体重</span>
						<span class="mui-pull-right cG font13">{{x.bweight}}KG</span>
					</li>

				</ul>
			</div>
			<div class="begin">
				<ul class="mui-table-view">
					<li class="mui-table-view-cell thisli">
						<span class="mui-pull-left">当前状态</span>
						<span class="mui-pull-right colorR font13" v-if="x.success==0">成功</span>
						<span class="mui-pull-right colorR font13" v-else-if="x.success==1">进行中</span>
						<span class="mui-pull-right colorR font13" v-else-if="x.success==2">失败</span>
						<span class="mui-pull-right colorR font13" v-else="x.success==3">结束</span>
					</li>
					<li class="mui-table-view-cell thisli">
						<span class="mui-pull-left">健身次数</span>
						<span class="mui-pull-right colorR font13">{{x.num1}}</span>
					</li>
					<li class="mui-table-view-cell thisli">
						<span class="mui-pull-left">结束体重</span>
						<input type="text" class="mui-pull-right colorR font13 w50" name="" id="thisplaceholder" v-model="x.eweight"
							v-if="" placeholder="健身结束后输入体重" />
						<!--<span class="mui-pull-right colorR font13">200KG</span>-->
					</li>
				</ul>
			</div>

			<div class="last">
				<img v-if="x.success==1" src="${pageContext.request.contextPath }/wxv45/images/Look-cool.png" width="33" />
				<img v-if="x.success==0" src="${pageContext.request.contextPath }/wxv45/images/Look-surprised.png" width="33" alt="挑战正在进行中，加油哦！" />
				<img v-if="x.success==2" src="${pageContext.request.contextPath }/wxv45/images/Look-not-happy.png" width="33" alt="您没能大道本次目标，请继续加油哦" />

				<p v-if="x.success==1" class="font13">恭喜你挑战成功！</p>
				<p v-if="x.success==0" class="font13">挑战正在进行，加油！</p>
				<p v-if="x.success==2" class="font13">您没达到本次目标，请继续努力！</p>
			</div>

			<footer v-if="x.success==3">
				<input type="submit" name="" id="" value="提交成绩" />
			</footer>
		</form>

	</div>
	<input id="pageInfo" type="hidden" value="<s:property value='#request.items'/>" />
	<script src="${pageContext.request.contextPath }/eg/js/mui.min.js"></script>
	<script src="https://cdn.bootcss.com/vue/2.2.2/vue.min.js"></script>
	<script src="${pageContext.request.contextPath }/eg/js/jquery-2.1.4.js" type="text/javascript" charset="utf-8"></script>
	<script type="text/javascript">
		var active = jQuery.parseJSON($("#pageInfo").val());
		
		mui.init()
		//处理头部的图片高度
		var headeraheight = $(".header-a").width() / 2;
		$('.header-a').css("height", headeraheight);

		var imgwidth = $('.header-div').width();
		var imgheight = imgwidth / 2 * 1;
		$('.header-img').css('height', imgheight);

		var data1 = [ {
			"imgsrc" : "picture/" + active[0].image,
			"tzmc" : active[0].name,
			"fbr" : active[0].member.name,
			"num" : active[0].applyCount,
			"btime" : active[0].startTime,
			"etime" : active[0].endTime,
			"title" : active[0].name,
			"bweight" : active[0].weight,
			"success" :  active[0].status,
			"num1" : 3,
			"eweight" : active[0].lastWeight
		} ];
		
		new Vue({
			el : "#app",
			data : {
				datajson : data1
			}
		})
	</script>
</body>

</html>