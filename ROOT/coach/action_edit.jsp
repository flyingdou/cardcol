<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript" src="script/uploadPreview.js"></script>
<script type="text/javascript">
	$(function() {
		new uploadPreview({
			UpBtn : "file",
			ImgShow : "preview"
		});
	});
</script>
<s:form theme="simple" name="form1" id="form1"
	enctype="multipart/form-data">
	<s:hidden name="action.system" value="0" />
	<s:hidden name="action.id" id="actionId" />
	<s:hidden name="action.video" id="actionVideo" />
	<s:hidden name="action.image"/>
	<div class="firstthree">
		动作名称：
		<s:textfield name="action.name" cssClass="text" cssStyle="border:1px solid #ccc;" />
	</div>
	<div class="firstthree-1">
		<div>锻炼部位：</div>
		<s:select name="action.part.id" list="#request.parts" listKey="id" listValue="name" cssStyle="margin-left:5px; width:160px;border:1px solid #ccc;" />
	</div>
	<s:if test="mode != 0 && mode !=3 && mode!=2">
		<div class="firstthree-2" id="divmuscle">
			<div class="muscle">锻炼肌肉：</div>
			<div class="muscle1">s
				<s:checkboxlist list="#request.muscles" listKey="name"
					listValue="name" name="action.muscle"
					value="#request.appliedmuscles" cssStyle="border:1px solid #ccc;" />
			</div>
		</div>
		<div class="firstthree-3" id="divneedWeight">
			是否输入重量：
			<s:radio list="#{'1':'是','0':'否'}" listKey="key" listValue="value" name="action.needWeight"></s:radio>
		</div>
	</s:if>
	<div class="firstthree">
		动作描述：
		<s:textarea rows="2" cssStyle="vertical-align: top; border:1px solid #ccc;" cols="40" name="action.descr" />
	</div>
	<div class="firstthree">
		注意事项：
		<s:textarea cols="40" cssStyle="vertical-align: top; border:1px solid #ccc;" name="action.regard" />
	</div>
	<div class="xiugai1-1">
		添加本地视频：
		<s:file name="file.file" />
	</div>
	<div class="xiugai1-1">
		缩略图： <img id="preview" width="60px" height="60px"
			<s:if test="action==null || action.image == null || action.image == ''">src="images/img-defaul.jpg"</s:if>
			<s:else> src="<s:property value='action.image'/>"</s:else>
			onclick="javascript: $('#file').click();" />
		<s:file id="file" name="image" style="display: none;" />
	</div>
	<div class="xiugai1-2">
		<input type="button" name="button" class="button" value="保存"
			onclick="onSave()" /> <input type="button" name="button"
			class="button" value="关闭" onclick="onClose()" />
	</div>
</s:form>
