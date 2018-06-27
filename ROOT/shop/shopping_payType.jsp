﻿<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
response.setHeader("Cache-Control","no-store");
response.setHeader("Pragrma","no-cache");
response.setDateHeader("Expires",0);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<s:include value="/share/meta.jsp"/>
<meta name="keywords" content="购物车" />
<meta name="description" content="购物车" />
<title>健身E卡通_购物车</title>
<link rel="stylesheet" type="text/css" href="css/pay.css"/>

<script type="text/javascript">
function toPay(){
	$("#orderForm").attr({"action":"orderpay!toPay.asp", "target":"orderWindow"});
	$("#orderForm").submit();
	$("#dialogOrder").dialog("open");
}
function savePay(){
	$.ajax({type:'post',url:'orderpay!canPay.asp', data:  $('#orderForm').serialize(), 
		success:function(msg){
			var rtnmsg = eval('('+msg+')');
			if(rtnmsg.success == true){
				$("#orderForm").attr({"action":"orderpay!savePay.asp", "target":"orderWindow"});
				$("#orderForm").submit();
				$("#dialogOrder").dialog("open");
			}else{
				if(rtnmsg.type=='exist'){
					alert(rtnmsg.message);
				}else{
					alert('未知错误，请联系系统设计人员。可能的原因为：' + rtnmsg.message);
				}
			}
		}
	});
}
function sureOrder(status){
	if(status == "0"){
		$("#dialogOrder").dialog("close");
		$.ajax({type:'post',url:'order!paySuccess.asp', data:  $('#orderForm').serialize(), 
			success:function(msg){
				$("body").html(msg);
			}
		});
	}else{
		$("#dialogOrder").dialog("close");
	}
}
$.fx.speeds._default = 1000;
$(function() {
	$( "#dialogProduct" ).dialog({
		autoOpen: false,
		show: "blind",
		hide: "explode",
		resizable: false
	});
	$( "#dialogOrder" ).dialog({
		autoOpen: false,
		show: "blind",
		hide: "explode",
		resizable: false
	});
});
function showProduct(productId){
	$.ajax({type:'post',url:'product!edit.asp',data:"product.id="+productId,
		success:function(msg){
			var product = $.parseJSON(msg)[0];
			$('#productName').html(product.name);
			var courseType = product.courseType;
			if(courseType == '1'){
				$('#productCourseType').html('计次收费');
				$('#productNum').html(product.num+'次');
			}else if(courseType == '2'){
				$('#productCourseType').html('计时收费');
				$('#productNum').html(product.num+'月');
			};
			$('#productCost').html(product.cost+'元');
			$('#productRemark').html(product.remark);
			$( "#dialogProduct" ).dialog( "open" );
		}
	});
}
function onCloseProduct(){
	$( "#dialogProduct" ).dialog( "close" );
}
function preShop(productId,memberId){
	if("<s:property value="#session.loginMember.id"/>" == ""){
		//alert("请先登录才能购买商品！");
		openLogin();
		return;
	}
	$('#productId').val(productId);
	$('#orderForm').attr('action','shop!preShop.asp');
	$('#orderForm').submit();
}
function goBindingMobile(){
	window.open("mobile.asp");
}
</script>
</head>
<body>
<s:include value="/share/home-header_1.jsp" />
<s:form id="orderForm" name="orderForm" action="order!saveAudit.asp" method="post" theme="simple">
 	<s:hidden name="totalMoney" id="totalMoney"/>
	<s:hidden name="subject" id="subject"/>
	<s:hidden name="payNo" id="payNo"/>
	<s:hidden name="product.id" id="productId"/>
<div class="pay_plan">
      <div class="pay_nav">
        <ul>
           <li><b>1.</b>提交定单</li>
           <li><span><b>2.</b>选择支付方式</span></li>
           <li><b>3.</b>购买成功</li>
        </ul>
     </div>
    <div class="pay_content">
       <div class="payinfo">
         <p>商品名称：
         <s:property value="#request.subject" />
         </p>
         <s:if test="#request.mobilephone != null">
       <p>您的手机：购买成功后，订单信息将发送到手机:<span><s:property value="#request.mobilephone" /></span></p>
       </s:if>
       <s:else>
        <p>您还没有绑定手机，<a href="javascript:goBindingMobile();">绑定手机号</a></p>
        </s:else>
       </div>
   		<div class="pay_price">应付金额总计:<span><s:property value="totalMoney"/>元</span></div>
	 
      <div class="pay_bank">
         <%-- <div class="pay_a">  <input type="radio" name="payType" value="2" id="payType"  checked="checked"/><img src="images/pay_zhifu.gif" class="pay_img"/><span>  银联在线支付服务是由中国银联提供的互联网支付服务，全面支持各大银行的信用卡，借寄卡</span></div> --%>
         <div class="pay_a">  <input type="radio" name="payType" value="1" id="payType" checked="checked"/><img src="images/pay_zfb.gif"class="pay_img" /><span>  支付宝支付是直通过支付宝支付储值帐户进行的在线付款的支付方式。</span></div>
      </div>
      <div class="fk"><a href="javascript:toPay();"><img src="images/pay_fk.gif" /></a></div>
  </div>
</div>
</s:form>
<s:include value="/share/footer.jsp"/>	

<div id="dialogOrder" title="支付信息确认">
	<p>
		请您在新打开的网上银行页面进行支付，支付完成前请不要关闭该窗口！
	</p>
<!--
	<p>
		商品总价：<span id="orderCountMoney"></span>
	</p>
-->
	<p class="pa">
	   <input type="button" value="已完成支付" onclick="sureOrder('0')" class="butzhifuok"/>
	   <input type="button" value="重新选择银行" onclick="sureOrder('1')" class="butzhifuok"/>
	</p>
</div>		
</body>
</html>