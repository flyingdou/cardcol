<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>训练方案</title>
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport"/>
    <link type="text/css" href="css/bootstrap.css" rel="stylesheet"/>
    <link type="text/css" href="css/base.css" rel="stylesheet"/>
   	<link type="text/css" href="css/style.css" rel="stylesheet"/>
    <link type="text/css" href="css/index.css" rel="stylesheet"/>
    <script type="text/javascript" src="js/jquery-2.2.0.min.js"></script>
    <script type="text/javascript" src="js/style.js"></script>
</head>
<body>
<div class="my_challenge container">
    <div class="table_list">
    <s:property value=" "/>
        <s:if test="#request.list.size > 0">
        <s:iterator value="#request.list" >
	        <div class="list">
	            <div class="pic fl">
	            <%-- <img src="../picture/<s:property value=""/>" alt=""> --%>
	            <img src="images/<s:property value="action.image"/>" alt="" />
	            </img></div>
	                <div class="txt fl">
	                    <h6><s:property value="action.name"/></h6>
	                <%--     <s:iterator value="#request.lists">
	                    <p><s:property value="str"/></p>
	                    </s:iterator> --%>
	                    <s:iterator value="#request.str" id="a"><s:property value="#a"/></s:iterator>
	                </div>
	        </div>
	       </s:iterator>
	   	</s:if>
	   	<s:else>
	   	<div class="list">
	                <div class="txt fl">
	                    <h6>请前往卡库App添加训练动作</h6>
	                </div>
	        </div>
	   	</s:else>
    </div>
</div>
    <script type="text/javascript" src="js/imgs.js"></script>
</body>
</html>