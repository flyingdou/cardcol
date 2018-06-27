<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <title>选择裁判</title>
    <link type="text/css" href="${pageContext.request.contextPath}/WX/css/bootstrap.css" rel="stylesheet">
    <link type="text/css" href="${pageContext.request.contextPath}/WX/css/base.css" rel="stylesheet">
    <link type="text/css" href="${pageContext.request.contextPath}/WX/css/style.css" rel="stylesheet">
    <link type="text/css" href="${pageContext.request.contextPath}/WX/css/index.css" rel="stylesheet">
    <script type="text/javascript" src="${pageContext.request.contextPath}/WX/js/jquery-2.2.0.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/WX/js/style.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/WX/js/choiceCp.js"></script>
</head>
<body>
<div class="choiceCp container">
    <div class="choiceTj clearfix">
        <div class="search fl" style="width: 100%">
            <input type="search" placeholder="搜索">
            <i class="glyphicon glyphicon-search search_icon"></i>
        </div>
    </div>
    <div class="table_list">
    <s:iterator value="#request.mb">
        <div class="list">
            <div class="pic fl"><img src="images/teachImg_03.png" ></div>
            <span><s:property value="name"/> </span>
            <label class="fr label_two"><input type="radio" name="checked" value="<s:property value="id"/>" checked class="check_two"></label>
        </div>
        </s:iterator>
    </div>
    <div class="assign">
        <a href="JavaScript:finish()">完成</a>
    </div>
</div>
<script>
function finish(){
	var val =  $("input[type='radio']:checked").val();
	var id ='<s:property value="active.id"/>';
	window.location.href = "activelistwx!over.asp?mid="+val+"&id="+id;
}
</script>
</body>
</html>