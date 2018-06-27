<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

	<head>
	    <base href="<%=basePath%>" >
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
		<meta name="apple-mobile-web-app-capable" content="yes">
		<title>提交订单</title>
		<link rel="stylesheet" href="ecartoon-weixin/css/mui.min.css" />
		<style type="text/css">
			.mui-content {
				font-size: 13px;
				color: black;
			}
			/*ul间上下间隔20px*/
			
			.mui-table-view {
				/*	margin-top: 20px;*/
				margin-bottom: 20px;
			}
			
			#yh {
				margin-bottom: 15px;
				color: black;
			}
			
			.address {
				font-size: 13px;
			}
			
			.address span:nth-child(2) {
				color: gray;
			}
			/*绑定手机样式*/
			
			.bind_phone span:nth-child(2),
			.mui-navigate-right span:nth-child(2) {
				margin-right: 15px;
				color: gray;
			}
			/*ul2样式*/
			
			#ul2 li a span:nth-child(2) {
				color: gray;
			}
			
			.shp_money span {
				color: #fe9e3a;
			}
			/*底部样式*/
			
			.fu {
				line-height: 45px;
				width: 50%;
				float: left;
				text-align: center;
				font-size: 13px;
				background-color: lightgray;
			}
			
			.mui-bar-footer span:nth-child(2) {
				float: left;
				line-height: 45px;
				width: 50%;
				background-color: #fe9e3a;
				margin-right: 0px;
				color: white;
				overflow: hidden;
				text-align: center;
				font-size: 13px;
			}
			/*去掉底部左右两边的padding值*/
			
			.mui-bar {
				padding-right: 0px;
				padding-left: 0px;
			}
			
			a span:first-child {
				color: #333333;
			}
			
			a span:nth-child(2) {
				color: #999999;
			}
			
			.weight700 {
				font-weight: 700;
			}
			/*css--------------------1*/
			
			.footer {
				border-top: rrgb(0, 0, 0)!important;
				outline: rgba(0, 0, 0, 0)!important;
				bottom:10px;
				margin:0 10px;
			}
			
			.footer_a {
				width: 100%!important;
				background: #FF4401;
				font-size: 14px;
				color: white;
				outline-color: rgb(0, 0, 0)!important;
				border-color: rgba(0, 0, 0, 0);
				height: 100%!important;
				text-align: center;
				display: block;
				line-height: 44px;
				font-weight: 700;
			}
			
			.card_name {
				text-align: right;
				border: none;
				font-size: 13px;
				color: #999999;
			}
			
			.ymoney {
				color: red;
				font-size: 16px;
				text-decoration: line-through;
			}
			
			.nmoney {
				color: red;
				font-size: 16px
			}
			
			.box-bs {
				box-sizing: border-box;
			}
			
			.zf-wx {
				height: 21px;
				display: block;
				line-height: 21px;
				box-sizing: border-box;
				background: url(ecartoon-weixin/img/weixin.png)no-repeat scroll 0 2px/16px auto;
			}
			
			.zf-zfb {
				height: 21px;
				display: block;
				line-height: 21px;
				box-sizing: border-box;
				background: url(ecartoon-weixin/img/zhifubao.png)no-repeat scroll 0 2px/18px auto;
			}
			
			.label_zf {
				width: 100%;
				display: inline-block;
				text-indent: 2em;
			}
			
			.input_ball {
				float: right;
				margin-top: 5px;
			}
			
			.mui-popover{
				margin-top:200px;
			}
		</style>
	</head>
	<body>
		<div class="mui-content">
			<div class="mui-scroll-wrapper">
				<div class="mui-scroll">
					<!--neirong-->
					<ul class="mui-table-view" id="ul1">
						<li class="mui-table-view-cell weight700">
							订单信息
						</li>
						<li class="mui-table-view-cell">
							<span>商品</span>
							<span class="mui-pull-right card_name" id="userName">${payMain.prod_name}</span>
						</li>
						<li class="mui-table-view-cell">
								<span>手机</span>
								<span class="mui-pull-right card_name" id="mobilphone">
									${member.mobilephone == null || member.mobilephone == 'null' ? '请绑定手机号' : member.mobilephone}
								</span>
						</li>
						<li class="mui-table-view-cell">
							<span>商品金额</span>
							<span class="mui-pull-right ymoney"  id="ymoney">${payMain.contractMoney}</span>
						</li>
						<li class="mui-table-view-cell">
							<span>实付金额</span>
							<span class="mui-pull-right nmoney">￥<span id="nmoney">${payMain.orderMoney}</span></span>
						</li>
						<li class="mui-table-view-cell">
							<a class="mui-navigate-right" id="selector">
								<span>优惠券</span>
								<span class="mui-pull-right" id="shp_quan">未使用</span>
							</a>
						</li>
					</ul>
				</div>
			</div>
			<div id="popover" class="mui-popover" style="left:12%;">
				<div style="height: 40px;line-height: 40px;text-align: center;">
					选择优惠券
				</div>
				<ul id="product_list" class="mui-table-view" style="max-height: 200px;overflow-y: scroll;margin-bottom: 50px;">
					<li class="mui-table-view-cell" id="product_li" style="position: relative;" v-for="(v,i) in init_data.tickets">
						<div style="height:50px;position:relative;">
							<span class="productName">{{v.name}}</span><br/>
							<span class="money" style="color:#ff4401;">￥{{v.price}}</span>
							<span style="position:absolute;right:0;">有效期至 : {{v.useDate}}</span>
						</div>
						<div style="width:13px;height:13px;position: absolute;top:11px;right:13px;margin-right:5px;">
							<input type="radio" name="ticket" class="ticketId productSelector" :value="v.id" />
						</div>
						<div style="width:100%;height:100%;position: absolute;left:0;top:0;z-index:1;" hidden="hidden">
						</div>
					</li>
				</ul>
				<div style="position:absolute;bottom: 0;left: 0;height: 40px;width: 100%;line-height: 40px;border-top:1px solid #999">
				<div id="false"style="width: 50%;float: left;text-align: center;border-right: 1px solid #999;">取消</div>
					<div id="true" style="width: 50%;float: left;text-align: center;color:red">确定</div>
					<div style="clear: both;"></div>
				</div>
			</div>
			<!-- 底部按钮 -->
			<footer class="mui-bar mui-bar-footer footer">
				<a class="footer_a" href="javascript:buy()" id="submit_order">确认支付</a>
			</footer>
		</div>
	</body>
	<script src="ecartoon-weixin/js/mui.min.js"></script>
	<script src="ecartoon-weixin/js/vue.min.js"></script>
	<script src="ecartoon-weixin/js/jquery.min.js" ></script>
	<script>
		var vue = new Vue({
			el:"#popover",
			data:{
				init_data : 0,
				unit_price:${payMain.contractMoney == null ? 0 : payMain.contractMoney}
			}
		});
		
		var ticketId = 0;
		// 优惠券列表
		(function(){
			var contractMoney = ${payMain.contractMoney == null ? 0 : payMain.contractMoney};
			contractMoney = contractMoney == 0 ? 0.00 : contractMoney.toFixed(2);
			var payMoney = ${payMain.orderMoney == null ? 0 : payMain.orderMoney};
			payMoney = payMoney == 0 ? 0.00 : payMoney.toFixed(2);
			$("#ymoney").html(contractMoney);
			$("#nmoney").html(payMoney);
			$.ajax({
				url:"eaccountswx!findTicketByType.asp",
				type:"post",
				data:{orderType:"${payMain.orderType}"},
				dataType:"json",
				success:function(res){
					vue.init_data = res;
				},
				error : function(e){
					console.log(JSON.stringify(e));
				}
			});
			
			document.getElementById("selector").addEventListener('tap', function() {
				if(vue.init_data.tickets.length > 0){
					$("#popover").css("margin-top",(($(document).height() - $(popover).height()) / 2) + "px");
					mui('#popover').popover('toggle'); // 将id为list的元素放在想要弹出的位置
				}else{
					alert("暂无可使用的优惠券");
				}
			});
			document.getElementById("true").addEventListener('tap', function() {
				mui('#popover').popover('hide'); // 将id为list的元素放在想要弹出的位置
				// 计算金额
				calculation();
			});
			document.getElementById("false").addEventListener('tap', function() {
				mui('#popover').popover('hide'); // 将id为list的元素放在想要弹出的位置
			});
			
			function calculation(){
				var index = $(".productSelector").index($(".productSelector:checked"));
				$("#shp_quan").html($(".productName").eq(index).html());
				if(isNaN(vue.init_data.tickets[index].price)){
					alert('抱歉,此优惠券不可用');
					$("#shp_quan").html("未使用");
					return;
				}
				var price = parseFloat(vue.unit_price)-parseFloat(vue.init_data.tickets[index].price);
				$("#nmoney").html(price <= 0 ? "0.00" : price.toFixed(2));
				ticketId = $(".ticketId").eq(index).val();
			}
		})();
		
		// 修改优惠券使用状态
		function updateTicketActive(){
			$.ajax({
				url:"eaccountswx!updateTicketActive.asp",
				type:"post",
				data:{ticketId:ticketId},
				dataType:"json",
				success:function(res){
					alert('购买成功!');
					location.href = "eproductorderwx!findOrder.asp";
				},
				error:function(e){
					//alert(JSON.stingify(e));
				}
			});
		}
		
		// 购买
		function buy(){
			if($("#nmoney").html() == 0.00){
				// 0元直接购买
				$.ajax({
					url:'eorderwx!updateOrderStatus.asp',
					type:'post',
					data:{ajax:"1"},
					dataType:'json',
					success:function(res){
						if(res.success){
							if(ticketId != 0){
								updateTicketActive();
							}else{
								alert('购买成功!');
								location.href = "eproductorderwx!findOrder.asp";
							}
						}else{
							alert(res.msg);
						}
					},
					error:function(e){
						//alert(JSON.stringify(e));
					}
				});
			}else{
				//微信支付
				$.ajax({
					url : "ewechatwx!paySign.asp",
					type : "post",
					data : {
						"productPrice" : $("#nmoney").html(),
						"orderNo" : "${payMain.orderNo}"
					},
					dataType : "json",
					success : function(res) {
						if (res.success) {
							WeixinJSBridge.invoke('getBrandWCPayRequest',
								{
									appId : res.appId,
									timeStamp : res.timeStamp,
									nonceStr : res.nonceStr,
									package : res.packageValue,
									signType : res.signType,
									paySign : res.paySign
								},
								function(res) {
									/* alert(JSON.stringify(res)); */
									if (res.err_msg == "get_brand_wcpay_request:ok") {
										if(ticketId != 0){
											updateTicketActive();
										}else{
											alert('购买成功!');
											location.href = "eproductorderwx!findOrder.asp";
										}
									} else if (res.err_msg == "get_brand_wcpay_request:cancel") {
										alert("已取消支付!");
									} else {
										alert("未知原因,支付失败,请稍后再试")
									}
								});
						} else {
							alert(res.msg);
						}
					},
					error : function(e) {
						// alert(JSON.stringify(e));
					}
				});
			}
		}
	</script>
</html>