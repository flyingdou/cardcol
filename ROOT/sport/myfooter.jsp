<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%  
String path = request.getContextPath();  
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";  
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

	<head>
		<meta charset="UTF-8">
		<title>我的足迹</title>
		<base href="<%=basePath %>" >
		<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
		<link href="sport/css/mui.min.css" rel="stylesheet" />
		<script src="sport/js/vue.min.js"></script>
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
				text-align:center;
				border-radius: 8px;
				line-height: 21px;
				font-size: 12px;
				padding: 0 5px; 
				margin : 20px;
				height: 21px;
			}
			
			.bgw {
				background: white;
				-webkit-box-flex: 1;
				padding-top:20px;
				padding-left:10px;
				border-radius: 8px;
			}
			
		</style>
	</head>
	<script type="text/javascript" src="sport/js/jquery.min.js"></script>

	<body>
<div id = "app">
	<div class="mui-content">
		<div class="domL">
			<div v-for="(v,i) in mySignIn">
				<div class="font12 cb" v-if="xx[i]" ><span class="font15">{{v.year}}</span> {{v.month}}月</div>
			
				<div class="webkitbox">
				
					<div>
					    <div class="mr10 bgr" >
							{{v.day}}日 <p style="color: blue; font-size: 16px; padding-top: 10px;">第{{v.count}}次</p>
						</div>
					</div>
			
					<div class="bgw" >
						<img :src="'sport/'+dd[i]" width="44px" height="44px" class="mui-pull-left" />
						<div class="mui-pull-left cr font13" style="margin-left: 10px;font-size: 13px;">
							<p>最高运动心率:{{v.heartRates}} 次/分钟</p>
							<p :class ="v.xx"  >评价信息</p>
						</div>
		    		</div>
		    
				</div>
			</div>
		</div>
	</div>
</div>
		<script src="sport/js/mui.min.js"></script>
		<script type="text/javascript">
			mui.init()
			$(function(){
				var data = ${myRate.phrList};
				var dataL = data.length;
				
				var xx = [];
				for(i = 0; i < dataL; i++) {
					var fullDate = data[i].train_date;
					var year = fullDate.substring(0,4);
					//2017-05-20
					var month = fullDate.substring(5,7);
					var day = fullDate.substring(8,10);
					var fullDate1 = null;
					var year1 = null;
					var month1 = null;
					var day1 = null;
					if(i != 0){
					fullDate1 = data[i-1].train_date;
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
					if(data[d].xx="C"){
						dd.push("img/less.png");
					}else if(data[d].xx="B"){
						dd.push("img/fit.png");
					}else{
						dd.push("img/high.png");
					}
				}
				
				var myF = new Vue({
					el:"#app",
				  data:{
					  mySignIn:${myRate.phrList},
					  xx:xx,
					  dd:dd
					  
					}
					
				})
				
				$(".C").html("运动强度不够，继续努力！").css({"font-weight":"bold"});
				$(".B").html("您的运动强度合适，请继续保持！").css({"font-weight":"bold"});
				$(".A").html("运动强度太大，请减少运动量！").css({"font-weight":"bold"});
				
				
				
				
			})
		</script>
		
	</body>
	

</html>