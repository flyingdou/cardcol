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
<form action="bodywx!writeSport.asp" method="post">
	<input type="hidden" name="trainRecord.doneDate" value="<s:date name="#request.doneDate" format="yyyy-MM-dd"/>"/>
   	<input type="hidden" name="trainRecord.action" value="<s:property value="#request.action"/>"/>
   	<input type="hidden" name="trainRecord.times" value="<s:property value="#request.actionquan"/>"/>
   	<input type="hidden" name="trainRecord.unit" value="<s:property value="#request.unit"/>"/>
   	<input type="hidden" name="trainRecord.times" value="<s:property value="#request.times"/>"/>
   	<input type="hidden" name="trainRecord.heartRate" value="<s:property value="#request.heartrate"/>"/>
    <div class="date_list">
        <div class="list">
            <img src="images/sportDate_icon_15.png">
            <span>体重</span>
            <span class="fr">KG</span>
            <input type="text" name="trainRecord.weight" value="" placeholder="请输入当前体重" class="fr">
        </div>
        <div class="list">
            <img src="images/sportDate_icon_15.png">
            <span>腰围</span>
            <span class="fr">CM</span>
            <input type="text" name="trainRecord.waist" value="" placeholder="请输入腰围" class="fr">
        </div>
        <div class="list">
            <img src="images/sportDate_icon_15.png">
            <span>臀围</span>
            <span class="fr">CM</span>
            <input type="text" name="trainRecord.hip" value="" placeholder="请输入臀围" class="fr">
        </div>
        <div class="list">
            <img src="images/sportDate_icon_15.png">
            <span>身高</span>
            <span class="fr">CM</span>
            <input type="text" name="setting.height" value="<s:property value="setting.height"/>" placeholder="请输入升高" class="fr">
        </div>
    </div>
    <div class="assign">
        <a><input type="submit" value="确 定" style="background-color: #FE9E3A "></a>
    </div>
    </form>
</div>
</body>
</html>