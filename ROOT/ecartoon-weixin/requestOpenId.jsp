<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
</head>
<body>
</body>
<script>
var page = {
	status : ${status == null ? 0 : status}
};
if(page.status == 0){
	document.cookie = "href=>"+location.href.split("#")[0];
	location.href='https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx1cd3ec8ce0ff0116&redirect_uri=http://www.ecartoon.com.cn/eloginwx!shareLogin.asp&response_type=code&scope=snsapi_base&state=STATE#wechat_redirect';
}else if(page.status == 1){
	page.cookies = document.cookie.split(";");
	page.cookies.forEach(function(cookie){
		if(cookie.indexOf("href") != -1){
			location.href = cookie.split("=>")[1];
			
		}
	});
}
</script>
</html>