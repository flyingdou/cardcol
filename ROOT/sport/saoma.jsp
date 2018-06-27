<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>扫码签到</title>
<script src="js/jquery.min.js"></script>
<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<script type="text/javascript" src="js/utils.elisa.js"></script>
</head>
<body>
<script type="text/javascript">
wxUtils.sign("swechatwx!sign.asp");
wx.ready(function(){
	wxUtils.getLocation({
		fun:function(res){
			wxUtils.scanQRCode({
				fun:function(code){
	    			location.href="ssignwx!findOrder.asp?cid="+code+"&signLat="+res.latitude+"&signLng="+res.longitude;
				}
			});
		}
	});
});
</script>
</body>
</html>