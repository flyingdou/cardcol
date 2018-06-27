<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<title>计划详情</title>
<link type="text/css" href="css/bootstrap.css" rel="stylesheet">
<link type="text/css" href="css/FJL.css" rel="stylesheet">
<link type="text/css" href="css/FJL.picker.css" rel="stylesheet">
<link type="text/css" href="css/base.css" rel="stylesheet">
<link type="text/css" href="css/style.css" rel="stylesheet">
<link type="text/css" href="css/index.css" rel="stylesheet">
<script type="text/javascript" src="js/jquery-2.2.0.min.js"></script>
<script type="text/javascript" src="js/FJL.min.js"></script>
<script type="text/javascript" src="js/FJL.picker.min.js"></script>
<script type="text/javascript" src="js/index.js"></script>
<script type="text/javascript">
function shopPlan() {
	var startDate = $("#date").text();
	var id =$("#planReleaseId").val();
	 $.ajax({
        url:"orderswx!genPlan.asp",
        type:"post",
        data: {"startDate": startDate,"id":id },
        success:function(msg){
        	var obj =eval("("+msg+")");
        	if (obj.success === true) {
				alert("您的计划已经成功生成，请进入用户中心在我的卡库健身计划中查看计划！");
			} else {
				alert(obj.message);
			}
        },
    });  
}

function submitProd(){
var date=$("#date").text();
$("#sd").val(date);
var val = $("#sd").val();
url = 'orderswx!submitProd.asp?prodType=3&id='
	+ $("#planReleaseId").val();
$('#queryForm').attr("action", url);
$('#queryForm').submit();
}

</script>
</head>
<body>
<s:form id="queryForm" name="queryForm" action="planlistwx.asp" theme="simple">
	<div class="planXq container">
		<div class="plan">
			<div class="bg">
			<img src="../picture/<s:property value="planrelease.image1"/>"  class="big_img" />
				<div class="txt fl">
					<h6>
						<s:property value="planrelease.planName" />
					<input type="hidden" value="<s:property value="planrelease.id"/>" id="planReleaseId">	
					</h6>
					<div class="wjx">
						<img src="images/wujiaoxing_03.png">
						<img src="images/wujiaoxing_03.png">
						<img src="images/wujiaoxing_03.png">
						<img src="images/wujiaoxing_03.png">
						<img src="images/wujiaoxing_03.png">
					</div>
					<p>
						<span><s:if test="#request.planAppraise[0].saleNum>0">
								<s:property value="#request.planAppraise[0].saleNum" />
							</s:if> <s:else>0</s:else></span>人使用
					</p>
				</div>
				<div class="rated fr">
					<p>
						<span> <s:if test='#request.planAppraise[0].avgGrade>0'>
								<s:property value='#request.planAppraise[0].avgGrade' />
							</s:if> <s:else>0</s:else></span>分
					</p>
					<a href="#">查看评价</a>
				</div>
			</div>
		</div>

		<div class="plan_teach">
			<div class="pic fl">
				<img src="../picture/<s:property value="planrelease.member.image"/>">
			</div>
			<div class="txt fl">
				<h6>
					<s:property value="planrelease.member.name" />
				</h6>
				<p><s:property value="planrelease.member.description"  escape="false"/></p>
			</div>
			<div class="message fr">
				<img src="images/plan_M_06.png">
			</div>
		</div>

		<div class="plan_list TheLast">
			<div class="list">
				<h6 class="fl">计划类型</h6>
				<p class="fr">
					<span><s:property value="planrelease.planType" /></span>
				</p>
			</div>
			<div class="list">
				<h6 class="fl">适用对象</h6>
				<p class="fr">
					<span><s:property value="planrelease.applyObject" /></span>
				</p>
			</div>
			<div class="list">
				<h6 class="fl">适用场景</h6>
				<p class="fr">
					<span><s:property value="planrelease.scene" /></span>
				</p>
			</div>
			<div class="list">
				<h6 class="fl">所需器材</h6>
				<p class="fr">
					<s:property value="planrelease.apparatuses" />
				</p>
			</div>
			<div class="list">
				<h6 class="fl">计划周期</h6>
				<p class="fr">
					<span><s:property value="period" />天</span>
				</p>
			</div>
		</div>

		<div class=" bottom page">
			<ul>
				<li class="fl">
					<p>设置计划开始日期</p>
					<button id='date'
						data-options='{"type":"date","beginYear":2000,"endYear":2086}'
						class="this_date date fr btn mui-btn mui-btn-block"></button>
						<input type="hidden" name="startDate" value="" id='sd'>
				</li>
				<s:if test="planrelease.unitPrice == 0">
					<li class="fl"><a href="JavaScript:shopPlan()">免费下载</a></li>
				</s:if>
				<else>
				<li class="fl">
				<a href="JavaScript:submitProd()">
				￥<s:property value="planrelease.unitPrice" />  
				立即购买
				</a>
				</li>
				</else>
			</ul>
		</div>
	</div>
</s:form>
	<script type="text/javascript" src="js/write.js"></script>
</body>
</html>