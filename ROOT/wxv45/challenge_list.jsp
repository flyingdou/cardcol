<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!doctype html>
<html>
<head>
<meta charset="UTF-8">
<title>健身挑战</title>
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/eg/css/mui.min.css" />
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
			<!-- 挑战列表开始 -->
			<s:iterator value="#request.map.items">
				<li class="mui-table-view-cell mui-media" onclick="showActiveDetail(<s:property value="id"/>)">
					<a href="javascript:;">
						<%-- <img class="mui-media-object mui-pull-left" src="${pageContext.request.contextPath}/picture/<s:property value="image" />"> --%>
						<img class="mui-media-object mui-pull-left" src="${pageContext.request.contextPath}/wxv45/images/jstz.png">
						<div class="mui-media-body">
							<s:property value="name" />
							<p class="mui-ellipsis ">
								<s:if test="applyCount!=null">
									<s:property value="applyCount" />
								</s:if>
								<s:else>0</s:else>
								人参加
							</p>
							<p class="mui-ellipsis">
								<span id="tackpart_m">
									<s:if test="category==\"A\"">
										<s:property value="days" />天内体重增加<s:property value="value" />KG</s:if>
									<s:elseif test="category==\"B\"">
										<s:property value="days" />天内体重减少<s:property value="value" />KG</s:elseif>
									<s:elseif test="category==\"D\"">
										<s:property value="days" />天内运动<s:property value="value" />次</s:elseif>
									<s:elseif test="category==\"E\"">
										<s:property value="days" />天内运动<s:property value="value" />小时</s:elseif>
									<s:elseif test="category==\"F\"">
										<s:property value="days" />天内每周运动<s:property value="value" />次</s:elseif>
									<s:elseif test="category==\"G\"">
										<s:property value="days" />天内
											<s:if test="action != null">
											<s:property value="action" />
										</s:if>
										<s:else>
											<s:property value="content" />
										</s:else>
										<s:property value="value" />千米</s:elseif>
									<s:elseif test="category==\"H\"">
										<s:property value="days" />天负荷<s:property value="value" />Kg
											</s:elseif>
								</span>
							</p>
						</div>
					</a>
				</li>
			</s:iterator>
			<!-- 挑战列表结束 -->
		</ul>
	</div>
	<script src="${pageContext.request.contextPath}/eg/js/mui.min.js"></script>
	<script type="text/javascript">
		mui.init()
	</script>
	<script type="text/javascript">
		function showActiveDetail (id) {
			window.location.href="activelistwxv45!detail.asp?id=" + id
		}
	</script>
</body>
</html>