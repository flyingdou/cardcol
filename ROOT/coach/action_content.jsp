<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<s:form id="form" name="form" action="" method="post" theme="simple">
	<div style="margin-left:30px; width:870px;">
	<table id="partable" class="conftable">
		<tr class="tableTitle">
            <td id="tdtop" width="30px;">&nbsp;</td>
            <td nowrap id="tdtop" width="100">训练动作</td>
            <td nowrap id="tdtop" width="70">训练部位</td>
			<td nowrap id="tdtop"  width="160">训练肌肉</td>
			<td id="tdtop" width="290">动作描述</td>
			<td id="tdtop" width="220">注意事项</td>
		</tr>
		<s:iterator value="#request.actions" status="st">
		<tr class="bc0">
			<td>
				<s:if test="system == '1'">&nbsp;</s:if>
				<s:else><input type="checkbox" name="ids" value="<s:property value="id"/>" /></s:else>
			</td>
			<td nowrap aName="<s:property value="name"/>" image="<s:property value="image"/>" flash="<s:property value="flash"/>" descr="<s:property value="descr" escape="false"/>">
				<a href="javascript:lookup('<s:property value="id"/>')" id="colotoa"><s:property value="name"/></a>
			</td>
			<td nowrap><s:property value="part.name"/></td>
			<td style="text-align:center; padding-left:5px;"><s:property value="muscle"/></td>
			<td style="text-align:left; padding-left:5px;"><s:property value="descr"/></td>
			<td style="text-align:left; padding-left:5px;"><s:property value="regard"/></td>
		</tr>
		</s:iterator>
	</table>
	</div>
</s:form>
