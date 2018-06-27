<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta content="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <title>昵称修改</title>
    <link type="text/css" href="css/bootstrap.css" rel="stylesheet">
    <link type="text/css" href="css/base.css" rel="stylesheet">
    <link type="text/css" href="css/form.css" rel="stylesheet">
    <script type="text/javascript" src="js/jquery-2.2.0.min.js"></script>
   <script type="text/javascript" src="js/style.js"></script>
</head>
<body>
<div class="container">
    <form action="personalwx!savename.asp" method="post">
        <input name="member.name" type="text" placeholder="请输入您的昵称" onfocus="this.placeholder=''" onblur="this.placeholder='请输入您的昵称'" value="" class="newName"><br>
        <input type="submit" value="保存" class="send">
    </form>
</div>
</body>
</html>