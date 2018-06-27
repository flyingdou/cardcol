<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<title>我的钱包</title>
<link type="text/css" href="${pageContext.request.contextPath}/WX/css/bootstrap.css" rel="stylesheet">
<link type="text/css" href="${pageContext.request.contextPath}/WX/css/swiper.min.css" rel="stylesheet">
<link type="text/css" href="${pageContext.request.contextPath}/WX/css/base.css" rel="stylesheet">
<link type="text/css" href="${pageContext.request.contextPath}/WX/css/style.css" rel="stylesheet">
<link type="text/css" href="${pageContext.request.contextPath}/WX/css/index.css" rel="stylesheet">
<script type="text/javascript" src="${pageContext.request.contextPath}/WX/js/jquery-2.2.0.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/WX/js/swiper-3.4.0.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/WX/js/style.js"></script>
<script type="text/javascript">
function query1(){
	$.ajax({
		type : "post",
		url : "mywalletwx!income.asp",
		data:null,
		success:function(msg){
			$("#part1").html($(msg).find("#part1").html());
	}
	});
	document.getElementById('part1').style.display = 'block';
	document.getElementById('part2').style.display = 'none';
	document.getElementById('part3').style.display = 'none';
}
function query2(){
	$.ajax({
		type : "post",
		url : "mywalletwx!expenditure.asp",
		data:null,
		success:function(msg){
			$("#part2").html($(msg).find("#part2").html());
	}
	});
	document.getElementById('part1').style.display = 'none';
	document.getElementById('part2').style.display = 'block';
	document.getElementById('part3').style.display = 'none';
}
function query3(){
	$.ajax({
		type : "post",
		url : "mywalletwx!findPickDetail.asp",
		data:null,
		success:function(msg){
			$("#part3").html($(msg).find("#part3").html());
	}
	});
	document.getElementById('part1').style.display = 'none';
	document.getElementById('part2').style.display = 'none';
	document.getElementById('part3').style.display = 'block';
}
</script>
</head>
<body>
<div class="join_challenge container">
	<div class="table_title">
        <ul class="clearfix">
            <li class="col-xs-3" style="width: 30%;" onclick="query1()">收入</li>
            <li class="col-xs-3" style="width: 30%;" onclick="query2()">支出</li>
            <li class="col-xs-3" style="width: 30%;" onclick="query3()">提现</li>
        </ul>
    </div>
	<div id="part1">
			<div class="table_list">
				<s:iterator value="pageInfo.items">
					<div class="list">
							<div class="txt fl">
								<p>
									日期：<s:property value="balanceTime" />
								</p>
								<p>
								付款人：<s:property value="fromName"/>
								</p>
								<p>
								项目：<s:property value="prodName"/>
								</p>
								<p>
								金额：<s:property value="balanceMoney"/>
								</p>
							</div>
					</div>
				</s:iterator>
			</div>
	</div>
	<div id="part2" style="display: none;">
			<div class="table_list">
				<s:iterator value="pageInfo.items">
					<div class="list">
							<div class="txt fl">
								<p>
									日期：<s:property value="payTime" />
								</p>
								<p>
								收款人：<s:property value="toName"/>
								</p>
								<p>
								项目：<s:property value="NAME"/>
								</p>
								<p>
								金额：<s:property value="orderMoney"/>
								</p>
							</div>
					</div>
				</s:iterator>
			</div>
	</div>
	<div id="part3" style="display: none;">
			<div class="table_list">
				<s:iterator value="pageInfo.items">
					<div class="list">
							<div class="txt fl">
								<p>
									日期：<s:property value="evalTime" />
								</p>
								<p>
								金额：<s:property value="pickMoney"/>
								</p>
								<p>
								收款账户：<s:property value="bankName"/>:<s:property value="account"/>
								</p>
							</div>
					</div>
				</s:iterator>
			</div>
			<div class="bottom">
		        <a href="#" class="share fl">余额：<s:property value="#request.pickMoneyCount"/></a>
		        <a href="mywalletwx!savePickDetail.asp" class="join fl">提现</a>
		    </div>
	</div>
</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/WX/js/imgs.js"></script>
</body>
</html>