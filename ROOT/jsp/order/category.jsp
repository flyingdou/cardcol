<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div id="content">
	<div id="left-1">
		<h1 class="order">商务中心</h1>
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
									href="javascript:goAjax('product!query.asp?query.type=1');"
									id="coloa">健身卡</a></li>
							</s:if>
							<s:if test="#session.loginMember.role == \"S\"">
								<li><a
									href="javascript:goAjax('product!query.asp?query.type=3');"
									id="coloa">健身卡</a></li>
								<li><a href="javascript:goAjax('plan!query.asp');"
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
							href="javascript:goAjax('order!query.asp?queryType=1');"
							id="coloa">卖出订单</a></li>
						<li><a
							href="javascript:goAjax('order!query.asp?queryType=2');"
							id="coloa">买入订单</a></li>
						<li><a
							href="javascript:goAjax('myappraise!queryAppraise.asp');"
							id="coloa">我的评价</a></li>
						<li><a id="coloa"
							href="javascript:goAjax('appraise!query.asp','11');">评价管理</a></li>
					</ul></li>
				<li><a href="#"><h1>
							<b>健身挑战</b>
						</h1></a>
					<ul class="ordermanagement">
						<li><a href="javascript:goAjax('joinactive.asp');" id="coloa">我参加的挑战</a></li>
						<li><a href="javascript:goAjax('active.asp');" id="coloa">我发起的挑战</a></li>
						<li><a href="javascript:goAjax('record.asp');" id="coloa">待审批的记录</a></li>
					</ul></li>

				<li><a href="#"><h1>
							<b>考勤管理</b>
						</h1></a>
					<ul class="ordermanagement">
						<li><a href="attendance.asp?category=M" id="coloa">考勤管理</a></li>
					</ul></li>
				<li><a href="#">
						<h1>
							<b>财务管理</b>
						</h1>
				</a>
					<ul class="ordermanagement">
						<li><a href="javascript:goAjax('orderdetail!query.asp');"
							id="coloa">结算记录</a></li>
						<li><a href="javascript:goAjax('pickdetail!query.asp');"
							id="coloa">提现</a></li>
						<li><a href="javascript:goAjax('pickdetail!queryList.asp');"
							id="coloa">提现记录</a></li>
					</ul></li>
				<li><a href="#">
						<h1>
							<b>投诉</b>
						</h1>
				</a>
					<ul class="ordermanagement">
						<li><a href="javascript:goAjax('complaint!edit.asp');"
							id="coloa">我要投诉</a></li>
						<li><a
							href="javascript:goAjax('complaint!query.asp?type=1');"
							id="coloa">我收到的投诉</a></li>
						<li><a
							href="javascript:goAjax('complaint!query.asp?type=2');"
							id="coloa">我发起的投诉</a></li>
					</ul></li>
			</ul>
		</div>
	</div>
</div>
