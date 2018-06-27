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
.cr {
	color: #ff4401;
	font-size: 16px;
}

.mui-navigate-right {
	font-size: 14px;
}
</style>
</head>

<body>
	<div class="mui-content">
		<s:iterator value="pageInfo.items">
			<ul class="mui-table-view" style="margin-top: 0px;">
				<li class="mui-table-view-cell mui-media">
					<a href="javascript:;">
						<img class="mui-media-object mui-pull-left" src="${pageContext.request.contextPath}/wxv45/images/user_heard_image.png" style="border-radius: 50%">
						<div class="mui-media-body">
							<s:property value="name"/>
							<p class="mui-ellipsis">
								已健身
								<span class="cr"><s:property value="timeNum"/></span>
								&nbsp;次
							</p>
						</div>
					</a>
				</li>

			</ul>
			<ul class="mui-table-view" style="margin-top: 11px;">
				<li class="mui-table-view-cell">
					<a class="mui-navigate-right" href="myorderwxv45!findOrder.asp"> 我的订单 </a>
				</li>
				<li class="mui-table-view-cell">
					<a class="mui-navigate-right" href="mywalletwxv45.asp"> 我的钱包 </a>
				</li>
				<li class="mui-table-view-cell">
					<a class="mui-navigate-right" href="activejoinwxv45.asp"> 我的挑战 </a>
				</li>
			</ul>
		</s:iterator>
	</div>
	<script src="${pageContext.request.contextPath }/eg/js/mui.min.js"></script>
	<script type="text/javascript">
		mui.init()
	</script>
</body>

</html>