<%@page import="java.util.Random"%>
<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="h" uri="/html" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title><h:title key="system.app.name"/></title>
</head>
<body>
<%
Random random = new Random();
for (int i = 0; i < 240; i++) {
	Integer val1 = random.nextInt(20);
	Integer val2 = random.nextInt(20);
	if (val1 == 0 || val2 == 0) continue;
	final Integer result=  val1 + val2;
	if (result > 20 || result < 10) continue;
	out.println("<div style=\"width:25%;float: left;\">" + val1 + " + " + val2 + " = " + "</div>");
}
%>
</body>
</html>
