<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript" src="script/FormValidator/formValidator-4.1.1.js"></script>
<script type="text/javascript" src="script/FormValidator/formValidatorRegex.js"></script>
<script type="text/javascript">
function queryPlanInfo(planId,planType) {
	if(planType == '3'){
		$('#queryForm').attr("action", 'plan.asp?pid='+pid);
	}else{
		$('#queryForm').attr("action", 'goods.asp?goodsId='+goodsId);
	}
	$('#queryForm').submit();
}
</script>
<s:form id ="queryForm" name="queryForm" method="post" action="plan!queryOther.asp" theme="simple">
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
           <td class="left"> <div style="word-break:break-all;padding-left:10px;">
          	<a href="javascript:queryPlanInfo('<s:property value="planId"/>','<s:property value="planType"/>');"><s:property value="planName"/></a></div>
          	</td>
          <td> <div style="word-break:break-all;padding-left:10px;"><s:property value="price"/>元</div></td>
		  <td> <div style="word-break:break-all;padding-left:10px;"><s:property value="publishTime"/></div></td>
		  <td> <div style="word-break:break-all;padding-left:10px;">
		  	<s:if test="audit == \"1\"">审核通过</s:if>
		  	<s:else>待审核</s:else></div>
		  </td>
		  <td> <div style="word-break:break-all;padding-left:10px;">
		    <a id="colotoa" href="javascript:queryPlanInfo('<s:property value="planId"/>','<s:property value="planType"/>');">购买</a></div>
		  </td>
		  </tr>
        </s:iterator>
		</table>
      </div>
       <s:include value="/share/pageAjax.jsp"/>
     
    </div>
</s:form>
