<%@ page language="java" contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%
  String path = request.getContextPath();
  String basePath = request.getScheme() + "://" + request.getServerName() + request.getServerPort() + "/" + path;
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<s:include value="share/meta.jsp"></s:include>
<base href="<%=basePath%>" />
<meta name="keywords" content="社区" /> 
<meta name="description" content="社区"/>  
<title>会员</title>
</head>
<body>
<%
String pathDou = request.getServerName();
if (pathDou.indexOf("cardcol") != -1){
response.sendRedirect("index!cardcol.asp");
} else {
response.sendRedirect("index.asp");
}
%>
</body>
</html>
