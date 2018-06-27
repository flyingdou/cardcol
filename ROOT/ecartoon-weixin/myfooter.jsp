<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

	<head>
		<meta charset="UTF-8">
		<title>我的足迹</title>
		<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
		<link href="ecartoon-weixin/css/mui.min.css" rel="stylesheet" />
		<script src="ecartoon-weixin/js/vue.min.js"></script>
		<style>
			body {
				background: #f2f2f2;
			}
			
			.mui-content {
				padding: 20px 10px;
			}
			
			.font15 {
				font-size: 15px;
			}
			
			.font13 {
				font-size: 13px;
			}
			
			.font12 {
				font-size: 12px;
			}
			
			.cb {
				color: #1e1e1e;
			}
			
			.cg {
				color: #999999;
			}
			
			.cr {
				color: #FF4401;
			}
			
			.webkitbox {
				margin: 10px 0;
				display: -webkit-box;
				-webkit-box-orient: horizontal;
				width: 100%;
				box-sizing: border-box;
				padding-bottom: 10px;
				position: relative;
			}
				.webkitbox1 {
				margin: 10px 0;
				display: -webkit-box;
				-webkit-box-orient: horizontal;
				width: 100%;
				box-sizing: border-box;
				padding-bottom: 10px;
				position: relative;
			}
			
			.webkitbox:after {
				position: absolute;
				content: "";
				width: 100%;
				height: 66px;
				top: 21px;
				left: 17px;
				border-left: 1px solid #dcdcdc;
			}
			
			.webkitbox:last-child::after {
				position: absolute;
				content: "";
				width: 100%;
				height: 66px;
				top: 21px;
				left: 17px;
				border-left: none;
			}
			
			.mr10 {
				margin-right: 10px;
			}
			
			.bgr {
				background: #ff4401;
				color: white;
				border-radius: 8px;
				line-height: 21px;
				font-size: 12px;
				padding: 0 5px;
				height: 21px;
			}
			
			.bgw {
				background: white;
				-webkit-box-flex: 1;
				padding: 10px;
				border-radius: 8px;
			}
		</style>
	</head>
	<script type="text/javascript" src="ecartoon-weixin/js/jquery.min.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
 
			/* var data = ${myFooter.mySignIn};
		

			var dataL = data.length;
			for(i = 0; i < dataL; i++) {
				var fullDate = data[i].signDate;
				var year = fullDate.substring(0,4);
				//2017-05-20
				var month = fullDate.substring(5,7);
				var day = fullDate.substring(8,10);
				
				  var monthyear = $('<div class="font12 cb"><span class="font15">'+year+'</span> '+month+'月</div>');
				      monthyear.appendTo($(".domL"));
				
				
				    
				
			        if(i < dataL-1){
					var webkitbox = $('<div class="webkitbox "><div class="mr10 bgr">' + day + '日</div><a href="ememberwx!EvaluateDetail.asp?id='+ data[i].id +'> <div class="bgw"><img src="' + 'ecartoon-weixin/'+ data[i].image + '" width="44px" height="44px" class="mui-pull-left" /><div class="mui-pull-left cr font13" style="margin-left: 10px;font-size: 13px;"><p style="margin-bottom: 0px;margin-top: 5px;" class="font15 cb">' + data[i].name + '</p><a href="" class="dis" style="color:#FF4401">我要点评</a><a href="" class="dis1" style="color:#999999"><span  style="color:#ff4401">'+ data[i].TOTALITY_SCORE +'分</span> 设备' + data[i].DEVICE_SCORE + '；环境'  + data[i].EVEN_SCORE + '；服务' + data[i].SERVICE_SCORE + '</a></div></a></div></div>');
					}else if(i=dataL-1){
					var webkitbox = $('<div class="webkitbox1 "><div class="mr10 bgr">' + day + '日</div><a href="ememberwx!EvaluateDetail.asp?id='+ data[i].id +'> <div class="bgw"><img src="' +'ecartoon-weixin/' + data[i].image + '" width="44px" height="44px" class="mui-pull-left" /><div class="mui-pull-left cr font13" style="margin-left: 10px;font-size: 13px;"><p style="margin-bottom: 0px;margin-top: 5px;" class="font15 cb">' + data[i].name + '</p><a href=""class="dis" style="color:#FF4401">我要点评</a><a href="ememberwx!EvaluateDetail.asp?id='+data[i].id+'" class="dis1" style="color:#999999"><span  style="color:#ff4401">'+ data[i].TOTALITY_SCORE +'分</span> 设备' + data[i].DEVICE_SCORE + '；环境' + data[i].EVEN_SCORE + '；服务' + data[i].SERVICE_SCORE + '</a></div></a></div></div>');
					}
					webkitbox.appendTo($(".domL"));
					
					 if(data[i].TOTALITY_SCORE != null) {
							$(".dis").eq($(this).index()).css("display", "none")
						} else{
							$(".dis1").eq($(this).index()).css("display", "none")
						}
					 
			    }
 */
		})
	</script>

	<body>
	<div id = "app">
		<div class="mui-content">
			<div class="domL">
			<div v-for="(v,i) in mySignIn">
			<div class="font12 cb" v-if="xx[i]" ><span class="font15">{{v.year}}</span> {{v.month | changeMonth}}月</div>
			<div class="webkitbox "><div class="mr10 bgr">{{v.day}}日</div>
			
			<a :href="dd[i]"> 
			<div class="bgw">
			<img :src="'picture/'+v.image" width="44px" height="44px" class="mui-pull-left" />
			<div class="mui-pull-left cr font13" style="margin-left: 10px;font-size: 13px;">
			<p style="margin-bottom: 0px;margin-top: 5px;" class="font15 cb">{{v.name}}</p>
			<a href="" class="dis" style="color:#FF4401" v-if="v.TOTALITY_SCORE==null" >我要点评</a>
			<a :href="'ememberwx!EvaluateDetail.asp?id='+v.id" class="dis1" style="color:#999999" v-if="v.TOTALITY_SCORE!=null" >
			<span  style="color:#ff4401" >{{v.TOTALITY_SCORE}}分</span>
			 设备{{v.DEVICE_SCORE}}；环境{{v.EVEN_SCORE}}；服务{{v.SERVICE_SCORE}}
			 </a>
		</div>
		</div>
	</a>
