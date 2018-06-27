<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" />
    <title>提交订单</title>
    <link type="text/css" href="${pageContext.request.contextPath}/WX/css/bootstrap.css" rel="stylesheet"/>
    <link type="text/css" href="${pageContext.request.contextPath}/WX/css/base.css" rel="stylesheet"/>
    <link type="text/css" href="${pageContext.request.contextPath}/WX/css/style.css" rel="stylesheet"/>
    <link type="text/css" href="${pageContext.request.contextPath}/WX/css/index.css" rel="stylesheet"/>
    <script type="text/javascript" src="${pageContext.request.contextPath}/WX/js/jquery-2.2.0.min.js"></script>
   <script type="text/javascript">
   function saveAudit(){
	   /* var val =$("#phone").text();
	   if(val = "未绑定手机"){
		   alert("请您先绑定手机");
	   }else{
		   url = 'orderswx!payprod.asp'
		   $('#payForm').attr("action", url);
		   $('#payForm').submit();
	   }  */
	   
	   if($('#phone').html()!=null){
		   url = 'orderswx!payprod.asp'
			   $('#payForm').attr("action", url);
			   $('#payForm').submit();
		}else{
			alert("必须绑定手机号才能提交！");
		}
	   }
   </script>
</head>
<body>

<div class="container">
<form id="payForm" action="orderswx!payprod.asp" method="post">
		<s:iterator value="#session.member">
        <div class="user_message">
            <p>用户昵称<span><s:property value="name"/></span></p>
            <p>手机号
             <s:if test="mobilephone != null">
             <span id="phone">
             <s:property value="mobilephone"/>
             </span>
             </s:if>
            <s:else>
            <span>
             <a href="personalPhone.jsp">未绑定手机</a>
            <span>
            </s:else>
            </p>
            <input type="text" placeholder="请输入收货地址">
        </div>
		</s:iterator>
        <div class="user_message TheLast">
        <s:iterator value="#request.list" status="stat">
            <p>商品名称<span><s:property value="pro_name"/></span></p>
            <p>开始时间<span>
	            <input name="shopDtos[<s:property value="#stat.index"/>].start_date" value="<s:property value="startDate"/>" style="text-align:right"/> 
            <input type="hidden" name="shopDtos[<s:property value="#stat.index"/>].prod_id" value="<s:property value="pro_id"/>" /> 
			<input type="hidden" name="shopDtos[<s:property value="#stat.index"/>].prod_type" value="<s:property value="type"/>" /> 
			<input type="hidden" name="shopDtos[<s:property value="#stat.index"/>].prod_name" value="<s:property value="pro_name"/>" /> 
			<input type="hidden" name="shopDtos[<s:property value="#stat.index"/>].prod_price" value="<s:property value="price"/>" />
            </span></p>
            <p>商品金额<i>￥<s:property value="PRICE"/></i></p>
            <a href="#">优惠券<img src="images/right_icon_03.png" class="fr"></a>
        </s:iterator>
        </div>

    <div class="assign">
        <a href="javascript:saveAudit();">提交订单</a>
    </div>
    </form>
</div>
</body>
</html>