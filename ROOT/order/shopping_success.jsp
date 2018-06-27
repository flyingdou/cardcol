<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<s:include value="/share/meta.jsp"/>
<meta name="keywords" content="购物车" />
<meta name="description" content="购物车" />
<title>健身E卡通_购物车</title>
<link rel="stylesheet" type="text/css" href="css/pulic-1.css"/>
<link rel="stylesheet" type="text/css" href="css/pay.css"/>
</head>
<body>
<s:include value="/share/home-header_1.jsp" />
<div class="pay_plan">
	<div class="success_nav">
        <ul>
           <li><b>1.</b>提交定单</li>
           <li><b>2.</b>选择支付方式</li>
           <li><span><b>3.</b>购买成功</span></li>
        </ul>
	</div>
	<div class="pay_content">
		<div class="cuccess_shopping">
			<div class="jingd"><img src="images/success_jd.gif" /></div>
			<div class="cuccess_content">
				<b>购买成功！</b>
           		<p class="cuccess_jine">应付金额：<span class="cuccess_qian">￥<s:property value="totalMoney"/></span><span class="cucc_danw">元</span>, &nbsp;&nbsp; 
           			你已付款:<span class="cuccess_qian">￥<s:property value="totalMoney"/></span><span class="cucc_danw">元</span>,&nbsp;&nbsp; 
           			还需付款&nbsp;<span class="cuccess_qian">￥0.00</span><span class="cucc_danw">元</span>,&nbsp;&nbsp;&nbsp;
           			支付方式:<span class="cuccess_qian">
           		<s:if test="payType==2">银行付款</s:if>
           		<s:if test="payType==1">支付宝</s:if></span></p>
              	<p class="cuccess_info">请您进入健身E卡通查看相关信息或凭手机短信上的订单号到商户消费。</p>
          	</div>
      	</div>
	</div>
</div>
<s:include value="/share/footer.jsp"/>
</body>
</html>