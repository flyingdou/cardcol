<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
	<meta name="Keywords" content="健身挑战详情">
<meta name="Description" content="健身挑战详情">
<title>挑战详情</title>
<s:include value="/share/meta.jsp" />
<link href="css/bootstrap.css" rel="stylesheet">
<link href="css/base.css" rel="stylesheet">
<link href="css/challengeXq.css" rel="stylesheet">
<link rel="stylesheet" href="js/datepicker/skin/WdatePicker.css" />
<script src="js/datepicker/WdatePicker.js"></script>
<script src="js/returnTop.js"></script>
<script type="text/javascript">
	$(function() {
		// 		$("#dialogPay").dialog({
		// 			autoOpen : false,
		// 			show : "blind",
		// 			hide : "explode",
		// 			resizable : false,
		// 			width : 1000,
		// 			modal : true
		// 		});
		$("#partake").click(function() {
			if (!"<s:property value="#session.loginMember.id"/>") {
				openLogin();
				return;
			}
			if ($("#spanWeight").val() == "") {
				alert("请先维护您的目前体重！");
				return;
			}
			if ($("#joinDate").val() == "") {
				alert("请输入活动开始时间！");
				$('#joinDate').focus();
				return;
			}
			$('#partakeJudge').val($('#tmpJudge').val());
			$('#orderStartTime').val($('#joinDate').val());
			$('#weight').val($('#spanWeight').val());
			$('#partakeform').attr('action', 'joinactive!save.asp');
			$('#partakeform').attr('method', 'post');
			$('#partakeform').submit();
		});
	});
</script>

