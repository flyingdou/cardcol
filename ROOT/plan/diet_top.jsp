<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
<!--
$(function(){
	$('a[title="删除餐次"]').bind('click', onDelete);
	$('a[title="编辑餐次"]').bind('click', onEdit);
	$('a[title="增加餐次"]').bind('click', onAdd);
	$("#opener").click(function() {$("#dialog").dialog("open");return false;});
	$('.inpsave').bind('click', onSave);
});
//-->
</script>
	<style>
	.inpa2 a{ color:#f60;}
   .inpa2 a:hover{ color:red; text-decoration:underline;}
.bdr select{ border:1px solid #ccc;}

		</style>
	
<div id="divmeal" class="bdr">
	<s:iterator value="#request.meals" status="st">
	<s:set name="disabled" value="false" />
	<s:if test="#st.index > 0"><s:set name="disabled" value="true"/></s:if>
	<div id="<s:property value="id"/>" >
		<span>餐次：<s:select list="#{'早餐':'早餐','中餐':'中餐','中餐加餐':'中餐加餐','晚餐':'晚餐','晚餐加餐':'晚餐加餐'}" value="%{mealNum}" listKey="key" listValue="value" name="diet.mealNum" disabled="%{disabled}" /></span>
		<span>开始时间：<s:select name="diet.startTime" list="#request.times" listKey="key" listValue="value" id="startTime" value="%{startTime}" disabled="%{disabled}" /></span>
		<span>结束时间：<s:select name="diet.endTime" list="#request.times" listKey="key" listValue="value" id="endTime" value="%{endTime}" disabled="%{disabled}" /></span>
		<span style="padding-left: 20px;" class="inpa2"><a href="#" id="<s:property value="id"/>" title="删除餐次">删除</a>&nbsp;&nbsp;<a href="#" id="<s:property value="id"/>" title="编辑餐次">编辑</a></span>
	</div>
	</s:iterator>
</div>
<div style="line-height: 22px;" class="inpa2">
	<a href="#" id="coload" title="增加餐次">[+增加餐数]</a>
</div>
<p class="copy">
	<input type="button" name="save" class="inpsave" value="保存计划" />
	<input type="button" name="remove" class="inpremove" value="复制计划" id="opener" />
</p>
<div id="mealSample" style="display: none;">
	<div id="">
		<span>餐次：<s:select list="#{'早餐':'早餐','中餐':'中餐','中餐加餐':'中餐加餐','晚餐':'晚餐','晚餐加餐':'晚餐加餐'}" listKey="key" listValue="value" name="XXXX.mealNum"/></span>
		<span>开始时间：<s:select name="XXXX.startTime" list="#request.times" listKey="key" listValue="value" id="startTime"/></span>
		<span>结束时间：<s:select name="XXXX.endTime" list="#request.times" listKey="key" listValue="value" id="endTime" /></span>
		<span style="padding-left: 20px;" class="inpa2">
			<a href="#" id="" title="删除" >删除</a>&nbsp;&nbsp;<a href="#" id="" title="编辑" >编辑</a>
		</span>
	</div>
</div>

