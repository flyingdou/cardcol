<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<script type="text/javascript">
function changeStatus(complaintId,status){
	var msg = "";
	if(status == "1"){
		msg ="同意";
	}else if(status == "0"){
		msg ="不同意";
	}
	if(window.confirm("确认"+msg+"该处理结果？")){
		loadMask();
		$.ajax({type:'post',url:'complaint!changeStatus.asp',data:"complaint.id="+complaintId+"&type="+$("input[name='type']").val()+"&complaint.status="+status+"&query.delStatus="+$("input[name='query.delStatus']").val(),
			success:function(msg){
				removeMask();
				if(msg == "ok"){
					alert('当前状态已成功提交！');
				}else{
					alert(msg);
				}
			}
		});
	}
}
</script>
<s:form id ="showForm" name="showForm" method="post" action="complaint!showComplaint.asp" theme="simple">
	<s:iterator value="#request.complaintList" status="st" >
	<s:hidden name="type"/>
	<s:hidden name="query.delStatus"/>
    <h1>投诉详情</h1>
      <div class="first">
		<div class="details">
				<div class="list1">
					<span>订单号：</span>
					<a id="spanNo"><s:property value="orderNo"/></a>
					<span class="list1">投诉时间：</span>
					<span id="spanCompDate"><s:date name="compDate" format="yyyy-MM-dd HH:mm:ss"/></span>
					<span class="list1">投诉状态：</span>
					<span id="spanStatus">
					<s:if test="status==\"0\"">未处理</s:if>
					<s:if test="status==\"1\"">处理中</s:if>
					<s:if test="status==\"2\"">已处理</s:if>
					</span>
				</div>
			<div>
				<span class="the_defendant">被投诉方：</span>
				<a id="spanMemberTo"><s:property value="memberToName"/></a>
				<span class="accuser">投诉方：</span>
				<a id="spanMemberFrom"><s:property value="memberFromName"/></a>
			</div>
		</div>
		<div class="content_all3">
			<div class="content_title">
				<b class="title2">投诉内容：</b>
			</div>
			<div class="contentp">
				<p id="spanContent">
				<s:property value="content"/>
				</p>
			</div>
		</div>
		<div class="content_all3">
			<div class="content_title">
				<b class="title2">证&nbsp;&nbsp;据:</b>
			</div>
			<p class="evidence_pic">
				<img src='picture/<s:property value="affix"/>'  style="height:150px;"   id="spanAffix">
			</p>
		</div>
		<div class="content_all3">
				<div class="content_title">
					<b class="title2">处理结果：</b>
				</div>
				<div class="contentp">
					<p id="spanProcessResult">
					<s:property value="processResult" escape="false"/>
					</p>
				</div>
				<div class="option_btn">
					<s:if test="complaint.status != '2'">
					<input id="button1" type="button" value="同 意" onclick="changeStatus('<s:property value="complaint.id"/>','1')" />
					<input id="button0" type="button" value="不 同 意" onclick="changeStatus('<s:property value="complaint.id"/>','0')"/>
					</s:if>
				</div>
			</div>
		</div>
	</s:iterator>
</s:form>