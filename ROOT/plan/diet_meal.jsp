<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
<!--
$(function(){
	$('#ptabs').tabs();
	$('#ptabs').removeClass('ui-widget-content ui-corner-all');
	$('#ptabs .ui-tabs-nav').removeClass('ui-widget-header ui-corner-all');
	$('#startDate').datepicker({autoSize: true});
	$('#endDate').datepicker();
	$("#dialog").dialog({autoOpen : false, show : "blind", hide : "explode", resizable : false, width : 450, modal : true});
});
//-->
</script>
<s:form name="dietform" id="dietform" theme="simple" method="post">
<div id="righttop">
	<s:include value="/plan/diet_top.jsp" />
</div>
<div class="first" id="ptabs">
	<ul>
		<li><a href="#divdiet" class="aa">饮食计划</a></li>
	</ul>
	<div id="divdiet" style="border: 1px solid #aaaaaa;">
		<s:include value="/plan/diet_detail.jsp" />
	</div>
</div>
</s:form>