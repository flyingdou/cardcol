<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<s:include value="/share/meta.jsp"/>
<meta name="keywords" content="购物车" />
<meta name="description" content="购物车" />
<title>健身E卡通_购物车</title>
<link rel="stylesheet" type="text/css" href="css/shoppingcart2.css"/>
</head>
<body>
<s:include value="/share/header.jsp"/>
<div id="content">
	<s:form id="editForm" name="editForm" action="order!saveAudit.asp" method="post" theme="simple">
	<div id="cont">
	  <div class="contop1">
	     <ul>
			<li class="last"><b>1.我的购物车</b></li>
			<li><b>2.填写并核对订单信息</b></li>
			<li><b>3.选择支付方式</b></li>
			<li class="first"><b>4.成功提交订单</b></li>
		 </ul>
	  </div>
	  <div class="contop2">
		  <div class="contop2-1">
				<div class="condiv1">
					<img src="images/sers.png" />
				</div>
				<div class="condiv2">
				  <p><span class="span1"><b>订单提交成功!</b></span>（<span class="span3"><a href="product.asp">点击查看</a></span>）</p>
				  <p>
				  <b>应付金额：</b><span class="span2" id="spanToatal">￥<s:property value="payMoney"/></span>元，
				  <b>你已付款：</b><span class="span2" id="spanPay">￥<s:property value="paidMoney"/></span>元，
				  <b>还需付款：</b><span class="span2" id="spanNeed">￥<s:property value="needPayMoney"/></span>元，
				  <b>支付方式：</b><span class="span2"><s:if test="payType == 1">在线支付</s:if><s:if test="payType == 2">银行付款</s:if></span> </p>
				  <p><b>配送方式：</b>
				  <span class="span2">
				  	<s:if test="shipType == 1">不需要配送</s:if>
				  	<s:if test="shipType == 2">快递送货上门</s:if>
				  </span></p>
				  <p><b>配送时间：</b>
				  <span class="span2">
				  	<s:if test="shipTimeType == 1">工作日，双休日，节假日均可</s:if>
				  	<s:if test="shipTimeType == 2">只双休日，节假日送货（工作日不送货）</s:if>
				  	<s:if test="shipTimeType == 3"> 只工作日送货（双休日，节假日不送货）</s:if>
				  </span>
				  </p>
				</div>
		  </div>
		  <div class="contop2-2">
				<p>*&nbsp你可以在“<span><a href="product.asp">我的订单</a></span>”中查看或者取消您的订单。由于系统需要进行订单预处理，订单信息的显示可能会延迟。</p>
				<p>*&nbsp未支付的订单我们将为您保留24小时。如果您现在不方便支付，请于24小时内在“<span><a href="product.asp">我的订单</a></span>”中完成支付。</p>
		  </div>
	  </div>
	</div>
	</s:form>
</div>
<s:include value="/share/footer.jsp"/>	
</body>
</html>