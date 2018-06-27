<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <title>身高修改</title>
    <link type="text/css" href="css/bootstrap.css" rel="stylesheet">
    <link type="text/css" href="css/base.css" rel="stylesheet">
    <link type="text/css" href="css/sm.css" rel="stylesheet">
    <link type="text/css" href="css/style.css" rel="stylesheet">
    <link type="text/css" href="css/form.css" rel="stylesheet">
    <script type="text/javascript" src="js/zepto.js"></script>
</head>
<body>
<div class="container">
    <form action="personalwx!saveHeight.asp" method="post">
        <!-- <input name="height" type="text" placeholder="请输入您的身高（cm）" onfocus="this.placeholder=''" onblur="this.placeholder='请输入您的身高(cm)'" value="" class="newName picker"> -->
        <input name="height" type="text" placeholder="请输入您的身高（cm）"/>
        <input type="submit" value="保存" class="send">
    </form>
</div>
<div class="bg"></div>
<script type="text/javascript" src="js/sm.js"></script>
<script type="text/javascript" src="js/write.js"></script>
</body>
</html>