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
<h1>提醒</h1>
<div>
	 <div class="top1">
		<p class="rigptop1">
		  <a href="javascript:delMessage();" id="colotoa">删除</a> 
		  <input type="checkbox" onclick="selectAll('ids')" class="riginp4"/>
		</p>
	  </div>
	  <s:iterator value="pageInfo.items">
	  <div class="center1">
		<p class="image1">
		  <input type="checkbox" name="ids" class="image" value="<s:property value="id"/>"/><a href="javascript:goMemberHome('<s:property value="memberTo.id"/>','<s:property value="memberTo.role"/>');" id="colotoa" ><img src="images/img-defaul.jpg"/></a> 
		</p>
		<div class="message1"> 
		     <span style="float:left;"> <a href="#" id="colotoa" >系统提醒</a></span>
			 <span class="mespan1"><s:date name="sendTime" format="yyyy-MM-dd HH:mm:ss"/></span>
             <span class="mespan2"><a href="javascript:delMessage('<s:property value="id"/>');" id="colotoa">[删除]</a> </span> 
		</div>
		<p class="writing2"><s:property value="content" escape="false"/> </p>
	  </div>
	  </s:iterator>
	  <s:include value="/share/pageAjax.jsp"/>
</div>
</s:form>

