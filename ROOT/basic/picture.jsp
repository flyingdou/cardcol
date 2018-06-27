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
<link rel="stylesheet" type="text/css" href="css/pulicstyle.css" />
<script type="text/javascript" src="script/uploadPreview.js"></script>
<style>
	.xx{
		margin-right: 20px;
		padding-bottom: 30px;
		border-bottom: 1px solid #BBB;
	}
</style>
<script language="javascript">
	$(document).ready(function() {
		new uploadPreview({
			UpBtn : "file",
			ImgShow : "preview"
		});
		new uploadPreview({
			UpBtn : "file2",
			ImgShow : "preview2"
		});
		$('#left-1 ul li a').css('cursor', 'pointer');
		$('input[type="button"]').click(function() {
			$('#userform').attr('action', 'picture!save.asp');
			$('#userform').submit();
		});
	});
</script>
</head>
<body>
	<s:include value="/share/header.jsp" />
	<div id="content">
		<s:include value="/basic/nav.jsp" />
		<div id="right-2">
			<div id="container">
				<h1>头像/背景图</h1>
				<s:form theme="simple" id="userform" name="userform" method="post"
					enctype="multipart/form-data">
					<s:hidden name="code" id="code" />
					<s:hidden name="member.id" />
					<div class="xx">
						<p>
							上传头像：
							<s:file name="file.file" cssClass="photo" id="file" />
						</p>
						<p class="tou_xiang">
							<img id="preview" onclick="javascript: $('#file').click();"
								src='<s:if test="member.image == ''">images/userpho.jpg</s:if><s:else>picture/<s:property value="member.image"/></s:else>' />
						</p>
						<input type="button" name="botton" value="保存" class="save-to" style="margin-left: 292px;"/>
					</div>
				</s:form>
				<s:form action="picture!save2.asp" theme="simple" id="userform" name="userform" method="post"
					enctype="multipart/form-data">
					<s:hidden name="code" id="code" />
					<s:hidden name="member.id" />
					<div>
						<p>
							上传背景图：
							<s:file name="memberHead" cssClass="photo" id="file2" />
						</p>
						<p class="tou_xiang">
							<div style="margin-left: 210px;width: 260px;height: 110px;overflow: hidden;">
								<img id="preview2" onclick="javascript: $('#file2').click();" style="width: 100%;height: auto;"
									src='<s:if test="banner == ''"></s:if>
									<s:if test="banner == null"></s:if>
									<s:if test="banner != null && banner != ''">picture/<s:property value="banner"/></s:if>' />
							</div>
						</p>
						<input type="submit" name="botton" value="保存" class="save-to" style="margin-left: 292px;"/>
					</div>
				</s:form>
			</div>
		</div>
	</div>
	<s:include value="/share/footer.jsp" />
</body>
</html>
