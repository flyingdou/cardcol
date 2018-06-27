<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <title>修改地址</title>
    <link type="text/css" href="${pageContext.request.contextPath}/WX/css/bootstrap.css" rel="stylesheet">
    <link type="text/css" href="${pageContext.request.contextPath}/WX/css/LArea.min.css" rel="stylesheet">
    <link type="text/css" href="${pageContext.request.contextPath}/WX/css/base.css" rel="stylesheet">
    <link type="text/css" href="${pageContext.request.contextPath}/WX/css/form.css" rel="stylesheet">
    <script type="text/javascript" src="${pageContext.request.contextPath}/WX/js/jquery-2.2.0.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/WX/js/LArea.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/WX/js/LAreaData2.js"></script>
</head>
<body>
<div class="container">
    <form class="content-block" action="personalwx!saveAdd.asp" method="post">
        <input name="add" id="address" type="text" readonly="" placeholder="请输入地址"  value=""/>
        <input id="value2" type="hidden">
        <input type="submit" value="保存" class="send">
    </form>
</div>
<script type="text/javascript">
    var area2 = new LArea();
    area2.init({
        'trigger': '#address',
        'valueTo': '#value2',
        'keys': {
            id: 'value',
            name: 'text'
        },
        'type': 2,
        'data': [provs_data, citys_data, dists_data]
    })
</script>
</body>
</html>