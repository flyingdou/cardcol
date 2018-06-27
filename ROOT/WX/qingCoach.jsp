<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta content="UTF-8" />
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" />
<title>聘请健身教练</title>
<link type="text/css" href="css/bootstrap.css" rel="stylesheet" />
<link type="text/css" href="css/style.css" rel="stylesheet" />
<link type="text/css" href="css/base.css" rel="stylesheet" />
<link type="text/css" href="css/index.css" rel="stylesheet" />
<base />
<script type="text/javascript" src="js/jquery-2.2.0.min.js"></script>
<script type="text/javascript" src="js/style.js"></script>
<script type="text/javascript" src="js/index.js"></script>
<script type="text/javascript">
	function query(speciality) {
		$.ajax({
			type : "post",
			url : "listcoachwx.asp",
			data : "speciality=" + speciality,
			success : function(msg) {
				$("#part").html($(msg).find("#part").html());
			}
		});
	}

</script>
</head>
<body>
	<div class="engage_teach container">
		<div class="table_title">
			<ul class="clearfix">
				<li class="col-xs-2"><a href="javaScript:query('A')">减脂</a></li>
				<li class="col-xs-2"><a href="javaScript:query('B')">增肌</a></li>
				<li class="col-xs-3"><a href="javaScript:query('C')">运动康复</a></li>
				<li class="col-xs-5"><a href="javaScript:query('D')">提高运动表现</a></li>
			</ul>
		</div>

		<div id="part">
			<div class="table_list">
				<s:iterator value="pageInfo.items">
					<div class="list">
						<a href="listcoachwx!detail.asp?id=<s:property value="id" />">
							<div class="pic fl">
								<img src="../picture/<s:property value="image"/>">
							</div>
							<div class="txt fl">
								<h6>
									<s:property value="name" />
								</h6>
								<p>
									 已有<span> <s:property value="grade" /></span>人评价
								</p>
							</div>
						</a>
					</div>
				</s:iterator>
			</div>
		</div>
	</div>
</body>
</html>