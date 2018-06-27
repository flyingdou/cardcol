<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<link rel="stylesheet" type="text/css" href="css/pulic-1.css"/>
<link rel="stylesheet" type="text/css" href="css/product.css"/>
<script type="text/javascript">
	function onAdd(){
		var path = document.getElementById("picFile").value;
		if(path==null||path==''){
			alert('图片路径不能为空，请添加路径');
			return;
		}
		var pos1 = path.lastIndexOf("\\");
		var pos2 = path.lastIndexOf(".");
		var pos = path.substring(pos1+1)
		var yanz = path.substring(pos2+1);
		if(yanz!='jpg'&&yanz!='jpeg'&&yanz!='png'){
			alert("请选择正确的图片格式！");
			return;
		}
		url="plan!savePicture.asp?fileName="+pos+"&pid="+'<s:property value="#request.id"/>';
		$('#frmimage').attr('action', url);
		$('#frmimage').submit();
	}
	function onReset(){
		url="plan.asp?pid="+'<s:property value="#request.id"/>';
		$('#frmimage').attr('action', url);
		$('#frmimage').submit();
	}
</script>

<div id="container_pro" >
<s:form name="frmimage" theme="simple" id="frmimage" action="plan.asp" enctype="multipart/form-data">
	<table style="width: 100%; border: 0px;">
		<tr>
			<td>选择图片文件：</td>
			<td><input type="file" name="picFile" id="picFile"/></td>
		</tr>
		<tr>
			<td><input type="button" name="musicAddr" class="button" value="确定" onclick="onAdd();"/></td>
			<td><input type="button" name="musicAddr" class="button" value="取消" onclick="onReset();"/></td>
		</tr>
		
	</table>
</s:form>
</div>
