<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<link rel="stylesheet" type="text/css" href="css/activ_new.css">
<s:form name="partakeform" id="partakeform" method="post" theme="simple">
<div id="container">
	<h1>挑战详情</h1>
	<div style="padding-left: 10px; padding-right: 40px;">
		<!--left-->
		<div class="join_l">
			<p style="padding-top:4px;height:33px;line-height:33px;"><strong>活动名称：</strong></p>
			<p style="height:33px;line-height:33px;"><strong>活动模式：</strong></p>
			<s:if test="#request.ao.active.mode=='B'">
         	<p style="height:33px;line-height:33px;"><strong>团队名称：</strong></p>
         	</s:if>
			<p style="height:33px;line-height:33px;"><strong>天数：</strong></p>
			<p style="height:33px;line-height:33px;"><strong>开始时间：</strong></p>
			<p style="height:33px;line-height:33px;"><strong>完成时间：</strong></p>
			<p style="height:33px;line-height:33px;"><strong>活动目标：</strong></p>
			<p style="height:33px;line-height:33px;"><strong>奖励：</strong></p>
		
			<p style="height:33px;line-height:33px;"><strong>惩罚：</strong></p>
			<p style="height:33px;line-height:33px;"><strong>裁判：</strong></p>
			<p style="color: #FFFFFF"><strong>注</strong></p>
			<p style="height:33px;line-height:33px;"><strong>参加对象：</strong></p>
			<p >&nbsp;</p>
			<p>&nbsp;</p>
		</div>
		<!--right-->
		<div class="join_r">
			<p style="padding-top:4px;height:33px;line-height:33px;"><s:property value="#request.ao.active.name"/></p>
			<p style="height:33px;line-height:33px;"><s:if test="#request.ao.active.mode=='A'">个人挑战</s:if><s:else>团体挑战</s:else></p>
			<s:if test="#request.ao.active.mode=='B'">
			<p style="height:33px;line-height:33px;"><span class="duidd"><s:property value="#request.ao.team.name"/></span></p>
			</s:if>
			<p style="height:33px;line-height:33px;"><s:property value="#request.ao.active.days"/>&nbsp;&nbsp;&nbsp;&nbsp;天</p>
			<p style="height:33px;line-height:33px;"><s:date name="#request.ao.orderStartTime" format="yyyy-MM-dd"/></p>
			<p style="height:33px;line-height:33px;"><s:date name="#request.ao.orderEndTime" format="yyyy-MM-dd"/></p>
			<p style="height:33px;line-height:33px; padding-top:2px;">
				<s:if test="#request.ao.active.category=='A'">体重管理-增加<s:property value="#request.ao.active.value"/>公斤</s:if>
				<s:elseif test="#request.ao.active.category=='B'">体重管理-减少<s:property value="#request.ao.active.value"/>公斤</s:elseif>
				<s:elseif test="#request.ao.active.category=='C'">体重管理-保持体重在<s:property value="#request.ao.active.value"/>%左右</s:elseif>
				<s:elseif test="#request.ao.active.category=='D'">次数/时间/频率-运动<s:property value="#request.ao.active.value"/>次</s:elseif>
				<s:elseif test="#request.ao.active.category=='E'">次数/时间/频率-运动<s:property value="#request.ao.active.value"/>小时</s:elseif>
				<s:elseif test="#request.ao.active.category=='F'">次数/时间/频率-每周运动<s:property value="#request.ao.active.value"/>次</s:elseif>
				<s:elseif test="#request.ao.active.category=='G'">运动量-<s:property value="#request.ao.active.action"/><s:property value="#request.ao.active.value"/>千米</s:elseif>
				<s:elseif test="#request.ao.active.category=='H'">运动量-力量运动负荷<s:property value="#request.ao.active.value"/>公斤</s:elseif>
				 <span class="tishi" style=" padding-right: 20px; color: #945F50;">您的当前体重为<span id="spanWeight"><s:property value="#request.ao.weight"/></span>公斤&nbsp;&nbsp;</span>
			</p>
			<p style="height:33px;line-height:33px;"><s:property value="#request.ao.active.award"/></p>
	   
			<p style="height:33px;line-height:33px;">
				向&nbsp;&nbsp;<span class="jcc"><s:property value="#request.ao.active.institution.name"/></span> 捐款<s:property value="#request.ao.active.amerceMoney"/>元
			</p>
			<p style="height:33px;line-height:33px;">
				<s:if test="#request.ao.active.judgeMode=='A'">输入裁判在卡库网上的用户名&nbsp;<s:textfield name="#request.ao.judge" id="judge" cssClass="input_w" cssStyle="width: 200px;"/></s:if>
				<s:else><s:textfield name="#request.ao.judge" style="height:26px;line-heigt:26px;" readonly="true" id="judge"/></s:else>
			</p>
			<p class="hui_s">
				<span class="tishi" style="padding-right: 20px; color: #945F50;">裁判将收到挑战者的健身报告，由裁判确认报告中健身数据的真实性。</span>
			</p>
			<p style="height:33px;line-height:33px;"><s:if test="#request.ao.active.joinMode =='A'">所有对象</s:if><s:else>仅限所属会员</s:else></p>
		</div><!--right end-->
	</div>
</div>
</s:form>
