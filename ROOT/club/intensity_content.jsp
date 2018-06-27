<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<s:form id="form" name="form" action="" method="post" theme="simple">
	<table id="contianer" class="goaltable">
		<tr class="tableTitle">
			<td width="28">&nbsp;</td>
			<td width="100">名称</td>
			<td>简称</td>
			<td width="100">强度</td>
			<td width="400">备注</td>
			<td></td>
		</tr>
		<s:iterator value="intensitys" status="st">
		<tr class="bc0">
			<td>
				<s:hidden name="%{'intensitys['+#st.index+'].id'}" />
				<s:hidden name="%{'intensitys['+#st.index+'].member'}" />
				<s:hidden name="%{'intensitys['+#st.index+'].sort'}" />
				<s:hidden name="%{'intensitys['+#st.index+'].system'}" />
				<s:hidden name="%{'intensitys['+#st.index+'].name'}"/>
				<s:hidden name="%{'intensitys['+#st.index+'].shortName'}"/>
				<s:hidden name="%{'intensitys['+#st.index+'].mode'}"/>
				<s:hidden name="%{'intensitys['+#st.index+'].memo'}"/>			
				<s:hidden name="%{'intensitys['+#st.index+'].intensity'}" />
				<input type="checkbox" name="ids" value="<s:property value="id"/>" class="hasBorder" />
			</td>
			<td><s:property value="name"/></td>
			<td><s:property value="shortName"/></td>
			<td><s:property value="intensity"/></td>
			<td><s:property value="memo"/></td>
			<td><s:if test="#st.first">
				<img src="images/allow5.gif" alt="down" onclick="onDown(this);" />
			</s:if> <s:elseif test="#st.last">
				<img src="images/allow4.gif" alt="up" onclick="onUp(this);" />
			</s:elseif> <s:else>
				<img src="images/allow5.gif" alt="down" onclick="onDown(this);" />
				<img src="images/allow4.gif" alt="up" onclick="onUp(this);" />
			</s:else></td>
		</tr>
		</s:iterator>
	</table>
</s:form>
