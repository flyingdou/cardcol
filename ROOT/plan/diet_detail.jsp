<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
$(function() {
	<s:if test="#request.monthStatus != null">
	st = <s:property value="#request.monthStatus" escape="false"/>;
	</s:if>
	setStatus();
	$('.inpremove[title="删除"]').bind('click', deleteDetail);
	$('.a1').bind('click', onAddDetail);
});
</script>
<s:hidden name="diet.id" id="dietId"/>
<s:hidden name="diet.member" id="member"/>
<s:hidden name="diet.planDate" id="planDate" />
<table class="table1" cellspacing="1" cellpadding="0" border="0" id="table">
	<tr>
		<th>食物名称</th>
		<th>重量G</th>
		<th>热量(kcal)</th>
		<th>删除</th>
	</tr>
	<s:iterator value="diets" status="st">
	<tr>
		<td>
			<s:hidden name="%{'diets['+#st.index+'].id'}" />
			<s:textfield name="%{'diets['+#st.index+'].mealName'}" theme="simple"/>
		</td>
		<td><s:textfield name="%{'diets['+#st.index+'].mealWeight'}" theme="simple"/></td>
		<td><s:textfield name="%{'diets['+#st.index+'].mealKcal'}" theme="simple"/></td>
		<td><a href="#" class="inpremove" title="删除" id="<s:property value="id"/>"><img src="images/x.png"/></a></td>
	</tr>
	</s:iterator>
</table>
<div style="width: 100%;text-align: center;"><a href="#"  id="coloa" title="增加食物" class="a1">增加食物</a></div>
