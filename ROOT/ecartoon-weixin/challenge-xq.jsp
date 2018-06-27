<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
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

.mui-table-view-cell
>
a
:not
 
(
.mui-btn
 
)
{
position
:
 
relative
;

	
display
:
 
block
;

	
overflow
:
 
hidden
;

	
margin
:
 
-10
px
 
-10
px
;

	
padding
:
 
inherit
;

	
white-space
:
 
nowrap
;

	
text-overflow
:
 
ellipsis
;

	
color
:
 
inherit
;


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

.submitButton {
	width: 100%;
	height: 100%;
	background-color: #FF4401;
	border: none;
}
</style>
</head>
<body>
	<div class="mui-content" id="app">
		<form action="" id="" method="post">
			<div class="header-a ">
				<div class="header-div ">
					<img :src="'picture/'+data1.image" class="header-img " />
					<div class="show_img">
						<ul class="mui-table-view">
							<li class="mui-table-view-cell mui-media"><a
								href="javascript:void(0);"> <img
									class="mui-media-object mui-pull-left" :src="'picture/'+data1.image">
									<div class="mui-media-body">
										<p class="mui-ellipsis font15">{{data1.name}}</p>
										<p class="mui-ellipsis font13">发布人：{{data1.member.name}}</p>
										<p class="mui-ellipsis font13">参加人数：{{data1.applyCount}}人</p>
									</div>
							</a></li>
						</ul>
					</div>
				</div>
			</div>

			<div class="begin">
				<ul class="mui-table-view">
					<li class="mui-table-view-cell thisli"><span
						class="mui-pull-left">开始时间</span> <span
						class="mui-pull-right cG font13">{{data1.orderStartTime}}</span></li>
					<li class="mui-table-view-cell thisli"><span
						class="mui-pull-left">结束时间</span> <span
						class="mui-pull-right cG font13">{{data1.orderEndTime}}</span></li>
					<li class="mui-table-view-cell thisli"><span
						class="mui-pull-left">挑战目标</span> <span
						class="mui-pull-right cG font13">
						{{data1.target == 'A' ? data1.days+'天减少体重'+data1.value+'KG' : (data1.target == 'B' ? data1.days+'天增加体重'+data1.value+'KG' : data1.days+'天运动'+data1.value+'次')}}
						</span></li>
					<li class="mui-table-view-cell thisli" v-if="target == 'A' || target == 'B'"><span
						class="mui-pull-left">开始体重</span> <span
						class="mui-pull-right cG font13">{{data1.weight}}KG</span></li>
				</ul>
			</div>
			<div class="begin">
				<ul class="mui-table-view">
					<li class="mui-table-view-cell thisli"><span
						class="mui-pull-left">当前状态</span> <span
						class="mui-pull-right colorR font13" v-if="data1.result==1">成功</span>
						<span class="mui-pull-right colorR font13"
						v-else-if="data1.result==0">进行中</span> <span
						class="mui-pull-right colorR font13" v-else-if="data1.result==2">失败</span>
						<span class="mui-pull-right colorR font13" v-else="data1.result==3">结束</span>
					</li>
					<li class="mui-table-view-cell thisli" v-if="target == 'C' || target == 'D'"><span
						class="mui-pull-left">健身次数</span> <span
						class="mui-pull-right colorR font13">{{data1.activeCount}}</span></li>
					<li class="mui-table-view-cell thisli" v-if="target == 'A' || target == 'B'"><span
						class="mui-pull-left">结束体重</span> <input type="text"
						class="mui-pull-right colorR font13 w50" name=""
						id="thisplaceholder" v-model="model.weight" 
						placeholder="挑战结束后输入体重" v-show="data1.result==3"/> 
						<span class="mui-pull-right colorR font13" v-show="data1.result != 3">{{lastWeight}}</span>
					</li>
				</ul>
			</div>
			<div class="last">
				<img v-show="data1.result==1" src="ecartoon-weixin/img/mine/Look-cool.png" width="33" />
				<img v-show="data1.result==0" src="ecartoon-weixin/img/mine/Look-surprised.png"
					width="33" alt="挑战正在进行中，加油哦！" /> <img v-show="data1.result==2"
					src="ecartoon-weixin/img/mine/Look-not-happy.png" width="33" alt="您没能达道本次目标，请继续加油哦" />

				<p v-show="data1.result==0" class="font13">挑战正在进行，加油！</p>
				<p v-show="data1.result==1" class="font13">恭喜你挑战成功！</p>
				<p v-show="data1.result==2" class="font13">您没达到本次目标，请继续努力！</p>
			</div>
			<footer v-show="data1.result==3"> 
				<input type="button" class="submitButton" value="提交成绩" @click="submitWeight()" style="background-color: #ff4401;color:#fff;"/> 
			</footer>
		</form>
	</div>
	<script src="ecartoon-weixin/js/mui.min.js"></script>
	<script src="ecartoon-weixin/js/vue.min.js" type="text/javascript"></script>
	<script type="text/javascript">
		mui.init()
		//处理头部的图片高度
		var headeraheight = $(".header-a").width() / 2;
		$('.header-a').css("height", headeraheight);

		var imgwidth = $('.header-div').width();
		var imgheight = imgwidth / 2 * 1;
		$('.header-img').css('height', imgheight);

		/*状态码success:0 进行中,1 成功,2 失败,3 结束*/
	 var vue = new Vue({
			el : "#app",
			data : {
				data1 : ${data == null ? 0 : data},
				target : "${data.target}",
				lastWeight : "挑战结束后输入体重",
				model : {}
			},
			methods:{
				submitWeight:function(){
					$.ajax({
						url:"ecoursewx!submitWeight.asp",
						type:"post",
						data:{
							weight : vue.model.weight,
							id : vue.data1.orderId
						},
						dataType:"json",
						success:function(res){
							vue.data1.result = res.code;
							setTimeout(function(){
								location.href = "ecoursewx!myActive.asp?type=2";
							},100);
						},
						error:function(e){
							alert("网络异常 !");
						}
					});
				}
			},
			created:function(){
				if(this.data1.result == 1 || this.data1.result == 2){
					this.lastWeight = this.data1.lastWeight + "KG";
				}
			}
		});
	</script>
</body>
</html>