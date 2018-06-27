<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>商务中心</title>
</head>
<body>
<div id="left-1">
			<h1 class="order"style="background:#ff4401;color:white;font-weight:600">商务中心</h1>
			<div>
				<ul class="order">
				
					<s:if test="#session.loginMember.role != \"M\"">
						<li><a href="#">
								<h1>
									<b>商品管理</b>
								</h1>
						</a>
							<ul class="ordermanagement">
								<s:if test="#session.loginMember.role == \"E\"">
									
										<li><a
										href="product.asp"
										id="coloa">健身卡</a></li>
								</s:if>
								<s:if test="#session.loginMember.role == \"S\"">
									<li><a
										href="product!query.asp?query.type=3"
										id="coloa">健身卡</a></li>
									<li><a href="plan!query.asp"
										id="coloa">健身计划</a></li>
								</s:if>
							</ul></li>
					</s:if>
					<li><a href="#">
							<h1>
								<b>订单管理</b>
							</h1>
					</a>
						<ul class="ordermanagement">
							<s:if test="#session.loginMember.role == \"E\"">
							</s:if>
							<li><a
								href="order!query.asp?queryType=1"
								id="coloa">卖出订单</a></li>
							<!-- <li><a
								href="order!query.asp?queryType=2"
								id="coloa">买入订单</a></li> -->
							
							<li><a 
								href="appraise!query.asp" id="coloa">评价管理</a></li>
						</ul></li>
					<li><a href="#"><h1>
								<b>健身活动</b>
							</h1></a>
						<ul class="ordermanagement">
							
							<li><a href="active.asp" id="coloa">我发起的挑战</a></li>
							<li><a href="clubmp!getTicketList.asp" id="coloa">我发起的优惠券</a></li>
							<li><a href="clubmp!getPriceActiveList.asp" id="coloa">我发起的砍价活动</a></li>
						</ul></li>

					
					<li><a href="#">
							<h1>
								<b>财务管理</b>
							</h1>
					</a>
						<ul class="ordermanagement">
							<li><a href="orderdetail!query.asp"
								id="coloa">我的钱包</a></li>
							<li><a href="pickdetail!query.asp"
								id="coloa">提现</a></li>
							<li><a href="pickdetail!queryList.asp"
								id="coloa">提现记录</a></li>
						</ul></li>
					<!-- <li>
					<a href="#">
							<h1>
								<b>投诉</b>
							</h1>
					</a>
						<ul class="ordermanagement">
							<li><a href="complaint!edit.asp"
								id="coloa">我要投诉</a></li>
							<li><a
								href="complaint!query.asp?type=1"
								id="coloa">我收到的投诉</a></li>
							<li><a
								href="complaint!query.asp?type=2"
								id="coloa">我发起的投诉</a></li>
						</ul></li> -->
				</ul>
			</div>
		</div>
</body>
</html>