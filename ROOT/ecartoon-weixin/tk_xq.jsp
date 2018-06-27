<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="UTF-8">
<title>淘课详情</title>
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

.f18 {
	font-size: 18px;
}

.fright {
	float: right;
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
	padding-top: 20px;
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

.img-card {
	display: block;
	float: left;
}

.mui-icon-star-filled {
	color: #FF4401;
}

.ll {
	width: 100%;
	height: 100%;
	display: -webkit-box;
	-webkit-box-orient: horizontal;
	line-height: 48px;
}

.ll1 {
	width: 16px;
	overflow: hidden;
}

.ll2 {
	overflow: hidden;
	display: inline-block;
	white-space: nowrap;
	text-overflow: ellipsis;
	height: 30px;
	box-sizing: border-box;
	position: relative
}

.ll2:after {
	position: absolute;
	content: "";
	width: 100%;
	height: 16px;
	top: 16px;
	right: 1px;
	border-right: 1px solid #f2f2f2;
}

.w24 {
	width: 24px;
}
/*	header*/
.kcxx {
	margin-top: 10px;
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

.p10 {
	padding: 10px;
}

.webkitbox {
	display: -webkit-box;
	-webkit-box-orient: horizontal;
	box-sizing: border-box;
	overflow: hidden;
}

.mb50 {
	margin-bottom: 50px;
}

.footer {
	height: 44px;
	text-align: center;
	line-height: 44px;
	position: fixed;
	padding: 0 10px;
	bottom: 10px;
	width: 100%;
}

#submit {
	width: 100%;
	height: 100%;
	color:#fff;
	background: #FF4401;
	border: none;
}

a{
   text-decoration : none;
   color: white;
}
</style>
</head>
<script type="text/javascript">
	function shuaxin() {

	}
	$(document).ready(function() {
		var ll2w = $(".ll").width() - 42 + "px";
		$(".ll2").css("width", ll2w);

	})
</script>

<body>
  <form id="form1" action="eorderwx!saveProductType.asp" method="post">
  	<input type="hidden" name="jsons" value='${courseDetail.json}' />
	<div class="header-a">
		<div class="header-div ">
			<img :src="'picture/'+course.image" class="header-img " />
			<div class="header-say ">
				<div class="header-smallimg ">
					<img :src="'picture/'+course.image" height="100% " class="img-card " />
					<div class="fleft ml-10 ">
						<div class="h33 ">
							<span class=" font13 ">{{course.courseName}}</span>
						</div>
						<div class="h33 font12 ">{{course.clubName}}</div>
					</div>
				</div>
				<div class="header-where ">
					<div class="cishu ">
						<div class="ll">
							<div id="" class="ll1">
								<!-- <span class="mui-icon mui-icon-location font15"
									style="width: 18px;"></span> -->
							</div>
							<span class="ll2"><span class="mui-icon mui-icon-location font15"
									style="width: 18px;"></span>{{course.address==null || course.address==''?"暂无地址信息":course.address}}</span>
							 <a id="phoneNum" class="phoneNum" href="#" >
							 		<span class="mui-icon mui-icon-phone w24"></span>
							 </a>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<div class="kcxx">
		<ul class="mui-table-view">
			<li class="mui-table-view-cell"><a class=""> <span
					class="mui-pull-left"><img
						src="ecartoon-weixin/img/shouye/taoke/time@2x.png" height="21"
						style="margin-right: 5px;" /></span> <span class="mui-pull-left font13">课程时间</span>
					<span class="mui-pull-right cgrey font12">{{course.planDate}}
						{{course.startTime}}-{{course.endTime}}</span>
			</a></li>
			<li class="mui-table-view-cell"><a class=""> <span
					class="mui-pull-left"><img
						src="ecartoon-weixin/img/shouye/taoke/Places@2x.png" height="21"
						style="margin-right: 5px;" /></span> <span class="mui-pull-left font13">剩余名额</span>
					<span class="mui-pull-right cgrey font12">
						{{course.surplusNum == null ? "1" : course.surplusNum}}人
					</span>
			</a></li>
			<li class="mui-table-view-cell"><a class=""> <span
					class="mui-pull-left"><img
						src="ecartoon-weixin/img/shouye/taoke/price@2x.png" height="21"
						style="margin-right: 5px;" /></span> <span class="mui-pull-left font13">课程价格</span>
					<span class="mui-pull-right cgrey font12 colorR">￥<span
						class="font12">{{course.hour_price}}</span>
				</span>
			</a></li>
		</ul>
	</div>

	<div class="kcxx bgW cgrey p10 mb50">
		<div class="cb p5">注意事项</div>
		<div class="font13">
			<div class="webkitbox">
				<div>1.</div>
				<div style="-webkit-box－flex: 1; margin-right: 10px;">购买课程后，请在课程开课前十分钟到俱乐部完成签到。签到方式：进入俱乐部打开“健身E卡通APP或微信公众号”，点击首页的签到按钮进行签到。</div>
			</div>
			<div class="webkitbox">
				<div>2.</div>
				<div>开课十分钟以后不得进入课堂。</div>
			</div>
			<div class="webkitbox" v-if="course.memo != ''">
				<div>3.</div>
				<div>{{course.memo}}</div>
			</div>
		</div>
	</div>
	<div class="footer">
		<input type="button" id="submit" value="购买" @click="checkBuyCourse()" />
	</div>
</form>
	<script src="ecartoon-weixin/js/mui.min.js"></script>
	<script src="ecartoon-weixin/js/vue.min.js"></script>
	<script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
	<script src="js/utils.elisa.js"></script>
	<script type="text/javascript">
		mui.init();

		var contentWebview = null;

		//处理头部的图片高度
		var headeraheight = $(".header-a").width() / 2;
		$('.header-a').css("height", headeraheight);
		var imgwidth = $('.header-div').width();
		var imgheight = imgwidth / 2 * 1;
		$('.header-img').css('height', imgheight);
		var imgcardheight = $(".img-card").height();
		var imgcardwidth = imgcardheight / 3 * 4;
		$('.img-card').css("width", imgcardwidth);
		//星星的个数
		var index = 3;
		var k = $(".icons").children("i:lt(" + index + ")").addClass(
				"mui-icon-star-filled");
	</script>
	<script type="text/javascript">
		$(function(){
			var courseDetail = ${courseDetail};
			var vCourse = new Vue({
				el : "#form1",
				data : {
					course : courseDetail.courseDetail
				},
				methods:{
					checkBuyCourse:function(){
						location.href = "eorderwx!saveProductType.asp?jsons=" + encodeURI(JSON.stringify(${courseDetail.json}));
						
						/* 每月只能购买一次淘课的逻辑
					  $.ajax({
							url:"ecoursewx!checkBuyCourse.asp",
							type:"post",
							data:{},
							dataType:"json",
							success:function(res){
							  if(res.success){
								 location.href = "eorderwx!saveProductType.asp?jsons=" + encodeURI(JSON.stringify(${courseDetail.json}));
							  }else{
								 alert("抱歉!您这个月不能再购买课程"); 
							  } 
							}
						}); */
					}
				},
				created:function(){
					// 分享
					wxUtils.sign("ewechatwx!sign.asp");
					wx.ready(function(){
						wxUtils.share({
							title : "生活多美好，运动乐无穷！这么便宜的健身课，快来体验吧！",
							link : "<%=basePath%>ecoursewx!findCourseDetail.asp"+ location.search,
							img : "<%=basePath%>picture/" + vCourse.course.image,
							desc : "课程名称：" + vCourse.course.name + "；上课地点：" + vCourse.course.address + 
									"；俱乐部名称：" + vCourse.course.clubName + "；课程时间：" + vCourse.course.planDate + 
									" " + vCourse.course.startTime + "-" + vCourse.course.endTime 
						});
				  });
				}
			});
			// 价格保留两位小数
			if(vCourse.course.hour_price != null && vCourse.course.hour_price != ""){
				vCourse.course.hour_price = parseFloat(vCourse.course.hour_price).toFixed(2);
			}else{
				vCourse.course.hour_price = '0.00';
			}
			
			//给电话图标加上电话号码
			var phoneNum = "${courseDetail.courseDetail.mobilephone}";
			$("#phoneNum").attr("href","tel:"+phoneNum);
			 mui('body').on('tap','#phoneNum',function(){
				if(phoneNum == null || phoneNum == ""){
					 alert("暂无联系方式");
				  }else{
					  document.location.href=this.href;
				  }
			  });
			 
			 //判断是否从有效订单进入
			 var orderId = ${orderId};
			 if(orderId != 0){
				 $(".footer").hide();
			 }
		});
	</script>
</body>
</html>