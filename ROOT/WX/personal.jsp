<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html>
<html>

	<head>
		<meta charset="UTF-8">
		<title>我的账号</title>
		<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
		<meta name="apple-mobile-web-app-capable" content="yes">
		<meta name="apple-mobile-web-app-status-bar-style" content="black">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/eg/css/mui.min.css" />
		<link rel="stylesheet" href="${pageContext.request.contextPath}/eg/css/imgView.css" />
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/eg/css/mui.picker.min.css" />
		<!--日期选择-->
		<style type="text/css">
			.face {
				height: 70px;
				background-color: white;
				color: #333333;
			}
			
			.face img {
				width: 40px;
				height: 40px;
				margin-left: 20px;
				margin-top: 15px;
				border-radius: 100%;
				float: left;
			}
			
			.face .name {
				float: left;
				margin-left: 15px;
				line-height: 70px;
				color: #333333;
				font-size: 15px;
			}
			/*ul样式*/
			
			.mui-table-view {
				margin-top: 20px;
				font-size: 15px;
				color: gray;
			}
			/*出生日期*/
			
			#chush_date {
				margin-right: 15px;
				text-align: right;
			}
			
			input[type=text] {
				margin: 0px;
				padding: 0px;
				height: auto;
				text-align: right;
			}
			
			.shg {
				float: right;
				margin-right: 15px;
				width: 120px;
				height: 23px;
				line-height: 23px;
				margin-top: 0px;
				margin-bottom: 0px;
				padding: 0px;
				text-align: right;
			}
			
			.shg input {
				height: 23px;
				line-height: 23px;
				margin-top: 0px;
				margin-bottom: 0px;
				padding: 0px;
				padding-top: -2px;
				width: 80px;
				font-size: 15px;
				border: none;
			}
			
			#shengao {
				line-height: 20px;
				padding-top: 0px;
				padding-right: 3px;
				text-align: right;
			}
			
			.text_w {
				margin-right: 15px;
			}
		</style>
	</head>

	<body>
		<footer class="mui-bar mui-bar-footer font-white" style="border:none;background: white;padding: 0;">
			<button type="button" style="background: orange;color:white;width: 100%;height: 100%;">申请入会</button>
		</footer>
		<div class="content">
		<s:iterator value="pageInfo.items">
			<div class="mui-scroll-wrapper">
				<div class="mui-scroll">
					<!--头部-->
					
					<a href="" id='userInfo'>
					<div style="width: 100%;;height: 80px;top:0;background:white;padding: 5px 0px 5px 10px;box-sizing: border-box;overflow: hidden;">
						<div style="width: 70px;height: 70px;float: left;border-radius: 50%;">
							<img src="../picture/<s:property value="image"/>" width="70px" />
						</div>
						<div style="height：70px!important;box-sizing:border-box;float: left;font-size: 13px;padding: 5px;color:#9f9f9f ;">
							<span style="line-height: 35px!important;color:#000000"><s:property value="name"/></span>
							<div style="">已经健身<span style="color:orangered"><s:property value="timeNum"/></span>次</div>
						</div>
						<div style="clear: both;"></div>
					</div>
					</a>
					<ul class="mui-table-view">
						<li class="mui-table-view-cell">
							<a class="mui-navigate-right">我的订单

							</a>
						</li>
						<li class="mui-table-view-cell">
							<a class="mui-navigate-right">我的钱包

							</a>
						</li>
						<li class="mui-table-view-cell">
							<a class="mui-navigate-right">我的挑战

							</a>
						</li>
					</ul>

					<div style='width:100%;background: white;margin-top: 1em;padding:.5em;font-size:13px;text-align: center;'>
						如果您已经是本俱乐部会员，请点击"申请入会"。审核通过后，可以使用"健身打卡","团课预约"等功能
					</div>

				</div>
			</div>
		
			</s:iterator>
		</div>

	</body>
	<script src="${pageContext.request.contextPath}/eg/js/mui.min.js"></script>
	<script src="${pageContext.request.contextPath}/eg/js/mui.picker.min.js"></script>
	<!--日历选择-->
	<script src="${pageContext.request.contextPath}/eg/js/mui.zoom.js"></script>
	<!--图片预览-->
	<script src="${pageContext.request.contextPath}/eg/js/mui.previewimage.js"></script>
	<!--图片预览-->
	<script>
		//图片预览
		mui.previewImage();
		//超出是滚动
		mui('.mui-scroll-wrapper').scroll({
			deceleration: 0.0005
		});


		//用户个人信息
		var go_phoneCheck = document.getElementById('userInfo');
		go_phoneCheck.addEventListener('click', function() {

			location.href = 'userInfo.html';
		})
		//手机验证
		/* var go_phoneCheck = document.getElementById('go_phoneCheck');
		go_phoneCheck.addEventListener('click', function() {

			location.href = 'phoneCheck.html';
		}) */

	</script>

</html>