<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!doctype html>
<html>

<head>
<meta charset="UTF-8">
<title>私教套餐</title>
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
<link href="${pageContext.request.contextPath}/eg/css/mui.min.css" rel="stylesheet" />
<style>
.mui-table-view .mui-media-object {
	line-height: 60px;
	max-width: 80px;
	height: 60px;
}

.cr {
	color: #ff4401;
}

.mui-media-body {
	font-size: 15px;
}

p {
	font-size: 13px;
	line-height: 20px;
}
</style>
</head>
<body>
	<div class="mui-content">
		<ul class="mui-table-view" style="margin-top: 0;">
			<!-- 套餐列表开始 -->
			<s:iterator value="pageInfo.items">
				<li class="mui-table-view-cell mui-media">
					<a href="mmemberwxv45!privateMealPackageDetail.asp?packageId=<s:property value='id'/>">
						<%-- <img class="mui-media-object mui-pull-left" src="images/<s:property value="image1"/>"> --%>
						<img class="mui-media-object mui-pull-left" src="${pageContext.request.contextPath}/wxv45/images/sjtc.png">
						<div class="mui-media-body">
							<s:property value="name" />
							<p class="mui-ellipsis cr">
								<s:property value="cost" />
								元
							</p>
							<p class="mui-ellipsis">
								<s:property value="freeProject" />
							</p>
						</div>
					</a>
				</li>
			</s:iterator>
			<!-- 套餐列表结束 -->
		</ul>
	</div>
	<script src="${pageContext.request.contextPath}/eg/js/mui.min.js"></script>
	<script type="text/javascript">
		mui.init()
	</script>
</body>
</html>