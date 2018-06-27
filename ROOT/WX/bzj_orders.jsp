<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<title>支付保证金订单</title>
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
		<div class="name">
			<p>
				商品名称<span><s:property value="active.name" /></span>
			</p>
		</div>
		<div class="coupon">
			<a href="free.html">优惠券<img src="images/right_icon_03.png"
				class="fr"><span>无可用优惠券</span></a>
		</div>
		<div class="orders_details">
			<div class="pic fl">
				<img src="../picture/<s:property value="active.image"/>" alt="">
			</div>
			<div class="txt fl">
				<h6>
					<s:property value="active.name" />
				</h6>
				<p>
					已有<span><s:property value="#request.count" /></span>人参加
				</p>
				<p>
					<span>
					<s:iterator value="active">
					<s:if test="category=='A'">
										<s:property value="days" />天内体重增加<s:property value="value" />KG</s:if>
									<s:elseif test="category=='B'">
										<s:property value="days" />天内体重减少<s:property value="value" />KG</s:elseif>
									<s:elseif test="category=='D'">
										<s:property value="days" />天内运动<s:property value="value" />次</s:elseif>
									<s:elseif test="category=='E'">
										<s:property value="days" />天内运动<s:property value="value" />小时</s:elseif>
									<s:elseif test="category=='F'">
										<s:property value="days" />天内每周运动<s:property value="value" />次</s:elseif>
									<s:elseif test="category=='G'">
										<s:property value="days" />天内
										<s:if test="action != null">
											<s:property value="action"/>
										</s:if>
										<s:else>
											<s:property value="content"/>
										</s:else>
										<s:property value="value" />千米</s:elseif>
									<s:elseif test="category=='H'">
									    <s:property value="days" />天负荷<s:property value="value" />Kg
									</s:elseif>
									</s:iterator>
					</span>
				</p>
			</div>
			<div class="orders_time fr">
				<p>
					<s:property value="#request.xq" />
				</p>
				<p>
					<s:date name="activeOrder.orderStartTime" format="M月dd日"/>
				</p>
			</div>
		</div>
		<div class="total_price">
			<p>
				应付总价<span class="fr">￥<s:property
						value="active.amerceMoney" /></span>
			</p>
		</div>
		<div class="financed">
			<div class="financed_list">
				<img src="images/zhifu_icon_03.png"> <span>微信支付</span> <label
					class="first_label label_one"><input type="radio"
					name="checked" value="2" checked class="check_one" id="wx"></label>
			</div>
			<div class="financed_list">
				<img src="images/zhifu_icon_06.png"> <span>支付宝支付</span> <label
					class="label_one"><input type="radio" name="checked"
					value="1" class="check_one" ></label>
			</div>
			<div class="financed_list">
				<img src="images/zhifu_icon_09.png"> <span>银联支付<i>(借记卡/储蓄卡)</i></span>
				<label class="label_one"><input type="radio" name="checked"
					value="3" class="check_one" id="yl"></label>
			</div>
		</div>
		<div class="assign">
			<a href="JavaScript:zfbzj()">支付保证金</a>
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