</head>
<body>
	<!--头部区-->
	<s:include value="/newpages/head.jsp"></s:include>
	<div class="top_bg"></div>

	<div class="container">
		<div class="row">
			<p class="col-md-12 title_nav">
				<span>首页&nbsp;&nbsp;-&nbsp;&nbsp;</span><span>健身挑战&nbsp;&nbsp;-&nbsp;&nbsp;</span><span>报名参加</span>
			</p>
		</div>
		<s:form name="partakeform" id="partakeform" method="post"
			theme="simple">
			<s:hidden name="type" id="type" />
			<s:hidden name="partake.active.id" id="activeId" />
			<s:hidden name="partake.active.name" id="activeName" />
			<s:hidden name="partake.active.mode" id="activeMode" />
			<s:hidden name="partake.active.judgeMode" id="judgeMode" />
			<s:hidden name="partake.active.amerceMoney" id="amerceMoney" />
			<s:hidden name="partake.active.days" id="days" />
			<s:hidden name="partake.weight" id="weight" />
			<s:hidden name="partake.judge" id="partakeJudge" />
			<s:hidden name="partake.active.teamNum" id="teamNum" />
			<s:hidden name="partake.orderStartTime" id="orderStartTime" />
			<s:hidden name="type" id="type" />
			<div class="row">
				<div class="challenge_active clearfix">
					<div class="col-md-4">
						<img src="picture/<s:property value="#request.active.image"/>"
							class="big_pic">
						<p class="people_num">
							已参与人员：<span><s:property
									value="#request.partakeMembers.size()" /></span>
						</p>
						<div class="row">
							<s:iterator value="#request.partakeMembers" status="start">
								<s:if test="(#start.index<=3)">
									<div class="small_pic col-md-3">
										<s:if test="<s:property value='id'/>==null">
										</s:if>
										<s:else>
											<s:if test="role==E">
												<a href="club.asp?member.id=<s:property value="id"/>"><img
													src="picture/<s:property value="image"/>"> <span><s:property
															value="name" /></span></a>
											</s:if>
											<s:elseif test="role==S">
												<a href="coach.asp?member.id=<s:property value="id"/>"><img
													src="picture/<s:property value="image"/>"> <span><s:property
															value="name" /></span></a>
											</s:elseif>
											<s:else>
												<a href="member.asp?member.id=<s:property value="id"/>"><img
													src="picture/<s:property value="image"/>"> <span><s:property
															value="name" /></span></a>
											</s:else>
										</s:else>
									</div>
								</s:if>
							</s:iterator>
						</div>
						<div class="row">
							<s:iterator value="#request.partakeMembers" status="start">
								<s:if test="((#start.index)>3&&(#start.index)<8)">
									<div class="small_pic col-md-3">
										<s:if test="<s:property value='id'/>==null">
										</s:if>
										<s:else>
											<s:if test="role==E">
												<a href="club.asp?member.id=<s:property value="id"/>"><img
													src="picture/<s:property value="image"/>"> <span><s:property
															value="name" /></span></a>
											</s:if>
											<s:elseif test="role==S">
												<a href="coach.asp?member.id=<s:property value="id"/>"><img
													src="picture/<s:property value="image"/>"> <span><s:property
															value="name" /></span></a>
											</s:elseif>
											<s:else>
												<a href="member.asp?member.id=<s:property value="id"/>"><img
													src="picture/<s:property value="image"/>"> <span><s:property
															value="name" /></span></a>
											</s:else>
										</s:else>
									</div>
								</s:if>
							</s:iterator>
						</div>
					</div>
					<div class="col-md-7 col-md-offset-1">
						<div class="row">
							<div class="col-md-7">
								<h3 class="challenge_title">
									<s:property value="name" />
								</h3>
								<p class="challenge_mb">
									完成时间 ：<span><s:property value="partake.active.days" />天</span>
								</p>
								<p class="challenge_mb">
									活动目标 ：<span><s:if test="partake.active.catagory=='B'">体重减少<s:property
												value="partake.active.value" />公斤</s:if> <s:elseif
											test="partake.active.category=='A'">体重增加<s:property
												value="partake.active.value" />公斤</s:elseif> <s:elseif
											test="partake.active.category=='E'">运动<s:property
												value="partake.active.value" />小时</s:elseif> <s:elseif
											test="partake.active.category=='D'">运动<s:property
												value="partake.active.value"/>次</s:elseif><s:else>
											<s:property value="partake.active.content" />
											<s:if test="partake.active.evaluationMethod=='00'">单次最好成绩大于或等于</s:if>
											<s:if test="partake.active.evaluationMethod=='01'">单次最好成绩小于或等于</s:if>
											<s:if test="partake.active.evaluationMethod=='10'">累计成绩大于或等于</s:if>
											<s:if test="partake.active.evaluationMethod=='11'">累计成绩小于或等于</s:if>
											<s:if test="partake.active.evaluationMethod=='20'">最后一次成绩大于或等于</s:if>
											<s:if test="partake.active.evaluationMethod=='21'">最后一次成绩小于或等于</s:if>
											<s:property value="partake.active.customTareget" />
											<s:property value="partake.active.unit" />
										</s:else></span>
								</p>
								<p class="challenge_mb">
									奖&nbsp;&nbsp;励 ：<span><s:property
											value="partake.active.award" /></span>
								</p>
								<p class="challenge_mb">
									惩&nbsp;&nbsp;罚 ：<span>向<s:property
											value="partake.active.institution.name" />捐献<s:property
											value="partake.active.amerceMoney" />元钱
									</span>
								</p>
								<p class="challenge_mb">
									发 布 人 ：<span><s:property
											value="partake.active.creator.name" /></span>
								</p>
							</div>
							<div class="col-md-5">
								<div class="beizhu">
									<h3>备注：</h3>
									<br>
									<p>
										<s:property value="partake.active.memo" />
									</p>
								</div>
							</div>
						</div>
						<div class="row">
							<h3 class="col-md-12 join_challenge">参加挑战</h3>
						</div>
						<div class="row">
							<div class="col-md-7">
								<div class="date">
									<span>开始时间：</span><input type="text" id="joinDate"
										name="partake.orderStartTime" onClick="WdatePicker()" readonly />
								</div>
								<div class="weight">
									<span>你的体重：</span><span><input type="text"
										name="partake.weight" id="spanWeight">&nbsp;&nbsp;&nbsp;公斤</span>
								</div>
								<div class="style">
									<span>选择裁判：</span>
									<s:if test="#request.active.judgeMode=='B'">
										<s:property value="partake.judge" />
									</s:if>
									<s:else>
										<select name="judge" id="tmpJudge"><s:iterator
												value="#request.active.judges">
												<option><s:property value="judge.name" /></option>
											</s:iterator></select>
									</s:else>
								</div>
								<p class="style_sm">裁判将收到挑战者的健身报告，由裁判确认报告中健身数据的真实性。</p>
							</div>
							<div class="deposit col-md-5">
								<span id="partake">支付保证金</span>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="all_join col-md-2">
					<h4>大家都在参加</h4>
					<div class="enjoy_join">
						<s:iterator value="#request.HotActive">
							<a
								href="activewindow.asp?id=<s:property value="#request.HotActive[0].id"/>"><img
								src="picture/<s:property value="#request.HotActive[0].image"/>">
							</a>
							<h5>
								<s:property value="name" />
							</h5>
							<p>
								挑战目标：<span><s:property value="days" />天<s:if
										test='category== "A"'>体重增加<s:property value="value" />公斤</s:if>
									<s:elseif test='category== "B"'>体重减少<s:property
											value="value" />公斤</s:elseif>
									<s:elseif test='category== "E"'>运动<s:property
											value="value" />小时</s:elseif>
									<s:elseif test='category== "D"'>运动<s:property
											value="value" />次</s:elseif>
									<s:else>
										<s:property value="content" />
										<s:property value="customTareget" />
										<s:property value="unit" />
									</s:else></span>
							</p>
						</s:iterator>
					</div>
				</div>
				<div class="challenge_list col-md-10">
					<h4>相关挑战</h4>
					<div class="about_challenge clearfix">
						<s:iterator value="#request.SimilarActive" status="start">
							<s:if test="(#start.index<4)">
								<div class="col-md-3">
									<a href="activewindow.asp?id=<s:property value="id"/>"><img
										src="picture/" <s:property value="image"/>> </a>
									<h5>
										<s:property value="name" />
									</h5>
									<p>
										挑战目标：<span><s:property value="days" />天 <s:if
												test="category== 'A'">体重增加<s:property value="value" />公斤</s:if>
											<s:elseif test="category=='B'">体重减少<s:property
													value="value" />公斤</s:elseif>
											<s:elseif test="category=='E'">运动<s:property
													value="value" />小时</s:elseif>
											<s:elseif test="category=='D'">运动<s:property
													value="value" />次</s:elseif>
											<s:else>
												<s:property value="content" />
												<s:property value="customTareget" />
												<s:property value="unit" />
											</s:else></span>
									</p>
								</div>
							</s:if>
						</s:iterator>
					</div>
				</div>
			</div>
		</s:form>
		<div id="dialogPay" title="支付保证金" style="display: none;"></div>
	</div>
	<!--底部区-->
	<s:include value="/newpages/footer.jsp"></s:include>
</body>
</html>