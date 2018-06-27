<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<s:include value="/share/meta.jsp" />
<meta name="keywords" content="我的社区" />
<meta name="description" content="我的社区" />
<title>我的社区</title>
<link href="css/sns.css" rel="stylesheet" type="text/css" />
</head>
<body>
	<s:include value="/share/header.jsp" />
	
	<div id="content">
	
		<div id="left-1">
		<s:include value="/share/sns_nav.jsp"/>
	</div>
	
		<div id="right-2">
			<h1>正文</h1>
			<div>
				<div class="rightfirst">
					<h3><s:property value="blog.title"/></h3>
					<span>(<s:date name="blog.createTime" format="yyyy-MM-ss hh:mm:ss"/>)</span>
					<span><a href="#">[编辑]</a> <a href="#">[删除]</a></span>
				</div>
				<div class="rightsecond">
					<dl>
						<dt>标签：</dt>
						<dd>
							<a href="#"><s:property value="blog.label"/>&nbsp;</a>
						</dd>
					</dl>
				</div>
				<div class="rightthird">
					<s:property value="blog.content" escape="false"/>
				</div>
				<div class="rightfourth">
					<ul>
						<li>阅读(29)</li>
						<li><a href="#">评论</a>(2)</li>
					</ul>
				</div>
				<div class="rightfifth">
					<dl>
						<dt>后一篇：</dt>
						<dd>
							<a href="#">PTstudio新版测试过程中</a>
						</dd>
					</dl>
				</div>
				<div class="rightsixth">
					<h1>
						评论<a href="#">发评论</a>
					</h1>
					<ul>
						<li><a href="#">xingxiang</a></li>
						<li>2010-08-16 09:37:40</li>
						<li><a href="#">[删除]</a></li>
					</ul>
					<p>good</p>
				</div>
				<div class="rightseventh">
					<h1>发评论</h1>
					<textarea rows="10" cols="50"></textarea>
					<p>
						<input type="button" name="button" value="发评论" />
					</p>
				</div>
	
	
			</div>
		</div>
	
	</div>
	
	<s:include value="/share/footer.jsp" />
</body>
</html>
</html>