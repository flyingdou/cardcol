<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
<meta name="apple-mobile-web-app-capable" content="yes">
<title>我参加的挑战-挑战详情</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/eg/css/mui.min.css" />
<style type="text/css">
.title {
	height: 150px;
	background-image: url(${pageContext.request.contextPath}/wxv45/images/banner.png);
	background-repeat: no-repeat;
	background-position: 100% auto;
	background-size: 100% 100%;
}

.zhezhao {
	background-color: rgba(0, 0, 0, .7);
	position: absolute;
	top: 0;
	width: 100%;
	height: 150px;
	z-index: 1111;
}

.yh_img {
	float: left;
	width: 100px;
	height: 75px;
	margin-left: 20px;
	margin-top: 35px;
}

.xx {
	float: left;
	margin: 35px 10px;
	padding: 15px 0;
}

.xx .mc {
	color: white;
}

.xx .bt {
	color: white;
	font-size: 12px;
	line-height: 6px;
}

.mui-table-view {
	margin-top: 20px;
	margin-bottom: 20px;
}

.list {
	line-height: 15px;
}

.list img {
	width: 20px;
	height: 20px;
	margin-right: 5px;
}

.list .xm {
	color: #a0a0a0;
	position: absolute;
	top: 13px;
	font-size: 15px;
}

.list .zt {
	color: #FF4401;
	font-size: 15px;
}

.zhuyi {
	font-size: 15px;
	font-weight: bold;
	margin-bottom: 10px;
	color: #666666;
}

.zhuyi_nr {
	font-size: 15px;
	color: #999999;
	height: 120px;
}
/*分享图标*/
.mui-bar .mui-icon {
	font-size: 20px;
	color: white;
}
/*底部样式*/
.fu {
	line-height: 45px;
	width: 50%;
	float: left;
	text-align: center;
	font-size: 15px;
	background-color: #666666;
}

.mui-bar-footer span:nth-child(2) {
	float: left;
	line-height: 45px;
	width: 50%;
	background-color: #FF6A00;
	margin-right: 0px;
	color: #FFFFFF;
	overflow: hidden;
	text-align: center;
	font-size: 15px;
}
/*去掉底部左右两边的padding值*/
.mui-bar {
	padding-right: 0px;
	padding-left: 0px;
}

#share {
	color: #FFFFFF;
}

.cw {
	color: white !important;
	line-height: 44px;
	height: 44px;
	text-align: center;
	width: 100%;
	background: #FF4401;
	display: block;
}
</style>
</head>
<body>
	<s:iterator value="active">
	<footer class="mui-bar mui-bar-footer">
		<a href="activelistwxv45!joinChallenge.asp?id=<s:property value='id' />" class="cw">报名参加</a>
	</footer>
		<div class="mui-content">
			<div class="mui-scroll-wrapper">
				<div class="mui-scroll">
					<div class="title">
						<div class="zhezhao">
							<img src="${pageContext.request.contextPath}/wxv45/images/banner.png" class="yh_img" />
							<div class="xx">

								<p class="bt">
									发布人：
									<span id="name">
										<s:property value="creator.name" />
									</span>
								</p>
								<p class="bt">
									裁判人：
									<span id="caipan"></span>
								</p>
								<p class="bt">
									参加人数：
									<span id="number">
										<s:property value="#request.count" />
									</span>
									人
								</p>
							</div>
						</div>
					</div>
					<!--这里放置真实显示的DOM内容-->
					<!--挑战目标部分-->
					<ul class="mui-table-view">
						<li class="mui-table-view-cell list">
							<img src="${pageContext.request.contextPath}/wxv45/images/01.png" />
							
							<span class="xm"style="font-size:14px!important">挑战目标</span>
							<span class="mui-pull-right zt" id="goal"style="font-size:13px!important">
								<s:if test="category=='A'">
									<s:property value="days" />天内体重增加<s:property value="value" />KG</s:if>
								<s:elseif test="category=='B'">
									<s:property value="days" />天内体重减少<s:property value="value" />KG</s:elseif>
								<s:elseif test="category=='D'">
									<s:property value="days" />天内运动<s:property value="value" />次</s:elseif>
								<s:elseif test="category=='E'">
									<s:property value="days" />天内运动<s:property value="value" />小时</s:elseif>
								<s:elseif test="category=='F'">
									<s:property value="days" />天内每周运动<s:property value="value" />次</s:elseif>
								<s:elseif test="category=='G'">
									<s:property value="days" />天内
										<s:if test="action != null">
										<s:property value="action" />
									</s:if>
									<s:else>
										<s:property value="content" />
									</s:else>
									<s:property value="value" />千米</s:elseif>
								<s:elseif test="category=='H'">
									<s:property value="days" />天负荷<s:property value="value" />Kg
										</s:elseif>
							</span>
						</li>
						<li class="mui-table-view-cell list">
							<img src="${pageContext.request.contextPath}/wxv45/images/02.png" />
							<span class="xm"style="font-size:14px!important">成功奖励</span>
							<span class="mui-pull-right zt" id="goal"style="font-size:13px!important">
								<s:property value="award" />
							</span>
						</li>
						<li class="mui-table-view-cell list">
							<img src="${pageContext.request.contextPath}/wxv45/images/03.png" />
							<span class="xm"style="font-size:14px!important">失败惩罚</span>
							<span class="mui-pull-right zt" id="goal"style="font-size:13px!important">
								向
								<s:property value="institution.name" />
								捐款
								<s:property value="amerceMoney" />
								元
							</span>
						</li>
					</ul>
					<ul class="mui-table-view">
						<li class="mui-table-view-cell">
							<div class="zhuyi"style="font-size:14px!important">注意事项</div>
							<p class="zhuyi_nr"style="font-size:13px!important">运动成绩请记录到训练日志中，裁判评判成绩的真实性。挑战成功，保证金全额退还。</p>
						</li>
					</ul>
				</div>
			</div>
		</div>
	</s:iterator>
</body>
<script src="${pageContext.request.contextPath}/eg/js/mui.min.js"></script>
<script>
	mui.plusReady(function() {

		//点击报名时跳转到报名页面
		//			var enter=document.getElementById('enter');
		//			enter.addEventListener('tap',function(){
		//				mui.openWindow({
		//					url:'enter.html',
		//					id:'enter.html'
		//				})
		//			});
	});
</script>
</html>
