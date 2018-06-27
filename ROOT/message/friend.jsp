<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<style type="text/css">
.rigdivp1{ width:170px;white-space: nowrap;word-break:break-all;word-wrap:break-word;}
</style>
<script type="text/javascript">
function queryByPage(currentPage){
	var params = {
			"type": $("#type").val(),
			"pageInfo.currentPage": currentPage
	}
	$.ajax({type:"post",url:"info!findFriend.asp",data:params,
		success:function(msg){
			$("#right-2").html(msg);
		}
	});
}
function relieve(id,memberName){
	if(!confirm("是否确定解除关系?")){
		return;
	}
	var params = "type="+$("#type").val();
	if(id){
		params+="&friend.id="+id;
	}
	$.ajax({type:"post",url:"info!saveRelieve.asp",data:params,
		success:function(msg){
			alert("你已成功解除与"+memberName+"的关系！");
			$("#right-2").html(msg);
		}
	});
}
function relieveAll(){
	var ids = [];
	$("input[name='ids']").each(function(){
		if($(this).attr("checked")){
			ids.push($(this).val());
		}
	});
	var params = {
			type: $("#type").val(),
			ids: JSON.stringify(ids)
	}
	$.ajax({type:"post",url:"info!saveRelieveAll.asp",data:params,
		success:function(msg){
			alert("操作成功!");
			$("#right-2").html(msg);
		}
	});
	console.log(ids);
}
function changeIsCore(id,isCore,type){
	$.ajax({type:"post",url:"info!saveChangeIsCore.asp",data:"friend.id="+id+"&friend.isCore="+isCore+"&type="+$("#type").val(),
		success:function(msg){
			alert("操作成功！");
			$("#right-2").html(msg);
		}
	});
}
function changeTopTime(id,topTime){
	var isTop;
	if(topTime){
		isTop = "2";
	}else{
		isTop = "1";
	}
	$.ajax({type:"post",url:"info!changeTopTime.asp",data:"friend.id="+id+"&type="+$("#type").val()+"&friend.topTime="+topTime,
		success:function(msg){
			alert("操作成功！");
			$("#right-2").html(msg);
		}
	});
}
function changeRole(id, friendId){
	if(confirm("是否确定设为教练?")){
		$.ajax({type:"post",url:"info!changeRole.asp",data:"id="+id+"&friend.id="+friendId,
			success:function(msg){
				alert("操作成功！");
				$("#right-2").html(msg);
			}
		});
	}
}
</script>
<s:form id ="infoForm" name="infoForm" method="post" theme="simple">
<s:hidden name="type" id="type"/>
 <h1>
 <s:if test="type==1">我的私人教练</s:if>
 <s:if test="type==2">我的俱乐部</s:if>
 <s:if test="type==3">我的教练</s:if>
 <s:if test="type==4">我的会员</s:if>
 </h1>
 <div class="rigdiv" style="white-space:normal;text-align:left;word-break:break-all;word-wrap:break-word;">
 	<s:if test="type==1 && #request.coach.id != '' && #request.coach.id != null">
 	<div class="rigdiv1" style="white-space:normal;word-break:break-all;word-wrap:break-word;">
         <div><a href="javascript:goMemberHome('<s:property value="#request.coach.id"/>','<s:property value="#request.coach.role"/>');"><s:if test="#request.coach.image == '' || #request.coach.image == null"><img src="images/userpho.jpg"/></s:if><s:else><img src="picture/<s:property value="#request.coach.image"/>"/></s:else></a></div>
		 <div class="rigdivp1">
            <p><a href="javascript:goMemberHome('<s:property value="#request.coach.id"/>','<s:property value="#request.coach.role"/>');"><s:property value="#request.coach.name"/></a></p>
			<p>
			  <a href="javascript:sendMessage('<s:property value="#request.coach.id"/>','<s:property value="#request.coach.name"/>');" id="colotoa">[发消息]</a>
			  <a href="javascript:relieve('','<s:property value="#request.coach.name"/>');" id="colotoa">[解除关系]</a>
			</p>
		 </div>
	</div>
	</s:if>
	<s:if test="type==2">
	<div class="top1">
		<p class="rigptop1">
			<!-- <a href="javascript:changeStatus('3');" id="colotoa">拒绝</a> -->
			<a href="javascript:relieveAll()" id="colotoa">[解除关系]</a>
			<input type="checkbox" onclick="selectAll('ids')" class="riginp4"/>
		</p>
	</div>
	<s:iterator value="#request.clubs">
	 	 <%-- <div class="rigdiv1" style="white-space:nowrap;word-break:break-all;word-wrap:break-word;">
	         <div><a href="javascript:goMemberHome('<s:property value="friend.id"/>','<s:property value="friend.role"/>');"><s:if test="friend.image == '' || friend.image == null"><img src="images/userpho.jpg"/></s:if><s:else><img src="picture/<s:property value="friend.image"/>"/></s:else></a></div>
			 <div class="rigdivp1">
	            <p>
	            <a href="javascript:goMemberHome('<s:property value="friend.id"/>','<s:property value="friend.role"/>');"><s:property value="friend.name"/></a>
	            <s:if test="#session.loginMember.role == \"S\"">
				<a href="#" id="colotoa"><s:if test="isCore == 0">[未设核心]</s:if><s:if test="isCore == 1">[已设核心]</s:if></a>
				</s:if>
				</p>
				<p>
	              <a href="javascript:sendMessage('<s:property value="friend.id"/>','<s:property value="friend.name"/>');" id="colotoa">[发消息]</a>		  
				  <a href="javascript:relieve('<s:property value="id"/>','<s:property value="friend.name"/>');" id="colotoa">[解除]</a>
				  <s:if test="#session.loginMember.role == \"S\"">
				  <s:if test="isCore == 0"><a href="javascript:changeIsCore('<s:property value="id"/>','1');" id="colotoa">[设置核心]</a></s:if>
				  <s:if test="isCore == 1"><a href="javascript:changeIsCore('<s:property value="id"/>','0');" id="colotoa">[取消核心]</a></s:if>
				  </s:if>
				</p>
			 </div>
		</div> --%>
		
		<div class="center1">
			<p class="image1">
				<input type="checkbox" name="ids" class="image" value="<s:property value="id"/>"/><a href="javascript:goMemberHome('<s:property value="member.id"/>','<s:property value="member.role"/>');"><s:if test="member.image == '' || member.image == null"><img src="images/userpho.jpg"/></s:if><s:else><img src="picture/<s:property value="member.image"/>"/></s:else></a>
			</p>
			<div class="message1">
				<a href="javascript:goMemberHome('<s:property value="friend.id"/>','<s:property value="friend.role"/>');"><s:property value="friend.name"/></a>
				<span class="mespan1"><s:date name="sendTime" format="yyyy-MM-dd HH:mm:ss"/></span> 
				<span class="mespan2">
					<s:if test="#session.loginMember.role == \"E\"">
					<%-- <a href="#" id="colotoa"><s:if test="isCore == 0">[未设核心]</s:if><s:if test="isCore == 1">[已设核心]</s:if></a> --%>
					<a id="colotoa" href="javascript:changeTopTime('<s:property value="id"/>','<s:property value="topTime"/>');"><s:if test="topTime==null">置顶</s:if><s:else>取消置顶</s:else></a>
					</s:if>
					 <s:if test="#session.loginMember.role == \"S\"">
					  <s:if test="isCore == 0"><a href="javascript:changeIsCore('<s:property value="id"/>','1');" id="colotoa">[设置核心]</a></s:if>
					  <s:if test="isCore == 1"><a href="javascript:changeIsCore('<s:property value="id"/>','0');" id="colotoa">[取消核心]</a></s:if>
					 </s:if>
					<a href="javascript:relieve('<s:property value="id"/>','<s:property value="nick"/>');" id="colotoa">[解除关系]</a>
				</span>
			</div>
			<p class="writing2">
				<s:if test="member.mobilephone != null && member.mobileValid == 1">
					联系电话 : <s:property value="member.mobilephone"/>
				</s:if>
				<s:else>
					联系电话 : 无
				</s:else>
			</p>
		</div> 
	</s:iterator>
	</s:if>
	<s:if test="type==3">
	<div class="top1">
		<p class="rigptop1">
			<!-- <a href="javascript:changeStatus('3');" id="colotoa">拒绝</a> -->
			<a href="javascript:relieveAll()" id="colotoa">[解除关系]</a>
			<input type="checkbox" onclick="selectAll('ids')" class="riginp4"/>
		</p>
	</div>
	<s:iterator value="#request.coachs">
		<div class="center1">
			<p class="image1">
				<input type="checkbox" name="ids" class="image" value="<s:property value="id"/>"/><a href="javascript:goMemberHome('<s:property value="member.id"/>','<s:property value="member.role"/>');"><s:if test="member.image == '' || member.image == null"><img src="images/userpho.jpg"/></s:if><s:else><img src="picture/<s:property value="member.image"/>"/></s:else></a>
			</p>
			<div class="message1">
				<s:if test="#session.loginMember.role == \"M\""><a href="javascript:goMemberHome('<s:property value="friend.id"/>','<s:property value="friend.role"/>');"><s:property value="friend.name"/></a></s:if>
		        <s:if test="#session.loginMember.role == \"E\""><a href="javascript:goMemberHome('<s:property value="member.id"/>','<s:property value="member.role"/>');"><s:property value="member.name"/></a></s:if>
				<span class="mespan1"><s:date name="sendTime" format="yyyy-MM-dd HH:mm:ss"/></span> 
				<span class="mespan2">
					<s:if test="#session.loginMember.role == \"E\"">
					<a href="#" id="colotoa"><s:if test="isCore == 0">[未设核心]</s:if><s:if test="isCore == 1">[已设核心]</s:if></a>
					<a id="colotoa" href="javascript:changeTopTime('<s:property value="id"/>','<s:property value="topTime"/>');"><s:if test="topTime==null">置顶</s:if><s:else>取消置顶</s:else></a>
					</s:if>
					<a href="javascript:relieve('<s:property value="id"/>','<s:property value="nick"/>');" id="colotoa">[解除关系]</a>
				</span>
			</div>
			<p class="writing2">
				<s:if test="member.mobilephone != null && member.mobileValid == 1">
					联系电话 : <s:property value="member.mobilephone"/>
				</s:if>
				<s:else>
					联系电话 : 无
				</s:else>
			</p>
		</div>
	</s:iterator>
	</s:if>
	<s:if test="type==4" >
	<div class="top1">
		<p class="rigptop1">
			<!-- <a href="javascript:changeStatus('3');" id="colotoa">拒绝</a> -->
			<a href="javascript:relieveAll()" id="colotoa">[解除关系]</a>
			<input type="checkbox" onclick="selectAll('ids')" class="riginp4"/>
		</p>
	</div>
	<s:iterator value="#request.members">
		<div class="center1">
			<p class="image1">
				<input type="checkbox" name="ids" class="image" value="<s:property value="id"/>"/><a href="javascript:goMemberHome('<s:property value="member.id"/>','<s:property value="member.role"/>');"><s:if test="member.image == '' || member.image == null"><img src="images/userpho.jpg"/></s:if><s:else><img src="picture/<s:property value="member.image"/>"/></s:else></a>
			</p>
			<div class="message1">
				<a href="javascript:goMemberHome('<s:property value="member.id"/>','<s:property value="member.role"/>');" id="colotoa" style="float: left;"><s:property value="member.name"/></a> 
				<span class="mespan1"><s:date name="sendTime" format="yyyy-MM-dd HH:mm:ss"/></span> 
				<span class="mespan2">
					<a href="javascript:relieve('<s:property value="id"/>','<s:property value="nick"/>');" id="colotoa">[解除关系]</a>
					<a href="javascript:changeRole('<s:property value="member.id"/>','<s:property value="id"/>');" id="colotoa">[设为教练]</a>
				</span>
			</div>
			<p class="writing2">
				<s:if test="member.mobilephone != null && member.mobileValid == 1">
					联系电话 : <s:property value="member.mobilephone"/>
				</s:if>
				<s:else>
					联系电话 : 无
				</s:else>
			</p>
		</div>
	</s:iterator>
	<s:iterator value="#request.memberList">
		<div class="center1">
			<p class="image1">
				<input type="checkbox" name="ids" class="image" value="<s:property value="id"/>"/><a href="javascript:goMemberHome('<s:property value="member.id"/>','<s:property value="member.role"/>');"><s:if test="member.image == '' || member.image == null"><img src="images/userpho.jpg"/></s:if><s:else><img src="picture/<s:property value="member.image"/>"/></s:else></a>
			</p>
			<div class="message1">
				<a href="javascript:goMemberHome('<s:property value="member.id"/>','<s:property value="member.role"/>');" id="colotoa" style="float: left;"><s:property value="member.name"/></a> 
				<span class="mespan1"><s:date name="sendTime" format="yyyy-MM-dd HH:mm:ss"/></span> 
				<span class="mespan2">
					<a href="javascript:relieve('<s:property value="id"/>','<s:property value="nick"/>');" id="colotoa">[解除关系]</a>
					<a href="javascript:changeRole('<s:property value="member.id"/>','<s:property value="id"/>');" id="colotoa">[设为教练]</a>
				</span>
			</div>
			<p class="writing2">
				<s:if test="member.mobilephone != null && member.mobileValid == 1">
					联系电话 : <s:property value="member.mobilephone"/>
				</s:if>
				<s:else>
					联系电话 : 无
				</s:else>
			</p>
		</div>
	</s:iterator>
	</s:if>
