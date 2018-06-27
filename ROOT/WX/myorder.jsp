<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<title>我的订单</title>
<link type="text/css" href="${pageContext.request.contextPath}/WX/css/bootstrap.css" rel="stylesheet">
<link type="text/css" href="${pageContext.request.contextPath}/WX/css/swiper.min.css" rel="stylesheet">
<link type="text/css" href="${pageContext.request.contextPath}/WX/css/base.css" rel="stylesheet">
<link type="text/css" href="${pageContext.request.contextPath}/WX/css/style.css" rel="stylesheet">
<link type="text/css" href="${pageContext.request.contextPath}/WX/css/index.css" rel="stylesheet">
<script type="text/javascript" src="${pageContext.request.contextPath}/WX/js/jquery-2.2.0.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/WX/js/swiper-3.4.0.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/WX/js/style.js"></script>
<script type="text/javascript">
function query(status){
	$.ajax({
		type : "post",
		url : "myorderwx!findOrder.asp?status="+status,
		data:{status:"status"},
		success:function(msg){
			if(status==1){
				document.getElementById('part1').style.display = 'block';
				document.getElementById('part2').style.display = 'none';
				document.getElementById('part3').style.display = 'none';
				 $("#part1").html($(msg).find("#part1").html());
			}else if(status==0){
				document.getElementById('part1').style.display = 'none';
				document.getElementById('part2').style.display = 'block';
				document.getElementById('part3').style.display = 'none';
				$("#part2").html($(msg).find("#part2").html());
			}else if(status==3){
				document.getElementById('part1').style.display = 'none';
				document.getElementById('part2').style.display = 'none';
				document.getElementById('part3').style.display = 'block';
				$("#part3").html($(msg).find("#part3").html());
			}
	}
	});
}

</script>
</head>
<body>
<div class="join_challenge container">
	<div class="table_title">
        <ul class="clearfix">
            <li class="col-xs-3" style="width: 30%;"><a href="javaScript:query('1')">有效订单</a></li>
            <li class="col-xs-3" style="width: 30%;"><a href="javaScript:query('0')">未付款订单</a></li>
            <li class="col-xs-3" style="width: 30%;"><a href="javaScript:query('3')">已完成订单</a></li>
        </ul>
    </div>
	<div id="part1">
			<div class="table_list">
				<s:iterator value="pageInfo.items">
					<a href="javaScript:detail(<s:property value="type"/>,<s:property value="id"/>)">
					<div class="list">
			            <p>订单编号:<s:property value="No"/></p>
			            <div class="pic fl"><img src="../picture/<s:property value="image"/>"/></div>
			                <div class="txt fl">
			                    <strong><s:property value="NAME"/></strong>
			                    <p>订单时间:<s:property value="orderDate"/></p>
			                    <h6><s:property value="orderMoney"/></h6>
			                </div>
        			</div>
        			</a>
				</s:iterator>
			</div>
	</div>
	<div id="part2" style="display: none;">
			<div class="table_list">
				<s:iterator value="pageInfo.items">
					<a href="">
					<div class="list">
			            <p>订单编号:<s:property value="No"/></p>
			            <div class="pic fl"><img src="../picture/<s:property value="image"/>"/></div>
			                <div class="txt fl">
			                    <strong><s:property value="NAME"/></strong>
			                    <p>订单时间:<s:property value="orderDate"/></p>
			                    <h6><s:property value="orderMoney"/></h6>
			                </div>
        			</div>
        			</a>
				</s:iterator>
			</div>
	</div>
	<div id="part3" style="display: none;">
			<div class="table_list">
				<s:iterator value="pageInfo.items">
					<div class="list">
			            <p>订单编号:<s:property value="No"/></p>
			            <div class="pic fl"><img src="../picture/<s:property value="image"/>"/></div>
			                <div class="txt fl">
			                    <strong><s:property value="NAME"/></strong>
			                    <p>订单时间:<s:property value="orderDate"/></p>
			                    <h6><s:property value="orderMoney"/></h6>
			                </div>
        			</div>
				</s:iterator>
			</div>
	</div>
</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/WX/js/imgs.js"></script>
<script type="text/javascript">
function detail(type,id){
	$.ajax({
		type : "post",
		url : "myorderwx!OrderDetail.asp",
		data:{type:"type",id:"id"},
		success:function(msg){
			
	}
	});
			
}
</script>
</body>
</html>