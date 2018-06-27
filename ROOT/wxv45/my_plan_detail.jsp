<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!doctype html>
<html>
<head>
<meta charset="UTF-8">
<title></title>
<meta name="viewport"
	content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
<link href="${pageContext.request.contextPath}/eg/css/mui.min.css"
	rel="stylesheet" />
<style>
.footer {
	height: 44px;
	line-height: 44px;
	background: #FF4401;
	text-align: center;
	position: fixed;
	bottom: 0;
	width: 100%;
}

.footer a {
	color: white;
	font-size: 15px;
}

.wxtip {
	background: rgba(0, 0, 0, 0.8);
	text-align: center;
	position: fixed;
	left: 0;
	top: 0;
	width: 100%;
	height: 100%;
	z-index: 998;
	display: none;
}

.wxtip-icon {
	width: 52px;
	height: 67px;
	background: url(weixin-tip.png) no-repeat;
	display: block;
	position: absolute;
	right: 20px;
	top: 20px;
}

.wxtip-txt {
	margin-top: 107px;
	color: #fff;
	font-size: 16px;
	line-height: 1.5;
}
</style>
</head>
<body>
	<!-- 遮罩层  -->
	<div class="wxtip" id="JweixinTip">
		<span class="wxtip-icon"></span>
		<p class="wxtip-txt">
			点击右上角<br />选择在浏览器中打开
		</p>
	</div>

	<div class="mui-content">
		<ul class="mui-table-view" style="margin-top: 0px;">
			<!-- 动作列表开始 -->
			<div id="planDetail">
				<li class="mui-table-view-cell mui-media" v-for="plan in plans">
					<a href="#"> 
					<!-- <img class="mui-media-object mui-pull-left" v-bind:src="plan.action.image"> -->
					<img class="mui-media-object mui-pull-left" src="${pageContext.request.contextPath}/wxv45/images/sport.png">
						<div class="mui-media-body">
							{{ plan.action.name }}
							<p class="mui-ellipsis">{{ plan.detail }}</p>
						</div>
				</a>
				</li>
			</div>
			<!-- 动作列表结束 -->
		</ul>
	</div>
	<div class="footer">
		<a id="JdownApp" href="http://www.ecartoon.com.cn/app/ecartoonV1.apk">下载E卡通APP，查看健身详情</a>
	</div>
	<input id="pageInfo" type="hidden"
		value="<s:property value='#request.pageInfo'/>" />
	<script src="${pageContext.request.contextPath}/eg/js/mui.min.js"></script>
	<script src="${pageContext.request.contextPath }/eg/js/jquery-2.1.4.js"
		type="text/javascript" charset="utf-8"></script>
	<script src="https://cdn.bootcss.com/vue/2.2.2/vue.min.js"></script>
	<script type="text/javascript">
		mui.init()
	</script>
	<script type="text/javascript">
		$(function() {
			var pageInfoObj = jQuery.parseJSON($("#pageInfo").val());
			var plans = pageInfoObj.items;

			new Vue({
				el : '#planDetail',
				data : {
					plans : plans
				}
			})
		})
	</script>
	<script>
		function weixinTip(ele) {
			var ua = navigator.userAgent;
			var isWeixin = !!/MicroMessenger/i.test(ua);
			if (isWeixin) {
				ele.onclick = function(e) {
					window.event ? window.event.returnValue = false : e
							.preventDefault();
					document.getElementById('JweixinTip').style.display = 'block';
				}
				document.getElementById('JweixinTip').onclick = function() {
					this.style.display = 'none';
				}
			}
		}

		var btn1 = document.getElementById('JdownApp');//下载一
		weixinTip(btn1);
	</script>
</body>
</html>