<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
<meta charset="UTF-8">
<title></title>
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
<link href="sport/css/mui.min.css" rel="stylesheet" />
<style type="text/css">
.put {
	height: 21px !important;
	line-height: 21px !important;
	padding: 0 !important;
	margin: 0 !important;
	width: 150px !important;
	text-align: right;
	padding-right: 1em !important;
	border: none !important
}
.cr {
	color: #FF4401
}
.submitButtion{
	background: #FF4401; 
	border: none; 
	width: 100%; 
	position: fixed; 
	bottom: 0; 
	height: 44px; 
	line-height: 44px !important; 
	padding: 0;
}
</style>
</head>
<body>
	<form action="" method="post" id="app">
		<ul class="mui-table-view">
			<!-- 订单信息展现开始 -->
			<li class="mui-table-view-cell">
				<a class="mui-navigate-right">
					用户名称
					<input type="text" disabled="disabled" id="" :value="item.memberName" placeholder="用户昵称" class="mui-pull-right put" />
				</a>
			</li>
			<li class="mui-table-view-cell">
				<a class="mui-navigate-right">
					手机号
					<input type="number" disabled="disabled" id="mobilePhone" :value="item.mobile" placeholder="请输入手机号码" class="mui-pull-right put" />
				</a>
			</li>
			<li class="mui-table-view-cell">
				<a class="">
					商品名称 
					<input type="text" disabled="disabled" id="productName" :value="item.prod_name" class="mui-pull-right put" />
				</a>
			</li>
			<li class="mui-table-view-cell">
				<a class="">
					开始时间
					<input type="text" disabled="disabled" id="" :value="item.orderDate" class="mui-pull-right put" />
				</a>
			</li>
			<li class="mui-table-view-cell">
				<a class="">
					商品金额
					<input type="text" disabled="disabled" id="productPrice" value="0.01" class="mui-pull-right put" />
				</a>
			</li>
			<!-- 订单信息结束 -->
		</ul>
		<a href="javascript:pay()" class="submitButton">提交订单</a>
	</form>
	<script src="sport/js/mui.min.js"></script>
	<script src="sport/js/vue.min.js"></script>
	<script type="text/javascript">
		mui.init();
		
		var data = ${payMain==null?0:payMain};
		var app = new Vue({
			el:"#app",
			data:{
				item:data
			}
		});
		
		function pay(){
			var phone = $("#mobilePhone").val();
			if(phone != "" && phone != undefined){
				onBridgeReady();
			}else{
				alert("请先完善手机号码,再购买");
			}
		}
		
		function onBridgeReady(){
		   $.ajax({
			   url:"swechatwx!paySign.asp",
			   type:"post",
			   data:{"productPrice":$("#productPrice").val(),"productDetail":$("#productName").val()},
			   dataType:"json",
			   success:function(res){
				  	if(res.success){
				  		 WeixinJSBridge.invoke(
			               'getBrandWCPayRequest', {
			                   "appId" : res.appId, //公众号名称，由商户传入     
			                   "timeStamp": res.timeStamp, //时间戳，自1970年以来的秒数     
			                   "nonceStr" : res.nonceStr, //随机串     
			                   "package" : res.packageValue, //支付授权
			                   "signType" : res.signType,  //微信签名方式     
			                   "paySign" : res.paySign //微信签名 
			               },function(res){
			                if(res.err_msg == "get_brand_wcpay_request:ok"){  
			                    location.href = "loading.html";
			                }else if(res.err_msg == "get_brand_wcpay_request:cancel"){  
			                    alert("已取消支付!");
			                }else{  
			                   alert("支付失败!");
			                }  
			            }); 
				  	}else{
				  		console.log(res.msg);
				  	}
		          }
			   },
			   error:function(e){
				   console.log(e.responseText);
			   }
		   });
		   
	</script>
</body>
</html>