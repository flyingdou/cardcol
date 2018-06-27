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
			<div class="Profile-menu">评论</div>
			<div class="delete">
				<input type="checkbox" name="checkbox" value="checkbox" class="delete1" /> <a href="#">删除</a>
			</div>
			<div class="message">
				<div class="message-1">
					<input type="checkbox" name="checkbox2" value="checkbox" class="Box-1" /> <img src="images/dd-1_03.jpg" />
				</div>
				<div class="message-2">
					<div class="message-3">
						<span class="time">2010-08-10 14:08:55 <a href="#">[回复]</a>
							<a href="#">[删除]</a>
						</span><a href="#" id="message-4">王教练</a>&nbsp;对你的破问<a href="#"
							id="message-4">健美操之美</a>发表了评论
					</div>
					<div class="message-3">90分。欢迎参加ptstudio网络健身俱乐部。详细内容请浏览ptstudio网络健身平台首页</div>
				</div>
				<div class="clear"></div>
				<div class="ye">
					<ul>
						<li><a href="#">1</a></li>
						<li id="ye-2"><a href="#">2</a></li>
						<li id="ye-2"><a href="#">3</a></li>
						<li id="ye-2"><a href="#">4</a></li>
						<li id="ye-3"><a href="#">下一页&#8250;</a></li>
						<li id=""><a href="#">共4页</a></li>
					</ul>
					<div class="clear"></div>
				</div>
			</div>
			<div class="review-1">
				评论：<br />
				<textarea name="" cols="" rows="" class="review-2"></textarea>
				<br /> <input type="submit" name="Submit" value="写评论"
					class="button review-3" />
			</div>
		</div>
		<div class="clear"></div>
	</div>
</body>
</html>
