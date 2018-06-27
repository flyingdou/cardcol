<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
</script>
<s:form id ="coachStepForm" name="coachStepForm" method="post" action="coachlist.asp" theme="simple">
<s:iterator value="pageInfo.items">
<div>
	<a href="javascript:goMemberHome('<s:property value="id"/>','<s:property value="role"/>');"><s:if test="image == '' || image == null"><img src="images/userpho.jpg" alt="健身教练" class="img1"/></s:if><s:else><img src="picture/<s:property value="image"/>" alt="健身教练" class="img1"/></s:else></a>
	<dl>
		<dt>
			<a href="javascript:goMemberHome('<s:property value="id"/>','<s:property value="role"/>');"><b><s:property value="nick"/></b></a>
		</dt>
		<dd>专业的私教体型匀称、健美、肤色健康。口若悬河而v体型不好的未必有专业经验。</dd>
	</dl>
</div>
</s:iterator>
<s:hidden name="speciality" id="speciality"/>
<s:hidden name="mode" id="mode"/>
<s:hidden name="style" id="style"/>
<s:hidden name="typeId" id="typeId"/>
<s:hidden name="typeName" id="typeName"/>
<s:hidden name="pageInfo.start" id="pageStart"/>
<s:hidden name="pageInfo.end" id="pageEnd"/>
<s:hidden name="pageInfo.pageSize" id="pageSize"/>
<s:hidden name="pageInfo.currentPage" id="currentPage"/>
<s:hidden name="pageInfo.totalPage" id="totalPage"/>
<s:hidden name="pageInfo.totalCount" id="totalCount"/>
<s:hidden name="pageInfo.splitFlag" id="splitFlag"/>
<s:hidden name="pageInfo.order" id="order"/>
<s:hidden name="pageInfo.orderFlag" id="orderFlag"/>
</s:form>
