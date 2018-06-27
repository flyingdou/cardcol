
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<title>挑战详情</title>
<link type="text/css" href="${pageContext.request.contextPath}/WX/css/bootstrap.css" rel="stylesheet">
<link type="text/css" href="${pageContext.request.contextPath}/WX/css/base.css" rel="stylesheet">
<link type="text/css" href="${pageContext.request.contextPath}/WX/css/style.css" rel="stylesheet">
<link type="text/css" href="${pageContext.request.contextPath}/WX/css/index.css" rel="stylesheet">
<script type="text/javascript" src="${pageContext.request.contextPath}/WX/js/jquery-2.2.0.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/WX/js/style.js"></script>
<script type="text/javascript">
</script>
</head>
<body>
	<div class="challengeXp container">
		<div class="banner">
			<div class="bg_color">
				<div class="pic fl">
					<img
						src="../picture/<s:property value="#request.Active.image"/>"alt="">
				</div>
				<div class="txt fl">
					<p>
						发布人：<span><s:property value="#session.toMember.name" /></span>
					</p>
					<p>
						裁判人：<span id="cpr">
						</span>
					</p>
					<p>
						参加人数：<span><s:property value="#request.count" />人</span>
					</p>
				</div>
			</div>
		</div>
		<div class="challenge_list">
			<div class="list_one">
				<div class="icon">
					<img src="images/D_icon_07.png">
				</div>
				<p>
					挑战目标<span id="md"></span>
				</p>
			</div>
			<div class="list_one">
				<div class="icon">
					<img src="images/D_icon_10.png">
				</div>
				<p>
					成功奖励<span><s:property value="#request.Active.award" /></span>
				</p>
			</div>
			<div class="list_one">
				<div class="icon">
					<img src="images/D_icon_12.png">
				</div>
				<p>
					失败惩罚<span>向<s:property
							value="#request.Active.institution.name" />捐款 <s:property
							value="#request.Active.amerceMoney" />元
					</span>
				</p>
			</div>
		</div>
		<div class="note">
			<h3>注意事项</h3>
			<s:property value="#request.Active.memo" />
			<p>
				<span>若挑战成功，保证金全额退回！ </span>
			</p>
		</div>

		<div class="bottom">
			<a href="#" class="share fl">分享</a><a href="JavaScript:void(0);"
				 class="join fl">查看</a>
		</div>
		<div class="share_bg" style="display: none">
			<div class="share_show">
				<div class="inner_top clearfix">
					<h3>
						分享<span>即将获得</span>2元红包
					</h3>
					<h5>日志好精彩～赶紧分享吧</h5>
					<div class="icon_top">
						<a href="#"><img src="images/share_icon_03.png" alt=""></a>
						<p>qq好友</p>
					</div>
					<div class="icon_top">
						<a href="#"><img src="images/share_icon_05.png" alt=""></a>
						<p>新浪微博</p>
					</div>
					<div class="icon_top">
						<a href="#"><img src="images/share_icon_10.png" alt=""></a>
						<p>微信好友</p>
					</div>
					<div class="icon_top">
						<a href="#"><img src="images/share_icon_07.png" alt=""></a>
						<p>朋友圈</p>
					</div>
				</div>
				<div class="quXiao">取消</div>
			</div>
		</div>
		<s:iterator value="#request.Active"></s:iterator>
	</div>
		<script type="text/javascript" src="js/join2.js"></script>
	<script type="text/javascript">
		$(function() {
			var oSrc = $(".pic>img").attr("src");
			$(".banner").css("background", 'url(' + oSrc + ') no-repeat')
			$(".banner").css("background-size", '100% 100%')
		})
		
		var name = '<s:property value="#session.toMember.name" />'
		var mode = '<s:property value="#request.Active.judgeMode"/>'
		var money = '<s:property value="#request.Active.amerceMoney"/>'
		var days = ' <s:property value="#request.Active.days"/>';
		var category = '<s:property value="#request.Active.category"/>';
		var value = '<s:property value="#request.Active.value"/>';
		var content = '<s:property value="#request.Active.content"/>';
		if (mode == "B"){
			$("#cpr").text(name);
		} else {
			$("#cpr").text("报名者指定");
		}
		if (category == "A") {
			$("#md").text(days + "天体重增加" + value + "Kg");
		} else if (category == "B") {
			$("#md").text(days + "天体重减少" + value + "Kg");
		} else if (category == "C") {
			$("#md").text(days + "天体重保持在" + value + "%左右");
		} else if (category == "D") {
			$("#md").text(days + "天运动" + value + "次");
		} else if (category == "E") {
			$("#md").text(days + "天运动" + value + "小时");
		} else if (category == "F") {
			$("#md").text(days + "天每周运动" + value + "次");
		} else if (category == "G") {
			$("#md").text(days + "天" + content + value + "千米");
		} else if (category == "H") {
			$("#md").text(days + "力量运动负荷" + value + "Kg");
		}
	</script>
</body>
</html>