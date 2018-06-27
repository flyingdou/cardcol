<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<s:include value="/share/meta.jsp" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="keywords" content="健身E卡通客户服务中心" />
<meta name="description" content="健身E卡通客户服务中心" />
<title>健身E卡通客户服务中心</title>
<link rel="stylesheet" type="text/css" href="css/contentlist.css" />
<link rel="stylesheet" type="text/css" href="css/public.css" />

<link rel="stylesheet" type="text/css" href="css/pulic-1.css" />
<script type="text/javascript">
$(function(){
	$('.articlelist').click(function(){
		loadMask();
		var id = $(this).attr('id');
		var title = $(this).html();
		$.ajax({type:'post',url:'detail!loadArticle.asp', data: 'channel=' + $('#channel').val() + '&id=' + id, 
			success:function(msg){
				$('#divcontent').html(msg);
				$('.article-title').html(title);
				removeMask();
			}
		});
	});
});
</script>

</head>
<body>
	<!--<div id="header">header</div>-->
<div id="top">
	<div id="top-content">
		<p class="left">
			<s:if test="#session.loginMember != null">
				<a href="account.asp"><s:property value="#session.loginMember.name"/></a>　欢迎来到健身E卡通<a href="login!logout.asp" id="coloa">退出</a></s:if>
			<s:else>
				欢迎来到健身E卡通<a href="login.asp" id="coloa">请登录</a><span>|</span><a href="register.asp" id="coloa">注册</a>
			</s:else>
		</p>
        <p class="right">
			<a href="index.asp" id="coloa" target="_parent">健身E卡通首页</a>
			<span>|</span><a href="login.asp" id="coloa" target="_parent">我的健身E卡通</a>
			<span>|</span><a href="message.asp" id="coloa" target="_parent">消息(<b class="msgb">0</b>)</a>
			<span>|</span><a href="shop!queryShopping.asp" id="coloa" target="_parent"><b class="aimgb">购物车</b><b class="ainsp">0</b>件</a>
			<span>|</span><a href="service.asp" id="coloa" target="_parent">服务中心</a>
			<span>|</span><a href="index.asp" id="coloa" target="_parent">网站导航</a>
		</p>
	</div>
</div>
<div id="header">
	<div id="logo">
		<blockquote>
			<p>
				<a href="index.asp"><img src="images/logo.png" /></a>
			</p>
		</blockquote>
	</div>
	<div id="switch">
		<span class="city"><s:property value="city"/></span><a href="#" id="coloa">[切换城市]</a>
	</div>
	<div id="ad">
		<img src="images/ad.png" />
	</div>
</div>
 <div id="navlogo">
		<div id="nav-content">
			<ul>
				<s:iterator value="#request.channels">
				<li><a <s:if test="channel == id">class="active"</s:if> href="<s:if test="id==1">service.asp</s:if><s:else>detail.asp?channel=<s:property value="id"/></s:else>"><s:property value="name"/></a></li>
				</s:iterator>
			</ul>
		</div>
</div>
	<div id="main">
		<div id="main-left">
			<h1><s:property value="#request.sector.name"/></h1>
			<div class="content">
				<ul>
					<s:iterator value="#request.articles">
					<li><a class="articlelist" id="<s:property value="id"/>"><s:property value="title"/></a></li>
					</s:iterator>
				</ul>
			</div>
		</div>
		<div id="main-right">
			<h1>
				<a href="index.asp">首页</a> > <a href="service.asp">服务中心</a> >
				<a href="detail.asp?channel=<s:property value="channel"/>"><s:property value="#request.sector.name"/></a> >
				<span class="article-title"><s:property value="article.title"/></span>
			</h1>
			<div class="select" id="divcontent">
				<s:include value="/content/nr.jsp"/>
			</div>
		</div>
	</div>
	<s:include value="/share/footer.jsp" />
</body>
</html>
