<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>正在提交订单中,请稍后...</title>
</head>
<body>
	<form action="eorderwx!saveProductType.asp" method="post" id="form1">
	    <input type="hidden" name="jsons" value='${json}' />
	    <script type="text/javascript">
	    	window.onload=function(){
	    		document.getElementById("form1").submit();
	    	}
	    </script>
	</form>
</body>
</html>