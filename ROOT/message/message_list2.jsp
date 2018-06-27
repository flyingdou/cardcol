<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
function delMessage(id){
	if(!(id || $("input[name='ids']:checked").length>0)){
		alert("请先选择要操作的数据！");
		return;
	}
	if (confirm("是否确认删除当前数据？")) {
		if(id){
			$("input[name='msg.id']").val(id);
		}
		$.ajax({type:"post",url:"message!delete.asp",data:$('#queryForm').serialize(),
			success:function(msg){
				alert("当前数据已成功删除！");
				$("#right-2").html(msg);
			}
		});
	}
}
</script>
<s:form id ="queryForm" name="queryForm" method="post" action="message!query.asp" theme="simple">
<input type="hidden" name="msg.id"/>
<s:hidden name="queryType" id="queryType"/>
<h1>消息</h1>
<div>
	 <div class="top1">
		<p class="rigptop1">
			<input type="button" value="收件箱" class="riginp1" onclick="goMessage('2');"/>
			<input type="button" value="发件箱" class="riginp2" onclick="goMessage('4');"/>
			<input type="button" value="发消息" class="riginp3" onclick="sendMessage();"/>
			<a href="javascript:delMessage();" id="colotoa">删除</a> 
		    <input type="checkbox" onclick="selectAll('ids')" class="riginp4"/>
		</p>
	  </div>
	  <s:iterator value="pageInfo.items">
	  <div class="center1">
		<p class="image1">
			<input type="checkbox" name="ids" class="image" value="<s:property value="id"/>"/>
			<s:if test="queryType == 2"><a href="javascript:goMemberHome('<s:property value="memberFrom.id"/>','<s:property value="memberFrom.role"/>');" id="colotoa" ><s:if test="memberFrom.image == '' || memberFrom.image == null"><img src="images/userpho.jpg"/></s:if><s:else><img src="picture/<s:property value="memberFrom.image"/>"/></s:else></a></s:if>
			<s:if test="queryType == 4"><a href="javascript:goMemberHome('<s:property value="memberTo.id"/>','<s:property value="memberTo.role"/>');"id="colotoa" ><s:if test="memberTo.image == '' || memberTo.image == null"><img src="images/userpho.jpg"/></s:if><s:else><img src="picture/<s:property value="memberTo.image"/>"/></s:else></a></s:if>
		</p>
		<div class="message1"> 
		     <span style="float:left;" ><s:if test="queryType == 2"><a href="javascript:goMemberHome('<s:property value="memberFrom.id"/>','<s:property value="memberFrom.role"/>');" id="colotoa" ><s:property value="memberFrom.name"/></a></s:if><s:if test="queryType == 4">你对<a href="javascript:goMemberHome('<s:property value="memberTo.id"/>','<s:property value="memberTo.role"/>');" id="colotoa" ><s:property value="memberTo.name"/></a></s:if>说：</span>
			 <span class="mespan1"><s:date name="sendTime" format="yyyy-MM-dd HH:mm:ss"/></span>
             <span class="mespan2">
             	<s:if test="queryType == 2"><a href="javascript:sendMessage('<s:property value="memberFrom.id"/>','<s:property value="memberFrom.name"/>', <s:if test="parent == null"><s:property value="id"/></s:if><s:else><s:property value="parent"/></s:else>);" id="colotoa" >[回复]</a></s:if>
             	<a href="javascript:delMessage('<s:property value="id"/>');" id="colotoa">[删除]</a> 
             </span> 
		</div>
		<p class="writing2"><s:property value="content"/> </p>
	  </div>
	  </s:iterator>
	  <s:include value="/share/pageAjax.jsp"/>
</div>
</s:form>

