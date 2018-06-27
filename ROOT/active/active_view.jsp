<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<link rel="stylesheet" type="text/css" href="css/activ_new.css">
<div id="container" style="color: #000" style="font-size:13px;">
	<h1>挑战详情</h1>
	<!--详情-->
	<div class="sns_cn">
		<div style="padding-top: 0px;">
			<b>活动名称：</b><s:property value="partake.active.name"/>&nbsp;&nbsp;&nbsp;&nbsp;<b>裁判：</b><s:property value="partake.judge"/>
		</div>
		<div style="padding-top: 0px;">
			<b>开始日期：</b><s:date name="partake.orderStartTime" format="yyyy-MM-dd"/>&nbsp;&nbsp;&nbsp;&nbsp;<b>结束日期：</b><s:date name="partake.orderEndTime" format="yyyy-MM-dd"/>
		<b>模式</b>：<s:if test="partake.active.mode=='A'">个人挑战</s:if><s:else>团体挑战</s:else>
	
		</div>
		
		<div style="clear:both;">
			<b>挑战内容:</b><span>
				<s:if test="partake.active.category=='A'">体重减少<s:property value="partake.active.value"/>公斤</s:if>
				<s:elseif test="partake.active.category=='B'">体重增加<s:property value="partake.active.value"/>公斤</s:elseif>
				<%-- <s:elseif test="partake.active.category=='C'">体重保持在<s:property value="partake.active.value"/>%左右</s:elseif>
				<s:elseif test="partake.active.category=='G'"><s:property value="partake.active.action"/><s:property value="partake.active.value"/>千米</s:elseif>
				<s:elseif test="partake.active.category=='H'">力量运动负荷<s:property value="partake.active.value"/>KG</s:elseif> --%>
				<s:elseif test="partake.active.category=='C'">运动<s:property value="partake.active.value"/>小时</s:elseif>
				<s:elseif test="partake.active.category=='D'">运动<s:property value="partake.active.value"/>次</s:elseif>
				<s:elseif test="partake.active.category=='E'">自定义<s:property value="partake.active.value"/>次</s:elseif>
			</span>
		</div>
		<div style="padding-top: 0px;">
			<b>奖惩方式：</b><span class="jc"><b style="color:red;padding-right:6px;">奖</b></span><s:property value="partake.active.award"/>，<span class="jcc">惩</span>向<s:property value="partake.active.institution.name"/>捐款<s:property value="partake.active.amerceMoney"/>元
		</div>
		<div style="padding-top: 0px;"><img src="<s:property value="#request.fileName"/>" /></div>
		<div class="snsname">
			<b>裁判过程记录</b>
		</div>
		<div class="t_lists" style="padding-left: 0px; padding-top: 0px; color: #454142;">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr class="head_table">
					<td width="100" height="28"><strong>时间</strong></td>
					<td><strong>用户名</strong></td>
					<td width="90"><strong>内容</strong></td>
					<td width="70"><strong>结果</strong></td>
				</tr>
				<s:iterator value="#request.rs">
				<tr class="top_right">
					<td height="28 class="top_right""><s:date name="doneDate" format="yyyy-MM-dd"/></td>
					<td width="100" class="top_right"><s:property value="partake.name"/></td>
					<td class="top_right">
						<s:if test="active.category=='A'">体重<s:property value="weight"/>KG</s:if>
						<s:elseif test="active.category=='B'">体重<s:property value="weight"/>KG</s:elseif>
						<s:elseif test="active.category=='C'">体重<s:property value="weight"/>KG</s:elseif>
						<s:elseif test="active.category=='G'"><s:property value="active.action"/><s:property value="actionQuan"/>千米</s:elseif>
						<s:elseif test="active.category=='H'">力量运动负荷<s:property value="strength"/>KG</s:elseif>
						<s:elseif test="active.category=='D'">运动1次</s:elseif>
						<s:elseif test="active.category=='E'">运动<s:property value="times"/>小时</s:elseif>
						<s:elseif test="active.category=='F'">运动1次</s:elseif>
					</td>
					<td class="top_right"><s:if test="confrim == '1'">通过</s:if><s:else>未通过</s:else></td>
				</tr>
				</s:iterator>
			</table>
		</div>
	</div>
</div>
