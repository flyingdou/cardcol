<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <title>运动数据</title>
    <link type="text/css" href="${pageContext.request.contextPath}/WX/css/bootstrap.css" rel="stylesheet">
    <link type="text/css" href="${pageContext.request.contextPath}/WX/css/base.css" rel="stylesheet">
    <link type="text/css" href="${pageContext.request.contextPath}/WX/css/style.css" rel="stylesheet">
    <link type="text/css" href="${pageContext.request.contextPath}/WX/css/index.css" rel="stylesheet">
</head>
<body>
<div class="sportDate container">
<form action=" bodywx!trainRizhis.asp" method="post">
<input type="hidden" name="trainRecord.doneDate" value="<s:property value="#request.doneDate"/>"/>
    <div class="date_list">
        <div class="list">
            <img src="images/sportDate_icon_03.png">
            <span>运动内容</span>
            <input type="text" name="trainRecord.action" value="" placeholder="输入运动内容" id="action" class="fr">
        </div>
        <div class="list">
            <img src="images/sportDate_icon_06.png">
            <span>本次成绩</span>
            <input type="text" name="trainRecord.actionQuan" value="" placeholder="输入成绩数值 " class="fr">
        </div>
        <div class="list">
            <img src="images/sportDate_icon_10.png">
            <span>运动成绩计量单位</span>
            <input type="text" name="trainRecord.unit" value="" placeholder="输入分钟、公斤、米... " class="fr">
        </div>
        
        <div class="list">
            <img src="images/sportDate_icon_12.png">
            <span>运动时间</span>
            <span class="fr">分钟</span>
            <input type="text" name="trainRecord.times" value="" placeholder="请输入运动时间" class="fr">
        </div>
        <div class="list">
            <img src="images/sportDate_icon_15.png">
            <span>最高心率</span>
            <span class="fr">次/分钟</span>
            <input type="text" name="trainRecord.heartRate" value="" placeholder="请输入最高心率" class="fr">
        </div>
    </div>
    <div class="assign">
        <a><input type="submit" value="确 定" style="background-color: #FE9E3A "></a>
    </div>
    </form>
</div>
</body>
</html>