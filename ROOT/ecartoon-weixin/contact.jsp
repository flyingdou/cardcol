<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
   String path = request.getContextPath();
   String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
%>
<!doctype html >
<html>
<head>
<base href="<%=basePath%>" />
<title>联系我们</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0"/>
<meta name="apple-mobile-web-app-capable" content="yes">
<link type="text/css" href="css/bootstrap.css" rel="stylesheet">
<link type="text/css" href="css/base.css" rel="stylesheet">
<link type="text/css" href="css/venue.css" rel="stylesheet">
<style type="text/css">
	.dou-contact{
       background: #f6f6f6;
       height: auto;
    }

    .contact{
       width: 60%;
       margin-top: 70px;
       margin-left: auto;
       margin-right: auto;
    }
    .navigate{
       padding-top:15px;
       padding-bottom:15px;
       font-weight: 500; 
       font-size: 14px; 
       box-sizing: border-box; 
       width: 100%; 
       height: auto;
    }
    .contact-font{
       width: 100%;
       text-align: center;
       background: white;
       padding:7% 0;
       margin-bottom: 100px;
    }
    
    
    .dou-font{
       width: 100%;
       text-align: center;
       overflow: hidden;
    }
    
    .contact-title{
       width: 80%;
       height: auto;
       font-size: 25px;
       text-align: left;
       font-weight: bold;
       margin-left: 10%;
       margin-bottom: 15px;
       color: #010101;
    }
    
    
    .contact-body{
       width: 80%;
       text-align: left;
       margin-left: auto;
       margin-right: auto;
       font-size: 20px;
       color: #010101;
    }
    
    .contact-text-1{
        margin-bottom: 20px;
    }
    
    .contact-text{
    	font-size: 14px;
       color: #808080;
    }
</style>
</head>
<body>
<div class="dou-font contact">
   <div class="contact-title">联系我们</div>
	   <div class="contact-body">
			 <div class="contact-text-1">
				    <span class="contact-text">总部 : 健易通信息科技（北京）有限公司</span>
				    <br>
					<span class="contact-text">地址：北京朝阳区东四环中路39号华业国际B1520</span>
					<br>
					<span class="contact-text">电话：010-65000620</span>
					<br>
					<span class="contact-text">邮箱：caijun@jszg.net</span>
				    <br>
			 </div>
			 <!-- <div class="contact-text-2">
			        <span class="contact-text">武汉好韵莱信息技术有限公司</span>
			        <br>
					<span class="contact-text">地址：湖北省武汉市鲁巷新都汇1栋2105</span>
					<br>
					<span class="contact-text">电话：13908653155</span>
					<br>
					<span class="contact-text">邮箱：917981712@qq.com</span>
					<br>
			 </div> -->
			 
	   </div>
	</div>
</body>
</html>