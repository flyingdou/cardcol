<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>二维码页面</title>
</head>
<body>
	<img src="img/qrcode.jpg">
</body>
<script src="ecartoon-weixin/js/jquery.min.js"></script>
<script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<script src="http://www.ecartoon.com.cn/js/utils.elisa.js"></script>
<script>
wxUtils.sign("ewechatwx!sign.asp");
wx.ready(function(){
	wxUtils.share({
		title : "分享测试",
		desc : "测试分享出去的链接是否能获取到用户的信息",
		img : 'http://www.ecartoon.com.cn/ecartoon-weixin/img/share.jpg',
		link : 'https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx1cd3ec8ce0ff0116&redirect_uri=www.ecartoon.com.cn/ememberwx!findMe.asp&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect'
	});
});
</script>
</html>