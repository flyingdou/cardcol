<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
function goAjax(url){
	loadMask();
	$.ajax({type:"post",url:url,data:"toMember.id=<s:property value="toMember.id"/>",
		success:function(msg){
			$("#right-2").html(msg);
			removeMask();
		}
	}); 
}
$(document).ready(function(){
	if('<s:property value="goType"/>' == '1'){
		goAjax('appraise!queryOther.asp');
	}
});
</script>
<s:form id ="queryForm" name="queryForm" method="post" action="appraise!queryOther.asp" theme="simple">
    <h1>评价</h1>
    <div>
      <div class="top2">
      </div>
      <s:iterator value="pageInfo.items">
      <div class="center2">
        <p class="image2">
          <img src="picture/<s:property value="fromImage"/>"/> </p>
	      <div class="message2-3"> 
	      	<a href="javascript:goMemberHome('<s:property value="fromId"/>','<s:property value="fromRole"/>');" id="colotoa"><s:if test="fromId == toMember.id">我</s:if><s:else><s:property value="fromNick"/></s:else></a>
	      	&nbsp;对
	      	<a href="javascript:goMemberHome('<s:property value="toId"/>','<s:property value="toRole"/>');" id="colotoa"><s:if test="toId == toMember.id">我</s:if><s:else><s:property value="toNick"/></s:else></a>
	      	<s:if test="appType == \"2\"">的 <a href="#" id="colotoa"><s:property value="courseName"/></a>&nbsp;课程</s:if>
	      	&nbsp;的评价
	      	<span class="time2">
	      		<s:date name="appDate" format="yyyy-MM-dd HH:mm:ss"/>
	      	</span>
		  </div>
          <p class="writing2"> <s:property value="grade"/>分。<s:property value="content"/></p>
          <s:if test="reContent!=null && reContent!=''">
	      <div class="message2-3"> 
	      	<a href="javascript:goMemberHome('<s:property value="toId"/>','<s:property value="toRole"/>');" id="colotoa"><s:if test="toId == toMember.id">我</s:if><s:else><s:property value="toNick"/></s:else></a>
	      	&nbsp;对
	      	<a href="javascript:goMemberHome('<s:property value="fromId"/>','<s:property value="fromRole"/>');" id="colotoa"><s:if test="fromId == toMember.id">我</s:if><s:else><s:property value="fromNick"/></s:else></a>
	      	&nbsp;的回复
	      	<span class="time2">
	      		<s:date name="reAppDate" format="yyyy-MM-dd HH:mm:ss"/>
	      	</span>
		  </div>
          <p class="writing2"><s:property value="reContent"/> </p>	
          </s:if>       
      </div>
      </s:iterator>
     <s:include value="/share/pageAjax.jsp"/>
    </div>
</s:form>