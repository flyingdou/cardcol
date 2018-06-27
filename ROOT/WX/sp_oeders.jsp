<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <title>商品订单</title>
    <link type="text/css" href="css/bootstrap.css" rel="stylesheet">
    <link type="text/css" href="css/base.css" rel="stylesheet">
    <link type="text/css" href="css/style.css" rel="stylesheet">
    <link type="text/css" href="css/index.css" rel="stylesheet">
    <script type="text/javascript" src="js/jquery-2.2.0.min.js"></script>
    <script type="text/javascript" src="js/style.js"></script>
</head>
<body>
<div class="container">
    <div class="name">
        <p>商品名称<span><s:property value="subject" /></span></p>
    </div>
    <%-- <div class="coupon">
        <a href="#">优惠券<img src="images/right_icon_03.png" class="fr"><span>无可用优惠券</span></a>
    </div> --%>
    <div class="coupon">
        <p>开始时间<span class="fr"><s:property value="startDate" /></span></p>
    </div>
    <div class="total_price ">
        <p>应付总价<span class="fr">￥<s:property value="totalMoney" /></span></p>
    </div>
        <div class="financed">
            <div class="financed_list">
                <img src="images/zhifu_icon_03.png">
                <span>微信支付</span>
                <label class="first_label label_one"><input type="radio" name="checked" value="" checked class="check_one"></label>
            </div>
            <div class="financed_list">
                <img src="images/zhifu_icon_06.png">
                <span>支付宝支付</span>
                <label class="label_one"><input type="radio" name="checked" value="" class="check_one"></label>
            </div>
            <div class="financed_list last">
                <img src="images/zhifu_icon_09.png">
                <span>银联支付<i>(借记卡/储蓄卡)</i></span>
                <label class="label_one"><input type="radio" name="checked" value="" class="check_one"></label>
            </div>
        </div>
    <div class="assign col-xs-12">
        <a href="#">确定购买</a>
    </div>
</div>
</body>
</html>