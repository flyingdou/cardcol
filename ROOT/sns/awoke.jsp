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
			<div class="Profile-menu">提醒</div>
			<div class="delete">
				<input type="checkbox" name="checkbox" value="checkbox" class="delete1" /> <a href="#">删除</a>
			</div>
			<div class="message-delete">
				<div class="message-1">
					<input type="checkbox" name="checkbox2" value="checkbox" class="Box-1" /> <img src="images/dd-1_03.jpg" />
				</div>
				<div class="message-2">
					<div class="message-3">
						<span class="time">2010-08-10 14:08:55 <a href="#">[删除]</a>	</span>&nbsp;系统消息
					</div>
					<div class="message-3">您这一周没有履行合同</div>
				</div>
				<div class="clear"></div>
			</div>
		</div>
		<div class="clear"></div>
	</div>
</body>
</html>