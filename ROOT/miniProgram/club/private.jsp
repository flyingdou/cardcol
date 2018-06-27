<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + path + "/";
%>
<!doctype html>
<html>
<head>
<base href="<%=basePath%>">
<title>智能健身计划引擎</title>
<meta charset="UTF-8">
<meta name="viewport"
	content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
<link  rel="stylesheet" type="text/css" href="expert/css/mui.min.css" />
<style>
html,body{
	height:100%;
	margin:0;
	padding:0;
}
body {
	background: url("expert/img/Private-custom-bg@2x.png") no-repeat;
	background-size:100% auto;
}
.wraper{
	width:100%;
	position: fixed;
	left:0;
	bottom:0;
}
/* 价格部分 */
.box-up{
	margin:0 20px;
	margin-bottom:10px;
	text-align: right;
	color:#fff;
}
/* 按钮部分 */
.box-middle{
	height:40px;
	margin:0 20px;
	margin-bottom:10px;
	line-height:40px;
}
.button{
	display:block;
	width:150px;
	height:40px;
	border:0;
	border-radius:5px;
	line-heigth:40px;
	text-align: center;
	float:left;
}
.button-plan{
	background-image: url("expert/img/My-plan@2x.png");
	background-size:100% 100%;
	font-size:18px;
	color:#000;
}
.button-order{
	float: right;
	background-image: url("expert/img/order@2x.png");
	background-size:100% 100%;
	font-size:18px;
	color:#fff;
}
/* 详情介绍 */
.box-down{
	width:100%;
	height:220px;
	background-color:rgba(0,0,0,0.5);
}
.box-down-header{
	width:100%;
	height:55px;
	overflow: hidden;
}
.box-down-header>img{
	width:40px;
	height:40px;
	float:left;
	margin-top:10px;
	margin-left:22px;
	margin-right:8px;
	border-radius:50%;
}
.box-down-header>div{
	width:auto;
	height:40px;
	float:left;
	margin-top:13px;
	line-height:40px;
	font-size:15px;
	color:#fff;
}
.box-down-footer{
	margin:0 20px;
	color:#fff;
}
.indicator{
	width:14px;
	height:14px;
	border-radius:50%;
	float:left;
	background-color:#fff;
	margin-right:5px;
}
.indicator-active{
	background-color:#FF4401;
}
.ticket{
	position: fixed;
	top: 0;
	height: 0;
	z-index: 1;
	width: 100%;
	height: 100%;
	text-align: center;
}
</style>
</head>
<body>
	<div class="wraper" id="wraper">
		<div class="box-up">
			<span style="font-size:18px;margin-right:-5px;">仅</span>
			<span style="font-size:28px;margin-right:-5px;display:inline-block;width:28px;">￥</span>
			<span style="font-size:55px;margin-right:-5px;">{{price1}}</span>
			<span style="font-size:38px;">{{price2}}</span>
		</div>
		<div class="box-middle">
			<a href="javascript:void(0)" @click="goBuy()" class="button button-order">我要定制</a>
		</div>
		<div class="box-down">
			<div class="box-down-header">
				<img src="http://www.ecartoon.com.cn/picture/wangyan1.jpg" />
				<div>
					<span>王严讲健身</span>
				</div>
			</div>
			<div class="box-down-footer">
				<div class="mui-slider">
				  <div class="mui-slider-group">
				    <!--第一个内容区容器-->
				    <div class="mui-slider-item mui-control-content mui-active" style="font-size:12px;">
				      	<span><span style="font-size:15px;"><b>专家介绍:</b></span>&nbsp;&nbsp;
				      		{{data1.item.summary}}
				      	</span>
				    </div>
				    <!--第二个内容区-->
				    <div class="mui-slider-item mui-control-content" style="overflow: hidden;">
				    	<div style="float:left;margin-right:15px;font-size:15px;">
				    		<b>产品简介:</b>
				    	</div>
				    	<div style="float:left;font-size:12px;">
				    		 <span>计划类型: &nbsp;瘦身减重,健美增肌,提高运动表现</span><br/>
				      	 <span>适用对象: &nbsp;初级,中级</span><br/>
				      	 <span>计划周期: &nbsp;4周</span><br/>
				      	 <span>使用场景: &nbsp;健身房</span><br/>
				      	 <span>所需器材: &nbsp;各种力量训练器材</span>
				    	</div>
				    </div>
				  </div>
				</div>
				<div style="overflow: hidden;width:50px;margin:0 auto;margin-top:20px;">
				    <div class="indicator indicator-active"></div>
				    <div class="indicator"></div>
				</div>
			</div>
		</div>
		<div class="ticket" onclick="clickBody()" hidden="hidden">
			<img class="hongbao" src="expert/img/hongbao.png" style="width: 298.4px;height: 478.2px;">
		</div>
	</div>
</body>
<script src='expert/js/mui.min.js' ></script>
<script src='expert/js/vue.min.js' ></script>
<script src='expert/js/jquery.min.js' ></script>
<script src="https://res.wx.qq.com/open/js/jweixin-1.3.2.js"></script>
<script src="js/utils.elisa.js"></script>
<script src="expert/js/swipe.min.js"></script>
<script>
	/*页面初始化*/
	mui.init();

	var vue = new Vue({
		el : "#wraper",
		data : {
			data1: {
				item: {
					summary: ""
				}
			},
			base_path: location.origin,
			price1: "0",
			price2: ".00",
			params: []
		},
		created:function(){
			
			var bodyHeight = $("body").height();
			var imageHeight = $(".hongbao").eq(0).height();
			var marginTop = (bodyHeight - imageHeight) / 2
			$(".hongbao").css({"margin-top":marginTop + "px"});
			
			// 获取数据
			$.post("expertex!loadPlan.asp",{
				
				},function(result){
				 	vue.data1 = JSON.parse(result);
					// 初始化价格
					var price = vue.data1.item.price.toString();
					if(price.split(".")[1] > 0){
						vue.price1 = price;
						vue.price2 = "";
					} else{
						vue.price1 = price.split(".")[0];
					}
			});
			// 分享
			wxUtils.sign("ewechatwx!sign.asp");
			wx.ready(function(){
				wxUtils.share({
					title : "【王严健身专家系统】为您私人定制健身计划",
					link : "<%=basePath%>ecoursewx!loadPlan.asp" + location.search,
					img : "<%=basePath%>picture/wangyan1.jpg",
					desc : "${data.item.summary}"
				});
		  });
		},
		methods:{
			goBuy: function(){
				wx.miniProgram.navigateTo({url: '../setting/setting'});
			}
		}
	});
	
	$("body").rhuiSwipe("swipeLeft",function(){
		$(".indicator").eq(0).removeClass("indicator-active");
		$(".indicator").eq(1).addClass("indicator-active");
	});
  $("body").rhuiSwipe("swipeRight",function(){
		$(".indicator").eq(1).removeClass("indicator-active");
		$(".indicator").eq(0).addClass("indicator-active");
	});
  
  function clickBody(){
	  $(".ticket").hide();
  }
	
</script>
</html>