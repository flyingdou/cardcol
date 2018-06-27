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
<script type="text/javascript" src="script/area.js"></script>
<script type="text/javascript" src="script/jquery.choosearea.js"></script>
<script type="text/javascript" src="script/jquery.qrcode.min.js"></script>
</head>
<body>
	<s:include value="/share/header.jsp" />
	<div id="content">
		<s:include value="/basic/nav.jsp" />
		<div id="right-2">
			<div id="container" style="height: 60%">
				<h1>我的二维码</h1>
				<div style="width: 300px;height: 300px;margin-top: 5%;margin-left: 25%"><img src="picture/<s:property value="#request.member.id"/>.jpg" alt="" /></div>
			</div>
	</div>
	<s:include value="/share/footer.jsp" />
</body>
</html>