</div>
</div>
</div>
</div>
		</div>
		<script src="ecartoon-weixin/js/mui.min.js"></script>

		<script type="text/javascript">
			mui.init()
			$(function(){
				var data = ${myFooter.mySignIn};
				var dataL = data.length;
				
				var xx = [];
				for(i = 0; i < dataL; i++) {
					var fullDate = data[i].signDate;
					var year = fullDate.substring(0,4);
					//2017-05-20
					var month = fullDate.substring(5,7);
					var day = fullDate.substring(8,10);
					var fullDate1 = null;
					var year1 = null;
					var month1 = null;
					var day1 = null;
					if(i != 0){
					fullDate1 = data[i-1].signDate;
					year1 = fullDate1.substring(0,4);
					//2017-05-20
					 month1 = fullDate1.substring(5,7);
					 day1 = fullDate1.substring(8,10);
					}
					if(fullDate1==null){
						xx.push(true);
					}else if(year1 == year && month1 == month){
						xx.push(false);
					}else if(year1 != year || month1 != month){
						xx.push(true);
					}
				}
				
				var dd = [];
				for(var d = 0; d < data.length; d++){
					if(data[d].TOTALITY_SCORE!=null){
						dd.push("ememberwx!EvaluateDetail.asp?id="+data[d].id);
					}else{
						dd.push("javascript:void(0)");
					}
				}
				
				var myF = new Vue({
					el:"#app",
				  data:{
					  mySignIn:${myFooter.mySignIn},
					  xx:xx,
					  dd:dd
					},
					filters:{
						changeMonth: function (value) {
							var dou = ["一","二","三","四","五","六","七","八","九","十","十一","十二"];
							for(var i = 0; i < 12; i++){
								if (parseInt(value) == (i+1)){
									value = dou[i];
									return value;
								}
							}
						}
					}
					
				})
				
			})
		</script>
		
	</body>
	

</html>