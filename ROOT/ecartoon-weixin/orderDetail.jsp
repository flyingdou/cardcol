<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":"+ request.getServerPort() + path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>加载中...</title>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
<script type="text/javascript">
	var page = {}
	page.params = location.href.split("?")[1].split("&");
	page.orderId = page.params[0].split("=")[1];
	page.orderType = page.params[1].split("=")[1];
	
	if(page.orderType == 5){
		location.href="ecoursewx!findCourseDetail.asp?orderId="+page.orderId;
	}else if(page.orderType == 2){
		location.href="ecoursewx!getActive.asp?type=4&orderId="+page.orderId;
	}else if(page.orderType == 8){
		location.href="eproductorderwx!oneCardOrderDetail.asp?id="+page.orderId;
	}else if(page.orderType == 6){
		location.href="ecoursewx!loadPlan.asp";
	}
</script>
</body>
</html>