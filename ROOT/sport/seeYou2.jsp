<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%  
String path = request.getContextPath();  
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";  
%>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<base href="<%=basePath%>" >
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>数据处理中</title>
<script src="js/jquery.min.js"></script>
<script type="text/javascript" src="https://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<script type="text/javascript" src="js/utils.elisa.js"></script>
</head>
<body>
<h3 align="center" >数据处理中，请稍后...</h3>
<script type="text/javascript">
wxUtils.sign("swechatwx!sign.asp");
wx.ready(function(){
	wx.getLocation({
	    success: function (res) {
	    	location.href="sloginwx!getLocation.asp?latitude="+res.latitude+"&longitude="+res.longitude;
	    },
	    cancel: function (res) {
	        alert('用户拒绝授权获取地理位置');
	    }
	});
	wxUtils.scanQRCode({
		fun : function(res){
			alert(res);
		}
		
	});
});
</script>
</body>
</html>