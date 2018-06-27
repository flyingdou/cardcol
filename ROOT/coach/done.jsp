﻿<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<s:include value="/share/meta.jsp" />
<meta name="keywords" content="健身E卡通-我的设定" />
<meta name="description" content="健身E卡通-我的设定" />
<title>健身E卡通-我的设定</title>
<link href="css/user-config.css" rel="stylesheet" type="text/css" />
<link type="text/css" rel="stylesheet" href="css/pulicstyle.css"/>
</head>
<body>
	<s:include value="/share/header.jsp" />
	<div id="content">
		<div class="my-setting">
			<ul id="nextstep">
				<li class="first">一.设定风格</li>
				<li>二.设定课程</li>
				<li>三.设定健身项目</li>
				<li>四.设定身体部位</li>
				<li>五.设定训练动作</li>
				<li class="first last">六.完成</li>
			</ul>
		</div>
		<form id="form" name="" action="" method="post">
			<div id="center-1">
				<div class="my-target">
				   <div class="sldiv1" >
				      <img src="images/xiao.jpg"/>
				   </div>
				   <div  class="sldiv2" >
				      <p class="sldiv2p1">我已经完成上述设定</p>
					  <p class="sldiv2p2">现在可以去服务会员了。</p>
				   </div>
				</div>
			</div>
		</form>
		<div class="stepoperate">
			<a href="coach.asp" title="返回首页" class="butnext">返回首页</a>
		</div>
	</div>
	<s:include value="/share/footer.jsp"/>
</body>
</html>