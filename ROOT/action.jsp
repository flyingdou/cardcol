<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="keywords" content="健身 健身俱乐部，健身教练，" />
<meta name="description" content="健身E卡通最大的网络健身平台，健身俱乐部，私人健身教练" />
<title>健身E卡通_健身电子商务-服务平台</title>
<script type="text/javascript" src="script/jquery-1.7.1.min.js"></script>
<script type="text/javascript">
function onSave() {
	var parms = $('#frm1').serialize();
	$.ajax({url: 'action!updateAll.asp', type: 'post', data: parms, 
		success: function(msg) {
			alert('所有数据已经成功保存！');
		}
	});
}
</script>
</head>
<body>
	<s:include value="share/home-header.jsp" />
	<s:form name="frm1" id="frm1" theme="simple">
	<table>
		<tr>
			<td>&nbsp;</td>
			<td>动作名称</td>
			<td>肌肉属性</td>
			<td>描述</td>
			<td>注意事项</td>
			<td>演示图片</td>
			<td>演示动画</td>
			<td>演示视频</td>
			<td>必须输入重量</td>
			<td>必须输入距离</td>
			<td>必须输入时间</td>
		</tr>
		<s:iterator value="actions" status="st">
		<tr class="bc0">
			<td><s:property value="#st.index+1"/></td>
			<td noWrap>
				<s:hidden name="%{'actions['+#st.index+'].id'}" />
				<s:hidden name="%{'actions['+#st.index+'].part.id'}" />
				<s:hidden name="%{'actions['+#st.index+'].system'}" />
				<s:textfield name="%{'actions['+#st.index+'].name'}" />
			</td>
			<td noWrap><s:textfield name="%{'actions['+#st.index+'].muscle'}" /></td>
			<td noWrap><s:textarea name="%{'actions['+#st.index+'].descr'}" /></td>
			<td noWrap><s:textarea name="%{'actions['+#st.index+'].regard'}" /></td>
			<td noWrap><s:textfield name="%{'actions['+#st.index+'].image'}" /></td>
			<td noWrap><s:textfield name="%{'actions['+#st.index+'].flash'}" /></td>
			<td noWrap><s:textfield name="%{'actions['+#st.index+'].video'}" /></td>
			<td noWrap><s:textfield name="%{'actions['+#st.index+'].needWeight'}" /></td>
			<td noWrap><s:textfield name="%{'actions['+#st.index+'].needDistance'}" /></td>
			<td noWrap><s:textfield name="%{'actions['+#st.index+'].needTime'}" /></td>
		</tr>
		</s:iterator>
	</table>
	<input type="button" value="保存" onclick="javascript: onSave()"></input>
	</s:form>
	<s:include value="share/footer.jsp" />

</body>
</html>
