<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<s:include value="/share/meta.jsp" />
<meta name="keywords" content="健身E卡通-我的账户" />
<meta name="description" content="健身E卡通-我的账户" />
<title>健身E卡通-我的账户</title>
</head>
<body>
	<s:include value="/share/header.jsp"/>
	<div id="content">
 		<div class="success">
			<div class="content-warning">
				<div class="first1">
					<img src="images/zheceok.jpg" width="50" height="50" border="0" alt="" />
				</div>
				<div class="first2">
					亲爱的<s:property value="#request.member.nick"/>会员，你的电子邮箱已经成功认证。
				</div>
			</div>
		</div>
	</div>
	<s:include value="/share/footer.jsp"/>
</body>
</html>
