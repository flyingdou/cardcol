<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
$(document).ready(function(){
	
});
function onQuery(){
	if(!$('input[name="queryOrder.no"]').val() && !$('input[name="queryMember.nick"]').val()){
		alert("必须输入一个查询条件！");
		return;
	}
	//queryByPage(1);
	loadMask();
	$.ajax({type:"post",url:jQuery('#queryForm').attr("action"),data:jQuery('#queryForm').serialize(),
		success:function(msg){
			$("#right-2").html(msg);
			removeMask();
		}
	});
}
</script>
<s:form id ="queryForm" name="queryForm" method="post" action="signin!querySign.asp" theme="simple">
  <h1>履约记录</h1>
  <div>
    <div class="date">输入订单号:
      <s:textfield name="queryOrder.no" cssClass="date-1" />
               用户
      <s:textfield name="queryMember.nick" cssClass="date-1" />        
      <!--  
      <s:select name="queryMember.id" list="#request.memberList" listKey="id" listValue="nick" headerKey="" headerValue="" cssClass="date-1" />
      -->
      <input type="button" name="button" value="查询" class="button1" onclick="onQuery();"/>
    </div>
    <p class="button"> </p>
    <div class="table">
      <table width="100%" border="0" cellpadding="0" cellspacing="0" >
        <tr class="end">
          <!-- <th id="tdtop">甲方</th> -->
          <th id="tdtop">合同方</th>
          <th id="tdtop">订单编号</th>
          <th id="tdtop">时段</th>
          <th id="tdtop">应到次数</th>
          <th id="tdtop">实到次数</th>
          <th id="tdtop">缺勤次数</th>
        </tr>
        <s:iterator value="#request.signCountList" var="signCount">
        <tr>
          <td class="left"><s:property value="#signCount[0]"/></td>
          <td><s:property value="#signCount[2]"/></td>
          <td><s:property value="#signCount[3]"/></td>
          <td><s:property value="#signCount[4]"/></td>
          <td><s:property value="#signCount[5]"/></td>
          <td class="right"><s:property value="#signCount[6]"/></td>
        </tr>
        </s:iterator>
      </table>
    </div>
    <!--  
	<s:include value="/share/pageAjax.jsp"/>
	-->
  </div>
</s:form>