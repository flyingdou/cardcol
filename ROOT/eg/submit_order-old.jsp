<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
		<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
		<meta name="apple-mobile-web-app-capable" content="yes">
		<link  rel="stylesheet" href="../eg/css/mui.min.css"/>
		<script type="text/javascript" src="script/jquery-1.7.2.min.js"></script>
		<style type="text/css">
		.mui-content{
			font-size:13px;
			color:black;
		}
		/*ul间上下间隔20px*/
		.mui-table-view{
			margin-top: 20px;
			margin-bottom:20px;
		}
			#yh{
				margin-bottom:15px;
				color:black;
			}
			.address{
				font-size:13px;
				
			}
			.address span:nth-child(2){
				color:gray;
			}
			/*绑定手机样式*/
			.bind_phone span:nth-child(2),.mui-navigate-right span:nth-child(2){
				margin-right:15px;
				color:gray;
			}
			/*ul2样式*/
			#ul2 li a span:nth-child(2){
				color:gray;
			}
			.shp_money span{
				color:#fe9e3a;
			}
		/*底部样式*/
		.fu{
			line-height:45px;
			width:50%;
			float:left;
			text-align:center;
			font-size:13px;
			background-color:lightgray;
		}
		.mui-bar-footer span:nth-child(2){
			float:left;
			line-height:45px;
			width:50%;
			background-color:#fe9e3a;
			margin-right:0px;
			color:white;
			overflow:hidden;
			text-align:center;
			font-size:13px;
		}
		/*去掉底部左右两边的padding值*/
		.mui-bar {
		    padding-right: 0px;
		    padding-left: 0px;
		}
		
		a span:first-child{
			color:#333333;
		}
		a span:nth-child(2){
			color: #999999;
		}
		</style>
		<script type="text/javascript">
		$(function(){
			$("mid").val()=<s:property value="#request.customer.id"/>
			$("pid").val()=<s:property value="#request.p.id"/>
			$("summoney").val()=<s:property value="#request.p.cost"/>
			$("orderdate").val()=<s:property value="orderdate"/>
			$("proname").val()=<s:property value="#request.p.name"/>
			
		})
		</script>
	</head>
	<body>
		<footer class="mui-bar mui-bar-footer">
			<span class="mui-col-sm-6 fu">实付¥<span id="money"><s:property value="#request.p.cost"/></span></span>
			<span class="mui-col-sm-6 " id="submit_order">提交订单</span>
		</footer>
		<div class="mui-content">
			<div class="mui-scroll-wrapper" >
				<div class="mui-scroll">
				<form action="cardselling!saveOrder.asp" method="post" id="formsubmit">
				<s:hidden  name="pname" id="proname"></s:hidden>
				<s:hidden  name="orderdate" id="orderdate"></s:hidden>
				<s:hidden  name="summoney" id="summoney"></s:hidden>
				<s:hidden  name="pid" id="pid"></s:hidden>
				<s:hidden  name="mid" id="mid"></s:hidden>
				
					<!--neirong-->
					<ul class="mui-table-view" id="ul1">
						<!--<li class="mui-table-view-cell">
							<p id="yh"><span>用户昵称：</span><span id="jia_name"></span></p>
							<a class="mui-navigate-right address" id="address">
								<span class="mui-icon mui-icon-location " style="color:green;"></span><span >请输入收货地址</span>
							</a>
						</li>-->
						<li class="mui-table-view-cell">
							<a class="mui-navigate-right bind_phone" > 
								<span>用户昵称</span>
								<span class="mui-pull-right" id="userName" name="username">
<!-- 								<input placeholder="请输入昵称" style="text-align: right;border:none;font-size: 13px;color:#999999;" /> -->
								<s:property value="#request.customer.name" />
								</span>
							</a>
							
						</li>
						<li class="mui-table-view-cell">
							<a class="mui-navigate-right bind_phone" id="phone_line"> 
								<span>绑定手机</span>
								<span class="mui-pull-right" id="phone" style="color: #999999;">
								<s:if test="#request.mobilephone==null">
								请绑定手机</s:if>
								<s:else><s:property value="#request.mobilephone"/></s:else>
								</span>
							</a>
							
						</li>
					</ul>
					<ul class="mui-table-view" id="ul2">
						<li class="mui-table-view-cell">
							<a >
								<span>商品名称</span>
								<span class="mui-pull-right" id="shp_name"><s:property value="#request.p.name"/></span>
							</a>
							
						</li>
						<li class="mui-table-view-cell">
							<a >
								<span>开始时间</span>
								<span class="mui-pull-right" id="start_time"><s:property value="orderdate"/></span>
							</a>
						</li>
						<li class="mui-table-view-cell">
							<a >
								<span>商品金额</span>
								<span class="mui-pull-right shp_money"><span style="color:#fe9e3a;">¥</span><span id="shp_money" style="color:#fe9e3a;"><s:property value="#request.p.cost"/></span></span>
							</a>
						</li>
					</ul>
					<ul class="mui-table-view">
						<li class="mui-table-view-cell">
							<a class="mui-navigate-right" id="coupon_line">
								<span>优惠券</span>
								<span class="mui-pull-right" id="shp_quan">未使用</span>
							</a>
							
						</li>
					</ul>
				</form>	
			    </div>
			</div>
			
		</div>
	</body>
	<script src="../eg/js/mui.min.js"></script>
	<script>
			var submit_order=document.getElementById('submit_order');
			submit_order.addEventListener('tap',function(){
				var phone=document.getElementById('phone').value;
				if(phone==''){
					mui.taost('请先绑定手机号');
					return;
				}
//				mui.openWindow({
//					url:'zhifu.html',
//					id:'zhifu.html'
//				})
              $("#formsubmit").submit();
				location.href="zhifu.html";
			});
			
			//跳转到收货地址页面
//			var address=document.getElementById('address');
//			address.addEventListener('click',function(){
//				location.href="p_address.html";
//			});
			
			//跳转到电话号码验证页面
			var phone_line=document.getElementById('phone_line');
			phone_line.addEventListener('click',function(){
				location.href='phoneCheck.html';
			});
			
			//跳转到优惠券页面
			var coupon_line=document.getElementById('coupon_line');
			var mid =<s:property value="#request.customer.id"/>
			coupon_line.addEventListener('click',function(){
				location.href="cardselling!getCoupons.asp?mid="+mid;
			});
	</script>
</html>
