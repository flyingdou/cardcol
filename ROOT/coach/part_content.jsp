<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<s:form id="form" name="form" action="" method="post" theme="simple">
	<s:hidden name="projectId" id="projectId"/>
	<div style="margin-left:30px; width:870px;">
		<table id="partable" class="conftable">
			<tr class="tableTitle">
				<td id="tdtop" width=30;>&nbsp;</td>
				<td id="tdtop"  width=275;>部位名称</td>
				<td id="tdtop" width=290;>肌肉属性</td>
				<td id="tdtop" width=275;>区域属性</td>
			</tr>
			<s:iterator value="parts" status="st">
			<tr class="bc0">
				<td>
					<s:hidden name="%{'parts['+#st.index+'].id'}"/>
					<s:hidden name="%{'parts['+#st.index+'].project.id'}"/>
					<s:hidden name="%{'parts['+#st.index+'].system'}"/>
					<s:if test="system == '1'">&nbsp;</s:if>
					<s:else><input type="checkbox" name="ids" class="tabBorder" value="<s:property value="id"/>" /></s:else>
				</td>
				<td><s:if test="system == '1'"><s:textfield name="%{'parts['+#st.index+'].name'}" readonly="true"/></s:if><s:else><s:textfield name="%{'parts['+#st.index+'].name'}"/></s:else></td>
				<td><s:if test="system == '1'"><s:textfield name="%{'parts['+#st.index+'].mgroup'}" readonly="true"/></s:if><s:else><s:textfield name="%{'parts['+#st.index+'].mgroup'}"/></s:else></td>
				<td><s:if test="system == '1'"><s:textfield name="%{'parts['+#st.index+'].marea'}" readonly="true"/></s:if><s:else><s:textfield name="%{'parts['+#st.index+'].marea'}"/></s:else><s:hidden name="%{'parts['+#st.index+'].member'}"/></td>
			</tr>
			</s:iterator>
		</table>
	</div>
</s:form>
