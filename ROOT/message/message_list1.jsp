<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
function changeStatus(status,id){
	if(!(id || $("input[name='ids']:checked").length>0)){
		alert("请先选择要操作的数据！");
		return;
	}
	var msgContent = "";
	if(status == "2"){
		msgContent = "同意";
	}else if(status == "3"){
		msgContent = "拒绝";
	}
	if (confirm("是否确认"+msgContent+"加入？")) {
		$("input[name='msg.status']").val(status);
		if(id){
			$("input[name='msg.id']").val(id);
		}
		$.ajax({type:"post",url:"message!changeStatus.asp",data:$('#queryForm').serialize(),
			success:function(msg){
				alert("当前数据已成功"+msgContent+"！");
				$("#right-2").html(msg);
				var msgbVal = $('#tishi1').html().replace('(', '').replace(')', '');
				if(id){
					$('.msgb').html($('.msgb').html()-1);
					$("#tishi1").html('('+(msgbVal-1)+')');
				}else{
					$('.msgb').html($('.msgb').html()-msgbVal);
					$("#tishi1").html("(0)");
				}
			}
		});
	}
}
</script>
<s:form id ="queryForm" name="queryForm" method="post" action="message!query.asp" theme="simple">
<input type="hidden" name="msg.id"/>
<input type="hidden" name="msg.status"/>
<s:hidden name="queryType" id="queryType"/>
<h1>审批</h1>
<div>
	<div class="top1">
		<p class="rigptop1">
			<a href="javascript:changeStatus('3');" id="colotoa">拒绝</a>
			<a href="javascript:changeStatus('2');" id="colotoa">同意</a>
			<input type="checkbox" onclick="selectAll('ids')" class="riginp4"/>
		</p>
	</div>
	<s:iterator value="pageInfo.items">
	<div class="center1">
		<p class="image1">
			<input type="checkbox" name="ids" class="image" value="<s:property value="id"/>"/><a href="javascript:goMemberHome('<s:property value="memberFrom.id"/>','<s:property value="memberFrom.role"/>');"><s:if test="memberFrom.image == '' || memberFrom.image == null"><img src="images/userpho.jpg"/></s:if><s:else><img src="picture/<s:property value="memberFrom.image"/>"/></s:else></a>
		</p>
		<div class="message1">
			<a href="javascript:goMemberHome('<s:property value="memberFrom.id"/>','<s:property value="memberFrom.role"/>');" id="colotoa" style="float: left;"><s:property value="memberFrom.name"/></a> 
			<span class="mespan1"><s:date name="sendTime" format="yyyy-MM-dd HH:mm:ss"/></span> 
			<span class="mespan2">
				<a href="javascript:changeStatus('2','<s:property value="id"/>');" id="colotoa">[同意]</a> 
				<a href="javascript:changeStatus('3','<s:property value="id"/>');" id="colotoa">[拒绝]</a>
			</span>
		</div>
		<p class="writing2"><s:property value="content"/></p>
	</div>
	</s:iterator>
	<s:include value="/share/pageAjax.jsp"/>
</div>
</s:form>
