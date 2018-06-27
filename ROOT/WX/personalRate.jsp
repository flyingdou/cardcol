<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <title>修改静心率</title>
    <link type="text/css" href="css/bootstrap.css" rel="stylesheet">
    <link type="text/css" href="css/base.css" rel="stylesheet">
    <link type="text/css" href="css/form.css" rel="stylesheet">
    <script type="text/javascript" src="js/jquery-2.2.0.min.js"></script>
    <script type="text/javascript" src="js/style.js"></script>
</head>
<body>
<div class="container rate">
    <form action="personalwx!saveHeart.asp" method="post">
        <input name="heart" type="text" placeholder="请输入静心率" onfocus="this.placeholder=''" onblur="this.placeholder='请输入静心率'" >&nbsp;/分钟
        <p>经过充足的休息（睡眠）后，测量每分钟心跳的次数</p>
        <input type="submit" value="保存" class="send">
    </form>
</div>
</body>
</html>