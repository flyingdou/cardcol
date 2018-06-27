<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
  String path = request.getContextPath();
  String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<base href="<%=basePath%>">
<meta charset="UTF-8">
<title>${club.club.name}</title>
<meta name="format-detection" content="telephone=no">
<meta http-equiv="x-rim-auto-match" content="none">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
<link href="ecartoon-weixin/css/mui.min.css" rel="stylesheet" />
<script src="http://libs.baidu.com/jquery/2.1.4/jquery.min.js" type="text/javascript" charset="utf-8"></script>

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

#dou11{
   margin-top: 14px;
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

.fw_next {
	box-sizing: border-box;
	overflow: hidden;
	padding: 0 10px;
	width: 100%;
	height: 44px;
	line-height: 44px;
	background: white;
	position: relative;
}

.fw_next:after {
	position: absolute;
	content: "";
	width: 100%;
	height: 100%;
	left: 10px;
	bottom: 0px;
	border-bottom: 1px solid #f2f2f2;
}

.mui-segmented-control.mui-scroll-wrapper .mui-control-item {
	padding: 2px 10px;
}

.hei70 {
	height: 70px;
}

.scroll_a {
	height: 70px;
	background: white !important;
	line-height: 16px !important;
	border-bottom: none !important;
	padding-top: 5px !important;
}

.scroll_title {
	width: 60px;
	height: 21px;
	margin: 0;
	white-space: nowrap;
	text-overflow: ellipsis;
	box-sizing: border-box;
	overflow: hidden;
}

.mui-scroll-box {
	height: 70px !important;
	background: white;
}

.fw {
	margin: 10px 0;
}

.pj {
	width: 100%;
	height: 44px;
}

.ojA {
	color: #999999 !important;
	line-height: 22px !important;
	font-size: 13px;
	height: 44px;
}

.ojA div {
	line-height: 15px !important;
}

.mui-segmented-control.mui-segmented-control-inverted .mui-control-item.mui-active
	{
	background: white;
	color: #1e1e1e !important;
}

.mt12 {
	margin-top: 5px;
}

.webkit-box {
	margin-top: 5px;
	display: -webkit-box;
	-webkit-box-orient: horizontal;
	width: 100% !important;
	text-align: center;
}

.childbox {
	-webkit-box-flex: 1;
}

.scroll_ul {
	background: #eaeaea;
	border-top: 1px solid #eaeaea;
}

.scroll_li {
	padding-right: 10px !important;
	padding-left: 10px !important;
	margin-bottom: 10px;
	background: white;
}

.scroll_ul-li-a {
	margin: 0 !important;
	padding: 0 !important;
}

