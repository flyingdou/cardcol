<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<s:include value="/share/meta.jsp" />
<meta name="keywords" content="社区" />
<meta name="description" content="社区" />
<title>我的社区</title>
<link href="css/all-index.css" rel="stylesheet" type="text/css" />
</head>
<body>
	<s:include value="/share/header.jsp" />
	<div class="content">
		<s:include value="/share/sns_left_nav.jsp" />
		<div class="profile-all">
			<div class="Profile-menu">我的好友</div>
			<div class="message-delete-1">
				<div class="Friends">
					<div class="Friends-img">
						<img src="../images/dd-1_03.jpg" />
					</div>
					<div class="Friends-1">
						<a href="#"><span class="Friends-2">我的好友</span></a><br /> 
						<span class=""><a href="#">[发消息]</a> <a href="#">[删除]</a> </span>
					</div>
					<div class="clear"></div>
				</div>
				<div class="Friends">
					<div class="Friends-img">
						<img src="../images/dd-1_03.jpg" />
					</div>
					<div class="Friends-1">
						<a href="#"><span class="Friends-2">健美俱乐部</span></a><br /> 
						<span class=""><a href="#">[发消息]</a> <a href="#">[删除]</a> </span>
					</div>
					<div class="clear"></div>
				</div>
				<div class="clear"></div>
			</div>
		</div>
		<div class="clear"></div>
	</div>
</body>
</html>
