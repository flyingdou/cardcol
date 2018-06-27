<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%
  String path = request.getContextPath();
  String basePath = request.getScheme() + "://" +request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<base href="<%=basePath%>" >
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<script src="js/jquery.min.js"></script>
<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js "></script>
<script type="text/javascript" src="js/utils.elisa.js"></script>
<script type="text/javascript">
    var upUrl = location.href.split("#")[0];
    wxUtils.sign("ewechatwx!sign.asp");
    wx.ready(function(){
    	wx.getLocation({
		    success: function (res) {
		    	var id = "";
		    	if(upUrl.indexOf("id") != -1){
		    	  id = upUrl.substring(upUrl.indexOf("id")+3,upUrl.length);
		    	}
		    	location.href="eproductwx!findProduct45Member.asp?latitude="+res.latitude+"&longitude="+res.longitude+"&id="+id;
		    },
		    cancel: function (res) {
		        alert('用户拒绝授权获取地理位置');
		    }
		});
    });
</script>
</head>
<body>
</body>
</html>