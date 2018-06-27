<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!doctype html>
<html>
<head>
<meta charset="UTF-8">
<title>我参加的挑战</title>
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
<link href="${pageContext.request.contextPath}/eg/css/mui.min.css" rel="stylesheet" />
<style>
.mui-table-view .mui-media-object {
	line-height: 60px;
	max-width: 80px;
	height: 60px;
}

.mui-media-body {
	padding: 5px 0;
}

.mui-ellipsis {
	font-size: 12px;
	line-height: 16px;
}

.thisimg {
	position: absolute;
	right: 0;
	top: 0;
	width: 60px;
}

.mt0 {
	margin-top: 0 !important;
}
</style>
</head>

<body>
	<div class="mui-content" id="myactive">
		<ul class="mui-table-view mt0">
			<li class="mui-table-view-cell mui-media" v-for="active in actives">
				<a :href="'activejoinwxv45!myActiveDetail.asp?activeId=' + active.id">
					<img class="mui-media-object mui-pull-left" src="${pageContext.request.contextPath}/wxv45/images/wdtz.png">
					<div class="mui-media-body">
						{{ active.name }}
						<p class="mui-ellipsis">{{ active.applyCount }}人参加</p>
						
						<p v-if="active.target === 'A'" class="mui-ellipsis">目标：体重减少 </p>
						<p v-else-if="active.target === 'B'" class="mui-ellipsis">目标：体重增加 </p>
						<p v-else-if="active.target === 'C'" class="mui-ellipsis">目标：运动时间 </p>
						<p v-else-if="active.target === 'D'" class="mui-ellipsis">目标：运动次数 </p>
					</div>
					<!-- 要判断状态 -->
					<img v-if="active.result === '0'" src="${pageContext.request.contextPath}/wxv45/images/ongoing.png" class="thisimg" alt="进行" />
					<img v-else-if="active.result === '2'" src="${pageContext.request.contextPath}/wxv45/images/failure.png" class="thisimg" alt="失败" />
					<img v-else-if="active.result === '3'" src="${pageContext.request.contextPath}/wxv45/images/ended.png" class="thisimg" alt="结束" />
					<img v-else-if="active.result === '1'" src="${pageContext.request.contextPath}/wxv45/images/successful.png" class="thisimg" alt="成功" />
				</a>
			</li>
		</ul>
	</div>
	<input id="pageInfo" type="hidden" value="<s:property value='#request.items'/>" />
	<script src="${pageContext.request.contextPath}/eg/js/mui.min.js"></script>
	<script src="${pageContext.request.contextPath }/eg/js/jquery-2.1.4.js" type="text/javascript" charset="utf-8"></script>
	<script src="https://cdn.bootcss.com/vue/2.2.2/vue.min.js"></script>
	<script type="text/javascript">
		mui.init()
	</script>
	<script type="text/javascript">
		$(function() {
			var pageInfoObj = jQuery.parseJSON($("#pageInfo").val());
			var actives = pageInfoObj;
			
			new Vue({
				  el: '#myactive',
				  data : {
					  actives : actives
				  } 
			})
		})
	</script>
</body>
</html>