<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<link rel="stylesheet" type="text/css" href="css/activ_new.css">
<script type="text/javascript">
var width = 300;
var height = 300;
function init(){
	document.all.im.width = width;
	document.all.im.height = height;}

function a(e){
	document.all.im.src = e.src;
	document.all.d.style.left = event.x;
	document.all.d.style.top = event.y;
	document.all.d.style.display = "";
}

function b(){ 
	document.all.d.style.left = 0;
	document.all.d.style.top = 0;
	document.all.d.style.display = "none";
}
</script>
<body   onload="javascript:init();">
<div id="container">
	<h1>计划详情</h1>
	<div style="padding-left: 10px; padding-right: 40px;">
		<!--left-->
		<div class="join_l">
			<p style="padding-top:4px;height:33px;line-height:33px;"><strong>计划名称：</strong></p>
			<p style="height:50px;line-height:50px;"><strong>缩略图：</strong></p>
         	<p style="height:33px;line-height:33px;"><strong>计划类型：</strong></p>
			<p style="height:33px;line-height:33px;"><strong>适用场景：</strong></p>
			<p style="height:33px;line-height:33px;"><strong>适用对象：</strong></p>
			<p style="height:33px;line-height:33px;"><strong>所需器材：</strong></p>
			<p style="height:33px;line-height:33px;"><strong>保存范围：</strong></p>
			<p style="height:33px;line-height:33px;"><strong>销售价格：</strong></p>
			<p style="height:33px;line-height:33px;"><strong>计划简介：</strong></p>
			<p style="height:33px;line-height:33px;"><strong>计划详情：</strong></p>
		</div>
		<!--right-->
		<div class="join_r">
			<p style="padding-top:4px;height:33px;line-height:33px;"><s:property value="planRelease.planName"/></p>
			<p style="height:50px;line-height:50px;">
				<s:if test="planRelease.image1 !=null&&planRelease.image1!=''">
					<input type="image" width=50 height=50 src="picture/<s:property value="planRelease.image1"/>" onMousemove="a(this)" onmouseout="b()">
				</s:if>
				<s:if test="planRelease.image2 !=null&&planRelease.image2!=''">
					<input type="image" width=50 height=50 src="picture/<s:property value="planRelease.image2"/>" onMousemove="a(this)" onmouseout="b()">
				</s:if>
				<s:if test="planRelease.image3 !=null&&planRelease.image3!=''">
					<input type="image" width=50 height=50 src="picture/<s:property value="planRelease.image3"/>" onMousemove="a(this)" onmouseout="b()">
				</s:if>
				<div id="d" style="position:absolute;left=0;top=0;display:none;border: 1px #FF00FF solid;" >
					<input id="im" type="image">
				</div>
			</p>
			<p style="height:33px;line-height:33px;">
				<s:if test="planRelease.planType==\"A\"">瘦身减重</s:if>
				<s:elseif test="planRelease.planType==\"B\"">健美增肌</s:elseif>
				<s:elseif test="planRelease.planType==\"C\"">运动康复</s:elseif>
				<s:elseif test="planRelease.planType==\"D\"">提高运动表现</s:elseif>
				<s:else></s:else>
			</p>
			<p style="height:33px;line-height:33px;">
				<s:if test="planRelease.scene==\"A\"">健身房</s:if>
				<s:elseif test="planRelease.scene==\"B\"">办公室</s:elseif>
				<s:elseif test="planRelease.scene==\"C\"">家庭</s:elseif>
				<s:elseif test="planRelease.scene==\"D\"">户外</s:elseif>
				<s:else></s:else>
			</p>
			<p style="height:33px;line-height:33px;">
				<s:if test="planRelease.applyObject==\"A\"">初级</s:if>
				<s:elseif test="planRelease.applyObject==\"B\"">中级</s:elseif>
				<s:elseif test="planRelease.applyObject==\"C\"">高级</s:elseif>
				<s:else></s:else>
			</p>
			<p style="height:33px;line-height:33px;"><s:property value="planRelease.apparatuses"/></p>
			<p style="height:33px;line-height:33px;">从<span><s:date name="planRelease.startDate" format="yyyy-MM-dd"/></span>到<span><s:date name="planRelease.endDate" format="yyyy-MM-dd"/></span></p>
			<p style="height:33px;line-height:33px;"><s:property value="planRelease.unitPrice"/></p>
			<p style="height:33px;line-height:60px;"><s:property value="planRelease.briefing"/></p>
			<p style="height:33px;line-height:33px;"><s:property value="planRelease.details"/></p>
			
		</div><!--right end-->
	</div>
</div>
</body>
