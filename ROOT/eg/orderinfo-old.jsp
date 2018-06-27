<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<title>支付</title>
<link type="text/css" href="${pageContext.request.contextPath}/WX/css/bootstrap.css" rel="stylesheet">
<link type="text/css" href="${pageContext.request.contextPath}/WX/css/base.css" rel="stylesheet">
<link type="text/css" href="${pageContext.request.contextPath}/WX/css/style.css" rel="stylesheet">
<link type="text/css" href="${pageContext.request.contextPath}/WX/css/index.css" rel="stylesheet">
<script type="text/javascript" src="${pageContext.request.contextPath}/WX/js/jquery-2.2.0.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/WX/js/style.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/WX/js/imgs.js"></script>
</head>
<body>
	<div class="container">
		<s:iterator value="pageInfo.items"></s:iterator>
		<div class="name">
			<p><strong>订单信息</strong></p>
		</div>
		<div class="name">
			<p>
				商品<span><s:property value="PROD_NAME" /></span>
			</p>
		</div>
		<div class="name">
			<p>
				手机<span><s:property value="mobilephone" /></span>
			</p>
		</div>
		<div class="total_price">
			<p>
				金额<span class="fr">￥<s:property
						value="PROD_PRICE" /></span>
			</p>
		</div>
		
		<div class="financed">
			<div class="name">
				<p><strong>选择支付方式</strong></p>
			</div>
			<div class="financed_list">
				<img src="${pageContext.request.contextPath}/WX/images/zhifu_icon_03.png"> <span>微信支付</span> <label
					class="first_label label_one"><input type="radio"
					name="checked" value="2" checked class="check_one" id="wx"></label>
			</div>
			<div class="financed_list">
				<img src="${pageContext.request.contextPath}/WX/images/zhifu_icon_06.png"> <span>支付宝支付</span> <label
					class="label_one"><input type="radio" name="checked"
					value="1" class="check_one" ></label>
			</div>
			<div class="financed_list">
				<img src="${pageContext.request.contextPath}/WX/images/zhifu_icon_09.png"> <span>银联支付<i>(借记卡/储蓄卡)</i></span>
				<label class="label_one"><input type="radio" name="checked"
					value="3" class="check_one" id="yl"></label>
			</div>
		</div>
		<div class="assign">
			<a href="JavaScript:zfbzj()">确认支付</a>
		</div>
	</div>
	
	<%-- <script type="text/javascript">
	function zfbzj(){
		var id = '<s:property value="#request.ae.id" />'
		var payType = $('.financed_list').find(':radio:checked').val();
		var date = '<s:property value="#session.d" />'
		window.location.href = "activepaywx!partake.asp?id="+id+"&startDate="+date
	}
	</script> --%>

</body>
</html>