<!-- 分页信息 -->
<div class="plan_nav" style="margin-top: 0px; margin-right:auto;margin-left:auto;">
	<div class="plan_fenye">
		<span class="plan_yema"> <s:property value="pageInfo.currentPage" />
			</span>/<span> <s:property value="pageInfo.totalPage" /></span>
	</div>
	<s:if test="pageInfo.currentPage > 1">
		<a href="javascript:queryByPage('<s:property value="pageInfo.currentPage-1"/>');">
			<img src="images/plan_pre.gif" /></a>
	</s:if>
	<s:if test="pageInfo.currentPage < pageInfo.totalPage">
		<a href="javascript:queryByPage('<s:property value="pageInfo.currentPage+1"/>');">
			<img src="images/plan_next.gif" /></a>
	</s:if>
</div>
<s:hidden name="pageInfo.pageSize" id="pageSize"/>
<s:hidden name="pageInfo.currentPage" id="currentPage"/>
<s:hidden name="pageInfo.totalPage" id="totalPage"/>
<s:hidden name="pageInfo.totalCount" id="totalCount"/>
<s:hidden name="pageInfo.splitFlag" id="splitFlag"/>
<s:hidden name="pageInfo.order" id="order"/>
<s:hidden name="pageInfo.orderFlag" id="orderFlag"/>
</s:form>
</div>
