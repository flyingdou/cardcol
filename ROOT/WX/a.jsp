<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>测试</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="js/jquery-2.2.0.min.js"></script>
<script type="text/javascript">
	function but() {
		$.ajax({
			url : "activelistwx!activeAll.asp",
			success : function(result) {
				$("#div1").html(result);
			}
		}); 
	}
</script>
</head>
<body>
	<input type="button" value="测试" onclick = "but()">
	<div align="center" style="border: solid;1xp;width: 50%;height: 50%" id="div1">
	</div>
</body>
</html>
