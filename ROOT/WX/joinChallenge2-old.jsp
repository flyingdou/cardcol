<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
<head>
<meta charset="UTF-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<title>参加挑战</title>
<link type="text/css"
	href="${pageContext.request.contextPath}/WX/css/bootstrap.css"
	rel="stylesheet">
<link type="text/css"
	href="${pageContext.request.contextPath}/WX/css/FJL.css"
	rel="stylesheet">
<link type="text/css"
	href="${pageContext.request.contextPath}/WX/css/FJL.picker.css"
	rel="stylesheet">
<link type="text/css"
	href="${pageContext.request.contextPath}/WX/css/sm.css"
	rel="stylesheet">
<link type="text/css"
	href="${pageContext.request.contextPath}/WX/css/base.css"
	rel="stylesheet">
<link type="text/css"
	href="${pageContext.request.contextPath}/WX/css/style.css"
	rel="stylesheet">
<link type="text/css"
	href="${pageContext.request.contextPath}/WX/css/index.css"
	rel="stylesheet">
<script type="text/javascript"
	src="${pageContext.request.contextPath}/WX/js/zepto.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/WX/js/FJL.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/WX/js/FJL.picker.min.js"></script>
</head>
<body>
	<div class="container joinChallenge">
		<div class="star_time">
			<div style="width: 20rem; height: 10rem">
			<div>
			<img alt="" src="${pageContext.request.contextPath}/picture/6122042970595328.jpg" width="80rem" height="80rem" style="margin-top: 1rem">
			</div>
			<div style="margin-left: 10rem;margin-top: -7rem">
			<span>测试挑战1</span> <br/>
			<span>测试挑战1</span><br/>
			<span>测试挑战1</span>
			</div>
			
			</div>
		</div>
		<div class="star_time">
			<p class="page">
				挑战开始时间 <i class="glyphicon glyphicon-calendar fr"></i>
				<button id='date' data-options='{"type":"date","beginYear":2000,"endYear":2086,}' class="this_date date fr btn mui-btn mui-btn-block"></button>
			</p>
			<a href="JavaScript:void()">当前体重 <span class="fr">KG</span> 
			<input type="text" name="weight" placeholder="请输入体重" value="" readonly class="picker fr" id="weight" />
			</a>
		</div>
		<div class="price">
			<div class="price_title">
				<span><s:property value="active.amerceMoney" />元</span>
				<p>本次挑战的保证金</p>
			</div>
			<h5>挑战参加人同意以下声明：</h5>
			<p>我自愿参加本次挑战，无条件接受裁判和系统的判决结果。</p>
			<p>同意运营方根据挑战的规则、挑战成绩及承诺处置保证金。</p>
		</div>
		<div class="assign">
			<a href="JavaScript:partake();">支付保证金</a>
		</div>
	</div>
	<script type="text/javascript" src="、${pageContext.request.contextPath}/WX/js/write.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/WX/js/sm.js"></script>
	<script>
		function partake() {
			var startDate = $("#date").text();
			var jid = $("#juid").val();
			var id = $("#id").val();
			$("#startDate").val(startDate);
			if ($("#judge").val() == '') {
				alert("请选着裁判");
				return;
			} else if ($("#weight").val() == '') {
				alert("请输入体重");
				return;
			} else {
				$.ajax({
					url : "activepaywx!savePartake.asp",
					type : "post",
					data : {
						"startDate" : startDate,
						"jid" : jid,
						"id" : id
					},
					success : function(msg) {
						var obj = eval("(" + msg + ")");
						if (obj.success == false) {
							alert(obj.message);
						} else if (obj.success == true) {
							var id = obj.order.id;
							window.location.href = "activepaywx.asp?id=" + id;
						}

					},
				});
			}

		}
	</script>
</body>
</html>