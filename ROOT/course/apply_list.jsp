<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<s:include value="/share/meta.jsp" />
<meta name="keywords" content="健身课程,课程安排,瘦身课程,健美课程" />
<meta name="description" content="健身教练的课程表中有健身教练为健身会员制定的健身课程，根据健身目安排课程。" />
<title>预约管理</title>
<link href="css/club-checkin.css" rel="stylesheet" type="text/css" />
<link type="text/css" rel="stylesheet" href="css/pulicstyle.css"/>
<script type="text/javascript">
$(document).ready(function(){
	$('#startDate').datepicker({changeYear: true});
	$('#endDate').datepicker({changeYear: true});
	//初始菜单后面的数字
	try{
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
function onQuery(){
	queryByPage(1);
}
function changeStatus(status){
	if(!($("input[name='typeandids']:checked").length>0)){
		alert("请先选择要操作的数据！");
		return;
	}
	$("input[name='apply.status']").val(status);
  	 $.ajax({type:"post",url:"apply!changeStatus.asp",data:$('#queryForm').serialize(),
		success:function(msg){ 
			var oldVal = $("#tishi"+status).html();
			oldVal = oldVal.substring(1,oldVal.length-1)
			$("#tishi"+status).html("("+(parseInt(oldVal)+$("input[name='typeandids']:checked").length)+")");
			$("body").html(msg);
			var msgg = "";
			if(status == "2"){
				msgg = "同意";
			}else{
				msgg = "拒绝";
			}
			alert("当前预约已成功"+msgg+"！");
			
		}
	}); 
	/* var oldVal = $("#tishi"+status).html();
	oldVal = oldVal.substring(1,oldVal.length-1);
	$("#tishi"+status).html("("+(parseInt(oldVal)+$("input[name='typeandids']:checked").length)+")");
	 $('#img_form').attr("action", "apply!changeStatus.asp").submit();
	 var msgg = "";
		if(status == "2"){
			msgg = "同意";
		}else{
			msgg = "拒绝";
		}
		alert("当前预约已成功"+msgg+"！"); */
}
function deleteApplay(){
	if(!($("input[name='typeandids']:checked").length>0)){
		alert("请先选择要操作的数据！");
		return;
	}
	$.ajax({type:"post",url:"apply!delete.asp",data:$('#queryForm').serialize(),
		success:function(msg){
			$("#right-2").html(msg);
			alert("当前预约已成功撤销！");
		}
	});
}
</script>
</head>
<body>
	<s:include value="/share/header.jsp"/>
	<div id="content">
	<s:include value="/course/nav.jsp" />
	<div id="right-2">
	<s:form id ="queryForm" name="queryForm" method="post" action="apply!query.asp" theme="simple">
<s:hidden name="query.status"/>
<s:hidden name="apply.status"/>
    <h1><s:if test="query.status == 1">待审的预约</s:if><s:if test="query.status == 2">成功的预约</s:if><s:if test="query.status == 3">拒绝的预约</s:if></h1>
    <div>
      <div class="date">预约起止日期
        <input type="text" name="query.startDate" id="startDate" class="date-1" value="<s:date name="query.startDate" format="yyyy-MM-dd"/>" />
         到
        <input type="text" name="query.endDate" id="endDate" class="date-1" value="<s:date name="query.endDate" format="yyyy-MM-dd"/>" />
        <input type="button" name="button" value="查询" class="button1" onclick="onQuery();"/>
        <s:if test="query.status == 1 && #session.loginMember.role != \"M\"">
        <input type="button" name="button" value="同意" class="button2" onclick="changeStatus('2');"/>
        <input type="button" name="button" value="拒绝" class="button2-1" onclick="changeStatus('3');"/>
        </s:if>
        <s:if test="query.status == 2">
        <input type="button" name="button" value="撤销预约" class="button3" onclick="deleteApplay();"/>
        </s:if>
        <!--  
        <s:if test="query.status == 3">
        <input type="button" name="button" value="删除" class="button2-2" onclick="deleteApplay();"/>
        </s:if>
        -->
      </div>
	  <s:if test="pageInfo.items.size>0">
	  <div class="table">
	   	<table width="100%" border="0" cellpadding="0" cellspacing="0" >
	          <tr class="end">
	            <th class="left" id="tdtop" width="30px"><input type="checkbox" onclick="selectAll('typeandids')"/></th>
	            <th id="tdtop" width=20%;>预约时间</th>
	            <th id="tdtop">商品名称</th>
	            <s:if test="#session.loginMember.role == \"M\"">
	            <th id="tdtop" width=12%;>教练</th>
	            </s:if>
	            <s:else>
	            <th id="tdtop" width=12%;>会员</th>
	            </s:else>
	            <th width="20%"; id="tdtop">课程时间</th>
	            <th id="tdtop" width="13%">地点</th>
	            <s:if test="query.status !=1">
				<th width=11%; id="tdtop">审批时间</th>
				</s:if>
	          </tr>
	          <s:iterator value="pageInfo.items" status="sta">
	          <s:if test="#sta.last">
	          <tr class="end">
	          </s:if>
	          <s:else>
	          <tr>
	          </s:else>
	            <td class="left"><input type="checkbox" name="typeandids" value="<s:property value="applyType"/>,<s:property value="applyid"/>" class="hasBorder" /></td>
	            <td><s:date name="applyDate" format="yyyy-MM-dd HH:mm:ss"/></td>
	            <td><s:property value="projectname"/></td>
	            <s:if test="#session.loginMember.role == \"M\"">
	            <td><s:property value="coachname"/></td>
	            </s:if>
	            <s:else>
	            <td><s:property value="membername"/></td>
	            </s:else>
	            <td><s:property value="planDate"/> <s:property value="startTime"/>-<s:property value="endTime"/></td>
	            <td><s:property value="place"/></td>
	            <s:if test="query.status !=1">
	            <td><s:date name="approveDate" format="yyyy-MM-dd HH:mm:ss"/></td>
	            </s:if>
	          </tr>
	          </s:iterator>
	        </table>
	    </div>
		<s:include value="/share/pageAjax.jsp"/>
	  </s:if>
	  <s:else>
	   <div class="table">
	   <div style="width:750px; height:50px; border:1px solid #d3d3d2; background:#e8e8e8; margin:10px 10px 10px 10px; line-height:50px; font-size:14px; text-align:center;">
	      <p style="">你暂时还没有任何的预约，你可以去<a id="colotoa" href="">教练的课程表</a>或<a id="colotoa" href="">俱乐部的课程表</a>或<a id="colotoa" href="">俱乐部的场地表</a>查看相关信息，进行预约参加活动</p>
	   </div>
	   </div>
	  </s:else>
</s:form>
	</div>
	</div>
	<s:include value="/share/footer.jsp" />
</body>
</html>