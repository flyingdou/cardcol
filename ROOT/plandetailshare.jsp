<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	   <meta name="keywords" content="健身E卡通 -首页介绍—下载APP" />
      <meta name="description" content="健身E卡通 -首页介绍—下载APP" />
<title>${p.planName}-健身E卡通</title>
<link href="css/bootstrap.min.css" rel="stylesheet">
 	<style>
    	#div{
			width:100%;
			float:left;
			position:fixed; 
			bottom:0;	
		}
		#div2{
			width:100%;
			_position:absolute; 
			position:fixed !important; 
 	 		overflow:hidden;
			bottom:0px; 
			right:0px;
		}
		#bg{
			background:url(picture/${p.image1}) no-repeat center center;
    		-webkit-background-size: cover;
   	 		-moz-background-size: cover;
    		-o-background-size: cover;
    		background-size: cover;
			height:200px;
			background-color: rgba(0,0,0,0.8);             /*增加遮罩*/
		}
		#image{
			 padding-top:50px;
			 padding-left:20px; 
			 width:140px; 
			 height:140px; 	
		}
		
		/*遮罩部分样式*/
		.zhezhao{
			position:absolute;
			top:0;
			width:100%;
			height:200px;
			background-color:rgba(0,0,0,0.7);
		}
    </style>
</head>
<body>
<div id="bg" class="">
<div  class="zhezhao">
	<div >
		<img src="picture/${p.image1}" class="img-responsive" id="image"/>
    </div>
    <div style="margin-left:170px;position: relative;top: -48%;">    <!-- 将margin-top:-90px; 改为position: relative;top: -48%;-->
    	<div style="line-height:20px; font-size:16px; color:#FFF;margin-bottom: 10px;"><s:property value="#request.p.planName"/></div><br />
        <div style=" margin-top:-20px;">
       		<img src="images/x1.png" style="width:16px; height:16px;"/>
            <img src="images/x1.png" style="width:16px; height:16px;"/>
            <img src="images/x1.png" style="width:16px; height:16px;"/>
            <img src="images/x1.png" style="width:16px; height:16px;"/>
       		<img src="images/x2.png" style="width:16px; height:16px;"/>
        </div>
        <div style=" margin-top:-20px; margin-left:120px; color:#F60"><s:if test="#request.planAppraise[0].avgGrade>0"><s:property value='#request.planAppraise[0].avgGrade' /></s:if><s:else>0</s:else>&nbsp;分</div>
        <div style="color:#CCC;margin-top: 5px;">${countperson}人 使 用 </div>
        <div style="color:#CCC; margin-top:5px;">作 者 : ${p.member.name}</div><br />
    </div>
    </div>
</div>
<div style="width:100%; height:15px; background-color:#EBEBEB;"></div>
<div>
	<div style="padding-left:3%; margin-top:5%;">计划类型</div>
    <div style="float:right; margin-right:3%; color:#999; margin-top:-20px;  ">${ptype}</div>
    <hr />
	<div style="padding-left:3%; margin-top:5%;">适用对象</div>
    <div style="float:right; margin-right:3%; color:#999; margin-top:-20px;">${papply}</div>
    <hr />
    <div style="padding-left:3%; margin-top:5%;">适用场景</span>
    <div style="float:right; margin-right:3%; color:#999; ">${pscene}</div>
    <hr />
	<div style=" margin-top:5%;">所需器材</div>
    <div style="float:right; margin-right:3%; color:#999; margin-top:-20px;">${p.apparatuses}</div>
    <hr />
    <div style=" margin-top:5%;">计划周期</div>
    <div style="float:right; margin-right:3%; color:#999; margin-top:-20px;">${p.planDay}天</div>
    <hr />
	<div style=" margin-top:5%;">计划简介</div>
    <div style=" color:#999;margin-top:10px;margin-bottom: 10px;padding: 0 5px;">&nbsp;&nbsp;&nbsp;&nbsp;${p.briefing}</div>
</div>
<div style="width:100%; height:30px; background-color:#EBEBEB; margin-bottom:13%;"></div>
 	<div id="div" style="float: left:;">
    	<a href="https://itunes.apple.com/cn/app/ka-ku-jian-shen/id848860632?mt=8" target="_blank">
    		<img src="images/iphone86.png" class="img-responsive"  style="width: 50%; float: left;">
    	</a>
    	<a href="app/cardcol.apk" target="_blank">
    		<img src="images/android86.png" class="img-responsive" style="width:50%;float: right; ">
        </a>
    </div>
    <script src="js/jquery.min.js"></script>
    <script src="js/bootstrap.min.js"></script>  
    
    
    
</body>
</html>