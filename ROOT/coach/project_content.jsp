<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<s:form name="form" id="form" theme="simple" method="post">
	<table id="contianer" class="conftable">
		<tr class="tableTitle">
			<td id="tdtop" width=3%;>&nbsp;</td>
			<td id="tdtop" width=10%;>项目名称</td>
			<td id="tdtop" width=10%;>简称</td>
			<td id="tdtop" width=60%;>训练动作描述内容</td>
			<td id="tdtop" width=12%;>是否应用在健身计划中</td>
			<td id="tdtop" width=7%;>顺序</td>
		</tr>
		<s:iterator value="projects" status="st">
			<tr class="bc0">
				<td><s:hidden name="%{'projects['+#st.index+'].id'}" />
					<s:hidden name="%{'projects['+#st.index+'].member'}" />
					<s:hidden name="%{'projects['+#st.index+'].sort'}" />
					<s:hidden name="%{'projects['+#st.index+'].system'}" />
					<s:hidden name="%{'projects['+#st.index+'].name'}"/>
					<s:hidden name="%{'projects['+#st.index+'].shortName'}"/>
					<s:hidden name="%{'projects['+#st.index+'].mode'}"/>
					<s:hidden name="%{'projects['+#st.index+'].memo'}"/>
					<s:if test="system == '1'">&nbsp;</s:if><s:else><input type="checkbox" name="ids" value="<s:property value="id"/>" /></s:else>
					</td>
				
				<td><s:property value="name" /></td>
				<td><s:property value="shortName" /></td>
				<td style="text-align:left; padding-left:10px;"><s:property value="memo" escape="false"/></td>
				<td><input type="checkbox" name="projects[<s:property value="#st.index"/>].applied"  value="1" <s:if test="applied == '1'">checked="checked"</s:if> /></td>
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
