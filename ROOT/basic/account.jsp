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
<link type="text/css" rel="stylesheet" href="css/pulicstyle.css"/>
<script src="script/ckeditor/ckeditor.js" type="text/javascript"></script>
<script language="javascript">
var editor;
var msg = "<s:property value="message" escape="false"/>";
$(document).ready(function() {
	//editor = CKEDITOR.replace('editor1',{width: '600px', extraPlugins: 'uicolor',toolbar : [['Source','-','Preview','-','Cut','Copy','Paste','PasteText','-','SpellChecker', 'Scayt','Bold', 'Italic','Underline','-','Image', '-', 'Format','Font','FontSize']]});
	$('#birthday').datepicker({autoSize: true,showMonthAfterYear:true,changeMonth:true,yearRange:'c-50:c+5',changeYear:true});
	$('#left-1 ul li a').css('cursor', 'pointer');
	$('input[type="button"]').click(function(){
		if ($('#birthday') && $('#birthday').val() == '') {
			alert('请输入您的生日后再保存！');
			return;
		}
		$('#userform').submit();
	});
	if (msg != '') {
		alert(msg);
	}
});
</script>
<style type="text/css">
select {
	margin-top:5px;
 	border:1px solid #999;
}

</style>
</head>
<body>
	<s:include value="/share/header.jsp"/>
	<div id="content">
		<s:include value="/basic/nav.jsp" />
		<div id="right-2">
			<div id="container">
				<h1>基本信息</h1>
				<s:form theme="simple" id="userform" name="userform" action="account!save.asp" method="post">
				<s:hidden name="code" id="code"/>
				<s:hidden name="member.id"/>
				<div>
					<div class="p_div">用 户 名： <s:textfield name="member.nick" cssClass="name-1" id="nick" readonly="true" style=" background-color:#CCCCCC"/><span class="red_sign">*</span></div>
					<div class="p_div"><p> 昵&nbsp;&nbsp;称：<s:textfield name="member.name" cssClass="name-1" id="name" maxLength="50"/></p></div>
					<s:if test="member.role!=\"E\"">
					<div class="p_div">
						 出生日期： <input type="text" name="member.birthday" readonly="true" class="name-1" id="birthday" value='<s:date name="member.birthday" format="yyyy-MM-dd"/>'/><span class="red_sign">*</span>
					</div>
					<div class="paaco">性&nbsp;&nbsp;别：<s:radio name="member.sex" id="sex" list="#{'M':'男','F':'女'}" listKey="key" listValue="value" value="%{member.sex}"/>
					</div>
					</s:if>
					<s:if test="member.role!=\"M\"">
						<div class="description">简&nbsp;&nbsp;介：<span id="wb">
						<s:textarea rows="6" cols="50"  name="member.description" id="editor1"></s:textarea></span></div>
					</s:if>
				</div>
				</s:form>
			</div>
			<input type="button" name="botton" value="保存" class="save-to" />
		</div>
	</div>
	<s:include value="/share/footer.jsp"/>
</body>
</html>
