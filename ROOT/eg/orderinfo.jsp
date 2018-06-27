<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
<meta name="apple-mobile-web-app-capable" content="yes">
<title>提交订单</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/eg/css/mui.min.css" />
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
.bind_phone span:nth-child(2), .mui-navigate-right span:nth-child(2) {
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
</style>
</head>
<body>
	<footer class="mui-bar mui-bar-footer"
		style="border-top: rrgb(0, 0, 0) !important; outline: rgba(0, 0, 0, 0) !important;">
		<div>
			<a class="" style="width: 100% !important; background: orange; font-size: 14px; color: white; outline-color: rgb(0, 0, 0) !important; border-color: rgba(0, 0, 0, 0); height: 100% !important; text-align: center; display: block; line-height: 44px;">确认支付</a>
		</div>
	</footer>
	<div class="mui-content">
		<div class="mui-scroll-wrapper">
			<div class="mui-scroll">
				<!--neirong-->
				<!-- 订单信息展现开始 -->
				<s:iterator value="pageInfo.items">
				<ul class="mui-table-view" id="ul1">
					<li class="mui-table-view-cell">订单信息</li>
					<li class="mui-table-view-cell">
						<span>商品</span>
						<span class="mui-pull-right" id="userName">
							<input placeholder="" value="<s:property value="orderName" />" readonly="readonly"
								style="text-align: right; border: none; font-size: 13px; color: #999999;" />
						</span>
					</li>
					<li class="mui-table-view-cell">
						<a class="mui-navigate-right bind_phone" id="phone_line">
							<span>手机</span>
							<span class="mui-pull-right" id="phone" style="color: #999999;"><s:property value="#session.member.mobilephone" /></span>
						</a>
					</li>
					<li class="mui-table-view-cell">
						<span>金额</span>
						<span class="mui-pull-right" id="phone" style="color: red; font-size: 16px"><s:property value="orderCost" /></span>
					</li>
					<li class="mui-table-view-cell">
						<span>实付金额</span>
						<span class="mui-pull-right" id="phone" style="color: red; font-size: 16px"><s:property value="orderCost" /></span>
					</li>
				</ul>
				</s:iterator>
				<!-- 订单信息展现结束 -->
				<ul class="mui-table-view" id="ul2">
					<li class="mui-table-view-cell">
						<a>
							<span>选择支付方式</span>
						</a>
					</li>
					<li class="mui-table-view-cell" style="box-sizing: border-box;">
						<a style="box-sizing: border-box;">
							<span
								style="height: 21px; display: block; line-height: 21px; box-sizing: border-box; background: url(../images/weixin.png) no-repeat scroll 0 2px/16px auto;">
								<lable for="wx" style="width: 100%;display: inline-block;text-indent: 2em;">微信支付<input type='radio'
									name="radios" id="wx" style="float: right; margin-top: 5px;" />
								<div style="clear: both;"></div>
								</lable>
							</span>
						</a>
					</li>
					<li class="mui-table-view-cell" style="box-sizing: border-box;">
						<a style="box-sizing: border-box;">
							<span
								style="height: 21px; display: block; line-height: 21px; box-sizing: border-box; background: url(../images/zhifubao.png) no-repeat scroll 0 2px/18px auto;">
								<lable for="zfb" style="width: 100%;display: inline-block;text-indent: 2em;">支付宝支付<input type='radio'
									name="radios" id="zfb" style="float: right; margin-top: 5px;" />
								<div style="clear: both;"></div>
								</lable>
							</span>
						</a>
					</li>
				</ul>
			</div>
		</div>

	</div>
</body>
<script src="${pageContext.request.contextPath}/eg/js/mui.min.js"></script>
<script>
	var submit_order = document.getElementById('submit_order');
	submit_order.addEventListener('tap', function() {
		var phone = document.getElementById('phone').value;
		if (phone == '') {
			mui.taost('请先绑定手机号');
			return;
		}
		location.href = "zhifu.html";
	});

	//跳转到电话号码验证页面
	var phone_line = document.getElementById('phone_line');
	phone_line.addEventListener('click', function() {
		location.href = 'phoneCheck.html';
	});

	//跳转到优惠券页面
	var coupon_line = document.getElementById('coupon_line');
	coupon_line.addEventListener('click', function() {
		location.href = 'coupon.html';
	});
</script>
</html>
