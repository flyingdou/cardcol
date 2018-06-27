<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<s:include value="/share/meta.jsp" />
<meta name="keywords" content="健身E卡通-我的账户" />
<meta name="description" content="健身E卡通-我的账户" />
<title>健身E卡通-我的账户</title>
<link rel="stylesheet" type="text/css" href="css/user-account.css" />
<script src="script/ckeditor/ckeditor.js" type="text/javascript"></script>
<script language="javascript">
var editor;
$(function(){
	editor = CKEDITOR.replace('editor1',{extraPlugins: 'uicolor',toolbar : [['Source','-','Preview','-','Cut','Copy','Paste','PasteText','-','Image','SpellChecker', 'Scayt','Bold', 'Italic','Underline','Format','Font','FontSize']]});
	$('#left-1 ul li a').css('cursor', 'pointer');
	$('input[type="button"]').click(function(){
		$('#userform').submit();
	});
});
</script>
</head>
<body>
	<s:include value="/share/header.jsp"/>
	<div id="content">
		<s:include value="/basic/nav.jsp" />
		<div id="right-2">
			<div id="container" class="descra">
				<h1>我的描述</h1>
				<s:form theme="simple" id="userform" name="userform" action="description!save.asp" method="post" target="saveframe">
				<s:hidden name="code" id="code"/>
				<s:hidden name="member.id"/>
				<div class="descrb">
					<s:textarea name="member.description" cssStyle="vertical-align:top; width:100px;" id="editor1"/>
				</div>
				</s:form>
			</div>
			<input type="button" name="botton" value="保存" class="save-to" />
		</div>
	</div>
	<s:include value="/share/footer.jsp"/>
	<iframe name="saveframe" id="saveframe" style="display: none;"></iframe>
</body>
</html>
