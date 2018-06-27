<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<s:include value="/share/meta.jsp"/>
<meta name="keywords" content="瘦身减肥课程,肌肉健美课程,增强体质" />
<meta name="description" content="健身教练的商务中心是健身教练制定的健身套餐,健身课程，如：瘦身减肥课程,肌肉健身课程,增强体质课程等。" />
<title>商务中心</title>
<link rel="stylesheet" type="text/css" href="css/shoppingcart1.css"></link>
<script type="text/javascript">
$(function(){
	$('#savePay').click(function(){
		$('#orderForm').submit();
	});
});
</script>
</head>
<body>
<s:include value="/share/home-header_1.jsp" />
<div id="content" style="margin-top:0px;">
<s:if test="message == null || message == ''">
	<div id="cont">
	  <div class="order-message">
		<div class="right-top">
			<div class="right-image">
				<img src="images/zheceok.jpg" >
			</div>
			<div>
				<div class="order-success1">
					<span>订单提交成功，请您尽快付款！</span>
				</div>
				<div class="order-success2" style="">
					<span>订单号：<s:property value="#request.no"/></span>
					<span>应付金额：<b><s:property value="#request.money"/></b>元</span>
				</div>
			</div>
		</div>
	  </div>
	  <span class="order-success3"><strong>立即支付<b><s:property value="#request.money"/>元</b>，即可完成订单。</strong> 请您在<b>24小时</b>内完成支付，否则订单会被自动取消。</span>
	  <s:form id="orderForm" name="orderForm" action="orderpay!toPay.asp" method="post" theme="simple" target="_blank">
	  <s:hidden name="id" id="orderId" value="%{partake.id}"/>
	  <s:hidden name="payNo" id="payNo" value="%{#request.no}"/>
	  <s:hidden name="totalMoney" id="totalMoney" value="%{#request.money}"/>
	  <s:hidden name="subject" id="subject" value="%{#request.name}"/>
	  <div class="contop5">
	       <div class="contop5-1">
		     <h3>支付方式</h3>
			 <p><input type="radio" name="payType" value="2" id="type"  checked="checked"/>
			 <img src="images/unionpay.png">
			 <span>银联在线支付服务是由中国银联提供的互联网支付服务，全面支持各大银行的信用卡、借记卡。 </span>
			 </p>
			 <p><input type="radio" name="payType" value="1" id="payType" />
			 <img src="images/alipay.jpg">
			 <span>支付宝支付是指通过支付宝支付储值账户进行的在线付款的支付方式。</span>
			 </p>
		   </div>
	  </div>
	  <div class="contop6">
		  <div class="contop6-2">
			 <p class="p2"><a href="#" id="savePay"><img src="images/tj.png"/></a></p>
		  </div>
	  </div>
	  </s:form>
	</div>
</s:if>
<s:else>
	<div id="cont">
	  <div class="order-message">
		<div class="right-top">
			<div class="right-image">
				<img src="images/zheceok.jpg" >
			</div>
			<div>
				<div class="order-success1">
					<span><s:property value="message"/></span>
				</div>
			</div>
		</div>
	  </div>
</s:else>
</div>
	<s:include value="/share/footer.jsp" />
</body>
</html>