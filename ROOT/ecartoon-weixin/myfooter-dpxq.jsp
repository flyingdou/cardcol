<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

	<head>
		<meta charset="UTF-8">
		<title></title>
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
			.bgW{
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
			
		
			
			.w24 {
				width: 24px;
			}
			.w33{
				width: 33%;
				padding: 0 5px 0 10px;
				overflow: hidden;
				text-align:center;
				box-sizing: border-box;
				
			}
			.itdiv{
				display: -webkit-box;
				-webkit-box-orient: horizontal;
				box-sizing: border-box;
				overflow: hidden;
				margin-right:10px ;
				
			}
			.divA{
				-webkit-box-flex: 1;
				margin-left: 10px;
				overflow: hidden;
			
			}
			
		.mui-table-view-cell:after {
    position: absolute;
    right: 0;
    bottom: 0;
    left: 15px;
    height: 1px;
    content: '';
    -webkit-transform: scaleY(.5);
    transform: scaleY(.5);
    background-color: rgba(0,0,0,0);
}
	.mui-table-view:after {
    position: absolute;
    right: 0;
    bottom: 0;
    left: 0;
    height: 1px;
    content: '';
    -webkit-transform: scaleY(.5);
    transform: scaleY(.5);
    background-color: rgba(0,0,0,0);
}		
.pb10{padding-bottom: 10px;}
		.footer{
			position:fixed;
			left: 0;
			bottom: 0px;
			width: 100%;
            height: 44px;		
            box-sizing: border-box;	
		}
		.footer div{
			
			width: 100%;
			height: 100%;
			line-height: 44px;
			text-align: center;
			background: #FF4401;
			color:white;
             font-size:17px;
             font-weight: 700;
			border-radius: 8px;
		}
		
		</style>
	</head>
	<script type="text/javascript">
         function shuaxin(){
         	
         }
		$(document).ready(function() {
			var ll2w = $(".ll").width() - 42 + "px";
			$(".ll2").css("width", ll2w);
			

				
		})
	</script>

	<body>
	<div id="app">
		<div class="header-a ">
			<div class="header-div ">
				<img src="ecartoon-weixin/img/banner.png " class="header-img " />
				<div class="header-say ">
					<div class="header-smallimg ">
						<img src="ecartoon-weixin/img/banner.png " height="100% " class="img-card " />
						<div class="fleft ml-10 ">
							<div class="h33 "><span class=" font13 ">{{data1.cname}}</span></div>
							<div class="h33 font15 ">
								<div class="icons mui-inline" style="">
									<i data-index="1" class="mui-icon mui-icon-star font15 cG"></i>
									<i data-index="2" class="mui-icon mui-icon-star font15 cG"></i>
									<i data-index="3" class="mui-icon mui-icon-star font15 cG"></i>
									<i data-index="4" class="mui-icon mui-icon-star font15 cG"></i>
									<i data-index="5" class="mui-icon mui-icon-star font15 cG"></i>
								</div>
							</div>
							<div class="h33 font12 ">设备{{data1.deviceScore}}；环境{{data1.evenScore}}；服务{{data1.serviceScore}}</div>
						</div>
					</div>
					<div class="header-where ">
						<div class="cishu ">
							<div class="ll">
								
								<span class="ll2">
									地址：{{data1.address}}
								</span>
								
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		
		
		<div class="mt-10">
			<ul class="mui-table-view">
			    <li class="mui-table-view-cell mui-media">
			        <a href="javascript:;">
			            <img class="mui-media-object mui-pull-left" :src="'eacrtoon-weixin/'+data1.image"style="border-radius: 50%;">
			            <div class="mui-media-body">
			                {{data1.name}}<span class="mui-pull-right cgrey font13">{{data1.EVAL_TIME}}</span>
			                <p class="mui-ellipsis">已健身{{data1.signNum}}次</p>
			            </div>
			        </a>
			    </li>
			</ul>
			<div class="bgW pb10">
				<p class="cb ml-10">{{data1.EVAL_CONTENT}}</p>
				
				<div class="w100 itdiv">
					<div class="divA">
						<img :src="'ecartoon-weixin/'+data1.image1"class="show_img"/>
					</div>
						<div class="divA">
						<img :src="'ecartoon-weixin/'+data1.image2"class="show_img"/>
					</div>
						<div class="divA">
						<img :src="'ecartoon-weixin/'+data1.image3"class="show_img"/>
					</div>
					
				</div>
			</div>
		</div>
		
		<!--分享调制到微信的右上角-->
	<div class="footer">
		<div>分享</div>
	</div>

		<script src="ecartoon-weixin/js/mui.min.js"></script>
		<script src="ecartoon-weixin/js/vue.min.js"></script>
		<script type="text/javascript">
		
		new Vue(
		  {
			  el:"#app",
			   data:{
				   data1:${EvaluateDetail.evaluateDetail}
			   }
		  
		  }
		
		)
        
		
		var contentWebview = null;
	
			//处理头部的图片高度
			var headeraheight = $(".header-a").width() / 2;
			$('.header-a').css("height", headeraheight);
			var imgwidth = $('.header-div').width();
			var imgheight = imgwidth / 2 * 1;
			$('.header-img').css('height', imgheight);
			var imgcardheight = $(".img-card").height();
			var imgcardwidth = imgcardheight;
			$('.img-card').css("width", imgcardwidth);
			//星星的个数
			var index = 3;
			var k = $(".icons").children("i:lt(" + index + ")").addClass("mui-icon-star-filled");
			var plImgW=$(".w33").width()-15+"px";
			$(".w33").css("height",$(".w33").width()-15+"px");
			var plImgW1=$(".w33").width()-15;
	  
	  
	  //图片的高度
				
			 var divAwidth= $('.divA').width()+"px";
			// alert(divAwidth)
	  $('.divA').css("height",divAwidth);
	 var wi= $(".show_img").width();
	 var hi= $(".show_img").height();
	 if(wi>hi){
	 	
	 	$(".show_img").css("height",divAwidth);
        var ml=-($(".show_img").width()-$('.divA').width())/2+"px"
	    $(".show_img").css("margin-left",ml);
	 }else{
	 		$(".show_img").css("width",divAwidth);
        var ml=-($(".show_img").height()-$('.divA').width())/2+"px"
	    $(".show_img").css("margin-top",ml);
	 }
	 

		</script>
		</div>
	</body>

</html>