<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
function addFactory() {
	var size = $('#factorytable').find('tr').length - 1;
	var html = $('#samples1').html().replace(new RegExp("XXXX","gm"), size + "");
	$('#factorytable').append(html);
}
function delFactory(){
	var chks = $('input[name="ids"]:checked');
	if (chks.length <= 0) {
		alert('请先选择需要删除的数据！');
		return;
	}
	var params = '';
	for (var i = 0 ; i < chks.length ; i++) {
		params += 'ids=' + $(chks[i]).val() + '&';
	}
	if (confirm('是否确认删除当前所有选择的数据？')) {
		$.ajax({type:'post',url:'factory!delete.asp',data: params,
			success: function(msg){
				for (var i = 0; i < chks.length; i++) {
					$(chks[i]).parents('tr').remove();
				}
				alert('所选择的数据已经成功删除！');
			}
		});
	}
}
function saveFactory(){
	var params = $('#form').serialize();
	$.ajax({type:'post',url: 'factory!save.asp',data: params, 
		success: function(msg){
			$('#context').html(msg);
			alert('当前场地数据已经成功保存！')
		}
	})
}
</script>
<s:form id="form" name="form" action="" method="post" theme="simple">
	<div id="center-1">
		<div class="configall">
			<table id="factorytable" class="conftable">
				<tr class="tableTitle">
					<td width="28" id="tdtop">&nbsp;</td>
					<td width="200" id="tdtop" type="text">场地名称</td>
					<td width="200" id="tdtop" type="text">运动项目</td>
					<td width="200" id="tdtop" type="text">备注</td>
					<td width="100" id="tdtop" type="text">是否生效</td>
				</tr>
				<s:iterator value="factorys" status="st">
				<tr class="bc0">
					<td width="28">
						<input type="checkbox" name="ids" value="<s:property value="id"/>" class="hasBorder" />
						<s:hidden name="%{'factorys['+#st.index+'].id'}"/>
						<s:hidden name="%{'factorys['+#st.index+'].club'}"/>
						<s:hidden name="%{'factorys['+#st.index+'].oldName'}" value="%{name}"/>
					</td>
					<td width="200"><s:textfield name="%{'factorys['+#st.index+'].name'}" cssClass="input2" /></td>
					<td width="200"><s:select name="%{'factorys['+#st.index+'].project'}" list="#request.projects" listKey="id" listValue="name" cssClass="input2" /></td>
					<td width="200"><s:textfield name="%{'factorys['+#st.index+'].memo'}" cssClass="input3" maxLength="200" /></td>
					<td width="100"><input type="checkbox" name="factorys[<s:property value="#st.index"/>].applied"  value="1" <s:if test="applied == '1'">checked="checked"</s:if> /></td>
				</tr>
				</s:iterator>
			</table>
			<div class="saveoperate">
				<span><a href="javascript:addFactory();" class="adat">新增</a></span>
				<span><a href="javascript:delFactory();" class="adel">删除</a></span>
				<span><a href="javascript:saveFactory();" class="asev">保存</a></span>
			</div>
		</div>
	</div>
</s:form>
