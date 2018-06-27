<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<s:include value="/share/meta.jsp" />
<meta name="keywords" content="健身课程,课程安排,瘦身课程,健美课程" />
<meta name="description" content="健身教练的课程表中有健身教练为健身会员制定的健身课程，根据健身目安排课程。" />
<title>健身课程表</title>
<link href="css/club-checkin.css" rel="stylesheet" type="text/css" />
<link type="text/css" rel="stylesheet" href="css/pulicstyle.css"/>
<script type="text/javascript">
$(document).ready(function(){
	//初始菜单后面的数字
	try{
// 	$("#tishi11").html("(<s:property value="#request.countList1"/>)");
	var countJson = $.parseJSON('<s:property value="#request.countList2" escape="false"/>');
	for(var i=0;i<countJson.length;i++){
		$("#tishi"+countJson[i].status).html("("+countJson[i].cnt +")");
	}
	$("#accordion").accordion({collapsible: true});
	$('#tabs').tabs();
	$('#tabs').removeClass('ui-widget-content ui-corner-all');
	$('#tabs .ui-tabs-nav').removeClass('ui-widget-header ui-corner-all');
	}catch(e) {
		alert(e);
	}
});
function goAjax(url,type){
	$.ajax({type:"post",url:url,data:"",
		success:function(msg){
			$("#right-2").html(msg);
// 			if(type){
// 				$("#tishi"+type).html("(0)");
// 			}
		}
	}); 
}
</script>
</head>
<body>
	<input type="hidden" name="countList1" value="<s:property value="#request.countList1"/>" />
	<input type="hidden" name="countList2" value="<s:property value="#request.countList2"/>" />
	<s:if test="#session.loginMember.id == member.id">
	<s:include value="/share/header.jsp"/>
	</s:if>
	<s:else>
	<s:include value="/share/window-header.jsp"/>
	</s:else>
	<div id="content">
	<s:include value="/course/nav.jsp" />
		<div id="right-2">
			<s:if test="goType ==1">
			<s:include value="/course/appraise_list.jsp"/>
			</s:if>
			<s:elseif test="goType == 2">
			<s:include value="/factoryorder/calendar.jsp"/>
			</s:elseif>
			<s:elseif test="goType == 3">
			<s:include value="/factoryorder/calendarshow.jsp"/>
			</s:elseif>
			<s:else>
    		<s:include value="/course/calendar.jsp"/>
    		</s:else>
		</div>
	</div>
	<s:include value="/share/footer.jsp" />
</body>
</html>