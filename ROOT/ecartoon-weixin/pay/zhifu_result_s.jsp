<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
		<meta charset="UTF-8">
		<title>支付完成</title>
		<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
		<meta name="apple-mobile-web-app-capable" content="yes">
		<link  rel="stylesheet" href="ecartoon-weixin/css/mui.min.css" />
		<style type="text/css">
			/*ul样式*/
			.mui-table-view{
				margin-top:10px ;
				padding-top: 20px;
				padding-bottom: 20px;
				text-align: center;
			}
			.zhi_money{
				color: #FE9E3A;
			}
			.img0{
				width: 150px;
				height: 110px;
			}
			.img1{
				width: 130px;
				height: 90px;
			}
			/*装填提示*/
			.zhtai_tishi{
				margin-top:20px;
				margin-bottom: 0px;
			}
			.go_tishi{
				
			}
			/*底部样式*/
			.fu{
				line-height:45px;
				width: 100%;
				float:left;
				text-align:center;
				font-size:13px;
				background-color:#FE9E3A;
				color: #ffffff;
			}
			/*去掉底部左右两边的padding值*/
			.mui-bar {
			    padding-right: 0px;
			    padding-left: 0px;
			}
		</style>
		
	</head>
	<body>
		<footer class="mui-bar mui-bar-footer">
			<span class="mui-col-sm-12 fu" id="go">前往</span>
		</footer>
		<div class="mui-content">
			<div class="mui-scroll-wrapper">
			    <div class="mui-scroll">
			    	<ul class="mui-table-view">
			    		<img src="ecartoon-weixin/images/wei_0.png" class="img0" />
			    		<p class="zhi_money">{{message}}
			    			<span v-if="pay.success" id="money">¥{{price}}</span>
			    		</p>
			    	</ul>
			    	<!--  
			    	<ul class="mui-table-view">
			    		<img src="ecartoon-weixin/images/wei_1.png" class="img1" />
			    		<p class="zhtai_tishi">你已经成功加入挑战！</p>
			    		<p class="go_tishi">前往找方案加强自己让挑战变得更轻松吧！</p>
			    	</ul>
			    	-->
			    </div>
			</div>
		</div>
	</body>
	<script src="ecartoon-weixin/js/vue.min.js"></script>
	<script src="ecartoon-weixin/js/mui.min.js"></script>
	<script type="text/javascript">
		mui('.mui-scroll-wrapper').scroll({
			deceleration:0.0005
		});
		
		//点击前往时
		var go=document.getElementById('go');
		go.addEventListener('click',function(){
			
			location.href='mime.html';
		})
	</script>
	<script type="text/javascript">
		var data = ${data};
		var price = ${price};
		var message = data.success?"支付成功":"抱歉,由于未知原因支付失败,请稍后再试";
		new Vue({
			el:"#app",
			data:{
				pay:data,
				price:data,
				message:message
			}
		});
	</script>
</html>