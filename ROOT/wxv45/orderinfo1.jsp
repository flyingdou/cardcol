<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!doctype html>
<html>
<head>
<meta charset="UTF-8">
<title></title>
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
<link href="${pageContext.request.contextPath}/eg/css/mui.min.css" rel="stylesheet" />
<style type="text/css">
.put {
	height: 21px !important;
	line-height: 21px !important;
	padding: 0 !important;
	margin: 0 !important;
	width: 150px !important;
	text-align: right;
	padding-right: 1em !important;
	border: none !important
}
.cr {
	color: #FF4401
}
</style>
</head>
<body>
	<form action="" method="post">
		<ul class="mui-table-view">
			<!-- 订单信息展现开始 -->
			<s:iterator value="pageInfo.items">
				<li class="mui-table-view-cell">
					<a class="mui-navigate-right">
						用户名称
						<input type="text" name="" id="" value="<s:property value="#session.member.name" />" placeholder="用户昵称" class="mui-pull-right put" />
					</a>
				</li>
				<li class="mui-table-view-cell">
					<a class="mui-navigate-right">
						手机号
						<input type="number" name="" id="" value="<s:property value="#session.member.mobilephone" />" placeholder="请输入手机号码" class="mui-pull-right put" />
					</a>
				</li>
				<li class="mui-table-view-cell">
					<a class="">
						商品名称 
						<input type="text" value="<s:property value='orderName' />" class="mui-pull-right put" />
					</a>
				</li>
				<li class="mui-table-view-cell">
					<a class="">
						开始时间
						<input type="text" readonly="true" name="" id="" value="2017-08-11" placeholder="" class="mui-pull-right put" />
					</a>
				</li>
				<li class="mui-table-view-cell">
					<a class="">
						商品金额
						<input type="number" name="" id="" value="<s:property value="orderCost" />" placeholder="" class="mui-pull-right put cr" />
					</a>
				</li>
			</s:iterator>
			<!-- 订单信息结束 -->
		</ul>
		<input type="submit" value="提交订单"
			style="background: #FF4401; border: none; width: 100%; position: fixed; bottom: 0; height: 44px; line-height: 44px !important; padding: 0;" />
	</form>
	<script src="${pageContext.request.contextPath}/eg/js/mui.min.js"></script>
	<script type="text/javascript">
		mui.init()
	</script>
</body>
</html>