#mdd{
	color:white;
	
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
        <div id="app">
		<div id="pullrefresh" class="mui-content  mui-scroll-wrapper">
			<div class="mui-scroll">
				<div class="header-a ">
					<div class="header-div ">
						<img src="picture/${club.club.image}" class="header-img imgdou" />
						<div class="header-say ">
							<div class="header-smallimg ">
								<img src="picture/${club.club.image}"  height="100% " class="img-card  imgdou" />
								<div class="fleft ml-10 ">
									<div class="h33 "><span class=" font13 ">{{club.signCount}}人来过</span></div>
									<div class="h33 font15 ">
										<div class="icons mui-inline" style="">
											<i data-index="1" class="mui-icon mui-icon-star font15 cG"></i>
											<i data-index="2" class="mui-icon mui-icon-star font15 cG"></i>
											<i data-index="3" class="mui-icon mui-icon-star font15 cG"></i>
											<i data-index="4" class="mui-icon mui-icon-star font15 cG"></i>
											<i data-index="5" class="mui-icon mui-icon-star font15 cG"></i>
										</div>
									</div>
									<div class="h33 font12 ">设备{{club.deviceScore}}；环境{{club.evenScore}}；服务{{club.serviceScore}}</div>
								</div>
							</div>
							<div class="header-where ">
								<div class="cishu ">
									<div class="ll">
										<div id="" class="ll1">
										  <div id = "dou11">
											<span class="mui-icon mui-icon-location font15" style="width: 18px;"></span>
										  </div>
										</div>
										<span class="ll2">
											地址：{{club.address}}
								        </span>
										   <a id = "mdd" class = "xdd">
										      <span class="mui-icon mui-icon-phone w24"></span>
										   </a>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="fw">
					<div class="fw_next clear">
						<span class="font13 cb fleft">服务项目</span>
						<span class="cgrey font12 fright">{{courseCount}}项目</span>
					</div>
					<div class="act_show">
						<div class="mui-scroll-wrapper mui-slider-indicator mui-segmented-control mui-segmented-control-inverted mui-scroll-box">
							<div class="mui-scroll hei70">
								<a class="mui-control-item mui-active scroll_a" v-for="v in courseList">
									<img :src="'picture/'+v.image" width="60px" height="44px" alt="" />
									<div class="font12 cgrey scroll_title">
										{{v.name}}
									</div>
								</a>
								
							</div>
						</div>
					</div>
				</div>
				<div class="pj">
					<div class="mui-slider">
						<div class="mui-slider-indicator mui-segmented-control mui-segmented-control-inverted">
							<a class="mui-control-item bgW ojA  mui-active" href="#item1">
								好评
								<div class="">
									({{evalCount1}})
								</div>
							</a>
							<a class="mui-control-item bgW ojA" href="#item2">
								中评
								<div class="">
									({{evalCount2}})
								</div>
							</a>
							<a class="mui-control-item bgW ojA" href="#item3">
								差评
								<div class="">
									({{evalCount3}})
								</div>
							</a>
						</div>

					</div>

				</div>

				<div class="mui-slider-group">
				   <div id="item1" class="mui-slider-item mui-control-content mui-active">
                     <div v-for="v1 in evaluateList1">
						<ul class="mui-table-view mui-table-view-chevron scroll_ul mui-table-view1 mui-table-view-cell1">
							<li class="mui-table-view-cell mui-media scroll_li">
								<a href="item1" class="scroll_ul-li-a">
									<img class="mui-media-object mui-pull-left imgA" :src="'picture/'+v1.image" width="40px" height="40px" />
									<div class="mui-media-body font15">
										{{v1.mName}}
										<div class="icons dou1 mui-inline" style="line-height: 18px;">
											<i data-index="1" class="mui-icon mui-icon-star  font15 cG"></i>
											<i data-index="2" class="mui-icon mui-icon-star  font15 cG"></i>
											<i data-index="3" class="mui-icon mui-icon-star  font15 cG"></i>
											<i data-index="2" class="mui-icon mui-icon-star  font15 cG"></i>
											<i data-index="3" class="mui-icon mui-icon-star  font15 cG"></i>

										</div>
										<span class=" mui-pull-right cG font12">{{v1.evalTime}}</span>
										<p class='mui-ellipsis font13'>健身{{v1.signNum}}次    本次评分{{v1.totality_score}}</p>
									</div>
									<p class="font14 mt12 cB mui-ellipsis">{{v1.eval_content}}</p>
									<div class="webkit-box">
										<div class="imgB childbox"><img :src="'picture/'+v1.image1" class="imgB" /></div>
										<div class="imgB childbox"><img :src="'picture/'+v1.image2" class="imgB" /></div>
										<div class="imgB childbox"><img :src="'picture/'+v1.image3" class="imgB" /></div>
									</div>
								</a>
							</li>
						</ul>
				    </div>
				</div>

					<div id="item2" class="mui-slider-item mui-control-content ">
					  <div v-for="v2 in evaluateList2">
						<ul class="mui-table-view mui-table-view-chevron scroll_ul">
							<li class="mui-table-view-cell mui-media scroll_li mui-table-view2 mui-table-view-cell2">
								<a class="scroll_ul-li-a">
									<img class="mui-media-object mui-pull-left imgA" :src="'picture/'+v2.image" width="40px" height="40px" />
									<div class="mui-media-body font15">
										{{v2.mName}}
										<div class="icons dou2 mui-inline" style="line-height: 18px;">
											<i data-index="1" class="mui-icon mui-icon-star  font15 cG"></i>
											<i data-index="2" class="mui-icon mui-icon-star  font15 cG"></i>
											<i data-index="3" class="mui-icon mui-icon-star  font15 cG"></i>
											<i data-index="2" class="mui-icon mui-icon-star  font15 cG"></i>
											<i data-index="3" class="mui-icon mui-icon-star  font15 cG"></i>

										</div>
										<span class=" mui-pull-right cG font12">{{v2.evalTime}}</span>
										<p class='mui-ellipsis font13'>健身{{v2.signNum}}次    本次评分{{v2.totality_score}}</p>
									</div>
									<p class="font14 mt12 cB mui-ellipsis">{{v2.eval_content}}</p>
									<div class="webkit-box">
										<div class="imgB childbox"><img :src="'picture/'+v2.image1" class="imgB" /></div>
										<div class="imgB childbox"><img :src="'picture/'+v2.image2" class="imgB" /></div>
										<div class="imgB childbox"><img :src="'picture/'+v2.image3" class="imgB" /></div>
									</div>
								</a>
							</li>
						</ul>
					   </div>
					</div>

					<div id="item3" class="mui-slider-item mui-control-content ">
 					  <div v-for="v3 in evaluateList3">
						<ul class="mui-table-view mui-table-view-chevron scroll_ul">
							<li class="mui-table-view-cell mui-media scroll_li mui-table-view3 mui-table-view-cell3">
								<a class="scroll_ul-li-a">
									<img class="mui-media-object mui-pull-left imgA" :src="'picture/'+v3.image" width="40px" height="40px" />
									<div class="mui-media-body font15">
										{{v3.mName}}
										<div class="icons dou3 mui-inline" style="line-height: 18px;">
											<i data-index="1" class="mui-icon mui-icon-star  font15 cG"></i>
											<i data-index="2" class="mui-icon mui-icon-star  font15 cG"></i>
											<i data-index="3" class="mui-icon mui-icon-star  font15 cG"></i>
											<i data-index="4" class="mui-icon mui-icon-star  font15 cG"></i>
											<i data-index="5" class="mui-icon mui-icon-star  font15 cG"></i>

										</div>
										<span class=" mui-pull-right cG font12">{{v3.evalTime}}</span>
										<p class='mui-ellipsis font13'>健身{{v3.signNum}}次    本次评分{{v3.totality_score}}</p>
									</div>
									<p class="font14 mt12 cB mui-ellipsis">{{v3.eval_content}}</p>
									<div class="webkit-box">
										<div class="imgB childbox"><img :src="'picture/'+v3.image1" class="imgB" /></div>
										<div class="imgB childbox"><img :src="'picture/'+v3.image2" class="imgB" /></div>
										<div class="imgB childbox"><img :src="'picture/'+v3.image3" class="imgB" /></div>
									</div>
								</a>
							</li>
						</ul>
					</div>
				</div>
				
				</div>
				
				<%-- var index1 = ${club.evaluate1[0].totality_score};
			    var k = $(".dou1").children("i:lt(" + index1 + ")").addClass("mui-icon-star-filled"); --%>

			</div>
		</div>

		<script src="ecartoon-weixin/js/mui.min.js"></script>
        <script src="ecartoon-weixin/js/vue.min.js"></script>
        <script>
          $(function(){
        	  var imgdou = $(".imgdou");
              var imgsrc = "${club.club.image}";
              if(imgsrc == null || imgsrc == ""){
            	 imgdou[0].attr("src","ecartoon-weixin/img/banner.png");
            	 imgdou[1].attr("src","ecartoon-weixin/img/banner.png");
              }
			   var phoneNum = "${club.club.mobilephone}";
			   $("#mdd").attr("href","tel:"+phoneNum);
			   mui('body').on('tap','.xdd',function(){
				   if(phoneNum == null || phoneNum == ""){
					   alert("暂无联系方式");
				   }else{
					   document.location.href=this.href;
				   }
				   });
				   
		      document.cookie = "id>1";
			  
          })
          
		  
        </script>
      
		<script type="text/javascript">
		//vue绑定后台数据
		var dou = new Vue({
			el:"#app",
			data:{
				club:${club.club},
				courseList:${club.course},
				courseCount:${club.courseCount},
				evaluateNum:${club.evaluateNum},
				evaluateList1:${club.evaluate1},
				evaluateList2:${club.evaluate2},
				evaluateList3:${club.evaluate3},
				evalCount1:${club.evalCount1},
				evalCount2:${club.evalCount2},
				evalCount3:${club.evalCount3}
			}
		})
		
		
		//星星的个数
		var index = ${club.club.star};
		var k = $(".icons").children("i:lt(" + index + ")").addClass("mui-icon-star-filled");
		
		//每个好评者的星星
		$(".dou1").each(function (i,item) {
			  var index1 = dou.evaluateList1[i].totality_score;
			  $(item).children("i:lt("+( index1 )+")").addClass("mui-icon-star-filled");
		});
		
		//每个中评的星星
		$(".dou2").each(function (i,item) {
			  var index2 = dou.evaluateList2[i].totality_score;
			  $(item).children("i:lt("+( index2 )+")").addClass("mui-icon-star-filled");
		});
		
		//每个差评者的星星
		$(".dou3").each(function (i,item) {
			  var index3 = dou.evaluateList3[i].totality_score;
			  $(item).children("i:lt("+( index3 )+")").addClass("mui-icon-star-filled");
		});
		
		
		
		
			//处理头部的图片高度
			var headeraheight = $(".header-a").width() / 2;
			$('.header-a').css("height", headeraheight);
			var imgwidth = $('.header-div').width();
			var imgheight = imgwidth / 2 * 1;
			$('.header-img').css('height', imgheight);
			var imgcardheight = $(".img-card").height();
			var imgcardwidth = imgcardheight;
			$('.img-card').css("width", imgcardwidth);
			
			

			mui.init({
				swipeBack: false,
				pullRefresh: {
					container: '#pullrefresh',
					/* up: {
						contentrefresh: '正在加载...',
						callback: pullupRefresh
					} */

				},

			});

			var count = 0;
			var indd = 0;
			$(".ojA").click(function() {
				indd = $(this).index()
				
			})
		var imgh = ($(".webkit-box").width() - 40) / 3 + "px";
			$('.imgB').css({
				"height": imgh,
				"width": imgh
			});
			/**
			 * 上拉加载具体业务实现
			 */
			function pullupRefresh() {

				if(indd==0) {
					
					setTimeout(function() {
						mui('#pullrefresh').pullRefresh().endPullupToRefresh((++count > 2)); //参数为true代表没有更多数据了。
						var table = document.body.querySelector('.mui-table-view1');
						var cells = document.body.querySelectorAll('.mui-table-view-cell1');
						for(var i = cells.length, len = i + 20; i < len; i++) {
							var li = document.createElement('li');
							li.className = 'mui-table-view-cell mui-media scroll_li';
							li.innerHTML = '<a href="" class="scroll_ul-li-a" ><img class="mui-media-object mui-pull-left imgA" src="img/banner.png" width="40px" height="40px" /><div class="mui-media-body font15">幸福1<div class="icons mui-inline" style="line-height: 18px;"><i data-index="1" class="mui-icon mui-icon-star mui-icon-star-filled font15 cG"></i><i data-index="2" class="mui-icon mui-icon-star mui-icon-star-filled font15 cG"></i><i data-index="3" class="mui-icon mui-icon-star  mui-icon-star-filled font15 cG"></i></div><span class=" mui-pull-right cG font12">1312</span><p class="mui-ellipsis font13">1111</p></div><p class="font14 mt12 cB mui-ellipsis">认真几把多1</p><div class="webkit-box"><div class="imgB childbox"><img src="img/banner.png"class="imgB"/></div><div class="imgB childbox"><img src="img/banner.png"class="imgB"/></div><div class="imgB childbox"><img src="img/banner.png"class="imgB"/></div></div></a>'

							table.appendChild(li);
							

							$('.imgB').css({
								"height": imgh,
								"width": imgh
							});
						}
					}, 1000);
				} else if(indd==1) {
					alert(2)
					setTimeout(function() {
						mui('#pullrefresh').pullRefresh().endPullupToRefresh((++count > 2)); //参数为true代表没有更多数据了。
						var table = document.body.querySelector('.mui-table-view2');
						var cells = document.body.querySelectorAll('.mui-table-view-cell2');
						for(var i = cells.length, len = i + 20; i < len; i++) {
							var li = document.createElement('li');
							li.className = 'mui-table-view-cell mui-media scroll_li';
							li.innerHTML = '<a href="" class="scroll_ul-li-a" ><img class="mui-media-object mui-pull-left imgA" src="img/banner.png" width="40px" height="40px" /><div class="mui-media-body font15">幸福2<div class="icons mui-inline" style="line-height: 18px;"><i data-index="1" class="mui-icon mui-icon-star mui-icon-star-filled font15 cG"></i><i data-index="2" class="mui-icon mui-icon-star mui-icon-star-filled font15 cG"></i><i data-index="3" class="mui-icon mui-icon-star  mui-icon-star-filled font15 cG"></i></div><span class=" mui-pull-right cG font12">1312</span><p class="mui-ellipsis font13">1111</p></div><p class="font14 mt12 cB mui-ellipsis">认真几把多1</p><div class="webkit-box"><div class="imgB childbox"><img src="img/banner.png"class="imgB"/></div><div class="imgB childbox"><img src="img/banner.png"class="imgB"/></div><div class="imgB childbox"><img src="img/banner.png"class="imgB"/></div></div></a>'

							table.appendChild(li);
							//var imgh = ($(".webkit-box").width() - 40) / 3 + "px";

							$('.imgB').css({
								"height": imgh,
								"width": imgh
							});
						}
					}, 1000);
				} else if(indd==2) {
					alert(3)
					setTimeout(function() {
						mui('#pullrefresh').pullRefresh().endPullupToRefresh((++count > 2)); //参数为true代表没有更多数据了。
						var table = document.body.querySelector('.mui-table-view3');
						var cells = document.body.querySelectorAll('.mui-table-view-cell3');
						for(var i = cells.length, len = i + 20; i < len; i++) {
							var li = document.createElement('li');
							li.className = 'mui-table-view-cell mui-media scroll_li';
							li.innerHTML = '<a href="" class="scroll_ul-li-a" ><img class="mui-media-object mui-pull-left imgA" src="img/banner.png" width="40px" height="40px" /><div class="mui-media-body font15">幸福3<div class="icons mui-inline" style="line-height: 18px;"><i data-index="1" class="mui-icon mui-icon-star mui-icon-star-filled font15 cG"></i><i data-index="2" class="mui-icon mui-icon-star mui-icon-star-filled font15 cG"></i><i data-index="3" class="mui-icon mui-icon-star  mui-icon-star-filled font15 cG"></i></div><span class=" mui-pull-right cG font12">1312</span><p class="mui-ellipsis font13">1111</p></div><p class="font14 mt12 cB mui-ellipsis">认真几把多1</p><div class="webkit-box"><div class="imgB childbox"><img src="img/banner.png"class="imgB"/></div><div class="imgB childbox"><img src="img/banner.png"class="imgB"/></div><div class="imgB childbox"><img src="img/banner.png"class="imgB"/></div></div></a>'

							table.appendChild(li);
							//var imgh = ($(".webkit-box").width() - 40) / 3 + "px";

							$('.imgB').css({
								"height": imgh,
								"width": imgh
							});
						}
					}, 1000);
				} else {
					mui('#pullrefresh').pullRefresh().disablePullupToRefresh();
				}

			}

		
		</script>
		</div>
	</body>

</html>