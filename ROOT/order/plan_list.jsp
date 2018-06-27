<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript" src="script/FormValidator/formValidator-4.1.1.js"></script>
<script type="text/javascript" src="script/FormValidator/formValidatorRegex.js"></script>
<script type="text/javascript">
function commPlanTop(id,topTime,planType){
	if(planType=='3'){
		$("#keyId").val(id);
	}else{
		$("#keyId1").val(id);
	}
	$("#planType").val(planType);
	if(topTime){
		$("#isTop").val("2");
	}else{
		$("#isTop").val("1");
	}
	$.ajax({type:"post",url:"plan!changeTopTime.asp",data:$('#queryForm').serialize(),
		success:function(msg){
			$("#right-2").html(msg);
		}
	});
}
function commPlanClose(id,isClose,planType){
	if(planType=='3'){
		$("#keyId").val(id);
		if(isClose == "1"){
			$("#isClose").val("2");
		}else{
			$("#isClose").val("1");
		}
	}else{
		$("#keyId1").val(id);
		if(isClose == "1"){
			$("#isClose1").val("2");
		}else{
			$("#isClose1").val("1");
		}
	}
	$("#planType").val(planType);
	$.ajax({type:"post",url:"plan!changeClose.asp",data:$('#queryForm').serialize(),
		success:function(msg){
			$("#right-2").html(msg);
		}
	});
}

function queryPlanInfo(planId,planType) {
	if(planType == '3'){
		$('#queryForm').attr("action", 'plan.asp?pid='+planId);
	}else{
		$('#queryForm').attr("action", 'goods.asp?goodsId='+goodsId);
	}
	$('#queryForm').submit();
}

function commPlanEdit(planId){
	$('#editPlan').val('1');
	$('#keyId').val(planId);
	$('#queryForm').attr('action','workout.asp');
	$('#queryForm').submit();
}
</script>
<s:form id ="queryForm" name="queryForm" method="post" action="plan!query.asp" theme="simple">
<s:hidden name="planRelease.id" id="keyId"/>
<s:hidden name="goods.id" id="keyId1"/>
<s:hidden name="planRelease.isClose" id="isClose"/>
<s:hidden name="goods.isClose" id="isClose1"/>
<s:hidden name="isTop" id="isTop"/>
<s:hidden name="planType" id="planType"/>
<s:hidden name="editPlan" id="editPlan"/>
    <h1>
    	健身计划
    </h1>
    <div>
      <div class="div2">
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
		  <tr class="end">
			 <th>名称</th>
			 <th>金额</th>
			 <th>时间</th>
			 <th>审核状态</th>
			 <th>操作</th>
		   </tr>
		   <s:iterator value="pageInfo.items" status="sta">
           <tr <s:if test="#sta.last">class="end"</s:if>>
           <td class="left"><div style="word-break:break-all;padding-left:10px;"><a href="javascript:queryPlanInfo('<s:property value="planId"/>','<s:property value="planType"/>');"><s:property value="planName"/></a></div></td>
          <td> <div style="word-break:break-all;padding-left:10px;"><s:property value="price"/>元</div></td>
		  <td> <div style="word-break:break-all;padding-left:10px;"><s:property value="publishTime"/></div></td>
		  <td> <div style="word-break:break-all;padding-left:10px;">
		  	<s:if test="audit == \"1\"">审核通过</s:if>
		  	<s:else>待审核</s:else>
		  	</div>
		  </td>
		  <td> <div style="word-break:break-all;padding-left:10px;">
          <a id="colotoa" href="javascript:commPlanClose('<s:property value="planId"/>','<s:property value="isClose"/>','<s:property value="planType"/>');"><s:if test="isClose==2">关闭</s:if><s:else>开启</s:else></a>
          <a id="colotoa" href="javascript:commPlanTop('<s:property value="planId"/>','<s:property value="topTime"/>','<s:property value="planType"/>');"><s:if test="topTime==null">置顶</s:if><s:else>取消置顶</s:else></a>
		  <s:if test="planType==\"3\"">
		  		<a id="colotoa" href="javascript:commPlanEdit('<s:property value="planId"/>');">编辑</a>
		  </s:if></div>
		  </td>
		  </tr>
        </s:iterator>
		</table>
      </div>
      <s:include value="/share/pageAjax.jsp"/>
    </div>
</s:form>
