<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="keywords" content="网站首页" />
<meta name="description" content="网站首页" />
<title>网站首页</title>
<s:include value="/share/meta.jsp" />
<link type="text/css" href="css/bootstrap.css" rel="stylesheet">
<link type="text/css" href="css/base.css" rel="stylesheet">
<link href="css/changecity.css" rel="stylesheet" type="text/css" />
</head>
<body>
	<s:include value="/newpages/head.jsp"/>
	<div class="top_bg"></div>
	<div id="maincity">
		<div id="citytopa">
			<h1>
				进入<a href="#"><s:property value="#session.currentCity"/>站<span style="font-size: 20px;">>></span></a>
			</h1>
			<p>
				<label>热门城市：</label>
				<s:iterator value="#request.hotCitys"><a href="city!changeCity.asp?id=<s:property value="id" />"><s:property value="name"/></a></s:iterator>
			</p>
		</div>
		<div id="citytitlelist">
			<h2>按拼音首字母选择</h2>
			<ul>
				<s:iterator value="#request.openCitys">
				<li>
					<p>
						<label><strong><s:property value="key"/></strong></label>
						<div>
							<s:iterator value="value"><a href="city!changeCity.asp?id=<s:property value="id" />"><s:property value="name"/></a></s:iterator>
						</div>
					</p>
				</li>
				</s:iterator>
			</ul>
		</div>
	</div>
	<s:include value="/newpages/footer.jsp" />
</body>
</html>
