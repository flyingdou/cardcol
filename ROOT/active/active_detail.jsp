<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<link rel="stylesheet" type="text/css" href="css/activ.css">
<link rel="stylesheet" type="text/css" href="css/activ_new.css">
<div id="container" style="color: #000">
	<h1>挑战详情</h1>
	<!--详情-->
	<div class="sns_cn" style="padding-left: 0">
		<div style="padding-top: 0px;">
			<b>活动名称：</b><s:property value="active.name"/>
		</div>
		<div style="padding-top: 0px;">
			<%-- <b>模式</b>：<s:if test="active.mode == 'A'">个人挑战</s:if><s:else>团体挑战</s:else> --%>
			<b>挑战内容:</b>
				<s:if test="active.category=='A'">体重减少<s:property value="active.value"/>公斤</s:if>
				<s:elseif test="active.category=='B'">体重增加<s:property value="active.value"/>公斤</s:elseif>
				<%-- <s:elseif test="active.category=='C'">体重保持在<s:property value="active.valeu"/>%左右</s:elseif>
				<s:elseif test="active.category=='G'"><s:property value="active.action"/><s:property value="active.value"/>千米</s:elseif>
				<s:elseif test="active.category=='H'">力量运动负荷<s:property value="active.value"/>KG</s:elseif> --%>
				<s:elseif test="active.category=='C'">运动<s:property value="active.value"/>小时</s:elseif>
				<s:elseif test="active.category=='D'">运动<s:property value="active.value"/>次</s:elseif>
				<s:elseif test="active.category=='E'"><s:property value="active.content"/>
				 <s:if test="active.evaluationMethod=='00'">单次最好成绩大于或等于</s:if><s:if test="active.evaluationMethod=='01'">单次最好成绩小于或等于</s:if>
				 <s:if test="active.evaluationMethod=='10'">累计成绩大于或等于</s:if><s:if test="active.evaluationMethod=='11'">累计成绩小于或等于</s:if>
				 <s:if test="active.evaluationMethod=='20'">最后一次成绩大于或等于</s:if><s:if test="active.evaluationMethod=='21'">最后一次成绩小于或等于</s:if>
				 <s:property value="active.customTareget"/><s:property value="active.unit"/></s:elseif>
			
		</div>
		<div style="padding-top: 0px;">
			<b>奖惩方式：</b><span class="jc"><b>奖</b></span><s:property value="active.award"/>，<span class="jcc">惩</span>挑战失败处罚金<s:property value="active.amerceMoney"/>元，捐赠给<s:property value="active.institution.name"/>。
		</div>
		<div style="padding-top: 0px;">
			<b>注意事项:</b><s:property value="active.memo"/><br/>&nbsp;&nbsp;&nbsp;<b>若挑战成功，保证金全额退回！</b>
		</div>
		<div class="snsname">
			<b style="padding-left: 310px;">挑战排行榜</b>
		</div>
		<center>
			<div class="t_lists"  style="padding-left: 0px; padding-top: 0px;" >
				<table width="100%" border="0" cellpadding="0" cellspacing="0">
					<tr class="head_table" style="color: #454142;">
						<td width="94" height="27"><strong>名次</strong></td>
						<td width="149" height="27"><strong>名称</strong></td>
						<td width="134" height="27"><strong>开始日期</strong></td>
						<td width="381" height="27"><strong>最新数据</strong></td>
					</tr>
					<s:iterator value="active.sorts" status="st">
					<tr class="top_right">
						<td height="28"><s:property value="#st.index+1"/></td>
						<td><s:property value="member.name"/></td>
						<td><s:date name="orderStartTime" format="yyyy-MM-dd" /></td>
						<td><s:property value="value"/></td>
					</tr>
					</s:iterator>
				</table>
			</div>
		</center>
	</div>
</div>